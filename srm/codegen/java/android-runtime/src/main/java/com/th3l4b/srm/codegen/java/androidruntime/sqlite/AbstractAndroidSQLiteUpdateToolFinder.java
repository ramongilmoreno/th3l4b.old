package com.th3l4b.srm.codegen.java.androidruntime.sqlite;

import java.util.HashMap;

import android.database.sqlite.SQLiteDatabase;

import com.th3l4b.srm.codegen.java.basicruntime.update.AbstractUpdateToolFinder;
import com.th3l4b.srm.runtime.IIdentifier;
import com.th3l4b.srm.runtime.IModelUtils;
import com.th3l4b.srm.runtime.IRuntimeEntity;

public abstract class AbstractAndroidSQLiteUpdateToolFinder extends
		AbstractUpdateToolFinder {

	protected abstract SQLiteDatabase getDatabase() throws Exception;

	@Override
	protected <T extends IRuntimeEntity<T>> void processEntity(
			IRuntimeEntity<T> entity,
			HashMap<IIdentifier, IRuntimeEntity<?>> r, IModelUtils utils)
			throws Exception {
		String a;
	}
}
