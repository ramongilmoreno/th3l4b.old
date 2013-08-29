package com.th3l4b.screens.testbed.shopping;

import java.util.LinkedHashMap;
import java.util.Locale;

import com.th3l4b.common.data.nullsafe.NullSafe;
import com.th3l4b.screens.base.IRenderingConstants;
import com.th3l4b.screens.base.IScreensContants;
import com.th3l4b.screens.base.ITreeOfScreens;
import com.th3l4b.screens.base.interaction.IInteractionListener;
import com.th3l4b.screens.base.utils.DefaultScreensConfiguration;
import com.th3l4b.screens.base.utils.DefaultTreeOfScreens;
import com.th3l4b.screens.base.utils.IScreensClientDescriptor;
import com.th3l4b.screens.base.utils.IScreensConfiguration;
import com.th3l4b.screens.testbed.shopping.data.IContainer;
import com.th3l4b.screens.testbed.shopping.data.INeed;
import com.th3l4b.screens.testbed.shopping.data.sample.SampleShoppingData;

public class Shopping implements IScreensContants {

	private static final String KEY = Shopping.class.getPackage().getName();
	private static final String STATUS = KEY + ".status";

	protected String name(String n) {
		return Shopping.class.getPackage().getName() + "." + n;
	}

	protected String nameOfLater(String needId) throws Exception {
		return name("need - later - " + needId);
	}

	protected String localizedLabel(String name, IShoppingContext client) {
		return name;
	}

	protected ITreeOfScreens clearRoot(String root, IShoppingContext context)
			throws Exception {
		return clearRoot(root, context, false);
	}

	protected IShoppingContext getContext(IScreensConfiguration context)
			throws Exception {
		return (IShoppingContext) context.getAttributes().get(KEY);
	}

	protected ITreeOfScreens clearRoot(final String root,
			IShoppingContext context, boolean atIndex) throws Exception {
		ITreeOfScreens tree = context.getContext().getTree();
		for (String c : tree.children(root)) {
			tree.removeScreen(c);
		}
		if (!atIndex) {
			// Back to index
			String text = "Back to index";
			String name = name(text);
			tree.addScreen(name, root);
			tree.setProperty(name, ORDER_INDEX,
					Integer.toString(Integer.MAX_VALUE));
			tree.setProperty(name, LABEL, localizedLabel(text, context));
			tree.setProperty(name, TYPE, TYPE_ACTION);
			tree.setProperty(name, INTERACTION, "true");
			tree.setProperty(name, INTERACTION_JAVA, name);
			context.getContext().getInteractions()
					.put(name, new IInteractionListener() {
						@Override
						public void handleInteraction(String screen,
								IScreensConfiguration context,
								IScreensClientDescriptor client)
								throws Exception {
							renderIndex(root, Shopping.this.getContext(context));
						}
					});
		}

		return tree;

	}

	protected void renderIndex(final String root, IShoppingContext context)
			throws Exception {
		ITreeOfScreens tree = clearRoot(root, context, true);
		{
			String text = "Start shopping";
			String name = name(text);
			tree.addScreen(name, root);
			tree.setProperty(name, ORDER_INDEX, "0");
			tree.setProperty(name, LABEL, localizedLabel(text, context));
			tree.setProperty(name, TYPE, TYPE_ACTION);
			tree.setProperty(name, INTERACTION, "true");
			tree.setProperty(name, INTERACTION_JAVA, name);
			context.getContext().getInteractions()
					.put(name, new IInteractionListener() {
						@Override
						public void handleInteraction(String screen,
								IScreensConfiguration context,
								IScreensClientDescriptor client)
								throws Exception {
							renderShopping(root,
									Shopping.this.getContext(context));
						}
					});
		}
		{
			String text = "Items to buy";
			String name = name(text);
			tree.addScreen(name, root);
			tree.setProperty(name, ORDER_INDEX, "1");
			tree.setProperty(name, LABEL, localizedLabel(text, context));
			tree.setProperty(name, TYPE, TYPE_ACTION);
			tree.setProperty(name, INTERACTION, "true");
			tree.setProperty(name, INTERACTION_JAVA, name);
			context.getContext().getInteractions()
					.put(name, new IInteractionListener() {
						@Override
						public void handleInteraction(String screen,
								IScreensConfiguration context,
								IScreensClientDescriptor client)
								throws Exception {

						}
					});
		}
		{
			String text = "Stores";
			String name = name(text);
			tree.addScreen(name, root);
			tree.setProperty(name, ORDER_INDEX, "2");
			tree.setProperty(name, LABEL, localizedLabel(text, context));
			tree.setProperty(name, TYPE, TYPE_ACTION);
			tree.setProperty(name, INTERACTION, "true");
			tree.setProperty(name, INTERACTION_JAVA, name);
			context.getContext().getInteractions()
					.put(name, new IInteractionListener() {
						@Override
						public void handleInteraction(String screen,
								IScreensConfiguration context,
								IScreensClientDescriptor client)
								throws Exception {

						}
					});
		}
	}

