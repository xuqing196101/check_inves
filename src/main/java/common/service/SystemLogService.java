package common.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import common.model.SystemLog;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述> 系统日志service
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
public interface SystemLogService {

    /**
     * 
     *〈简述〉保存系统日志
     *〈详细描述〉
     * @author myc
     * @param systemLog
     */
    void saveLog(SystemLog systemLog);
    
    /**
     * 
     *〈简述〉获取操作记录
     *〈详细描述〉
     * @author myc
     * @param request {@link HttpServletRequest}
     * @return
     */
    public List<SystemLog> getListByParam(HttpServletRequest request, Integer pageNum);
    
    /**
     * 
     *〈简述〉根据主键查询详情
     *〈详细描述〉
     * @author myc
     * @param id 主键
     * @return 
     */
    SystemLog findById(String id);
}
