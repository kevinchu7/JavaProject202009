package com.njwb.dao;

import java.sql.SQLException;
import java.util.List;

import com.njwb.bean.FlightCabinRelation;

public interface FlightCabinRelationDao {
	/**
	 *  添加航班与舱位的级联关系
	 * @param flightCabinRelation
	 * @throws SQLException
	 */
	public void addRelation(FlightCabinRelation flightCabinRelation) throws SQLException;
	
	
	/**
	 * 根据航班号查询所有的级联关系
	 * @param flightId
	 * @return
	 * @throws SQLException
	 */
	public List<FlightCabinRelation> queryRelationByFlightId(String flightId) throws SQLException;
	
	
	/**
	 * 查询航班与舱位的具体级联关系
	 * @param flightId
	 * @param cabinId
	 * @return
	 * @throws SQLException
	 */
	public FlightCabinRelation queryRelation(String flightId,int cabinId) throws SQLException;
	
	/**
	 * 更新级联关系
	 * @param relation
	 * @throws SQLException
	 */
	public void updateRelation(FlightCabinRelation relation) throws SQLException;
	
}
