package com.th3l4b.testbed.integrated.model;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;

import com.th3l4b.srm.runtime.IIdentifier;
import com.th3l4b.srm.runtime.IRuntimeEntity;
import com.th3l4b.testbed.integrated.model.generated.base.INameForIntegratedTestContext;
import com.th3l4b.testbed.integrated.model.generated.inmemory.AbstractNameForIntegratedTestInMemorySRMContext;

public class InMemoryIntegratedTest {
	@Test
	public void test() throws Exception {
		final Map<IIdentifier, IRuntimeEntity<?>> map = new LinkedHashMap<IIdentifier, IRuntimeEntity<?>>();
		final AbstractNameForIntegratedTestInMemorySRMContext context = new AbstractNameForIntegratedTestInMemorySRMContext() {
			@Override
			protected Map<IIdentifier, IRuntimeEntity<?>> getEntities()
					throws Exception {
				return map;
			}
		};
		AbstractIntegratedTestTests tests = new AbstractIntegratedTestTests() {
			@Override
			protected INameForIntegratedTestContext getContext()
					throws Exception {
				return context;
			}
		};
		tests.everything();
	}
}
