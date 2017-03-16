package ses.model.sms;

import java.io.Serializable;
import java.util.Date;

/**
 * 版权：(C) 版权所有
 * <简述>
 *  售后服务表对应实体类
 * <详细描述>
 * @author   LiChenHao
 * @since    2017年3月2日
 * @see
 */

public class AfterSaleSer implements Serializable{
	
	private static final long serialVersionUID = 1L;
    
    /** 主键ID **/
    private String id;
    
    /** 售后服务地址 **/
    private String address;
    
    /** 联系人 **/
    private String contactName;
    
    /** 联系方式 **/
    private String mobile;

    /** 是否售后服务0：未添加售后1：已添加售后 **/
    private String isAfterSupport;
    
    /** 创建时间 **/
    private Date createdAt;
    
    /** 修改时间 **/
    private Date updateAt;
    
    /** 是否删除0：未删除1：已删除 **/
    private String isDeleted;
    
    /** 明细id **/
    private String requiredId;
    
    /** 申请人 **/
    private String proposer;
    
    /** 申请人电话 **/
    private String proMobile;
    
    /** 供应商名称 **/
    private String supplierName;
    
    /** 问题描述 **/
    private String question;
    
	/** 技术参数 **/
    private String technicalParameters;
    
    public String getTechnicalParameters() {
		return technicalParameters;
	}

	public void setTechnicalParameters(String technicalParameters) {
		this.technicalParameters = technicalParameters;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getIsAfterSupport() {
		return isAfterSupport;
	}

	public void setIsAfterSupport(String isAfterSupport) {
		this.isAfterSupport = isAfterSupport;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}

	public String getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getRequiredId() {
		return requiredId;
	}

	public void setRequiredId(String requiredId) {
		this.requiredId = requiredId;
	}

	public String getProposer() {
		return proposer;
	}

	public void setProposer(String proposer) {
		this.proposer = proposer;
	}

	public String getProMobile() {
		return proMobile;
	}

	public void setProMobile(String proMobile) {
		this.proMobile = proMobile;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}
    
    
}
