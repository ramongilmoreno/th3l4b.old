package com.th3l4b.screens.base.junit;

import java.io.PrintWriter;

import org.junit.Test;

import com.th3l4b.screens.base.ITreeOfScreens;
import com.th3l4b.screens.base.ScreensWebUtils;
import com.th3l4b.screens.base.utils.DefaultTreeOfScreens;

public class ScreenWebUtilsTest {
	@Test
	public void testDumpTree() throws Exception {
		ITreeOfScreens tree = new DefaultTreeOfScreens();
		tree.setRoot("A");
		tree.addScreen("B", "A");
		tree.addScreen("C", "A");
		tree.addScreen("D", "C");
		tree.addScreen("E", "C");

		PrintWriter out = new PrintWriter(System.out, true);
		ScreensWebUtils.dump(tree, out);
		out.flush();
	}
}
