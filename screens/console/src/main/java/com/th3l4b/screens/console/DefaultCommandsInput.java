package com.th3l4b.screens.console;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class DefaultCommandsInput implements ICommandsInput {

	BufferedReader _reader;

	public DefaultCommandsInput(InputStream is, String charset)
			throws UnsupportedEncodingException {
		_reader = new BufferedReader(new InputStreamReader(is, charset));
	}

	@Override
	public String[] nextCommand() throws Exception {
		String read = _reader.readLine();
		if (read != null) {
			return read.split(" ");
		} else {
			return null;
		}
	}

}
