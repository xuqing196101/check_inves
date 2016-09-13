package ses.model.bms;

import java.util.Date;
import java.util.List;

public class PreMenu {
	
    /** 主键 */
    private String id;

    /** name :名称 */
    private String name;

    /**
     * 类型
     * navigation:导航
	 * accordion：折叠导航
	 * menu:菜单  
	 * button:按钮 
     */
    private String type;

    /**
     *  删除标志  
     * 0：未删除，1：删除
     */
    private Integer isDeleted;

    /**
     * 状态 
     * 0：可用，1：暂停
     */
    private Integer state;

    /** 路径 */
    private String url;

    /** 创建时间 */ 
    private Date createdAt;
    
    /** 修改时间 */
    private Date updatedAt;

    /** orderby :排序 */
    private Integer orderby;

    /** 菜单级别 */
    private Integer menulevel;

    /** parentId :上级菜单 */
    private PreMenu parentId;
    
    /** 角色 */
    private List<Role> roles;

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	
}