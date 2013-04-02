package com.th3l4b.common.data.predicate;

public interface IPredicate<T> {
	boolean accept (T t) throws Exception;
}
