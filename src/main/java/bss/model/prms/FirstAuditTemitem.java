package bss.model.prms;

import java.io.Serializable;
import java.util.Date;

public class FirstAuditTemitem implements Serializable{
    /**
	 * @Fields serialVersionUID : 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

    /**
     * @Fields name : TODO(目的和意义)评审名称
     */
    private String name;

    private String kind;

    private String creater;

    private Date createdAt;

    private Date updatedAt;
    
    /**
     * @Fields position : TODO(目的和意义)排序
     */
    private Integer position;
    
    /**
     * @Fields content : TODO(目的和意义)评审内容
     */
    private String content;
    
    private String templatId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind == null ? null : kind.trim();
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater == null ? null : creater.trim();
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

    public String getTemplatId() {
        return templatId;
    }

    public void setTemplatId(String templatId) {
        this.templatId = templatId == null ? null : templatId.trim();
    }

    public Integer getPosition() {
      return position;
    }

    public void setPosition(Integer position) {
      this.position = position;
    }

    public String getContent() {
      return content;
    }

    public void setContent(String content) {
      this.content = content;
    }
    
}