package common.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @ClassName: LoginLog
 * @Description: 登录日志实体封装
 * @author Easong
 * @date 2017年5月3日 下午6:22:19
 * 
 */
public class LoginLog implements Serializable{

	/** 
	* @Fields serialVersionUID : 
	*/ 
	private static final long serialVersionUID = 1L;

	/** 主键 **/
	private String id;

	/** 登录人Id **/
	private String userId;

	/** 登录名称 **/
	private String name;

	/** 1：专家  2：供应商 3：后台管理员 **/
	private Integer type;

	/** 登录人IP **/
    private String ip;

	/** 0：未删除 1：删除 **/
    private Integer isDeleted;
    
    /** 登录时间 **/
    private Date createdAt;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	private String remark;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
	
}
