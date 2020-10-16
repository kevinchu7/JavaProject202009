package com.njwb.dao;

import java.sql.SQLException;
import java.util.List;

import com.njwb.bean.FlightSeat;

public interface FlightSeatDao {
	/**
	 * 添加座位
	 * @param flightSeat
	 * @throws SQLException
	 */
	public void addSeat(FlightSeat flightSeat) throws SQLException;
	
	/**
	 * 查找所有座位Id(未售出的)
	 * @param flightId
	 * @param cabinId
	 * @return
	 * @throws SQLException
	 */
	public List<String> queryUnsoldSeatId(String flightId,int cabinId) throws SQLException;
	
	
	/**
	 * 通过座位Id查找座位
	 * @param seatId
	 * @return
	 * @throws SQLException
	 */
	public FlightSeat queryFlightSeatBySeatId(String seatId) throws SQLException;
	
	/**
	 * 更新座位
	 * @param flightSeat
	 * @throws SQLException
	 */
	public void updateSeat(FlightSeat flightSeat) throws SQLException;

	/**
	 * 查询指定航班的所有座位的售出情况
	 * @param FlightId
	 * @return
	 * @throws SQLException
	 */
	public List<Integer> queryAllSeatSoldStatus(String flightId) throws SQLException;
}
