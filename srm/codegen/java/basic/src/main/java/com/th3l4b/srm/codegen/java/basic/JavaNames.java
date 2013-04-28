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
	public static String javaIdentifier(String name) {
		return TextUtils.cIdentifier(CamelUtils.toCamelCase(name));
	}

	protected static String valueOrProperty(String value, IPropertied propertied)
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
	
	public static String fqn (String clazz, JavaCodeGeneratorContext context) {
		return context.getPackage() + "." + clazz;
	}
	
	public static String packageForImpl (JavaCodeGeneratorContext context) {
		return context.getPackage() + ".basicruntime";
	}

	public static String fqnImpl (String clazz, JavaCodeGeneratorContext context) {
		return packageForImpl(context) + "." + clazz;
	}
	
	public static String name(INormalizedEntity entity) throws Exception {
		return "I" + valueOrProperty(javaIdentifier(entity.getName()), entity);
	}

	public static String nameImpl(INormalizedEntity entity) throws Exception {
		return "Default" + valueOrProperty(javaIdentifier(entity.getName()), entity);
	}
	
	public static String name(IField field) throws Exception {
		return valueOrProperty(javaIdentifier(field.getName()), field);
	}

	public static String finder(INormalizedModel model) throws Exception {
		return "I" + valueOrProperty(javaIdentifier(model.getName()) + "Finder",
				model);
	}

	public static String finderInMemory(INormalizedModel model) throws Exception {
		return "Abstract" + valueOrProperty(javaIdentifier(model.getName()) + "InMemoryFinder",
				model);
	}

	public static String nameOfReverse(INormalizedManyToOneRelationship relationship,
			INormalizedModel model) throws Exception {
		INamed reverse = relationship.getReverse();
		return valueOrProperty(javaIdentifier(reverse.getName()), reverse);
	}

	public static String nameOfDirect(INormalizedManyToOneRelationship relationship,
			INormalizedModel model) throws Exception {
		INamed direct = relationship.getDirect();
		return valueOrProperty(javaIdentifier(direct.getName()), direct);
	}

}