	enum Status {
		normal, later, marked
	};

	protected void addLaterAction(final String needName,
			IShoppingContext context) throws Exception {
		ITreeOfScreens tree = context.getContext().getTree();
		String id = tree.getProperty(needName, KEY);
		String name = nameOfLater(id);
		tree.addScreen(name, needName);
		tree.setProperty(name, LABEL, localizedLabel("Later", context));
		tree.setProperty(name, TYPE, TYPE_ACTION);
		tree.setProperty(name, INTERACTION, "true");
		tree.setProperty(name, INTERACTION_JAVA, name);
		context.getContext().getInteractions()
				.put(name, new IInteractionListener() {
					@Override
					public void handleInteraction(String screen,
							IScreensConfiguration context,
							IScreensClientDescriptor client) throws Exception {
						updateNeed(needName, context, client, Boolean.FALSE,
								Boolean.TRUE);
					}
				});

	}

	protected void updateNeed(String needName, IScreensConfiguration context,
			IScreensClientDescriptor client, Boolean toggleMarked,
			Boolean toggleLater) throws Exception {
		ITreeOfScreens tree = context.getTree();
		String id = tree.getProperty(needName, KEY);
		IShoppingContext shoppingContext = getContext(context);
		IContainer<INeed> container = shoppingContext.getData()
				.getNeedsContainer();
		INeed need = container.get(id);
		Status status = Status.valueOf(tree.getProperty(needName, STATUS));

		if (NullSafe.equals(toggleMarked, Boolean.TRUE)) {
			// Toggle marked
			switch (status) {
			case later:
			case normal:
				status = Status.marked;
				container.remove(need);
				tree.removeScreen(nameOfLater(id));
				break;
			case marked:
				status = Status.normal;
				container.restore(need);
				addLaterAction(needName, shoppingContext);
				break;
			}
		} else {
			// Toggle later
			switch (status) {
			case later:
				status = Status.normal;
				break;
			case normal:
				status = Status.later;
				break;
			case marked:
				throw new IllegalStateException();
			}
		}

		tree.setProperty(needName, STATUS, status.toString());

		String newTone = null;
		if (status == Status.marked) {
			// Set to weak and remove from list
			newTone = IRenderingConstants.TONE_VALUE_WEAK;
		} else {
			// Set to normal and restore it to list
			newTone = IRenderingConstants.TONE_VALUE_NORMAL;
		}
		tree.setProperty(needName, IRenderingConstants.TONE, newTone);
		String newStatus = null;
		if (status == Status.later) {
			// Set to bad status but do not touch it
			newStatus = IRenderingConstants.STATUS_VALUE_BAD;
		} else {
			// Set to weak and remove from list
			newStatus = IRenderingConstants.STATUS_VALUE_NORMAL;
		}
		tree.setProperty(needName, IRenderingConstants.STATUS, newStatus);
	}

	protected void renderShopping(String root, IShoppingContext context)
			throws Exception {
		ITreeOfScreens tree = clearRoot(root, context);
		String itemsChild = name("Items");
		tree.addScreen(itemsChild, root);
		tree.setProperty(itemsChild, ORDER_INDEX, "0");

		// Render items
		int index = 0;
		for (INeed need : context.getData().getNeedsContainer().all()) {
			final String needId = need.getIdentifier();
			final String needName = name("need of - " + need.getIdentifier());
			{
				String name = needName;
				tree.addScreen(name, itemsChild);
				tree.setProperty(name, ORDER_INDEX, Integer.toString(index++));
				tree.setProperty(name, LABEL,
						need.getLabel(context.getLocale()));
				tree.setProperty(needName, KEY, needId);
				tree.setProperty(needName, STATUS, Status.normal.toString());
				tree.setProperty(name, TYPE, TYPE_ACTION);
				tree.setProperty(name, INTERACTION, "true");
				tree.setProperty(name, INTERACTION_JAVA, name);
				context.getContext().getInteractions()
						.put(name, new IInteractionListener() {
							@Override
							public void handleInteraction(String screen,
									IScreensConfiguration context,
									IScreensClientDescriptor client)
									throws Exception {
								updateNeed(needName, context, client,
										Boolean.TRUE, Boolean.FALSE);
							}
						});
			}
			addLaterAction(needName, context);
		}

	}

	public IScreensConfiguration sample(IScreensClientDescriptor client)
			throws Exception {
		IShoppingContext context = new DefaultShoppingContext();
		LinkedHashMap<String, IInteractionListener> interactions = new LinkedHashMap<String, IInteractionListener>();
		DefaultTreeOfScreens tree = new DefaultTreeOfScreens();
		DefaultScreensConfiguration config = new DefaultScreensConfiguration(
				tree, interactions);
		context.setClient(client);
		context.setContext(config);
		context.setData(new SampleShoppingData());
		context.setLocale(Locale.getDefault());
		context.getContext().getAttributes().put(KEY, context);
		String root = name("Root");
		context.getContext().getTree().setRoot(root);
		renderIndex(root, context);
		return config;
	}
}
