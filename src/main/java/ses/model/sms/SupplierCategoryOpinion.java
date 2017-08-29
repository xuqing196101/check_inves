package ses.model.sms;

import java.io.Serializable;

/**
 * 
 * Description: 供应商产品类别审核意见实体
 * 
 * @author zhang shubin
 * @version 2016-9-7
 * @since JDK1.7
 */
public class SupplierCategoryOpinion implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * 目录ID
     */
    private String categoryId;

    /**
     * 目录名称
     */
    private String categoryName;

    /**
     * 目录父节点ID
     */
    private String parentId;

    /**
     * 审核意见
     */
    private String Opinion;

    /**
     * 类型
     */
    private String type;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getOpinion() {
        return Opinion;
    }

    public void setOpinion(String opinion) {
        Opinion = opinion;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
