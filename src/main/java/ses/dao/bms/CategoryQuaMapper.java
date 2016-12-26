package ses.dao.bms;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import ses.model.bms.CategoryQua;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述>品目资质Mapper
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
public interface CategoryQuaMapper {

    /**
     * 
     *〈简述〉保存质量资质
     *〈详细描述〉
     * @author myc
     * @param categoryQua {@link CategoryQua}
     */
    void save(CategoryQua categoryQua);
    
    /**
     * 
     *〈简述〉根据品目Id查询关联资质信息
     *〈详细描述〉
     * @author myc
     * @param categoryId 品目id
     * @return  
     */
    List<CategoryQua> findList(@Param("categoryId")String categoryId);
    
    /**
     * 
     *〈简述〉根据品目Id删除品目
     *〈详细描述〉
     * @author myc
     * @param categoryId 品目Id
     */
    void delQuaByCategoryId(@Param("categoryId")String categoryId);
}
