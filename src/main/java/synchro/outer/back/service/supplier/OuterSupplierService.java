package synchro.outer.back.service.supplier;

import java.util.Date;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述>供应商信息同步
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
public interface OuterSupplierService {
    
    /**
     * 
     *〈简述〉获取提交的供应商
     *〈详细描述〉
     * @author myc
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param synchDate 同步时间
     */
    public void exportCommitSupplier(String startTime, String endTime, Date synchDate);
    
    /**
     * 
    * @Title: modify
    * @Description: 备份修退回修改的供应商
    * author: Li Xiaoxiao 
    * @param @param startTime
    * @param @param endTime
    * @param @param synchDate     
    * @return void     
    * @throws
     */
    public void modify(String startTime, String endTime, Date synchDate);
    
    
    
    /**
     * 
    * @Title: auditPass
    * @Description: 审核通过 
    * author: Li Xiaoxiao 
    * @param @param startTime
    * @param @param endTime     
    * @return void     
    * @throws
     */
    public void auditPass(String startTime, String endTime);
    
    
    public void tempSupplier(String startTime, String endTime);
    
}
