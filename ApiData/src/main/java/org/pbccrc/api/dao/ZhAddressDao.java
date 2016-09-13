package org.pbccrc.api.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class ZhAddressDao extends AbstractMyBatisDao{
	
	public Map<String, Object> query(String insideCode){
		return getSqlSession().selectOne("dao.zhAddressDao.query", insideCode);
	}
	
}
