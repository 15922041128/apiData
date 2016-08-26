//package org.pbccrc.api.biz.impl;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//import org.pbccrc.api.bean.ResultContent;
//import org.pbccrc.api.biz.TempBiz;
//import org.pbccrc.api.dao.SingleDao;
//import org.pbccrc.api.util.Constants;
//import org.pbccrc.api.util.RemoteApiOperator;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//
//@Service
//public class TempBizImpl implements TempBiz {
//
//	@Autowired
//	private SingleDao singleDao;
//	
//	@Autowired
//	private RemoteApiOperator remoteApiOperator;
//	
//	@Override
//	public String query(String service, Map urlParams) {
//		
//		List<Map<String, Object>> remoteApiList = singleDao.getRemoteApi(service);
//		
//		Map<String, Object> remoteApi = remoteApiList.get(0);
//		
//		String url = String.valueOf(remoteApi.get("url"));
//		String param = String.valueOf(remoteApi.get("param"));
//		JSONArray paramRelArray = JSONArray.parseArray(param);
//		
//		String remotreRequestUrl = Constants.BLANK;
//		StringBuffer remotreRequestUrlBuff = new StringBuffer(url);
//		
//		for (Object o : paramRelArray) {
//			
//			JSONObject object = (JSONObject)o;
//			
//			Set<String> keySet = object.keySet();
//			
//			for (String key : keySet) {
//				// 根据remoteApi获取远程访问参数
//				String remoteKey = object.getString(key);
//				// 拼接远程apiUrl参数
//				remotreRequestUrlBuff.append(remoteKey);
//				remotreRequestUrlBuff.append(Constants.URL_EQUAL);
//				remotreRequestUrlBuff.append(((String[])urlParams.get(key))[0]);
//				remotreRequestUrlBuff.append(Constants.URL_PARAM_CONNECTOR);
//			}
//		}
//		remotreRequestUrl = remotreRequestUrlBuff.toString();
//		remotreRequestUrl = remotreRequestUrl.substring(0, remotreRequestUrl.length() -1);
//		
//		String remoteResult = remoteApiOperator.remoteAccept(remotreRequestUrl);
//		
//		return remoteResult;
//	}
//
//	@Override
//	public String queryTemp(String service, Map urlParams) throws Exception {
//		
//		String resultStr = Constants.BLANK;
//		
//		ResultContent resultContent = new ResultContent();
//		
//		List<Map<String, Object>> remoteApiList = singleDao.getRemoteApi(service);
//		
//		Map<String, Object> remoteApi = remoteApiList.get(0);
//		
//		int id = Integer.parseInt(String.valueOf(remoteApi.get("ID")));
//		String url = String.valueOf(remoteApi.get("url"));
//		String param = String.valueOf(remoteApi.get("param"));
//		int count = Integer.parseInt(String.valueOf(remoteApi.get("count")));
//		if (count == 0) {
//			resultContent.setErrNum(Constants.ERR_CNT);
//			resultContent.setRetMsg(Constants.RET_MSG_CNT);
//			resultStr = JSONObject.toJSONString(resultContent);
//			return resultStr;
//		}
//		count--;
//		
//		Map<String, Object> paramMap = new HashMap<String, Object>();
//		JSONArray paramArray = JSONArray.parseArray(param);
//		
//		for (Object o : paramArray) {
//			
//			JSONObject object = (JSONObject)o;
//			
//			String paramName = String.valueOf(object.get("paramName"));
//			String notNull = String.valueOf(object.get("notNull"));
//			
//			if (Constants.PARAM_REQUIRED_Y.equals(notNull)) {
//				if (null == urlParams.get(paramName)) {
//					resultContent.setErrNum(Constants.ERR_URL_INVALID);
//					resultContent.setRetMsg(Constants.RET_MSG_URL_INVALID + paramName);
//					resultStr = JSONObject.toJSONString(resultContent);
//					return resultStr;
//				}
//				paramMap.put(paramName, ((String[])urlParams.get(paramName))[0]);
//			} else {
//				if (null != urlParams.get(paramName)) {
//					paramMap.put(paramName, ((String[])urlParams.get(paramName))[0]);
//				}
//			}
//		}
//		
//		Map<String, Object> keys = singleDao.getKey();
//		String appid = String.valueOf(keys.get("appid"));
//		String mKey = String.valueOf(keys.get("mkey"));
//	
//		url = url + "?service=" + service + "&appid=" + appid;
//		
//		String remoteResult = remoteApiOperator.tempRemoteAccept(mKey, url, paramMap);
//		
//		resultStr = remoteResult;
//		
//		singleDao.updateCnt(id, count);
//		
//		return resultStr;
//	}
//
//}
