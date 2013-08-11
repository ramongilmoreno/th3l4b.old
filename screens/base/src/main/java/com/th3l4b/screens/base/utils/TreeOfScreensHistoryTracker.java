package com.th3l4b.screens.base.utils;

import java.util.ArrayList;

import com.th3l4b.screens.base.IScreen;

public class TreeOfScreensHistoryTracker {

	public static enum Action {
		add, remove, update;
	}

	public static class Change {
		public Action _action;
		public IScreen _screen;

		public Change(Action action, IScreen screen) {
			_action = action;
			_screen = screen;
		}
	}

	protected ArrayList<Change> _items = new ArrayList<Change>();

	public TreeOfScreensHistoryTracker() {
		clear();
	}

	public ITreeOfScreens track(ITreeOfScreens source) throws Exception {

		return new AbstractInterceptedTreeOfScreens(source) {
			@Override
			protected void itemUpdated(IScreen screen) throws Exception {
				_items.add(new Change(Action.update, screen));
			}

			@Override
			protected void itemRemoved(IScreen screen) throws Exception {
				_items.add(new Change(Action.remove, screen));
			}

			@Override
			protected void itemAdded(IScreen screen) throws Exception {
				_items.add(new Change(Action.add, screen));
			}

		};
	}

	public void clear() {
		_items.clear();
	}

}
