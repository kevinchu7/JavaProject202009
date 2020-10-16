package com.njwb.bean;

import java.util.Date;

public class Admin {
	/**管理员id*/
	private int adminId;
	/**管理员用户名*/
	private String adminName;
	/**密码*/
	private String password;
	/**身份证号*/
	private String idCardNo;
	/**真实姓名*/
	private String realName;
	/**创建时间*/
	private Date createTime;
	
	public Admin() {
		super();
	}

	public Admin(int adminId, String adminName, String password, String idCardNo, String realName, Date createTime) {
		super();
		this.adminId = adminId;
		this.adminName = adminName;
		this.password = password;
		this.idCardNo = idCardNo;
		this.realName = realName;
		this.createTime = createTime;
	}

	public int getAdminId() {
		return adminId;
	}

	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getIdCardNo() {
		return idCardNo;
	}

	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "Admin [adminId=" + adminId + ", adminName=" + adminName + ", password=" + password + ", idCardNo="
				+ idCardNo + ", realName=" + realName + ", createTime=" + createTime + "]";
	}
	
	
	

}
