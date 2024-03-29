package com.th3l4b.apps.shopping.base;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.LinkedHashMap;
import java.util.Locale;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;

import com.th3l4b.apps.shopping.base.codegen.srm.IItem;
import com.th3l4b.apps.shopping.base.codegen.srm.INeed;
import com.th3l4b.apps.shopping.base.codegen.srm.base.DefaultShoppingModelUtils;
import com.th3l4b.apps.shopping.base.codegen.srm.base.IShoppingContext;
import com.th3l4b.apps.shopping.base.codegen.srm.base.IShoppingFinder;
import com.th3l4b.apps.shopping.base.codegen.srm.sync.ShoppingDiffContext;
import com.th3l4b.apps.shopping.base.codegen.srm.tomap.ShoppingToMapParserContext;
import com.th3l4b.common.data.Pair;
import com.th3l4b.common.data.nullsafe.NullSafe;
import com.th3l4b.common.data.predicate.IPredicate;
import com.th3l4b.common.data.predicate.PredicateUtils;
import com.th3l4b.common.text.ITextConstants;
import com.th3l4b.screens.base.IRenderingConstants;
import com.th3l4b.screens.base.IScreensConstants;
import com.th3l4b.screens.base.ITreeOfScreens;
import com.th3l4b.screens.base.addon.AbstractToggleInteractionListener;
import com.th3l4b.screens.base.interaction.IInteractionListener;
import com.th3l4b.screens.base.utils.DefaultScreensConfiguration;
import com.th3l4b.screens.base.utils.DefaultTreeOfScreens;
import com.th3l4b.screens.base.utils.IScreensClientDescriptor;
import com.th3l4b.screens.base.utils.IScreensConfiguration;
import com.th3l4b.srm.codegen.java.basic.runtime.DefaultIdentifier;
import com.th3l4b.srm.codegen.java.basic.runtime.tomap.DefaultToMapIdentifierParser;
import com.th3l4b.srm.codegen.java.basic.runtime.tomap.DefaultToMapStatusParser;
import com.th3l4b.srm.codegen.java.sync.runtime.AbstractSyncTool;
import com.th3l4b.srm.codegen.java.sync.runtime.IDiffContext;
import com.th3l4b.srm.codegen.java.web.runtime.AbstractJSONEntitiesServlet;
import com.th3l4b.srm.codegen.java.web.runtime.JSONEntitiesParser;
import com.th3l4b.srm.runtime.EntityStatus;
import com.th3l4b.srm.runtime.ICoordinates;
import com.th3l4b.srm.runtime.IIdentifier;
import com.th3l4b.srm.runtime.IModelUtils;
import com.th3l4b.srm.runtime.IRuntimeEntity;
import com.th3l4b.srm.runtime.SRMContextUtils;
import com.th3l4b.types.runtime.basicset.JavaRuntimeTypesBasicSet;

public class Shopping implements IScreensConstants, IRenderingConstants {

	private static final String KEY = Shopping.class.getPackage().getName();
	private static final String STATUS_ENUM_VALUE = KEY + ".status";

	protected String name(String n) {
		return Shopping.class.getPackage().getName() + "." + n;
	}

	protected String nameOfLater(String needId) throws Exception {
		return name("need - later - " + needId);
	}

	protected String nameOfInBasket(String needId) throws Exception {
		return name("need - inbasket - " + needId);
	}

	protected String localizedLabel(String name, IShoppingApplication client) {
		return name;
	}

	protected ITreeOfScreens clearRoot(String root, IShoppingApplication context)
			throws Exception {
		return clearRoot(root, context, false);
	}

	protected ITreeOfScreens clearRoot(final String root,
			IShoppingApplication application, boolean atIndex) throws Exception {
		IScreensConfiguration screens = application.getScreens();
		screens.getInteractions().clear();
		ITreeOfScreens tree = screens.getTree();
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
			screens.getInteractions().put(name, new IInteractionListener() {
				@Override
				public void handleInteraction(String screen,
						IScreensConfiguration context,
						IScreensClientDescriptor client) throws Exception {
					renderIndex(root, getShoppingApplication(context));
				}
			});
		}

