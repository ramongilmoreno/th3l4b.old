package com.th3l4b.srm.base.normalized;

import com.th3l4b.common.named.DefaultNamedContainer;
import com.th3l4b.common.named.INamedContainer;
import com.th3l4b.srm.base.original.DefaultEntity;

public class DefaultNormalizedEntity extends DefaultEntity implements
		INormalizedEntity {

	protected DefaultNamedContainer<INormalizedManyToOneRelationship> _relationships = new DefaultNamedContainer<INormalizedManyToOneRelationship>();

	@Override
	public INamedContainer<INormalizedManyToOneRelationship> relationships()
			throws Exception {
		return _relationships;
	}

}
