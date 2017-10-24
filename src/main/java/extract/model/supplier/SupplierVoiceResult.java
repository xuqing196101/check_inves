package extract.model.supplier;

import java.io.Serializable;

public class SupplierVoiceResult implements Serializable {

	private String supplierMobile;
	private String feedBackTime;
	private String join;
	@Override
	public String toString() {
		return "SupplierVoiceResult [supplierMobile=" + supplierMobile
				+ ", feedBackTime=" + feedBackTime + ", join=" + join + "]";
	}
	public SupplierVoiceResult(String supplierMobile, String feedBackTime,
			String join) {
		super();
		this.supplierMobile = supplierMobile;
		this.feedBackTime = feedBackTime;
		this.join = join;
	}
	public String getSupplierMobile() {
		return supplierMobile;
	}
	public void setSupplierMobile(String supplierMobile) {
		this.supplierMobile = supplierMobile;
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