		return tree;

	}

	protected void renderIndex(final String root,
			IShoppingApplication application) throws Exception {
		ITreeOfScreens tree = clearRoot(root, application, true);
		{
			String text = "Choose activity";
			String name = name(text);
			tree.addScreen(name, root);
			tree.setProperty(name, ORDER_INDEX, "0");
			tree.setProperty(name, LABEL, localizedLabel(text, application));
		}
		{
			String text = "Start shopping";
			String name = name(text);
			tree.addScreen(name, root);
			tree.setProperty(name, ORDER_INDEX, "1");
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
							renderShopping(root,
									getShoppingApplication(context));
						}
					});
		}
		{
			String text = "Select items";
			String name = name(text);
			tree.addScreen(name, root);
			tree.setProperty(name, ORDER_INDEX, "2");
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
							renderItems(root, getShoppingApplication(context));
						}
					});
		}
		{
			String text = "Sync";
			String name = name(text);
			tree.addScreen(name, root);
			tree.setProperty(name, ORDER_INDEX, "3");
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

							HttpClient http = new HttpClient();
							PostMethod post = new PostMethod(
									"http://sample-ramongilmoreno.rhcloud.com/web/SyncServlet");
							IShoppingApplication application = getShoppingApplication(context);
							final IShoppingContext srm = application.getData();
							Iterable<IRuntimeEntity<?>> backup = srm
									.getFinder().backup();
							StringWriter writer = new StringWriter();
							JSONEntitiesParser parser = new JSONEntitiesParser();
							ShoppingToMapParserContext toMap = new ShoppingToMapParserContext(
									new DefaultToMapIdentifierParser(),
									new DefaultToMapStatusParser(),
									new JavaRuntimeTypesBasicSet(),
									new DefaultShoppingModelUtils());
							parser.serialize(backup, writer, srm, toMap);
							writer.flush();
							post.setRequestEntity(new StringRequestEntity(
									writer.getBuffer().toString(),
									AbstractJSONEntitiesServlet.JSON_CONTENT_TYPE,
									ITextConstants.UTF_8));

							int result = http.executeMethod(post);
							String msg;
							if (result == 200) {
								msg = "OK - 200";

								StringReader reader = new StringReader(post
										.getResponseBodyAsString());
								Pair<IRuntimeEntity<?>, Iterable<IRuntimeEntity<?>>> parsed = parser
										.parse(false, true, reader, srm, toMap);
								AbstractSyncTool<IShoppingFinder, IShoppingContext> sync = new AbstractSyncTool<IShoppingFinder, IShoppingContext>() {
									@Override
									protected IShoppingContext getContext()
											throws Exception {
										return srm;
									}

									@Override
									protected IDiffContext getDiffContext()
											throws Exception {
										return new ShoppingDiffContext();
									}
								};
								srm.update(sync.sync(SRMContextUtils.map(parsed
										.getB())));
								msg = "OK";
							} else {
								System.out.println("HTTP Error: " + result);
								msg = "KO";
							}
							{
								ITreeOfScreens tree = application.getScreens()
										.getTree();
								String text = msg;
								String name = name(text);
								tree.addScreen(name, root);
								tree.setProperty(name, ORDER_INDEX, "5");
								tree.setProperty(name, LABEL,
										localizedLabel(text, application));
							}

						}
					});
		}
	}

	enum Status {
		normal, later, marked
	};

	protected void addLaterAction(final String needName, final String needRow,
			IShoppingApplication application) throws Exception {
		ITreeOfScreens tree = application.getScreens().getTree();
		String id = tree.getProperty(needName, KEY);
		String name = nameOfLater(id);
		tree.addScreen(name, needRow);
		tree.setProperty(name, LABEL, localizedLabel("Later", application));
		tree.setProperty(name, ORDER_INDEX, Integer.toString(2));
		tree.setProperty(name, TYPE, TYPE_ACTION);
		tree.setProperty(name, INTERACTION, "true");
		tree.setProperty(name, INTERACTION_JAVA, name);
		application.getScreens().getInteractions()
				.put(name, new IInteractionListener() {
					@Override
					public void handleInteraction(String screen,
							IScreensConfiguration context,
							IScreensClientDescriptor client) throws Exception {
						updateNeed(needName, needRow, context, client,
								Boolean.FALSE, Boolean.TRUE);
					}
				});

	}

	protected void addInBasket(final String needName, final String needRow,
			IShoppingApplication context) throws Exception {
		ITreeOfScreens tree = context.getScreens().getTree();
		String id = tree.getProperty(needName, KEY);
		String name = nameOfInBasket(id);
		tree.addScreen(name, needRow);
		tree.setProperty(name, LABEL, localizedLabel("In basket", context));
		tree.setProperty(name, ORDER_INDEX, Integer.toString(2));
	}

	protected void updateNeed(String needName, String needRow,
			IScreensConfiguration context, IScreensClientDescriptor client,
			Boolean toggleMarked, Boolean toggleLater) throws Exception {
		ITreeOfScreens tree = context.getTree();
		IShoppingApplication application = getShoppingApplication(context);
		String idAsString = tree.getProperty(needName, KEY);
		IIdentifier id = new DefaultIdentifier(INeed.class, idAsString);
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
					remove.coordinates().setStatus(EntityStatus.Remove);
					updates.put(id, remove);
				}
				tree.removeScreen(nameOfLater(idAsString));
				addInBasket(needName, needRow, application);
				break;
			case marked:
				status = Status.normal;
				{
					INeed remove = utils.create(INeed.class);
					remove.coordinates().setIdentifier(id);
					remove.coordinates().setStatus(EntityStatus.Modify);
					updates.put(id, remove);
				}
				tree.removeScreen(nameOfInBasket(idAsString));
				addLaterAction(needName, needRow, application);
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

	protected void renderAdd(final String root, String value,
			IShoppingApplication application) throws Exception {
		ITreeOfScreens tree = clearRoot(root, application);
		String add = name("Add item");
		tree.addScreen(add, root);
		tree.setProperty(add, ORDER_INDEX, "0");
		tree.setProperty(add, IScreensConstants.LABEL, "Add item");

		final String nameField = name("Name");
		tree.addScreen(nameField, add);
		tree.setProperty(nameField, ORDER_INDEX, "1");
		tree.setProperty(nameField, TYPE, TYPE_FIELD);
		tree.setProperty(nameField, VALUE, value);

		String ok = name("Confirm add");
		tree.addScreen(ok, add);
		tree.setProperty(ok, IScreensConstants.LABEL, "Confirm add");
		tree.setProperty(ok, ORDER_INDEX, "3"); // Keep add button in the same
												// position
		tree.setProperty(ok, TYPE, TYPE_ACTION);
		tree.setProperty(ok, INTERACTION_JAVA, ok);
		tree.setProperty(ok, INTERACTION, "true");
		application.getScreens().getInteractions()
				.put(ok, new IInteractionListener() {
					@Override
					public void handleInteraction(String screen,
							IScreensConfiguration context,
							IScreensClientDescriptor client) throws Exception {
						String name = context.getTree().getProperty(nameField,
								IScreensConstants.VALUE);
						if ((name != null) && (name.trim().length() > 0)) {
							IShoppingApplication application = getShoppingApplication(context);
							LinkedHashMap<IIdentifier, IRuntimeEntity<?>> updates = new LinkedHashMap<IIdentifier, IRuntimeEntity<?>>();
							IItem item = application.getData().getUtils()
									.create(IItem.class);
							item.setName(name);
							updates.put(item.coordinates().getIdentifier(),
									item);
							application.getData().update(updates);
							renderItems(root, getShoppingApplication(context));
						}
					}
				});

		String cancel = name("Cancel");
		tree.addScreen(cancel, add);
		tree.setProperty(cancel, IScreensConstants.LABEL, "Cancel");
		tree.setProperty(cancel, ORDER_INDEX, "2"); // Keep "Add" button in the
													// same position
		tree.setProperty(cancel, TYPE, TYPE_ACTION);
		tree.setProperty(cancel, INTERACTION_JAVA, cancel);
		tree.setProperty(cancel, INTERACTION, "true");
		application.getScreens().getInteractions()
				.put(cancel, new IInteractionListener() {
					@Override
					public void handleInteraction(String screen,
							IScreensConfiguration context,
							IScreensClientDescriptor client) throws Exception {
						renderItems(root, getShoppingApplication(context));
					}
				});

	}

	protected void renderShopping(String root, IShoppingApplication application)
			throws Exception {
		ITreeOfScreens tree = clearRoot(root, application);
		String itemsChild = name("Items");
		tree.addScreen(itemsChild, root);
		tree.setProperty(itemsChild, LABEL, "Items");
		tree.setProperty(itemsChild, ORDER_INDEX, "0");

		String itemsTable = name("Items table");
		tree.addScreen(itemsTable, itemsChild);
		tree.setProperty(itemsTable, IRenderingConstants.CONTAINER,
				IRenderingConstants.CONTAINER_TABLE);

		// Render items
		int index = 0;
		IShoppingFinder finder = application.getData().getFinder();
		for (INeed need : finder.allNeed()) {
			String id = need.coordinates().getIdentifier().getKey();
			final String needRow = name("row - " + id);
			tree.addScreen(needRow, itemsTable);
			tree.setProperty(needRow, ORDER_INDEX, Integer.toString(index++));

			final String needName = name("need of - " + id);
			{
				IItem item = need.getItem(finder);
				String name = needName;
				tree.addScreen(name, needRow);
				tree.setProperty(name, ORDER_INDEX, Integer.toString(1));
				tree.setProperty(name, LABEL, item.getName());
				tree.setProperty(name, TYPE, TYPE_ACTION);
				tree.setProperty(name, INTERACTION, "true");
				tree.setProperty(name, INTERACTION_JAVA, name);
				tree.setProperty(name, KEY, id);
				tree.setProperty(name, STATUS_ENUM_VALUE,
						Status.normal.toString());
				application.getScreens().getInteractions()
						.put(name, new IInteractionListener() {
							@Override
							public void handleInteraction(String screen,
									IScreensConfiguration context,
									IScreensClientDescriptor client)
									throws Exception {
								updateNeed(needName, needRow, context, client,
										Boolean.TRUE, Boolean.FALSE);
							}
						});
			}
			addLaterAction(needName, needRow, application);
		}

	}

	protected void renderItems(String root, IShoppingApplication application)
			throws Exception {
		renderItems(application.getData().getFinder().allItem(), root,
				application);
	}

	protected void renderItems(Iterable<IItem> items, final String root,
			IShoppingApplication application) throws Exception {
		ITreeOfScreens tree = clearRoot(root, application);
		renderSearch(root, application, new ISearchInteractionListener() {
			@Override
			public void handleInteraction(final String searchValue,
					String screen, IScreensConfiguration context,
					IScreensClientDescriptor client) throws Exception {
				IShoppingApplication application = getShoppingApplication(context);
				Iterable<IItem> filtered = PredicateUtils.filter(application
						.getData().getFinder().allItem(),
						new IPredicate<IItem>() {
							@Override
							public boolean accept(IItem t) throws Exception {
								String n = t.getName();
								if (n == null) {
									n = "";
								}
								return n.indexOf(searchValue) != -1;
							}
						});
				renderItems(filtered, root, application);
			}
		});

		String addButton = name("Add");
		tree.addScreen(addButton, root);
		tree.setProperty(addButton, LABEL, localizedLabel("Add", application));
		tree.setProperty(addButton, ORDER_INDEX, "1");
		tree.setProperty(addButton, TYPE, TYPE_ACTION);
		tree.setProperty(addButton, INTERACTION_JAVA, addButton);
		tree.setProperty(addButton, INTERACTION, "true");
		application.getScreens().getInteractions()
				.put(addButton, new IInteractionListener() {
					@Override
					public void handleInteraction(String screen,
							IScreensConfiguration context,
							IScreensClientDescriptor client) throws Exception {
						renderAdd(
								root,
								context.getTree().getProperty(
										name(SEARCH_FIELD), VALUE),
								getShoppingApplication(context));
					}
				});

		String itemsChild = name("Items");
		tree.addScreen(itemsChild, root);
		tree.setProperty(itemsChild, LABEL,
				localizedLabel("Items", application));
		tree.setProperty(itemsChild, ORDER_INDEX, "2");

		String itemsTable = name("Items table");
		tree.addScreen(itemsTable, itemsChild);
		tree.setProperty(itemsTable, IRenderingConstants.CONTAINER,
				IRenderingConstants.CONTAINER_TABLE);

		int index = 0;
		for (IItem item : items) {
			String idAsString = item.coordinates().getIdentifier().getKey();
			String itemRow = name("row - " + idAsString);
			tree.addScreen(itemRow, itemsTable);
			tree.setProperty(itemRow, ORDER_INDEX, Integer.toString(index++));
			tree.setProperty(itemRow, IRenderingConstants.CONTAINER,
					IRenderingConstants.CONTAINER_TABLE_ROW);

			{
				String name = name("item - " + idAsString);
				tree.addScreen(name, itemRow);
				tree.setProperty(name, LABEL, item.getName());
				tree.setProperty(name, KEY, idAsString);
				tree.setProperty(name, ORDER_INDEX, Integer.toString(0));
				tree.setProperty(name, TYPE, TYPE_ACTION);
				tree.setProperty(name, INTERACTION, "true");
				tree.setProperty(name, INTERACTION_JAVA, name);
				boolean hasNeeds = itemsHasNeeds(item.coordinates()
						.getIdentifier(), application);
				AbstractToggleInteractionListener.setStatus(tree, name,
						hasNeeds);
				tree.setProperty(name, TONE, hasNeeds ? TONE_VALUE_STRONG
						: TONE_VALUE_NORMAL);
				application.getScreens().getInteractions()
						.put(name, new AbstractToggleInteractionListener() {
							@Override
							protected void handleInteraction(String screen,
									boolean status,
									IScreensConfiguration context,
									IScreensClientDescriptor client)
									throws Exception {
								IShoppingApplication application = getShoppingApplication(context);
								ITreeOfScreens tree = context.getTree();
								String itemAsString = tree.getProperty(screen,
										KEY);
								IIdentifier itemAsId = new DefaultIdentifier(
										IItem.class, itemAsString);

								// Clean needs.
								LinkedHashMap<IIdentifier, IRuntimeEntity<?>> updates = new LinkedHashMap<IIdentifier, IRuntimeEntity<?>>();
								IShoppingContext data = application.getData();
								for (INeed need : data.getFinder()
										.findAllNeedFromItem(itemAsId)) {
									need.coordinates().setStatus(
											EntityStatus.Remove);
									updates.put(need.coordinates()
											.getIdentifier(), need);
								}
								// Create it if selected.
								if (status) {
									INeed need = data.getUtils().create(
											INeed.class);
									need.setItem(itemAsId);
									updates.put(need.coordinates()
											.getIdentifier(), need);
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
			{
				String name = name("delete - " + idAsString);
				tree.addScreen(name, itemRow);
				tree.setProperty(name, LABEL,
						localizedLabel("Delete", application));
				tree.setProperty(name, KEY, idAsString);
				tree.setProperty(name, ORDER_INDEX, Integer.toString(1));
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
								renderDelete(root, context.getTree()
										.getProperty(screen, KEY),
										getShoppingApplication(context));
							}
						});
			}
		}
	}

	public void renderDelete(final String root, String itemIdAsString,
			IShoppingApplication application) throws Exception {
		ITreeOfScreens tree = clearRoot(root, application);
		{
			String title = "Are you sure?";
			String name = name(title);
			tree.addScreen(name, root);
			tree.setProperty(name, LABEL, localizedLabel(title, application));
			tree.setProperty(name, ORDER_INDEX, Integer.toString(0));
		}
		{
			// Delete the item
			IIdentifier itemAsId = new DefaultIdentifier(IItem.class,
					itemIdAsString);
			IShoppingFinder finder = application.getData().getFinder();
			IItem item = finder.getItem(itemAsId);

			String name = name("delete - " + itemIdAsString);
			tree.addScreen(name, root);
			tree.setProperty(name, LABEL,
					localizedLabel("Delete " + item.getName(), application));
			tree.setProperty(name, KEY, itemIdAsString);
			tree.setProperty(name, ORDER_INDEX, Integer.toString(1));
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
							IShoppingApplication application = getShoppingApplication(context);
							ITreeOfScreens tree = context.getTree();
							String itemAsString = tree.getProperty(screen, KEY);
							IIdentifier itemAsId = new DefaultIdentifier(
									IItem.class, itemAsString);

							// Delete the item
							IShoppingFinder finder = application.getData()
									.getFinder();
							IItem item = finder.getItem(itemAsId);
							ICoordinates coord = item.coordinates();
							coord.setStatus(EntityStatus.Remove);
							LinkedHashMap<IIdentifier, IRuntimeEntity<?>> updates = new LinkedHashMap<IIdentifier, IRuntimeEntity<?>>();
							IIdentifier id = coord.getIdentifier();
							updates.put(id, item);

							// And associated needs
							for (INeed need : finder.findAllNeedFromItem(id)) {
								ICoordinates nc = need.coordinates();
								nc.setStatus(EntityStatus.Remove);
								updates.put(nc.getIdentifier(), need);
							}

							// Commit
							application.getData().update(updates);

							// Back to items screen
							renderItems(root, application);
						}
					});
		}
		{
			String name = name("back to items");
			tree.addScreen(name, root);
			tree.setProperty(name, LABEL,
					localizedLabel("Back to items", application));
			tree.setProperty(name, KEY, itemIdAsString);
			tree.setProperty(name, ORDER_INDEX, Integer.toString(2));
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
							renderItems(root, getShoppingApplication(context));
						}
					});
		}
	}

	public interface ISearchInteractionListener {
		void handleInteraction(String searchValue, String screen,
				IScreensConfiguration context, IScreensClientDescriptor client)
				throws Exception;
	}

	/**
	 * Simple class to manage searches.
	 */
	public static class SearchInteractionListener implements
			IInteractionListener {

		private String _searchFieldName;
		private ISearchInteractionListener _listener;

		public SearchInteractionListener(String searchFieldName,
				ISearchInteractionListener listener) {
			_searchFieldName = searchFieldName;
			_listener = listener;
		}

		@Override
		public void handleInteraction(String screen,
				IScreensConfiguration context, IScreensClientDescriptor client)
				throws Exception {
			String value = context.getTree().getProperty(_searchFieldName,
					IScreensConstants.VALUE);
			if (value == null) {
				value = "";
			}
			_listener.handleInteraction(value, screen, context, client);
		}
	}

	public static final String SEARCH_FIELD = "Search field";

	protected void renderSearch(String root, IShoppingApplication application,
			ISearchInteractionListener listener) throws Exception {
		ITreeOfScreens tree = application.getScreens().getTree();
		String search = name("Search");
		tree.addScreen(search, root);
		tree.setProperty(search, ORDER_INDEX, "0");
		tree.setProperty(search, LABEL,
				localizedLabel("Enter input", application));

		String searchField = name(SEARCH_FIELD);
		tree.addScreen(searchField, search);
		tree.setProperty(searchField, ORDER_INDEX, "1");
		tree.setProperty(searchField, TYPE, TYPE_FIELD);

		String searchButton = name("Search button");
		tree.addScreen(searchButton, search);
		tree.setProperty(searchButton, LABEL,
				localizedLabel("Search", application));
		tree.setProperty(searchButton, ORDER_INDEX, "2");
		tree.setProperty(searchButton, TYPE, TYPE_ACTION);
		tree.setProperty(searchButton, INTERACTION_JAVA, searchButton);
		tree.setProperty(searchButton, INTERACTION, "true");
		application
				.getScreens()
				.getInteractions()
				.put(searchButton,
						new SearchInteractionListener(searchField, listener));
	}

	private boolean itemsHasNeeds(IIdentifier identifier,
			IShoppingApplication context) throws Exception {
		return context.getData().getFinder().findAllNeedFromItem(identifier)
				.iterator().hasNext();
	}

	public IScreensConfiguration sample(IScreensClientDescriptor client)
			throws Exception {
		return sample(client, ShoppingSample.getSampleContext());
	}

	public IScreensConfiguration sample(IScreensClientDescriptor client,
			IShoppingContext data) throws Exception {
		LinkedHashMap<String, IInteractionListener> interactions = new LinkedHashMap<String, IInteractionListener>();
		DefaultTreeOfScreens tree = new DefaultTreeOfScreens();
		DefaultScreensConfiguration screens = new DefaultScreensConfiguration(
				tree, interactions);
		IShoppingApplication application = setupShoppipngApplication(data,
				screens);
		String root = name("Root");
		screens.getTree().setRoot(root);
		renderIndex(root, application);
		return screens;
	}

	public static IShoppingApplication getShoppingApplication(
			IScreensConfiguration screens) throws Exception {
		return (IShoppingApplication) screens.getAttributes().get(KEY);
	}

	public static void setShoppingApplication(IShoppingApplication application,
			IScreensConfiguration configuration) throws Exception {
		configuration.getAttributes().put(KEY, application);
	}

	public static IShoppingApplication setupShoppipngApplication(
			IShoppingContext context, IScreensConfiguration screens)
			throws Exception {
		IShoppingApplication application = new DefaultShoppingApplication();
		application.setScreens(screens);
		application.setData(context);
		application.setLocale(Locale.getDefault());
		setShoppingApplication(application, screens);
		return application;
	}
}
