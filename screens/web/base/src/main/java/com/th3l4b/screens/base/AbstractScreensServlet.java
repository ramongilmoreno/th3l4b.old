package com.th3l4b.screens.base;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.th3l4b.common.data.tree.ITree;
import com.th3l4b.common.text.ITextConstants;
import com.th3l4b.screens.base.utils.IScreensConfiguration;

@SuppressWarnings("serial")
public abstract class AbstractScreensServlet extends HttpServlet {

	protected abstract IScreensConfiguration getConfiguration(
			HttpServletRequest request) throws Exception;

	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			IScreensConfiguration configuration = getConfiguration(request);
			
			// TODO: read actions
			ITree<IScreen> tree = configuration.getTree();

			// Dump result.
			response.setContentType("application/javascript");
			response.setCharacterEncoding(ITextConstants.UTF_8);
			ScreensWebUtils.dump(tree, response.getWriter());
			
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

}
