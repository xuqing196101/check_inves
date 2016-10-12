package ses.model.ems;

import java.util.Date;

import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
/**
* @Title:ExamPaper 
* @Description:考卷表
* @author ZhaoBo
* @date 2016-9-7上午10:04:55
 */
public class ExamPaper {
	/**
     * @Fields id : 主键ID
     */
    private String id;
    
    /**
     * @Fields name : 考卷名称
     */
    @NotBlank(message="考卷名称不能为空")
    private String name;
    
    /**
     * @Fields code : 考卷编号
     */
    @NotBlank(message="考卷编号不能为空")
    private String code;
    
    /**
     * @Fields score : 考卷总分值
     */
    private String score;
    
    /**
     * @Fields startTime :考试开始日期
     */
    private Date startTime;
    
    /**
     * @Fields time :考试开始日期
     */
    private String time;
    
    /**
     * @Fields startTrueDate :备用日期
     */
    private String startTrueDate;
    
    /**
     * @Fields year :考卷年度
     */
    private String year;
    
    /**
     * @Fields typeDistribution :题型分布
     */
    private String typeDistribution;
    
    /**
     * @Fields testTime :考试用时
     */
    private String testTime;
    
    /**
     * @Fields createdAt :创建日期
     */
    private Date createdAt;
    
    /**
     * @Fields updatedAt :更新日期
     */
    private Date updatedAt;
    
    /**
     * @Fields isAllowRetake :是否允许重考
     */
    private Integer isAllowRetake;
    
    /**
     * @Fields expiryDate :考试有效期
     */
    private String expiryDate;
    
    /**
     * @Fields status :考卷状态
     */
    private String status;

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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public String getStartTrueDate() {
		return startTrueDate;
	}

	public void setStartTrueDate(String startTrueDate) {
		this.startTrueDate = startTrueDate;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getTypeDistribution() {
		return typeDistribution;
	}

	public void setTypeDistribution(String typeDistribution) {
		this.typeDistribution = typeDistribution;
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

	public Integer getIsAllowRetake() {
		return isAllowRetake;
	}

	public void setIsAllowRetake(Integer isAllowRetake) {
		this.isAllowRetake = isAllowRetake;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	
    
}