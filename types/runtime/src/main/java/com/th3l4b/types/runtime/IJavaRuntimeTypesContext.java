package com.th3l4b.types.runtime;

public interface IJavaRuntimeTypesContext {
	<T> IJavaRuntimeType<T> get (String name, Class<T> clazz);
}
