package com.th3l4b.srm.codegen.java.restruntime;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IRESTRequest {
	public HttpServletRequest getHttpServletRequest() throws Exception;
	public void setHttpServletRequest(HttpServletRequest httpServletRequest) throws Exception;
	public HttpServletResponse getHttpServletResponse() throws Exception;
	public void setHttpServletResponse(HttpServletResponse httpServletResponse) throws Exception;
	public Map<String, String> getStringMap()throws Exception;
	public void setStringMap(Map<String, String> map)throws Exception;
	public Map<String, Object> getObjectMap()throws Exception;
	public void setObjectMap(Map<String, Object> map)throws Exception;
}
