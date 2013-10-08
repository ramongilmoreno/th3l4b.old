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
	
	public static final String CONTAINER = PREFIX + ".container";
	public static final String CONTAINER_LIST = CONTAINER + ".list";
	
	public static final String CONTAINER_TABLE = CONTAINER + ".table";
	public static final String CONTAINER_TABLE_ROWS = CONTAINER_TABLE + ".rows";
	public static final String CONTAINER_TABLE_ROW = CONTAINER_TABLE + ".row";
	public static final String CONTAINER_TABLE_HEADER = CONTAINER_TABLE + ".header";
}
