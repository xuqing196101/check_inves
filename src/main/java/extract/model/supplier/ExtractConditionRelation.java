package extract.model.supplier;

import java.util.Date;

public class ExtractConditionRelation {
	
	/**
	 * 条件id
	 */
	private String conditionId;
	
	/**
	 * 存储条件的名称 例如存储供应商类别 supplierTypeCode
	 */
	private String propertyName;
	
	/**
	 * 存储查询条件所对应的值 id  工程类供应商类别 存储 PROJECT
	 */
	private String propertyValue;
	
	private String cateLevel;
	
	private Integer isDelete;
	
	private Date createdAt;
	
	public String getConditionId() {
		return conditionId;
	}
	public void setConditionId(String conditionId) {
		this.conditionId = conditionId;
	}
	public String getPropertyName() {
		return propertyName;
	}
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	public String getPropertyValue() {
		return propertyValue;
	}
	public void setPropertyValue(String propertyValue) {
		this.propertyValue = propertyValue;
	}
	
	
	public String getCateLevel() {
		return cateLevel;
	}
	public void setCateLevel(String cateLevel) {
		this.cateLevel = cateLevel;
	}
	/**
	 * 不包含品目等级关系
	 * @param conditionId
	 * @param propertyName
	 * @param propertyValue
	 */
	public ExtractConditionRelation(String conditionId, String propertyName,
			String propertyValue) {
		super();
		this.conditionId = conditionId;
		this.propertyName = propertyName;
		this.propertyValue = propertyValue;
	}
	
	/**
	 * 包含品目等级关系
	 * @param conditionId
	 * @param propertyName
	 * @param propertyValue
	 * @param levelType
	 */
	public ExtractConditionRelation(String conditionId, String propertyName,
			String propertyValue, String catelevel) {
		super();
		this.conditionId = conditionId;
		this.propertyName = propertyName;
		this.propertyValue = propertyValue;
		this.cateLevel = catelevel;
	}
	public ExtractConditionRelation() {
		super();
	}
	public Integer getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
}
