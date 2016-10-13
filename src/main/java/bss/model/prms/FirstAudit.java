package bss.model.prms;

import java.io.Serializable;
import java.util.Date;

public class FirstAudit implements Serializable{
    /**
	 * @Fields serialVersionUID : 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
    /**初审项名称*/
    private String name;
    /**初审项类型*/
    private String kind;
    /**初审项创建时间*/
    private String createdAt;
    /**初审项创建人*/
    private String creater;
    /**初审项修改时间*/
    private Date updatedAt;
    /**关联项目id*/
    private String projectId;

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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt == null ? null : createdAt.trim();
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
}