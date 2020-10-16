package com.njwb.dao.impl;

import java.sql.SQLException;
import java.util.List;

import com.njwb.bean.Order;
import com.njwb.dao.OrderDao;
import com.njwb.jdbc.JdbcTemplate;
import com.njwb.util.DateUtil;

public class OrderDaoImpl implements OrderDao{

	@Override
	public void addOrder(Order order) throws SQLException {
		String sql="insert into t_order(flight_id,start_place,end_place,"
				+ "take_off_time,cabin_id,seat_id,price,real_name,"
				+ "id_card_no,t_create_time) "
				+ "values(?,?,?,?,?,?,?,?,?,?)";
		JdbcTemplate.insert(sql, 
				order.getFlightId(),
				order.getStartPlace(),
				order.getEndPlace(),
				order.getTakeOffTime(),
				order.getCabinId(),
				order.getSeatId(),
				order.getPrice(),
				order.getRealName(),
				order.getIdCardNo(),
				DateUtil.dateToTimeStamp(order.getCreateTime()));
	}

	@Override
	public List<Order> queryOrderByIdCardNo(String idCardNo) throws SQLException {
		
		String sql="select * from t_order where id_card_no=? and effect_status=1";
		List<Order> orderList=JdbcTemplate.select(sql, Order.class, idCardNo);
		return orderList;
	}

	
	
	@Override
	public void updateOrder(Order order) throws SQLException {
		String sql="update t_order set cabin_id=?,seat_id=?,"
				+ "price=?,effect_status=?";
		
		JdbcTemplate.update(sql, 
				order.getCabinId(),
				order.getSeatId(),
				order.getPrice(),
				order.getEffectStatus());
		
	}

	@Override
	public List<Order> queryOrderByFlightId(String flightId) throws SQLException {
		String sql="select * from t_order where flight_id=? and effect_status=1";
		List<Order> orderList=JdbcTemplate.select(sql, Order.class, flightId);
		return orderList;
	}
	
	
//	public static void main(String[] args) {
//		OrderDaoImpl o=new OrderDaoImpl();
//		List<Order> orderList;
//		try {
//			orderList=o.queryOrderByFlightId("Sk330");
////			orderList = o.queryOrderByIdCardNo("320121199812251338");
//			for(Order or:orderList) {
//				System.out.println(or.toString());
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	

}
