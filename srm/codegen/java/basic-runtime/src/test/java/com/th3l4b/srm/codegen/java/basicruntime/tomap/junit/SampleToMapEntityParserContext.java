package com.th3l4b.srm.codegen.java.basicruntime.tomap.junit;

import com.th3l4b.srm.codegen.java.basicruntime.junit.IEntityA;
import com.th3l4b.srm.codegen.java.basicruntime.tomap.IToMapIdentifierParser;
import com.th3l4b.srm.codegen.java.basicruntime.tomap.IToMapStatusParser;
import com.th3l4b.srm.runtime.DefaultToMapEntityParserContext;
import com.th3l4b.types.runtime.IJavaRuntimeTypesContext;

public abstract class SampleToMapEntityParserContext extends
		DefaultToMapEntityParserContext {

	public SampleToMapEntityParserContext(
			IToMapIdentifierParser identifierParser,
			IToMapStatusParser statusParser, IJavaRuntimeTypesContext java)
			throws Exception {
		put(IEntityA.class, new SampleEntityAToMapEntityParser(
				identifierParser, statusParser, java));
	}

}
