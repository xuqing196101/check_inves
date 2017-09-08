/**
 * 
 */
package extract.service.supplier.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.oms.PurchaseDepMapper;
import ses.dao.oms.PurchaseInfoMapper;
import ses.model.bms.DictionaryData;
import ses.model.bms.User;
import ses.model.oms.PurchaseDep;
import ses.model.oms.PurchaseInfo;
import ses.model.sms.Supplier;
import ses.service.bms.UserServiceI;
import ses.service.oms.PurChaseDepOrgService;
import ses.util.DictionaryDataUtil;
import ses.util.PropUtil;
import ses.util.UUIDUtils;
import bss.dao.ppms.AdvancedDetailMapper;
import bss.dao.ppms.AdvancedPackageMapper;
import bss.dao.ppms.AdvancedProjectMapper;
import bss.model.ppms.AdvancedDetail;
import bss.model.ppms.AdvancedPackages;
import bss.model.ppms.AdvancedProject;
import bss.model.ppms.MarkTerm;
import bss.model.ppms.Packages;
import bss.model.ppms.Project;
import bss.model.ppms.ProjectDetail;
import bss.model.ppms.SaleTender;
import bss.model.ppms.ScoreModel;
import bss.model.prms.FirstAudit;
import bss.service.ppms.BidMethodService;
import bss.service.ppms.MarkTermService;
import bss.service.ppms.PackageService;
import bss.service.ppms.ProjectDetailService;
import bss.service.ppms.ProjectService;
import bss.service.ppms.SaleTenderService;
import bss.service.ppms.ScoreModelService;
import bss.service.prms.FirstAuditService;
import extract.dao.supplier.SupplierExtractUserMapper;
import extract.model.common.ExtractUser;
import extract.service.common.ExtractUserService;
import extract.service.common.SuperviseService;
import extract.service.supplier.SupplierPersonServicel;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * @Description:监督人员
 *   
 * @author Wang Wenshuai
 * @version 2016年10月17日下午3:52:16
 * @since  JDK 1.7
 */
@Service
public class SupplierPersonServicelmp implements SupplierPersonServicel {
 /* @Autowired
  SupplierExtractUserMapper supplierExtUserMapper;

  @Autowired
  FirstAuditService firstAuditService;

  @Autowired
  ScoreModelService scoreModelService;

  @Autowired
  ProjectDetailService detailService;

  @Autowired
  MarkTermService markTermService;

  @Autowired
  BidMethodService bidMethodService;

  @Autowired
  ProjectService projectService;

  @Autowired
  PackageService packageService;

  @Autowired
  SaleTenderService saleTenderService; //关联表
  
  @Autowired
  UserServiceI userServiceI;

  @Autowired
  PurchaseInfoMapper infoMapper;

  @Autowired
  PurChaseDepOrgService orgnizationServiceI;

  @Autowired
  PurchaseDepMapper  purchaseDepMapper;
  
  @Autowired
  private AdvancedProjectMapper advancedProjectMapper;
  
  @Autowired
  private AdvancedDetailMapper advancedDetailMapper;
  
  @Autowired
  private AdvancedPackageMapper advancedPackageMapper;*/

