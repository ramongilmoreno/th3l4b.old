package com.th3l4b.srm.codegen.java.androidruntime.sqlite;

import java.util.Map;

import android.database.sqlite.SQLiteDatabase;

import com.th3l4b.srm.codegen.java.basicruntime.update.IUpdateToolUpdater;
import com.th3l4b.srm.runtime.IIdentifier;
import com.th3l4b.srm.runtime.IModelUtils;
import com.th3l4b.srm.runtime.IRuntimeEntity;

public abstract class AbstractAndroidSQLiteUpdateToolUpdater implements
		IUpdateToolUpdater {

	protected abstract SQLiteDatabase getDatabase() throws Exception;

	@Override
	public void update(Map<IIdentifier, IRuntimeEntity<?>> updates,
			Map<IIdentifier, IRuntimeEntity<?>> originals, IModelUtils utils)
			throws Exception {
		String a;
	}

}
