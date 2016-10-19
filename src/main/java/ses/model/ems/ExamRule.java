/**
 * 
 */
package ses.model.ems;

import java.util.Date;

import javax.validation.constraints.Future;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

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
	 * @Fields typeDistribution : 题型分布
	 */
	private String typeDistribution;
	
	/**
	 * @Fields testCycle : 考试周期
	 */
	private String testCycle;
	
	/**
	 * @Fields createdAt : 创建时间
	 */
	private Date createdAt;
	
	/**
	 * @Fields updatedAt : 更新时间
	 */
	private Date updatedAt;
	
	/**
	 * @Fields testLong : 备用字段
	 */
	private Date testLong;
	
	/**
	 * @Fields formatDate : 时间备用字段
	 */
	private String formatDate;
	
	/**
	 * @Fields time : 验证考试开始时间字段
	 */
	private String time;
	
	/**
	 * @Fields singlePoint : 验证单选题数量字段
	 */
	private String singlePoint;
	
	/**
	 * @Fields multiplePoint : 验证多选题数量字段
	 */
	private String multiplePoint;
	
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

	public String getTestCycle() {
		return testCycle;
	}

	public void setTestCycle(String testCycle) {
		this.testCycle = testCycle;
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

	public Date getTestLong() {
		return testLong;
	}

	public void setTestLong(Date testLong) {
		this.testLong = testLong;
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

	public String getTypeDistribution() {
		return typeDistribution;
	}

	public void setTypeDistribution(String typeDistribution) {
		this.typeDistribution = typeDistribution;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getSinglePoint() {
		return singlePoint;
	}

	public void setSinglePoint(String singlePoint) {
		this.singlePoint = singlePoint;
	}

	public String getMultiplePoint() {
		return multiplePoint;
	}

	public void setMultiplePoint(String multiplePoint) {
		this.multiplePoint = multiplePoint;
	}

	
	
	
}
