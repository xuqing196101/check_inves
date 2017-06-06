package ses.model.ems;


import java.util.Date;

/**
* @Title:ExamQuestion 
* @Description:题目表
* @author ZhaoBo
* @date 2016-9-7上午9:30:55
 */
public class ExamQuestion{
	/**
	 * @Fields id : 主键ID
	 */
    private String id;
    
    /**
     * @Fields answer : 答案
     */
    private String answer;
    
    /**
     * @Fields questionTypeId :题目类型
     */
    private Integer questionTypeId;
    
    /**
     * @Fields personType :参考人员类型
     */
    private Integer personType;
    
    /**
     * @Fields kind :选择题分类(针对专家)
     */
    private String kind;
    
    /**
     * @Fields createdAt :创建时间
     */
    private Date createdAt;
    
    /**
     * @Fields updatedAt :更新时间
     */
    private Date updatedAt;
    
    /**
     * @Fields topic :题干
     */
    private String topic;
    
    /**
     * @Fields items :选项
     */
    private String items;
    
    /**
     * @Fields examPoolType :关联题型表
     */
    private ExamQuestionType examQuestionType;
    
    /**
     * @Fields queNum :题目数量
     */
    private Integer queNum;
    
    /**
     * @Fields option :选项数量
     */
    private String option;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Integer getQuestionTypeId() {
		return questionTypeId;
	}

	public void setQuestionTypeId(Integer questionTypeId) {
		this.questionTypeId = questionTypeId;
	}

	public Integer getPersonType() {
		return personType;
	}

	public void setPersonType(Integer personType) {
		this.personType = personType;
	}

	public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
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

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getItems() {
		return items;
	}

	public void setItems(String items) {
		this.items = items;
	}

	public ExamQuestionType getExamQuestionType() {
		return examQuestionType;
	}

	public void setExamQuestionType(ExamQuestionType examQuestionType) {
		this.examQuestionType = examQuestionType;
	}

	public Integer getQueNum() {
		return queNum;
	}

	public void setQueNum(Integer queNum) {
		this.queNum = queNum;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	
    
    
	
}