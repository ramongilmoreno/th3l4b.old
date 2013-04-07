package com.th3l4b.srm.codegen.java.basicruntime.junit;

import java.util.LinkedHashMap;
import java.util.Map;

import com.th3l4b.srm.codegen.java.basicruntime.storage.inmemory.AbstractInMemoryContainer;
import com.th3l4b.srm.runtime.IIdentifier;
import com.th3l4b.srm.runtime.IRuntimeEntity;

public class TestInMemoryContainer extends AbstractInMemoryContainer {

	Map<IIdentifier, IRuntimeEntity<?>> _entities;

	@Override
	protected Map<IIdentifier, IRuntimeEntity<?>> getEntities()
			throws Exception {
		if (_entities == null) {
			LinkedHashMap<IIdentifier, IRuntimeEntity<?>> r = new LinkedHashMap<IIdentifier, IRuntimeEntity<?>>();
			_entities = r;
		}
		return _entities;
	}

}
