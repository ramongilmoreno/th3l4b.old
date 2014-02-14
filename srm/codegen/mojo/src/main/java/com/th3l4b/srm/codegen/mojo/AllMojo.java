package com.th3l4b.srm.codegen.mojo;

import java.io.File;
import java.io.PrintWriter;
import java.util.Collections;

import org.apache.maven.model.Resource;
import org.apache.maven.project.MavenProject;

import com.th3l4b.common.text.IPrintable;
import com.th3l4b.common.text.TextUtils;
import com.th3l4b.srm.base.normalized.INormalizedEntity;
import com.th3l4b.srm.base.normalized.INormalizedModel;
import com.th3l4b.srm.base.original.IModel;
import com.th3l4b.srm.codegen.base.CodeGeneratorContext;
import com.th3l4b.srm.codegen.base.FileUtils;
import com.th3l4b.srm.codegen.database.SQLCodeGenerator;
import com.th3l4b.srm.codegen.database.SQLCodeGeneratorContext;
import com.th3l4b.srm.codegen.java.basic.JavaCodeGenerator;
import com.th3l4b.srm.codegen.java.basic.JavaCodeGeneratorContext;
import com.th3l4b.srm.codegen.java.basic.inmemory.JavaInMemoryCodeGenerator;
import com.th3l4b.srm.codegen.java.basic.inmemory.JavaInMemoryCodeGeneratorContext;
import com.th3l4b.srm.codegen.java.jdbc.JDBCCodeGenerator;
import com.th3l4b.srm.codegen.java.jdbc.JDBCCodeGeneratorContext;

/**
 * Generates all sources
 * 
 * @goal all
 */
public class AllMojo extends SRMAbstractMojo {

	// http://www.maestrodev.com/better-builds-with-maven/developing-custom-maven-plugins/advanced-mojo-development/
	/**
	 * Project instance, needed for attaching the buildinfo file. Used to add
	 * new source directory to the build.
	 * 
	 * @parameter default-value="${project}"
	 * @required
	 */
	private MavenProject _project;

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
		context.copyTo(sqlContext);
		startProduct("SQL files", sqlContext);
		sqlCodegen.sql(normalized, sqlContext);
		endProduct(sqlContext);

		// Include .srm file as resource
		// http://www.maestrodev.com/better-builds-with-maven/developing-custom-maven-plugins/advanced-mojo-development/
		// https://www.mail-archive.com/users@maven.apache.org/msg102603.html
		Resource resource = new Resource();
		resource.setDirectory(getInput().getParentFile().getCanonicalPath());
		resource.setIncludes(Collections.singletonList(getInput().getName()));
		resource.setTargetPath(new File(javaContext.getOutput(), FileUtils
				.asDirectories(javaContext.getPackage())).getCanonicalPath());
		_project.addResource(resource);

		// Include the generated files as sources
		Resource resource2 = new Resource();
		resource2.setDirectory(javaContext.getOutput().getCanonicalPath());
		resource2.setExcludes(Collections.singletonList("**/*.java"));
		_project.addResource(resource2);

		// Compile the generated Java output as part of the project build
		// http://stackoverflow.com/questions/11931652/dynamically-adding-mojo-generated-code-to-source-path
		_project.addCompileSourceRoot(javaContext.getOutput().getCanonicalPath());
	}
}
