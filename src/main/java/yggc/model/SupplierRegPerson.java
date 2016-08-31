package yggc.model;

public class SupplierRegPerson {
    private Long id;

    private Long matEngId;

    private String regType;

    private Long regNumber;

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

    public String getRegType() {
        return regType;
    }

    public void setRegType(String regType) {
        this.regType = regType == null ? null : regType.trim();
    }

    public Long getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(Long regNumber) {
        this.regNumber = regNumber;
    }
}