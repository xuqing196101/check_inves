package bss.service.ppms.impl;

import iss.dao.ps.ArticleMapper;
import iss.dao.ps.ArticleTypeMapper;
import iss.model.ps.Article;
import iss.model.ps.ArticleCategory;
import iss.model.ps.ArticleType;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import common.annotation.CurrentUser;
import common.constant.Constant;
import common.constant.StaticVariables;
import common.dao.FileUploadMapper;
import common.model.UploadFile;
import ses.dao.bms.DictionaryDataMapper;
import ses.dao.ems.ProjectExtractMapper;
import ses.dao.oms.OrgnizationMapper;
import ses.dao.sms.QuoteMapper;
import ses.model.bms.DictionaryData;
import ses.model.bms.User;
import ses.model.ems.ProjectExtract;
import ses.model.oms.Orgnization;
import ses.model.sms.Quote;
import ses.util.DictionaryDataUtil;
import ses.util.WfUtil;
import bss.dao.pms.PurchaseDetailMapper;
import bss.dao.ppms.BidMethodMapper;
import bss.dao.ppms.FlowDefineMapper;
import bss.dao.ppms.FlowExecuteMapper;
import bss.dao.ppms.MarkTermMapper;
import bss.dao.ppms.NegotiationMapper;
import bss.dao.ppms.NegotiationReportMapper;
import bss.dao.ppms.PackageMapper;
import bss.dao.ppms.ParamIntervalMapper;
import bss.dao.ppms.ProjectDetailMapper;
import bss.dao.ppms.ProjectMapper;
import bss.dao.ppms.ProjectTaskMapper;
import bss.dao.ppms.SaleTenderMapper;
import bss.dao.ppms.ScoreModelMapper;
import bss.dao.ppms.SupplierCheckPassMapper;
import bss.dao.ppms.TaskMapper;
import bss.dao.ppms.theSubjectMapper;
import bss.dao.prms.FirstAuditMapper;
import bss.dao.prms.PackageExpertMapper;
import bss.dao.prms.ReviewFirstAuditMapper;
import bss.dao.prms.ReviewProgressMapper;
import bss.model.pms.PurchaseDetail;
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
import bss.model.prms.FirstAudit;
import bss.model.prms.PackageExpert;
import bss.model.prms.ReviewFirstAudit;
import bss.model.prms.ReviewProgress;
import bss.service.ppms.TerminationService;
import bss.util.TerminationConstant;
@Service("terminationService")
public class TerminationServiceImpl implements TerminationService {
  @Autowired
  private PackageMapper packageMapper;
  @Autowired
  private ProjectDetailMapper projectDetailMapper ;
  @Autowired
  private ProjectMapper projectMapper;
  @Autowired
  private FlowDefineMapper flowDefineMapper;
  @Autowired
  private DictionaryDataMapper dictionaryDataMapper;
  @Autowired
  private FirstAuditMapper firstAuditMapper;
  @Autowired
  private BidMethodMapper bidMethodMapper;
  @Autowired
  private FileUploadMapper uploadDao;
  @Autowired
  private ArticleMapper articleMapper;
  @Autowired
  private SaleTenderMapper saleTenderMapper;
  @Autowired
  private QuoteMapper quoteMapper;
  @Autowired
  private PackageExpertMapper packageExpertMapper;
  @Autowired
  private ReviewProgressMapper reviewProgressMapper;
  @Autowired
  private ReviewFirstAuditMapper reviewFirstAuditMapper; 
  @Autowired
  private ArticleTypeMapper articleTypeMapper;
  @Autowired
  private theSubjectMapper theSubjectMapper;
  @Autowired
  private SupplierCheckPassMapper supplierCheckPassMapper;
  @Autowired
  private FlowExecuteMapper flowExecuteMapper;
  @Autowired
  private MarkTermMapper markTermMapper;
  @Autowired
  private ScoreModelMapper scoreModelMapper;
  @Autowired
  private ParamIntervalMapper paramIntervalMapper;
  @Autowired
  private NegotiationMapper negotiationMapper;
  @Autowired
  private NegotiationReportMapper negotiationReportMapper;
  @Autowired
  private TaskMapper taskMapper;
  @Autowired
  private PurchaseDetailMapper purchaseDetailMapper;
  @Autowired
  private ProjectExtractMapper  projectExtractMapper;
  
  @Autowired
  private OrgnizationMapper orgnizationMapper;
  
