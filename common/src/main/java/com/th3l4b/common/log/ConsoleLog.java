package com.th3l4b.common.log;

import java.io.PrintWriter;

import com.th3l4b.common.text.IPrintable;
import com.th3l4b.common.text.IndentedWriter;

public class ConsoleLog extends AbstractLog {

	private PrintWriter _out;

	public ConsoleLog() {
		this(new PrintWriter(System.out, true));
	}

	public ConsoleLog(PrintWriter out) {
		_out = out;
	}

	@Override
	public void log(IPrintable item, ILogLevel level) throws Exception {
		PrintWriter iout = IndentedWriter.get(_out, level.name() + " ");
		item.print(iout);
		iout.flush();
	}

}
