package app.model;

import java.util.Date;

public class AppInfo {

    /**
     * 版本号
     */
    private String version;

    /**
     * 路径
     */
    private String path;

    /**
     * 备注（描述信息）
     */
    private String remark;

    /**
     * 添加时间
     */
    private Date createdAt;

    /**
     * 修改时间
     */
    private Date updatedAt;

    /**
     * 删除标识 0正常 1删除
     */
    private Integer isDeleted;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version.trim();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    @Override
    public String toString() {
        return "AppInfo [version=" + version + ", path=" + path + ", remark="
                + remark + ", createdAt=" + createdAt + ", updatedAt="
                + updatedAt + ", isDeleted=" + isDeleted + "]";
    }

}
