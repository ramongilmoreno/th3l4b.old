package com.th3l4b.srm.codegen.java.jdbcruntime.types;

import java.sql.Types;
import java.util.LinkedHashMap;
import java.util.Map;

import com.th3l4b.srm.codegen.java.jdbc.runtime.IJDBCRuntimeType;
import com.th3l4b.srm.codegen.java.jdbc.runtime.IJDBCRuntimeTypesContext;
import com.th3l4b.types.base.basicset.BasicSetTypesEnum;

public class JDBCRuntimeTypesBasicSet implements IJDBCRuntimeTypesContext {

	protected Map<String, IJDBCRuntimeType<?>> _types = new LinkedHashMap<String, IJDBCRuntimeType<?>>();

	public JDBCRuntimeTypesBasicSet() {
		_types.put(BasicSetTypesEnum._label.getName(),
				new JDBCStringRuntimeType(40));
		_types.put(BasicSetTypesEnum._string.getName(),
				new JDBCStringRuntimeType(500));
		_types.put(BasicSetTypesEnum._text.getName(),
				new JDBCStringRuntimeType(5000));
		_types.put(BasicSetTypesEnum._integer.getName(),
				new JDBCIntegerRuntimeType());
		_types.put(BasicSetTypesEnum._decimal.getName(),
				new DefaultJDBCRuntimeType<Double>(Types.DOUBLE));
		_types.put(BasicSetTypesEnum._date.getName(),
				new JDBCIntegerRuntimeType());
		_types.put(BasicSetTypesEnum._timestamp.getName(),
				new JDBCIntegerRuntimeType());
		_types.put(BasicSetTypesEnum._boolean.getName(),
				new DefaultJDBCRuntimeType<Boolean>(Types.INTEGER) {
					@Override
					protected Boolean parseNotNull(Object value)
							throws Exception {
						return ((Number) value).intValue() != 0;
					}

					@Override
					protected Object setNotNull(Boolean value) throws Exception {
						return Integer.valueOf(value.booleanValue() ? 1 : 0);
					}
				});
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> IJDBCRuntimeType<T> get(String name, Class<T> clazz) {
		return (IJDBCRuntimeType<T>) _types.get(name);
	}

}
