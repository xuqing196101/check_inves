package ses.model.bms;

import java.util.Date;
/**
 * <p>Title: UserTask</p>
 * <p>Description: 用户任务管理实体</p> 
 * @author Li Xiaoxiao
 * @date  2016年9月7日,下午5:37:07
 *
 */
public class UserTask {
	/**
	 * 主键
	 */
    private String id;
    /**
     * 内容
     */
    private String content;
    /**
     * 开始日期
     */
    private Date startDate;
    /**
     * 结束日期
     */
    private Date endDate;
    /**
     * 状态
     */
    private String status;
    /**
     * 创建日期
     */
    private Date createdAt;
    /**
     * 更新日期
     */
    private Date updatedAt;
    /**
     * 备注
     */
    private String memo;
    /**
     * 事情级别
     */
    private String level;
    /**
     * 用户id
     */
    private String userId;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
    	this.status = status == null ? null : status.trim();
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

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level == null ? null : level.trim();
    }

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
	}
    
    
}