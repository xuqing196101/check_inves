package iss.model.fs;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import org.hibernate.validator.constraints.NotBlank;

import ses.model.bms.User;

/**
* @Title:Post 
* @Description: 帖子实体类
* @author Peng Zhongjun
* @date 2016-9-7下午6:26:35
 */
public class Post {
    /**
     * @Fields id : 主键
     */
    private String id;
    /**
     * @Fields name : 帖子名称
     */
    @NotBlank(message = "帖子名称不能为空")
    private String name;
    /**
     * @Fields content : 帖子内容
     */
    @NotBlank(message = "帖子内容不能为空")
    private String content;
    /**
     * @Fields isTop : 是否置顶
     */
    private Integer isTop;
    /**
     * @Fields isLocking : 是否锁定
     */
    private Integer isLocking;

    /**
     * @Fields publishedTime : 发布时间
     */
    private Timestamp publishedAt;
    /**
     * @Fields lastReplyedTime : 最后回复时间
     */
    private Timestamp lastReplyedAt;
    /**
     * @Fields lastReply : 最后回复人
     */
    private User lastReplyer;
    
    /**
     * @Fields user : 发表人
     */
    private User user;
    /**
     * @Fields park : 所属版块
     */
    private Park park;    
    /**
     * @Fields topic : 所属主题
     */
    private Topic topic;
    /**
     * @Fields replies : 回复表
     */
    private List<Reply> replies;
    /**
     * @Fields replycount : 回复数
     */
    private BigDecimal replycount;
    /**
     * @Fields isDeleted : 是否删除标识
     */
    private Integer isDeleted;
    /**
     * @Fields postAttachments : 帖子附件表
     */
    private List<PostAttachments> postAttachments;


	/**
	 * @param id
	 */
	public Post(String id) {
		super();
		this.id = id;
	}
	public Post() {
		super();
		// TODO Auto-generated constructor stub
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

    public Integer getIsTop() {
        return isTop;
    }

    public void setIsTop(Integer isTop) {
        this.isTop = isTop;
    }

    public Integer getIsLocking() {
        return isLocking;
    }

    public void setIsLocking(Integer isLocking) {
        this.isLocking = isLocking;
    }
    public Timestamp getPublishedAt() {
		return publishedAt;
	}

	public void setPublishedAt(Timestamp publishedAt) {
		this.publishedAt = publishedAt;
	}

	public Timestamp getLastReplyedAt() {
		return lastReplyedAt;
	}

	public void setLastReplyedAt(Timestamp lastReplyedAt) {
		this.lastReplyedAt = lastReplyedAt;
	}

	public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Park getPark() {
		return park;
	}

	public void setPark(Park park) {
		this.park = park;
	}

	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}

	public List<Reply> getReplies() {
		return replies;
	}

	public void setReplies(List<Reply> replies) {
		this.replies = replies;
	}

	public User getLastReplyer() {
		return lastReplyer;
	}

	public void setLastReplyer(User lastReplyer) {
		this.lastReplyer = lastReplyer;
	}

	public BigDecimal getReplycount() {
		return replycount;
	}

	public void setReplycount(BigDecimal replycount) {
		this.replycount = replycount;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public List<PostAttachments> getPostAttachments() {
		return postAttachments;
	}

	public void setPostAttachments(List<PostAttachments> postAttachments) {
		this.postAttachments = postAttachments;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((isDeleted == null) ? 0 : isDeleted.hashCode());
		result = prime * result
				+ ((isLocking == null) ? 0 : isLocking.hashCode());
		result = prime * result + ((isTop == null) ? 0 : isTop.hashCode());
		result = prime * result
				+ ((lastReplyedAt == null) ? 0 : lastReplyedAt.hashCode());
		result = prime * result
				+ ((lastReplyer == null) ? 0 : lastReplyer.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((park == null) ? 0 : park.hashCode());
		result = prime * result
				+ ((postAttachments == null) ? 0 : postAttachments.hashCode());
		result = prime * result
				+ ((publishedAt == null) ? 0 : publishedAt.hashCode());
		result = prime * result + ((replies == null) ? 0 : replies.hashCode());
		result = prime * result
				+ ((replycount == null) ? 0 : replycount.hashCode());
		result = prime * result + ((topic == null) ? 0 : topic.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Post other = (Post) obj;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isDeleted == null) {
			if (other.isDeleted != null)
				return false;
		} else if (!isDeleted.equals(other.isDeleted))
			return false;
		if (isLocking == null) {
			if (other.isLocking != null)
				return false;
		} else if (!isLocking.equals(other.isLocking))
			return false;
		if (isTop == null) {
			if (other.isTop != null)
				return false;
		} else if (!isTop.equals(other.isTop))
			return false;
		if (lastReplyedAt == null) {
			if (other.lastReplyedAt != null)
				return false;
		} else if (!lastReplyedAt.equals(other.lastReplyedAt))
			return false;

		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;

		if (postAttachments == null) {
			if (other.postAttachments != null)
				return false;
		} else if (!postAttachments.equals(other.postAttachments))
			return false;
		if (publishedAt == null) {
			if (other.publishedAt != null)
				return false;
		} else if (!publishedAt.equals(other.publishedAt))
			return false;
		if (replycount == null) {
			if (other.replycount != null)
				return false;
		} else if (!replycount.equals(other.replycount))
			return false;

		return true;
	}

	   
}