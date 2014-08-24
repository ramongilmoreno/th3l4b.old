package com.th3l4b.testbed.screens.shopping;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;

import com.th3l4b.common.text.ITextConstants;
import com.th3l4b.srm.codegen.java.web.runtime.AbstractJSONEntitiesServlet;

public class ShoppingSyncClient {

	public static void main(String[] args) throws Exception {
		int i = 0;
		String server = args[i];

		HttpClient client = new HttpClient();
		PostMethod post = new PostMethod(server);
		String body = "[{ \"_Type\": \"Item\", \"_Id\": \"synced\", \"Name\": \"Synced\" }]";

		post.setRequestEntity(new StringRequestEntity(body,
				AbstractJSONEntitiesServlet.JSON_CONTENT_TYPE,
				ITextConstants.UTF_8));

		int result = client.executeMethod(post);
		if (result == 200) {
			System.out.println(post.getResponseBodyAsString());
		} else {
			System.out.println("HTTP Error: " + result);
		}
	}
}
