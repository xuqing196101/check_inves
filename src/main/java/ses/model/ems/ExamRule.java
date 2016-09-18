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
	 * @Fields questionCount : 题目数量
	 */
	private Integer questionCount;
	
	/**
	 * @Fields paperScore : 试卷分值
	 */
	private String paperScore;
	
	/**
	 * @Fields testCycle : 考试周期
	 */
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

	public Integer getQuestionCount() {
		return questionCount;
	}

	public void setQuestionCount(Integer questionCount) {
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
	
	
}
