package com.njwb.service.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.njwb.bean.Cabin;
import com.njwb.bean.Flight;
import com.njwb.bean.FlightCabinRelation;
import com.njwb.bean.FlightSeat;
import com.njwb.dao.CabinDao;
import com.njwb.dao.FlightCabinRelationDao;
import com.njwb.dao.FlightDao;
import com.njwb.dao.FlightSeatDao;
import com.njwb.exception.FlightTicketException;
import com.njwb.factory.annotation.FactoryAnnotation;
import com.njwb.jdbc.transaction.TransactionManager;
import com.njwb.service.FlightService;

public class FlightServiceImpl implements FlightService{
	private Logger log = Logger.getLogger(this.getClass());
	
	@FactoryAnnotation("flightDao")
	private FlightDao flightDao;
	
	@FactoryAnnotation("flightSeatDao")
	private FlightSeatDao flightSeatDao;
	
	@FactoryAnnotation("cabinDao")
	private CabinDao cabinDao;
	
	@FactoryAnnotation("flightCabinRelationDao")
	private FlightCabinRelationDao flightCabinRelationDao;
	
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

	public void setTransactionManager(TransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}


	@Override
	public void addFlight(Flight flight, Map<Integer, Double> flightCabinMap) 
	throws FlightTicketException {
		try {
			transactionManager.begin();
			String flightId=flight.getFlightId();
			
			//添加航班
			if((flightDao.queryFlightByFlightId(flightId))!=null){
				log.info("航班号已存在！");
				throw new FlightTicketException("添加失败，该航班号已经存在！");
			}
			flightDao.addFlight(flight);
			
			
			for(Integer cabinId:flightCabinMap.keySet()){
				Cabin cabin=cabinDao.queryCabinById(cabinId);
				FlightCabinRelation relation=
					new FlightCabinRelation(flightId,cabinId,flightCabinMap.get(cabinId),
							cabin.getCabinSeats());
				
				//添加该航班和舱位的级联关系
				flightCabinRelationDao.addRelation(relation);
				for(int line=1;line<=cabin.getSeatLines();line++){
					for(int col=1;col<=cabin.getOneLineSeats();col++){
						//生成座位id(生成规则：航班号_舱型号_行号_列号）
						String seatId=flightId+"_"+cabinId+"_"+line+"_"+col;
						FlightSeat flightSeat=new FlightSeat(seatId,cabinId,flightId);
						//添加该航班的座位信息
						flightSeatDao.addSeat(flightSeat);
					}
				}

			}
			
			//提交（航班、航班舱位级联关系添加）
			transactionManager.commit();
			
			
			
			
		} catch (SQLException e) {
			log.error("添加航班出错,数据库异常！",e);
			try {
				transactionManager.rollback();
			} catch (SQLException e1) {
				log.error("事务回滚失败！",e);
//				e1.printStackTrace();
			}
			throw new FlightTicketException("添加航班失败,数据库异常！");
//			e.printStackTrace();
		}
	}


	
	
	@Override
	public Map<Integer,Cabin> getAllCabins() throws FlightTicketException {
		Map<Integer,Cabin> cabinMap=new HashMap<Integer, Cabin>();
		try {
			List<Cabin> cabinList=cabinDao.queryAllCabin();
			for(Cabin cabin:cabinList){
				cabinMap.put(cabin.getCabinId(), cabin);
			}
		} catch (SQLException e) {
			log.error("获取舱位数据出错!",e);
			throw new FlightTicketException("操作失败,数据库异常！");
//			e.printStackTrace();
		}
		
		return cabinMap;
	}





	@Override
	public String getCabinNameByCabinId(int cabinId) throws FlightTicketException {
		String cabinName=null;
		try {
			cabinName=cabinDao.queryCabinById(cabinId).getCabinName();
		} catch (SQLException e) {
			log.info("获取信息失败,数据库异常！");
			throw new FlightTicketException("操作失败,数据库异常,请联系维护人员！");
		}
		return cabinName;
	}


	
	
	@Override
	public Map<Flight, List<FlightCabinRelation>> getAllFlightInfo() throws FlightTicketException {
		Map<Flight, List<FlightCabinRelation>> flightInfoMap=new HashMap<Flight, List<FlightCabinRelation>>();
		
		try {
			List<Flight> flightList=flightDao.queryAllFlight();
			for(Flight flight:flightList) {
				List<FlightCabinRelation> relationList=
						flightCabinRelationDao.queryRelationByFlightId(flight.getFlightId());
				flightInfoMap.put(flight, relationList);
			}
		} catch (SQLException e) {
			log.info("获取信息失败，数据库异常！",e);
			throw new FlightTicketException("获取信息失败，数据库异常！");
//			e.printStackTrace();
		}
		
		return flightInfoMap;
	}


	@Override
	public Map<Flight, List<FlightCabinRelation>> getFlightInfoByCondition(String condition, Object value)
			throws FlightTicketException {
		Map<Flight, List<FlightCabinRelation>> flightInfoMap=
				new HashMap<Flight, List<FlightCabinRelation>>();
		try {
			List<Flight> flightList=flightDao.queryFlightByCondition(condition, value);
			for(Flight flight:flightList) {
				List<FlightCabinRelation> relationList=
						flightCabinRelationDao.queryRelationByFlightId(flight.getFlightId());
				flightInfoMap.put(flight, relationList);
			}
			
		} catch (SQLException e) {
			log.info("获取信息失败，数据库异常！",e);
			throw new FlightTicketException("获取信息失败，数据库异常！");
//			e.printStackTrace();
		}
		
		return flightInfoMap;
	}


