package com.th3l4b.srm.codegen.java.jdbcruntime;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.th3l4b.srm.runtime.EntityStatus;

public interface IJDBCStatusParser {
	EntityStatus parse(int column, ResultSet result) throws Exception;

	void set(EntityStatus status, int column, PreparedStatement statement)
			throws Exception;
}
