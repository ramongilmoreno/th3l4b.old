package com.th3l4b.srm.base.original;

import com.th3l4b.common.named.INamedContainer;

public interface IModel {
	INamedContainer<? extends IEntity> entities () throws Exception;
	INamedContainer<? extends IRelationship> relationships () throws Exception;
}
