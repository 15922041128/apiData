package org.pbccrc.api.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class ApiConfigDao extends AbstractMyBatisDao{
	
	public Map<String, Object> queryConfig(int localID, int remoteID){
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("localID", localID);
		param.put("remoteID", remoteID);
		
		return getSqlSession().selectOne("dao.apiConfigDao.queryConfig", param);
	}

}
