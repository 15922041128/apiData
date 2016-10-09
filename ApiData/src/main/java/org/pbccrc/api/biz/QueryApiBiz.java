package org.pbccrc.api.biz;

import java.util.Map;

public interface QueryApiBiz {
	
	@SuppressWarnings("rawtypes")
	public Map<String, Object> query(String service, Map urlParams) throws Exception;

}
