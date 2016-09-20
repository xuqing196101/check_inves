package ses.model.sms;

import java.io.Serializable;
import java.util.Date;

/**
 * @Title: SupplierType
 * @Description: 供应商类型
 * @author: Wang Zhaohua
 * @date: 2016-9-18下午2:09:07
 */
public class SupplierType implements Serializable {
	private static final long serialVersionUID = -1606094970239375891L;

	/**
	 * <pre>
	 * 主键
	 * 表字段 : T_SES_SMS_SUPPLIER_TYPE.ID
	 * </pre>
	 */
	private String id;

	/**
	 * <pre>
	 * 类别名称
	 * 表字段 : T_SES_SMS_SUPPLIER_TYPE.NAME
	 * </pre>
	 */
	private String name;

	/**
	 * <pre>
	 * 父节点ID
	 * 表字段 : T_SES_SMS_SUPPLIER_TYPE.PARENT_ID
	 * </pre>
	 */
	private String parentId;

	/**
	 * <pre>
	 * 表字段 : T_SES_SMS_SUPPLIER_TYPE.POSITION
	 * </pre>
	 */
	private Integer position;

	/**
	 * <pre>
	 * 表字段 : T_SES_SMS_SUPPLIER_TYPE.CREATED_AT
	 * </pre>
	 */
	private Date createdAt;

	/**
	 * <pre>
	 * 表字段 : T_SES_SMS_SUPPLIER_TYPE.UPDATED_AT
	 * </pre>
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

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
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