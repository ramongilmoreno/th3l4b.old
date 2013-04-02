package com.th3l4b.srm.runtime;

public interface ICoordinates {
	IIdentifier getIdentifier () throws Exception;
	void setIdentifier (IIdentifier identifier) throws Exception;
	EntityStatus getStatus () throws Exception;
	void setStatus (EntityStatus status) throws Exception;
}
