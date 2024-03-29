package com.th3l4b.android.srm.codegen.sqlite;

import java.io.PrintWriter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import com.th3l4b.android.srm.runtime.sqlite.AbstractAndroidSQLiteEntityParser;
import com.th3l4b.android.srm.runtime.sqlite.AbstractAndroidSQLiteFinder;
import com.th3l4b.android.srm.runtime.sqlite.AbstractAndroidSQLiteSRMContext;
import com.th3l4b.android.srm.runtime.sqlite.DefaultAndroidSQLiteEntityParserContext;
import com.th3l4b.android.srm.runtime.sqlite.IAndroidSQLiteEntityParserContext;
import com.th3l4b.android.srm.runtime.sqlite.IAndroidSQLiteIdentifierParser;
import com.th3l4b.android.srm.runtime.sqlite.IAndroidSQLiteRuntimeType;
import com.th3l4b.android.srm.runtime.sqlite.IAndroidSQLiteRuntimeTypesContext;
import com.th3l4b.android.srm.runtime.sqlite.IAndroidSQLiteStatusParser;
import com.th3l4b.common.text.AbstractPrintable;
import com.th3l4b.common.text.IndentedWriter;
import com.th3l4b.common.text.TextUtils;
import com.th3l4b.srm.base.IField;
import com.th3l4b.srm.base.normalized.INormalizedEntity;
import com.th3l4b.srm.base.normalized.INormalizedManyToOneRelationship;
import com.th3l4b.srm.base.normalized.INormalizedModel;
import com.th3l4b.srm.codegen.base.FileUtils;
import com.th3l4b.srm.codegen.base.names.BaseNames;
import com.th3l4b.srm.codegen.database.SQLCodeGenerator;
import com.th3l4b.srm.codegen.database.SQLCodeGeneratorContext;
import com.th3l4b.srm.codegen.database.SQLNames;
import com.th3l4b.srm.codegen.java.basic.JavaNames;
import com.th3l4b.srm.codegen.java.basic.runtime.DefaultIdentifier;
import com.th3l4b.srm.codegen.java.basic.runtime.inmemory.Pair;
import com.th3l4b.srm.database.BasicSetDatabaseTypesContext;
import com.th3l4b.srm.database.IDatabaseType;
import com.th3l4b.srm.runtime.IDatabaseConstants;
import com.th3l4b.srm.runtime.IIdentifier;
import com.th3l4b.srm.runtime.IModelUtils;
import com.th3l4b.srm.runtime.IRuntimeEntity;
import com.th3l4b.types.base.ITypesConstants;

