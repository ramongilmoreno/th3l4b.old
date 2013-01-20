package com.th3l4b.srm.base.normalized;

import com.th3l4b.common.named.INamed;
import com.th3l4b.common.named.INamedContainer;

public interface INormalizedEntity extends INamed,
		INamedContainer<INormalizedManyToOneRelationship> {
}
