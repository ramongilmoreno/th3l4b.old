package com.th3l4b.screens.console;

import java.io.PrintWriter;

import com.th3l4b.screens.base.utils.IScreensClientDescriptor;

public interface IConsoleScreensClientDescriptor extends IScreensClientDescriptor {
	
	PrintWriter getWriter() throws Exception;
	
	void setWriter(PrintWriter writer) throws Exception;

}
