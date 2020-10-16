package com.njwb.dao.impl;

import java.sql.SQLException;
import java.util.List;

import com.njwb.bean.Flight;
import com.njwb.dao.FlightDao;
import com.njwb.jdbc.JdbcTemplate;
import com.njwb.util.DateUtil;

public class FlightDaoImpl implements FlightDao{
	

	@Override
	public void addFlight(Flight flight) throws SQLException {
		String sql="insert into t_flight(flight_id,flight_name,take_off_time,arrive_time," +
				"start_place,end_place,t_create_time) " +
				"values(?,?,?,?,?,?,?)";
		JdbcTemplate.insert(sql, 
				flight.getFlightId(),
				flight.getFlightName(),
				DateUtil.dateToTimeStamp(flight.getTakeOffTime()),
				DateUtil.dateToTimeStamp(flight.getArriveTime()),
				flight.getStartPlace(),
				flight.getEndPlace(),
				DateUtil.dateToTimeStamp(flight.getCreateTime()));
	}


	@Override
	public List<Flight> queryAllFlight() throws SQLException {
		String sql="select * from t_flight where effect_status=1";
		List<Flight> flightList=JdbcTemplate.select(sql, Flight.class);
		return flightList;
	}

	@Override
	public List<Flight> queryFlightByCondition(String condition, Object value) throws SQLException {
		String sql;
		if(condition.equals("take_off_time")) {
			sql="select * from t_flight where "+condition+" like ? and effect_status=1";
			
		}else {
			sql="select * from t_flight where "+condition+"= ? and effect_status=1";
		}
		List<Flight> flightList=JdbcTemplate.select(sql, Flight.class, value);
		return flightList;
	}
	
	
	@Override
	public Flight queryFlightByFlightId(String flightId) throws SQLException {
		String sql="select * from t_flight where flight_id=? and effect_status=1";
		Flight flight=JdbcTemplate.selectOne(sql, Flight.class, flightId);
		return flight;
	}


	@Override
	public void updateFlight(Flight flight) throws SQLException {
		String sql="update t_flight set flight_name=?,take_off_time=?,"
				+ "arrive_time=?,start_place=?,end_place=?,effect_status=? "
				+ "where flight_id=?";
		JdbcTemplate.update(sql, 
				flight.getFlightName(),
				DateUtil.dateToTimeStamp(flight.getTakeOffTime()),
				DateUtil.dateToTimeStamp(flight.getArriveTime()),
				flight.getStartPlace(),
				flight.getEndPlace(),
				flight.getEffectStatus(),
				flight.getFlightId());
			
		
	}
	
	
	
//	public static void main(String[] args) {
//		try {
//			FlightDaoImpl f=new FlightDaoImpl();
//			Flight flight=f.queryFlightByFlightId("PT960");
//			System.out.println(flight.toString());
//			flight.setFlightName("OB990");
//			f.updateFlight(flight);
//			System.out.println(f.queryFlightByFlightId(flight.getFlightId()).toString());
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}

}
