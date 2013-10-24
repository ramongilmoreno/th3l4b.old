package com.th3l4b.srm.codegen.java.jdbc;

import com.th3l4b.srm.base.IField;
import com.th3l4b.srm.base.normalized.INormalizedEntity;
import com.th3l4b.srm.base.normalized.INormalizedManyToOneRelationship;
import com.th3l4b.srm.base.normalized.INormalizedModel;
import com.th3l4b.srm.codegen.java.basic.JavaCodeGeneratorContext;
import com.th3l4b.srm.codegen.java.basic.JavaNames;

public class JDBCNames extends JavaNames {

	private static final String PREFIX = JDBCNames.class.getPackage().getName();
	public static final String TABLE = PREFIX + ".table";
	public static final String COLUMN = PREFIX + ".column";
	public static final String ID = PREFIX + ".id";
	public static final String STATUS = PREFIX + ".status";

	public String packageForJDBC(JavaCodeGeneratorContext context) {
		return context.getPackage() + ".jdbc";
	}

	public String fqnJDBC(String clazz, JavaCodeGeneratorContext context) {
		return packageForJDBC(context) + "." + clazz;
	}

	public String parserJDBC(INormalizedEntity entity) throws Exception {
		return valueOrProperty(javaIdentifier(entity.getName()) + "Parser",
				entity);
	}

	public String table(INormalizedEntity entity) throws Exception {
		return valueOrProperty(javaIdentifier(entity.getName()), TABLE, entity);
	}

	public String id(INormalizedEntity entity) throws Exception {
		return valueOrProperty("_Id", ID, entity);
	}

	public String status(INormalizedEntity entity) throws Exception {
		return valueOrProperty("_Status", STATUS, entity);
	}

	public String column(IField field) throws Exception {
		return valueOrProperty(name(field), COLUMN, field);
	}

	public String column(INormalizedManyToOneRelationship relationship,
			INormalizedModel model) throws Exception {
		return valueOrProperty(nameOfDirect(relationship, model), COLUMN,
				relationship);
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
