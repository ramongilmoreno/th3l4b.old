package com.th3l4b.diary7.store;

public interface IIdentifier {
	byte[] getBytes () throws Exception;
	void setBytes (byte[] bytes) throws Exception;
}
