package com.njwb.dao;

import java.sql.SQLException;
import java.util.List;

import com.njwb.bean.Order;

public interface OrderDao {

	/**
	 * 添加订单
	 * @param order
	 * @throws SQLException
	 */
	public void addOrder(Order order) throws SQLException;
	
	
	/**
	 * 通过身份证号查询订单（所有，包含起飞，未起飞的）
	 * @param Id
	 * @return
	 * @throws SQLException
	 */
	public List<Order> queryOrderByIdCardNo(String idCardNo) throws SQLException;
	
	
	/**
	 * 通过航班号查询订单（所有，包含起飞，未起飞的）
	 * @param flightId
	 * @return
	 * @throws SQLException
	 */
	public List<Order> queryOrderByFlightId(String flightId) throws SQLException;
	
	
	
	/**
	 * 更新订单
	 * @param order
	 * @throws SQLException
	 */
	public void updateOrder(Order order) throws SQLException;
}
