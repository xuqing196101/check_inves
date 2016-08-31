package yggc.model;

import java.util.Date;

public class SupplierApptitute {
    private Long id;

    private Long matEngId;

    private String certType;

    private String certCode;

    private String aptituteSequence;

    private String professType;

    private String aptituteLevel;

    private Short isMajorFund;

    private String aptituteContent;

    private String aptituteCode;

    private Date aptituteDate;

    private String aptituteWay;

    private Short aptituteStatus;

    private Date aptituteChangeAt;

    private String aptituteChangeReason;

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

    public String getAptituteSequence() {
        return aptituteSequence;
    }

    public void setAptituteSequence(String aptituteSequence) {
        this.aptituteSequence = aptituteSequence == null ? null : aptituteSequence.trim();
    }

    public String getProfessType() {
        return professType;
    }

    public void setProfessType(String professType) {
        this.professType = professType == null ? null : professType.trim();
    }

    public String getAptituteLevel() {
        return aptituteLevel;
    }

    public void setAptituteLevel(String aptituteLevel) {
        this.aptituteLevel = aptituteLevel == null ? null : aptituteLevel.trim();
    }

    public Short getIsMajorFund() {
        return isMajorFund;
    }

    public void setIsMajorFund(Short isMajorFund) {
        this.isMajorFund = isMajorFund;
    }

    public String getAptituteContent() {
        return aptituteContent;
    }

    public void setAptituteContent(String aptituteContent) {
        this.aptituteContent = aptituteContent == null ? null : aptituteContent.trim();
    }

    public String getAptituteCode() {
        return aptituteCode;
    }

    public void setAptituteCode(String aptituteCode) {
        this.aptituteCode = aptituteCode == null ? null : aptituteCode.trim();
    }

    public Date getAptituteDate() {
        return aptituteDate;
    }

    public void setAptituteDate(Date aptituteDate) {
        this.aptituteDate = aptituteDate;
    }

    public String getAptituteWay() {
        return aptituteWay;
    }

    public void setAptituteWay(String aptituteWay) {
        this.aptituteWay = aptituteWay == null ? null : aptituteWay.trim();
    }

    public Short getAptituteStatus() {
        return aptituteStatus;
    }

    public void setAptituteStatus(Short aptituteStatus) {
        this.aptituteStatus = aptituteStatus;
    }

    public Date getAptituteChangeAt() {
        return aptituteChangeAt;
    }

    public void setAptituteChangeAt(Date aptituteChangeAt) {
        this.aptituteChangeAt = aptituteChangeAt;
    }

    public String getAptituteChangeReason() {
        return aptituteChangeReason;
    }

    public void setAptituteChangeReason(String aptituteChangeReason) {
        this.aptituteChangeReason = aptituteChangeReason == null ? null : aptituteChangeReason.trim();
    }

    public String getAttachCert() {
        return attachCert;
    }

    public void setAttachCert(String attachCert) {
        this.attachCert = attachCert == null ? null : attachCert.trim();
    }
}