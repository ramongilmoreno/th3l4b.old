package com.th3l4b.srm.codegen.java.androidruntime.sqlite;

import com.th3l4b.srm.runtime.EntityStatus;
import com.th3l4b.srm.runtime.IIdentifier;

public class AndroidSQLiteUtils {
	public static void columns(IAndroidSQLiteEntityParser<?> parser,
			StringBuffer query) throws Exception {
		query.append(parser.idColumn());
		query.append(", ");
		query.append(parser.statusColumn());
		for (String col : parser.fieldsColumns()) {
			query.append(", ");
			query.append(col);
		}
	}

	public static void columnsAndFrom(IAndroidSQLiteEntityParser<?> parser,
			StringBuffer query) throws Exception {
		columns(parser, query);
		query.append(" from ");
		query.append(parser.table());
	}
	
	public static String fromIdentifier (IIdentifier id) {
		return id.getKey();
	}
	
	public static String fromEntityStatus (EntityStatus status) {
		return status.initial();
	}
}
