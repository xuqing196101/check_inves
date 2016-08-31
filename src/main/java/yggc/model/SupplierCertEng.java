package yggc.model;

import java.util.Date;

public class SupplierCertEng {
    private Long id;

    private Long matEngId;

    private String certType;

    private String certCode;

    private String certMaxLevel;

    private String techName;

    private String techPt;

    private String techJop;

    private String depName;

    private String depPt;

    private String depJop;

    private String licenceAuthorith;

    private Date expStartDate;

    private Date expEndDate;

    private Short certStatus;

    private String attachCert;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMatEngId() {
        return matEngId;
    }

    public void setMatEngId(Long matEngId) {
        this.matEngId = matEngId;
    }

    public String getCertType() {
        return certType;
    }

    public void setCertType(String certType) {
        this.certType = certType == null ? null : certType.trim();
    }

    public String getCertCode() {
        return certCode;
    }

    public void setCertCode(String certCode) {
        this.certCode = certCode == null ? null : certCode.trim();
    }

    public String getCertMaxLevel() {
        return certMaxLevel;
    }

    public void setCertMaxLevel(String certMaxLevel) {
        this.certMaxLevel = certMaxLevel == null ? null : certMaxLevel.trim();
    }

    public String getTechName() {
        return techName;
    }

    public void setTechName(String techName) {
        this.techName = techName == null ? null : techName.trim();
    }

    public String getTechPt() {
        return techPt;
    }

    public void setTechPt(String techPt) {
        this.techPt = techPt == null ? null : techPt.trim();
    }

    public String getTechJop() {
        return techJop;
    }

    public void setTechJop(String techJop) {
        this.techJop = techJop == null ? null : techJop.trim();
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName == null ? null : depName.trim();
    }

    public String getDepPt() {
        return depPt;
    }

    public void setDepPt(String depPt) {
        this.depPt = depPt == null ? null : depPt.trim();
    }

    public String getDepJop() {
        return depJop;
    }

    public void setDepJop(String depJop) {
        this.depJop = depJop == null ? null : depJop.trim();
    }

    public String getLicenceAuthorith() {
        return licenceAuthorith;
    }

    public void setLicenceAuthorith(String licenceAuthorith) {
        this.licenceAuthorith = licenceAuthorith == null ? null : licenceAuthorith.trim();
    }

    public Date getExpStartDate() {
        return expStartDate;
    }

    public void setExpStartDate(Date expStartDate) {
        this.expStartDate = expStartDate;
    }

    public Date getExpEndDate() {
        return expEndDate;
    }

    public void setExpEndDate(Date expEndDate) {
        this.expEndDate = expEndDate;
    }

    public Short getCertStatus() {
        return certStatus;
    }

    public void setCertStatus(Short certStatus) {
        this.certStatus = certStatus;
    }

    public String getAttachCert() {
        return attachCert;
    }

    public void setAttachCert(String attachCert) {
        this.attachCert = attachCert == null ? null : attachCert.trim();
    }
}