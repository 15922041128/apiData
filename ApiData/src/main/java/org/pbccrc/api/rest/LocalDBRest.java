package org.pbccrc.api.rest;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.pbccrc.api.biz.LocalDBBiz;
import org.pbccrc.api.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;

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
	public Response queryApi(@Context HttpServletRequest request) throws Exception {
		
		request.setCharacterEncoding("utf-8");
		
		String result = Constants.BLANK;
		
		return Response.ok(result).build();
	}

}
