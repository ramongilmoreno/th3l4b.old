package com.th3l4b.srm.codegen.java.jdbcruntime;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.th3l4b.srm.runtime.IIdentifier;

public interface IJDBCIdentifierParser {
	IIdentifier parse(String column, ResultSet result) throws Exception;

	void set(IIdentifier identifier, int column, PreparedStatement statement)
			throws Exception;
}
