package com.th3l4b.srm.codegen.java.basicruntime.tomap.junit;

import java.util.Map;

import com.th3l4b.srm.codegen.java.basicruntime.junit.IEntityA;
import com.th3l4b.srm.codegen.java.basicruntime.junit.basicruntime.DefaultEntityA;
import com.th3l4b.srm.codegen.java.basicruntime.tomap.AbstractToMapEntityParser;
import com.th3l4b.srm.codegen.java.basicruntime.tomap.IToMapIdentifierParser;
import com.th3l4b.srm.codegen.java.basicruntime.tomap.IToMapStatusParser;
import com.th3l4b.types.runtime.IJavaRuntimeType;
import com.th3l4b.types.runtime.IJavaRuntimeTypesContext;

public class SampleEntityAToMapEntityParser extends
		AbstractToMapEntityParser<IEntityA> {

	private IJavaRuntimeType<String> _field_StringAttribute;

	public SampleEntityAToMapEntityParser(
			IToMapIdentifierParser identifierParser,
			IToMapStatusParser statusParser, IJavaRuntimeTypesContext java)
			throws Exception {
		super("SampleEntityA", identifierParser, statusParser);
		_field_StringAttribute = java.get("string", String.class);
	}

	@Override
	protected IEntityA create() throws Exception {
		return new DefaultEntityA();
	}

	@Override
	protected void parseRest(IEntityA entity, Map<String, String> map)
			throws Exception {
		if (getIdentifierParser().hasValue("EntityB", map)) {
			entity.setEntityB(getIdentifierParser().parse("EntityB", map));
		}
		if (map.containsKey("StringAttribute")) {
			entity.setStringAttribute(_field_StringAttribute.fromString(map
					.get("StringAttribute")));
		}
	}

	@Override
	protected void setRest(IEntityA value, Map<String, String> map)
			throws Exception {
		if (value.isSetEntityB()) {
			getIdentifierParser().set(value.getEntityB(), "EntityB", map);
		}
		if (value.isSetStringAttribute()) {
			map.put("StringAttribute",
					_field_StringAttribute.toString(value.getStringAttribute()));
		}
	}

}
