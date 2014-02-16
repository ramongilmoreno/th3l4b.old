package com.th3l4b.srm.codegen.java.jdbcruntime;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.th3l4b.srm.runtime.IDatabaseParser;

public interface IJDBCEntityParser<R> extends
		IDatabaseParser<R, ResultSet, PreparedStatement, Integer, Integer> {
	String[] allColumns() throws Exception;
}
