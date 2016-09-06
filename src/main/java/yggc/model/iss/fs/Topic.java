package yggc.model.iss.fs;
import java.sql.Timestamp;
import java.util.List;

import yggc.model.bms.User;
/**
* <p>Title:Topic </p>
* <p>Description:用户实体类 </p>
* <p>Company: yggc </p> 
* @author Peng Zhongjun
* @Timestamp 2016-8-3下午4:53:36
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
    private Timestamp upTimestampdAt;

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

    public Timestamp getUpTimestampdAt() {
        return upTimestampdAt;
    }

    public void setUpTimestampdAt(Timestamp upTimestampdAt) {
        this.upTimestampdAt = upTimestampdAt;
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
}