package com.th3l4b.testbed.integrated.model;

import com.th3l4b.common.data.nullsafe.NullSafe;

public class TestsUtils {

	public static void assertNotNull(Object obj, String msg) throws Exception {
		if (obj == null) {
			throw new IllegalArgumentException("Unexpected null: " + msg);
		}
	}

	public static <T> void assertEquals(T expected, T actual, String msg) {
		if (NullSafe.equals(expected, actual)
				&& NullSafe.equals(actual, expected)) {
			return;
		} else {
			throw new IllegalArgumentException("Not equals: " + msg);
		}
	}

	public static <T> void assertDifferent(T expected, T actual, String msg) {
		if (expected != actual) {
			return;
		} else {
			throw new IllegalArgumentException("Not different: " + msg);
		}
	}

	public static <T> void assertTrue(boolean value, String msg) {
		if (value) {
			return;
		} else {
			throw new IllegalArgumentException("Not true: " + msg);
		}
	}

	public static <T> void assertFalse(boolean value, String msg) {
		if (!value) {
			return;
		} else {
			throw new IllegalArgumentException("Not false: " + msg);
		}
	}

}
