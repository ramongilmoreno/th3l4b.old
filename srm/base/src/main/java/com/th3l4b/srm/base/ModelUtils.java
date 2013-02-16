package com.th3l4b.srm.base;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.th3l4b.common.named.NamedUtils;
import com.th3l4b.common.text.ITextConstants;
import com.th3l4b.srm.base.original.IRelationship;

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

	public static void toStringList(Iterable<? extends Reader> list, Writer out)
			throws Exception {
		boolean first = true;
		for (Reader s : list) {
			if (first) {
				first = false;
			} else {
				out.write(";");
			}
			encode(s, out);
		}
	}

	public static Iterable<String> fromStringList(Reader input)
			throws Exception {
		throw new UnsupportedOperationException("Not yet implemented");
	}

	protected static String notNull(String s) {
		return s != null ? s : ITextConstants.EMPTY_STRING;
	}

	public static void relationshipName(String from, String to, String direct,
			String reverse, Writer out) throws Exception {
		StringWriter sw = new StringWriter();
		try {
			Reader[] r = { new StringReader(notNull(from)),
					new StringReader(notNull(to)),
					new StringReader(notNull(direct)),
					new StringReader(notNull(reverse)) };
			toStringList(Arrays.asList(r), sw);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static String getRelationshipName(IRelationship relationship)
			throws Exception {
		StringWriter sw = new StringWriter();
		relationshipName(relationship.getFrom(), relationship.getTo(),
				NamedUtils.NAME_GETTER.get(relationship.getDirect()),
				NamedUtils.NAME_GETTER.get(relationship.getReverse()), sw);
		sw.flush();
		return sw.getBuffer().toString();
	}

	public static void applyRelationshipName(IRelationship relationship)
			throws Exception {
		relationship.setName(getRelationshipName(relationship));
	}

	public static List<String> stringAsList(String input) throws Exception {
		if (input == null) {
			return Collections.<String> emptyList();
		}
		ArrayList<String> r = new ArrayList<String>();
		StringWriter sw = new StringWriter();
		for (String s : input.split(";")) {
			sw.getBuffer().setLength(0);
			decode(new StringReader(s), sw);
			sw.flush();
			r.add(sw.getBuffer().toString());
		}
		return r;
	}

	public static String listAsString(List<String> input) throws Exception {
		if (input.size() == 0) {
			return ITextConstants.EMPTY_STRING;
		}

		boolean first = true;
		StringWriter out = new StringWriter();
		for (String s : input) {
			if (first) {
				first = false;
			} else {
				out.write(';');
			}
			encode(new StringReader(s), out);
		}
		out.flush();
		return out.getBuffer().toString();
	}

}
