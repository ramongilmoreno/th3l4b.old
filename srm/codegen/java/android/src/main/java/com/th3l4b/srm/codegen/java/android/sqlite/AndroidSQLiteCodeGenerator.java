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
import com.th3l4b.srm.codegen.java.androidruntime.sqlite.IAndroidSQLiteIdentifierParser;
import com.th3l4b.srm.codegen.java.androidruntime.sqlite.IAndroidSQLiteRuntimeType;
import com.th3l4b.srm.codegen.java.androidruntime.sqlite.IAndroidSQLiteRuntimeTypesContext;
import com.th3l4b.srm.codegen.java.androidruntime.sqlite.IAndroidSQLiteStatusParser;
import com.th3l4b.srm.codegen.java.basic.JavaNames;
import com.th3l4b.srm.database.BasicSetDatabaseTypesContext;
import com.th3l4b.srm.database.IDatabaseType;
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
						+ AbstractAndroidSQLiteEntityParser.class.getName() + "<"
						+ entityInterface + "> {");
				out.println();
				for (IField field : entity.items()) {
					String name = fieldName(field, names);
					String clazz = context.getTypes().get(field.getType())
							.getProperties()
							.get(ITypesConstants.PROPERTY_JAVA_CLASS);
					iout.println("private " + IAndroidSQLiteRuntimeType.class.getName()
							+ "<" + clazz + "> " + name + ";");
				}
				out.println();
				iout.println("public " + clazz + "("
						+ IAndroidSQLiteIdentifierParser.class.getName() + " ids, "
						+ IAndroidSQLiteStatusParser.class.getName() + " status, "
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
						+ TextUtils.escapeJavaString(sqlNames.id(entity))
						+ "\"; }");
				iout.println("public " + String.class.getName()
						+ " statusColumn() throws " + Exception.class.getName()
						+ " { return \""
						+ TextUtils.escapeJavaString(sqlNames.status(entity))
						+ "\"; }");
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
						+ " entity, Void arg, "
						+ ContentValues.class.getName()
						+ " values) throws " + Exception.class.getName()
						+ " {");
				for (IField field : entity.items()) {
					iiout.println(fieldName(field, names)
							+ ".set(entity.get"
							+ javaNames.name(field)
							+ "(), arg, values, "
							+ context.getTypes().get(field.getType())
									.getProperties()
									.get(ITypesConstants.PROPERTY_JAVA_CLASS)
							+ ".class);");
				}
				for (INormalizedManyToOneRelationship rel : entity
						.relationships().items()) {
					iiout.println("getIdsParser().set(entity.get"
							+ javaNames.nameOfDirect(rel, model)
							+ "(), arg, values);");
				}

				iout.println("}");
				out.println("}");
				iiout.flush();
				iout.flush();

			}
		});
	}
}
