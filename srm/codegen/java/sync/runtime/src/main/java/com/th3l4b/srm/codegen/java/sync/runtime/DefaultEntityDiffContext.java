package com.th3l4b.srm.codegen.java.sync.runtime;

import com.th3l4b.srm.runtime.DefaultPerEntityContext;
import com.th3l4b.srm.runtime.IRuntimeEntity;

public class DefaultEntityDiffContext extends
		DefaultPerEntityContext<IEntityDiff<?>> implements
		IEntityDiffContext {
	
	@SuppressWarnings("unchecked")
	public <R extends IRuntimeEntity<R>> IEntityDiff<R> getEntityDiff(
			Class<R> clazz) throws Exception {
		return (IEntityDiff<R>) get(clazz);
	}

}

