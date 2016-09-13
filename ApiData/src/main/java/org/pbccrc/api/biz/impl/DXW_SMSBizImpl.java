package org.pbccrc.api.biz.impl;

import java.net.HttpURLConnection;
import java.net.URL;

import org.pbccrc.api.biz.SMSBiz;
import org.springframework.stereotype.Service;

/** 短信服务接口(短信王实现) */
@Service("dxw_smsBiz")
public class DXW_SMSBizImpl implements SMSBiz{
	
	//账号
	static String name = "yingze";
	//密码
	static String password = "1216B8CF79FB34846C458E062840";

	@Override
	public void query(String phoneNo, String vCode) throws Exception {
		
		// 模板内容
		String content = "您好，您的手机动态验证码为：" + vCode + "。该码10分钟内有效且只能输入1次，若10分钟内未输入，需重新获取。";
		// 模板签名
		String sign="鹰泽评分";
		
		// 创建StringBuffer对象用来操作字符串
		StringBuffer sb = new StringBuffer("http://web.duanxinwang.cc/asmx/smsservice.aspx?");
		// 向StringBuffer追加用户名
		sb.append("name=" + name);
		// 向StringBuffer追加密码（登陆网页版，在管理中心--基本资料--接口密码，是28位的）
		sb.append("&pwd=" + password);
		// 向StringBuffer追加手机号码
		sb.append("&mobile=" + phoneNo);
		// 向StringBuffer追加消息内容转URL标准码
//		sb.append("&content=" + URLEncoder.encode(content,"UTF-8"));
		sb.append("&content=" + content);
		//追加发送时间，可为空，为空为及时发送
		sb.append("&stime=");
		//加签名
//		sb.append("&sign=" + URLEncoder.encode(sign,"UTF-8"));
		sb.append("&sign=" + sign);
		//type为固定值pt  extno为扩展码，必须为数字 可为空
		sb.append("&type=pt&extno=");
		URL url = new URL(sb.toString());
		// 打开url连接
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		// 设置url请求方式 ‘get’ 或者 ‘post’
		connection.setRequestMethod("GET");
		// 发送
		url.openStream();
	}

}
