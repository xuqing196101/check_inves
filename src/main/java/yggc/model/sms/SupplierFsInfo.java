package yggc.model.sms;

import java.util.Date;

public class SupplierFsInfo {
    /**
     * <pre>
     * 主键
     * 表字段 : T_SES_SMS_SUPPLIER_FS_INFO.ID
     * </pre>
     */
    private String id;

    /**
     * <pre>
     * 登录名
     * 表字段 : T_SES_SMS_SUPPLIER_FS_INFO.LOGIN_NAME
     * </pre>
     */
    private String loginName;

    /**
     * <pre>
     * 手机号
     * 表字段 : T_SES_SMS_SUPPLIER_FS_INFO.MOBILE
     * </pre>
     */
    private String mobile;

    /**
     * <pre>
     * 密码
     * 表字段 : T_SES_SMS_SUPPLIER_FS_INFO.PASSWORD
     * </pre>
     */
    private String password;

    /**
     * <pre>
     * 企业名称
     * 表字段 : T_SES_SMS_SUPPLIER_FS_INFO.SUPPLIER_NAME
     * </pre>
     */
    private String supplierName;

    /**
     * <pre>
     * 企业类型
     * 表字段 : T_SES_SMS_SUPPLIER_FS_INFO.SUPPLIER_TEPE
     * </pre>
     */
    private String supplierTepe;

    /**
     * <pre>
     * 中文译名
     * 表字段 : T_SES_SMS_SUPPLIER_FS_INFO.SUPPLIER_CHINESR_NAME
     * </pre>
     */
    private String supplierChinesrName;

    /**
     * <pre>
     * 法人姓名
     * 表字段 : T_SES_SMS_SUPPLIER_FS_INFO.LEGAL_NAME
     * </pre>
     */
    private String legalName;

    /**
     * <pre>
     * 公司地址
     * 表字段 : T_SES_SMS_SUPPLIER_FS_INFO.ADDRESS
     * </pre>
     */
    private String address;

    /**
     * <pre>
     * 邮编
     * 表字段 : T_SES_SMS_SUPPLIER_FS_INFO.SUPPLIER_ZIP_CODE
     * </pre>
     */
    private Integer supplierZipCode;

    /**
     * <pre>
     * 经营产品大类
     * 表字段 : T_SES_SMS_SUPPLIER_FS_INFO.PRODUCT_TYPE
     * </pre>
     */
    private String productType;

    /**
     * <pre>
     * 主经营产品
     * 表字段 : T_SES_SMS_SUPPLIER_FS_INFO.MAJOR_PRODUCT
     * </pre>
     */
    private String majorProduct;

    /**
     * <pre>
     * 副经营产品
     * 表字段 : T_SES_SMS_SUPPLIER_FS_INFO.SIDE_PRODUCT
     * </pre>
     */
    private String sideProduct;

    /**
     * <pre>
     * 生产商名称
     * 表字段 : T_SES_SMS_SUPPLIER_FS_INFO.PRODUCER_NAME
     * </pre>
     */
    private String producerName;

    /**
     * <pre>
     * 联系人
     * 表字段 : T_SES_SMS_SUPPLIER_FS_INFO.CONTACT_PERSON
     * </pre>
     */
    private String contactPerson;

    /**
     * <pre>
     * 电话
     * 表字段 : T_SES_SMS_SUPPLIER_FS_INFO.SUPPLIER_TELE
     * </pre>
     */
    private Long supplierTele;

    /**
     * <pre>
     * 传真
     * 表字段 : T_SES_SMS_SUPPLIER_FS_INFO.SUPPLIER_FAX
     * </pre>
     */
    private String supplierFax;

    /**
     * <pre>
     * 邮箱
     * 表字段 : T_SES_SMS_SUPPLIER_FS_INFO.SUPPLIER_EMAIL
     * </pre>
     */
    private String supplierEmail;

    /**
     * <pre>
     * 网址
     * 表字段 : T_SES_SMS_SUPPLIER_FS_INFO.NET_URL
     * </pre>
     */
    private String netUrl;

    /**
     * <pre>
     * 供应商状态0代表待审核1表审核通过2代表未通过3代表变更
     * 表字段 : T_SES_SMS_SUPPLIER_FS_INFO.STATUS
     * </pre>
     */
    private Short status;

    /**
     * <pre>
     * 机构ID   T_SES_OMS_PROCUREMENT_DEP
     * 表字段 : T_SES_SMS_SUPPLIER_FS_INFO.ORGMAN_ID
     * </pre>
     */
    private String orgmanId;

