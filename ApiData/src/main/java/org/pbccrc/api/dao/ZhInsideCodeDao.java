package org.pbccrc.api.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class ZhInsideCodeDao extends AbstractMyBatisDao{
	
	public Map<String, Object> queryByCode(String identifier){
		return getSqlSession().selectOne("dao.zhInsideCodeDao.queryByIdentifier", identifier);
	}
	
}
