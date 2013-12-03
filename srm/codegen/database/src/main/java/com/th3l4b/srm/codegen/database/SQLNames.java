package com.th3l4b.srm.codegen.database;

import com.th3l4b.srm.base.IField;
import com.th3l4b.srm.base.normalized.INormalizedEntity;
import com.th3l4b.srm.base.normalized.INormalizedManyToOneRelationship;
import com.th3l4b.srm.base.normalized.INormalizedModel;
import com.th3l4b.srm.codegen.java.basic.JavaNames;
import com.th3l4b.srm.database.IDatabaseType;
import com.th3l4b.types.base.IType;

public class SQLNames extends JavaNames {

	private static final String PREFIX = SQLNames.class.getPackage().getName();
	public static final String MODEL = PREFIX + ".model";
	public static final String TABLE = PREFIX + ".table";
	public static final String COLUMN = PREFIX + ".column";
	public static final String ID = "_Id";
	public static final String STATUS = "_Status";
	public static final String TYPE = PREFIX + ".type";

	public String name(IDatabaseType database) throws Exception {
		return database.getName();
	}

	public String table(INormalizedEntity entity) throws Exception {
		return valueOrProperty(javaIdentifier(entity.getName()), TABLE, entity);
	}

	public String column(IField field) throws Exception {
		return valueOrProperty(name(field), COLUMN, field);
	}

	public String column(INormalizedManyToOneRelationship relationship,
			INormalizedModel model) throws Exception {
		return valueOrProperty(nameOfDirect(relationship, model), COLUMN,
				relationship);
	}

	public String type(IType type, IDatabaseType database) throws Exception {
		String value = type.getProperties().get(TYPE + "." + name(database));
		if (value == null) {
			value = type.getProperties().get(TYPE);
		}
		if (value == null) {
			throw new IllegalStateException(
					"Cannot find SQL type property in type: " + TYPE + ", "
							+ type.getName());
		} else {
			return value;
		}
	}

}
