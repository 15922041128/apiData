package org.pbccrc.api.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class ZhLoanDao extends AbstractMyBatisDao{
	
	public List<Map<String, Object>> query(String insideCode){
		return getSqlSession().selectList("dao.zhLoanDao.query", insideCode);
	}
	
}
