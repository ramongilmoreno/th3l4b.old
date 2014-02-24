package com.th3l4b.srm.codegen.java.restruntime;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.th3l4b.common.text.IPrintable;

public interface IRESTRequest {
	HttpServletRequest getHttpServletRequest() throws Exception;
	void setHttpServletRequest(HttpServletRequest httpServletRequest) throws Exception;
	HttpServletResponse getHttpServletResponse() throws Exception;
	void setHttpServletResponse(HttpServletResponse httpServletResponse) throws Exception;
	Map<String, String> getStringMap()throws Exception;
	void setStringMap(Map<String, String> map)throws Exception;
	Map<String, Object> getObjectMap()throws Exception;
	void setObjectMap(Map<String, Object> map)throws Exception;
	PrintWriter getOut () throws Exception;
	void setOut (PrintWriter out) throws Exception;
	void addPrintable (IPrintable printable) throws Exception;
}
