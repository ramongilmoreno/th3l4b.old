package com.th3l4b.srm.codegen.java.basicruntime.tomap;

import java.util.Map;

import com.th3l4b.common.text.TextUtils;
import com.th3l4b.srm.codegen.java.basicruntime.DefaultIdentifier;
import com.th3l4b.srm.runtime.IIdentifier;

public class DefaultToMapIdentifierParser implements IToMapIdentifierParser {

	@Override
	public boolean hasValue(String arg, Map<String, String> result)
			throws Exception {
		return result.containsKey(arg);
	}

	@Override
	public IIdentifier parse(String arg, Map<String, String> result)
			throws Exception {
		String id = result.get(arg);
		if (TextUtils.empty(id)) {
			return null;
		} else {
			return new DefaultIdentifier(id);
		}
	}

	@Override
	public void set(IIdentifier value, String arg, Map<String, String> statement)
			throws Exception {
		statement.put(arg, value != null ? value.getKey() : "");
	}
}
