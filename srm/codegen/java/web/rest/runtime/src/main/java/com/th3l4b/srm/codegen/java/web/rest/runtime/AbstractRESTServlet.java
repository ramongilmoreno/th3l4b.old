package com.th3l4b.srm.codegen.java.web.rest.runtime;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.th3l4b.common.data.Pair;
import com.th3l4b.common.text.ITextConstants;
import com.th3l4b.srm.codegen.java.basicruntime.rest.IRESTFinder;
import com.th3l4b.srm.runtime.IDatabaseConstants;
import com.th3l4b.srm.runtime.IFinder;
import com.th3l4b.srm.runtime.IIdentifier;
import com.th3l4b.srm.runtime.IRuntimeEntity;
import com.th3l4b.srm.runtime.ISRMContext;
import com.th3l4b.srm.runtime.IToMapEntityParser;
import com.th3l4b.srm.runtime.IToMapEntityParserContext;
import com.th3l4b.srm.runtime.SRMContextUtils;

@SuppressWarnings("serial")
public abstract class AbstractRESTServlet<CONTEXT extends ISRMContext<FINDER>, FINDER extends IFinder>
		extends HttpServlet {

	private IRESTFinder<FINDER> _finder;
	private IToMapEntityParserContext _tomap;

	protected abstract CONTEXT getContext(IRESTRequest request)
			throws Exception;

	protected abstract IRESTFinder<FINDER> createRESTFinder() throws Exception;

	protected IRESTFinder<FINDER> getRESTFinder() throws Exception {
		if (_finder == null) {
			_finder = createRESTFinder();
		}
		return _finder;
	}

	protected abstract IToMapEntityParserContext createToMapEntityParserContext()
			throws Exception;

	protected IToMapEntityParserContext getToMapEntityParserContext()
			throws Exception {
		if (_tomap == null) {
			_tomap = createToMapEntityParserContext();
		}
		return _tomap;
	}

	protected String[] split(IRESTRequest request) throws Exception {
		// Returns up to 3 strings with this format:
		// http://stackoverflow.com/questions/4278083/how-to-get-request-uri-without-context-path
		String pathInfo = request.getHttpServletRequest().getPathInfo();
		if (pathInfo == null) {
			return new String[0];
		} else {
			String[] split = pathInfo.split("/");
			String[] r = new String[split.length - 1];
			System.arraycopy(split, 1, r, 0, split.length - 1);
			return r;
		}
	}

	protected Pair<IRuntimeEntity<?>, Iterable<IRuntimeEntity<?>>> parseEntities(
			IRESTRequest request, boolean acceptOne, boolean acceptMany)
			throws Exception {
		Pair<IRuntimeEntity<?>, Iterable<IRuntimeEntity<?>>> r = new Pair<IRuntimeEntity<?>, Iterable<IRuntimeEntity<?>>>();
		HashSet<IRuntimeEntity<?>> many = new HashSet<IRuntimeEntity<?>>();
		if (acceptMany) {
			r.setB(many);
		}

		// http://www.studytrails.com/java/json/java-jackson-json-streaming.jsp
		JsonFactory factory = new JsonFactory();
		JsonParser parser = factory.createParser(request
				.getHttpServletRequest().getReader());
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
						many.add(createEntity(request, parser, map));
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
				r.setA(createEntity(request, parser, map));
				parser.close();
			} else {
				throw new IllegalArgumentException("Unexpected JSON token: "
						+ token);
			}
		}
		return r;
	}

	public IRuntimeEntity<?> createEntity(IRESTRequest request,
			JsonParser parser, Map<String, String> map) throws Exception {
		map.clear();
		parseFields(parser, map);
		String[] context = split(request);
		if (context.length > 0) {
			map.put(IDatabaseConstants.TYPE, context[0]);
		}
		if (context.length > 1) {
			map.put(IDatabaseConstants.ID, context[1]);
		}

		// Find parser and create entity.
		Class<? extends IRuntimeEntity<?>> clazz = getContext(request)
				.getUtils().classFromName(map.get(IDatabaseConstants.TYPE));
		IRuntimeEntity<?> entity = getToMapEntityParserContext().getParser(
				clazz).parse(null, map);
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

	protected void serialize(IRuntimeEntity<?> one, IRESTRequest request)
			throws Exception {
		if (one != null) {
			JsonGenerator generator = setupJSONResponse(request);
			Map<String, String> map = new LinkedHashMap<String, String>();
			generator.writeStartObject();
			fillMapForEntity(one, one.clazz(), map);
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

	public JsonGenerator setupJSONResponse(IRESTRequest request)
			throws Exception, IOException {
		// http://www.studytrails.com/java/json/java-jackson-json-streaming.jsp
		JsonFactory factory = new JsonFactory();
		HttpServletResponse response = request.getHttpServletResponse();
		response.setCharacterEncoding(ITextConstants.UTF_8);
		// http://stackoverflow.com/questions/477816/what-is-the-correct-json-content-type
		response.setContentType("application/json");
		JsonGenerator generator = factory.createGenerator(response.getWriter());
		return generator;
	}

	@SuppressWarnings("unchecked")
	public <T extends IRuntimeEntity<T>> void fillMapForEntity(Object one,
			Class<T> clazz, Map<String, String> map) throws Exception {
		IToMapEntityParser<T> parser = getToMapEntityParserContext().getParser(
				clazz);
		map.clear();
		parser.set((T) one, null, map);
	}

	protected void serialize(Iterable<? extends IRuntimeEntity<?>> many,
			IRESTRequest request) throws Exception {
		JsonGenerator generator = setupJSONResponse(request);
		generator.writeStartArray();
		Map<String, String> map = new LinkedHashMap<String, String>();
		for (IRuntimeEntity<?> entity : many) {
			generator.writeStartObject();
			fillMapForEntity(entity, entity.clazz(), map);
			for (Map.Entry<String, String> e : map.entrySet()) {
				generator.writeFieldName(e.getKey());
				generator.writeString(e.getValue());
			}
			generator.writeEndObject();
		}
		generator.writeEndArray();
		generator.close();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			IRESTRequest request = new DefaultRESTRequest(req, resp);
			CONTEXT context = getContext(request);
			String[] coordinates = split(request);
			IRuntimeEntity<?> one = null;
			boolean isOne = false;
			Iterable<? extends IRuntimeEntity<?>> many = null;
			boolean isMany = false;
			switch (coordinates.length) {
			case 1:
				many = getRESTFinder().all(coordinates[0], context.getFinder());
				isMany = true;
				break;
			case 2:
				one = getRESTFinder().get(coordinates[0], coordinates[1],
						context.getFinder());
				isOne = true;
				break;
			case 3:
				many = getRESTFinder().get(coordinates[0], coordinates[1],
						coordinates[2], context.getFinder());
				isMany = true;
				break;
			default:
				throw new IllegalArgumentException(
						"Unsupported number of coordinates in REST path: "
								+ coordinates.length);
			}

			// Return
			if (isOne) {
				serialize(one, request);
			} else if (isMany) {
				serialize(many, request);
			} else {
				throw new IllegalStateException(
						"Could not decide if result is one or many");
			}
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			IRESTRequest request = new DefaultRESTRequest(req, resp);
			CONTEXT context = getContext(request);
			String[] coordinates = split(request);
			boolean isMany = false;
			switch (coordinates.length) {
			case 0:
			case 1:
				isMany = true;
				break;
			case 2:
				isMany = false;
				break;
			default:
				throw new IllegalArgumentException(
						"Unsupported number of coordinates in REST path: "
								+ coordinates.length);
			}

			Pair<IRuntimeEntity<?>, Iterable<IRuntimeEntity<?>>> r = parseEntities(
					request, true, isMany);

			if (isMany) {
				// Check if one was read
				Map<IIdentifier, IRuntimeEntity<?>> map = null;
				if (r.getA() != null) {
					map = SRMContextUtils.map(r.getA());
				} else {
					map = SRMContextUtils.map(r.getB());
				}
				context.update(map);
			} else {
				context.update(SRMContextUtils.map(r.getA()));
			}

			// If everthing was OK up to this moment, return true
			JsonGenerator generator = setupJSONResponse(request);
			generator.writeBoolean(true);
			generator.close();
		} catch (Exception e) {
			throw new ServletException(e);
		}
		// parseEntities
	}
}
