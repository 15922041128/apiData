package org.pbccrc.api.biz;

import java.io.File;

import org.pbccrc.api.bean.User;

import com.alibaba.fastjson.JSONObject;

public interface PersonBiz {
	
	/**
	 * @param name
	 * @param idCardNo
	 * @param tel
	 * @param address
	 * @param photo
	 * @param currentUser
	 * @return
	 */
	public String addPerson(String name, String idCardNo, String tel, String address, File photo, String currentUser);
	
	/**
	 * @param personID 			personID
	 * @param contactDate 		合同日期
	 * @param hireDate			起租日
	 * @param expireDate		到期日
	 * @param type				业务类型
	 * @param loanUsed			用途
	 * @param totalAmount		总金额
	 * @param balance			余额
	 * @param status			状态
	 * @param user				当前用户
	 * @return
	 */
	public boolean addPersonRedit(String personID, String contactDate, String hireDate, String expireDate, 
			String type, String loanUsed, String totalAmount, String balance, String status, User user);
	
	/**
	 * @param name 				姓名
	 * @param idCardNo			身份证号
	 * @return
	 */
	public JSONObject getReditList(String name, String idCardNo) throws Exception;
	
}
