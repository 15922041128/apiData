package org.pbccrc.api.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class RemoteApiDao extends AbstractMyBatisDao{
	
	public List<Map<String, Object>> getRemoteApiByLocal(int localApiID){
		return getSqlSession().selectList("dao.remoteApiDao.getRemoteApiByLocal", localApiID);
	}
	
	public int updateCnt(int id, int count){
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("ID", id);
		param.put("count", count);
		
		return getSqlSession().update("dao.remoteApiDao.updateCnt", param);
	}
	
	public Map<String, Object> getKey(){
		
		return getSqlSession().selectOne("dao.remoteApiDao.getKey");
	}

}
