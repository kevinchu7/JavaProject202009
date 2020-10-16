package com.njwb.bean;

import java.util.Date;

public class Order {
	/**订单id*/
	private int orderId;
	/**航班id*/
	private String flightId;
	/**出发地*/
	private String startPlace;
	/**目的地*/
	private String endPlace;
	/**起飞时间*/
	private Date takeOffTime;
	/**舱位id*/
	private int cabinId;
	/**座位id*/
	private String seatId;
	/**票价*/
	private double price;
	/**乘客姓名*/
	private String realName;
	/**身份证号*/
	private String idCardNo;
	/**创建时间*/
	private Date createTime;
	/**生效状态*/
	private int effectStatus;
	public Order() {
		super();
	}
	
	
	
	public Order(String flightId, String startPlace, String endPlace, Date takeOffTime, int cabinId, String seatId,
			double price, String realName, String idCardNo) {
		super();
		this.flightId = flightId;
		this.startPlace = startPlace;
		this.endPlace = endPlace;
		this.takeOffTime = takeOffTime;
		this.cabinId = cabinId;
		this.seatId = seatId;
		this.price = price;
		this.realName = realName;
		this.idCardNo = idCardNo;
	}



	
	public Order(int orderId, String flightId, String startPlace, String endPlace, Date takeOffTime, int cabinId,
			String seatId, double price, String realName, String idCardNo, Date createTime, int effectStatus) {
		super();
		this.orderId = orderId;
		this.flightId = flightId;
		this.startPlace = startPlace;
		this.endPlace = endPlace;
		this.takeOffTime = takeOffTime;
		this.cabinId = cabinId;
		this.seatId = seatId;
		this.price = price;
		this.realName = realName;
		this.idCardNo = idCardNo;
		this.createTime = createTime;
		this.effectStatus = effectStatus;
	}



	public int getOrderId() {
		return orderId;
	}



	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}



	public String getFlightId() {
		return flightId;
	}



	public void setFlightId(String flightId) {
		this.flightId = flightId;
	}



	public String getStartPlace() {
		return startPlace;
	}



	public void setStartPlace(String startPlace) {
		this.startPlace = startPlace;
	}



	public String getEndPlace() {
		return endPlace;
	}



	public void setEndPlace(String endPlace) {
		this.endPlace = endPlace;
	}



	public Date getTakeOffTime() {
		return takeOffTime;
	}



	public void setTakeOffTime(Date takeOffTime) {
		this.takeOffTime = takeOffTime;
	}



	public int getCabinId() {
		return cabinId;
	}



	public void setCabinId(int cabinId) {
		this.cabinId = cabinId;
	}



	public String getSeatId() {
		return seatId;
	}



	public void setSeatId(String seatId) {
		this.seatId = seatId;
	}



	public double getPrice() {
		return price;
	}



	public void setPrice(double price) {
		this.price = price;
	}



	public String getRealName() {
		return realName;
	}



	public void setRealName(String realName) {
		this.realName = realName;
	}



	public String getIdCardNo() {
		return idCardNo;
	}



	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}



	public Date getCreateTime() {
		return createTime;
	}



	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}



	public int getEffectStatus() {
		return effectStatus;
	}



	public void setEffectStatus(int effectStatus) {
		this.effectStatus = effectStatus;
	}



	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", flightId=" + flightId + ", startPlace=" + startPlace + ", endPlace="
				+ endPlace + ", takeOffTime=" + takeOffTime + ", cabinId=" + cabinId + ", seatId=" + seatId + ", price="
				+ price + ", realName=" + realName + ", idCardNo=" + idCardNo + ", createTime=" + createTime
				+ ", effectStatus=" + effectStatus + "]";
	}
	
	
	
	
	
	
	
	

}
