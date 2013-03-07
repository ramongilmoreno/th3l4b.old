package com.th3l4b.diary7.store;

import java.io.InputStream;

public interface IData {
	String getNature () throws Exception;
	InputStream getStream () throws Exception;
}
