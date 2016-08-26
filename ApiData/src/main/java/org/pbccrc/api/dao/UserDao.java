package org.pbccrc.api.dao;

import org.pbccrc.api.bean.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao extends AbstractMyBatisDao{
	
	public Integer isExist(String userName){
		return getSqlSession().selectOne("dao.userDao.isExist", userName);
	}
	
	public void addUser(User user){
		getSqlSession().update("dao.userDao.addUser", user);
	}
	
	public User login(User user){
		return getSqlSession().selectOne("dao.userDao.login", user);
	}
}
