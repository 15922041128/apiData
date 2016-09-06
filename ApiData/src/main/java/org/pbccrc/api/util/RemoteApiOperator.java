package org.pbccrc.api.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.util.Base64;

@Service
public class RemoteApiOperator {
	
	// 普通访问
	public String remoteAccept(String url) {
		
		ClientConfig config = new DefaultClientConfig();
		config.getProperties().put(ClientConfig.PROPERTY_CONNECT_TIMEOUT, 10 * 1000);
		Client client = Client.create(config);
		
		WebResource resource = client.resource(url);
		
		String result = resource.accept(MediaType.APPLICATION_JSON_TYPE, MediaType.APPLICATION_XML_TYPE).get(String.class);
		
		return StringUtil.decodeUnicode(result);
	}
	
	// 全联加密
	public String qlRemoteAccept(String key, String url, Map<String, Object> paramMap) throws Exception {
		
		HttpURLConnection httpConnection = null;
		OutputStream outputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader bufferedReader2 = null;
		String lineString = "";
		
		String readLine = JSON.toJSONString(paramMap);
		httpConnection = (HttpURLConnection) new URL(url).openConnection();
		httpConnection.setRequestMethod("POST");
		httpConnection.setDoOutput(true);
		httpConnection.setDoInput(true);
		httpConnection.setAllowUserInteraction(true);
		httpConnection.setRequestProperty("Content-Type", "application/json");
		outputStream = httpConnection.getOutputStream();
		// 加密 填入给的key
		byte[] mkey = key.getBytes();
		byte[] encryption = DesUtils.encrypt(readLine.getBytes("utf-8"), mkey);
		byte[] encodeBase64 = Base64.encode(encryption);
		outputStream.write(encodeBase64);
		inputStreamReader = new InputStreamReader(httpConnection.getInputStream(),"UTF-8");
		bufferedReader2 = new BufferedReader(inputStreamReader);
		StringBuffer sbOutput = new StringBuffer(1024);
		while ((lineString = bufferedReader2.readLine()) != null) {
			sbOutput.append(lineString);
		}

		byte[] decodeBase64 = Base64.decode(sbOutput.toString().getBytes());
		byte[] decrypt = DesUtils.decrypt(decodeBase64, mkey);
		lineString = new String(decrypt, "utf-8");
		outputStream.close();
		bufferedReader2.close();
		inputStreamReader.close();
		httpConnection.disconnect();
		
		return lineString;
	}
	
	// 内部访问
}
