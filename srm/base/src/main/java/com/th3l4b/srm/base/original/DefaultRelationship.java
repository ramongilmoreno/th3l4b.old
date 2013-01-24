package com.th3l4b.srm.base.original;

import com.th3l4b.common.java.EnumUtils;
import com.th3l4b.common.named.DefaultNamed;
import com.th3l4b.srm.base.IModelConstants;

public class DefaultRelationship extends DefaultNamed implements IRelationship,
		IModelConstants {

	@Override
	public String getFrom() throws Exception {
		return getProperties().get(PROPERTY_RELATIONSHIP_FROM);
	}

	@Override
	public void setFrom(String from) throws Exception {
		getProperties().put(PROPERTY_RELATIONSHIP_FROM, from);
	}

	@Override
	public String getTo() throws Exception {
		return getProperties().get(PROPERTY_RELATIONSHIP_TO);
	}

	@Override
	public void setTo(String to) throws Exception {
		getProperties().put(PROPERTY_RELATIONSHIP_TO, to);
	}

	@Override
	public String getReverseName() throws Exception {
		return getProperties().get(PROPERTY_RELATIONSHIP_REVERSE_NAME);
	}

	@Override
	public void setReverseName(String reverseName) throws Exception {
		getProperties().put(PROPERTY_RELATIONSHIP_REVERSE_NAME, reverseName);
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
}
