package com.th3l4b.srm.codegen.java.mongo.codegen;

import java.io.PrintWriter;

import com.mongodb.DB;
import com.mongodb.DBObject;
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
import com.th3l4b.srm.codegen.java.basicruntime.DefaultIdentifier;
import com.th3l4b.srm.codegen.java.mongo.runtime.AbstractMongoEntityParser;
import com.th3l4b.srm.codegen.java.mongo.runtime.AbstractMongoFinder;
import com.th3l4b.srm.codegen.java.mongo.runtime.AbstractMongoSRMContext;
import com.th3l4b.srm.codegen.java.mongo.runtime.DefaultMongoEntityParserContext;
import com.th3l4b.srm.codegen.java.mongo.runtime.IMongoEntityParserContext;
import com.th3l4b.srm.codegen.java.mongo.runtime.IMongoIdentifierParser;
import com.th3l4b.srm.codegen.java.mongo.runtime.IMongoRuntimeType;
import com.th3l4b.srm.codegen.java.mongo.runtime.IMongoRuntimeTypesContext;
import com.th3l4b.srm.codegen.java.mongo.runtime.IMongoStatusParser;
import com.th3l4b.srm.runtime.IIdentifier;
import com.th3l4b.srm.runtime.IModelUtils;
import com.th3l4b.types.base.ITypesConstants;

public class MongoCodeGenerator {

