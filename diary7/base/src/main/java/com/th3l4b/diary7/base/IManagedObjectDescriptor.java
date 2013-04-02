package com.th3l4b.diary7.base;

/**
 * @deprecated see srm
 */
public interface IManagedObjectDescriptor extends IIdentified {
	ManagedObjectStatus getStatus () throws Exception;
	void setStatus (ManagedObjectStatus status) throws Exception;
	ITimestamp getTimestamp () throws Exception;
	void setTimestamp (ITimestamp timestamp) throws Exception;
	IIdentifier getModificationUser () throws Exception;
	void setModificationUser () throws Exception;
}
