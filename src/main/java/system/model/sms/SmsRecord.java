package system.model.sms;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 
 * Description: 短信发送记录实体
 * 
 * @date 2017年12月6日
 * @since JDK1.7
 */
public class SmsRecord {
	
    private String id;

    /**
     * 短信发送环节
     */
    private String sendLink;

    /**
     * 操作人
     */
    private String operator;

    /**
     * 发送内容
     */
    private String sendContent;

    /**
     * 接收人
     */
    private String recipient;

    /**
     * 接收号码
     */
    private String receiveNumber;

    /**
     * 发送时间
     */
    private Date sendTime;

    /**
     * 状态
     */
    private String status;

    /**
     * 失败原因
     */
    private String failReason;

    /**
     * 修改时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedAt;

    /**
     * 删除标识   0未删除 1删除
     */
    private Short isDeleted;
    
    /**
     * 查询条件  开始时间
     */
    private String startTime;
    
    /**
     * 查询条件   结束时间
     */
    private String endTime;
    
    public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getSendLink() {
        return sendLink;
    }

    public void setSendLink(String sendLink) {
        this.sendLink = sendLink == null ? null : sendLink.trim();
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public String getSendContent() {
        return sendContent;
    }

    public void setSendContent(String sendContent) {
        this.sendContent = sendContent == null ? null : sendContent.trim();
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient == null ? null : recipient.trim();
    }

    public String getReceiveNumber() {
        return receiveNumber;
    }

    public void setReceiveNumber(String receiveNumber) {
        this.receiveNumber = receiveNumber == null ? null : receiveNumber.trim();
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getFailReason() {
        return failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason == null ? null : failReason.trim();
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Short getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }
}