package bss.service.ppms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import ses.util.PropUtil;

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
@Service("flowManage")
public class FlowManageServiceImpl implements FlowMangeService {
	
    @Autowired
	private FlowDefineMapper flowDefineMapper;
    
    @Autowired
    private FlowExecuteMapper flowExecuteMapper;

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
	
}
