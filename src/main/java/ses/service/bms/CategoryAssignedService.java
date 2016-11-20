package ses.service.bms;

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
    * @return
    */
   public String assigned(String orgIds, String cateIds);
}
