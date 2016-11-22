package ses.dao.bms;

import java.util.List;

import ses.model.bms.Category;

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
}
