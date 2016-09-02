package yggc.model.iss.fs;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import yggc.model.bms.User;
/**
* <p>Title:Park </p>
* <p>Description:用户实体类 </p>
* <p>Company: yggc </p> 
* @author junjunjun1993
* @date 2016-8-3下午4:53:36
*/
public class Park {
    /**
     * @Fields id : 主键
     */
    private Integer id;
    
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
     * @Fields user : 版主
     */
    private User user;
    /**
     * @Fields creater : 创建人
     */
    private User creater;
    /**
     * @Fields topics : 主题
     */
    private List<Topic> topics;
    /**
     * @Fields posts : 帖子
     */
    private List<Post> posts;
      
    
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
	
	
	
}