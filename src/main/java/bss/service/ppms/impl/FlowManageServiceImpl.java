package bss.service.ppms.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import ses.dao.bms.DictionaryDataMapper;
import ses.model.bms.DictionaryData;
import ses.model.bms.User;
import ses.util.PropUtil;
import ses.util.WfUtil;
import bss.dao.ppms.FlowDefineMapper;
import bss.dao.ppms.FlowExecuteMapper;
import bss.model.ppms.FlowDefine;
import bss.model.ppms.FlowExecute;
import bss.service.ppms.FlowMangeService;

/**
 * 版权：(C) 版权所有 
 * <简述>项目实施流程环节业务处理实现
 * <详细描述>
 * @author   Ye MaoLin
 * @version  
 * @since
 * @see
 */
@Service
public class FlowManageServiceImpl implements FlowMangeService {
	
    @Autowired
	private FlowDefineMapper flowDefineMapper;
    
    @Autowired
    private FlowExecuteMapper flowExecuteMapper;
    
    @Autowired
    private DictionaryDataMapper dataMapper;
    

    @Override
    public List<FlowDefine> find(FlowDefine fd) {
        return flowDefineMapper.findList(fd);
    }

    @Override
    public void update(FlowDefine fd) {
        flowDefineMapper.update(fd);
    }

    @Override
    public List<FlowDefine> listByPage(FlowDefine fd, int page) {
        PageHelper.startPage(page,Integer.parseInt(PropUtil.getProperty("pageSize")));
        List<FlowDefine> fds = flowDefineMapper.findList(fd);
        return fds;
    }

    @Override
    public void save(FlowDefine fd) {
        flowDefineMapper.insert(fd);
    }

    @Override
    public void saveExecute(FlowExecute flowExecute) {
        flowExecuteMapper.insert(flowExecute);
    }

    @Override
    public void updateExecute(FlowExecute flowExecute) {
        flowExecuteMapper.update(flowExecute);
    }

    @Override
    public List<FlowExecute> findFlowExecute(FlowExecute flowExecute) {
        return flowExecuteMapper.findList(flowExecute);
    }

    @Override
    public FlowDefine getFlowDefine(String id) {
        return flowDefineMapper.get(id);
    }

    @Override
    public FlowExecute getFlowExecute(String id) {
        return flowExecuteMapper.get(id);
    }
    
    /**
     *〈简述〉添加一条流程执行记录
     *〈详细描述〉
     * @author Ye MaoLin
     * @param request
     * @param flowDefineId 流程环节定义
     * @param projectId 项目id
     * @param status 执行状态
     */
    public void flowExe(HttpServletRequest request, String flowDefineId, String projectId, Integer status){
        FlowExecute temp = new FlowExecute();
        temp.setFlowDefineId(flowDefineId);
        temp.setProjectId(projectId);
        List<FlowExecute> flowExecutes = flowExecuteMapper.findExecuted(temp);
        //如果该项目该环节流程已经执行过
        if (flowExecutes != null && flowExecutes.size() > 0) {
            //执行记录设置为假删除状态
            FlowExecute oldFlowExecute = flowExecutes.get(0); 
            oldFlowExecute.setIsDeleted(1);
            oldFlowExecute.setUpdatedAt(new Date());
            updateExecute(oldFlowExecute);
            //新增一条相同环节记录
            oldFlowExecute.setCreatedAt(new Date());
            oldFlowExecute.setStatus(status);
            oldFlowExecute.setId(WfUtil.createUUID());
            oldFlowExecute.setIsDeleted(0);
            User currUser = (User) request.getSession().getAttribute("loginUser");
            oldFlowExecute.setOperatorId(currUser.getId());
            oldFlowExecute.setOperatorName(currUser.getRelName());
            saveExecute(oldFlowExecute);
        } else {
            //如果该项目该环节流程没有执行过
            FlowDefine flowDefine = getFlowDefine(flowDefineId);
            FlowExecute flowExecute = new FlowExecute();
            flowExecute.setCreatedAt(new Date());
            flowExecute.setFlowDefineId(flowDefineId);
            flowExecute.setIsDeleted(0);
            User currUser = (User) request.getSession().getAttribute("loginUser");
            flowExecute.setOperatorId(currUser.getId());
            flowExecute.setOperatorName(currUser.getRelName());
            flowExecute.setProjectId(projectId);
            flowExecute.setStatus(status);
            flowExecute.setId(WfUtil.createUUID());
            flowExecute.setStep(flowDefine.getStep());
            saveExecute(flowExecute);
        }
    }
    
    
    /**
     *〈简述〉预研添加一条流程执行记录
     *〈详细描述〉
     * @author FengTian
     * @param request
     * @param flowDefineId 流程环节定义
     * @param projectId 项目id
     * @param status 执行状态
     */
    public void flowExes(HttpServletRequest request, String flowDefineId, String projectId, Integer status){
        FlowExecute temp = new FlowExecute();
        temp.setFlowDefineId(flowDefineId);
        temp.setProjectId(projectId);
        List<FlowExecute> flowExecutes = flowExecuteMapper.findExecuted(temp);
        //如果该项目该环节流程已经执行过
        if (flowExecutes != null && flowExecutes.size() > 0) {
            //执行记录设置为假删除状态
            FlowExecute oldFlowExecute = flowExecutes.get(0); 
            oldFlowExecute.setIsDeleted(1);
            oldFlowExecute.setUpdatedAt(new Date());
            updateExecute(oldFlowExecute);
            //新增一条相同环节记录
            oldFlowExecute.setCreatedAt(new Date());
            oldFlowExecute.setStatus(status);
            oldFlowExecute.setId(WfUtil.createUUID());
            oldFlowExecute.setIsDeleted(0);
            User currUser = (User) request.getSession().getAttribute("loginUser");
            oldFlowExecute.setOperatorId(currUser.getId());
            oldFlowExecute.setOperatorName(currUser.getRelName());
            oldFlowExecute.setStatus(status);
            saveExecute(oldFlowExecute);
        } else {
            //如果该项目该环节流程没有执行过
            DictionaryData dd = dataMapper.selectByPrimaryKey(flowDefineId);
            FlowExecute flowExecute = new FlowExecute();
            flowExecute.setCreatedAt(new Date());
            flowExecute.setFlowDefineId(flowDefineId);
            flowExecute.setIsDeleted(0);
            User currUser = (User) request.getSession().getAttribute("loginUser");
            flowExecute.setOperatorId(currUser.getId());
            flowExecute.setOperatorName(currUser.getRelName());
            flowExecute.setProjectId(projectId);
            flowExecute.setStatus(status);
            flowExecute.setId(WfUtil.createUUID());
            flowExecute.setStep(dd.getPosition());
            saveExecute(flowExecute);
        }
    }
	
}
