package com.th3l4b.srm.runtime;

public interface IRuntimeEntity<T extends IRuntimeEntity<T>> {
	Class<T> clazz ();
	ICoordinates coordinates () throws Exception;
}
