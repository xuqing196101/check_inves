package bss.service.ppms.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.bms.DictionaryDataMapper;
import ses.dao.bms.UserMapper;
import ses.dao.oms.PurchaseInfoMapper;
import ses.model.bms.DictionaryData;
import ses.model.bms.User;
import ses.model.oms.PurchaseInfo;
import ses.util.PropertiesUtil;
import ses.util.WfUtil;

import com.github.pagehelper.PageHelper;

import bss.dao.ppms.AdvancedProjectMapper;
import bss.dao.ppms.FlowExecuteMapper;
import bss.model.ppms.AdvancedProject;
import bss.model.ppms.FlowDefine;
import bss.model.ppms.FlowExecute;
import bss.service.ppms.AdvancedProjectService;

@Service("advancedProjectService")
public class AdvancedProjectServiceImpl implements AdvancedProjectService {
    @Autowired
    private AdvancedProjectMapper advancedProjectMapper;
    
    @Autowired
    private FlowExecuteMapper flowExecuteMapper;
    
    @Autowired
    private DictionaryDataMapper dataMapper;
    
    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private PurchaseInfoMapper purchaseInfoMapper;

    @Override
    public void deleteById(String id) {
        
        advancedProjectMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void save(AdvancedProject record) {
        
        advancedProjectMapper.insertSelective(record);
    }

    @Override
    public AdvancedProject selectById(String id) {
        
        return advancedProjectMapper.selectAdvancedProjectByPrimaryKey(id);
    }

    @Override
    public void update(AdvancedProject record) {
        
        advancedProjectMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public List<AdvancedProject> selectByList(HashMap<String, Object> map) {
        
        return advancedProjectMapper.selectByList(map);
    }

    @Override
    public boolean SameNameCheck(AdvancedProject advancedProject) {
        boolean flag= true;
        List<AdvancedProject> list = advancedProjectMapper.verifyByProject(advancedProject);
        if(list != null && list.size()>0){
            flag = false;
        }
        return flag;
    }

    @Override
    public List<AdvancedProject> selectByDemand(HashMap<String, Object> map) {
        
        return advancedProjectMapper.selectByDemand(map);
    }

    @Override
    public List<AdvancedProject> selectByOrg(HashMap<String, Object> map) {
        
        return advancedProjectMapper.selectByOrg(map);
    }

    @Override
    public List<AdvancedProject> selectProjectByAll(Integer page,AdvancedProject project) {
        PropertiesUtil config = new PropertiesUtil("config.properties");
        PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
        return advancedProjectMapper.selectProjectByAll(project);
    }
    
    @Override
    public JSONObject getNextFlow(User user, String projectId, String flowDefineId) {
        JSONObject jsonObj = new JSONObject();
        AdvancedProject project = advancedProjectMapper.selectAdvancedProjectByPrimaryKey(projectId);
        //查询当前项目有没有经办人
        FlowExecute execute0 = new FlowExecute();
        execute0.setProjectId(projectId);
        execute0.setStatus(0);
        execute0.setIsDeleted(0);
        List<FlowExecute> execute0s = flowExecuteMapper.findList(execute0);
        DictionaryData dd = new DictionaryData();
        dd.setCode(project.getPurchaseType());
        List<DictionaryData> list = dataMapper.findList(dd);
        //如果当前项目没有初始化各环节经办人,或者初始化的环节不够
        if (execute0s == null || execute0s.size() < list.size()) {
          for (FlowExecute flowExecute : execute0s) {
              flowExecuteMapper.delete(flowExecute.getId());
          }
            //设置各环节经办人默认为项目负责人
            FlowExecute flowExecute = new FlowExecute();
            flowExecute.setProjectId(projectId);
            flowExecute.setStatus(0);
            flowExecute.setCreatedAt(new Date());
            flowExecute.setUpdatedAt(new Date());
            List<User> operator = userMapper.selectByPrimaryKey(project.getPrincipal());
            if (operator != null && operator.size() > 0) {
                flowExecute.setOperatorId(operator.get(0).getId());
                flowExecute.setOperatorName(operator.get(0).getRelName());
            }
            flowExecute.setIsDeleted(0);
            DictionaryData data = new DictionaryData();
            data.setCode(project.getPurchaseType());
            data.setIsDeleted(0);
            List<DictionaryData> list2 = dataMapper.findList(data);
            for (DictionaryData dd1 : list2) {
                flowExecute.setId(WfUtil.createUUID());
                flowExecute.setFlowDefineId(dd1.getId());
                flowExecute.setStep(dd1.getPosition());
                flowExecuteMapper.insert(flowExecute);
            }
        }
        
        //当前点击环节
        DictionaryData dictionaryData = new DictionaryData();
        if ("0".equals(flowDefineId)) {
            //默认进来第一环节
            DictionaryData define = new DictionaryData();
            define.setCode(project.getPurchaseType());
            define.setPosition(1);
            List<DictionaryData> defines = dataMapper.findList(define);
            if (defines != null && defines.size() > 0) {
                dictionaryData = defines.get(0);
            }
        } else {
            dictionaryData = dataMapper.selectByPrimaryKey(flowDefineId);
        }
        jsonObj.put("currFlowDefineId", dictionaryData.getId());
        
        //获取环节是否结束
        FlowExecute fe = new FlowExecute();
        fe.setFlowDefineId(dictionaryData.getId());
        fe.setProjectId(projectId);
        fe.setStatus(3);
        List<FlowExecute> fes = flowExecuteMapper.findList(fe);
        if(fes != null && fes.size() > 0){
            jsonObj.put("isFes", 1);
        }else{
            jsonObj.put("isFes", 0);
        }
        //当前登录人对当前环节的操作权限
        FlowExecute execute = new FlowExecute();
        execute.setFlowDefineId(dictionaryData.getId());
        execute.setIsDeleted(0);
        execute.setProjectId(projectId);
        execute.setStatus(0);
        List<FlowExecute> executes = flowExecuteMapper.findList(execute);
        if (executes != null && executes.size() > 0) {
            List<User> users = userMapper.selectByPrimaryKey(executes.get(0).getOperatorId());
            if (users != null && users.size() > 0) {
                jsonObj.put("operateName", users.get(0).getRelName());
                jsonObj.put("currOperatorId", users.get(0).getId());
            }
            if (executes.get(0).getOperatorId().equals(user.getId())) {
                //环节是否结束
                if(fes != null && fes.size() > 0){
                    jsonObj.put("isOperate", 0);
                } else {
                  //具有操作权限
                    jsonObj.put("isOperate", 1);
                }
                
            } else {
                //具有查看权限
                jsonObj.put("isOperate", 0);
            }
        }
        
        
        DictionaryData di = new DictionaryData();
        di.setCode(dictionaryData.getCode());
        di.setPosition(dictionaryData.getPosition() + 1);
        List<DictionaryData> nextFlowDefine = dataMapper.findList(di);
        if (nextFlowDefine != null && nextFlowDefine.size() > 0) {
            //下一环节
            DictionaryData fDefine = nextFlowDefine.get(0);
            FlowExecute flowExecute = new FlowExecute();
            flowExecute.setFlowDefineId(fDefine.getId());
            flowExecute.setIsDeleted(0);
            flowExecute.setProjectId(projectId);
            flowExecute.setStatus(0);
            List<FlowExecute> flowExecutes = flowExecuteMapper.findList(flowExecute);
            if (flowExecutes != null && flowExecutes.size() > 0) {
                FlowExecute flowExecute2 = flowExecutes.get(0);
                jsonObj.put("success", true);
                jsonObj.put("isEnd", false);
                jsonObj.put("operatorId", flowExecute2.getOperatorId());
                jsonObj.put("flowDefineId", fDefine.getId());
                jsonObj.put("flowDefineName", fDefine.getName());
            }
        } else {
            //当前环节是最后一个环节
            jsonObj.put("success", true);
            jsonObj.put("isEnd", true);
        }
        List<PurchaseInfo> purchaseInfo = new ArrayList<>();
        if(user != null && user.getOrg() != null){
           //获取当前用户所属机构人员
           purchaseInfo = purchaseInfoMapper.findPurchaseUserList(user.getOrg().getId());
        }
        jsonObj.put("users", purchaseInfo);
        return jsonObj;
    }
    
    
    
    
    @Override
    public JSONObject updateCurrOperator(User currLoginUser, String projectId, String currFlowDefineId, String currUpdateUserId) {
        JSONObject jsonObj = new JSONObject();
        FlowExecute flowExecute = new FlowExecute();
        flowExecute.setFlowDefineId(currFlowDefineId);
        flowExecute.setProjectId(projectId);
        flowExecute.setIsDeleted(0);
        flowExecute.setStatus(0);
        List<FlowExecute> flowExecutes = flowExecuteMapper.findList(flowExecute);
        if (flowExecutes != null && flowExecutes.size() > 0) {
            FlowExecute flowExecute2 = flowExecutes.get(0);
            flowExecute2.setOperatorId(currUpdateUserId);
            List<User> users = userMapper.selectByPrimaryKey(currUpdateUserId);
            if (users != null && users.size() > 0) {
                flowExecute2.setOperatorName(users.get(0).getRelName()); 
            }
            flowExecute2.setUpdatedAt(new Date());
            flowExecuteMapper.update(flowExecute2);
            DictionaryData dd = dataMapper.selectByPrimaryKey(currFlowDefineId);
            if (dd != null) {
                jsonObj.put("url", dd.getDescription());
                jsonObj.put("flowDefineName", dd.getName());
            }
            jsonObj.put("currLoginUser", currLoginUser);
            jsonObj.put("success", true);
        } else {
            jsonObj.put("success", false);
        }
        return jsonObj;
    }
    
    
    
    @Override
    public JSONObject isSubmit(String projectId, String currFlowDefineId) {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("success", true);
        return jsonObj;
    }
    
    
    @Override
    public JSONObject submitHuanjie(User currLoginUser, String projectId, String currFlowDefineId) {
        JSONObject jsonObj = new JSONObject();
        FlowExecute temp = new FlowExecute();
        temp.setFlowDefineId(currFlowDefineId);
        temp.setProjectId(projectId);
        List<FlowExecute> flowExecutes = flowExecuteMapper.findExecuted(temp);
        //如果该项目该环节流程已经执行过
        if (flowExecutes != null && flowExecutes.size() > 0) {
            //执行记录设置为假删除状态
            FlowExecute oldFlowExecute = flowExecutes.get(0); 
            oldFlowExecute.setIsDeleted(1);
            oldFlowExecute.setUpdatedAt(new Date());
            flowExecuteMapper.update(oldFlowExecute);
            //新增一条相同环节记录
            oldFlowExecute.setCreatedAt(new Date());
            oldFlowExecute.setStatus(3);
            oldFlowExecute.setId(WfUtil.createUUID());
            oldFlowExecute.setIsDeleted(0);
            oldFlowExecute.setOperatorId(currLoginUser.getId());
            oldFlowExecute.setOperatorName(currLoginUser.getRelName());
            flowExecuteMapper.insert(oldFlowExecute);
        } else {
            //如果该项目该环节流程没有执行过
            DictionaryData dd = dataMapper.selectByPrimaryKey(currFlowDefineId);
            FlowExecute flowExecute = new FlowExecute();
            flowExecute.setCreatedAt(new Date());
            flowExecute.setFlowDefineId(currFlowDefineId);
            flowExecute.setIsDeleted(0);
            flowExecute.setOperatorId(currLoginUser.getId());
            flowExecute.setOperatorName(currLoginUser.getRelName());
            flowExecute.setProjectId(projectId);
            flowExecute.setStatus(3);
            flowExecute.setId(WfUtil.createUUID());
            flowExecute.setStep(dd.getPosition());
            flowExecuteMapper.insert(flowExecute);
        }
        jsonObj.put("success", true);
        return jsonObj;
    }

}
