/**
 * 
 */
package ses.model.ems;

import java.util.Date;


/**
 * @Title:ExamRule
 * @Description: 专家考试规则类
 * @author ZhaoBo
 * @date 2016-9-8上午10:38:12
 */
public class ExamRule {
	/**
	 * @Fields id : 主键ID
	 */
	private String id;
	
	/**
	 * @Fields passStandard : 及格标准
	 */
	private String passStandard;
	
	/**
	 * @Fields paperScore : 试卷分值
	 */
	private String paperScore;
	
	/**
	 * @Fields discribution : 题型描述
	 */
	private String discribution;
	
	/**
	 * @Fields typeDistribution : 题型分布
	 */
	private String typeDistribution;
	
	/**
	 * @Fields createdAt : 创建时间
	 */
	private Date createdAt;
	
	/**
	 * @Fields updatedAt : 更新时间
	 */
	private Date updatedAt;
	
	/**
	 * @Fields formatDate : 时间备用字段
	 */
	private String formatDate;
	
	/**
	 * @Fields status : 规则状态
	 */
	private Integer status;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassStandard() {
		return passStandard;
	}

	public void setPassStandard(String passStandard) {
		this.passStandard = passStandard;
	}
	
	public String getPaperScore() {
		return paperScore;
	}

	public void setPaperScore(String paperScore) {
		this.paperScore = paperScore;
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

	public String getFormatDate() {
		return formatDate;
	}

	public void setFormatDate(String formatDate) {
		this.formatDate = formatDate;
	}
	
	public String getDiscribution() {
		return discribution;
	}

	public void setDiscribution(String discribution) {
		this.discribution = discribution;
	}

	public String getTypeDistribution() {
		return typeDistribution;
	}

	public void setTypeDistribution(String typeDistribution) {
		this.typeDistribution = typeDistribution;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
}
