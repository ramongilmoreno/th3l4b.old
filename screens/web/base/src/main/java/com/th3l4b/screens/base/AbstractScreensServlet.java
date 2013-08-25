package com.th3l4b.screens.base;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.th3l4b.common.data.nullsafe.NullSafe;
import com.th3l4b.common.text.ITextConstants;
import com.th3l4b.common.text.IndentedWriter;
import com.th3l4b.screens.base.interaction.IInteractionListener;
import com.th3l4b.screens.base.modifications.Modification;
import com.th3l4b.screens.base.modifications.ModificationsEngine;
import com.th3l4b.screens.base.modifications.TrackedTreeOfScreens;
import com.th3l4b.screens.base.utils.IScreensConfiguration;

/**
 * Skeleton for a servlet that handles modification and interactions on the tree
 * of screens.
 */
@SuppressWarnings("serial")
public abstract class AbstractScreensServlet extends HttpServlet {

	public static final String MODIFICATIONS_PARAMETER_NAME = "modifications";
	public static final String ACTION_PARAMETER_NAME = "action";
	public static final String MODIFICATIONS_ONLY_PARAMETER_NAME = "modificationsOnly";

	protected abstract IScreensConfiguration getConfiguration(
			IWebScreensClientDescriptor client) throws Exception;

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
			IWebScreensClientDescriptor client = new DefaultWebScreensClientDescriptor();
			ArrayList<Locale> locales = new ArrayList<Locale>();
			Enumeration<Locale> e = request.getLocales();
			while (e.hasMoreElements()) {
				locales.add(e.nextElement());
			}
			client.setLocales(locales);
			ArrayList<String> languages = new ArrayList<String>();
			languages.add(IScreensContants.INTERACTION_JAVA);
			languages.add(IScreensContants.INTERACTION_JAVASCRIPT);
			client.setLanguages(languages);
			client.setRequest(request);

			IScreensConfiguration context = getConfiguration(client);

			ITreeOfScreens tree = context.getTree();

			// Build a list of modifications and apply it
			ArrayList<Modification> modifications = new ArrayList<Modification>();
			int count = intParameter(MODIFICATIONS_PARAMETER_NAME, request);
			HashMap<String, String> map = new HashMap<String, String>();
			for (int i = 0; i < count; i++) {
				String prefix = MODIFICATIONS_PARAMETER_NAME + "." + i + ".";
				for (String s : Modification.ALL_ATTRIBUTES) {
					map.put(s, request.getParameter(prefix + s));
				}
				modifications.add(Modification.fromMap(map));
				map.clear();
			}
			ModificationsEngine.apply(modifications, tree);

			// Check if partial modifications shall be returned by the servlet.
			boolean modificationsOnly = false;
			modifications.clear();
			if (NullSafe.equals(
					request.getParameter(MODIFICATIONS_ONLY_PARAMETER_NAME),
					"true")) {
				modificationsOnly = true;
				tree = new TrackedTreeOfScreens(tree, modifications);
			}

			// Process action
			String action = request.getParameter(ACTION_PARAMETER_NAME);
			if (action != null) {
				ITreeOfScreens original = context.getTree();
				context.setTree(tree);
				String action2 = tree.getProperty(action,
						IScreensContants.INTERACTION_JAVA);
				Map<String, IInteractionListener> interactions = context
						.getInteractions();
				IInteractionListener interaction = interactions.get(action2);
				try {
					if (interaction != null) {
						interaction.handleInteraction(action, context, client);
					} else {
						throw new IllegalArgumentException(
								"Unknown action for screen: " + action);
					}
				} finally {
					context.setTree(original);
				}
			}

			// Dump result.
			response.setContentType("application/javascript");
			response.setCharacterEncoding(ITextConstants.UTF_8);
			PrintWriter out = response.getWriter();
			PrintWriter iout = IndentedWriter.get(out);
			out.println('{');
			iout.print("ok: true");
			if (modificationsOnly) {
				if (modifications.size() > 0) {
					iout.println(',');
					iout.print("modifications: ");
					ScreensWebUtils.dump(modifications, iout);
				}
			} else {
				iout.println(',');
				iout.print("tree: ");
				ScreensWebUtils.dump(tree, iout);
			}
			iout.println();
			out.println('}');
			iout.flush();
			out.flush();

		} catch (Exception e) {
			throw new ServletException(e);
		}
	}
}
