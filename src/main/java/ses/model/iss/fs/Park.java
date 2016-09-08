package ses.model.iss.fs;

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
public class Park {
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
	
	
	
}