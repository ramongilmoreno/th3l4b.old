package com.th3l4b.srm.codegen.java.sync.runtime;

import com.th3l4b.srm.runtime.IRuntimeEntity;

public interface IDiffContext {
	<R extends IRuntimeEntity<R>> IEntityDiff<R> getEntityDiff(
			Class<R> clazz) throws Exception;

}
