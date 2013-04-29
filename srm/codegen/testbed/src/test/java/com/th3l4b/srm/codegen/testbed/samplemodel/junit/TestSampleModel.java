package com.th3l4b.srm.codegen.testbed.samplemodel.junit;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.th3l4b.srm.codegen.testbed.samplemodel.IDepartment;
import com.th3l4b.srm.codegen.testbed.samplemodel.IOfficeFinder;
import com.th3l4b.srm.codegen.testbed.samplemodel.basicruntime.AbstractOfficeInMemoryFinder;
import com.th3l4b.srm.codegen.testbed.samplemodel.basicruntime.OfficeModelUtils;
import com.th3l4b.srm.runtime.IIdentifier;
import com.th3l4b.srm.runtime.IRuntimeEntity;

public class TestSampleModel {

	protected static <T extends IRuntimeEntity<T>> T put(T t,
			Map<IIdentifier, IRuntimeEntity<?>> map) throws Exception {
		map.put(t.coordinates().getIdentifier(), t);
		return t;
	}

	@Test
	public void test() throws Exception {
		final LinkedHashMap<IIdentifier, IRuntimeEntity<?>> map = new LinkedHashMap<IIdentifier, IRuntimeEntity<?>>();
		OfficeModelUtils utils = new OfficeModelUtils();
		IDepartment dept1 = put(utils.create(IDepartment.class), map);
		dept1.setName("Department #1");
		IDepartment dept2 = put(utils.create(IDepartment.class), map);
		dept2.setName("Department #2");
		dept2.setParent(dept1);
		IDepartment dept3 = put(utils.create(IDepartment.class), map);
		dept3.setName("Department #3");
		dept3.setParent(dept1);
		IOfficeFinder finder = new AbstractOfficeInMemoryFinder() {
			@Override
			protected Map<IIdentifier, IRuntimeEntity<?>> getEntities()
					throws Exception {
				return map;
			}
		};
		
		HashSet<IDepartment> children = new HashSet<IDepartment>();
		children.add(dept2);
		children.add(dept3);
		for (IDepartment d: finder.findAllChildrenFromIDepartment(dept1)) {
			Assert.assertTrue("Unexpected child: " + d, children.contains(d));
			children.remove(d);
		}
		Assert.assertTrue("Missing children", children.isEmpty());
	}
}
