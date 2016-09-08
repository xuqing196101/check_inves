package ses.model.sms;

import java.io.Serializable;
import java.util.Date;

/**
 * @Title: SupplierShare
 * @Description: 供应商股东实体类
 * @author: Poppet_Brook
 * @date: 2016-9-1下午3:28:39
 */
public class SupplierShare implements Serializable {
	private static final long serialVersionUID = -5047831902205549532L;

	/**
	 * <pre>
	 * 主键
	 * 表字段 : T_SES_SMS_SUPPLIER_SHARE.ID
	 * </pre>
	 */
	private String id;

	/**
	 * <pre>
	 * 供应商ID T_SES_SMS_SUPPLIER_INFO
	 * 表字段 : T_SES_SMS_SUPPLIER_SHARE.SUPPLIER_ID
	 * </pre>
	 */
	private Integer supplierId;

	/**
	 * <pre>
	 * 出资人名称或姓名
	 * 表字段 : T_SES_SMS_SUPPLIER_SHARE.NAME
	 * </pre>
	 */
	private String name;

	/**
	 * <pre>
	 * 出资人性质
	 * 表字段 : T_SES_SMS_SUPPLIER_SHARE.NATURE
	 * </pre>
	 */
	private String nature;

	/**
	 * <pre>
	 * 统一社会信用代码或身份证号码
	 * 表字段 : T_SES_SMS_SUPPLIER_SHARE.IDENTITY
	 * </pre>
	 */
	private String identity;

	/**
	 * <pre>
	 * 出资金额或股份
	 * 表字段 : T_SES_SMS_SUPPLIER_SHARE.SHARE
	 * </pre>
	 */
	private String share;

	/**
	 * <pre>
	 * 比例
	 * 表字段 : T_SES_SMS_SUPPLIER_SHARE.PROPORTION
	 * </pre>
	 */
	private String proportion;

	/**
	 * <pre>
	 * 创建时间 格式年月日时分秒
	 * 表字段 : T_SES_SMS_SUPPLIER_SHARE.CREATED_AT
	 * </pre>
	 */
	private Date createdAt;

	/**
	 * <pre>
	 * 更新时间 格式年月日时分秒
	 * 表字段 : T_SES_SMS_SUPPLIER_SHARE.UPDATED_AT
	 * </pre>
	 */
	private Date updatedAt;
	
	/**
	 * <pre>
	 * 供应商信息
	 * </pre>
	 */
	private SupplierInfo supplierInfo;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNature() {
		return nature;
	}

	public void setNature(String nature) {
		this.nature = nature;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getShare() {
		return share;
	}

	public void setShare(String share) {
		this.share = share;
	}

	public String getProportion() {
		return proportion;
	}

	public void setProportion(String proportion) {
		this.proportion = proportion;
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

	public SupplierInfo getSupplierInfo() {
		return supplierInfo;
	}

	public void setSupplierInfo(SupplierInfo supplierInfo) {
		this.supplierInfo = supplierInfo;
	}
}