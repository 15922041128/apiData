package org.pbccrc.api.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class ZhCreditCardDao extends AbstractMyBatisDao{
	
	public Map<String, Object> query(String insideCode){
		return getSqlSession().selectOne("dao.zhCreditCardDao.query", insideCode);
	}
	
}
