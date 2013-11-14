package com.th3l4b.srm.codegen.java.jdbcruntime;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.th3l4b.srm.runtime.IIdentifier;
import com.th3l4b.srm.runtime.IIdentifierParser;

public interface IJDBCIdentifierParser extends
		IIdentifierParser<ResultSet, PreparedStatement, Integer, Integer> {
	IIdentifier parse(Integer column, ResultSet result) throws Exception;

	void set(IIdentifier identifier, Integer column, PreparedStatement statement)
			throws Exception;
}
