package com.th3l4b.android.srm.runtime.sqlite;

public interface IAndroidSQLiteRuntimeTypesContext {
	<T> IAndroidSQLiteRuntimeType<T> get(String name, Class<T> clazz);
}