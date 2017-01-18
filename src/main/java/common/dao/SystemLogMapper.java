package common.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import common.model.SystemLog;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述> 系统日志Dao
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
public interface SystemLogMapper {
    
    /**
     * 
     *〈简述〉保存系统日志
     *〈详细描述〉
     * @author myc
     * @param systemLog {@link SystemLog}
     */
    void save(SystemLog systemLog);
    
    /**
     * 
     *〈简述〉根据相关的参数查询
     *〈详细描述〉
     * @author myc
     * @param paramMap 参数map
     * @return
     */
    List<SystemLog> getListByParam(Map<String, Object> paramMap);
    
    /**
     * 
     *〈简述〉根据主键查询
     *〈详细描述〉
     * @author myc
     * @param id  主键
     * @return
     */
    SystemLog findById(@Param("id")String id);
}
