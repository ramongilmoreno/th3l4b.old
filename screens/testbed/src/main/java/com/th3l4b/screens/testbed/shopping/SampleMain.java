package com.th3l4b.screens.testbed.shopping;

import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Locale;

import com.th3l4b.screens.base.IScreensContants;
import com.th3l4b.screens.base.utils.IScreensConfiguration;
import com.th3l4b.screens.console.ConsoleFacade;
import com.th3l4b.screens.console.DefaultCommandsInput;
import com.th3l4b.screens.console.DefaultConsoleScreensClientDescriptor;
import com.th3l4b.screens.console.IConsoleScreensClientDescriptor;

public class SampleMain {
	public static void main(String[] args) throws Exception {
		IConsoleScreensClientDescriptor client = new DefaultConsoleScreensClientDescriptor();
		ArrayList<Locale> locales = new ArrayList<Locale>();
		locales.add(Locale.getDefault());
		client.setLocales(locales);
		ArrayList<String> languages = new ArrayList<String>();
		languages.add(IScreensContants.INTERACTION_JAVA);
		languages.add(IScreensContants.INTERACTION_JAVASCRIPT);
		client.setLanguages(languages);
		client.setWriter(new PrintWriter(System.out, true));

		IScreensConfiguration context = new Shopping().sample(client);

		ConsoleFacade facade = new ConsoleFacade();
		facade.handle(context.getTree().getRoot(), new DefaultCommandsInput(
				System.in, Charset.defaultCharset().name()), context, client);
	}
}
