package com.njwb.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.njwb.bean.Cabin;
import com.njwb.bean.Flight;
import com.njwb.bean.FlightCabinRelation;
import com.njwb.exception.FlightTicketException;

public interface FlightService {
	/**
	 * 添加航班
	 * @param flight
	 * @param cabinMap
	 * @throws SQLException
	 */
	public void addFlight(Flight flight, Map<Integer, Double> flightCabinMap) 
	throws FlightTicketException;
	
	/**
	 * 获取现有的航班舱位类型
	 * @return
	 * @throws FlightTicketException
	 */
	public Map<Integer,Cabin> getAllCabins() throws FlightTicketException;
	
	/**
	 * 根据舱位号获取航班的舱位名称
	 * @return
	 * @throws FlightTicketException
	 */
	public String getCabinNameByCabinId(int cabinId) throws FlightTicketException;
	

	
	/**
	 * 获取所有的航班信息
	 * @return
	 * @throws FlightTicketException
	 */
	public Map<Flight,List<FlightCabinRelation>> getAllFlightInfo() throws FlightTicketException;
	
	
	
	/**
	 * 根据条件获取对应的航班信息
	 * @param flightId
	 * @param field
	 * @param value
	 * @return
	 * @throws FlightTicketException
	 */
	public Map<Flight,List<FlightCabinRelation>> getFlightInfoByCondition(
			String condition,Object value) 
			throws FlightTicketException;
	
	
	/**
	 * 获取所有未起飞的航班信息
	 * @return
	 * @throws FlightTicketException
	 */
	public Map<Flight,List<FlightCabinRelation>> getNoTakeOffFlightInfo() throws FlightTicketException;
	
	
	/**
	 * 根据条件获取对应的未起飞的航班信息
	 * @param flightId
	 * @param field
	 * @param value
	 * @return
	 * @throws FlightTicketException
	 */
	public Map<Flight,List<FlightCabinRelation>> getNoTakeOffFlightInfoByCondition(
			String condition,Object value)
			throws FlightTicketException;
	
	
	/**
	 * 获取指定航班指定舱位的未售出的座位号信息
	 * @param flightId
	 * @param cabinId
	 * @return
	 * @throws FlightTicketException
	 */
	public List<String> getUnsoldSeat(String flightId, int cabinId) throws FlightTicketException;
	
	
	/**
	 * 判断航班有无机票卖出
	 * @param flightId
	 * @return
	 * @throws FlightTicketException
	 */
	public boolean flightHasSoldTicket(String flightId) throws FlightTicketException;
	
	
	
	/**
	 * 通过航班号获取指定航班信息（所有）
	 * @param flightId
	 * @return
	 * @throws FlightTicketException
	 */
	public Flight getFlightByFlightId(String flightId) throws FlightTicketException;
	
	/**
	 * 修改航班
	 * @param flight
	 * @throws FlightTicketException
	 */
	public void changeFlightInfo(Flight flight) throws FlightTicketException;
	
	/**
	 * 删除航班
	 * @param flight
	 * @throws FlightTicketException
	 */
	public void deleteFlight(Flight flight,List<FlightCabinRelation> relationList) throws FlightTicketException;
}
