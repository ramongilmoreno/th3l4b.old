package com.th3l4b.srm.codegen.base.names;

import java.util.Map;

import com.th3l4b.common.named.INamed;
import com.th3l4b.common.propertied.IPropertied;
import com.th3l4b.common.text.TextUtils;
import com.th3l4b.srm.base.IField;
import com.th3l4b.srm.base.normalized.INormalizedEntity;
import com.th3l4b.srm.base.normalized.INormalizedManyToOneRelationship;
import com.th3l4b.srm.base.normalized.INormalizedModel;

public class BaseNames {

	public String valueOrProperty(String value, IPropertied propertied,
			String sourcePrefix) throws Exception {
		return valueOrProperty(value, sourcePrefix + ".identifier", propertied);
	}

	public String valueOrProperty(String value, String property,
			IPropertied propertied) throws Exception {
		Map<String, String> map = propertied.getProperties();
		if (map.containsKey(property)) {
			return map.get(property);
		} else {
			return value;
		}
	}

	protected String valueOrProperty(String value, IPropertied propertied)
			throws Exception {
		return valueOrProperty(value,
				BaseNames.class.getName() + ".identifier", propertied);
	}

	/**
	 * Creates an identifier out of the given input name (C style).
	 * 
	 * @see TextUtils#cIdentifier(String)
	 */
	public String identifier(String name) {
		return TextUtils.cIdentifier(name);
	}

	public String name(INormalizedEntity entity) throws Exception {
		return valueOrProperty(identifier(entity.getName()), entity);
	}

	public String name(IField field) throws Exception {
		return valueOrProperty(identifier(field.getName()), field);
	}

	public String name(INormalizedModel model) throws Exception {
		return identifier(model.getName());
	}

	public String nameOfReverse(INormalizedManyToOneRelationship relationship,
			INormalizedModel model) throws Exception {
		INamed reverse = relationship.getReverse();
		return valueOrProperty(identifier(reverse.getName()), reverse);
	}

	public String nameOfDirect(INormalizedManyToOneRelationship relationship,
			INormalizedModel model) throws Exception {
		INamed direct = relationship.getDirect();
		return valueOrProperty(identifier(direct.getName()), direct);
	}

}
