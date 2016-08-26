package org.pbccrc.api.biz;

import javax.servlet.http.HttpServletRequest;

public interface QueryApiBiz {
	
	public String query(String service, HttpServletRequest request) throws Exception;

}
