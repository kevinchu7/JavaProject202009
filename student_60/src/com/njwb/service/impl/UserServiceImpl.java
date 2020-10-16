package com.njwb.service.impl;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.omg.CORBA.UserException;

import com.njwb.bean.User;
import com.njwb.dao.UserDao;
import com.njwb.exception.FlightTicketException;
import com.njwb.factory.ObjectFactory;
import com.njwb.factory.annotation.FactoryAnnotation;
import com.njwb.jdbc.transaction.TransactionManager;
import com.njwb.service.UserService;

public class UserServiceImpl implements UserService{
	private Logger log = Logger.getLogger(this.getClass());
	
	@FactoryAnnotation("userDao")
	private UserDao userDao;
	
	@FactoryAnnotation("transactionManager")
	private TransactionManager transactionManager;
	


	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}


	public void setTransactionManager(TransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}


	@Override
	public void userRegister(User user) throws FlightTicketException {

		try {
			if(userDao.queryUserByUserName(user.getUserName())!=null){
				log.info("用户名已存在！");
				throw new FlightTicketException("注册失败，用户名已存在！");
			}else if(idCardNoExist(user.getIdCardNo())==true){
				//一个身份证号只能注册一个帐号
				log.info("该身份证号已被注册！");
				throw new FlightTicketException("注册失败，该身份证号已被注册！");
			}
			transactionManager.begin();
			userDao.addUser(user);
			transactionManager.commit();
		} catch (SQLException e) {
			log.error("注册失败，数据库异常！"+user,e);
			try {
				transactionManager.rollback();
			} catch (SQLException e1) {
				log.error("rollback失败！",e1);
//				e1.printStackTrace();
			}
			throw new FlightTicketException("注册失败，数据库异常！");
		}
		
	}

	
	@Override
	public void userLogin(String userName, String password) throws FlightTicketException {
		try {
			User user=userDao.queryUserByUserName(userName);
			if(user==null){
				log.info("登陆失败，输入用户名不存在！");
				throw new FlightTicketException("登陆失败，用户名不存在！");
			}else if(password.equals(user.getPassword())==false){
				log.info("登陆失败，密码错误！");
				throw new FlightTicketException("登陆失败，密码错误");
			}
			
		} catch (SQLException e) {
			log.error("登陆失败,数据库异常！ userName:"+userName,e);
			throw new FlightTicketException("登陆失败,数据库异常！");
//			e.printStackTrace();
		}
	}

	
	
	
	@Override
	public User getUserInfo(String userName) throws FlightTicketException {
		User user=null;
		try {
			user=userDao.queryUserByUserName(userName);
		} catch (SQLException e) {
			log.error("获取失败,数据库异常！ userName:"+userName,e);
			throw new FlightTicketException("操作失败,数据库异常！");
//			e.printStackTrace();
		}
		
		return user;
	}


	@Override
	public void userChangeInfo(String userName,String sex,String password,
			String phone) throws FlightTicketException {
		try {
			transactionManager.begin();
			userDao.updateUser(userName,sex,password,phone);
			transactionManager.commit();
			
		} catch (SQLException e) {
			log.error("更新失败，数据库异常！"+userName,e);
			
			try {
				transactionManager.rollback();
			} catch (SQLException e1) {
				log.error("rollback失败！",e1);
//				e1.printStackTrace();
			}
			throw new FlightTicketException("修改失败，数据库异常！");
//			e.printStackTrace();
		}
		
		
	}


	/**
	 * 判断身份证号是否已注册
	 * @param idCardNo
	 * @return
	 * @throws SQLException
	 */
	private boolean idCardNoExist(String idCardNo) throws SQLException{
		User user=userDao.queryUserByIdCardNo(idCardNo);
		if(user==null){
			return false;
		}
		return true;
		
	}

}
