package com.th3l4b.testbed.integrated.model;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;

import com.th3l4b.common.data.nullsafe.NullSafe;
import com.th3l4b.srm.codegen.java.basic.runtime.DefaultIdentifier;
import com.th3l4b.srm.runtime.EntityStatus;
import com.th3l4b.srm.runtime.IIdentifier;
import com.th3l4b.srm.runtime.IModelUtils;
import com.th3l4b.srm.runtime.IRuntimeEntity;
import com.th3l4b.srm.runtime.SRMContextUtils;
import com.th3l4b.testbed.integrated.model.generated.IRegularEntity;
import com.th3l4b.testbed.integrated.model.generated.IStringLimitTest;
import com.th3l4b.testbed.integrated.model.generated.ITargetOfARelationship;
import com.th3l4b.testbed.integrated.model.generated.base.INameForIntegratedTestContext;
import com.th3l4b.testbed.integrated.model.generated.base.INameForIntegratedTestFinder;

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
		TestsUtils.assertEquals(EntityStatus.Modify, created.coordinates()
				.getStatus(), "Object created is in modify status");

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

		// Test creation by the unknown status
		IRegularEntity unknownToCreate = utils.create(IRegularEntity.class);
		unknownToCreate.coordinates().setStatus(EntityStatus.Unknown);
		String value3 = "Unknown";
		unknownToCreate.setField1(value3);
		context.update(SRMContextUtils.map(unknownToCreate));
		found = context.getFinder().find(IRegularEntity.class,
				unknownToCreate.coordinates().getIdentifier());
		TestsUtils.assertNotNull(found,
				"Could not find just unknown created entity");
		TestsUtils.assertEquals(EntityStatus.Persisted, found.coordinates()
				.getStatus(),
				"Unknown status created entity is not saved as persisted");
		TestsUtils.assertEquals(value3, found.getField1(),
				"Unknown status created entity was not saved");

		// Test modifications by the unknown status
		IRegularEntity unknownToModify = utils.create(IRegularEntity.class);
		unknownToModify.coordinates().setIdentifier(id);
		unknownToModify.coordinates().setStatus(EntityStatus.Unknown);
		unknownToModify.setField1(value3);
		context.update(SRMContextUtils.map(unknownToModify));
		found = context.getFinder().find(IRegularEntity.class, id);
		TestsUtils.assertEquals(value3, found.getField1(),
				"Unknown status entity did not cause a modification");

	}

	public void testRelationships() throws Exception {
		INameForIntegratedTestContext context = getContext();
		INameForIntegratedTestFinder finder = context.getFinder();
		IModelUtils utils = context.getUtils();

		IRegularEntity entity = null;
		{
			DefaultIdentifier id = new DefaultIdentifier(IRegularEntity.class);
			entity = utils.create(IRegularEntity.class);
			entity.coordinates().setIdentifier(id);
		}

		ITargetOfARelationship target = null;
		{
			DefaultIdentifier id = new DefaultIdentifier(
					ITargetOfARelationship.class);
			target = utils.create(ITargetOfARelationship.class);
			target.coordinates().setIdentifier(id);
		}
		entity.setTargetOfARelationship(target);

		Map<IIdentifier, IRuntimeEntity<?>> entities = new LinkedHashMap<IIdentifier, IRuntimeEntity<?>>();
		entities.put(entity.coordinates().getIdentifier(), entity);
		entities.put(target.coordinates().getIdentifier(), target);
		context.update(entities);

		// Find source regular entity
		IRegularEntity found = finder.find(IRegularEntity.class, entity
				.coordinates().getIdentifier());
		TestsUtils.assertNotNull(found,
				"Not found object source of relationship");
		ITargetOfARelationship foundTarget = found
				.getTargetOfARelationship(finder);
		TestsUtils.assertNotNull(foundTarget,
				"Not found object target of relationship");
		TestsUtils.assertEquals(target.coordinates().getIdentifier(),
				foundTarget.coordinates().getIdentifier(),
				"Target of relationship not the expected result");

		IRegularEntity reverseFound = null;
		for (IRegularEntity e : finder
				.findAllRegularEntityFromTargetOfARelationship(foundTarget)) {
			if (NullSafe.equals(e.coordinates().getIdentifier(), entity
					.coordinates().getIdentifier())) {
				reverseFound = e;
			}
		}

		TestsUtils.assertNotNull(reverseFound,
				"Source of relationship not found in reverse finder method");
	}

	public void testUndefinedValues() throws Exception {
		INameForIntegratedTestContext context = getContext();
		IModelUtils utils = context.getUtils();

		DefaultIdentifier id = new DefaultIdentifier(IRegularEntity.class);
		IRegularEntity created = utils.create(IRegularEntity.class);
		created.coordinates().setIdentifier(id);
		String value1 = "Hello";
		String value2 = "Good bye";
		created.setField1(value1);
		created.setField2(value2);
		String fieldNotSet = "Field not marked as set";
		TestsUtils.assertTrue(created.isSetField1(), fieldNotSet);
		TestsUtils.assertTrue(created.isSetField2(), fieldNotSet);
		context.update(SRMContextUtils.map(created));

		IRegularEntity found = context.getFinder().find(IRegularEntity.class,
				id);
		TestsUtils.assertTrue(found.isSetField1(), fieldNotSet);
		TestsUtils.assertTrue(found.isSetField2(), fieldNotSet);

		IRegularEntity update = utils.create(IRegularEntity.class);
		update.coordinates().setIdentifier(id);
		update.setField2(null);
		TestsUtils.assertFalse(update.isSetField1(), fieldNotSet);
		TestsUtils.assertTrue(update.isSetField2(), fieldNotSet);
		context.update(SRMContextUtils.map(update));

		found = context.getFinder().find(IRegularEntity.class, id);
		TestsUtils.assertTrue(found.isSetField1(), fieldNotSet);
		TestsUtils.assertFalse(found.isSetField2(), fieldNotSet);
	}

	public void testUnknownItem() throws Exception {
		DefaultIdentifier id = new DefaultIdentifier(IRegularEntity.class);
		IRegularEntity unknown = getContext().getFinder().find(
				IRegularEntity.class, id);
		TestsUtils.assertNotNull(unknown, "Unknown item not found");
		TestsUtils.assertEquals(EntityStatus.Unknown, unknown.coordinates()
				.getStatus(), "Unknown item status is not Unknown");
	}

	public void testEmptyFields() throws Exception {
		INameForIntegratedTestContext context = getContext();
		IModelUtils utils = context.getUtils();

		{
			DefaultIdentifier id = new DefaultIdentifier(IRegularEntity.class);
			IRegularEntity created = utils.create(IRegularEntity.class);
			created.coordinates().setIdentifier(id);
			String value1 = "Hello";
			created.setField1(value1);
			context.update(SRMContextUtils.map(created));

			IRegularEntity found = context.getFinder().getRegularEntity(id);
			TestsUtils.assertTrue(found.isSetField1(),
					"Field 1 is not retrieved as set");
			TestsUtils.assertFalse(found.isSetField2(),
					"Field 2 is retrieved as set");

			IRegularEntity updated = utils.create(IRegularEntity.class);
			updated.coordinates().setIdentifier(id);
			updated.coordinates().setStatus(EntityStatus.Modify);
			String value2 = "Good bye";
			updated.setField2(value2);
			context.update(SRMContextUtils.map(updated));

			found = context.getFinder().getRegularEntity(id);
			TestsUtils.assertTrue(found.isSetField1(),
					"Field 1 is not retrieved as set");
			TestsUtils.assertTrue(found.isSetField2(),
					"Field 2 is not retrieved as set");
		}
		{
			DefaultIdentifier id = new DefaultIdentifier(IRegularEntity.class);
			IRegularEntity created = utils.create(IRegularEntity.class);
			created.coordinates().setIdentifier(id);
			String value2 = "Good bye";
			created.setField2(value2);
			context.update(SRMContextUtils.map(created));

			IRegularEntity found = context.getFinder().getRegularEntity(id);
			TestsUtils.assertFalse(found.isSetField1(),
					"Field 1 is not retrieved as set");
			TestsUtils.assertTrue(found.isSetField2(),
					"Field 2 is retrieved as set");

			IRegularEntity updated = utils.create(IRegularEntity.class);
			updated.coordinates().setIdentifier(id);
			updated.coordinates().setStatus(EntityStatus.Modify);
			String value1 = "Hello";
			updated.setField1(value1);
			context.update(SRMContextUtils.map(updated));

			found = context.getFinder().getRegularEntity(id);
			TestsUtils.assertTrue(found.isSetField1(),
					"Field 1 is not retrieved as set");
			TestsUtils.assertTrue(found.isSetField2(),
					"Field 2 is not retrieved as set");
		}
	}

	public void testGetAll() throws Exception {
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

	public void testLimitStringTypes() throws Exception {
		INameForIntegratedTestContext context = getContext();
		IModelUtils utils = context.getUtils();

		DefaultIdentifier id = new DefaultIdentifier(IStringLimitTest.class);
		IStringLimitTest created = utils.create(IStringLimitTest.class);
		created.coordinates().setIdentifier(id);

		// Create a long string
		int limit = 10000;
		StringBuilder sb = new StringBuilder(limit);
		for (int i = 0; i < limit; i++) {
			sb.append("a");
		}
		String longString = sb.toString();
		created.setLabel(longString);
		created.setString(longString);
		created.setText(longString);
		context.update(SRMContextUtils.map(created));
		IStringLimitTest found = context.getFinder().find(
				IStringLimitTest.class, id);
		String msg = "Long string expected to be different";
		TestsUtils.assertDifferent(longString, found.getLabel(), msg);
		TestsUtils.assertDifferent(longString, found.getString(), msg);
		TestsUtils.assertDifferent(longString, found.getText(), msg);
	}

	public void testBackup() throws Exception {
		INameForIntegratedTestContext context = getContext();
		IModelUtils utils = context.getUtils();

		// Create two entities
		DefaultIdentifier id1 = new DefaultIdentifier(IRegularEntity.class);
		String value = "Hello";
		{
			IRegularEntity created = utils.create(IRegularEntity.class);
			created.coordinates().setIdentifier(id1);
			created.setField1(value);
			context.update(SRMContextUtils.map(created));
		}
		DefaultIdentifier id2 = new DefaultIdentifier(IRegularEntity.class);
		{
			IRegularEntity created = utils.create(IRegularEntity.class);
			created.coordinates().setIdentifier(id2);
			created.setField1("Bye");
			context.update(SRMContextUtils.map(created));
		}

		// Delete one of them
		{
			IRegularEntity created = utils.create(IRegularEntity.class);
			created.coordinates().setIdentifier(id1);
			created.coordinates().setStatus(EntityStatus.Remove);
			context.update(SRMContextUtils.map(created));
		}

		// Check both found in backup
		IRegularEntity found1 = null, found2 = null;
		for (IRuntimeEntity<?> entity : context.getFinder().backup()) {
			if ((found1 == null)
					&& NullSafe.equals(entity.coordinates().getIdentifier(),
							id1)) {
				found1 = (IRegularEntity) entity;
			} else if ((found2 == null)
					&& NullSafe.equals(entity.coordinates().getIdentifier(),
							id2)) {
				found2 = (IRegularEntity) entity;
			}

			if ((found1 != null) && (found2 != null)) {
				break;
			}
		}

		TestsUtils.assertNotNull(found1, "Deleted entity not found in backup");
		TestsUtils.assertEquals(value, found1.getField1(),
				"Deleted entity value does not match the original");
		TestsUtils.assertNotNull(found2,
				"Non deleted entity not found in backup");
	}

	public void everything() throws Exception {
		testCommonActions();
		testRelationships();
		testUndefinedValues();
		testGetAll();
		testEmptyFields();
		testUnknownItem();
		testLimitStringTypes();
		testBackup();
	}
}
