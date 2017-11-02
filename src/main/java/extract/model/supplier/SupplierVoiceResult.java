package extract.model.supplier;

import java.io.Serializable;

public class SupplierVoiceResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String supplierId;
	private String feedBackTime;
	private String join;
	@Override
	public String toString() {
		return "SupplierVoiceResult [supplierMobile=" + supplierId
				+ ", feedBackTime=" + feedBackTime + ", join=" + join + "]";
	}
	public SupplierVoiceResult(String supplierMobile, String feedBackTime,
			String join) {
		super();
		this.supplierId = supplierMobile;
		this.feedBackTime = feedBackTime;
		this.join = join;
	}
	public String getSupplierMobile() {
		return supplierId;
	}
	public void setSupplierMobile(String supplierMobile) {
		this.supplierId = supplierMobile;
	}
	public String getFeedBackTime() {
		return feedBackTime;
	}
	public void setFeedBackTime(String feedBackTime) {
		this.feedBackTime = feedBackTime;
	}
	public String getJoin() {
		return join;
	}
	public void setJoin(String join) {
		this.join = join;
	}
	public SupplierVoiceResult() {
		super();
	}

}
