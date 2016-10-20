package ses.model.ppms;

import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;

import ses.model.bms.Category;

//产品参数实体类

public class CategoryParam {
	/**
	 * id
	 * 
	 * */
	private String id;
	/**
	 * 参数名称
	 * */
	@NotBlank(message= "参数名不能为空")
	private String name;
	/**
	 * 参数类型
	 * */
	@NotBlank(message = "参数类型不能为空")
	private String valueType;
	
	/**
	 * 创建时间
	 * */
	private Date createdAt;
	/**
	 * 修改时间
	 * 
	 * */
	private Date updatedAt;
	/**
	 * 是否删除
	 * */
	private Integer isDeleted;
	/**
	 * 
	 * */
	private Category category;
	
	private String paramValue;
	
	private String paramValueId;

	
	public String getValueType() {
		return valueType;
	}
	public void setValueType(String valueType) {
		this.valueType = valueType;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public String getId() {
		return id;
	}
	public CategoryParam() {
		super();
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
	public String getParamValue() {
		return paramValue;
	}
	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}
	public String getParamValueId() {
		return paramValueId;
	}
	public void setParamValueId(String paramValueId) {
		this.paramValueId = paramValueId;
	}
	
	
}
