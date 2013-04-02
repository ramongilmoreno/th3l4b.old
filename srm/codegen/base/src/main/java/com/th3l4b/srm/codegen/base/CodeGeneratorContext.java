package com.th3l4b.srm.codegen.base;

import java.io.File;

import com.th3l4b.common.data.ICopyable;
import com.th3l4b.common.log.ILogger;
import com.th3l4b.types.base.ITypesContext;

public class CodeGeneratorContext implements ICopyable<CodeGeneratorContext> {

	private long _timestamp;

	private ILogger _log;
	
	private File _output;
	
	private boolean _overwrite;
	
	private ITypesContext _types;

	public long getTimestamp() {
		return _timestamp;
	}

	public void setTimestamp(long timestamp) {
		_timestamp = timestamp;
	}

	public ILogger getLog() {
		return _log;
	}

	public void setLog(ILogger log) {
		_log = log;
	}
	
	public File getOutput() {
		return _output;
	}
	
	public void setOutput(File output) {
		_output = output;
	}
	
	public boolean isOverwrite() {
		return _overwrite;
	}
	
	public void setOverwrite(boolean overwrite) {
		_overwrite = overwrite;
	}
	
	public ITypesContext getTypes() {
		return _types;
	}
	
	public void setTypes(ITypesContext types) {
		_types = types;
	}

	@Override
	public void copyTo(CodeGeneratorContext to) throws Exception {
		to.setTimestamp(getTimestamp());
		to.setLog(getLog());
		to.setOutput(getOutput());
		to.setOverwrite(isOverwrite());
		to.setTypes(getTypes());
	}

}
