package com.th3l4b.testbed.integrated.model;

import java.util.LinkedHashSet;

import com.th3l4b.srm.codegen.java.basicruntime.DefaultIdentifier;
import com.th3l4b.srm.runtime.EntityStatus;
import com.th3l4b.srm.runtime.IIdentifier;
import com.th3l4b.srm.runtime.IModelUtils;
import com.th3l4b.srm.runtime.SRMContextUtils;
import com.th3l4b.testbed.integrated.model.generated.IRegularEntity;
import com.th3l4b.testbed.integrated.model.generated.base.INameForIntegratedTestContext;

public abstract class AbstractIntegratedTestTests {

	protected abstract INameForIntegratedTestContext getContext()
			throws Exception;

	public void testCommonActions() throws Exception {
		INameForIntegratedTestContext context = getContext();
		IModelUtils utils = context.getUtils();

		DefaultIdentifier id = new DefaultIdentifier(IRegularEntity.class);
		IRegularEntity created = utils.create(IRegularEntity.class);
		created.coordinates().setIdentifier(id);
		String value1 = "Hello";
		String value2 = "Good bye";
		created.setField1(value1);
		created.setField2(value2);
		context.update(SRMContextUtils.map(created));
		TestsUtils.assertEquals(EntityStatus.New, created.coordinates()
				.getStatus(), "Object created is in new status");

		IRegularEntity found = context.getFinder().find(IRegularEntity.class,
				id);
		TestsUtils.assertDifferent(created, found,
				"Check returned object are not the same");
		TestsUtils.assertEquals(id, found.coordinates().getIdentifier(),
				"Testing identifiers match");
		TestsUtils.assertEquals(EntityStatus.Persisted, found.coordinates()
				.getStatus(), "Object saved is in persisted status");
		TestsUtils.assertEquals(value1, found.getField1(),
				"Field 1 match original");
		TestsUtils.assertEquals(value2, found.getField2(),
				"Field 2 match original");
	}

	public void testUnknownItem() throws Exception {
		DefaultIdentifier id = new DefaultIdentifier(IRegularEntity.class);
		IRegularEntity unknown = getContext().getFinder().find(
				IRegularEntity.class, id);
		TestsUtils.assertNotNull(unknown, "Unknown item not found");
		TestsUtils.assertEquals(EntityStatus.Unknown, unknown.coordinates()
				.getStatus(), "Unknown item status is not Unknown");
	}

	public void testAll() throws Exception {
		INameForIntegratedTestContext context = getContext();
		IModelUtils utils = context.getUtils();

		int count = 10;
		LinkedHashSet<IIdentifier> ids = new LinkedHashSet<IIdentifier>();
		for (int i = 0; i < count; i++) {
			ids.add(new DefaultIdentifier(IRegularEntity.class));
		}
		int i = 0;
		for (IIdentifier id : ids) {
			IRegularEntity newEntity = utils.create(IRegularEntity.class);
			newEntity.setField1("" + (++i));
			newEntity.coordinates().setIdentifier(id);
			context.update(SRMContextUtils.map(newEntity));
		}

		LinkedHashSet<IIdentifier> found = new LinkedHashSet<IIdentifier>();
		for (IRegularEntity fe : context.getFinder().all(IRegularEntity.class)) {
			IIdentifier id = fe.coordinates().getIdentifier();
			found.add(id);
		}
		TestsUtils.assertTrue(found.size() >= count,
				"Not enough items found. Expected: " + count + ", found: "
						+ found.size());

		for (IIdentifier id : ids) {
			TestsUtils.assertTrue(found.contains(id), "Not found: " + id);
		}
	}

	public void everything() throws Exception {
		testCommonActions();
		testAll();
		testUnknownItem();
	}
}
