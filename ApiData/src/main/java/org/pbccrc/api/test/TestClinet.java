package org.pbccrc.api.test;



import java.io.UnsupportedEncodingException;

import javax.ws.rs.core.MediaType;

import org.pbccrc.api.util.StringUtil;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;


public class TestClinet {

	public static void main(String[] args) throws UnsupportedEncodingException {

		ClientConfig config = new DefaultClientConfig();
		config.getProperties().put(ClientConfig.PROPERTY_CONNECT_TIMEOUT, 10 * 1000);
		Client client = Client.create(config);

//		WebResource resource = client.resource("http://127.0.0.1:8080/ApiData/r/test/testMethod?param=vParam&str=vStr");
		
//		WebResource resource = client.resource("http://ccbde.cn:80/api/364/id?id=420984198704207896");
//		String result = resource.accept(MediaType.APPLICATION_JSON_TYPE, MediaType.APPLICATION_XML_TYPE)
//				.header("apikey", "2170baf9a5122481bbd4b5637227e4d2b").get(String.class);
		
//		WebResource resource = client.resource("http://ccbde.cn:80/api/439/mobilenumber?phone=15922041128");
		WebResource resource = client.resource("http://www.ccbde.cn:80/api/283/idInquiry?id=420984198704207896");
		String result = resource.accept(MediaType.APPLICATION_JSON_TYPE).header("apikey", "20800035e452f4719b68df127eefede32").get(String.class);

		result = StringUtil.decodeUnicode(result);
		
//		String result = resource.header("apikey", "28567ecfffbb440d4a5ccd0499993dd21").get(String.class);
		System.out.println(result);

	}
}
