package com.th3l4b.apps.slides;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Stack;

import com.th3l4b.apps.slides.Parser.Contents;
import com.th3l4b.common.data.tree.DefaultTree;
import com.th3l4b.common.data.tree.ITree;
import com.th3l4b.common.data.tree.TreeUtils;

/**
 * This is the main class of the presentation tools, and peforms the operations
 * that split the source presentation into multiple pages.
 */
public class Presentation {

	public static void main(String[] args) throws Exception {
		try {
		PrintWriter out = new PrintWriter(System.out);
		int i = 0;
		String title = args[i++];
		File input = new File(args[i++]);
		File output = new File(args[i++]);
		FileInputStream fis = new FileInputStream(input);
		InputStreamReader sr = new InputStreamReader(fis, "UTF-8");
		final BufferedReader br = new BufferedReader(sr);
		ITree<Contents> tree;
		try {
			tree = new Parser().parse(title, new Iterable<String>() {
				@Override
				public Iterator<String> iterator() {
					return new Iterator<String>() {
						boolean _end = false;
						String _last = null;

						@Override
						public boolean hasNext() {
							if (!_end) {
								if (_last == null) {
									try {
										_last = br.readLine();
										if (_last == null) {
											_end = true;
										}
									} catch (IOException e) {
										throw new RuntimeException(e);
									}
								}
								return _last != null;
							} else {
								return false;
							}
						}

						@Override
						public String next() {
							if (hasNext()) {
								String l = _last;
								_last = null;
								return l;
							} else {
								throw new IllegalStateException(
										"No remaining items.");
							}
						}

						@Override
						public void remove() {
							throw new UnsupportedOperationException();
						}

					};
				}

			});
		} finally {
			if (br != null) {
				try {
					br.close();
				} finally {
					if (sr != null) {
						try {
							sr.close();
						} finally {
							if (fis != null) {
								fis.close();
							}
						}
					}
				}
			}
		}

		// Process input
		TreeUtils.print(tree).print(out);

		// Reparent
		ITree<Contents> reparented = reparent(tree);
		TreeUtils.print(reparented).print(out);

		// Flatten
		ITree<Contents> flattened = flatten(reparented);
		TreeUtils.print(flattened).print(out);
		File outdir = new File(output, title);
		outdir.mkdirs();
		new HTMLRenderer().render(flattened, outdir);

		out.flush();
		} catch (Exception e) {
			System.out.println("Usage: java " + Presentation.class.getName() + " <title string> <input indented file> <output directory where a subdir with the title will be created>");
			
		}
	}

	/**
	 * Splits a tree on different pages based on whether the contents start with
	 * * (no break) or = (break). The root element is the same in all produced
	 * trees (as it is in the src tree), and it is not intended to be print or
	 * hold contents of the presentation itself.
	 */
	public static ITree<Contents> reparent(ITree<Contents> src)
			throws Exception {
		DefaultTree<Contents> r = new DefaultTree<Contents>();
		Contents root = src.getRoot();
		r.setRoot(root);
		for (Contents c : TreeUtils.dfs(src)) {
			if (c == root) {
				continue;
			}

			String s = c.getContents();
			Contents parent = c.getParent();
			if (s.startsWith("*")) {
				s = s.replaceAll("\\*[\\s]*", "");
			} else if (s.startsWith("=")) {
				s = s.replaceAll("\\=[\\s]*", "");

				// Check if the reparented item needs to appear with its
				// siblings (simply, that it is not hunging from root)
				if (parent != root) {
					Contents inline = new Contents(s + " (cont.)", 0, null);
					r.addChild(inline, parent);
				}

				// Replicate parents structure.
				Contents p = parent;
				Stack<Contents> parents = new Stack<Contents>();
				while ((p != null) && (p != root)) {
					Contents nc = new Contents(p.getContents(), 0, null);
					parents.push(nc);
					p = p.getParent();
				}

				// Reconstruct from root
				parent = root;
				while (!parents.isEmpty()) {
					Contents c2 = parents.pop();
					r.addChild(c2, parent);
					parent = c2;
				}
			}

			c.setContents(s);
			r.addChild(c, parent);
		}
		return r;
	}

	protected static int countChildren(Contents node, ITree<Contents> src)
			throws Exception {
		int r = 0;
		Iterator<Contents> iterator = src.getChildren(node).iterator();
		while (iterator.hasNext()) {
			iterator.next();
			r++;
		}
		return r;
	}

	/**
	 * Modifies a tree by keeping the root, iterating the first level of
	 * children and, for each, their removing son and grandsons from that first
	 * level when the son one child and no siblings. These items get put
	 * together in a single child of root with their contents concatenated:
	 * 
	 * <pre>
	 * Root
	 *     A
	 *         B
	 *             C
	 *             D
	 *     E
	 *         F
	 *         G
	 * </pre>
	 * 
	 * Becomes
	 * 
	 * <pre>
	 * Root
	 *     A - B
	 *         C
	 *         D
	 *     E
	 *         F
	 *         G
	 * </pre>
	 */
	public static ITree<Contents> flatten(ITree<Contents> src) throws Exception {
		DefaultTree<Contents> r = new DefaultTree<Contents>();
		Contents root = src.getRoot();
		r.setRoot(root);
		for (Contents p : src.getChildren(root)) {
			StringBuffer sb = new StringBuffer();
			// Find the first child with many children.
			while (countChildren(p, src) == 1) {
				if (sb.length() > 0) {
					sb.append(" - ");
				}
				sb.append(p.getContents());
				p = src.getChildren(p).iterator().next();
			}
			if (sb.length() > 0) {
				sb.append(" - ");
			}
			sb.append(p.getContents());
			p.setContents(sb.toString());

			ITree<Contents> subtree = TreeUtils.subtree(src, p);
			r.addChild(p, root);
			TreeUtils.copy(subtree, p, r, p);
		}
		return r;
	}

}
