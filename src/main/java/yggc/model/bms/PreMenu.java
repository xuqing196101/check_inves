package yggc.model.bms;

import java.util.Date;
import java.util.List;

public class PreMenu {
    /**
     * @Fields id :主键 
     */
    private Integer id;

    /**
     * @Fields name :名称 
     */
    private String name;

    /**
     * @Fields type :类型
     * navigation:导航
	 * accordion：折叠导航
	 * menu:菜单  
	 * button:按钮 
     */
    private String type;

    /**
     * @Fields isDeleted : 删除标志  
     * 0：未删除，1：删除
     */
    private Integer isDeleted;

    /**
     * @Fields state :状态 
     * 0：可用，1：暂停
     */
    private Integer state;

    /**
     * @Fields url : 路径
     */
    private String url;

    /**
     * @Fields createdAt : 创建时间
     */
    private Date createdAt;

    /**
     * @Fields orderby :排序 
     */
    private Integer orderby;

    /**
     * @Fields menulevel : 菜单级别
     */
    private Integer menulevel;

    /**
     * @Fields parentId :上级菜单 
     */
    private PreMenu parentId;
    
    private List<Role> roles;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Integer getOrderby() {
		return orderby;
	}

	public void setOrderby(Integer orderby) {
		this.orderby = orderby;
	}

	public Integer getMenulevel() {
		return menulevel;
	}

	public void setMenulevel(Integer menulevel) {
		this.menulevel = menulevel;
	}

	public PreMenu getParentId() {
		return parentId;
	}

	public void setParentId(PreMenu parentId) {
		this.parentId = parentId;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

}