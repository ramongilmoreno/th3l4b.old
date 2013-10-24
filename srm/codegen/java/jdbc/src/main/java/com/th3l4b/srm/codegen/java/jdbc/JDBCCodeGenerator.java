package com.th3l4b.srm.codegen.java.jdbc;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.th3l4b.common.text.AbstractPrintable;
import com.th3l4b.common.text.IndentedWriter;
import com.th3l4b.common.text.TextUtils;
import com.th3l4b.srm.base.IField;
import com.th3l4b.srm.base.normalized.INormalizedEntity;
import com.th3l4b.srm.base.normalized.INormalizedManyToOneRelationship;
import com.th3l4b.srm.base.normalized.INormalizedModel;
import com.th3l4b.srm.codegen.base.FileUtils;
import com.th3l4b.srm.codegen.java.basic.JavaNames;
import com.th3l4b.srm.codegen.java.jdbcruntime.AbstractJDBCEntityParser;
import com.th3l4b.srm.codegen.java.jdbcruntime.AbstractJDBCFinder;
import com.th3l4b.srm.codegen.java.jdbcruntime.AbstractJDBCSRMContext;
import com.th3l4b.srm.codegen.java.jdbcruntime.DefaultJDBCEntityParserContext;
import com.th3l4b.srm.codegen.java.jdbcruntime.IJDBCEntityParserContext;
import com.th3l4b.srm.codegen.java.jdbcruntime.IJDBCIdentifierParser;
import com.th3l4b.srm.codegen.java.jdbcruntime.IJDBCStatusParser;
import com.th3l4b.srm.runtime.IIdentifier;
import com.th3l4b.types.base.ITypesConstants;
import com.th3l4b.types.runtime.IJDBCRuntimeType;
import com.th3l4b.types.runtime.IJDBCRuntimeTypesContext;

public class JDBCCodeGenerator {

