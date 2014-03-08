package com.th3l4b.srm.codegen.java.jdbc;

import com.th3l4b.common.propertied.IPropertied;
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

	protected String valueOrProperty(String value, IPropertied propertied)
			throws Exception {
		return _baseNames.valueOrProperty(value, JDBCNames.class.getName()
				+ ".identifier", propertied);
	}

	public String parserJDBC(INormalizedEntity entity) throws Exception {
		return _baseNames.identifier(entity.getName()) + "Parser";
	}

	public String finderJDBC(INormalizedModel model) throws Exception {
		return "Abstract" + _baseNames.identifier(model.getName())
				+ "JDBCFinder";
	}

	public String abstractJDBCContext(INormalizedModel model) throws Exception {
		return "Abstract" + _baseNames.identifier(model.getName())
				+ "JDBCContext";
	}

	public String parsersJDBC(INormalizedModel model) throws Exception {
		return _baseNames.identifier(model.getName()) + "JDBCParsers";
	}

}
