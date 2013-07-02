package com.th3l4b.screens.base;

public interface IScreensContants {
	public static final String PREFIX = IScreensContants.class.getPackage()
			.getName();
	public static final String TYPE_PREFIX = PREFIX + ".type";
	public static final String TYPE = TYPE_PREFIX;
	public static final String TYPE_FIELD = TYPE_PREFIX + ".field";
	public static final String TYPE_INTERACTION = TYPE_PREFIX + ".interaction";

	public static final String VALUE = PREFIX + ".value";
	public static final String LABEL = PREFIX + ".label";
	public static final String MODIFIED = PREFIX + ".modified";

	public static final String APPEARANCE_PREFIX = PREFIX + ".appearance";
	public static final String APPEARANCE = APPEARANCE_PREFIX;
	public static final String APPEARANCE_MENU = APPEARANCE_PREFIX + ".menu";
}
