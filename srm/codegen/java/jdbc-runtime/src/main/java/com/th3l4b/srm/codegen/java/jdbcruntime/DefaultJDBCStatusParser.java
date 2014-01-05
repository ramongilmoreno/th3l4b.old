package com.th3l4b.srm.codegen.java.jdbcruntime;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.th3l4b.srm.runtime.EntityStatus;

public class DefaultJDBCStatusParser implements IJDBCStatusParser {

	@Override
	public EntityStatus parse(int column, ResultSet result) throws Exception {
		return EntityStatus.fromInitial(result.getString(column), EntityStatus.Unknown);
	}

	@Override
	public void set(EntityStatus status, int column, PreparedStatement statement)
			throws Exception {
		statement.setString(column, status.initial());
	}

}
