package com.th3l4b.srm.codegen.java.mongo.mojo;

import java.io.File;

import org.apache.maven.project.MavenProject;

import com.th3l4b.srm.base.normalized.INormalizedEntity;
import com.th3l4b.srm.base.normalized.INormalizedModel;
import com.th3l4b.srm.base.original.IModel;
import com.th3l4b.srm.codegen.base.CodeGeneratorContext;
import com.th3l4b.srm.codegen.java.basic.JavaCodeGeneratorContext;
import com.th3l4b.srm.codegen.java.mongo.codegen.MongoCodeGenerator;
import com.th3l4b.srm.codegen.java.mongo.codegen.MongoCodeGeneratorContext;

/**
 * Generates all sources
 * 
 * @goal mongo
 */
public class MongoMojo extends SRMAbstractMojo3 {

	// http://www.maestrodev.com/better-builds-with-maven/developing-custom-maven-plugins/advanced-mojo-development/
	/**
	 * @parameter default-value="${project}"
	 * @required
	 */
	private MavenProject _project;

	@Override
	protected void execute(IModel model, INormalizedModel normalized,
			CodeGeneratorContext context) throws Exception {

		// Prepare Java context
		JavaCodeGeneratorContext javaContext = new JavaCodeGeneratorContext(
				context.getBaseNames());
		context.copyTo(javaContext);
		javaContext.setOutput(new File(context.getOutput(), "java"));
		javaContext.setPackage(getPackage());

		// Mongo
		MongoCodeGenerator mongoCodegen = new MongoCodeGenerator();
		MongoCodeGeneratorContext mongoContext = new MongoCodeGeneratorContext(
				context.getBaseNames());
		javaContext.copyTo(mongoContext);
		startProduct("Abstract Mongo finder", mongoContext);
		mongoCodegen.finder(normalized, mongoContext);
		endProduct(mongoContext);
		startProduct("Abstract Mongo context", mongoContext);
		mongoCodegen.context(normalized, mongoContext);
		endProduct(mongoContext);
		startProduct("Mongo entities parsers", mongoContext);
		mongoCodegen.parsers(normalized, mongoContext);
		endProduct(mongoContext);
		for (INormalizedEntity entity : normalized.items()) {
			startProduct("Mongo parser for entity: " + entity.getName(),
					mongoContext);
			mongoCodegen.entityParser(entity, normalized, mongoContext);
			endProduct(mongoContext);

		}

		// Compile the generated Java output as part of the project build
		// http://stackoverflow.com/questions/11931652/dynamically-adding-mojo-generated-code-to-source-path
		_project.addCompileSourceRoot(javaContext.getOutput()
				.getCanonicalPath());

	}

}
