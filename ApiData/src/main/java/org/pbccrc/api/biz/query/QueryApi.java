package org.pbccrc.api.biz.query;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface QueryApi {

	/**
	 * @param localApi		  本地api	
	 * @param request		  请求request	
	 * @return
	 * @throws Exception
	 */
	public String query(Map<String, Object> localApi, HttpServletRequest request) throws Exception;
}
