package com.th3l4b.screens.base;

import javax.servlet.http.HttpServletRequest;

import com.th3l4b.screens.base.utils.DefaultScreensClientDescriptor;

public class DefaultWebScreensClientDescriptor extends
		DefaultScreensClientDescriptor implements IWebScreensClientDescriptor {

	private transient HttpServletRequest _request;

	@Override
	public HttpServletRequest getRequest() throws Exception {
		return _request;
	}

	@Override
	public void setRequest(HttpServletRequest request) throws Exception {
		_request = request;
	}

}
