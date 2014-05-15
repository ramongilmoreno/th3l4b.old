package com.th3l4b.srm.codegen.java.basic.runtime.update;

import java.util.Map;

import com.th3l4b.srm.runtime.IIdentifier;
import com.th3l4b.srm.runtime.IModelUtils;
import com.th3l4b.srm.runtime.IRuntimeEntity;

public interface IUpdateToolFinder {
	Map<IIdentifier, IRuntimeEntity<?>> find(
			Map<IIdentifier, IRuntimeEntity<?>> input, IModelUtils utils)
			throws Exception;
}
