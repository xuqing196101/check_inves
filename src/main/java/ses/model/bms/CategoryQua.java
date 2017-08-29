package ses.model.bms;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述>品目质量关联实体
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
public class CategoryQua implements Serializable{
	
    /**
	 * CategoryQua.java
	 */
	private static final long serialVersionUID = 1L;
	/** 主键  */
    private String id;
    /** 品目Id **/
    private String categoryId;
    /** 资质Id **/
    private String quaId;
    /** 资质类型Id  1:通用 2：物资生产型 3：物资销售型 4：工程类  服务2**/
    private int quaType;
    /**创建时间**/
    private Date createdAt;
    /**更新时间*/
    private Date updatedAt;
    /**是否 有效 0 有效  1删除**/
    private Integer isDeleted;

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

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getQuaId() {
        return quaId;
    }

    public void setQuaId(String quaId) {
        this.quaId = quaId;
    }

    public int getQuaType() {
        return quaType;
    }

    public void setQuaType(int quaType) {
        this.quaType = quaType;
    }

    
    
}
