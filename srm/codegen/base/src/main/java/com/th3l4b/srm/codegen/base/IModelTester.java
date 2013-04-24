package com.th3l4b.srm.codegen.base;

import com.th3l4b.common.log.ILogger;
import com.th3l4b.srm.base.original.IModel;

/**
 * Checks a model for possible errors.
 */
public interface IModelTester {
	void test(IModel model, ILogger logger) throws Exception;
}
