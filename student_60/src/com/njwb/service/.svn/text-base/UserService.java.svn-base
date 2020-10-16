package com.njwb.service;

import com.njwb.bean.User;
import com.njwb.exception.FlightTicketException;

public interface UserService {
	/**
	 * 用户登陆
	 * @param userName
	 * @param password
	 * @throws FlightTicketException
	 */
	public void userLogin(String userName, String password) throws FlightTicketException;

	/**
	 * 用户注册
	 * @param user
	 * @throws FlightTicketException
	 */
	public void userRegister(User user) throws FlightTicketException;

	/**
	 * 通过userName获取用户信息
	 * @param userName
	 * @return
	 * @throws FlightTicketException
	 */
	public User getUserInfo(String userName) throws FlightTicketException;

	/**
	 * 修改用户信息
	 * @param userName
	 * @param sex
	 * @param password
	 * @param phone
	 * @throws FlightTicketException
	 */
	public void userChangeInfo(String userName,String sex,String password,
			String phone) throws FlightTicketException;

	
}
