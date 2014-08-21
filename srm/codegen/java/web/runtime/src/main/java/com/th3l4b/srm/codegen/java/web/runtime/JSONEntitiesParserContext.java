package com.th3l4b.srm.codegen.java.web.runtime;

import java.io.Reader;
import java.io.Writer;

import com.th3l4b.srm.runtime.ISRMContext;
import com.th3l4b.srm.runtime.IToMapEntityParserContext;

public class JSONEntitiesParserContext {

	private Reader _reader;
	private Writer _writer;
	private ISRMContext<?> _srm;
	private IToMapEntityParserContext _toMap;

	public JSONEntitiesParserContext(Reader reader, ISRMContext<?> srm,
			IToMapEntityParserContext toMap) {
		this(reader, null, srm, toMap);
	}

	public JSONEntitiesParserContext(Writer writer, ISRMContext<?> srm,
			IToMapEntityParserContext toMap) {
		this(null, writer, srm, toMap);
	}

	public JSONEntitiesParserContext(Reader reader, Writer writer, ISRMContext<?> srm,
			IToMapEntityParserContext toMap) {
		_reader = reader;
		_writer = writer;
		_srm = srm;
		_toMap = toMap;
	}

	public Reader getReader() {
		return _reader;
	}

	public void setReader(Reader reader) {
		_reader = reader;
	}

	public Writer getWriter() {
		return _writer;
	}

	public void setWriter(Writer writer) {
		_writer = writer;
	}

	public ISRMContext<?> getSRM() {
		return _srm;
	}

	public void setSRM(ISRMContext<?> sRMContext) {
		_srm = sRMContext;
	}

	public IToMapEntityParserContext getToMap() {
		return _toMap;
	}

	public void setToMap(IToMapEntityParserContext toMap) {
		_toMap = toMap;
	}
}
