package org.pbccrc.api.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.pbccrc.api.biz.SMSBiz;
import org.pbccrc.api.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

@Path("SMS")
public class SMSRest {
	
	@Autowired
	@Qualifier("dxw_smsBiz")
	private SMSBiz smsBiz;
	
	@GET
	@Path("/getVCode")
	public Response query(@QueryParam("phoneNo") String phoneNo) throws Exception {
		
		// 生成随机4位验证码
		String vCode = StringUtil.createVCode4();
		
		// 将验证码发送至指定用户
		smsBiz.query(phoneNo, vCode);
		
		return Response.ok(vCode).build();
	}

}
