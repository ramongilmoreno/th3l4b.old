package com.th3l4b.srm.codegen.java.web.rest.runtime;

import com.th3l4b.srm.runtime.IFinder;
import com.th3l4b.srm.runtime.IRuntimeEntity;

public interface IRESTFinder<F extends IFinder> {
	Iterable<? extends IRuntimeEntity<?>> all(String type, F finder)
			throws Exception;

	IRuntimeEntity<?> get(String type, String id, F finder) throws Exception;

	Iterable<? extends IRuntimeEntity<?>> get(String type, String id,
			String relationship, F finder) throws Exception;
}
