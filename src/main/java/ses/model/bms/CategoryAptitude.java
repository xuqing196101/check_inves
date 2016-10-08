package ses.model.bms;

import java.util.Date;

public class CategoryAptitude {
	/*
	 * 类型id
	 * 
	 * */
	private String id;
	/*
	 * 品目id
	 * 
	 * */
	private Category category;
	/*
	 *生产型资质名称
	 * 
	 * */
	private String productName;
	/*
	 *销售型资质名称
	 * 
	 * */
	private String saleName;
	/*
	 * 创建事件
	 * */
	private Date createdAt;
	/*
	 * 修改事件
	 * 
	 * */
	private Date updatedAt;
	/*
	 * 是否删除
	 * 
	 * */
	private Integer isDeleted;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	
	
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getSaleName() {
		return saleName;
	}
	public void setSaleName(String saleName) {
		this.saleName = saleName;
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
	

}
