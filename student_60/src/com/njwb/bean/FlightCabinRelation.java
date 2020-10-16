package com.njwb.bean;

import java.util.Date;

public class FlightCabinRelation {
	/**航班号*/
	private String flightId;
	/**舱型号*/
	private int cabinId;
	/**该航班的该舱型的座位单价*/
	private double price;
	/**余票*/
	private int restTickets;
	/**创建时间*/
	private Date createTime;
	/**有效状态*/
	private int effectStatus;
	public FlightCabinRelation() {
		super();
	}
	
	public FlightCabinRelation(String flightId, int cabinId, double price, int restTickets) {
		super();
		this.flightId = flightId;
		this.cabinId = cabinId;
		this.price = price;
		this.restTickets = restTickets;
	}

	public FlightCabinRelation(String flightId, int cabinId, double price, int restTickets, Date createTime, int effectStatus) {
		super();
		this.flightId = flightId;
		this.cabinId = cabinId;
		this.price = price;
		this.restTickets = restTickets;
		this.createTime = createTime;
		this.effectStatus = effectStatus;
	}
	
	
	
	public String getFlightId() {
		return flightId;
	}
	public void setFlightId(String flightId) {
		this.flightId = flightId;
	}
	public int getCabinId() {
		return cabinId;
	}
	public void setCabinId(int cabinId) {
		this.cabinId = cabinId;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getRestTickets() {
		return restTickets;
	}
	public void setRestTickets(int restTickets) {
		this.restTickets = restTickets;
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
		return "FlightCabinRelation [cabinId=" + cabinId + ", createTime=" + createTime + ", effectStatus=" + effectStatus + ", flightId=" + flightId + ", price=" + price + ", restTickets="
				+ restTickets + "]";
	}
	
	
	

}
