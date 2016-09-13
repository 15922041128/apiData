package org.pbccrc.api.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class ZhEmploymentDao extends AbstractMyBatisDao{
	
	public Map<String, Object> query(String insideCode){
		return getSqlSession().selectOne("dao.zhEmploymentDao.query", insideCode);
	}
	
}
