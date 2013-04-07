package com.th3l4b.srm.codegen.java.basicruntime.update;

import com.th3l4b.common.log.ILogger;
import com.th3l4b.srm.runtime.IFinder;

public class UpdateContext {

	protected IFinder _finder;
	protected IListener _listener;
	protected ILogger _logger;
	protected boolean _failFast = true;

	public IFinder getFinder() {
		return _finder;
	}

	public void setFinder(IFinder finder) {
		_finder = finder;
	}

	public IListener getListener() {
		return _listener;
	}

	public void setListener(IListener listener) {
		_listener = listener;
	}

	public ILogger getLogger() {
		return _logger;
	}

	public void setLogger(ILogger logger) {
		_logger = logger;
	}

	public boolean isFailFast() {
		return _failFast;
	}

	public void setFailFast(boolean failFast) {
		_failFast = failFast;
	}

}
