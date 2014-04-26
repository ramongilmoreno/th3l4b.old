package com.th3l4b.srm.codegen.java.mongo;

import com.th3l4b.common.propertied.IPropertied;
import com.th3l4b.srm.base.IField;
import com.th3l4b.srm.base.normalized.INormalizedEntity;
import com.th3l4b.srm.base.normalized.INormalizedManyToOneRelationship;
import com.th3l4b.srm.base.normalized.INormalizedModel;
import com.th3l4b.srm.codegen.base.names.BaseNames;
import com.th3l4b.srm.codegen.java.basic.JavaCodeGeneratorContext;

public class MongoNames {

	public static final String MONGO_NAME_PROPERTY = MongoNames.class.getName()
			+ ".identifier";

	private BaseNames _baseNames;

	public MongoNames(BaseNames baseNames) {
		_baseNames = baseNames;
	}

	public String packageForMongo(JavaCodeGeneratorContext context) {
		return context.getPackage() + ".mongo";
	}

	public String fqnMongo(String clazz, JavaCodeGeneratorContext context) {
		return packageForMongo(context) + "." + clazz;
	}

	public String packageForMongoParsers(JavaCodeGeneratorContext context) {
		return packageForMongo(context) + ".parsers";
	}

	public String fqnMongoParsers(String clazz, JavaCodeGeneratorContext context) {
		return packageForMongoParsers(context) + "." + clazz;
	}

	public String parserMongo(INormalizedEntity entity) throws Exception {
		return _baseNames.name(entity) + "Parser";
	}

	public String finderMongo(INormalizedModel model) throws Exception {
		return "Abstract" + _baseNames.name(model) + "MongoFinder";
	}

	public String abstractMongoContext(INormalizedModel model) throws Exception {
		return "Abstract" + _baseNames.name(model) + "MongoContext";
	}

	public String parserContext(INormalizedModel model) throws Exception {
		return _baseNames.name(model) + "MongoParserContext";
	}

	public String collection(INormalizedEntity entity) throws Exception {
		return mongoName(_baseNames.name(entity), entity);
	}

	public String column(IField field) throws Exception {
		return mongoName(_baseNames.name(field), field);
	}

	public String column(INormalizedManyToOneRelationship relationship,
			INormalizedModel model) throws Exception {
		return mongoName(_baseNames.nameOfDirect(relationship, model),
				relationship);
	}

	private String mongoName(String name, IPropertied propertied)
			throws Exception {
		if (propertied.getProperties().containsKey(MONGO_NAME_PROPERTY)) {
			return propertied.getProperties().get(MONGO_NAME_PROPERTY);
		} else {
			return name;
		}
	}

	public static void main(String[] args) {
		System.out.println("a$b$d".replaceAll("\\$", ""));
	}
}
