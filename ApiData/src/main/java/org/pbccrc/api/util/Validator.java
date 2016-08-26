package org.pbccrc.api.util;

import java.util.Map;

import org.pbccrc.api.bean.ResultContent;
import org.pbccrc.api.dao.RelationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Service
public class Validator {
	
	@Autowired
	private RelationDao relationDao;
	
	/**
	 * @param userID     		用户ID
	 * @param apiKey     		apiKey
	 * @param localApi     		本地api
	 * @param urlParams  		url参数
	 * @param resultContent     查询返回对象
	 * @return           		是否通过验证
	 */
	@SuppressWarnings("rawtypes")
	public boolean validateRequest(String userID, String apiKey, Map<String, Object> localApi, Map urlParams, ResultContent resultContent) {
		
		// 验证userID是否存在
		if (StringUtil.isNull(userID)) {
			resultContent.setErrNum(Constants.ERR_MISSING_USER_ID);
			resultContent.setRetMsg(Constants.RET_MSG_MISSING_USER_ID);
			return false;
		}
		
		Map<String, Object> relation = relationDao.query(userID, apiKey, Integer.parseInt(String.valueOf(localApi.get("ID"))));
		
		// 验证APIKEY是否存在
		if (StringUtil.isNull(apiKey)) {
			resultContent.setErrNum(Constants.ERR_MISSING_APIKEY);
			resultContent.setRetMsg(Constants.RET_MSG_MISSING_APIKEY);
			return false;
		}
		
		// 验证APIKEY是否有效
		if (null == relation) {
			resultContent.setErrNum(Constants.ERR_APIKEY_USER_INVALID);
			resultContent.setRetMsg(Constants.RET_MSG_APIKEY_USER_INVALID);
			return false;
		}
		
		String dbAPIKEY = String.valueOf(relation.get(Constants.API_KEY));
		
		if (!apiKey.equals(dbAPIKEY)) {
			resultContent.setErrNum(Constants.ERR_APIKEY_INVALID);
			resultContent.setRetMsg(Constants.RET_MSG_APIKEY_INVALID);
			return false;
		}
		
		// 验证访问次数
		int count = Integer.parseInt(String.valueOf(relation.get("count")));
		if (count == 0) {
			resultContent.setErrNum(Constants.ERR_CNT);
			resultContent.setRetMsg(Constants.RET_MSG_CNT);
			return false;
		}
		
		// 验证参数是否与api匹配
		String params = (String) localApi.get("params");
		JSONArray array = JSONArray.parseArray(params);
		for (Object o : array) {
			
			JSONObject object = (JSONObject)o;
			
			String paramName = String.valueOf(object.get("paramName"));
			String paramType = String.valueOf(object.get("paramType"));
			String notNull = String.valueOf(object.get("notNull"));
			
			if (Constants.PARAM_TYPE_URL.equals(paramType) && Constants.PARAM_REQUIRED_Y.equals(notNull)) {
				if (null == urlParams.get(paramName)) {
					resultContent.setErrNum(Constants.ERR_URL_INVALID);
					resultContent.setRetMsg(Constants.RET_MSG_URL_INVALID + paramName);
					return false;
				}
			}
		}
		
		return true;
	}

}
