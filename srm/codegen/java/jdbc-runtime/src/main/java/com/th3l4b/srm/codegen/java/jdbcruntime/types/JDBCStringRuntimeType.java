package com.th3l4b.srm.codegen.java.jdbcruntime.types;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.th3l4b.srm.codegen.java.jdbcruntime.IJDBCRuntimeType;

public class JDBCStringRuntimeType implements IJDBCRuntimeType<String> {

	@Override
	public String parse(Integer arg, ResultSet result) throws Exception {
		return result.getString(arg.intValue());
	}

	@Override
	public void set(String value, Integer arg, PreparedStatement statement)
			throws Exception {
		statement.setString(arg.intValue(), value);
	}

}
