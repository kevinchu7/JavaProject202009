package com.njwb.dao.impl;

import java.sql.SQLException;
import java.util.List;

import com.njwb.bean.FlightSeat;
import com.njwb.dao.FlightSeatDao;
import com.njwb.jdbc.JdbcTemplate;
import com.njwb.util.DateUtil;

public class FlightSeatDaoImpl implements FlightSeatDao{

	@Override
	public void addSeat(FlightSeat flightSeat) throws SQLException {
		String sql="insert into t_flight_seat(seat_id,cabin_id,flight_id,t_create_time) " +
				"values(?,?,?,?)";
		JdbcTemplate.insert(sql, 
				flightSeat.getSeatId(),
				flightSeat.getCabinId(),
				flightSeat.getFlightId(),
				DateUtil.dateToTimeStamp(flightSeat.getCreateTime()));
		
	}

	@Override
	public List<String> queryUnsoldSeatId(String flightId, int cabinId) throws SQLException {
		String sql="select seat_id from t_flight_seat where flight_id=? "
				+ "and cabin_id=? and sold_status=0 and effect_status=1";
		List<String> seatIdList=
				JdbcTemplate.selectOneCol(sql, "seatId", String.class, flightId, cabinId);
		return seatIdList;
	}

	@Override
	public FlightSeat queryFlightSeatBySeatId(String seatId) throws SQLException {
		String sql="select * from t_flight_seat where seat_id=?";
		FlightSeat flightSeat=JdbcTemplate.selectOne(sql, FlightSeat.class, seatId);
		return flightSeat;
	}

	@Override
	public void updateSeat(FlightSeat flightSeat) throws SQLException {
		String sql="update t_flight_seat set sold_status=?,effect_status=? "
				+ "where seat_id=?";
		JdbcTemplate.update(sql, 
				flightSeat.getSoldStatus(),
				flightSeat.getEffectStatus(),
				flightSeat.getSeatId());
		
	}

	@Override
	public List<Integer> queryAllSeatSoldStatus(String flightId) throws SQLException {
		String sql="select sold_status from t_flight_seat where flight_id=? "
				+ "and effect_status=1";
		List<Integer> soldStatusList=
				JdbcTemplate.selectOneCol(sql, "soldStatus", int.class, flightId);
		
		return soldStatusList;
	}



	
	
//	public static void main(String[] args) {
//		FlightSeatDaoImpl flightSeatDaoImpl=new FlightSeatDaoImpl();
//		try {
//			
////			FlightSeat flightSeat=flightSeatDaoImpl.queryFlightSeatBySeatId("MH370_206_2_3");
////			System.out.println(flightSeat.toString());
//			List<Integer> list=flightSeatDaoImpl.queryAllSeatSoldStatus("AD508");
//			for(int i:list) {
//				System.out.println(i);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		
//	}
	
	
	
}
