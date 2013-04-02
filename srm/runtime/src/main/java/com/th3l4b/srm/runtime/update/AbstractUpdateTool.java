package com.th3l4b.srm.runtime.update;

import java.util.ArrayList;

import com.ibm.gsk.ikeyman.util.Pair;
import com.th3l4b.srm.runtime.ICoordinates;
import com.th3l4b.srm.runtime.IEntityOfModel;
import com.th3l4b.srm.runtime.IRuntimeEntity;

public abstract class AbstractUpdateTool<T extends IEntityOfModel> {

	public static class Update<T extends IEntityOfModel> extends Pair<T, T> {
		public Update(T first, T second) {
			super(first, second);
		}
	};

	protected abstract void beforeChanges(Iterable<Update<T>> updates,
			UpdateContext context) throws Exception;

	protected abstract void afterChanges(Iterable<Update<T>> updates,
			UpdateContext context) throws Exception;

	public void update(Iterable<T> entities, UpdateContext context)
			throws Exception {
		ArrayList<Update<T>> updates = new ArrayList<Update<T>>();
		for (T entity : entities) {
			ICoordinates c = entity.coordinates();
			switch (c.getStatus()) {
			case Deleted:
				break;
			case Modified:
				break;
			case Persisted:
				break;
			case Unknown:
				break;
			case Wild:
				break;
			case New:
				break;
			default:
				throw new IllegalStateException(c.getStatus().name());
			}
		}

		beforeChanges(updates, context);
		afterChanges(updates, context);
	}
}
