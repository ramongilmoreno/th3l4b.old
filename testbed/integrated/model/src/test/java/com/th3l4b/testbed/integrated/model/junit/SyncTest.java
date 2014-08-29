package com.th3l4b.testbed.integrated.model.junit;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.th3l4b.srm.codegen.java.sync.runtime.AbstractSyncTool;
import com.th3l4b.srm.codegen.java.sync.runtime.IDiffContext;
import com.th3l4b.srm.runtime.EntityStatus;
import com.th3l4b.srm.runtime.IIdentifier;
import com.th3l4b.srm.runtime.IRuntimeEntity;
import com.th3l4b.srm.runtime.SRMContextUtils;
import com.th3l4b.testbed.integrated.model.generated.ISyncEntity;
import com.th3l4b.testbed.integrated.model.generated.base.INameForIntegratedTestContext;
import com.th3l4b.testbed.integrated.model.generated.base.INameForIntegratedTestFinder;
import com.th3l4b.testbed.integrated.model.generated.inmemory.AbstractNameForIntegratedTestInMemorySRMContext;
import com.th3l4b.testbed.integrated.model.generated.sync.AbstractNameForIntegratedTestUpdateFilter;
import com.th3l4b.testbed.integrated.model.generated.sync.NameForIntegratedTestDiffContext;

public class SyncTest {

	public void test(final INameForIntegratedTestContext left,
			final INameForIntegratedTestContext right) throws Exception {
		final HashSet<IIdentifier> leftUpdates = new HashSet<IIdentifier>();
		final AbstractNameForIntegratedTestUpdateFilter leftContext = new AbstractNameForIntegratedTestUpdateFilter(
				left) {
			@Override
			protected void recordUpdate(
					Map<IIdentifier, IRuntimeEntity<?>> entities)
					throws Exception {
				for (IIdentifier id : entities.keySet()) {
					leftUpdates.add(id);
				}
			}
		};

		// Create an entity at source
		String valueA = "Hello";
		ISyncEntity originalEntity = leftContext.getUtils().create(
				ISyncEntity.class);
		originalEntity.setA(valueA);
		leftContext.update(SRMContextUtils.map(originalEntity));

		// Check
		Assert.assertEquals("Modification not tracked", 1, leftUpdates.size());
		Assert.assertEquals("Modification incorrectly tracked", originalEntity
				.coordinates().getIdentifier(), leftUpdates.iterator().next());

		// Retrieve changes
		LinkedHashMap<IIdentifier, IRuntimeEntity<?>> changes = new LinkedHashMap<IIdentifier, IRuntimeEntity<?>>();
		for (IIdentifier id : leftUpdates) {
			changes.put(id, left.getFinder().find(ISyncEntity.class, id));
		}

		// Create target context
		final NameForIntegratedTestDiffContext rightDiffContext = new NameForIntegratedTestDiffContext();
		AbstractSyncTool<INameForIntegratedTestFinder, INameForIntegratedTestContext> rightSync = new AbstractSyncTool<INameForIntegratedTestFinder, INameForIntegratedTestContext>() {
			@Override
			protected INameForIntegratedTestContext getContext()
					throws Exception {
				return right;
			}

			@Override
			protected IDiffContext getDiffContext() throws Exception {
				return rightDiffContext;
			}
		};
		right.update(rightSync.sync(changes));

		// Find item in right context
		ISyncEntity find = right.getFinder().find(ISyncEntity.class,
				originalEntity.coordinates().getIdentifier());
		Assert.assertNotNull("Synchronized item not found", find);
		Assert.assertEquals("Synchronized item attribute incorrect", valueA,
				find.getA());
		Assert.assertEquals("Synchronized item status not correct",
				EntityStatus.Persisted, find.coordinates().getStatus());

		// Delete item in right context
		ISyncEntity deleteInRight = right.getUtils().create(ISyncEntity.class);
		deleteInRight.coordinates().setIdentifier(
				find.coordinates().getIdentifier());
		deleteInRight.coordinates().setStatus(EntityStatus.Remove);
		right.update(SRMContextUtils.map(deleteInRight));

		// Apply to left
		final NameForIntegratedTestDiffContext leftDiffContext = new NameForIntegratedTestDiffContext();
		AbstractSyncTool<INameForIntegratedTestFinder, INameForIntegratedTestContext> leftSync = new AbstractSyncTool<INameForIntegratedTestFinder, INameForIntegratedTestContext>() {
			@Override
			protected INameForIntegratedTestContext getContext()
					throws Exception {
				return left;
			}

			@Override
			protected IDiffContext getDiffContext() throws Exception {
				return leftDiffContext;
			}
		};
		left.update(leftSync.sync(SRMContextUtils.map(right.getFinder()
				.backup())));

		// Ensure deleted in left
		ISyncEntity deletedInLeft = left.getFinder().getSyncEntity(
				deleteInRight.coordinates().getIdentifier());
		Assert.assertEquals("Synchronized item status in left not correct",
				EntityStatus.Deleted, deletedInLeft.coordinates().getStatus());
	}

	@Test
	public void test() throws Exception {

		final LinkedHashMap<IIdentifier, IRuntimeEntity<?>> ldata = new LinkedHashMap<IIdentifier, IRuntimeEntity<?>>();
		INameForIntegratedTestContext lcontext = new AbstractNameForIntegratedTestInMemorySRMContext() {
			@Override
			protected Map<IIdentifier, IRuntimeEntity<?>> getEntities()
					throws Exception {
				return ldata;
			}
		};

		final LinkedHashMap<IIdentifier, IRuntimeEntity<?>> rdata = new LinkedHashMap<IIdentifier, IRuntimeEntity<?>>();
		INameForIntegratedTestContext rcontext = new AbstractNameForIntegratedTestInMemorySRMContext() {
			@Override
			protected Map<IIdentifier, IRuntimeEntity<?>> getEntities()
					throws Exception {
				return rdata;
			}
		};

		test(lcontext, rcontext);
	}
}
