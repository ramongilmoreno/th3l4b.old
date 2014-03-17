package com.th3l4b.testbed.screens.web;

import com.th3l4b.apps.shopping.base.codegen.srm.base.DefaultShoppingModelUtils;
import com.th3l4b.apps.shopping.base.codegen.srm.base.IShoppingContext;
import com.th3l4b.apps.shopping.base.codegen.srm.base.IShoppingFinder;
import com.th3l4b.apps.shopping.base.codegen.srm.rest.ShoppingRESTFinder;
import com.th3l4b.apps.shopping.base.codegen.srm.tomap.ShoppingToMapParserContext;
import com.th3l4b.srm.codegen.java.basicruntime.rest.IRESTFinder;
import com.th3l4b.srm.codegen.java.basicruntime.tomap.DefaultToMapIdentifierParser;
import com.th3l4b.srm.codegen.java.basicruntime.tomap.DefaultToMapStatusParser;
import com.th3l4b.srm.codegen.java.restruntime.AbstractRESTServlet;
import com.th3l4b.srm.codegen.java.restruntime.IRESTRequest;
import com.th3l4b.srm.runtime.IToMapEntityParserContext;
import com.th3l4b.types.runtime.basicset.JavaRuntimeTypesBasicSet;

@SuppressWarnings("serial")
public class ShoppingRESTServlet extends
		AbstractRESTServlet<IShoppingContext, IShoppingFinder> {

	@Override
	protected IShoppingContext getContext(IRESTRequest request)
			throws Exception {
		return ShoppingServletUtils.getShoppingContext(request);
	}

	@Override
	protected IRESTFinder<IShoppingFinder> createRESTFinder() throws Exception {
		return new ShoppingRESTFinder();
	}

	@Override
	protected IToMapEntityParserContext createToMapEntityParserContext()
			throws Exception {
		return new ShoppingToMapParserContext(
				new DefaultToMapIdentifierParser(),
				new DefaultToMapStatusParser(), new JavaRuntimeTypesBasicSet(),
				new DefaultShoppingModelUtils());
	}

}