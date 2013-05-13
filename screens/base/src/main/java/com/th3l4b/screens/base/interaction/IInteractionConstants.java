package com.th3l4b.screens.base.interaction;

public interface IInteractionConstants {
	public static final String PREFIX = IInteractionConstants.class
			.getPackage().getName();

	public static final String INTERACTION_PREFIX = PREFIX + ".interaction";

	public static final String INTERACTION_ACTION_PREFIX = INTERACTION_PREFIX
			+ ".action";
	public static final String INTERACTION_ACTION_MAIN = INTERACTION_ACTION_PREFIX
			+ ".main";
	public static final String INTERACTION_TYPE_PREFIX = INTERACTION_PREFIX
			+ ".type";
	public static final String INTERACTION_TYPE_BUTTON = INTERACTION_TYPE_PREFIX
			+ ".button";
}
