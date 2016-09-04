package org.pbccrc.api.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.pbccrc.api.util.DesUtils;
import org.pbccrc.api.util.StringUtil;

import com.alibaba.fastjson.JSON;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.util.Base64;

public class TempTest {
	
	public static void test1() throws Exception{
		HttpURLConnection httpConnection = null;
		OutputStream outputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader bufferedReader2 = null;
		String lineString = "";
		try {
			
			String url = "http://www.uniocredit.com/nuapi/UService.do?service=ucquanlian&appid=D37EF162524F265";
			Map map=new HashMap();
			map.put("identityCard", "120103198603292638");
//			map.put("personName", "王梓");
//			map.put("personIDNum", "120103198603292638");
//			map.put("sortName", "总监理工程师");
//			map.put("BANKPREMOBILE", "15922041128");
			
			
			String readLine = JSON.toJSONString(map);
			httpConnection = (HttpURLConnection) new URL(url).openConnection();
			httpConnection.setRequestMethod("POST");
			httpConnection.setDoOutput(true);
			httpConnection.setDoInput(true);
			httpConnection.setAllowUserInteraction(true);
			httpConnection.setRequestProperty("Content-Type", "application/json");
			outputStream = httpConnection.getOutputStream();
			// 加密 填入给的key
			byte[] mkey = "4EC17A77D8A150A5D37EF162524F2658".getBytes();
			byte[] encryption = DesUtils.encrypt(readLine.getBytes(), mkey);
//			byte[] encodeBase64String = Base64.encode(encryption);
			byte[] encodeBase64 = Base64.encode(encryption);
			outputStream.write(encodeBase64);
			inputStreamReader = new InputStreamReader(httpConnection.getInputStream());
			bufferedReader2 = new BufferedReader(inputStreamReader);
			StringBuffer sbOutput = new StringBuffer(1024);
			while ((lineString = bufferedReader2.readLine()) != null) {
				sbOutput.append(lineString);
			}

			byte[] decodeBase64 = Base64.decode(sbOutput.toString().getBytes());
			byte[] decrypt = DesUtils.decrypt(decodeBase64, mkey);
			lineString = new String(decrypt);
			System.out.println(lineString);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			outputStream.close();
			bufferedReader2.close();
			inputStreamReader.close();
			httpConnection.disconnect();
		}
	}
	
	public static void test2() {
		ClientConfig config = new DefaultClientConfig();
		config.getProperties().put(ClientConfig.PROPERTY_CONNECT_TIMEOUT, 10 * 1000);
		Client client = Client.create(config);

		StringBuffer url = new StringBuffer();
//		url.append("http://127.0.0.1:8080/ApiData/r/ldb/get");
		url.append("http://www.qilingyz.com/api.php");
//		url.append("?m=open.queryStar");
//		url.append("&appkey=jdwx991230");
//		url.append("&fullName=王梓");
//		url.append("&identityCard=1120103198603292638");
		url.append("?m=open.queryScore");
		url.append("&identityCard=120103198603292638");
		url.append("&appkey=NyiKvzaqtz");
		
		WebResource resource = client.resource(url.toString());
		
		String result = resource.accept(MediaType.APPLICATION_JSON_TYPE, MediaType.APPLICATION_XML_TYPE).header("apiKey", "X37EF162524F265").header("userID", "1").get(String.class);
		
		result = StringUtil.decodeUnicode(result);
		
		System.out.println(result);
	}

	public static void main(String[] args) throws Exception {
//		test1();
		test2();
	}
}
