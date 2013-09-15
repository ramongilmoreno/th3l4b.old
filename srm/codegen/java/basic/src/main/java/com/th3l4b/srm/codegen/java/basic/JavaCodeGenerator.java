package com.th3l4b.srm.codegen.java.basic;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;

import com.th3l4b.common.text.AbstractPrintable;
import com.th3l4b.common.text.IndentedWriter;
import com.th3l4b.common.text.TextUtils;
import com.th3l4b.srm.base.IField;
import com.th3l4b.srm.base.normalized.INormalizedEntity;
import com.th3l4b.srm.base.normalized.INormalizedManyToOneRelationship;
import com.th3l4b.srm.base.normalized.INormalizedModel;
import com.th3l4b.srm.codegen.base.FileUtils;
import com.th3l4b.srm.codegen.java.basicruntime.AbstractModelUtils;
import com.th3l4b.srm.codegen.java.basicruntime.ISRMContext;
import com.th3l4b.srm.codegen.java.basicruntime.storage.inmemory.AbstractInMemoryFinder;
import com.th3l4b.srm.codegen.java.basicruntime.storage.inmemory.AbstractInMemorySRMContext;
import com.th3l4b.srm.codegen.java.basicruntime.storage.inmemory.AbstractPredicateOfRelationship;
import com.th3l4b.srm.codegen.java.basicruntime.storage.inmemory.AbstractRuntimeEntity;
import com.th3l4b.srm.codegen.java.basicruntime.storage.inmemory.Pair;
import com.th3l4b.srm.runtime.IIdentifier;
import com.th3l4b.srm.runtime.IModelUtils;
import com.th3l4b.srm.runtime.IRuntimeEntity;
import com.th3l4b.types.base.ITypesConstants;

public class JavaCodeGenerator {
	public void entity(final INormalizedEntity entity,
			final INormalizedModel model, final JavaCodeGeneratorContext context)
			throws Exception {
		final JavaNames javaNames = context.getJavaNames();
		final String clazz = javaNames.nameInterface(entity);
		FileUtils.java(context, context.getPackage(), clazz,
				new AbstractPrintable() {
					@Override
					protected void printWithException(PrintWriter out)
							throws Exception {
						PrintWriter iout = IndentedWriter.get(out);
						out.println("package " + context.getPackage() + ";");
						out.println();
						out.println("public interface " + clazz + " extends "
								+ javaNames.modelEntity(model) + "<" + clazz
								+ "> {");

						// Fill attributes
						for (IField field : entity.items()) {
							String name = javaNames.name(field);
							String clazz = context.getTypes()
									.get(field.getType()).getProperties()
									.get(ITypesConstants.PROPERTY_JAVA_CLASS);
							iout.println("" + clazz + " get" + name
									+ "() throws Exception;");
							iout.println("void set" + name + "(" + clazz
									+ " value) throws Exception;");
							iout.println("boolean isSet" + name
									+ "() throws Exception;");
						}

						// Fill relationships
						for (INormalizedManyToOneRelationship rel : entity
								.relationships().items()) {
							String name = javaNames.nameOfDirect(rel, model);
							String getter = "get" + name;
							String setter = "set" + name;
							String clazz = javaNames.fqn(javaNames
									.nameInterface(model.get(rel.getTo())),
									context);
							iout.println("public "
									+ IIdentifier.class.getName() + " "
									+ getter + " () throws "
									+ Exception.class.getName() + ";");
							iout.println("public "
									+ clazz
									+ " "
									+ getter
									+ " ("
									+ javaNames.fqn(javaNames.finder(model),
											context) + " accessor) throws "
									+ Exception.class.getName() + ";");
							iout.println("void " + setter + " ("
									+ IIdentifier.class.getName()
									+ " arg) throws "
									+ Exception.class.getName() + ";");
							iout.println("void " + setter + " (" + clazz
									+ " arg) throws "
									+ Exception.class.getName() + ";");
							iout.println("boolean isSet" + name
									+ "() throws Exception;");
						}

						out.println("}");
						iout.flush();
					}
				});

	}

