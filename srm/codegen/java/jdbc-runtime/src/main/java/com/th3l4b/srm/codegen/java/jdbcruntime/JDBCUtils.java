package com.th3l4b.srm.codegen.java.jdbcruntime;

public class JDBCUtils {
	public static void columns(IJDBCEntityParser<?> parser, StringBuffer query) throws Exception {
		query.append(parser.idColumn());
		query.append(", ");
		query.append(parser.statusColumn());
		for (String col : parser.fieldsColumns()) {
			query.append(", ");
			query.append(col);
		}
	}

	public static void columnsAndFrom(IJDBCEntityParser<?> parser, StringBuffer query) throws Exception {
		columns(parser, query);
		query.append(" from ");
		query.append(parser.table());
	}
}
