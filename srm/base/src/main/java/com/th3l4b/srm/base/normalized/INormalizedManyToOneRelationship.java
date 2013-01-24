package com.th3l4b.srm.base.normalized;

import com.th3l4b.common.named.INamed;

public interface INormalizedManyToOneRelationship extends INamed {
	String getTarget () throws Exception;
	void setTarget (String target) throws Exception;
	String getReverseName () throws Exception;
	void setReverseName (String reverseName) throws Exception;
}
