package org.pbccrc.api.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class PPersonDao extends AbstractMyBatisDao{
	
	public Integer isExist(Map<String, String> person){
		return getSqlSession().selectOne("dao.pPersonDao.isExist", person);
	}
	
	public Integer addPerson(Map<String, String> person){
		return getSqlSession().update("dao.pPersonDao.addPerson", person);
	}
	
	public Map<String, Object> selectOne(Map<String, String> person){
		return getSqlSession().selectOne("dao.pPersonDao.selectOne", person);
	}
}
