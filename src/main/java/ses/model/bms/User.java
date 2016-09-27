package ses.model.bms;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import ses.model.oms.Orgnization;

/**
 * Description: 用户实体
 *
 * @author Ye MaoLin
 * @version 2016-9-13
 * @since JDK1.7
 */
public class User implements Serializable{
	
	private static final long serialVersionUID = 1L;

	/** 主键 */
    private String id;

    /** 用户名 */
    private String loginName;

    /** 密码 */
    private String password;

    /** 创建时间 */
    private Date createdAt;

    /** 
     * 删除标识
     * 0：未删除
     * 1：删除
     */
    private Integer isDeleted;

    /** 真实姓名 */
    private String relName;

    /** 修改时间 */
    private Date updatedAt;

    /** 创建人 */
    private User user;

    /** 手机 */
    private String mobile;

    /** 
     * 性别
     * F：女
     * M：男
     */
    private String gender;

    /** 座机电话 */
    private String telephone;

    /** 邮箱 */
    private String email;

    /** 所属机构 */
    private Orgnization org;

    /** 职务 */
    private String duties;

    /** 
     * 用户类型
     * 0:采购管理部门
     * 1:采购机构
     * 2:需求部门
     * 3:其他人员
     * 4：供应商
     * 5：专家
     * 6：进口供应商
     */
    private Integer typeName;

    /** 关联用户ID */
    private String typeId;
    
    /** 详细地址 */
    private String address;
    
    /** 随机码 */
    private String randomCode;
    
    /** 所属角色 */
    private List<Role> roles;
    
    private List<PreMenu> menus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
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

    public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone == null ? null : telephone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Orgnization getOrg() {
		return org;
	}

	public void setOrg(Orgnization org) {
		this.org = org;
	}

	public String getDuites() {
        return duties;
    }

    public void setDuites(String duties) {
        this.duties = duties == null ? null : duties.trim();
    }

    public Integer getTypeName() {
        return typeName;
    }

    public void setTypeName(Integer typeName) {
        this.typeName = typeName;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId == null ? null : typeId.trim();
    }

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDuties() {
		return duties;
	}

	public void setDuties(String duties) {
		this.duties = duties;
	}

	public String getRandomCode() {
		return randomCode;
	}

	public void setRandomCode(String randomCode) {
		this.randomCode = randomCode;
	}

	public List<PreMenu> getMenus() {
		return menus;
	}

	public void setMenus(List<PreMenu> menus) {
		this.menus = menus;
	}
	
}