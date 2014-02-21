package com.th3l4b.srm.codegen.java.restruntime;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.th3l4b.srm.codegen.java.basicruntime.tomap.IToMapEntityParser;
import com.th3l4b.srm.codegen.java.basicruntime.tomap.IToMapEntityParserContext;
import com.th3l4b.srm.codegen.java.restruntime.access.IRESTFinder;
import com.th3l4b.srm.runtime.IFinder;
import com.th3l4b.srm.runtime.IRuntimeEntity;
import com.th3l4b.srm.runtime.ISRMContext;

@SuppressWarnings("serial")
public abstract class AbstractRESTServlet<CONTEXT extends ISRMContext<FINDER>, FINDER extends IFinder>
		extends HttpServlet {

	private IRESTFinder _finder;
	private IToMapEntityParserContext _tomap;

	protected abstract CONTEXT getContext(IRESTRequest request)
			throws Exception;

	protected abstract IRESTFinder createRESTFinder() throws Exception;

	protected IRESTFinder getRESTFinder() throws Exception {
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
		// <Type>/<Id>/<Relationship>
		String IMPLEMENT_PARSING_THE_HTTP_REQUEST;
		throw new UnsupportedOperationException("Not implemented yet");
	}

	protected void serialize(IRuntimeEntity<?> one, IRESTRequest request)
			throws Exception {
		if (one != null) {
			Map<String, String> map = request.getStringMap();
			map.clear();
			serializeClass(one, one.clazz(), map, request);
			map.clear();
		} else {
			throw new IllegalArgumentException(
					"Cannot serialized an unique object that is null");
		}
	}

	@SuppressWarnings("unchecked")
	private <T extends IRuntimeEntity<T>> void serializeClass(Object one,
			Class<T> clazz, Map<String, String> map, IRESTRequest request)
			throws Exception {
		IToMapEntityParser<T> parser = getToMapEntityParserContext().getParser(
				clazz);
		parser.set((T) one, null, map);
		String IMPLEMENT_SERIALIZING_ONE;
		throw new UnsupportedOperationException("Not implemented yet");
	}

	protected void serialize(Iterable<IRuntimeEntity<?>> many,
			IRESTRequest request) throws Exception {
		String IMPLEMENT_SERIALIZING_MANY_EFFICIENTLY;
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			IRESTRequest request = new DefaultRESTRequest(req, resp);

			String[] coordinates = split(request);
			IRuntimeEntity<?> one = null;
			boolean isOne = false;
			Iterable<IRuntimeEntity<?>> many = null;
			boolean isMany = false;
			switch (coordinates.length) {
			case 1:
				many = getRESTFinder().get(coordinates[0]);
				isMany = true;
				break;
			case 2:
				one = getRESTFinder().get(coordinates[0], coordinates[1]);
				isOne = true;
				break;
			case 3:
				many = getRESTFinder().get(coordinates[0], coordinates[1],
						coordinates[2]);
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
}
