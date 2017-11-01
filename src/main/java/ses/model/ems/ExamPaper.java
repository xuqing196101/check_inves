package ses.model.ems;

import java.util.Date;
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
    private String name;
    
    /**
     * @Fields code : 考卷编号
     */
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
     * @Fields testTime :考试答题时间
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
     * @Fields passStandard :及格标准
     */
    private String passStandard;
    
    /**
     * @Fields offTime :考试截止时间
     */
    private Date offTime;
    
    /**
     * @Fields offTrueDate :备用考试截止时间
     */
    private String offTrueDate;
    
    /**
     * @Fields status :考卷状态
     */
    private String status;
    
    /**
     * @Fields createrId : 创建人
     */
    private String createrId;
    
    /**
     * @Fields orgId : 创建单位
     */
    private String orgId;

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

	public String getPassStandard() {
		return passStandard;
	}

	public void setPassStandard(String passStandard) {
		this.passStandard = passStandard;
	}
	
	public Date getOffTime() {
		return offTime;
	}

	public void setOffTime(Date offTime) {
		this.offTime = offTime;
	}
	
	public String getOffTrueDate() {
		return offTrueDate;
	}

	public void setOffTrueDate(String offTrueDate) {
		this.offTrueDate = offTrueDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreaterId() {
		return createrId;
	}

	public void setCreaterId(String createrId) {
		this.createrId = createrId;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
}