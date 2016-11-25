package ses.service.bms;

import java.util.List;

import ses.formbean.CategotyBean;
import ses.model.bms.CategoryAssigned;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述>
 *  产品目录分配service
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
public interface CategoryAssignedService {

   /**
    * 
    *〈简述〉
    * 产品目录分配
    *〈详细描述〉
    * @author myc
    * @param orgIds  选择的需求部门id
    * @param cateIds 所有产品id
    * @param cateName 品目名称
    * @return
    */
   public String assigned(String orgIds, String cateIds, String cateName);
   
   /**
    * 
    *〈简述〉
    * 取消任务分配
    *〈详细描述〉
    * @author myc
    * @param orgIds {@link java.lang.String} 需求部门Id
    * @param cateIds {@link java.lang.String} 品目Id
    * @return
    */
   public String unassigned(String orgIds, String cateIds);
   
   /**
    * 
    *〈简述〉
    * 根据需求部门id查询品目
    *〈详细描述〉
    * @author myc
    * @param orgId
    * @return 品目集合
    */
   public List<CategoryAssigned> findCaListByOrgId(String orgId);
   
   /**
    * 
    *〈简述〉
    * 获取授权后的结果
    *〈详细描述〉
    * @author myc
    * @param orgIds
    * @return
    */
   public List<CategotyBean> getCateAssignedRes(String orgIds);
   
   /**
    * 
    *〈简述〉
    *  获取已经分配的品目Id
    *〈详细描述〉
    * @author myc
    * @return
    */
   public List<String> getAllocationItemIds(String orgId);
   
   
}
