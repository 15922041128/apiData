package org.pbccrc.api.biz.impl;

import java.util.HashMap;
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
	public Map<String, Object> querySxr(String identifier) throws Exception{
		
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		// 根据身份证号获取内码信息
		Map<String, Object> insideCodeMap = zhInsideCodeDao.queryByCode(identifier);
		// 内码
		String insideCode = String.valueOf(insideCodeMap.get("insideCode"));
		// 姓名
		String name = String.valueOf(insideCodeMap.get("name"));
		returnMap.put("name", name);
		returnMap.put("idCardNo", identifier);
		
		// 根据内码获取基本信息
		Map<String, Object> personMap = zhPersonDao.query(insideCode);
		returnMap.put("person", personMap);
		
		// 根据内码获取居住信息
		Map<String, Object> addressMap = zhAddressDao.query(insideCode);
		returnMap.put("address", addressMap);
		
		// 根据内码获取就业信息
		Map<String, Object> employmentMap = zhEmploymentDao.query(insideCode);
		returnMap.put("employment", employmentMap);
		
		// 根据内码获取担保信息
		Map<String, Object> loanMap = zhLoanDao.query(insideCode);
		returnMap.put("loan", loanMap);
		
		// 根据内码获取信用卡信息
		Map<String, Object> creditCardMap = zhCreditCardDao.query(insideCode);
		returnMap.put("creditCard", creditCardMap);
		
		// 根据内码获取担保信息
		Map<String, Object> guaranteeMap = zhGuaranteeDao.query(insideCode);
		returnMap.put("guarantee", guaranteeMap);
		
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
		
		// 获取公积金信息
		String gjj = "暂无信息";
		service = Constants.SERVICE_S_QGJJD;
		params = new HashMap<String, String[]>();
		params.put("NAME", new String[]{name});
		params.put("CERTNO", new String[]{identifier});
		gjj = queryApiBiz.query(service, params);
		returnMap.put("gjj", JSONObject.parse(gjj));
		
		// 获取涉诉信息(从执行公告中查询某人)
		String zxgg = "暂无信息";
		service = Constants.SERVICE_S_UCACCIND_ZXGG;
		params = new HashMap<String, String[]>();
		params.put("pname", new String[]{name});
		params.put("idcardNo", new String[]{identifier});
		zxgg = queryApiBiz.query(service, params);
		returnMap.put("zxgg", JSONObject.parse(zxgg));
		
		// 获取涉诉信息(从失信公告中查询某人)
		String sxgg = "暂无信息";
		service = Constants.SERVICE_S_UCACCIND_SXGG;
		params = new HashMap<String, String[]>();
		params.put("pname", new String[]{name});
		params.put("idcardNo", new String[]{identifier});
		sxgg = queryApiBiz.query(service, params);
		returnMap.put("sxgg", JSONObject.parse(sxgg));
		
		// 获取涉诉信息(从裁判文书中查询某人)
		String cpws = "暂无信息";
		service = Constants.SERVICE_S_UCACCIND_CPWS;
		params = new HashMap<String, String[]>();
		params.put("pname", new String[]{name});
		params.put("idcardNo", new String[]{identifier});
		cpws = queryApiBiz.query(service, params);
		returnMap.put("cpws", JSONObject.parse(cpws));
		
		// 获取失信被执行人信息
		String sxr = "暂无信息";
		StringBuffer url = new StringBuffer(Constants.URL_LDB_GETSXR);
		url.append(Constants.URL_CONNECTOR);
		url.append("idCardNo");
		url.append(Constants.EQUAL);
		url.append(identifier);
		sxr = remoteApiOperator.remoteAccept(url.toString());
		returnMap.put("sxr", JSONObject.parse(sxr));
		
		return returnMap;
	}
	

}
