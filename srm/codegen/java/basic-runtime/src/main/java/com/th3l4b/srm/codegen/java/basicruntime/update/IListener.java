package com.th3l4b.srm.codegen.java.basicruntime.update;

import com.th3l4b.srm.runtime.IRuntimeEntity;

public interface IListener {
	<T extends IRuntimeEntity<T>> void created(T entity) throws Exception;

	<T extends IRuntimeEntity<T>> void deleted(T entity) throws Exception;

	<T extends IRuntimeEntity<T>> void updated(T modifications, T old)
			throws Exception;
}
