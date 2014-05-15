package com.th3l4b.srm.codegen.java.jdbc.codegen;

import com.th3l4b.srm.base.normalized.INormalizedEntity;
import com.th3l4b.srm.base.normalized.INormalizedModel;
import com.th3l4b.srm.codegen.base.names.BaseNames;
import com.th3l4b.srm.codegen.java.basic.JavaCodeGeneratorContext;

public class JDBCNames {

	private BaseNames _baseNames;

	public JDBCNames(BaseNames baseNames) {
		_baseNames = baseNames;
	}

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
		return _baseNames.name(entity) + "Parser";
	}

	public String finderJDBC(INormalizedModel model) throws Exception {
		return "Abstract" + _baseNames.name(model) + "JDBCFinder";
	}

	public String abstractJDBCContext(INormalizedModel model) throws Exception {
		return "Abstract" + _baseNames.name(model) + "JDBCContext";
	}

	public String parserContext(INormalizedModel model) throws Exception {
		return _baseNames.name(model) + "JDBCParserContext";
	}

}
