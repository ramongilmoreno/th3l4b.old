package com.th3l4b.screens.console.renderer;

import com.th3l4b.common.data.nullsafe.NullSafe;
import com.th3l4b.screens.base.IRenderingConstants;
import com.th3l4b.screens.base.IScreensConstants;
import com.th3l4b.screens.base.ITreeOfScreens;
import com.th3l4b.screens.base.utils.IScreensConfiguration;
import com.th3l4b.screens.console.IConsoleScreensClientDescriptor;

public class SimpleConsoleRenderer implements IConsoleRenderer {

	public String getLabel(String item, IScreensConfiguration context,
			IConsoleScreensClientDescriptor client) throws Exception {
		String r = context.getTree().getProperty(item, IScreensConstants.LABEL);
		if (r == null) {
			r = item;
		}
		r += decorations(item, context);
		return r;
	}

	protected String decorations(String item, IScreensConfiguration context)
			throws Exception {
		StringBuffer sb = new StringBuffer();
		ITreeOfScreens tree = context.getTree();

		String status = tree.getProperty(item, IRenderingConstants.STATUS);
		if (NullSafe.equals(status, IRenderingConstants.STATUS_VALUE_GOOD)) {
			sb.append(" - GOOD");
		} else if (NullSafe.equals(status,
				IRenderingConstants.STATUS_VALUE_NORMAL)) {
			// Nothing
		} else if (NullSafe
				.equals(status, IRenderingConstants.STATUS_VALUE_BAD)) {
			sb.append(" - BAD");
		}

		String tone = tree.getProperty(item, IRenderingConstants.TONE);
		if (NullSafe.equals(tone, IRenderingConstants.TONE_VALUE_STRONG)) {
			sb.append(" - *");
		} else if (NullSafe.equals(tone, IRenderingConstants.TONE_VALUE_NORMAL)) {
			// Nothing
		} else if (NullSafe.equals(tone, IRenderingConstants.TONE_VALUE_WEAK)) {
			sb.append(" - _");
		}

		return sb.toString();
	}

	@Override
	public boolean render(String item, IScreensConfiguration context,
			IConsoleScreensClientDescriptor client) throws Exception {
		client.getWriter().println(getLabel(item, context, client));
		return false;
	}

}
