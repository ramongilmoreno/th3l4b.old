package com.th3l4b.srm.codegen.ant;

import java.io.File;
import java.io.PrintWriter;

import com.th3l4b.common.text.IPrintable;
import com.th3l4b.common.text.TextUtils;
import com.th3l4b.srm.base.normalized.INormalizedEntity;
import com.th3l4b.srm.base.normalized.INormalizedModel;
import com.th3l4b.srm.base.original.IModel;
import com.th3l4b.srm.codegen.base.CodeGeneratorContext;
import com.th3l4b.srm.codegen.base.FileUtils;
import com.th3l4b.srm.codegen.base.names.BaseNames;
import com.th3l4b.srm.codegen.database.SQLCodeGenerator;
import com.th3l4b.srm.codegen.database.SQLCodeGeneratorContext;
import com.th3l4b.srm.codegen.java.basic.JavaCodeGenerator;
import com.th3l4b.srm.codegen.java.basic.JavaCodeGeneratorContext;
import com.th3l4b.srm.codegen.java.basic.inmemory.JavaInMemoryCodeGenerator;
import com.th3l4b.srm.codegen.java.basic.inmemory.JavaInMemoryCodeGeneratorContext;
import com.th3l4b.srm.codegen.java.basic.tomap.ToMapCodeGenerator;
import com.th3l4b.srm.codegen.java.basic.tomap.ToMapCodeGeneratorContext;
import com.th3l4b.srm.codegen.java.jdbc.codegen.JDBCCodeGenerator;
import com.th3l4b.srm.codegen.java.jdbc.codegen.JDBCCodeGeneratorContext;
import com.th3l4b.srm.codegen.java.sync.SyncCodeGenerator;
import com.th3l4b.srm.codegen.java.sync.SyncCodeGeneratorContext;
import com.th3l4b.srm.codegen.java.web.rest.codegen.RESTCodeGenerator;
import com.th3l4b.srm.codegen.java.web.rest.codegen.RESTCodeGeneratorContext;

public class AllSRMTask extends AbstractSRMTask {

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
		BaseNames baseNames = context.getBaseNames();
		JavaCodeGeneratorContext javaContext = new JavaCodeGeneratorContext(
				baseNames);
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

		// ToMap
		ToMapCodeGenerator toMapCodegen = new ToMapCodeGenerator();
		ToMapCodeGeneratorContext toMapContext = new ToMapCodeGeneratorContext(
				baseNames);
		javaContext.copyTo(toMapContext);
		startProduct("Abstract to map context", toMapContext);
		toMapCodegen.toMapParserContext(normalized, toMapContext);
		endProduct(toMapContext);
		startProduct("To map entities parsers", toMapContext);
		toMapCodegen.toMapParserContext(normalized, toMapContext);
		endProduct(toMapContext);
		for (INormalizedEntity entity : normalized.items()) {
			startProduct("To map parser for entity: " + entity.getName(),
					toMapContext);
			toMapCodegen.entityParser(entity, normalized, toMapContext);
			endProduct(toMapContext);
		}

		// REST
		RESTCodeGenerator restCodegen = new RESTCodeGenerator();
		RESTCodeGeneratorContext restContext = new RESTCodeGeneratorContext(
				baseNames);
		javaContext.copyTo(restContext);
		startProduct("REST finder", restContext);
		restCodegen.finder(normalized, restContext);
		endProduct(restContext);

		// In memory
		JavaInMemoryCodeGenerator inMemoryCodegen = new JavaInMemoryCodeGenerator();
		JavaInMemoryCodeGeneratorContext inMemoryContext = new JavaInMemoryCodeGeneratorContext(
				baseNames);
		javaContext.copyTo(inMemoryContext);
		startProduct("Abstract in memory finder", inMemoryContext);
		inMemoryCodegen.finderInMemory(normalized, inMemoryContext);
		endProduct(inMemoryContext);
		startProduct("Abstract in memory context", inMemoryContext);
		inMemoryCodegen.abstractInMemoryContext(normalized, inMemoryContext);
		endProduct(inMemoryContext);

		// Sync
		SyncCodeGenerator syncCodegen = new SyncCodeGenerator();
		SyncCodeGeneratorContext syncContext = new SyncCodeGeneratorContext(
				baseNames);
		javaContext.copyTo(syncContext);
		for (INormalizedEntity entity : normalized.items()) {
			startProduct("Diff for entity: " + entity.getName(), syncContext);
			syncCodegen.diff(entity, normalized, syncContext);
			endProduct(syncContext);

		}
		startProduct("Diff context", syncContext);
		syncCodegen.diffContext(normalized, syncContext);
		endProduct(syncContext);
		startProduct("Diff update filter", syncContext);
		syncCodegen.abstractUpdateFilter(normalized, syncContext);
		endProduct(syncContext);

		// JDBC
		JDBCCodeGenerator jdbcCodegen = new JDBCCodeGenerator();
		JDBCCodeGeneratorContext jdbcContext = new JDBCCodeGeneratorContext(
				baseNames);
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
		SQLCodeGeneratorContext sqlContext = new SQLCodeGeneratorContext(
				baseNames);
		context.copyTo(sqlContext);
		startProduct("SQL files", sqlContext);
		sqlCodegen.sql(normalized, sqlContext);
		endProduct(sqlContext);
	}

}
