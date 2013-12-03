package com.th3l4b.srm.codegen.java.androidruntime.sqlite.types;

import java.util.LinkedHashMap;
import java.util.Map;

import com.th3l4b.srm.codegen.java.androidruntime.sqlite.IAndroidSQLiteRuntimeType;
import com.th3l4b.srm.codegen.java.androidruntime.sqlite.IAndroidSQLiteRuntimeTypesContext;

public class AndroidSQLiteRuntimeTypesBasicSet implements
		IAndroidSQLiteRuntimeTypesContext {

	protected Map<String, IAndroidSQLiteRuntimeType<?>> _types = new LinkedHashMap<String, IAndroidSQLiteRuntimeType<?>>();

	public AndroidSQLiteRuntimeTypesBasicSet() {
		int i;
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> IAndroidSQLiteRuntimeType<T> get(String name, Class<T> clazz) {
		return (IAndroidSQLiteRuntimeType<T>) _types.get(name);
	}

}
