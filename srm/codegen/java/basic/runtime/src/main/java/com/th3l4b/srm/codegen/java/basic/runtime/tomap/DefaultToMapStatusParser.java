package com.th3l4b.srm.codegen.java.basic.runtime.tomap;

import java.util.Map;

import com.th3l4b.srm.runtime.EntityStatus;

public class DefaultToMapStatusParser implements IToMapStatusParser {

	@Override
	public boolean hasValue(String arg, Map<String, String> result)
			throws Exception {
		return result.containsKey(arg);
	}

	@Override
	public EntityStatus parse(String arg, Map<String, String> result)
			throws Exception {
		return EntityStatus.fromInitial(result.get(arg));
	}

	@Override
	public void set(EntityStatus value, String arg,
			Map<String, String> statement) throws Exception {
		value = value != null ? value : EntityStatus.Unknown;
		statement.put(arg, value.initial());
	}

}
