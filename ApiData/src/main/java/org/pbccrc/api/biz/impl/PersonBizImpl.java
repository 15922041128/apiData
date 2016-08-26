package org.pbccrc.api.biz.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Blob;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.pbccrc.api.bean.User;
import org.pbccrc.api.biz.PersonBiz;
import org.pbccrc.api.dao.PBaseInfoDao;
import org.pbccrc.api.dao.PPersonDao;
import org.pbccrc.api.dao.PReditDao;
import org.pbccrc.api.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;

@Service
public class PersonBizImpl implements PersonBiz {
	
	@Autowired
	private PPersonDao pPersonDao;
	
	@Autowired
	private PBaseInfoDao pBaseInfoDao;
	
	@Autowired
	private PReditDao pReditDao;

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
		if(pBaseInfoDao.isExist(personID) > 0) {
			// 当前数据在pBaseInfo中已存在,update
			pBaseInfo.put("updateUser", currentUser);
			pBaseInfo.put("updateTime", format.format(new Date()));
			pBaseInfoDao.updatePerson(pBaseInfo);
		} else {
			// 当前数据在pBaseInfo中不存在,insert
			pBaseInfo.put("createUser", currentUser);
			pBaseInfo.put("createTime", format.format(new Date()));
			pBaseInfoDao.addPerson(pBaseInfo);
		}
		
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
	public JSONArray getReditList(String name, String idCardNo) throws Exception{
		
		JSONArray array = new JSONArray();
		
		Map<String, Object> pRedit = new HashMap<String, Object>();
		pRedit.put("name", name);
		pRedit.put("idCardNo",idCardNo);
		
		List<Map<String, Object>> reditList = pReditDao.queryAll(pRedit);
		
		for (int i = 0 ; i < reditList.size(); i++) {
			
			Map<String, Object> map = reditList.get(i);
			
			array.add(map);
			
//			Blob blob = (Blob) map.get("pbi_photo");
//			InputStream inputStream = blob.getBinaryStream();
//			int length = (int) blob.length();
//			byte[] b = new byte[length];
//			inputStream.read(b, 0, length);
//			PrintWriter out = response.getWriter();
//			InputStream is = new ByteArrayInputStream(b);
//			int a = is.read();
//			while (a != -1) {
//				out.print((char) a);
//				a = is.read();
//			}
//			out.flush();
//			out.close();

			
			
//			System.out.println(map.get("pp_ID"));
//			System.out.println(map.get("pp_idCardNo"));
//			System.out.println(map.get("pp_name"));
//			
//			System.out.println(map.get("pbi_ID"));
//			System.out.println(map.get("pbi_personID"));
//			System.out.println(map.get("pbi_photo"));
//			System.out.println(map.get("pbi_tel"));
//			System.out.println(map.get("pbi_address"));
//			System.out.println(map.get("pbi_createUser"));
//			System.out.println(map.get("pbi_createTime"));
//			System.out.println(map.get("pbi_updateUser"));
//			System.out.println(map.get("pbi_updateTime"));
//			
//			System.out.println(map.get("pr_ID"));
//			System.out.println(map.get("pr_personID"));
//			System.out.println(map.get("pr_contactDate"));
//			System.out.println(map.get("pr_loanDate"));
//			System.out.println(map.get("pr_hireDate"));
//			System.out.println(map.get("pr_expireDate"));
//			System.out.println(map.get("pr_loanUsed"));
//			System.out.println(map.get("pr_totalAmount"));
//			System.out.println(map.get("pr_balance"));
//			System.out.println(map.get("pr_status"));
//			System.out.println(map.get("pr_bizOccurOrg"));
//			System.out.println(map.get("pr_createUser"));
//			System.out.println(map.get("pr_createTime"));
//			System.out.println(map.get("pr_updateUser"));
//			System.out.println(map.get("pr_updateTime"));
		}
		
		return array;
	}

}
