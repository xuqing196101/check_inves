package synchro.service;

import java.util.Date;

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
     *〈简述〉记录新增的供应商导出信息
     *〈详细描述〉
     * @param  content 内容
     * @param  synchDate 同步时间
     * @author myc
     */
    public void commitSupplierRecord(String content, Date synchDate);

    /**
     *
     *〈简述〉记录新增的专家导出信息
     *〈详细描述〉
     * @param  content 内容
     * @author myc
     */
    public void commitExpertRecord(String content);

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
    public void importModifySupplierRecord(String content,Date synchDate);
    
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
     *〈简述〉记录新注册专家导入信息
     *〈详细描述〉
     * @author WangHuijie
     * @param content 内容
     */
    public void importNewExpertRecord(String content);
    
    /**
     * 
     *〈简述〉记录修改的专家导入信息
     *〈详细描述〉
     * @author WangHuijie
     * @param content 内容
     */
    public void importModifyExpertRecord(String content);
    
    /**
     * 
     *〈简述〉记录信息同步
     *〈详细描述〉
     * @author myc
     * @param content 内容
     */
    public void backupInfos(Date time,String content);
    /**
     * 同步竞价
     * @param date
     * @param content
     * @param dateCode
     * @param type
     * @param commitInfos
     */
    public void synchBidding(Date date, String content,String dateCode,int type,String commitInfos);
    
    /**
     * 
     *〈简述〉记录信息导入
     *〈详细描述〉
     * @author myc
     * @param content 内容
     */
    public void importInfos(String content);
    
    /**
     * 
     *〈简述〉备份附件
     *〈详细描述〉
     * @author myc
     * @param content 内容
     */
    public void backupAttach(String content);
    
    /**
     * 
     *〈简述〉备份附件
     *〈详细描述〉
     * @author myc
     * @param content 内容
     */
    public void importAttach(String content);
    
    /**
     * 
     *〈简述〉备份附件
     *〈详细描述〉
     * @author myc
     * @param content 内容
     */
    public void importAttachKey(String content,Integer key);
    
    /**
     * 
     *〈简述〉根据类型获取最近一次的同步时间
     *〈详细描述〉
     * @author myc
     * @param dataType 类型Id
     * @return
     */
    public String getSynchTime(Integer operType, String dataType);
    
}