  @Override
  public void updateTermination(String packagesId, String projectId,String currFlowDefineId,String oldCurrFlowDefineId,String type) {
    String[] packId=packagesId.split(StaticVariables.COMMA_SPLLIT);
    Map<String, String> mapOrder=new HashMap<String, String>();
    List<String> number=new ArrayList<String>();
    HashMap<String, Object> map=new HashMap<String, Object>();
    map.put("projectId", projectId);
    List<Packages> packages = packageMapper.selectByPrimaryKey(map);
    if(packages!=null&&packages.size()>0){
      for(int i=0;i<packages.size();i++){
    	  String substring = packages.get(i).getName().substring(1, packages.get(i).getName().length()-1);
    	  if (Pattern.compile("^[0-9]*[1-9][0-9]*$").matcher(substring).matches()) {
    		  mapOrder.put(packages.get(i).getId(),substring);
    	  } else {
    		  mapOrder.put(packages.get(i).getId(),packages.get(i).getName());
    	  }
        
      }
    }
    if(packagesId!=null){
        if(packId.length>0){
          for(String id:packId){
            number.add(mapOrder.get(id));
          }
        }
       }
    //获取名称
    String title=ShortBooleanTitle(number);
    //生成项目
    Project project = insertProject(projectId, title,type,currFlowDefineId);
    updateProjectName(projectId, packId);
    Map<String, String> mapId=new HashMap<String, String>();
    if(!TerminationConstant.FLW_XMLX.equals(currFlowDefineId)){
      projectMapper.insertSelective(project);
    }
    if(TerminationConstant.FLW_XMLX.equals(currFlowDefineId)){
      if(packId.length>0){
        for(String id:packId){
          Packages pg = packageMapper.selectByPrimaryKeyId(id);
          pg.setOldFlowId(oldCurrFlowDefineId);
          pg.setNewFlowId("CGLC_CGXMLX");
          packageMapper.updateByPrimaryKeySelective(pg);
          HashMap<String, Object> projectMap=new HashMap<String, Object>();
          projectMap.put("packageId", id);
          List<ProjectDetail> pds = projectDetailMapper.selectById(projectMap);
          for(ProjectDetail pd:pds){
            Map<String, Object> hashMap=new HashMap<String, Object>();
            hashMap.put("id", pd.getRequiredId());
            List<PurchaseDetail> puds = purchaseDetailMapper.selectByParent(hashMap);
            for(PurchaseDetail pud:puds){
              pud.setProjectStatus(0);
              purchaseDetailMapper.updateByPrimaryKeySelective(pud);
            }
            if(puds!=null&&puds.size()>0){
              HashMap<String, Object> pMap=new HashMap<String, Object>();
              PurchaseDetail purchaseDetail = puds.get(0);
              pMap.put("collectId", purchaseDetail.getUniqueId());
              pMap.put("purchaseId", project.getPurchaseDepId());
              List<Task> tasks = taskMapper.listBycollect(pMap);
              if(tasks!=null&&tasks.size()>0){
                Task task = tasks.get(0);
                if(task!=null){
                  task.setNotDetail(0);
                  taskMapper.updateByPrimaryKeySelective(task);
                }
              }
            }
          }
        }
      }
    }else if(TerminationConstant.FLW_XMFB.equals(currFlowDefineId)){
      if(packId.length>0){
        Set<ProjectDetail> set=new HashSet<ProjectDetail>();
        Project pr = projectMapper.selectProjectByPrimaryKey(projectId);
        Project oldProject = projectMapper.selectProjectByPrimaryKey(pr.getParentId());
        List<ProjectDetail> oldPd = projectDetailMapper.selectNotEmptyPackageOfDetail(oldProject.getId());
        for(String id:packId){
          Packages pg = packageMapper.selectByPrimaryKeyId(id);
          pg.setOldFlowId(oldCurrFlowDefineId);
          pg.setNewFlowId("CGLC_CGXMFB");
          pg.setProjectStatus("F0EAF1136F7E4E8A8BDA6561AE8B4390");
          packageMapper.updateByPrimaryKeySelective(pg);
          List<ProjectDetail> pds = projectDetailMapper.selectByPackageRecursively(id);
          for(ProjectDetail pd:pds){
            List<ProjectDetail> pdss = projectDetailMapper.selectByRequiredIdTree(pd.getRequiredId());
            set.addAll(pdss);
          }
        }
        Iterator<ProjectDetail> iterator = set.iterator();
        while(iterator.hasNext()){
          ProjectDetail next = iterator.next();
          for (ProjectDetail pde : oldPd) {
            if(next.getId().equals(pde.getId())){
              next.setPackageId(null);
              next.setProject(project);
              next.setCreatedAt(new Date());
              next.setUpdateAt(null);
              projectDetailMapper.insertSelective(next);
            }
          }
          if(next.getPackageId()!=null&&!"".equals(next.getPackageId())){
            next.setPackageId(null);
            next.setProject(project);
            next.setCreatedAt(new Date());
            next.setUpdateAt(null);
            projectDetailMapper.insertSelective(next);
          }
        }
      }
      
    }else{
    //生成包
      insertPackages(packagesId, project, mapId,currFlowDefineId,oldCurrFlowDefineId,projectId,type);
      //生成明细
      insertDetail(packagesId,projectId, project, mapId);
      //获取当前流程以前的所有步骤并复制一份
      FlowDefine define=new FlowDefine();
      List<FlowDefine> flowDefines=null;
      if(type!=null){
        FlowDefine flowDefine = flowDefineMapper.get(currFlowDefineId);
        HashMap<String, Object> hashMap=new HashMap<String, Object>();
        hashMap.put("code", flowDefine.getCode());
        hashMap.put("id", "3CF3C643AE0A4499ADB15473106A7B80");
        flowDefines=flowDefineMapper.getJzxtp(hashMap);
      }else{
        define.setId(currFlowDefineId);
        define.setUrl("gt");
        flowDefines = flowDefineMapper.getFlow(define);
      }
      Map<String, Map<String, Object>> IsTurnUpMap=new HashMap<String, Map<String, Object>>();
      Map<String, String> firstAuditIdMap=new HashMap<String, String>();
      for(FlowDefine flw:flowDefines){
        if(type!=null){
          FlowDefine define1=new FlowDefine();
          define1.setPurchaseTypeId(project.getPurchaseType());
          define1.setCode(flw.getCode());
          List<FlowDefine> findList = flowDefineMapper.findList(define1);
          if(findList!=null&&findList.size()>0){
            FlowExecute temp=new FlowExecute();
            temp.setFlowDefineId(findList.get(0).getId());
            temp.setProjectId(projectId);
            List<FlowExecute> findExecuteds = flowExecuteMapper.findExecutedByProjectIdAndFlowId(temp);
            if(findExecuteds!=null&&findExecuteds.size()>0){
              FlowExecute flowExecute = findExecuteds.get(0);
              flowExecute.setId(WfUtil.createUUID());
              flowExecute.setFlowDefineId(flw.getId());
              flowExecute.setProjectId(project.getId());
              flowExecuteMapper.insert(flowExecute);
            }
          }
        }
        flowDefine(flw,mapId,project,projectId,IsTurnUpMap,firstAuditIdMap,type);
       }
      }
  }
  private void flowDefine(FlowDefine flw,Map<String, String> mapId,Project project,String oldProjectId,Map<String, Map<String, Object>> IsTurnUpMap,Map<String, String> firstAuditIdMap,String type){
    //判断是采购方式
    DictionaryData data = dictionaryDataMapper.selectByPrimaryKey(flw.getPurchaseTypeId());
    if(TerminationConstant.MODE_GKZB.equals(data.getCode())){//公开招标
      project_gkzb(flw, mapId, project, oldProjectId, IsTurnUpMap,
          firstAuditIdMap,type);
    }else if(TerminationConstant.MODE_XJCG.equals(data.getCode())){//询价采购
      project_xjcg(flw, mapId, project, oldProjectId, IsTurnUpMap,
          firstAuditIdMap);
    }else if(TerminationConstant.MODE_JZXTP.equals(data.getCode())){//竞争性谈判
      project_jzxtp(flw, mapId, project, oldProjectId, IsTurnUpMap,
          firstAuditIdMap,type);
    }else if(TerminationConstant.MODE_DYLY.equals(data.getCode())){//单一来源
      project_dyly(flw, mapId, project, oldProjectId, IsTurnUpMap);
    }else if(TerminationConstant.MODE_YQZB.equals(data.getCode())){//邀请招标
      project_yqzb(flw, mapId, project, oldProjectId, IsTurnUpMap,
          firstAuditIdMap);
    }
    
  }
  private void project_jzxtp(FlowDefine flw, Map<String, String> mapId,
      Project project, String oldProjectId,
      Map<String, Map<String, Object>> IsTurnUpMap,
      Map<String, String> firstAuditIdMap,String type) {
    if(flw.getCode().equals("XMXX")){//项目信息
    }else if(flw.getCode().equals("NZCGWJ")){//拟制竞谈文件
      flw_nzcgwj(mapId, project, oldProjectId,firstAuditIdMap);
    }else if(flw.getCode().equals("NZCGGG")){//发布竞谈公告
      flw_nzcggg(project, oldProjectId);
    }else if(flw.getCode().equals("CQGYS")){//抽取供应商
      flw_fsbs(mapId, project, oldProjectId,IsTurnUpMap,"JZXTP",type);
    }else if(flw.getCode().equals("FSBS")){//发售标书
      flw_gysqd(IsTurnUpMap,"JZXTP","one",null);
    }else if(flw.getCode().equals("CQPSZJ")){//抽取评审专家
    }else if(flw.getCode().equals("GYSQD")){//供应商签到
      flw_gysqd(IsTurnUpMap,"JZXTP","two",null);
      IsTurnUpMap=null;
    }else if(flw.getCode().equals("KBCB")){//开标唱标
      flw_kbcb(mapId, project, oldProjectId);
    }else if(flw.getCode().equals("ZZZJPS")){//组织专家评审
      flw_zzzjps(mapId, project, oldProjectId, firstAuditIdMap);
    }else if(flw.getCode().equals("NZZBGS")){//发布成交公示
      flw_nzzbgs(project, oldProjectId);
    }else if(flw.getCode().equals("QRZBGYS")){//确定成交供应商
      flw_qrzbgys(mapId, project);
    }
  }
  private void project_yqzb(FlowDefine flw, Map<String, String> mapId,
      Project project, String oldProjectId,
      Map<String, Map<String, Object>> IsTurnUpMap,
      Map<String, String> firstAuditIdMap) {
    if(flw.getCode().equals("XMXX")){//项目信息
      
    }else if(flw.getCode().equals("NZCGWJ")){//拟制招标文件
      flw_nzcgwj(mapId, project, oldProjectId,firstAuditIdMap);
    }else if(flw.getCode().equals("NZCGGG")){//拟制招标公告
      flw_nzcggg(project, oldProjectId);
    }else if(flw.getCode().equals("CQGYS")){//抽取供应商
      flw_fsbs(mapId, project, oldProjectId,IsTurnUpMap,"YQZB",null);
    }else if(flw.getCode().equals("FSBS")){//发售标书
      flw_gysqd(IsTurnUpMap,"YQZB","one",null);
    }else if(flw.getCode().equals("CQPSZJ")){//抽取评审专家
    }else if(flw.getCode().equals("GYSQD")){//供应商签到
      flw_gysqd(IsTurnUpMap,"YQZB","two",null);
      IsTurnUpMap=null;
    }else if(flw.getCode().equals("KBCB")){//开标唱标
      flw_kbcb(mapId, project, oldProjectId);
    }else if(flw.getCode().equals("ZZZJPS")){//组织专家评审
      flw_zzzjps(mapId, project, oldProjectId, firstAuditIdMap);
    }else if(flw.getCode().equals("NZZBGS")){//拟制中标公示
      flw_nzzbgs(project, oldProjectId);
    }else if(flw.getCode().equals("QRZBGYS")){//确定中标供应商
      flw_qrzbgys(mapId, project);
    }
  }
  private void project_xjcg(FlowDefine flw, Map<String, String> mapId,
      Project project, String oldProjectId,
      Map<String, Map<String, Object>> IsTurnUpMap,
      Map<String, String> firstAuditIdMap) {
    if(flw.getCode().equals("XMXX")){//项目信息
      
    }else if(flw.getCode().equals("NZCGWJ")){//拟制询价文件
      flw_nzcgwj(mapId, project, oldProjectId,firstAuditIdMap);
    }else if(flw.getCode().equals("NZCGGG")){//拟制询价公告
      flw_nzcggg(project, oldProjectId);
    }else if(flw.getCode().equals("CQGYS")){//抽取供应商
      flw_fsbs(mapId, project, oldProjectId,IsTurnUpMap,"XJCG",null);
    }else if(flw.getCode().equals("FSBS")){//发售标书
      flw_gysqd(IsTurnUpMap,"XJCG","one",null);
    }else if(flw.getCode().equals("CQPSZJ")){//抽取评审专家
      
    }else if(flw.getCode().equals("GYSQD")){//供应商签到
      flw_gysqd(IsTurnUpMap,"XJCG","two",null);
      IsTurnUpMap=null;
    }else if(flw.getCode().equals("KBCB")){//开标唱标
      flw_kbcb(mapId, project, oldProjectId);
    }else if(flw.getCode().equals("ZZZJPS")){//组织专家评审
      flw_zzzjps(mapId, project, oldProjectId, firstAuditIdMap);
    }else if(flw.getCode().equals("NZZBGS")){//拟定中标公告
      flw_nzzbgs(project, oldProjectId);
    }else if(flw.getCode().equals("QRZBGYS")){//确认中标供应商
      flw_qrzbgys(mapId, project);
    }
  }
  private void project_dyly(FlowDefine flw, Map<String, String> mapId,
      Project project, String oldProjectId, Map<String, Map<String, Object>> IsTurnUpMap) {
    if(flw.getCode().equals("XMXX")){//项目信息
      
    }else if(flw.getCode().equals("ZDGYS")){//指定供应商
      flw_fsbs(mapId, project, oldProjectId,IsTurnUpMap,null,null);
    }else if(flw.getCode().equals("NZCGGG")){//编制单一来源公示
      flw_nzcggg(project, oldProjectId);
    }else if(flw.getCode().equals("CQPSZJ")){//抽取评审专家
      
    }else if(flw.getCode().equals("GYSQD")){//供应商签到
      flw_gysqd(IsTurnUpMap,null,null,null);
      IsTurnUpMap=null;
    }else if(flw.getCode().equals("KBCB")){//开标唱标
      flw_kbcb(mapId, project, oldProjectId);
    }else if(flw.getCode().equals("BZTPJL")){//编制谈判记录
      flw_bztpjl(mapId, project, oldProjectId);
    }else if(flw.getCode().equals("ZZZJTP")){//组织专家谈判
      flw_zzzjtp(mapId, project, oldProjectId);
    }else if(flw.getCode().equals("QRZBGYS")){//确定成交供应商
      flw_qrzbgys(mapId, project);
    }else if(flw.getCode().equals("DYLYTPBG")){//单一来源谈判报告
      flw_dylytpbg(mapId, project);
    }
  }
  private void flw_dylytpbg(Map<String, String> mapId, Project project) {
    Iterator<Entry<String, String>> iterator = mapId.entrySet().iterator();
    while (iterator.hasNext()) {
      Entry<String, String> next = iterator.next();
      String newId=next.getValue();
      String oldId=next.getKey();
      NegotiationReport nr = negotiationReportMapper.selectByPackageId(oldId);
      nr.setId(WfUtil.createUUID());
      nr.setPackageId(newId);
      nr.setProjectId(project.getId());
      negotiationReportMapper.insertSelective(nr);
    }
  }
  private void flw_zzzjtp(Map<String, String> mapId, Project project,
      String oldProjectId) {
    Iterator<Entry<String, String>> iterator = mapId.entrySet().iterator();
    while (iterator.hasNext()) {
      Entry<String, String> next = iterator.next();
      String newId=next.getValue();
      String oldId=next.getKey();
      Map<String, Object> map = new HashMap<String, Object>();
      map.put("projectId", oldProjectId);
      map.put("packageId", oldId);
      List<PackageExpert> selectLists = packageExpertMapper.selectList(map);
        for(PackageExpert sl:selectLists){
          sl.setProjectId(project.getId());
          sl.setPackageId(newId);
          packageExpertMapper.insertSelective(sl);
        }
    }
  }
  private void flw_bztpjl(Map<String, String> mapId, Project project,
      String oldProjectId) {
    Iterator<Entry<String, String>> iterator = mapId.entrySet().iterator();
    while (iterator.hasNext()) {
      Entry<String, String> next = iterator.next();
      String newId=next.getValue();
      String oldId=next.getKey();
      HashMap<String, Object> hashMap=new HashMap<String, Object>();
      hashMap.put("projectId", oldProjectId);
      hashMap.put("packageId", oldId);
      List<Negotiation> negotiations = negotiationMapper.listByNegotiation(hashMap);
      for (Negotiation nt : negotiations) {
        nt.setId(WfUtil.createUUID());
        nt.setCreatedAt(new Date());
        nt.setPackageId(newId);
        nt.setProjectId(project.getId());
        negotiationMapper.insertSelective(nt);
      }
    }
  }
  private void project_gkzb(FlowDefine flw, Map<String, String> mapId,
      Project project, String oldProjectId, Map<String, Map<String, Object>> IsTurnUpMap,
      Map<String, String> firstAuditIdMap,String type) {
    if(flw.getCode().equals("XMXX")){//项目信息
      
    }else if(flw.getCode().equals("NZCGWJ")){//拟制招标文件
      flw_nzcgwj(mapId, project, oldProjectId,firstAuditIdMap);
    }else if(flw.getCode().equals("NZCGGG")){//拟制招标公告
      flw_nzcggg(project, oldProjectId);
    }else if(flw.getCode().equals("FSBS")){//发售标书
      flw_fsbs(mapId, project, oldProjectId,IsTurnUpMap,null,type);
    }else if(flw.getCode().equals("CQPSZJ")){//抽取评审专家
      
    }else if(flw.getCode().equals("GYSQD")){//供应商签到
      flw_gysqd(IsTurnUpMap,null,null,type);
      IsTurnUpMap=null;
    }else if(flw.getCode().equals("KBCB")){//开标唱标
      flw_kbcb(mapId, project, oldProjectId);
    }else if(flw.getCode().equals("ZZZJPS")){//组织专家评审
      flw_zzzjps(mapId, project, oldProjectId, firstAuditIdMap);
    }else if(flw.getCode().equals("NZZBGS")){//拟制中标公告
      flw_nzzbgs(project, oldProjectId);
    }else if(flw.getCode().equals("QRZBGYS")){//确认中标供应商
      flw_qrzbgys(mapId, project);
    }
  }
  private void flw_qrzbgys(Map<String, String> mapId, Project project) {
    Iterator<Entry<String, String>> iterator = mapId.entrySet().iterator();
    while (iterator.hasNext()) {
      Entry<String, String> next = iterator.next();
      String newId=next.getValue();
      String oldId=next.getKey();
      List<SupplierCheckPass> supplierCheckPasss = supplierCheckPassMapper.getByCheck(oldId);
      for(SupplierCheckPass scp:supplierCheckPasss){
        scp.setId(WfUtil.createUUID());
        scp.setPackageId(newId);
        scp.setProjectId(project.getId());
        supplierCheckPassMapper.insertSelective(scp);
      }
      List<theSubject> theSubjects = theSubjectMapper.selectByPackagesId(oldId);
      for (theSubject ts : theSubjects) {
        ts.setPackageId(newId);
        theSubjectMapper.insertSelective(ts);
      }
    }
  }
  private void flw_zzzjps(Map<String, String> mapId, Project project,
      String oldProjectId, Map<String, String> firstAuditIdMap) {
    Iterator<Entry<String, String>> iterator = mapId.entrySet().iterator();
    while (iterator.hasNext()) {
      Entry<String, String> next = iterator.next();
      String newId=next.getValue();
      String oldId=next.getKey();
      Map<String, Object> map = new HashMap<String, Object>();
      map.put("projectId", oldProjectId);
      map.put("packageId", oldId);
      List<PackageExpert> selectLists = packageExpertMapper.selectList(map);
        for(PackageExpert sl:selectLists){
          sl.setProjectId(project.getId());
          sl.setPackageId(newId);
          packageExpertMapper.insertSelective(sl);
        }
      
    //符合性和资格性检查
      Map<String, Object> map2 = new HashMap<String, Object>();
      map2.put("projectId", oldProjectId);
      map2.put("packageId", oldId);
      List<ReviewProgress> rplist = reviewProgressMapper.selectByMap(map2);
        for(ReviewProgress rp:rplist){
          rp.setId(WfUtil.createUUID());
          rp.setProjectId(project.getId());
          rp.setPackageId(newId);
          reviewProgressMapper.insertSelective(rp);
        }
      //检查项
      FirstAudit firstAudit1 = new FirstAudit();
      firstAudit1.setPackageId(oldId);
      List<FirstAudit> firstAudits = firstAuditMapper.find(firstAudit1);
        for (FirstAudit firstAudit2 : firstAudits) {
          Map<String, Object> rfaMap = new HashMap<String, Object>();
          rfaMap.put("packageId", oldId);
          rfaMap.put("projectId", oldProjectId);
          rfaMap.put("firstAuditId", firstAudit2.getId());
          List<ReviewFirstAudit> reviewFirstAudit =  reviewFirstAuditMapper.selectList(rfaMap);
          for (ReviewFirstAudit rfa : reviewFirstAudit) {
            rfa.setProjectId(project.getId());
            rfa.setPackageId(newId);
            rfa.setFirstAuditId(firstAuditIdMap.get(rfa.getFirstAuditId()));
            reviewFirstAuditMapper.insertSelective(rfa);
          }
       }
        Packages oldPackages = packageMapper.selectByPrimaryKeyId(oldId);
        Packages newPackages = packageMapper.selectByPrimaryKeyId(newId);
        if (newPackages != null) {
        	if (oldPackages.getQualificationTime() != null) {
            	newPackages.setQualificationTime(oldPackages.getQualificationTime());
    		}
            if (oldPackages.getTechniqueTime() != null) {
            	newPackages.setTechniqueTime(oldPackages.getTechniqueTime());
    		}
            packageMapper.updateByPrimaryKeySelective(newPackages);
		}
    }
  }
  private void flw_kbcb(Map<String, String> mapId, Project project,
      String oldProjectId) {
    Iterator<Entry<String, String>> iterator = mapId.entrySet().iterator();
    while (iterator.hasNext()) {
      Entry<String, String> next = iterator.next();
      String newId=next.getValue();
      String oldId=next.getKey();
      List<ProjectExtract> byId = projectExtractMapper.getById(oldId);
      for (ProjectExtract projectExtract : byId) {
        projectExtract.setId(WfUtil.createUUID());
        projectExtract.setProjectId(newId);
        projectExtract.setExpertId(projectExtract.getExpert().getId());
        projectExtractMapper.insertSelective(projectExtract);
      }
      Quote quote=new Quote();
      quote.setProjectId(oldProjectId);
      quote.setPackageId(oldId);
      List<Quote> selectQuoteHistory = quoteMapper.selectQuoteHistory(quote);
      for(Quote qt:selectQuoteHistory){
        qt.setId(WfUtil.createUUID());
        qt.setProjectId(project.getId());
        qt.setPackageId(newId);
        qt.setCreatedAt(new Timestamp(new Date().getTime()));
        quoteMapper.insertSelective(qt);
      }
    }
  }
  private void flw_nzzbgs(Project project, String oldProjectId) {
    Article article = new Article();
    DictionaryData data = dictionaryDataMapper.selectByPrimaryKey(project.getPurchaseType());
    if("GKZB".equals(data.getCode())||"XJCG".equals(data.getCode())||"YQZB".equals(data.getCode())||"JZXTP".equals(data.getCode())){
      ArticleType at = articleTypeMapper.selectArticleTypeByCode("success_notice");
      article.setArticleType(new ArticleType(at.getId()));
    }
    article.setProjectId(oldProjectId);
    List<Article> articles = articleMapper.selectArticleByProjectId(article);
    if(articles!=null&&articles.size()>0){
      String oldArtId = articles.get(0).getId();
      Article oldArt = articles.get(0);
      oldArt.setId(WfUtil.createUUID());
      oldArt.setProjectId(project.getId());
      oldArt.setCreatedAt(new Date());
      oldArt.setUpdatedAt(null);
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
  private void flw_gysqd(Map<String, Map<String, Object>> IsTurnUpMap,String code,String step,String type) {
    Iterator<Entry<String, Map<String, Object>>> iterator = IsTurnUpMap.entrySet().iterator();
    while (iterator.hasNext()) {
      Entry<String, Map<String, Object>> next = iterator.next();
      String id=next.getKey(); 
      Map<String, Object> value=next.getValue();
      SaleTender saleTenders = saleTenderMapper.selectByPrimaryKey(id);
      if(code!=null&&("XJCG".equals(code)||"YQZB".equals(code))||"JZXTP".equals(code)){
        if(step!=null&&"one".equals(step)){
          saleTenders.setStatusBid((Short) value.get("statusBid"));
          saleTenders.setStatusBond((Short) value.get("statusBond"));
        }else{
          saleTenders.setIsTurnUp((Integer) value.get("isTurnUp"));
        }
      }else{
        saleTenders.setIsTurnUp((Integer) value.get("isTurnUp"));
      }
      saleTenderMapper.updateByPrimaryKeySelective(saleTenders);
    }
    //添加投标文件
  }
  private void flw_fsbs(Map<String, String> mapId, Project project,
      String oldProjectId,Map<String, Map<String, Object>> IsTurnUpMap,String code,String type) {
    Iterator<Entry<String, String>> iterator = mapId.entrySet().iterator();
    while (iterator.hasNext()) {
      Entry<String, String> next = iterator.next();
      String newId=next.getValue();
      String oldId=next.getKey();
      SaleTender saleTender = new SaleTender();
      saleTender.setProject(new Project(oldProjectId));
      saleTender.setPackages(oldId);
      if(type!=null){
        saleTender.setIsTurnUp(3);
      }
      List<SaleTender> saleTenders = saleTenderMapper.getPackegeSupplier(saleTender);
      for(SaleTender st:saleTenders){
        st.setId(WfUtil.createUUID());
        st.setCreatedAt(new Date());
        st.setUpdatedAt(null);
        st.setProjectId(project.getId());
        st.setPackages(newId);
        st.setSupplierId(st.getSuppliers().getId());
        st.setUserId(st.getUser().getId());
        st.setIsFirstPass(null);
        Map<String, Object> map=new HashMap<String, Object>();
        map.put("isTurnUp", st.getIsTurnUp());
        map.put("statusBid", st.getStatusBid());
        map.put("statusBond", st.getStatusBond());
        IsTurnUpMap.put(st.getId(), map);
        st.setIsTurnUp(null);
        if(code!=null&&("XJCG".equals(code)||"YQZB".equals(code))||"JZXTP".equals(code)){
          st.setStatusBid(null);
          st.setStatusBond(null);
        }
        saleTenderMapper.insertSelective(st);
      }
    }
  }
  private void flw_nzcggg(Project project, String oldProjectId) {
  	Article article = new Article();
  	DictionaryData data = dictionaryDataMapper.selectByPrimaryKey(project.getPurchaseType());
  	if("GKZB".equals(data.getCode())||"XJCG".equals(data.getCode())||"YQZB".equals(data.getCode())||"JZXTP".equals(data.getCode())){
  	  ArticleType at = articleTypeMapper.selectArticleTypeByCode("purchase_notice");
  	  article.setArticleType(new ArticleType(at.getId()));
  	}else if("DYLY".equals(data.getCode())){
  	  ArticleType at = articleTypeMapper.selectArticleTypeByCode("single_source_notice");
      article.setArticleType(new ArticleType(at.getId()));
  	}
    article.setProjectId(oldProjectId);
    List<Article> articles = articleMapper.selectArticleByProjectId(article);
    if(articles!=null&&articles.size()>0){
      String oldArtId = articles.get(0).getId();
      Article oldArt = articles.get(0);
      oldArt.setId(WfUtil.createUUID());
      oldArt.setProjectId(project.getId());
      oldArt.setCreatedAt(new Date());
      oldArt.setUpdatedAt(null);
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
  private void flw_nzcgwj(Map<String, String> mapId, Project project,
      String oldProjectId,Map<String, String> firstAuditIdMap) {
    
    
    insert_firstAudit(project, firstAuditIdMap, mapId);
    
    Map<String, String> BidMethodId=new HashMap<String, String>();
    insert_bidMethod(oldProjectId,project, mapId, BidMethodId);
    
    Map<String, String> markTermsId=new HashMap<String, String>();
    Map<String, String> markTermsPId=new HashMap<String, String>();
    insert_markTerms(oldProjectId,project, mapId, markTermsId, markTermsPId,BidMethodId);
    
    Map<String, String>  scoreModelId=new HashMap<String, String>();
    insert_scoreModel(project, oldProjectId, mapId, markTermsId,
        scoreModelId);
    insert_paramInterval(project, oldProjectId, mapId, scoreModelId);
    //采购文件
    String typeId = DictionaryDataUtil.getId("PROJECT_BID");
    Integer systemKey = Integer.parseInt(Constant.TENDER_SYS_KEY+"");
    String tableName = Constant.fileSystem.get(systemKey);
    List<UploadFile> files = uploadDao.getFiles(tableName, oldProjectId, typeId);
    if (files != null && files.size() > 0){
       for(UploadFile uf:files){
         uf.setBusinessId(project.getId());
         uf.setTableName(tableName);
         uploadDao.insertFile(uf);
       }
    }
    //采购管理部门审核意见附件
    String pc_reason = DictionaryDataUtil.getId("PC_REASON");
    files = uploadDao.getFiles(tableName, oldProjectId, pc_reason);
    if (files != null && files.size() > 0){
      for(UploadFile uf:files){
        uf.setBusinessId(project.getId());
        uf.setTableName(tableName);
        uploadDao.insertFile(uf);
      }
   }
    //事业部门审核意见附件
    String cause_reason = DictionaryDataUtil.getId("CAUSE_REASON");
    files = uploadDao.getFiles(tableName, oldProjectId, cause_reason);
    if (files != null && files.size() > 0){
      for(UploadFile uf:files){
        uf.setBusinessId(project.getId());
        uf.setTableName(tableName);
        uploadDao.insertFile(uf);
      }
   }
    //财务部门审核意见附件
    String finance_reason = DictionaryDataUtil.getId("FINANCE_REASON");
    files = uploadDao.getFiles(tableName, oldProjectId, finance_reason);
    if (files != null && files.size() > 0){
      for(UploadFile uf:files){
        uf.setBusinessId(project.getId());
        uf.setTableName(tableName);
        uploadDao.insertFile(uf);
      }
   }
    //最终意见附件
    String FINAL_OPINION = DictionaryDataUtil.getId("FINAL_OPINION");
    files = uploadDao.getFiles(tableName, oldProjectId, FINAL_OPINION);
    if (files != null && files.size() > 0){
      for(UploadFile uf:files){
        uf.setBusinessId(project.getId());
        uf.setTableName(tableName);
        uploadDao.insertFile(uf);
      }
   }
  }
  private void insert_paramInterval(Project project, String oldProjectId,
      Map<String, String> mapId, Map<String, String> scoreModelId) {
    Iterator<Entry<String, String>> iterator = mapId.entrySet().iterator();
    while(iterator.hasNext()){
      Map.Entry<String, String> entry = iterator.next();
      String oldId=entry.getKey();
      String newId=entry.getValue();
      ParamInterval paramInterval=new ParamInterval();
      paramInterval.setPackageId(oldId);
      paramInterval.setProjectId(oldProjectId);
      List<ParamInterval> paramIntervals = paramIntervalMapper.findListByParamInterval(paramInterval);
      for (ParamInterval pt : paramIntervals) {
        pt.setPackageId(newId);
        pt.setProjectId(project.getId());
        pt.setScoreModelId(scoreModelId.get(pt.getScoreModelId()));
        paramIntervalMapper.saveParamInterval(pt);
      }
      
    }
  }
  private void insert_scoreModel(Project project, String oldProjectId,
      Map<String, String> mapId,
      Map<String, String> markTermsId, Map<String, String> scoreModelId) {
    Iterator<Entry<String, String>> iterator = mapId.entrySet().iterator();
    while(iterator.hasNext()){
      Map.Entry<String, String> entry = iterator.next();
      String oldId=entry.getKey();
      String newId=entry.getValue();
      ScoreModel model=new ScoreModel();
      model.setPackageId(oldId);
      model.setProjectId(oldProjectId);
      List<ScoreModel> scoreModels = scoreModelMapper.findScoreModelByPackageId(model);
      for (ScoreModel sm : scoreModels) {
        String id=sm.getId();
        sm.setPackageId(newId);
        sm.setProjectId(project.getId());
        sm.setMarkTermId(markTermsId.get(sm.getMarkTermId()));
        scoreModelMapper.saveScoreModel(sm);
        scoreModelId.put(id,sm.getId());
      }
    }
  }
  private void insert_markTerms(String oldprojectId,Project project,
      Map<String, String> mapId,
      Map<String, String> markTermsId, Map<String, String> markTermsPId,Map<String, String> bidMethodIds) {
    Iterator<Entry<String, String>> iterator = mapId.entrySet().iterator();
    while(iterator.hasNext()){
      Map.Entry<String, String> entry = iterator.next();
      String oldId=entry.getKey();
      String newId=entry.getValue();
      MarkTerm markTerm=new MarkTerm();
      markTerm.setPackageId(oldId);
      markTerm.setProjectId(oldprojectId);
      List<MarkTerm> markTerms = markTermMapper.findMarkTermByPackageId(markTerm);
      List<MarkTerm> markTermslist=new ArrayList<MarkTerm>();
      markTermslist.addAll(markTerms);
      for (MarkTerm mt : markTerms) {
        if(mt.getPid().equals("0")){
          String id=mt.getId();
          mt.setPackageId(newId);
          mt.setProjectId(project.getId());
          mt.setBidMethodId(bidMethodIds.get(mt.getBidMethodId()));
          markTermMapper.saveMarkTerm(mt);
          markTermsId.put(id, mt.getId());
        }
      }
      for (MarkTerm mt : markTermslist) {
        if(!mt.getPid().equals("0")){
          String id=mt.getId();
          mt.setPackageId(newId);
          mt.setProjectId(project.getId());
          mt.setPid(markTermsId.get(mt.getPid()));
          mt.setBidMethodId(bidMethodIds.get(mt.getBidMethodId()));
          markTermMapper.saveMarkTerm(mt);
          markTermsId.put(id, mt.getId());
        }
      }
    }
  }
  private void insert_bidMethod(String oldProjectId,Project project,
      Map<String, String> mapId, Map<String, String> BidMethodId) {
    Iterator<Entry<String, String>> iterator = mapId.entrySet().iterator();
    while(iterator.hasNext()){
      Map.Entry<String, String> entry = iterator.next();
      String oldId=entry.getKey();
      String newId=entry.getValue();
      //经济和技术评审
      BidMethod condition = new BidMethod();
      condition.setPackageId(oldId);
      condition.setProjectId(oldProjectId);
      List<BidMethod> bmList = bidMethodMapper.findScoreMethodByPackageId(condition);
      for(BidMethod bm:bmList){
        String id=bm.getId();
        bm.setPackageId(newId);
        bm.setProjectId(project.getId());
        bm.setUpdatedAt(null);
        bidMethodMapper.saveBidMethod(bm);
        BidMethodId.put(id, bm.getId());
      }
    }
  }
  private void insert_firstAudit(Project project,
      Map<String, String> firstAuditIdMap,
      Map<String, String> mapId) {
    Iterator<Entry<String, String>> iterator = mapId.entrySet().iterator();
    //资格性和符合性审查
    while(iterator.hasNext()){
      Map.Entry<String, String> entry = iterator.next();
      String oldId=entry.getKey();
      String newId=entry.getValue();
      FirstAudit firstAudit = new FirstAudit();
      firstAudit.setPackageId(oldId);
      List<FirstAudit> fas = firstAuditMapper.find(firstAudit);
      for(FirstAudit fa:fas){
        String id=WfUtil.createUUID();
        firstAuditIdMap.put(fa.getId(), id);
        fa.setId(id);
        fa.setPackageId(newId);
        fa.setCreatedAt(new Date());
        fa.setUpdatedAt(null);
        fa.setProjectId(project.getId());
        firstAuditMapper.insertSelective(fa);
      }
    }
  }
  private void insertDetail(String packagesId,String projectId, Project project,
      Map<String, String> mapId) {
    String[] packId=packagesId.split(",");
    HashMap<String, Object> hashMap=new HashMap<String, Object>();
    hashMap.put("id", projectId);
    List<ProjectDetail> detail = projectDetailMapper.selectById(hashMap);
    for(ProjectDetail pd:detail){
      for(String str:packId){
        if(pd.getPackages()!=null&&str.equals(pd.getPackages().getId())){
          pd.setProject(project);
          pd.setPackageId(mapId.get(str));
          pd.setCreatedAt(new Date());
          pd.setUpdateAt(null);
          projectDetailMapper.insertSelective(pd);
        }
      }
    }
  }

  private void insertPackages(String packagesId, Project project,
      Map<String, String> mapId,String currFlowDefineId,String oldCurrFlowDefineId,String oldProjectId,String type) {
    if(packagesId!=null){
      String[] split = packagesId.split(",");
      String pagId="";
      for(int i=0;i<split.length;i++){
        Packages pg = packageMapper.selectByPrimaryKeyId(split[i]);
        if(type!=null){
          pg.setProjectStatus(DictionaryDataUtil.getId("ZJZXTP"));
          pg.setEditFlowId(currFlowDefineId);
        }else{
          pg.setProjectStatus(DictionaryDataUtil.getId("YZZ"));
          pg.setOldFlowId(oldCurrFlowDefineId);
        }
        
        packageMapper.updateByPrimaryKeySelective(pg);
        if(pg!=null){
          pagId=pg.getId();
          pg.setProjectId(project.getId());
          pg.setCreatedAt(new Date());
          pg.setUpdatedAt(null);
          pg.setOldFlowId(null);
          if(type!=null){
            pg.setProjectStatus(oldCurrFlowDefineId);
            pg.setPurchaseType(DictionaryDataUtil.getId("JZXTP"));
          }else{
            pg.setNewFlowId(currFlowDefineId);
            pg.setProjectStatus(null);
          }
          pg.setTechniqueTime(null);
          pg.setQualificationTime(null);
          
          packageMapper.insertSelective(pg);
          mapId.put(pagId, pg.getId());
        }
      }
    }
  }

  private Project insertProject(String projectId, String title,String type,String currFlowDefineId ) {
    Project project = projectMapper.selectProjectByPrimaryKey(projectId);
    project.setRelationId(project.getId());
    project.setCreateAt(new Date());
    project.setName(project.getName().substring(0,project.getName().lastIndexOf("（"))+title);
    
    project.setProjectNumber(project.getProjectNumber().substring(0,project.getProjectNumber().lastIndexOf("（"))+title);
    /*project.setConfirmFile(null);*/
    //project.setSupplierNumber(null);
    //project.setDeadline(null);
    //project.setBidDate(null);
    //project.setBidAddress(null);
    //project.setStartTime(null);
    /*project.setStatus(DictionaryDataUtil.getId("FBWC"));*/
    //project.setIsCharge(null);
    if(type!=null){
    	Orgnization orgnization = orgnizationMapper.findOrgByPrimaryKey(project.getPurchaseDepId());
    	project.setSectorOfDemand(orgnization.getName());
    	/*project.setPurchaseType("3CF3C643AE0A4499ADB15473106A7B80");*/
    	project.setPurchaseNewType(DictionaryDataUtil.getId("JZXTP"));
    	
    }else{
      project.setConfirmFile(null);
      project.setPurchaseNewType(null);
    }
    if("XMLX".equals(currFlowDefineId)){
      
    }else if("XMFB".equals(currFlowDefineId)){
      project.setStatus(DictionaryDataUtil.getId("YJLX"));
      project.setParentId("1");
    }else{
      
    }
    return project;
  }
  
  private void updateProjectName(String projectId, String[] packageIds) {
	  Project project = projectMapper.selectProjectByPrimaryKey(projectId);
	  HashMap<String, Object> map = new HashMap<>();
	  map.put("projectId", projectId);
	  map.put("projectStatus", "status");
	  List<Packages> findByID = packageMapper.findByID(map);
	  if (findByID != null && !findByID.isEmpty() && findByID.size() > 1) {
		  List<String> num = new ArrayList<String>();
		  int number = 0;
		  for (Packages packages : findByID) {
			  for (String packageId : packageIds) {
				  if (!StringUtils.equals(packageId, packages.getId())) {
					  num.add(packages.getName().substring(1, packages.getName().length()-1));
					  if(!packages.getProjectStatus().equals(DictionaryDataUtil.getId("YZZ")) && !packages.getProjectStatus().equals(DictionaryDataUtil.getId("ZJZXTP"))){
						  number = 1;
					  }
				  }
			  }
		  }	
		  String title = ShortBooleanTitle(num);
		  String name = project.getName().substring(0,project.getName().lastIndexOf("（"));
		  project.setName(name+title);
		  project.setProjectNumber(project.getProjectNumber().substring(0,project.getProjectNumber().lastIndexOf("（"))+title);
		  if (number == 0) {
			  project.setStatus(DictionaryDataUtil.getId("YZZ"));
		  }
	  } else {
		  project.setStatus(DictionaryDataUtil.getId("YZZ"));
	  }
	  
	  projectMapper.updateByPrimaryKeySelective(project);
	  
  }
  private String ShortBooleanTitle(List<String> number) {
	  String title;
	  Collections.sort(number);
	  boolean flg=false;
	  if(number!=null&&number.size()>0){
		  for(int i=0;i<number.size();i++){
			  if(i!=number.size()-1){
				  if (Pattern.compile("^[0-9]*[1-9][0-9]*$").matcher(number.get(i)).matches()) {
					  if(number.get(i)+1==number.get(i+1)){
						  flg=true;
					  }else{
						  flg=false;
					  }
				  }
			  }
		  }
	  }
	  if(flg){
		  title="（第"+number.get(0)+"-"+number.get(number.size()-1)+"包）";
	  }else{
		  String num = null;
		  for (String string : number) {
			  if (Pattern.compile("^[0-9]*[1-9][0-9]*$").matcher(string).matches()) {
				  num = "0";
			  } else {
				  num = "1";
				  break;
			  }
		  }
		  if("0".equals(num)){
			  title="（第"+StringUtils.join(number,",")+"包）";
		  } else {
			  title="（"+StringUtils.join(number,",")+"）";
		  }
		  
	  }
	  return title;
  }
  @Override
  public List<FlowDefine> selectFlowDefineTermination(String currFlowDefineId) {
    FlowDefine define=new FlowDefine();
    define.setId(currFlowDefineId);
    define.setUrl("gt");
    List<FlowDefine> flow = flowDefineMapper.getFlow(define);
    return flow;
  }
}
