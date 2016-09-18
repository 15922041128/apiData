package org.pbccrc.api.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class ZhGuaranteeDao extends AbstractMyBatisDao{
	
	public List<Map<String, Object>> query(String insideCode){
		return getSqlSession().selectList("dao.zhGuaranteeDao.query", insideCode);
	}
	
}
