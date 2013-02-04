package com.th3l4b.srm.parser;

import java.io.InputStream;
import java.util.Map;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;

import com.th3l4b.common.named.INamed;
import com.th3l4b.common.propertied.IPropertied;
import com.th3l4b.srm.base.ModelUtils;
import com.th3l4b.srm.base.original.DefaultRelationship;
import com.th3l4b.srm.base.original.IEntity;
import com.th3l4b.srm.base.original.IModel;
import com.th3l4b.srm.base.original.IRelationship;
import com.th3l4b.srm.base.original.RelationshipType;

public class ParserUtils {

	public static void setName(String name, INamed named) {
		try {
			named.setName(name);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void setContext(String context, IModel model) {
		try {
			model.setContext(context);
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

	public static IModel parse(InputStream is) throws Exception {
		SRMGrammarLexer lex = new SRMGrammarLexer(new ANTLRInputStream(is));
		CommonTokenStream tokens = new CommonTokenStream(lex);
		SRMGrammarParser parser = new SRMGrammarParser(tokens);
		parser.document();
		return parser.getModel();
	}

}
