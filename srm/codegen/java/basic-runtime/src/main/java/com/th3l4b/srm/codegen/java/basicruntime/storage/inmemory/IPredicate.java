package com.th3l4b.srm.codegen.java.basicruntime.storage.inmemory;

public interface IPredicate<T> {
	boolean accept (T arg) throws Exception;
}
