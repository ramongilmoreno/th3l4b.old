package com.th3l4b.srm.base.normalized;

import com.th3l4b.common.named.INamedContainer;
import com.th3l4b.srm.base.original.IEntity;

public interface INormalizedEntity extends IEntity {
	INamedContainer<INormalizedManyToOneRelationship> relationships() throws Exception;
}
