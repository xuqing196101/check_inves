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
import bss.dao.ppms.PackageMapper;
import bss.dao.ppms.ProjectDetailMapper;
import bss.dao.ppms.ProjectMapper;
import bss.dao.ppms.SaleTenderMapper;
import bss.dao.prms.FirstAuditMapper;
import bss.dao.prms.PackageExpertMapper;
import bss.dao.prms.ReviewFirstAuditMapper;
import bss.dao.prms.ReviewProgressMapper;
import bss.model.ppms.BidMethod;
import bss.model.ppms.FlowDefine;
import bss.model.ppms.Packages;
import bss.model.ppms.Project;
import bss.model.ppms.ProjectDetail;
import bss.model.ppms.SaleTender;
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
  
  @Override
  /*
   * (non-Javadoc)
   * @see bss.service.ppms.TerminationService#updateTermination(java.lang.String包的id集合, java.lang.String项目id，java.lang.String流程id)
   */
  public void updateTermination(String packagesId, String projectId,String currFlowDefineId) {
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
    insertPackages(packagesId, project, mapId);
    //生成明细
    insertDetail(packagesId,projectId, project, mapId);
    //获取当前流程以前的所有步骤并复制一份
    List<FlowDefine> flowDefines = flowDefineMapper.getFlow(currFlowDefineId);
    Map<String, Integer> IsTurnUpMap=new HashMap<String, Integer>();
    Map<String, String> firstAuditIdMap=new HashMap<String, String>();
    for(FlowDefine flw:flowDefines){
      flowDefine(flw,mapId,project,projectId,IsTurnUpMap,firstAuditIdMap);
    }
  }
  private void flowDefine(FlowDefine flw,Map<String, String> mapId,Project project,String oldProjectId,Map<String, Integer> IsTurnUpMap,Map<String, String> firstAuditIdMap){
    //判断是哪一种采购方式
    DictionaryData data = dictionaryDataMapper.selectByPrimaryKey(flw.getPurchaseTypeId());
    if(data.getCode().equals("GKZB")){//公开招标
      
      if(flw.getCode().equals("XMXX")){//项目信息
        
      }else if(flw.getCode().equals("NZCGWJ")){//拟制招标文件
        //------拟制招标文件------
        flw_nzcgwj(mapId, project, oldProjectId,firstAuditIdMap);
      }else if(flw.getCode().equals("NZCGGG")){//拟制招标公告
        
        //------拟制招标公告---
        flw_nzcggg(project, oldProjectId);
        
      }else if(flw.getCode().equals("FSBS")){//发售标书
       
        //---------发售标书---------
        flw_fsbs(mapId, project, oldProjectId,IsTurnUpMap);
        
      }else if(flw.getCode().equals("CQPSZJ")){//抽取评审专家
        
      }else if(flw.getCode().equals("GYSQD")){//供应商签到
        //-------供应商签到-----
        flw_gysqd(IsTurnUpMap);
        IsTurnUpMap=null;
      }else if(flw.getCode().equals("KBCB")){//开标唱标
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
      }else if(flw.getCode().equals("ZZZJPS")){//组织专家评审
        //专家
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
      }else if(flw.getCode().equals("NZZBGS")){//拟制中标公告
        flw_nzzbgs(project, oldProjectId);
      }else if(flw.getCode().equals("QRZBGYS")){//确认中标供应商
        
      }
      
    }else if(data.getCode().equals("XJCG")){//询价采购
      
    }else if(data.getCode().equals("JZXTP")){//竞争性谈判
      
    }else if(data.getCode().equals("DYLY")){//单一来源
      
    }else if(data.getCode().equals("YQZB")){//邀请招标
      
      
    }
    
  }
  private void flw_nzzbgs(Project project, String oldProjectId) {
    Article article = new Article();
    DictionaryData data = dictionaryDataMapper.selectByPrimaryKey(project.getPurchaseType());
    if("GKZB".equals(data.getCode())){
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
  private void flw_gysqd(Map<String, Integer> IsTurnUpMap) {
    Iterator<Entry<String, Integer>> iterator = IsTurnUpMap.entrySet().iterator();
    while (iterator.hasNext()) {
      Entry<String, Integer> next = iterator.next();
      String id=next.getKey();
      Integer value=next.getValue();
      SaleTender saleTenders = saleTenderMapper.selectByPrimaryKey(id);
      saleTenders.setIsTurnUp(value);
      saleTenderMapper.updateByPrimaryKeySelective(saleTenders);
    }
    //添加投标文件
  }
  private void flw_fsbs(Map<String, String> mapId, Project project,
      String oldProjectId,Map<String, Integer> IsTurnUpMap) {
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
        IsTurnUpMap.put(st.getId(), st.getIsTurnUp());
        st.setIsTurnUp(null);
        saleTenderMapper.insertSelective(st);
      }
    }
  }
  private void flw_nzcggg(Project project, String oldProjectId) {
  	Article article = new Article();
  	DictionaryData data = dictionaryDataMapper.selectByPrimaryKey(project.getPurchaseType());
  	if("GKZB".equals(data.getCode())){
  	  ArticleType at = articleTypeMapper.selectArticleTypeByCode("purchase_notice");
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
      
      //经济和技术评审
      BidMethod condition = new BidMethod();
      condition.setPackageId(oldId);
      List<BidMethod> bmList = bidMethodMapper.findScoreMethod(condition);
      for(BidMethod bm:bmList){
        bm.setPackageId(newId);
        bm.setProjectId(project.getId());
        bm.setUpdatedAt(null);
        bidMethodMapper.saveBidMethod(bm);
      }
    }
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
      Map<String, String> mapId) {
    if(packagesId!=null){
      String[] split = packagesId.split(",");
      String pagId="";
      for(int i=0;i<split.length;i++){
        Packages pg = packageMapper.selectByPrimaryKeyId(split[i]);
        if(pg!=null){
          pagId=pg.getId();
          pg.setProjectId(project.getId());
          pg.setCreatedAt(new Date());
          pg.setUpdatedAt(null);
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
