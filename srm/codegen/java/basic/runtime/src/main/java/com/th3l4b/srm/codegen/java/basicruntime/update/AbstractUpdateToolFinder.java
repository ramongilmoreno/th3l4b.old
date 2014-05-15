package com.th3l4b.srm.codegen.java.basicruntime.update;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.th3l4b.srm.runtime.IIdentifier;
import com.th3l4b.srm.runtime.IModelUtils;
import com.th3l4b.srm.runtime.IRuntimeEntity;

public abstract class AbstractUpdateToolFinder implements IUpdateToolFinder {

	@Override
	public Map<IIdentifier, IRuntimeEntity<?>> find(
			Map<IIdentifier, IRuntimeEntity<?>> input, IModelUtils utils)
			throws Exception {
		LinkedHashMap<IIdentifier, IRuntimeEntity<?>> r = new LinkedHashMap<IIdentifier, IRuntimeEntity<?>>();
		for (IRuntimeEntity<?> entity : input.values()) {
			findEntity(entity, r, utils);
		}
		return r;
	}

	protected abstract <T extends IRuntimeEntity<T>> void findEntity(
			IRuntimeEntity<T> entity,
			HashMap<IIdentifier, IRuntimeEntity<?>> r, IModelUtils utils)
			throws Exception;

}
