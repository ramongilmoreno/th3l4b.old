package com.th3l4b.srm.codegen.java.web.restruntime;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.th3l4b.common.propertied.IPropertied;
import com.th3l4b.common.text.IPrintable;

public interface IRESTRequest extends IPropertied {
	HttpServletRequest getHttpServletRequest() throws Exception;
	void setHttpServletRequest(HttpServletRequest httpServletRequest) throws Exception;
	HttpServletResponse getHttpServletResponse() throws Exception;
	void setHttpServletResponse(HttpServletResponse httpServletResponse) throws Exception;
	PrintWriter getOut () throws Exception;
	void setOut (PrintWriter out) throws Exception;
	void addPrintable (IPrintable printable) throws Exception;
}
