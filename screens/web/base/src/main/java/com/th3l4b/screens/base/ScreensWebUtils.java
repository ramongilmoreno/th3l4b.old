package com.th3l4b.screens.base;

import java.io.PrintWriter;
import java.util.Map;

import com.th3l4b.common.data.tree.ITree;
import com.th3l4b.common.text.IndentedWriter;
import com.th3l4b.common.text.TextUtils;

public class ScreensWebUtils {

	public static void dump(ITree<IScreen> tree, PrintWriter out)
			throws Exception {
		dumpRecursively(tree.getRoot(), null, tree, out);
	}

	protected static void dumpRecursively(IScreen screen, IScreen parent, ITree<IScreen> tree,
			PrintWriter out) throws Exception {
		PrintWriter iout = IndentedWriter.get(out);
		PrintWriter iiout = IndentedWriter.get(iout);
		out.print("\"");
		TextUtils.escapeJavaString(screen.getName(), out);
		out.println("\": {");
		iout.print("name: \"");
		TextUtils.escapeJavaString(screen.getName(), iout);
		iout.println("\",");
		iout.println("properties: {");
		boolean first = true;
		for (Map.Entry<String, String> e : screen.getProperties().entrySet()) {
			if (first) {
				first = false;
			} else {
				iiout.println(',');
			}
			iiout.print('\"');
			TextUtils.escapeJavaString(e.getKey(), iiout);
			iiout.print("\": \"");
			TextUtils.escapeJavaString(e.getValue(), iiout);
			iiout.print('\"');
		}
		if (!first) {
			iiout.println();
		}
		iout.println("},");
		iout.print("parent: ");
		if (parent == null) {
			iout.println("undefined");
		} else {
			iout.print("\"");
			TextUtils.escapeJavaString(parent.getName(), iout);
			iout.println("\"");
		}
		out.print("}");
		first = true;
		for (IScreen child : tree.getChildren(screen)) {
			first = false;
			out.println(',');
			dumpRecursively(child, screen, tree, out);

		}
		iiout.flush();
		iout.flush();
	}
}
