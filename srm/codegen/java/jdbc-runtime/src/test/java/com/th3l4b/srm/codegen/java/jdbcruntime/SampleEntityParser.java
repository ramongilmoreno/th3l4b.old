package com.th3l4b.srm.codegen.java.jdbcruntime;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.th3l4b.types.runtime.IJDBCRuntimeType;
import com.th3l4b.types.runtime.IJDBCRuntimeTypesContext;

public class SampleEntityParser extends AbstractJDBCEntityParser<ISampleEntity> {

	private IJDBCRuntimeType<String> _field_Field1;

	public SampleEntityParser(IJDBCIdentifierParser ids,
			IJDBCStatusParser status, IJDBCRuntimeTypesContext types) {
		super(ids, status);
		_field_Field1 = types.get("string", String.class);
	}

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
	protected void parseRest(ISampleEntity entity, int index, ResultSet result)
			throws Exception {
		entity.setField1(_field_Field1.fromResultSet(index++, result));
	}

	@Override
	protected void setRest(ISampleEntity entity, int index,
			PreparedStatement statement) throws Exception {
		_field_Field1.toPreparedStatement(entity.getField1(), index++, statement, String.class);

	}

}
