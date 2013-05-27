package com.th3l4b.screens.console;

import java.io.PrintWriter;

import com.th3l4b.screens.base.interaction.DefaultInteractionContext;

public class DefaultConsoleContext extends DefaultInteractionContext implements IConsoleInteractionContext {

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
