package com.th3l4b.common.log;

import com.th3l4b.common.text.IPrintable;

public interface ILogger {
	void log (IPrintable item, ILogLevel level) throws Exception;
	void error (IPrintable item) throws Exception;
	void warning (IPrintable item) throws Exception;
	void message (IPrintable item) throws Exception;
	void debug (IPrintable item) throws Exception;
}
