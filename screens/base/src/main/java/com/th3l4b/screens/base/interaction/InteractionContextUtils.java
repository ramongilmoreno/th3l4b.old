package com.th3l4b.screens.base.interaction;

public class InteractionContextUtils {
	public static void copy(IInteractionContext source,
			IInteractionContext target) throws Exception {
		target.setInteractions(source.getInteractions());
		target.setLocale(source.getLocale());
		target.setTree(source.getTree());
	}
}
