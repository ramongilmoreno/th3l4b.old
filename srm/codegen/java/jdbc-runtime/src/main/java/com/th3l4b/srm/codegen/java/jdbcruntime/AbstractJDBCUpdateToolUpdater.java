package com.th3l4b.srm.codegen.java.jdbcruntime;

import java.sql.Connection;
import java.util.Map;

import com.th3l4b.srm.codegen.java.basicruntime.update.IUpdateToolUpdater;
import com.th3l4b.srm.runtime.IIdentifier;
import com.th3l4b.srm.runtime.IModelUtils;
import com.th3l4b.srm.runtime.IRuntimeEntity;

public abstract class AbstractJDBCUpdateToolUpdater implements
		IUpdateToolUpdater {

	protected abstract Connection getConnection() throws Exception;

	@Override
	public void update(Map<IIdentifier, IRuntimeEntity<?>> updates,
			Map<IIdentifier, IRuntimeEntity<?>> originals, IModelUtils utils)
			throws Exception {
		throw new UnsupportedOperationException("Not implemented yet");
	}

}
