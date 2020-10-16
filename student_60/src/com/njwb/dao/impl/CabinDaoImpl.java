package com.njwb.dao.impl;

import java.sql.SQLException;
import java.util.List;

import com.njwb.bean.Cabin;
import com.njwb.dao.CabinDao;
import com.njwb.jdbc.JdbcTemplate;

public class CabinDaoImpl implements CabinDao{

	@Override
	public List<Cabin> queryAllCabin() throws SQLException {
		List<Cabin> cabinList=null;
		String sql="select * from t_cabin";
		cabinList=JdbcTemplate.select(sql, Cabin.class);
		return cabinList;
	}

	@Override
	public Cabin queryCabinById(int cabinId) throws SQLException {
		Cabin cabin=null;
		String sql="select * from t_cabin where cabin_id=?";
		cabin=JdbcTemplate.selectOne(sql, Cabin.class, cabinId);
		return cabin;
	}

//	@Override
//	public int queryCabinSeatById(int cabinId) throws SQLException {
//		String sql="select cabin_seats from t_cabin where cabin_id=?";
//		int seatCount=JdbcTemplate.select(sql, "cabinSeats", int.class, 
//				cabinId);
//		return seatCount;
//	}
	
	
//	public static void main(String[] args) {
//		CabinDaoImpl c=new CabinDaoImpl();
//		try {
//			System.out.println(c.queryCabinSeatById(206));
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}

	

}
