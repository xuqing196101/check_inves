package ses.model.sms;

import java.io.Serializable;
import java.util.Date;

public class ImportSupplierAud implements Serializable {
    /**
	 * @Fields serialVersionUID : 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

    private String nameReason;

    private String chinesrNameReason;

    private String addressReason;

    private String productTypeReason;

    private String majorpRoductReason;

    private String byproductReason;

    private String producerNameReason;

    private String contactPersonReason;

    private String telephoneReason;

    private String faxReason;

    private String emailReason;

    private String websiteReason;

    private String pledgeReason;

    private String reglistReason;

    private Date createdAt;

    private Date updatedAt;

    private String legalNameReason;

    private String supplierTypeReason;

    private String mobileReason;

    private Long auditCount;

    private String postCodeReason;

    private String civilAchievementReason;

    private String remarkReason;

    private String importSupplierId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getNameReason() {
        return nameReason;
    }

    public void setNameReason(String nameReason) {
        this.nameReason = nameReason == null ? null : nameReason.trim();
    }

    public String getChinesrNameReason() {
        return chinesrNameReason;
    }

    public void setChinesrNameReason(String chinesrNameReason) {
        this.chinesrNameReason = chinesrNameReason == null ? null : chinesrNameReason.trim();
    }

    public String getAddressReason() {
        return addressReason;
    }

    public void setAddressReason(String addressReason) {
        this.addressReason = addressReason == null ? null : addressReason.trim();
    }

    public String getProductTypeReason() {
        return productTypeReason;
    }

    public void setProductTypeReason(String productTypeReason) {
        this.productTypeReason = productTypeReason == null ? null : productTypeReason.trim();
    }

    public String getMajorpRoductReason() {
        return majorpRoductReason;
    }

    public void setMajorpRoductReason(String majorpRoductReason) {
        this.majorpRoductReason = majorpRoductReason == null ? null : majorpRoductReason.trim();
    }

    public String getByproductReason() {
        return byproductReason;
    }

    public void setByproductReason(String byproductReason) {
        this.byproductReason = byproductReason == null ? null : byproductReason.trim();
    }

    public String getProducerNameReason() {
        return producerNameReason;
    }

    public void setProducerNameReason(String producerNameReason) {
        this.producerNameReason = producerNameReason == null ? null : producerNameReason.trim();
    }

    public String getContactPersonReason() {
        return contactPersonReason;
    }

    public void setContactPersonReason(String contactPersonReason) {
        this.contactPersonReason = contactPersonReason == null ? null : contactPersonReason.trim();
    }

    public String getTelephoneReason() {
        return telephoneReason;
    }

    public void setTelephoneReason(String telephoneReason) {
        this.telephoneReason = telephoneReason == null ? null : telephoneReason.trim();
    }

    public String getFaxReason() {
        return faxReason;
    }

    public void setFaxReason(String faxReason) {
        this.faxReason = faxReason == null ? null : faxReason.trim();
    }

    public String getEmailReason() {
        return emailReason;
    }

    public void setEmailReason(String emailReason) {
        this.emailReason = emailReason == null ? null : emailReason.trim();
    }

    public String getWebsiteReason() {
        return websiteReason;
    }

    public void setWebsiteReason(String websiteReason) {
        this.websiteReason = websiteReason == null ? null : websiteReason.trim();
    }

    public String getPledgeReason() {
        return pledgeReason;
    }

    public void setPledgeReason(String pledgeReason) {
        this.pledgeReason = pledgeReason == null ? null : pledgeReason.trim();
    }

    public String getReglistReason() {
        return reglistReason;
    }

    public void setReglistReason(String reglistReason) {
        this.reglistReason = reglistReason == null ? null : reglistReason.trim();
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

    public String getLegalNameReason() {
        return legalNameReason;
    }

    public void setLegalNameReason(String legalNameReason) {
        this.legalNameReason = legalNameReason == null ? null : legalNameReason.trim();
    }

    public String getSupplierTypeReason() {
        return supplierTypeReason;
    }

    public void setSupplierTypeReason(String supplierTypeReason) {
        this.supplierTypeReason = supplierTypeReason == null ? null : supplierTypeReason.trim();
    }

    public String getMobileReason() {
        return mobileReason;
    }

    public void setMobileReason(String mobileReason) {
        this.mobileReason = mobileReason == null ? null : mobileReason.trim();
    }

    public Long getAuditCount() {
        return auditCount;
    }

    public void setAuditCount(Long auditCount) {
        this.auditCount = auditCount;
    }

    public String getPostCodeReason() {
        return postCodeReason;
    }

    public void setPostCodeReason(String postCodeReason) {
        this.postCodeReason = postCodeReason == null ? null : postCodeReason.trim();
    }

    public String getCivilAchievementReason() {
        return civilAchievementReason;
    }

    public void setCivilAchievementReason(String civilAchievementReason) {
        this.civilAchievementReason = civilAchievementReason == null ? null : civilAchievementReason.trim();
    }

    public String getRemarkReason() {
        return remarkReason;
    }

    public void setRemarkReason(String remarkReason) {
        this.remarkReason = remarkReason == null ? null : remarkReason.trim();
    }

    public String getImportSupplierId() {
        return importSupplierId;
    }

    public void setImportSupplierId(String importSupplierId) {
        this.importSupplierId = importSupplierId == null ? null : importSupplierId.trim();
    }
}