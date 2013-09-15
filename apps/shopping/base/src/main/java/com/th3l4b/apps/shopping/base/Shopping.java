package com.th3l4b.apps.shopping.base;

import java.util.LinkedHashMap;
import java.util.Locale;

import com.th3l4b.apps.shopping.base.codegen.srm.IItem;
import com.th3l4b.apps.shopping.base.codegen.srm.INeed;
import com.th3l4b.apps.shopping.base.codegen.srm.IShoppingContext;
import com.th3l4b.apps.shopping.base.codegen.srm.IShoppingEntity;
import com.th3l4b.apps.shopping.base.codegen.srm.IShoppingFinder;
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
import com.th3l4b.srm.runtime.EntityStatus;
import com.th3l4b.srm.runtime.IIdentifier;
import com.th3l4b.srm.runtime.IModelUtils;
import com.th3l4b.srm.runtime.IRuntimeEntity;

public class Shopping implements IScreensContants, IRenderingConstants {

	private static final String KEY = Shopping.class.getPackage().getName();
	private static final String STATUS_ENUM_VALUE = KEY + ".status";

	protected String name(String n) {
		return Shopping.class.getPackage().getName() + "." + n;
	}

	protected String nameOfLater(String needId) throws Exception {
		return name("need - later - " + needId);
	}

	protected String localizedLabel(String name, IShoppingApplication client) {
		return name;
	}

	protected ITreeOfScreens clearRoot(String root, IShoppingApplication context)
			throws Exception {
		return clearRoot(root, context, false);
	}

	protected IShoppingApplication getApplication(IScreensConfiguration screens)
			throws Exception {
		return (IShoppingApplication) screens.getAttributes().get(KEY);
	}

	protected ITreeOfScreens clearRoot(final String root,
			IShoppingApplication application, boolean atIndex) throws Exception {
		ITreeOfScreens tree = application.getScreens().getTree();
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
			tree.setProperty(name, LABEL, localizedLabel(text, application));
			tree.setProperty(name, TYPE, TYPE_ACTION);
			tree.setProperty(name, INTERACTION, "true");
			tree.setProperty(name, INTERACTION_JAVA, name);
			application.getScreens().getInteractions()
					.put(name, new IInteractionListener() {
						@Override
						public void handleInteraction(String screen,
								IScreensConfiguration context,
								IScreensClientDescriptor client)
								throws Exception {
							renderIndex(root,
									Shopping.this.getApplication(context));
						}
					});
		}

