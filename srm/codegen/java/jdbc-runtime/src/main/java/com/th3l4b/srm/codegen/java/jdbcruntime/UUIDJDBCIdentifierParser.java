package com.th3l4b.srm.codegen.java.jdbcruntime;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.th3l4b.srm.runtime.IIdentifier;

public class UUIDJDBCIdentifierParser implements IJDBCIdentifierParser {

	@Override
	public IIdentifier parse(Integer column, ResultSet result) throws Exception {
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
	public void set(IIdentifier identifier, Integer column,
			PreparedStatement statement) throws Exception {
		throw new UnsupportedOperationException("Not yet implemented");
	}

}
