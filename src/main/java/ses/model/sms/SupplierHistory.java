package ses.model.sms;

import java.util.Date;

public class SupplierHistory {
	private String id;
	private String supplierId;  //供应商Id
	private String beforeField;  //修改之前字段
	private String modifyType;  //修改之后字段
	private String beforeContent;  //修改之前内容
	private String relationId;  //关联ID
	private Date createdAt;  // 创建时间
	private Integer listType; //列表类型
	private Integer isDeleted;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
	public String getBeforeField() {
		return beforeField;
	}
	public void setBeforeField(String beforeField) {
		this.beforeField = beforeField;
	}
	public String getModifyType() {
		return modifyType;
	}
	public void setModifyType(String modifyType) {
		this.modifyType = modifyType;
	}
	public String getBeforeContent() {
		return beforeContent;
	}
	public void setBeforeContent(String beforeContent) {
		this.beforeContent = beforeContent;
	}
	public String getRelationId() {
		return relationId;
	}
	public void setRelationId(String relationId) {
		this.relationId = relationId;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Integer getListType() {
		return listType;
	}
	public void setListType(Integer listType) {
		this.listType = listType;
	}
	public Integer getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	


	
}
