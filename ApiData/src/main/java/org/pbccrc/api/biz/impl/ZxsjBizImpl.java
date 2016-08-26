//package org.pbccrc.api.biz.impl;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//import org.pbccrc.api.bean.DBEntity;
//import org.pbccrc.api.bean.ResultContent;
//import org.pbccrc.api.biz.ZxsjBiz;
//import org.pbccrc.api.dao.ApiConfigDao;
//import org.pbccrc.api.dao.DBOperator;
//import org.pbccrc.api.dao.LocalApiDao;
//import org.pbccrc.api.dao.RemoteApiDao;
//import org.pbccrc.api.util.Constants;
//import org.pbccrc.api.util.RemoteApiOperator;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//
//@Service
//public class ZxsjBizImpl implements ZxsjBiz{
//	
//	@Autowired
//	private LocalApiDao localApiDao;
//	
//	@Autowired
//	private RemoteApiDao remoteApiDao;
//	
//	@Autowired
//	private ApiConfigDao apiConfigDao;
//	
//	@Autowired
//	private RemoteApiOperator remoteApiOperator;
//	
//	@Autowired
//	private DBOperator dbOperator;
//	
//	/**
//	 * @param type				查询类型
//	 * @param appKey			appKey
//	 * @param urlParams			url参数
//	 * @param resultContent		查询返回对象
//	 * @return					查询结果
//	 */
//	public String queryZxsj(String type, String appKey, Map urlParams, ResultContent resultContent) {
//		
//		// 返回字符串
//		String resultStr = Constants.BLANK;
//		
//		// 获取本地api
//		Map<String, Object> localApi = localApiDao.queryByService(type);
//		int localID = Integer.parseInt(String.valueOf(localApi.get("ID")));
//		String localParams = String.valueOf(localApi.get("params"));
//		JSONArray localParamArray = JSONArray.parseArray(localParams);
//		
//		// DB操作对象
//		DBEntity entity = new DBEntity();
//		entity.setTableName("d_" + type);
//		List<String> fields = new ArrayList<String>();
//		List<String> values = new ArrayList<String>();
//		
//		// 设置查询参数
//		for (Object o : localParamArray) {
//			JSONObject object = (JSONObject)o;
//			String paramName = String.valueOf(object.get("paramName"));
//			String paramType = String.valueOf(object.get("paramType"));
//			if (Constants.PARAM_TYPE_URL.equals(paramType)) {
//				fields.add(paramName);
//			}
//		}
//		entity.setFields(fields);
//		for (Object o : localParamArray) {
//			JSONObject object = (JSONObject)o;
//			String paramName = String.valueOf(object.get("paramName"));
//			String paramType = String.valueOf(object.get("paramType"));
//			if (Constants.PARAM_TYPE_URL.equals(paramType)) {
//				values.add(((String[])urlParams.get(paramName))[0]);
//			}
//		}
//		entity.setValues(values);
//		
//		// 查询本地api
//		Map<String, Object> queryData = dbOperator.queryData(entity);
//		
//		if (null != queryData) {
//			// 本地api有数据
//			String returnVal = String.valueOf(queryData.get("returnVal"));
//			return returnVal;
//		} else {
//			// 本地api无数据,查询远程api
//			
//			// 获取当前类型所有远程api
//			List<Map<String, Object>> remoteApiList = remoteApiDao.getRemoteApiByLocal(localID);
//			
//			for (Map<String, Object> map : remoteApiList) {
//				
//				int remoteID = Integer.parseInt(String.valueOf(map.get("ID")));
//				String url = String.valueOf(map.get("url"));
////						String params = String.valueOf(map.get("params"));
//				String apiKey = String.valueOf(map.get("apiKey"));
//				int returnType = Integer.parseInt(String.valueOf(map.get("returnType")));
//				
//				Map<String, Object> apiConfig = apiConfigDao.queryConfig(localID, remoteID);
//				String paramRel = String.valueOf(apiConfig.get("paramRel"));
//				JSONArray paramRelArray = JSONArray.parseArray(paramRel);
//				
//				// 拼接请求url
//				String remotreRequestUrl = Constants.BLANK;
//				StringBuffer remotreRequestUrlBuff = new StringBuffer(url);
//				if (paramRelArray.size() != 0) {
//					remotreRequestUrlBuff.append(Constants.URL_CONNECTOR);
//				}
//				for (Object o : paramRelArray) {
//					
//					JSONObject object = (JSONObject)o;
//					
//					Set<String> keySet = object.keySet();
//					
//					for (String key : keySet) {
//						// 根据config获取远程apiKey
//						String remoteKey = object.getString(key);
//						// 拼接远程apiUrl参数
//						remotreRequestUrlBuff.append(remoteKey);
//						remotreRequestUrlBuff.append(Constants.URL_EQUAL);
//						remotreRequestUrlBuff.append(((String[])urlParams.get(key))[0]);
//						remotreRequestUrlBuff.append(Constants.URL_PARAM_CONNECTOR);
//					}
//				}
//				
//				remotreRequestUrl = remotreRequestUrlBuff.toString();
//				remotreRequestUrl = remotreRequestUrl.substring(0, remotreRequestUrl.length() -1);
//				
//				String remoteResult = remoteApiOperator.remoteAccept(remotreRequestUrl, apiKey);
//				JSONObject resultJson = JSONObject.parseObject(remoteResult);
//				
//				String errNum = String.valueOf(resultJson.get("errNum"));
//				String retMsg = String.valueOf(resultJson.get("retMsg"));
////				String retData = String.valueOf(resultJson.get("retData"));
//				
//				if (Constants.RETMSG_SUCCESS.equals(retMsg)) {
////					result.setRetData(retData);
//					resultStr = remoteResult;
//					
//					// 将查询到的数据保存至本地数据库
//					fields = new ArrayList<String>();
//					fields.add("localApiID");
//					fields.add("returnTyp");
//					fields.add("returnVal");
//					for (Object o : localParamArray) {
//						JSONObject object = (JSONObject)o;
//						String paramName = String.valueOf(object.get("paramName"));
//						String paramType = String.valueOf(object.get("paramType"));
//						if (Constants.PARAM_TYPE_URL.equals(paramType)) {
//							fields.add(paramName);
//						}
//					}
//					entity.setFields(fields);
//					
//					values = new ArrayList<String>();
//					values.add(String.valueOf(localID));
//					values.add(String.valueOf(returnType));
//					values.add(remoteResult);
//					for (Object o : localParamArray) {
//						JSONObject object = (JSONObject)o;
//						String paramName = String.valueOf(object.get("paramName"));
//						String paramType = String.valueOf(object.get("paramType"));
//						if (Constants.PARAM_TYPE_URL.equals(paramType)) {
//							values.add(((String[])urlParams.get(paramName))[0]);
//						}
//					}
//					entity.setValues(values);
//					
//					dbOperator.insertData(entity);
//					
//					// TODO 目前只考虑同一数据来源单一api,故查到数据后马上返回
//					break;
//				} else {
//					resultContent.setErrNum(errNum);
//					resultContent.setRetMsg(retMsg);
//					resultStr = JSONObject.toJSONString(resultContent);
//					break;
//				}
//			}
//		}
//		
//		return resultStr;
//	}
//
//}
