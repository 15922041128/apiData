package org.pbccrc.api.biz;

import java.util.Map;

public interface QueryApiBiz {
	
	@SuppressWarnings("rawtypes")
	public String query(String service, Map urlParams) throws Exception;

}
