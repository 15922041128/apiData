package org.pbccrc.api.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class CodeDao extends AbstractMyBatisDao{
	
	public Map<String, Object> queryByCode(String codeName){
		return getSqlSession().selectOne("dao.codeDao.queryByCode", codeName);
	}
	
}
