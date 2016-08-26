package org.pbccrc.api.biz;

import java.io.File;

import org.pbccrc.api.bean.User;

import com.alibaba.fastjson.JSONArray;

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
			String loanUsed, String totalAmount, String balance, String status, User user);
	
	/**
	 * @param name 				姓名
	 * @param idCardNo			身份证号
	 * @return
	 */
	public JSONArray getReditList(String name, String idCardNo) throws Exception;
}
