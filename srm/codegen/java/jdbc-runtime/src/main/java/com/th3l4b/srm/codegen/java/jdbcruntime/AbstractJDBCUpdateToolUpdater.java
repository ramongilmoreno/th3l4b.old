package com.th3l4b.srm.codegen.java.jdbcruntime;

import java.sql.Connection;

import com.th3l4b.srm.codegen.java.basicruntime.update.AbstractUpdateToolUpdater;
import com.th3l4b.srm.runtime.IModelUtils;
import com.th3l4b.srm.runtime.IRuntimeEntity;

public abstract class AbstractJDBCUpdateToolUpdater extends
		AbstractUpdateToolUpdater {

	protected abstract Connection getConnection() throws Exception;

	@Override
	protected <T extends IRuntimeEntity<T>> void updateEntity(T entity,
			T original, IModelUtils utils) throws Exception {
		throw new UnsupportedOperationException("Not implemented yet.");
	}

}
