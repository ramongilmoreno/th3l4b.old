package com.th3l4b.screens.base.utils;

import java.util.Collection;
import java.util.Locale;

import com.th3l4b.common.propertied.IPropertied;

public interface IScreensClientDescriptor extends IPropertied {

	void setLocales(Collection<Locale> locales) throws Exception;

	void setLanguages(Collection<String> languages) throws Exception;

	Collection<Locale> getLocales() throws Exception;

	Collection<String> getLanguages() throws Exception;
}
