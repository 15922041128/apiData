package org.pbccrc.api.dao;

import java.util.List;

import org.pbccrc.api.bean.SystemLog;
import org.springframework.stereotype.Repository;

@Repository
public class SystemLogDao extends AbstractMyBatisDao{
	
	public List<SystemLog> queryLog(SystemLog systemLog) {
		return getSqlSession().selectList("dao.systemLogDao.queryLog", systemLog);
	}
	
	public void addLog(SystemLog systemLog) {
		getSqlSession().update("dao.systemLogDao.addLog", systemLog);
	}
	
}
