package com.njwb.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.njwb.bean.FlightCabinRelation;
import com.njwb.bean.FlightSeat;
import com.njwb.bean.Order;
import com.njwb.dao.CabinDao;
import com.njwb.dao.FlightCabinRelationDao;
import com.njwb.dao.FlightDao;
import com.njwb.dao.FlightSeatDao;
import com.njwb.dao.OrderDao;
import com.njwb.exception.FlightTicketException;
import com.njwb.factory.annotation.FactoryAnnotation;
import com.njwb.jdbc.transaction.TransactionManager;
import com.njwb.service.OrderService;

public class OrderServiceImpl implements OrderService {

	private Logger log = Logger.getLogger(this.getClass());

	@FactoryAnnotation("flightDao")
	private FlightDao flightDao;

	@FactoryAnnotation("flightSeatDao")
	private FlightSeatDao flightSeatDao;

	@FactoryAnnotation("cabinDao")
	private CabinDao cabinDao;

	@FactoryAnnotation("flightCabinRelationDao")
	private FlightCabinRelationDao flightCabinRelationDao;
	
	@FactoryAnnotation("orderDao")
	private OrderDao orderDao;

	@FactoryAnnotation("transactionManager")
	private TransactionManager transactionManager;

	public void setFlightDao(FlightDao flightDao) {
		this.flightDao = flightDao;
	}

	public void setFlightSeatDao(FlightSeatDao flightSeatDao) {
		this.flightSeatDao = flightSeatDao;
	}

	public void setCabinDao(CabinDao cabinDao) {
		this.cabinDao = cabinDao;
	}

	public void setFlightCabinRelationDao(FlightCabinRelationDao flightCabinRelationDao) {
		this.flightCabinRelationDao = flightCabinRelationDao;
	}
	

	public void setOrderDao(OrderDao orderDao) {
		this.orderDao = orderDao;
	}

	public void setTransactionManager(TransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	
	
	
	
	
	
	@Override
	public void addOrder(Order order) throws FlightTicketException {
		try {
			transactionManager.begin();
			//座位状态改成卖出
			FlightSeat flightSeat=
					flightSeatDao.queryFlightSeatBySeatId(order.getSeatId());
			flightSeat.setSoldStatus(1);
			flightSeatDao.updateSeat(flightSeat);
			
			//航班该舱位的余票-1
			FlightCabinRelation relation=
					flightCabinRelationDao.queryRelation(
							order.getFlightId(), order.getCabinId());
			relation.setRestTickets(relation.getRestTickets()-1);
			flightCabinRelationDao.updateRelation(relation);
			
			
			//添加订单
			orderDao.addOrder(order);
			
			transactionManager.commit();
		} catch (SQLException e) {
			log.error("添加订单失败，数据库异常！",e);
			try {
				transactionManager.rollback();
			} catch (SQLException e1) {
				log.error("事务回滚失败！",e1);
//				e1.printStackTrace();
			}
			throw new FlightTicketException("订票失败，数据库异常！");
//			e.printStackTrace();
		}
		
		

	}

	@Override
	public void updateOrder(Order order,int initCabinId,String initSeatId) throws FlightTicketException {
		try {
			transactionManager.begin();
			String flightId=order.getFlightId();
			
			//初始座位改为未售出
			FlightSeat initSeat=flightSeatDao.queryFlightSeatBySeatId(initSeatId);
			initSeat.setSoldStatus(0);
			flightSeatDao.updateSeat(initSeat);
			
			//初始级联关系的座位数+1
			FlightCabinRelation initRelation=flightCabinRelationDao.queryRelation(flightId, initCabinId);
			initRelation.setRestTickets(initRelation.getRestTickets()+1);
			
			//现在的关系的余票数-1
			FlightCabinRelation relation=flightCabinRelationDao.queryRelation(flightId, order.getCabinId());
			relation.setRestTickets(relation.getRestTickets()-1);
			flightCabinRelationDao.updateRelation(relation);
			
			//现在的座位改为售出
			FlightSeat seat=flightSeatDao.queryFlightSeatBySeatId(order.getSeatId());
			seat.setSoldStatus(1);
			flightSeatDao.updateSeat(seat);
			
			//更新order
			orderDao.updateOrder(order);
			
			transactionManager.commit();
			
		} catch (SQLException e) {
			log.error("添加订单失败，数据库异常！",e);
			try {
				transactionManager.rollback();
			} catch (SQLException e1) {
				log.error("事务回滚失败！",e1);
//				e1.printStackTrace();
			}
			throw new FlightTicketException("订票失败，数据库异常！");
//			e.printStackTrace();
		}
		
		
		
	}

	@Override
	public void refoundOrder(Order order) throws FlightTicketException {
		try {
			transactionManager.begin();
			String flightId=order.getFlightId();
			int cabinId=order.getCabinId();
			String seatId=order.getSeatId();
			
			//座位置为未售出
			FlightSeat seat=flightSeatDao.queryFlightSeatBySeatId(seatId);
			seat.setSoldStatus(0);
			flightSeatDao.updateSeat(seat);
			
			//级联关系的余票数+1
			FlightCabinRelation relation=flightCabinRelationDao.queryRelation(flightId, cabinId);
			relation.setRestTickets(relation.getRestTickets()+1);
			flightCabinRelationDao.updateRelation(relation);
			
			
			//order置为无效
			order.setEffectStatus(0);
			orderDao.updateOrder(order);
			
			transactionManager.commit();
		} catch (SQLException e) {
			log.error("删除订单失败，数据库异常！",e);
			try {
				transactionManager.rollback();
			} catch (SQLException e1) {
				log.error("事务回滚失败！",e1);
//				e1.printStackTrace();
			}
			throw new FlightTicketException("退订失败，数据库异常！");
//			e.printStackTrace();
		}
		
	}



	@Override
	public Map<Integer,Order> getNoStartOrderByIdCard(String IdCardNo) throws FlightTicketException {
		Map<Integer,Order> orderMap=new HashMap<Integer, Order>();
		try {
			List<Order> orderList=orderDao.queryOrderByIdCardNo(IdCardNo);
			if(orderList==null||orderList.size()==0){
				return null;
			}
			Date now=new Date();
			//筛选未出发的
			for(Order order:orderList) {
				if(order.getTakeOffTime().after(now)==false) {
					continue;
				}
				orderMap.put(order.getOrderId(), order);
			}
		} catch (SQLException e) {
			log.error("获取订单信息失败，数据库异常！", e);
			throw new FlightTicketException("操作失败，数据库异常！");
//			e.printStackTrace();
		}
		return orderMap;
	}


	
	
	
	
}
