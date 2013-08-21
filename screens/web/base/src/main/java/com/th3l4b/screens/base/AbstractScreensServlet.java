package com.th3l4b.screens.base;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.th3l4b.common.text.ITextConstants;
import com.th3l4b.common.text.IndentedWriter;
import com.th3l4b.screens.base.interaction.IInteractionListener;
import com.th3l4b.screens.base.modifications.Modification;
import com.th3l4b.screens.base.modifications.ModificationsEngine;
import com.th3l4b.screens.base.utils.IScreensConfiguration;

/**
 * Skeleton for a servlet that handles modification and interactions on the tree
 * of screens.
 */
@SuppressWarnings("serial")
public abstract class AbstractScreensServlet extends HttpServlet {

	public static final String MODIFICATION_PARAMETER_NAME = "modification";
	public static final String ACTION_PARAMETER_NAME = "action";

	protected abstract IScreensConfiguration getConfiguration(
			HttpServletRequest request) throws Exception;

	protected abstract Locale getLocale(HttpServletRequest request,
			HttpServletResponse response) throws Exception;

	protected int intParameter(String parameter, HttpServletRequest request)
			throws Exception {
		String p = request.getParameter(parameter);
		if (p != null) {
			return Integer.parseInt(p);
		}
		return -1;
	}

	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			IScreensConfiguration configuration = getConfiguration(request);
			ITreeOfScreens tree = configuration.getTree();

			// Build a list of modifications and apply it
			ArrayList<Modification> modifications = new ArrayList<Modification>();
			int count = intParameter(MODIFICATION_PARAMETER_NAME, request);
			HashMap<String, String> map = new HashMap<String, String>();
			for (int i = 0; i < count; i++) {
				String prefix = MODIFICATION_PARAMETER_NAME + "." + i + ".";
				for (String s : Modification.ALL_ATTRIBUTES) {
					map.put(s, request.getParameter(prefix + s));
				}
				modifications.add(Modification.fromMap(map));
				map.clear();
			}
			ModificationsEngine.apply(modifications, tree);

			// Process action
			String action = request.getParameter(ACTION_PARAMETER_NAME);
			if (action != null) {
				Map<String, IInteractionListener> interactions = configuration
						.getInteractions();
				IInteractionListener interaction = interactions.get(action);
				if (interaction != null) {
					DefaultWebInteractionContext context = new DefaultWebInteractionContext();
					context.setTree(tree);
					context.setLocale(getLocale(request, response));
					context.setInteractions(interactions);
					context.setRequest(request);
					interaction.handleInteraction(action, context);
				} else {
					throw new IllegalArgumentException("Unknown action: "
							+ action);
				}
			}

			// Dump result.
			response.setContentType("application/javascript");
			response.setCharacterEncoding(ITextConstants.UTF_8);
			PrintWriter out = response.getWriter();
			PrintWriter iout = IndentedWriter.get(out);
			out.println("{");
			iout.println("ok: true,");
			iout.print("tree: ");
			ScreensWebUtils.dump(tree, iout);
			iout.println();
			out.println("}");
			iout.flush();
			out.flush();

		} catch (Exception e) {
			throw new ServletException(e);
		}
	}
}
