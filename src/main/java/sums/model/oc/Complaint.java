package sums.model.oc;

import java.util.Date;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述> 网上投诉
 * <详细描述>
 * @author   jff
 * @version  
 * @since
 * @see
 */

public class Complaint {
    /**
     * <pre>
     * 主键
     * 表字段 : T_SUMS_OC_COMPLAINT.ID
     * </pre>
     */
    private String id;

    /**
     * <pre>
     * 投诉人名称
     * 表字段 : T_SUMS_OC_COMPLAINT.NAME
     * </pre>
     */
    private String name;

    /**
     * <pre>
     * 投诉人类型
     * 表字段 : T_SUMS_OC_COMPLAINT.TYPE
     * </pre>
     */
    private Integer type;

    /**
     * <pre>
     * 投诉对象
     * 表字段 : T_SUMS_OC_COMPLAINT.COMPLAINT_OBJECT
     * </pre>
     */
    private String complaintObject;

    /**
     * <pre>
     * 投诉事项
     * 表字段 : T_SUMS_OC_COMPLAINT.COMPLAINT_MATTER
     * </pre>
     */
    private String complaintMatter;

    /**
     * <pre>
     * 是否立项状态
     * 表字段 : T_SUMS_OC_COMPLAINT.STATUS
     * </pre>
     */
    private Integer status;

    /**
     * <pre>
     * 处理内容信息
     * 表字段 : T_SUMS_OC_COMPLAINT.RESION
     * </pre>
     */
    private String resion;

    /**
     * <pre>
     * 是否删除
     * 表字段 : T_SUMS_OC_COMPLAINT.IS_DELETED
     * </pre>
     */
    private Integer isDeleted;

    /**
     * <pre>
     * 创建时间
     * 表字段 : T_SUMS_OC_COMPLAINT.CREATED_AT
     * </pre>
     */
    private Date createdAt;

    /**
     * <pre>
     * 修改时间
     * 表字段 : T_SUMS_OC_COMPLAINT.UPDATED_AT
     * </pre>
     */
    private Date updatedAt;
    /**
     * <pre>
     * 创建人
     * 表字段 : T_SUMS_OC_COMPLAINT.CREATER_ID
     * </pre>
     */
    private String createrId;
    /**
     * <pre>
     * 审核人
     * 表字段 : T_SUMS_OC_COMPLAINT.AUDIT_ID
     * </pre>
     */
    private String auditId;
    /**
     * <pre>
     * 获取：主键
     * 表字段：T_SUMS_OC_COMPLAINT.ID
     * </pre>
     *
     * @return T_SUMS_OC_COMPLAINT.ID：主键
     */
    public String getId() {
        return id;
    }

    /**
     * <pre>
     * 设置：主键
     * 表字段：T_SUMS_OC_COMPLAINT.ID
     * </pre>
     *
     * @param id
     *            T_SUMS_OC_COMPLAINT.ID：主键
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * <pre>
     * 获取：投诉人名称
     * 表字段：T_SUMS_OC_COMPLAINT.NAME
     * </pre>
     *
     * @return T_SUMS_OC_COMPLAINT.NAME：投诉人名称
     */
    public String getName() {
        return name;
    }

    /**
     * <pre>
     * 设置：投诉人名称
     * 表字段：T_SUMS_OC_COMPLAINT.NAME
     * </pre>
     *
     * @param name
     *            T_SUMS_OC_COMPLAINT.NAME：投诉人名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * <pre>
     * 获取：投诉人类型
     * 表字段：T_SUMS_OC_COMPLAINT.TYPE
     * </pre>
     *
     * @return T_SUMS_OC_COMPLAINT.TYPE：投诉人类型
     */
    public Integer getType() {
        return type;
    }

    /**
     * <pre>
     * 设置：投诉人类型
     * 表字段：T_SUMS_OC_COMPLAINT.TYPE
     * </pre>
     *
     * @param type
     *            T_SUMS_OC_COMPLAINT.TYPE：投诉人类型
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * <pre>
     * 获取：投诉对象
     * 表字段：T_SUMS_OC_COMPLAINT.COMPLAINT_OBJECT
     * </pre>
     *
     * @return T_SUMS_OC_COMPLAINT.COMPLAINT_OBJECT：投诉对象
     */
    public String getComplaintObject() {
        return complaintObject;
    }

    /**
     * <pre>
     * 设置：投诉对象
     * 表字段：T_SUMS_OC_COMPLAINT.COMPLAINT_OBJECT
     * </pre>
     *
     * @param complaintObject
     *            T_SUMS_OC_COMPLAINT.COMPLAINT_OBJECT：投诉对象
     */
    public void setComplaintObject(String complaintObject) {
        this.complaintObject = complaintObject == null ? null : complaintObject.trim();
    }

