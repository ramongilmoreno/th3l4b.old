package com.th3l4b.screens.testbed.shopping;

import java.io.PrintWriter;
import java.nio.charset.Charset;

import com.th3l4b.apps.shopping.base.Shopping;
import com.th3l4b.screens.base.utils.IScreensConfiguration;
import com.th3l4b.screens.console.ConsoleFacade;
import com.th3l4b.screens.console.DefaultCommandsInput;
import com.th3l4b.screens.console.DefaultConsoleScreensClientDescriptor;
import com.th3l4b.screens.console.IConsoleScreensClientDescriptor;

public class ShoppingConsoleMain {
	public static void main(String[] args) throws Exception {
		IConsoleScreensClientDescriptor client = new DefaultConsoleScreensClientDescriptor();
		client.setWriter(new PrintWriter(System.out, true));

		IScreensConfiguration context = new Shopping().sample(client);

		ConsoleFacade facade = new ConsoleFacade();
		facade.handle(context.getTree().getRoot(), new DefaultCommandsInput(
				System.in, Charset.defaultCharset().name()), context, client);
	}
}
