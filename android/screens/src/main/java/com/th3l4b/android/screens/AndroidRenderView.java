package com.th3l4b.android.screens;

import java.util.HashMap;

import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

import com.th3l4b.common.data.nullsafe.NullSafe;
import com.th3l4b.screens.base.IRenderingConstants;
import com.th3l4b.screens.base.IScreensConstants;
import com.th3l4b.screens.base.ITreeOfScreens;
import com.th3l4b.screens.base.utils.IScreensConfiguration;
import com.th3l4b.types.runtime.basicset.StringType;
import com.th3l4b.types.runtime.ui.IEditorListener;

public class AndroidRenderView implements IScreensConstants, IRenderingConstants {
	public String getLabel(String item, IScreensConfiguration context)
			throws Exception {
		String r = context.getTree().getProperty(item, LABEL);
		if (r == null) {
			r = item;
		}
		return r;
	}

	public void render(final IScreensConfiguration configuration,
			final IAndroidScreensClientDescriptor client,
			final Runnable runnable) throws Exception {
		final ITreeOfScreens tree = configuration.getTree();
		renderItem(tree.getRoot(), client.getViewGroup(), null, configuration,
				client, runnable);
	}

	private void renderItem(String screen, ViewGroup group,
			android.view.ViewGroup.LayoutParams layout,
			final IScreensConfiguration configuration,
			final IAndroidScreensClientDescriptor client,
			final Runnable runnable) throws Exception {
		boolean childrenMustBeProcessed = true;
		final ITreeOfScreens tree = configuration.getTree();
		String type = tree.getProperty(screen, TYPE);
		if (NullSafe.equals(type, TYPE_FIELD)) {
			EditTextFieldForType<String> field = new EditTextFieldForType<String>(
					screen, new StringType(1000), client);
			field.setValue(tree.getProperty(screen, VALUE));
			field.addEditorListener(new IEditorListener() {
				@Override
				public void valueChanged(String screen, String value) {
					try {
						tree.setProperty(screen, VALUE, value);
						if (tree.hasProperty(screen, INTERACTION)) {
							String java = tree.getProperty(screen,
									INTERACTION_JAVA);
							configuration
									.getInteractions()
									.get(java)
									.handleInteraction(screen, configuration,
											client);
							runnable.run();
						}
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				}
			});
			if (layout != null) {
				field.getEditText().setLayoutParams(layout);
			}
		} else if (NullSafe.equals(type, TYPE_ACTION)) {
			Button button = new Button(client.getActivity());
			button.setText(getLabel(screen, configuration));
			final String fscreen = screen;
			button.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					try {
						String java = tree.getProperty(fscreen,
								INTERACTION_JAVA);
						configuration
								.getInteractions()
								.get(java)
								.handleInteraction(fscreen, configuration,
										client);
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
					runnable.run();
				}
			});
			applyStatusAndTone(tree, screen, button);
			if (layout != null) {
				button.setLayoutParams(layout);
			}
			group.addView(button);
		} else if (NullSafe.equals(
				configuration.getTree().getProperty(screen, CONTAINER),
				CONTAINER_TABLE)) {
			renderTable(screen, group, layout, configuration, client, runnable);
			childrenMustBeProcessed = false;
		} else {
			String label = configuration.getTree().getProperty(screen, LABEL);
			if (label != null) {
				TextView text = new TextView(client.getActivity());
				text.setText(label);
				applyStatusAndTone(tree, screen, text);
				if (layout != null) {
					text.setLayoutParams(layout);
				}
				group.addView(text);
			}
		}

		if (childrenMustBeProcessed) {
			for (String child : tree.children(screen)) {
				renderItem(child, group, layout, configuration, client,
						runnable);
			}
		}
	}

	private void applyStatusAndTone(final ITreeOfScreens tree, String screen,
			TextView textView) throws Exception {
		String status = tree.getProperty(screen, STATUS);
		if (NullSafe.equals(status, STATUS_VALUE_GOOD)) {
			textView.setBackgroundColor(Color.GREEN);
		} else if (NullSafe.equals(status, STATUS_VALUE_BAD)) {
			textView.setBackgroundColor(Color.RED);
		}

		String tone = tree.getProperty(screen, TONE);
		if (NullSafe.equals(tone, TONE_VALUE_STRONG)) {
			textView.setTypeface(Typeface.DEFAULT_BOLD);
		} else if (NullSafe.equals(tone, TONE_VALUE_WEAK)) {
			textView.setTypeface(Typeface.MONOSPACE);
		}
	}

	private void renderTable(String screen, ViewGroup group,
			ViewGroup.LayoutParams layout, IScreensConfiguration configuration,
			final IAndroidScreensClientDescriptor client,
			final Runnable runnable) throws Exception {
		ITreeOfScreens tree = configuration.getTree();
		int rows = 0;
		int columns = 0;
		HashMap<String, String> cells = new HashMap<String, String>();

		String rowsParent = screen;
		for (String child : tree.children(screen)) {
			if (NullSafe.equals(CONTAINER, CONTAINER_TABLE_ROWS)) {
				rowsParent = child;
			}
		}

		for (String row : tree.children(rowsParent)) {
			int i = 0;
			for (String cell : tree.children(row)) {
				cells.put("" + rows + "," + i++, cell);
				columns = Math.max(columns, i);
			}
			rows++;
		}

		// Render everything.
		TableLayout table = new TableLayout(client.getActivity());
		if (layout != null) {
			table.setLayoutParams(layout);
		} else {
			table.setLayoutParams(new TableLayout.LayoutParams(
					TableLayout.LayoutParams.MATCH_PARENT,
					TableLayout.LayoutParams.MATCH_PARENT));
		}
		for (int i = 0; i < rows; i++) {
			TableRow row = new TableRow(client.getActivity());
			LayoutParams cellLayout = new TableRow.LayoutParams(0,
					TableRow.LayoutParams.WRAP_CONTENT);
			cellLayout.weight = 1;
			for (int j = 0; j < columns; j++) {
				String cell = cells.get("" + i + "," + j);
				if (cell != null) {
					renderItem(cell, row, cellLayout, configuration, client,
							runnable);
				} else {
					TextView text = new TextView(client.getActivity());
					text.setText("Empty");
					text.setLayoutParams(cellLayout);
					row.addView(text);
				}
			}
			table.addView(row);
		}
		group.addView(table);
	}
}
