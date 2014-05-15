package com.th3l4b.srm.codegen.java.web.rest.codegen;

import java.io.PrintWriter;

import com.th3l4b.common.text.AbstractPrintable;
import com.th3l4b.common.text.IndentedWriter;
import com.th3l4b.common.text.TextUtils;
import com.th3l4b.srm.base.normalized.INormalizedEntity;
import com.th3l4b.srm.base.normalized.INormalizedManyToOneRelationship;
import com.th3l4b.srm.base.normalized.INormalizedModel;
import com.th3l4b.srm.codegen.base.FileUtils;
import com.th3l4b.srm.codegen.base.names.BaseNames;
import com.th3l4b.srm.codegen.java.basic.JavaNames;
import com.th3l4b.srm.codegen.java.basicruntime.DefaultIdentifier;
import com.th3l4b.srm.codegen.java.basicruntime.rest.DefaultRESTFinder;
import com.th3l4b.srm.codegen.java.basicruntime.rest.IFindAllForRESTFinder;
import com.th3l4b.srm.codegen.java.basicruntime.rest.IFindManyForRESTFinder;
import com.th3l4b.srm.codegen.java.basicruntime.rest.IFindOneForRESTFinder;

public class RESTCodeGenerator {

	public void finder(final INormalizedModel model,
			final RESTCodeGeneratorContext context) throws Exception {
		final BaseNames baseNames = context.getBaseNames();
		final JavaNames javaNames = context.getJavaNames();
		final RESTNames restNames = context.getRESTNames();
		final String clazz = restNames.finder(model);
		final String pkg = restNames.packageForREST(context);
		FileUtils.java(context, pkg, clazz, new AbstractPrintable() {
			@Override
			protected void printWithException(PrintWriter out) throws Exception {
				PrintWriter iout = IndentedWriter.get(out);
				PrintWriter iiout = IndentedWriter.get(iout);
				PrintWriter iiiout = IndentedWriter.get(iiout);
				PrintWriter iiiiout = IndentedWriter.get(iiiout);
				out.println("package " + pkg + ";");
				out.println();
				out.println("public class " + clazz + " extends "
						+ DefaultRESTFinder.class.getName() + "<"
						+ javaNames.fqnBase(javaNames.finder(model), context)
						+ ">" + " {");

				iout.println("public " + clazz + " () throws "
						+ Exception.class.getName() + " {");
				String finder = javaNames.fqnBase(javaNames.finder(model),
						context);

				iiout.println("// All");
				for (INormalizedEntity entity : model.items()) {
					String itf = javaNames.fqn(javaNames.nameInterface(entity),
							context);
					iiout.println("register(new "
							+ IFindAllForRESTFinder.class.getName() + "<" + itf
							+ ", " + finder + ">() {");
					iiiout.println("public " + Iterable.class.getName() + "<"
							+ itf + "> all (" + finder + " finder) throws "
							+ Exception.class.getName() + " {");
					String name = baseNames.name(entity);
					iiiiout.println("return finder.all" + name + "();");
					iiiout.println("}");
					iiout.println("}, \"" + TextUtils.escapeJavaString(name)
							+ "\");");
				}

				iiout.println();
				iiout.println("// One");
				for (INormalizedEntity entity : model.items()) {
					String itf = javaNames.fqn(javaNames.nameInterface(entity),
							context);
					iiout.println("register(new "
							+ IFindOneForRESTFinder.class.getName() + "<" + itf
							+ ", " + finder + ">() {");
					iiiout.println("public " + itf + " find ("
							+ String.class.getName() + " id, " + finder
							+ " finder) throws " + Exception.class.getName()
							+ " {");
					String name = baseNames.name(entity);
					iiiiout.println("return finder.get"
							+ name + "(new "
							+ DefaultIdentifier.class.getName() + "(" + itf
							+ ".class, id));");
					iiiout.println("}");
					iiout.println("}, \""
							+ TextUtils.escapeJavaString(name)
							+ "\");");
				}

				iiout.println();
				iiout.println("// Many");
				for (INormalizedEntity entity : model.items()) {
					String itf = javaNames.fqn(javaNames.nameInterface(entity),
							context);
					for (INormalizedManyToOneRelationship rel : entity
							.relationships().items()) {
						iiout.println("register(new "
								+ IFindManyForRESTFinder.class.getName() + "<"
								+ itf + ", " + finder + ">() {");
						iiiout.println("public " + Iterable.class.getName()
								+ "<" + itf + "> many ("
								+ String.class.getName() + " id, " + finder
								+ " finder) throws "
								+ Exception.class.getName() + " {");
						String reverseRelationshipName = baseNames
								.nameOfReverse(rel, model);
						String name = baseNames.name(model.get(rel.getTo()));
						iiiiout.println("return finder.findAll"
								+ reverseRelationshipName
								+ "From"
								+ name
								+ "(new "
								+ DefaultIdentifier.class.getName()
								+ "("
								+ javaNames.fqn(javaNames.nameInterface(model
										.get(rel.getTo())), context)
								+ ".class, id));");
						iiiout.println("}");
						iiout.println("}, \""
								+ TextUtils.escapeJavaString(name)
								+ "\", \""
								+ TextUtils
										.escapeJavaString(reverseRelationshipName)
								+ "\");");
					}
				}

				iout.println("}");
				out.println("}");
				iiiiout.flush();
				iiiout.flush();
				iiout.flush();
				iout.flush();
			}
		});
	}
}
