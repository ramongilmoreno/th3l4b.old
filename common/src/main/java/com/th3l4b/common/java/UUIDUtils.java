package com.th3l4b.common.java;

import java.nio.ByteBuffer;
import java.util.UUID;

import javax.xml.bind.DatatypeConverter;

public class UUIDUtils {

	public static byte[] get(UUID uuid) {
		ByteBuffer buffer = ByteBuffer.allocate(16);
		buffer.putLong(uuid.getMostSignificantBits());
		buffer.putLong(uuid.getLeastSignificantBits());
		return buffer.array();
	}

	public static byte[] get() {
		return get(UUID.randomUUID());
	}

	public static String toString(byte[] data) {
		return DatatypeConverter.printBase64Binary(data).replaceAll(
				"[^a-zA-Z0-9/\\+=]*", "");
	}

	public static byte[] fromString(String input) throws NumberFormatException {
		return DatatypeConverter.parseBase64Binary(input);
	}
}
