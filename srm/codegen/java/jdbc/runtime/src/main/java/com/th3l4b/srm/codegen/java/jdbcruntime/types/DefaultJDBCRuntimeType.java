package com.th3l4b.srm.codegen.java.jdbcruntime.types;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.th3l4b.srm.codegen.java.jdbc.runtime.IJDBCRuntimeType;

public class DefaultJDBCRuntimeType<T> implements IJDBCRuntimeType<T> {

	private int _sqlType;

	public DefaultJDBCRuntimeType(int sqlType) {
		_sqlType = sqlType;
	}

	protected T filterNotNullBeforeGet(T value) throws Exception {
		return value;
	}

	@SuppressWarnings("unchecked")
	protected T parseNotNull(Object value) throws Exception {
		return (T) value;
	}

	@Override
	public T parse(Integer arg, ResultSet result) throws Exception {
		int i = arg.intValue();
		Object object = result.getObject(i);
		return result.wasNull() ? null
				: filterNotNullBeforeGet(parseNotNull(object));
	}

	/**
	 * @param value
	 *            Never null
	 * @return May be null
	 */
	protected Object setNotNull(T value) throws Exception {
		return value;
	}

	@Override
	public void set(T value, Integer arg, PreparedStatement statement)
			throws Exception {
		if (value != null) {
			statement.setObject(arg.intValue(), setNotNull(value), _sqlType);
		} else {
			statement.setNull(arg.intValue(), _sqlType);
		}
	}

	@Override
	public boolean hasValue(Integer arg, ResultSet result) throws Exception {
		return true;
	}
}
