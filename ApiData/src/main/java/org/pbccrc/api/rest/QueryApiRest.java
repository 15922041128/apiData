package org.pbccrc.api.rest;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.pbccrc.api.bean.ResultContent;
import org.pbccrc.api.biz.QueryApiBiz;
import org.pbccrc.api.util.Constants;
import org.pbccrc.api.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;


@Path("/queryApi")
public class QueryApiRest {

	@Autowired
	private QueryApiBiz queryApiBiz;
	
	@GET
	@Path("/get")
	public Response query(@QueryParam("service") String service, @Context HttpServletRequest request) throws Exception {
		
		request.setCharacterEncoding("utf-8");
		
		String result = Constants.BLANK;
		
		ResultContent resultContent = new ResultContent();
		
		// 验证service格式
		if (StringUtil.isNull(service) || service.split(Constants.CONNECTOR_LINE).length != 2) {
			resultContent.setErrNum(Constants.ERR_SERVICE);
			resultContent.setRetMsg(Constants.RET_MSG_SERVICE);
			return Response.ok(JSONObject.toJSON(resultContent)).build();
		}
		
		result = queryApiBiz.query(service, request);
		
		return Response.ok(result).build();
	}
}
