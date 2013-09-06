package com.th3l4b.apps.shopping.base;

import java.util.LinkedHashMap;
import java.util.Map;

import com.th3l4b.apps.shopping.base.codegen.srm.IItem;
import com.th3l4b.apps.shopping.base.codegen.srm.INeed;
import com.th3l4b.apps.shopping.base.codegen.srm.IShoppingFinder;
import com.th3l4b.apps.shopping.base.codegen.srm.IStore;
import com.th3l4b.apps.shopping.base.codegen.srm.basicruntime.AbstractShoppingInMemoryFinder;
import com.th3l4b.apps.shopping.base.codegen.srm.basicruntime.ShoppingModelUtils;
import com.th3l4b.srm.codegen.java.basicruntime.ISRMContext;
import com.th3l4b.srm.codegen.java.basicruntime.storage.inmemory.AbstractInMemorySRMContext;
import com.th3l4b.srm.runtime.IIdentifier;
import com.th3l4b.srm.runtime.IModelUtils;
import com.th3l4b.srm.runtime.IRuntimeEntity;

public class Sample {

	public static abstract class AbstractSampleContext extends
			AbstractInMemorySRMContext<IShoppingFinder> {

		@Override
		protected IModelUtils createUtils() throws Exception {
			return new ShoppingModelUtils();
		}

		@Override
		protected IShoppingFinder createFinder() throws Exception {
			return new AbstractShoppingInMemoryFinder() {
				@Override
				protected Map<IIdentifier, IRuntimeEntity<?>> getEntities()
						throws Exception {
					return AbstractSampleContext.this.getEntities();
				}
			};
		}

	}

	public static Map<IIdentifier, IRuntimeEntity<?>> getEntities()
			throws Exception {
		final LinkedHashMap<IIdentifier, IRuntimeEntity<?>> r = new LinkedHashMap<IIdentifier, IRuntimeEntity<?>>();
		ISRMContext<IShoppingFinder> context = new AbstractSampleContext() {
			@Override
			protected Map<IIdentifier, IRuntimeEntity<?>> getEntities()
					throws Exception {
				return r;
			}
		};

		LinkedHashMap<IIdentifier, IRuntimeEntity<?>> updates = new LinkedHashMap<IIdentifier, IRuntimeEntity<?>>();
		// Create some items, stores and needs
		{
			String[] values = { "Toilet paper", "Bread", "Eggs", "Olive oil",
					"Milk" };
			for (String i : values) {
				// Item
				IItem item = context.getUtils().create(IItem.class);
				item.setName(i);
				updates.put(item.coordinates().getIdentifier(), item);
				// And need
				INeed need = context.getUtils().create(INeed.class);
				need.setItem(item);
				updates.put(need.coordinates().getIdentifier(), need);
			}
		}
		{
			String[] values = { "Mercadona", "Carrefour",
					"El Corte Ingl\u00e9s", "Ultramarinos Gil" };
			for (String i : values) {
				IStore store = context.getUtils().create(IStore.class);
				store.setName(i);
				updates.put(store.coordinates().getIdentifier(), store);
			}
		}

		context.update(updates);
		return r;
	}

	public static void main(String[] args) throws Exception {
		System.out.println(Sample.getEntities());
	}
}
