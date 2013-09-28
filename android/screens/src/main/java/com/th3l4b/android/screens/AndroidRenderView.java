package com.th3l4b.android.screens;

import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.th3l4b.common.data.nullsafe.NullSafe;
import com.th3l4b.common.data.tree.TreeUtils;
import com.th3l4b.screens.base.IRenderingConstants;
import com.th3l4b.screens.base.IScreensContants;
import com.th3l4b.screens.base.ITreeOfScreens;
import com.th3l4b.screens.base.utils.AsTree;
import com.th3l4b.screens.base.utils.IScreensConfiguration;
import com.th3l4b.types.runtime.basicset.StringType;
import com.th3l4b.types.runtime.ui.IEditorListener;

public class AndroidRenderView {
	public String getLabel(String item, IScreensConfiguration context)
			throws Exception {
		String r = context.getTree().getProperty(item, IScreensContants.LABEL);
		if (r == null) {
			r = item;
		}
		return r;
	}

	public void render(final IScreensConfiguration configuration,
			final IAndroidScreensClientDescriptor client,
			final Runnable runnable) throws Exception {
		final ITreeOfScreens tree = configuration.getTree();
		for (String screen : TreeUtils.dfs(AsTree.getTree(tree))) {
			String type = tree.getProperty(screen, IScreensContants.TYPE);
			if (NullSafe.equals(type, IScreensContants.TYPE_FIELD)) {
				EditTextFieldForType<String> field = new EditTextFieldForType<String>(
						screen, new StringType(1000), client);
				field.setValue(tree.getProperty(screen, IScreensContants.VALUE));
				field.addEditorListener(new IEditorListener() {
					@Override
					public void valueChanged(String screen, String value) {
						try {
							tree.setProperty(screen, IScreensContants.VALUE,
									value);
							if (tree.hasProperty(screen,
									IScreensContants.INTERACTION)) {
								String java = tree.getProperty(screen,
										IScreensContants.INTERACTION_JAVA);
								configuration
										.getInteractions()
										.get(java)
										.handleInteraction(screen,
												configuration, client);
							}
							runnable.run();
						} catch (Exception e) {
							throw new RuntimeException(e);
						}
					}
				});
			} else if (NullSafe.equals(type, IScreensContants.TYPE_ACTION)) {
				Button button = new Button(client.getActivity());
				button.setText(getLabel(screen, configuration));
				final String fscreen = screen;
				button.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View arg0) {
						try {
							String java = tree.getProperty(fscreen,
									IScreensContants.INTERACTION_JAVA);
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
				client.getViewGroup().addView(button);
			} else {
				TextView text = new TextView(client.getActivity());
				text.setText(getLabel(screen, configuration));
				applyStatusAndTone(tree, screen, text);
				client.getViewGroup().addView(text);
			}
		}
	}

	private void applyStatusAndTone(final ITreeOfScreens tree, String screen,
			TextView textView) throws Exception {
		String status = tree.getProperty(screen, IRenderingConstants.STATUS);
		if (NullSafe.equals(status, IRenderingConstants.STATUS_VALUE_GOOD)) {
			textView.setBackgroundColor(Color.GREEN);
		} else if (NullSafe
				.equals(status, IRenderingConstants.STATUS_VALUE_BAD)) {
			textView.setBackgroundColor(Color.RED);
		}

		String tone = tree.getProperty(screen, IRenderingConstants.TONE);
		if (NullSafe.equals(tone, IRenderingConstants.TONE_VALUE_STRONG)) {
			textView.setTypeface(Typeface.DEFAULT_BOLD);
		} else if (NullSafe.equals(tone, IRenderingConstants.TONE_VALUE_WEAK)) {
			textView.setTypeface(Typeface.MONOSPACE);
		}

	}
}
