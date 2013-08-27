package com.th3l4b.screens.base.junit;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import com.th3l4b.screens.base.IScreensContants;
import com.th3l4b.screens.base.utils.DefaultTreeOfScreens;

public class TreeOfScreensTest implements IScreensContants {

	private String[] toArray(Iterable<String> it) {
		ArrayList<String> r = new ArrayList<String>();
		for (String i : it) {
			r.add(i);
		}
		return r.toArray(new String[r.size()]);
	}

	@Test
	public void testOrder() throws Exception {
		DefaultTreeOfScreens tree = new DefaultTreeOfScreens();
		tree.setRoot("a");
		tree.addScreen("b", "a");
		tree.addScreen("c", "a");
		tree.setProperty("b", ORDER_INDEX, "1");
		tree.setProperty("c", ORDER_INDEX, "2");
		Assert.assertArrayEquals(new String[] { "b", "c" },
				toArray(tree.children("a")));
		tree.setProperty("b", ORDER_INDEX, "3");
		Assert.assertArrayEquals(new String[] { "c", "b" },
				toArray(tree.children("a")));
	}

}
