package com.th3l4b.common.log;

import com.th3l4b.common.text.IPrintable;

public abstract class AbstractLog implements ILogger {

	@Override
	public void error(IPrintable item) throws Exception {
		log(item, ILogLevel.error);
	}

	@Override
	public void warning(IPrintable item) throws Exception {
		log(item, ILogLevel.warning);
	}

	@Override
	public void message(IPrintable item) throws Exception {
		log(item, ILogLevel.message);
	}

	@Override
	public void debug(IPrintable item) throws Exception {
		log(item, ILogLevel.debug);
	}

}
