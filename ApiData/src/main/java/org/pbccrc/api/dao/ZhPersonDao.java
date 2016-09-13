package org.pbccrc.api.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class ZhPersonDao extends AbstractMyBatisDao{
	
	public Map<String, Object> query(String insideCode){
		return getSqlSession().selectOne("dao.zhPersonDao.query", insideCode);
	}
	
}
