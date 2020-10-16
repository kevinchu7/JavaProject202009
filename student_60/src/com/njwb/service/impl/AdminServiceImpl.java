package com.njwb.service.impl;

import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.njwb.bean.Admin;
import com.njwb.dao.AdminDao;
import com.njwb.exception.FlightTicketException;
import com.njwb.factory.annotation.FactoryAnnotation;
import com.njwb.service.AdminService;

public class AdminServiceImpl implements AdminService{
	private Logger log = Logger.getLogger(this.getClass());
	
	@FactoryAnnotation("adminDao")
	private AdminDao adminDao;
	

	public void setAdminDao(AdminDao adminDao) {
		this.adminDao = adminDao;
	}


	@Override
	public void adminLogin(String adminName, String password) throws FlightTicketException {
		try {
			Admin admin=adminDao.queryAdminByAdminName(adminName);
			if(admin==null){
				log.info("登陆失败,管理员用户名不存在！");
				throw new FlightTicketException("登陆失败，该管理员用户名不存在！");
			}else if(password.equals(admin.getPassword())==false){
				log.info("登陆失败,密码错误！");
				throw new FlightTicketException("登陆失败,密码错误！");
			}
			
		} catch (SQLException e) {
			log.error("登陆失败,数据库异常！ adminName:"+adminName,e);
			throw new FlightTicketException("登陆失败,数据库异常！");
//			e.printStackTrace();
		}
	}


	@Override
	public Admin getAdminInfo(String adminName) throws FlightTicketException {
		Admin admin=null;
		try {
			admin=adminDao.queryAdminByAdminName(adminName);
		} catch (SQLException e) {
			log.error("数据库异常！",e);
//			e.printStackTrace();
			throw new FlightTicketException("错误,数据库异常,请联系维护人员！");
		}
		return null;
	}


	






	
}
