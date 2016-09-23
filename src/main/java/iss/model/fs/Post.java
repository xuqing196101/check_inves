package iss.model.fs;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

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
    private String name;
    /**
     * @Fields content : 帖子内容
     */
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
	   
}