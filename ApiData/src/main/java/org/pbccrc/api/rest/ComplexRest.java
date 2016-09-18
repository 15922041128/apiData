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

import org.pbccrc.api.biz.ComplexBiz;
import org.pbccrc.api.util.Constants;
import org.pbccrc.api.util.PdfBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@Path("complex")
public class ComplexRest {
	
	@Autowired
	private ComplexBiz complexBiz;
	
	@Autowired
	private PdfBuilder pdfBuilder;

	/**
	 * 失信人查询
	 * @param identifier
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("/sxr")
	@Produces(MediaType.APPLICATION_JSON)
	public Response querySxr(@QueryParam("identifier") String identifier, @QueryParam("queryItem") String queryItem,
			@Context HttpServletRequest request) throws Exception{
		
		String[] queryItems = queryItem.split(Constants.COMMA);
		
		String bathPath = Constants.FILE_DOWNLOAD_SXR_PDF;
		
		Map<String, Object> queryResult = complexBiz.querySxr(identifier, queryItems);
		
		JSONObject result = new JSONObject();
		
		// 判断内码中是否存在被查询用户
		String isNull = "N";
		isNull = String.valueOf(queryResult.get("isNull"));
		if ("Y".equals(isNull)) {
			result.put("isNull", isNull);
			return Response.ok(result).build();
		}
		
		String filePath = pdfBuilder.getPDF(JSON.toJSONString(queryResult), queryItems, bathPath, request);
		result.put("filePath", "/ApiData" + bathPath + filePath);
		result.put("isNull", isNull);
		
		return Response.ok(result).build();
	}
}
