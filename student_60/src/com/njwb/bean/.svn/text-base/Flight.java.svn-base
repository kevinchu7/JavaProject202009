package com.njwb.bean;

import java.util.Date;

public class Flight {
	/** 航班id */
	private String flightId;
	/** 航班名称 */
	private String flightName;
	/** 起飞时间 */
	private Date takeOffTime;
	/** 到达时间 */
	private Date arriveTime;
	/** 出发地 */
	private String startPlace;
	/** 目的地 */
	private String endPlace;
	/** 生效状态 */
	private int effectStatus;
	/** 创建时间 */
	private Date createTime;

	public Flight() {
		super();
	}

	public Flight(String flightId, String flightName, Date takeOffTime, Date arriveTime, String startPlace,
			String endPlace) {
		super();
		this.flightId = flightId;
		this.flightName = flightName;
		this.takeOffTime = takeOffTime;
		this.arriveTime = arriveTime;
		this.startPlace = startPlace;
		this.endPlace = endPlace;
	}

	public Flight(String flightId, String flightName, Date takeOffTime, Date arriveTime, String startPlace,
			String endPlace, int effectStatus, Date createTime) {
		super();
		this.flightId = flightId;
		this.flightName = flightName;
		this.takeOffTime = takeOffTime;
		this.arriveTime = arriveTime;
		this.startPlace = startPlace;
		this.endPlace = endPlace;
		this.effectStatus = effectStatus;
		this.createTime = createTime;
	}

	public String getFlightId() {
		return flightId;
	}

	public void setFlightId(String flightId) {
		this.flightId = flightId;
	}

	public String getFlightName() {
		return flightName;
	}

	public void setFlightName(String flightName) {
		this.flightName = flightName;
	}

	public Date getTakeOffTime() {
		return takeOffTime;
	}

	public void setTakeOffTime(Date takeOffTime) {
		this.takeOffTime = takeOffTime;
	}

	public Date getArriveTime() {
		return arriveTime;
	}

	public void setArriveTime(Date arriveTime) {
		this.arriveTime = arriveTime;
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

	public int getEffectStatus() {
		return effectStatus;
	}

	public void setEffectStatus(int effectStatus) {
		this.effectStatus = effectStatus;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	
	
	@Override
	public boolean equals(Object obj) {
		//自反性
        if (this == obj) return true;
        //任何对象不等于null，比较是否为同一类型
        if (!(obj instanceof Flight)) return false;
        //强制类型转换
        Flight flight = (Flight) obj;
        //比较属性值
        return flightId.equals(flight.getFlightId());
	}

	
	@Override
	public String toString() {
		return "Flight [flightId=" + flightId + ", flightName=" + flightName + ", takeOffTime=" + takeOffTime
				+ ", arriveTime=" + arriveTime + ", startPlace=" + startPlace + ", endPlace=" + endPlace
				+ ", effectStatus=" + effectStatus + ", createTime=" + createTime + "]";
	}
	
	
}
