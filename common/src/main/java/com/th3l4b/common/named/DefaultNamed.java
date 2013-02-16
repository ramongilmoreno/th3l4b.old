package com.th3l4b.common.named;

import com.th3l4b.common.data.nullsafe.NullSafe;
import com.th3l4b.common.propertied.DefaultPropertied;

public class DefaultNamed extends DefaultPropertied implements INamed {

	public DefaultNamed() {
	}

	public DefaultNamed(String name) throws Exception {
		setName(name);
	}

	@Override
	public String getName() throws Exception {
		return getProperties().get(PROPERTY_NAME);
	}

	@Override
	public void setName(String name) throws Exception {
		getProperties().put(PROPERTY_NAME, name);
	}

	@Override
	public String toString() {
		try {
			return getName();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public int hashCode() {
		try {
			return NullSafe.hashCode(getName());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof INamed) {
			try {
				return NullSafe.equals(getName(), ((INamed) obj).getName());
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		} else {
			return false;
		}
	}

}
