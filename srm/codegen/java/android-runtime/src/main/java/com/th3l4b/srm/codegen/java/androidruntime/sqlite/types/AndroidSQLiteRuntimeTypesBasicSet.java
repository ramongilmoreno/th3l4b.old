package com.th3l4b.srm.codegen.java.androidruntime.sqlite.types;

import java.util.LinkedHashMap;
import java.util.Map;

import android.content.ContentValues;
import android.database.Cursor;

import com.th3l4b.srm.codegen.java.androidruntime.sqlite.IAndroidSQLiteRuntimeType;
import com.th3l4b.srm.codegen.java.androidruntime.sqlite.IAndroidSQLiteRuntimeTypesContext;
import com.th3l4b.types.base.basicset.BasicSetTypesEnum;

public class AndroidSQLiteRuntimeTypesBasicSet implements
		IAndroidSQLiteRuntimeTypesContext {

	protected Map<String, IAndroidSQLiteRuntimeType<?>> _types = new LinkedHashMap<String, IAndroidSQLiteRuntimeType<?>>();

	public AndroidSQLiteRuntimeTypesBasicSet() {
		_types.put(BasicSetTypesEnum._label.getName(),
				new AndroidSQLiteStringRuntimeType(40));
		_types.put(BasicSetTypesEnum._string.getName(),
				new AndroidSQLiteStringRuntimeType(500));
		_types.put(BasicSetTypesEnum._text.getName(),
				new AndroidSQLiteStringRuntimeType(5000));
		_types.put(BasicSetTypesEnum._integer.getName(),
				new AndroidSQLiteIntegerRuntimeType());
		_types.put(BasicSetTypesEnum._decimal.getName(),
				new AbstractAndroidSQLiteType<Double>() {
					@Override
					public Double parseNotNull(int i, Cursor result)
							throws Exception {
						return result.getDouble(i);
					}

					@Override
					public void setNotNull(Double value, String arg,
							ContentValues statement) throws Exception {
						statement.put(arg, value);
					}
				});
		_types.put(BasicSetTypesEnum._date.getName(),
				new AndroidSQLiteIntegerRuntimeType());
		_types.put(BasicSetTypesEnum._timestamp.getName(),
				new AndroidSQLiteIntegerRuntimeType());
		_types.put(BasicSetTypesEnum._boolean.getName(),
				new AbstractAndroidSQLiteType<Boolean>() {
					@Override
					public Boolean parseNotNull(int i, Cursor result)
							throws Exception {
						return result.getLong(i) != 0;
					}

					@Override
					public void setNotNull(Boolean value, String arg,
							ContentValues statement) throws Exception {
						statement.put(arg, value);
					}
				});
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> IAndroidSQLiteRuntimeType<T> get(String name, Class<T> clazz) {
		return (IAndroidSQLiteRuntimeType<T>) _types.get(name);
	}

}
