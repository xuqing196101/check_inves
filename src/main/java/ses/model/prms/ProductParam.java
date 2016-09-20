package ses.model.prms;

import java.util.Date;

public class ProductParam {
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
	 * 状态
	 * 
	 * */
	private Integer status;
	/**
	 * 
	 * 参数
	 * */
	private String value;
	/**
	 * 参数类型
	 * */
	private Integer valueType;
	/**
	 * 选项
	 * 
	 * */
	private Integer option;
	/**
	 * 是否未生产型文件
	 * 
	 * */
	private Integer isProductFile;
	/**
	 * 
	 * 是否未销售型文件
	 * */
	private Integer isSaleFile;
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
	public ProductParam() {
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Integer getValueType() {
		return valueType;
	}
	public void setValueType(Integer valueType) {
		this.valueType = valueType;
	}
	public Integer getOption() {
		return option;
	}
	public void setOption(Integer option) {
		this.option = option;
	}
	public Integer getIsProductFile() {
		return isProductFile;
	}
	public void setIsProductFile(Integer isProductFile) {
		this.isProductFile = isProductFile;
	}
	public Integer getIsSaleFile() {
		return isSaleFile;
	}
	public void setIsSaleFile(Integer isSaleFile) {
		this.isSaleFile = isSaleFile;
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
