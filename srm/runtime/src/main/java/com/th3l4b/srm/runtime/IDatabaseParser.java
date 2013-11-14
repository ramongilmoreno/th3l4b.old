package com.th3l4b.srm.runtime;

public interface IDatabaseParser<R, DBIN, DBOUT, ARGIN, ARGOUT> extends
		IBaseParser<R, DBIN, DBOUT, ARGIN, ARGOUT> {

	R create();

	String table() throws Exception;

	String idColumn() throws Exception;

	String statusColumn() throws Exception;

	String[] fieldsColumns() throws Exception;

}
