package com.th3l4b.srm.base.normalized;

import com.th3l4b.common.named.INamed;

public interface INormalizedManyToOneRelationship extends INamed {
	public String getReverseName () throws Exception;
}
