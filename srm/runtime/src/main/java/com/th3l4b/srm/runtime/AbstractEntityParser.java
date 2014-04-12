package com.th3l4b.srm.runtime;

/**
 * Base for entity parser implementations.
 */
public abstract class AbstractEntityParser<IDENTIFIER_PARSER, STATUS_PARSER> {

	protected IDENTIFIER_PARSER _idsParser;
	protected STATUS_PARSER _statusParser;

	public AbstractEntityParser(IDENTIFIER_PARSER idsParser,
			STATUS_PARSER statusParser) {
		_idsParser = idsParser;
		_statusParser = statusParser;
	}

	public IDENTIFIER_PARSER getIdsParser() {
		return _idsParser;
	}

	public void setIdsParser(IDENTIFIER_PARSER idsParser) {
		_idsParser = idsParser;
	}

	public STATUS_PARSER getStatusParser() {
		return _statusParser;
	}

	public void setStatusParser(STATUS_PARSER statusParser) {
		_statusParser = statusParser;
	}

	String[] _allColumns;

	public abstract String idColumn() throws Exception;

	public abstract String statusColumn() throws Exception;

	public abstract String[] fieldsColumns() throws Exception;

	public String[] allColumns() throws Exception {
		if (_allColumns == null) {
			String[] fieldsColumns = fieldsColumns();
			String[] r = new String[fieldsColumns.length + 2];
			r[0] = idColumn();
			r[1] = statusColumn();
			System.arraycopy(fieldsColumns, 0, r, 2, fieldsColumns.length);
			_allColumns = r;
		}
		return _allColumns;
	}

}
