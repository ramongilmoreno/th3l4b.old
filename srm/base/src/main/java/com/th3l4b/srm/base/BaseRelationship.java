package com.th3l4b.srm.base;

import com.th3l4b.common.named.DefaultNamed;
import com.th3l4b.common.named.INamed;

public class BaseRelationship extends DefaultNamed implements IModelConstants {

	protected INamed _direct;
	protected INamed _reverse;

	public String getTo() throws Exception {
		return getProperties().get(PROPERTY_RELATIONSHIP_TO);
	}

	public void setTo(String to) throws Exception {
		getProperties().put(PROPERTY_RELATIONSHIP_TO, to);
	}

	public INamed getDirect() throws Exception {
		return _direct;
	}
	
	public void setDirect(INamed direct) throws Exception {
		_direct = direct;
	}

	public INamed getReverse() throws Exception {
		return _reverse;
	}
	
	public void setReverse(INamed reverse) throws Exception {
		_reverse = reverse;
	}
}
