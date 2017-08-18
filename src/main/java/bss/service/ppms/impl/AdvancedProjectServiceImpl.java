package bss.service.ppms.impl;

import iss.dao.ps.ArticleMapper;
import iss.dao.ps.ArticleTypeMapper;
import iss.model.ps.Article;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.bms.DictionaryDataMapper;
import ses.dao.bms.UserMapper;
import ses.dao.oms.PurchaseInfoMapper;
import ses.dao.sms.QuoteMapper;
import ses.model.bms.DictionaryData;
import ses.model.bms.User;
import ses.model.oms.PurchaseInfo;
import ses.model.sms.Quote;
import ses.util.DictionaryDataUtil;
import ses.util.PropUtil;
import ses.util.PropertiesUtil;
import ses.util.WfUtil;

import com.github.pagehelper.PageHelper;
import common.constant.Constant;
import common.model.UploadFile;
import common.service.UploadService;

import bss.dao.ppms.AdvancedPackageMapper;
import bss.dao.ppms.AdvancedProjectMapper;
import bss.dao.ppms.FlowDefineMapper;
import bss.dao.ppms.FlowExecuteMapper;
import bss.dao.ppms.SaleTenderMapper;
import bss.dao.prms.FirstAuditMapper;
import bss.model.ppms.AdvancedPackages;
import bss.model.ppms.AdvancedProject;
import bss.model.ppms.FlowDefine;
import bss.model.ppms.FlowExecute;
import bss.model.ppms.Project;
import bss.model.ppms.SaleTender;
import bss.model.prms.FirstAudit;
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
    
    @Autowired
    private FlowDefineMapper flowDefineMapper;
    
    @Autowired
    private UploadService uploadService;
    
    @Autowired
    private ArticleTypeMapper articleTypeMapper;
    
    @Autowired
    private ArticleMapper articleMapper;
    
    @Autowired
    private SaleTenderMapper saleTenderMapper;
    
    @Autowired
    private QuoteMapper quoteMapper;

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
        FlowDefine fd0 = new FlowDefine();
        fd0.setIsDeleted(0);
        fd0.setPurchaseTypeId(project.getPurchaseType());
        List<FlowDefine> fds = flowDefineMapper.findList(fd0);
        //如果当前项目没有初始化各环节经办人,或者初始化的环节不够
        if (execute0s == null || execute0s.size() < fds.size()) {
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
            FlowDefine flowDefine = new FlowDefine();
            flowDefine.setPurchaseTypeId(project.getPurchaseType());
            flowDefine.setIsDeleted(0);
            List<FlowDefine> flowDefines = flowDefineMapper.findList(flowDefine);
            for (FlowDefine fd : flowDefines) {
                flowExecute.setId(WfUtil.createUUID());
                flowExecute.setFlowDefineId(fd.getId());
                flowExecute.setStep(fd.getStep());
                flowExecuteMapper.insert(flowExecute);
            }
        }
        
        //当前点击环节
        FlowDefine flowDefine = new FlowDefine();
        if ("0".equals(flowDefineId)) {
            //默认进来第一环节
            FlowDefine define = new FlowDefine();
            define.setIsDeleted(0);
            define.setPurchaseTypeId(project.getPurchaseType());
            define.setStep(1);
            List<FlowDefine> defines = flowDefineMapper.findList(define);
            if (defines != null && defines.size() > 0) {
                flowDefine = defines.get(0);
            }
        } else {
            flowDefine = flowDefineMapper.get(flowDefineId);
        }
        jsonObj.put("currFlowDefineId", flowDefine.getId());
        
        //获取环节是否结束
        FlowExecute fe = new FlowExecute();
        fe.setFlowDefineId(flowDefine.getId());
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
        execute.setFlowDefineId(flowDefine.getId());
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
        
        FlowDefine fd = new FlowDefine();
        fd.setPurchaseTypeId(flowDefine.getPurchaseTypeId());
        fd.setStep(flowDefine.getStep() + 1);
        List<FlowDefine> nextFlowDefine = flowDefineMapper.findList(fd);
        if (nextFlowDefine != null && nextFlowDefine.size() > 0) {
            //下一环节
            FlowDefine fDefine = nextFlowDefine.get(0);
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
                jsonObj.put("nextOperatorName", flowExecute2.getOperatorName());
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
       //获取当前项目所属机构人员
       String orgId = project.getPurchaseDepId();
       if (orgId != null && !"".equals(orgId)) {
           purchaseInfo = purchaseInfoMapper.findPurchaseUserList(orgId);
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
        FlowDefine flowDefine = flowDefineMapper.get(currFlowDefineId);
        AdvancedProject project = advancedProjectMapper.selectAdvancedProjectByPrimaryKey(projectId);
        if(flowDefine != null){
            if ("XMXX".equals(flowDefine.getCode())) {
                //项目信息
                if (project != null && project.getSupplierNumber() != null && project.getDeadline() != null && project.getBidDate() != null && StringUtils.isNotBlank(project.getBidAddress())) {
                  jsonObj.put("success", true);
                } else {
                  jsonObj.put("success", false);
                  jsonObj.put("msg", "请完善并保存项目信息");
                }
            } else if ("NZCGWJ".equals(flowDefine.getCode())) {
                String typeId = DictionaryDataUtil.getId("PROJECT_BID");
                List<UploadFile> files = uploadService.getFilesOther(projectId, typeId, Constant.TENDER_SYS_KEY+"");
                if(files != null && files.size() > 0){
                    jsonObj.put("success", true);
                } else {
                    jsonObj.put("success", false);
                    jsonObj.put("msg", "请完善信息");
                }
            } else if ("NZCGGG".equals(flowDefine.getCode())) {
                DictionaryData data = DictionaryDataUtil.findById(project.getPurchaseType());
                Article article = new Article();
                if("DYLY".equals(data.getCode())){
                    article.setArticleType(articleTypeMapper.selectArticleTypeByCode("single_source_notice"));
                } else {
                    article.setArticleType(articleTypeMapper.selectArticleTypeByCode("purchase_notice"));
                }
                article.setProjectId(projectId);
                List<Article> articles = articleMapper.selectArticleByProjectId(article);
                if(articles != null && articles.size() > 0){
                    jsonObj.put("success", true);
                } else {
                    jsonObj.put("success", false);
                    jsonObj.put("msg", "请拟制采购公告");
                }
            } else if ("FSBS".equals(flowDefine.getCode())) {
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("projectId", projectId);
                List<SaleTender> saleTenderList = saleTenderMapper.getAdPackegeSuppliers(hashMap);
                if(saleTenderList != null && saleTenderList.size() > 0){
                    jsonObj.put("success", true);
                } else {
                    jsonObj.put("success", false);
                    jsonObj.put("msg", "请登记供应商");
                }
            } else if ("GYSQD".equals(flowDefine.getCode())) {
                jsonObj.put("success", true);
            } else if("KBCB".equals(flowDefine.getCode())) {
                //开标唱标
                Quote quoteCondition = new Quote();
                quoteCondition.setProjectId(projectId);
                List<Date> listDate = quoteMapper.selectQuoteCount(quoteCondition);
                if(listDate != null && listDate.size() > 0){
                    jsonObj.put("success", true);
                }else{
                    jsonObj.put("success", false);
                    jsonObj.put("msg", "请填写报价");
                }
            } else {
                jsonObj.put("success", true);
            }
        }
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
            FlowDefine flowDefine = flowDefineMapper.get(currFlowDefineId);
            FlowExecute flowExecute = new FlowExecute();
            flowExecute.setCreatedAt(new Date());
            flowExecute.setFlowDefineId(currFlowDefineId);
            flowExecute.setIsDeleted(0);
            flowExecute.setOperatorId(currLoginUser.getId());
            flowExecute.setOperatorName(currLoginUser.getRelName());
            flowExecute.setProjectId(projectId);
            flowExecute.setStatus(3);
            flowExecute.setId(WfUtil.createUUID());
            flowExecute.setStep(flowDefine.getStep());
            flowExecuteMapper.insert(flowExecute);
        }
        FlowDefine flowDefine = flowDefineMapper.get(currFlowDefineId);
        if (flowDefine != null) {
            jsonObj.put("url", flowDefine.getAdvancedUrl());
        }
        jsonObj.put("success", true);
        return jsonObj;
    }

    @Override
    public List<AdvancedProject> findByPackage(Integer page, User user, AdvancedProject project) {
        HashMap<String,Object> map = new HashMap<String,Object>();
        if(StringUtils.isNotBlank(project.getName())){
            map.put("name", project.getName());
        }
        if(StringUtils.isNotBlank(project.getProjectNumber())){
            map.put("projectNumber", project.getProjectNumber());
        }
        if(StringUtils.isNotBlank(project.getStatus())){
            map.put("status", project.getStatus());
        }
        map.put("principal", user.getId());
        map.put("purchaseDepId", user.getOrg().getId());
        PageHelper.startPage(page,Integer.parseInt(PropUtil.getProperty("pageSizeArticle")));
        List<AdvancedProject> list = advancedProjectMapper.findByPackage(map);
        return list;
    }

    @Override
    public HashMap<String, Object> getFlowDefine(String purchaseTypeId, String projectId) {
        if(StringUtils.isNotBlank(projectId) && StringUtils.isNotBlank(purchaseTypeId)){
            HashMap<String, Object> map = new HashMap<String, Object>();
            FlowDefine fd = new FlowDefine();
            fd.setPurchaseTypeId(purchaseTypeId);
            //该采购方式定义的流程环节
            List<FlowDefine> list = flowDefineMapper.findList(fd);
            for (FlowDefine flowDefine : list) {
                FlowExecute flowExecute = new FlowExecute();
                flowExecute.setProjectId(projectId);
                flowExecute.setFlowDefineId(flowDefine.getId());
                //获取该项目该环节的执行情况
                List<FlowExecute> flowExecutes2 = flowExecuteMapper.findStatusDesc(flowExecute);
                if (flowExecutes2 != null && flowExecutes2.size() > 0) {
                    Integer s = flowExecutes2.get(0).getStatus();
                    if (s == 1) {
                        //已执行状态
                        flowDefine.setStatus(1);
                    } else if (s == 2) {
                        //执行中状态
                        flowDefine.setStatus(2);
                    }else if (s == 3) {
                        //当前环节结束
                        flowDefine.setStatus(3);
                    }
                } else {
                    //未执行状态
                    flowDefine.setStatus(0);
                }
            
            }
            map.put("url", list.get(0).getAdvancedUrl()+"?projectId="+projectId+"&flowDefineId="+list.get(0).getId());
            map.put("list", list);
            return map;
        }
        return null;
    }

}
