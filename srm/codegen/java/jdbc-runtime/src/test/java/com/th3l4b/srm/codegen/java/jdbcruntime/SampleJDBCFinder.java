package com.th3l4b.srm.codegen.java.jdbcruntime;


public abstract class SampleJDBCFinder extends AbstractJDBCFinder {
	
	public SampleJDBCFinder() throws Exception {
		putEntityParser(new SampleEntityParser(), ISampleEntity.class);
	}


}
