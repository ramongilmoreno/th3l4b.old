package com.th3l4b.srm.runtime;

public interface IModelUtils {
	<T extends IRuntimeEntity<?>> String nameFromClass (Class<T> clazz) throws Exception;
	Class<? extends IRuntimeEntity<?>> classFromName (String name) throws Exception;
	<T extends IRuntimeEntity<?>> T create(Class<T> clazz) throws Exception;
	<T extends IRuntimeEntity<?>> T clone(T src) throws Exception;
	<T extends IRuntimeEntity<?>> void copy(IRuntimeEntity<?> source, IRuntimeEntity<?> target, Class<T> clazz) throws Exception;
	<T extends IRuntimeEntity<?>> void unSetNullValues (T obj) throws Exception;
}
