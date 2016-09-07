package org.pbccrc.api.biz.impl;

import java.util.Map;

import org.pbccrc.api.biz.CostBiz;
import org.pbccrc.api.dao.RelationDao;
import org.pbccrc.api.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CostBizImpl implements CostBiz {
	
	@Autowired
	private RelationDao relationDao;

	/**
	 * @param costType  计费类型
	 * @param localApi  localApi
	 */
	public void cost(String costType, String userID, String apiKey, Map<String, Object> localApi) {
		
		// 判断计费类型
		if (Constants.COST_TYPE_COUNT.equals(costType)) {
			// 按次数计费
			Map<String, Object> relation = relationDao.query(userID, apiKey, Integer.parseInt(String.valueOf(localApi.get("ID"))));
			int count = Integer.parseInt(String.valueOf(relation.get("count")));
			// count为-1免费查询
			if (-1 != count) {
				String relationID = String.valueOf(relation.get("ID"));
				relationDao.updateCount(relationID);
			}
		} else if (Constants.COST_TYPE_PRICE.equals(costType)) {
			// 按金额计费
		} else {
			// to be extended
		}
		
	}
}
