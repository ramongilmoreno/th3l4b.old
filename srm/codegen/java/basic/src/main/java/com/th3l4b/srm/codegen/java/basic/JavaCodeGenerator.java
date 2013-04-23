package com.th3l4b.srm.codegen.java.basic;

import java.io.PrintWriter;

import com.th3l4b.common.text.AbstractPrintable;
import com.th3l4b.common.text.IndentedWriter;
import com.th3l4b.srm.base.IField;
import com.th3l4b.srm.base.normalized.INormalizedEntity;
import com.th3l4b.srm.base.normalized.INormalizedManyToOneRelationship;
import com.th3l4b.srm.base.normalized.INormalizedModel;
import com.th3l4b.srm.codegen.base.FileUtils;
import com.th3l4b.srm.codegen.java.basicruntime.storage.inmemory.AbstractRuntimeEntity;
import com.th3l4b.srm.runtime.IIdentifier;
import com.th3l4b.srm.runtime.IRuntimeEntity;
import com.th3l4b.types.base.ITypesConstants;

public class JavaCodeGenerator {
	public void entity(final INormalizedEntity entity,
			final INormalizedModel model, final JavaCodeGeneratorContext context)
			throws Exception {
		final String clazz = JavaNames.name(entity);
		FileUtils.java(context, context.getPackage(), clazz,
				new AbstractPrintable() {
					@Override
					protected void printWithException(PrintWriter out)
							throws Exception {
						PrintWriter iout = IndentedWriter.get(out);
						out.println("package " + context.getPackage() + ";");
						out.println();
						out.println("public interface " + clazz + " extends "
								+ IRuntimeEntity.class.getName() + "<" + clazz
								+ "> {");

						// Fill attributes
						for (IField field : entity.items()) {
							String name = JavaNames.name(field);
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
							String name = JavaNames.javaIdentifier(rel.getDirect()
									.getName());
							String getter = "get" + name;
							String setter = "set" + name;
							String clazz = JavaNames.fqn(
									JavaNames.name(model.get(rel.getTo())),
									context);
							iout.println("public " + IIdentifier.class.getName() + " "
									+ getter + " () throws "
									+ Exception.class.getName() + ";");
							iout.println("public "
									+ clazz
									+ " "
									+ getter
									+ " ("
									+ JavaNames.fqn(JavaNames.finder(model),
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
		final String clazz = JavaNames.nameImpl(entity);
		final String iclazz = JavaNames.fqn(JavaNames.name(entity), context);
		final String pkg = JavaNames.packageForImpl(context);
		FileUtils.java(context, pkg, clazz, new AbstractPrintable() {
			@Override
			protected void printWithException(PrintWriter out) throws Exception {
				PrintWriter iout = IndentedWriter.get(out);
				out.println("package " + pkg + ";");
				out.println();
				out.println("public abstract class " + clazz + " extends "
						+ AbstractRuntimeEntity.class.getName() + "<" + iclazz
						+ "> implements " + iclazz + " {");

				// Create attributes
				for (IField field : entity.items()) {
					String name = JavaNames.name(field);
					String clazz = context.getTypes().get(field.getType())
							.getProperties()
							.get(ITypesConstants.PROPERTY_JAVA_CLASS);
					iout.println("protected " + clazz + " _value_" + name + ";");
					iout.println("protected boolean _isSet_" + name + ";");
				}
				for (INormalizedManyToOneRelationship rel : entity
						.relationships().items()) {
					String name = JavaNames.javaIdentifier(rel.getDirect()
							.getName());
					String clazz = IIdentifier.class.getName();
					iout.println("protected " + clazz + " _value_" + name + ";");
					iout.println("protected boolean _isSet_" + name + ";");
				}


				// Implement accessors
				for (IField field : entity.items()) {
					String name = JavaNames.name(field);
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
					String name = JavaNames.javaIdentifier(rel.getDirect()
							.getName());
					String getter = "get" + name;
					String setter = "set" + name;
					String clazz = JavaNames.name(model.get(rel.getTo()));
					String fqn = JavaNames.fqn(clazz, context);
					iout.println("public " + IIdentifier.class.getName() + " "
							+ getter + " () throws "
							+ Exception.class.getName() + " { return _value_"
							+ name + "; }");
					iout.println("public " + fqn + " " + getter + " ("
							+ JavaNames.fqn(JavaNames.finder(model), context)
							+ " accessor) throws " + Exception.class.getName()
							+ " { return _value_" + name
							+ " != null ? accessor.get" + clazz + "(_value_"
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
				}

				out.println("}");
				iout.flush();
			}
		});

	}

	public void finder(final INormalizedModel model,
			final JavaCodeGeneratorContext context) throws Exception {
		final String clazz = JavaNames.finder(model);
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
							String clazz = JavaNames.name(ne);
							iout.println(JavaNames.fqn(clazz, context) + " get"
									+ clazz + "(" + IIdentifier.class.getName()
									+ " id) throws Exception;");
						}

						for (INormalizedEntity ne : model.items()) {
							String clazz = JavaNames.name(ne);
							iout.println(Iterable.class.getName() + "<"
									+ JavaNames.fqn(clazz, context) + "> all"
									+ clazz + "() throws Exception;");
						}

						// And the relationships
						for (INormalizedEntity ne : model.items()) {
							for (INormalizedManyToOneRelationship rel : ne
									.relationships().items()) {
								String clazzMany = JavaNames.name(ne);
								String clazzOne = JavaNames.name(model.get(rel
										.getTo()));
								String leading = Iterable.class.getName() + "<"
										+ JavaNames.fqn(clazzMany, context)
										+ "> findAll"
										+ JavaNames.name(rel, model) + "From"
										+ clazzOne + "(";
								iout.println(leading
										+ JavaNames.fqn(clazzOne, context)
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

}
