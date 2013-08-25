package com.th3l4b.screens.console;

import java.io.PrintWriter;

import com.th3l4b.screens.base.utils.DefaultScreensClientDescriptor;

public class DefaultConsoleScreensClientDescriptor extends DefaultScreensClientDescriptor
		implements IConsoleScreensClientDescriptor {

	private PrintWriter _writer;

	@Override
	public PrintWriter getWriter() throws Exception {
		return _writer;
	}

	@Override
	public void setWriter(PrintWriter writer) throws Exception {
		_writer = writer;

	}

}
