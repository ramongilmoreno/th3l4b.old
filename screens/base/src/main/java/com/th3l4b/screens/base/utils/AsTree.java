package com.th3l4b.screens.base.utils;

import com.th3l4b.common.data.tree.AbstractUnmodifiableTree;
import com.th3l4b.common.data.tree.ITree;
import com.th3l4b.screens.base.ITreeOfScreens;

public class AsTree {
	public static ITree<String> getTree(final ITreeOfScreens source) {
		return new AbstractUnmodifiableTree<String>() {

			@Override
			public String getRoot() throws Exception {
				return source.getRoot();
			}

			@Override
			public Iterable<String> getChildren(String node) throws Exception {
				return source.children(node);
			}
		};
	}
}
