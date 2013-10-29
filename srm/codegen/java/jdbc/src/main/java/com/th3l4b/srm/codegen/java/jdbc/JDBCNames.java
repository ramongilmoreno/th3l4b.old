package com.th3l4b.srm.codegen.java.jdbc;

import com.th3l4b.srm.base.normalized.INormalizedEntity;
import com.th3l4b.srm.base.normalized.INormalizedModel;
import com.th3l4b.srm.codegen.java.basic.JavaCodeGeneratorContext;
import com.th3l4b.srm.codegen.java.basic.JavaNames;

public class JDBCNames extends JavaNames {

	public String packageForJDBC(JavaCodeGeneratorContext context) {
		return context.getPackage() + ".jdbc";
	}

	public String fqnJDBC(String clazz, JavaCodeGeneratorContext context) {
		return packageForJDBC(context) + "." + clazz;
	}

	public String packageForJDBCParsers(JavaCodeGeneratorContext context) {
		return packageForJDBC(context) + ".parsers";
	}

	public String fqnJDBCParsers(String clazz, JavaCodeGeneratorContext context) {
		return packageForJDBCParsers(context) + "." + clazz;
	}
	
	
	public String parserJDBC(INormalizedEntity entity) throws Exception {
		return valueOrProperty(javaIdentifier(entity.getName()) + "Parser",
				entity);
	}


	public String finderJDBC(INormalizedModel model) throws Exception {
		return "Abstract"
				+ valueOrProperty(javaIdentifier(model.getName())
						+ "JDBCFinder", model);
	}

	public String abstractJDBCContext(INormalizedModel model) throws Exception {
		return "Abstract"
				+ valueOrProperty(javaIdentifier(model.getName())
						+ "JDBCContext", model);
	}

	public String parsersJDBC(INormalizedModel model) throws Exception {
		return valueOrProperty(javaIdentifier(model.getName()) + "JDBCParsers",
				model);
	}

}
