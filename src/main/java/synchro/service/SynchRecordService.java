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
     *〈简述〉记录新增供应商导入信息
     *〈详细描述〉
     * @author myc
     * @param content 内容
     */
    public void importNewSupplierRecord(String content);
    
    /**
     * 
     *〈简述〉记录修改的供应商记录
     *〈详细描述〉
     * @author myc
     * @param content
     */
    public void importModifySupplierRecord(String content);
    
    /**
     * 
     *〈简述〉记录新增的专家信息
     *〈详细描述〉
     * @param  content 内容
     * @author WangHuijie
     */
    public void backNewExpertRecord(String content);
    
    /**
     * 
     *〈简述〉记录修改的专家信息
     *〈详细描述〉
     * @author WangHuijie
     * @param content 内容
     */
    public void backModifyExpertRecord(String content);
    
    /**
     * 
     *〈简述〉记录专家导入信息
     *〈详细描述〉
     * @author WangHuijie
     * @param content 内容
     */
    public void importExpertRecord(String content);
    
}
