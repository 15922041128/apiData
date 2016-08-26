//package org.pbccrc.api.rest;
//
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.ws.rs.GET;
//import javax.ws.rs.Path;
//import javax.ws.rs.PathParam;
//import javax.ws.rs.core.Context;
//import javax.ws.rs.core.Response;
//
//import org.pbccrc.api.bean.ResultContent;
//import org.pbccrc.api.biz.ZxsjBiz;
//import org.pbccrc.api.util.Constants;
//import org.pbccrc.api.util.Validator;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import com.alibaba.fastjson.JSONObject;
//
//@Path("/zxsj")
//public class ZxsjRest {
//	
//	@Autowired
//	private Validator validator;
//	
//	@Autowired
//	private ZxsjBiz zxsjBiz;
//	
//	@GET
//	@Path("/grxx/{type}")
//	public Response query(@PathParam("type")String type, @Context HttpServletRequest request) {
//		
//		// 返回字符串
//		String resultStr = Constants.BLANK;
//		
//		ResultContent resultContent = new ResultContent();
//		resultContent.setErrNum(Constants.ERR_NOERR);
//		resultContent.setRetMsg(Constants.RET_MSG_SUCCESS);
//		
//		// 获取appKey
//		String appKey = request.getHeader("appKey");
//		// 获取用户ID
//		int userID = Integer.parseInt(request.getHeader("userID"));
//		
//		// 获取url参数
//		Map urlParams = request.getParameterMap();
//		
//		// 验证
//		if (!validator.validateRequest(userID, type, appKey, urlParams, resultContent)) {
//			return Response.ok(JSONObject.toJSONString(resultContent)).build();
//		}
//		
//		resultStr = zxsjBiz.queryZxsj(type, appKey, urlParams, resultContent);
//		
//		return Response.ok(resultStr).build();
//	} 
//
//}
