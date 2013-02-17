package com.th3l4b.srm.base.original;

import com.th3l4b.common.java.EnumUtils;
import com.th3l4b.srm.base.BaseRelationship;
import com.th3l4b.srm.base.IModelConstants;

public class DefaultRelationship extends BaseRelationship implements
		IRelationship, IModelConstants {

	@Override
	public String getFrom() throws Exception {
		return getProperties().get(PROPERTY_RELATIONSHIP_FROM);
	}

	@Override
	public void setFrom(String from) throws Exception {
		getProperties().put(PROPERTY_RELATIONSHIP_FROM, from);
	}

	@Override
	public RelationshipType getType() throws Exception {
		return EnumUtils.fromString(
				getProperties().get(PROPERTY_RELATIONSHIP_TYPE),
				RelationshipType.class);
	}

	@Override
	public void setType(RelationshipType type) throws Exception {
		getProperties().put(PROPERTY_RELATIONSHIP_TYPE, type.name());
	}

	@Override
	public String getEntity() throws Exception {
		return getProperties().get(PROPERTY_RELATIONSHIP_ENTITY);
	}

	@Override
	public void setEntity(String entity) throws Exception {
		getProperties().put(PROPERTY_RELATIONSHIP_ENTITY, entity);
	}

	@Override
	public String toString() {
		try {
			return "" + getName() + ", " + getFrom() + " -> " + getTo();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
