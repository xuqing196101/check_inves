package yggc.model.bms;

import java.util.Date;
import java.util.List;

import yggc.model.iss.ArticleType;

public class Article {
    private Long id;

    private String name;

    private ArticleType articleType;

    private Date publishedAt;

    private Short isPicShow;

    private Long position;

    private User user;

    private Long showCount;

    private Short status;

    private String pic;

    private String publishedName;

    private Short isIndex;

    private String type;

    private Long projectId;

    private Date createdAt;

    private Date updatedAt;

    private String content;
    
    private Short range;
    
    private Short isDeleted;
    
    public Short getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Short isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Short getRange() {
		return range;
	}

	public void setRange(Short range) {
		this.range = range;
	}

	private List<ArticleAttachments> articleAttachments;
    
	public List<ArticleAttachments> getArticleAttachments() {
		return articleAttachments;
	}

	public void setArticleAttachments(List<ArticleAttachments> articleAttachments) {
		this.articleAttachments = articleAttachments;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Short getIsPicShow() {
        return isPicShow;
    }

    public void setIsPicShow(Short isPicShow) {
        this.isPicShow = isPicShow;
    }

    public Long getPosition() {
        return position;
    }

    public void setPosition(Long position) {
        this.position = position;
    }

    public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getShowCount() {
        return showCount;
    }

    public void setShowCount(Long showCount) {
        this.showCount = showCount;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
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

    public Short getIsIndex() {
        return isIndex;
    }

    public void setIsIndex(Short isIndex) {
        this.isIndex = isIndex;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
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
}