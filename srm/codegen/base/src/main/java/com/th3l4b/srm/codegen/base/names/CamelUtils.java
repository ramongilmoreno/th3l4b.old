package com.th3l4b.srm.codegen.base.names;

import com.th3l4b.common.text.StringPrintWriter;
import com.th3l4b.common.text.TextUtils;

public class CamelUtils {

	public static String toCamelCase(String src) {
		if (src == null) {
			return null;
		}
		StringPrintWriter sw = new StringPrintWriter();

		boolean uppercasePending = true;
		for (Integer i : TextUtils.unicodeIterable(src)) {
			int c = i.intValue();
			if (uppercasePending) {
				uppercasePending = false;
				TextUtils.print(Character.toUpperCase(c), sw);
			} else {
				if (Character.isWhitespace(c)) {
					uppercasePending = true;
					// Skip this character
				} else {
					TextUtils.print(c, sw);
				}
			}
		}

		return sw.getBuffer().toString();

	}
}
