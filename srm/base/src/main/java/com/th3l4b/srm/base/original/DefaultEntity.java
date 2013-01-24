package com.th3l4b.srm.base.original;

import java.util.Map;

import com.th3l4b.common.named.DefaultNamed;
import com.th3l4b.common.named.DefaultNamedContainer;
import com.th3l4b.srm.base.IField;

public class DefaultEntity extends DefaultNamedContainer<IField> implements IEntity {
	DefaultNamed _delegated = new DefaultNamed();

	public String getName() throws Exception {
		return _delegated.getName();
	}

	public void setName(String name) throws Exception {
		_delegated.setName(name);
	}

	public Map<String, String> getProperties() throws Exception {
		return _delegated.getProperties();
	}

	public Map<String, Object> getAttributes() throws Exception {
		return _delegated.getAttributes();
	}

	public int hashCode() {
		return _delegated.hashCode();
	}

	public boolean equals(Object obj) {
		return _delegated.equals(obj);
	}

	public String toString() {
		return _delegated.toString();
	}
}
