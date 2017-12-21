package ses.model.sms;

import ses.model.bms.ContinentNationRelExt;

public class SupplierBranch {
    private String id;

    private String supplierId;

    private String country;

    private String detailAddress;

    private String organizationName;

    private String businessSope;
    
    private String countryName;  //境外分支中的国家
    
    private ContinentNationRelExt cnre;// 洲-国家关系
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId == null ? null : supplierId.trim();
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country == null ? null : country.trim();
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress == null ? null : detailAddress.trim();
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName == null ? null : organizationName.trim();
    }

    public String getBusinessSope() {
        return businessSope;
    }

    public void setBusinessSope(String businessSope) {
        this.businessSope = businessSope == null ? null : businessSope.trim();
    }

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public ContinentNationRelExt getCnre() {
		return cnre;
	}

	public void setCnre(ContinentNationRelExt cnre) {
		this.cnre = cnre;
	}
    
}