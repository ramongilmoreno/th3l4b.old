package com.th3l4b.srm.base.original;

import java.util.List;

import com.th3l4b.common.named.INamed;
import com.th3l4b.common.named.INamedContainer;

public interface IModel extends INamed {
	String getContext () throws Exception;
	List<String> imports () throws Exception;
	INamedContainer<IEntity> entities() throws Exception;
	INamedContainer<IRelationship> relationships() throws Exception;
}
