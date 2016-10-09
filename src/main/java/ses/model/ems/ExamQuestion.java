package ses.model.ems;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.sun.istack.internal.NotNull;
/**
* @Title:ExamQuestion 
* @Description:题目表
* @author ZhaoBo
* @date 2016-9-7上午9:30:55
 */
public class ExamQuestion implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * @Fields id : 主键ID
	 */
    private String id;
    
    /**
     * @Fields answer : 答案
     */
    private String answer;
    
    /**
     * @Fields point :分值
     */
    private Integer point;
    
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
    private Integer kind;
    
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
     * @Fields singleNum :采购人单选题数量
     */
    private Integer singleNum;
    
    /**
     * @Fields multipleNum :采购人多选题数量
     */
    private Integer multipleNum;
    
    /**
     * @Fields judgeNum :采购人判断题数量
     */
    private Integer judgeNum;
    
    /**
     * @Fields queNum :专家考试题目数量
     */
    private Integer queNum;
    
    
    private String type;

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

	public Integer getPoint() {
		return point;
	}

	public void setPoint(Integer point) {
		this.point = point;
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

	public Integer getKind() {
		return kind;
	}

	public void setKind(Integer kind) {
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

	public Integer getSingleNum() {
		return singleNum;
	}

	public void setSingleNum(Integer singleNum) {
		this.singleNum = singleNum;
	}

	public Integer getMultipleNum() {
		return multipleNum;
	}

	public void setMultipleNum(Integer multipleNum) {
		this.multipleNum = multipleNum;
	}

	public Integer getJudgeNum() {
		return judgeNum;
	}

	public void setJudgeNum(Integer judgeNum) {
		this.judgeNum = judgeNum;
	}

	public Integer getQueNum() {
		return queNum;
	}

	public void setQueNum(Integer queNum) {
		this.queNum = queNum;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
    
    
	
}