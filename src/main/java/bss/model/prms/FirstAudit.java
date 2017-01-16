package bss.model.prms;

import java.util.Date;

public class FirstAudit {
    private String id;

    private String name;

    private String kind;

    private Date createdAt;

    private String creater;

    private Date updatedAt;

    private String projectId;
    
    private String projectName;//不做数据存储
    
    private String packageName;//不做数据存储

    private Short isConfirm;//是否是经济技术评审项 0：否 1：是
    
    private Integer page;
    
    /**
     * @Fields packageId : TODO(目的和意义)所属包id
     */
    private String packageId;
    
    /**
     * @Fields position : TODO(目的和意义)排序
     */
    private Integer position;
    
    /**
     * @Fields content : TODO(目的和意义)评审内容
     */
    private String content;

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

    public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater == null ? null : creater.trim();
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    public Short getIsConfirm() {
        return isConfirm;
    }

    public void setIsConfirm(Short isConfirm) {
        this.isConfirm = isConfirm;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
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

    public String getPackageId() {
      return packageId;
    }

    public void setPackageId(String packageId) {
      this.packageId = packageId;
    }

    public String getProjectName() {
      return projectName;
    }

    public void setProjectName(String projectName) {
      this.projectName = projectName;
    }

    public String getPackageName() {
      return packageName;
    }

    public void setPackageName(String packageName) {
      this.packageName = packageName;
    }
    
}