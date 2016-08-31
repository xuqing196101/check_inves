package yggc.model.bms;

import java.util.Date;
import java.util.List;

/**
* <p>Title:User </p>
* <p>Description:用户实体类 </p>
* <p>Company: yggc </p> 
* @author yyyml
* @date 2016-7-27下午4:53:36
*/
public class User {
	
	
    /**
     * @Fields id : 主键
     */
    private Integer id;

    /**
     * @Fields loginName : 用户名
     */
    private String loginName;

    /**
     * @Fields password : 密码
     */
    private String password;

    /**
     * @Fields createdAt : 创建日期
     */
    private Date createdAt;

    /**
     * @Fields isDeleted : 删除状态 0：未删除，1：已删除
     */
    private Integer isDeleted;

    /**
     * @Fields relName : 真实姓名
     */
    private String relName;

    /**
     * @Fields updatedAt : 更新日期
     */
    private Date updatedAt;

    /**
     * @Fields phone : 联系电话
     */
    private String phone;
    
    /**
     * @Fields creater : 创建人
     */
    private User creater;
    
    private List<Role> roles;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getRelName() {
        return relName;
    }

    public void setRelName(String relName) {
        this.relName = relName == null ? null : relName.trim();
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public User getCreater() {
        return creater;
    }

    public void setCreater(User creater) {
        this.creater = creater;
    }

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}