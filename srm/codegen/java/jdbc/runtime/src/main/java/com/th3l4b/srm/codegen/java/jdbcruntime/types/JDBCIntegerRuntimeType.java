package com.th3l4b.srm.codegen.java.jdbcruntime.types;

import java.sql.Types;

public class JDBCIntegerRuntimeType extends DefaultJDBCRuntimeType<Long> {

	public JDBCIntegerRuntimeType() {
		super(Types.BIGINT);
	}

}
