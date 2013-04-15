package com.th3l4b.srm.codegen.java.basicruntime.junit.basicruntime;

import com.th3l4b.srm.codegen.java.basicruntime.junit.IEntityA;
import com.th3l4b.srm.codegen.java.basicruntime.junit.ITestFinder;
import com.th3l4b.srm.codegen.java.basicruntime.storage.inmemory.AbstractInMemoryContainer;
import com.th3l4b.srm.codegen.java.basicruntime.storage.inmemory.IPredicate;
import com.th3l4b.srm.runtime.IIdentifier;
import com.th3l4b.srm.runtime.IRuntimeEntity;

public abstract class AbstractFinder extends AbstractInMemoryContainer implements ITestFinder {
		
	@Override
	public <T extends IRuntimeEntity<T>> Iterable<T> find(Class<T> clazz,
			IIdentifier identifier, String relationship) throws Exception {
		Predicate<?> p = null;
		if (clazz.isAssignableFrom(IEntityA.class)) {
			if (relationship.equals("Relationship a")) {
				p = new Predicate<IEntityA>(identifier) {
					@Override
					protected IIdentifier getTarget(IEntityA src)
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
	public IEntityA getEntityA(IIdentifier identifier) throws Exception {
		return find(IEntityA.class, identifier);
	}

	@Override
	public Iterable<IEntityA> findEntityA(IIdentifier from)
			throws Exception {
		return find(IEntityA.class, from, "Entity A");
	}

}
