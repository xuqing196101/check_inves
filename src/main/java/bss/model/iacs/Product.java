package bss.model.iacs;

public class Product {
	
    private String id;
    /**
     * 编码
     */
    private String productNo;
    
    private String productName;

    private String productInstruction;

    private String productCategoryId;

    private Short isDetele;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo == null ? null : productNo.trim();
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public String getProductInstruction() {
        return productInstruction;
    }

    public void setProductInstruction(String productInstruction) {
        this.productInstruction = productInstruction == null ? null : productInstruction.trim();
    }

    public String getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(String productCategoryId) {
        this.productCategoryId = productCategoryId == null ? null : productCategoryId.trim();
    }

    public Short getIsDetele() {
        return isDetele;
    }

    public void setIsDetele(Short isDetele) {
        this.isDetele = isDetele;
    }
}