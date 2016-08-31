package yggc.model;

public class SupplierItemsPro {
    private Long id;

    private Long matProId;

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

    public Long getMatProId() {
        return matProId;
    }

    public void setMatProId(Long matProId) {
        this.matProId = matProId;
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