package ses.model.sms.review;

/**
 * 附件审核表（供应商复核和实地考察用）
 * 
 * T_SES_SMS_SUPPLIER_ATCH_AUDIT
 * 
 * @author yggc
 * 
 * @date 2017-12-29
 *
 */
public class SupplierAttachAudit {
    /**
     * 主键
     */
    private String id;

    /**
     * 供应商ID
     */
    private String supplierId;

    /**
     * 附件类型ID
     */
    private String typeId;

    /**
     * 附件业务ID
     */
    private String businessId;

    /**
     * 附件CODE
     */
    private String attachCode;

    /**
     * 附件名称
     */
    private String attachName;

    /**
     * 是否一致
     */
    private Integer isAccord;

    /**
     * 理由
     */
    private String suggest;

    /**
     * 附件查看的URL
     */
    private String viewUrl;

    /**
     * 审核类型（1：复核；2：考察）
     */
    private Integer auditType;

    /**
     * 是否删除 0 有效 1 删除
     */
    private Integer isDeleted;

    /**
     * 显示位置（排序用）
     */
    private Integer position;

    /**
     * 附件列表，存储json格式，例如： -- [{"xxx":[{"typeId":"xxx","businessId":"xxx","attachName":"xxx"}...]}...] -- [{"typeId":"xxx","businessId":"xxx","attachName":"xxx"}...]
     */
    private String attachList;

    /**
     * 主键
     * @return ID 主键
     */
    public String getId() {
        return id;
    }

    /**
     * 主键
     * @param id 主键
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 供应商ID
     * @return SUPPLIER_ID 供应商ID
     */
    public String getSupplierId() {
        return supplierId;
    }

    /**
     * 供应商ID
     * @param supplierId 供应商ID
     */
    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    /**
     * 附件类型ID
     * @return TYPE_ID 附件类型ID
     */
    public String getTypeId() {
        return typeId;
    }

    /**
     * 附件类型ID
     * @param typeId 附件类型ID
     */
    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    /**
     * 附件业务ID
     * @return BUSINESS_ID 附件业务ID
     */
    public String getBusinessId() {
        return businessId;
    }

    /**
     * 附件业务ID
     * @param businessId 附件业务ID
     */
    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    /**
     * 附件CODE
     * @return ATTACH_CODE 附件CODE
     */
    public String getAttachCode() {
        return attachCode;
    }

    /**
     * 附件CODE
     * @param attachCode 附件CODE
     */
    public void setAttachCode(String attachCode) {
        this.attachCode = attachCode;
    }

    /**
     * 附件名称
     * @return ATTACH_NAME 附件名称
     */
    public String getAttachName() {
        return attachName;
    }

    /**
     * 附件名称
     * @param attachName 附件名称
     */
    public void setAttachName(String attachName) {
        this.attachName = attachName;
    }

    /**
     * 是否一致
     * @return IS_ACCORD 是否一致
     */
    public Integer getIsAccord() {
        return isAccord;
    }

    /**
     * 是否一致
     * @param isAccord 是否一致
     */
    public void setIsAccord(Integer isAccord) {
        this.isAccord = isAccord;
    }

    /**
     * 理由
     * @return SUGGEST 理由
     */
    public String getSuggest() {
        return suggest;
    }

    /**
     * 理由
     * @param suggest 理由
     */
    public void setSuggest(String suggest) {
        this.suggest = suggest;
    }

    /**
     * 附件查看的URL
     * @return VIEW_URL 附件查看的URL
     */
    public String getViewUrl() {
        return viewUrl;
    }

    /**
     * 附件查看的URL
     * @param viewUrl 附件查看的URL
     */
    public void setViewUrl(String viewUrl) {
        this.viewUrl = viewUrl;
    }

    /**
     * 审核类型（1：复核；2：考察）
     * @return AUDIT_TYPE 审核类型（1：复核；2：考察）
     */
    public Integer getAuditType() {
        return auditType;
    }

    /**
     * 审核类型（1：复核；2：考察）
     * @param auditType 审核类型（1：复核；2：考察）
     */
    public void setAuditType(Integer auditType) {
        this.auditType = auditType;
    }

    /**
     * 是否删除 0 有效 1 删除
     * @return IS_DELETED 是否删除 0 有效 1 删除
     */
    public Integer getIsDeleted() {
        return isDeleted;
    }

    /**
     * 是否删除 0 有效 1 删除
     * @param isDeleted 是否删除 0 有效 1 删除
     */
    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * 显示位置（排序用）
     * @return POSITION 显示位置（排序用）
     */
    public Integer getPosition() {
        return position;
    }

    /**
     * 显示位置（排序用）
     * @param position 显示位置（排序用）
     */
    public void setPosition(Integer position) {
        this.position = position;
    }

    /**
     * 附件列表，存储json格式，例如： -- [{"xxx":[{"typeId":"xxx","businessId":"xxx","attachName":"xxx"}...]}...] -- [{"typeId":"xxx","businessId":"xxx","attachName":"xxx"}...]
     * @return ATTACH_LIST 附件列表，存储json格式，例如： -- [{"xxx":[{"typeId":"xxx","businessId":"xxx","attachName":"xxx"}...]}...] -- [{"typeId":"xxx","businessId":"xxx","attachName":"xxx"}...]
     */
    public String getAttachList() {
        return attachList;
    }

    /**
     * 附件列表，存储json格式，例如： -- [{"xxx":[{"typeId":"xxx","businessId":"xxx","attachName":"xxx"}...]}...] -- [{"typeId":"xxx","businessId":"xxx","attachName":"xxx"}...]
     * @param attachList 附件列表，存储json格式，例如： -- [{"xxx":[{"typeId":"xxx","businessId":"xxx","attachName":"xxx"}...]}...] -- [{"typeId":"xxx","businessId":"xxx","attachName":"xxx"}...]
     */
    public void setAttachList(String attachList) {
        this.attachList = attachList;
    }
}