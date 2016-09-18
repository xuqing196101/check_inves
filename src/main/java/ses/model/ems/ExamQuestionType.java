package ses.model.ems;

import java.util.Date;
/**
* @Title:ExamQuestionType 
* @Description:题目类型表
* @author ZhaoBo
* @date 2016-9-6上午10:15:06
 */
public class ExamQuestionType {
	/**
     * @Fields id : 主键ID
     */
    private Integer id;
    
    /**
     * @Fields name : 题型名字
     */
    private String name;
    
    /**
     * @Fields createdAt : 创建日期
     */
    private Date createdAt;
    
    /**
     * @Fields updatedAt : 更新日期
     */
    private Date updatedAt;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	
}