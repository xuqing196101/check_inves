package ses.model.bms;


import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @Title:Area 
 * @Description:地区管理实体类
 * @author FengTian
 * @date 2016-9-7下午5:58:54
 */
public class Area implements Serializable{
  
	/**
   * @Fields serialVersionUID : TODO(目的和意义)
   */
  private static final long serialVersionUID = 1L;
  
  /**
	 * @Fields id : 主键
	 */
    private String id;
    /**
	 * @Fields createdAt : 添加时间
	 */
    private Date createdAt;
    /**
	 * @Fields updatedAt : 修改时间
	 */
    private Date updatedAt;
    /**
	 * @Fields isDeleted : 删除标识
	 */
    private Integer isDeleted;
    /**
	 * @Fields position : 序号
	 */
    private Integer position;
    /**
	 * @Fields areaType : 父节点
	 */
    private String areaType;
    /**
	 * @Fields parentId : 上级地区
	 */
    private String parentId;
    /**
	 * @Fields name : 地区名称
	 */
    private String name;
    
    private String errInfo;

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

    public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getAreaType() {
		return areaType;
	}

	public void setAreaType(String areaType) {
		this.areaType = areaType;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getErrInfo() {
        return errInfo;
    }

    public void setErrInfo(String errInfo) {
        this.errInfo = errInfo;
    }
}