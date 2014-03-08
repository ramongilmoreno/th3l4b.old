package com.th3l4b.srm.codegen.java.basic;

import com.th3l4b.srm.base.normalized.INormalizedEntity;
import com.th3l4b.srm.base.normalized.INormalizedModel;
import com.th3l4b.srm.codegen.base.names.BaseNames;

public class JavaNames {

	private BaseNames _baseNames;

	public JavaNames(BaseNames baseNames) {
		_baseNames = baseNames;
	}

	public String fqn(String clazz, JavaCodeGeneratorContext context) {
		return context.getPackage() + "." + clazz;
	}

	public String packageForImpl(JavaCodeGeneratorContext context) {
		return packageForBase(context) + ".impl";
	}

	public String fqnImpl(String clazz, JavaCodeGeneratorContext context) {
		return packageForImpl(context) + "." + clazz;
	}

	public String packageForBase(JavaCodeGeneratorContext context) {
		return context.getPackage() + ".base";
	}

	public String fqnBase(String clazz, JavaCodeGeneratorContext context) {
		return packageForBase(context) + "." + clazz;
	}

	public String nameInterface(INormalizedEntity entity) throws Exception {
		return "I" + _baseNames.name(entity);
	}

	public String nameImpl(INormalizedEntity entity) throws Exception {
		return "Default" + _baseNames.identifier(entity.getName()) + entity;
	}

	public String finder(INormalizedModel model) throws Exception {
		return "I" + _baseNames.name(model) + "Finder";
	}

	public String context(INormalizedModel model) throws Exception {
		return "I" + _baseNames.name(model) + "Context";
	}

	public String modelUtils(INormalizedModel model) throws Exception {
		return "Default" + _baseNames.name(model) + "ModelUtils";
	}

	public String modelEntity(INormalizedModel model) throws Exception {
		return "I" + _baseNames.name(model) + "Entity";
	}
}
