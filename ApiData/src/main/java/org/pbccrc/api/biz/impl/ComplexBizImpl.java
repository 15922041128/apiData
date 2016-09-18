package org.pbccrc.api.biz.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.pbccrc.api.biz.ComplexBiz;
import org.pbccrc.api.biz.QueryApiBiz;
import org.pbccrc.api.dao.ZhAddressDao;
import org.pbccrc.api.dao.ZhCreditCardDao;
import org.pbccrc.api.dao.ZhEmploymentDao;
import org.pbccrc.api.dao.ZhGuaranteeDao;
import org.pbccrc.api.dao.ZhInsideCodeDao;
import org.pbccrc.api.dao.ZhLoanDao;
import org.pbccrc.api.dao.ZhPersonDao;
import org.pbccrc.api.util.Constants;
import org.pbccrc.api.util.RemoteApiOperator;
import org.pbccrc.api.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Service
public class ComplexBizImpl implements ComplexBiz {
	
	@Autowired
	private ZhInsideCodeDao zhInsideCodeDao;
	
	@Autowired
	private ZhPersonDao zhPersonDao;
	
	@Autowired
	private ZhAddressDao zhAddressDao;
	
	@Autowired
	private ZhEmploymentDao zhEmploymentDao;
	
	@Autowired
	private ZhLoanDao zhLoanDao;
	
	@Autowired
	private ZhCreditCardDao zhCreditCardDao;
	
	@Autowired
	private ZhGuaranteeDao zhGuaranteeDao;
	
	@Autowired
	private QueryApiBiz queryApiBiz;
	
	@Autowired
	private RemoteApiOperator remoteApiOperator;
	
