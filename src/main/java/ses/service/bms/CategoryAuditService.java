package ses.service.bms;

import java.util.List;

import ses.model.bms.CategoryTree;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述>
 *  参数审核service
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
public interface CategoryAuditService {
    
    /**
     * 
     *〈简述〉
     * 初始化tree
     *〈详细描述〉
     * @author myc
     * @param treeId 根节点id
     * @return CategoryTree对象集合
     */
    public List<CategoryTree> initTree(String treeId);
}
