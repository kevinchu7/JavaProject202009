package com.njwb.bean;

import java.util.Date;

public class Cabin {
	/**舱型号*/
	private int cabinId;
	/**舱型名称*/
	private String cabinName;
	/**舱型座位总数*/
	private int cabinSeats;
	/**座位行数*/
	private int seatLines;
	/**单行的座位数*/
	private int oneLineSeats;
	/**创建时间*/
	private Date createTime;
	public Cabin() {
		super();
	}
	public Cabin(int cabinId, String cabinName, int cabinSeats, int seatLines, int oneLineSeats, Date createTime) {
		super();
		this.cabinId = cabinId;
		this.cabinName = cabinName;
		this.cabinSeats = cabinSeats;
		this.seatLines = seatLines;
		this.oneLineSeats = oneLineSeats;
		this.createTime = createTime;
	}
	public int getCabinId() {
		return cabinId;
	}
	public void setCabinId(int cabinId) {
		this.cabinId = cabinId;
	}
	public String getCabinName() {
		return cabinName;
	}
	public void setCabinName(String cabinName) {
		this.cabinName = cabinName;
	}
	public int getCabinSeats() {
		return cabinSeats;
	}
	public void setCabinSeats(int cabinSeats) {
		this.cabinSeats = cabinSeats;
	}
	public int getSeatLines() {
		return seatLines;
	}
	public void setSeatLines(int seatLines) {
		this.seatLines = seatLines;
	}
	public int getOneLineSeats() {
		return oneLineSeats;
	}
	public void setOneLineSeats(int oneLineSeats) {
		this.oneLineSeats = oneLineSeats;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Override
	public String toString() {
		return "Cabin [cabinId=" + cabinId + ", cabinName=" + cabinName + ", cabinSeats=" + cabinSeats + ", createTime=" + createTime + ", oneLineSeats=" + oneLineSeats + ", seatLines=" + seatLines
				+ "]";
	}
	
}
