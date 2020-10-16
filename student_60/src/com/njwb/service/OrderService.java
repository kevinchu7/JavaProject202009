package com.njwb.service;

import java.util.Map;
import com.njwb.bean.Order;
import com.njwb.exception.FlightTicketException;

public interface OrderService {
	/**
	 * 添加订单
	 * @param order
	 * @throws FlightTicketException
	 */
	public void addOrder(Order order) throws FlightTicketException;
	
	/**
	 * 改签
	 * @param order
	 * @param initCabinId
	 * @param initSeatId
	 * @throws FlightTicketException
	 */
	public void updateOrder(Order order,int initCabinId,String initSeatId) throws FlightTicketException;
	
	
	/**
	 * 退订
	 * @param order
	 * @throws FlightTicketException
	 */
	public void refoundOrder(Order order) throws FlightTicketException;
	
	
	
	/**
	 * 获取指定身份证号的所有有效订单信息（未出发的） 
	 * @param IdCardNo
	 * @return
	 * @throws FlightTicketException
	 */
	public Map<Integer,Order> getNoStartOrderByIdCard(String IdCardNo) throws FlightTicketException;
	
	
}
