package com.th3l4b.srm.codegen.java.basicruntime.tomap;

import java.util.Map;

import com.th3l4b.srm.codegen.java.basicruntime.DefaultIdentifier;
import com.th3l4b.srm.runtime.IDatabaseConstants;
import com.th3l4b.srm.runtime.IIdentifier;

public class DefaultToMapIdentifierParser implements IToMapIdentifierParser {

	@Override
	public IIdentifier parse(Void arg, Map<String, String> result)
			throws Exception {
		String id = result.get(IDatabaseConstants.ID);
		if (id == null || (id.length() == 0)) {
			return null;
		} else {
			return new DefaultIdentifier(id);
		}
	}

	@Override
	public void set(IIdentifier value, Void arg, Map<String, String> statement)
			throws Exception {
		statement.put(IDatabaseConstants.ID, value != null ? value.getKey()
				: "");
	}
}
