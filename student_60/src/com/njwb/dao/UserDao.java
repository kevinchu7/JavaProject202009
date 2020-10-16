package com.njwb.dao;

import java.sql.SQLException;

import com.njwb.bean.User;

public interface UserDao {
	public void addUser(User user) throws SQLException;
	
	
	public User queryUserByUserName(String userName) throws SQLException ;
	
	public User queryUserByIdCardNo(String idCardNo) throws SQLException;
	
	public void updateUser(String userName,String sex,String password,
			String phone) throws SQLException;
	
}
