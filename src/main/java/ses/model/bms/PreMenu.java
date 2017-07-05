package ses.model.bms;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Description: 权限菜单实体
 *
 * @author Ye MaoLin
 * @version 2016-9-13
 * @since JDK1.7
 */
public class PreMenu implements Serializable{
	
	private static final long serialVersionUID = 1L;

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
     * 3：进口代理商后台
     */
    private Integer kind;
    
    /** 图标 */
    private String icon;
    
    /** 角色 */
    private List<Role> roles;
    
    /** 权限编码 */
    private String permissionCode;

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

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

    public String getPermissionCode() {
        return permissionCode;
    }

    public void setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode;
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
      result = prime * result + ((icon == null) ? 0 : icon.hashCode());
      result = prime * result + ((id == null) ? 0 : id.hashCode());
      result = prime * result + ((isDeleted == null) ? 0 : isDeleted.hashCode());
      result = prime * result + ((kind == null) ? 0 : kind.hashCode());
      result = prime * result + ((menulevel == null) ? 0 : menulevel.hashCode());
      result = prime * result + ((name == null) ? 0 : name.hashCode());
      result = prime * result + ((parentId == null) ? 0 : parentId.hashCode());
      result = prime * result + ((permissionCode == null) ? 0 : permissionCode.hashCode());
      result = prime * result + ((position == null) ? 0 : position.hashCode());
      result = prime * result + ((roles == null) ? 0 : roles.hashCode());
      result = prime * result + ((status == null) ? 0 : status.hashCode());
      result = prime * result + ((type == null) ? 0 : type.hashCode());
      result = prime * result + ((updatedAt == null) ? 0 : updatedAt.hashCode());
      result = prime * result + ((url == null) ? 0 : url.hashCode());
      return result;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (getClass() != obj.getClass())
        return false;
      PreMenu other = (PreMenu) obj;
      if (createdAt == null) {
        if (other.createdAt != null)
          return false;
      } else if (!createdAt.equals(other.createdAt))
        return false;
      if (icon == null) {
        if (other.icon != null)
          return false;
      } else if (!icon.equals(other.icon))
        return false;
      if (id == null) {
        if (other.id != null)
          return false;
      } else if (!id.equals(other.id))
        return false;
      if (isDeleted == null) {
        if (other.isDeleted != null)
          return false;
      } else if (!isDeleted.equals(other.isDeleted))
        return false;
      if (kind == null) {
        if (other.kind != null)
          return false;
      } else if (!kind.equals(other.kind))
        return false;
      if (menulevel == null) {
        if (other.menulevel != null)
          return false;
      } else if (!menulevel.equals(other.menulevel))
        return false;
      if (name == null) {
        if (other.name != null)
          return false;
      } else if (!name.equals(other.name))
        return false;
      if (parentId == null) {
        if (other.parentId != null)
          return false;
      } else if (!parentId.equals(other.parentId))
        return false;
      if (permissionCode == null) {
        if (other.permissionCode != null)
          return false;
      } else if (!permissionCode.equals(other.permissionCode))
        return false;
      if (position == null) {
        if (other.position != null)
          return false;
      } else if (!position.equals(other.position))
        return false;
      if (roles == null) {
        if (other.roles != null)
          return false;
      } else if (!roles.equals(other.roles))
        return false;
      if (status == null) {
        if (other.status != null)
          return false;
      } else if (!status.equals(other.status))
        return false;
      if (type == null) {
        if (other.type != null)
          return false;
      } else if (!type.equals(other.type))
        return false;
      if (updatedAt == null) {
        if (other.updatedAt != null)
          return false;
      } else if (!updatedAt.equals(other.updatedAt))
        return false;
      if (url == null) {
        if (other.url != null)
          return false;
      } else if (!url.equals(other.url))
        return false;
      return true;
    }
	
    
	
}