package ses.dao.bms;

import java.util.List;

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
    
    /**
     * 
     *〈简述〉
     * 根据需求部门查询品目名称
     *〈详细描述〉
     * @author myc
     * @param orgId {@link 需求部门Id}
     * @return
     */
    List<CategoryAssigned> findCateListByOrg(String orgId);
    
    /**
     * 
     *〈简述〉
     *  获取组织机构下的已分配的品目,如果组织机构Id为空,返回所有的已分配的品目
     *〈详细描述〉
     * @author myc
     * @param orgId 组织机构Id
     * @return
     */
    List<String> findAllocationIds(@Param("orgId")String orgId);
    
    
}
