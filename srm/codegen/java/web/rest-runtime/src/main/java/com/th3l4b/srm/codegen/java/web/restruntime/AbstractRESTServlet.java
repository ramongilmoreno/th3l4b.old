package com.th3l4b.srm.codegen.java.web.restruntime;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.th3l4b.common.text.IPrintable;
import com.th3l4b.common.text.ITextConstants;
import com.th3l4b.common.text.TextUtils;
import com.th3l4b.srm.codegen.java.basicruntime.rest.IRESTFinder;
import com.th3l4b.srm.runtime.IFinder;
import com.th3l4b.srm.runtime.IRuntimeEntity;
import com.th3l4b.srm.runtime.ISRMContext;
import com.th3l4b.srm.runtime.IToMapEntityParser;
import com.th3l4b.srm.runtime.IToMapEntityParserContext;

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

	private static final IPrintable printable(Map<String, String> object)
			throws Exception {
		final StringBuilder sb = new StringBuilder(200);
		sb.append("{ ");
		for (Map.Entry<String, String> attribute : object.entrySet()) {
			if (sb.length() > 2) {
				sb.append(", ");
			}
			sb.append('\"');
			sb.append(TextUtils.escapeJavaString(attribute.getKey()));
			sb.append("\": \"");
			sb.append(TextUtils.escapeJavaString(attribute.getValue()));
			sb.append('\"');
		}
		sb.append(" }");
		// Return a printable that does not add a line feed at the end.
		return new IPrintable() {
			@Override
			public void print(PrintWriter out) {
				out.print(sb.toString());
			}
		};
	}

	private <T extends IRuntimeEntity<T>> IPrintable printable(Object one,
			Class<T> clazz, Map<String, String> map, IRESTRequest request)
			throws Exception {
		fillMapForEntity(one, clazz, map);
		IPrintable printable = printable(map);
		map.clear();
		return printable;
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

			// Flush result.
			request.getOut().flush();
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}
}
