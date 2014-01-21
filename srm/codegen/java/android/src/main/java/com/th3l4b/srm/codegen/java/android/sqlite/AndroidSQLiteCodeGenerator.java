package com.th3l4b.srm.codegen.java.android.sqlite;

import java.io.PrintWriter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import com.th3l4b.common.text.AbstractPrintable;
import com.th3l4b.common.text.IndentedWriter;
import com.th3l4b.common.text.TextUtils;
import com.th3l4b.srm.base.IField;
import com.th3l4b.srm.base.normalized.INormalizedEntity;
import com.th3l4b.srm.base.normalized.INormalizedManyToOneRelationship;
import com.th3l4b.srm.base.normalized.INormalizedModel;
import com.th3l4b.srm.codegen.base.FileUtils;
import com.th3l4b.srm.codegen.database.SQLCodeGenerator;
import com.th3l4b.srm.codegen.database.SQLCodeGeneratorContext;
import com.th3l4b.srm.codegen.database.SQLNames;
import com.th3l4b.srm.codegen.java.androidruntime.sqlite.AbstractAndroidSQLiteEntityParser;
import com.th3l4b.srm.codegen.java.androidruntime.sqlite.AbstractAndroidSQLiteFinder;
import com.th3l4b.srm.codegen.java.androidruntime.sqlite.AbstractAndroidSQLiteSRMContext;
import com.th3l4b.srm.codegen.java.androidruntime.sqlite.DefaultAndroidSQLiteEntityParserContext;
import com.th3l4b.srm.codegen.java.androidruntime.sqlite.IAndroidSQLiteEntityParserContext;
import com.th3l4b.srm.codegen.java.androidruntime.sqlite.IAndroidSQLiteIdentifierParser;
import com.th3l4b.srm.codegen.java.androidruntime.sqlite.IAndroidSQLiteRuntimeType;
import com.th3l4b.srm.codegen.java.androidruntime.sqlite.IAndroidSQLiteRuntimeTypesContext;
import com.th3l4b.srm.codegen.java.androidruntime.sqlite.IAndroidSQLiteStatusParser;
import com.th3l4b.srm.codegen.java.basic.JavaNames;
import com.th3l4b.srm.codegen.java.basicruntime.inmemory.Pair;
import com.th3l4b.srm.database.BasicSetDatabaseTypesContext;
import com.th3l4b.srm.database.IDatabaseType;
import com.th3l4b.srm.runtime.IIdentifier;
import com.th3l4b.srm.runtime.IModelUtils;
import com.th3l4b.types.base.ITypesConstants;

public class AndroidSQLiteCodeGenerator {
	public void helper(final INormalizedModel model,
			final AndroidSQLiteCodeGeneratorContext context) throws Exception {
		final AndroidSQLiteNames names = context.getSQLiteNames();
		final String pkg = names.packageForSQLite(context);
		final String clazz = names.helper(model, context);
		FileUtils.java(context, pkg, clazz, new AbstractPrintable() {
			@Override
			protected void printWithException(PrintWriter out) throws Exception {
				PrintWriter iout = IndentedWriter.get(out);
				PrintWriter iiout = IndentedWriter.get(iout);
				out.println("package " + pkg + ";");
				out.println();
				out.println("public class " + clazz + " extends "
						+ SQLiteOpenHelper.class.getName() + " {");
				out.println();
				iout.println("public " + clazz + "(" + Context.class.getName()
						+ " context, String name, "
						+ SQLiteDatabase.class.getName() + "."
						+ CursorFactory.class.getSimpleName()
						+ " factory, int version) {");
				iiout.println("super(context, name, factory, version);");
				iout.println("}");
				out.println();
				iout.println("public " + clazz + "(" + Context.class.getName()
						+ " context, String name, "
						+ SQLiteDatabase.class.getName() + "."
						+ CursorFactory.class.getSimpleName()
						+ " factory, int version, "
						+ DatabaseErrorHandler.class.getName()
						+ " errorHandler) {");
				iiout.println("super(context, name, factory, version, errorHandler);");
				iout.println("}");
				out.println();
				iout.println("public void onCreate("
						+ SQLiteDatabase.class.getName() + " database) {");
				SQLCodeGeneratorContext sqlContext = new SQLCodeGeneratorContext();
				context.copyTo(sqlContext);
				SQLCodeGenerator sqlGenerator = new SQLCodeGenerator();
				IDatabaseType sqlite = BasicSetDatabaseTypesContext.get()
						.get(BasicSetDatabaseTypesContext.Databases.SQLite
								.getName());
				for (INormalizedEntity entity : model.items()) {
					iiout.print("database.execSQL(\"");
					iiout.print(sqlGenerator.createTableSingleLine(entity,
							model, sqlite, sqlContext));
					iiout.println("\");");
				}
				iout.println("}");
				out.println();
				iout.println("public void onUpgrade("
						+ SQLiteDatabase.class.getName()
						+ " database, int oldv, int newv) {");
				iout.println("}");
				out.println("}");

				iiout.flush();
				iout.flush();
			}
		});
	}

