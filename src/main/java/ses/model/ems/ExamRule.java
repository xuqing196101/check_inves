/**
 * 
 */
package ses.model.ems;

import java.util.Date;

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
	@NotBlank(message="请输入及格标准分")
	@Pattern(regexp="^-?[1-9]\\d*$",message="请输入数字")
	private String passStandard;
	
	/**
	 * @Fields questionCount : 题目数量
	 */
	@NotBlank(message="请输入题目数量")
	@Pattern(regexp="^-?[1-9]\\d*$",message="请输入数字")
	private String questionCount;
	
	/**
	 * @Fields paperScore : 试卷分值
	 */
	@NotBlank(message="请输入试卷分值")
	@Pattern(regexp="^-?[1-9]\\d*$",message="请输入数字")
	private String paperScore;
	
	/**
	 * @Fields testCycle : 考试周期
	 */
	@NotBlank(message="请输入考试周期")
	@Pattern(regexp="^-?[1-9]\\d*$",message="请输入数字")
	private String testCycle;
	
	/**
	 * @Fields testTime : 考试时间
	 */
	private String testTime;
	
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

	public String getQuestionCount() {
		return questionCount;
	}

	public void setQuestionCount(String questionCount) {
		this.questionCount = questionCount;
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

	public String getTestTime() {
		return testTime;
	}

	public void setTestTime(String testTime) {
		this.testTime = testTime;
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
	
	
}
