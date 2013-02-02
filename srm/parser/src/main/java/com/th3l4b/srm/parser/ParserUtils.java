package com.th3l4b.srm.parser;

import java.util.Map;

import com.th3l4b.srm.base.ModelUtils;
import com.th3l4b.srm.base.original.DefaultEntity;
import com.th3l4b.srm.base.original.DefaultRelationship;
import com.th3l4b.srm.base.original.IEntity;
import com.th3l4b.srm.base.original.IModel;
import com.th3l4b.srm.base.original.IRelationship;
import com.th3l4b.srm.base.original.RelationshipType;

public class ParserUtils {

	public static IEntity addEntity(String name, IModel model) {
		try {
			DefaultEntity item = new DefaultEntity(name);
			model.entities().add(item);
			return item;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void addProperties(IEntity entity,
			Map<String, String> properties) {
		try {
			entity.getProperties().putAll(properties);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static IRelationship addRelationship(String from, String to,
			String direct, String reverse, RelationshipType type, IModel model) {
		try {
			DefaultRelationship item = new DefaultRelationship(
					ModelUtils.relationshipName(from, to, direct, reverse),
					type);
			model.relationships().add(item);
			return item;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
