package com.th3l4b.srm.codegen.java.jdbc;

import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;

import com.th3l4b.common.text.AbstractPrintable;
import com.th3l4b.common.text.IndentedWriter;
import com.th3l4b.common.text.TextUtils;
import com.th3l4b.srm.base.IField;
import com.th3l4b.srm.base.normalized.INormalizedEntity;
import com.th3l4b.srm.base.normalized.INormalizedManyToOneRelationship;
import com.th3l4b.srm.base.normalized.INormalizedModel;
import com.th3l4b.srm.codegen.base.FileUtils;
import com.th3l4b.srm.codegen.java.jdbcruntime.AbstractJDBCFinder;
import com.th3l4b.srm.codegen.java.jdbcruntime.AbstractJDBCSRMContext;
import com.th3l4b.srm.codegen.java.jdbcruntime.IJDBCEntityParser;
import com.th3l4b.srm.codegen.java.jdbcruntime.IJDBCIdentifierParser;
import com.th3l4b.srm.runtime.IIdentifier;
import com.th3l4b.srm.runtime.IModelUtils;
import com.th3l4b.srm.runtime.IRuntimeEntity;
import com.th3l4b.types.runtime.IJavaRuntimeTypesContext;

public class JDBCCodeGenerator {

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
				out.println("public class " + clazz + " implements "
						+ IJDBCEntityParser.class.getName() + "<"
						+ entityInterface + "> {");
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
				iout.println("public void parse(" + entityInterface
						+ " entity, int index, " + ResultSet.class.getName()
						+ " result, " + IJDBCIdentifierParser.class.getName()
						+ " ids, " + IJavaRuntimeTypesContext.class.getName()
						+ " types) throws " + Exception.class.getName() + " {");
				iiout.println("throw new "
						+ UnsupportedOperationException.class.getName() + "();");
				iout.println("}");
				iout.println("public void set(" + entityInterface
						+ " entity, int index, "
						+ PreparedStatement.class.getName() + " statement, "
						+ IJDBCIdentifierParser.class.getName() + " ids, "
						+ IJavaRuntimeTypesContext.class.getName()
						+ " types) throws " + Exception.class.getName() + " {");
				iiout.println("throw new "
						+ UnsupportedOperationException.class.getName() + "();");
				iout.println("}");
				out.println("}");
				iiout.flush();
				iout.flush();

			}
		});
	}

	public void finderJDBC(final INormalizedModel model,
			final JDBCCodeGeneratorContext context) throws Exception {
		final JDBCNames names = context.getJDBCNames();
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

				// Setup parsers in constructor
				iout.println("public " + clazz + " () throws "
						+ Exception.class.getName() + " {");
				for (INormalizedEntity entity : model.items()) {
					iiout.println("putEntityParser(new "
							+ names.fqnJDBC(names.parserJDBC(entity), context)
							+ " (), "
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

	public void abstractJDBCContext(final INormalizedModel model,
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
				iout.println("protected " + IModelUtils.class.getName()
						+ " createUtils() throws Exception {");
				iiout.println("return new "
						+ names.fqnImpl(names.modelUtils(model), context)
						+ "();");
				iout.println("}");
				out.println();

				iout.println("protected " + finderClass
						+ " createFinder() throws Exception {");
				iiout.println("return new "
						+ names.fqnImpl(context.getJDBCNames()
								.finderJDBC(model), context) + "() {");
				iiiout.println("protected " + Map.class.getName() + "<"
						+ IIdentifier.class.getName() + ", "
						+ IRuntimeEntity.class.getName()
						+ "<?>> getEntities() throws Exception {");
				iiiiout.println("return " + clazz + ".this.getEntities();");
				iiiout.println("}");
				iiout.println("};");
				iout.println("}");
				out.println("}");

				iiout.flush();
				iout.flush();
			}
		});
	}

}
