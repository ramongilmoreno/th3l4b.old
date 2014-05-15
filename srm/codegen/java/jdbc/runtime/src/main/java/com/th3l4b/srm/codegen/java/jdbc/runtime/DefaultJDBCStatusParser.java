package com.th3l4b.srm.codegen.java.jdbc.runtime;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.th3l4b.srm.runtime.EntityStatus;

public class DefaultJDBCStatusParser implements IJDBCStatusParser {

	@Override
	public boolean hasValue(Integer arg, ResultSet result) throws Exception {
		return true;
	}

	@Override
	public EntityStatus parse(Integer arg, ResultSet result) throws Exception {
		return EntityStatus.fromInitial(result.getString(arg.intValue()));
	}

	@Override
	public void set(EntityStatus value, Integer arg, PreparedStatement statement)
			throws Exception {
		statement.setString(arg.intValue(), JDBCUtils.fromEntityStatus(value));
	}

}
