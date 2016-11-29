package iss.model.fs;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import ses.model.bms.User;

/**
* @Title:Park 
* @Description: 版块实体类
* @author Peng Zhongjun
* @date 2016-9-7下午6:26:03
 */
public class Park{
    /**
     * @Fields id : 主键
     */
    private String id;
    
    /**
     * @Fields name : 版块名称
     */
    private String name;

    /**
     * @Fields content : 版块内容
     */
    private String content;
    /**
     * @Fields createdAt : 创建时间
     */
    private Timestamp createdAt;
    /**
     * @Fields updated_At：更新时间
     */
    private Timestamp updatedAt;    
    
    /**
     * @Fields user_id : 版主
     */
    private User user;
    /**
     * @Fields creater_id : 创建人
     */
    private User creater;
    /**
     * @Fields topics : 主题列表
     */
    private List<Topic> topics;
    /**
     * @Fields posts : 帖子列表
     */
    private List<Post> posts;
    /**
     * @Fields topiccount : 主题数
     */
    private BigDecimal topiccount;
    /**
     * @Fields postcount : 帖子数
     */
    private BigDecimal postcount;
    /**
     * @Fields replycount : 回复数
     */
    private BigDecimal replycount;
    /**
     * @Fields isDeleted : 是否删除标识
     */
    private Integer isDeleted;
    /**
     *  @Fields isHot : 是否热门版块
     */
    private Integer isHot;
         
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

    public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

	public User getCreater() {
		return creater;
	}

	public void setCreater(User creater) {
		this.creater = creater;
	}

	public List<Topic> getTopics() {
		return topics;
	}

	public void setTopics(List<Topic> topics) {
		this.topics = topics;
	}

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public BigDecimal getTopiccount() {
		return topiccount;
	}

	public void setTopiccount(BigDecimal topiccount) {
		this.topiccount = topiccount;
	}

	public BigDecimal getPostcount() {
		return postcount;
	}

	public void setPostcount(BigDecimal postcount) {
		this.postcount = postcount;
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

	public Integer getIsHot() {
		return isHot;
	}

	public void setIsHot(Integer isHot) {
		this.isHot = isHot;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result
				+ ((createdAt == null) ? 0 : createdAt.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((isDeleted == null) ? 0 : isDeleted.hashCode());
		result = prime * result + ((isHot == null) ? 0 : isHot.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((postcount == null) ? 0 : postcount.hashCode());
		result = prime * result
				+ ((replycount == null) ? 0 : replycount.hashCode());
		result = prime * result
				+ ((topiccount == null) ? 0 : topiccount.hashCode());
		result = prime * result
				+ ((updatedAt == null) ? 0 : updatedAt.hashCode());
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
		Park other = (Park) obj;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (createdAt == null) {
			if (other.createdAt != null)
				return false;
		} else if (!createdAt.equals(other.createdAt))
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
		if (isHot == null) {
			if (other.isHot != null)
				return false;
		} else if (!isHot.equals(other.isHot))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (postcount == null) {
			if (other.postcount != null)
				return false;
		} else if (!postcount.equals(other.postcount))
			return false;
		if (replycount == null) {
			if (other.replycount != null)
				return false;
		} else if (!replycount.equals(other.replycount))
			return false;
		if (topiccount == null) {
			if (other.topiccount != null)
				return false;
		} else if (!topiccount.equals(other.topiccount))
			return false;
		if (updatedAt == null) {
			if (other.updatedAt != null)
				return false;
		} else if (!updatedAt.equals(other.updatedAt))
			return false;
		return true;
	}
	
}