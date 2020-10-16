package com.njwb.service;

import com.njwb.bean.Admin;
import com.njwb.exception.FlightTicketException;

public interface AdminService {
	/**
	 * 管理员登陆
	 * @param adminName
	 * @param password
	 * @throws FlightTicketException
	 */
	public void adminLogin(String adminName,String password) throws FlightTicketException;

	/**
	 * 通过adminName获取管理员信息
	 * @param adminName
	 * @return
	 * @throws FlightTicketException
	 */
	public Admin getAdminInfo(String adminName) throws FlightTicketException;
	
	
}
