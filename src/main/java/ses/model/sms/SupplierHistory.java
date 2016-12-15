package ses.model.sms;

import java.util.Date;

public class SupplierHistory {
	private String supplierId;  //供应商Id
	private String beforeField;  //修改之前字段
	private String afterField;  //修改之后字段
	private String beforeContent;  //修改之后内容
	private String afterContent;  //修改之前内容
	private String relationId;  //关联ID
	private Date createdAt;  // 创建时间
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
	public String getAfterField() {
		return afterField;
	}
	public void setAfterField(String afterField) {
		this.afterField = afterField;
	}
	public String getBeforeContent() {
		return beforeContent;
	}
	public void setBeforeContent(String beforeContent) {
		this.beforeContent = beforeContent;
	}
	public String getAfterContent() {
		return afterContent;
	}
	public void setAfterContent(String afterContent) {
		this.afterContent = afterContent;
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
	
	
}
