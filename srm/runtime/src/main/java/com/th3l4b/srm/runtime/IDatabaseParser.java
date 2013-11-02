package com.th3l4b.srm.runtime;

public interface IDatabaseParser<R, DBIN, DBOUT> {

	R create(Class<R> clazz);

	String table() throws Exception;

	String idColumn() throws Exception;

	String statusColumn() throws Exception;

	String[] fieldsColumns() throws Exception;

	void parse(R entity, int index, DBIN result) throws Exception;

	void set(R entity, int index, DBOUT statement) throws Exception;

}
