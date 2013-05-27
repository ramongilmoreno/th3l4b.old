package com.th3l4b.screens.base;

import javax.servlet.http.HttpServletRequest;

import com.th3l4b.screens.base.interaction.IInteractionContext;

public interface IWebInteractionContext extends IInteractionContext {
	HttpServletRequest getRequest () throws Exception;
	void setRequest (HttpServletRequest request) throws Exception;

}
