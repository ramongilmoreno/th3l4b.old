package com.th3l4b.diary7.store;

public interface IStoreEntry {
	IIdentifier getIdentifier() throws Exception;
	void setIdentifier(IIdentifier identifier) throws Exception;
	IIdentifier getTarget() throws Exception;
	void setTarget(IIdentifier target) throws Exception;
	IIdentifier getSource() throws Exception;
	void setSource(IIdentifier source) throws Exception;
	IData getData () throws Exception;
	void setData (IData data) throws Exception;
}
