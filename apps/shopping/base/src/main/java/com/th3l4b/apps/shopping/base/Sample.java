package com.th3l4b.apps.shopping.base;

import java.util.LinkedHashMap;
import java.util.Map;

import com.th3l4b.apps.shopping.base.codegen.srm.IItem;
import com.th3l4b.apps.shopping.base.codegen.srm.INeed;
import com.th3l4b.apps.shopping.base.codegen.srm.IStore;
import com.th3l4b.apps.shopping.base.codegen.srm.basicruntime.ShoppingModelUtils;
import com.th3l4b.srm.codegen.java.basicruntime.storage.inmemory.AbstractInMemoryUpdaterAndFinder;
import com.th3l4b.srm.codegen.java.basicruntime.update.UpdateTool;
import com.th3l4b.srm.runtime.IIdentifier;
import com.th3l4b.srm.runtime.IModelUtils;
import com.th3l4b.srm.runtime.IRuntimeEntity;

public class Sample {

	public static Map<IIdentifier, IRuntimeEntity<?>> getEntities()
			throws Exception {
		final LinkedHashMap<IIdentifier, IRuntimeEntity<?>> r = new LinkedHashMap<IIdentifier, IRuntimeEntity<?>>();
		LinkedHashMap<IIdentifier, IRuntimeEntity<?>> updates = new LinkedHashMap<IIdentifier, IRuntimeEntity<?>>();
		IModelUtils utils = new ShoppingModelUtils();
		// Create some items, stores and needs
		{
			String[] values = { "Toilet paper", "Bread", "Eggs", "Olive oil",
					"Milk" };
			for (String i : values) {
				// Item
				IItem item = utils.create(IItem.class);
				item.setName(i);
				updates.put(item.coordinates().getIdentifier(), item);
				// And need
				INeed need = utils.create(INeed.class);
				need.setItem(item);
				updates.put(need.coordinates().getIdentifier(), need);
			}
		}
		{
			String[] values = { "Mercadona", "Carrefour",
					"El Corte Ingl\u00e9s", "Ultramarinos Gil" };
			for (String i : values) {
				IStore store = utils.create(IStore.class);
				store.setName(i);
				updates.put(store.coordinates().getIdentifier(), store);
			}
		}

		AbstractInMemoryUpdaterAndFinder updater = new AbstractInMemoryUpdaterAndFinder() {
			@Override
			protected Map<IIdentifier, IRuntimeEntity<?>> getEntities()
					throws Exception {
				return r;
			}
		};
		UpdateTool tool = new UpdateTool();
		tool.process(updates, updater, updater, utils);
		return r;
	}

	public static void main(String[] args) throws Exception {
		System.out.println(Sample.getEntities());
	}
}
