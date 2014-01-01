package com.th3l4b.srm.codegen.java.basicruntime;

import java.util.UUID;

import com.th3l4b.common.data.nullsafe.NullSafe;
import com.th3l4b.srm.runtime.IIdentifier;

/**
 * Identifier class that manages a type value and an id value
 */
public class DefaultIdentifier implements IIdentifier {

	protected String _type;
	protected String _key;

	public DefaultIdentifier(Class<?> type) {
		this(type, UUID.randomUUID().toString());
	}

	public DefaultIdentifier(Class<?> type, String key) {
		this(type.getName(), key);
	}

	public DefaultIdentifier(String key) {
		this((Class<?>) null, key);
	}
	
	public DefaultIdentifier(String type, String key) {
		_type = type;
		_key = key;
	}

	public String getKey() {
		return _key;
	}

	public void setKey(String key) {
		_key = key;
	}

	public String getType() {
		return _type;
	}

	public void setType(String type) {
		_type = type;
	}

	@Override
	public int hashCode() {
		return _key.hashCode() ^ _type.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof IIdentifier) {
			IIdentifier o = (IIdentifier) obj;
			return NullSafe.equals(_key, o.getKey())
					&& NullSafe.equals(_type, o.getType());
		} else {
			return false;
		}
	}
}
