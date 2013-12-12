package com.th3l4b.apps.slides;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;

import com.th3l4b.apps.slides.Parser.Contents;
import com.th3l4b.common.data.tree.ITree;
import com.th3l4b.common.data.tree.TreeUtils;

/**
 * Simple utility to render a presentation as HTML.
 */
public class HTMLRenderer {

	public void render(ITree<Contents> contents, File out) throws Exception {
		ArrayList<ITree<Contents>> pages = new ArrayList<ITree<Contents>>();
		for (Contents page : contents.getChildren(contents.getRoot())) {
			pages.add(TreeUtils.subtree(contents, page));
		}
		int index = 0;
		for (Iterator<ITree<Contents>> i = pages.iterator(); i.hasNext();) {
			render(contents.getRoot().getContents(), index++, pages, out);
			i.next();
		}
	}

	private String file(String title, int index,
			ArrayList<ITree<Contents>> pages) throws Exception {
		return "" + title + " - " + new DecimalFormat("00").format(index)
				+ ".html";
	}

	private String isSpecial(String input) {
		String img = "IMG:";
		if (input.startsWith(img)) {
			return input.substring(img.length()).trim();
		} else {
			return null;
		}

	}

	private String escape(String input) {
		if (input == null) {
			return null;
		} else {
			String img = isSpecial(input);
			if (img != null) {
				return "<a href=\"" + img + "\"><img src=\"" + img + "\"/></a>";
			}
			return input.replaceAll("&", "&amp;").replaceAll("\\\"", "&quot;")
					.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
		}
	}

	private void render(String title, int index,
			ArrayList<ITree<Contents>> pages, File fout) throws Exception {
		ITree<Contents> page = pages.get(index);
		String previous = index > 0 ? file(title, index - 1, pages) : null;
		String next = index < (pages.size() - 1) ? file(title, index + 1, pages)
				: null;
		File f = new File(fout, file(title, index, pages));

		FileOutputStream fos = new FileOutputStream(f);
		try {
			OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
			try {
				PrintWriter out = new PrintWriter(osw);
				out.println("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
				out.println("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
				out.println("<head><meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\"/><link rel=\"stylesheet\" type=\"text/css\" href=\"slides-resources/slides.css\"></head>");
				out.println("<body>");
				out.println("<div class=\"contents\">");
				Contents root = page.getRoot();
				out.println("<h1>" + escape(root.getContents()) + "</h1>");
				list(page, root, out);
				out.println("<div class=\"number\">" + (index + 1) + "/"
						+ pages.size() + "</div>");
				link("<", previous, "back", out);
				link(">", next, "forth", out);
				out.println("<div>");
				out.println("</body>");
				out.println("</html>");
			} finally {
				if (osw != null) {
					osw.close();
				}
			}

		} finally {
			if (fos != null) {
				fos.close();
			}
		}

	}

	private void list(ITree<Contents> page, Contents item, PrintWriter out)
			throws Exception {
		if (page.getChildren(item).iterator().hasNext()) {
			out.println("<ul>");
			for (Contents c : page.getChildren(item)) {
				String special = isSpecial(c.getContents());
				if (special != null) {
					out.println(escape(c.getContents()));
				} else {
					out.println("<li>" + escape(c.getContents()) + "</li>");
				}
				list(page, c, out);
			}
			out.println("</ul>");
		}
	}

	private void link(String text, String target, String clazz, PrintWriter out) {
		if (target != null) {
			out.println("<a href=\"" + escape(target) + "\" class=\"" + clazz
					+ "\">" + escape(text) + "</a>");
		}
	}
}
