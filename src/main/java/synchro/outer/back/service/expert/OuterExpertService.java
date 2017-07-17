package synchro.outer.back.service.expert;


/**
 * 
 * 版权：(C) 版权所有 
 * <简述>专家信息同步
 * <详细描述>
 * @author   WangHuijie
 * @version  
 * @since
 * @see
 */
public interface OuterExpertService {

    /**
     * 
     *〈简述〉备份新注册的专家
     *〈详细描述〉
     * @author WangHuijie
     */
    public void backupCreated(String startTime, String endTime);    
    
    /**
     * 
     *〈简述〉备份修改的专家
     *〈详细描述〉
     * @author WangHuijie
     */
    public void backupModified();

    /**
     * 退回修改专家导出到文件待入库
     * @param startTime
     * @param endTime
     */
    void backModifyExpert(String startTime, String endTime);
    
    /**
     * 
     * Description:查询公示专家导出外网
     * 
     * @author Easong
     * @version 2017/7/9
     * @param startTime
     * @param endTime
     * @since JDK1.7
     */
    void selectExpByPublictyOfExport(String startTime, String endTime);
}
