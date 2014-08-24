package com.th3l4b.testbed.screens.shopping.web;

import javax.servlet.http.HttpSession;

import com.th3l4b.apps.shopping.base.Shopping;
import com.th3l4b.apps.shopping.base.ShoppingSample;
import com.th3l4b.apps.shopping.base.codegen.srm.base.IShoppingContext;
import com.th3l4b.common.data.IFactory;
import com.th3l4b.common.propertied.IPropertied;
import com.th3l4b.common.propertied.PropertiedUtils;
import com.th3l4b.screens.base.utils.IScreensClientDescriptor;
import com.th3l4b.screens.base.utils.IScreensConfiguration;

public class ShoppingServletUtils {
	public static final String PREFIX = ShoppingServletUtils.class.getPackage()
			.getName();
	public static final String SCREENS = PREFIX + ".screens";
	public static final String CONTEXT = PREFIX + ".context";

	public static IShoppingContext getShoppingContext(HttpSession session)
			throws Exception {
		IShoppingContext found = (IShoppingContext) session.getAttribute(CONTEXT);
		if (found == null) {
			found = ShoppingSample.getSampleContext();
			session.setAttribute(CONTEXT, found);
		}
		return found;
	}
	
	public static IShoppingContext getShoppingContext(IPropertied propertied)
			throws Exception {
		return PropertiedUtils.defaultAttribute(CONTEXT, propertied,
				new IFactory<IShoppingContext>() {
					@Override
					public IShoppingContext create() throws Exception {
						return ShoppingSample.getSampleContext();
					}
				});
	}

	public static IScreensConfiguration getScreensConfiguration(
			final IScreensClientDescriptor client) throws Exception {
		IScreensConfiguration r = PropertiedUtils.defaultAttribute(SCREENS,
				client, new IFactory<IScreensConfiguration>() {
					@Override
					public IScreensConfiguration create() throws Exception {
						return new Shopping().sample(client, null);
					}
				});
		Shopping.setupShoppipngApplication(getShoppingContext(client), r);
		return r;
	}

}
