package org.pbccrc.api.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class ApiUserDao extends AbstractMyBatisDao{
	
	public List<Map<String, Object>> queryAll() {
		return getSqlSession().selectList("dao.apiUserDao.queryAll");
	}
	
}