  @Autowired 
  private ExtractUserService extractUserService;
  @Autowired
  private SuperviseService SuperviseService;
  @Autowired
  private SupplierExtractUserMapper supplierExtractUserMapper;
  /**
   * @Description:集合
   *
   * @author Wang Wenshuai
   * @version 2016年10月14日 下午7:34:06  
   * @param @return      
   * @return List<ProjectSupervisor>
   */
 /* @Override
  public List<ExtractUser> list(ExtractUser extSupervise) {
    return supplierExtUserMapper.list(extSupervise);
  }

  *//**
   * @Description:根据项目id删除监督信息
   *
   * @author Wang Wenshuai
   * @version 2016年10月15日 下午7:05:15  
   * @param       
   * @return void
   *//*
  @Override
  public void deleteProjectId(String prjectId) {
    supplierExtUserMapper.deleteProjectId(prjectId);
  }

  @Override
  public void insert(ExtractUser record) {
    supplierExtUserMapper.insertSelective(record);
  }

  *//**
   * 
   *〈简述〉批量插入
   *〈详细描述〉
   * @author Wang Wenshuai
   * @param listInsert
   *//*
  @Override
  public void listInsert(List<ExtractUser> list){
    supplierExtUserMapper.listInsert(list);
  }

  *//**
   * 修改
   * @see ses.service.sms.SupplierExtUserServicel#update(ses.model.sms.SupplierExtUser)
   *//*
  @Override
  public void update(ExtractUser extUser) {
    supplierExtUserMapper.updateByPrimaryKeySelective(extUser);
  }


  *//**
   * 生成模板
   * @throws Exception 
   * @see ses.service.sms.SupplierExtUserServicel#downLoadBiddingDoc()
   *//*
  @Override
  public String downLoadBiddingDoc(HttpServletRequest request,String projectId,int type,String suppliersID) throws Exception {
    Map<String, Object> datamap = new HashMap<>();
    //经济评审
    List<MarkTerm> listScoreEconomy = new ArrayList<MarkTerm>();

    //技术技术评审
    List<MarkTerm> listScoreTechnology = new ArrayList<MarkTerm>();
    
    //基准价法和最低价法
    

    //获取项目信息
    Project project = projectService.selectById(projectId);

    //获取项目下的明细
    HashMap<String, Object> map = new HashMap<String, Object>();
    map.put("id", projectId);
    List<ProjectDetail> selectById = detailService.selectById(map);

    //模型数据获取
    //显示经济技术 和子节点  子节点的子节点就是模型
    List<DictionaryData> ddList = DictionaryDataUtil.find(23);
    List<Packages> find=null;
    
  //判断 是否有传入供应商的id 根据传参判断获取的参与包
    if(suppliersID !=null && suppliersID!=""){
    	Supplier supplier=new Supplier();
    	supplier.setId(suppliersID);
    	SaleTender saleTender = new SaleTender();
    	saleTender.setSuppliers(supplier);
    	saleTender.setProject(project);
        //该供应商参与的包
        List<SaleTender> ls = saleTenderService.findByCon(saleTender);
        List<String> listPackagesID=new ArrayList<>();
        for(SaleTender st:ls){
        	listPackagesID.add(st.getPackages());
        }
        Map<String, Object> params = new HashMap<String, Object>(2);
        params.put("projectId", projectId);
        params.put("listPackagesID", listPackagesID);
    	find= packageService.findByID(params);
    }else{
    	Packages packages = new Packages();
    	packages.setProjectId(projectId);
    	find= packageService.find(packages);
    }
    //资格性和符合性审查表
    for (Packages pack : find) {
      //资格符合性
      FirstAudit audit = new FirstAudit();
      audit.setPackageId(pack.getId());
      audit.setIsConfirm((short)0);
      List<FirstAudit> listByProjectId = firstAuditService.findBykind(audit);
      pack.setListFirstAudit(listByProjectId);
        
      //基准最低
      audit.setIsConfirm((short)1);
      listByProjectId = firstAuditService.findBykind(audit);
      if (listByProjectId != null && listByProjectId.size() != 0) {
        for (FirstAudit firstAudit : listByProjectId) {
          DictionaryData findById = DictionaryDataUtil.findById(firstAudit.getKind());
          //循环出经济技术型
          if ("ECONOMY".equals(findById.getCode())) {
            pack.setListMinimumEconomy(listByProjectId);
          } else if ("TECHNOLOGY".equals(findById.getCode())){
            pack.setListMinimumTechnology(listByProjectId);
          }
        }
        
      }

      //循环出评审类型
      for (DictionaryData dictionaryData : ddList) {  
        List<MarkTerm> list = model(dictionaryData.getId(), projectId,pack.getId());
        if(list != null){
          if ("ECONOMY".equals(dictionaryData.getCode())) {
            listScoreEconomy.addAll(list);
          } else if ("TECHNOLOGY".equals(dictionaryData.getCode())) {
            listScoreTechnology.addAll(list);
          }  
        }
      }
      //放入package中  
      pack.setListScoreEconomy(listScoreEconomy);
      pack.setListScoreTechnology(listScoreTechnology);
      //清空集合
      listScoreEconomy = new ArrayList<MarkTerm>();
      listScoreTechnology = new ArrayList<MarkTerm>();
      //查询出项目明细
      HashMap<String, Object> strMap = new HashMap<String, Object>();
      strMap.put("packageId", pack.getId());
      List<ProjectDetail> listProjectDetail = detailService.selectById(strMap);
      pack.setProjectDetails(listProjectDetail);



    }

    //采购机构信息
    PurchaseDep findByOrgId = purchaseDepMapper.findByOrgId(project.getPurchaseDepId());
    datamap.put("org",findByOrgId);


    //获取项目承办人id
    User user = new User();
    user.setId(project.getPrincipal());
    List<User> find2 = userServiceI.find(user);
    HashMap<String,Object> findMap = new HashMap<String, Object>();
    findMap.put("userId", find2.get(0).getId());
    List<PurchaseInfo> findPurchaseList = infoMapper.findPurchaseList(findMap);






    datamap.put("project", project);
    selectById.remove(0);
    selectById.remove(0);
    selectById.remove(0);
    //明细
    datamap.put("projectDetail", selectById);
    //资格性符合性
    datamap.put("packagesList",find);

    //负责人
    if (findPurchaseList != null && findPurchaseList.size() != 0) {
      datamap.put("user",findPurchaseList.get(0));
    }


    // 图片前缀路径
    String host = request.getRequestURL().toString().replace(request.getRequestURI(),"") 
      + "/" + request.getContextPath() + File.separator.replace("\\", "/");
    datamap.put("host",host);
    Integer status = 0;

    return productionDoc(request, datamap,ftlName(project.getDictionary().getCode(),type,status));

  }


  *//**
   * 模型数据获取
   *〈简述〉
   *〈详细描述〉
   * @author Wang Wenshuai
   * @param projectId
   * @param listScoreEconomy
   * @param listScoreTechnology
   *//*
  private  List<MarkTerm> model(String TypeName,String projectId,String packageId) {
    List<MarkTerm> mtList = null;
    MarkTerm mt = new MarkTerm();
    mt.setTypeName(TypeName);
    mt.setProjectId(projectId); 
    mt.setPackageId(packageId);
    //默认顶级节点为0
    mt.setPid("0");
    mtList = markTermService.findListByMarkTerm(mt);
    //根据顶级节点获取mark
    for (MarkTerm markTerm : mtList) {
      MarkTerm mt1 = new MarkTerm();
      mt1.setPid(markTerm.getId());
      mt1.setProjectId(projectId);
      mt1.setPackageId(packageId);
      //           mt1.setPackageId(packageId);
      List<MarkTerm> mtValue = markTermService.findListByMarkTerm(mt1);
      for (MarkTerm markTerm2 : mtValue) {
        ScoreModel scoreModel = new ScoreModel();
        //技术评审标准表,经济评审标准表
        scoreModel.setMarkTermId(markTerm2.getId());
        //获取模型
        List<ScoreModel> findListByScoreModel = scoreModelService.findListByScoreModel(scoreModel);
        if (findListByScoreModel != null && findListByScoreModel.size() != 0) {
          markTerm2.setScoreModels(findListByScoreModel.get(0));
        }
      }
      markTerm.setListMarkTerm(mtValue);
    }
    return mtList;
  }

  *//**
   * 获取对应ftl模板
   *〈简述〉
   *〈详细描述〉
   * @author Wang Wenshuai
   * @return ftl path
   * @param type :1( 拆包部分模板)  type:0(总模板)
   *//*
  private String ftlName(String ftl,int type,int status){
	   // 根据拆包需求   修改返回值 书写分包对应模板名
	    String str = "";
	    switch (ftl) {
	    *//***
	     * GkZB: 公开招标文件
	     * 对应模板说明：总模板，包1（资格性和符合性审查），包2（经济和技术评审细则）
	     *//*
	      case "GKZB":
	    	  if(type==0){
	    	      if(status == 0){
	    	          str="biddingdocument.ftl"; 
	    	      }else{
	    	          str="Adbiddingdocument.ftl";
	    	      }
	    		 
	    	  }else{
	    		  str = "biddingdocument_package1.ftl,biddingdocument_package2.ftl";
	    	  }
	        break;
	      case "XJCG":// 询价文件
	        if(type==0){
	            if(status == 0){
	               str="InquiryDocument.ftl";
	            }else{
	               str="AdInquiryDocument.ftl";
	            }
	    		  
	    	  }else{
	    		  str = "InquiryDocument_package1.ftl,InquiryDocument_package2.ftl";
	    	  }
	        break;
	      case "JZXTP":// 谈判文件
	        if(type==0){
	            if(status == 0){
	                str="jzxtp.ftl";
	            }else{
	                str="Adjzxtp.ftl";
	            }
	    	  }else{
	    		  str = "biddingdocument_package1.ftl,biddingdocument_package2.ftl";
	    	  }
	        break;
	      case "DYLY":// 单一来源
	        str = "";
	        break;
	      case "YQZB":// 邀请招标
	        if(type==0){
	            if(status == 0){
	                str="Invitebidding.ftl";
	            }else{
	                str="AdInvitebidding.ftl";
	            }
	    		  
	    	  }else{
	    		  str = "biddingdocument_package1.ftl,biddingdocument_package2.ftl";
	    	  }
	        break;

	      default:
	        break;
	    }
	    return str;
  }

  *//**
   * 
   *〈简述〉生成word
   *〈详细描述〉
   * @author myc
   * @param request {@link HttpServletRequest}
   * @return 文件名称
   *//*
  private String productionDoc(HttpServletRequest request, Map<String,Object> dataMap,String ftlString){
    Configuration configuration = new Configuration();
    // 修改编码格式 
    configuration.setDefaultEncoding("UTF-8");
    configuration.setServletContextForTemplateLoading(request.getSession().getServletContext(), "/template");
    String filePath = "";
    String ftlPaths[]=null;
    try {
    	long times=System.currentTimeMillis();
    	ftlPaths=ftlString.split(",");
    	int ftlLength=ftlPaths.length;
    	for(int i=0;ftlLength>i;i++){
				Template t = configuration.getTemplate(ftlPaths[i]);
				String basePath = PropUtil.getProperty("file.base.path");
				String temp = PropUtil.getProperty("file.temp.path");
				String path = basePath + File.separator + temp;
				String fileName="";
				if(ftlLength==1){
					fileName = times+"_0.doc";
				}else{
					fileName = times+"_"+(i+1) + ".doc";
				}
				File file = new File(path);
				if (!file.exists()) {
					file.mkdirs();
				}
				File rootFile = new File(path, fileName);
				if (!rootFile.exists()) {
					rootFile.createNewFile();
				}
				// 重新制定模板 编码 将GBK修改 编码为UTF-8
				Writer out = new BufferedWriter(new OutputStreamWriter(
						new FileOutputStream(rootFile), "UTF-8"));
				t.process(dataMap, out);
				out.flush();
				out.close();
				if(ftlLength==1){
					filePath = rootFile.getPath().replaceAll("\\\\","/");
				}else{
					filePath = rootFile.getPath().replaceAll("\\\\","/")+","+filePath;
				}
    	}
    } catch (IOException | TemplateException e) {
      e.printStackTrace();
    }
    return filePath;
  }

    @Override
    public String downLoadBiddingDocs(HttpServletRequest request, String projectId, int type, String suppliersID)
        throws Exception {

        Map<String, Object> datamap = new HashMap<>();
        //经济评审
        List<MarkTerm> listScoreEconomy = new ArrayList<MarkTerm>();

        //技术技术评审
        List<MarkTerm> listScoreTechnology = new ArrayList<MarkTerm>();
        
        //基准价法和最低价法
        

        //获取项目信息
        AdvancedProject project = advancedProjectMapper.selectAdvancedProjectByPrimaryKey(projectId);
        DictionaryData findById2 = DictionaryDataUtil.findById(project.getPurchaseType());

        //获取项目下的明细
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("advancedProject", projectId);
        List<AdvancedDetail> selectById = advancedDetailMapper.selectByAll(map);

        //模型数据获取
        //显示经济技术 和子节点  子节点的子节点就是模型
        List<DictionaryData> ddList = DictionaryDataUtil.find(23);
        List<AdvancedPackages> find = null;
        
      //判断 是否有传入供应商的id 根据传参判断获取的参与包
        if(suppliersID !=null && suppliersID!=""){
            Supplier supplier=new Supplier();
            supplier.setId(suppliersID);
            SaleTender saleTender = new SaleTender();
            saleTender.setSuppliers(supplier);
            saleTender.setProjectId(project.getId());
            //该供应商参与的包
            List<SaleTender> ls = saleTenderService.finds(saleTender);
            List<String> listPackagesID=new ArrayList<>();
            for(SaleTender st:ls){
                listPackagesID.add(st.getPackages());
            }
            Map<String, Object> params = new HashMap<String, Object>(2);
            params.put("projectId", projectId);
            params.put("listPackagesID", listPackagesID);
            find = advancedPackageMapper.findByID(params);
        }else{
            AdvancedPackages packages = new AdvancedPackages();
            packages.setProjectId(projectId);
            find= advancedPackageMapper.find(packages);
        }
        //资格性和符合性审查表
        for (AdvancedPackages pack : find) {
          //资格符合性
          FirstAudit audit = new FirstAudit();
          audit.setPackageId(pack.getId());
          audit.setIsConfirm((short)0);
          List<FirstAudit> listByProjectId = firstAuditService.findBykind(audit);
          pack.setListFirstAudit(listByProjectId);
            
          //基准最低
          audit.setIsConfirm((short)1);
          listByProjectId = firstAuditService.findBykind(audit);
          if (listByProjectId != null && listByProjectId.size() != 0) {
            for (FirstAudit firstAudit : listByProjectId) {
              DictionaryData findById = DictionaryDataUtil.findById(firstAudit.getKind());
              //循环出经济技术型
              if ("ECONOMY".equals(findById.getCode())) {
                pack.setListMinimumEconomy(listByProjectId);
              } else if ("TECHNOLOGY".equals(findById.getCode())){
                pack.setListMinimumTechnology(listByProjectId);
              }
            }
            
          }

          //循环出评审类型
          for (DictionaryData dictionaryData : ddList) {
            List<MarkTerm> list = model(dictionaryData.getId(), projectId,pack.getId());
            if(list != null && list.size() > 0){
              if ("ECONOMY".equals(dictionaryData.getCode())) {
                listScoreEconomy.addAll(list);
              } else if ("TECHNOLOGY".equals(dictionaryData.getCode())) {
                listScoreTechnology.addAll(list);
              }  
            }
          }
          //放入package中  
          pack.setListScoreEconomy(listScoreEconomy);
          pack.setListScoreTechnology(listScoreTechnology);
          //清空集合
          listScoreEconomy = new ArrayList<MarkTerm>();
          listScoreTechnology = new ArrayList<MarkTerm>();
          //查询出项目明细
          HashMap<String, Object> strMap = new HashMap<String, Object>();
          strMap.put("packageId", pack.getId());
          List<AdvancedDetail> listProjectDetail = advancedDetailMapper.selectByAll(strMap);
          pack.setAdvancedDetails(listProjectDetail);


        }

        //采购机构信息
        PurchaseDep findByOrgId = purchaseDepMapper.findByOrgId(project.getPurchaseDepId());
        datamap.put("org",findByOrgId);


        //获取项目承办人id
        User user = new User();
        user.setId(project.getPrincipal());
        List<User> find2 = userServiceI.find(user);
        HashMap<String,Object> findMap = new HashMap<String, Object>();
        findMap.put("userId", find2.get(0).getId());
        List<PurchaseInfo> findPurchaseList = infoMapper.findPurchaseList(findMap);
        datamap.put("project", project);
        selectById.remove(0);
        selectById.remove(0);
        selectById.remove(0);
        //明细
        datamap.put("projectDetail", selectById);
        //资格性符合性
        datamap.put("packagesList",find);

        //负责人
        if (findPurchaseList != null && findPurchaseList.size() != 0) {
          datamap.put("user",findPurchaseList.get(0));
        }


        // 图片前缀路径
        String host = request.getRequestURL().toString().replace(request.getRequestURI(),"") 
          + "/" + request.getContextPath() + File.separator.replace("\\", "/");
        datamap.put("host",host);
        Integer status = 1;

        return productionDoc(request, datamap,ftlName(findById2.getCode(),type,status));
      
    }*/

	@Override
	public String getName(String recordId) {
		return "";//supplierExtUserMapper.getName(recordId);
	}

	@Override
	public void addPerson(ExtractUser extUser) {
		if(StringUtils.isNotEmpty(extUser.getPersonType()) && StringUtils.isNotEmpty(extUser.getId())){
			String[] personId = extUser.getId().split(", "); //引用的历史人员
			HashMap<String, Object> map = new HashMap<>();
			map.put("personId", personId);
			if(extUser.getList().size()>0){
				//新添加人员
				map.put("personList", extUser.getList());
			}
			supplierExtractUserMapper.insertSelectiveByMap(map);
			if("extractUser" == extUser.getPersonType()){
				//
				
				
			}else if("supervise" == extUser.getPersonType()){
				
			}
		}
	}
	
}
