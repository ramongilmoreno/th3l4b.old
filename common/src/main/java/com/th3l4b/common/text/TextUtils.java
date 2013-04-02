package com.th3l4b.common.text;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.text.Normalizer;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class TextUtils {

	public static Iterable<Integer> unicodeIterable(String input) {
		return unicodeIterable(new StringReader(input));
	}

	/**
	 * Facility to code for() loops of characters from iterator. You can only
	 * invoke {@link Iterable#iterator()} once or an
	 * {@link IllegalArgumentException} witll be thrown.
	 */
	public static Iterable<Integer> unicodeIterable(final Reader input) {
		return new Iterable<Integer>() {

			boolean _first = true;

			@Override
			public Iterator<Integer> iterator() {
				if (_first) {
					_first = false;
					return unicodeIterator(input);
				} else {
					throw new IllegalStateException(
							"Called twice for the iterator. Only once supported.");
				}
			}
		};
	}

	public static Iterator<Integer> unicodeIterator(String input) {
		return unicodeIterator(new StringReader(input));
	}

	/**
	 * Iterates an input reader by unicode characters.
	 */
	public static Iterator<Integer> unicodeIterator(final Reader input) {
		return new Iterator<Integer>() {

			// A -2 means that the input reader shall be inspected to find new
			// characters.
			// A -1 means that the EOF of the reader was found.
			int _current = -2;

			@Override
			public boolean hasNext() {
				if (_current == -1) {
					// Detect the -1 value as EOF
					return false;
				} else if (_current >= 0) {
					// Found a value for which next() has not been called yet.
					return true;
				} else {
					// Proceed
					try {
						int c = input.read();
						if (c != -1) {
							// Check surrogates
							char ch = (char) c;
							if (Character.isHighSurrogate(ch)) {
								char low = (char) input.read();
								if (!Character.isLowSurrogate(low)) {
									throw new IllegalArgumentException(
											"Found low surrogate without high. High: "
													+ ch + ", candidate low: "
													+ low);
								}
								if (!Character.isSurrogatePair(ch, low)) {
									throw new IllegalArgumentException(
											"Candidate surrogates were not surrogates: "
													+ ch + ", candidate low: "
													+ low);
								}
								c = Character.toCodePoint(ch, low);
							}
						}
						_current = c;
						return _current >= 0;

					} catch (IOException ioe) {
						throw new RuntimeException(ioe);
					}
				}
			}

			@Override
			public Integer next() {
				if (!hasNext()) {
					throw new NoSuchElementException();
				}
				int r = _current;
				// Next character needs to be read from input.
				_current = -2;

				return new Integer(r);
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}

		};
	}

	public static void write(int codePoint, Writer out) throws IOException {
		if (Character.isSupplementaryCodePoint(codePoint)) {
			out.write(Character.toChars(codePoint));
		} else {
			out.write(codePoint);
		}
	}

	public static void print(int codePoint, PrintWriter out) {
		if (Character.isSupplementaryCodePoint(codePoint)) {
			out.write(Character.toChars(codePoint));
		} else {
			out.write(codePoint);
		}
	}

	public static void print(Object o, PrintWriter out) {
		if (o instanceof IPrintable) {
			((IPrintable) o).print(out);
		} else {
			out.println("" + o);
		}
	}

	/**
	 * Get an ASCII representation of the input string.
	 */
	public static String ASCII(String input) {
		// http://stackoverflow.com/questions/3322152/java-getting-rid-of-accents-and-converting-them-to-regular-letters
		return Normalizer.normalize(input, Normalizer.Form.NFD).replaceAll(
				"[^\\p{ASCII}]", "");
	}

	public static String cIdentifier(String input) {
		return ASCII(input).replaceAll("[^A-Za-z0-9]", "");
	}

	public static String toString(IPrintable printable) {
		StringWriter out = new StringWriter();
		PrintWriter pw = new PrintWriter(out);
		printable.print(pw);
		pw.flush();
		out.flush();
		return out.getBuffer().toString();
	}

	public static IPrintable toPrintable(final String string) {
		return new IPrintable() {
			@Override
			public void print(PrintWriter out) {
				out.println(string);
			}

		};
	}
}
