package com.th3l4b.srm.codegen.java.jdbc.runtime;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.th3l4b.common.text.TextUtils;
import com.th3l4b.srm.codegen.java.basicruntime.DefaultIdentifier;
import com.th3l4b.srm.runtime.IIdentifier;

public class DefaultJDBCIdentifierParser implements IJDBCIdentifierParser {

	@Override
	public boolean hasValue(Integer arg, ResultSet result) throws Exception {
		return true;
	}

	@Override
	public IIdentifier parse(Integer column, ResultSet result) throws Exception {
		String id = result.getString(column.intValue());
		if (TextUtils.empty(id)) {
			return null;
		} else {
			return new DefaultIdentifier(id);
		}
	}

	@Override
	public void set(IIdentifier identifier, Integer column,
			PreparedStatement statement) throws Exception {
		statement.setString(column, JDBCUtils.fromIdentifier(identifier));
	}

}
