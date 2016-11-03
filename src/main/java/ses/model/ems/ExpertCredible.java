package ses.model.ems;

import java.util.Date;

public class ExpertCredible {
    private String id;

    private String badBehavior;

    private Short isDelete;

    private Short isStatus;

    private Date createAt;

    private Date updateAt;

    private Integer score;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getBadBehavior() {
        return badBehavior;
    }

    public void setBadBehavior(String badBehavior) {
        this.badBehavior = badBehavior == null ? null : badBehavior.trim();
    }

    public Short getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Short isDelete) {
        this.isDelete = isDelete;
    }

    public Short getIsStatus() {
        return isStatus;
    }

    public void setIsStatus(Short isStatus) {
        this.isStatus = isStatus;
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

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}