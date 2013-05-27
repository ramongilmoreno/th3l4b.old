package com.th3l4b.screens.testbed.desktop;

import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.Locale;

import com.th3l4b.screens.base.utils.IScreensConfiguration;
import com.th3l4b.screens.console.ConsoleFacade;
import com.th3l4b.screens.console.DefaultCommandsInput;
import com.th3l4b.screens.console.DefaultConsoleContext;
import com.th3l4b.screens.console.IConsoleInteractionContext;

public class Main {
	public static void main(String[] args) throws Exception {
		IScreensConfiguration configuration = ClipboardMenu.create();
		IConsoleInteractionContext context = new DefaultConsoleContext();
		context.setTree(configuration.getTree());
		context.setInteractions(configuration.getInteractions());
		context.setLocale(new Locale("en"));
		context.setWriter(new PrintWriter(System.out, true));

		ConsoleFacade facade = new ConsoleFacade();
		facade.handle(configuration.getTree().getRoot(),
				new DefaultCommandsInput(System.in, Charset.defaultCharset()
						.name()), context);
	}
}
