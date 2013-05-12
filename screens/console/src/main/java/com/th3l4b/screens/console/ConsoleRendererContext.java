package com.th3l4b.screens.console;

import java.io.PrintWriter;

public class ConsoleRendererContext {

	private PrintWriter _writer;
	
	public PrintWriter getWriter() {
		return _writer;
	}
	
	public void setWriter(PrintWriter writer) {
		_writer = writer;
	}
	
}
