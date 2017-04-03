package bss.model.ppms;

import java.util.Date;

/**
 * 版权：(C) 版权所有 
 * <简述>流程环节定义实体
 * <详细描述>
 * @author   Ye MaoLin
 * @version  
 * @since
 * @see
 */
public class FlowDefine {
    
    private static final long serialVersionUID = 1L;
    
    private String id;

    /**
     * @Fields name : 名称
     */
    private String name;
    
    /**
     * @Fields code : 编码
     */
    private String code;

    /**
     * @Fields step : 步骤
     */
    private Integer step;
    
    /**
     * @Fields url : 跳转路径
     */
    private String url;

    /**
     * @Fields createdAt : 创建时间
     */
    private Date createdAt;

    /**
     * @Fields updatedAt : 修改时间
     */
    private Date updatedAt;

    /**
     * @Fields isDeleted : 是否删除 0：未删除， 1：删除
     */
    private Integer isDeleted;

    /**
     * @Fields purchaseTypeId : 采购方式ID
     */
    private String purchaseTypeId;
    
    /**
     * @Fields status : 0:未执行，1:已执行，2：执行中，3：环节结束（不可再操作），4：将要执行
     */
    private Integer status;

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

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
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

    public String getPurchaseTypeId() {
        return purchaseTypeId;
    }

    public void setPurchaseTypeId(String purchaseTypeId) {
        this.purchaseTypeId = purchaseTypeId == null ? null : purchaseTypeId.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCode() {
      return code;
    }

    public void setCode(String code) {
      this.code = code;
    }
    
}