    /**
     * <pre>
     *  供应商承诺书 上传
     * 表字段 : T_SES_SMS_SUPPLIER_FS_INFO.SUPPLIER_PLEDGE
     * </pre>
     */
    private String supplierPledge;

    /**
     * <pre>
     * 供应商入库申请表 上传
     * 表字段 : T_SES_SMS_SUPPLIER_FS_INFO.SUPPLIER_REG_LIST
     * </pre>
     */
    private String supplierRegList;

    /**
     * <pre>
     * 创建时间 格式年月日时分秒
     * 表字段 : T_SES_SMS_SUPPLIER_FS_INFO.CREATED_AT
     * </pre>
     */
    private Date createdAt;

    /**
     * <pre>
     * 更新时间 格式年月日时分秒
     * 表字段 : T_SES_SMS_SUPPLIER_FS_INFO.UPDATED_AT
     * </pre>
     */
    private Date updatedAt;

    /**
     * <pre>
     * 获取：主键
     * 表字段：T_SES_SMS_SUPPLIER_FS_INFO.ID
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_FS_INFO.ID：主键
     */
    public String getId() {
        return id;
    }

    /**
     * <pre>
     * 设置：主键
     * 表字段：T_SES_SMS_SUPPLIER_FS_INFO.ID
     * </pre>
     *
     * @param id
     *            T_SES_SMS_SUPPLIER_FS_INFO.ID：主键
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * <pre>
     * 获取：登录名
     * 表字段：T_SES_SMS_SUPPLIER_FS_INFO.LOGIN_NAME
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_FS_INFO.LOGIN_NAME：登录名
     */
    public String getLoginName() {
        return loginName;
    }

