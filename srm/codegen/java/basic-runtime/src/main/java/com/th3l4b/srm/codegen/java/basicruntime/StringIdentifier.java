package com.th3l4b.srm.codegen.java.basicruntime;

import java.util.UUID;

import com.th3l4b.common.data.nullsafe.NullSafe;
import com.th3l4b.srm.runtime.IIdentifier;

/**
 * Dummy implementation of a string identifier of arbitrary length.
 */
public class StringIdentifier implements IIdentifier {

	private String _value;

	public StringIdentifier() {
		this(UUID.randomUUID().toString());
	}

	public StringIdentifier(String value) {
		_value = value;
	}
	
	public String getValue() {
		return _value;
	}

	public int hashCode() {
		return _value.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof StringIdentifier) {
			return NullSafe.equals(((StringIdentifier) obj)._value, _value);
		}
		return false;
	}

}
