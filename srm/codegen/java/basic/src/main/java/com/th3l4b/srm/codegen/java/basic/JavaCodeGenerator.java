package com.th3l4b.srm.codegen.java.basic;

import java.io.PrintWriter;

import com.th3l4b.common.text.AbstractPrintable;
import com.th3l4b.common.text.IndentedWriter;
import com.th3l4b.srm.base.IField;
import com.th3l4b.srm.base.normalized.INormalizedEntity;
import com.th3l4b.srm.base.normalized.INormalizedManyToOneRelationship;
import com.th3l4b.srm.base.normalized.INormalizedModel;
import com.th3l4b.srm.codegen.base.FileUtils;
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
								+ IRuntimeEntity.class.getName() + " {");

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
							String n = JavaNames.javaIdentifier(rel.getDirect()
									.getName());
							String getter = "get" + n;
							String setter = "set" + n;
							String clazz = JavaNames.fqn(
									JavaNames.name(model.get(rel.getTo())),
									context);
							iout.println("" + IIdentifier.class.getName() + " "
									+ getter + " () throws "
									+ Exception.class.getName() + ";");
							iout.println(""
									+ clazz
									+ " "
									+ getter
									+ " ("
									+ JavaNames.fqn(JavaNames.accessor(model),
											context) + " accessor) throws "
									+ Exception.class.getName() + ";");
							iout.println("void " + setter + " ("
									+ IIdentifier.class.getName()
									+ " arg) throws "
									+ Exception.class.getName() + ";");
							iout.println("void " + setter + " (" + clazz
									+ " arg) throws "
									+ Exception.class.getName() + ";");

						}

						out.println("}");
						iout.flush();
					}
				});

	}

	public void accessor(final INormalizedModel model,
			final JavaCodeGeneratorContext context) throws Exception {
		final String clazz = JavaNames.accessor(model);
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
