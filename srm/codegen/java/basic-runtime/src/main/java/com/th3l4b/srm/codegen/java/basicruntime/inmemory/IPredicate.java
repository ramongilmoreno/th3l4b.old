package com.th3l4b.srm.codegen.java.basicruntime.inmemory;

public interface IPredicate<T> {
	boolean accept (T arg) throws Exception;
	Class<T> clazz () throws Exception;
}
