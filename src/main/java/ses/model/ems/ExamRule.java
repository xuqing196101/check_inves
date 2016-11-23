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
	 * @Fields startTime : 考试开始时间
	 */
	private Date startTime;
	
	/**
	 * @Fields offTime : 考试截止时间
	 */
	private Date offTime;
	
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
	 * @Fields year : 考卷年度
	 */
	private Integer year;
	
	/**
	 * @Fields formatYear : 预备考卷年度
	 */
	private Integer formatYear;
	
	/**
	 * @Fields status : 考卷状态
	 */
	private String status;
	
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

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	public Date getOffTime() {
		return offTime;
	}

	public void setOffTime(Date offTime) {
		this.offTime = offTime;
	}

	public String getTypeDistribution() {
		return typeDistribution;
	}

	public void setTypeDistribution(String typeDistribution) {
		this.typeDistribution = typeDistribution;
	}
	
	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}
	
	public Integer getFormatYear() {
		return formatYear;
	}

	public void setFormatYear(Integer formatYear) {
		this.formatYear = formatYear;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
