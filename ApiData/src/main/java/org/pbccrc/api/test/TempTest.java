package org.pbccrc.api.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.pbccrc.api.util.DesUtils;

import com.alibaba.fastjson.JSON;
import com.sun.jersey.core.util.Base64;

public class TempTest {

	public static void main(String[] args) throws IOException {
		HttpURLConnection httpConnection = null;
		OutputStream outputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader bufferedReader2 = null;
		String lineString = "";
		try {
			
			String url = "http://www.uniocredit.com/nuapi/UService.do?service=ucqiis&appid=D37EF162524F265";
			Map map=new HashMap();
			map.put("NAME", "王梓");
			map.put("IDENTITYCARD", "120103198603292638");
			
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
}
