package com.njwb.view;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.njwb.bean.Flight;
import com.njwb.bean.FlightCabinRelation;
import com.njwb.exception.FlightTicketException;
import com.njwb.factory.annotation.FactoryAnnotation;
import com.njwb.service.FlightService;
import com.njwb.util.ChoiceUtil;
import com.njwb.util.DateUtil;
import com.njwb.util.ValueCheckUtil;

/**查询界面
 * @author  朱凯
 * No.60
 */
public class QueryView {
	private static Scanner scanner = new Scanner(System.in);

	@FactoryAnnotation("flightService")
	private FlightService flightService;

	public void setFlightService(FlightService flightService) {
		this.flightService = flightService;
	}

	public void start() {
		mainView("user");
	}

	public void startAdminIF() {
		mainView("admin");
	}
	

	/**
	 * 查询主界面（默认）
	 */
	private void mainView(String userType) {
		int ch;
		while (true) {
			System.out.println("****查询航班*****");
			System.out.println("1.查看所有航班");
			System.out.println("2.搜索航班");
			System.out.println("3.退出");
			System.out.println("请选择:");
			ch = ChoiceUtil.getRangeChoice(scanner, 3);
			switch (ch) {
			case 1:
				queryAllFlight(userType);
				break;
			case 2:
				queryFlightByCondition(userType);
				break;
			case 3:
				return;

			}

		}
	}

	/**
	 * 查询所有航班
	 * （普通用户仅可以查询未起飞的航班，
	 * 管理员身份可以查询所有的航班）
	 * @param userType
	 */
	private void queryAllFlight(String userType) {
		try {
			if(userType.equals("admin")) {
				//
				showFlightInfo(flightService.getAllFlightInfo());
			}else {
				showFlightInfo(flightService.getNoTakeOffFlightInfo());
			}
		} catch (FlightTicketException e) {
			System.out.println(e.getErrorMsg());
//			e.printStackTrace();
		}

	}


	/**
	 * 分条件搜索
	 * （普通用户仅可以查询未起飞的航班，
	 * 管理员身份可以查询所有的航班）
	 * @param userType
	 */
	private void queryFlightByCondition(String userType) {
		try {
			int cho;
			while (true) {
				System.out.println("----条件搜索-----");
				System.out.println("1.航班号 2.出发地 3.目的地 4.出发日期 5.退出");
				System.out.println("请选择搜索条件或退出搜索:");
				cho = ChoiceUtil.getRangeChoice(scanner, 5);
				switch (cho) {
				case 1:
					System.out.println("请输入航班号：");
					String flightId = scanner.next();
					if (ValueCheckUtil.matchFlightId(flightId) == false) {
						System.out.println("输入的航班号格式错误！");
						break;
					}
					if(userType.equals("admin")){
						showFlightInfo(flightService.
								getFlightInfoByCondition("flight_id", flightId));
					}else {
						showFlightInfo(flightService.
								getNoTakeOffFlightInfoByCondition("flight_id", flightId));
					}
					break;
				case 2:
					System.out.println("请输入出发地：");
					String startPlace = scanner.next();
					if(userType.equals("admin")) {
						showFlightInfo(flightService.
								getFlightInfoByCondition("start_place", startPlace));
					}else {
						showFlightInfo(flightService.
								getNoTakeOffFlightInfoByCondition("start_place", startPlace));
					}
					break;
				case 3:
					System.out.println("请输入目的地：");
					String endPlace=scanner.next();
					if(userType.equals("admin")) {
						showFlightInfo(flightService.
								getFlightInfoByCondition("end_place", endPlace));
					}else {
						showFlightInfo(flightService.
								getNoTakeOffFlightInfoByCondition("end_place", endPlace));
					}
					break;
					
				case 4:
					System.out.println("请输入出发日期(yyyy-MM-dd)：");
					scanner.nextLine();
					String takeOffTimeStr=scanner.nextLine();
					if(ValueCheckUtil.matchDateStr(takeOffTimeStr)==false) {
						System.out.println("输入的时间格式有误！");
						break;
					}
					if(userType.equals("admin")) {
						showFlightInfo(flightService.
								getFlightInfoByCondition("take_off_time", 
										takeOffTimeStr+"%"));
					}else {
						showFlightInfo(flightService.
								getNoTakeOffFlightInfoByCondition("take_off_time", 
										takeOffTimeStr+"%"));
					}
					break;
					
				case 5:
					return;
				}

			}
		} catch (FlightTicketException e) {
			System.out.println(e.getErrorMsg());
//			e.printStackTrace();
		}
	}

	/**
	 * 打印航班信息
	 * 
	 * @param flightInfoMap
	 * @throws FlightTicketException
	 */
	public void showFlightInfo(Map<Flight, List<FlightCabinRelation>> flightInfoMap) throws FlightTicketException {
		System.out.println("");
		if (flightInfoMap == null || flightInfoMap.size() == 0) {
			System.out.println("当前找不到任何航班！");
			return;
		}
		System.out.println("航班信息如下：");
		for (Flight flight : flightInfoMap.keySet()) {
			System.out.println(
					"————————————————————————————————————"
			+ "——————————————————————————————————————————————————————"
							+ "———————————————————————————————————————");
			System.out.println("航班号：" + flight.getFlightId() + "  航班名：" + flight.getFlightName() + "  出发地："
					+ flight.getStartPlace() + " 目的地：" + flight.getEndPlace() + "\n" + "出发时间："
					+ DateUtil.dateToString(null, flight.getTakeOffTime()) + "  到达时间："
					+ DateUtil.dateToString(null, flight.getArriveTime()));
			for (FlightCabinRelation relation : flightInfoMap.get(flight)) {
				System.out.print(flightService.getCabinNameByCabinId(relation.getCabinId()) + " " + relation.getPrice()
						+ " RMB/座 余票：" + relation.getRestTickets() + "  ");
			}
			System.out.println(
					"\n————————————————————————————————————" 
			+ "——————————————————————————————————————————————————————"
							+ "———————————————————————————————————————");
		}
	}
}
