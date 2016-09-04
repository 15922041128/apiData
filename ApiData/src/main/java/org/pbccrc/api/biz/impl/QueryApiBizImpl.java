package org.pbccrc.api.biz.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.pbccrc.api.bean.DBEntity;
import org.pbccrc.api.bean.ResultContent;
import org.pbccrc.api.biz.QueryApiBiz;
import org.pbccrc.api.biz.query.QueryApi;
import org.pbccrc.api.dao.DBOperator;
import org.pbccrc.api.dao.LocalApiDao;
import org.pbccrc.api.dao.RelationDao;
import org.pbccrc.api.dao.RemoteApiDao;
import org.pbccrc.api.util.Constants;
import org.pbccrc.api.util.StringUtil;
import org.pbccrc.api.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Service
public class QueryApiBizImpl implements QueryApiBiz{

	private QueryApi queryApi;
	
	@Autowired
	private LocalApiDao localApiDao;
	
	@Autowired
	private RemoteApiDao remoteApiDao;
	
	@Autowired
	private DBOperator dbOperator;
	
	@Autowired
	private Validator validator;
	
	@Autowired
	private RelationDao relationDao;
	
	@Override
	@SuppressWarnings("rawtypes")
	public String query(String service, HttpServletRequest request) throws Exception {
		
		String result = Constants.BLANK;
		
		// 获取apiKey
		String apiKey = request.getHeader(Constants.HEAD_APIKEY);
		// 获得用ID
		String userID = request.getHeader(Constants.HEAD_USER_ID);
		
		// 返回信息对象
		ResultContent resultContent = new ResultContent();
		
		// 获取service标识
		String[] serviceArray = service.split(Constants.CONNECTOR_LINE);
		// 单个或多个api标识
		String isSingle = serviceArray[0];
		// 访问服务
		String target = serviceArray[1];
		
		// 获得请求url参数
		Map urlParams = request.getParameterMap();
		
		// 获取本地api
		Map<String, Object> localApi = localApiDao.queryByService(isSingle + Constants.CONNECTOR_LINE + target);
		
		// 请求参数验证
		if (!validator.validateRequest(userID, apiKey, localApi, urlParams, resultContent)) {
			return JSONObject.toJSONString(resultContent);
		}
		
		// 本地api参数
		String localParams = String.valueOf(localApi.get("params"));
		JSONArray localParamArray = JSONArray.parseArray(localParams);
		
		// 本地api返回参数
		String[] returnParam = String.valueOf(localApi.get("returnParam")).split(Constants.COMMA);
		
		// DB操作对象
		DBEntity entity = new DBEntity();
		entity.setTableName("d_" + isSingle + Constants.UNDERLINE + target);
		List<String> fields = new ArrayList<String>();
		List<String> values = new ArrayList<String>();
		
		// 设置查询参数
		for (Object o : localParamArray) {
			JSONObject object = (JSONObject)o;
			String paramName = String.valueOf(object.get("paramName"));
			String paramType = String.valueOf(object.get("paramType"));
			if (Constants.PARAM_TYPE_URL.equals(paramType) 
					&& null != urlParams.get(paramName) && !StringUtil.isNull(((String[])urlParams.get(paramName))[0])) {
				fields.add(paramName);
			}
		}
		entity.setFields(fields);
		for (Object o : localParamArray) {
			JSONObject object = (JSONObject)o;
			String paramName = String.valueOf(object.get("paramName"));
			String paramType = String.valueOf(object.get("paramType"));
			if (Constants.PARAM_TYPE_URL.equals(paramType) 
					&& null != urlParams.get(paramName) && !StringUtil.isNull(((String[])urlParams.get(paramName))[0])) {
				values.add(((String[])urlParams.get(paramName))[0]);
			}
		}
		entity.setValues(values);
		
		// 查询本地api
		Map<String, Object> queryData = dbOperator.queryData(entity);
		
		if (null != queryData) {
			// 本地api有数据 直接返回本地数据
			if (Constants.PREFIX_SINGLE.equals(isSingle)) {
				// 唯一外部api
				String returnVal = String.valueOf(queryData.get("returnVal"));
				result = returnVal;
			} else {
				// 多个外部api
				JSONObject retJson = new JSONObject();
				for (String param : returnParam) {
					retJson.put(param, queryData.get(param));
				}
				result = retJson.toJSONString();
			}
			
			// 更新localApi访问次数
			// 获得localApiID
			int localApiID = Integer.parseInt(String.valueOf(localApi.get("ID")));
			Map<String, Object> relation = relationDao.query(userID, apiKey, localApiID);
			int count = Integer.parseInt(String.valueOf(relation.get("count")));
			// 判断关系映射表count是否为-1(-1为不计查询次数)
			if (-1 != count) {
				// 不是-1的情况下,更新查询次数
				boolean updateCount = true;
				// 判断是否为单一remoteApi
				if (Constants.PREFIX_SINGLE.equals(isSingle)) {
					// 单一remoteApi
					JSONObject resultJson = JSONObject.parseObject(result);
					// 获得远程remoteApi
					Map<String, Object> remoteApi= remoteApiDao.getRemoteApiByLocal(localApiID).get(0);
					String retCode = String.valueOf(remoteApi.get("retCode"));
					JSONObject retCodeJson = JSONObject.parseObject(retCode);
					// countCondition
					JSONObject countCondition = retCodeJson.getJSONObject("countCondition");
					
					boolean isCount = countCondition.getBoolean("isCount");
					// 判断是否更新查询次数
					if (isCount) {
						// 更新查询次数
						JSONArray conditionArray = countCondition.getJSONArray("conditionArray");
						// 判断insert条件是否为空
						if (null == conditionArray) {
							// 如果为空则直接更新查询次数
						} else {
							// 不为空则进行条件判断
							for (Object o : conditionArray) {
								JSONObject condition = (JSONObject)o;
								String countConditionName = condition.getString("conditionName");
								String countConditionValue = condition.getString("conditionValue");
								String countConditionType = condition.getString("conditionType");
								
								// 获取判断用返回value值
								String countValue = Constants.BLANK;
								String[] keyArray = countConditionName.split(Constants.CONNECTOR_LINE);
								// 判断条件是否为多层
								if (keyArray.length == 1) {
									// 单层
									String key = keyArray[0];
									countValue = resultJson.getString(key);
								} else {
									// 多层
									JSONObject jsonObject = new JSONObject();
									for (int n = 0; n < keyArray.length - 1; n++) {
										if (n == 0) {
											jsonObject = resultJson.getJSONObject(keyArray[0]);
										} else {
											jsonObject = jsonObject.getJSONObject(keyArray[n]);
										}
									}
									String key = keyArray[keyArray.length - 1];
									countValue = jsonObject.getString(key);
								}
								
								// 判断条件类型
								if (Constants.CONDITION_TYPE_NOTNULL.equals(countConditionType)) {
									// notNull类型
									if (StringUtil.isNull(countValue)) {
										updateCount = false;
										break;
									}
								} else if (Constants.CONDITION_TYPE_REGEX.equals(countConditionType)) {
									// 正则类型
									Pattern pattern = Pattern.compile(countConditionValue);
									Matcher matcher = pattern.matcher(countValue);
									if (!matcher.matches()) {
										updateCount = false;
										break;
									}
								} else {
									// 默认为文本类型 (文本类型判断方式为equal)
									if (!countValue.equals(countConditionValue)) {
										updateCount = false;
										break;
									}
								}
							}
						}
					} else {
						// 不更新查询次数
						updateCount = false;
					}
				} else {
					// 多个remoteApi
					String localApiCountCode = String.valueOf(localApi.get("countCode"));
					JSONObject localApiCountCodeJson = JSONObject.parseObject(localApiCountCode);
					boolean isCount = localApiCountCodeJson.getBoolean("isCount");
					if (isCount) {
						// 获取返回数据中retrunCode
						String retrunCode = String.valueOf(queryData.get("returnCode"));
						// 获取conditionCode
						String conditionCode = localApiCountCodeJson.getString("conditionCode");
						// 比较countCode
						if (!StringUtil.isNull(conditionCode) && conditionCode.equals(retrunCode)) {
							updateCount = true;
						} else {
							updateCount = false;
						}
					} 
				}
				
				if (updateCount) {
					String relationID = String.valueOf(relation.get("ID"));
					relationDao.updateCount(relationID);
				}
				
			}
			
		} else {
			// 本地api无数据 查询外部api
			
			Class<?> clazz;
			
			// 根据前缀获取具体实现类
			String className = Constants.PACKAGE_BIZ_IMP_QUERY;
			if (Constants.PREFIX_SINGLE.equals(isSingle)) {
				// 唯一外部api
				className += Constants.POINT + Constants.QUERY_API_SINGLE;
			} else {
				// 多个外部api
				className += Constants.POINT + Constants.QUERY_API_MULTIPLE;
			}
			
			clazz = Class.forName(className);
			
			queryApi = (QueryApi)clazz.newInstance();
			
			Map<String, Object> returnMap = queryApi.query(localApi, request);
			
			boolean updateCount = false;
			if (Constants.PREFIX_SINGLE.equals(isSingle)) {
				// 唯一外部api
				result = String.valueOf(returnMap.get("resultStr"));
				updateCount = Boolean.parseBoolean(String.valueOf(returnMap.get("updateCount")));
			} else {
				// 多个外部api
				result = String.valueOf(returnMap.get("resultStr"));
				// 获取localApi.countCode
				String localApiCountCode = String.valueOf(localApi.get("countCode"));
				JSONObject localApiCountCodeJson = JSONObject.parseObject(localApiCountCode);
				boolean isCount = localApiCountCodeJson.getBoolean("isCount");
				if (isCount) {
					// 获取返回countCode
					String retrunCountCode = String.valueOf(returnMap.get("countCode"));
					// 获取conditionCode
					String conditionCode = localApiCountCodeJson.getString("conditionCode");
					// 比较countCode
					if (!StringUtil.isNull(conditionCode) && conditionCode.equals(retrunCountCode)) {
						updateCount = true;
					}
				} 
			}
			
			if (updateCount) {
				// 更新访问localApi次数
				Map<String, Object> relation = relationDao.query(userID, apiKey, Integer.parseInt(String.valueOf(localApi.get("ID"))));
				int count = Integer.parseInt(String.valueOf(relation.get("count")));
				if (-1 != count) {
					String relationID = String.valueOf(relation.get("ID"));
					relationDao.updateCount(relationID);
				}
			}
		}
		
		return result;
	}

}
