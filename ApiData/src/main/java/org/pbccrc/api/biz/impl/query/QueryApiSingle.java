package org.pbccrc.api.biz.impl.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.pbccrc.api.bean.DBEntity;
import org.pbccrc.api.bean.ResultContent;
import org.pbccrc.api.biz.query.QueryApi;
import org.pbccrc.api.dao.DBOperator;
import org.pbccrc.api.dao.RemoteApiDao;
import org.pbccrc.api.util.Constants;
import org.pbccrc.api.util.RemoteApiOperator;
import org.pbccrc.api.util.SpringContextUtil;
import org.pbccrc.api.util.StringUtil;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class QueryApiSingle implements QueryApi {
	
	private RemoteApiDao remoteApiDao;
	
	private RemoteApiOperator remoteApiOperator;
	
	private DBOperator dbOperator;
	
	public QueryApiSingle() {
		
		remoteApiDao = (RemoteApiDao) SpringContextUtil.getBean("remoteApiDao");
		
		remoteApiOperator = (RemoteApiOperator) SpringContextUtil.getBean("remoteApiOperator");
		
		dbOperator = (DBOperator) SpringContextUtil.getBean("dbOperator");
	}

	@Override
	@SuppressWarnings("rawtypes")
	public Map<String, Object> query(Map<String, Object> localApi, HttpServletRequest request) throws Exception{
		
		Map<String, Object> returnMap = new HashMap<String, Object>();

		String resultStr = Constants.BLANK;
		
		// 获得url参数
		Map urlParams = request.getParameterMap();
		
		// 返回信息对象
		ResultContent resultContent = new ResultContent();
		
		// 本地api参数
		String localParams = String.valueOf(localApi.get("params"));
		JSONArray localParamArray = JSONArray.parseArray(localParams);
		
		// 获得远程api
		List<Map<String, Object>> remoteApiList = remoteApiDao.getRemoteApiByLocal(Integer.parseInt(String.valueOf(localApi.get("ID"))));
		Map<String, Object> remoteApi = remoteApiList.get(0);
		
		// remoteID
		int id = Integer.parseInt(String.valueOf(remoteApi.get("ID")));
		// url
		String url = String.valueOf(remoteApi.get("url"));
		// 远程api访问参数
		String param = String.valueOf(remoteApi.get("param"));
		// 访问参数对应关系
		String localParamRel = String.valueOf(remoteApi.get("localParamRel"));
		// 服务名称
		String service = String.valueOf(remoteApi.get("service"));
		// apiKey
		String apiKey = String.valueOf(remoteApi.get("apiKey"));
		// 加密密钥
		String encryptKey = String.valueOf(remoteApi.get("encryptKey"));
		// 加密类型
		String encryptType = String.valueOf(remoteApi.get("encryptType"));
		// 返回参数
		String retCode = String.valueOf(remoteApi.get("retCode"));
		// 剩余查询次数
		int count = Integer.parseInt(String.valueOf(remoteApi.get("count")));
		
		// 判断是否超过查询次数
		if (count == 0) {
			resultContent.setErrNum(Constants.ERR_CNT);
			resultContent.setRetMsg(Constants.RET_MSG_CNT);
			resultStr = JSONObject.toJSONString(resultContent);
			returnMap.put("resultStr", resultStr);
			returnMap.put("updateCount", false);
			return returnMap;
		}
		
		// 远程访问参数列表
		Map<String, Object> paramMap = new HashMap<String, Object>();
		// 获得远程api访问参数
		JSONArray remoteParamArray = JSONArray.parseArray(param);
		
		// service参数名称
		String serviceName = Constants.BLANK;
		// apiKey参数名称
		String apiKeyName = Constants.BLANK;
		
		// 将本地api参数转换成远程api参数
		JSONArray localParamRelArray = JSONArray.parseArray(localParamRel);
		for (Object o : localParamRelArray) {
			
			JSONObject localParam = (JSONObject)o;
			
			// 获得本地api参数名称
			String localParamName = localParam.getString(localParam.keySet().iterator().next());
			
			// 遍历远程参数集合
			for (Object object : remoteParamArray) {
				
				JSONObject remoteParam = (JSONObject)object;
				String paramName = String.valueOf(remoteParam.get("paramName"));
				String lParam = String.valueOf(localParam.get(paramName));
				
				// 遍历本地参数集合,在本地参数集合中找到该参数,并判断参数类型
				// 如果参数类型为常量 ,直接添加,并跳转到下一条记录
				boolean isBreak = false;
				for (Object o1 : localParamArray) {
					JSONObject localObject = (JSONObject)o1;
					String localObjectParanName = localObject.getString("paramName");
					if (localParamName.equals(localObjectParanName)) {
						String paramType = localObject.getString("paramType");
						if (Constants.PARAM_TYPE_CONSTANT.equals(paramType)) {
							if (!StringUtil.isNull(lParam)) {
								remoteParam.put("paramName", lParam);
								isBreak = true;
							}
						}
						break;
					}
				}
				if (isBreak) {
					break;
				}
				
				if (!StringUtil.isNull(lParam)) {
					remoteParam.put("paramName", lParam);
					break;
				}
			}
		}
		
		// 循环参数
		for (Object o : remoteParamArray) {
			
			JSONObject remoteParam = (JSONObject)o;
			
			String paramName = String.valueOf(remoteParam.get("paramName"));
			String notNull = String.valueOf(remoteParam.get("notNull"));
			String paramType = String.valueOf(remoteParam.get("paramType"));
			
			// 获取service参数名称
			if (Constants.PARAM_TYPE_SERVICE.equals(paramType)) {
				serviceName = paramName;
				continue;
			}
			
			// 获取apiKey参数名称
			if (Constants.PARAM_TYPE_APIKEY.equals(paramType)) {
				apiKeyName = paramName;
				continue;
			}
			
			// 遍历本地参数集合,在本地参数集合中找到该参数,并判断参数类型
			// 如果参数类型为常量 ,直接添加,并跳转到下一条记录
			boolean isContinue = false;
			for (Object o1 : localParamArray) {
				JSONObject localObject = (JSONObject)o1;
				String localObjectParanName = localObject.getString("paramName");
				if (paramName.equals(localObjectParanName)) {
					String localParamType = localObject.getString("paramType");
					if (Constants.PARAM_TYPE_CONSTANT.equals(localParamType)) {
						String constantValue = localObject.getString("constantValue");
						isContinue = true;
						paramMap.put(paramName, constantValue);
					}
					break;
				}
			}
			if (isContinue) {
				continue;
			}
			
			// 判断参数是否为必填
			if (Constants.PARAM_REQUIRED_Y.equals(notNull) && 
					!Constants.PARAM_TYPE_SERVICE.equals(paramType) &&
					!Constants.PARAM_TYPE_APIKEY.equals(paramType)) {
				// 必填参数
				// 设置远程访问参数
				paramMap.put(paramName, ((String[])urlParams.get(paramName))[0]);
			} else {
				// 非必填参数
				// 设置远程访问参数
				if (null != urlParams.get(paramName)&& 
						!Constants.PARAM_TYPE_SERVICE.equals(paramType) &&
						!Constants.PARAM_TYPE_APIKEY.equals(paramType)) {
					paramMap.put(paramName, ((String[])urlParams.get(paramName))[0]);
				}
			}
			
		}
		
		// 访问远程api之前,将本地参数转为远程访问需要的参数
		for (Object o : localParamRelArray) {
			
			JSONObject localParam = (JSONObject)o;
			
			String relKey = localParam.keySet().iterator().next();
			
			for (String key : paramMap.keySet()) {
				
				Object value = paramMap.get(key);
				
				if (key.equals(String.valueOf(localParam.get(relKey)))) {
					paramMap.put(relKey, value);
					if (!relKey.equals(key)) {
						paramMap.remove(key);
					}
					break;
				}
			}
		}
		
		url += Constants.URL_CONNECTOR + serviceName + Constants.EQUAL + service + 
				Constants.URL_PARAM_CONNECTOR + apiKeyName + Constants.EQUAL+ apiKey;
		
		// 判断加密方式
		if(Constants.ENCRYPT_TYPE_NORMAL.equals(encryptType)) {
			// 不加密
			for (String key : paramMap.keySet()) {
				String value = String.valueOf(paramMap.get(key));
				url += Constants.URL_PARAM_CONNECTOR + key + Constants.EQUAL + value;
			}
			resultStr = remoteApiOperator.remoteAccept(url);
		} else if (Constants.ENCRYPT_TYPE_QL.equals(encryptType)) {
			// 全联加密
			resultStr = remoteApiOperator.qlRemoteAccept(encryptKey, url, paramMap);
		}
		returnMap.put("resultStr", resultStr);
		
		// 解析返回结果
		JSONObject resultJson = JSONObject.parseObject(resultStr);
		JSONObject retCodeJson = JSONObject.parseObject(retCode);
		
		// 获取successCondition
		JSONArray successConditionArray = retCodeJson.getJSONArray("successCondition");
		// 获取insertCondition
		JSONObject insertConditionJson = retCodeJson.getJSONObject("insertCondition");
		
		// 判断是否成功
		boolean isSuccess = true;
		for (Object o : successConditionArray) {
			JSONObject successCondition = (JSONObject)o;
			String successConditionName = successCondition.getString("conditionName");
			String successConditionValue = successCondition.getString("conditionValue");
			String successConditionType = successCondition.getString("conditionType");
			
			// 获取判断用返回value值
			String successValue = Constants.BLANK;
			String[] keyArray = successConditionName.split(Constants.CONNECTOR_LINE);
			// 判断条件是否为多层
			if (keyArray.length == 1) {
				// 单层
				String key = keyArray[0];
				successValue = resultJson.getString(key);
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
				successValue = jsonObject.getString(key);
			}
			
			// 判断条件类型
			if (Constants.CONDITION_TYPE_NOTNULL.equals(successConditionType)) {
				// notNull类型
				if (StringUtil.isNull(successValue)) {
					isSuccess = false;
					break;
				}
			} else if (Constants.CONDITION_TYPE_REGEX.equals(successConditionType)) {
				// 正则类型
				Pattern pattern = Pattern.compile(successConditionValue);
				Matcher matcher = pattern.matcher(successValue);
				if (!matcher.matches()) {
					isSuccess = false;
					break;
				}
			} else {
				// 默认为文本类型 (文本类型判断方式为equal)
				if (!successValue.equals(successConditionValue)) {
					isSuccess = false;
					break;
				}
			}
		}
		
		if (isSuccess) {
			// 查询成功
			// 更新查询次数
			returnMap.put("updateCount", true);
			
			// 判断是否插入数据库
			boolean isInsert = insertConditionJson.getBoolean("isInsert");
			if (isInsert) {
				boolean isertDB = true;
				JSONArray conditionArray = insertConditionJson.getJSONArray("conditionArray");
				// 判断insert条件是否为空
				if (null == conditionArray) {
					// 如果为空直接插入数据库
					// 插入数据库
					insertDB(localApi, resultStr, localParamArray, urlParams);
					// 更新访问次数
					remoteApiDao.updateCnt(id, --count);
				} else {
					// 不为空则进行条件判断
					for (Object o : conditionArray) {
						JSONObject condition = (JSONObject)o;
						String insertConditionName = condition.getString("conditionName");
						String insertConditionValue = condition.getString("conditionValue");
						String insertConditionType = condition.getString("conditionType");
						
						// 获取判断用返回value值
						String insertValue = Constants.BLANK;
						String[] keyArray = insertConditionName.split(Constants.CONNECTOR_LINE);
						// 判断条件是否为多层
						if (keyArray.length == 1) {
							// 单层
							String key = keyArray[0];
							insertValue = resultJson.getString(key);
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
							insertValue = jsonObject.getString(key);
						}
						
						// 判断条件类型
						if (Constants.CONDITION_TYPE_NOTNULL.equals(insertConditionType)) {
							// notNull类型
							if (StringUtil.isNull(insertValue)) {
								isertDB = false;
								break;
							}
						} else if (Constants.CONDITION_TYPE_REGEX.equals(insertConditionType)) {
							// 正则类型
							Pattern pattern = Pattern.compile(insertConditionValue);
							Matcher matcher = pattern.matcher(insertValue);
							if (!matcher.matches()) {
								isertDB = false;
								break;
							}
						} else {
							// 默认为文本类型 (文本类型判断方式为equal)
							if (!insertValue.equals(insertConditionValue)) {
								isertDB = false;
								break;
							}
						}
					}
					
					if (isertDB) {
						// 插入数据库
						insertDB(localApi, resultStr, localParamArray, urlParams);
						// 更新访问次数
						remoteApiDao.updateCnt(id, --count);
					}
					
				}
			}
		} else {
			// 查询失败
			// 不更新查询次数
			returnMap.put("updateCount", false);
		}
		
		return returnMap;
	}

	@SuppressWarnings("rawtypes")
	private void insertDB(Map<String, Object> localApi, String resultStr, JSONArray localParamArray, Map urlParams) {
		
		// tableName末段与localApi.service后缀相同
		String tableName = String.valueOf(localApi.get("service")).split(Constants.CONNECTOR_LINE)[1];
		// DB操作对象
		DBEntity entity = new DBEntity();
		entity.setTableName("d_" + "s_" + tableName);
		List<String> fields = new ArrayList<String>();
		fields.add("localApiID");
		fields.add("returnTyp");
		fields.add("returnVal");
		List<String> values = new ArrayList<String>();
		values.add(String.valueOf(localApi.get("ID")));
		values.add(String.valueOf(localApi.get("returnType")));
		values.add(resultStr);
		
		// insert项
		for (Object o : localParamArray) {
			JSONObject object = (JSONObject)o;
			String paramName = String.valueOf(object.get("paramName"));
			String paramType = String.valueOf(object.get("paramType"));
			// url类型
			if (Constants.PARAM_TYPE_URL.equals(paramType) 
					&& null != urlParams.get(paramName) && !StringUtil.isNull(((String[])urlParams.get(paramName))[0])) {
				fields.add(paramName);
			}
			// 常量
			if (Constants.PARAM_TYPE_CONSTANT.equals(paramType)) {
				fields.add(paramName);
			}
		}
		entity.setFields(fields);
		// insert值
		for (Object o : localParamArray) {
			JSONObject object = (JSONObject)o;
			String paramName = String.valueOf(object.get("paramName"));
			String paramType = String.valueOf(object.get("paramType"));
			// url类型
			if (Constants.PARAM_TYPE_URL.equals(paramType) 
					&& null != urlParams.get(paramName) && !StringUtil.isNull(((String[])urlParams.get(paramName))[0])) {
				values.add(((String[])urlParams.get(paramName))[0]);
			}
			// 常量
			if (Constants.PARAM_TYPE_CONSTANT.equals(paramType)) {
				String constantValue = String.valueOf(object.get("constantValue"));
				values.add(constantValue);
			}
		}
		entity.setValues(values);
		
		// 本地数据库插入数据
		dbOperator.insertData(entity);
		
	}
}
