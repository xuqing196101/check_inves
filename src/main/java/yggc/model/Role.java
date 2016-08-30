package yggc.model;

import java.util.Date;
import java.util.List;

public class Role {
    private Integer id;

    private String name;

    private Date createdAt;

    private String describe;

    private Integer isDeleted;
    
    private List<User> users;
    
    private List<PreMenu> preMenus;

    public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

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
        this.name = name == null ? null : name.trim();
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe == null ? null : describe.trim();
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
    
}