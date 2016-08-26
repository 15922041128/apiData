package org.pbccrc.api.test;

import java.io.UnsupportedEncodingException;

import javax.ws.rs.core.MediaType;

import org.pbccrc.api.util.StringUtil;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;


public class ZxsjClient {

	public static void main(String[] args) throws UnsupportedEncodingException {

		ClientConfig config = new DefaultClientConfig();
		config.getProperties().put(ClientConfig.PROPERTY_CONNECT_TIMEOUT, 10 * 1000);
		Client client = Client.create(config);

//		WebResource resource = client.resource("http://127.0.0.1:8080/ApiData/r/zxsj/grxx/sfzxx?idCardNo=420984198704207896");
//		WebResource resource = client.resource("http://www.qilingyz.cn:8080/ApiData/r/zxsj/grxx/sfzxx?idCardNo=420984198704207896");
		WebResource resource = client.resource("http://www.qilingyz.com/api.php?m=open.queryStar&appkey=jdwx991230&identityCard=120103198603292638&fullName=王梓");
		
//		WebResource resource = client.resource("http://192.168.62.47:8080/ApiData/r/user/login?userName=wangzi&password=111111");
		
		String result = resource.accept(MediaType.APPLICATION_JSON_TYPE, MediaType.APPLICATION_XML_TYPE).header("Appkey", "123321").header("userID", "1").get(String.class);
		
//		String result = resource.accept(MediaType.TEXT_HTML).get(String.class);

		result = StringUtil.decodeUnicode(result);
		
		System.out.println(result);

	}
}
