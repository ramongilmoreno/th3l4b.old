package com.th3l4b.srm.codegen.java.mongo.runtime.types;

import java.util.LinkedHashMap;
import java.util.Map;

import com.th3l4b.srm.codegen.java.mongo.runtime.IMongoRuntimeType;
import com.th3l4b.srm.codegen.java.mongo.runtime.IMongoRuntimeTypesContext;
import com.th3l4b.types.base.basicset.BasicSetTypesEnum;

public class MongoRuntimeTypesBasicSet implements IMongoRuntimeTypesContext {

	protected Map<String, IMongoRuntimeType<?>> _types = new LinkedHashMap<String, IMongoRuntimeType<?>>();

	public MongoRuntimeTypesBasicSet() {
		_types.put(BasicSetTypesEnum._label.getName(),
				new MongoStringRuntimeType(40));
		_types.put(BasicSetTypesEnum._string.getName(),
				new MongoStringRuntimeType(500));
		_types.put(BasicSetTypesEnum._text.getName(),
				new MongoStringRuntimeType(5000));
		_types.put(BasicSetTypesEnum._integer.getName(),
				new MongoIntegerRuntimeType());
		// http://docs.mongodb.org/manual/reference/bson-types/
		_types.put(BasicSetTypesEnum._decimal.getName(),
				new DefaultMongoRuntimeType<Double>(1));
		_types.put(BasicSetTypesEnum._date.getName(),
				new MongoIntegerRuntimeType());
		_types.put(BasicSetTypesEnum._timestamp.getName(),
				new MongoIntegerRuntimeType());
		// http://docs.mongodb.org/manual/reference/bson-types/
		_types.put(BasicSetTypesEnum._boolean.getName(),
				new DefaultMongoRuntimeType<Boolean>(8));
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> IMongoRuntimeType<T> get(String name, Class<T> clazz) {
		return (IMongoRuntimeType<T>) _types.get(name);
	}

}
