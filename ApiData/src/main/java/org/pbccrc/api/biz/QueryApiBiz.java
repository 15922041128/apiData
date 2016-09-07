package org.pbccrc.api.biz;

import java.util.Map;

public interface QueryApiBiz {
	
	@SuppressWarnings("rawtypes")
	public String query(String service, Map<String, Object> localApi, Map urlParams) throws Exception;

}
