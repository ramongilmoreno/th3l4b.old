package com.th3l4b.screens.testbed.desktop;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.Map;

import com.th3l4b.common.data.Pair;
import com.th3l4b.common.data.tree.ITree;
import com.th3l4b.screens.base.IScreen;
import com.th3l4b.screens.base.interaction.IInteractionListener;
import com.th3l4b.screens.console.ConsoleFacade;
import com.th3l4b.screens.console.DefaultConsoleContext;
import com.th3l4b.screens.console.ICommandsInput;
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
		context.setScreen(create.getA().getRoot());
		context.setWriter(new PrintWriter(System.out, true));

		ConsoleFacade facade = new ConsoleFacade();
		facade.handle(context.getScreen(), new ICommandsInput() {
			BufferedReader _reader = new BufferedReader(new InputStreamReader(
					System.in));

			@Override
			public String[] nextCommand() throws Exception {
				String l = _reader.readLine();
				if (l != null) {
					return l.split(" ");
				} else {
					return null;
				}
			}
		}, context);
	}
}
