package org.pbccrc.api.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class PReditDao extends AbstractMyBatisDao{
	
	public Integer addPRedit(Map<String, Object> pRedit){
		return getSqlSession().update("dao.pReditDao.addPRedit", pRedit);
	}
	
	public List<Map<String, Object>> queryAll(Map<String, Object> pRedit) {
		return getSqlSession().selectList("dao.pReditDao.queryAll", pRedit);
	}
	
	
}
