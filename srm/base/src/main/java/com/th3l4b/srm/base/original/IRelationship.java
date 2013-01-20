package com.th3l4b.srm.base.original;

import com.th3l4b.common.named.INamed;

public interface IRelationship extends INamed {
	String getFrom () throws Exception;
	String getTo () throws Exception;
	String getReverseName () throws Exception;
	RelationshipType getType () throws Exception;
}
