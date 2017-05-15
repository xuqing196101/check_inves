package ses.model.oms;

import java.io.Serializable;

public class PurchaseOrg implements Serializable {
    /**
	 * @Fields serialVersionUID : 
	 */
	private static final long serialVersionUID = 1L;

	private String purchaseDepId;

    private String orgId;
    private Orgnization orgnization;
    public String getPurchaseDepId() {
        return purchaseDepId;
    }

    public void setPurchaseDepId(String purchaseDepId) {
        this.purchaseDepId = purchaseDepId == null ? null : purchaseDepId.trim();
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId == null ? null : orgId.trim();
    }

	public Orgnization getOrgnization() {
		return orgnization;
	}

	public void setOrgnization(Orgnization orgnization) {
		this.orgnization = orgnization;
	}
    
}