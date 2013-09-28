package com.th3l4b.screens.base.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import com.th3l4b.common.propertied.DefaultPropertied;
import com.th3l4b.screens.base.IScreensContants;

public class DefaultScreensClientDescriptor extends DefaultPropertied implements
		IScreensClientDescriptor {

	private Collection<Locale> _locales;
	private Collection<String> _languages;

	/**
	 * Creates a default client for the
	 * {@link IScreensContants#INTERACTION_JAVA} language and for the
	 * {@link Locale#getDefault()} locale;
	 */
	public DefaultScreensClientDescriptor() {
		ArrayList<Locale> locales = new ArrayList<Locale>();
		locales.add(Locale.getDefault());
		this.setLocales(locales);
		ArrayList<String> languages = new ArrayList<String>();
		languages.add(IScreensContants.INTERACTION_JAVA);
		this.setLanguages(languages);
	}

	@Override
	public void setLocales(Collection<Locale> locales) {
		_locales = locales;
	}

	@Override
	public void setLanguages(Collection<String> languages) {
		_languages = languages;
	}

	@Override
	public Collection<Locale> getLocales() throws Exception {
		return _locales;
	}

	@Override
	public Collection<String> getLanguages() throws Exception {
		return _languages;
	}
}
