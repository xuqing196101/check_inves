package ses.model.sms;

/**
 * <p>Title:SupplierBlacklistVO</p>
 * <p>Description:供应商黑名单VO</p>
 * @author Huang Xigang
 * @date 2017-5-23 下午3:17:03
 */
public class SupplierBlacklistVO extends SupplierBlacklist {
	
	private static final long serialVersionUID = 9082231915423636155L;
	
	private String supplierTypeName;// 供应商类型名称

	public String getSupplierTypeName() {
		return supplierTypeName;
	}

	public void setSupplierTypeName(String supplierTypeName) {
		this.supplierTypeName = supplierTypeName;
	}
	
	
	/**
	 * 供应商黑名单
	 *//*
	private SupplierBlacklist supplierBlacklist;
	
	*//**
	 * 供应商
	 *//*
	private Supplier supplier;

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public SupplierBlacklist getSupplierBlacklist() {
		return supplierBlacklist;
	}

	public void setSupplierBlacklist(SupplierBlacklist supplierBlacklist) {
		this.supplierBlacklist = supplierBlacklist;
	}*/
	
}
