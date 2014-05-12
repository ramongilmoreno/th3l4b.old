package com.th3l4b.srm.codegen.java.sync;

import java.io.PrintWriter;

import com.th3l4b.common.text.AbstractPrintable;
import com.th3l4b.common.text.IndentedWriter;
import com.th3l4b.srm.base.IField;
import com.th3l4b.srm.base.normalized.INormalizedEntity;
import com.th3l4b.srm.base.normalized.INormalizedManyToOneRelationship;
import com.th3l4b.srm.base.normalized.INormalizedModel;
import com.th3l4b.srm.codegen.base.FileUtils;
import com.th3l4b.srm.codegen.base.names.BaseNames;
import com.th3l4b.srm.codegen.java.basic.JavaNames;
import com.th3l4b.srm.codegen.java.sync.runtime.AbstractEntityDiff;
import com.th3l4b.srm.codegen.java.sync.runtime.AbstractSRMContextUpdateFilter;
import com.th3l4b.srm.codegen.java.sync.runtime.DefaultDiffContext;

public class SyncCodeGenerator {

	public void diff(final INormalizedEntity entity,
			final INormalizedModel model, final SyncCodeGeneratorContext context)
			throws Exception {
		final BaseNames baseNames = context.getBaseNames();
		final JavaNames javaNames = context.getJavaNames();
		final SyncNames syncNames = context.getSyncNames();
		final String clazz = syncNames.diffName(entity);
		final String pkg = syncNames.packageForDiff(context);
		FileUtils.java(context, pkg, clazz, new AbstractPrintable() {
			@Override
			protected void printWithException(PrintWriter out) throws Exception {
				PrintWriter iout = IndentedWriter.get(out);
				PrintWriter iiout = IndentedWriter.get(iout);
				PrintWriter iiiout = IndentedWriter.get(iiout);
				PrintWriter iiiiout = IndentedWriter.get(iiiout);
				out.println("package " + pkg + ";");
				out.println();
				String iclazz = javaNames.fqn(javaNames.nameInterface(entity),
						context);
				out.println("public class " + clazz + " extends "
						+ AbstractEntityDiff.class.getName() + "<" + iclazz
						+ "> {");

				out.println();
				iout.println("protected boolean diffRest(" + iclazz + " from, "
						+ iclazz + " to, " + iclazz + " diff) throws "
						+ Exception.class.getName() + " {");
				iiout.println("boolean r = false;");
				for (IField field : entity.items()) {
					String name = baseNames.name(field);
					handleDiff(name, iiout);
				}
				for (INormalizedManyToOneRelationship rel : entity
						.relationships().items()) {
					String name = baseNames.nameOfDirect(rel, model);
					handleDiff(name, iiout);
				}
				iiout.println("return r;");
				iout.println("}");
				out.println("}");
				iiiiout.flush();
				iiiout.flush();
				iiout.flush();
				iout.flush();
			}

			public void handleDiff(String name, PrintWriter out) {
				String getter = "get" + name + "()";
				PrintWriter iout = IndentedWriter.get(out);
				PrintWriter iiout = IndentedWriter.get(iout);

				out.println("if (to.isSet" + name + "()) {");
				iout.println("if (!nullSafeEquals(from." + getter + ", to."
						+ getter + ")) {");
				iiout.println("r = true;");
				iiout.println("diff.set" + name + "(to." + getter + ");");
				iout.println("}");
				out.println("}");
				iiout.flush();
				iout.flush();
			}
		});
	}

	public void diffContext(final INormalizedModel model,
			final SyncCodeGeneratorContext context) throws Exception {
		final JavaNames javaNames = context.getJavaNames();
		final SyncNames syncNames = context.getSyncNames();
		final String clazz = syncNames.diffContextName(model);
		final String pkg = syncNames.packageForSync(context);
		FileUtils.java(context, pkg, clazz, new AbstractPrintable() {
			@Override
			protected void printWithException(PrintWriter out) throws Exception {
				PrintWriter iout = IndentedWriter.get(out);
				PrintWriter iiout = IndentedWriter.get(iout);
				out.println("package " + pkg + ";");
				out.println();
				out.println("public class " + clazz + " extends "
						+ DefaultDiffContext.class.getName() + " {");
				out.println();
				iout.println("public " + clazz + " () throws "
						+ Exception.class.getName() + " {");
				for (INormalizedEntity entity : model.items()) {
					iiout.println("put("
							+ javaNames.fqn(javaNames.nameInterface(entity),
									context)
							+ ".class, new "
							+ syncNames.fqnDiff(syncNames.diffName(entity),
									context) + "());");
				}
				iout.println("}");
				out.println("}");
				iiout.flush();
				iout.flush();
			}
		});
	}

	public void abstractUpdateFilter(final INormalizedModel model,
			final SyncCodeGeneratorContext context) throws Exception {
		final JavaNames javaNames = context.getJavaNames();
		final SyncNames syncNames = context.getSyncNames();
		final String clazz = syncNames.abstractUpdateFilterName(model);
		final String pkg = syncNames.packageForSync(context);
		FileUtils.java(context, pkg, clazz, new AbstractPrintable() {
			@Override
			protected void printWithException(PrintWriter out) throws Exception {
				PrintWriter iout = IndentedWriter.get(out);
				PrintWriter iiout = IndentedWriter.get(iout);
				out.println("package " + pkg + ";");
				out.println();
				String modelContext = javaNames.fqnBase(
						javaNames.context(model), context);
				out.println("public abstract class " + clazz + " extends "
						+ AbstractSRMContextUpdateFilter.class.getName() + "<"
						+ javaNames.fqnBase(javaNames.finder(model), context)
						+ "> implements " + modelContext + " {");
				out.println();
				iout.println("public " + clazz + " (" + modelContext
						+ " delegated) {");
				iiout.println("super(delegated);");
				iout.println("}");
				out.println("}");
				iiout.flush();
				iout.flush();
			}
		});

	}
}
