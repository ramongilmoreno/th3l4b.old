package com.th3l4b.srm.codegen.java.jdbc;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.th3l4b.common.data.nullsafe.NullSafe;
import com.th3l4b.common.text.AbstractPrintable;
import com.th3l4b.common.text.IndentedWriter;
import com.th3l4b.common.text.TextUtils;
import com.th3l4b.srm.base.IField;
import com.th3l4b.srm.base.normalized.INormalizedEntity;
import com.th3l4b.srm.base.normalized.INormalizedManyToOneRelationship;
import com.th3l4b.srm.base.normalized.INormalizedModel;
import com.th3l4b.srm.codegen.base.FileUtils;
import com.th3l4b.srm.codegen.base.names.BaseNames;
import com.th3l4b.srm.codegen.database.SQLNames;
import com.th3l4b.srm.codegen.java.basic.JavaNames;
import com.th3l4b.srm.codegen.java.jdbcruntime.AbstractJDBCEntityParser;
import com.th3l4b.srm.codegen.java.jdbcruntime.AbstractJDBCFinder;
import com.th3l4b.srm.codegen.java.jdbcruntime.AbstractJDBCSRMContext;
import com.th3l4b.srm.codegen.java.jdbcruntime.DefaultJDBCEntityParserContext;
import com.th3l4b.srm.codegen.java.jdbcruntime.IJDBCEntityParserContext;
import com.th3l4b.srm.codegen.java.jdbcruntime.IJDBCIdentifierParser;
import com.th3l4b.srm.codegen.java.jdbcruntime.IJDBCRuntimeType;
import com.th3l4b.srm.codegen.java.jdbcruntime.IJDBCRuntimeTypesContext;
import com.th3l4b.srm.codegen.java.jdbcruntime.IJDBCStatusParser;
import com.th3l4b.srm.runtime.DatabaseUtils;
import com.th3l4b.srm.runtime.IDatabaseConstants;
import com.th3l4b.srm.runtime.IIdentifier;
import com.th3l4b.srm.runtime.IModelUtils;
import com.th3l4b.types.base.ITypesConstants;

public class JDBCCodeGenerator {

	public void finder(final INormalizedModel model,
			final JDBCCodeGeneratorContext context) throws Exception {
		final BaseNames baseNames = context.getBaseNames();
		final JDBCNames jdbcNames = context.getJDBCNames();
		final JavaNames javaNames = context.getJavaNames();
		final SQLNames sqlNames = context.getSQLNames();
		final String clazz = jdbcNames.finderJDBC(model);
		final String pkg = jdbcNames.packageForJDBC(context);
		FileUtils.java(context, pkg, clazz, new AbstractPrintable() {
			@Override
			protected void printWithException(PrintWriter out) throws Exception {
				PrintWriter iout = IndentedWriter.get(out);
				PrintWriter iiout = IndentedWriter.get(iout);
				out.println("package " + pkg + ";");
				out.println();
				out.println("public abstract class " + clazz + " extends "
						+ AbstractJDBCFinder.class.getName() + " implements "
						+ javaNames.fqnBase(javaNames.finder(model), context)
						+ " {");

				// Get the entities (individually or all)
				for (INormalizedEntity ne : model.items()) {
					String neClazz = javaNames.fqn(javaNames.nameInterface(ne),
							context);
					iout.println("public " + neClazz + " get"
							+ baseNames.name(ne) + "("
							+ IIdentifier.class.getName() + " id) throws "
							+ Exception.class.getName() + " {");
					iiout.println("return find(" + neClazz + ".class, id);");
					iout.println("}");
				}

				for (INormalizedEntity ne : model.items()) {
					String neClazz = javaNames.fqn(javaNames.nameInterface(ne),
							context);
					iout.println("public " + Iterable.class.getName() + "<"
							+ neClazz + "> all" + baseNames.name(ne)
							+ "() throws " + Exception.class.getName() + " {");
					iiout.println("return all(" + neClazz + ".class);");
					iout.println("}");
				}

				// And the relationships
				for (INormalizedEntity ne : model.items()) {
					for (INormalizedManyToOneRelationship rel : ne
							.relationships().items()) {
						String clazzMany = javaNames.fqn(
								javaNames.nameInterface(ne), context);
						String clazzOne = javaNames.fqn(
								javaNames.nameInterface(model.get(rel.getTo())),
								context);
						String leading = "public " + Iterable.class.getName()
								+ "<" + clazzMany + "> findAll"
								+ baseNames.nameOfReverse(rel, model) + "From"
								+ baseNames.name(model.get(rel.getTo())) + "(";
						iout.println(leading + clazzOne + " from) throws "
								+ Exception.class.getName() + " {");
						iiout.println("return from != null ? findAll"
								+ baseNames.nameOfReverse(rel, model)
								+ "From"
								+ baseNames.name(model.get(rel.getTo()))
								+ "(from.coordinates().getIdentifier()) : null;");
						iout.println("}");
						iout.println(leading + IIdentifier.class.getName()
								+ " from) throws " + Exception.class.getName()
								+ " {");
						iiout.println("return find(" + clazzMany
								+ ".class, from, \""
								+ sqlNames.column(rel, true, model) + "\");");
						iout.println("}");
					}
				}
				out.println("}");
				iiout.flush();
				iout.flush();
			}
		});
	}

