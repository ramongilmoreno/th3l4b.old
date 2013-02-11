package com.th3l4b.srm.parser;

import java.io.InputStream;
import java.util.Map;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;

import com.th3l4b.common.named.INamed;
import com.th3l4b.common.propertied.IPropertied;
import com.th3l4b.srm.base.IField;
import com.th3l4b.srm.base.original.IEntity;
import com.th3l4b.srm.base.original.IModel;
import com.th3l4b.srm.base.original.IRelationship;

public class ParserUtils {
	
	public static void setName(String name, INamed named) {
		try {
			named.setName(name);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void addProperties(Map<String, String> properties,
			IPropertied propertied) {
		try {
			propertied.getProperties().putAll(properties);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void addEntity(IEntity entity, IModel model) {
		try {
			model.entities().add(entity);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void setFrom(String from, IRelationship relationship) {
		try {
			relationship.setFrom(from);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void setTo(String to, IRelationship relationship) {
		try {
			relationship.setTo(to);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void setDirectName(String directName, IRelationship relationship) {
		try {
			relationship.setName(directName);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void setReverseName (String reverseName, IRelationship relationship) {
		try {
			relationship.setReverseName(reverseName);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void setType(String type, IField field) {
		try {
			field.setType(type);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void addField(IField field, IEntity entity) {
		try {
			entity.add(field);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static IModel parse(InputStream is) throws Exception {
		SRMGrammarLexer lex = new SRMGrammarLexer(new ANTLRInputStream(is));
		CommonTokenStream tokens = new CommonTokenStream(lex);
		SRMGrammarParser parser = new SRMGrammarParser(tokens);
		parser.document();
		return parser.getModel();
	}

}
