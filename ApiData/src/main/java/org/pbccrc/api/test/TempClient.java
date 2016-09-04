package org.pbccrc.api.test;

import javax.ws.rs.core.MediaType;

import org.pbccrc.api.util.StringUtil;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class TempClient {
	
	public static void main(String[] args) throws Exception{
//		test1();
//		test2();
		test3();
	}
	
	public static void test3() {

		ClientConfig config = new DefaultClientConfig();
		config.getProperties().put(ClientConfig.PROPERTY_CONNECT_TIMEOUT, 10 * 1000);
		Client client = Client.create(config);

		StringBuffer url = new StringBuffer();
		url.append("http://127.0.0.1:8080/ApiData/r/queryApi/get");
//		url.append("http://120.25.230.224:8080/ApiData/r/queryApi/get");
//		url.append("?service=s-queryScore");
		url.append("?service=m-sfzxx");
//		url.append("&NAME=王梓");
//		url.append("&IDCARDNUM=120103198603292638");
//		url.append("&ACCOUNTNO=6214830223501445");
		url.append("&name=王梓");
		url.append("&idCardNo=120103198603292638");
		
		WebResource resource = client.resource(url.toString());
		
		String result = resource.accept(MediaType.APPLICATION_JSON_TYPE, MediaType.APPLICATION_XML_TYPE).header("apiKey", "X37EF162524F265").header("userID", "1").get(String.class);
		
		result = StringUtil.decodeUnicode(result);
		
		System.out.println(result);
	}
	
	public static void test2() {
		
		ClientConfig config = new DefaultClientConfig();
		config.getProperties().put(ClientConfig.PROPERTY_CONNECT_TIMEOUT, 10 * 1000);
		Client client = Client.create(config);

//		StringBuffer url = new StringBuffer();
//		url.append("http://127.0.0.1:8080/ApiData/r/queryApi/get");
//		url.append("?service=s-qlexecute");
//		url.append("&exeName=王健林");
		
		StringBuffer url = new StringBuffer();
		url.append("http://127.0.0.1:8080/ApiData/r/queryApi/get");
		url.append("?service=s-lhxwxx");
		url.append("&companyId=1015");
//		url.append("&personName=王梓");
//		url.append("&idCardNo=120103198603292638");
//		url.append("&sortName=总监理工程师");
//		url.append("&accountNo=6214830223501445");
//		url.append("&bankPreMobile=15922041128");
		
		
//		url.append("http://127.0.0.1:8080/ApiData/r/queryApi/get");
//		url.append("?service=m-sfzxx");
//		url.append("&name=王梓");
//		url.append("&idCardNo=120103198603292638");
		WebResource resource = client.resource(url.toString());
		
//		WebResource resource = client.resource("http://192.168.62.47:8080/ApiData/r/user/login?userName=wangzi&password=111111");
		
		String result = resource.accept(MediaType.APPLICATION_JSON_TYPE, MediaType.APPLICATION_XML_TYPE).header("apiKey", "X37EF162524F265").header("userID", "1").get(String.class);
		
		result = StringUtil.decodeUnicode(result);
		
		System.out.println(result);
	}
	
	public static void test1() {
		ClientConfig config = new DefaultClientConfig();
		config.getProperties().put(ClientConfig.PROPERTY_CONNECT_TIMEOUT, 10 * 1000);
		Client client = Client.create(config);
		
		StringBuffer url = new StringBuffer();
		url.append("http://127.0.0.1:8080/ApiData/r/queryApi/get");
//		url.append("http://www.qilingyz.cn:8080/ApiData/r/queryApi/get");
		url.append("?service=s-qlproexe");
		String name = "王健林";
		String idCardNo = "120103198603292638";
		
		url.append("&exeName=" + name);
		
		String str = url.toString();
		System.out.println(str);
		
		WebResource resource = client.resource(str);
		String result = resource.accept(MediaType.APPLICATION_JSON_TYPE, MediaType.APPLICATION_XML_TYPE).header("apiKey", "123321").header("userID", "1").get(String.class);
		System.out.println(result);
	}
}