	public void entityImpl(final INormalizedEntity entity,
			final INormalizedModel model, final JavaCodeGeneratorContext context)
			throws Exception {
		final JavaNames javaNames = context.getJavaNames();
		final String clazz = javaNames.nameImpl(entity);
		final String iclazz = javaNames.fqn(javaNames.nameInterface(entity),
				context);
		final String pkg = javaNames.packageForImpl(context);
		FileUtils.java(context, pkg, clazz, new AbstractPrintable() {
			@Override
			protected void printWithException(PrintWriter out) throws Exception {
				PrintWriter iout = IndentedWriter.get(out);
				out.println("package " + pkg + ";");
				out.println();
				out.println("public class " + clazz + " extends "
						+ AbstractRuntimeEntity.class.getName() + "<" + iclazz
						+ "> implements " + iclazz + " {");
				iout.println();

				// Create attributes
				for (IField field : entity.items()) {
					String name = javaNames.name(field);
					String clazz = context.getTypes().get(field.getType())
							.getProperties()
							.get(ITypesConstants.PROPERTY_JAVA_CLASS);
					iout.println("protected " + clazz + " _value_" + name + ";");
					iout.println("protected boolean _isSet_" + name + ";");
				}
				for (INormalizedManyToOneRelationship rel : entity
						.relationships().items()) {
					String name = javaNames.nameOfDirect(rel, model);
					String clazz = IIdentifier.class.getName();
					iout.println("protected " + clazz + " _value_" + name + ";");
					iout.println("protected boolean _isSet_" + name + ";");
				}
				iout.println();

				// Implement accessors
				for (IField field : entity.items()) {
					String name = javaNames.name(field);
					String clazz = context.getTypes().get(field.getType())
							.getProperties()
							.get(ITypesConstants.PROPERTY_JAVA_CLASS);
					iout.println("public " + clazz + " get" + name
							+ "() throws Exception { return _value_" + name
							+ "; }");
					iout.println("public void set" + name + "(" + clazz
							+ " value) throws Exception { _isSet_" + name
							+ " = true; _value_" + name + " = value; }");
					iout.println("public boolean isSet" + name
							+ "() throws Exception { return _isSet_" + name
							+ "; }");
				}

				// Fill relationships
				for (INormalizedManyToOneRelationship rel : entity
						.relationships().items()) {
					String name = javaNames.nameOfDirect(rel, model);
					String getter = "get" + name;
					String setter = "set" + name;
					String clazz = javaNames.nameInterface(model.get(rel
							.getTo()));
					String fqn = javaNames.fqn(clazz, context);
					iout.println("public " + IIdentifier.class.getName() + " "
							+ getter + " () throws "
							+ Exception.class.getName() + " { return _value_"
							+ name + "; }");
					iout.println("public " + fqn + " " + getter + " ("
							+ javaNames.fqn(javaNames.finder(model), context)
							+ " accessor) throws " + Exception.class.getName()
							+ " { return _value_" + name
							+ " != null ? accessor.get"
							+ javaNames.name(model.get(rel.getTo()))
							+ "(_value_" + name + ") : null; }");
					iout.println("public void " + setter + " ("
							+ IIdentifier.class.getName() + " arg) throws "
							+ Exception.class.getName() + " { _isSet_" + name
							+ " = true; _value_" + name + " = arg; }");
					iout.println("public void "
							+ setter
							+ " ("
							+ fqn
							+ " arg) throws "
							+ Exception.class.getName()
							+ " { "
							+ setter
							+ "(arg != null ? arg.coordinates().getIdentifier() : null); }");
					iout.println("public boolean isSet" + name
							+ "() throws Exception { return _isSet_" + name
							+ "; }");
				}
				iout.println();
				iout.println("@Override");
				iout.println("public Class<" + iclazz + "> clazz() { return "
						+ iclazz + ".class; }");
				out.println("}");
				iout.flush();
			}
		});

	}

