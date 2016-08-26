package org.pbccrc.api.bean;

import java.util.List;

public class DBEntity {
	
	/** 表名 */
	private String tableName;
	
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
	
}