	/**
	 * 失信人查询
	 * @param identifier
	 * @return
	 */
	public Map<String, Object> querySxr(String identifier, String[] queryItems) throws Exception{
		
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("isNull", "N");
		
		// 根据身份证号获取内码信息
		Map<String, Object> insideCodeMap = zhInsideCodeDao.queryByCode(identifier);
		if (null == insideCodeMap) {
			returnMap.put("isNull", "Y");
			return returnMap;
		}
		// 内码
		String insideCode = String.valueOf(insideCodeMap.get("insideCode"));
		// 姓名
		String name = String.valueOf(insideCodeMap.get("name"));
		
		// 获取用户信用评分信息
		String score = "暂无分数"; 
		String service = Constants.SERVICE_S_QUERYSCORE;
		Map<String, String[]> params = new HashMap<String, String[]>();
		params.put("identityCard", new String[]{identifier});
		String result = queryApiBiz.query(service, params);
		JSONObject resultObj = JSONObject.parseObject(result);
		String resultScore = resultObj.getString("score");
		if (!StringUtil.isNull(resultScore)) {
			score = resultScore;
		}
		returnMap.put("score", score);
		
		// 遍历查询项
		for (String queryItem : queryItems) {
			// 人员基本信息
			if (Constants.ITEM_PERSON.equals(queryItem)) {
				Map<String, Object> personMap = zhPersonDao.query(insideCode);
				personMap.put("name", name);
				personMap.put("idCardNo", identifier);
				returnMap.put("person", personMap);
				continue;
			}
			// 居住信息
			if (Constants.ITEM_ADDRESS.equals(queryItem)) {
				Map<String, Object> addressMap = zhAddressDao.query(insideCode);
				returnMap.put("address", addressMap);
				continue;
			}
			// 就业信息
			if (Constants.ITEM_EMPLOYMENT.equals(queryItem)) {
				Map<String, Object> employmentMap = zhEmploymentDao.query(insideCode);
				returnMap.put("employment", employmentMap);
				continue;
			}
			// 贷款信息
			if (Constants.ITEM_LOAN.equals(queryItem)) {
				List<Map<String, Object>> loanList= zhLoanDao.query(insideCode);
				returnMap.put("loan", loanList);
				continue;
			}
			// 信用卡信息
			if (Constants.ITEM_CREDITCARD.equals(queryItem)) {
				Map<String, Object> creditCardMap = zhCreditCardDao.query(insideCode);
				returnMap.put("creditCard", creditCardMap);
				continue;
			}
			// 担保信息
			if (Constants.ITEM_GUARANTEE.equals(queryItem)) {
				List<Map<String, Object>> guaranteeList = zhGuaranteeDao.query(insideCode);
				returnMap.put("guarantee", guaranteeList);
				continue;
			}
			// 公积金信息
			if (Constants.ITEM_GGJ.equals(queryItem)) {
				JSONArray gjjArray = new JSONArray();
				service = Constants.SERVICE_S_QGJJD;
				params = new HashMap<String, String[]>();
				params.put("NAME", new String[]{name});
				params.put("CERTNO", new String[]{identifier});
				String gjj = queryApiBiz.query(service, params);
				JSONObject ggjObj = JSONObject.parseObject(gjj);
				JSONObject ggjResult = ggjObj.getJSONObject("Result");
				if (null != ggjResult){
					JSONArray jsonArray = ggjResult.getJSONArray("basicList");
					if (null != jsonArray) {
						gjjArray = jsonArray;
					}
				}
				returnMap.put("gjj", gjjArray);
				continue;
			}
			// 涉法涉诉信息
			if (Constants.ITEM_SFSS.equals(queryItem)) {
				// 涉诉信息(从执行公告中查询某人)
				JSONObject zxggObj = new JSONObject();
				service = Constants.SERVICE_S_UCACCIND_ZXGG;
				params = new HashMap<String, String[]>();
				params.put("pname", new String[]{name});
				params.put("idcardNo", new String[]{identifier});
				String zxgg = queryApiBiz.query(service, params);
				zxggObj = JSONObject.parseObject(zxgg);
				JSONArray zxggArray = new JSONArray();
				JSONObject zxggResultObject = zxggObj.getJSONObject("Result");
				if (null != zxggResultObject) {
					JSONArray resultZxggArray = zxggResultObject.getJSONArray("zxggList");
					if (null != resultZxggArray){
						zxggArray = resultZxggArray;
						// 设置涉法涉诉类型
						for (Object o : zxggArray){
							JSONObject object = (JSONObject) o;
							object.put("sfssType", "执行公告");
						}
					}
				}
				returnMap.put("zxgg", zxggArray);
				
				// 涉诉信息(从失信公告中查询某人)
				JSONObject sxggObj = new JSONObject();
				service = Constants.SERVICE_S_UCACCIND_SXGG;
				params = new HashMap<String, String[]>();
				params.put("pname", new String[]{name});
				params.put("idcardNo", new String[]{identifier});
				String sxgg = queryApiBiz.query(service, params);
				sxggObj = JSONObject.parseObject(sxgg);
				JSONArray sxggArray = new JSONArray();
				JSONObject sxggResultObject = sxggObj.getJSONObject("Result");
				if (null != sxggResultObject) {
					JSONArray resultSxggArray = sxggResultObject.getJSONArray("sxggList");
					if (null != resultSxggArray){
						sxggArray = resultSxggArray;
						// 设置涉法涉诉类型
						for (Object o : sxggArray){
							JSONObject object = (JSONObject) o;
							object.put("sfssType", "失信公告");
						}
					}
				}
				returnMap.put("sxgg", sxggArray);
				
				// 涉诉信息(从裁判文书中查询某人)
				JSONObject cpwsObj = new JSONObject();
				service = Constants.SERVICE_S_UCACCIND_CPWS;
				params = new HashMap<String, String[]>();
				params.put("pname", new String[]{name});
				params.put("idcardNo", new String[]{identifier});
				String cpws = queryApiBiz.query(service, params);
				cpwsObj = JSONObject.parseObject(cpws);
				JSONArray cpwsArray = new JSONArray();
				JSONObject cpwsResultObject = cpwsObj.getJSONObject("Result");
				if (null != cpwsResultObject) {
					JSONArray resultCpwsArray = cpwsResultObject.getJSONArray("cpwsList");
					if (null != resultCpwsArray){
						cpwsArray = resultCpwsArray;
						// 设置涉法涉诉类型
						for (Object o : cpwsArray){
							JSONObject object = (JSONObject) o;
							object.put("sfssType", "裁判文书");
						}
					}
				}
				returnMap.put("cpws", cpwsArray);
				continue;
			}
			// 失信被执行人信息
			if (Constants.ITEM_SXR.equals(queryItem)) {
				String sxr = "暂无信息";
				StringBuffer url = new StringBuffer(Constants.URL_LDB_GETSXR);
				url.append(Constants.URL_CONNECTOR);
				url.append("idCardNo");
				url.append(Constants.EQUAL);
				url.append(identifier);
				sxr = remoteApiOperator.remoteAccept(url.toString());
				returnMap.put("sxr", JSONObject.parse(sxr));
				continue;
			}
		}
		
		return returnMap;
	}
}
