package yggc.model;

import java.util.Date;

public class FsInfo {
    private Long id;

    private String loginName;

    private String mobile;

    private String password;

    private String supplierName;

    private String supplierTepe;

    private String supplierChinesrName;

    private String legalName;

    private String address;

    private Integer supplierZipCode;

    private String productType;

    private String majorProduct;

    private String sideProduct;

    private String producerName;

    private String contactPerson;

    private Long supplierTele;

    private String supplierFax;

    private String supplierEmail;

    private String netUrl;

    private Short status;

    private Long orgmanId;

    private String supplierPledge;

    private String supplierRegList;

    private Date createdAt;

    private Date updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName == null ? null : supplierName.trim();
    }

    public String getSupplierTepe() {
        return supplierTepe;
    }

    public void setSupplierTepe(String supplierTepe) {
        this.supplierTepe = supplierTepe == null ? null : supplierTepe.trim();
    }

    public String getSupplierChinesrName() {
        return supplierChinesrName;
    }

    public void setSupplierChinesrName(String supplierChinesrName) {
        this.supplierChinesrName = supplierChinesrName == null ? null : supplierChinesrName.trim();
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

    public Integer getSupplierZipCode() {
        return supplierZipCode;
    }

    public void setSupplierZipCode(Integer supplierZipCode) {
        this.supplierZipCode = supplierZipCode;
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

    public String getSideProduct() {
        return sideProduct;
    }

    public void setSideProduct(String sideProduct) {
        this.sideProduct = sideProduct == null ? null : sideProduct.trim();
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

    public Long getSupplierTele() {
        return supplierTele;
    }

    public void setSupplierTele(Long supplierTele) {
        this.supplierTele = supplierTele;
    }

    public String getSupplierFax() {
        return supplierFax;
    }

    public void setSupplierFax(String supplierFax) {
        this.supplierFax = supplierFax == null ? null : supplierFax.trim();
    }

    public String getSupplierEmail() {
        return supplierEmail;
    }

    public void setSupplierEmail(String supplierEmail) {
        this.supplierEmail = supplierEmail == null ? null : supplierEmail.trim();
    }

    public String getNetUrl() {
        return netUrl;
    }

    public void setNetUrl(String netUrl) {
        this.netUrl = netUrl == null ? null : netUrl.trim();
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Long getOrgmanId() {
        return orgmanId;
    }

    public void setOrgmanId(Long orgmanId) {
        this.orgmanId = orgmanId;
    }

    public String getSupplierPledge() {
        return supplierPledge;
    }

    public void setSupplierPledge(String supplierPledge) {
        this.supplierPledge = supplierPledge == null ? null : supplierPledge.trim();
    }

    public String getSupplierRegList() {
        return supplierRegList;
    }

    public void setSupplierRegList(String supplierRegList) {
        this.supplierRegList = supplierRegList == null ? null : supplierRegList.trim();
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