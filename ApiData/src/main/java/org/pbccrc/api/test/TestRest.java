package org.pbccrc.api.test;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

@Path("/test")
public class TestRest {
	
	
	@GET
	@Path("/testMethod")
	public Response testMethod(@QueryParam("param") String param, @QueryParam("str") String str, @Context HttpServletRequest request) {
		
		System.out.println(request.getQueryString());
		System.out.println(request.getHeader("param"));
		
		return Response.ok(param + str).build();
	} 

}
