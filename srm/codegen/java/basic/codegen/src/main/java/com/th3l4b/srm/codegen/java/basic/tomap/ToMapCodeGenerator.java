package com.th3l4b.srm.codegen.java.basic.tomap;

import java.io.PrintWriter;
import java.util.Map;

import com.th3l4b.common.text.AbstractPrintable;
import com.th3l4b.common.text.IndentedWriter;
import com.th3l4b.common.text.TextUtils;
import com.th3l4b.srm.base.IField;
import com.th3l4b.srm.base.normalized.INormalizedEntity;
import com.th3l4b.srm.base.normalized.INormalizedManyToOneRelationship;
import com.th3l4b.srm.base.normalized.INormalizedModel;
import com.th3l4b.srm.codegen.base.FileUtils;
import com.th3l4b.srm.codegen.base.names.BaseNames;
import com.th3l4b.srm.codegen.java.basic.JavaNames;
import com.th3l4b.srm.codegen.java.basicruntime.DefaultIdentifier;
import com.th3l4b.srm.codegen.java.basicruntime.tomap.AbstractToMapEntityParser;
import com.th3l4b.srm.codegen.java.basicruntime.tomap.IToMapIdentifierParser;
import com.th3l4b.srm.codegen.java.basicruntime.tomap.IToMapStatusParser;
import com.th3l4b.srm.runtime.DefaultToMapEntityParserContext;
import com.th3l4b.srm.runtime.IModelUtils;
import com.th3l4b.types.base.ITypesConstants;
import com.th3l4b.types.runtime.IJavaRuntimeType;
import com.th3l4b.types.runtime.IJavaRuntimeTypesContext;

public class ToMapCodeGenerator {

	public void entityParser(final INormalizedEntity entity,
			final INormalizedModel model,
			final ToMapCodeGeneratorContext context) throws Exception {
		final BaseNames baseNames = context.getBaseNames();
		final ToMapNames toMapNames = context.getToMapNames();
		final JavaNames javaNames = context.getJavaNames();
		final String clazz = toMapNames.parserToMap(entity);
		final String pkg = toMapNames.packageForToMapParsers(context);
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
						+ AbstractToMapEntityParser.class.getName() + "<"
						+ entityInterface + "> {");
				out.println();
				iout.println("private " + IModelUtils.class.getName()
						+ " _utils;");
				for (IField field : entity.items()) {
					iout.println("private "
							+ IJavaRuntimeType.class.getName()
							+ "<"
							+ context.getTypes().get(field.getType())
									.getProperties()
									.get(ITypesConstants.PROPERTY_JAVA_CLASS)
							+ "> " + fieldName(field, baseNames) + ";");
				}
				out.println();
				iout.println("public " + clazz + "("
						+ IToMapIdentifierParser.class.getName() + " ids, "
						+ IToMapStatusParser.class.getName() + " status, "
						+ IJavaRuntimeTypesContext.class.getName() + " types, "
						+ IModelUtils.class.getName() + " utils) throws "
						+ Exception.class.getName() + "{");
				iiout.println("super(\""
						+ TextUtils.escapeJavaString(baseNames.name(entity))
						+ "\", ids, status);");
				iiout.println("_utils = utils;");
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
				iout.println("protected " + entityInterface
						+ " create () throws " + Exception.class.getName()
						+ " { return _utils.create(" + entityInterface
						+ ".class); }");
				iout.println();

				iout.println("protected void parseRest(" + entityInterface
						+ " entity, " + Map.class.getName()
						+ "<String, String> map) throws "
						+ Exception.class.getName() + " {");
				for (IField field : entity.items()) {
					String fname = "\""
							+ TextUtils.escapeJavaString(baseNames.name(field))
							+ "\"";
					iiout.println("if (map.containsKey(" + fname + ")) {");
					iiiout.println("entity.set" + baseNames.name(field) + "("
							+ fieldName(field, baseNames)
							+ ".fromString(map.get(" + fname + ")));");
					iiout.println("}");
				}
				for (INormalizedManyToOneRelationship rel : entity
						.relationships().items()) {
					String relName = baseNames.nameOfDirect(rel, model);
					iiout.println("if (getIdentifierParser().hasValue(\""
							+ relName + "\", map)) {");
					iiiout.println("entity.set"
							+ relName
							+ "("
							+ DefaultIdentifier.class.getName()
							+ ".setIdentifierType(getIdentifierParser().parse(\""
							+ relName
							+ "\", map), "
							+ javaNames.fqn(javaNames.nameInterface(model
									.get(rel.getTo())), context) + ".class));");
					iiout.println("}");
				}

				iout.println("}");
				iout.println();
				iout.println("public void setRest(" + entityInterface
						+ " entity, " + Map.class.getName()
						+ "<String, String> map) throws "
						+ Exception.class.getName() + " {");
				for (IField field : entity.items()) {
					String name = baseNames.name(field);
					iiout.println("if (entity.isSet" + name + "()) {");
					iiiout.println("map.put(\""
							+ TextUtils.escapeJavaString(name) + "\", "
							+ fieldName(field, baseNames)
							+ ".toString(entity.get" + name + "()));");
					iiout.println("}");
				}
				for (INormalizedManyToOneRelationship rel : entity
						.relationships().items()) {
					String name = baseNames.nameOfDirect(rel, model);
					iiout.println("if (entity.isSet" + name + "()) {");
					iiiout.println("getIdentifierParser().set(entity.get"
							+ name + "(), \""
							+ TextUtils.escapeJavaString(name) + "\", map);");
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

	private String fieldName(IField f, BaseNames names) throws Exception {
		return "_field_" + names.name(f);
	}

	public void toMapParserContext(final INormalizedModel model,
			final ToMapCodeGeneratorContext context) throws Exception {
		final JavaNames javaNames = context.getJavaNames();
		final ToMapNames toMapNames = context.getToMapNames();
		final String clazz = toMapNames.toMapParserContext(model);
		final String pkg = toMapNames.packageForToMap(context);
		FileUtils.java(context, pkg, clazz, new AbstractPrintable() {
			@Override
			protected void printWithException(PrintWriter out) throws Exception {
				PrintWriter iout = IndentedWriter.get(out);
				PrintWriter iiout = IndentedWriter.get(iout);
				out.println("package " + pkg + ";");
				out.println();
				out.println("public class " + clazz + " extends "
						+ DefaultToMapEntityParserContext.class.getName()
						+ " {");

				// Setup parsers in constructor
				iout.println("public " + clazz + " ("
						+ IToMapIdentifierParser.class.getName()
						+ " idsParser, " + IToMapStatusParser.class.getName()
						+ " statusParser, "
						+ IJavaRuntimeTypesContext.class.getName() + " types, "
						+ IModelUtils.class.getName() + " utils) throws "
						+ Exception.class.getName() + " {");
				for (INormalizedEntity entity : model.items()) {
					iiout.print("put(");
					iiout.print(javaNames.fqn(javaNames.nameInterface(entity),
							context));
					iiout.print(".class, new ");
					iiout.print(toMapNames.fqnToMapParsers(
							toMapNames.parserToMap(entity), context));
					iiout.println("(idsParser, statusParser, types, utils));");
				}
				iout.println("}");
				out.println("}");
				iiout.flush();
				iout.flush();
			}
		});
	}
}
