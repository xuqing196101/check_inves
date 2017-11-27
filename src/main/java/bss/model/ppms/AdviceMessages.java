package bss.model.ppms;

import java.io.Serializable;
import java.util.Date;

public class AdviceMessages implements Serializable {
    /**
   * 
   */
  private static final long serialVersionUID = 1L;

    private String id;

    private String code;

    private String projectId;

    private String managers;

    private String sender;

    private Date createAt;

    private Date carryoutAt;

    private Short status;

    private Short isDelete;

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

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    public String getManagers() {
        return managers;
    }

    public void setManagers(String managers) {
        this.managers = managers == null ? null : managers.trim();
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender == null ? null : sender.trim();
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getCarryoutAt() {
        return carryoutAt;
    }

    public void setCarryoutAt(Date carryoutAt) {
        this.carryoutAt = carryoutAt;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Short getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Short isDelete) {
        this.isDelete = isDelete;
    }
}