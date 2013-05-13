package com.th3l4b.screens.base.interaction;

import java.util.Locale;

import com.th3l4b.screens.base.IScreen;

public class DefaultInteractionContext implements IInteractionContext {

	protected IScreen _screen;
	protected Locale _locale;

	public Locale getLocale() {
		return _locale;
	}

	public void setLocale(Locale locale) {
		_locale = locale;
	}

	public IScreen getScreen() {
		return _screen;
	}

	public void setScreen(IScreen screen) {
		_screen = screen;
	}
}
