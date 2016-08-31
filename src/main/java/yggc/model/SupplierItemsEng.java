package yggc.model;

public class SupplierItemsEng {
    private Long id;

    private Long matEngId;

    private Long itemsId;

    private String bigKindName;

    private String normalKindName;

    private String smallKingName;

    private String kindName;

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

    public Long getItemsId() {
        return itemsId;
    }

    public void setItemsId(Long itemsId) {
        this.itemsId = itemsId;
    }

    public String getBigKindName() {
        return bigKindName;
    }

    public void setBigKindName(String bigKindName) {
        this.bigKindName = bigKindName == null ? null : bigKindName.trim();
    }

    public String getNormalKindName() {
        return normalKindName;
    }

    public void setNormalKindName(String normalKindName) {
        this.normalKindName = normalKindName == null ? null : normalKindName.trim();
    }

    public String getSmallKingName() {
        return smallKingName;
    }

    public void setSmallKingName(String smallKingName) {
        this.smallKingName = smallKingName == null ? null : smallKingName.trim();
    }

    public String getKindName() {
        return kindName;
    }

    public void setKindName(String kindName) {
        this.kindName = kindName == null ? null : kindName.trim();
    }
}