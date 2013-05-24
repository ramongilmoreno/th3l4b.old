package com.th3l4b.screens.base;

import java.io.PrintWriter;

import com.th3l4b.common.data.tree.ITree;
import com.th3l4b.common.text.IndentedWriter;
import com.th3l4b.common.text.TextUtils;

public class ScreensWebUtils {
	
	public static void dump(ITree<IScreen> tree, PrintWriter out)
			throws Exception {
		dumpRecursively(tree.getRoot(), tree, out);
	}

	protected static void dumpRecursively(IScreen screen, ITree<IScreen> tree,
			PrintWriter out) throws Exception {
		PrintWriter iout = IndentedWriter.get(out);
		PrintWriter iiout = IndentedWriter.get(iout);
		out.print('{');
		iout.print("name: \"");
		TextUtils.escapeJavaString(screen.getName(), iout);
		iout.println("\",");
		iout.println("children: [");
		boolean first = true;
		for (IScreen child : tree.getChildren(screen)) {
			if (first) {
				first = false;
			} else {
				iiout.println(',');
			}
			dumpRecursively(child, tree, iiout);

		}
		iout.println("]");
		out.print('}');
		iiout.flush();
		iout.flush();
	}
}