    /**
     * <pre>
     * 获取：投诉事项
     * 表字段：T_SUMS_OC_COMPLAINT.COMPLAINT_MATTER
     * </pre>
     *
     * @return T_SUMS_OC_COMPLAINT.COMPLAINT_MATTER：投诉事项
     */
    public String getComplaintMatter() {
        return complaintMatter;
    }

    /**
     * <pre>
     * 设置：投诉事项
     * 表字段：T_SUMS_OC_COMPLAINT.COMPLAINT_MATTER
     * </pre>
     *
     * @param complaintMatter
     *            T_SUMS_OC_COMPLAINT.COMPLAINT_MATTER：投诉事项
     */
    public void setComplaintMatter(String complaintMatter) {
        this.complaintMatter = complaintMatter == null ? null : complaintMatter.trim();
    }

    /**
     * <pre>
     * 获取：是否立项状态
     * 表字段：T_SUMS_OC_COMPLAINT.STATUS
     * </pre>
     *
     * @return T_SUMS_OC_COMPLAINT.STATUS：是否立项状态
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * <pre>
     * 设置：是否立项状态
     * 表字段：T_SUMS_OC_COMPLAINT.STATUS
     * </pre>
     *
     * @param status
     *            T_SUMS_OC_COMPLAINT.STATUS：是否立项状态
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * <pre>
     * 获取：处理内容信息
     * 表字段：T_SUMS_OC_COMPLAINT.RESION
     * </pre>
     *
     * @return T_SUMS_OC_COMPLAINT.RESION：处理内容信息
     */
    public String getResion() {
        return resion;
    }

    /**
     * <pre>
     * 设置：处理内容信息
     * 表字段：T_SUMS_OC_COMPLAINT.RESION
     * </pre>
     *
     * @param resion
     *            T_SUMS_OC_COMPLAINT.RESION：处理内容信息
     */
    public void setResion(String resion) {
        this.resion = resion == null ? null : resion.trim();
    }

    /**
     * <pre>
     * 获取：是否删除
     * 表字段：T_SUMS_OC_COMPLAINT.IS_DELETED
     * </pre>
     *
     * @return T_SUMS_OC_COMPLAINT.IS_DELETED：是否删除
     */
    public Integer getIsDeleted() {
        return isDeleted;
    }

    /**
     * <pre>
     * 设置：是否删除
     * 表字段：T_SUMS_OC_COMPLAINT.IS_DELETED
     * </pre>
     *
     * @param isDeleted
     *            T_SUMS_OC_COMPLAINT.IS_DELETED：是否删除
     */
    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * <pre>
     * 获取：创建时间
     * 表字段：T_SUMS_OC_COMPLAINT.CREATED_AT
     * </pre>
     *
     * @return T_SUMS_OC_COMPLAINT.CREATED_AT：创建时间
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * <pre>
     * 设置：创建时间
     * 表字段：T_SUMS_OC_COMPLAINT.CREATED_AT
     * </pre>
     *
     * @param createdAt
     *            T_SUMS_OC_COMPLAINT.CREATED_AT：创建时间
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * <pre>
     * 获取：修改时间
     * 表字段：T_SUMS_OC_COMPLAINT.UPDATED_AT
     * </pre>
     *
     * @return T_SUMS_OC_COMPLAINT.UPDATED_AT：修改时间
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * <pre>
     * 设置：修改时间
     * 表字段：T_SUMS_OC_COMPLAINT.UPDATED_AT
     * </pre>
     *
     * @param updatedAt
     *            T_SUMS_OC_COMPLAINT.UPDATED_AT：修改时间
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
    /**
     * <pre>
     * 获取：操作人
     * 表字段：T_SUMS_OC_COMPLAINT.CREATER_ID
     * </pre>
     *
     * @return T_SUMS_OC_COMPLAINT.CREATER_ID：操作人
     */
    public String getCreaterId() {
        return createrId;
    }

    /**
     * <pre>
     * 设置：操作人
     * 表字段：T_SUMS_OC_COMPLAINT.CREATER_ID
     * </pre>
     *
     * @param updatedAt
     *            T_SUMS_OC_COMPLAINT.CREATER_ID：操作人
     */
    public void  setCreaterId(String createrId) {
        this.createrId = createrId;
    }
    
    public String getAuditId() {
        return auditId;
    }

    public void setAuditId(String auditId){
    	this.auditId = auditId;
    }
}