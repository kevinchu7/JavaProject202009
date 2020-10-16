package com.njwb.dao;

import java.sql.SQLException;
import java.util.List;

import com.njwb.bean.Flight;

public interface FlightDao {
	/**
	 * 添加航班
	 * @param flight
	 * @throws SQLException
	 */
	public void addFlight(Flight flight) throws SQLException;
	
	
	/**
	 * 查询所有航班
	 * @return
	 * @throws SQLException
	 */
	public List<Flight> queryAllFlight() throws SQLException;
	
	/**
	 * 根据条件查找航班
	 * @param condition
	 * @param value
	 * @return
	 * @throws SQLException
	 */
	public List<Flight> queryFlightByCondition(String condition,Object value) throws SQLException;
	
	/**
	 * 通过航班号查找航班
	 * @param flightId
	 * @return
	 * @throws SQLException
	 */
	public Flight queryFlightByFlightId(String flightId) throws SQLException;
	
	
	/**
	 * 更新航班
	 * @param flight
	 * @throws SQLException
	 */
	public void updateFlight(Flight flight) throws SQLException;
	
}
