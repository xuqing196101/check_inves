package bss.model.iacs;

public class ProductCategory {
    private String id;

    private String productCategory;

    private String categoryInstruction;

    private String parentId;

    private Short isDetele;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory == null ? null : productCategory.trim();
    }

    public String getCategoryInstruction() {
        return categoryInstruction;
    }

    public void setCategoryInstruction(String categoryInstruction) {
        this.categoryInstruction = categoryInstruction == null ? null : categoryInstruction.trim();
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }

    public Short getIsDetele() {
        return isDetele;
    }

    public void setIsDetele(Short isDetele) {
        this.isDetele = isDetele;
    }
}