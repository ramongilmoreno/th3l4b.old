package com.th3l4b.srm.codegen.java.web.runtime;

import java.io.Reader;
import java.io.Writer;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.th3l4b.common.data.Pair;
import com.th3l4b.srm.runtime.IDatabaseConstants;
import com.th3l4b.srm.runtime.IRuntimeEntity;
import com.th3l4b.srm.runtime.ISRMContext;
import com.th3l4b.srm.runtime.IToMapEntityParser;
import com.th3l4b.srm.runtime.IToMapEntityParserContext;

public class JSONEntitiesParser {

	public Pair<IRuntimeEntity<?>, Iterable<IRuntimeEntity<?>>> parse(
			boolean acceptOne, boolean acceptMany, Reader reader,
			ISRMContext<?> context, IToMapEntityParserContext toMap)
			throws Exception {
		return parse(acceptOne, acceptMany, new JSONEntitiesParserContext(
				reader, context, toMap));
	}

	public Pair<IRuntimeEntity<?>, Iterable<IRuntimeEntity<?>>> parse(
			boolean acceptOne, boolean acceptMany,
			JSONEntitiesParserContext context) throws Exception {
		Pair<IRuntimeEntity<?>, Iterable<IRuntimeEntity<?>>> r = new Pair<IRuntimeEntity<?>, Iterable<IRuntimeEntity<?>>>();
		HashSet<IRuntimeEntity<?>> many = new HashSet<IRuntimeEntity<?>>();
		if (acceptMany) {
			r.setB(many);
		}

		// http://www.studytrails.com/java/json/java-jackson-json-streaming.jsp
		JsonFactory factory = new JsonFactory();
		JsonParser parser = factory.createParser(context.getReader());
		Map<String, String> map = new LinkedHashMap<String, String>();

		while (!parser.isClosed()) {
			JsonToken token = parser.nextToken();
			if (token == null) {
				// EOF
				break;
			} else if (JsonToken.START_ARRAY.equals(token)) {
				if (!acceptMany) {
					throw new IllegalArgumentException(
							"JSON Array input was not expected");
				}
				while ((token = parser.nextToken()) != null) {
					if (JsonToken.START_OBJECT.equals(token)) {
						many.add(createEntity(parser, map, context));
					} else if (JsonToken.END_ARRAY.equals(token)) {
						parser.close();
						break;
					} else {
						throw new IllegalArgumentException(
								"JSON Array end was expected");
					}
				}
			} else if (JsonToken.START_OBJECT.equals(token)) {
				if (!acceptOne) {
					throw new IllegalArgumentException(
							"JSON Object input was not expected");
				}
				r.setA(createEntity(parser, map, context));
				parser.close();
			} else {
				throw new IllegalArgumentException("Unexpected JSON token: "
						+ token);
			}
		}
		return r;
	}

	private IRuntimeEntity<?> createEntity(JsonParser parser,
			Map<String, String> map, JSONEntitiesParserContext context)
			throws Exception {
		map.clear();
		parseFields(parser, map);

		// Find parser and create entity.
		Class<? extends IRuntimeEntity<?>> clazz = context.getSRM().getUtils()
				.classFromName(map.get(IDatabaseConstants.TYPE));
		IRuntimeEntity<?> entity = context.getToMap().getParser(clazz)
				.parse(null, map);
		return entity;
	}

	private void parseFields(JsonParser parser, Map<String, String> map)
			throws Exception {
		while (true) {
			JsonToken field = parser.nextToken();
			String f = parser.getText();
			if ((field == null) || JsonToken.END_OBJECT.equals(field)) {
				break;
			} else if (JsonToken.FIELD_NAME.equals(field)) {
				// Load value
				JsonToken value = parser.nextToken();
				if ((value == null) || !JsonToken.VALUE_STRING.equals(value)) {
					// Throw an exception if value not found
					throw new IllegalStateException(
							"Could not find string field value. Instead found token: "
									+ field);
				} else {
					map.put(f, parser.getText());
				}

			} else {
				throw new IllegalStateException(
						"Could not find field name. Instead found token: "
								+ field);
			}
		}

	}

	public void serialize(IRuntimeEntity<?> one, Writer writer,
			ISRMContext<?> context, IToMapEntityParserContext toMap)
			throws Exception {
		serialize(one, new JSONEntitiesParserContext(writer, context, toMap));
	}

	public void serialize(IRuntimeEntity<?> one,
			JSONEntitiesParserContext context) throws Exception {
		if (one != null) {
			// http://www.studytrails.com/java/json/java-jackson-json-streaming.jsp
			JsonFactory factory = new JsonFactory();
			JsonGenerator generator = factory.createGenerator(context
					.getWriter());
			Map<String, String> map = new LinkedHashMap<String, String>();
			generator.writeStartObject();
			fillMapForEntity(one, one.clazz(), map, context);
			for (Map.Entry<String, String> e : map.entrySet()) {
				generator.writeFieldName(e.getKey());
				generator.writeString(e.getValue());
			}
			generator.writeEndObject();
			generator.close();
		} else {
			throw new IllegalArgumentException(
					"Cannot serialized an unique object that is null");
		}
	}

	@SuppressWarnings("unchecked")
	private <T extends IRuntimeEntity<T>> void fillMapForEntity(Object one,
			Class<T> clazz, Map<String, String> map,
			JSONEntitiesParserContext context) throws Exception {
		IToMapEntityParser<T> parser = context.getToMap().getParser(clazz);
		map.clear();
		parser.set((T) one, null, map);
	}

	public void serialize(Iterable<? extends IRuntimeEntity<?>> many,
			Writer writer, ISRMContext<?> context,
			IToMapEntityParserContext toMap) throws Exception {
		serialize(many, new JSONEntitiesParserContext(writer, context, toMap));
	}

	public void serialize(Iterable<? extends IRuntimeEntity<?>> many,
			JSONEntitiesParserContext context) throws Exception {
		// http://www.studytrails.com/java/json/java-jackson-json-streaming.jsp
		JsonFactory factory = new JsonFactory();
		JsonGenerator generator = factory.createGenerator(context.getWriter());
		generator.writeStartArray();
		Map<String, String> map = new LinkedHashMap<String, String>();
		for (IRuntimeEntity<?> entity : many) {
			generator.writeStartObject();
			fillMapForEntity(entity, entity.clazz(), map, context);
			for (Map.Entry<String, String> e : map.entrySet()) {
				generator.writeFieldName(e.getKey());
				generator.writeString(e.getValue());
			}
			generator.writeEndObject();
		}
		generator.writeEndArray();
		generator.close();
	}

}
