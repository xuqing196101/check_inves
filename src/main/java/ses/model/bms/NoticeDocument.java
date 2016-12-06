package ses.model.bms;

import java.sql.Timestamp;

import org.hibernate.validator.constraints.NotBlank;

public class NoticeDocument {
    private String id;

    @NotBlank(message = "须知模板名称不能为空")
    private String name;

    @NotBlank(message = "须知模板类型不能为空")
    private String docType;

    @NotBlank(message = "须知模板内容不能为空")
    private String content;

    private Timestamp createdAt;

    private Timestamp updatedAt;

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

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType == null ? null : docType.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
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
}