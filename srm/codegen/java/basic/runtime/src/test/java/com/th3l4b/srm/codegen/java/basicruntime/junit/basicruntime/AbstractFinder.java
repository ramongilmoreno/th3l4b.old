package com.th3l4b.srm.codegen.java.basicruntime.junit.basicruntime;

import com.th3l4b.srm.codegen.java.basicruntime.inmemory.AbstractInMemoryFinder;
import com.th3l4b.srm.codegen.java.basicruntime.inmemory.AbstractPredicateOfRelationship;
import com.th3l4b.srm.codegen.java.basicruntime.inmemory.Pair;
import com.th3l4b.srm.codegen.java.basicruntime.junit.IEntityA;
import com.th3l4b.srm.codegen.java.basicruntime.junit.IEntityB;
import com.th3l4b.srm.codegen.java.basicruntime.junit.ITestFinder;
import com.th3l4b.srm.runtime.IIdentifier;
import com.th3l4b.srm.runtime.IModelUtils;

public abstract class AbstractFinder extends AbstractInMemoryFinder implements
		ITestFinder {

	public AbstractFinder(IModelUtils utils) {
		super(utils);
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
