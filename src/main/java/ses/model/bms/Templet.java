package ses.model.bms;

import java.sql.Timestamp;
import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;

public class Templet {
    private String id;
    
    @NotBlank(message = "模板名称不能为空")
    private String temType;
    
    @NotBlank(message = "模板名称不能为空")
    private String name;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    private int isDeleted;
    
    @NotBlank(message = "模板名称不能为空")
    private String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTemType() {
        return temType;
    }

    public void setTemType(String temType) {
        this.temType = temType == null ? null : temType.trim();
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

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}