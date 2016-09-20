package org.pbccrc.api.rest;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.pbccrc.api.bean.ResultContent;
import org.pbccrc.api.biz.CostBiz;
import org.pbccrc.api.biz.QueryApiBiz;
import org.pbccrc.api.dao.LocalApiDao;
import org.pbccrc.api.util.Constants;
import org.pbccrc.api.util.RemoteApiOperator;
import org.pbccrc.api.util.StringUtil;
import org.pbccrc.api.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


@Path("/queryApi")
public class QueryApiRest {

	@Autowired
	private QueryApiBiz queryApiBiz;
	
	@Autowired
	private Validator validator;
	
	@Autowired
	private LocalApiDao localApiDao;
	
	@Autowired
	private CostBiz costBiz;
	
	@Autowired
	private RemoteApiOperator remoteApiOperator;
	
	@GET
	@Path("/get")
	@SuppressWarnings("rawtypes")
	public Response query(@QueryParam("service") String service, @Context HttpServletRequest request) throws Exception {
		
		request.setCharacterEncoding("utf-8");
		
		String result = Constants.BLANK;
		
		ResultContent resultContent = new ResultContent();
		
		// 获取apiKey
		String apiKey = request.getHeader(Constants.HEAD_APIKEY);
		// 获得用ID
		String userID = request.getHeader(Constants.HEAD_USER_ID);
		
		// 获得请求url参数
		Map urlParams = request.getParameterMap();

		// 验证service格式
		if (StringUtil.isNull(service) || service.split(Constants.CONNECTOR_LINE).length != 2) {
			resultContent.setErrNum(Constants.ERR_SERVICE);
			resultContent.setRetMsg(Constants.RET_MSG_SERVICE);
			return Response.ok(JSONObject.toJSON(resultContent)).build();
		}
		
		// 获取service标识
		String[] serviceArray = service.split(Constants.CONNECTOR_LINE);
		// 单个或多个api标识
		String isSingle = serviceArray[0];
		// 访问服务
		String target = serviceArray[1];
		
		// 获取本地api
		Map<String, Object> localApi = localApiDao.queryByService(isSingle + Constants.CONNECTOR_LINE + target);
		
		// 请求参数验证
		if (!validator.validateRequest(userID, apiKey, localApi, urlParams, resultContent)) {
			result = JSONObject.toJSONString(resultContent);
			return Response.ok(result).build();
		}
		
		result = queryApiBiz.query(service, urlParams);
		JSONObject resultJson = JSONObject.parseObject(result);
		
		// 计费
		boolean isCost = true;
		// 判断是否计费
		// 获取localApi计费条件
		String costConditionStr = String.valueOf(localApi.get("costCondition"));
		JSONObject costCondition = JSONObject.parseObject(costConditionStr);
		// 判断是否计费
		boolean isCount = costCondition.getBooleanValue("isCount");
		if (isCount) {
			// 获取计费条件
			JSONArray conditionArray = costCondition.getJSONArray("conditionArray");
			// 遍历计费条件,只有当所有条件都成立时,才会进行计费
			for (Object o : conditionArray) {
				JSONObject condition = (JSONObject)o;
				String conditionName = condition.getString("conditionName");
				String conditionValue = condition.getString("conditionValue");
				String conditionType = condition.getString("conditionType");
				
				// 获取判断用返回value值
				String resultValue = Constants.BLANK;
				String[] keyArray = conditionName.split(Constants.CONNECTOR_LINE);
				// 判断条件是否为多层
				if (keyArray.length == 1) {
					// 单层
					String key = keyArray[0];
					resultValue = resultJson.getString(key);
				} else {
					// 多层
					JSONObject jsonObject = new JSONObject();
					for (int i = 0; i < keyArray.length - 1; i++) {
						if (i == 0) {
							jsonObject = resultJson.getJSONObject(keyArray[0]);
						} else {
							jsonObject = jsonObject.getJSONObject(keyArray[i]);
						}
					}
					String key = keyArray[keyArray.length - 1];
					resultValue = jsonObject.getString(key);
				}
				
				// 判断条件类型
				if (Constants.CONDITION_TYPE_NOTNULL.equals(conditionType)) {
					// notNull类型
					if (StringUtil.isNull(resultValue)) {
						isCost = false;
						break;
					}
				} else if (Constants.CONDITION_TYPE_REGEX.equals(conditionType)) {
					// 正则类型
					Pattern pattern = Pattern.compile(conditionValue);
					Matcher matcher = pattern.matcher(resultValue);
					if (!matcher.matches()) {
						isCost = false;
						break;
					}
				} else {
					// 默认为文本类型 (文本类型判断方式为equal)
					if (!resultValue.equals(conditionValue)) {
						isCost = false;
						break;
					}
				}
			}
			if (isCost) {
				costBiz.cost(Constants.COST_TYPE_COUNT, userID, apiKey, localApi);	
			}
		}
		
		return Response.ok(result).build();
	}
	
	@GET
	@Path("/score")
	public Response score(@Context HttpServletRequest request) throws Exception {
		
		ResultContent content = new ResultContent();
		content.setErrNum(Constants.CODE_ERR_SUCCESS);
		content.setRetMsg(Constants.CODE_ERR_SUCCESS_MSG);
		
		String url = Constants.REMOTE_URL_SCORE;
		
		String queryString = new String(request.getQueryString().getBytes("ISO-8859-1"), "utf-8");
		
		String appKey = "&appkey=SVXcpvaHNw";
		
		String result = remoteApiOperator.remoteAccept(url + Constants.URL_PARAM_CONNECTOR + queryString + appKey);
		
		JSONObject obj = JSONObject.parseObject(result);
		String score = obj.getString("score");
		if (StringUtil.isNull(score)) {
			content.setErrNum(Constants.CODE_ERR_FAIL);
			content.setRetMsg(Constants.CODE_ERR_FAIL_MSG);
		} else {
			content.setRetData(result);
		}
		
		return Response.ok(content.toString()).build();
	}
	
}
