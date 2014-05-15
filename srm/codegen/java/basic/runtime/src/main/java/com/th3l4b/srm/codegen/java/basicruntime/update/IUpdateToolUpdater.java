package com.th3l4b.srm.codegen.java.basicruntime.update;

import java.util.Map;

import com.th3l4b.srm.runtime.IIdentifier;
import com.th3l4b.srm.runtime.IModelUtils;
import com.th3l4b.srm.runtime.IRuntimeEntity;

public interface IUpdateToolUpdater {
	void update(Map<IIdentifier, IRuntimeEntity<?>> updates,
			Map<IIdentifier, IRuntimeEntity<?>> originals, IModelUtils utils)
			throws Exception;
}
