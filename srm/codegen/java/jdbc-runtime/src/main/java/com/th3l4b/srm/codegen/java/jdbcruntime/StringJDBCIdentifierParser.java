package com.th3l4b.srm.codegen.java.jdbcruntime;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.th3l4b.srm.codegen.java.basicruntime.StringIdentifier;
import com.th3l4b.srm.runtime.IIdentifier;

public class StringJDBCIdentifierParser implements IJDBCIdentifierParser {

	@Override
	public IIdentifier parse(Integer column, ResultSet result) throws Exception {
		return new StringIdentifier(result.getString(column));
	}

	@Override
	public void set(IIdentifier identifier, Integer column,
			PreparedStatement statement) throws Exception {
		statement.setString(column, ((StringIdentifier) identifier).getValue());

	}

}
