package com.th3l4b.srm.runtime;

public interface IFinder {
	<T extends IRuntimeEntity<T>> T find(Class<T> clazz, IIdentifier identifier)
			throws Exception;

	<T extends IRuntimeEntity<T>> Iterable<T> find(Class<T> clazz,
			IIdentifier identifier, String relationship) throws Exception;

	<T extends IRuntimeEntity<T>> Iterable<T> all(Class<T> clazz)
			throws Exception;
}
