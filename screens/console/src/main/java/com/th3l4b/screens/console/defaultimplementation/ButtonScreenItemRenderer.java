package com.th3l4b.screens.console.defaultimplementation;

import com.th3l4b.screens.base.IScreenItem;
import com.th3l4b.screens.base.IScreensContants;
import com.th3l4b.screens.base.interaction.IInteractionConstants;
import com.th3l4b.screens.base.utils.PropertiesUtils;
import com.th3l4b.screens.console.ConsoleRendererContext;
import com.th3l4b.screens.console.IScreenItemRenderer;
import com.th3l4b.screens.console.interaction.DefaultInteractionProducer;

public class ButtonScreenItemRenderer implements IScreenItemRenderer, IScreensContants, IInteractionConstants {

	@Override
	public void render(IScreenItem item, ConsoleRendererContext context)
			throws Exception {
		String text = PropertiesUtils.getValue(LABEL,
				item.getName(), context.getLocale(), item);
		String id = context.getInteractionIdentifierGenerator()
				.getIdentifier(item, INTERACTION_ACTION_MAIN);
		context.getWriter().println("[" + text + "] (" + id + ")");
		context.getInteractionProducers()
		.put(id,
				new DefaultInteractionProducer(
						INTERACTION_ACTION_MAIN));
		
	}

}
