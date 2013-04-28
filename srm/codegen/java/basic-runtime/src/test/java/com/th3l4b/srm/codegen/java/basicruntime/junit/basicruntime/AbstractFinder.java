package com.th3l4b.srm.codegen.java.basicruntime.junit.basicruntime;

import com.th3l4b.srm.codegen.java.basicruntime.junit.IEntityA;
import com.th3l4b.srm.codegen.java.basicruntime.junit.IEntityB;
import com.th3l4b.srm.codegen.java.basicruntime.junit.ITestFinder;
import com.th3l4b.srm.codegen.java.basicruntime.storage.inmemory.AbstractInMemoryContainer;
import com.th3l4b.srm.codegen.java.basicruntime.storage.inmemory.AbstractPredicateOfRelationship;
import com.th3l4b.srm.codegen.java.basicruntime.storage.inmemory.Pair;
import com.th3l4b.srm.runtime.IIdentifier;

public abstract class AbstractFinder extends AbstractInMemoryContainer
		implements ITestFinder {

	public AbstractFinder() {
		_map.put(new Pair(IEntityB.class.getName(), "Entity A"),
				new AbstractPredicateOfRelationship<IEntityA, IEntityB>(
						IEntityA.class, IEntityB.class) {
					@Override
					protected IIdentifier getSource(IEntityA candidateResult)
							throws Exception {
						return candidateResult.getEntityB();
					}
				});
	}

	@Override
	public IEntityA getEntityA(IIdentifier identifier) throws Exception {
		return find(IEntityA.class, identifier);
	}

	@Override
	public Iterable<IEntityA> allEntityA() throws Exception {
		return all(IEntityA.class);
	}

	@Override
	public Iterable<IEntityA> findEntityAFromB(IIdentifier from)
			throws Exception {
		return find(IEntityA.class, IEntityB.class, from, "Entity A");
	}

}