		return tree;

	}

	protected void renderIndex(final String root, IShoppingApplication context)
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
			context.getScreens().getInteractions()
					.put(name, new IInteractionListener() {
						@Override
						public void handleInteraction(String screen,
								IScreensConfiguration context,
								IScreensClientDescriptor client)
								throws Exception {
							renderShopping(root,
									Shopping.this.getApplication(context));
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
			context.getScreens().getInteractions()
					.put(name, new IInteractionListener() {
						@Override
						public void handleInteraction(String screen,
								IScreensConfiguration context,
								IScreensClientDescriptor client)
								throws Exception {
							renderItems(root,
									Shopping.this.getApplication(context));
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
			context.getScreens().getInteractions()
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
			IShoppingApplication context) throws Exception {
		ITreeOfScreens tree = context.getScreens().getTree();
		String id = tree.getProperty(needName, KEY);
		String name = nameOfLater(id);
		tree.addScreen(name, needName);
		tree.setProperty(name, LABEL, localizedLabel("Later", context));
		tree.setProperty(name, TYPE, TYPE_ACTION);
		tree.setProperty(name, INTERACTION, "true");
		tree.setProperty(name, INTERACTION_JAVA, name);
		context.getScreens().getInteractions()
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

	protected IIdentifier getIdentifierFromString(String id,
			IShoppingApplication context) throws Exception {
		return context.getData().getUtils().identifierFromString(id);
	}

	protected String getStringFromIdentifier(IShoppingEntity<?> entity,
			IShoppingApplication context) throws Exception {
		return context.getData().getUtils()
				.identifierToString(entity.coordinates().getIdentifier());
	}

	protected void updateNeed(String needName, IScreensConfiguration context,
			IScreensClientDescriptor client, Boolean toggleMarked,
			Boolean toggleLater) throws Exception {
		ITreeOfScreens tree = context.getTree();
		IShoppingApplication application = getApplication(context);
		String idAsString = tree.getProperty(needName, KEY);
		IIdentifier id = getIdentifierFromString(idAsString, application);
		Status status = Status.valueOf(tree.getProperty(needName,
				STATUS_ENUM_VALUE));
		if (NullSafe.equals(toggleMarked, Boolean.TRUE)) {
			// Toggle marked
			LinkedHashMap<IIdentifier, IRuntimeEntity<?>> updates = new LinkedHashMap<IIdentifier, IRuntimeEntity<?>>();
			IShoppingContext data = application.getData();
			IModelUtils utils = data.getUtils();
			switch (status) {
			case later:
			case normal:
				status = Status.marked;
				{
					INeed remove = utils.create(INeed.class);
					remove.coordinates().setIdentifier(id);
					remove.coordinates().setStatus(EntityStatus.Deleted);
					updates.put(id, remove);
				}
				tree.removeScreen(nameOfLater(idAsString));
				break;
			case marked:
				status = Status.normal;
				{
					INeed remove = utils.create(INeed.class);
					remove.coordinates().setIdentifier(id);
					remove.coordinates().setStatus(EntityStatus.Modified);
					updates.put(id, remove);
				}
				addLaterAction(needName, application);
				break;
			}
			if (!updates.isEmpty()) {
				data.update(updates);
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

	protected void renderShopping(String root, IShoppingApplication application)
			throws Exception {
		ITreeOfScreens tree = clearRoot(root, application);
		String itemsChild = name("Items");
		tree.addScreen(itemsChild, root);
		tree.setProperty(itemsChild, ORDER_INDEX, "0");

		// Render items
		int index = 0;
		IShoppingFinder finder = application.getData().getFinder();
		for (INeed need : finder.allNeed()) {
			String id = getStringFromIdentifier(need, application);
			final String needName = name("need of - " + id);
			{
				IItem item = need.getItem(finder);
				String name = needName;
				tree.addScreen(name, itemsChild);
				tree.setProperty(name, ORDER_INDEX, Integer.toString(index++));
				tree.setProperty(name, LABEL, item.getName());
				tree.setProperty(needName, KEY, id);
				tree.setProperty(needName, STATUS_ENUM_VALUE,
						Status.normal.toString());
				tree.setProperty(name, TYPE, TYPE_ACTION);
				tree.setProperty(name, INTERACTION, "true");
				tree.setProperty(name, INTERACTION_JAVA, name);
				application.getScreens().getInteractions()
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
			addLaterAction(needName, application);
		}

	}

	protected void renderItems(String root, IShoppingApplication context)
			throws Exception {
		ITreeOfScreens tree = clearRoot(root, context);
		String itemsChild = name("Items");
		tree.addScreen(itemsChild, root);
		tree.setProperty(itemsChild, ORDER_INDEX, "0");

		int index = 0;
		IShoppingFinder finder = context.getData().getFinder();
		for (IItem item : finder.allItem()) {
			String idAsString = getStringFromIdentifier(item, context);
			String name = name("item - " + idAsString);
			tree.addScreen(name, itemsChild);
			tree.setProperty(name, ORDER_INDEX, Integer.toString(index++));
			tree.setProperty(name, LABEL, item.getName());
			tree.setProperty(name, KEY, idAsString);
			tree.setProperty(name, TYPE, TYPE_ACTION);
			tree.setProperty(name, INTERACTION, "true");
			tree.setProperty(name, INTERACTION_JAVA, name);
			boolean hasNeeds = itemsHasNeeds(
					item.coordinates().getIdentifier(), context);
			AbstractToggleInteractionListener.setStatus(tree, name, hasNeeds);
			tree.setProperty(name, TONE, hasNeeds ? TONE_VALUE_STRONG
					: TONE_VALUE_NORMAL);
			context.getScreens().getInteractions()
					.put(name, new AbstractToggleInteractionListener() {
						@Override
						protected void handleInteraction(String screen,
								boolean status, IScreensConfiguration context,
								IScreensClientDescriptor client)
								throws Exception {
							IShoppingApplication application = getApplication(context);
							ITreeOfScreens tree = context.getTree();
							String itemAsString = tree.getProperty(screen, KEY);
							IIdentifier itemAsId = getIdentifierFromString(
									itemAsString, application);

							// Clean needs.
							LinkedHashMap<IIdentifier, IRuntimeEntity<?>> updates = new LinkedHashMap<IIdentifier, IRuntimeEntity<?>>();
							IShoppingContext data = application.getData();
							for (INeed need : data.getFinder()
									.findAllNeedFromIItem(itemAsId)) {
								need.coordinates().setStatus(
										EntityStatus.Deleted);
								updates.put(need.coordinates().getIdentifier(),
										need);
							}
							// Create it if selected.
							if (status) {
								INeed need = data.getUtils()
										.create(INeed.class);
								need.setItem(itemAsId);
								updates.put(need.coordinates().getIdentifier(),
										need);
							}
							if (!updates.isEmpty()) {
								data.update(updates);
							}
							tree.setProperty(screen, TONE,
									status ? TONE_VALUE_STRONG
											: TONE_VALUE_NORMAL);
						}
					});
		}

	}

	private boolean itemsHasNeeds(IIdentifier identifier,
			IShoppingApplication context) throws Exception {
		return context.getData().getFinder().findAllNeedFromIItem(identifier)
				.iterator().hasNext();
	}

	public IScreensConfiguration sample(IScreensClientDescriptor client)
			throws Exception {
		IShoppingApplication context = new DefaultShoppingApplication();
		LinkedHashMap<String, IInteractionListener> interactions = new LinkedHashMap<String, IInteractionListener>();
		DefaultTreeOfScreens tree = new DefaultTreeOfScreens();
		DefaultScreensConfiguration config = new DefaultScreensConfiguration(
				tree, interactions);
		context.setClient(client);
		context.setContext(config);
		context.setData(ShoppingSample.getSampleContext());
		context.setLocale(Locale.getDefault());
		context.getScreens().getAttributes().put(KEY, context);
		String root = name("Root");
		context.getScreens().getTree().setRoot(root);
		renderIndex(root, context);
		return config;
	}
}
