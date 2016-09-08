package iss.model.fs;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import ses.model.bms.User;

/**
* @Title:Topic 
* @Description: 主题实体类
* @author Peng Zhongjun
* @date 2016-9-7下午6:27:25
 */
public class Topic {
    /**
     * @Fields id : 主键
     */
    private String id;
    /**
     * @Fields name : 主题名称
     */
    private String name;
    
    /**
     * @Fields content : 主题内容
     */
    private String content;
    /**
     * @Fields createdAt : 创建时间
     */
    private Timestamp createdAt;
    /**
     * @Fields upTimestampdAt : 更新时间
     */
    private Timestamp updatedAt;
    /**
     * @Fields park : 所属版块
     */
    private Park park;
    /**
     * @Fields user : 创建人
     */
    private User user; 
    /**
     * @Fields posts : 帖子
     */
    private List<Post> posts;
    /**
     * @Fields posts : 帖子数
     */
    private BigDecimal postcount;
    /**
     * @Fields posts : 回复
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

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

	public Park getPark() {
		return park;
	}

	public void setPark(Park park) {
		this.park = park;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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