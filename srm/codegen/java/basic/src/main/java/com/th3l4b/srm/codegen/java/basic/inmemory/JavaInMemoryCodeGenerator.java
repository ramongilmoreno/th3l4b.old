package com.th3l4b.srm.codegen.java.basic.inmemory;

import java.io.PrintWriter;
import java.util.Map;

import com.th3l4b.common.text.AbstractPrintable;
import com.th3l4b.common.text.IndentedWriter;
import com.th3l4b.common.text.TextUtils;
import com.th3l4b.srm.base.normalized.INormalizedEntity;
import com.th3l4b.srm.base.normalized.INormalizedManyToOneRelationship;
import com.th3l4b.srm.base.normalized.INormalizedModel;
import com.th3l4b.srm.codegen.base.FileUtils;
import com.th3l4b.srm.codegen.java.basicruntime.inmemory.AbstractInMemoryFinder;
import com.th3l4b.srm.codegen.java.basicruntime.inmemory.AbstractInMemorySRMContext;
import com.th3l4b.srm.codegen.java.basicruntime.inmemory.AbstractPredicateOfRelationship;
import com.th3l4b.srm.codegen.java.basicruntime.inmemory.Pair;
import com.th3l4b.srm.runtime.IIdentifier;
import com.th3l4b.srm.runtime.IModelUtils;
import com.th3l4b.srm.runtime.IRuntimeEntity;

public class JavaInMemoryCodeGenerator {

	public void finderInMemory(final INormalizedModel model,
			final JavaInMemoryCodeGeneratorContext context) throws Exception {
		final JavaInMemoryNames names = context.getJavaInMemoryNames();
		final String clazz = names.finderInMemory(model);
		final String pkg = names.packageForInMemory(context);
		FileUtils.java(context, pkg, clazz, new AbstractPrintable() {
			@Override
			protected void printWithException(PrintWriter out) throws Exception {
				PrintWriter iout = IndentedWriter.get(out);
				PrintWriter iiout = IndentedWriter.get(iout);
				PrintWriter iiiout = IndentedWriter.get(iiout);
				out.println("package " + pkg + ";");
				out.println();
				out.println("public abstract class " + clazz + " extends "
						+ AbstractInMemoryFinder.class.getName()
						+ " implements "
						+ names.fqnBase(names.finder(model), context) + " {");

				// Setup finders in constructor
				iout.println("public " + clazz + " () {");
				// And the relationships
				for (INormalizedEntity ne : model.items()) {
					for (INormalizedManyToOneRelationship rel : ne
							.relationships().items()) {
						String clazzMany = names.fqn(names.nameInterface(ne),
								context);
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
								+ "\"), new "
								+ AbstractPredicateOfRelationship.class
										.getName() + "<" + clazzMany + ", "
								+ clazzOne + ">(" + clazzMany + ".class, "
								+ clazzOne + ".class) {");
						iiiout.println("protected "
								+ IIdentifier.class.getName()
								+ " getSource ("
								+ clazzMany
								+ " candidateResult) throws Exception { return candidateResult.get"
								+ names.nameOfDirect(rel, model) + "(); }");
						iiout.println("});");
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

	public void abstractInMemoryContext(final INormalizedModel model,
			final JavaInMemoryCodeGeneratorContext context) throws Exception {
		final JavaInMemoryNames names = context.getJavaInMemoryNames();
		final String clazz = names.abstractInMemoryContext(model);
		final String pkg = names.packageForInMemory(context);
		FileUtils.java(context, pkg, clazz, new AbstractPrintable() {
			@Override
			protected void printWithException(PrintWriter out) throws Exception {
				out.println("package " + pkg + ";");
				out.println();
				String finderClass = names.fqnBase(names.finder(model), context);
				out.println("public abstract class " + clazz + " extends "
						+ AbstractInMemorySRMContext.class.getName() + "<"
						+ finderClass + "> implements "
						+ names.fqnBase(names.context(model), context) + " {");
				PrintWriter iout = IndentedWriter.get(out);
				PrintWriter iiout = IndentedWriter.get(iout);
				PrintWriter iiiout = IndentedWriter.get(iiout);
				PrintWriter iiiiout = IndentedWriter.get(iiiout);
				iout.println("protected " + IModelUtils.class.getName()
						+ " createUtils() throws Exception {");
				iiout.println("return new "
						+ names.fqnBase(names.modelUtils(model), context)
						+ "();");
				iout.println("}");
				out.println();

				iout.println("protected " + finderClass
						+ " createFinder() throws Exception {");
				iiout.println("return new "
						+ names.fqnInMemory(context.getJavaInMemoryNames()
								.finderInMemory(model), context) + "() {");
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
