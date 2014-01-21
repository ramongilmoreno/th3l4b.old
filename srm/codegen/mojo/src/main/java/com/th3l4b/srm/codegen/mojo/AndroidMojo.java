package com.th3l4b.srm.codegen.mojo;

import java.io.File;

import com.th3l4b.srm.base.normalized.INormalizedEntity;
import com.th3l4b.srm.base.normalized.INormalizedModel;
import com.th3l4b.srm.base.original.IModel;
import com.th3l4b.srm.codegen.base.CodeGeneratorContext;
import com.th3l4b.srm.codegen.java.android.sqlite.AndroidSQLiteCodeGenerator;
import com.th3l4b.srm.codegen.java.android.sqlite.AndroidSQLiteCodeGeneratorContext;
import com.th3l4b.srm.codegen.java.basic.JavaCodeGeneratorContext;

/**
 * Generates all sources
 * 
 * @goal android
 */
public class AndroidMojo extends SRMAbstractMojo {

	@Override
	protected void execute(IModel model, INormalizedModel normalized,
			CodeGeneratorContext context) throws Exception {

		// Prepare Java context
		JavaCodeGeneratorContext javaContext = new JavaCodeGeneratorContext();
		context.copyTo(javaContext);
		javaContext.setOutput(new File(context.getOutput(), "java"));
		javaContext.setPackage(getPackage());

		// Android SQLite
		AndroidSQLiteCodeGenerator androidSQLiteCodegen = new AndroidSQLiteCodeGenerator();
		AndroidSQLiteCodeGeneratorContext androidSQLiteContext = new AndroidSQLiteCodeGeneratorContext();
		javaContext.copyTo(androidSQLiteContext);
		startProduct("Android SQLite files", androidSQLiteContext);
		androidSQLiteCodegen.helper(normalized, androidSQLiteContext);
		androidSQLiteCodegen.finder(normalized, androidSQLiteContext);
		androidSQLiteCodegen.context(normalized, androidSQLiteContext);
		androidSQLiteCodegen.parserContext(normalized, androidSQLiteContext);
		endProduct(androidSQLiteContext);

		for (INormalizedEntity entity : normalized.items()) {
			startProduct("Entity parser " + entity.getName(),
					androidSQLiteContext);
			androidSQLiteCodegen.entityParser(entity, normalized,
					androidSQLiteContext);
			endProduct(androidSQLiteContext);
		}
	}

}
