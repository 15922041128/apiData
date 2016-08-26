package org.pbccrc.api.rest;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.pbccrc.api.bean.ResultContent;
import org.pbccrc.api.biz.TempBiz;
import org.pbccrc.api.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;

@Path("/temp")
public class TempRest {
	
	@Autowired
	private TempBiz tempBiz;
	
	@GET
	@Path("/query")
	public Response query(@QueryParam("service") String service, @Context HttpServletRequest request, @Context HttpServletResponse response) throws UnsupportedEncodingException {
		
		String result = Constants.BLANK;
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		
		try{
			result = tempBiz.queryTemp(service, request.getParameterMap());
		} catch (Exception e) {
			e.printStackTrace();
			ResultContent resultContent = new ResultContent();
			resultContent.setErrNum(Constants.ERR_SERVER);
			resultContent.setRetMsg(Constants.RET_MSG_SERVER);
			return Response.ok(JSONObject.toJSON(resultContent)).build();
		}
		
		return Response.ok(result).build();
	}

}
