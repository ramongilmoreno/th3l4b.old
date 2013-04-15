package com.th3l4b.srm.codegen.java.basicruntime.update;

import com.th3l4b.srm.runtime.IFinder;
import com.th3l4b.srm.runtime.IModelUtils;

public class UpdateContext {

	protected IFinder _finder;
	protected IListener _listener;
	protected IModelUtils _utils;
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

	public boolean isFailFast() {
		return _failFast;
	}

	public void setFailFast(boolean failFast) {
		_failFast = failFast;
	}

	public IModelUtils getUtils() {
		return _utils;
	}

	public void setUtils(IModelUtils utils) {
		_utils = utils;
	}

}
