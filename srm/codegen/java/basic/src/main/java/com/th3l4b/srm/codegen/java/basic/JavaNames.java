package com.th3l4b.srm.codegen.java.basic;

import java.util.Map;

import com.th3l4b.common.named.INamed;
import com.th3l4b.common.propertied.IPropertied;
import com.th3l4b.common.text.TextUtils;
import com.th3l4b.srm.base.IField;
import com.th3l4b.srm.base.normalized.INormalizedEntity;
import com.th3l4b.srm.base.normalized.INormalizedManyToOneRelationship;
import com.th3l4b.srm.base.normalized.INormalizedModel;
import com.th3l4b.srm.codegen.base.names.CamelUtils;

public class JavaNames {

	/**
	 * Creates a Java identifier out of the given input name.
	 */
	public String javaIdentifier(String name) {
		return TextUtils.cIdentifier(CamelUtils.toCamelCase(name));
	}

	protected String valueOrProperty(String value, IPropertied propertied)
			throws Exception {
		Map<String, String> map = propertied.getProperties();
		String property = JavaNames.class.getPackage().getName()
				+ ".identifier";
		if (map.containsKey(property)) {
			return map.get(property);
		} else {
			return value;
		}
	}

	public String fqn(String clazz, JavaCodeGeneratorContext context) {
		return context.getPackage() + "." + clazz;
	}

	public String packageForImpl(JavaCodeGeneratorContext context) {
		return context.getPackage() + ".basicruntime";
	}

	public String fqnImpl(String clazz, JavaCodeGeneratorContext context) {
		return packageForImpl(context) + "." + clazz;
	}

	public String name(INormalizedEntity entity) throws Exception {
		return valueOrProperty(javaIdentifier(entity.getName()), entity);
	}

	public String nameInterface(INormalizedEntity entity) throws Exception {
		return "I" + name(entity);
	}
	
	public String nameImpl(INormalizedEntity entity) throws Exception {
		return "Default"
				+ valueOrProperty(javaIdentifier(entity.getName()), entity);
	}

	public String name(IField field) throws Exception {
		return valueOrProperty(javaIdentifier(field.getName()), field);
	}

	public String finder(INormalizedModel model) throws Exception {
		return "I"
				+ valueOrProperty(javaIdentifier(model.getName()) + "Finder",
						model);
	}

	public String context(INormalizedModel model) throws Exception {
		return "I"
				+ valueOrProperty(javaIdentifier(model.getName()) + "Context",
						model);
	}

	
	public String modelUtils(INormalizedModel model) throws Exception {
		return ""
				+ valueOrProperty(javaIdentifier(model.getName())
						+ "ModelUtils", model);
	}

	public String modelEntity(INormalizedModel model) throws Exception {
		return "I"
				+ valueOrProperty(javaIdentifier(model.getName()) + "Entity",
						model);
	}

	public String nameOfReverse(INormalizedManyToOneRelationship relationship,
			INormalizedModel model) throws Exception {
		INamed reverse = relationship.getReverse();
		return valueOrProperty(javaIdentifier(reverse.getName()), reverse);
	}

	public String nameOfDirect(INormalizedManyToOneRelationship relationship,
			INormalizedModel model) throws Exception {
		INamed direct = relationship.getDirect();
		return valueOrProperty(javaIdentifier(direct.getName()), direct);
	}

}
