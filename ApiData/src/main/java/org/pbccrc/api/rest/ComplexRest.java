package org.pbccrc.api.rest;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.pbccrc.api.biz.ComplexBiz;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;

@Path("complex")
public class ComplexRest {
	
	@Autowired
	private ComplexBiz complexBiz;

	/**
	 * 失信人查询
	 * @param identifier
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("/sxr")
	public Response querySxr(@QueryParam("identifier") String identifier,
			@Context HttpServletRequest request) throws Exception{
		
		Map<String, Object> queryResult = complexBiz.querySxr(identifier);
		
		return Response.ok(JSONObject.toJSON(queryResult)).build();
	}
}
