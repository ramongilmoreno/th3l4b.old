package com.th3l4b.screens.base.addon;

import com.th3l4b.common.data.nullsafe.NullSafe;
import com.th3l4b.screens.base.ITreeOfScreens;
import com.th3l4b.screens.base.interaction.IInteractionListener;
import com.th3l4b.screens.base.utils.IScreensClientDescriptor;
import com.th3l4b.screens.base.utils.IScreensConfiguration;

/**
 * Custom interaction for on/off buttons. Uses the {@link #TOGGLE_STATUS_KEY} property to
 * check the status. The value of the {@link #TOGGLE_STATUS_KEY} will be passed to the
 * {@link #handleInteraction(String, boolean, IScreensConfiguration, IScreensClientDescriptor)}
 * method (true unless {@link #TOGGLE_STATUS_KEY} is set explicitly to false). The first click
 * will be true as the {@link #TOGGLE_STATUS_KEY} is uninitialized.
 */
public abstract class AbstractToggleInteractionListener implements
		IInteractionListener {
	public static final String TOGGLE_STATUS_KEY = AbstractToggleInteractionListener.class
			.getPackage().getName();

	public static final void setStatus(ITreeOfScreens tree, String screen,
			boolean status) throws Exception {
		tree.setProperty(screen, TOGGLE_STATUS_KEY, Boolean.toString(status));
	}

	@Override
	public void handleInteraction(String screen, IScreensConfiguration context,
			IScreensClientDescriptor client) throws Exception {
		ITreeOfScreens tree = context.getTree();
		boolean status = NullSafe.equals(tree.getProperty(screen, TOGGLE_STATUS_KEY),
				Boolean.TRUE.toString());
		// Toggle status
		status = !status;
		handleInteraction(screen, status, context, client);
		setStatus(tree, screen, status);
	}

	protected abstract void handleInteraction(String screen, boolean status,
			IScreensConfiguration context, IScreensClientDescriptor client)
			throws Exception;

}
