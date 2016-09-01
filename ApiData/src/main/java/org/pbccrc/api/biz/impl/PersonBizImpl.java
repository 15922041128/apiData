package org.pbccrc.api.biz.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.pbccrc.api.bean.DBEntity;
import org.pbccrc.api.bean.User;
import org.pbccrc.api.biz.PersonBiz;
import org.pbccrc.api.dao.DBOperator;
import org.pbccrc.api.dao.PBaseInfoDao;
import org.pbccrc.api.dao.PPersonDao;
import org.pbccrc.api.dao.PReditDao;
import org.pbccrc.api.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Service
public class PersonBizImpl implements PersonBiz {
	
	@Autowired
	private PPersonDao pPersonDao;
	
	@Autowired
	private PBaseInfoDao pBaseInfoDao;
	
	@Autowired
	private PReditDao pReditDao;
	
	@Autowired
	private DBOperator dbOperator;

	/**
	 * @param name          姓名
	 * @param idCardNo		身份证号
	 * @param tel			电话
	 * @param address		地址
	 * @param photo			照片
	 * @param currentUser	当前用户
	 * @return
	 */
	public String addPerson(String name, String idCardNo, String tel, String address, File photo, String currentUser) {
		
		Map<String, String> person = new HashMap<String, String>();
		person.put("name", name);
		person.put("idCardNo", idCardNo);
		
		String personID;
		
		/** p_person表操作 */
		if(pPersonDao.isExist(person) > 0) {
			// 当前person已存在,查询出personID
			Map<String, Object> resPerson = pPersonDao.selectOne(person);
			personID = String.valueOf(resPerson.get(Constants.PERSON_ID));
		} else {
			// 当前person不存在,插入person表数据并返回personID
			pPersonDao.addPerson(person);
			personID = String.valueOf(person.get(Constants.PERSON_ID));
		}
		
		Map<String, Object> pBaseInfo = new HashMap<String, Object>();
		pBaseInfo.put("personID", personID);
		pBaseInfo.put("tel", tel);
		pBaseInfo.put("address", address);
		pBaseInfo.put("photo", photo.getPath());
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		/** p_baseInfo表操作 */
//		if(pBaseInfoDao.isExist(personID) > 0) {
//			// 当前数据在pBaseInfo中已存在,update
//			pBaseInfo.put("updateUser", currentUser);
//			pBaseInfo.put("updateTime", format.format(new Date()));
//			pBaseInfoDao.updatePerson(pBaseInfo);
//		} else {
//			// 当前数据在pBaseInfo中不存在,insert
//			pBaseInfo.put("createUser", currentUser);
//			pBaseInfo.put("createTime", format.format(new Date()));
//			pBaseInfoDao.addPerson(pBaseInfo);
//		}
		// 即使存在相同数据,依然保留
		pBaseInfo.put("createUser", currentUser);
		pBaseInfo.put("createTime", format.format(new Date()));
		pBaseInfo.put("updateUser", currentUser);
		pBaseInfo.put("updateTime", format.format(new Date()));
		pBaseInfoDao.addPerson(pBaseInfo);
		
		return personID;
		
	}
	
	/**
	 * @param personID 			personID
	 * @param contactDate 		合同日期
	 * @param loanDate			放款日期
	 * @param hireDate			起租日
	 * @param expireDate		到期日
	 * @param loanUsed			贷款用途
	 * @param totalAmount		总金额
	 * @param balance			余额
	 * @param status			状态
	 * @param user				当前用户
	 * @return
	 */
	public boolean addPersonRedit(String personID, String contactDate, String loanDate, String hireDate, String expireDate, 
			String loanUsed, String totalAmount, String balance, String status, User user) {
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Map<String, Object> pRedit = new HashMap<String, Object>();
		pRedit.put("personID", personID);
		pRedit.put("contactDate",contactDate);
		pRedit.put("loanDate", loanDate);
		pRedit.put("hireDate", hireDate);
		pRedit.put("expireDate", expireDate);
		pRedit.put("loanUsed", loanUsed);
		pRedit.put("totalAmount", totalAmount);
		pRedit.put("balance", balance);
		pRedit.put("status", status);
		pRedit.put("bizOccurOrg", user.getCompName());
		pRedit.put("createUser", user.getUserName());
		pRedit.put("createTime", format.format(new Date()));
		
		return pReditDao.addPRedit(pRedit) > 0;
	}
	
	/**
	 * @param name 				姓名
	 * @param idCardNo			身份证号
	 * @return
	 */
	public JSONObject getReditList(String name, String idCardNo) throws Exception{
		
		JSONObject returnObj = new JSONObject();
		
		// 获取用户ID
		Map<String, String> person = new HashMap<String, String>();
		person.put("name", name);
		person.put("idCardNo", idCardNo);
		Map<String, Object> resPerson = pPersonDao.selectOne(person);
		if (null == resPerson) {
			returnObj.put("status", "error");
			return returnObj;
		}
		String personID = String.valueOf(resPerson.get(Constants.PERSON_ID));
		returnObj.put("person", person);
		
		// 获取用户基本信息
		List<Map<String, Object>> pBaseInfoList = pBaseInfoDao.queryByPersonID(personID);
		if (null == pBaseInfoList) {
			returnObj.put("pBaseInfo", null);
		} else {
			returnObj.put("pBaseInfo", pBaseInfoList.get(0));
		}
		
		// 获取用户信贷信息
		List<Map<String, Object>> reditList = pReditDao.queryAll(personID);
		JSONArray array = new JSONArray();
		for (int i = 0 ; i < reditList.size(); i++) {
			Map<String, Object> map = reditList.get(i);
			array.add(map);
		}
		returnObj.put("reditList", array);
		
		// 被失信人被执行信息
		DBEntity entity = new DBEntity();
		entity.setTableName("ldb_dishonest_info");
		List<String> fields = new ArrayList<String>();
		fields.add("INAME");
		fields.add("CARDNUM");
		List<String> values = new ArrayList<String>();
		values.add(name);
		values.add(idCardNo);
		entity.setFields(fields);
		entity.setValues(values);
		List<Map<String, Object>> dishonestList = dbOperator.queryDatas(entity);
		array = new JSONArray();
		for (Map<String, Object> map : dishonestList) {
			array.add(map);
		}
		
		returnObj.put("dishonestList", array);
		
		return returnObj;
	}
	
}
