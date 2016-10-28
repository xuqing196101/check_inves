package ses.model.bms;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

public class AttachmentType implements Serializable{
    
    private static final long serialVersionUID = 1L;

    private String id;

    @NotNull(message = "编码不能为空")  
    private String code;

    @NotNull(message = "描述不能为空") 
    private String description;

    private Date createdAt;

    private Date updatedAt;

    private Integer isDeleted;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
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

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }
}