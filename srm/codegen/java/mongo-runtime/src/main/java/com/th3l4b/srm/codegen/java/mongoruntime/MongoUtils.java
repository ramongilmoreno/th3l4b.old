package com.th3l4b.srm.codegen.java.mongoruntime;

import com.th3l4b.srm.runtime.EntityStatus;
import com.th3l4b.srm.runtime.IIdentifier;

public class MongoUtils {

	public static String fromIdentifier(IIdentifier id) {
		return id == null ? null : id.getKey();
	}

	public static String fromEntityStatus(EntityStatus status) {
		return status.initial();
	}

}
