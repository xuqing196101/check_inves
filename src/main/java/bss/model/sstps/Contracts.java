package bss.model.sstps;

public class Contracts {
    private String id;

    private String name;

    private String contractNo;

    private Integer appraisal;

    private Integer given;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo == null ? null : contractNo.trim();
    }

    public Integer getAppraisal() {
        return appraisal;
    }

    public void setAppraisal(Integer appraisal) {
        this.appraisal = appraisal;
    }

    public Integer getGiven() {
        return given;
    }

    public void setGiven(Integer given) {
        this.given = given;
    }
}