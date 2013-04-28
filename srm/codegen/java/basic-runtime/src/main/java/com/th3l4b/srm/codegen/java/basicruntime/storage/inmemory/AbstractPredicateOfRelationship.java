package com.th3l4b.srm.codegen.java.basicruntime.storage.inmemory;

import com.th3l4b.srm.runtime.IIdentifier;

public abstract class AbstractPredicateOfRelationship<R, S> {

	private Class<R> _resultClass;
	private Class<S> _sourceClass;

	public AbstractPredicateOfRelationship(Class<R> resultClass, Class<S> sourceClass) {
		_resultClass = resultClass;
		_sourceClass = sourceClass;
	}
	
	public Class<S> getTargetClass() {
		return _sourceClass;
	}
	
	public Class<R> getResultClass() {
		return _resultClass;
	}

	protected abstract IIdentifier getSource(R candidateResult) throws Exception;

	public IPredicate<R> predicate(final IIdentifier target) {
		return new IPredicate<R>() {
			@Override
			public boolean accept(R arg) throws Exception {
				return AbstractModelUtils.compareStatic(getSource(arg), target);
			}

			@Override
			public Class<R> clazz() throws Exception {
				return AbstractPredicateOfRelationship.this._resultClass;
			}
		};
	}
	
	
}
