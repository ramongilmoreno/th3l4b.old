package com.th3l4b.srm.codegen.java.restruntime;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.th3l4b.common.text.IPrintable;
import com.th3l4b.common.text.ITextConstants;

public class DefaultRESTRequest implements IRESTRequest {

	private HttpServletRequest _httpServletRequest;
	private HttpServletResponse _httpServletResponse;
	private Map<String, String> _stringMap = new LinkedHashMap<String, String>();
	private Map<String, Object> _objectMap = new LinkedHashMap<String, Object>();
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

	public Map<String, String> getStringMap() throws Exception {
		return _stringMap;
	}

	public void setStringMap(Map<String, String> map) throws Exception {
		_stringMap = map;
	}

	public Map<String, Object> getObjectMap() throws Exception {
		return _objectMap;
	}

	public void setObjectMap(Map<String, Object> map) throws Exception {
		_objectMap = map;
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

}
