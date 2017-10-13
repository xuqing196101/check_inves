package extract.model.expert;

/**
 * 
 * Description: 专家抽取品目信息实体
 * 
 * @author zhang shubin
 * @version 2016-9-7
 * @since JDK1.7
 */
public class ExtractCategory {

    /**
     * 主键
     */
    private String id;

    /**
     * 条件ID
     */
    private String conditionId;

    /**
     * 产品目录ID
     */
    private String categoryId;

    /**
     * 产品类型id
     */
    private String typeId;

    /**
     * 删除标识
     */
    private Short isDeleted;
    
    /**
     * 是否为工程专业品目
     */
    private Short isEng;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getConditionId() {
        return conditionId;
    }

    public void setConditionId(String conditionId) {
        this.conditionId = conditionId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public Short getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Short getIsEng() {
        return isEng;
    }

    public void setIsEng(Short isEng) {
        this.isEng = isEng;
    }
}
