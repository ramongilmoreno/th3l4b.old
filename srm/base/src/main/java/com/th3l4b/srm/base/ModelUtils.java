package com.th3l4b.srm.base;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

public class ModelUtils {

	public static String mergeWithContext(String item, String context) {
		return context + "." + item;
	}

	public static void encode(Reader input, Writer out) throws IOException {
		int c = -1;
		while ((c = input.read()) != -1) {
			// Check surrogates
			char ch = (char) c;
			int v = -1;
			if (Character.isHighSurrogate(ch)) {
				char low = (char) input.read();
				if (!Character.isLowSurrogate(low)) {
					throw new IllegalArgumentException(
							"Found low surrogate without high. High: " + ch
									+ ", candidate low: " + low);
				}
				if (!Character.isSurrogatePair(ch, low)) {
					throw new IllegalArgumentException(
							"Candidate surrogates were not surrogates: " + ch
									+ ", candidate low: " + low);
				}
				v = Character.toCodePoint(ch, low);
			} else if ((c < 33) || (c > 127) || (c == '\\') || (c == '\"')
					|| (c == ';')) {
				v = c;
			} else {
				out.write(ch);
			}

			if (v != -1) {
				out.write("\\u");
				out.write(Integer.toString(v, Character.MAX_RADIX));
				out.write(';');
			}
		}
	}

	public static void decode(Reader input, Writer out) throws Exception {
		int c = -1;
		StringWriter sw = new StringWriter(10);
		while ((c = input.read()) != -1) {
			if (c == '\\') {
				sw.getBuffer().setLength(0);
				boolean ok = false;
				while ((c = input.read()) != -1) {
					if (c == ';') {
						if (sw.getBuffer().length() > 0) {
							int v = Integer.parseInt(sw.getBuffer().toString(),
									Character.MAX_RADIX);
							for (char ch : Character.toChars(v)) {
								out.write(ch);
							}
							ok = true;
						}
					}
				}
				if (!ok) {
					throw new Exception("Uncomplete escaped character");
				}
			}

			// Check surrogates
			char ch = (char) c;
			int v = -1;
			if (Character.isHighSurrogate(ch)) {
				char low = (char) input.read();
				if (!Character.isLowSurrogate(low)) {
					throw new IllegalArgumentException(
							"Found low surrogate without high. High: " + ch
									+ ", low: " + low);
				}
				v = Character.toCodePoint(ch, low);
			} else if ((c < 33) || (c > 127) || (c == '\\') || (c == '\"')
					|| (c == ';')) {
				v = c;
			} else {
				out.write(ch);
			}

			if (v != -1) {
				out.write("\\u");
				out.write(Integer.toString(v, Character.MAX_RADIX));
				out.write(';');
			}
		}
	}

	public static String relationshipName(String from, String to,
			String direct, String reverse) {
		StringWriter sw = new StringWriter();
		try {
			encode(new StringReader(from), sw);
			encode(new StringReader(to), sw);
			encode(new StringReader(direct), sw);
			encode(new StringReader(reverse), sw);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return sw.getBuffer().toString();
	}
}
