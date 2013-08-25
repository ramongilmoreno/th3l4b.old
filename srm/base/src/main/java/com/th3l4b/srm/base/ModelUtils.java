package com.th3l4b.srm.base;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.th3l4b.common.named.INamed;
import com.th3l4b.common.text.ITextConstants;
import com.th3l4b.common.text.TextUtils;
import com.th3l4b.srm.base.original.IRelationship;
import com.th3l4b.srm.base.original.RelationshipType;

public class ModelUtils {

	public static final String SEPARATOR = " ";

	public static String mergeWithContext(String item, String context) {
		return context + "." + item;
	}

	public static void encode(Reader input, Writer out) throws IOException {
		for (Integer i : TextUtils.unicodeIterable(input)) {
			int c = i.intValue();
			if ((c < 33) || (c > 127) || (c == '\\') || (c == '\"')
					|| (c == ';')) {
				out.write("\\u");
				out.write(Integer.toString(c, 16));
				out.write(';');
			} else {
				out.write(c);
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
				out.write(SEPARATOR);
			}
			encode(s, out);
		}
	}

	public static Iterable<Reader> fromStringList(final Reader input)
			throws Exception {
		throw new UnsupportedOperationException("Not yet implemented");
	}

	protected static String notNull(String s) {
		return s != null ? s : ITextConstants.EMPTY_STRING;
	}

	public static String getRelationshipName(IRelationship relationship)
			throws Exception {
		String name = relationship.getName();
		if (name != null) {
			return name;
		}

		if (relationship.getType() == RelationshipType.manyToMany) {
			name = relationship.getEntity();
			if (name != null) {
				return name;
			} else {
				String a = null;
				{
					INamed direction = relationship.getReverse();
					if (direction != null) {
						a = direction.getName();
					} else {
						a = relationship.getFrom();
					}
				}
				String b = null;
				{
					INamed direction = relationship.getDirect();
					if (direction != null) {
						b = direction.getName();
					} else {
						b = relationship.getTo();
					}
				}

				return "" + a + " " + b;
			}
		} else {
			INamed direct = relationship.getDirect();
			return ""
					+ relationship.getFrom()
					+ " "
					+ (direct != null ? direct.getName() : relationship.getTo());
		}
	}

	public static List<String> stringAsList(String input) throws Exception {
		if (input == null) {
			return Collections.<String> emptyList();
		}
		ArrayList<String> r = new ArrayList<String>();
		StringWriter sw = new StringWriter();
		for (String s : input.split(SEPARATOR)) {
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