	private String fieldName(IField f, AndroidSQLiteNames names)
			throws Exception {
		return "_field_" + names.name(f);
	}

	public void entityParser(final INormalizedEntity entity,
			final INormalizedModel model,
			final AndroidSQLiteCodeGeneratorContext context) throws Exception {
		final AndroidSQLiteNames names = context.getSQLiteNames();
		final JavaNames javaNames = context.getJavaNames();
		final SQLNames sqlNames = context.getSQLNames();
		final String clazz = names.parserAndroidSQLite(entity);
		final String pkg = names.packageForSQLiteParsers(context);
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
						+ AbstractAndroidSQLiteEntityParser.class.getName()
						+ "<" + entityInterface + "> {");
				out.println();
				for (IField field : entity.items()) {
					String name = fieldName(field, names);
					String clazz = context.getTypes().get(field.getType())
							.getProperties()
							.get(ITypesConstants.PROPERTY_JAVA_CLASS);
					iout.println("private "
							+ IAndroidSQLiteRuntimeType.class.getName() + "<"
							+ clazz + "> " + name + ";");
				}
				out.println();
				iout.println("public " + clazz + "("
						+ IAndroidSQLiteIdentifierParser.class.getName()
						+ " ids, " + IAndroidSQLiteStatusParser.class.getName()
						+ " status, "
						+ IAndroidSQLiteRuntimeTypesContext.class.getName()
						+ " types) {");
				iiout.println("super(ids, status);");
				for (IField field : entity.items()) {
					String name = fieldName(field, names);
					String clazz = context.getTypes().get(field.getType())
							.getProperties()
							.get(ITypesConstants.PROPERTY_JAVA_CLASS);
					iiout.println(name + " = types.get(\""
							+ TextUtils.escapeJavaString(field.getType())
							+ "\", " + clazz + ".class);");
				}
				iout.println("}");
				iout.println();

