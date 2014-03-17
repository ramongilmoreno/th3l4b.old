package com.th3l4b.screens.base;

import java.util.AbstractMap;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.th3l4b.screens.base.utils.DefaultScreensClientDescriptor;

public class DefaultWebScreensClientDescriptor extends
		DefaultScreensClientDescriptor implements IWebScreensClientDescriptor {

	private transient HttpServletRequest _request;
	
	public DefaultWebScreensClientDescriptor (HttpServletRequest request) {
		_request = request;
	}

	@Override
	public HttpServletRequest getRequest() throws Exception {
		return _request;
	}

	@Override
	public void setRequest(HttpServletRequest request) throws Exception {
		_request = request;
	}
	
	@Override
	public Map<String, Object> getAttributes() throws Exception {
		return new AbstractMap<String, Object>() {
			@Override
			public Set<java.util.Map.Entry<String, Object>> entrySet() {
				HashSet<java.util.Map.Entry<String, Object>> r = new HashSet<Map.Entry<String, Object>>();
				try {
					HttpSession session = getRequest().getSession(
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
					HttpSession session = getRequest().getSession(
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
