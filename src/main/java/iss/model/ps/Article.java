package iss.model.ps;


import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.NotBlank;

import ses.model.bms.User;



/**
* @Title:Article 
* @Description: 信息实体类
* @author Shen Zhenfei
* @date 2016-9-7下午5:59:51
 */
public class Article{
	
	/**
	 * @Fields id : 主键
	 */
	private String id ;

	/**
	 * @Fields name : 标题
	 */
	@NotBlank(message = "标题不能为空")
    private String name;

    /**
     * @Fields articleType : 栏目
     */
    private ArticleType articleType;

    /**
     * @Fields publishedAt : 发布时间
     */
    private Date publishedAt;

    /**
     * @Fields isPicShow : 是否展示
     */
    private Integer isPicShow;

    /**
     * @Fields position : 排序值
     */
    private Integer position;

    /**
     * @Fields user : 创建人
     */
    private User user;

    /**
     * @Fields showCount : 浏览量
     */
    private Integer showCount;
    
    /**
     * @Fields downloadCount : 下载量
     */
    private Integer downloadCount;

    /**
     * @Fields status : 状态 0：暂存 1：已提交2：已审核 3：退回 4：已取消发布
     */
    private Integer status;

    /**
     * @Fields pic : 图片路径
     */
    private String pic;

    /**
     * @Fields publishedName : 发布人
     */
    private String publishedName;

    /**
     * @Fields isIndex : 是否索引
     */
    private Integer isIndex;

    /**
     * @Fields type : 项目类型
     */
    private String type;

    /**
     * @Fields projectId : 项目ID
     */
    private String projectId;

    /**
     * @Fields createdAt : 创建时间
     */
    private Date createdAt;

    /**
     * @Fields updatedAt : 修改时间
     */
    private Date updatedAt;

    /**
     * @Fields content : 内容
     */
    private String content;
    
    /**
     * @Fields range : 发布范围 0：内网  1：外网  2：内外网
     */
    private Integer range;
    
    /**
     * @Fields isDeleted :  删除 0：未删除，1：已删除
     */
    private Integer isDeleted;
    
    /**
     * @Fields reason : 退回内容
     */
    private String reason;
    
    /**
     * @Fields source : 文章来源
     */
    private String source;
    
    /**
     * @Fields source : 来源链接
     */
    private String sourceLink;
    
    /**
     * @Fields attributeType : 二级栏目节点(栏目属性)
     */
    private String secondArticleTypeId;
    
    /**
     * @Fields purchaseType : 三级栏目节点(采购类型)
     */
    private String threeArticleTypeId;
    
    /**
     * @Fields attributeType : 四级栏目节点(采购方式)
     */
    private String fourArticleTypeId;
    
    private ArticleType lastArticleType;
    
    private String lastArticleTypeId;
    
    private String lastArticleTypeName;
    
    //string类型发布时间
    private String create_at;
    
    //APP公告标识
    private Integer type_id;
    
    public Integer getType_id() {
		return type_id;
	}

	public void setType_id(Integer type_id) {
		this.type_id = type_id;
	}

	public String getLastArticleTypeName() {
		return lastArticleTypeName;
	}

	public void setLastArticleTypeName(String lastArticleTypeName) {
		this.lastArticleTypeName = lastArticleTypeName;
	}

	private String uploadId;
    
    private Date submitAt;
    
    private Date cancelPublishAt;
    
    /**
     * group
     */
    private String groupsUploadId;
    private String groupShowId;
    
    /**
     * id
     */
    private String groupsUpload;
    private String groupShow;
    
    /** 第一级Id **/
    private String articleTypeId;
    
    /** 用户Id **/
    private String userId;
    
    
    //产品目录id
    private String categoryId;
    
    //产品目录名称
  	private String categoryName;
    
    public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
    
	public String getUploadId() {
		return uploadId;
	}

	public void setUploadId(String uploadId) {
		this.uploadId = uploadId;
	}

	private List<ArticleAttachments> articleAttachments;
        
	public String getLastArticleTypeId() {
		return lastArticleTypeId;
	}

