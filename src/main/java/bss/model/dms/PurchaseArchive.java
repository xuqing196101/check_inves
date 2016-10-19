/**
 * 
 */
package bss.model.dms;

import java.util.Date;

/**
 * @Title:PurchaseArchive
 * @Description: 采购档案实体类
 * @author ZhaoBo
 * @date 2016-10-18下午5:29:15
 */
public class PurchaseArchive {
	/**
     * @Fields id : 主键
     */
	private String id;
	
	/**
     * @Fields name : 档案名称
     */
	private String name;
	
	/**
     * @Fields code : 档案编号
     */
	private String code;
	
	/**
     * @Fields purchaseContractMode : 采购合同方式
     */
	private Integer purchaseContractMode;
	
	/**
     * @Fields purchaseContractId : 采购合同ID
     */
	private String purchaseContractId;
	
	/**
     * @Fields range : 档案范围
     */
	private Integer range;
	
	/**
     * @Fields status : 档案状态
     */
	private Integer status;
	
	/**
     * @Fields createdAt : 创建时间
     */
	private Date createdAt;
	
	/**
     * @Fields updatedAt : 更新时间
     */
	private Date updatedAt;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getPurchaseContractMode() {
		return purchaseContractMode;
	}

	public void setPurchaseContractMode(Integer purchaseContractMode) {
		this.purchaseContractMode = purchaseContractMode;
	}

	public String getPurchaseContractId() {
		return purchaseContractId;
	}

	public void setPurchaseContractId(String purchaseContractId) {
		this.purchaseContractId = purchaseContractId;
	}

	public Integer getRange() {
		return range;
	}

	public void setRange(Integer range) {
		this.range = range;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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
	
	
}
