package com.th3l4b.srm.codegen.java.web.rest.runtime;

import com.th3l4b.srm.runtime.IFinder;

public interface IFindManyForRESTFinder<T, F extends IFinder> {
	Iterable<T> many (String id, F finder) throws Exception;
}
