package com.th3l4b.screens.base;

import javax.servlet.http.HttpServletRequest;

import com.th3l4b.screens.base.utils.IScreensClientDescriptor;

public interface IWebScreensClientDescriptor extends IScreensClientDescriptor {
	HttpServletRequest getRequest () throws Exception;
	void setRequest (HttpServletRequest request) throws Exception;

}
