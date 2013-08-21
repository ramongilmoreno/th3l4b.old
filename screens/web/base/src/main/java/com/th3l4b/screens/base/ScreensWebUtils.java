package com.th3l4b.screens.base;

import java.io.PrintWriter;

import com.th3l4b.common.text.IndentedWriter;
import com.th3l4b.common.text.TextUtils;

public class ScreensWebUtils {

	public static void dump(ITreeOfScreens tree, PrintWriter out)
			throws Exception {
		PrintWriter iout = IndentedWriter.get(out);
		PrintWriter iiout = IndentedWriter.get(iout);
		out.println("{");
		iout.println("tree: {");
		dumpRecursively(tree.getRoot(), null, tree, iiout);
		iiout.println();
		iout.println("},");
		iout.print("root: \"");
		TextUtils.escapeJavaString(tree.getRoot(), iout);
		iout.println("\"");
		out.println("}");
		iiout.flush();
		iout.flush();
	}

	protected static void dumpRecursively(String screen, String parent,
			ITreeOfScreens tree, PrintWriter out) throws Exception {
		PrintWriter iout = IndentedWriter.get(out);
		PrintWriter iiout = IndentedWriter.get(iout);
		String prefix = "_tos_";
		out.print("\"" + prefix);
		TextUtils.escapeJavaString(screen, out);
		out.println("\": {");
		iout.print("name: \"");
		TextUtils.escapeJavaString(screen, iout);
		iout.println("\",");
		iout.println("properties: {");
		boolean first = true;
		for (String property : tree.properties(screen)) {
			if (first) {
				first = false;
			} else {
				iiout.println(',');
			}
			iiout.print("\"" + prefix);
			TextUtils.escapeJavaString(property, iiout);
			iiout.print("\": \"");
			TextUtils.escapeJavaString(tree.getProperty(screen, property),
					iiout);
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
			TextUtils.escapeJavaString(parent, iout);
			iout.println("\"");
		}
		out.print("}");
		first = true;
		for (String child : tree.children(screen)) {
			first = false;
			out.println(',');
			dumpRecursively(child, screen, tree, out);

		}
		iiout.flush();
		iout.flush();
	}
}
