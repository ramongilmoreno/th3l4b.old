package com.th3l4b.srm.codegen.java.jdbcruntime;

import java.sql.Connection;

import com.th3l4b.srm.codegen.java.basicruntime.AbstractSRMContext;

public abstract class AbstractJDBCSRMContext<FINDER> extends
AbstractSRMContext<FINDER> {
	
	protected abstract Connection getConnection () throws Exception;
	
}
