package com.th3l4b.srm.codegen.java.mongo.runtime;

public interface IMongoRuntimeTypesContext {
	<T> IMongoRuntimeType<T> get (String name, Class<T> clazz);
}
