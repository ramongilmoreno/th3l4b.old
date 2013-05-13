package com.th3l4b.screens.base.interaction;

import java.util.Locale;

import com.th3l4b.screens.base.IScreen;

public interface IInteractionContext {
	IScreen getScreen() throws Exception;

	void setScreen(IScreen screen) throws Exception;

	Locale getLocale() throws Exception;

	void setLocale(Locale locale) throws Exception;

}
