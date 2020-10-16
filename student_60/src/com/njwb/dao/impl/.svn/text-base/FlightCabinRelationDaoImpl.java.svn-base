package com.njwb.dao.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.njwb.bean.FlightCabinRelation;
import com.njwb.dao.FlightCabinRelationDao;
import com.njwb.jdbc.JdbcTemplate;
import com.njwb.util.DateUtil;

public class FlightCabinRelationDaoImpl implements FlightCabinRelationDao{

	@Override
	public void addRelation(FlightCabinRelation flightCabinRelation) throws SQLException {
		String sql="insert into t_flight_cabin_relation(flight_id," +
		"cabin_id,price,rest_tickets,t_create_time) " +
		"values(?,?,?,?,?)";
		JdbcTemplate.insert(sql,flightCabinRelation.getFlightId(),
				flightCabinRelation.getCabinId(),
				flightCabinRelation.getPrice(),
				flightCabinRelation.getRestTickets(),
				DateUtil.dateToTimeStamp(flightCabinRelation.getCreateTime()));
	}

	@Override
	public List<FlightCabinRelation> queryRelationByFlightId(String flightId) throws SQLException {
		String sql="select * from t_flight_cabin_relation where flight_id=? and effect_status=1";
		List<FlightCabinRelation> relationList=JdbcTemplate.select(sql, FlightCabinRelation.class, flightId);
		return relationList;
	}

	@Override
	public FlightCabinRelation queryRelation(String flightId, int cabinId) throws SQLException {
		String sql="select * from t_flight_cabin_relation "
				+ "where flight_id=? and cabin_id=? and effect_status=1";
		FlightCabinRelation flightCabinRelation=
				JdbcTemplate.selectOne(sql, FlightCabinRelation.class, flightId,cabinId);
		return flightCabinRelation;
	}

	@Override
	public void updateRelation(FlightCabinRelation relation) throws SQLException {
		String sql="update t_flight_cabin_relation set rest_tickets=?,"
				+ "effect_status=? where flight_id=? and cabin_id=?";
		JdbcTemplate.update(sql, 
				relation.getRestTickets(),
				relation.getEffectStatus(),
				relation.getFlightId(),
				relation.getCabinId());
	}

	
	
	
//	public static void main(String[] args) {
//		FlightCabinRelationDaoImpl f=new FlightCabinRelationDaoImpl();
//		try {
//			System.out.println(f.queryRelation("AD508", 206).toString());
//			System.out.println();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
	
}
