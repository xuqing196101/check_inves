package ses.model.ems;

import java.util.Date;

public class ExpertAttachment {
    private String id;
    //文件类型
    private String contentType;
    //文件名
    private String fileName;
    //文件大小
    private Double fileSize;
    //创建时间
    private Date createAt;
    //修改时间
    private Date updateAt;
    //专家id
    private String expertId;
    //文件全路径
    private String filePath;
    //是否删除 0否 1是
    private Short isDelete;
    //是否为历史数据 0否 1是
    private Short isHistory;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType == null ? null : contentType.trim();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    public Double getFileSize() {
        return fileSize;
    }

    public void setFileSize(Double fileSize) {
        this.fileSize = fileSize;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public String getExpertId() {
        return expertId;
    }

    public void setExpertId(String expertId) {
        this.expertId = expertId == null ? null : expertId.trim();
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath == null ? null : filePath.trim();
    }

    public Short getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Short isDelete) {
        this.isDelete = isDelete;
    }

    public Short getIsHistory() {
        return isHistory;
    }

    public void setIsHistory(Short isHistory) {
        this.isHistory = isHistory;
    }
}