package org.pbccrc.api.biz;

import org.pbccrc.api.bean.User;

public interface UserBiz {
	
	/**
	 * @param userName 	帐号
	 * @return			是否存在
	 */
	public boolean isExist(String userName);
	
	/**
	 * @param user 	用户信息
	 */
	public void addUser(User user);
	
	/**
	 * @param userName	帐号
	 * @param password	密码
	 * @return			是否成功
	 */
	public User login(String userName, String password);

}
