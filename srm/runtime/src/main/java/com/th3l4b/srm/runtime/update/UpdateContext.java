package com.th3l4b.srm.runtime.update;

import com.th3l4b.common.log.ILogger;
import com.th3l4b.srm.runtime.ILocator;

public class UpdateContext {
	
	protected ILocator _locator;
	protected IListener _listener;
	protected ILogger _logger;
	protected boolean _failFast = true;
	
	
	public ILocator getLocator() {
		return _locator;
	}
	public void setLocator(ILocator locator) {
		_locator = locator;
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
