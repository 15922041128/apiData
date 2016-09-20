package org.pbccrc.api.biz;

import java.util.Map;

public interface ComplexBiz {
	
	/**
	 * 失信人查询
	 * @param identifier
	 * @return
	 */
	public Map<String, Object> querySxr(String identifier, String[] queryItems) throws Exception;
	
	/**
	 * 可授信额度查询
	 * @param identifier
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> queryQuota(String identifier) throws Exception;

}