	public void finder(final INormalizedModel model,
			final MongoCodeGeneratorContext context) throws Exception {
		final BaseNames baseNames = context.getBaseNames();
		final MongoNames mongoNames = context.getMongoNames();
		final JavaNames javaNames = context.getJavaNames();
		final String clazz = mongoNames.finderMongo(model);
		final String pkg = mongoNames.packageForMongo(context);
		FileUtils.java(context, pkg, clazz, new AbstractPrintable() {
			@Override
			protected void printWithException(PrintWriter out) throws Exception {
				PrintWriter iout = IndentedWriter.get(out);
				PrintWriter iiout = IndentedWriter.get(iout);
				out.println("package " + pkg + ";");
				out.println();
				out.println("public abstract class " + clazz + " extends "
						+ AbstractMongoFinder.class.getName() + " implements "
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
						iiout.println(DefaultIdentifier.class.getName()
								+ ".checkIdentifierType(from, " + clazzOne
								+ ".class);");
						iiout.println("return find(" + clazzMany
								+ ".class, from, \""
								+ mongoNames.column(rel, model) + "\");");
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
			final INormalizedModel model,
			final MongoCodeGeneratorContext context) throws Exception {
		final BaseNames baseNames = context.getBaseNames();
		final MongoNames mongoNames = context.getMongoNames();
		final JavaNames javaNames = context.getJavaNames();
		final String clazz = mongoNames.parserMongo(entity);
		final String pkg = mongoNames.packageForMongoParsers(context);
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
						+ AbstractMongoEntityParser.class.getName() + "<"
						+ entityInterface + "> {");

				out.println();
				iout.println("public static final String COLLECTION_NAME = \""
						+ TextUtils.escapeJavaString(mongoNames
								.collection(entity)) + "\";");
				out.println();

				for (IField field : entity.items()) {
					iout.println("private "
							+ IMongoRuntimeType.class.getName()
							+ "<"
							+ context.getTypes().get(field.getType())
									.getProperties()
									.get(ITypesConstants.PROPERTY_JAVA_CLASS)
							+ "> " + fieldName(field, baseNames) + ";");
				}
				out.println();
				iout.println("public " + clazz + "("
						+ IMongoIdentifierParser.class.getName() + " ids, "
						+ IMongoStatusParser.class.getName() + " status, "
						+ IMongoRuntimeTypesContext.class.getName()
						+ " types) {");
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
						+ " { return COLLECTION_NAME; }");
				iout.println("public " + String.class.getName()
						+ " idColumn() throws " + Exception.class.getName()
						+ " { return \""
						+ TextUtils.escapeJavaString(SQLNames.ID) + "\"; }");
				iout.println("public " + String.class.getName()
						+ " statusColumn() throws " + Exception.class.getName()
						+ " { return \""
						+ TextUtils.escapeJavaString(SQLNames.STATUS) + "\"; }");
				iout.println("public "
						+ entityInterface
						+ " create() { return new "
						+ javaNames.fqnImpl(javaNames.nameImpl(entity), context)
						+ "(); }");
				iout.println("public static final String[] COLUMNS = {");
				boolean first = true;
				// Fill attributes
				for (IField field : entity.items()) {
					if (first) {
						first = false;
					} else {
						iiout.println(",");
					}
					iiout.print("\""
							+ TextUtils.escapeJavaString(mongoNames
									.column(field)) + "\"");
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
							+ TextUtils.escapeJavaString(mongoNames.column(rel,
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
						+ " entity, " + DBObject.class.getName()
						+ " result) throws " + Exception.class.getName() + " {");
				for (IField field : entity.items()) {
					String fname = baseNames.name(field);
					String cname = "\""
							+ TextUtils.escapeJavaString(mongoNames
									.column(field)) + "\"";
					iiout.println("if (result.containsField(" + cname
							+ ")) { entity.set" + fname + "("
							+ fieldName(field, baseNames) + ".parse(" + cname
							+ ", result)); }");
				}
				for (INormalizedManyToOneRelationship rel : entity
						.relationships().items()) {
					String rname = baseNames.nameOfDirect(rel, model);
					String cname = "\""
							+ TextUtils.escapeJavaString(mongoNames.column(rel,
									model)) + "\"";
					iiout.println("if (result.containsField(" + cname
							+ ")) { entity.set" + rname
							+ "(getIdsParser().parse(" + cname
							+ ", result)); }");
				}

				iout.println("}");
				iout.println("public void setRest(" + entityInterface
						+ " entity, " + DBObject.class.getName()
						+ " statement) throws " + Exception.class.getName()
						+ " {");
				for (IField field : entity.items()) {
					String fname = baseNames.name(field);
					String cname = "\""
							+ TextUtils.escapeJavaString(mongoNames
									.column(field)) + "\"";
					iiout.println("if (entity.isSet" + fname + "()) { "
							+ fieldName(field, baseNames) + ".set(entity.get"
							+ fname + "(), " + cname + ", statement); }");
				}
				for (INormalizedManyToOneRelationship rel : entity
						.relationships().items()) {
					String rname = baseNames.nameOfDirect(rel, model);
					String cname = "\""
							+ TextUtils.escapeJavaString(mongoNames.column(rel,
									model)) + "\"";
					iiout.println("if (entity.isSet" + rname
							+ "()) { getIdsParser().set(entity.get" + rname
							+ "(), " + cname + ", statement); }");
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
			final MongoCodeGeneratorContext context) throws Exception {
		final JavaNames javaNames = context.getJavaNames();
		final MongoNames names = context.getMongoNames();
		final String clazz = names.parserContext(model);
		final String pkg = names.packageForMongo(context);
		FileUtils.java(context, pkg, clazz, new AbstractPrintable() {
			@Override
			protected void printWithException(PrintWriter out) throws Exception {
				PrintWriter iout = IndentedWriter.get(out);
				PrintWriter iiout = IndentedWriter.get(iout);
				out.println("package " + pkg + ";");
				out.println();
				out.println("public class " + clazz + " extends "
						+ DefaultMongoEntityParserContext.class.getName()
						+ " {");

				// Setup parsers in constructor
				iout.println("public " + clazz + " ("
						+ IMongoIdentifierParser.class.getName()
						+ " idsParser, " + IMongoStatusParser.class.getName()
						+ " statusParser, "
						+ IMongoRuntimeTypesContext.class.getName()
						+ " types) throws " + Exception.class.getName() + " {");
				for (INormalizedEntity entity : model.items()) {
					iiout.print("put(");
					iiout.print(javaNames.fqn(javaNames.nameInterface(entity),
							context));
					iiout.print(".class, new ");
					iiout.print(names.fqnMongoParsers(
							names.parserMongo(entity), context));
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
			final MongoCodeGeneratorContext context) throws Exception {
		final JavaNames javaNames = context.getJavaNames();
		final MongoNames names = context.getMongoNames();
		final String clazz = names.abstractMongoContext(model);
		final String pkg = names.packageForMongo(context);
		FileUtils.java(context, pkg, clazz, new AbstractPrintable() {
			@Override
			protected void printWithException(PrintWriter out) throws Exception {
				out.println("package " + pkg + ";");
				out.println();
				String finderClass = javaNames.fqnBase(javaNames.finder(model),
						context);
				out.println("public abstract class " + clazz + " extends "
						+ AbstractMongoSRMContext.class.getName() + "<"
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
						+ IMongoEntityParserContext.class.getName()
						+ " createParsers() throws Exception {");
				iiout.println("return new "
						+ names.fqnMongo(names.parserContext(model), context)
						+ "(getIdentifierParser(), getStatusParser(), getTypes());");
				iout.println("}");
				out.println();

				iout.println("protected " + finderClass
						+ " createFinder() throws Exception {");
				iiout.println("return new "
						+ names.fqnMongo(names.finderMongo(model), context)
						+ "() {");

				iiiout.println(delegated(DB.class, "DB", clazz));
				iiiout.println(delegated(IMongoIdentifierParser.class,
						"IdentifierParser", clazz));
				iiiout.println(delegated(IMongoStatusParser.class,
						"StatusParser", clazz));
				iiiout.println(delegated(IMongoRuntimeTypesContext.class,
						"Types", clazz));
				iiiout.println(delegated(IMongoEntityParserContext.class,
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
