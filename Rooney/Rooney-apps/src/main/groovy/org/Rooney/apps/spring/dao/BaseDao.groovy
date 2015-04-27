package org.Rooney.apps.spring.dao

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;

class BaseDao {
	@Autowired
	@Qualifier("jdbcTemlate")
	JdbcTemplate template
	
	
	boolean exeSql(String sql){
		return template.update(sql)>0?true:false;
	}
}
