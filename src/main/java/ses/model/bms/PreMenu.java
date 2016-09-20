package ses.model.bms;

import java.util.Date;
import java.util.List;

/**
 * Description: 权限菜单实体
 *
 * @author Ye MaoLin
 * @version 2016-9-13
 * @since JDK1.7
 */
public class PreMenu {
	
    /** 主键 */
    private String id;

    /** 名称 */
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
     * 删除标志  
     * 0：未删除
     * 1：删除
     */
    private Integer isDeleted;

    /**
     * 状态 
     * 0：可用
     * 1：暂停
     */
    private Integer status;

    /** 路径 */
    private String url;

    /** 创建时间 */ 
    private Date createdAt;
    
    /** 修改时间 */
    private Date updatedAt;

    /** 排序 */
    private Integer position;

    /** 菜单级别 */
    private Integer menulevel;

    /** parentId :上级菜单 */
    private PreMenu parentId;
    
    /** 菜单种类
     * 0:采购管理后台 
     * 1：供应商后台 
     * 2：专家后台
     * 3：进口供应商后台
     */
    private Integer kind;
    
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
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

	public Integer getKind() {
		return kind;
	}

	public void setKind(Integer kind) {
		this.kind = kind;
	}
	
}