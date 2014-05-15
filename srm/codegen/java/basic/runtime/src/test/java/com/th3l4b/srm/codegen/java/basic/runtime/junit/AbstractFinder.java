package com.th3l4b.srm.codegen.java.basic.runtime.junit;

import com.th3l4b.srm.codegen.java.basic.runtime.inmemory.AbstractInMemoryFinder;
import com.th3l4b.srm.codegen.java.basic.runtime.inmemory.AbstractPredicateOfRelationship;
import com.th3l4b.srm.codegen.java.basic.runtime.inmemory.Pair;
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
