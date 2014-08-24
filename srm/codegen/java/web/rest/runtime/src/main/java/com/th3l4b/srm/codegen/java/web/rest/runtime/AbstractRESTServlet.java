package com.th3l4b.srm.codegen.java.web.rest.runtime;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.th3l4b.common.data.Pair;
import com.th3l4b.common.text.ITextConstants;
import com.th3l4b.srm.codegen.java.web.runtime.AbstractJSONEntitiesServlet;
import com.th3l4b.srm.codegen.java.web.runtime.JSONEntitiesParser;
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

	protected abstract CONTEXT getContext(HttpServletRequest request)
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

	protected String[] split(HttpServletRequest request) throws Exception {
		// Returns up to 3 strings with this format:
		// http://stackoverflow.com/questions/4278083/how-to-get-request-uri-without-context-path
		String pathInfo = request.getPathInfo();
		if (pathInfo == null) {
			return new String[0];
		} else {
			String[] split = pathInfo.split("/");
			String[] r = new String[split.length - 1];
			System.arraycopy(split, 1, r, 0, split.length - 1);
			return r;
		}
	}

	public void setupJSONResponse(HttpServletResponse response)
			throws Exception, IOException {
		response.setCharacterEncoding(ITextConstants.UTF_8);
		// http://stackoverflow.com/questions/477816/what-is-the-correct-json-content-type
		response.setContentType(AbstractJSONEntitiesServlet.JSON_CONTENT_TYPE);
	}

	@SuppressWarnings("unchecked")
	public <T extends IRuntimeEntity<T>> void fillMapForEntity(Object one,
			Class<T> clazz, Map<String, String> map) throws Exception {
		IToMapEntityParser<T> parser = getToMapEntityParserContext().getParser(
				clazz);
		map.clear();
		parser.set((T) one, null, map);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			CONTEXT context = getContext(req);
			String[] coordinates = split(req);
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
			setupJSONResponse(resp);
			JSONEntitiesParser json = new JSONEntitiesParser();
			IToMapEntityParserContext toMap = getToMapEntityParserContext();

			if (isOne) {
				json.serialize(one, resp.getWriter(), context, toMap);
			} else if (isMany) {
				json.serialize(many, resp.getWriter(), context, toMap);
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
			CONTEXT context = getContext(req);
			String[] coordinates = split(req);
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

			Pair<IRuntimeEntity<?>, Iterable<IRuntimeEntity<?>>> r = new JSONEntitiesParser()
					.parse(true, isMany, req.getReader(), context,
							getToMapEntityParserContext());

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
			setupJSONResponse(resp);

			// http://www.studytrails.com/java/json/java-jackson-json-streaming.jsp
			JsonFactory factory = new JsonFactory();
			JsonGenerator generator = factory.createGenerator(resp.getWriter());
			generator.writeBoolean(true);
			generator.close();
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}
}
