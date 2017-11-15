package bss.model.prms;

import java.io.Serializable;
import java.util.List;

import bss.model.ppms.MarkTerm;
import bss.model.ppms.ScoreModel;
import ses.model.ems.Expert;

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
    //符合性评审状态0未评审1提交评审结果2暂存评审
    private Short isAudit;
    //初审是否汇总（结束）0否1是
    private Short isGather;
    //是否评分 0未评审1提交评审结果2暂存评审
    private Short isGrade;
    //评分是否汇总（结束）0否1是2专家咨询委员会提交
    private Short isGatherGather;
    //评审类型id 
    private String reviewTypeId;
    //专家实体
    private Expert expert;
    //数量
    private Integer count;
    
    //是否到场 0未到场 1到场
    private Integer isSigin;
    
    //是否临时专家  0是 1否
    private Integer isTempExpert;
    
    
    private List<ScoreModel> scoreModels;
    
    private List<MarkTerm> markTerms;
    
    private List<ExpertScore> expertScores;

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

    /**
     * @return Returns the expert.
     */
    public Expert getExpert() {
        return expert;
    }

    /**
     * @param expert The expert to set.
     */
    public void setExpert(Expert expert) {
        this.expert = expert;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getReviewTypeId() {
        return reviewTypeId;
    }

    public void setReviewTypeId(String reviewTypeId) {
        this.reviewTypeId = reviewTypeId;
    }

    public Integer getIsSigin() {
      return isSigin;
    }

    public void setIsSigin(Integer isSigin) {
      this.isSigin = isSigin;
    }

    public Integer getIsTempExpert() {
      return isTempExpert;
    }

    public void setIsTempExpert(Integer isTempExpert) {
      this.isTempExpert = isTempExpert;
    }

    public List<ScoreModel> getScoreModels() {
      return scoreModels;
    }

    public void setScoreModels(List<ScoreModel> scoreModels) {
      this.scoreModels = scoreModels;
    }

    public List<MarkTerm> getMarkTerms() {
      return markTerms;
    }

    public void setMarkTerms(List<MarkTerm> markTerms) {
      this.markTerms = markTerms;
    }

    public List<ExpertScore> getExpertScores() {
      return expertScores;
    }

    public void setExpertScores(List<ExpertScore> expertScores) {
      this.expertScores = expertScores;
    }
    
    
}