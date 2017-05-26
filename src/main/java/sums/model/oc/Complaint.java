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
     * 标题
     * 表字段 : T_SUMS_OC_COMPLAINT.TITLE
     * </pre>
     */
    private String title;

    /**
     * <pre>
     * 投诉内容
     * 表字段 : T_SUMS_OC_COMPLAINT.COMPLAINT_CONTENT
     * </pre>
     */
    private String complaintContent;

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
     * 投诉人联系电话
     */
    private String telephone;
    
    /**
     * 投诉人联系地址
     */
    private String adress;
    
    /**
     * 投诉人邮箱
     */
    private String email;
   
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

    //标题
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title == null ? null : title.trim();
	}

	//投诉内容
	public String getComplaintContent() {
		return complaintContent;
	}

	public void setComplaintContent(String complaintContent) {
		this.complaintContent = complaintContent == null ? null : complaintContent.trim();
	}

	//投诉人联系电话
	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone == null ? null : telephone.trim();
	}

	//投诉人联系地址
	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress == null ? null : adress.trim();
	}

	//投诉人邮箱
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email == null ? null : email.trim();
	}
    
   
}