/**
 * 
 */
package ses.service.sms.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import bss.model.ppms.BidMethod;
import bss.model.ppms.MarkTerm;
import bss.model.ppms.Packages;
import bss.model.ppms.Project;
import bss.model.ppms.ProjectDetail;
import bss.model.ppms.ScoreModel;
import bss.model.prms.FirstAudit;
import bss.service.ppms.BidMethodService;
import bss.service.ppms.MarkTermService;
import bss.service.ppms.PackageService;
import bss.service.ppms.ProjectDetailService;
import bss.service.ppms.ProjectService;
import bss.service.ppms.ScoreModelService;
import bss.service.prms.FirstAuditService;
import ses.dao.oms.PurchaseInfoMapper;
import ses.dao.sms.SupplierExtUserMapper;
import ses.model.bms.DictionaryData;
import ses.model.bms.User;
import ses.model.oms.PurchaseInfo;
import ses.model.sms.SupplierExtUser;
import ses.service.bms.UserServiceI;
import ses.service.sms.SupplierExtUserServicel;
import ses.util.DictionaryDataUtil;
import ses.util.PropUtil;

/**
 * @Description:监督人员
 *	 
 * @author Wang Wenshuai
 * @version 2016年10月17日下午3:52:16
 * @since  JDK 1.7
 */
@Service
public class SupplierExtUserServicelmpl implements SupplierExtUserServicel {
  @Autowired
  SupplierExtUserMapper supplierExtUserMapper;

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
  UserServiceI userServiceI;
  
  @Autowired
  PurchaseInfoMapper infoMapper;

  /**
   * @Description:集合
   *
   * @author Wang Wenshuai
   * @version 2016年10月14日 下午7:34:06  
   * @param @return      
   * @return List<ProjectSupervisor>
   */
  @Override
  public List<SupplierExtUser> list(SupplierExtUser extSupervise) {
    return supplierExtUserMapper.list(extSupervise);
  }

  /**
   * @Description:根据项目id删除监督信息
   *
   * @author Wang Wenshuai
   * @version 2016年10月15日 下午7:05:15  
   * @param       
   * @return void
   */
  @Override
  public void deleteProjectId(String prjectId) {
    supplierExtUserMapper.deleteProjectId(prjectId);
  }

  @Override
  public void insert(SupplierExtUser record) {
    supplierExtUserMapper.insertSelective(record);
  }

  /**
   * 
   *〈简述〉批量插入
   *〈详细描述〉
   * @author Wang Wenshuai
   * @param listInsert
   */
  @Override
  public void listInsert(List<SupplierExtUser> list){
    supplierExtUserMapper.listInsert(list);
  }

  /**
   * 修改
   * @see ses.service.sms.SupplierExtUserServicel#update(ses.model.sms.SupplierExtUser)
   */
  @Override
  public void update(SupplierExtUser extUser) {
    supplierExtUserMapper.updateByPrimaryKeySelective(extUser);
  }


  /**
   * 
   * @throws Exception 
   * @see ses.service.sms.SupplierExtUserServicel#downLoadBiddingDoc()
   */
  @Override
  public void downLoadBiddingDoc(HttpServletRequest request,String projectId) throws Exception {
    //经济评审
    List<MarkTerm> listScoreEconomy = new ArrayList<MarkTerm>();

    //技术技术评审
    List<MarkTerm> listScoreTechnology = new ArrayList<MarkTerm>();

    //获取项目信息
    Project project = projectService.selectById(projectId);
    //获取项目下的明细
    HashMap<String, Object> map = new HashMap<String, Object>();
    map.put("id", projectId);

    List<ProjectDetail> selectById = detailService.selectById(map);
    //模型数据获取
    //显示经济技术 和子节点  子节点的子节点就是模型
    List<DictionaryData> ddList = DictionaryDataUtil.find(23);

    Packages packages = new Packages();
    packages.setProjectId(projectId);
    List<Packages> find = packageService.find(packages);
    //资格性和符合性审查表
    for (Packages pack : find) {
      FirstAudit audit = new FirstAudit();
      audit.setPackageId(pack.getId());
      List<FirstAudit> listByProjectId = firstAuditService.findBykind(audit);
      pack.setListFirstAudit(listByProjectId);

      //循环出评审类型
      for (DictionaryData dictionaryData : ddList) {

        List<MarkTerm> list = model(dictionaryData.getId(), projectId,pack.getId());
        if ("ECONOMY".equals(dictionaryData.getCode())) {
          listScoreEconomy.addAll(list);
        } else if ("TECHNOLOGY".equals(dictionaryData.getCode())) {
          listScoreTechnology.addAll(list);
        }
      }
      //放入package中
      pack.setListScoreEconomy(listScoreEconomy);
      pack.setListScoreTechnology(listScoreTechnology);
      //清空集合
      listScoreEconomy = new ArrayList<MarkTerm>();
      listScoreTechnology = new ArrayList<MarkTerm>();

    }
    
    //获取项目承办人id
    User user = new User();
    user.setId(project.getPrincipal());
    List<User> find2 = userServiceI.find(user);
    HashMap<String,Object> findMap = new HashMap<String, Object>();
    findMap.put("userId", find2.get(0).getId());
    List<PurchaseInfo> findPurchaseList = infoMapper.findPurchaseList(findMap);



    Map<String, Object> datamap = new HashMap<>();
    //    Enumeration<String> paramNames = request.getParameterNames();
    //    // 存入参数
    //    while(paramNames.hasMoreElements()) {
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

    //    }

    // 图片前缀路径
    datamap.put("host", request.getRequestURL().toString().replace(request.getRequestURI(),"") 
      + File.separator + request.getContextPath() + File.separator);


    productionDoc(request, datamap);

  }


  /**
   * 模型数据获取
   *〈简述〉
   *〈详细描述〉
   * @author Wang Wenshuai
   * @param projectId
   * @param listScoreEconomy
   * @param listScoreTechnology
   */
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


  /**
   * 
   *〈简述〉生成word
   *〈详细描述〉
   * @author myc
   * @param request {@link HttpServletRequest}
   * @return 文件名称
   */
  private String productionDoc(HttpServletRequest request, Map<String,Object> dataMap){
    Configuration configuration = new Configuration();
    configuration.setDefaultEncoding("gb2312");
    configuration.setServletContextForTemplateLoading(request.getSession().getServletContext(), "/template");
    String filePath = "";
    try {
      Template t = configuration.getTemplate("InquiryDocument.ftl");
      String basePath = PropUtil.getProperty("file.base.path");
      String temp = PropUtil.getProperty("file.temp.path");
      String path = basePath + File.separator + temp;
      String fileName = System.currentTimeMillis()+ ".doc";
      File file = new File(path);
      if (!file.exists()){
        file.mkdirs();
      }
      File rootFile = new File(path,fileName);
      if(!rootFile.exists()){
        rootFile.createNewFile();
      }
      Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(rootFile),"gb2312"));
      t.process(dataMap, out);
      out.flush();
      out.close();
      filePath = rootFile.getPath().replaceAll("\\\\","/");
    } catch (IOException | TemplateException e) {
      e.printStackTrace();
    }
    return filePath;
  }

}