    /**
     * <pre>
     * 设置：登录名
     * 表字段：T_SES_SMS_SUPPLIER_FS_INFO.LOGIN_NAME
     * </pre>
     *
     * @param loginName
     *            T_SES_SMS_SUPPLIER_FS_INFO.LOGIN_NAME：登录名
     */
    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
    }

    /**
     * <pre>
     * 获取：手机号
     * 表字段：T_SES_SMS_SUPPLIER_FS_INFO.MOBILE
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_FS_INFO.MOBILE：手机号
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * <pre>
     * 设置：手机号
     * 表字段：T_SES_SMS_SUPPLIER_FS_INFO.MOBILE
     * </pre>
     *
     * @param mobile
     *            T_SES_SMS_SUPPLIER_FS_INFO.MOBILE：手机号
     */
    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    /**
     * <pre>
     * 获取：密码
     * 表字段：T_SES_SMS_SUPPLIER_FS_INFO.PASSWORD
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_FS_INFO.PASSWORD：密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * <pre>
     * 设置：密码
     * 表字段：T_SES_SMS_SUPPLIER_FS_INFO.PASSWORD
     * </pre>
     *
     * @param password
     *            T_SES_SMS_SUPPLIER_FS_INFO.PASSWORD：密码
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * <pre>
     * 获取：企业名称
     * 表字段：T_SES_SMS_SUPPLIER_FS_INFO.SUPPLIER_NAME
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_FS_INFO.SUPPLIER_NAME：企业名称
     */
    public String getSupplierName() {
        return supplierName;
    }

    /**
     * <pre>
     * 设置：企业名称
     * 表字段：T_SES_SMS_SUPPLIER_FS_INFO.SUPPLIER_NAME
     * </pre>
     *
     * @param supplierName
     *            T_SES_SMS_SUPPLIER_FS_INFO.SUPPLIER_NAME：企业名称
     */
    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName == null ? null : supplierName.trim();
    }

    /**
     * <pre>
     * 获取：企业类型
     * 表字段：T_SES_SMS_SUPPLIER_FS_INFO.SUPPLIER_TEPE
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_FS_INFO.SUPPLIER_TEPE：企业类型
     */
    public String getSupplierTepe() {
        return supplierTepe;
    }

    /**
     * <pre>
     * 设置：企业类型
     * 表字段：T_SES_SMS_SUPPLIER_FS_INFO.SUPPLIER_TEPE
     * </pre>
     *
     * @param supplierTepe
     *            T_SES_SMS_SUPPLIER_FS_INFO.SUPPLIER_TEPE：企业类型
     */
    public void setSupplierTepe(String supplierTepe) {
        this.supplierTepe = supplierTepe == null ? null : supplierTepe.trim();
    }

    /**
     * <pre>
     * 获取：中文译名
     * 表字段：T_SES_SMS_SUPPLIER_FS_INFO.SUPPLIER_CHINESR_NAME
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_FS_INFO.SUPPLIER_CHINESR_NAME：中文译名
     */
    public String getSupplierChinesrName() {
        return supplierChinesrName;
    }

    /**
     * <pre>
     * 设置：中文译名
     * 表字段：T_SES_SMS_SUPPLIER_FS_INFO.SUPPLIER_CHINESR_NAME
     * </pre>
     *
     * @param supplierChinesrName
     *            T_SES_SMS_SUPPLIER_FS_INFO.SUPPLIER_CHINESR_NAME：中文译名
     */
    public void setSupplierChinesrName(String supplierChinesrName) {
        this.supplierChinesrName = supplierChinesrName == null ? null : supplierChinesrName.trim();
    }

    /**
     * <pre>
     * 获取：法人姓名
     * 表字段：T_SES_SMS_SUPPLIER_FS_INFO.LEGAL_NAME
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_FS_INFO.LEGAL_NAME：法人姓名
     */
    public String getLegalName() {
        return legalName;
    }

    /**
     * <pre>
     * 设置：法人姓名
     * 表字段：T_SES_SMS_SUPPLIER_FS_INFO.LEGAL_NAME
     * </pre>
     *
     * @param legalName
     *            T_SES_SMS_SUPPLIER_FS_INFO.LEGAL_NAME：法人姓名
     */
    public void setLegalName(String legalName) {
        this.legalName = legalName == null ? null : legalName.trim();
    }

    /**
     * <pre>
     * 获取：公司地址
     * 表字段：T_SES_SMS_SUPPLIER_FS_INFO.ADDRESS
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_FS_INFO.ADDRESS：公司地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * <pre>
     * 设置：公司地址
     * 表字段：T_SES_SMS_SUPPLIER_FS_INFO.ADDRESS
     * </pre>
     *
     * @param address
     *            T_SES_SMS_SUPPLIER_FS_INFO.ADDRESS：公司地址
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * <pre>
     * 获取：邮编
     * 表字段：T_SES_SMS_SUPPLIER_FS_INFO.SUPPLIER_ZIP_CODE
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_FS_INFO.SUPPLIER_ZIP_CODE：邮编
     */
    public Integer getSupplierZipCode() {
        return supplierZipCode;
    }

    /**
     * <pre>
     * 设置：邮编
     * 表字段：T_SES_SMS_SUPPLIER_FS_INFO.SUPPLIER_ZIP_CODE
     * </pre>
     *
     * @param supplierZipCode
     *            T_SES_SMS_SUPPLIER_FS_INFO.SUPPLIER_ZIP_CODE：邮编
     */
    public void setSupplierZipCode(Integer supplierZipCode) {
        this.supplierZipCode = supplierZipCode;
    }

    /**
     * <pre>
     * 获取：经营产品大类
     * 表字段：T_SES_SMS_SUPPLIER_FS_INFO.PRODUCT_TYPE
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_FS_INFO.PRODUCT_TYPE：经营产品大类
     */
    public String getProductType() {
        return productType;
    }

    /**
     * <pre>
     * 设置：经营产品大类
     * 表字段：T_SES_SMS_SUPPLIER_FS_INFO.PRODUCT_TYPE
     * </pre>
     *
     * @param productType
     *            T_SES_SMS_SUPPLIER_FS_INFO.PRODUCT_TYPE：经营产品大类
     */
    public void setProductType(String productType) {
        this.productType = productType == null ? null : productType.trim();
    }

    /**
     * <pre>
     * 获取：主经营产品
     * 表字段：T_SES_SMS_SUPPLIER_FS_INFO.MAJOR_PRODUCT
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_FS_INFO.MAJOR_PRODUCT：主经营产品
     */
    public String getMajorProduct() {
        return majorProduct;
    }

    /**
     * <pre>
     * 设置：主经营产品
     * 表字段：T_SES_SMS_SUPPLIER_FS_INFO.MAJOR_PRODUCT
     * </pre>
     *
     * @param majorProduct
     *            T_SES_SMS_SUPPLIER_FS_INFO.MAJOR_PRODUCT：主经营产品
     */
    public void setMajorProduct(String majorProduct) {
        this.majorProduct = majorProduct == null ? null : majorProduct.trim();
    }

    /**
     * <pre>
     * 获取：副经营产品
     * 表字段：T_SES_SMS_SUPPLIER_FS_INFO.SIDE_PRODUCT
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_FS_INFO.SIDE_PRODUCT：副经营产品
     */
    public String getSideProduct() {
        return sideProduct;
    }

    /**
     * <pre>
     * 设置：副经营产品
     * 表字段：T_SES_SMS_SUPPLIER_FS_INFO.SIDE_PRODUCT
     * </pre>
     *
     * @param sideProduct
     *            T_SES_SMS_SUPPLIER_FS_INFO.SIDE_PRODUCT：副经营产品
     */
    public void setSideProduct(String sideProduct) {
        this.sideProduct = sideProduct == null ? null : sideProduct.trim();
    }

    /**
     * <pre>
     * 获取：生产商名称
     * 表字段：T_SES_SMS_SUPPLIER_FS_INFO.PRODUCER_NAME
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_FS_INFO.PRODUCER_NAME：生产商名称
     */
    public String getProducerName() {
        return producerName;
    }

    /**
     * <pre>
     * 设置：生产商名称
     * 表字段：T_SES_SMS_SUPPLIER_FS_INFO.PRODUCER_NAME
     * </pre>
     *
     * @param producerName
     *            T_SES_SMS_SUPPLIER_FS_INFO.PRODUCER_NAME：生产商名称
     */
    public void setProducerName(String producerName) {
        this.producerName = producerName == null ? null : producerName.trim();
    }

    /**
     * <pre>
     * 获取：联系人
     * 表字段：T_SES_SMS_SUPPLIER_FS_INFO.CONTACT_PERSON
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_FS_INFO.CONTACT_PERSON：联系人
     */
    public String getContactPerson() {
        return contactPerson;
    }

    /**
     * <pre>
     * 设置：联系人
     * 表字段：T_SES_SMS_SUPPLIER_FS_INFO.CONTACT_PERSON
     * </pre>
     *
     * @param contactPerson
     *            T_SES_SMS_SUPPLIER_FS_INFO.CONTACT_PERSON：联系人
     */
    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson == null ? null : contactPerson.trim();
    }

    /**
     * <pre>
     * 获取：电话
     * 表字段：T_SES_SMS_SUPPLIER_FS_INFO.SUPPLIER_TELE
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_FS_INFO.SUPPLIER_TELE：电话
     */
    public Long getSupplierTele() {
        return supplierTele;
    }

    /**
     * <pre>
     * 设置：电话
     * 表字段：T_SES_SMS_SUPPLIER_FS_INFO.SUPPLIER_TELE
     * </pre>
     *
     * @param supplierTele
     *            T_SES_SMS_SUPPLIER_FS_INFO.SUPPLIER_TELE：电话
     */
    public void setSupplierTele(Long supplierTele) {
        this.supplierTele = supplierTele;
    }

    /**
     * <pre>
     * 获取：传真
     * 表字段：T_SES_SMS_SUPPLIER_FS_INFO.SUPPLIER_FAX
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_FS_INFO.SUPPLIER_FAX：传真
     */
    public String getSupplierFax() {
        return supplierFax;
    }

    /**
     * <pre>
     * 设置：传真
     * 表字段：T_SES_SMS_SUPPLIER_FS_INFO.SUPPLIER_FAX
     * </pre>
     *
     * @param supplierFax
     *            T_SES_SMS_SUPPLIER_FS_INFO.SUPPLIER_FAX：传真
     */
    public void setSupplierFax(String supplierFax) {
        this.supplierFax = supplierFax == null ? null : supplierFax.trim();
    }

    /**
     * <pre>
     * 获取：邮箱
     * 表字段：T_SES_SMS_SUPPLIER_FS_INFO.SUPPLIER_EMAIL
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_FS_INFO.SUPPLIER_EMAIL：邮箱
     */
    public String getSupplierEmail() {
        return supplierEmail;
    }

    /**
     * <pre>
     * 设置：邮箱
     * 表字段：T_SES_SMS_SUPPLIER_FS_INFO.SUPPLIER_EMAIL
     * </pre>
     *
     * @param supplierEmail
     *            T_SES_SMS_SUPPLIER_FS_INFO.SUPPLIER_EMAIL：邮箱
     */
    public void setSupplierEmail(String supplierEmail) {
        this.supplierEmail = supplierEmail == null ? null : supplierEmail.trim();
    }

    /**
     * <pre>
     * 获取：网址
     * 表字段：T_SES_SMS_SUPPLIER_FS_INFO.NET_URL
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_FS_INFO.NET_URL：网址
     */
    public String getNetUrl() {
        return netUrl;
    }

    /**
     * <pre>
     * 设置：网址
     * 表字段：T_SES_SMS_SUPPLIER_FS_INFO.NET_URL
     * </pre>
     *
     * @param netUrl
     *            T_SES_SMS_SUPPLIER_FS_INFO.NET_URL：网址
     */
    public void setNetUrl(String netUrl) {
        this.netUrl = netUrl == null ? null : netUrl.trim();
    }

    /**
     * <pre>
     * 获取：供应商状态0代表待审核1表审核通过2代表未通过3代表变更
     * 表字段：T_SES_SMS_SUPPLIER_FS_INFO.STATUS
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_FS_INFO.STATUS：供应商状态0代表待审核1表审核通过2代表未通过3代表变更
     */
    public Short getStatus() {
        return status;
    }

    /**
     * <pre>
     * 设置：供应商状态0代表待审核1表审核通过2代表未通过3代表变更
     * 表字段：T_SES_SMS_SUPPLIER_FS_INFO.STATUS
     * </pre>
     *
     * @param status
     *            T_SES_SMS_SUPPLIER_FS_INFO.STATUS：供应商状态0代表待审核1表审核通过2代表未通过3代表变更
     */
    public void setStatus(Short status) {
        this.status = status;
    }

    /**
     * <pre>
     * 获取：机构ID   T_SES_OMS_PROCUREMENT_DEP
     * 表字段：T_SES_SMS_SUPPLIER_FS_INFO.ORGMAN_ID
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_FS_INFO.ORGMAN_ID：机构ID   T_SES_OMS_PROCUREMENT_DEP
     */
    public String getOrgmanId() {
        return orgmanId;
    }

    /**
     * <pre>
     * 设置：机构ID   T_SES_OMS_PROCUREMENT_DEP
     * 表字段：T_SES_SMS_SUPPLIER_FS_INFO.ORGMAN_ID
     * </pre>
     *
     * @param orgmanId
     *            T_SES_SMS_SUPPLIER_FS_INFO.ORGMAN_ID：机构ID   T_SES_OMS_PROCUREMENT_DEP
     */
    public void setOrgmanId(String orgmanId) {
        this.orgmanId = orgmanId;
    }

    /**
     * <pre>
     * 获取： 供应商承诺书 上传
     * 表字段：T_SES_SMS_SUPPLIER_FS_INFO.SUPPLIER_PLEDGE
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_FS_INFO.SUPPLIER_PLEDGE： 供应商承诺书 上传
     */
    public String getSupplierPledge() {
        return supplierPledge;
    }

    /**
     * <pre>
     * 设置： 供应商承诺书 上传
     * 表字段：T_SES_SMS_SUPPLIER_FS_INFO.SUPPLIER_PLEDGE
     * </pre>
     *
     * @param supplierPledge
     *            T_SES_SMS_SUPPLIER_FS_INFO.SUPPLIER_PLEDGE： 供应商承诺书 上传
     */
    public void setSupplierPledge(String supplierPledge) {
        this.supplierPledge = supplierPledge == null ? null : supplierPledge.trim();
    }

    /**
     * <pre>
     * 获取：供应商入库申请表 上传
     * 表字段：T_SES_SMS_SUPPLIER_FS_INFO.SUPPLIER_REG_LIST
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_FS_INFO.SUPPLIER_REG_LIST：供应商入库申请表 上传
     */
    public String getSupplierRegList() {
        return supplierRegList;
    }

    /**
     * <pre>
     * 设置：供应商入库申请表 上传
     * 表字段：T_SES_SMS_SUPPLIER_FS_INFO.SUPPLIER_REG_LIST
     * </pre>
     *
     * @param supplierRegList
     *            T_SES_SMS_SUPPLIER_FS_INFO.SUPPLIER_REG_LIST：供应商入库申请表 上传
     */
    public void setSupplierRegList(String supplierRegList) {
        this.supplierRegList = supplierRegList == null ? null : supplierRegList.trim();
    }

    /**
     * <pre>
     * 获取：创建时间 格式年月日时分秒
     * 表字段：T_SES_SMS_SUPPLIER_FS_INFO.CREATED_AT
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_FS_INFO.CREATED_AT：创建时间 格式年月日时分秒
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * <pre>
     * 设置：创建时间 格式年月日时分秒
     * 表字段：T_SES_SMS_SUPPLIER_FS_INFO.CREATED_AT
     * </pre>
     *
     * @param createdAt
     *            T_SES_SMS_SUPPLIER_FS_INFO.CREATED_AT：创建时间 格式年月日时分秒
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * <pre>
     * 获取：更新时间 格式年月日时分秒
     * 表字段：T_SES_SMS_SUPPLIER_FS_INFO.UPDATED_AT
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_FS_INFO.UPDATED_AT：更新时间 格式年月日时分秒
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * <pre>
     * 设置：更新时间 格式年月日时分秒
     * 表字段：T_SES_SMS_SUPPLIER_FS_INFO.UPDATED_AT
     * </pre>
     *
     * @param updatedAt
     *            T_SES_SMS_SUPPLIER_FS_INFO.UPDATED_AT：更新时间 格式年月日时分秒
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}