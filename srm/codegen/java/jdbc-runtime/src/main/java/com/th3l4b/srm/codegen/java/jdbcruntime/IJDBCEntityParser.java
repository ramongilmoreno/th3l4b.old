package com.th3l4b.srm.codegen.java.jdbcruntime;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public interface IJDBCEntityParser<R> {

	R create(Class<R> clazz);

	String table() throws Exception;

	String idColumn() throws Exception;

	String statusColumn() throws Exception;

	String[] fieldsColumns() throws Exception;

	void parse(R entity, int index, ResultSet result) throws Exception;

	void set(R entity, int index, PreparedStatement statement) throws Exception;
}
