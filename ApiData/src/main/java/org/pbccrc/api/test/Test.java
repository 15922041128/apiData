package org.pbccrc.api.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

import org.glassfish.jersey.server.model.Suspendable;

import com.alibaba.fastjson.JSONObject;

public class Test {
	
	public static String string2MD5(String inStr){  
        MessageDigest md5 = null;  
        try{  
            md5 = MessageDigest.getInstance("MD5");  
        }catch (Exception e){  
            System.out.println(e.toString());  
            e.printStackTrace();  
            return "";  
        }  
        char[] charArray = inStr.toCharArray();  
        byte[] byteArray = new byte[charArray.length];  
  
        for (int i = 0; i < charArray.length; i++)  
            byteArray[i] = (byte) charArray[i];  
        byte[] md5Bytes = md5.digest(byteArray);  
        StringBuffer hexValue = new StringBuffer();  
        for (int i = 0; i < md5Bytes.length; i++){  
            int val = ((int) md5Bytes[i]) & 0xff;  
            if (val < 16)  
                hexValue.append("0");  
            hexValue.append(Integer.toHexString(val));  
        }  
        return hexValue.toString();  
  
    }  
  
    public static String convertMD5(String inStr){  
  
        char[] a = inStr.toCharArray();  
        for (int i = 0; i < a.length; i++){  
            a[i] = (char) (a[i] ^ 221);  
        }  
        String s = new String(a);  
        return s;  
  
    }
    
	public void test(){
		
		String s = new String("teststr");  
        System.out.println("原始：" + s);  
        System.out.println("MD5后：" + string2MD5(s));  
        System.out.println("加密的：" + convertMD5(s));  
        System.out.println("解密的：" + convertMD5(convertMD5(s)));
		
	}
	
	InputStreamReader inputStreamReader;
	BufferedReader bufferedReader2;
	OutputStream outputStream;
	HttpURLConnection httpConnection;
	
	public void test2() throws Exception {

		String str = "D:/Program/workspace/work/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/ApiData/files/photos/1472455211812.jpg";
		
		String s = str.substring(str.indexOf("/ApiData"));
		
		System.out.println(s);
	}
	
	public static void main(String[] args) throws Exception {
		
		new Test().test2();
		
	/*	String str = "{\"apikye\": \"appKey\",\"id\": \"idCardNo\"}";
		
		String str2 = "{\"idCardNo\": \"id\"}";
		
		JSONObject jsonObject = JSONObject.parseObject(str2);
		
		Set<String> keySet = jsonObject.keySet();
		
		for (String key : keySet) {
			System.out.println(key + " : " + jsonObject.getString(key));
		}*/
		
	}

}
