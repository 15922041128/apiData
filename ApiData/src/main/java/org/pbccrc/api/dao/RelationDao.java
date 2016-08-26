package org.pbccrc.api.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class RelationDao extends AbstractMyBatisDao {

	public Map<String, Object> query(String userID, String apiKey, int localApiID) {

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userID", userID);
		param.put("apiKey", apiKey);
		param.put("localApiID", localApiID);

		return getSqlSession().selectOne("dao.relationDao.queryRelation", param);
	}

}