	private String fieldName(IField f, BaseNames names) throws Exception {
		return "_field_" + names.name(f);
	}

	public void entityParser(final INormalizedEntity entity,
			final INormalizedModel model, final JDBCCodeGeneratorContext context)
			throws Exception {
		final BaseNames baseNames = context.getBaseNames();
		final JDBCNames names = context.getJDBCNames();
		final JavaNames javaNames = context.getJavaNames();
		final SQLNames sqlNames = context.getSQLNames();
		final String clazz = names.parserJDBC(entity);
		final String pkg = names.packageForJDBCParsers(context);
		FileUtils.java(context, pkg, clazz, new AbstractPrintable() {
			@Override
			protected void printWithException(PrintWriter out) throws Exception {
				PrintWriter iout = IndentedWriter.get(out);
				PrintWriter iiout = IndentedWriter.get(iout);
				PrintWriter iiiout = IndentedWriter.get(iiout);
				out.println("package " + pkg + ";");
				out.println();
				String entityInterface = javaNames.fqn(
						javaNames.nameInterface(entity), context);
				out.println("public class " + clazz + " extends "
						+ AbstractJDBCEntityParser.class.getName() + "<"
						+ entityInterface + "> {");
				out.println();
				iout.println("private " + IJDBCRuntimeType.class.getName()
						+ "<" + Boolean.class.getName() + "> _isSet;");
				for (IField field : entity.items()) {
					iout.println("private "
							+ IJDBCRuntimeType.class.getName()
							+ "<"
							+ context.getTypes().get(field.getType())
									.getProperties()
									.get(ITypesConstants.PROPERTY_JAVA_CLASS)
							+ "> " + fieldName(field, baseNames) + ";");
				}
				out.println();
				iout.println("public " + clazz + "("
						+ IJDBCIdentifierParser.class.getName() + " ids, "
						+ IJDBCStatusParser.class.getName() + " status, "
						+ IJDBCRuntimeTypesContext.class.getName()
						+ " types) {");
				iiout.println("super(ids, status);");
				iiout.println("_isSet = types.get(\""
						+ TextUtils
								.escapeJavaString(IDatabaseConstants.BOOLEAN_TYPE)
						+ "\", " + Boolean.class.getName() + ".class);");
				for (IField field : entity.items()) {
					iiout.println(fieldName(field, baseNames)
							+ " = types.get(\""
							+ TextUtils.escapeJavaString(field.getType())
							+ "\", "
							+ context.getTypes().get(field.getType())
									.getProperties()
									.get(ITypesConstants.PROPERTY_JAVA_CLASS)
							+ ".class);");
				}
				iout.println("}");
				iout.println();

				iout.println("public " + String.class.getName()
						+ " table() throws " + Exception.class.getName()
						+ " { return \""
						+ TextUtils.escapeJavaString(sqlNames.table(entity))
						+ "\"; }");
				iout.println("public "
						+ String.class.getName()
						+ " idColumn() throws "
						+ Exception.class.getName()
						+ " { return \""
						+ TextUtils.escapeJavaString(DatabaseUtils.column(
								SQLNames.ID, true)) + "\"; }");
				iout.println("public "
						+ String.class.getName()
						+ " statusColumn() throws "
						+ Exception.class.getName()
						+ " { return \""
						+ TextUtils.escapeJavaString(DatabaseUtils.column(
								SQLNames.STATUS, true)) + "\"; }");
				iout.println("public "
						+ entityInterface
						+ " create() { return new "
						+ javaNames.fqnImpl(javaNames.nameImpl(entity), context)
						+ "(); }");
				iout.println("private static final String[] COLUMNS = {");
				boolean first = true;
				// Fill attributes
				for (IField field : entity.items()) {
					if (first) {
						first = false;
					} else {
						iiout.println(",");
					}
					iiout.println("\""
							+ TextUtils.escapeJavaString(sqlNames.column(field,
									false)) + "\",");
					iiout.print("\""
							+ TextUtils.escapeJavaString(sqlNames.column(field,
									true)) + "\"");
				}

				// Fill relationships
				for (INormalizedManyToOneRelationship rel : entity
						.relationships().items()) {
					if (first) {
						first = false;
					} else {
						iiout.println(",");
					}
					iiout.println("\""
							+ TextUtils.escapeJavaString(sqlNames.column(rel,
									false, model)) + "\",");
					iiout.print("\""
							+ TextUtils.escapeJavaString(sqlNames.column(rel,
									true, model)) + "\"");
				}
				if (!first) {
					iiout.println();
				}
				iout.println("};");
				iout.println("public " + String.class.getName()
						+ "[] fieldsColumns() throws "
						+ Exception.class.getName() + " { return COLUMNS; }");
				iout.println("public void parseRest(" + entityInterface
						+ " entity, int index, " + ResultSet.class.getName()
						+ " result) throws " + Exception.class.getName() + " {");
				for (IField field : entity.items()) {
					iiout.println("if (" + NullSafe.class.getName()
							+ ".equals(_isSet.parse(index++, result), "
							+ Boolean.class.getName() + ".TRUE)) {");
					iiiout.println("entity.set" + baseNames.name(field) + "("
							+ fieldName(field, baseNames)
							+ ".parse(index++, result));");
					iiout.println("} else {");
					iiiout.println("index++;");
					iiout.println("}");
				}
				for (INormalizedManyToOneRelationship rel : entity
						.relationships().items()) {
					String relName = baseNames.nameOfDirect(rel, model);
					iiout.println("if (" + NullSafe.class.getName()
							+ ".equals(_isSet.parse(index++, result), "
							+ Boolean.class.getName() + ".TRUE)) {");
					iiiout.println("entity.set" + relName
							+ "(getIdsParser().parse(index++, result));");
					iiiout.println("if (entity.get"
							+ relName
							+ "() != null) { entity.get"
							+ relName
							+ "().setType("
							+ javaNames.fqn(javaNames.nameInterface(model
									.get(rel.getTo())), context)
							+ ".class.getName()); }");
					iiout.println("} else {");
					iiiout.println("index++;");
					iiout.println("}");
				}

				iout.println("}");
				iout.println("public void setRest(" + entityInterface
						+ " entity, int index, "
						+ PreparedStatement.class.getName()
						+ " statement) throws " + Exception.class.getName()
						+ " {");
				for (IField field : entity.items()) {
					String name = baseNames.name(field);
					iiout.println("_isSet.set(" + Boolean.class.getName()
							+ ".valueOf(entity.isSet" + name
							+ "()), index++, statement);");
					iiout.println("" + fieldName(field, baseNames)
							+ ".set(entity.get" + name
							+ "(), index++, statement);");
				}
				for (INormalizedManyToOneRelationship rel : entity
						.relationships().items()) {
					String name = baseNames.nameOfDirect(rel, model);
					iiout.println("_isSet.set(" + Boolean.class.getName()
							+ ".valueOf(entity.isSet" + name
							+ "()), index++, statement);");
					iiout.println("getIdsParser().set(entity.get" + name
							+ "(), index++, statement);");
				}

				iout.println("}");
				out.println("}");
				iiiout.flush();
				iiout.flush();
				iout.flush();

			}
		});
	}

