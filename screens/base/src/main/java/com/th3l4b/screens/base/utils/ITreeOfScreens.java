package com.th3l4b.screens.base.utils;

import com.th3l4b.common.data.tree.ITree;
import com.th3l4b.common.named.INamedContainer;
import com.th3l4b.screens.base.IScreen;

public interface ITreeOfScreens extends ITree<IScreen>, INamedContainer<IScreen> {
}
