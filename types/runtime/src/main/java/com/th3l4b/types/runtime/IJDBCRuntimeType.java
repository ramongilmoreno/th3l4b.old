package com.th3l4b.types.runtime;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public interface IJDBCRuntimeType<T> {
	Class<T> clazz ();
	T fromResultSet (int index, ResultSet result) throws Exception;
	<T2> T2 fromResultSet (int index, ResultSet result, Class<T2> clazz) throws Exception;
	void toPreparedStatement (T value, int index, PreparedStatement statement) throws Exception;
	<T2> T2 toPreparedStatement (T2 value, int index, PreparedStatement statement, Class<T2> clazz) throws Exception;
}
