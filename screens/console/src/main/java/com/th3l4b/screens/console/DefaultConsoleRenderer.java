package com.th3l4b.screens.console;

import java.util.LinkedHashMap;
import java.util.Map;

import com.th3l4b.common.data.Pair;
import com.th3l4b.common.text.IndentedWriter;
import com.th3l4b.screens.base.IField;
import com.th3l4b.screens.base.IInteraction;
import com.th3l4b.screens.base.IScreen;
import com.th3l4b.screens.base.IScreenItem;
import com.th3l4b.screens.base.interaction.IInteractionConstants;
import com.th3l4b.screens.console.defaultimplementation.ButtonScreenItemRenderer;
import com.th3l4b.screens.console.defaultimplementation.DefaultScreenItemRenderer;
import com.th3l4b.screens.console.defaultimplementation.DefaultScreenRenderer;

public class DefaultConsoleRenderer implements IConsoleRenderer,
		IInteractionConstants {

	class P extends Pair<Class<?>, String> {
		public P(Class<?> clazz, String s) {
			super(clazz, s);
		}
	};

	protected Map<P, IScreenItemRenderer> _screenItemsRenderers = new LinkedHashMap<P, IScreenItemRenderer>();
	protected DefaultScreenRenderer _defaultScreenRenderer = new DefaultScreenRenderer();

	public DefaultConsoleRenderer() {
		_screenItemsRenderers.put(new P(IInteraction.class,
				INTERACTION_TYPE_BUTTON), new ButtonScreenItemRenderer());
	}

	protected Class<?> getClass(IScreenItem item) {
		Class<?> clazz = item.getClass();
		if (IInteraction.class.isAssignableFrom(clazz)) {
			return IInteraction.class;
		} else if (IField.class.isAssignableFrom(clazz)) {
			return IField.class;
		} else {
			return IScreenItem.class;
		}
	}

	protected void render(IScreenItem item, ConsoleRendererContext context)
			throws Exception {
		P key = new P(getClass(item), item.getType());
		_screenItemsRenderers.get(key).render(item, context);
	}

	@Override
	public void render(IScreen screen, ConsoleRendererContext context)
			throws Exception {
		// Render the screen.
		if (_defaultScreenRenderer.render(screen, context)) {
			return;
		}

		// Render items and children.
		ConsoleRendererContext childContext = new ConsoleRendererContext();
		context.copyTo(childContext);
		childContext.setWriter(IndentedWriter.get(childContext.getWriter()));
		for (IScreenItem item : screen.items()) {
			render(item, childContext);
		}
		for (IScreen child : screen.getChildren(screen)) {
			this.render(child, childContext);
		}

	}

}
