package com.th3l4b.screens.base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.th3l4b.common.text.ITextConstants;
import com.th3l4b.screens.base.interaction.IInteractionListener;
import com.th3l4b.screens.base.utils.IScreensConfiguration;
import com.th3l4b.screens.base.utils.ITreeOfScreens;

/**
 * Skeleton for a servlet that handles modification and interactions on the tree
 * of screens.
 */
@SuppressWarnings("serial")
public abstract class AbstractScreensServlet extends HttpServlet {

	public static final String ADD_PARAMETER_NAME = "add";
	public static final String ADD_SCREEN_PARAMETER_NAME = "screen";
	public static final String ADD_PARENT_PARAMETER_NAME = "parent";
	public static final String SET_PARAMETER_NAME = "set";
	public static final String SET_SCREEN_PARAMETER_NAME = "screen";
	public static final String SET_PROPERTY_PARAMETER_NAME = "property";
	public static final String SET_VALUE_PARAMETER_NAME = "value";
	public static final String REMOVE_PARAMETER_NAME = "remove";
	public static final String ACTIONS_PARAMETER_NAME = "actions";

	protected abstract IScreensConfiguration getConfiguration(
			HttpServletRequest request) throws Exception;

	protected abstract Locale getLocale(HttpServletRequest request,
			HttpServletResponse response) throws Exception;

	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			IScreensConfiguration configuration = getConfiguration(request);
			ITreeOfScreens tree = configuration.getTree();

			// add screens
			{
				int count = Integer.parseInt(request
						.getParameter(ADD_PARAMETER_NAME));
				for (int i = 0; i < count; i++) {
					String prefix = ADD_PARAMETER_NAME + "." + i + ".";
					String screen = request.getParameter(prefix
							+ ADD_SCREEN_PARAMETER_NAME);
					String parent = request.getParameter(prefix
							+ ADD_PARENT_PARAMETER_NAME);
					IScreen s = tree.get(screen);
					IScreen p = tree.get(parent);
					tree.addChild(s, p);
				}
			}

			// Set values
			{
				int count = Integer.parseInt(request
						.getParameter(SET_PARAMETER_NAME));
				for (int i = 0; i < count; i++) {
					String prefix = SET_PARAMETER_NAME + "." + i + ".";
					String screen = request.getParameter(prefix
							+ SET_SCREEN_PARAMETER_NAME);
					String property = request.getParameter(prefix
							+ SET_PROPERTY_PARAMETER_NAME);
					String value = request.getParameter(prefix
							+ SET_VALUE_PARAMETER_NAME);
					tree.get(screen).getProperties().put(property, value);
				}
			}

			// Remove screens
			{
				int count = Integer.parseInt(request
						.getParameter(REMOVE_PARAMETER_NAME));
				for (int i = 0; i < count; i++) {
					String screen = request.getParameter(REMOVE_PARAMETER_NAME
							+ "." + i);
					tree.removeChild(tree.get(screen));
				}
			}

			// Process actions
			ArrayList<IScreen> actions = new ArrayList<IScreen>();
			{
				int count = Integer.parseInt(request
						.getParameter(ACTIONS_PARAMETER_NAME));
				for (int i = 0; i < count; i++) {
					String s = request.getParameter(ACTIONS_PARAMETER_NAME
							+ "." + i);
					IScreen screen = tree.contains(s);
					if (screen == null) {
						throw new IllegalArgumentException("Unknown screen: "
								+ s);
					} else {
						if (!actions.contains(screen)) {
							actions.add(screen);
						}
					}
				}
			}

			// Find interactions
			Map<IScreen, IInteractionListener> interactions = configuration
					.getInteractions();
			LinkedHashMap<IScreen, IInteractionListener> filtered = new LinkedHashMap<IScreen, IInteractionListener>();
			for (IScreen screen : actions) {
				IInteractionListener interaction = interactions.get(screen);
				if (interaction != null) {
					filtered.put(screen, interaction);
				}
			}

			// Run interactions.
			DefaultWebInteractionContext context = new DefaultWebInteractionContext();
			context.setTree(tree);
			context.setLocale(getLocale(request, response));
			context.setInteractions(interactions);
			context.setRequest(request);
			for (Map.Entry<IScreen, IInteractionListener> iteration : filtered
					.entrySet()) {
				iteration.getValue().handleInteraction(iteration.getKey(),
						context);
			}

			// Dump result.
			response.setContentType("application/javascript");
			response.setCharacterEncoding(ITextConstants.UTF_8);
			ScreensWebUtils.dump(tree, response.getWriter());

		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

}
