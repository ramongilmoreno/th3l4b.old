package com.th3l4b.srm.codegen.java.jdbcruntime.types;

import java.sql.Types;

import com.th3l4b.common.text.TextUtils;

public class JDBCStringRuntimeType extends DefaultJDBCRuntimeType<String> {

	private Integer _limit;

	public JDBCStringRuntimeType(Integer limit) {
		super(Types.VARCHAR);
		_limit = limit;
	}

	@Override
	protected String setNotNull(String value) throws Exception {
		return _limit != null ? TextUtils.limit(value, _limit.intValue())
				: value;
	}

	@Override
	protected String filterNotNullBeforeGet(String value) throws Exception {
		return _limit != null ? TextUtils.limit(value, _limit.intValue())
				: value;
	}
}
