package com.th3l4b.srm.base.original;

import com.th3l4b.common.named.DefaultNamed;
import com.th3l4b.common.named.DefaultNamedContainer;
import com.th3l4b.common.named.INamedContainer;

public class DefaultModel extends DefaultNamed implements IModel,
		IModelConstants {

	DefaultNamedContainer<IEntity> _entities = new DefaultNamedContainer<IEntity>();
	DefaultNamedContainer<IRelationship> _relationships = new DefaultNamedContainer<IRelationship>();

	@Override
	public INamedContainer<IEntity> entities() throws Exception {
		return _entities;
	}

	@Override
	public INamedContainer<IRelationship> relationships() throws Exception {
		return _relationships;
	}
}
