package com.th3l4b.srm.codegen.java.jdbcruntime;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.th3l4b.srm.runtime.IBaseParser;

public interface IJDBCRuntimeType<T> extends
		IBaseParser<T, ResultSet, PreparedStatement, Integer, Integer> {
}
