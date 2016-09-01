package org.pbccrc.api.bean;

import java.util.List;
import java.util.Map;

public class DBEntity {
	
	/** 表名 */
	private String tableName;
	
	/** 返回字段 */
	private Map<String, String> returnFidlds;
	
	/** 字段 */
	private List<String> fields;
	
	/** 值 */
	private List<String> values;
	
	/** sql语句 */
	private String sql;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public List<String> getFields() {
		return fields;
	}

	public void setFields(List<String> fields) {
		this.fields = fields;
	}

	public List<String> getValues() {
		return values;
	}

	public void setValues(List<String> values) {
		this.values = values;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}
	
	public Map<String, String> getReturnFidlds() {
		return returnFidlds;
	}

	public void setReturnFidlds(Map<String, String> returnFidlds) {
		this.returnFidlds = returnFidlds;
	}
	
}
