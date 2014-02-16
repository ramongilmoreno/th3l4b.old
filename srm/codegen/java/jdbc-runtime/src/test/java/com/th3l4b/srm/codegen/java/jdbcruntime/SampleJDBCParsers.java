package com.th3l4b.srm.codegen.java.jdbcruntime;

public class SampleJDBCParsers extends DefaultJDBCEntityParserContext {
	public SampleJDBCParsers(IJDBCIdentifierParser ids,
			IJDBCStatusParser status, IJDBCRuntimeTypesContext types)
			throws Exception {
		put(ISampleEntity.class, new SampleEntityParser(ids, status, types));
	}
}
