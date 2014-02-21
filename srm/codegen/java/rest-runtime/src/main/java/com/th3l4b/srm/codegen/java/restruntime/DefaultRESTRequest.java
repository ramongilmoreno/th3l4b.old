package com.th3l4b.srm.codegen.java.restruntime;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DefaultRESTRequest implements IRESTRequest {

	private HttpServletRequest _httpServletRequest;
	private HttpServletResponse _httpServletResponse;
	private Map<String, String> _stringMap = new LinkedHashMap<String, String>();
	private Map<String, Object> _objectMap = new LinkedHashMap<String, Object>();

	public DefaultRESTRequest(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws Exception {
		_httpServletRequest = httpServletRequest;
		_httpServletResponse = httpServletResponse;
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

	public Map<String, String> getStringMap() throws Exception {
		return _stringMap;
	}

	public void setStringMap(Map<String, String> map) throws Exception {
		_stringMap = map;
	}

	public Map<String, Object> getObjectMap() throws Exception {
		return _objectMap ;
	}

	public void setObjectMap(Map<String, Object> map) throws Exception {
		_objectMap = map;
	}
}
