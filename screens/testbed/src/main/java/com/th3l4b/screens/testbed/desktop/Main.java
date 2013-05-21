package com.th3l4b.screens.testbed.desktop;

import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.Map;

import com.th3l4b.common.data.Pair;
import com.th3l4b.common.data.tree.ITree;
import com.th3l4b.screens.base.IScreen;
import com.th3l4b.screens.base.interaction.IInteractionListener;
import com.th3l4b.screens.console.ConsoleFacade;
import com.th3l4b.screens.console.DefaultCommandsInput;
import com.th3l4b.screens.console.DefaultConsoleContext;
import com.th3l4b.screens.console.IConsoleContext;

public class Main {
	public static void main(String[] args) throws Exception {
		ClipboardMenu menu = new ClipboardMenu();
		Pair<ITree<IScreen>, Map<IScreen, IInteractionListener>> create = menu
				.create();
		IConsoleContext context = new DefaultConsoleContext();
		context.setTree(create.getA());
		context.setInteractions(create.getB());
		context.setLocale(new Locale("en"));
		context.setWriter(new PrintWriter(System.out, true));

		ConsoleFacade facade = new ConsoleFacade();
		facade.handle(create.getA().getRoot(), new DefaultCommandsInput(
				System.in, Charset.defaultCharset().name()), context);
	}
}
