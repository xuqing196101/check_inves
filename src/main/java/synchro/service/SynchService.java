package synchro.service;

import java.util.List;

import synchro.model.SynchRecord;

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
public interface SynchService {
    
    /**
     * 
     *〈简述〉获取同步日志列表
     *〈详细描述〉
     * @author myc
     * @param optype 操作类型
     * @param page 页码
     * @return
     */
    public List<SynchRecord> getList(Integer optype, Integer page);
}
