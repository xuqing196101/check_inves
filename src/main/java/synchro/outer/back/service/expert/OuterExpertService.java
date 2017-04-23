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
}
