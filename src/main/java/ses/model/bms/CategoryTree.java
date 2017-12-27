package ses.model.bms;

import java.util.Date;

public class CategoryTree {
    /*
     * 节点的id
     */
    private String id;

    /*
     * 节点的名称
     */
    private String name;

    private String kind;
    /*
     * 节点的父节点id
     */
    private String pId;
    
    /*
     * 专家品目Tree专用父节点Id(上面的PID有时获取不到)
     */
    private String parentId;

    /*
     * 是否是父类
     */
    private String isParent;
    
    /*
     * 是否被选中
     */
    private boolean checked;
    
    private boolean nocheck;

    private Integer isEnd;

    private Integer status;

    private String paramStatus;

    /** 类型,物资类:1,其他为空 **/
    private  String classify;

    /** 公开状态 **/
    private transient Integer pubStatus;
    
    /** 审核日期 */
    private transient Date auditDate;
    
    /** 审核意见 */
    private transient String auditAdvise;

    /** 父类的code **/
    private String code;
    
    private Integer treeLevel;



    public String getParamStatus() {
        return paramStatus;
    }
    public void setParamStatus(String paramStatus) {
        this.paramStatus = paramStatus;
    }
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
    public Integer getIsEnd() {
        return isEnd;
    }
    public void setIsEnd(Integer isEnd) {
        this.isEnd = isEnd;
    }
    public String getKind() {
        return kind;
    }
    public void setKind(String kind) {
        this.kind = kind;
    }
    public String getIsParent() {
        return isParent;
    }
    public void setIsParent(String isParent) {
        this.isParent = isParent;
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
        this.name = name;
    }
    public String getpId() {
        return pId;
    }
    public void setpId(String pId) {
        this.pId = pId;
    }
    public String getClassify() {
        return classify;
    }
    public void setClassify(String classify) {
        this.classify = classify;
    }
    public Integer getPubStatus() {
        return pubStatus;
    }
    public void setPubStatus(Integer pubStatus) {
        this.pubStatus = pubStatus;
    }
    /**
     * @return Returns the code.
     */
    public String getCode() {
        return code;
    }
    /**
     * @param code The code to set.
     */
    public void setCode(String code) {
        this.code = code;
    }
    public Date getAuditDate() {
        return auditDate;
    }
    public void setAuditDate(Date auditDate) {
        this.auditDate = auditDate;
    }
    public String getAuditAdvise() {
        return auditAdvise;
    }
    public void setAuditAdvise(String auditAdvise) {
        this.auditAdvise = auditAdvise;
    }
    public boolean getChecked() {
        return checked;
    }
    public void setChecked(boolean checked) {
        this.checked = checked;
    }
    public String getParentId() {
        return parentId;
    }
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public boolean isNocheck() {
      return nocheck;
    }
    public void setNocheck(boolean nocheck) {
      this.nocheck = nocheck;
    }
	public Integer getTreeLevel() {
		return treeLevel;
	}
	public void setTreeLevel(Integer treeLevel) {
		this.treeLevel = treeLevel;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (checked ? 1231 : 1237);
		result = prime * result
				+ ((classify == null) ? 0 : classify.hashCode());
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((isEnd == null) ? 0 : isEnd.hashCode());
		result = prime * result
				+ ((isParent == null) ? 0 : isParent.hashCode());
		result = prime * result + ((kind == null) ? 0 : kind.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + (nocheck ? 1231 : 1237);
		result = prime * result + ((pId == null) ? 0 : pId.hashCode());
		result = prime * result
				+ ((paramStatus == null) ? 0 : paramStatus.hashCode());
		result = prime * result
				+ ((parentId == null) ? 0 : parentId.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result
				+ ((treeLevel == null) ? 0 : treeLevel.hashCode());
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
		CategoryTree other = (CategoryTree) obj;
		if (checked != other.checked)
			return false;
		if (classify == null) {
			if (other.classify != null)
				return false;
		} else if (!classify.equals(other.classify))
			return false;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isEnd == null) {
			if (other.isEnd != null)
				return false;
		} else if (!isEnd.equals(other.isEnd))
			return false;
		if (isParent == null) {
			if (other.isParent != null)
				return false;
		} else if (!isParent.equals(other.isParent))
			return false;
		if (kind == null) {
			if (other.kind != null)
				return false;
		} else if (!kind.equals(other.kind))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (nocheck != other.nocheck)
			return false;
		if (pId == null) {
			if (other.pId != null)
				return false;
		} else if (!pId.equals(other.pId))
			return false;
		if (paramStatus == null) {
			if (other.paramStatus != null)
				return false;
		} else if (!paramStatus.equals(other.paramStatus))
			return false;
		if (parentId == null) {
			if (other.parentId != null)
				return false;
		} else if (!parentId.equals(other.parentId))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (treeLevel == null) {
			if (other.treeLevel != null)
				return false;
		} else if (!treeLevel.equals(other.treeLevel))
			return false;
		return true;
	}
	
    
}


