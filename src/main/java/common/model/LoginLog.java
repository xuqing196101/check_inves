package common.model;

import java.util.Date;

/**
 * 
 * @ClassName: LoginLog
 * @Description: 登录日志实体封装
 * @author Easong
 * @date 2017年5月3日 下午6:22:19
 * 
 */
public class LoginLog {

	/** 主键 **/
	private String id;

	/** 登录人Id **/
	private String loginId;

	/** 登录名称 **/
	private String loginName;

	/** 1：后台 2：供应商 3：专家 **/
	private Integer loginType;

	/** 登录人IP **/
	private String loginIp;

	/** 登录时间 **/
	private Date loginTime;

	/** 0：未删除 1：删除 **/
	private Integer isDeleted;

	private String remark;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public Integer getLoginType() {
		return loginType;
	}

	public void setLoginType(Integer loginType) {
		this.loginType = loginType;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
