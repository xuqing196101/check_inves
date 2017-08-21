package ses.model.ems;

import java.io.Serializable;

public class ExpertCategory implements Serializable{
	/**
	 * @Fields serialVersionUID : 
	 */
	private static final long serialVersionUID = 1L;
	//专家id
    private String expertId;
    //品目id
    private String categoryId;
    // 类型id(根)
    private String typeId;

    private String levels;
    private String engin_type;
    
    //审核状态（0默认通过，1不通过）
    private Integer auditStatus;
    
    public String getExpertId() {
        return expertId;
    }

    public void setExpertId(String expertId) {
        this.expertId = expertId == null ? null : expertId.trim();
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId == null ? null : categoryId.trim();
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

	public String getLevels() {
		return levels;
	}

	public void setLevels(String levels) {
		this.levels = levels;
	}

	public String getEngin_type() {
		return engin_type;
	}

	public void setEngin_type(String engin_type) {
		this.engin_type = engin_type;
	}

	public Integer getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}
	
}