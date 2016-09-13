package ses.model.bms;

import java.util.Date;
import java.util.List;

/**
 * Description: 角色实体
 *
 * @author Ye MaoLin
 * @version 2016-9-13
 * @since JDK1.7
 */
public class Role {
	
    /** 主键 */
    private String id;

    /** 名称 */
    private String name;

    /** 创建时间 */
    private Date createdAt;
    
    /** 修改时间 */
    private Date updatedAt;

    /** 描述 */
    private String description;

    /** 
     * 删除标识
     * 0：未删除
     * 1：删除
     */
    private Integer isDeleted;
    
    /** 包含用户 */
    private List<User> users;
    
    /** 包含菜单 */
    private List<PreMenu> preMenus;

    public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

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
        this.name = name == null ? null : name.trim();
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

	public List<PreMenu> getPreMenus() {
		return preMenus;
	}

	public void setPreMenus(List<PreMenu> preMenus) {
		this.preMenus = preMenus;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
    
}