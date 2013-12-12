package com.th3l4b.apps.slides;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.th3l4b.common.data.predicate.IPredicate;
import com.th3l4b.common.data.predicate.PredicateUtils;
import com.th3l4b.common.data.tree.AbstractUnmodifiableTree;
import com.th3l4b.common.data.tree.ITree;
import com.th3l4b.common.text.IPrintable;

/**
 * Just reads a input file and composes an {@link ITree} of {@link Contents}
 * with the data in the file.
 */
public class Parser {

	public static class Contents implements IPrintable {
		int _depth;
		String _contents;
		Contents _parent;

		public Contents(String contents, int newDepth, Contents parent) {
			_contents = contents;
			_depth = newDepth;
			_parent = parent;
		}

		public String getContents() {
			return _contents;
		}

		public int getDepth() {
			return _depth;
		}

		public Contents getParent() {
			return _parent;
		}

		@Override
		public void print(PrintWriter out) {
			out.println(_contents);
		}

		public void setContents(String contents) {
			_contents = contents;
		}
	}

	/**
	 * Computes depth of a line and removes empty and comment # lines.
	 */
	protected int depth(String line) {
		String trimmed = line.trim();
		if ((line == null) || (trimmed.length() == 0)
				|| trimmed.startsWith("#")) {
			return -1;
		} else {
			line = line.replaceAll("\t", "        ").replaceAll("\\s", " ");
			int i = 0;
			for (; i < line.length(); i++) {
				if (line.charAt(i) != ' ') {
					break;
				}
			}
			return i;
		}
	}

	private String parse(String line, Iterator<String> lines, Contents context,
			Collection<Contents> all) {
		while (true) {
			if (line == null) {
				if (lines.hasNext()) {
					line = lines.next();
				}
			}
			if (line == null) {
				return null;
			}
			int depth = depth(line);
			if (depth == -1) {
				line = null;
				continue;
			}
			if (depth > context.getDepth()) {
				Contents n = new Contents(line.trim(), depth, context);
				all.add(n);
				line = parse(null, lines, n, all);
			} else {
				return line;
			}
		}
	}

	public ITree<Contents> parse(String rootTitle, Iterable<String> lines) {
		final Contents root = new Contents(rootTitle, -1, null);
		final ArrayList<Contents> all = new ArrayList<Contents>();
		parse(null, lines.iterator(), root, all);
		return new AbstractUnmodifiableTree<Contents>() {
			@Override
			public Contents getRoot() throws Exception {
				return root;
			}

			@Override
			public Iterable<Contents> getChildren(final Contents node)
					throws Exception {
				return PredicateUtils.filter(all, new IPredicate<Contents>() {
					@Override
					public boolean accept(Contents t) throws Exception {
						return t.getParent() == node;
					}
				});
			}
		};
	}
}