	public void finder(final INormalizedModel model,
			final JDBCCodeGeneratorContext context) throws Exception {
		final JDBCNames names = context.getJDBCNames();
		final JavaNames javaNames = context.getJavaNames();
		final String clazz = names.finderJDBC(model);
		final String pkg = names.packageForJDBC(context);
		FileUtils.java(context, pkg, clazz, new AbstractPrintable() {
			@Override
			protected void printWithException(PrintWriter out) throws Exception {
				PrintWriter iout = IndentedWriter.get(out);
				PrintWriter iiout = IndentedWriter.get(iout);
				out.println("package " + pkg + ";");
				out.println();
				out.println("public abstract class " + clazz + " extends "
						+ AbstractJDBCFinder.class.getName() + " implements "
						+ names.fqn(names.finder(model), context) + " {");

				// Get the entities (individually or all)
				for (INormalizedEntity ne : model.items()) {
					String neClazz = javaNames.fqn(javaNames.nameInterface(ne),
							context);
					iout.println("public " + neClazz + " get"
							+ javaNames.name(ne) + "("
							+ IIdentifier.class.getName()
							+ " id) throws Exception {");
					iiout.println("return find(" + neClazz + ".class, id);");
					iout.println("}");
				}

				for (INormalizedEntity ne : model.items()) {
					String neClazz = javaNames.fqn(javaNames.nameInterface(ne),
							context);
					iout.println("public " + Iterable.class.getName() + "<"
							+ neClazz + "> all" + javaNames.name(ne)
							+ "() throws Exception {");
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
								+ javaNames.nameOfReverse(rel, model) + "From"
								+ javaNames.name(model.get(rel.getTo())) + "(";
						iout.println(leading + clazzOne
								+ " from) throws Exception {");
						iiout.println("return from != null ? findAll"
								+ javaNames.nameOfReverse(rel, model)
								+ "From"
								+ javaNames.name(model.get(rel.getTo()))
								+ "(from.coordinates().getIdentifier()) : null;");
						iout.println("}");
						iout.println(leading + IIdentifier.class.getName()
								+ " from) throws Exception {");
						iiout.println("return find(" + clazzMany
								+ ".class, from, \"" + names.column(rel, model)
								+ "\");");
						iout.println("}");
					}
				}
				out.println("}");
				iiout.flush();
				iout.flush();
			}
		});
	}

	private String fieldName(IField f, JDBCNames names) throws Exception {
		return "_field_" + names.name(f);
	}

	public void entityParser(final INormalizedEntity entity,
			final INormalizedModel model, final JDBCCodeGeneratorContext context)
			throws Exception {
		final JDBCNames names = context.getJDBCNames();
		final String clazz = names.parserJDBC(entity);
		final String pkg = names.packageForJDBC(context);
		FileUtils.java(context, pkg, clazz, new AbstractPrintable() {
			@Override
			protected void printWithException(PrintWriter out) throws Exception {
				PrintWriter iout = IndentedWriter.get(out);
				PrintWriter iiout = IndentedWriter.get(iout);
				out.println("package " + pkg + ";");
				out.println();
				String entityInterface = names.fqn(names.nameInterface(entity),
						context);
				out.println("public class " + clazz + " extends "
						+ AbstractJDBCEntityParser.class.getName() + "<"
						+ entityInterface + "> {");
				out.println();
				for (IField field : entity.items()) {
					String name = fieldName(field, names);
					String clazz = context.getTypes().get(field.getType())
							.getProperties()
							.get(ITypesConstants.PROPERTY_JAVA_CLASS);
					iout.println("private " + IJDBCRuntimeType.class.getName()
							+ "<" + clazz + "> " + name + ";");
				}
				out.println();
				iout.println("public " + clazz + "("
						+ IJDBCIdentifierParser.class.getName() + " ids, "
						+ IJDBCStatusParser.class.getName() + " status, "
						+ IJDBCRuntimeTypesContext.class.getName()
						+ " types) {");
				iiout.println("super(ids, status);");
				for (IField field : entity.items()) {
					String name = fieldName(field, names);
					String clazz = context.getTypes().get(field.getType())
							.getProperties()
							.get(ITypesConstants.PROPERTY_JAVA_CLASS);
					iiout.println(name + " = types.get(\""
							+ TextUtils.escapeJavaString(field.getName())
							+ "\", " + clazz + ".class);");
				}
				iout.println("}");
				iout.println();

				iout.println("public " + String.class.getName()
						+ " table() throws " + Exception.class.getName()
						+ " { return \""
						+ TextUtils.escapeJavaString(names.table(entity))
						+ "\"; }");
				iout.println("public " + String.class.getName()
						+ " idColumn() throws " + Exception.class.getName()
						+ " { return \""
						+ TextUtils.escapeJavaString(names.id(entity))
						+ "\"; }");
				iout.println("public " + String.class.getName()
						+ " statusColumn() throws " + Exception.class.getName()
						+ " { return \""
						+ TextUtils.escapeJavaString(names.status(entity))
						+ "\"; }");
				iout.println("public " + entityInterface + " create(Class<"
						+ entityInterface + "> clazz) { return new "
						+ names.fqnImpl(names.nameImpl(entity), context)
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
					iiout.print("\""
							+ TextUtils.escapeJavaString(names.column(field))
							+ "\"");
				}

				// Fill relationships
				for (INormalizedManyToOneRelationship rel : entity
						.relationships().items()) {
					if (first) {
						first = false;
					} else {
						iiout.println(",");
					}
					iiout.print("\""
							+ TextUtils.escapeJavaString(names.column(rel,
									model)) + "\"");
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
				iiout.println("throw new "
						+ UnsupportedOperationException.class.getName() + "();");
				iout.println("}");
				iout.println("public void setRest(" + entityInterface
						+ " entity, int index, "
						+ PreparedStatement.class.getName()
						+ " statement) throws " + Exception.class.getName()
						+ " {");
				iiout.println("throw new "
						+ UnsupportedOperationException.class.getName() + "();");
				iout.println("}");
				out.println("}");
				iiout.flush();
				iout.flush();

			}
		});
	}

	public void parsers(final INormalizedModel model,
			final JDBCCodeGeneratorContext context) throws Exception {
		final JDBCNames names = context.getJDBCNames();
		final String clazz = names.parsersJDBC(model);
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
						+ IJDBCIdentifierParser.class.getName() + " ids, "
						+ IJDBCStatusParser.class.getName() + " status, "
						+ IJDBCRuntimeTypesContext.class.getName()
						+ " types) throws " + Exception.class.getName() + " {");
				for (INormalizedEntity entity : model.items()) {
					iiout.println("putEntityParser(new "
							+ names.fqnJDBC(names.parserJDBC(entity), context)
							+ " (ids, status, types), "
							+ names.fqn(names.nameInterface(entity), context)
							+ ".class);");
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
		final JDBCNames names = context.getJDBCNames();
		final String clazz = names.abstractJDBCContext(model);
		final String pkg = names.packageForJDBC(context);
		FileUtils.java(context, pkg, clazz, new AbstractPrintable() {
			@Override
			protected void printWithException(PrintWriter out) throws Exception {
				out.println("package " + pkg + ";");
				out.println();
				String finderClass = names.fqn(names.finder(model), context);
				out.println("public abstract class " + clazz + " extends "
						+ AbstractJDBCSRMContext.class.getName() + "<"
						+ finderClass + "> implements "
						+ names.fqn(names.context(model), context) + " {");
				PrintWriter iout = IndentedWriter.get(out);
				PrintWriter iiout = IndentedWriter.get(iout);
				PrintWriter iiiout = IndentedWriter.get(iiout);
				PrintWriter iiiiout = IndentedWriter.get(iiiout);

				iout.println("protected "
						+ IJDBCEntityParserContext.class.getName()
						+ " createParsers() throws Exception {");
				iiout.println("return new "
						+ names.fqnImpl(names.parsersJDBC(model), context)
						+ "(getIdentifierParser(), getStatusParser(), getTypes());");
				iout.println("}");
				out.println();

				iout.println("protected " + finderClass
						+ " createFinder() throws Exception {");
				iiout.println("return new "
						+ names.fqnImpl(names.finderJDBC(model), context)
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
				+ clazz + ".this.get" + attribute + "(); }";
	}

}
