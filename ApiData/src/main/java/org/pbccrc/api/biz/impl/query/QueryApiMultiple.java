package org.pbccrc.api.biz.impl.query;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.pbccrc.api.bean.DBEntity;
import org.pbccrc.api.bean.ResultContent;
import org.pbccrc.api.biz.query.QueryApi;
import org.pbccrc.api.dao.CodeDao;
import org.pbccrc.api.dao.DBOperator;
import org.pbccrc.api.dao.RemoteApiDao;
import org.pbccrc.api.util.Constants;
import org.pbccrc.api.util.RemoteApiOperator;
import org.pbccrc.api.util.SpringContextUtil;
import org.pbccrc.api.util.StringUtil;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class QueryApiMultiple implements QueryApi {
	
	private RemoteApiDao remoteApiDao;
	
	private RemoteApiOperator remoteApiOperator;
	
	private CodeDao codeDao;
	
	private DBOperator dbOperator;
	
	public QueryApiMultiple() {
		
		remoteApiDao = (RemoteApiDao) SpringContextUtil.getBean("remoteApiDao");
		
		remoteApiOperator = (RemoteApiOperator) SpringContextUtil.getBean("remoteApiOperator");
		
		codeDao = (CodeDao) SpringContextUtil.getBean("codeDao");
		
		dbOperator = (DBOperator) SpringContextUtil.getBean("dbOperator");
	}

	@Override
	@SuppressWarnings("rawtypes")
	public String query(Map<String, Object> localApi, Map urlParams) throws Exception {
		
		// 返回字符串
		String resultStr = Constants.BLANK;
		
		// 返回信息对象
		ResultContent resultContent = new ResultContent();
		resultContent.setErrNum(Constants.CODE_ERR_SUCCESS);
		resultContent.setRetMsg(Constants.CODE_ERR_SUCCESS_MSG);
		
		// 本地api请求参数
		String localParams = String.valueOf(localApi.get("params"));
		JSONArray localParamArray = JSONArray.parseArray(localParams);
		
		// 获得远程api
		List<Map<String, Object>> remoteApiList = remoteApiDao.getRemoteApiByLocal(Integer.parseInt(String.valueOf(localApi.get("ID"))));
		
		// 遍历远程api
		for (int i = 0; i < remoteApiList.size(); i++) {
			
			Map<String, Object> remoteApi = remoteApiList.get(i);
			
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
			// 返回值对应关系
			String retParamRel = String.valueOf(remoteApi.get("retParamRel"));
			
			
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
					if (null == urlParams.get(paramName)) {
						resultContent.setErrNum(Constants.ERR_URL_INVALID);
						resultContent.setRetMsg(Constants.RET_MSG_URL_INVALID + paramName);
						return resultContent.toString();
					}
					// 设置远程访问参数
					paramMap.put(paramName, ((String[])urlParams.get(paramName))[0]);
				} else {
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
			
			// 返回字符串
			String returnStr = Constants.BLANK;
			
			// 远程访问url
			url += Constants.URL_CONNECTOR + serviceName + Constants.EQUAL + service + 
					Constants.URL_PARAM_CONNECTOR + apiKeyName + Constants.EQUAL+ apiKey;
			// 判断加密方式
			if(Constants.ENCRYPT_TYPE_NORMAL.equals(encryptType)) {
				// 不加密
				for (String key : paramMap.keySet()) {
					String value = String.valueOf(paramMap.get(key));
					url += Constants.URL_PARAM_CONNECTOR + key + Constants.EQUAL + URLEncoder.encode(value, "utf-8");
				}
				returnStr = remoteApiOperator.remoteAccept(url);
			} else if (Constants.ENCRYPT_TYPE_QL.equals(encryptType)) {
				// 全联加密
				returnStr = remoteApiOperator.qlRemoteAccept(encryptKey, url, paramMap);
			}
			
			// 如果返回字符串为空,则返回失败信息
			if (StringUtil.isNull(returnStr)) {
				// 返回
				Map<String, Object> code = codeDao.queryByCode(Constants.CODE_ERR_FAIL);
				resultContent.setErrNum(Constants.CODE_ERR_FAIL);
				resultContent.setRetMsg(String.valueOf(code.get("codeValue")));
				return resultContent.toString();
			}
			
			
			// 解析返回数据
			JSONObject returnJson = JSONObject.parseObject(returnStr);
			// remoteApi.retCode
			JSONObject retCodeJson = JSONObject.parseObject(retCode);

			String codeName = retCodeJson.getString("codeName");
			
			// 判断codeName是否存在
			if (StringUtil.isNull(codeName)) {
				if (i == remoteApiList.size() - 1) {
					// 返回
					Map<String, Object> code = codeDao.queryByCode(Constants.CODE_ERR_FAIL);
					resultContent.setErrNum(Constants.CODE_ERR_FAIL);
					resultContent.setRetMsg(String.valueOf(code.get("codeValue")));
					return resultContent.toString();
				} else {
					continue;
				}
			}
			
			JSONObject codeValue = retCodeJson.getJSONObject("codeValue");
			JSONObject insertCondition = retCodeJson.getJSONObject("insertCondition");
			
			// 判断code值是否存在
			String returnCode = returnJson.getString(codeName);
			if (StringUtil.isNull(returnCode)) {
				if (i == remoteApiList.size() - 1) {
					// 返回
					Map<String, Object> code = codeDao.queryByCode(Constants.CODE_ERR_FAIL);
					resultContent.setErrNum(Constants.CODE_ERR_FAIL);
					resultContent.setRetMsg(String.valueOf(code.get("codeValue")));
					return resultContent.toString();
				} else {
					continue;
				}
			}
			
			// 判断是否有匹配的code
			JSONObject returnCodeObj = codeValue.getJSONObject(returnCode);
			if (null == returnCodeObj) {
				// 没有对应的code 判断是否为最后一条数据
				// 是:解析并返回 
				// 否:continue
				if (i == remoteApiList.size() - 1) {
					// 返回
					Map<String, Object> code = codeDao.queryByCode(Constants.CODE_ERR_FAIL);
					resultContent.setErrNum(Constants.CODE_ERR_FAIL);
					resultContent.setRetMsg(String.valueOf(code.get("codeValue")));
					return resultContent.toString();
				} else {
					continue;
				}
				
			} else {
				// 找到对应code
				String codeStatus = returnCodeObj.getString("codeStatus");
				String codeType = returnCodeObj.getString("codeType");
				String localCode = returnCodeObj.getString("localCode");
				// codeMsg字段是为了方便理解该code含义用 代码中并无实际用处
				// String codeMsg = returnCodeObj.getString("codeMsg");
				
				// 判断是否成功
				if (Constants.RET_CODE_SUCCESS.equals(codeStatus)) {
					// success
					// 如果插入条件为空,则直接插入数据库
					if (null == insertCondition) {
						// insertDB
						resultStr = insertDB(returnJson, localApi, localParamArray, urlParams, retParamRel, Constants.CODE_ERR_SUCCESS);
						resultContent.setRetData(resultStr);
						resultStr = resultContent.toString();
					} else {
						// 插入条件不为空,则判断是否符合插入条件
						String key = insertCondition.keySet().iterator().next();
						String value = insertCondition.getString(key);
						String[] keyArray = key.split(Constants.CONNECTOR_LINE);
						
						JSONObject jsonObject = new JSONObject();
						
						String returnValue = Constants.BLANK;
						if (keyArray.length == 1) {
							returnValue = returnJson.getString(keyArray[0]);
						} else {
							for (int n = 0; n < keyArray.length - 1; n++) {
								if (n == 0) {
									jsonObject = returnJson.getJSONObject(keyArray[0]);
								} else {
									jsonObject = jsonObject.getJSONObject(keyArray[n]);
								}
							}
							key = keyArray[keyArray.length - 1];
							returnValue = jsonObject.getString(key);
						}
						
						if (!StringUtil.isNull(returnValue) && returnValue.equals(value)) {
							// insertDB
							resultStr = insertDB(returnJson, localApi, localParamArray, urlParams, retParamRel, Constants.CODE_ERR_SUCCESS);
							resultContent.setRetData(resultStr);
							resultStr = resultContent.toString();
						} else {
							// 返回成功
							Map<String, Object> code = codeDao.queryByCode(Constants.CODE_ERR_SUCCESS);
							resultContent.setErrNum(localCode);
							resultContent.setRetMsg(String.valueOf(code.get("codeValue")));
							resultContent.setRetData(returnStr);
							resultStr = resultContent.toString();
						}
					}
					break;
				} else {
					// error
					// 判断返回类型
					if (Constants.RET_CODE_TYPE_CONTINUE.equals(codeType)) {
						// continue 判断是否为最后一条数据
						// 是:解析并返回 
						// 否:continue
						if (i == remoteApiList.size() - 1) {
							// 返回
							Map<String, Object> code = codeDao.queryByCode(localCode);
							resultContent.setErrNum(localCode);
							resultContent.setRetMsg(String.valueOf(code.get("codeValue")));
							resultStr = resultContent.toString();
						} else {
							continue;
						}
					} else {
						// break 解析结果并返回
						Map<String, Object> code = codeDao.queryByCode(localCode);
						resultContent.setErrNum(localCode);
						resultContent.setRetMsg(String.valueOf(code.get("codeValue")));
						resultStr = resultContent.toString();
					}
				}
			}
			
		} // loop remoteApiList end
		return resultStr;
	}
	
	@SuppressWarnings("rawtypes")
	private String insertDB (JSONObject returnJson, Map<String, Object> localApi, 
			JSONArray localParamArray, Map urlParams, String retParamRel, String returnCode) {
		
		JSONObject retObj = new JSONObject();
		
		// 本地api返回参数
		String[] returnParams = String.valueOf(localApi.get("returnParam")).split(Constants.COMMA);
		
		// tableName末段与localApi.service后缀相同
		String tableName = String.valueOf(localApi.get("service")).split(Constants.CONNECTOR_LINE)[1];
		DBEntity dbEntity = new DBEntity();
		dbEntity.setTableName("d_" + "m_" + tableName);
		List<String> fields = new ArrayList<String>();
		fields.add("localApiID");
		fields.add("returnTyp");
		fields.add("returnCode");
		List<String> values = new ArrayList<String>();
		values.add(String.valueOf(localApi.get("ID")));
		values.add(String.valueOf(localApi.get("returnType")));
		values.add(returnCode);
		
		// 查询条件
		// insert项
		for (Object o : localParamArray) {
			JSONObject object = (JSONObject)o;
			String paramName = String.valueOf(object.get("paramName"));
			String paramType = String.valueOf(object.get("paramType"));
			// url
			if (Constants.PARAM_TYPE_URL.equals(paramType) 
					&& null != urlParams.get(paramName) && !StringUtil.isNull(((String[])urlParams.get(paramName))[0])) {
				fields.add(paramName);
			}
			// 常量
			if (Constants.PARAM_TYPE_CONSTANT.equals(paramType)) {
				fields.add(paramName);
			}
		}
		// insert值
		for (Object o : localParamArray) {
			JSONObject object = (JSONObject)o;
			String paramName = String.valueOf(object.get("paramName"));
			String paramType = String.valueOf(object.get("paramType"));
			// url
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
		// 返回值
		// 获得返回值对应关系
		JSONObject retParamRelObj = JSONObject.parseObject(retParamRel);
		for (String returnParam : returnParams) {
			// insert项
			fields.add(returnParam);
			// insert值
			// example:
			/*
			返回字符串: 
			{
			    "Result": {
			        "NAME": "张三",
			        "COMPSTATUS": "1",
			        "COMPRESULT": "一致",
			        "IDENTITYCARD": "120103XXXXXXXX2638",
			        "serialno": "14719165292088474"
			    },
			    "NAME": "张三",
			    "Msg": "成功",
			    "Code": "000000",
			    "IDENTITYCARD": "120103XXXXXXXX2638"
			}
			localApi.returnParam : idCardNo,name,status
			remoteApi.retParamRel:
			[
			    {
			        "idCardNo": "IDENTITYCARD"
			    },
			    {
			        "name": "NAME"
			    },
			    {
			        "status": "Result-COMPRESULT"
			    }
			]
			*/
			// 获取对应关系key
			String retParamRelStr = retParamRelObj.getString(returnParam);
			if (StringUtil.isNull(retParamRelStr)) {
				values.add(Constants.BLANK);
				continue;
			}
			String[] retKeyArray = retParamRelStr.split(Constants.CONNECTOR_LINE);
			
			// 判断key是否为单层
			if (1 == retKeyArray.length) {
				// 单层
				values.add(returnJson.getString(retKeyArray[0]));
				retObj.put(returnParam, returnJson.getString(retKeyArray[0]));
			} else {
				// 多层
				JSONObject jsonObject = new JSONObject();
				for (int n = 0; n < retKeyArray.length - 1; n++) {
					if (n == 0) {
						jsonObject = returnJson.getJSONObject(retKeyArray[0]);
					} else {
						jsonObject = jsonObject.getJSONObject(retKeyArray[n]);
					}	
				}
				values.add(jsonObject.getString(retKeyArray[retKeyArray.length - 1]));
				retObj.put(returnParam, jsonObject.getString(retKeyArray[retKeyArray.length - 1]));
			}
		}
		dbEntity.setFields(fields);
		dbEntity.setValues(values);
		dbOperator.insertData(dbEntity);
		
		return retObj.toJSONString();
	}

}
