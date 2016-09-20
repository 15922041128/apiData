package org.pbccrc.api.rest;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.pbccrc.api.biz.LocalDBBiz;
import org.pbccrc.api.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;

@Path("ldb")
public class LocalDBRest {
	
	@Autowired
	private LocalDBBiz localDBBiz;

	/**
	 * 根据身份证和姓名查询信贷信息
	 * @param name
	 * @param idCardNo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("/get")
	public Response query(@QueryParam("name") String name, @QueryParam("idCardNo") String idCardNo, 
			@Context HttpServletRequest request) throws Exception {
		
		request.setCharacterEncoding("utf-8");
		
		name = request.getParameter("name");
		
		String result = Constants.BLANK;
		
		result = localDBBiz.query(name, idCardNo);
		
		return Response.ok(result).build();
	}
	
	/**
	 * 失信人查询
	 * @param idCardNo
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("/getSxr")
	public Response getSxr(@QueryParam("idCardNo") String idCardNo) throws Exception {
		
		String result = Constants.BLANK;
		
		result = localDBBiz.getSxr(idCardNo);
		
		return Response.ok(result).build();
	}
	
	@GET
	@Path("/query")
	@Produces(MediaType.APPLICATION_JSON)
	public Response queryApiByInsideCode(@QueryParam("service") String service, @QueryParam("idCardNo") String idCardNo) throws Exception {
		
		Map<String, Object> resultMap = localDBBiz.queryApi(service, idCardNo);
		
		JSONObject result = new JSONObject();
		
		// 判断内码中是否存在被查询用户
		String isNull = "N";
		isNull = String.valueOf(resultMap.get("isNull"));
		if ("Y".equals(isNull)) {
			result.put("isNull", isNull);
			return Response.ok(result).build();
		}
		
		result.put("isNull", isNull);
		result.put("result", resultMap.get("result"));
		
		return Response.ok(result).build();
	}

}
