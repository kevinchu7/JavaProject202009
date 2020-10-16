package com.njwb.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import org.apache.log4j.Logger;

import com.njwb.bean.Flight;
import com.njwb.bean.FlightCabinRelation;
import com.njwb.bean.Order;
import com.njwb.bean.User;
import com.njwb.exception.FlightTicketException;
import com.njwb.factory.annotation.FactoryAnnotation;
import com.njwb.service.FlightService;
import com.njwb.service.OrderService;
import com.njwb.service.UserService;
import com.njwb.util.ChoiceUtil;
import com.njwb.util.DateUtil;
import com.njwb.util.ValueCheckUtil;



/**用户界面
 * @author  朱凯
 * No.60
 */
public class UserView {
	private Logger log = Logger.getLogger(this.getClass());
	
	private static Scanner scanner = new Scanner(System.in);
	
	
	@FactoryAnnotation("userService")
	private UserService userService;
	

	@FactoryAnnotation("flightService")
	private FlightService flightService;
	
	@FactoryAnnotation("orderService")
	private OrderService orderService;
	
	@FactoryAnnotation("queryView")
	private QueryView queryView;
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}


	public void setFlightService(FlightService flightService) {
		this.flightService = flightService;
	}


	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}


	public void setQueryView(QueryView queryView) {
		this.queryView = queryView;
	}


	public void start() {
		mainView();
	}

	/**
	 * 用户服务主界面（未登陆）
	 */
	private void mainView() {
		int ch;
		while (true) {
			System.out.println("******用户服务******");
			System.out.println("1.注册");
			System.out.println("2.登陆");
			System.out.println("3.查询航班");
			System.out.println("4.退出");
			System.out.println("请选择：");

			ch = ChoiceUtil.getRangeChoice(scanner, 4);
			switch (ch) {
			case 1:
				userRegiseter();
				break;
			case 2:
				userLogin();
				break;
			case 3:
				queryView.start();
				break;
			case 4:
				return;
			}
		}

	}

	/**
	 * 用户登陆界面
	 */
	private void userLogin() {
		System.out.println("------->用户登陆<------");
		System.out.println("请输入用户名:");
		String userName = scanner.next();
		System.out.println("请输入密码:");
		String password = scanner.next();

		try {
			userService.userLogin(userName, password);
			System.out.println("登陆成功！");
			log.info("[用户登陆]"+" userName:"+userName);
			userView(userName);
		} catch (FlightTicketException e) {
			System.out.println(e.getErrorMsg());
			// e.printStackTrace();
		}

	}

	/**
	 * 用户注册界面
	 */
	private void userRegiseter() {
		System.out.println("------->用户注册<------");
		System.out.println("请输入用户名:");
		String userName = scanner.next();
		while (ValueCheckUtil.matchUserName(userName) == false) {
			System.out.println("用户名格式错误，请输入6-16位字母、数字或下划线，不能以数字打头:");
			userName = scanner.next();
		}

		String password = getPassword();

		System.out.println("请选择性别：1.男  2.女");
		int ch = ChoiceUtil.getRangeChoice(scanner, 2);
		System.out.println("请输入身份证号:");
		String idCardNo = scanner.next();
		while (ValueCheckUtil.matchIdCardNo(idCardNo) == false) {
			System.out.println("身份证号格式错误，请输入正确的身份证号：");
			idCardNo = scanner.next();
		}

		System.out.println("请输入真实姓名：");
		String realName = scanner.next();
		while (ValueCheckUtil.matchRealName(realName) == false) {
			System.out.println("姓名格式错误，请输入正确的姓名：");
			realName = scanner.next();
		}

		String sex;
		if (ch == 1) {
			sex = "男";
		} else {
			sex = "女";
		}
		System.out.println("请输入手机号：");
		String phone = scanner.next();
		while (ValueCheckUtil.matchPhoneNumber(phone) == false) {
			System.out.println("手机号格式不正确，请输入正确的手机号码：");
			phone = scanner.next();
		}

		try {
			userService.userRegister(new User(userName, password, idCardNo, realName, sex, phone));
			log.info("[用户注册]"+" userName:"+userName);
			System.out.println("注册成功！");
		} catch (FlightTicketException e) {
			System.out.println(e.getErrorMsg());
			// e.printStackTrace();
		}

	}

	/**
	 * 用户界面（登陆后）
	 * @param userName
	 */
	private void userView(String userName) {
		int ch;
		System.out.println("[用户] "+userName+" 已登陆，欢迎！");
		while (true) {
			System.out.println("******用户菜单******");
			System.out.println("1.查询航班");
			System.out.println("2.预订机票");
			System.out.println("3.机票改签");
			System.out.println("4.申请退票");
			System.out.println("5.修改个人信息");
			System.out.println("6.注销");
			System.out.println("请选择：");

			ch = ChoiceUtil.getRangeChoice(scanner, 6);
			switch (ch) {
			case 1:
				queryView.start();
				break;
			case 2:
				orderTicket(userName);
				break;
			case 3:
				changeOrder(userName);
				break;
			case 4:
				refoundOrder(userName);
				break;
			case 5:
				userChangeInfo(userName);
				break;
			case 6:
				System.out.println("注销成功！");
				return;
			}
		}
	}

	/**
	 * 修改信息主界面
	 * @param userName
	 */
	private void userChangeInfo(String userName) {
		int ch;
		try {
			while (true) {
				User user = userService.getUserInfo(userName);
				System.out.println("-------->用户信息<--------");
				System.out.println("用户名:" + user.getUserName());
				System.out.println("身份证号:" + user.getIdCardNo());
				System.out.println("真实姓名:" + user.getRealName());
				System.out.println("性别:" + user.getSex());
				System.out.println("手机号:" + user.getPhone());
				System.out.println("-------------------------");
				System.out.println("请选择：1.修改信息 2.退出");
				ch = ChoiceUtil.getRangeChoice(scanner, 2);
				switch (ch) {
				case 1:
					userChangeInfoChildView(user);
					break;
				case 2:
					return;
				}
			}
		} catch (FlightTicketException e) {
			System.out.println(e.getErrorMsg());
//			e.printStackTrace();
		}
	}

	/**
	 * 修改信息子界面
	 * @param user
	 * @throws FlightTicketException
	 */
	private void userChangeInfoChildView(User user) throws FlightTicketException {
		int ch;
		String sex = user.getSex();
		String password = user.getPassword();
		String phone = user.getPhone();
		while (true) {
			System.out.println("------->修改信息<--------");
			System.out.println("1.修改注册性别 2.修改密码 3.修改手机号 4.确认");
			System.out.println("请选择：");
			ch = ChoiceUtil.getRangeChoice(scanner, 4);
			switch (ch) {
			case 1:
				System.out.println("请选择性别：1.男  2.女");
				int cho = ChoiceUtil.getRangeChoice(scanner, 2);
				if (cho == 1) {
					sex = "男";
				} else {
					sex = "女";
				}
				break;
			case 2:
				password = getPassword();
				break;
			case 3:
				System.out.println("请输入手机号：");
				phone = scanner.next();
				while (ValueCheckUtil.matchPhoneNumber(phone) == false) {
					System.out.println("手机号格式不正确，请输入正确的手机号码：");
					phone = scanner.next();
				}
				break;
			case 4:
				try {
					userService.userChangeInfo(user.getUserName(), sex, password, phone);
				} catch (FlightTicketException e) {
					//子页面不处理，直接抛出，交给主页面处理
					throw e;
					// e.printStackTrace();
				}
				log.info("[用户修改信息]"+" userName:"+user.getUserName());
				System.out.println("信息修改成功！");
				return;

			}

		}
	}

	/**
	 * 获取密码界面
	 * @return
	 */
	private String getPassword() {
		String password = null;
		String checkPassword;
		boolean flag = false;
		while (flag == false) {
			System.out.println("请输入密码:");
			password = scanner.next();
			while (ValueCheckUtil.matchPassword(password) == false) {
				System.out.println("密码格式错误，请输入6-16位字母、数字或下划线:");
				password = scanner.next();
			}
			System.out.println("请确认密码：");
			checkPassword = scanner.next();
			if (checkPassword.equals(password)) {
				flag = true;
			} else {
				System.out.println("两次输入密码不一致！");
			}
		}
		return password;
	}
	
	
	
	private void orderTicket(String userName) {
		try {
			System.out.println("请输入您要预定航班的航班号：");
			String flightId=scanner.next();
			Map<Flight,List<FlightCabinRelation>> flightInfoMap=
					flightService.getNoTakeOffFlightInfoByCondition("flight_id", flightId);
			if(flightInfoMap==null||flightInfoMap.size()==0) {
				log.info("[用户订票]: "+userName+" 订票失败  航班号不存在");
				System.out.println("订票失败：找不到该航班号!");
				return;
			}
			//map中只有一个映射，取出来
			List<Flight> flightList=new ArrayList<Flight>(flightInfoMap.keySet());
			Flight flight=flightList.get(0);
			if(DateUtil.getDateInterval(flight.getTakeOffTime(), new Date())<30) {
				log.info("[用户订票]: "+userName+" 订票失败  航班即将起飞");
				System.out.println("抱歉，该航班即将起飞，无法订票！");
				return;
			}
			System.out.println("——————————————————————————————————————"
					+ "——————————————————————————————————————");
			System.out.println(flightId+"  "+flight.getFlightName()
					+"  From "+flight.getStartPlace()+" To "+flight.getEndPlace()
					+"\n出发时间:"+DateUtil.dateToString(null, flight.getTakeOffTime())
					+"  到达时间:"+DateUtil.dateToString(null, flight.getArriveTime()));
			System.out.println("——————————————————————————————————————"
					+ "——————————————————————————————————————");
			List<FlightCabinRelation> relationList=flightInfoMap.get(flight);
			for(int i=0;i<relationList.size();i++) {
				FlightCabinRelation relation=relationList.get(i);
				System.out.println((i+1)+". "+
						flightService.getCabinNameByCabinId(relation.getCabinId())+" "
						+ relation.getPrice()+" RMB/座  余票："+relation.getRestTickets());
			}
			int cancelNo=relationList.size()+1;
			System.out.println(cancelNo+". 取消");
			System.out.println("请选择要订购的座位舱位或取消：");
			int cho=ChoiceUtil.getRangeChoice(scanner, cancelNo);
			if(cho==cancelNo) {
				log.info("[用户订票]: "+userName+" 订票失败 取消订票");
				System.out.println("您已取消本次操作！");
				return;
			}
			int index=cho-1;
			FlightCabinRelation relation=relationList.get(index);
			if(relation.getRestTickets()<1) {
				log.info("[用户订票]: "+userName+" 订票失败 座位售空");
				System.out.println("该舱位的座位已经售空！");
				return;
			}
			
			List<String> seatIdList=flightService.getUnsoldSeat(flightId, relation.getCabinId());
			String seatId=chooseSeat(seatIdList);
			
			if(payForTicket(relation.getPrice())==false) {
				log.info("[用户订票]: "+userName+" 订票失败 支付取消");
				System.out.println("您取消了支付！");
				return;
			}
			log.info("[用户订票]: "+userName+" 订票成功 "+flightId+" "
					+relation.getCabinId());
			
			Order order=new Order(flightId, flight.getStartPlace(), flight.getEndPlace(), 
					flight.getTakeOffTime(), relation.getCabinId(), seatId, 
					relation.getPrice(), 
					userService.getUserInfo(userName).getRealName(), 
					userService.getUserInfo(userName).getIdCardNo());
			
			orderService.addOrder(order);
			log.info("[预订机票]  userName:"+userName+" flightId:"
					+flightId+" seatID"+seatId);
			System.out.println("订票成功！");		
					
		} catch (FlightTicketException e) {
			log.error("修改航班失败！ userName:"+userName);
			System.out.println(e.getErrorMsg());
//			e.printStackTrace();
		}
		
		
	}
	
	
	
	
	private String chooseSeat(List<String> seatIdList) {
		int cho;
		System.out.println("----------------");
		for(int i=0;i<seatIdList.size();i++) {
			System.out.println((i+1)+" . "+seatIdList.get(i));
		}
		System.out.println("请挑选座位号:");
		cho=ChoiceUtil.getRangeChoice(scanner, seatIdList.size());
		return seatIdList.get(cho-1);
		
	}
	
	/**
	 * 改签
	 * @param userName
	 */
	private void changeOrder(String userName) {
		int cho;
		String IdCardNo;
		try {
			IdCardNo = userService.getUserInfo(userName).getIdCardNo();
			Map<Integer,Order> orderMap=orderService.getNoStartOrderByIdCard(IdCardNo);
			
			if(orderMap==null||orderMap.size()==0) {
				System.out.println("您当前没有等待出发的航班机票订单！");
//				log.info("[机票改签]  "+userName+"  改签失败");
				return;
			}
			showOrder(orderMap);
			System.out.println("请输入选择改签的订单号：");
			int orderId=ChoiceUtil.getRangeChoice(scanner, orderMap.keySet());
			Order order=orderMap.get(orderId);
			
			//起飞前2小时无法改签
			if(DateUtil.getDateInterval(order.getTakeOffTime(),new Date())<=120) {
				log.info("[机票改签]  "+userName+"  改签失败");
				System.out.println("改签失败，航班即将起飞，航班起飞前两小时起不能改签");
				return;
			}
			//初始舱位、座位号
			int initCabinId=order.getCabinId();
			String initSeatId=order.getSeatId();
			while(true) {
				System.out.println("----------航班改签---------------");
				System.out.println("1.升舱改换座位 2.取消");
				System.out.println("请选择:");
				cho=ChoiceUtil.getRangeChoice(scanner, 2);
				switch(cho) {
				case 1:
					Map<Flight,List<FlightCabinRelation>> flightInfoMap=
						flightService.getNoTakeOffFlightInfoByCondition("flight_id", 
						order.getFlightId());
					//map中只有一个映射，取出来
					List<Flight> flightList=new ArrayList<Flight>(flightInfoMap.keySet());
					Flight flight=flightList.get(0);
					
					List<FlightCabinRelation> relationList=flightInfoMap.get(flight);
					for(int i=0;i<relationList.size();i++) {
						FlightCabinRelation relation=relationList.get(i);
						System.out.println((i+1)+". "+
								flightService.getCabinNameByCabinId(relation.getCabinId())+" "
								+ relation.getPrice()+" RMB/座  余票："+relation.getRestTickets());
					}
					int cancelNo=relationList.size()+1;
					System.out.println(cancelNo+". 取消");
					System.out.println("请选择要改订的座位舱位或取消：");
					int choi=ChoiceUtil.getRangeChoice(scanner, cancelNo);
					if(choi==cancelNo) {
						log.info("[用户改签]: "+userName+" 改签失败 改签取消");
						System.out.println("您已取消本次操作！");
						return;
					}
					int index=choi-1;
					FlightCabinRelation relation=relationList.get(index);
					
					if(relation.getRestTickets()<1) {
						log.info("[用户改签]: "+userName+" 改签失败 座位售空");
						System.out.println("该舱位的座位已经售空！");
						return;
					}
					
					List<String> seatIdList=flightService.getUnsoldSeat(order.getFlightId(), 
							relation.getCabinId());
					String seatId=chooseSeat(seatIdList);
					
					if(payForTicket(relation.getPrice())==false) {
						log.info("[用户改签]: "+userName+" 改签失败 支付取消");
						System.out.println("您取消了支付！");
						return;
					}
					
					
					order.setCabinId(relation.getCabinId());
					order.setSeatId(seatId);
					order.setPrice(relation.getPrice());
					
					orderService.updateOrder(order,initCabinId,initSeatId);
					
					log.info("[用户改签]: "+userName+" 改签成功 "+order.getFlightId()+" "
							+relation.getCabinId()+" "+seatId);
					System.out.println("改签成功！");
					return;
				case 2:
					return;
				}
			}
			
		} catch (FlightTicketException e) {
			log.error("改签失败!  userName:"+userName);
			System.out.println(e.getErrorMsg());
//			e.printStackTrace();
		}
		
		
	}
	
	
	/**
	 * 申请退票
	 */
	private void refoundOrder(String userName) {
		int cho;
		String IdCardNo;
		try {
			IdCardNo = userService.getUserInfo(userName).getIdCardNo();
			Map<Integer,Order> orderMap=orderService.getNoStartOrderByIdCard(IdCardNo);
			
			if(orderMap==null||orderMap.size()==0) {
				System.out.println("您当前没有等待出发的航班机票订单！");
//			log.info("[退订机票]  "+userName+"  退订失败");
				return;
			}
			showOrder(orderMap);
			System.out.println("请输入选择退订的订单号：");
			int orderId=ChoiceUtil.getRangeChoice(scanner, orderMap.keySet());
			Order order=orderMap.get(orderId);
			
			//起飞前2小时无法退票
			if(DateUtil.getDateInterval(order.getTakeOffTime(),new Date())<=120) {
				log.info("[退订机票]  "+userName+"  退订失败");
				System.out.println("退票失败，航班即将起飞，航班起飞前两小时起不能申请退票");
				return;
			}
			
			System.out.println("您正在办理退票业务，请确认是否退订"+orderId+"号订单？");
			System.out.println("请选择： 1.确认  2.取消");
			cho=ChoiceUtil.getRangeChoice(scanner, 2);
			if(cho==2) {
				log.info("[退订机票]  "+userName+"  退订取消");
				System.out.println("退票业务已取消！");
				return;
			}
			orderService.refoundOrder(order);
			System.out.println("退订机票成功！扣除票价费5%的手续费，剩余的机票费用已返回！");
			
		} catch (FlightTicketException e) {
			log.error("退票失败!  userName:"+userName);
			System.out.println(e.getErrorMsg());
//			e.printStackTrace();
		}
		
		
		
		
		
	}
	
	/**
	 * 显示订单信息
	 * @param orderMap
	 * @throws FlightTicketException
	 */
	private void showOrder(Map<Integer,Order> orderMap) throws FlightTicketException {
		
		System.out.println("待出发的机票订单：");
		for(int orderId:orderMap.keySet()) {
			Order order=orderMap.get(orderId);
			System.out.println("——————————————————————————————————"
					+ "————————————————————————————————————————");
			
			System.out.println("订单号："+orderId);
			System.out.println(order.getFlightId()+"  "+order.getStartPlace()+
					" TO "+order.getEndPlace()+" 出发时间:"+
					DateUtil.dateToString(null, order.getTakeOffTime()));
			System.out.println("舱位："+flightService.getCabinNameByCabinId(order.getCabinId())+" "
					+"座位号："+order.getSeatId()+"支付金额："+order.getPrice());
			System.out.println("姓名："+order.getRealName()+"  身份证号："+order.getIdCardNo());
	
			
			System.out.println("———————————————————————————————————"
					+ "———————————————————————————————————————");
		}
	}
	
	
	
	
	/**
	 * 简单支付界面（写在这里纯属模拟！！！）
	 * @param price
	 * @return
	 */
	private boolean payForTicket(double price) {
		System.out.println("本次订单合计 "+price+"元，请扫描下方二维码完成支付：");
		System.out.println("");
		System.out.println("回回————回回");
		System.out.println("回回-**-回回");
		System.out.println("||[支付宝]||");
		System.out.println("回回-**-口**");
		System.out.println("回回————**口");
		System.out.println("");
		System.out.println("支付是否已完成？：1.完成  2.取消");
		int cho=ChoiceUtil.getRangeChoice(scanner, 2);
		if(cho==1) {
			return true;
		}
		return false;
	}
}
