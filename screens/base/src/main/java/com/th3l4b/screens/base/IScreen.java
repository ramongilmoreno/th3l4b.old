package com.th3l4b.screens.base;

import com.th3l4b.common.data.tree.ITree;
import com.th3l4b.common.named.INamed;
import com.th3l4b.common.named.INamedContainer;

public interface IScreen extends ITree<IScreen>, INamedContainer<IScreenItem>,
		INamed {

}
