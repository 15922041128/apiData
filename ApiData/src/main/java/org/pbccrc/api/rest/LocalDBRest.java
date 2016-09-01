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

}
