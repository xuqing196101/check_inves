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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import common.constant.Constant;
import common.dao.FileUploadMapper;
import common.model.UploadFile;
import ses.dao.bms.DictionaryDataMapper;
import ses.dao.sms.QuoteMapper;
import ses.model.bms.DictionaryData;
import ses.model.sms.Quote;
import ses.util.DictionaryDataUtil;
import ses.util.WfUtil;
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
import bss.dao.ppms.SaleTenderMapper;
import bss.dao.ppms.ScoreModelMapper;
import bss.dao.ppms.SupplierCheckPassMapper;
import bss.dao.ppms.theSubjectMapper;
import bss.dao.prms.FirstAuditMapper;
import bss.dao.prms.PackageExpertMapper;
import bss.dao.prms.ReviewFirstAuditMapper;
import bss.dao.prms.ReviewProgressMapper;
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
import bss.model.ppms.SaleTender;
import bss.model.ppms.ScoreModel;
import bss.model.ppms.SupplierCheckPass;
import bss.model.ppms.theSubject;
import bss.model.prms.FirstAudit;
import bss.model.prms.PackageExpert;
import bss.model.prms.ReviewFirstAudit;
import bss.model.prms.ReviewProgress;
import bss.service.ppms.TerminationService;
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
  
  @Override
  /*
   * (non-Javadoc)
   * @see bss.service.ppms.TerminationService#updateTermination(java.lang.String包的id集合, java.lang.String项目id，java.lang.String流程id)
   */
  public void updateTermination(String packagesId, String projectId,String currFlowDefineId,String oldCurrFlowDefineId) {
    Map<String, Integer> mapOrder=new HashMap<String, Integer>();
    List<Integer> number=new ArrayList<Integer>();
    HashMap<String, Object> map=new HashMap<String, Object>();
    map.put("projectId", projectId);
    List<Packages> packages = packageMapper.selectByPrimaryKey(map);
    if(packages!=null&&packages.size()>0){
      for(int i=0;i<packages.size();i++){
        mapOrder.put(packages.get(i).getId(),Integer.valueOf(packages.get(i).getName().substring(1, 2)));
      }
    }
    if(packagesId!=null){
      String[] packId=packagesId.split(",");
        if(packId.length>0){
          for(String id:packId){
            number.add(mapOrder.get(id));
          }
        }
       }
    //获取名称
    String title=ShortBooleanTitle(number);
    //生成项目
    Project project = insertProject(projectId, title);
    Map<String, String> mapId=new HashMap<String, String>();
    projectMapper.insertSelective(project);
    //生成包
    insertPackages(packagesId, project, mapId,currFlowDefineId,oldCurrFlowDefineId);
    //生成明细
    insertDetail(packagesId,projectId, project, mapId);
    //获取当前流程以前的所有步骤并复制一份
    List<FlowDefine> flowDefines = flowDefineMapper.getFlow(currFlowDefineId);
    Map<String, Map<String, Object>> IsTurnUpMap=new HashMap<String, Map<String, Object>>();
    Map<String, String> firstAuditIdMap=new HashMap<String, String>();
    for(FlowDefine flw:flowDefines){
      FlowExecute temp=new FlowExecute();
      temp.setFlowDefineId(flw.getId());
      temp.setProjectId(projectId);
      List<FlowExecute> findExecuteds = flowExecuteMapper.findExecutedByProjectIdAndFlowId(temp);
      if(findExecuteds!=null&&findExecuteds.size()>0){
        FlowExecute flowExecute = findExecuteds.get(0);
        flowExecute.setId(WfUtil.createUUID());
        flowExecute.setProjectId(project.getId());
        flowExecuteMapper.insert(flowExecute);
      }
      flowDefine(flw,mapId,project,projectId,IsTurnUpMap,firstAuditIdMap);
    }
    
  }
  private void flowDefine(FlowDefine flw,Map<String, String> mapId,Project project,String oldProjectId,Map<String, Map<String, Object>> IsTurnUpMap,Map<String, String> firstAuditIdMap){
    //判断是采购方式
    DictionaryData data = dictionaryDataMapper.selectByPrimaryKey(flw.getPurchaseTypeId());
    if(data.getCode().equals("GKZB")){//公开招标
      project_gkzb(flw, mapId, project, oldProjectId, IsTurnUpMap,
          firstAuditIdMap);
    }else if(data.getCode().equals("XJCG")){//询价采购
      project_xjcg(flw, mapId, project, oldProjectId, IsTurnUpMap,
          firstAuditIdMap);
    }else if(data.getCode().equals("JZXTP")){//竞争性谈判
      
    }else if(data.getCode().equals("DYLY")){//单一来源
      project_dyly(flw, mapId, project, oldProjectId, IsTurnUpMap);
    }else if(data.getCode().equals("YQZB")){//邀请招标
      if(flw.getCode().equals("XMXX")){//项目信息
        
      }else if(flw.getCode().equals("NZCGWJ")){//拟制招标文件
        flw_nzcgwj(mapId, project, oldProjectId,firstAuditIdMap);
      }else if(flw.getCode().equals("NZCGGG")){//拟制招标公告
        flw_nzcggg(project, oldProjectId);
      }else if(flw.getCode().equals("CQGYS")){//抽取供应商
        flw_fsbs(mapId, project, oldProjectId,IsTurnUpMap,"YQZB");
      }else if(flw.getCode().equals("FSBS")){//发售标书
        flw_gysqd(IsTurnUpMap,"YQZB","one");
      }else if(flw.getCode().equals("CQPSZJ")){//抽取评审专家
      }else if(flw.getCode().equals("GYSQD")){//供应商签到
        flw_gysqd(IsTurnUpMap,"YQZB","two");
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
      flw_fsbs(mapId, project, oldProjectId,IsTurnUpMap,"XJCG");
    }else if(flw.getCode().equals("FSBS")){//发售标书
      flw_gysqd(IsTurnUpMap,"XJCG","one");
    }else if(flw.getCode().equals("CQPSZJ")){//抽取评审专家
      
    }else if(flw.getCode().equals("GYSQD")){//供应商签到
      flw_gysqd(IsTurnUpMap,"XJCG","two");
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
      flw_fsbs(mapId, project, oldProjectId,IsTurnUpMap,null);
    }else if(flw.getCode().equals("NZCGGG")){//编制单一来源公示
      flw_nzcggg(project, oldProjectId);
    }else if(flw.getCode().equals("CQPSZJ")){//抽取评审专家
      
    }else if(flw.getCode().equals("GYSQD")){//供应商签到
      flw_gysqd(IsTurnUpMap,null,null);
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
      Map<String, String> firstAuditIdMap) {
    if(flw.getCode().equals("XMXX")){//项目信息
      
    }else if(flw.getCode().equals("NZCGWJ")){//拟制招标文件
      flw_nzcgwj(mapId, project, oldProjectId,firstAuditIdMap);
    }else if(flw.getCode().equals("NZCGGG")){//拟制招标公告
      flw_nzcggg(project, oldProjectId);
    }else if(flw.getCode().equals("FSBS")){//发售标书
      flw_fsbs(mapId, project, oldProjectId,IsTurnUpMap,null);
    }else if(flw.getCode().equals("CQPSZJ")){//抽取评审专家
      
    }else if(flw.getCode().equals("GYSQD")){//供应商签到
      flw_gysqd(IsTurnUpMap,null,null);
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
    }
  }
  private void flw_kbcb(Map<String, String> mapId, Project project,
      String oldProjectId) {
    Iterator<Entry<String, String>> iterator = mapId.entrySet().iterator();
    while (iterator.hasNext()) {
      Entry<String, String> next = iterator.next();
      String newId=next.getValue();
      String oldId=next.getKey();
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
    if("GKZB".equals(data.getCode())||"XJCG".equals(data.getCode())||"YQZB".equals(data.getCode())){
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
  private void flw_gysqd(Map<String, Map<String, Object>> IsTurnUpMap,String code,String step) {
    Iterator<Entry<String, Map<String, Object>>> iterator = IsTurnUpMap.entrySet().iterator();
    while (iterator.hasNext()) {
      Entry<String, Map<String, Object>> next = iterator.next();
      String id=next.getKey(); 
      Map<String, Object> value=next.getValue();
      SaleTender saleTenders = saleTenderMapper.selectByPrimaryKey(id);
      if(code!=null&&("XJCG".equals(code)||"YQZB".equals(code))){
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
      String oldProjectId,Map<String, Map<String, Object>> IsTurnUpMap,String code) {
    Iterator<Entry<String, String>> iterator = mapId.entrySet().iterator();
    while (iterator.hasNext()) {
      Entry<String, String> next = iterator.next();
      String newId=next.getValue();
      String oldId=next.getKey();
      SaleTender saleTender = new SaleTender();
      saleTender.setProject(new Project(oldProjectId));
      saleTender.setPackages(oldId);
      List<SaleTender> saleTenders = saleTenderMapper.getPackegeSupplier(saleTender);
      for(SaleTender st:saleTenders){
        st.setId(WfUtil.createUUID());
        st.setCreatedAt(new Date());
        st.setUpdatedAt(null);
        st.setProjectId(project.getId());
        st.setPackages(newId);
        st.setSupplierId(st.getSuppliers().getId());
        st.setUserId(st.getUser().getId());
        Map<String, Object> map=new HashMap<String, Object>();
        map.put("isTurnUp", st.getIsTurnUp());
        map.put("statusBid", st.getStatusBid());
        map.put("statusBond", st.getStatusBond());
        IsTurnUpMap.put(st.getId(), map);
        st.setIsTurnUp(null);
        if(code!=null&&("XJCG".equals(code)||"YQZB".equals(code))){
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
  	if("GKZB".equals(data.getCode())||"XJCG".equals(data.getCode())||"YQZB".equals(data.getCode())){
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
      
    }
    //采购管理部门审核意见附件
    String pc_reason = DictionaryDataUtil.getId("PC_REASON");
    //事业部门审核意见附件
    String cause_reason = DictionaryDataUtil.getId("CAUSE_REASON");
    //财务部门审核意见附件
    String finance_reason = DictionaryDataUtil.getId("FINANCE_REASON");
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
      Map<String, String> mapId,String currFlowDefineId,String oldCurrFlowDefineId) {
    if(packagesId!=null){
      String[] split = packagesId.split(",");
      String pagId="";
      for(int i=0;i<split.length;i++){
        Packages pg = packageMapper.selectByPrimaryKeyId(split[i]);
        FlowDefine flowDefine = flowDefineMapper.get(oldCurrFlowDefineId);
        pg.setFlowId(flowDefine.getCode());
        packageMapper.updateByPrimaryKeySelective(pg);
        if(pg!=null){
          FlowDefine flowDefine1 = flowDefineMapper.get(currFlowDefineId);
          pagId=pg.getId();
          pg.setProjectId(project.getId());
          pg.setCreatedAt(new Date());
          pg.setUpdatedAt(null);
          pg.setFlowId(flowDefine1.getCode());
          packageMapper.insertSelective(pg);
          mapId.put(pagId, pg.getId());
        }
      }
    }
  }

  private Project insertProject(String projectId, String title) {
    Project project = projectMapper.selectProjectByPrimaryKey(projectId);
    project.setRelationId(project.getId());
    project.setCreateAt(new Date());
    project.setName(project.getName()+title);
    project.setProjectNumber(project.getProjectNumber()+title);
    project.setSupplierNumber(null);
    project.setDeadline(null);
    project.setBidDate(null);
    project.setBidAddress(null);
    project.setStartTime(null);
    project.setStatus("8239AF4991F448A28FE1FE09F525FA3D");
    project.setIsCharge(null);
    return project;
  }
  private String ShortBooleanTitle(List<Integer> number) {
    String title;
    Collections.sort(number);
    boolean flg=false;
    if(number!=null&&number.size()>0){
        for(int i=0;i<number.size();i++){
          if(i!=number.size()-1){
            if(number.get(i)+1==number.get(i+1)){
              flg=true;
            }else{
              flg=false;
            }
          }
      }
    }
    if(flg){
      title="(第"+number.get(0)+"-"+number.get(number.size()-1)+"包)";
    }else{
      title="(第"+StringUtils.join(number,",")+"包)";
    }
    return title;
  }
  @Override
  public List<FlowDefine> selectFlowDefineTermination(String currFlowDefineId) {
    List<FlowDefine> flow = flowDefineMapper.getFlow(currFlowDefineId);
    return flow;
  }
}
