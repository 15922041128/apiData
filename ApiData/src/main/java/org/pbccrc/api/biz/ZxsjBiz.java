package org.pbccrc.api.biz;

import java.util.Map;

import org.pbccrc.api.bean.ResultContent;

public interface ZxsjBiz {
	
	/**
	 * @param type				查询类型
	 * @param appKey			appKey
	 * @param urlParams			url参数
	 * @param resultContent		查询返回对象
	 * @return					查询结果
	 */
	public String queryZxsj(String type, String appKey, Map urlParams, ResultContent resultContent); 

}
