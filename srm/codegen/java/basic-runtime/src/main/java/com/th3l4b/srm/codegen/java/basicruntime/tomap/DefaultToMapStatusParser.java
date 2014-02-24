package com.th3l4b.srm.codegen.java.basicruntime.tomap;

import java.util.Map;

import com.th3l4b.srm.runtime.EntityStatus;
import com.th3l4b.srm.runtime.IDatabaseConstants;

public class DefaultToMapStatusParser implements IToMapStatusParser {

	@Override
	public EntityStatus parse(Void arg, Map<String, String> result)
			throws Exception {
		return EntityStatus.fromInitial(result.get(IDatabaseConstants.STATUS));
	}

	@Override
	public void set(EntityStatus value, Void arg, Map<String, String> statement)
			throws Exception {
		statement.put(IDatabaseConstants.STATUS, value.initial());
	}

}
