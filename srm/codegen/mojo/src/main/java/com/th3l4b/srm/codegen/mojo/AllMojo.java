package com.th3l4b.srm.codegen.mojo;

import java.io.File;
import java.io.PrintWriter;

import com.th3l4b.common.text.IPrintable;
import com.th3l4b.common.text.TextUtils;
import com.th3l4b.srm.base.normalized.INormalizedEntity;
import com.th3l4b.srm.base.normalized.INormalizedModel;
import com.th3l4b.srm.base.original.IModel;
import com.th3l4b.srm.codegen.base.CodeGeneratorContext;
import com.th3l4b.srm.codegen.base.FileUtils;
import com.th3l4b.srm.codegen.java.basic.JavaCodeGenerator;
import com.th3l4b.srm.codegen.java.basic.JavaCodeGeneratorContext;
import com.th3l4b.srm.codegen.java.basic.inmemory.JavaInMemoryCodeGenerator;
import com.th3l4b.srm.codegen.java.basic.inmemory.JavaInMemoryCodeGeneratorContext;
import com.th3l4b.srm.codegen.java.jdbc.JDBCCodeGenerator;
import com.th3l4b.srm.codegen.java.jdbc.JDBCCodeGeneratorContext;
import com.th3l4b.srm.codegen.java.jdbc.SQLCodeGenerator;
import com.th3l4b.srm.codegen.java.jdbc.SQLCodeGeneratorContext;

/**
 * Generates all sources
 * 
 * @goal all
 */
public class AllMojo extends SRMAbstractMojo {

	@Override
	protected void execute(final IModel model,
			final INormalizedModel normalized, CodeGeneratorContext context)
			throws Exception {
		// Produce debug files
		FileUtils.overwriteIfOlder(context, "model.txt", new IPrintable() {
			@Override
			public void print(PrintWriter out) {
				TextUtils.print(model, out);
			}

		});
		FileUtils.overwriteIfOlder(context, "model-normalized.txt",
				new IPrintable() {
					@Override
					public void print(PrintWriter out) {
						TextUtils.print(normalized, out);
					}

				});
		// Produce code.
		JavaCodeGeneratorContext javaContext = new JavaCodeGeneratorContext();
		context.copyTo(javaContext);
		javaContext.setOutput(new File(context.getOutput(), "java"));
		javaContext.setPackage(getPackage());
		JavaCodeGenerator javaCodegen = new JavaCodeGenerator();
		startProduct("Entities", javaContext);
		javaCodegen.modelEntity(normalized, javaContext);
		for (INormalizedEntity ne : normalized.items()) {
			javaCodegen.entity(ne, normalized, javaContext);
			javaCodegen.entityImpl(ne, normalized, javaContext);
		}
		endProduct(javaContext);
		startProduct("Finder", javaContext);
		javaCodegen.finder(normalized, javaContext);
		endProduct(javaContext);
		startProduct("Model utils", javaContext);
		javaCodegen.modelUtils(normalized, javaContext);
		endProduct(javaContext);
		startProduct("Context interface", javaContext);
		javaCodegen.context(normalized, javaContext);
		endProduct(javaContext);

		// In memory
		JavaInMemoryCodeGenerator inMemoryCodegen = new JavaInMemoryCodeGenerator();
		JavaInMemoryCodeGeneratorContext inMemoryContext = new JavaInMemoryCodeGeneratorContext();
		javaContext.copyTo(inMemoryContext);
		startProduct("Abstract in memory finder", inMemoryContext);
		inMemoryCodegen.finderInMemory(normalized, inMemoryContext);
		endProduct(inMemoryContext);
		startProduct("Abstract in memory context", inMemoryContext);
		inMemoryCodegen.abstractInMemoryContext(normalized, inMemoryContext);
		endProduct(inMemoryContext);

		// JDBC
		JDBCCodeGenerator jdbcCodegen = new JDBCCodeGenerator();
		JDBCCodeGeneratorContext jdbcContext = new JDBCCodeGeneratorContext();
		javaContext.copyTo(jdbcContext);
		startProduct("Abstract JDBC finder", jdbcContext);
		jdbcCodegen.finder(normalized, jdbcContext);
		endProduct(jdbcContext);
		startProduct("Abstract JDBC context", jdbcContext);
		jdbcCodegen.context(normalized, jdbcContext);
		endProduct(jdbcContext);
		startProduct("JDBC entities parsers", jdbcContext);
		jdbcCodegen.parsers(normalized, jdbcContext);
		endProduct(jdbcContext);
		for (INormalizedEntity entity : normalized.items()) {
			startProduct("JDBC parser for entity: " + entity.getName(),
					jdbcContext);
			jdbcCodegen.entityParser(entity, normalized, jdbcContext);
			endProduct(jdbcContext);

		}

		// SQL code generator
		SQLCodeGenerator sqlCodegen = new SQLCodeGenerator();
		SQLCodeGeneratorContext sqlContext = new SQLCodeGeneratorContext();
		javaContext.copyTo(sqlContext);
		startProduct("SQL files", sqlContext);
		sqlCodegen.sql(normalized, sqlContext);
		endProduct(sqlContext);

	}
}
