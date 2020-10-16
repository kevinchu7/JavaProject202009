package com.njwb.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.njwb.bean.Cabin;
import com.njwb.bean.Flight;
import com.njwb.bean.FlightCabinRelation;
import com.njwb.exception.FlightTicketException;
import com.njwb.factory.ObjectFactory;
import com.njwb.factory.annotation.FactoryAnnotation;
import com.njwb.service.AdminService;
import com.njwb.service.FlightService;
import com.njwb.util.ChoiceUtil;
import com.njwb.util.DateUtil;
import com.njwb.util.ValueCheckUtil;

/**管理员界面
 * @author  朱凯
 * No.60
 */
public class AdminView {
	private Logger log = Logger.getLogger(this.getClass());

	private static Scanner scanner = new Scanner(System.in);
	

	@FactoryAnnotation("adminService")
	private AdminService adminService;

	@FactoryAnnotation("flightService")
	private FlightService flightService;

	@FactoryAnnotation("queryView")
	private QueryView queryView;
	


	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}



	public void setFlightService(FlightService flightService) {
		this.flightService = flightService;
	}


	public void setQueryView(QueryView queryView) {
		this.queryView = queryView;
	}





	public void start() {
		mainView();
	}





	/**
	 * 管理员服务主界面（未登陆）
	 */
	private void mainView() {
		int ch;
		while (true) {
			System.out.println("******管理员入口******");
			System.out.println("1.登陆");
			System.out.println("2.退出");
			System.out.println("请选择：");
			ch = ChoiceUtil.getRangeChoice(scanner, 2);
			switch (ch) {
			case 1:
				adminLogin();
				break;
			case 2:
				return;

			}
		}

	}

	/**
	 * 管理员登陆界面
	 */
	private void adminLogin() {
		System.out.println("------->管理员登陆<------");
		System.out.println("请输入管理员用户名:");
		String adminName = scanner.next();
		System.out.println("请输入密码:");
		String password = scanner.next();

		try {
			adminService.adminLogin(adminName, password);
			log.info("[管理员登陆]:" + adminName);
			System.out.println("登陆成功！");
			adminView(adminName);
		} catch (FlightTicketException e) {
			System.out.println(e.getErrorMsg());
			// e.printStackTrace();
		}
	}

	/**
	 * 管理员界面（登录后）
	 * 
	 * @param adminName
	 */
	private void adminView(String adminName) {
		int ch;
		System.out.println("[管理员] "+adminName+" 已登陆，欢迎！");
		while (true) {
			System.out.println("******管理员菜单******");
			System.out.println("1.查询航班");
			System.out.println("2.添加航班");
			System.out.println("3.修改航班");
			System.out.println("4.删除航班");
			System.out.println("5.注销");
			System.out.println("请选择：");

			ch = ChoiceUtil.getRangeChoice(scanner, 5);
			switch (ch) {
			case 1:
				queryView.startAdminIF();
				break;
			case 2:
				addFlight(adminName);
				break;

			case 3:
				changeFlightInfo(adminName);
				break;
			case 4:
				deleteFlight(adminName);
				break;
			case 5:
				System.out.println("注销成功！");
				return;
			}
		}
	}

	private void addFlight(String adminName) {

		try {
			System.out.println("----->添加航班<-----");
			System.out.println("请输入航班号:");
			String flightId=scanner.next();
			while(ValueCheckUtil.matchFlightId(flightId)==false){
				System.out.println("输入的航班号不正确!");
				System.out.println("请重新输入（前两位代码由大写字母组成，后跟3-4位数字）:");
				flightId=scanner.next();
			}
			
			System.out.println("请输入航班名称:");
			String flightName = scanner.next();
			String startPlace, endPlace;
			while (true) {
				System.out.println("请输入出发地:");
				startPlace = scanner.next();
				System.out.println("请输入目的地:");
				endPlace = scanner.next();
				if ((startPlace.equals(endPlace)) == false) {
					break;
				}
				System.out.println("输入有误,出发地不可与目的地相同！");
			}

			Date[] dateArr = getTimeInfo();
			Date takeOffTime = dateArr[0];
			Date arriveTime = dateArr[1];
			Map<Integer, Double> flightCabinMap = addCabin();

			flightService.addFlight(new Flight(flightId,flightName, 
					takeOffTime, arriveTime, startPlace, endPlace), 
					flightCabinMap);
			log.info("[添加航班]"+" adminName:"+adminName+" flightId:"+flightId);
			System.out.println("航班添加成功！");
		} catch (FlightTicketException e) {
			log.error("添加航班错误！ adminName:"+adminName);
			System.out.println(e.getErrorMsg());
			// e.printStackTrace();
		}

	}

	private Date[] getTimeInfo() {
		Date[] dateArr = new Date[2];
		Date takeOffTime;
		Date arriveTime;
		String arriveTimeStr;
		String takeOffTimeStr;
		while (true) {
			System.out.println("请输入出发时间:");
			scanner.nextLine();//用来换行
			takeOffTimeStr = scanner.nextLine();
			while (ValueCheckUtil.matchTimeStr(takeOffTimeStr) == false) {
				System.out.println("出发时间格式有误,请输入正确的时间(yyyy-MM-dd HH:mm:ss):");
				takeOffTimeStr = scanner.nextLine();
			}
			System.out.println("请输入到达时间：");
			arriveTimeStr = scanner.nextLine();
			while (ValueCheckUtil.matchTimeStr(arriveTimeStr) == false) {
				System.out.println("到达时间格式有误,请输入正确的时间(yyyy-MM-dd HH:mm:ss):");
				arriveTimeStr=scanner.nextLine();
			}
			takeOffTime = DateUtil.stringToDate(takeOffTimeStr, "yyyy-MM-dd HH:mm:ss");
			arriveTime = DateUtil.stringToDate(arriveTimeStr, "yyyy-MM-dd HH:mm:ss");
			if (arriveTime.after(takeOffTime) == true) {
				if(arriveTime.after(new Date())==true) {
					
					break;
				}
				System.out.println("出发时间无效，出发时间必须在当前时间之后");
				continue;
			}
			System.out.println("时间输入有误,到达时间必须在出发时间之后！");
		}
		dateArr[0] = takeOffTime;
		dateArr[1] = arriveTime;
		return dateArr;
	}

	private Map<Integer, Double> addCabin() throws FlightTicketException {
		Map<Integer, Double> flightCabinMap = new HashMap<Integer, Double>();
		try {
			Map<Integer, Cabin> cabinMap = flightService.getAllCabins();
			System.out.println("-----------现有舱型----------");
			System.out.println("----------------------------");
			for (int cabinId : cabinMap.keySet()) {
				System.out.println("ID:" + cabinId + " " + "名称:" + cabinMap.get(cabinId).getCabinName() + " " + "座位数:" + cabinMap.get(cabinId).getCabinSeats());
			}
			System.out.println("---------------------------");
			System.out.println("请为该航班配置舱型：");

			int ch;
			boolean flag = false;
			while (flag == false) {
				System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~");
				if (flightCabinMap == null || flightCabinMap.size() == 0) {
					System.out.println("当前未添加任何舱型！");
				} else {
					System.out.println("已添加：");
					for (int cabinId : flightCabinMap.keySet()) {
						System.out.println(cabinId + " " + cabinMap.get(cabinId).getCabinName() + " " + cabinMap.get(cabinId).getCabinSeats() + "座 " + "定价：" + flightCabinMap.get(cabinId) + "元/座");
					}
				}
				System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~");
				System.out.println("请选择:1.添加 2.删除 3.确认");
				ch = ChoiceUtil.getRangeChoice(scanner, 3);
				int cho;
				switch (ch) {
				case 1:
					String priceStr;
					while (true) {
						System.out.println("请输入选择添加的舱型ID:");
						cho = ChoiceUtil.getRangeChoice(scanner, cabinMap.keySet());

						if (flightCabinMap.containsKey(cho) == false) {
							System.out.println("请为本航班该舱位配置价格：");
							priceStr = scanner.next();
							while (ValueCheckUtil.matchPriceStr(priceStr) == false) {
								System.out.println("输入的价格格式有误，请重新输入(正的整数或两位以内小数):");
								priceStr = scanner.next();
							}
							flightCabinMap.put(cho, Double.valueOf(priceStr.toString()));
							System.out.println("添加成功！");
							break;
						}
						System.out.println("该舱型已经被添加，无法再次添加！");
					}
					break;
				case 2:
					if(flightCabinMap.size()==0){
						System.out.println("当前未添加任何舱型！");
						break;
					}
					System.out.println("请输入要移除的舱型Id:");
					cho = ChoiceUtil.getRangeChoice(scanner, flightCabinMap.keySet());
					flightCabinMap.remove(cho);
					System.out.println("移除成功！");
					break;
				case 3:
					if (flightCabinMap.size() == 0) {
						System.out.println("本航班尚未添加任何舱位,无法确认！");
						break;
					} else {
						flag = true;
						break;
					}
				}

			}
		} catch (FlightTicketException e) {
			throw e;
			// e.printStackTrace();
		}
		return flightCabinMap;

	}
	
	/**
	 * 
	 * @param adminName
	 */
	private void changeFlightInfo(String adminName) {
		try {
			System.out.println("请输入要修改航班号:");
			String flightId=scanner.next();
			Map<Flight,List<FlightCabinRelation>> flightInfoMap=
					flightService.getFlightInfoByCondition("flight_id",flightId);
			//航班号不存在，无法修改
			if(flightInfoMap==null||flightInfoMap.size()==0) {
				log.info("[修改航班]"+ " adminName:"+adminName+"  修改失败");
				System.out.println("修改失败，找不到该航班！");
				return;
			}
			List<Flight> flightList=new ArrayList<Flight>(flightInfoMap.keySet());
			Flight flight=flightList.get(0);
			//航班出发后，无法修改
			if(flight.getTakeOffTime().before(new Date())==true) {
				log.info("[修改航班]"+ " adminName:"+adminName+"  修改失败");
				System.out.println("航班已经出发，无法修改！");
				return;
			}
			//航班未出发，但已经有票售出，无法修改
			if(flightService.flightHasSoldTicket(flightId)==true) {
				log.info("[修改航班]"+ " adminName:"+adminName+"  修改失败");
				System.out.println("该航班已经有机票售出，无法更改！");
				return;
			}
			queryView.showFlightInfo(flightInfoMap);
			int cho;
			String flightName=flight.getFlightName();
			String startPlace=flight.getStartPlace();
			String endPlace=flight.getEndPlace();
			Date takeOffTime=flight.getTakeOffTime();
			Date arriveTime=flight.getArriveTime();
			while(true) {
				System.out.println("---------修改航班："+flight.getFlightId()+"---------");
				System.out.println("1.航班名称  2.出发地、目的地  3.起飞、到达时间   4.确认修改");
				System.out.println("请选择:");
				cho=ChoiceUtil.getRangeChoice(scanner, 4);
				switch(cho) {
				case 1:
					System.out.println("请输入新的航班名称：");
					flightName = scanner.next();
					break;
				case 2:
					while (true) {
						System.out.println("请输入出发地:");
						startPlace = scanner.next();
						System.out.println("请输入目的地:");
						endPlace = scanner.next();
						if ((startPlace.equals(endPlace)) == false) {
							break;
						}
						System.out.println("输入有误,出发地不可与目的地相同！");
					}
					break;
				case 3:
					Date[] date=getTimeInfo();
					takeOffTime=date[0];
					arriveTime=date[1];
					break;
				case 4:
					flight.setFlightName(flightName);
					flight.setStartPlace(startPlace);
					flight.setEndPlace(endPlace);
					flight.setTakeOffTime(takeOffTime);
					flight.setArriveTime(arriveTime);
					flightService.changeFlightInfo(flight);
					log.info("[修改航班]"+" adminName:"+adminName+" flightId:"+flightId);
					System.out.println("航班信息修改成功！");
					return;
				}
			}
		} catch (FlightTicketException e) {
			log.error("修改航班失败！ adminName"+adminName);
			System.out.println(e.getErrorMsg());
//			e.printStackTrace();
		}
		
	}
	
	/**
	 * 删除航班
	 * @param adminName
	 */
	private void deleteFlight(String adminName) {
		try {
			System.out.println("请输入要删除的航班号：");
			String flightId=scanner.next();
			Map<Flight, List<FlightCabinRelation>> flightInfoMap;
			flightInfoMap = flightService.getFlightInfoByCondition("flight_id",flightId);
			//航班号不存在,无法删除
			if(flightInfoMap==null||flightInfoMap.size()==0) {
				log.info("[删除航班]"+ " adminName:"+adminName+"  删除失败");
				System.out.println("删除失败，找不到该航班！");
				return;
			}
			List<Flight> flightList=new ArrayList<Flight>(flightInfoMap.keySet());
			Flight flight=flightList.get(0);
			//如果还没到达（已经到达的航班肯定能删除）
			if(flight.getArriveTime().before(new Date())==false) {
				//航班出发后，无法删除
				if(flight.getTakeOffTime().before(new Date())==true) {
					log.info("[删除航班]"+ " adminName:"+adminName+"  删除失败");
					System.out.println("航班已经出发，无法删除！");
					return;
				}
				//还未出发，但是航班已经有票售出，无法删除
				if(flightService.flightHasSoldTicket(flightId)==true) {
					log.info("[删除航班]"+ " adminName:"+adminName+"  删除失败");
					System.out.println("该航班已经有机票售出，无法更改！");
					return;
				}
			}
			
			List<FlightCabinRelation> relationList=flightInfoMap.get(flight);
			flightService.deleteFlight(flight,relationList);
			log.info("[删除航班]"+" adminName:"+adminName+" flightId:"+flightId);
			System.out.println("航班信息删除成功！");
		
		} catch (FlightTicketException e) {
			log.error("删除航班错误！ adminName"+adminName);
			System.out.println(e.getErrorMsg());
//			e.printStackTrace();
		}
		
	}
}
