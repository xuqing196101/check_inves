package ses.model.sms;

import java.io.Serializable;
import java.util.Date;
/**
 * 
* @ClassName: ProductCheckRecord 
* @Description: 产品审核记录
* @author Easong
* @date 2017年4月10日 下午6:22:06 
*
 */
public class SMSProductCheckRecord implements Serializable{
	/**
	 * 产品审核记录表ID
	 */
    private String id;

    /**
     * 产品信息ID
     */
    private String productBasicId;
    /**
     * 审核人ID
     */
    private String createdId;
    /**
     * 审核意见
     */
    private String advice;
    /**
     * 备注
     */
    private String remark;
    /**
     * 创建时间
     */
    private Date createdAt;
    /**
     * 修改时间
     */
    private Date updatedAt;
    /**
     * 
     */
    private Integer isDeleted;
    
    /**
     * 审核通过1/审核不通过0
     */
    private Integer flag;
    
    /**
     * 批量审核ids
     */
    private String productBasicIds;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getProductBasicId() {
        return productBasicId;
    }

    public void setProductBasicId(String productBasicId) {
        this.productBasicId = productBasicId == null ? null : productBasicId.trim();
    }

    public String getCreatedId() {
        return createdId;
    }

    public void setCreatedId(String createdId) {
        this.createdId = createdId == null ? null : createdId.trim();
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice == null ? null : advice.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public String getProductBasicIds() {
		return productBasicIds;
	}

	public void setProductBasicIds(String productBasicIds) {
		this.productBasicIds = productBasicIds;
	}


    
}