				iout.println("public " + String.class.getName()
						+ " table() throws " + Exception.class.getName()
						+ " { return \""
						+ TextUtils.escapeJavaString(sqlNames.table(entity))
						+ "\"; }");
				iout.println("public " + String.class.getName()
						+ " idColumn() throws " + Exception.class.getName()
						+ " { return \""
						+ TextUtils.escapeJavaString(SQLNames.ID) + "\"; }");
				iout.println("public " + String.class.getName()
						+ " statusColumn() throws " + Exception.class.getName()
						+ " { return \""
						+ TextUtils.escapeJavaString(SQLNames.STATUS) + "\"; }");
				iout.println("public " + entityInterface
						+ " create() { return new "
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
							+ TextUtils.escapeJavaString(sqlNames.column(field))
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
							+ TextUtils.escapeJavaString(sqlNames.column(rel,
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
						+ " entity, int index, " + Cursor.class.getName()
						+ " result) throws " + Exception.class.getName() + " {");
				for (IField field : entity.items()) {
					iiout.println("entity.set" + javaNames.name(field) + "("
							+ fieldName(field, names)
							+ ".parse(index++, result));");
				}
				for (INormalizedManyToOneRelationship rel : entity
						.relationships().items()) {
					iiout.println("entity.set"
							+ javaNames.nameOfDirect(rel, model)
							+ "(getIdsParser().parse(index++, result));");
				}

				iout.println("}");
				iout.println("public void setRest(" + entityInterface
						+ " entity, Void arg, " + ContentValues.class.getName()
						+ " values) throws " + Exception.class.getName() + " {");
				for (IField field : entity.items()) {
					String name = javaNames.name(field);
					iiout.println("if (entity.isSet"
							+ name
							+ "()) { "
							+ fieldName(field, names)
							+ ".set(entity.get"
							+ name
							+ "(), \""
							+ TextUtils.escapeJavaString(sqlNames.column(field))
							+ "\", values); }");
				}
				for (INormalizedManyToOneRelationship rel : entity
						.relationships().items()) {
					String name = javaNames.nameOfDirect(rel, model);
					iiout.println("if (entity.isSet"
							+ name
							+ "()) { getIdsParser().set(entity.get"
							+ name
							+ "(), \""
							+ TextUtils.escapeJavaString(sqlNames.column(rel,
									model)) + "\", values); }");
				}

				iout.println("}");
				out.println("}");
				iiout.flush();
				iout.flush();

			}
		});
	}

	public void finder(final INormalizedModel model,
			final AndroidSQLiteCodeGeneratorContext context) throws Exception {

		final AndroidSQLiteNames names = context.getSQLiteNames();
		final SQLNames sqlNames = context.getSQLNames();
		final String clazz = names.finder(model, context);
		final String pkg = names.packageForSQLite(context);

		FileUtils.java(context, pkg, clazz, new AbstractPrintable() {
			@Override
			protected void printWithException(PrintWriter out) throws Exception {
				PrintWriter iout = IndentedWriter.get(out);
				PrintWriter iiout = IndentedWriter.get(iout);
				PrintWriter iiiout = IndentedWriter.get(iiout);
				out.println("package " + pkg + ";");
				out.println();
				out.println("public abstract class " + clazz + " extends "
						+ AbstractAndroidSQLiteFinder.class.getName()
						+ " implements "
						+ names.fqnBase(names.finder(model), context) + " {");

				// Setup finders in constructor
				iout.println("public " + clazz + " () {");
				// And the relationships
				for (INormalizedEntity ne : model.items()) {
					for (INormalizedManyToOneRelationship rel : ne
							.relationships().items()) {
						String clazzOne = names.fqn(
								names.nameInterface(model.get(rel.getTo())),
								context);
						String name = TextUtils.escapeJavaString(rel
								.getReverse().getName());
						iiout.println("_map.put(new "
								+ Pair.class.getName()
								+ "("
								+ clazzOne
								+ ".class.getName(), \""
								+ name
								+ "\"), \""
								+ TextUtils.escapeJavaString(sqlNames.column(
										rel, model)) + "\");");
					}
				}
				iout.println("}");
				iout.println();

				// Get the entities (individually or all)
				for (INormalizedEntity ne : model.items()) {
					String fqn = names.fqn(names.nameInterface(ne), context);
					iout.println("public " + fqn + " get" + names.name(ne)
							+ "(" + IIdentifier.class.getName()
							+ " id) throws Exception {");
					iiout.println("return find(" + fqn + ".class, id);");
					iout.println("}");
				}

				for (INormalizedEntity ne : model.items()) {
					String fqn = names.fqn(names.nameInterface(ne), context);
					iout.println("public " + Iterable.class.getName() + "<"
							+ fqn + "> all" + names.name(ne)
							+ "() throws Exception {");

					iiout.println("return all(" + fqn + ".class);");
					iout.println("}");
				}

				// And the relationships
				for (INormalizedEntity ne : model.items()) {
					for (INormalizedManyToOneRelationship rel : ne
							.relationships().items()) {
						String clazzMany = names.nameInterface(ne);
						String clazzOne = names.nameInterface(model.get(rel
								.getTo()));
						String methodName = "findAll"
								+ names.nameOfReverse(rel, model) + "From"
								+ names.name(model.get(rel.getTo()));
						String leading = "public " + Iterable.class.getName()
								+ "<" + names.fqn(clazzMany, context) + "> "
								+ methodName + "(";
						iout.println(leading + names.fqn(clazzOne, context)
								+ " from) throws Exception {");
						iiout.println(" return "
								+ methodName
								+ "(from == null ? null : from.coordinates().getIdentifier());");
						iout.println("}");
						iout.println(leading + IIdentifier.class.getName()
								+ " from) throws Exception {");
						iiout.println("return find("
								+ names.fqn(clazzMany, context)
								+ ".class, "
								+ names.fqn(clazzOne, context)
								+ ".class, from, \""
								+ TextUtils.escapeJavaString(rel.getReverse()
										.getName()) + "\");");
						iout.println("}");
					}
				}

				out.println("}");
				iiiout.flush();
				iiout.flush();
				iout.flush();
			}
		});
	}

	public void context(final INormalizedModel model,
			final AndroidSQLiteCodeGeneratorContext context) throws Exception {

		final AndroidSQLiteNames names = context.getSQLiteNames();
		final String clazz = names.context(model, context);
		final String pkg = names.packageForSQLite(context);

		FileUtils.java(context, pkg, clazz, new AbstractPrintable() {
			@Override
			protected void printWithException(PrintWriter out) throws Exception {
				PrintWriter iout = IndentedWriter.get(out);
				PrintWriter iiout = IndentedWriter.get(iout);
				PrintWriter iiiout = IndentedWriter.get(iiout);
				PrintWriter iiiiout = IndentedWriter.get(iiiout);

				out.println("package " + pkg + ";");
				out.println();
				String fqnFinder = names.fqnBase(names.finder(model), context);
				out.println("public abstract class " + clazz + " extends "
						+ AbstractAndroidSQLiteSRMContext.class.getName() + "<"
						+ fqnFinder + "> {");
				iout.println("protected "
						+ IAndroidSQLiteEntityParserContext.class.getName()
						+ " createParsers() throws "
						+ Exception.class.getName() + " {");
				iiout.println("return new "
						+ names.fqnSQLite(names.parserContext(model, context),
								context)
						+ "(getIdentifierParser(), getStatusParser(), getTypes());");
				iout.println("}");
				out.println();
				iout.println("protected " + IModelUtils.class.getName()
						+ " createUtils() throws " + Exception.class.getName()
						+ " {");
				iiout.println("return new "
						+ names.fqnBase(names.modelUtils(model), context)
						+ "();");
				iout.println("}");
				out.println();
				iout.println("protected " + fqnFinder
						+ " createFinder() throws " + Exception.class.getName()
						+ " {");
				iiout.println("return new "
						+ names.fqnSQLite(names.finder(model, context), context)
						+ "() {");
				iiiout.println("protected " + SQLiteDatabase.class.getName()
						+ " getDatabase() throws " + Exception.class.getName()
						+ " {");
				iiiiout.println("return " + clazz + ".this.getDatabase();");
				iiiout.println("}");
				iiiout.println("protected "
						+ IAndroidSQLiteIdentifierParser.class.getName()
						+ " getIdentifierParser() throws "
						+ Exception.class.getName() + " {");
				iiiiout.println("return " + clazz
						+ ".this.getIdentifierParser();");
				iiiout.println("}");
				iiiout.println("protected "
						+ IAndroidSQLiteStatusParser.class.getName()
						+ " getStatusParser() throws "
						+ Exception.class.getName() + " {");
				iiiiout.println("return " + clazz + ".this.getStatusParser();");
				iiiout.println("}");
				iiiout.println("protected "
						+ IAndroidSQLiteRuntimeTypesContext.class.getName()
						+ " getTypes() throws " + Exception.class.getName()
						+ " {");
				iiiiout.println("return " + clazz + ".this.getTypes();");
				iiiout.println("}");
				iiiout.println("protected "
						+ IAndroidSQLiteEntityParserContext.class.getName()
						+ " getParsers() throws " + Exception.class.getName()
						+ " {");
				iiiiout.println("return " + clazz + ".this.getParsers();");
				iiiout.println("}");
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

	public void parserContext(final INormalizedModel model,
			final AndroidSQLiteCodeGeneratorContext context) throws Exception {
		final AndroidSQLiteNames names = context.getSQLiteNames();
		final String pkg = names.packageForSQLite(context);
		final String clazz = names.parserContext(model, context);
		FileUtils.java(context, pkg, clazz, new AbstractPrintable() {
			@Override
			protected void printWithException(PrintWriter out) throws Exception {
				PrintWriter iout = IndentedWriter.get(out);
				PrintWriter iiout = IndentedWriter.get(iout);
				out.println("package " + pkg + ";");
				out.println();
				out.println("public class "
						+ clazz
						+ " extends "
						+ DefaultAndroidSQLiteEntityParserContext.class
								.getName() + " {");
				out.println();
				iout.println("public " + clazz + "("
						+ IAndroidSQLiteIdentifierParser.class.getName()
						+ " idsParser, "
						+ IAndroidSQLiteStatusParser.class.getName()
						+ " statusParser, "
						+ IAndroidSQLiteRuntimeTypesContext.class.getName()
						+ " types) throws " + Exception.class.getName() + " {");
				for (INormalizedEntity entity : model.items()) {
					iiout.print("put(");
					iiout.print(names.fqn(names.nameInterface(entity), context));
					iiout.print(".class, new ");
					iiout.print(names.fqnSQLiteParsers(
							names.parserAndroidSQLite(entity), context));
					iiout.println("(idsParser, statusParser, types));");
				}
				iout.println("}");
				out.println("}");
				iiout.flush();
				iout.flush();
			}
		});
	}

}
