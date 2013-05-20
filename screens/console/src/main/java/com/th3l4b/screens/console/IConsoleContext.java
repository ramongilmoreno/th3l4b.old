package com.th3l4b.screens.console;

import java.io.PrintWriter;

import com.th3l4b.screens.base.interaction.IInteractionContext;

public interface IConsoleContext extends IInteractionContext {
	
	PrintWriter getWriter() throws Exception;
	
	void setWriter(PrintWriter writer) throws Exception;

}
