package org.pbccrc.api.biz.impl;

import org.pbccrc.api.bean.User;
import org.pbccrc.api.biz.UserBiz;
import org.pbccrc.api.dao.UserDao;
import org.pbccrc.api.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserBizImpl implements UserBiz{
	
	@Autowired
	private UserDao userDao;

	/**
	 * @param userName 	帐号
	 * @return			是否存在
	 */
	public boolean isExist(String userName) {
		return userDao.isExist(userName) > 0;
	}

	/**
	 * @param user 	用户信息
	 */
	public void addUser(User user) {
		
		userDao.addUser(user);
	}

	/**
	 * @param userName	帐号
	 * @param password	密码
	 * @return			是否成功
	 */
	public User login(String userName, String password) {
		
		User user = new User();
		user.setUserName(userName);
		user.setPassword(StringUtil.string2MD5(password));
		
		return userDao.login(user);
	}
	
	/**
	 * 
	 * @param userID    用户ID
	 * @param password  用户新密码
	 */
	public void resetPassword(int userID, String password) {
		User user = new User();
		user.setID(userID);
		user.setPassword(StringUtil.string2MD5(password));
		userDao.updateUser(user);
	}
	
	/**
	 * @param user 	用户信息
	 */
	public void modifyUser(User user) {
		userDao.updateUser(user);
	}
}
