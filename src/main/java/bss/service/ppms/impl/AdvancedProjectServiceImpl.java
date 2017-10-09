package bss.service.ppms.impl;

import iss.dao.ps.ArticleMapper;
import iss.dao.ps.ArticleTypeMapper;
import iss.model.ps.Article;
import iss.model.ps.ArticleCategory;
import iss.model.ps.ArticleType;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.bms.DictionaryDataMapper;
import ses.dao.bms.UserMapper;
import ses.dao.ems.ProjectExtractMapper;
import ses.dao.oms.PurchaseInfoMapper;
import ses.dao.sms.QuoteMapper;
import ses.model.bms.DictionaryData;
import ses.model.bms.User;
import ses.model.ems.ProjectExtract;
import ses.model.oms.PurchaseDep;
import ses.model.oms.PurchaseInfo;
import ses.model.sms.Quote;
import ses.util.DictionaryDataUtil;
import ses.util.PropUtil;
import ses.util.PropertiesUtil;
import ses.util.WfUtil;

import com.github.pagehelper.PageHelper;
import common.constant.Constant;
import common.constant.StaticVariables;
import common.model.UploadFile;
import common.service.UploadService;

import bss.dao.pms.PurchaseDetailMapper;
import bss.dao.ppms.AdvancedDetailMapper;
import bss.dao.ppms.AdvancedPackageMapper;
import bss.dao.ppms.AdvancedProjectMapper;
import bss.dao.ppms.BidMethodMapper;
import bss.dao.ppms.FlowDefineMapper;
import bss.dao.ppms.FlowExecuteMapper;
import bss.dao.ppms.MarkTermMapper;
import bss.dao.ppms.NegotiationMapper;
import bss.dao.ppms.NegotiationReportMapper;
import bss.dao.ppms.ParamIntervalMapper;
import bss.dao.ppms.ProjectDetailMapper;
import bss.dao.ppms.ProjectMapper;
import bss.dao.ppms.ProjectTaskMapper;
import bss.dao.ppms.SaleTenderMapper;
import bss.dao.ppms.ScoreModelMapper;
import bss.dao.ppms.SupplierCheckPassMapper;
import bss.dao.ppms.TaskMapper;
import bss.dao.ppms.theSubjectMapper;
import bss.dao.prms.ExpertScoreMapper;
import bss.dao.prms.FirstAuditMapper;
import bss.dao.prms.PackageExpertMapper;
import bss.dao.prms.PackageFirstAuditMapper;
import bss.dao.prms.ReviewFirstAuditMapper;
import bss.dao.prms.ReviewProgressMapper;
import bss.model.pms.PurchaseDetail;
import bss.model.ppms.AdvancedDetail;
import bss.model.ppms.AdvancedPackages;
import bss.model.ppms.AdvancedProject;
import bss.model.ppms.BidMethod;
import bss.model.ppms.FlowDefine;
import bss.model.ppms.FlowExecute;
import bss.model.ppms.MarkTerm;
import bss.model.ppms.Negotiation;
import bss.model.ppms.NegotiationReport;
import bss.model.ppms.Packages;
import bss.model.ppms.ParamInterval;
import bss.model.ppms.Project;
import bss.model.ppms.ProjectDetail;
import bss.model.ppms.ProjectTask;
import bss.model.ppms.SaleTender;
import bss.model.ppms.ScoreModel;
import bss.model.ppms.SupplierCheckPass;
import bss.model.ppms.Task;
import bss.model.ppms.theSubject;
import bss.model.prms.ExpertScore;
import bss.model.prms.FirstAudit;
import bss.model.prms.PackageExpert;
import bss.model.prms.PackageFirstAudit;
import bss.model.prms.ReviewFirstAudit;
import bss.model.prms.ReviewProgress;
import bss.service.ppms.AdvancedProjectService;
import bss.service.ppms.PackageService;

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
    
    @Autowired
    private ProjectTaskMapper projectTaskMapper;
    
    @Autowired
    private TaskMapper taskMapper;
    
    @Autowired
    private ProjectMapper projectMapper;
    
    @Autowired
    private PurchaseDetailMapper purchaseDetailMapper;
    
    @Autowired
    private AdvancedDetailMapper detailMapper;
    
    @Autowired
    private ProjectDetailMapper projectDetailMapper;
    
    @Autowired
    private FirstAuditMapper firstAuditMapper;
    
    @Autowired
    private AdvancedPackageMapper advancedPackageMapper;
    
    @Autowired
    private PackageService packageService;
    
    @Autowired
    private PackageFirstAuditMapper packageFirstAuditMapper;
    
    @Autowired
    private BidMethodMapper bidMethodMapper;
    
    @Autowired
    private MarkTermMapper markTermMapper;
    
    @Autowired
    private ScoreModelMapper scoreModelMapper;
    
    @Autowired
    private ParamIntervalMapper paramIntervalMapper;
    
    @Autowired
    private ProjectExtractMapper projectExtractMapper;
    
    @Autowired
    private PackageExpertMapper packageExpertMapper;
    
    @Autowired
    private ReviewProgressMapper reviewProgressMapper;
    
    @Autowired
    private ReviewFirstAuditMapper reviewFirstAuditMapper;
    
    @Autowired
    private SupplierCheckPassMapper supplierCheckPassMapper;
    
    @Autowired
    private theSubjectMapper subjectMapper;
    
    @Autowired
    private NegotiationMapper negotiationMapper;
    
    @Autowired
    private NegotiationReportMapper negotiationReportMapper;
    
    @Autowired
    private ExpertScoreMapper expertScoreMapper;

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
                if(files != null && !files.isEmpty()){
                	if (project.getConfirmFile() == 3) {
              		  	jsonObj.put("success", true);
              	  	} else {
              	  		jsonObj.put("success", false);
                        jsonObj.put("msg", "请审核采购文件");
              	  	}
                } else {
                    jsonObj.put("success", false);
                    jsonObj.put("msg", "请上传采购文件");
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
    
    @Override
    public void quote(List<AdvancedDetail> list, String taskId) {
        HashSet<String> set = new HashSet<>();
        for (AdvancedDetail advancedDetail : list) {
            set.add(advancedDetail.getAdvancedProject());
        }
        //合并任务,获取预研父项目ID
        String projectIds = saveTask(list.get(0).getAdvancedProject(), taskId);
        
        //添加正式项目
        if(StringUtils.isNotBlank(projectIds)){
            //正式项目父ID
            String saveProject = saveProject(projectIds, null);
            
            //添加中间表
            ProjectTask projectTask2 = new ProjectTask();
            projectTask2.setProjectId(saveProject);
            projectTask2.setTaskId(taskId);
            projectTaskMapper.insertSelective(projectTask2);
            
            //正式父项目添加明细
            saveDetail(projectIds, saveProject, null);
            HashMap<String, String> map = new HashMap<>();
            for (String string : set) {
                //子项目ID
                String projectId = saveProject(string, saveProject);
                
                //添加分包信息
                savePackage(string, projectId, map);
                
                //正式子项目添加明细
                saveDetail(string, projectId, map);
                
                //拟制采购文件
                HashMap<String, String> firstAuditMap = save_nzcgwj(string, projectId, map);
                
                //发布公告
                savePurchaseArticle(string, projectId, map);
                
                //供应商抽取，发售标书,供应商签到
                saveSaleTender(string, projectId, map);
                
                //开标唱标
                saveQuote(string, projectId, map);
                
                //专家评审
                saveExpertsReview(string, projectId, map, firstAuditMap);
                
                //单一来源编制谈判记录
                saveNegotiation(string, projectId, map);
                
                //单一来源谈判报告
                saveNegotiationReport(string, projectId, map);
                
                //中标公示
                saveWinArticle(string, projectId, map);
                
                //确认供应商
                saveSuppliers(string, projectId, map);
            }
        }
    }

    
    private void saveNegotiationReport(String string, String projectId, HashMap<String, String> map) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("projectId", string);
        List<NegotiationReport> list = negotiationReportMapper.listByNegotiation(hashMap);
        if(list != null && !list.isEmpty()){
            NegotiationReport negotiation = null;
            for (NegotiationReport negotiationReport : list) {
                negotiation = new NegotiationReport();
                negotiation.setId(WfUtil.createUUID());
                negotiation.setProjectId(projectId);
                negotiation.setReviewSite(negotiationReport.getReviewSite());
                negotiation.setReviewTime(new Date());
                negotiation.setTalks(negotiationReport.getTalks());
                negotiation.setPackageId(map.get(negotiationReport.getPackageId()));
                negotiationReportMapper.insertSelective(negotiation);
            }
        }
    }

    private void saveNegotiation(String string, String projectId, HashMap<String, String> map) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("projectId", string);
        List<Negotiation> list = negotiationMapper.listByNegotiation(hashMap);
        if(list != null && !list.isEmpty()){
            Negotiation negotiation = null;
            for (Negotiation negotiation2 : list) {
                negotiation = new Negotiation();
                negotiation.setId(WfUtil.createUUID());
                negotiation.setCreatedAt(new Date());
                negotiation.setNegotiationRecord(negotiation2.getNegotiationRecord());
                negotiation.setNuter(negotiation2.getNuter());
                negotiation.setPackageId(map.get(negotiation2.getPackageId()));
                negotiation.setProjectId(negotiation2.getProjectId());
                negotiationMapper.insertSelective(negotiation);
            }
        }
    }

    private void saveSuppliers(String string, String projectId, HashMap<String, String> map) {
        Iterator<Entry<String, String>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Entry<String, String> next = iterator.next();
            String newId=next.getValue();
            String oldId=next.getKey();
            List<SupplierCheckPass> supplierCheckPasss = supplierCheckPassMapper.getByCheck(oldId);
            for(SupplierCheckPass scp:supplierCheckPasss){
                scp.setId(WfUtil.createUUID());
                scp.setPackageId(newId);
                scp.setProjectId(projectId);
                supplierCheckPassMapper.insertSelective(scp);
            }
            List<theSubject> theSubjects = subjectMapper.selectByPackagesId(oldId);
            for (theSubject ts : theSubjects) {
                ts.setDetailId(map.get(ts.getDetailId()));
                ts.setPackageId(newId);
                subjectMapper.insertSelective(ts);
            }
        }
      
    }

    private void saveWinArticle(String string, String projectId, HashMap<String, String> map) {
        AdvancedProject project = advancedProjectMapper.selectAdvancedProjectByPrimaryKey(string);
        if(project != null && StringUtils.isNotBlank(project.getPurchaseType())){
            DictionaryData data = DictionaryDataUtil.findById(project.getPurchaseType());
            Article article = new Article();
            if("GKZB".equals(data.getCode()) || "XJCG".equals(data.getCode()) || "YQZB".equals(data.getCode()) || "JZXTP".equals(data.getCode())){
                ArticleType at = articleTypeMapper.selectArticleTypeByCode("success_notice");
                article.setArticleType(new ArticleType(at.getId()));
            }
            article.setProjectId(string);
            List<Article> articles = articleMapper.selectArticleByProjectId(article);
            if(articles != null && !articles.isEmpty()){
                String oldArtId = articles.get(0).getId();
                Article oldArt = articles.get(0);
                oldArt.setId(WfUtil.createUUID());
                oldArt.setProjectId(projectId);
                oldArt.setCreatedAt(new Date());
                oldArt.setUpdatedAt(new Date());
                articleMapper.insertSelective(oldArt);
                ArticleCategory articleCategory = new ArticleCategory();
                articleCategory.setArticleId(oldArtId);
                List<ArticleCategory> artC = articleMapper.findArtCategory(articleCategory);
                if(artC!=null&&artC.size()>0){
                    ArticleCategory ac = artC.get(0);
                    ac.setArticleId(oldArt.getId());
                    articleMapper.saveArtCategory(ac);
                }
            }
        }
    
    }

    private void saveExpertsReview(String string, String projectId, HashMap<String, String> map, HashMap<String, String> firstAuditMap) {
        Map<String, Object> hMap = new HashMap<String, Object>();
        hMap.put("projectId", string);
        List<PackageExpert> selectLists = packageExpertMapper.selectList(hMap);
        if(selectLists != null && !selectLists.isEmpty()){
            for (PackageExpert packageExpert : selectLists) {
                packageExpert.setProjectId(projectId);
                packageExpert.setPackageId(map.get(packageExpert.getPackageId()));
                packageExpertMapper.insertSelective(packageExpert);
            }
        }
        
        //符合性和资格性检查
        Map<String, Object> map2 = new HashMap<String, Object>();
        map2.put("projectId", string);
        List<ReviewProgress> progresses = reviewProgressMapper.selectByMap(map2);
        if(progresses != null && !progresses.isEmpty()){
            for (ReviewProgress reviewProgress : progresses) {
                reviewProgress.setId(WfUtil.createUUID());
                reviewProgress.setProjectId(projectId);
                reviewProgress.setPackageId(map.get(reviewProgress.getPackageId()));
                reviewProgressMapper.insertSelective(reviewProgress);
            }
        }
        
        //检查项
        Map<String, Object> rfaMap = new HashMap<String, Object>();
        rfaMap.put("projectId", string);
        List<ReviewFirstAudit> reviewFirstAudit = reviewFirstAuditMapper.selectList(rfaMap);
        for (ReviewFirstAudit firstAudit : reviewFirstAudit) {
            firstAudit.setProjectId(projectId);
            firstAudit.setPackageId(map.get(firstAudit.getPackageId()));
            firstAudit.setFirstAuditId(firstAuditMap.get(firstAudit.getFirstAuditId()));
            reviewFirstAuditMapper.insertSelective(firstAudit);
        }
        
        //专家打分项
        Map<String, Object> map3 = new HashMap<String, Object>();
        map3.put("projectId", string);
        List<ExpertScore> selectByMap = expertScoreMapper.selectByMap(map3);
        if(selectByMap != null && !selectByMap.isEmpty()){
            for (ExpertScore expertScore : selectByMap) {
                expertScore.setId(WfUtil.createUUID());
                expertScore.setProjectId(projectId);
                expertScore.setPackageId(map.get(expertScore.getPackageId()));
                expertScore.setScoreModelId(map.get(expertScore.getScoreModelId()));
                expertScoreMapper.insertSelective(expertScore);
            }
        }
    }

    private void saveQuote(String string, String projectId, HashMap<String, String> map) {
        Iterator<Entry<String, String>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Entry<String, String> next = iterator.next();
            String newPackageId = next.getValue();
            String oldPackageId = next.getKey();
            List<ProjectExtract> projectExtracts = projectExtractMapper.getById(oldPackageId);
            if(projectExtracts != null && !projectExtracts.isEmpty()){
                ProjectExtract extract = null;
                for (ProjectExtract projectExtract : projectExtracts) {
                    extract = new ProjectExtract();
                    extract.setId(WfUtil.createUUID());
                    extract.setProjectId(newPackageId);
                    extract.setExpertId(projectExtract.getExpert().getId());
                    extract.setCreatedAt(projectExtract.getCreatedAt());
                    if(projectExtract.getOperatingType() != null){
                        extract.setOperatingType(projectExtract.getOperatingType());
                    }
                    extract.setIsDeleted(projectExtract.getIsDeleted());
                    if(projectExtract.getStatusCount() != null){
                        extract.setStatusCount(projectExtract.getStatusCount());
                    }
                    if(projectExtract.getIsProvisional() != null){
                        extract.setIsProvisional(projectExtract.getIsProvisional());
                    }
                    if(StringUtils.isNotBlank(projectExtract.getReviewType())){
                        extract.setReviewType(projectExtract.getReviewType());
                    }
                    projectExtractMapper.insertSelective(extract);
                }
            }
            Quote quote = new Quote();
            quote.setProjectId(string);
            quote.setPackageId(oldPackageId);
            List<Quote> selectQuoteHistory = quoteMapper.selectQuoteHistory(quote);
            if(selectQuoteHistory != null && !selectQuoteHistory.isEmpty()){
                Quote quote2 = null;
                for(Quote qt : selectQuoteHistory){
                    quote2 = new Quote();
                    quote2.setId(WfUtil.createUUID());
                    quote2.setProjectId(projectId);
                    quote2.setPackageId(newPackageId);
                    quote2.setCreatedAt(new Timestamp(new Date().getTime()));
                    if(qt.getQuotePrice() != null){
                        quote2.setQuotePrice(qt.getQuotePrice());
                    }
                    if(qt.getSupplier() != null && StringUtils.isNotBlank(qt.getSupplier().getId())){
                        quote2.setSupplierId(qt.getSupplier().getId());
                    }
                    if(qt.getTotal() != null){
                        quote2.setTotal(qt.getTotal());
                    }
                    if(StringUtils.isNotBlank(qt.getDeliveryTime())){
                        quote2.setDeliveryTime(qt.getDeliveryTime());
                    }
                    quoteMapper.insertSelective(quote2);
                }
            }
        }
    }

    private void saveSaleTender(String string, String projectId, HashMap<String, String> map) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("projectId", string);
        List<SaleTender> saleTenders = saleTenderMapper.getAdPackegeSuppliers(hashMap);
        if(saleTenders != null && !saleTenders.isEmpty()){
            SaleTender saleTender = null;
            for (SaleTender tender : saleTenders) {
                saleTender = new SaleTender();
                saleTender.setId(WfUtil.createUUID());
                saleTender.setCreatedAt(new Date());
                saleTender.setUpdatedAt(null);
                saleTender.setProjectId(projectId);
                saleTender.setPackages(map.get(tender.getPackages()));
                saleTender.setSupplierId(tender.getSupplierId());
                saleTender.setUserId(tender.getUserId());
                if(tender.getStatusBid() != null){
                    saleTender.setStatusBid(tender.getStatusBid());
                }
                if(tender.getStatusBond() != null){
                    saleTender.setStatusBond(tender.getStatusBond());
                }
                if(tender.getBidFinish() != null){
                    saleTender.setBidFinish(tender.getBidFinish());
                }
                if(tender.getIsFirstPass() != null){
                    saleTender.setIsFirstPass(tender.getIsFirstPass());
                }
                if(tender.getEconomicScore() != null){
                    saleTender.setEconomicScore(tender.getEconomicScore());
                }
                if(tender.getTechnologyScore() != null){
                    saleTender.setTechnologyScore(tender.getTechnologyScore());
                }
                if(StringUtils.isNotBlank(tender.getReviewResult())){
                    saleTender.setReviewResult(tender.getReviewResult());
                }
                if(StringUtils.isNotBlank(tender.getIsRemoved())){
                    saleTender.setIsRemoved(tender.getIsRemoved());
                }
                if(StringUtils.isNotBlank(tender.getRemovedReason())){
                    saleTender.setRemovedReason(tender.getRemovedReason());
                }
                if(tender.getIsTurnUp() != null){
                    saleTender.setIsTurnUp(tender.getIsTurnUp());
                }
                saleTenderMapper.insertSelective(saleTender);
            }
        }
        
    }

    private void savePurchaseArticle(String string, String projectId, HashMap<String, String> map) {
        AdvancedProject project = advancedProjectMapper.selectAdvancedProjectByPrimaryKey(string);
        if(project != null && StringUtils.isNotBlank(project.getPurchaseType())){
            DictionaryData data = DictionaryDataUtil.findById(project.getPurchaseType());
            Article article = new Article();
            if("GKZB".equals(data.getCode()) || "XJCG".equals(data.getCode()) || "YQZB".equals(data.getCode()) || "JZXTP".equals(data.getCode())){
                ArticleType at = articleTypeMapper.selectArticleTypeByCode("purchase_notice");
                article.setArticleType(new ArticleType(at.getId()));
            }else if("DYLY".equals(data.getCode())){
                ArticleType at = articleTypeMapper.selectArticleTypeByCode("single_source_notice");
                article.setArticleType(new ArticleType(at.getId()));
            }
            article.setProjectId(string);
            List<Article> articles = articleMapper.selectArticleByProjectId(article);
            if(articles != null && !articles.isEmpty()){
                String oldArtId = articles.get(0).getId();
                Article oldArt = articles.get(0);
                oldArt.setId(WfUtil.createUUID());
                oldArt.setProjectId(projectId);
                oldArt.setCreatedAt(new Date());
                oldArt.setUpdatedAt(new Date());
                articleMapper.insertSelective(oldArt);
                ArticleCategory articleCategory = new ArticleCategory();
                articleCategory.setArticleId(oldArtId);
                List<ArticleCategory> artC = articleMapper.findArtCategory(articleCategory);
                if(artC!=null&&artC.size()>0){
                    ArticleCategory ac = artC.get(0);
                    ac.setArticleId(oldArt.getId());
                    articleMapper.saveArtCategory(ac);
                }
            }
        }
    }

    public String saveTask(String projectId, String taskId) {
        if(StringUtils.isNotBlank(taskId) && StringUtils.isNotBlank(projectId)){
            //查询父项目
            AdvancedProject project = advancedProjectMapper.selectAdvancedProjectByPrimaryKey(projectId);
            if(project != null && StringUtils.isNotBlank(project.getParentId())){
                AdvancedProject parentProject = advancedProjectMapper.selectAdvancedProjectByPrimaryKey(project.getParentId());
                HashMap<String, Object> map2 = new HashMap<>();
                map2.put("projectId", parentProject.getId());
                List<ProjectTask> projectTask = projectTaskMapper.queryByNo(map2);
                if(projectTask != null && !projectTask.isEmpty()){
                    Task task2 = taskMapper.selectByPrimaryKey(projectTask.get(0).getTaskId());
                    Task task = taskMapper.selectByPrimaryKey(taskId);
                    task.setStatus(task2.getStatus());
                    task.setUserId(task2.getUserId());
                    task.setNotDetail(1);
                    task.setAcceptTime(new Date());
                    taskMapper.updateByPrimaryKeySelective(task);
                    taskMapper.softDelete(task2.getId());
                }
                return parentProject.getId();
            }
        }
        return null;
    }

    
    public String saveProject(String projectIds, String projectId) {
        AdvancedProject project = advancedProjectMapper.selectAdvancedProjectByPrimaryKey(projectIds);
        //添加正式项目
        Project newProject = new Project();
        if(StringUtils.isNotBlank(project.getName())){
            newProject.setName(project.getName());
        }
        if(StringUtils.isNotBlank(project.getProjectNumber())){
            newProject.setProjectNumber(project.getProjectNumber());
        }
        if(StringUtils.isNotBlank(project.getPrincipal())){
            newProject.setPrincipal(project.getPrincipal());
        }
        if(StringUtils.isNotBlank(project.getIpone())){
            newProject.setIpone(project.getIpone());
        }
        if(StringUtils.isNotBlank(project.getPurchaseType())){
            newProject.setPurchaseType(project.getPurchaseType());
        }
        if(StringUtils.isNotBlank(project.getPurchaseDepId())){
            newProject.setPurchaseDep(new PurchaseDep(project.getPurchaseDepId()));
        }
        if(StringUtils.isNotBlank(project.getPlanType())){
            newProject.setPlanType(project.getPlanType());
        }
        if(StringUtils.isNotBlank(project.getStatus())){
            newProject.setStatus(project.getStatus());
        }
        if(StringUtils.isNotBlank(project.getAppointMan())){
            newProject.setAppointMan(project.getAppointMan());
        }
        if(StringUtils.isNotBlank(project.getBidAddress())){
            newProject.setBidAddress(project.getBidAddress());
        }
        if(project.getSupplierNumber() != null){
            newProject.setSupplierNumber(project.getSupplierNumber());
        }
        if(StringUtils.isNotBlank(projectId)) {
            newProject.setParentId(projectId);
        } else {
            newProject.setParentId(project.getParentId());
        }
        if(project.getIsImport() != null){
            newProject.setIsImport(project.getIsImport());
        }
        newProject.setIsRehearse(0);
        if(project.getIsProvisional() != null){
            newProject.setIsProvisional(project.getIsProvisional());
        }
        if(project.getStartTime() != null){
            newProject.setStartTime(project.getStartTime());
        }
        if(project.getDeadline() != null){
            newProject.setDeadline(project.getDeadline());
        }
        if(project.getBidDate() != null){
            newProject.setBidDate(project.getBidDate());
        }
        if(project.getConfirmFile() != null){
            newProject.setConfirmFile(project.getConfirmFile());
        }
        newProject.setCreateAt(new Date());
        projectMapper.insertSelective(newProject);
        return newProject.getId();
    }
    

    @Override
    public Boolean reflect(PurchaseDetail detail, AdvancedDetail advancedDetail) {
        if(!advancedDetail.getGoodsName().equals(detail.getGoodsName())){
            return false;
        }
        if(advancedDetail.getStand() != null && detail.getStand() != null){
            if(!advancedDetail.getStand().equals(detail.getStand())){
                return false;
            }
        }
        if(advancedDetail.getStand() == null && detail.getStand() != null){
            return false;
        }
        if(advancedDetail.getStand() != null && detail.getStand() == null){
            return false;
        }
        
        if(advancedDetail.getQualitStand() != null && detail.getQualitStand() != null){
            if(!advancedDetail.getQualitStand().equals(detail.getQualitStand())){
                return false;
            }
        }
        if(advancedDetail.getQualitStand() == null && detail.getQualitStand() != null){
            return false;
        }
        if(advancedDetail.getQualitStand() != null && detail.getQualitStand() == null){
            return false;
        }
        if(advancedDetail.getItem() != null && detail.getItem() != null){
            if(!advancedDetail.getItem().equals(detail.getItem())){
                return false;
            }
        }
        if(advancedDetail.getItem() == null && detail.getItem() != null){
            return false;
        }
        if(advancedDetail.getItem() != null && detail.getItem() == null){
            return false;
        }
        
        if(!advancedDetail.getPurchaseCount().equals(detail.getPurchaseCount())){
            return false;
        }
        if(!advancedDetail.getPrice().equals(detail.getPrice())){
            return false;
        }
        if(!advancedDetail.getBudget().equals(detail.getBudget())){
            return false;
        }
        if(advancedDetail.getDeliverDate() != null && detail.getDeliverDate() != null){
            if(!advancedDetail.getDeliverDate().equals(detail.getDeliverDate())){
                return false;
            }
        }
        if(advancedDetail.getDeliverDate() == null && detail.getDeliverDate() != null){
            return false;
        }
        if(advancedDetail.getDeliverDate() != null && detail.getDeliverDate() == null){
            return false;
        }
        
        if(!advancedDetail.getPurchaseType().equals(detail.getPurchaseType())){
            return false;
        }
        if(!advancedDetail.getOrganization().equals(detail.getOrganization())){
            return false;
        }
        if(advancedDetail.getSupplier() != null && detail.getSupplier() != null){
            if(!advancedDetail.getSupplier().equals(detail.getSupplier())){
                return false;
            }
        }
        if(advancedDetail.getSupplier() != null && detail.getSupplier() == null){
            return false;
        }
        if(detail.getSupplier() != null && advancedDetail.getSupplier() == null){
            return false;
        }
        if(advancedDetail.getIsFreeTax() != null && detail.getIsFreeTax() != null){
            if(!advancedDetail.getIsFreeTax().equals(detail.getIsFreeTax())){
                return false;
            }
        }
        if(advancedDetail.getIsFreeTax() == null && detail.getIsFreeTax() != null){
            return false;
        }
        if(advancedDetail.getIsFreeTax() != null && detail.getIsFreeTax() == null){
            return false;
        }
        
        if(advancedDetail.getGoodsUse() != null && detail.getGoodsUse() != null){
            if(!advancedDetail.getGoodsUse().equals(detail.getGoodsUse())){
                return false;
            }
        }
        if(advancedDetail.getGoodsUse() == null && detail.getGoodsUse() != null){
            return false;
        }
        if(advancedDetail.getGoodsUse() != null && detail.getGoodsUse() == null){
            return false;
        }
        if(advancedDetail.getUseUnit() != null && detail.getUserUnit() != null){
            if(!advancedDetail.getUseUnit().equals(detail.getUserUnit())){
                return false;
            }
        }
        if(advancedDetail.getUseUnit() == null && detail.getUserUnit() != null){
            return false;
        }
        if(advancedDetail.getUseUnit() != null && detail.getUserUnit() == null){
            return false;
        }
        return true;
    }

    @Override
    public List<PurchaseDetail> purchaseDetail(String collectId, User user) {
        List<PurchaseDetail> detail = new ArrayList<PurchaseDetail>();
        List<PurchaseDetail> uinuqeId = purchaseDetailMapper.getByUinuqeId(collectId,user.getOrg().getId(),null);
        if(uinuqeId != null && !uinuqeId.isEmpty()){
            for (PurchaseDetail purchaseRequired : uinuqeId) {
                if(purchaseRequired.getPrice() != null){
                    detail.add(purchaseRequired);
                }
            }
        }
        return detail;
    }

    @Override
    public List<AdvancedDetail> advancedDetail(List<PurchaseDetail> purchaseDetail) {
        List<AdvancedDetail> list = new ArrayList<AdvancedDetail>();
        for (PurchaseDetail detail : purchaseDetail) {
            AdvancedDetail requiredId = detailMapper.selectByRequiredId(detail.getId());
            if(requiredId != null){
                list.add(requiredId);
            }
        }
        return list;
    }

    @Override
    public List<AdvancedDetail> ifAdvancedDetail(List<PurchaseDetail> purchaseDetail) {
        List<AdvancedDetail> list = new ArrayList<AdvancedDetail>();
        String uniqueId = null;
        for (PurchaseDetail detail : purchaseDetail) {
            AdvancedDetail requiredId = detailMapper.selectByRequiredId(detail.getId());
            if(requiredId != null && StringUtils.isNotBlank(requiredId.getUniqueId())){
                uniqueId = requiredId.getUniqueId();
                break;
            }
        }
        if(uniqueId != null){
            HashMap<String, Object> map = new HashMap<>();
            map.put("uniqueId", uniqueId);
            List<AdvancedDetail> selectByAll = detailMapper.selectByAll(map);
            if(selectByAll != null && !selectByAll.isEmpty()){
                for (AdvancedDetail advancedDetail : selectByAll) {
                    if(advancedDetail != null && advancedDetail.getPrice() != null){
                        list.add(advancedDetail);
                    }
                }
                
            }
        }
        return list;
    }
    
    public void saveDetail(String projectIds, String projectId, HashMap<String, String> map){
        HashMap<String, Object> maps = new HashMap<>();
        maps.put("advancedProject", projectIds);
        List<AdvancedDetail> list = detailMapper.selectByAll(maps);
        if(list != null && !list.isEmpty()){
            ProjectDetail projectDetail = null;
            for (AdvancedDetail advancedDetail : list) {
                projectDetail = new ProjectDetail();
                if(StringUtils.isNotBlank(advancedDetail.getRequiredId())){
                    projectDetail.setRequiredId(advancedDetail.getRequiredId());
                }
                if(StringUtils.isNotBlank(advancedDetail.getSerialNumber())){
                    projectDetail.setSerialNumber(advancedDetail.getSerialNumber());
                }
                if(StringUtils.isNotBlank(advancedDetail.getDepartment())){
                    projectDetail.setDepartment(advancedDetail.getDepartment());
                }
                if(StringUtils.isNotBlank(advancedDetail.getGoodsName())){
                    projectDetail.setGoodsName(advancedDetail.getGoodsName());
                }
                if(StringUtils.isNotBlank(advancedDetail.getStand())){
                    projectDetail.setStand(advancedDetail.getStand());
                }
                if(StringUtils.isNotBlank(advancedDetail.getQualitStand())){
                    projectDetail.setQualitStand(advancedDetail.getQualitStand());
                }
                if(StringUtils.isNotBlank(advancedDetail.getItem())){
                    projectDetail.setItem(advancedDetail.getItem());
                }
                projectDetail.setCreatedAt(new Date());
                projectDetail.setProject(new Project(projectId));
                if (advancedDetail.getPurchaseCount() != null) {
                    projectDetail.setPurchaseCount(advancedDetail.getPurchaseCount().doubleValue());
                }
                if (advancedDetail.getPrice() != null) {
                    projectDetail.setPrice(advancedDetail.getPrice().doubleValue());
                }
                if (advancedDetail.getBudget() != null) {
                    projectDetail.setBudget(advancedDetail.getBudget().doubleValue());
                }
                if (StringUtils.isNotBlank(advancedDetail.getDeliverDate())) {
                    projectDetail.setDeliverDate(advancedDetail.getDeliverDate());
                }
                if (StringUtils.isNotBlank(advancedDetail.getPurchaseType())) {
                    projectDetail.setPurchaseType(advancedDetail.getPurchaseType());
                }
                if (StringUtils.isNotBlank(advancedDetail.getSupplier())) {
                    projectDetail.setSupplier(advancedDetail.getSupplier());
                }
                if (StringUtils.isNotBlank(advancedDetail.getIsFreeTax())) {
                    projectDetail.setIsFreeTax(advancedDetail.getIsFreeTax());
                }
                if (StringUtils.isNotBlank(advancedDetail.getGoodsUse())) {
                    projectDetail.setGoodsUse(advancedDetail.getGoodsUse());
                }
                if (StringUtils.isNotBlank(advancedDetail.getUseUnit())) {
                    projectDetail.setUseUnit(advancedDetail.getUseUnit());
                }
                if (StringUtils.isNotBlank(advancedDetail.getParentId())) {
                    projectDetail.setParentId(advancedDetail.getParentId());
                }
                if (StringUtils.isNotBlank(advancedDetail.getStatus())) {
                    projectDetail.setStatus(advancedDetail.getStatus());
                }
                if(StringUtils.isNotBlank(advancedDetail.getMemo())){
                    projectDetail.setMemo(advancedDetail.getMemo());
                }
                if(StringUtils.isNotBlank(advancedDetail.getPackageId())){
                    projectDetail.setPackageId(map.get(advancedDetail.getPackageId()));
                }
                if(advancedDetail.getPosition() != null){
                    projectDetail.setPosition(advancedDetail.getPosition());
                }
                projectDetailMapper.insertSelective(projectDetail);
                map.put(advancedDetail.getId(), projectDetail.getId());
            }
        }
    }
    
    public void savePackage(String projectIds, String projectId, HashMap<String, String> map){
        HashMap<String, Object> maps = new HashMap<>();
        maps.put("projectId", projectIds);
        List<AdvancedPackages> advancedPackages = advancedPackageMapper.selectByAll(maps);
        if(advancedPackages != null && !advancedPackages.isEmpty()){
            Packages package1 = null;
            for (AdvancedPackages packages : advancedPackages) {
                package1 = new Packages();
                package1.setId(WfUtil.createUUID());
                package1.setName(packages.getName());
                package1.setProjectId(projectId);
                package1.setIsDeleted(packages.getIsDeleted());
                package1.setCreatedAt(packages.getCreatedAt());
                package1.setIsImport(packages.getIsImport());
                package1.setPurchaseType(packages.getPurchaseType());
                if(StringUtils.isNotBlank(packages.getProjectStatus())){
                    package1.setProjectStatus(packages.getProjectStatus());
                }
                packageService.insertPackage(package1);
                map.put(packages.getId(), package1.getId());
            }
        }
    }
    
    private HashMap<String, String> save_nzcgwj(String projectIds, String projectId, HashMap<String, String> map){
        //资格性和符合性审查
        HashMap<String, String> firstAuditMap = saveFirstAudit(projectIds, projectId, map);
        savePackageFirstAudit(projectIds, projectId, map, firstAuditMap);
        
        //经济技术评审
        saveBidMethod(projectIds, projectId, map);
        
        //评分标准
        saveMarkTerm(projectIds, projectId, map);
        
        //八大评分模型
        saveScoreModel(projectIds, projectId, map);
        
        //评分细则
        saveParamInterval(projectIds, projectId, map);
        
        //采购文件
        String typeId = DictionaryDataUtil.getId("PROJECT_BID");
        List<UploadFile> files = uploadService.getFilesOther(projectIds, typeId, Constant.TENDER_SYS_KEY+"");
        if(files != null && files.size() > 0){
            for (UploadFile uploadFile : files) {
                uploadFile.setBusinessId(projectId);
                uploadService.updateLoad(uploadFile);
            }
        }
        
        //采购管理部门审核意见附件
        String PC_REASON = DictionaryDataUtil.getId("PC_REASON");
        List<UploadFile> filesPc = uploadService.getFilesOther(projectIds, PC_REASON, Constant.TENDER_SYS_KEY+"");
        if(filesPc != null && filesPc.size() > 0){
            for (UploadFile uploadFile : filesPc) {
                uploadFile.setBusinessId(projectId);
                uploadService.updateLoad(uploadFile);
            }
        }
        
        //事业部门审核意见附件
        String CAUSE_REASON = DictionaryDataUtil.getId("CAUSE_REASON");
        List<UploadFile> filesCause = uploadService.getFilesOther(projectIds, CAUSE_REASON, Constant.TENDER_SYS_KEY+"");
        if(filesCause != null && filesCause.size() > 0){
            for (UploadFile uploadFile : filesCause) {
                uploadFile.setBusinessId(projectId);
                uploadService.updateLoad(uploadFile);
            }
        }
        
        //财务部门审核意见附件
        String FINANCE_REASON = DictionaryDataUtil.getId("FINANCE_REASON");
        List<UploadFile> filesRe = uploadService.getFilesOther(projectIds, FINANCE_REASON, Constant.TENDER_SYS_KEY+"");
        if(filesRe != null && filesRe.size() > 0){
            for (UploadFile uploadFile : filesRe) {
                uploadFile.setBusinessId(projectId);
                uploadService.updateLoad(uploadFile);
            }
        }
        return firstAuditMap;
    }

    private void saveParamInterval(String projectIds, String projectId, HashMap<String, String> map) {
        ParamInterval paramInterval = new ParamInterval();
        paramInterval.setProjectId(projectIds);
        List<ParamInterval> intervals = paramIntervalMapper.findListByParamInterval(paramInterval);
        if(intervals != null && !intervals.isEmpty()){
            ParamInterval interval = null;
            for (ParamInterval paramInterval2 : intervals) {
                interval = new ParamInterval();
                interval.setCreatedAt(new Date());
                interval.setEndParam(paramInterval2.getEndParam());
                interval.setEndRelation(paramInterval2.getEndRelation());
                interval.setExplain(paramInterval2.getExplain());
                interval.setIsDeleted(paramInterval2.getIsDeleted());
                interval.setPackageId(map.get(paramInterval2.getPackageId()));
                interval.setProjectId(projectId);
                interval.setScore(paramInterval2.getScore());
                interval.setScoreModelId(map.get(paramInterval2.getScoreModelId()));
                interval.setStartParam(paramInterval2.getStartParam());
                interval.setStartRelation(paramInterval2.getStartRelation());
                interval.setUpdatedAt(new Date());
                paramIntervalMapper.saveParamInterval(interval);
            }
        }
        
    }

    private void saveScoreModel(String projectIds, String projectId, HashMap<String, String> map) {
        ScoreModel scoreModel = new ScoreModel();
        scoreModel.setProjectId(projectIds);
        List<ScoreModel> findListByScoreModel = scoreModelMapper.findListByScoreModel(scoreModel);
        if(findListByScoreModel != null && !findListByScoreModel.isEmpty()){
            ScoreModel mScoreModel = null;
            for (ScoreModel scoreModel2 : findListByScoreModel) {
                mScoreModel = new ScoreModel();
                mScoreModel.setProjectId(projectId);
                mScoreModel.setPackageId(map.get(scoreModel2.getPackageId()));
                if(scoreModel2.getMarkTermId() != null){
                    mScoreModel.setMarkTermId(map.get(scoreModel2.getMarkTermId()));
                }
                mScoreModel.setName(scoreModel2.getName());
                mScoreModel.setTypeName(scoreModel2.getTypeName());
                mScoreModel.setReviewContent(scoreModel2.getReviewContent());
                mScoreModel.setEasyUnderstandContent(scoreModel2.getEasyUnderstandContent());
                mScoreModel.setStandExplain(scoreModel2.getStandExplain());
                mScoreModel.setStandardScore(scoreModel2.getStandardScore());
                mScoreModel.setJudgeContent(scoreModel2.getJudgeContent());
                mScoreModel.setReviewParam(scoreModel2.getReviewParam());
                mScoreModel.setAddSubtractTypeName(scoreModel2.getAddSubtractTypeName());
                mScoreModel.setUnitScore(scoreModel2.getUnitScore());
                mScoreModel.setUnit(scoreModel2.getUnit());
                mScoreModel.setReviewStandScore(scoreModel2.getReviewStandScore());
                mScoreModel.setMaxScore(scoreModel2.getMaxScore());
                mScoreModel.setMinScore(scoreModel2.getMinScore());
                mScoreModel.setScore(scoreModel2.getScore());
                mScoreModel.setDeadlineNumber(scoreModel2.getDeadlineNumber());
                mScoreModel.setIntervalNumber(scoreModel2.getIntervalNumber());
                mScoreModel.setIsDeleted(scoreModel2.getIsDeleted());
                mScoreModel.setCreatedAt(scoreModel2.getCreatedAt());
                mScoreModel.setIntervalTypeName(scoreModel2.getIntervalTypeName());
                scoreModelMapper.saveScoreModel(mScoreModel);
                map.put(scoreModel2.getId(), mScoreModel.getId());
            }
        }
    }

    private void saveMarkTerm(String projectIds, String projectId, HashMap<String, String> map) {
        MarkTerm condition = new MarkTerm();
        condition.setProjectId(projectIds);
        List<MarkTerm> mtList = markTermMapper.findListByMarkTerm(condition);
        List<MarkTerm> markTerms = new ArrayList<MarkTerm>();
        if(mtList != null && !mtList.isEmpty()){
            markTerms.addAll(mtList);
            MarkTerm term = null;
            for (MarkTerm markTerm : mtList) {
                if("0".equals(markTerm.getPid())){
                    term = new MarkTerm();
                    term.setProjectId(projectId);
                    term.setPackageId(map.get(markTerm.getPackageId()));
                    term.setPid(markTerm.getPid());
                    term.setName(markTerm.getName());
                    term.setIsDeleted(markTerm.getIsDeleted());
                    term.setCreatedAt(new Date());
                    term.setBidMethodId(map.get(markTerm.getBidMethodId()));
                    term.setMaxScore(markTerm.getMaxScore());
                    term.setRemainScore(markTerm.getRemainScore());
                    term.setTypeName(markTerm.getTypeName());
                    markTermMapper.saveMarkTerm(term);
                    map.put(markTerm.getId(), term.getId());
                }
                
            }
        }
        if(markTerms != null && !markTerms.isEmpty()){
            MarkTerm term = null;
            for (MarkTerm markTerm : markTerms) {
                if(!"0".equals(markTerm.getPid())){
                    term = new MarkTerm();
                    term.setProjectId(projectId);
                    term.setPackageId(map.get(markTerm.getPackageId()));
                    term.setPid(map.get(markTerm.getPid()));
                    term.setName(markTerm.getName());
                    term.setIsDeleted(markTerm.getIsDeleted());
                    term.setCreatedAt(new Date());
                    term.setBidMethodId(map.get(markTerm.getBidMethodId()));
                    term.setMaxScore(markTerm.getMaxScore());
                    term.setRemainScore(markTerm.getRemainScore());
                    term.setTypeName(markTerm.getTypeName());
                    markTermMapper.saveMarkTerm(term);
                    map.put(markTerm.getId(), term.getId());
                }
            }
        }
    }

    private void saveBidMethod(String projectIds, String projectId, HashMap<String, String> map) {
        BidMethod bidMethod = new BidMethod();
        bidMethod.setProjectId(projectIds);
        List<BidMethod> listByBidMethod = bidMethodMapper.findListByBidMethod(bidMethod);
        if(listByBidMethod != null && !listByBidMethod.isEmpty()){
            BidMethod method = null;
            for (BidMethod bidMethod2 : listByBidMethod) {
                method = new BidMethod();
                method.setName(bidMethod2.getName());
                method.setTypeName(bidMethod2.getTypeName());
                method.setIsDeleted(bidMethod2.getIsDeleted());
                method.setCreatedAt(bidMethod2.getCreatedAt());
                method.setProjectId(projectId);
                method.setRemark(bidMethod2.getRemark());
                method.setRemainScore(bidMethod2.getRemainScore());
                method.setPackageId(map.get(bidMethod2.getPackageId()));
                method.setFloatingRatio(bidMethod2.getFloatingRatio());
                method.setBusiness(bidMethod2.getBusiness());
                method.setValid(bidMethod2.getValid());
                bidMethodMapper.saveBidMethod(method);
                map.put(bidMethod2.getId(), method.getId());
            }
        }
    }

    private void savePackageFirstAudit(String projectIds, String projectId, HashMap<String, String> map, HashMap<String, String> firstAuditMap) {
        HashMap<String, Object> maps = new HashMap<>();
        maps.put("projectId", projectIds);
        List<PackageFirstAudit> selectList = packageFirstAuditMapper.selectList(maps);
        if(selectList != null && !selectList.isEmpty()){
            PackageFirstAudit audit = null;
            for (PackageFirstAudit packageFirstAudit : selectList) {
                audit = new PackageFirstAudit();
                audit.setProjectId(projectId);
                audit.setPackageId(map.get(packageFirstAudit.getPackageId()));
                audit.setFirstAuditId(firstAuditMap.get(packageFirstAudit.getFirstAuditId()));
                packageFirstAuditMapper.insertSelective(audit);
            }
        }
    }

    private HashMap<String, String> saveFirstAudit(String projectIds, String projectId, HashMap<String, String> map) {
        HashMap<String, String> firstAuditMap = new HashMap<>();
        List<FirstAudit> firstAudits = firstAuditMapper.selectListByProjectId(projectIds);
        if(firstAudits != null && !firstAudits.isEmpty()){
            FirstAudit audit = null;
            for (FirstAudit firstAudit : firstAudits) {
                audit = new FirstAudit();
                audit.setId(WfUtil.createUUID());
                audit.setProjectId(projectId);
                audit.setName(firstAudit.getName());
                audit.setKind(firstAudit.getKind());
                audit.setCreatedAt(firstAudit.getCreatedAt());
                audit.setPosition(firstAudit.getPosition());
                audit.setContent(firstAudit.getContent());
                audit.setPackageId(map.get(firstAudit.getPackageId()));
                audit.setIsConfirm(firstAudit.getIsConfirm());
                firstAuditMapper.insertSelective(audit);
                firstAuditMap.put(firstAudit.getId(), audit.getId());
            }
        }
        return firstAuditMap;
    }

	@Override
	public List<AdvancedProject> selectByAudit(HashMap<String, Object> map) {
		
		return advancedProjectMapper.selectByAudit(map);
	}

	@Override
	public JSONObject getNext(String projectId, String flowDefineId) {
		JSONObject jsonObj = new JSONObject();
		//判断是否要进入开标环节
        int count = 0;
        AdvancedProject project = advancedProjectMapper.selectAdvancedProjectByPrimaryKey(projectId);
        FlowDefine fds = new FlowDefine();
        fds.setPurchaseTypeId(project.getPurchaseType());
        List<FlowDefine> find = flowDefineMapper.findList(fds);
        for (FlowDefine flowDefine : find) {
            if("KBCB".equals(flowDefine.getCode())){
                count = flowDefine.getStep();
                break;
            }
        }
        FlowDefine define = flowDefineMapper.get(flowDefineId);
        // 组织专家评审前判断开标唱标是否完成
        if(define != null && "ZZZJPS".equals(define.getCode()) && define.getStep() >= count){
            FlowExecute execute = new FlowExecute();
            execute.setProjectId(projectId);
            execute.setStep(8);
            List<FlowExecute> executes = flowExecuteMapper.findList(execute);
            StringBuffer sb = new StringBuffer();
            if(executes != null && !executes.isEmpty()){
                for (FlowExecute fe : executes){
                    sb.append(fe.getStatus() + StaticVariables.COMMA_SPLLIT);
                }
                if(!sb.toString().contains("3")){
                    FlowDefine define2 = flowDefineMapper.get(executes.get(0).getFlowDefineId());
                    jsonObj.put("name", define2.getName());
                    jsonObj.put("next", "1");
                    return jsonObj;
                }
            }
        }
        
        if(define != null && !"ZZZJPS".equals(define.getCode()) && define.getStep() >= count){
            //根据采购方式获取当前所有的环节
            FlowDefine fd = new FlowDefine();
            fd.setPurchaseTypeId(project.getPurchaseType());
            List<FlowDefine> defines = flowDefineMapper.findList(fd);
            List<FlowDefine> list = new ArrayList<FlowDefine>();
            if(defines != null && defines.size() > 0){
               //根据当前环节的步骤获取前面的环节
                for (FlowDefine flowDefine : defines) {
                    if(flowDefine.getStep() < define.getStep()){
                        if(!"CQPSZJ".equals(flowDefine.getCode()) && !"XMFB".equals(flowDefine.getCode())){
                            list.add(flowDefine);
                        }
                    }
                }
            }
            
            //获取到所有小于当前环节的流程
            if(list != null && list.size() > 0){
                for (FlowDefine flowDefine : list) {
                    FlowExecute execute = new FlowExecute();
                    execute.setProjectId(projectId);
                    execute.setFlowDefineId(flowDefine.getId());
                    List<FlowExecute> executes = flowExecuteMapper.findList(execute);
                    if(executes != null && executes.size() > 0){
                        for (int i = 0; i < executes.size(); i++ ) {
                            //判断每一个环节是否有环节结束的状态，有的话跳出循环
                            if(executes.get(i).getStatus() == 3){
                                break;
                            } else if (i == executes.size() - 1){
                                FlowDefine define2 = flowDefineMapper.get(executes.get(i).getFlowDefineId());
                                jsonObj.put("name", define2.getName());
                                jsonObj.put("next", "1");
                                return jsonObj;
                            }
                        }
                    }
                }
            }
        }
        jsonObj.put("next", "2");
		return jsonObj;
	}

}
