package com.th3l4b.srm.parser;

import java.io.InputStream;
import java.util.Map;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;

import com.th3l4b.common.named.DefaultNamed;
import com.th3l4b.common.named.INamed;
import com.th3l4b.common.propertied.IPropertied;
import com.th3l4b.srm.base.IField;
import com.th3l4b.srm.base.ModelUtils;
import com.th3l4b.srm.base.original.DefaultRelationship;
import com.th3l4b.srm.base.original.IEntity;
import com.th3l4b.srm.base.original.IModel;
import com.th3l4b.srm.base.original.IRelationship;
import com.th3l4b.srm.base.original.RelationshipType;
import com.th3l4b.srm.parser.grammar.SRMGrammarLexer;
import com.th3l4b.srm.parser.grammar.SRMGrammarParser;

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

	public static void addRelationship(IRelationship relationship, IModel model) {
		try {
			model.relationships().add(relationship);
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

	public static void setDirect(IRelationship relationship) {
		try {
			relationship.setDirect(new DefaultNamed());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void setDirectName(String directName,
			IRelationship relationship) {
		try {
			relationship.getDirect().setName(directName);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void setReverse(IRelationship relationship) {
		try {
			relationship.setReverse(new DefaultNamed());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void setReverseName(String reverseName,
			IRelationship relationship) {
		try {
			relationship.getReverse().setName(reverseName);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void addDirectProperties(Map<String, String> properties,
			IRelationship relationship) {
		try {
			addProperties(properties, relationship.getDirect());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void addReverseProperties(Map<String, String> properties,
			IRelationship relationship) {
		try {
			addProperties(properties, relationship.getReverse());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void setType(RelationshipType type, IRelationship relationship) {
		try {
			relationship.setType(type);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void setEntity(String entity, IRelationship relationship) {
		try {
			relationship.setEntity(entity);
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

	public static void applyRelationshipNames(DefaultRelationship relationship) {
		try {
			// If direct does not exist, create it and use the to name
			{
				INamed d = relationship.getDirect();
				if (d == null) {
					d = new DefaultNamed();
					relationship.setDirect(d);
				}
				if (d.getName() == null) {
					d.setName(relationship.getTo());
				}
			}

			// Same for reverse.
			{
				INamed r = relationship.getReverse();
				if (r == null) {
					r = new DefaultNamed();
					relationship.setReverse(r);
				}
				if (r.getName() == null) {
					r.setName(relationship.getFrom());
				}
			}
			ModelUtils.applyRelationshipName(relationship);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static IModel parse(InputStream is) throws Exception {
		SRMGrammarLexer lex = new SRMGrammarLexer(new ANTLRInputStream(is));
		CommonTokenStream tokens = new CommonTokenStream(lex);
		SRMGrammarParser parser = new SRMGrammarParser(tokens);
		parser.document();
		int errors = parser.getNumberOfSyntaxErrors();
		if (errors > 0) {
			throw new IllegalArgumentException("Syntax errors in input: "
					+ errors + " errors found");
		}

		return parser.getModel();
	}

}
