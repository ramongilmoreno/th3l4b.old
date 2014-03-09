package com.th3l4b.srm.codegen.java.basic.tomap;

import com.th3l4b.common.propertied.IPropertied;
import com.th3l4b.srm.base.normalized.INormalizedEntity;
import com.th3l4b.srm.base.normalized.INormalizedModel;
import com.th3l4b.srm.codegen.base.names.BaseNames;

public class ToMapNames {

	private BaseNames _baseNames;

	public ToMapNames(BaseNames baseNames) {
		_baseNames = baseNames;
	}
	
	public String packageForToMap(ToMapCodeGeneratorContext context) {
		return context.getPackage() + ".tomap";
	}

	public String fqnToMap(String clazz, ToMapCodeGeneratorContext context) {
		return packageForToMap(context) + "." + clazz;
	}

	public String packageForToMapParsers(ToMapCodeGeneratorContext context) {
		return packageForToMap(context) + ".parsers";
	}

	public String fqnToMapParsers(String clazz, ToMapCodeGeneratorContext context) {
		return packageForToMapParsers(context) + "." + clazz;
	}

	protected String valueOrProperty(String value, IPropertied propertied)
			throws Exception {
		return _baseNames.valueOrProperty(value, ToMapNames.class.getName()
				+ ".identifier", propertied);
	}

	public String parserToMap(INormalizedEntity entity) throws Exception {
		return _baseNames.identifier(entity.getName()) + "Parser";
	}


	public String toMapParserContext(INormalizedModel model) throws Exception {
		return _baseNames.identifier(model.getName()) + "ToMapParserContext";
	}
		
}
