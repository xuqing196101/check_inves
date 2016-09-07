package yggc.model.oms;

import java.util.Date;

public class Orgnization {
    private String id;

    private Date createdAt;

    private Date updatedAt;

    private Integer orglevel;

    private Integer orderby;

    private Integer isDeleted;

    private String parentId;

    private String name;

    private String depId;

    private Integer type;

    private Integer isRoot;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    

    public Integer getOrglevel() {
		return orglevel;
	}

	public void setOrglevel(Integer orglevel) {
		this.orglevel = orglevel;
	}

	public Integer getOrderby() {
        return orderby;
    }

    public void setOrderby(Integer orderby) {
        this.orderby = orderby;
    }


    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDepId() {
        return depId;
    }

    public void setDepId(String depId) {
        this.depId = depId == null ? null : depId.trim();
    }

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getIsRoot() {
		return isRoot;
	}

	public void setIsRoot(Integer isRoot) {
		this.isRoot = isRoot;
	}


}