package com.th3l4b.srm.codegen.java.androidruntime.sqlite;

public interface IAndroidSQLiteRuntimeTypesContext {
	<T> IAndroidSQLiteRuntimeType<T> get(String name, Class<T> clazz);
}