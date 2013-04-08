package com.th3l4b.srm.codegen.java.basicruntime.storage.inmemory;

import com.th3l4b.srm.runtime.IIdentifier;
import com.th3l4b.srm.runtime.IModelUtils;
import com.th3l4b.srm.runtime.IRuntimeEntity;

public abstract class AbstractModelUtils implements IModelUtils {

	@Override
	public <T2 extends IRuntimeEntity<T2>> IIdentifier identifier(
			Class<T2> clazz, Object... args) throws Exception {
		Long msb = (Long) args[0];
		Long lsb = (Long) args[1];
		return new UUIDIdentifier(clazz, msb.longValue(), lsb.longValue());
	}

	@Override
	public boolean compare(IIdentifier a, IIdentifier b) throws Exception {
		return compareStatic(a, b);
	}

	public static boolean compareStatic(IIdentifier a, IIdentifier b)
			throws Exception {
		return UUIDIdentifier.eq((UUIDIdentifier) a, (UUIDIdentifier) b);
	}
}
