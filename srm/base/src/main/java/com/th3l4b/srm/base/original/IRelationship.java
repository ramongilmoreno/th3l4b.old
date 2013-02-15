package com.th3l4b.srm.base.original;

import com.th3l4b.common.named.INamed;

public interface IRelationship extends INamed {
	String getFrom () throws Exception;
	void setFrom (String from) throws Exception;
	String getTo () throws Exception;
	void setTo (String to) throws Exception;
	INamed getDirect () throws Exception;
	void setDirect (INamed direct) throws Exception;
	INamed getReverse () throws Exception;
	void setReverse (INamed reverse) throws Exception;
	RelationshipType getType () throws Exception;
	void setType (RelationshipType type) throws Exception;
	String getEntity () throws Exception;
	void setEntity (String entity) throws Exception;
}
