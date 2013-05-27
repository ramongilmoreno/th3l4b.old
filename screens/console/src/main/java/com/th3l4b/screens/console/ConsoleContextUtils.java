package com.th3l4b.screens.console;

import com.th3l4b.screens.base.interaction.InteractionContextUtils;

public class ConsoleContextUtils {
	public static void copy(IConsoleInteractionContext source, IConsoleInteractionContext target)
			throws Exception {
		InteractionContextUtils.copy(source, target);
		target.setWriter(source.getWriter());
	}
}
