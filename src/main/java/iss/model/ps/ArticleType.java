package iss.model.ps;

import java.io.Serializable;
import java.sql.Timestamp;

import org.hibernate.validator.constraints.NotBlank;

import ses.model.bms.User;

/*
 *@Title:ArticleType
 *@Description:文章类型实体类
 *@author QuJie
 *@date 2016-9-7下午6:24:23
 */
public class ArticleType implements Serializable{
  
  public ArticleType(){}
  public ArticleType(String id){
    this.id=id;
  }
	
	/**
     * @Fields id : 主键
     */
    private String id;
    
    /**
     * @Fields name : 栏目名称
     */
    @NotBlank(message = "栏目名称不能为空")
    private String name;
    /**
     * @Fields describe : 栏目描述
     */
    private String describe;
    /**
     * @Fields creater: 创建人
     */
    private User creater;   
    /**
     * @Fields createddAt :创建时间
     */
    private Timestamp createdAt;
    /**
     * @Fields updatedAt : 修改时间
     */
    private Timestamp updatedAt;

    /**
     * @Fields code:栏目编码
     */
    private String code;

    /**
     * @Fidlds parentId:父级节点
     */
    private String parentId; 
    
    private String showNum;
    
    private ArticleType articleType;
    
    public String getShowNum() {
		return showNum;
	}

	public void setShowNum(String showNum) {
		this.showNum = showNum;
	}

	public ArticleType getArticleType() {
		return articleType;
	}

	public void setArticleType(ArticleType articleType) {
		this.articleType = articleType;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

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
        this.name = name == null ? null : name.trim();
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }


	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public User getCreater() {
		return creater;
	}

	public void setCreater(User creater) {
		this.creater = creater;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	
}