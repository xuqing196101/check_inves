package synchro.outer.back.service.supplier;

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
     *〈简述〉备份新注册的供应商
     *〈详细描述〉
     * @author myc
     */
    public void backupCreated();
    
    /**
     * 
     *〈简述〉获取修改的供应商
     *〈详细描述〉
     * @author myc
     */
    public void backupModify();
}
