package bss.model.prms;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

public class FirstAuditTemplat implements Serializable{
    /**
	 * @Fields serialVersionUID : 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

	  @NotNull(message = "名称不能为空")
    private String name;

    /**
     * 模板类型
     */
	  @NotNull(message = "模板类型不能为空")
    private String kind;

    private String creater;

    private Date createdAt;

    private Date updatedAt;

    private String userId;

    private Short isUse;

    private Short isOpen;
    
    /**
     * @Fields categoryId : 所属产品目录
     */
    @NotNull(message = "所属产品目录不能为空")
    private String categoryId;

    /**
     * @Fields categoryName : 所属产品目录名称，不做数据存储
     */
    private String categoryName;
    
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public Short getIsUse() {
        return isUse;
    }

    public void setIsUse(Short isUse) {
        this.isUse = isUse;
    }

    public Short getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Short isOpen) {
        this.isOpen = isOpen;
    }

    public String getCategoryId() {
      return categoryId;
    }

    public void setCategoryId(String categoryId) {
      this.categoryId = categoryId;
    }

    public String getCategoryName() {
      return categoryName;
    }

    public void setCategoryName(String categoryName) {
      this.categoryName = categoryName;
    }
    
}