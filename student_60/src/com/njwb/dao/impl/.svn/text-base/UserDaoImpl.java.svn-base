package com.njwb.dao.impl;

import java.sql.SQLException;

import com.njwb.bean.User;
import com.njwb.dao.UserDao;
import com.njwb.jdbc.JdbcTemplate;
import com.njwb.jdbc.JdbcUtil;
import com.njwb.util.DateUtil;

public class UserDaoImpl implements UserDao{
	
	
	

	@Override
	public void addUser(User user) throws SQLException {
		String sql="insert into " +
				"t_user(user_name,password,id_card_no," +
				"real_name,sex,phone,t_create_time) " +
				"values(?,?,?,?,?,?,?)";
		JdbcTemplate.insert(sql, user.getUserName(),
				user.getPassword(),user.getIdCardNo(),user.getRealName(),
				user.getSex(),user.getPhone(),
				DateUtil.dateToTimeStamp(user.getCreateTime()));
		
	}

	@Override
	public User queryUserByUserName(String userName) throws SQLException {
		String sql="select * from t_user where user_name=?";
		User user=JdbcTemplate.selectOne(sql, User.class, userName);
		return user;
	}


	
	
	@Override
	public User queryUserByIdCardNo(String idCardNo) throws SQLException {
		String sql="select * from t_user where id_card_no=?";
		User user=JdbcTemplate.selectOne(sql, User.class, idCardNo);
		return user;
	}

	
	@Override
	public void updateUser(String userName,String sex,String password,
			String phone) throws SQLException {
		String sql="update t_user set sex=?,password=?,phone=?" +
				" where user_name=?";
		JdbcTemplate.update(sql, sex,password,phone,userName);
		
	}
	
	

}
