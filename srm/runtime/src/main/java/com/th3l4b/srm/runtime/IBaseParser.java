package com.th3l4b.srm.runtime;

public interface IBaseParser<R, DBIN, DBOUT, ARGIN, ARGOUT> {
	R parse(ARGIN arg, DBIN result) throws Exception;
	boolean hasValue (ARGIN arg, DBIN result) throws Exception;
	void set(R value, ARGOUT arg, DBOUT statement) throws Exception;
}
