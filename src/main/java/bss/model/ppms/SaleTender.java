package bss.model.ppms;

import java.util.Date;
import java.util.List;

import ses.model.bms.User;
import ses.model.sms.Supplier;

public class SaleTender {
	
	
	
    public SaleTender() {
		super();
	}

    
	public SaleTender(String projectId) {
		super();
		this.projectId = projectId;
	}


	public SaleTender(String projectId, Short statusBid, String supplierId,
			Short statusBond, String userId) {
		super();
		this.projectId = projectId;
		this.statusBid = statusBid;
		this.supplierId = supplierId;
		this.statusBond = statusBond;
		this.userId = userId;
	}


	/**
     * <pre>
     * 表字段 : T_BSS_PPMS_SALE_TENDER.ID
     * </pre>
     */
    private String id;

    /**
     * <pre>
     * 项目id
     * 表字段 : T_BSS_PPMS_SALE_TENDER.PROJECT_ID
     * </pre>
     */
    private String projectId;

    /**
     * <pre>
     * 表字段 : T_BSS_PPMS_SALE_TENDER.STATUS_BID
     * </pre>
     */
    private Short statusBid;

    /**
     * <pre>
     * 供应商id
     * 表字段 : T_BSS_PPMS_SALE_TENDER.SUPPLIER_ID
     * </pre>
     */
    private String supplierId;

    /**
     * <pre>
     * 保证金状态 1缴纳 2，缴纳
     * 表字段 : T_BSS_PPMS_SALE_TENDER.STATUS_BOND
     * </pre>
     */
    private Short statusBond;

    /**
     * <pre>
     * 创建时间
     * 表字段 : T_BSS_PPMS_SALE_TENDER.CREATED_AT
     * </pre>
     */
    private Date createdAt;

    /**
     * <pre>
     * 修改时间
     * 表字段 : T_BSS_PPMS_SALE_TENDER.UPDATED_AT
     * </pre>
     */
    private Date updatedAt;

    /**
     * <pre>
     * 发售人id
     * 表字段 : T_BSS_PPMS_SALE_TENDER.USER_ID
     * </pre>
     */
    private String userId;
    
    
    private Supplier suppliers;
    
    
    private User user;
    /**
     * <pre>
     * 获取：null
     * 表字段：T_BSS_PPMS_SALE_TENDER.ID
     * </pre>
     *
     * @return T_BSS_PPMS_SALE_TENDER.ID：null
     */
    public String getId() {
        return id;
    }

    /**
     * <pre>
     * 设置：null
     * 表字段：T_BSS_PPMS_SALE_TENDER.ID
     * </pre>
     *
     * @param id
     *            T_BSS_PPMS_SALE_TENDER.ID：null
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * <pre>
     * 获取：项目id
     * 表字段：T_BSS_PPMS_SALE_TENDER.PROJECT_ID
     * </pre>
     *
     * @return T_BSS_PPMS_SALE_TENDER.PROJECT_ID：项目id
     */
    public String getProjectId() {
        return projectId;
    }

    /**
     * <pre>
     * 设置：项目id
     * 表字段：T_BSS_PPMS_SALE_TENDER.PROJECT_ID
     * </pre>
     *
     * @param projectId
     *            T_BSS_PPMS_SALE_TENDER.PROJECT_ID：项目id
     */
    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    /**
     * <pre>
     * 获取：null
     * 表字段：T_BSS_PPMS_SALE_TENDER.STATUS_BID
     * </pre>
     *
     * @return T_BSS_PPMS_SALE_TENDER.STATUS_BID：null
     */
    public Short getStatusBid() {
        return statusBid;
    }

    /**
     * <pre>
     * 设置：null
     * 表字段：T_BSS_PPMS_SALE_TENDER.STATUS_BID
     * </pre>
     *
     * @param statusBid
     *            T_BSS_PPMS_SALE_TENDER.STATUS_BID：null
     */
    public void setStatusBid(Short statusBid) {
        this.statusBid = statusBid;
    }

    /**
     * <pre>
     * 获取：供应商id
     * 表字段：T_BSS_PPMS_SALE_TENDER.SUPPLIER_ID
     * </pre>
     *
     * @return T_BSS_PPMS_SALE_TENDER.SUPPLIER_ID：供应商id
     */
    public String getSupplierId() {
        return supplierId;
    }

    /**
     * <pre>
     * 设置：供应商id
     * 表字段：T_BSS_PPMS_SALE_TENDER.SUPPLIER_ID
     * </pre>
     *
     * @param supplierId
     *            T_BSS_PPMS_SALE_TENDER.SUPPLIER_ID：供应商id
     */
    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId == null ? null : supplierId.trim();
    }

    /**
     * <pre>
     * 获取：保证金状态 1缴纳 2，缴纳
     * 表字段：T_BSS_PPMS_SALE_TENDER.STATUS_BOND
     * </pre>
     *
     * @return T_BSS_PPMS_SALE_TENDER.STATUS_BOND：保证金状态 1缴纳 2，缴纳
     */
    public Short getStatusBond() {
        return statusBond;
    }

    /**
     * <pre>
     * 设置：保证金状态 1缴纳 2，缴纳
     * 表字段：T_BSS_PPMS_SALE_TENDER.STATUS_BOND
     * </pre>
     *
     * @param statusBond
     *            T_BSS_PPMS_SALE_TENDER.STATUS_BOND：保证金状态 1缴纳 2，缴纳
     */
    public void setStatusBond(Short statusBond) {
        this.statusBond = statusBond;
    }

    /**
     * <pre>
     * 获取：创建时间
     * 表字段：T_BSS_PPMS_SALE_TENDER.CREATED_AT
     * </pre>
     *
     * @return T_BSS_PPMS_SALE_TENDER.CREATED_AT：创建时间
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * <pre>
     * 设置：创建时间
     * 表字段：T_BSS_PPMS_SALE_TENDER.CREATED_AT
     * </pre>
     *
     * @param createdAt
     *            T_BSS_PPMS_SALE_TENDER.CREATED_AT：创建时间
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * <pre>
     * 获取：修改时间
     * 表字段：T_BSS_PPMS_SALE_TENDER.UPDATED_AT
     * </pre>
     *
     * @return T_BSS_PPMS_SALE_TENDER.UPDATED_AT：修改时间
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * <pre>
     * 设置：修改时间
     * 表字段：T_BSS_PPMS_SALE_TENDER.UPDATED_AT
     * </pre>
     *
     * @param updatedAt
     *            T_BSS_PPMS_SALE_TENDER.UPDATED_AT：修改时间
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * <pre>
     * 获取：发售人id
     * 表字段：T_BSS_PPMS_SALE_TENDER.USER_ID
     * </pre>
     *
     * @return T_BSS_PPMS_SALE_TENDER.USER_ID：发售人id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * <pre>
     * 设置：发售人id
     * 表字段：T_BSS_PPMS_SALE_TENDER.USER_ID
     * </pre>
     *
     * @param userId
     *            T_BSS_PPMS_SALE_TENDER.USER_ID：发售人id
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }


	/**
	 * @return the suppliers
	 */
	public Supplier getSuppliers() {
		return suppliers;
	}


	/**
	 * @param suppliers the suppliers to set
	 */
	public void setSuppliers(Supplier suppliers) {
		this.suppliers = suppliers;
	}


	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}


	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}



    
    
}