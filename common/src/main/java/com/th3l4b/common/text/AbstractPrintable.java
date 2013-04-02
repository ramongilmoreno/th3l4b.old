package com.th3l4b.common.text;

import java.io.PrintWriter;

public abstract class AbstractPrintable implements IPrintable {

	protected abstract void printWithException(PrintWriter out)
			throws Exception;

	@Override
	public void print(PrintWriter out) {
		try {
			printWithException(out);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
