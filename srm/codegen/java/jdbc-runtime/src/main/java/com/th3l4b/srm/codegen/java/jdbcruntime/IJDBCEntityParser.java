package com.th3l4b.srm.codegen.java.jdbcruntime;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public interface IJDBCEntityParser<R> {
	String table () throws Exception;
	
	String idColumn (String table) throws Exception;
	
	Iterable<String> columns(String table) throws Exception;

	R parse(String table, ResultSet result, IJDBCIdentifierParser ids)
			throws Exception;

	void set(R entity, int index, PreparedStatement statement,
			IJDBCIdentifierParser ids) throws Exception;
}
