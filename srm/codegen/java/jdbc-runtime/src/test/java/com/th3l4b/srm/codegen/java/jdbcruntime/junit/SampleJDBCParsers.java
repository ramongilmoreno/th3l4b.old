package com.th3l4b.srm.codegen.java.jdbcruntime.junit;

import com.th3l4b.srm.codegen.java.jdbcruntime.DefaultJDBCEntityParserContext;
import com.th3l4b.srm.codegen.java.jdbcruntime.IJDBCIdentifierParser;
import com.th3l4b.srm.codegen.java.jdbcruntime.IJDBCRuntimeTypesContext;
import com.th3l4b.srm.codegen.java.jdbcruntime.IJDBCStatusParser;

public class SampleJDBCParsers extends DefaultJDBCEntityParserContext {
	public SampleJDBCParsers(IJDBCIdentifierParser ids,
			IJDBCStatusParser status, IJDBCRuntimeTypesContext types)
			throws Exception {
		put(ISampleEntity.class, new SampleEntityParser(ids, status, types));
	}
}
