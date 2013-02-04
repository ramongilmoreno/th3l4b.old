package com.th3l4b.srm.base.original;

import java.util.List;

import com.th3l4b.common.named.DefaultNamed;
import com.th3l4b.common.named.DefaultNamedContainer;
import com.th3l4b.common.named.INamedContainer;
import com.th3l4b.srm.base.ModelUtils;

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

	@Override
	public String getContext() throws Exception {
		return getProperties().get(PROPERTY_CONTEXT);
	}

	@Override
	public void setContext(String context) throws Exception {
		getProperties().put(PROPERTY_CONTEXT, context);

	}

	@Override
	public List<String> getImports() throws Exception {
		return ModelUtils.stringAsList(getProperties().get(PROPERTY_IMPORTS));
	}

	@Override
	public void setImports(List<String> imports) throws Exception {
		getProperties().put(PROPERTY_IMPORTS, ModelUtils.listAsString(imports));

	}

}
