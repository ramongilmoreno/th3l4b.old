package com.th3l4b.screens.base;

import java.util.Map;
import java.util.UUID;

import com.th3l4b.common.named.DefaultNamed;
import com.th3l4b.common.named.DefaultNamedContainer;
import com.th3l4b.common.named.INamed;

public class DefaultScreen extends DefaultNamedContainer<IScreen> implements IScreen {

	private INamed _delegatedNamed = new DefaultNamed();
	
	public DefaultScreen() throws Exception {
		this(UUID.randomUUID().toString());
	}
	
	public DefaultScreen(String name) throws Exception {
		setName(name);
	}

	public Map<String, String> getProperties() throws Exception {
		return _delegatedNamed.getProperties();
	}

	public String getName() throws Exception {
		return _delegatedNamed.getName();
	}

	public Map<String, Object> getAttributes() throws Exception {
		return _delegatedNamed.getAttributes();
	}

	public void setName(String name) throws Exception {
		_delegatedNamed.setName(name);
	}

	@Override
	public int hashCode() {
		return _delegatedNamed.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return _delegatedNamed.equals(obj);
	}

}
