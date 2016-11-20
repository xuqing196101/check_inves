package ses.dao.bms;

import org.apache.ibatis.annotations.Param;

import ses.model.bms.CategoryAssigned;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述>
 *   产品分配
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
public interface CategoryAssignedMapper {
    
    /**
     * 
     *〈简述〉
     *  批量保存
     *〈详细描述〉
     * @author myc
     * @param categoryAssigned {@link CategoryAssigned}
     */
    void batchInsert(CategoryAssigned categoryAssigned);
    
    /**
     * 
     *〈简述〉
     *  批量删除
     *〈详细描述〉
     * @author myc
     * @param cateId 产品目录Id {@link java.lang.String}
     * @param orgId 需求部门Id{@link java.lang.String}
     */
    void batchaDelete(@Param("cateId") String cateId, @Param("orgId")String orgId);
}
