package iss.model.fs;

import java.sql.Timestamp;

import ses.model.bms.User;

/**
* @Title:Reply 
* @Description: 回复实体类
* @author Peng Zhongjun
* @date 2016-9-7下午6:27:03
 */
public class Reply {
    /**
     * @Fields id : 主键
     */
    private String id;
    /**
     * @Fields name : 回复名称
     */
    private String name;
    /**
     * @Fields content : 内容
     */
    private String content;

    /**
     * @Fields publishedTime : 发表时间
     */
    private Timestamp publishedTime;
    /**
     * @Fields post : 所属帖子
     */
    private Post post;
    /**
     * @Fields reply : 所属回复
     */
    private Reply reply;
    /**
     * @Fields user : 回复人
     */
    private User user;

    
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


    public Timestamp getPublishedTime() {
        return publishedTime;
    }

    public void setPublishedTime(Timestamp publishedTime) {
        this.publishedTime = publishedTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public Reply getReply() {
		return reply;
	}

	public void setReply(Reply reply) {
		this.reply = reply;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
 
}