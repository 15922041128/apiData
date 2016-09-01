package org.pbccrc.api.biz;

public interface LocalDBBiz {
	
	/***
	 * 根据身份证和姓名查询信贷信息
	 * @param name			姓名
	 * @param idCardNo		身份证号
	 * @return
	 * @throws Exception
	 */
	public String query(String name, String idCardNo) throws Exception;

}
