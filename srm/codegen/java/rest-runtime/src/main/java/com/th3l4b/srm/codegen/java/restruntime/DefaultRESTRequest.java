package com.th3l4b.srm.codegen.java.restruntime;

import java.io.PrintWriter;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.th3l4b.common.propertied.IPropertied;
import com.th3l4b.common.text.IPrintable;
import com.th3l4b.common.text.ITextConstants;

/**
 * Maps {@link IPropertied#getAttributes()} to {@link HttpSession} attributes.
 */
public class DefaultRESTRequest implements IRESTRequest {

	private Map<String, String> _properties = new LinkedHashMap<String, String>();
	private HttpServletRequest _httpServletRequest;
	private HttpServletResponse _httpServletResponse;
	private PrintWriter _out;
	private List<IPrintable> _printables = new ArrayList<IPrintable>();

	public DefaultRESTRequest(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws Exception {
		_httpServletRequest = httpServletRequest;
		_httpServletResponse = httpServletResponse;
		_httpServletResponse.setCharacterEncoding(ITextConstants.UTF_8);
		_out = new PrintWriter(_httpServletResponse.getWriter());
	}

	public HttpServletRequest getHttpServletRequest() throws Exception {
		return _httpServletRequest;
	}

	public void setHttpServletRequest(HttpServletRequest httpServletRequest)
			throws Exception {
		_httpServletRequest = httpServletRequest;
	}

	public HttpServletResponse getHttpServletResponse() throws Exception {
		return _httpServletResponse;
	}

	public void setHttpServletResponse(HttpServletResponse httpServletResponse)
			throws Exception {
		_httpServletResponse = httpServletResponse;
	}

	public PrintWriter getOut() {
		return _out;
	}

	public void setOut(PrintWriter out) throws Exception {
		_out = out;
	}

	public void addPrintable(IPrintable printable) throws Exception {
		_printables.add(printable);
	}

	@Override
	public Map<String, String> getProperties() throws Exception {
		return _properties;
	}

	@Override
	public Map<String, Object> getAttributes() throws Exception {
		return new AbstractMap<String, Object>() {
			@Override
			public Set<java.util.Map.Entry<String, Object>> entrySet() {
				HashSet<java.util.Map.Entry<String, Object>> r = new HashSet<Map.Entry<String, Object>>();
				try {
					HttpSession session = getHttpServletRequest().getSession(
							true);
					Enumeration<?> attributes = session.getAttributeNames();
					while (attributes.hasMoreElements()) {
						String a = (String) attributes.nextElement();
						Object v = session.getAttribute(a);
						r.add(new AbstractMap.SimpleEntry<String, Object>(a, v));
					}
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
				return r;
			}

			@Override
			public Object put(String key, Object value) {
				try {
					HttpSession session = getHttpServletRequest().getSession(
							true);
					Object previous = session.getAttribute(key);
					session.setAttribute(key, value);
					return previous;
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}

		};
	}
}
