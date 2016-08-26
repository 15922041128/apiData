package org.pbccrc.api.biz;

import java.util.Map;

public interface TempBiz {
	
	public String query(String service, Map urlParams);
	
	public String queryTemp(String service, Map urlParams) throws Exception;

}
