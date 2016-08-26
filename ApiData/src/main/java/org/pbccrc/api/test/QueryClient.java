package org.pbccrc.api.test;

import javax.ws.rs.core.MediaType;

import org.pbccrc.api.util.StringUtil;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class QueryClient {
	
	public static void main(String[] args) throws Exception{
		
		ClientConfig config = new DefaultClientConfig();
		config.getProperties().put(ClientConfig.PROPERTY_CONNECT_TIMEOUT, 10 * 1000);
		Client client = Client.create(config);

		WebResource resource = client.resource("http://127.0.0.1:8080/ApiData/r/queryApi/get?service=m-sfzxx&idCardNo=420984198704207896");
		
		String result = resource.accept(MediaType.APPLICATION_JSON_TYPE, MediaType.APPLICATION_XML_TYPE).header("appKey", "123").header("userID", "1").get(String.class);
		
		result = StringUtil.decodeUnicode(result);
		
		System.out.println(result);
	}
}
