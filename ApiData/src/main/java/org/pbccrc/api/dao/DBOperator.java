package org.pbccrc.api.dao;

import java.util.List;
import java.util.Map;

import org.pbccrc.api.bean.DBEntity;
import org.pbccrc.api.util.Constants;
import org.springframework.stereotype.Service;

@Service("dbOperator")
public class DBOperator extends AbstractMyBatisDao{
	
	public int insertData(DBEntity entity) {
		return this.getSqlSession().insert("dao.dbOperator.insertData", entity);
	}
	
	public Map<String, Object> queryData(DBEntity entity) {
		
		StringBuffer sql = new StringBuffer();
		
		List<String> fields = entity.getFields();
		List<String> values = entity.getValues();
		
		for (int i = 0; i < fields.size(); i++) {
			sql.append(Constants.AND);
			sql.append("t." + fields.get(i));
			sql.append(Constants.EQUAL);
			sql.append(Constants.SINGLE_QUOTES + values.get(i) + Constants.SINGLE_QUOTES);
			sql.append(Constants.SPACE);
		}
		
		entity.setSql(sql.toString());
		
		return getSqlSession().selectOne("dao.dbOperator.queryData", entity);
	}
	
	public List<Map<String, Object>> queryDatas(DBEntity entity) {
		
		StringBuffer sql = new StringBuffer();
		
		List<String> fields = entity.getFields();
		List<String> values = entity.getValues();
		
		for (int i = 0; i < fields.size(); i++) {
			sql.append(Constants.AND);
			sql.append("t." + fields.get(i));
			sql.append(Constants.EQUAL);
			sql.append(Constants.SINGLE_QUOTES + values.get(i) + Constants.SINGLE_QUOTES);
			sql.append(Constants.SPACE);
		}
		
		entity.setSql(sql.toString());
		
		return getSqlSession().selectList("dao.dbOperator.queryData", entity);
	}
	
	public void updateData(DBEntity entity) {
		getSqlSession().update("dao.dbOperator.updateData", entity);
	}

}
