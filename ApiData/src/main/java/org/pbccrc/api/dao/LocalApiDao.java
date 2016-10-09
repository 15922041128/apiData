package org.pbccrc.api.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class LocalApiDao extends AbstractMyBatisDao{
	
	public Map<String, Object> queryByService(String service){
		return getSqlSession().selectOne("dao.localApiDao.queryByService", service);
	}
	
	public List<Map<String, Object>> queryAll() {
		return getSqlSession().selectList("dao.localApiDao.queryAll");
	}
	
}
