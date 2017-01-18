package common.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import common.dao.SystemLogMapper;
import common.model.SystemLog;
import common.service.SystemLogService;
import ses.util.PropUtil;

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
@Service
public class SystemLogServiceImpl implements SystemLogService {
    
    /** mapper 注解 **/
    @Autowired
    private SystemLogMapper systemLogMapper;
    
    /**
     * @see common.service.SystemLogService#saveLog(common.model.SystemLog)
     */
    @Override
    public void saveLog(SystemLog systemLog) {
        systemLogMapper.save(systemLog);
    }
    
    /**
     * 
     * @see common.service.SystemLogService#findById(java.lang.String)
     */
    @Override
    public SystemLog findById(String id) {
        if (StringUtils.isNotBlank(id)){
            return systemLogMapper.findById(id);
        }
        return new SystemLog();
    }

    /**
     * 
     * @see common.service.SystemLogService#getListByParam(javax.servlet.http.HttpServletRequest)
     */
    @Override
    public List<SystemLog> getListByParam(HttpServletRequest request, Integer pageNum) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("operPerson", request.getParameter("operPerson"));
        paramMap.put("logType", request.getParameter("logType"));
        paramMap.put("operIp", request.getParameter("operIp"));
        paramMap.put("operStartTime", request.getParameter("operStartTime"));
        paramMap.put("operEndTime", request.getParameter("operEndTime"));
        paramMap.put("desc", request.getParameter("desc"));
        
        if (pageNum == null){
            pageNum = 1;
        }
        PageHelper.startPage(pageNum,Integer.parseInt(PropUtil.getProperty("pageSize")));
        List<SystemLog> list = systemLogMapper.getListByParam(paramMap);
        if (list == null || list.size() == 0){
            list = new ArrayList<>();
        }
        return list;
    }
    
    

}
