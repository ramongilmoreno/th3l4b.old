package com.th3l4b.screens.base;

public interface IScreensConstants {
	public static final String PREFIX = IScreensConstants.class.getPackage()
			.getName();
	public static final String TYPE_PREFIX = PREFIX + ".type";
	public static final String TYPE = TYPE_PREFIX;
	public static final String TYPE_FIELD = TYPE_PREFIX + ".field";
	public static final String TYPE_ACTION = TYPE_PREFIX + ".action";
	public static final String TYPE_HIDDEN = TYPE_PREFIX + ".hidden";
	
	public static final String INTERACTION = PREFIX + ".interaction";
	public static final String INTERACTION_JAVA = INTERACTION + ".java";
	public static final String INTERACTION_JAVASCRIPT = INTERACTION + ".javascript";

	public static final String VALUE = PREFIX + ".value";
	public static final String LABEL = PREFIX + ".label";

	public static final String ORDER_PREFIX = PREFIX + ".order";
	public static final String ORDER_INDEX = ORDER_PREFIX + ".index";
}
