package com.th3l4b.srm.codegen.database;

import java.util.Map;

import com.th3l4b.common.propertied.IPropertied;
import com.th3l4b.srm.base.IField;
import com.th3l4b.srm.base.normalized.INormalizedEntity;
import com.th3l4b.srm.base.normalized.INormalizedManyToOneRelationship;
import com.th3l4b.srm.base.normalized.INormalizedModel;
import com.th3l4b.srm.codegen.base.names.BaseNames;
import com.th3l4b.srm.database.IDatabaseType;
import com.th3l4b.srm.runtime.IDatabaseConstants;
import com.th3l4b.types.base.IType;

public class SQLNames implements IDatabaseConstants {

	private static final String PREFIX = SQLNames.class.getPackage().getName();
	public static final String MODEL = PREFIX + ".model";
	public static final String TABLE = PREFIX + ".table";
	public static final String COLUMN = PREFIX + ".column";
	public static final String TYPE = PREFIX + ".type";
	public static final String INDEX = PREFIX + ".index";

	private BaseNames _baseNames;

	public SQLNames(BaseNames baseNames) {
		_baseNames = baseNames;
	}

	public String name(IDatabaseType database) throws Exception {
		return database.getName();
	}

	public String table(INormalizedEntity entity) throws Exception {
		return _baseNames.valueOrProperty(
				sql92Name(_baseNames.identifier(entity.getName()), "t"), TABLE,
				entity);
	}

	public String column(IField field) throws Exception {
		return column(_baseNames.name(field), field);
	}

	public String column(INormalizedManyToOneRelationship relationship,
			INormalizedModel model) throws Exception {
		return column(_baseNames.nameOfDirect(relationship, model),
				relationship);
	}

	/**
	 * @param propertied
	 *            May be null
	 */
	public String column(String name, IPropertied propertied) throws Exception {
		String r = sql92Name(name, "c");
		if (propertied == null) {
			return r;
		} else {
			return _baseNames.valueOrProperty(r, COLUMN, propertied);
		}
	}

	private String sql92Name(String name, String prefix) throws Exception {
		// SQL names start with letter
		// http://db.apache.org/derby/docs/10.1/ref/crefsqlj1003454.html
		if (name.matches("[A-Za-z].*"))
			return name;
		else
			return prefix + name;
	}

	public String type(IType type, IDatabaseType database) throws Exception {
		String value = property(type, TYPE, database);
		if (value == null) {
			throw new IllegalStateException(
					"Cannot find SQL type property in type: " + TYPE + ", "
							+ type.getName());
		} else {
			return value;
		}
	}

	public String index(INormalizedEntity e,
			INormalizedManyToOneRelationship rel, IDatabaseType database,
			INormalizedModel model) throws Exception {
		String r = property(rel.getDirect(), INDEX, database);
		if (r == null) {
			return "Idx" + table(e) + column(rel, model);
		}
		return r;
	}

	public String property(IPropertied propertied, String property,
			IDatabaseType database) throws Exception {
		Map<String, String> properties = propertied.getProperties();
		String value = properties.get(property + "." + name(database));
		if (value != null) {
			return value;
		}
		value = properties.get(property);
		return value;
	}

}
