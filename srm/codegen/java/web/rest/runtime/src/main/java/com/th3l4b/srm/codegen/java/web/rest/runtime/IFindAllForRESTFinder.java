package com.th3l4b.srm.codegen.java.web.rest.runtime;

import com.th3l4b.srm.runtime.IFinder;

public interface IFindAllForRESTFinder<T, F extends IFinder> {
	Iterable<T> all (F finder) throws Exception;
}
