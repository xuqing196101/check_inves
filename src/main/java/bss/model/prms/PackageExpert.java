package bss.model.prms;

import java.io.Serializable;

public class PackageExpert implements Serializable{
    /**
	 * @Fields serialVersionUID : 
	 */
	private static final long serialVersionUID = 1L;
	//包id
	private String packageId;
    //专家id
    private String expertId;
    //项目id
    private String projectId;
    //是否为组长0否1是
    private Short isGroupLeader;
    //是否评审0否1是
    private Short isAudit;
    //初审是否汇总0否1是
    private Short isGather;
    //是否评分 0否1是
    private Short isGrade;
    //评分是否汇总0否1是
    private Short isGatherGather;

    
    

	public Short getIsGrade() {
		return isGrade;
	}

	public void setIsGrade(Short isGrade) {
		this.isGrade = isGrade;
	}

	public Short getIsGatherGather() {
		return isGatherGather;
	}

	public void setIsGatherGather(Short isGatherGather) {
		this.isGatherGather = isGatherGather;
	}

	public Short getIsGather() {
		return isGather;
	}

	public void setIsGather(Short isGather) {
		this.isGather = isGather;
	}

	public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId == null ? null : packageId.trim();
    }

    public String getExpertId() {
        return expertId;
    }

    public void setExpertId(String expertId) {
        this.expertId = expertId == null ? null : expertId.trim();
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    public Short getIsGroupLeader() {
        return isGroupLeader;
    }

    public void setIsGroupLeader(Short isGroupLeader) {
        this.isGroupLeader = isGroupLeader;
    }

    public Short getIsAudit() {
        return isAudit;
    }

    public void setIsAudit(Short isAudit) {
        this.isAudit = isAudit;
    }
}