package com.th3l4b.screens.base.junit;

import java.io.PrintWriter;

import org.junit.Test;

import com.th3l4b.common.data.tree.DefaultTree;
import com.th3l4b.screens.base.DefaultScreen;
import com.th3l4b.screens.base.IScreen;
import com.th3l4b.screens.base.ScreensWebUtils;

public class ScreenWebUtilsTest {
	@Test
	public void testDumpTree () throws Exception {
		DefaultTree<IScreen> tree = new DefaultTree<IScreen>();
		DefaultScreen a = new DefaultScreen("A");
		tree.setRoot(a);
		DefaultScreen b = new DefaultScreen("B");
		tree.addChild(b, a);
		DefaultScreen c = new DefaultScreen("C");
		tree.addChild(c, a);
		DefaultScreen d = new DefaultScreen("D");
		tree.addChild(d, c);
		DefaultScreen e = new DefaultScreen("E");
		tree.addChild(e, c);
		
		PrintWriter out = new PrintWriter(System.out, true);
		ScreensWebUtils.dump(tree, out);
		out.flush();
	}
}
