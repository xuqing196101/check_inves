package system.model.sms;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;


/**
 * 
 * Description: 待发送短信临时表  用于同步外网定时发短信
 * 
 * @version 2018年1月5日
 * @since JDK1.7
 */
public class SmsRecordTemp {
	
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
     * 删除标识   0未删除 1删除
     */
    private Short isDeleted;

    /**
     * 机构ID
     */
    private String orgId;
    
    /**
     * 修改时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedAt;

    public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
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

    public Short getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId == null ? null : orgId.trim();
    }
}