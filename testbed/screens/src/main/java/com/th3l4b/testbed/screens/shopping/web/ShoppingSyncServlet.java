package com.th3l4b.testbed.screens.shopping.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.th3l4b.apps.shopping.base.codegen.srm.base.DefaultShoppingModelUtils;
import com.th3l4b.apps.shopping.base.codegen.srm.base.IShoppingContext;
import com.th3l4b.apps.shopping.base.codegen.srm.base.IShoppingFinder;
import com.th3l4b.apps.shopping.base.codegen.srm.sync.ShoppingDiffContext;
import com.th3l4b.apps.shopping.base.codegen.srm.tomap.ShoppingToMapParserContext;
import com.th3l4b.common.data.Pair;
import com.th3l4b.srm.codegen.java.basic.runtime.tomap.DefaultToMapIdentifierParser;
import com.th3l4b.srm.codegen.java.basic.runtime.tomap.DefaultToMapStatusParser;
import com.th3l4b.srm.codegen.java.sync.runtime.AbstractSyncTool;
import com.th3l4b.srm.codegen.java.sync.runtime.IDiffContext;
import com.th3l4b.srm.codegen.java.web.runtime.AbstractJSONEntitiesServlet;
import com.th3l4b.srm.runtime.IRuntimeEntity;
import com.th3l4b.srm.runtime.IToMapEntityParserContext;
import com.th3l4b.srm.runtime.SRMContextUtils;
import com.th3l4b.types.runtime.basicset.JavaRuntimeTypesBasicSet;

@SuppressWarnings("serial")
public class ShoppingSyncServlet extends
		AbstractJSONEntitiesServlet<IShoppingContext, IShoppingFinder> {

	protected IShoppingContext getSRM(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return ShoppingServletUtils
				.getShoppingContext(request.getSession(true));
	}

	protected ShoppingToMapParserContext getToMap() throws Exception {
		return new ShoppingToMapParserContext(
				new DefaultToMapIdentifierParser(),
				new DefaultToMapStatusParser(), new JavaRuntimeTypesBasicSet(),
				new DefaultShoppingModelUtils());
	}

	protected IDiffContext getDiffContext() throws Exception {
		return new ShoppingDiffContext();
	}

	@Override
	protected Pair<IShoppingContext, IToMapEntityParserContext> getContext(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return new Pair<IShoppingContext, IToMapEntityParserContext>(getSRM(
				request, response), getToMap());
	}

	@Override
	protected Iterable<IRuntimeEntity<?>> service(
			Iterable<IRuntimeEntity<?>> parsed, final IShoppingContext srm,
			IToMapEntityParserContext b) throws Exception {
		AbstractSyncTool<IShoppingFinder, IShoppingContext> sync = new AbstractSyncTool<IShoppingFinder, IShoppingContext>() {
			@Override
			protected IShoppingContext getContext() throws Exception {
				return srm;
			}

			@Override
			protected IDiffContext getDiffContext() throws Exception {
				return ShoppingSyncServlet.this.getDiffContext();
			}
		};

		srm.update(sync.sync(SRMContextUtils.map(parsed)));

		return srm.getFinder().backup();
	}
}
