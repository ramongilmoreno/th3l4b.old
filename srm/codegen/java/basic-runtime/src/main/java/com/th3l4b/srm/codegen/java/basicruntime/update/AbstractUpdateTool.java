package com.th3l4b.srm.codegen.java.basicruntime.update;

import java.util.LinkedHashMap;
import java.util.Map;

import com.th3l4b.srm.runtime.ICoordinates;
import com.th3l4b.srm.runtime.IIdentifier;
import com.th3l4b.srm.runtime.IRuntimeEntity;

public abstract class AbstractUpdateTool {
 
	public static class Pair<A, B> {
		A _a;
		B _b;
		public Pair(A a, B b) {
			_a = a;
			_b = b;
		}
	}

	public static class Update<T extends IRuntimeEntity<?>> extends Pair<T, T> {
		public Update(T first, T second) {
			super(first, second);
		}
	};

	/**
	 * Notifies the listener.
	 */
	protected <T extends IRuntimeEntity<?>> void beforeChanges(
			Iterable<Update<T>> updates, UpdateContext context)
			throws Exception {
	}

	/**
	 * Does nothing.
	 */
	protected <T extends IRuntimeEntity<?>> void afterChanges(
			Iterable<Update<T>> updates, UpdateContext context)
			throws Exception {
		
	}

	private <T extends IRuntimeEntity<?>> void process(T e,
			Map<IIdentifier, Update<T>> updates, UpdateContext context)
			throws Exception {
		IIdentifier id = e.coordinates().getIdentifier();
		context.getFinder().find(e.clazz(), id);
	}

	public <T extends IRuntimeEntity<?>> void update(Iterable<T> entities,
			UpdateContext context) throws Exception {
		LinkedHashMap<IIdentifier, Update<T>> updates = new LinkedHashMap<IIdentifier, Update<T>>();
		for (T entity : entities) {
			ICoordinates c = entity.coordinates();
			switch (c.getStatus()) {
			case Deleted:
			case Modified:
			case Persisted:
			case Unknown:
			case Wild:
			case New:
				process(entity, updates, context);
			default:
				throw new IllegalStateException(c.getStatus().name());
			}
		}

		//beforeChanges(updates.values(), context);
		//afterChanges(updates.values(), context);
	}
}
