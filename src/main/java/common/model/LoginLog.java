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
	private String name;

	/** 1：后台 2：供应商 3：专家 **/
	private Integer type;

	/** 登录人IP **/
    private String ip;

    /** 登录时间 **/
    private Date loginAt;
    
	/** 0：未删除 1：删除 **/
    private Integer isDeleted;

	private String remark;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Date getLoginAt() {
		return loginAt;
	}

	public void setLoginAt(Date loginAt) {
		this.loginAt = loginAt;
	}
	
}
