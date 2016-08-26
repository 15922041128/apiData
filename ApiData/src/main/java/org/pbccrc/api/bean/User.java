package org.pbccrc.api.bean;

public class User {

	/** 主键 */
	private Integer ID;
	
	/** 帐号 */
	private String userName;
	
	/** 密码 */
	private String password;
	
	/** 注册机构名称 */
	private String compName;
	
	/** 注册机构电话 */
	private String compTel;
	
	/** 联系人姓名 */
	private String contactName;
	
	/** 联系人电话 */
	private String contactTel;

	public Integer getID() {
		return ID;
	}

	public void setID(Integer iD) {
		ID = iD;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCompName() {
		return compName;
	}

	public void setCompName(String compName) {
		this.compName = compName;
	}

	public String getCompTel() {
		return compTel;
	}

	public void setCompTel(String compTel) {
		this.compTel = compTel;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactTel() {
		return contactTel;
	}

	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}
	
}
