package org.pbccrc.api.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class LdbApiDao extends AbstractMyBatisDao{
	
	public Map<String, Object> queryByService(String service){
		return getSqlSession().selectOne("dao.ldbApiDao.queryByService", service);
	}
	
}
