package org.pbccrc.api.biz;

import java.util.Map;

public interface CostBiz {
	
	/**
	 * @param costType  计费类型
	 * @param localApi  localApi
	 */
	public void cost(String costType, String userID, String apiKey, Map<String, Object> localApi);

}
