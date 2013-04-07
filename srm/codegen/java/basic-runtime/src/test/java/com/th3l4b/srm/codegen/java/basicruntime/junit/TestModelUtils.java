package com.th3l4b.srm.codegen.java.basicruntime.junit;

import java.util.LinkedHashMap;
import java.util.Map;

import com.th3l4b.srm.codegen.java.basicruntime.storage.inmemory.AbstractModelUtils;
import com.th3l4b.srm.runtime.IRuntimeEntity;

public class TestModelUtils extends AbstractModelUtils {
	
	private interface Creator {
		Object create () throws Exception;
	}
	
	Map<String, Creator> _creators = new LinkedHashMap<String, Creator>();
	
	public TestModelUtils () {
		_creators.put(ITestEntityA.class.getName(), new Creator() {
			@Override
			public Object create() throws Exception {
				return new TestEntityA();
			}
		});
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public <T2 extends IRuntimeEntity<T2>> T2 create(Class<T2> clazz)
			throws Exception {
		Creator creator = _creators.get(clazz.getName());
		if (creator == null) {
			throw new IllegalArgumentException("Unknown class: " + clazz.getName());
		}
		return (T2) creator.create();
	}

}