	public void parsers(final INormalizedModel model,
			final JDBCCodeGeneratorContext context) throws Exception {
		final JavaNames javaNames = context.getJavaNames();
		final JDBCNames names = context.getJDBCNames();
		final String clazz = names.parserContext(model);
		final String pkg = names.packageForJDBC(context);
		FileUtils.java(context, pkg, clazz, new AbstractPrintable() {
			@Override
			protected void printWithException(PrintWriter out) throws Exception {
				PrintWriter iout = IndentedWriter.get(out);
				PrintWriter iiout = IndentedWriter.get(iout);
				out.println("package " + pkg + ";");
				out.println();
				out.println("public class " + clazz + " extends "
						+ DefaultJDBCEntityParserContext.class.getName() + " {");

				// Setup parsers in constructor
				iout.println("public " + clazz + " ("
						+ IJDBCIdentifierParser.class.getName()
						+ " idsParser, " + IJDBCStatusParser.class.getName()
						+ " statusParser, "
						+ IJDBCRuntimeTypesContext.class.getName()
						+ " types) throws " + Exception.class.getName() + " {");
				for (INormalizedEntity entity : model.items()) {
					iiout.print("put(");
					iiout.print(javaNames.fqn(javaNames.nameInterface(entity),
							context));
					iiout.print(".class, new ");
					iiout.print(names.fqnJDBCParsers(names.parserJDBC(entity),
							context));
					iiout.println("(idsParser, statusParser, types));");
				}
				iout.println("}");
				out.println("}");
				iiout.flush();
				iout.flush();
			}
		});
	}

