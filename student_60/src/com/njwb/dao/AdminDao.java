package com.njwb.dao;

import java.sql.SQLException;

import com.njwb.bean.Admin;


public interface AdminDao {
	/**
	 * 通过adminName查找管理员
	 * @param adminName
	 * @return
	 * @throws SQLException
	 */
	public Admin queryAdminByAdminName(String adminName) throws SQLException ;

}
