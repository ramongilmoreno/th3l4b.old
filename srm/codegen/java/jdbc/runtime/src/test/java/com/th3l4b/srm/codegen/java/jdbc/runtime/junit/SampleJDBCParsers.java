package com.th3l4b.srm.codegen.java.jdbc.runtime.junit;

import com.th3l4b.srm.codegen.java.jdbc.runtime.DefaultJDBCEntityParserContext;
import com.th3l4b.srm.codegen.java.jdbc.runtime.IJDBCIdentifierParser;
import com.th3l4b.srm.codegen.java.jdbc.runtime.IJDBCRuntimeTypesContext;
import com.th3l4b.srm.codegen.java.jdbc.runtime.IJDBCStatusParser;

public class SampleJDBCParsers extends DefaultJDBCEntityParserContext {
	public SampleJDBCParsers(IJDBCIdentifierParser ids,
			IJDBCStatusParser status, IJDBCRuntimeTypesContext types)
			throws Exception {
		put(ISampleEntity.class, new SampleEntityParser(ids, status, types));
	}
}
