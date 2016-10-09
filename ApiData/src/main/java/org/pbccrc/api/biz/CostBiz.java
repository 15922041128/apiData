package org.pbccrc.api.biz;

import java.util.Map;

public interface CostBiz {
	
	/**
	 * 计费
	 * @param userID
	 * @param apiKey
	 * @param localApi
	 */
	public void cost(String userID, String apiKey, Map<String, Object> localApi);

}
