package org.pbccrc.api.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class PBaseInfoDao extends AbstractMyBatisDao{
	
	public Integer isExist(String personID){
		return getSqlSession().selectOne("dao.pBaseInfoDao.isExist", personID);
	}
	
	public Integer addPerson(Map<String, Object> pBaseInfo){
		return getSqlSession().update("dao.pBaseInfoDao.addPBaseInfo", pBaseInfo);
	}
	
	public Integer updatePerson(Map<String, Object> pBaseInfo){
		return getSqlSession().update("dao.pBaseInfoDao.updatePBaseInfo", pBaseInfo);
	}
	
	public List<Map<String, Object>> queryByPersonID(String personID) {
		return getSqlSession().selectList("dao.pBaseInfoDao.queryByPersonID", personID);
	}
	
}
