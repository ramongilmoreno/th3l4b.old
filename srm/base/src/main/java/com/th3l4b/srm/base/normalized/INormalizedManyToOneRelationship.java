package com.th3l4b.srm.base.normalized;

import com.th3l4b.common.named.INamed;

public interface INormalizedManyToOneRelationship extends INamed {
	String getTo () throws Exception;
	void setTo (String target) throws Exception;
	INamed getDirect () throws Exception;
	void setDirect (INamed direct) throws Exception;
	INamed getReverse () throws Exception;
	void setReverse (INamed reverse) throws Exception;
}
