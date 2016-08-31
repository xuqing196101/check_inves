package yggc.model;

import java.util.Date;

public class SupplierCertSell {
    private Long id;

    private Long matSellId;

    private String name;

    private String levelCert;

    private String licenceAuthorith;

    private Date expStartDate;

    private Date expEndDate;

    private Short mot;

    private String attach;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMatSellId() {
        return matSellId;
    }

    public void setMatSellId(Long matSellId) {
        this.matSellId = matSellId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getLevelCert() {
        return levelCert;
    }

    public void setLevelCert(String levelCert) {
        this.levelCert = levelCert == null ? null : levelCert.trim();
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

    public Short getMot() {
        return mot;
    }

    public void setMot(Short mot) {
        this.mot = mot;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach == null ? null : attach.trim();
    }
}