	public void setLastArticleTypeId(String lastArticleTypeId) {
		this.lastArticleTypeId = lastArticleTypeId;
	}

	public Integer getDownloadCount() {
		return downloadCount;
	}

	public void setDownloadCount(Integer downloadCount) {
		this.downloadCount = downloadCount;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getIsPicShow() {
		return isPicShow;
	}

	public void setIsPicShow(Integer isPicShow) {
		this.isPicShow = isPicShow;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public Integer getShowCount() {
		return showCount;
	}

	public void setShowCount(Integer showCount) {
		this.showCount = showCount;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getIsIndex() {
		return isIndex;
	}

	public void setIsIndex(Integer isIndex) {
		this.isIndex = isIndex;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public Integer getRange() {
		return range;
	}

	public void setRange(Integer range) {
		this.range = range;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public List<ArticleAttachments> getArticleAttachments() {
		return articleAttachments;
	}

	public void setArticleAttachments(List<ArticleAttachments> articleAttachments) {
		this.articleAttachments = articleAttachments;
	}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public ArticleType getArticleType() {
		return articleType;
	}

	public void setArticleType(ArticleType articleType) {
		this.articleType = articleType;
	}

	public Date getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Date publishedAt) {
        this.publishedAt = publishedAt;
    }

    public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic == null ? null : pic.trim();
    }

    public String getPublishedName() {
        return publishedName;
    }

    public void setPublishedName(String publishedName) {
        this.publishedName = publishedName == null ? null : publishedName.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
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

    public String getContent() {
    	/*if(content!=null){
    		return content.replaceAll("\\s*", "");
    	}else{
    		return content;
    	}*/
      return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Article(String id) {
		super();
		this.id = id;
	}

	public Article() {
		super();
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getSourceLink() {
		return sourceLink;
	}

	public void setSourceLink(String sourceLink) {
		this.sourceLink = sourceLink;
	}

	public String getSecondArticleTypeId() {
		return secondArticleTypeId;
	}

	public void setSecondArticleTypeId(String secondArticleTypeId) {
		this.secondArticleTypeId = secondArticleTypeId;
	}

	public String getThreeArticleTypeId() {
		return threeArticleTypeId;
	}

	public void setThreeArticleTypeId(String threeArticleTypeId) {
		this.threeArticleTypeId = threeArticleTypeId;
	}

	public String getFourArticleTypeId() {
		return fourArticleTypeId;
	}

	public void setFourArticleTypeId(String fourArticleTypeId) {
		this.fourArticleTypeId = fourArticleTypeId;
	}

	public ArticleType getLastArticleType() {
		return lastArticleType;
	}

	public void setLastArticleType(ArticleType lastArticleType) {
		this.lastArticleType = lastArticleType;
	}

	public String getGroupsUpload() {
		return groupsUpload;
	}

	public void setGroupsUpload(String groupsUpload) {
		this.groupsUpload = groupsUpload;
	}

	public String getGroupShow() {
		return groupShow;
	}

	public void setGroupShow(String groupShow) {
		this.groupShow = groupShow;
	}

	public String getGroupsUploadId() {
		return groupsUploadId;
	}

	public void setGroupsUploadId(String groupsUploadId) {
		this.groupsUploadId = groupsUploadId;
	}

	public String getGroupShowId() {
		return groupShowId;
	}

	public void setGroupShowId(String groupShowId) {
		this.groupShowId = groupShowId;
	}

	public Date getSubmitAt() {
		return submitAt;
	}

	public void setSubmitAt(Date submitAt) {
		this.submitAt = submitAt;
	}

    public String getArticleTypeId() {
        return articleTypeId;
    }

    public void setArticleTypeId(String articleTypeId) {
        this.articleTypeId = articleTypeId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getCancelPublishAt() {
        return cancelPublishAt;
    }

    public void setCancelPublishAt(Date cancelPublishAt) {
        this.cancelPublishAt = cancelPublishAt;
    }

	public String getCreate_at() {
		return create_at;
	}

	public void setCreate_at(String create_at) {
		this.create_at = create_at;
	}

   
    
}