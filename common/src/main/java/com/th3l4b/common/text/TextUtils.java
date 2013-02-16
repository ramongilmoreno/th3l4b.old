package com.th3l4b.common.text;

import java.io.PrintWriter;

public class TextUtils {
	public static void print(Object o, PrintWriter out) {
		if (o instanceof IPrintable) {
			((IPrintable) o).print(out);
		} else {
			out.println("" + o);
		}
	}
}
