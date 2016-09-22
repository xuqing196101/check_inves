package ses.model.sms;

import java.io.Serializable;

public class SupplierItemsPro implements Serializable {
	private static final long serialVersionUID = -6956735111077817153L;

	/**
	 * <pre>
	 * 主键
	 * 表字段 : T_SES_SMS_SUPPLIER_ITEMS_PRO.ID
	 * </pre>
	 */
	private String id;

	/**
	 * <pre>
	 * 物资生产信息ID T_SES_SMS_SUPPLIER_MAT_PRO
	 * 表字段 : T_SES_SMS_SUPPLIER_ITEMS_PRO.MAT_PRO_ID
	 * </pre>
	 */
	private String matProId;

	/**
	 * <pre>
	 * 品目类型 T_SES_SMS_SUPPLIER_ITEMS
	 * 表字段 : T_SES_SMS_SUPPLIER_ITEMS_PRO.ITEMS_ID
	 * </pre>
	 */
	private String itemsId;

	/**
	 * <pre>
	 * 大类名称
	 * 表字段 : T_SES_SMS_SUPPLIER_ITEMS_PRO.BIG_KIND_NAME
	 * </pre>
	 */
	private String bigKindName;

	/**
	 * <pre>
	 * 中类名称
	 * 表字段 : T_SES_SMS_SUPPLIER_ITEMS_PRO.NORMAL_KIND_NAME
	 * </pre>
	 */
	private String normalKindName;

	/**
	 * <pre>
	 * 小类名称
	 * 表字段 : T_SES_SMS_SUPPLIER_ITEMS_PRO.SMALL_KING_NAME
	 * </pre>
	 */
	private String smallKingName;

	/**
	 * <pre>
	 * 品种名称
	 * 表字段 : T_SES_SMS_SUPPLIER_ITEMS_PRO.KIND_NAME
	 * </pre>
	 */
	private String kindName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMatProId() {
		return matProId;
	}

	public void setMatProId(String matProId) {
		this.matProId = matProId;
	}

	public String getItemsId() {
		return itemsId;
	}

	public void setItemsId(String itemsId) {
		this.itemsId = itemsId;
	}

	public String getBigKindName() {
		return bigKindName;
	}

	public void setBigKindName(String bigKindName) {
		this.bigKindName = bigKindName;
	}

	public String getNormalKindName() {
		return normalKindName;
	}

	public void setNormalKindName(String normalKindName) {
		this.normalKindName = normalKindName;
	}

	public String getSmallKingName() {
		return smallKingName;
	}

	public void setSmallKingName(String smallKingName) {
		this.smallKingName = smallKingName;
	}

	public String getKindName() {
		return kindName;
	}

	public void setKindName(String kindName) {
		this.kindName = kindName;
	}
}