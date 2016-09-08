package ses.model.bms;

import iss.model.ArticleType;

import java.util.Date;
import java.util.List;



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
     * @Fields status : 状态 0：暂存 1：已提交2：已审核 3：退回 
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
     * @Fields range : 发布范围 0：内网 1：外网 
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
    
    private List<ArticleAttachments> articleAttachments;
        
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
}