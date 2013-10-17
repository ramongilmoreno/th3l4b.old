package com.th3l4b.srm.codegen.java.jdbcruntime;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.th3l4b.types.runtime.IJDBCRuntimeType;
import com.th3l4b.types.runtime.IJavaRuntimeTypesContext;

public class SampleEntityParser implements IJDBCEntityParser<ISampleEntity> {

	@Override
	public String table() throws Exception {
		return "SampleEntity";
	}

	@Override
	public String idColumn() throws Exception {
		return "Id";
	}

	private static String[] COLUMNS = { "A", "B" };

	@Override
	public ISampleEntity create(Class<ISampleEntity> clazz) {
		return new DefaultSampleEntity();
	}

	@Override
	public String statusColumn() throws Exception {
		return "Status";
	}

	@Override
	public String[] fieldsColumns() throws Exception {
		return COLUMNS;
	}

	@Override
	public void parse(ISampleEntity entity, int index, ResultSet result,
			IJDBCIdentifierParser ids, IJavaRuntimeTypesContext types)
			throws Exception {
		entity.setField1(((IJDBCRuntimeType<?>) types.get("string",
				String.class)).fromResultSet(index++, result, String.class));
	}

	@Override
	public void set(ISampleEntity entity, int index,
			PreparedStatement statement, IJDBCIdentifierParser ids,
			IJavaRuntimeTypesContext types) throws Exception {
		((IJDBCRuntimeType<?>) types.get("string", String.class))
				.toPreparedStatement(entity.getField1(), index++, statement,
						String.class);

	}

}
