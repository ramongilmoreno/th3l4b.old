package com.th3l4b.srm.codegen.database;

import com.th3l4b.common.propertied.IPropertied;
import com.th3l4b.srm.base.IField;
import com.th3l4b.srm.base.normalized.INormalizedEntity;
import com.th3l4b.srm.base.normalized.INormalizedManyToOneRelationship;
import com.th3l4b.srm.base.normalized.INormalizedModel;
import com.th3l4b.srm.codegen.java.basic.JavaNames;
import com.th3l4b.srm.database.IDatabaseType;
import com.th3l4b.srm.runtime.DatabaseUtils;
import com.th3l4b.srm.runtime.IDatabaseConstants;
import com.th3l4b.types.base.IType;

public class SQLNames extends JavaNames implements IDatabaseConstants {

	private static final String PREFIX = SQLNames.class.getPackage().getName();
	public static final String MODEL = PREFIX + ".model";
	public static final String TABLE = PREFIX + ".table";
	private static final String COLUMN_PREFIX = PREFIX + ".column";
	public static final String COLUMN_VALUE = COLUMN_PREFIX + ".value";
	public static final String COLUMN_ISSET = COLUMN_PREFIX + ".isset";
	public static final String TYPE = PREFIX + ".type";

	public String name(IDatabaseType database) throws Exception {
		return database.getName();
	}

	public String table(INormalizedEntity entity) throws Exception {
		return valueOrProperty(javaIdentifier(entity.getName()), TABLE, entity);
	}

	public String column(IField field, boolean forField) throws Exception {
		return column(name(field), forField, field);
	}

	public String column(INormalizedManyToOneRelationship relationship,
			boolean forField, INormalizedModel model) throws Exception {
		return column(nameOfDirect(relationship, model), forField, relationship);
	}

	private String column(String name, boolean forField, IPropertied propertied)
			throws Exception {
		return valueOrProperty(DatabaseUtils.column(name, forField),
				forField ? COLUMN_VALUE : COLUMN_ISSET, propertied);
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
