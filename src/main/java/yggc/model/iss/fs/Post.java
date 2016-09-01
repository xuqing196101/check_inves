package yggc.model.iss.fs;
import java.util.Date;
import java.util.List;

import yggc.model.bms.User;
/**
* <p>Title:Post </p>
* <p>Description:用户实体类 </p>
* <p>Company: yggc </p> 
* @author junjunjun1993
* @date 2016-8-3下午4:53:36
*/
public class Post {
    /**
     * @Fields id : 主键
     */
    private Integer id;
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
     * @Fields isEssence : 是否精华
     */
    private Integer isEssence;
    /**
     * @Fields isCanReply : 是否可回复
     */
    private Integer isCanReply;

    /**
     * @Fields publishedTime : 发布时间
     */
    private Date publishedTime;
    /**
     * @Fields lastReplyedTime : 最后回复时间
     */
    private Date lastReplyedTime;
    
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
     * @Fields topic : 回复
     */
    private List<Reply> replies;


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

    public Integer getIsEssence() {
        return isEssence;
    }

    public void setIsEssence(Integer isEssence) {
        this.isEssence = isEssence;
    }

    public Integer getIsCanReply() {
        return isCanReply;
    }

    public void setIsCanReply(Integer isCanReply) {
        this.isCanReply = isCanReply;
    }
    public Date getPublishedTime() {
        return publishedTime;
    }

    public void setPublishedTime(Date publishedTime) {
        this.publishedTime = publishedTime;
    }

    public Date getLastReplyedTime() {
        return lastReplyedTime;
    }

    public void setLastReplyedTime(Date lastReplyedTime) {
        this.lastReplyedTime = lastReplyedTime;
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
	   
}