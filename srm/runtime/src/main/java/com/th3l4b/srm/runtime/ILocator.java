package com.th3l4b.srm.runtime;

public interface ILocator {
	<T extends IRuntimeEntity> T find(Class<T> clazz, IIdentifier identifier)
			throws Exception;

	<T extends IRuntimeEntity> Iterable<T> find(Class<T> clazz,
			IIdentifier identifier, String relationship) throws Exception;

	<T extends IRuntimeEntity> Iterable<T> all(Class<T> clazz) throws Exception;
}