public class AndroidSQLiteCodeGenerator {
	public void helper(final INormalizedModel model,
			final AndroidSQLiteCodeGeneratorContext context) throws Exception {
		final BaseNames baseNames = context.getBaseNames();
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
				SQLCodeGeneratorContext sqlContext = new SQLCodeGeneratorContext(
						baseNames);
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

	private String fieldName(IField f, BaseNames names) throws Exception {
		return "_field_" + names.name(f);
	}

	public void entityParser(final INormalizedEntity entity,
			final INormalizedModel model,
			final AndroidSQLiteCodeGeneratorContext context) throws Exception {
		final BaseNames baseNames = context.getBaseNames();
		final JavaNames javaNames = context.getJavaNames();
		final AndroidSQLiteNames asqlNames = context.getSQLiteNames();
		final SQLNames sqlNames = context.getSQLNames();
		final String clazz = asqlNames.parserAndroidSQLite(entity);
		final String pkg = asqlNames.packageForSQLiteParsers(context);
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
				out.println("public class "
						+ clazz
						+ " extends "
						+ asqlNames.fqnSQLite(
								asqlNames.abstractParser(model, context),
								context) + "<" + entityInterface + "> {");
				for (IField field : entity.items()) {
					iout.println("private "
							+ IAndroidSQLiteRuntimeType.class.getName()
							+ "<"
							+ context.getTypes().get(field.getType())
									.getProperties()
									.get(ITypesConstants.PROPERTY_JAVA_CLASS)
							+ "> " + fieldName(field, baseNames) + ";");
				}
				out.println();
				iout.println("public " + clazz + "("
						+ IAndroidSQLiteIdentifierParser.class.getName()
						+ " ids, " + IAndroidSQLiteStatusParser.class.getName()
						+ " status, "
						+ IAndroidSQLiteRuntimeTypesContext.class.getName()
						+ " types) throws " + Exception.class.getName() + " {");
				iiout.println("super(ids, status);");
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
					iiout.println("if (!result.isNull(index)) {");
					iiiout.println("entity.set" + baseNames.name(field) + "("
							+ fieldName(field, baseNames)
							+ ".parse(index++, result));");
					iiout.println("} else { index++; }");
				}
				for (INormalizedManyToOneRelationship rel : entity
						.relationships().items()) {
					iiout.println("if (!result.isNull(index)) {");
					String relName = baseNames.nameOfDirect(rel, model);
					iiiout.println("entity.set"
							+ relName
							+ "("
							+ DefaultIdentifier.class.getName()
							+ ".setIdentifierType(getIdsParser().parse(index++, result), "
							+ javaNames.fqn(javaNames.nameInterface(model
									.get(rel.getTo())), context) + ".class));");
					iiout.println("} else { index++; }");
				}

				iout.println("}");
				iout.println("public void setRest(" + entityInterface
						+ " entity, Void arg, " + ContentValues.class.getName()
						+ " values) throws " + Exception.class.getName() + " {");
				for (IField field : entity.items()) {
					String name = baseNames.name(field);
					iiout.println("if (entity.isSet" + name + "()) {");
					iiiout.println(""
							+ fieldName(field, baseNames)
							+ ".set(entity.get"
							+ name
							+ "(), \""
							+ TextUtils.escapeJavaString(sqlNames.column(field))
							+ "\", values);");
					iiout.println("}");
				}
				for (INormalizedManyToOneRelationship rel : entity
						.relationships().items()) {
					String name = baseNames.nameOfDirect(rel, model);
					iiout.println("if (entity.isSet" + name + "()) {");
					iiiout.println("getIdsParser().set(entity.get"
							+ name
							+ "(), \""
							+ TextUtils.escapeJavaString(sqlNames.column(rel,
									model)) + "\", values);");
					iiout.println("}");
				}

				iout.println("}");
				out.println("}");
				iiiout.flush();
				iiout.flush();
				iout.flush();

			}
		});
	}

	public void finder(final INormalizedModel model,
			final AndroidSQLiteCodeGeneratorContext context) throws Exception {
		final BaseNames baseNames = context.getBaseNames();
		final JavaNames javaNames = context.getJavaNames();
		final AndroidSQLiteNames asqlNames = context.getSQLiteNames();
		final SQLNames sqlNames = context.getSQLNames();
		final String clazz = asqlNames.finder(model, context);
		final String pkg = asqlNames.packageForSQLite(context);

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
						+ javaNames.fqnBase(javaNames.finder(model), context)
						+ " {");

				// Setup finders in constructor
				iout.println("public " + clazz + " () {");
				// And the relationships
				for (INormalizedEntity ne : model.items()) {
					for (INormalizedManyToOneRelationship rel : ne
							.relationships().items()) {
						String clazzOne = javaNames.fqn(
								javaNames.nameInterface(model.get(rel.getTo())),
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
					String fqn = javaNames.fqn(javaNames.nameInterface(ne),
							context);
					iout.println("public " + fqn + " get" + baseNames.name(ne)
							+ "(" + IIdentifier.class.getName()
							+ " id) throws Exception {");
					iiout.println("return find(" + fqn + ".class, id);");
					iout.println("}");
				}

				for (INormalizedEntity ne : model.items()) {
					String fqn = javaNames.fqn(javaNames.nameInterface(ne),
							context);
					iout.println("public " + Iterable.class.getName() + "<"
							+ fqn + "> all" + baseNames.name(ne)
							+ "() throws Exception {");

					iiout.println("return all(" + fqn + ".class);");
					iout.println("}");
				}

				// And the relationships
				for (INormalizedEntity ne : model.items()) {
					for (INormalizedManyToOneRelationship rel : ne
							.relationships().items()) {
						String clazzMany = javaNames.nameInterface(ne);
						String clazzOne = javaNames.nameInterface(model.get(rel
								.getTo()));
						String methodName = "findAll"
								+ baseNames.nameOfReverse(rel, model) + "From"
								+ baseNames.name(model.get(rel.getTo()));
						String leading = "public " + Iterable.class.getName()
								+ "<" + javaNames.fqn(clazzMany, context)
								+ "> " + methodName + "(";
						iout.println(leading + javaNames.fqn(clazzOne, context)
								+ " from) throws Exception {");
						iiout.println(" return "
								+ methodName
								+ "(from == null ? null : from.coordinates().getIdentifier());");
						iout.println("}");
						iout.println(leading + IIdentifier.class.getName()
								+ " from) throws Exception {");
						iiout.println(DefaultIdentifier.class.getName()
								+ ".checkIdentifierType(from, "
								+ javaNames.fqn(clazzOne, context) + ".class);");
						iiout.println("return find("
								+ javaNames.fqn(clazzMany, context)
								+ ".class, "
								+ javaNames.fqn(clazzOne, context)
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

		final JavaNames names = context.getJavaNames();
		final AndroidSQLiteNames anames = context.getSQLiteNames();
		final String clazz = anames.context(model, context);
		final String pkg = anames.packageForSQLite(context);

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
						+ fqnFinder + "> implements "
						+ names.fqnBase(names.context(model), context) + " {");
				iout.println("protected "
						+ IAndroidSQLiteEntityParserContext.class.getName()
						+ " createParsers() throws "
						+ Exception.class.getName() + " {");
				iiout.println("return new "
						+ anames.fqnSQLite(
								anames.parserContext(model, context), context)
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
						+ anames.fqnSQLite(anames.finder(model, context),
								context) + "() {");
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
		final JavaNames names = context.getJavaNames();
		final AndroidSQLiteNames anames = context.getSQLiteNames();
		final String pkg = anames.packageForSQLite(context);
		final String clazz = anames.parserContext(model, context);
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
					iiout.print(anames.fqnSQLiteParsers(
							anames.parserAndroidSQLite(entity), context));
					iiout.println("(idsParser, statusParser, types));");
				}
				iout.println("}");
				out.println("}");
				iiout.flush();
				iout.flush();
			}
		});
	}

	public void abstractParser(final INormalizedModel model,
			final AndroidSQLiteCodeGeneratorContext context) throws Exception {
		final AndroidSQLiteNames anames = context.getSQLiteNames();
		final SQLNames sqlNames = context.getSQLNames();
		final String pkg = anames.packageForSQLite(context);
		final String clazz = anames.abstractParser(model, context);
		FileUtils.java(context, pkg, clazz, new AbstractPrintable() {
			@Override
			protected void printWithException(PrintWriter out) throws Exception {
				PrintWriter iout = IndentedWriter.get(out);
				PrintWriter iiout = IndentedWriter.get(iout);
				out.println("package " + pkg + ";");
				out.println();
				out.println("public abstract class " + clazz + "<R extends "
						+ IRuntimeEntity.class.getName() + "<R>> extends "
						+ AbstractAndroidSQLiteEntityParser.class.getName()
						+ "<R> {");
				out.println();
				iout.println("public " + clazz + "("
						+ IAndroidSQLiteIdentifierParser.class.getName()
						+ " idsParser, "
						+ IAndroidSQLiteStatusParser.class.getName()
						+ " statusParser) throws " + Exception.class.getName()
						+ " {");
				iiout.println("super(idsParser, statusParser);");
				iout.println("}");
				iout.println();
				iout.println("public String idColumn() throws Exception {");
				iiout.println("return \""
						+ TextUtils.escapeJavaString(sqlNames.column(
								IDatabaseConstants.ID, null)) + "\";");
				iout.println("}");
				iout.println();
				iout.println("public String statusColumn() throws Exception {");
				iiout.println("return \""
						+ TextUtils.escapeJavaString(sqlNames.column(
								IDatabaseConstants.STATUS, null)) + "\";");
				iout.println("}");
				out.println("}");
				iiout.flush();
				iout.flush();
			}
		});
	}
}
