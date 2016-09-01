package yggc.model.iss.fs;
import java.util.Date;
import java.util.List;

import yggc.model.bms.User;
/**
* <p>Title:Topic </p>
* <p>Description:用户实体类 </p>
* <p>Company: yggc </p> 
* @author junjunjun1993
* @date 2016-8-3下午4:53:36
*/
public class Topic {
    /**
     * @Fields id : 主键
     */
    private Integer id;
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
    private Date createdAt;
    /**
     * @Fields updatedAt : 更新时间
     */
    private Date updatedAt;

    /**
     * @Fields park : 所属版块
     */
    private Park park;
    /**
     * @Fields user : 创建人
     */
    private User creater; 
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

	public Park getPark() {
		return park;
	}

	public void setPark(Park park) {
		this.park = park;
	}

	public User getCreater() {
		return creater;
	}

	public void setCreater(User creater) {
		this.creater = creater;
	}


    
}