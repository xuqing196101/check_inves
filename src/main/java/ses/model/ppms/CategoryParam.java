package ses.model.ppms;

import java.util.Date;

public class CategoryParam {
	/**
	 * id
	 * 
	 * */
	private String id;
	/**
	 * 参数名称
	 * */
	private String name;
	/**
	 * 参数类型
	 * */
	private Integer valueType;
	
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
	
	
}
