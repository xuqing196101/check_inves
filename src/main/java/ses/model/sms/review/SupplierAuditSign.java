package ses.model.sms.review;

import java.util.Date;

/**
 * 
 * T_SES_SMS_SUPPLIER_AUDIT_SIGN
 * 
 * @author yggc
 * 
 * @date 2018-01-04
 *
 */
public class SupplierAuditSign {
    /**
     * null
     */
    private String id;

    /**
     * 单位
     */
    private String company;

    /**
     * 名字
     */
    private String name;

    /**
     * 职业
     */
    private String job;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 更新时间
     */
    private Date updatedAt;

    /**
     * 供应商id
     */
    private String supplierId;

    /**
     * 批次
     */
    private String batch;

    /**
     * null
     * @return ID null
     */
    public String getId() {
        return id;
    }

    /**
     * null
     * @param id null
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 单位
     * @return COMPANY 单位
     */
    public String getCompany() {
        return company;
    }

    /**
     * 单位
     * @param company 单位
     */
    public void setCompany(String company) {
        this.company = company;
    }

    /**
     * 名字
     * @return NAME 名字
     */
    public String getName() {
        return name;
    }

    /**
     * 名字
     * @param name 名字
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 职业
     * @return JOB 职业
     */
    public String getJob() {
        return job;
    }

    /**
     * 职业
     * @param job 职业
     */
    public void setJob(String job) {
        this.job = job;
    }

    /**
     * 创建时间
     * @return CREATED_AT 创建时间
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * 创建时间
     * @param createdAt 创建时间
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * 更新时间
     * @return UPDATED_AT 更新时间
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * 更新时间
     * @param updatedAt 更新时间
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * 供应商id
     * @return SUPPLIER_ID 供应商id
     */
    public String getSupplierId() {
        return supplierId;
    }

    /**
     * 供应商id
     * @param supplierId 供应商id
     */
    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    /**
     * 批次
     * @return BATCH 批次
     */
    public String getBatch() {
        return batch;
    }

    /**
     * 批次
     * @param batch 批次
     */
    public void setBatch(String batch) {
        this.batch = batch;
    }
}