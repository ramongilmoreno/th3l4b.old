package com.th3l4b.srm.codegen.java.web.rest.runtime;

import com.th3l4b.srm.runtime.IFinder;

public interface IFindOneForRESTFinder<T, F extends IFinder> {
	T find(String id, F finder) throws Exception;
}
