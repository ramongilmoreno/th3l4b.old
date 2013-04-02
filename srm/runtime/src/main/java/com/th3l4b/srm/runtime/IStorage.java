package com.th3l4b.srm.runtime;

public interface IStorage {
	<T extends IRuntimeEntity> void commit(Iterable<T> entity) throws Exception;
}
