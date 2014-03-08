package com.th3l4b.srm.codegen.java.basic;

import java.io.PrintWriter;
import java.util.ArrayList;

import com.th3l4b.common.text.AbstractPrintable;
import com.th3l4b.common.text.IndentedWriter;
import com.th3l4b.srm.base.IField;
import com.th3l4b.srm.base.normalized.INormalizedEntity;
import com.th3l4b.srm.base.normalized.INormalizedManyToOneRelationship;
import com.th3l4b.srm.base.normalized.INormalizedModel;
import com.th3l4b.srm.codegen.base.FileUtils;
import com.th3l4b.srm.codegen.base.names.BaseNames;
import com.th3l4b.srm.codegen.java.basicruntime.AbstractModelUtils;
import com.th3l4b.srm.codegen.java.basicruntime.inmemory.AbstractRuntimeEntity;
import com.th3l4b.srm.runtime.IIdentifier;
import com.th3l4b.srm.runtime.IRuntimeEntity;
import com.th3l4b.srm.runtime.ISRMContext;
import com.th3l4b.types.base.ITypesConstants;

public class JavaCodeGenerator {
	public void entity(final INormalizedEntity entity,
			final INormalizedModel model, final JavaCodeGeneratorContext context)
			throws Exception {
		final BaseNames baseNames = context.getBaseNames();
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
						out.println("public interface "
								+ clazz
								+ " extends "
								+ javaNames.fqnBase(
										javaNames.modelEntity(model), context)
								+ "<" + clazz + "> {");

						// Fill attributes
						for (IField field : entity.items()) {
							String name = baseNames.name(field);
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
							String name = baseNames.nameOfDirect(rel, model);
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
									+ javaNames.fqnBase(
											javaNames.finder(model), context)
									+ " accessor) throws "
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
							iout.println("void reset" + name
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
		final BaseNames baseNames = context.getBaseNames();
		final JavaNames names = context.getJavaNames();
		final String clazz = names.nameImpl(entity);
		final String iclazz = names.fqn(names.nameInterface(entity), context);
		final String pkg = names.packageForImpl(context);
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
					String name = baseNames.name(field);
					String clazz = context.getTypes().get(field.getType())
							.getProperties()
							.get(ITypesConstants.PROPERTY_JAVA_CLASS);
					iout.println("protected " + clazz + " _value_" + name + ";");
					iout.println("protected boolean _isSet_" + name + ";");
				}
				for (INormalizedManyToOneRelationship rel : entity
						.relationships().items()) {
					String name = baseNames.nameOfDirect(rel, model);
					String clazz = IIdentifier.class.getName();
					iout.println("protected " + clazz + " _value_" + name + ";");
					iout.println("protected boolean _isSet_" + name + ";");
				}
				iout.println();

				// Implement accessors
				for (IField field : entity.items()) {
					String name = baseNames.name(field);
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
					String name = baseNames.nameOfDirect(rel, model);
					String getter = "get" + name;
					String setter = "set" + name;
					String clazz = names.nameInterface(model.get(rel.getTo()));
					String fqn = names.fqn(clazz, context);
					iout.println("public " + IIdentifier.class.getName() + " "
							+ getter + " () throws "
							+ Exception.class.getName() + " { return _value_"
							+ name + "; }");
					iout.println("public " + fqn + " " + getter + " ("
							+ names.fqnBase(names.finder(model), context)
							+ " accessor) throws " + Exception.class.getName()
							+ " { return _value_" + name
							+ " != null ? accessor.get"
							+ baseNames.name(model.get(rel.getTo())) + "(_value_"
							+ name + ") : null; }");
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
					iout.println("public void reset" + name
							+ "() throws Exception { _isSet_" + name
							+ " = false; }");
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
		final BaseNames baseNames = context.getBaseNames();
		final JavaNames javaNames = context.getJavaNames();
		final String clazz = javaNames.finder(model);
		FileUtils.java(context, javaNames.packageForBase(context), clazz,
				new AbstractPrintable() {
					@Override
					protected void printWithException(PrintWriter out)
							throws Exception {
						PrintWriter iout = IndentedWriter.get(out);
						out.println("package "
								+ javaNames.packageForBase(context) + ";");
						out.println();
						out.println("public interface " + clazz + " {");

						// Get the entities (individually or all)
						for (INormalizedEntity ne : model.items()) {
							iout.println(javaNames.fqn(
									javaNames.nameInterface(ne), context)
									+ " get"
									+ baseNames.name(ne)
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
									+ baseNames.name(ne)
									+ "() throws Exception;");
						}

						// And the relationships
						for (INormalizedEntity ne : model.items()) {
							for (INormalizedManyToOneRelationship rel : ne
									.relationships().items()) {
								String clazzMany = javaNames.nameInterface(ne);
								String clazzOne = javaNames.nameInterface(model
										.get(rel.getTo()));
								String leading = Iterable.class.getName()
										+ "<"
										+ javaNames.fqn(clazzMany, context)
										+ "> findAll"
										+ baseNames.nameOfReverse(rel, model)
										+ "From"
										+ baseNames.name(model.get(rel.getTo()))
										+ "(";
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

	public void modelUtils(final INormalizedModel model,
			final JavaCodeGeneratorContext context) throws Exception {
		final BaseNames baseNames = context.getBaseNames();
		final JavaNames javaNames = context.getJavaNames();
		final String clazz = javaNames.modelUtils(model);
		final String pkg = javaNames.packageForBase(context);
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
						attributes.add(baseNames.name(f));
					}

					for (INormalizedManyToOneRelationship rel : ne
							.relationships().items()) {
						attributes.add(baseNames.nameOfDirect(rel, model));

					}
					for (String attribute : attributes) {
						iiiout.println("if (source.isSet" + attribute
								+ "()) { target.set" + attribute
								+ "(source.get" + attribute + "()); }");
					}
					iiiout.println("target.coordinates().setIdentifier(source.coordinates().getIdentifier());");
					iiiout.println("target.coordinates().setStatus(source.coordinates().getStatus());");
					iiout.println("}});");
					iiout.println("_resetters.put(" + fqn
							+ ".class.getName(), new "
							+ AbstractModelUtils.class.getName()
							+ ".ForeignKeysClearer<" + fqn + ">() {");
					iiiout.println("@Override");
					iiiout.println("protected void clearEntity(" + fqn
							+ " obj) throws Exception {");
					// Clear relationships
					for (INormalizedManyToOneRelationship rel : ne
							.relationships().items()) {
						String name = baseNames.nameOfDirect(rel, model);
						iiiiout.println("obj.set" + name + "(("
								+ IIdentifier.class.getName() + ") null);");
						iiiiout.println("obj.reset" + name + "();");
					}
					iiiout.println("}");
					iiout.println("});");
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

	public void modelEntity(final INormalizedModel model,
			final JavaCodeGeneratorContext context) throws Exception {
		final JavaNames javaNames = context.getJavaNames();
		final String clazz = javaNames.modelEntity(model);
		final String pkg = javaNames.packageForBase(context);
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
		final String pkg = javaNames.packageForBase(context);
		FileUtils.java(context, pkg, clazz, new AbstractPrintable() {
			@Override
			protected void printWithException(PrintWriter out) throws Exception {
				out.println("package " + pkg + ";");
				out.println();
				out.println("public interface " + clazz + " extends "
						+ ISRMContext.class.getName() + "<"
						+ javaNames.fqnBase(javaNames.finder(model), context)
						+ "> {");
				out.println("}");
			}
		});
	}
}
