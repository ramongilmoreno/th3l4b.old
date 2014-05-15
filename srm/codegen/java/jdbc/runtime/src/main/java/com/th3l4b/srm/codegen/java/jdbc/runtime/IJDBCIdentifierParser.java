package com.th3l4b.srm.codegen.java.jdbc.runtime;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.th3l4b.srm.runtime.IIdentifierParser;

public interface IJDBCIdentifierParser extends
		IIdentifierParser<ResultSet, PreparedStatement, Integer, Integer> {
}
