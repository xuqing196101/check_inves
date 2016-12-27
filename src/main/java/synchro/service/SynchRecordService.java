package synchro.service;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述>同步service
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
public interface SynchRecordService {
    
    /**
     * 
     *〈简述〉记录新增的供应商信息
     *〈详细描述〉
     * @param  content 内容
     * @author myc
     */
    public void backNewSupplierRecord(String content);
    
    /**
     * 
     *〈简述〉记录修改的供应商信息
     *〈详细描述〉
     * @author myc
     * @param content 内容
     */
    public void backModifySupplierRecord(String content);
    
    /**
     * 
     *〈简述〉记录供应商导入信息
     *〈详细描述〉
     * @author myc
     * @param content 内容
     */
    public void importSupplierRecord(String content);
}
