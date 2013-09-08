package com.th3l4b.screens.testbed.shopping;

import java.util.LinkedHashMap;
import java.util.Locale;

import com.th3l4b.common.data.nullsafe.NullSafe;
import com.th3l4b.screens.base.IRenderingConstants;
import com.th3l4b.screens.base.IScreensContants;
import com.th3l4b.screens.base.ITreeOfScreens;
import com.th3l4b.screens.base.addon.AbstractToggleInteractionListener;
import com.th3l4b.screens.base.interaction.IInteractionListener;
import com.th3l4b.screens.base.utils.DefaultScreensConfiguration;
import com.th3l4b.screens.base.utils.DefaultTreeOfScreens;
import com.th3l4b.screens.base.utils.IScreensClientDescriptor;
import com.th3l4b.screens.base.utils.IScreensConfiguration;
import com.th3l4b.screens.testbed.shopping.data.IContainer;
import com.th3l4b.screens.testbed.shopping.data.IItem;
import com.th3l4b.screens.testbed.shopping.data.INeed;
import com.th3l4b.screens.testbed.shopping.data.sample.SampleShoppingData;

public class Shopping implements IScreensContants, IRenderingConstants {

	private static final String KEY = Shopping.class.getPackage().getName();
	private static final String STATUS_ENUM_VALUE = KEY + ".status";

	protected String name(String n) {
		return Shopping.class.getPackage().getName() + "." + n;
	}

	protected String nameOfLater(String needId) throws Exception {
		return name("need - later - " + needId);
	}

	protected String localizedLabel(String name, IShoppingApplicationContext client) {
		return name;
	}

	protected ITreeOfScreens clearRoot(String root, IShoppingApplicationContext context)
			throws Exception {
		return clearRoot(root, context, false);
	}

	protected IShoppingApplicationContext getContext(IScreensConfiguration context)
			throws Exception {
		return (IShoppingApplicationContext) context.getAttributes().get(KEY);
	}

	protected ITreeOfScreens clearRoot(final String root,
			IShoppingApplicationContext context, boolean atIndex) throws Exception {
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

	protected void renderIndex(final String root, IShoppingApplicationContext context)
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
							renderItems(root, Shopping.this.getContext(context));
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
			IShoppingApplicationContext context) throws Exception {
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
		IShoppingApplicationContext shoppingContext = getContext(context);
		IContainer<INeed> container = shoppingContext.getData()
				.getNeedsContainer();
		INeed need = container.get(id);
		Status status = Status.valueOf(tree.getProperty(needName,
				STATUS_ENUM_VALUE));

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

		tree.setProperty(needName, STATUS_ENUM_VALUE, status.toString());

		String newTone = null;
		if (status == Status.marked) {
			// Set to weak and remove from list
			newTone = TONE_VALUE_WEAK;
		} else {
			// Set to normal and restore it to list
			newTone = TONE_VALUE_NORMAL;
		}
		tree.setProperty(needName, TONE, newTone);
		String newStatus = null;
		if (status == Status.later) {
			// Set to bad status but do not touch it
			newStatus = STATUS_VALUE_BAD;
		} else {
			// Set to weak and remove from list
			newStatus = STATUS_VALUE_NORMAL;
		}
		tree.setProperty(needName, IRenderingConstants.STATUS, newStatus);
	}

	protected void renderShopping(String root, IShoppingApplicationContext context)
			throws Exception {
		ITreeOfScreens tree = clearRoot(root, context);
		String itemsChild = name("Items");
		tree.addScreen(itemsChild, root);
		tree.setProperty(itemsChild, ORDER_INDEX, "0");

		// Render items
		int index = 0;
		for (INeed need : context.getData().getNeedsContainer().all()) {
			final String needName = name("need of - " + need.getIdentifier());
			{
				IItem item = context.getData().getItemsContainer()
						.get(need.getIdentifier());
				String name = needName;
				tree.addScreen(name, itemsChild);
				tree.setProperty(name, ORDER_INDEX, Integer.toString(index++));
				tree.setProperty(name, LABEL,
						item.getLabel());
				tree.setProperty(needName, KEY, need.getIdentifier());
				tree.setProperty(needName, STATUS_ENUM_VALUE,
						Status.normal.toString());
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

	protected boolean itemHasNeeds(String item, IShoppingApplicationContext context)
			throws Exception {
		for (@SuppressWarnings("unused")
		INeed need : context.getData().getNeedsByItem(item)) {
			return true;
		}
		return false;
	}

	protected void renderItems(String root, IShoppingApplicationContext context)
			throws Exception {
		ITreeOfScreens tree = clearRoot(root, context);
		String itemsChild = name("Items");
		tree.addScreen(itemsChild, root);
		tree.setProperty(itemsChild, ORDER_INDEX, "0");

		int index = 0;
		for (IItem item : context.getData().getItemsContainer().all()) {
			String name = name("item - " + item.getIdentifier());
			tree.addScreen(name, itemsChild);
			tree.setProperty(name, ORDER_INDEX, Integer.toString(index++));
			tree.setProperty(name, LABEL, item.getLabel());
			tree.setProperty(name, KEY, item.getIdentifier());
			tree.setProperty(name, TYPE, TYPE_ACTION);
			tree.setProperty(name, INTERACTION, "true");
			tree.setProperty(name, INTERACTION_JAVA, name);
			boolean hasNeeds = itemsHasNeeds(item.getIdentifier(), context);
			AbstractToggleInteractionListener.setStatus(tree, name, hasNeeds);
			tree.setProperty(name, TONE, hasNeeds ? TONE_VALUE_STRONG
					: TONE_VALUE_NORMAL);
			context.getContext().getInteractions()
					.put(name, new AbstractToggleInteractionListener() {
						@Override
						protected void handleInteraction(String screen,
								boolean status, IScreensConfiguration context,
								IScreensClientDescriptor client)
								throws Exception {
							IShoppingApplicationContext shopping = getContext(context);
							ITreeOfScreens tree = context.getTree();
							IContainer<INeed> needs = shopping.getData()
									.getNeedsContainer();
							String item = tree.getProperty(screen, KEY);
							// Clean needs.
							for (INeed need : shopping.getData()
									.getNeedsByItem(item)) {
								needs.remove(need);

							}
							// Create it if selected.
							if (status) {
								INeed created = needs.create();
								created.setItem(item);
							}
							tree.setProperty(screen, TONE,
									status ? TONE_VALUE_STRONG
											: TONE_VALUE_NORMAL);
						}
					});
		}

	}

	private boolean itemsHasNeeds(String identifier, IShoppingApplicationContext context) {
		// TODO Auto-generated method stub
		return false;
	}

	public IScreensConfiguration sample(IScreensClientDescriptor client)
			throws Exception {
		IShoppingApplicationContext context = new DefaultShoppingApplicationContext();
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
