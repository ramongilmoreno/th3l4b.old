package com.th3l4b.srm.base.original;

import com.th3l4b.common.java.EnumUtils;
import com.th3l4b.common.named.DefaultNamed;
import com.th3l4b.common.named.INamed;
import com.th3l4b.srm.base.IModelConstants;

public class DefaultRelationship extends DefaultNamed implements IRelationship,
		IModelConstants {
	
	protected INamed _direct;
	protected INamed _reverse;
	
	public DefaultRelationship(){
		super();
	}

	@Override
	public String getFrom() throws Exception {
		return getProperties().get(PROPERTY_RELATIONSHIP_FROM);
	}

	@Override
	public void setFrom(String from) throws Exception {
		getProperties().put(PROPERTY_RELATIONSHIP_FROM, from);
	}

	@Override
	public String getTo() throws Exception {
		return getProperties().get(PROPERTY_RELATIONSHIP_TO);
	}

	@Override
	public void setTo(String to) throws Exception {
		getProperties().put(PROPERTY_RELATIONSHIP_TO, to);
	}

	@Override
	public RelationshipType getType() throws Exception {
		return EnumUtils.fromString(
				getProperties().get(PROPERTY_RELATIONSHIP_TYPE),
				RelationshipType.class);
	}

	@Override
	public void setType(RelationshipType type) throws Exception {
		getProperties().put(PROPERTY_RELATIONSHIP_TYPE, type.name());
	}
	
	@Override
	public String getEntity() throws Exception {
		return getProperties().get(PROPERTY_RELATIONSHIP_ENTITY);
	}
	
	@Override
	public void setEntity(String entity) throws Exception {
		getProperties().put(PROPERTY_RELATIONSHIP_ENTITY, entity);
	}

	@Override
	public INamed getDirect() throws Exception {
		return _direct;
	}
	
	@Override
	public void setDirect(INamed direct) throws Exception {
		_direct = direct;
	}

	@Override
	public INamed getReverse() throws Exception {
		return _reverse;
	}
	
	@Override
	public void setReverse(INamed reverse) throws Exception {
		_reverse = reverse;
	}
}
