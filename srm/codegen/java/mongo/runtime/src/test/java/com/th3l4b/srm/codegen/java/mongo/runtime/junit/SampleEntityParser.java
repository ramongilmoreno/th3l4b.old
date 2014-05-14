package com.th3l4b.srm.codegen.java.mongo.runtime.junit;

import com.mongodb.DBObject;
import com.th3l4b.srm.codegen.java.mongo.runtime.AbstractMongoEntityParser;
import com.th3l4b.srm.codegen.java.mongo.runtime.IMongoIdentifierParser;
import com.th3l4b.srm.codegen.java.mongo.runtime.IMongoRuntimeType;
import com.th3l4b.srm.codegen.java.mongo.runtime.IMongoRuntimeTypesContext;
import com.th3l4b.srm.codegen.java.mongo.runtime.IMongoStatusParser;

public class SampleEntityParser extends
		AbstractMongoEntityParser<ISampleEntity> {

	private IMongoRuntimeType<String> _field_Field1;

	public SampleEntityParser(IMongoIdentifierParser ids,
			IMongoStatusParser status, IMongoRuntimeTypesContext types) {
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
	public ISampleEntity create() {
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
	protected void parseRest(ISampleEntity entity, DBObject result)
			throws Exception {
		if (result.containsField("Field1")) { entity.setField1(_field_Field1.parse("Field1", result)); }
		if (result.containsField("Relation")) { entity.setRelation(getIdsParser().parse("Relation", result)); }

	}

	@Override
	protected void setRest(ISampleEntity entity, DBObject statement)
			throws Exception {
		if (entity.isSetField1()) { _field_Field1.set(entity.getField1(), "Field1", statement); }
		if (entity.isSetRelation()) { getIdsParser().set(entity.getRelation(), "Relation", statement); }
	}

}
