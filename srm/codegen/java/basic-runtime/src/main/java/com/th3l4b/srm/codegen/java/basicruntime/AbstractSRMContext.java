package com.th3l4b.srm.codegen.java.basicruntime;

import java.util.Map;

import com.th3l4b.srm.codegen.java.basicruntime.update.IUpdateToolFinder;
import com.th3l4b.srm.codegen.java.basicruntime.update.IUpdateToolUpdater;
import com.th3l4b.srm.codegen.java.basicruntime.update.UpdateTool;
import com.th3l4b.srm.runtime.IIdentifier;
import com.th3l4b.srm.runtime.IModelUtils;
import com.th3l4b.srm.runtime.IRuntimeEntity;
import com.th3l4b.srm.runtime.ISRMContext;

public abstract class AbstractSRMContext<FINDER> implements ISRMContext<FINDER> {

	protected IModelUtils _utils;
	private FINDER _finder;

	protected abstract IModelUtils createUtils() throws Exception;

	protected abstract FINDER createFinder() throws Exception;

	@Override
	public IModelUtils getUtils() throws Exception {
		if (_utils == null) {
			_utils = createUtils();
		}
		return _utils;
	}

	@Override
	public void setUtils(IModelUtils utils) throws Exception {
		_utils = utils;
	}

	@Override
	public FINDER getFinder() throws Exception {
		if (_finder == null) {
			_finder = createFinder();
		}
		return _finder;
	}

	@Override
	public void setFinder(FINDER finder) throws Exception {
		_finder = finder;
	}

	protected abstract IUpdateToolFinder getUpdateToolFinder() throws Exception;

	protected abstract IUpdateToolUpdater getUpdateToolUpdater()
			throws Exception;

	@Override
	public void update(Map<IIdentifier, IRuntimeEntity<?>> entities)
			throws Exception {
		UpdateTool.process(entities, getUpdateToolFinder(),
				getUpdateToolUpdater(), getUtils());
	}

}
