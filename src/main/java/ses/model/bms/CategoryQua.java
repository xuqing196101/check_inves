package ses.model.bms;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述>品目质量关联实体
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
public class CategoryQua {
    
    /** 主键  */
    private String id;
    /** 品目Id **/
    private String categoryId;
    /** 资质Id **/
    private String quaId;
    /** 资质类型Id  1:通用 2：物资生成型 3：物资销售型**/
    private int quaType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getQuaId() {
        return quaId;
    }

    public void setQuaId(String quaId) {
        this.quaId = quaId;
    }

    public int getQuaType() {
        return quaType;
    }

    public void setQuaType(int quaType) {
        this.quaType = quaType;
    }

    
    
}
