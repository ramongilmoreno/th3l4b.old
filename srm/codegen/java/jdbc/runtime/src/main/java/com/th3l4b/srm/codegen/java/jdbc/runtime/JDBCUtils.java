package com.th3l4b.srm.codegen.java.jdbc.runtime;

import com.th3l4b.srm.runtime.EntityStatus;
import com.th3l4b.srm.runtime.IIdentifier;

public class JDBCUtils {
	public static void columns(IJDBCEntityParser<?> parser, StringBuffer query)
			throws Exception {
		query.append(parser.idColumn());
		query.append(", ");
		query.append(parser.statusColumn());
		for (String col : parser.fieldsColumns()) {
			query.append(", ");
			query.append(col);
		}
	}

	public static void columnsAndFrom(IJDBCEntityParser<?> parser,
			StringBuffer query) throws Exception {
		columns(parser, query);
		query.append(" from ");
		query.append(parser.table());
	}

	public static String fromIdentifier(IIdentifier id) {
		return id == null ? null : id.getKey();
	}

	public static String fromEntityStatus(EntityStatus status) {
		return status.initial();
	}

}