	public void context(final INormalizedModel model,
			final JDBCCodeGeneratorContext context) throws Exception {
		final JavaNames javaNames = context.getJavaNames();
		final JDBCNames names = context.getJDBCNames();
		final String clazz = names.abstractJDBCContext(model);
		final String pkg = names.packageForJDBC(context);
		FileUtils.java(context, pkg, clazz, new AbstractPrintable() {
			@Override
			protected void printWithException(PrintWriter out) throws Exception {
				out.println("package " + pkg + ";");
				out.println();
				String finderClass = javaNames.fqnBase(javaNames.finder(model),
						context);
				out.println("public abstract class " + clazz + " extends "
						+ AbstractJDBCSRMContext.class.getName() + "<"
						+ finderClass + "> implements "
						+ javaNames.fqnBase(javaNames.context(model), context)
						+ " {");
				PrintWriter iout = IndentedWriter.get(out);
				PrintWriter iiout = IndentedWriter.get(iout);
				PrintWriter iiiout = IndentedWriter.get(iiout);
				PrintWriter iiiiout = IndentedWriter.get(iiiout);

				iout.println("protected " + IModelUtils.class.getName()
						+ " createUtils() throws " + Exception.class.getName()
						+ " {");
				iiout.println("return new "
						+ javaNames.fqnBase(javaNames.modelUtils(model),
								context) + "();");
				iout.println("}");
				out.println();

				iout.println("protected "
						+ IJDBCEntityParserContext.class.getName()
						+ " createParsers() throws Exception {");
				iiout.println("return new "
						+ names.fqnJDBC(names.parserContext(model), context)
						+ "(getIdentifierParser(), getStatusParser(), getTypes());");
				iout.println("}");
				out.println();

				iout.println("protected " + finderClass
						+ " createFinder() throws Exception {");
				iiout.println("return new "
						+ names.fqnJDBC(names.finderJDBC(model), context)
						+ "() {");

				iiiout.println(delegated(Connection.class, "Connection", clazz));
				iiiout.println(delegated(IJDBCIdentifierParser.class,
						"IdentifierParser", clazz));
				iiiout.println(delegated(IJDBCStatusParser.class,
						"StatusParser", clazz));
				iiiout.println(delegated(IJDBCRuntimeTypesContext.class,
						"Types", clazz));
				iiiout.println(delegated(IJDBCEntityParserContext.class,
						"Parsers", clazz));
				iiout.println("};");
				iout.println("}");
				out.println("}");

				iiiiout.flush();
				iiiout.flush();
				iiout.flush();
				iout.flush();
			}
		});
	}

	private String delegated(Class<?> clazz, String attribute,
			String parentClass) {
		return "protected " + clazz.getName() + " get" + attribute
				+ "() throws " + Exception.class.getName() + " { return "
				+ parentClass + ".this.get" + attribute + "(); }";
	}

}
