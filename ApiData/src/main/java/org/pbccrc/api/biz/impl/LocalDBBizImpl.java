package org.pbccrc.api.biz.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.pbccrc.api.bean.DBEntity;
import org.pbccrc.api.biz.LocalDBBiz;
import org.pbccrc.api.dao.DBOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

@Service
public class LocalDBBizImpl implements LocalDBBiz {
	
	@Autowired
	private DBOperator dbOperator;
	
	/***
	 * 根据身份证和姓名查询信贷信息
	 * @param name			姓名
	 * @param idCardNo		身份证号
	 * @return
	 * @throws Exception
	 */
	public String query(String name, String idCardNo) throws Exception {
		String tableName = "ldb_isn";
		DBEntity entity = new DBEntity();
		entity.setTableName(tableName);
		List<String> fields = new ArrayList<String>();
		fields.add("name");
		fields.add("idCardNo");
		List<String> values = new ArrayList<String>();
		values.add(name);
		values.add(idCardNo);
		entity.setFields(fields);
		entity.setValues(values);
		Map<String, Object> user = dbOperator.queryData(entity);
		String userID = String.valueOf(user.get("id"));
		
		fields = new ArrayList<String>();
		fields.add("user_id");
		values = new ArrayList<String>();
		values.add(userID);
		entity.setFields(fields);
		entity.setValues(values);
		
		tableName = "ldb_locan";
		entity.setTableName(tableName);
		List<Map<String, Object>> locanList = dbOperator.queryDatas(entity);
		
		tableName = "ldb_guarantee";
		entity.setTableName(tableName);
		List<Map<String, Object>> geeList = dbOperator.queryDatas(entity);
		
		tableName = "ldb_credit_card";
		entity.setTableName(tableName);
		Map<String, Object> creditInfo = dbOperator.queryData(entity);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("LocanInfo", locanList);
		map.put("GuaranteeInfo", geeList);
		map.put("Credit_Card", creditInfo);
		String total = JSON.toJSONString(map);
		return total;
	}
	

}
