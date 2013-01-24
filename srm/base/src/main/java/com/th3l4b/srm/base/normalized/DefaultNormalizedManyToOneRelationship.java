package com.th3l4b.srm.base.normalized;

import com.th3l4b.common.named.DefaultNamed;

public class DefaultNormalizedManyToOneRelationship extends DefaultNamed
		implements INormalizedManyToOneRelationship, INormalizedConstants {

	@Override
	public String getReverseName() throws Exception {
		return getProperties().get(PROPERTY_RELATIONSHIP_REVERSE_NAME);
	}

	@Override
	public void setReverseName(String reverseName) throws Exception {
		getProperties().put(PROPERTY_RELATIONSHIP_REVERSE_NAME, reverseName);
	}

	@Override
	public String getTarget() throws Exception {
		return getProperties().get(PROPERTY_RELATIONSHIP_TARGET);
	}

	@Override
	public void setTarget(String target) throws Exception {
		getProperties().put(PROPERTY_RELATIONSHIP_TARGET, target);
	}

}
