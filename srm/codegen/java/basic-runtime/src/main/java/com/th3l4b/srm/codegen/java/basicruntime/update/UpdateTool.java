package com.th3l4b.srm.codegen.java.basicruntime.update;

import java.util.LinkedHashMap;
import java.util.Map;

import com.th3l4b.srm.runtime.EntityStatus;
import com.th3l4b.srm.runtime.ICoordinates;
import com.th3l4b.srm.runtime.IIdentifier;
import com.th3l4b.srm.runtime.IModelUtils;
import com.th3l4b.srm.runtime.IRuntimeEntity;

public class UpdateTool {

	public static class Pair<A, B> {
		A _a;
		B _b;

		public Pair(A a, B b) {
			_a = a;
			_b = b;
		}

		public A getA() {
			return _a;
		}

		public B getB() {
			return _b;
		}
	}

	public static class Update<T extends IRuntimeEntity<?>> extends Pair<T, T> {
		public Update(T updated, T original) {
			super(updated, original);
		}

		public T getUpdated() {
			return getA();
		}

		public T getOriginal() {
			return getB();
		}

	};

	/**
	 * Notifies the listener.
	 */
	protected <T extends IRuntimeEntity<T>> void beforeChanges(
			Iterable<Update<T>> updates, UpdateContext context)
			throws Exception {
		IListener listener = context.getListener();
		for (Update<T> u : updates) {
			T a = u.getUpdated();
			switch (a.coordinates().getStatus()) {
			case Deleted:
				listener.deleted(a, u.getOriginal());
				break;
			case Modified:
				listener.updated(a, u.getOriginal());
				break;
			case New:
				listener.created(a);
				break;
			// These are unsupported status
			case Persisted:
			case Unknown:
			case Wild:
			default:
				throw new IllegalArgumentException("Unknown status: "
						+ a.coordinates().getStatus() + ", in entity " + a);
			}
		}
	}

	/**
	 * Does nothing.
	 */
	protected <T extends IRuntimeEntity<?>> void afterChanges(
			Iterable<Update<T>> updates, UpdateContext context)
			throws Exception {

	}

	private static <T extends IRuntimeEntity<T>> T clone(T source,
			IModelUtils utils) throws Exception {
		if (source != null) {
			Class<T> clazz = source.clazz();
			T r = utils.create(clazz);
			utils.copy(source, r, clazz);
			return r;
		} else {
			return null;
		}

	}

	private <T extends IRuntimeEntity<T>> void process(T updated,
			Map<IIdentifier, Update<T>> updates, UpdateContext context)
			throws Exception {
		IIdentifier id = updated.coordinates().getIdentifier();
		EntityStatus status = updated.coordinates().getStatus();
		Class<T> clazz = updated.clazz();
		T original = null;
		switch (status) {
		case Deleted:
		case Modified:
		case Unknown:
			original = (T) context.getFinder().find(clazz, id);
			break;
		case New:
			break;
		case Persisted:
		case Wild:
		default:
			return;
		}

		IModelUtils utils = context.getUtils();
		if (updated != null) {
			updated = clone(updated, utils);
		}
		if (original != null) {
			original = clone(original, utils);
		}

		if (status == EntityStatus.Unknown) {
			if (original == null) {
				updated.coordinates().setStatus(EntityStatus.New);
			} else {
				updated.coordinates().setStatus(EntityStatus.Modified);
			}
		}

		updates.put(updated.coordinates().getIdentifier(), new Update<T>(
				updated, original));
	}

	public <T extends IRuntimeEntity<T>> void update(Iterable<T> entities,
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

		beforeChanges(updates.values(), context);
		afterChanges(updates.values(), context);
	}
}
