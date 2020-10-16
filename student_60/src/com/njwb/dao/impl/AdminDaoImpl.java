package com.njwb.dao.impl;

import java.sql.SQLException;

import com.njwb.bean.Admin;
import com.njwb.dao.AdminDao;
import com.njwb.jdbc.JdbcTemplate;

public class AdminDaoImpl implements AdminDao{

	@Override
	public Admin queryAdminByAdminName(String adminName) throws SQLException {
		String sql="select * from t_admin where admin_name=?";
		Admin admin=JdbcTemplate.selectOne(sql, Admin.class,adminName);
		return admin;
	}
	

}
