package ses.model.sms;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;

public class ImportSupplier implements Serializable {
    /**
	 * @Fields serialVersionUID : 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	@NotBlank(message="企业名称不能为空")
    private String name;
	@NotBlank(message="企业类型不能为空")
    private String supplierType;
	@NotBlank(message="中文名称不能为空")
    private String chinesrName;
	@NotBlank(message="法人姓名不能为空")
    private String legalName;
	@NotBlank(message="企业地址不能为空")
    private String address;
    private Integer postCode;
	@NotBlank(message="产品大类不能为空")
    private String productType;
	@NotBlank(message="主营产品不能为空")
    private String majorProduct;
	@NotBlank(message="兼营产品不能为空")
    private String byproduct;
	@NotBlank(message="成产商不能为空")
    private String producerName;
	@NotBlank(message="联系人姓名不能为空")
    private String contactPerson;
    private Long telephone;
	@NotBlank(message="传真不能为空")
    private String fax;
	@NotBlank(message="邮件不能为空")
    private String email;
	@NotBlank(message="企业网址不能为空")
    private String website;

    private Short status;

    private String orgId;

    private Timestamp createdAt;

    private Timestamp updatedAt;
    
    private String creatorId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }
    
  
    public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getSupplierType() {
        return supplierType;
    }

    public void setSupplierType(String supplierType) {
        this.supplierType = supplierType == null ? null : supplierType.trim();
    }

    public String getChinesrName() {
        return chinesrName;
    }

    public void setChinesrName(String chinesrName) {
        this.chinesrName = chinesrName == null ? null : chinesrName.trim();
    }

    public String getLegalName() {
        return legalName;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName == null ? null : legalName.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public Integer getPostCode() {
        return postCode;
    }

    public void setPostCode(Integer postCode) {
        this.postCode = postCode;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType == null ? null : productType.trim();
    }

    public String getMajorProduct() {
        return majorProduct;
    }

    public void setMajorProduct(String majorProduct) {
        this.majorProduct = majorProduct == null ? null : majorProduct.trim();
    }

    public String getByproduct() {
        return byproduct;
    }

    public void setByproduct(String byproduct) {
        this.byproduct = byproduct == null ? null : byproduct.trim();
    }

    public String getProducerName() {
        return producerName;
    }

    public void setProducerName(String producerName) {
        this.producerName = producerName == null ? null : producerName.trim();
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson == null ? null : contactPerson.trim();
    }

    public Long getTelephone() {
        return telephone;
    }

    public void setTelephone(Long telephone) {
        this.telephone = telephone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax == null ? null : fax.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website == null ? null : website.trim();
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId == null ? null : orgId.trim();
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}