	@Override
	public Map<Flight, List<FlightCabinRelation>> getNoTakeOffFlightInfo() throws FlightTicketException {
		Map<Flight, List<FlightCabinRelation>> flightInfoMap=getAllFlightInfo();
		Map<Flight, List<FlightCabinRelation>> noTakeOffFlightInfoMap=new HashMap<Flight, List<FlightCabinRelation>>();
		for(Flight flight:flightInfoMap.keySet()) {
			if(flight.getTakeOffTime().after(new Date())) {
				noTakeOffFlightInfoMap.put(flight, flightInfoMap.get(flight));
			}
		}
		return noTakeOffFlightInfoMap;
	}


	
	
	@Override
	public Map<Flight, List<FlightCabinRelation>> getNoTakeOffFlightInfoByCondition(String condition,
			Object value) throws FlightTicketException {
		
		Map<Flight, List<FlightCabinRelation>> flightInfoMap=getFlightInfoByCondition(condition, value);
		Map<Flight, List<FlightCabinRelation>> noTakeOffFlightInfoMap=new HashMap<Flight, List<FlightCabinRelation>>();
		for(Flight flight:flightInfoMap.keySet()) {
			if(flight.getTakeOffTime().after(new Date())) {
				noTakeOffFlightInfoMap.put(flight, flightInfoMap.get(flight));
			}
		}
		return noTakeOffFlightInfoMap;
	}


	@Override
	public List<String> getUnsoldSeat(String flightId, int cabinId) throws FlightTicketException {
		List<String> seatIdList=null;
		try {
			seatIdList=flightSeatDao.queryUnsoldSeatId(flightId, cabinId);
			if(seatIdList==null||seatIdList.size()==0) {
				log.info("获取座位信息失败，数据库异常！flightId:"+flightId+" cabinId:"+cabinId);
				throw new FlightTicketException("操作失败，数据库异常！");
			}
		} catch (SQLException e) {
			log.info("获取信息失败，数据库异常！",e);
			throw new FlightTicketException("操作失败，数据库异常！");
//			e.printStackTrace();
		}
		
		return seatIdList;
	}


	@Override
	public boolean flightHasSoldTicket(String flightId) throws FlightTicketException {
		List<Integer> soldStatusList=null;
		try {
			soldStatusList=flightSeatDao.queryAllSeatSoldStatus(flightId);
			if(soldStatusList==null) {
				log.info("获取信息失败，数据库异常！");
				throw new FlightTicketException("操作失败，数据库异常！");
			}
			if(soldStatusList.contains(1)) {
				return true;
			}

		} catch (SQLException e) {
			log.info("获取信息失败，数据库异常！",e);
			throw new FlightTicketException("操作失败，数据库异常！");
//			e.printStackTrace();
		}
		return false;
	}


	@Override
	public Flight getFlightByFlightId(String flightId) throws FlightTicketException {
		try {
			return flightDao.queryFlightByFlightId(flightId);
		} catch (SQLException e) {
			log.info("获取信息失败，数据库异常！",e);
			throw new FlightTicketException("操作失败，数据库异常！");
//			e.printStackTrace();
		}
	}


	@Override
	public void changeFlightInfo(Flight flight) throws FlightTicketException {
		try {
			transactionManager.begin();
			
			flightDao.updateFlight(flight);
			
			transactionManager.commit();
			
			
		} catch (SQLException e) {
			log.info("获取信息失败，数据库异常！",e);
			try {
				transactionManager.rollback();
			} catch (SQLException e1) {
				log.error("事务回滚失败！",e1);
//				e1.printStackTrace();
			}
			throw new FlightTicketException("修改航班信息失败，数据库异常！");
//			e.printStackTrace();
		}
		
		
		
	}


	@Override
	public void deleteFlight(Flight flight, List<FlightCabinRelation> relationList) 
			throws FlightTicketException {
		try {
			transactionManager.begin();
			String flightId=flight.getFlightId();
			for(FlightCabinRelation relation:relationList) {
				List<String> seatIdList=
						flightSeatDao.queryUnsoldSeatId(flightId, relation.getCabinId());
				for(String seatId:seatIdList) {
					FlightSeat seat=flightSeatDao.queryFlightSeatBySeatId(seatId);
					//将座位有效状态改为0
					seat.setEffectStatus(0);
					flightSeatDao.updateSeat(seat);
				}
				//关系有效状态改为0
				relation.setEffectStatus(0);
				flightCabinRelationDao.updateRelation(relation);
			}
			
			
			
			//航班的有效状态改为0
			flight.setEffectStatus(0);
			flightDao.updateFlight(flight);
			
			transactionManager.commit();
		} catch (SQLException e) {
			log.info("获取信息失败，数据库异常！",e);
			try {
				transactionManager.rollback();
			} catch (SQLException e1) {
				log.error("事务回滚失败！",e1);
//				e1.printStackTrace();
			}
			throw new FlightTicketException("删除航班信息失败，数据库异常！");
		}
		
		
		
	}


	
	


}