	public void finder(final INormalizedModel model,
			final JavaCodeGeneratorContext context) throws Exception {
		final JavaNames javaNames = context.getJavaNames();
		final String clazz = javaNames.finder(model);
		FileUtils.java(context, context.getPackage(), clazz,
				new AbstractPrintable() {
					@Override
					protected void printWithException(PrintWriter out)
							throws Exception {
						PrintWriter iout = IndentedWriter.get(out);
						out.println("package " + context.getPackage() + ";");
						out.println();
						out.println("public interface " + clazz + " {");

						// Get the entities (individually or all)
						for (INormalizedEntity ne : model.items()) {
							iout.println(javaNames.fqn(
									javaNames.nameInterface(ne), context)
									+ " get"
									+ javaNames.name(ne)
									+ "("
									+ IIdentifier.class.getName()
									+ " id) throws Exception;");
						}

						for (INormalizedEntity ne : model.items()) {
							iout.println(Iterable.class.getName()
									+ "<"
									+ javaNames.fqn(
											javaNames.nameInterface(ne),
											context) + "> all"
									+ javaNames.name(ne)
									+ "() throws Exception;");
						}

						// And the relationships
						for (INormalizedEntity ne : model.items()) {
							for (INormalizedManyToOneRelationship rel : ne
									.relationships().items()) {
								String clazzMany = javaNames.nameInterface(ne);
								String clazzOne = javaNames.nameInterface(model
										.get(rel.getTo()));
								String leading = Iterable.class.getName() + "<"
										+ javaNames.fqn(clazzMany, context)
										+ "> findAll"
										+ javaNames.nameOfReverse(rel, model)
										+ "From" + clazzOne + "(";
								iout.println(leading
										+ javaNames.fqn(clazzOne, context)
										+ " from) throws Exception;");
								iout.println(leading
										+ IIdentifier.class.getName()
										+ " from) throws Exception;");

							}
						}
						out.println("}");
						iout.flush();
					}
				});

	}

	public void finderInMemory(final INormalizedModel model,
			final JavaCodeGeneratorContext context) throws Exception {
		final JavaNames javaNames = context.getJavaNames();
		final String clazz = javaNames.finderInMemory(model);
		final String pkg = javaNames.packageForImpl(context);
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
						+ javaNames.fqn(javaNames.finder(model), context)
						+ " {");

