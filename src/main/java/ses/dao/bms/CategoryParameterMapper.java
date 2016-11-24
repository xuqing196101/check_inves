package ses.dao.bms;

import java.util.List;

import ses.model.bms.Category;
import ses.model.bms.CategoryParameter;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述>
 *  参数管理持久层
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
public interface CategoryParameterMapper {

    /**
     * 
     *〈简述〉
     * 查询品目tree数据
     *〈详细描述〉
     * @author myc
     * @param orgId 需求单位Id
     * @return
     */
    public List<Category> findCategoryTree(String orgId);
    
    /**
     * 
     *〈简述〉
     * 保存
     *〈详细描述〉
     * @author myc
     * @param cp {@link CategoryParameter}
     */
    public void saveParameter(CategoryParameter cp);
    
    /**
     * 
     *〈简述〉
     *  根据品目Id进行删除
     *〈详细描述〉
     * @author myc
     * @param cateId 品目ID
     */
    public void delParameter(String cateId);
    
    
    /**
     * 
     *〈简述〉
     *  根据品目类型ID获取对应的参数
     *〈详细描述〉
     * @author myc
     * @param cateId 类型参数Id
     * @return
     */
    public List<CategoryParameter> getParamsByCateId(String cateId);
    
    /**
     * 
     *〈简述〉
     * 根据主键Id查询CategoryParameter对象
     *〈详细描述〉
     * @author myc
     * @param id {@link java.lang.String}
     * @return
     */
    public CategoryParameter getParameterById(String id);
    
    /**
     * 
     *〈简述〉
     *  更新对象
     *〈详细描述〉
     * @author myc
     * @param cp CategoryParameter 对象
     */
    public void update(CategoryParameter cp);
    
}
