package com.th3l4b.srm.codegen.java.basicruntime.junit;

import com.th3l4b.srm.codegen.java.basicruntime.storage.inmemory.AbstractInMemoryContainer;
import com.th3l4b.srm.codegen.java.basicruntime.storage.inmemory.IPredicate;
import com.th3l4b.srm.runtime.IIdentifier;
import com.th3l4b.srm.runtime.IRuntimeEntity;

public abstract class AbstractTest_Finder extends AbstractInMemoryContainer implements ITest_Finder {
		
	@Override
	public <T extends IRuntimeEntity<T>> Iterable<T> find(Class<T> clazz,
			IIdentifier identifier, String relationship) throws Exception {
		Predicate<?> p = null;
		if (clazz.isAssignableFrom(EntityA.class)) {
			if (relationship.equals("Relationship a")) {
				p = new Predicate<EntityA>(identifier) {
					@Override
					protected IIdentifier getTarget(EntityA src)
							throws Exception {
						return null;
					}
				};
				
			}
		}
		final Predicate<?> fp = (Predicate<?>) p;
		return find(clazz, new IPredicate<T>() {
			@Override
			public boolean accept(T arg) throws Exception {
				return fp.accept(arg);
			}
		});
	}

	@Override
	public <T extends IRuntimeEntity<T>> Iterable<T> all(Class<T> clazz)
			throws Exception {
		return find(clazz, new IPredicate<T>() {
			@Override
			public boolean accept(T arg) throws Exception {
				return true;
			}
		});
	}

	@Override
	public EntityA getEntityA(IIdentifier identifier) throws Exception {
		return find(EntityA.class, identifier);
	}

	@Override
	public Iterable<EntityA> findEntityA(IIdentifier from)
			throws Exception {
		return find(EntityA.class, from, "Entity A");
	}

}
