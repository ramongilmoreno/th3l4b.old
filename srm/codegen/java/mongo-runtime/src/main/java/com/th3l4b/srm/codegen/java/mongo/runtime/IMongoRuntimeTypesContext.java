package com.th3l4b.srm.codegen.java.mongoruntime;

public interface IMongoRuntimeTypesContext {
	<T> IMongoRuntimeType<T> get (String name, Class<T> clazz);
}
