package com.th3l4b.screens.base;

public interface IRenderingConstants {
	
	static final String PREFIX = IRenderingConstants.class.getPackage().getName() + ".rendering";
	
	public static final String TONE = PREFIX + ".tone";
	public static final String TONE_VALUE_WEAK = TONE + ".weak";
	public static final String TONE_VALUE_NORMAL = TONE + ".normal";
	public static final String TONE_VALUE_STRONG = TONE + ".strong";

	public static final String STATUS = PREFIX + ".status";
	public static final String STATUS_VALUE_GOOD = STATUS + ".good";
	public static final String STATUS_VALUE_NORMAL = STATUS + ".normal";
	public static final String STATUS_VALUE_BAD = STATUS + ".bad";
	
	public static final String CONTAINER_PREFIX = PREFIX + ".container";
	public static final String CONTAINER_LIST = CONTAINER_PREFIX + ".list";
}
