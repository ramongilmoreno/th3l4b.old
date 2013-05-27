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

@SuppressWarnings("serial")
public abstract class AbstractScreensServlet extends HttpServlet {

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

			int count = Integer.parseInt(request
					.getParameter(ACTIONS_PARAMETER_NAME));
			ArrayList<IScreen> screens = new ArrayList<IScreen>();
			for (int i = 0; i < count; i++) {
				String s = request.getParameter(ACTIONS_PARAMETER_NAME + "."
						+ i);
				IScreen screen = configuration.getTree().contains(s);
				if (screen == null) {
					throw new IllegalArgumentException("Unknown screen: " + s);
				} else {
					if (!screens.contains(screen)) {
						screens.add(screen);
					}
				}
			}

			// Find interactions
			Map<IScreen, IInteractionListener> interactions = configuration
					.getInteractions();
			LinkedHashMap<IScreen, IInteractionListener> filtered = new LinkedHashMap<IScreen, IInteractionListener>();
			for (IScreen screen : screens) {
				IInteractionListener interaction = interactions.get(screen);
				if (interaction != null) {
					filtered.put(screen, interaction);
				}
			}

			// Run interactions.
			DefaultWebInteractionContext context = new DefaultWebInteractionContext();
			context.setTree(configuration.getTree());
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
			ScreensWebUtils.dump(configuration.getTree(), response.getWriter());

		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

}
