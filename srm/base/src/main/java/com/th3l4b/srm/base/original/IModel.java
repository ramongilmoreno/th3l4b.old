package com.th3l4b.srm.base.original;

import com.th3l4b.common.named.INamed;
import com.th3l4b.common.named.INamedContainer;

public interface IModel extends INamed {
	INamedContainer<IEntity> entities() throws Exception;
	INamedContainer<IRelationship> relationships() throws Exception;
}
