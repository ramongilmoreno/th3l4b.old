package com.th3l4b.srm.base.normalized;

import com.th3l4b.srm.base.BaseRelationship;

public class DefaultNormalizedManyToOneRelationship extends BaseRelationship
		implements INormalizedManyToOneRelationship {

	@Override
	public String toString() {
		try {
			return getName();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