				// Setup finders in constructor
				iout.println("public " + clazz + " () {");
				// And the relationships
				for (INormalizedEntity ne : model.items()) {
					for (INormalizedManyToOneRelationship rel : ne
							.relationships().items()) {
						String clazzMany = javaNames.fqn(
								javaNames.nameInterface(ne), context);
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
								+ javaNames.nameOfDirect(rel, model) + "(); }");
						iiout.println("});");
					}
				}
				iout.println("}");
				iout.println();

				// Get the entities (individually or all)
				for (INormalizedEntity ne : model.items()) {
					String fqn = javaNames.fqn(javaNames.nameInterface(ne),
							context);
					iout.println("public " + fqn + " get" + javaNames.name(ne)
							+ "(" + IIdentifier.class.getName()
							+ " id) throws Exception {");
					iiout.println("return find(" + fqn + ".class, id);");
					iout.println("}");
				}

				for (INormalizedEntity ne : model.items()) {
					String fqn = javaNames.fqn(javaNames.nameInterface(ne),
							context);
					iout.println("public " + Iterable.class.getName() + "<"
							+ fqn + "> all" + javaNames.name(ne)
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
								+ javaNames.nameOfReverse(rel, model) + "From"
								+ clazzOne;
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

	public void modelUtils(final INormalizedModel model,
			final JavaCodeGeneratorContext context) throws Exception {
		final JavaNames javaNames = context.getJavaNames();
		final String clazz = javaNames.modelUtils(model);
		final String pkg = javaNames.packageForImpl(context);
		FileUtils.java(context, pkg, clazz, new AbstractPrintable() {
			@Override
			protected void printWithException(PrintWriter out) throws Exception {
				PrintWriter iout = IndentedWriter.get(out);
				PrintWriter iiout = IndentedWriter.get(iout);
				PrintWriter iiiout = IndentedWriter.get(iiout);
				out.println("package " + pkg + ";");
				out.println();
				out.println("public class " + clazz + " extends "
						+ AbstractModelUtils.class.getName() + " {");
				iout.println("public " + clazz + "() {");
				for (INormalizedEntity ne : model.items()) {
					String clazz = javaNames.nameInterface(ne);
					String fqn = javaNames.fqn(clazz, context);
					iiout.println("_creators.put("
							+ fqn
							+ ".class.getName(), new "
							+ AbstractModelUtils.class.getName()
							+ ".Creator() { public Object create() throws Exception { return initialize("
							+ fqn
							+ ".class, new "
							+ javaNames.fqnImpl(javaNames.nameImpl(ne), context)
							+ "());}});");
					iiout.println("_copiers.put(" + fqn
							+ ".class.getName(), new "
							+ AbstractModelUtils.class.getName() + ".Copier<"
							+ fqn + ">() { protected void copyEntity(" + fqn
							+ " source, " + fqn + " target) throws Exception {");
					ArrayList<String> attributes = new ArrayList<String>();
					for (IField f : ne.items()) {
						attributes.add(javaNames.name(f));
					}

					for (INormalizedManyToOneRelationship rel : ne
							.relationships().items()) {
						attributes.add(javaNames.nameOfDirect(rel, model));

					}
					for (String attribute : attributes) {
						iiiout.println("if (source.isSet" + attribute
								+ "()) { target.set" + attribute
								+ "(source.get" + attribute + "()); }");
					}
					iiiout.println("source.coordinates().setIdentifier(source.coordinates().getIdentifier());");
					iiiout.println("source.coordinates().setStatus(source.coordinates().getStatus());");
					iiout.println("}});");
				}
				iout.println("}");
				out.println("}");
				iiiout.flush();
				iiout.flush();
				iout.flush();
			}
		});
	}

	public void modelEntity(final INormalizedModel model,
			final JavaCodeGeneratorContext context) throws Exception {
		final JavaNames javaNames = context.getJavaNames();
		final String clazz = javaNames.modelEntity(model);
		final String pkg = context.getPackage();
		FileUtils.java(context, pkg, clazz, new AbstractPrintable() {
			@Override
			protected void printWithException(PrintWriter out) throws Exception {
				out.println("package " + pkg + ";");
				out.println();
				out.println("public interface " + clazz + "<T extends " + clazz
						+ "<T>> extends " + IRuntimeEntity.class.getName()
						+ "<T> {");
				out.println("}");
			}
		});
	}

	public void context(final INormalizedModel model,
			final JavaCodeGeneratorContext context) throws Exception {
		final JavaNames javaNames = context.getJavaNames();
		final String clazz = javaNames.context(model);
		final String pkg = context.getPackage();
		FileUtils.java(context, pkg, clazz, new AbstractPrintable() {
			@Override
			protected void printWithException(PrintWriter out) throws Exception {
				out.println("package " + pkg + ";");
				out.println();
				out.println("public interface " + clazz + " extends "
						+ ISRMContext.class.getName() + "<"
						+ javaNames.fqn(javaNames.finder(model), context)
						+ "> {");
				out.println("}");
			}
		});
	}

	public void abstractInMemoryContext(final INormalizedModel model,
			final JavaCodeGeneratorContext context) throws Exception {
		final JavaNames javaNames = context.getJavaNames();
		final String clazz = javaNames.abstractInMemoryContext(model);
		final String pkg = javaNames.packageForImpl(context);
		FileUtils.java(context, pkg, clazz, new AbstractPrintable() {
			@Override
			protected void printWithException(PrintWriter out) throws Exception {
				out.println("package " + pkg + ";");
				out.println();
				String finderClass = javaNames.fqn(javaNames.finder(model),
						context);
				out.println("public abstract class " + clazz + " extends "
						+ AbstractInMemorySRMContext.class.getName() + "<"
						+ finderClass + "> implements "
						+ javaNames.fqn(javaNames.context(model), context)
						+ " {");
				PrintWriter iout = IndentedWriter.get(out);
				PrintWriter iiout = IndentedWriter.get(iout);
				PrintWriter iiiout = IndentedWriter.get(iiout);
				PrintWriter iiiiout = IndentedWriter.get(iiiout);
				iout.println("protected " + IModelUtils.class.getName()
						+ " createUtils() throws Exception {");
				iiout.println("return new "
						+ javaNames.fqnImpl(javaNames.modelUtils(model),
								context) + "();");
				iout.println("}");
				out.println();

				iout.println("protected " + finderClass
						+ " createFinder() throws Exception {");
				iiout.println("return new "
						+ javaNames.fqnImpl(javaNames.finderInMemory(model),
								context) + "() {");
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
