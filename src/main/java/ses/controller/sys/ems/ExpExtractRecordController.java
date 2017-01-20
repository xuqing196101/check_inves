/**
 * 
 */
package ses.controller.sys.ems;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;





import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;












import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.dao.bms.DictionaryDataMapper;
import ses.model.bms.Area;
import ses.model.bms.Category;
import ses.model.bms.CategoryTree;
import ses.model.bms.DictionaryData;
import ses.model.bms.Todos;
import ses.model.bms.User;
import ses.model.ems.ExpExtCondition;
import ses.model.ems.ExpExtPackage;
import ses.model.ems.ExpExtractRecord;
import ses.model.ems.Expert;
import ses.model.ems.ExpertAudit;
import ses.model.ems.ExtConType;
import ses.model.ems.ProExtSupervise;
import ses.model.ems.ProjectExtract;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierCondition;
import ses.model.sms.SupplierExtPackage;
import ses.model.sms.SupplierExtRelate;
import ses.model.sms.SupplierExtUser;
import ses.model.sms.SupplierExtracts;
import ses.model.sms.SupplierTypeTree;
import ses.service.bms.AreaServiceI;
import ses.service.bms.CategoryService;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.bms.TodosService;
import ses.service.bms.UserServiceI;
import ses.service.ems.ExpExtConditionService;
import ses.service.ems.ExpExtPackageService;
import ses.service.ems.ExpExtractRecordService;
import ses.service.ems.ExpertAttachmentService;
import ses.service.ems.ExpertAuditService;
import ses.service.ems.ExpertService;
import ses.service.ems.ExtConTypeService;
import ses.service.ems.ProjectExtractService;
import ses.service.ems.ProjectSupervisorServicel;
import ses.service.sms.SupplierExtUserServicel;
import ses.service.sms.SupplierTypeService;
import ses.util.DateUtil;
import ses.util.DictionaryDataUtil;
import ses.util.PropertiesUtil;

import com.alibaba.druid.stat.TableStat.Mode;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import bss.controller.base.BaseController;
import bss.model.ppms.Packages;
import bss.model.ppms.Project;
import bss.service.ppms.FlowMangeService;
import bss.service.ppms.PackageService;
import bss.service.ppms.ProjectService;
import bss.util.PropUtil;

/**
 * @Description:专家抽取
 *   
 * @author Wang Wenshuai
 * @version 2016年9月27日下午4:34:37
 * @since  JDK 1.7
 */
@Controller
@Scope("prototype")
@RequestMapping("/ExpExtract")
public class ExpExtractRecordController extends BaseController {

  /** SCCUESS */
  private static final String SUCCESS = "SUCCESS";
  /** ERROR */
  private static final String ERROR = "ERROR";
  /** ZERO */
  private static final Integer ZERO = 0;
  /** ONE */
  private static final Integer ONE = 1;
  /** TWO */
  private static final Integer TWO = 2;

  /** 包  **/
  @Autowired
  private PackageService packagesService; 


  @Autowired
  ProjectService projectService;
  @Autowired
  AreaServiceI areaService;
  @Autowired
  ExpExtConditionService conditionService;
  @Autowired
  ExtConTypeService conTypeService;
  @Autowired
  ExpertService ExpertService;//专家管理
  @Autowired
  ProjectExtractService extractService; //关联表
  @Autowired
  ExpExtractRecordService expExtractRecordService;
  @Autowired
  ProjectSupervisorServicel projectSupervisorServicel;
  @Autowired
  UserServiceI userServiceI;//用户管理
  /** 关联包**/
  @Autowired
  private ExpExtPackageService  expExtPackageServicel;
  /** 字典**/
  @Autowired
  private DictionaryDataServiceI dictionaryDataServiceI; 
  @Autowired
  private DictionaryDataMapper dictionaryDataMapper; //
  @Autowired
  private SupplierTypeService supplierTypeService;// 供应商类型
  @Autowired
  private UserServiceI userService;
  @Autowired
  private FlowMangeService flowMangeService;//环节

  @Autowired
  private CategoryService categoryService; //品目

  /**待办消息**/
  @Autowired
  private TodosService todosService;

  @Autowired
  ExpertAuditService expertAuditService;

  @Autowired
  ExpertService  expertServices;

  @Autowired
  private SupplierExtUserServicel extUserServicel; //监督人员


  /**
   * 
   *〈简述〉获取项目下的所有包。
   *〈详细描述〉
   * @author Wang Wenshuai
   * @param model
   * @param projectId 项目id
   * @param page 
   * @return 路径
   */
  @RequestMapping("/packageList")
  public String packageList(Model model, String  projectId, String packName, String page,String typeclassId){
    if (projectId != null && !"".equals(projectId)){
      model.addAttribute("packName", packName);
      model.addAttribute("projectId", projectId);
      model.addAttribute("typeclassId", typeclassId);
      ExpExtPackage extPackage = new ExpExtPackage();
      extPackage.setProjectId(projectId);
      Packages packages = new Packages();
      packages.setName(packName);
      extPackage.setPackages(packages);
      List<ExpExtPackage> list = expExtPackageServicel.list(extPackage, page == null || "".equals(page) ? "1" : page);
      model.addAttribute("info", new PageInfo<ExpExtPackage>(list));    
      return "ses/ems/exam/expert/extract/ext_package";
    } else {
      return "redirect:Extraction.html";
    }
  }



  /**
   * @Description:    获取项目集合
   *
   * @author Wang Wenshuai
   * @version 2016年9月27日 下午4:38:31  
   * @param @param page
   * @param @param model
   * @param @param project
   * @param @return      
   * @return String
   */
  @RequestMapping("/projectList")
  public String list(Integer page,Model model,Project project,String typeclassId){
    List<Project> list = projectService.provisionalList(page == null?1:page, project);
    List<DictionaryData> find = DictionaryDataUtil.find(5);
    PageInfo<Project> info = new PageInfo<>(list);
    model.addAttribute("info", info);
    model.addAttribute("projects", project);
    model.addAttribute("kind", find);
    model.addAttribute("typeclassId", typeclassId);
    return "ses/ems/exam/expert/extract/project_list";
  }
  /**
   * @Description:条件查询集合
   *
   * @author Wang Wenshuai
   * @version 2016年9月27日 下午6:03:40  
   * @param @param id
   * @param @return      
   * @return String
   */
  @RequestMapping("/Extraction")  
  public String listExtraction(Model model,String projectId,String page,String typeclassId,String packageId){
    if (packageId != null && !"".equals(packageId)){
      //已抽取
      String[] packageIds =  packageId.split(",");
      if(packageIds.length != 0 ){
        ExpExtCondition con = null;
        for (String pckId : packageIds) {
          if(pckId != null && !"".equals(pckId)){
            con = new ExpExtCondition();
            con.setProjectId(pckId);
            con.setStatus((short)2);
            conditionService.update(con);
          }
        }
      }
    }

    List<Area> listArea = areaService.findTreeByPid("0",null);
    model.addAttribute("listArea", listArea);
    model.addAttribute("typeclassId",typeclassId);

    if (projectId != null && !"".equals(projectId)){
      //修改流程
      Project projectNew = new Project();
      projectNew.setId(projectId);
      projectNew.setStatus(DictionaryDataUtil.getId("CQPSZJZ"));
      projectService.update(projectNew);
      //专家类型
      model.addAttribute("ddList", expExtractRecordService.ddList());
      //根据包获取抽取出的专家
      List<Packages> listResultExpert = packagesService.listProjectExtract(projectId);
      model.addAttribute("listResultExpert", listResultExpert);
      //专家抽取记录
      ExpExtractRecord record = new ExpExtractRecord();
      record.setProjectId(projectId);
      List<ExpExtractRecord> listSe = expExtractRecordService.listExtractRecord(record,0);
      if (listSe != null && listSe.size() != 0){
        //抽取地区
        model.addAttribute("extractionSites", listSe.get(0).getExtractionSites());
        //响应时间
        String[] atime = listSe.get(0).getResponseTime() != null ? listSe.get(0).getResponseTime().split(","):null;
        if (atime != null && atime.length >= 2){
          model.addAttribute("minute", atime[0]);
          model.addAttribute("hour", atime[1]);
        }
      }

      //获取监督人员
      List<ProExtSupervise>  listUser = projectSupervisorServicel.list(new ProExtSupervise(projectId));
      model.addAttribute("listUser", listUser);
      String userName = "";
      if (listUser != null && listUser.size() != 0){
        for (ProExtSupervise ps : listUser) {
          if (ps != null ){
            userName += ps.getRelName()+ ",";
          }
        }
        if(!"".equals(userName)){
          model.addAttribute("userName", userName.substring(0, userName.length()-1));
        }
      }

      //获取项目信息
      Project project = projectService.selectById(projectId);
      if (project != null){
        if (project.getBidDate() != null && !"".equals(project.getBidDate()) ){
          Long currentTime = System.currentTimeMillis();
          Long currentBidDate = project.getBidDate().getTime()-(30*60*1000);
          if (currentTime > currentBidDate){
            model.addAttribute("typeId", 1);
          }else{
            model.addAttribute("typeId", 0);
          }
        }
        model.addAttribute("projectId", project.getId());
        model.addAttribute("projectName", project.getName());
        model.addAttribute("projectNumber", project.getProjectNumber());
        model.addAttribute("bidDate", project.getBidDate());
        //获取采购方式
        List<DictionaryData>  dictionaryData = new ArrayList<DictionaryData>();
        dictionaryData.add(dictionaryDataServiceI.getDictionaryData(project.getPurchaseType()));
        model.addAttribute("findByMap", dictionaryData);
      }

      //条件集合
      List<ExpExtCondition> listCon = conditionService.list(new ExpExtCondition(projectId), page == null ? 1 : Integer.valueOf(page));
      model.addAttribute("list", new PageInfo<ExpExtCondition>(listCon));

    } else {
      Map<String, Object> map = new HashMap<String, Object>();
      String[] str = {"YQZB", "DYLY", "JZXTP", "XJCG", "GKZB"};
      map.put("strs", str);
      List<DictionaryData> findByMap = dictionaryDataMapper.findByMap(map);
      model.addAttribute("findByMap", findByMap);
    }
    List<DictionaryData> find = DictionaryDataUtil.find(12);
    model.addAttribute("stemFrom", find);

    return "ses/ems/exam/expert/extract/condition_list";
  }
  /**
   * @Description:添加查询条件
   *
   * @author Wang Wenshuai
   * @version 2016年9月27日 下午6:04:26  
   * @param @param id
   * @param @return      
   * @return String
   */ 
  @RequestMapping("/addExtractions")
  public String addExtraction(Model model,String projectId,String typeclassId, String[] packageId){
    List<Area> privnce = areaService.findRootArea();
    model.addAttribute("privnce", privnce);
    model.addAttribute("typeclassId", typeclassId);
    model.addAttribute("projectId", projectId);
    String packIds="";
    for (String packId : packageId) {
      packIds+=packId+",";
    }
    model.addAttribute("packageId", packIds);

    //数据字典。专家来源
    model.addAttribute("find", DictionaryDataUtil.find(12));
    //专家类型
    model.addAttribute("ddList", expExtractRecordService.ddList());
    //专家类型
    model.addAttribute("ddListJson", JSON.toJSONString(expExtractRecordService.ddList()));

    //获取查询条件类型
    ExpExtCondition condition=new ExpExtCondition();
    condition.setProjectId(packageId[0]);
    condition.setStatus((short)1);
    List<ExpExtCondition> listCon = conditionService.list(condition,0);
    if (listCon != null && listCon.size() !=0 ){
      //条件
      model.addAttribute("listCon", listCon.get(0));

      //所在地区回显
      //                if (listCon.get(0).getAddress() != null && listCon.get(0).getAddress() != null ){
      //                    Area area = areaService.listById(listCon.get(0).getAddress());
      //                    List<Area> city = areaService.findAreaByParentId(area.getParentId());
      //                    model.addAttribute("city", city);
      //                    model.addAttribute("area", area);
      //                }

      Map<String, Integer> mapcount = new HashMap<String, Integer>();
      Integer sum = conTypeService.getSum(listCon.get(0).getId());
      model.addAttribute("sumCount",sum);
      PageHelper.startPage(1, sum*2);
      List<ProjectExtract> list = extractService.list(new ProjectExtract(listCon.get(0).getId()));
      //已操作的
      List<ProjectExtract> projectExtractListYes = new ArrayList<ProjectExtract>();
      //未操作的
      List<ProjectExtract> projectExtractListNo = new ArrayList<ProjectExtract>();
      for (ProjectExtract projectExtract : list) {
        if (projectExtract.getOperatingType() != null && (projectExtract.getOperatingType() ==1 || projectExtract.getOperatingType() == 2)){
          projectExtractListYes.add(projectExtract);
          Integer conTypeId = mapcount.get(projectExtract.getConTypeId());
          if (conTypeId != null && conTypeId != 0){
            mapcount.put(projectExtract.getConTypeId(), conTypeId+=1);
          } else {
            mapcount.put(projectExtract.getConTypeId(), 1);
          }
        } else if (projectExtract.getOperatingType() != null && projectExtract.getOperatingType() == 3){
          projectExtractListYes.add(projectExtract);
        } else {
          projectExtractListNo.add(projectExtract);
        }
      }
      //获取查询条件类型
      List<ExtConType> conTypes = listCon.get(0).getConTypes();
      if (conTypes != null && conTypes.size() == 1  &&  ( conTypes.get(0).getExpertsTypeId() == null || !"".equals(conTypes.get(0).getExpertsTypeId())) ){
        ProjectExtract projectExtrac = new ProjectExtract();
        projectExtrac.setConTypeId("1");
        projectExtrac.setExpertConditionId(listCon.get(0).getId());
        List<ProjectExtract> peList = extractService.list(projectExtrac);
        conTypes.get(0).setAlreadyCount(peList == null ? 0 : peList.size());
      }else{
        for (ExtConType extConType1 : conTypes) {
          //获取抽取的专家类别
          ProjectExtract projectExtrac = new ProjectExtract();
          projectExtrac.setReviewType(extConType1.getExpertsTypeId());
          projectExtrac.setExpertConditionId(listCon.get(0).getId());
          List<ProjectExtract> list2 = extractService.list(projectExtrac);
          extConType1.setAlreadyCount(list2 == null ? 0 : list2.size());
        }
      }
      model.addAttribute("extConType", conTypes);
      model.addAttribute("extConTypeJson",JSON.toJSONString(conTypes));


      if (projectExtractListNo.size() != 0){
        projectExtractListYes.add(projectExtractListNo.get(0));
        projectExtractListNo.remove(0);
      }else{
        //已抽取
        //        conditionService.update(new ExpExtCondition(condition.getId(), (short)2));
      }
      model.addAttribute("extRelateListYes", projectExtractListYes);
      model.addAttribute("extRelateListNo", projectExtractListNo);
      //删除查询不出的查询结果
      if (projectExtractListNo.size() == 0 && projectExtractListYes.size() == 0){
        conditionService.delById(listCon.get(0).getId());
        conTypeService.delete(listCon.get(0).getConTypes().get(0).getId());
      }
    }
    return "ses/ems/exam/expert/extract/add_condition";
  }

  /**
   * 
   *〈简述〉
   *〈详细描述〉ajax 验证必填项
   * @author Wang Wenshuai
   * @param model
   * @param project
   * @param packageName
   * @param typeclassId
   * @param sids
   * @param extAddress
   * @return
   */
  @SuppressWarnings("unused")
  @ResponseBody
  @RequestMapping("/validateAddExtraction")
  public String validateAddExtraction(Project project, String packageName, String typeclassId, String[] sids, String extractionSites,HttpServletRequest rq,String[] packageId,String[] superviseId,Integer type){
    Map<String, String> map = new HashMap<String, String>();
    map.put("type", type.toString());
    //后台数据校验
    int count=0;
    if (project.getName() == null || "".equals(project.getName())){
      map.put("projectNameError", "不能为空");
      count = 1;
    }

    if (project.getProjectNumber() == null || "".equals(project.getProjectNumber())){
      map.put("projectNumberError", "不能为空");
      count = 1;
    }

    if (extractionSites == null ||  "".equals(extractionSites)){
      map.put("extractionSitesError", "不能为空");
      count=1;
    }

    //独立
    if(typeclassId != null && !"".equals(typeclassId)){
      if(superviseId == null || superviseId.length == 0){
        map.put("supervise", "不能为空");
        count = 1;
      }
    }else{
      List<ProExtSupervise> list = projectSupervisorServicel.list(new ProExtSupervise(project.getId()));
      if (list == null || list.size() == 0 || "".equals(project.getId())){
        map.put("supervise", "不能为空");
        count = 1;
      } 
    }
    if(type == null || type != 1){
      if(packageId == null || packageId.length == 0 ){
        map.put("packageError", "不能为空");
        count = 1;
      }
    }

    //    List<ProExtSupervise> list = projectSupervisorServicel.list(new ProExtSupervise(project.getId()));
    //    if (list == null || list.size() == 0){
    //      map.put("supervise", "不能为空");
    //      count = 1;
    //    }
    //    if(packageId == null || packageId.length == 0 ){
    //      map.put("packageError", "不能为空");
    //      count = 1;
    //    }
    //        //时
    //        String hour = rq.getParameter("hour");
    //        //分
    //        String minute = rq.getParameter("minute");
    //        if (hour == null || "".equals(hour) || minute == null || "".equals(minute)){
    //            map.put("responseTimeError", "不能为空");
    //            count = 1;
    //        }
    //        String tenderTime = rq.getParameter("tenderTime");
    //        if (tenderTime == null || "".equals(tenderTime)){
    //            map.put("tenderTimeError", "不能为空");
    //            count = 1;
    //        }

    if (count == 1){
      map.put("error", "error");
      return JSON.toJSONString(map);

    } else{

      //真实的项目id
      String projectId = project.getId();
      //            String packageId = "";
      if (project.getId() == null || "".equals(project.getId())){
        // 创建一个临时项目临时包
        project.setIsProvisional(1);
        projectService.add(project);
        projectId = project.getId();
        project.setId(projectId);
        //修改监督人员
        if(superviseId != null && superviseId.length != 0){
          for (String id : superviseId) {
            ProExtSupervise extUser = new ProExtSupervise();
            extUser.setProjectId(projectId);
            extUser.setId(id);
            projectSupervisorServicel.update(extUser);
          }
        }
      }
      //抽取地址
      if (extractionSites != null && !"".equals(extractionSites)){

        ExpExtractRecord extractRecord = new ExpExtractRecord();
        extractRecord.setProjectId(projectId);
        PageHelper.startPage(1, 1);
        List<ExpExtractRecord> listSe = expExtractRecordService.listExtractRecord(extractRecord,0);
        extractRecord.setExtractionSites(extractionSites);
        //                extractRecord.setResponseTime(hour + "," + minute);
        User user = (User)rq.getSession().getAttribute("loginUser");
        if(user != null ){
          extractRecord.setExtractsPeople(user.getId());
        }
        if (listSe != null && listSe.size() != 0){
          extractRecord.setId(listSe.get(0).getId());
          expExtractRecordService.update(extractRecord);
        } else {
          extractRecord.setProjectCode(project.getProjectNumber());
          extractRecord.setProjectName(project.getName());
          extractRecord.setExtractionTime(new Date());
          expExtractRecordService.insert(extractRecord);
        }
      }  
      //监督人员
      if (sids != null && sids.length != 0){
        projectSupervisorServicel.deleteProjectId(project.getId());
        for (String id : sids) {
          if (!"".equals(id)){
            ProExtSupervise  record1 = new ProExtSupervise();
            record1.setProjectId(project.getId());
            record1.setSupviseId(id);
            projectSupervisorServicel.insert(record1);
          }
        }
      }
      map.put("projectId", project.getId());
      //            获取抽取条件状态，未抽取不能在抽取
      if(packageId != null && !"".equals(packageId) && packageId.length != 0){
        String count2 = conditionService.getCount(packageId);

        if (count2 != null && !"".equals(count2)){
          map.put("status", "1");
          map.put("packageId",count2);

        } else {

          map.put("sccuess", "SCCUESS");
        }
      }
      return JSON.toJSONString(map);
    }


  }



  /**
   * @Description:选择品目
   *
   * @author Wang Wenshuai
   * @version 2016年9月28日 上午9:48:28  
   * @param @return      
   * @return String
   */
  @RequestMapping("/addHeading")
  public String addHeading(Model model, String[] id,String type){
    ExtConType extConType=null;
    if(id!=null&&id.length!=0){
      extConType=new ExtConType();
      extConType.setCategoryId(id[0]);
      extConType.setExpertsCount(Integer.parseInt(id[2]));
      extConType.setExpertsQualification(id[3]);
    }
    model.addAttribute("type", type);
    model.addAttribute("extConType", extConType);
    return "ses/ems/exam/expert/extract/product";
  }
  /**
   * @Description: 条件抽取专家
   *
   * @author Wang Wenshuai
   * @version 2016年9月28日 下午3:12:34  
   * @param @return      
   * @return String
   */
  @RequestMapping("/extractCondition")
  public String extractCondition(HttpServletRequest sq, Model model,String cId){
    Map<String, Integer> mapcount = new HashMap<String, Integer>();
    User user=(User) sq.getSession().getAttribute("loginUser");
    List<ProjectExtract> list = extractService.list(new ProjectExtract(cId));
    //        if (list == null || list.size() == 0){
    //            extractService.insert(cId, user != null && !"".equals(user.getId()) ? user.getId() : "");
    //            list = extractService.list(new ProjectExtract(cId));
    //        }
    //已操作的
    List<ProjectExtract> projectExtractListYes = new ArrayList<ProjectExtract>();
    //未操作的
    List<ProjectExtract> projectExtractListNo = new ArrayList<ProjectExtract>();
    for (ProjectExtract projectExtract : list) {
      if (projectExtract.getOperatingType() != null && (projectExtract.getOperatingType() ==1 || projectExtract.getOperatingType() == 2)){
        projectExtractListYes.add(projectExtract);
        Integer conTypeId = mapcount.get(projectExtract.getConTypeId());
        if (conTypeId != null && conTypeId != 0){
          mapcount.put(projectExtract.getConTypeId(), conTypeId+=1);
        } else {
          mapcount.put(projectExtract.getConTypeId(), 1);
        }
      } else if (projectExtract.getOperatingType() != null && projectExtract.getOperatingType() == 3){
        projectExtractListYes.add(projectExtract);
      } else {
        projectExtractListNo.add(projectExtract);
      }
    }
    //获取查询条件类型
    List<ExpExtCondition> listCondition = conditionService.list(new ExpExtCondition(cId, ""),null);
    List<ExtConType> conTypes = listCondition.get(0).getConTypes();
    if (conTypes != null && conTypes.size() == 1  &&  ( conTypes.get(0).getExpertsTypeId() == null || !"".equals(conTypes.get(0).getExpertsTypeId())) ){
      ProjectExtract projectExtrac = new ProjectExtract();
      projectExtrac.setConTypeId("1");
      projectExtrac.setExpertConditionId(listCondition.get(0).getId());
      List<ProjectExtract> peList = extractService.list(projectExtrac);
      conTypes.get(0).setAlreadyCount(peList == null ? 0 : peList.size());
    }else{
      for (ExtConType extConType1 : conTypes) {
        //获取抽取的专家类别
        ProjectExtract projectExtrac = new ProjectExtract();
        projectExtrac.setReviewType(extConType1.getExpertsTypeId());
        projectExtrac.setExpertConditionId(listCondition.get(0).getId());
        List<ProjectExtract> list2 = extractService.list(projectExtrac);
        extConType1.setAlreadyCount(list2 == null ? 0 : list2.size());
      }
    }
    model.addAttribute("extConType", conTypes);

    if (projectExtractListNo.size() != 0){
      projectExtractListYes.add(projectExtractListNo.get(0));
      projectExtractListNo.remove(0);
    }else{
      //已抽取
      //      conditionService.update(new ExpExtCondition(listCondition.get(0).getId(), (short)2));
    }
    model.addAttribute("extRelateListYes", projectExtractListYes);
    model.addAttribute("extRelateListNo", projectExtractListNo);
    return "ses/ems/exam/expert/extract/resultlist";
  }

  /**
   * @Description:返回结果
   *
   * @author Wang Wenshuai
   * @date 2016年9月19日 下午2:31:46  
   * @param @param model
   * @param @return      
   * @return String
   */
  @ResponseBody
  @RequestMapping("/resultextract")
  public Object resultextract(Model model,String id,String reason,HttpServletRequest sq,String[] packageId){
    //      修改状态
    String[] ids = id.split(",");

    if ("1".equals(ids[2])){
      ProjectExtract expExtRelate = extractService.getExpExtRelate(ids[0]);
      //获取抽取类型
      List<ExpExtCondition> conList =  conditionService.list(new ExpExtCondition(expExtRelate.getExpertConditionId(), "") , null);
      String expertTypeId = conList.get(0).getExpertsTypeId();
      //获取专家类型
      String expTypeId = expExtRelate.getExpert().getExpertsTypeId();
      //截取专家类型 如果满足insert
      if (expertTypeId != null && !"".equals(expertTypeId)){
        //抽取类型
        String[] expertTypeIdArray = expertTypeId.split(",");
        //专家类型
        String[] expTypeIdAry = expTypeId.split(",");
        ProjectExtract projectExtrac = new ProjectExtract();
        for (String typeId : expertTypeIdArray) {

          //获取抽取的专家类别
          projectExtrac.setReviewType(typeId);
          projectExtrac.setExpertConditionId(ids[1]);
          List<ProjectExtract> list = extractService.list(projectExtrac);
          //获取条件进行对比
          Integer counts = conTypeService.getExpertTypeById(ids[1], typeId) ;
          if(counts == null ){
            counts = 0;
          }
          if(counts !=0 && list.size() != 0 && list.size() >= counts){
            continue;
          }else{
            int tp = 0;
            for (String exptypeay : expTypeIdAry) {
              if(exptypeay.equals(typeId)){
                //修改为抽取的类型
                ProjectExtract extract = new ProjectExtract();
                extract.setReviewType(typeId);
                extract.setId(ids[0]);
                extractService.update(extract); 
                tp = 1;
              }
            }

            if(tp == 1){
              break; 
            }
          }

        }

      }else{
        //修改为抽取的类型
        ProjectExtract projectExtrac = new ProjectExtract();
        projectExtrac.setExpertConditionId(ids[1]);
        List<ProjectExtract> list = extractService.list(projectExtrac);
        if (list != null && list.size() != 0){
          ProjectExtract extract = new ProjectExtract();
          int i = 0;
          String[] split = list.get(0).getExpert().getExpertsTypeId().split(",");
          if(split.length > 1 ){
            int max=split.length-1;
            int min=0;
            Random random = new Random();
            i = random.nextInt(max)%(max-min+1) + min;
          }
          extract.setReviewType(split[i]);
          extract.setId(ids[0]);
          extractService.update(extract);
        }
      }


    }


    if (reason != null && !"".equals(reason)){
      extractService.update(new ProjectExtract(ids[0], new Short(ids[2]), reason ,packageId));

    } else {
      extractService.update(new ProjectExtract(ids[0], new Short(ids[2]) ,packageId));
    }
    if ("1".equals(ids[2])){
      ProjectExtract expExtRelate = extractService.getExpExtRelate(ids[0]);

      if ("1".equals(expExtRelate.getExpert().getStatus())){
        /**
         * 推送
         */
        Todos todos = new Todos();
        todos.setCreatedAt(new Date());
        todos.setIsDeleted((short)0);
        todos.setIsFinish((short)0);
        //待办名称
        todos.setName(expExtRelate.getExpert().getRelName()+"专家复审");
        //todos.setReceiverId();
        //接受人id
        todos.setOrgId(expExtRelate.getExpert().getPurchaseDepId());
        //权限id
        PropertiesUtil config = new PropertiesUtil("config.properties");
        todos.setPowerId(config.getString("zjdb"));
        //发送人id
        User user = (User)sq.getSession().getAttribute("loginUser");
        todos.setSenderId(user.getId());
        //类型
        todos.setUndoType((short)2);
        //发送人姓名
        todos.setSenderName(expExtRelate.getExpert().getRelName());
        //审核地址
        todos.setUrl("expertAudit/basicInfo.html?expertId=" + expExtRelate.getExpert().getId());
        todosService.insert(todos );
        Expert expert = new Expert();
        expert.setId(id);
        expert.setStatus("4");
        expertServices.updateByPrimaryKeySelective(expert);
      }
    }

    List<ProjectExtract> projectExtractListYes = resultProjectExtract(sq, ids);

    return projectExtractListYes;
  }


  /**
   *〈简述〉返回专家抽取方法
   *〈详细描述〉
   * @author Wang Wenshuai
   * @param sq HttpServletRequest
   * @param ids 集合  [0]关联表id [1]条件表id [2]参与不参与
   * @return List<ProjectExtract>
   */
  private List<ProjectExtract> resultProjectExtract(HttpServletRequest sq, String[] ids) {
    //存放已抽取的数量
    Map<String, Integer> mapcount = new HashMap<String, Integer>();
    //存放已操作
    List<ProjectExtract> projectExtractListYes = new ArrayList<ProjectExtract>();
    //未操作
    List<ProjectExtract> projectExtractListNo = new ArrayList<ProjectExtract>();
    //循环出抽取未抽取的
    forExtract(mapcount, ids[1], projectExtractListYes, projectExtractListNo, 0);
    //获取查询条件类型
    List<ExpExtCondition> listCondition = conditionService.list(new ExpExtCondition(ids[1], ""),null);
    //删除已经满足类型的
    List<String> expertTypeIds = new ArrayList<String>();
    for (ExtConType extConType1 : listCondition.get(0).getConTypes()) {
      //获取抽取的专家类别
      ProjectExtract projectExtrac = new ProjectExtract();
      projectExtrac.setReviewType(extConType1.getExpertsTypeId());
      projectExtrac.setExpertConditionId(ids[1]);
      List<ProjectExtract> list = extractService.list(projectExtrac);
      extConType1.setAlreadyCount(list == null ? 0 : list.size());
      //删除满足数量的
      if(list.size() >= extConType1.getExpertsCount()){
        expertTypeIds.add(extConType1.getExpertsTypeId());
      }
    }
    
    
    if (expertTypeIds != null && expertTypeIds.size() != 0){
      Packages packages = new Packages();
      packages.setId(listCondition.get(0).getProjectId());
      List<Packages> find = packagesService.find(packages);
      extractService.del(listCondition.get(0).getId(),find.get(0).getProjectId(),expertTypeIds);
    }

    //拿出数量和session中存放的数字进行对比
    ProjectExtract pe = new ProjectExtract();
    pe.setId(ids[0]);
    List<ProjectExtract> list2 = extractService.list(pe);
    ExtConType extConType = conTypeService.getExtConType(list2.get(0).getConTypeId());
    Integer sum = conTypeService.getSum(list2.get(0).getExpertConditionId());
    Integer count = mapcount.get(extConType.getId());
    if (count != null && count != 0){
      if (count >= sum){
        extractService.updateStatusCount("1",extConType.getId());
        //                删除为抽取的数据
        Packages packages = new Packages();
        packages.setId(list2.get(0).getProjectId());
        List<Packages> find = packagesService.find(packages);
        extractService.delPe(find.get(0).getProjectId());
        forExtract(mapcount, ids[1], projectExtractListYes, projectExtractListNo,1);
      } else {
        forExtract(mapcount, ids[1], projectExtractListYes, projectExtractListNo,1);
      }
    }
    List<ExtConType> conTypes = listCondition.get(0).getConTypes();


    if (conTypes != null && conTypes.size() == 1  &&  ( conTypes.get(0).getExpertsTypeId() == null || !"".equals(conTypes.get(0).getExpertsTypeId())) ){
      ProjectExtract projectExtrac = new ProjectExtract();
      projectExtrac.setConTypeId("1");
      projectExtrac.setExpertConditionId(listCondition.get(0).getId());
      List<ProjectExtract> peList = extractService.list(projectExtrac);
      conTypes.get(0).setAlreadyCount(peList == null ? 0 : peList.size());
    }
    projectExtractListYes.get(0).setConType(conTypes);
    if (projectExtractListNo.size() != 0){
      projectExtractListYes.add(projectExtractListNo.get(0));
    }else{
      //已抽取
      //      conditionService.update(new ExpExtCondition(ids[1],(short)2));
    }

    return projectExtractListYes;
  }
  /**
   * 
   *〈简述〉循环出抽取未抽取的
   *〈详细描述〉
   * @author yggc
   * @param mapcount
   * @param expertConditionId
   * @param projectExtractListYes
   * @param projectExtractListNo
   */
  private void forExtract(Map<String, Integer> mapcount, String expertConditionId,
                          List<ProjectExtract> projectExtractListYes,
                          List<ProjectExtract> projectExtractListNo,Integer type) {
    //每次进入方法清除数据
    projectExtractListYes.clear();
    projectExtractListNo.clear();
    //查询数据
    List<ProjectExtract> list = extractService.list(new ProjectExtract(expertConditionId));
    for (ProjectExtract projectExtract : list) {
      if (projectExtract.getOperatingType() != null && (projectExtract.getOperatingType()==1 || projectExtract.getOperatingType() == 2)){
        projectExtractListYes.add(projectExtract);
        if (type == 0){
          Integer conTypeId = mapcount.get(projectExtract.getConTypeId());
          if (conTypeId != null && conTypeId != 0){
            mapcount.put(projectExtract.getConTypeId(), conTypeId += 1);
          } else {
            mapcount.put(projectExtract.getConTypeId(), 1);
          }
        }
      } else if (projectExtract != null && projectExtract.getOperatingType() != null && projectExtract.getOperatingType() == 3){
        //不参与
        projectExtractListYes.add(projectExtract);
        if (type == 0){
          ExtConType extConType = conTypeService.getExtConType(projectExtract.getConTypeId());
          extractService.updateStatusCount("0",extConType.getId());
        }
      } else {
        projectExtractListNo.add(projectExtract);
      }
    }
  }   

  /**
   * @Description:专家抽取记录集合
   *
   * @author Wang Wenshuai
   * @version 2016年9月29日 下午2:11:25  
   * @param @param model
   * @param @return      
   * @return String
   */
  @RequestMapping("/resuleRecordlist")
  public String resuleRecord(Model model,ExpExtractRecord expExtractRecord,String page){
    List<ExpExtractRecord> listExtractRecord = expExtractRecordService.listExtractRecord(expExtractRecord,page!=null&&!page.equals("")?Integer.parseInt(page):1);
    model.addAttribute("listExtractRecord", new PageInfo<ExpExtractRecord>(listExtractRecord));
    model.addAttribute("expExtractRecord", expExtractRecord);
    return "ses/ems/exam/expert/extract/recordlist";
  }

  /**
   * @Description:抽取记录
   *
   * @author Wang Wenshuai
   * @version 2016年10月14日 下午7:29:36  
   * @param @param model
   * @param @param id
   * @param @return      
   * @return String
   */
  @RequestMapping("/showRecord")
  public String showRecord(Model model,String id,String projectId,String packageId,String typeclassId){
    //专家类型
    model.addAttribute("ddList", expExtractRecordService.ddList());
    model.addAttribute("find", DictionaryDataUtil.find(12));
    model.addAttribute("typeclassId", typeclassId);
    model.addAttribute("projectId", projectId);
    model.addAttribute("packageId", packageId);
    //获取抽取记录
    ExpExtractRecord showExpExtractRecord = null;
    if (id != null && !"".equals(id)) {
      showExpExtractRecord = expExtractRecordService.listExtractRecord(new ExpExtractRecord(id),0).get(0);
    }else{
      ExpExtractRecord record = new ExpExtractRecord();
      record.setProjectId(projectId);
      showExpExtractRecord =  expExtractRecordService.listExtractRecord(record,0).get(0);
    }

    if (packageId != null && !"".equals(packageId)){
      //已抽取
      String[] packageIds =  packageId.split(",");
      if(packageIds.length != 0 ){
        ExpExtCondition con = null;
        for (String pckId : packageIds) {
          if(pckId != null && !"".equals(pckId)){
            con = new ExpExtCondition();
            con.setProjectId(pckId);
            con.setStatus((short)2);
            conditionService.update(con);
          }
        }
      }
    }

    model.addAttribute("ExpExtractRecord", showExpExtractRecord);
    if(showExpExtractRecord !=null){
      //抽取条件
      List<Packages> conList = packagesService.listExpExtCondition(showExpExtractRecord.getProjectId());
      model.addAttribute("conditionList", conList);
      //获取监督人员
      List<ProExtSupervise>  listUser = projectSupervisorServicel.list(new ProExtSupervise(showExpExtractRecord.getProjectId()));
      model.addAttribute("listUser", listUser);
    }
    return "ses/ems/exam/expert/extract/show_info";
  }


  /**
   * @Description:抽取专家记录(流程)
   *
   * @author Wang Wenshuai
   * @version 2016年10月14日 下午7:29:36  
   * @param @param model
   * @param @param id
   * @param @return      
   * @return String
   */
  @RequestMapping("/record")
  public String Record(Model model,String projectId){
    //专家类型
    model.addAttribute("ddList", expExtractRecordService.ddList());
    ExpExtractRecord showExpExtractRecord = null;
    //获取抽取记录
    ExpExtractRecord expExtractRecord=new ExpExtractRecord();
    expExtractRecord.setProjectId(projectId);
    List<ExpExtractRecord> listExtractRecord = expExtractRecordService.listExtractRecord(expExtractRecord,0);
    if(listExtractRecord != null && listExtractRecord.size() != 0){
      showExpExtractRecord = listExtractRecord.get(0);
    }

    if(showExpExtractRecord != null  && showExpExtractRecord.getProjectId() != null){

      model.addAttribute("ExpExtractRecord", showExpExtractRecord);
      //抽取条件
      List<ExpExtCondition> conditionList = conditionService.list(new ExpExtCondition(showExpExtractRecord.getProjectId()),null);

      model.addAttribute("conditionList", conditionList);

      List<Packages> listResultExpert = packagesService.listResultExpert(projectId);
      model.addAttribute("listResultExpert", listResultExpert);

      //            List<Packages> listResultExpert = packagesService.listResultExpert(projectId);
      //            model.addAttribute("listResultExpert", listResultExpert);

    }
    return "ses/ems/exam/expert/extract/show_record";
  }



  /**
   * @Description:重置密码
   *
   * @author Wang Wenshuai
   * @version 2016年10月14日 下午7:29:36  
   * @param model  实体
   * @param  id 专家id
   */
  @ResponseBody
  @RequestMapping("/resetPwd")
  public String resetPwd(Model model, String[] eid){
    User user = null;
    for (String id : eid) {
      user = new User();
      user.setTypeId(id);
      List<User> queryByList = userServiceI.queryByList(user);
      if (queryByList != null && queryByList.size() != 0){
        user = queryByList.get(0);
        //根据随机码+密码加密
        Md5PasswordEncoder md5 = new Md5PasswordEncoder();     
        // false 表示：生成32位的Hex版, 这也是encodeHashAsBase64的, Acegi 默认配置; true  表示：生成24位的Base64版     
        md5.setEncodeHashAsBase64(false);     
        String pwd = md5.encodePassword("123456", user.getRandomCode());
        String userId = user.getId();
        user = new User();
        user.setPassword(pwd);
        user.setId(userId);
        userServiceI.update(user);
      }else{
        return JSON.toJSONString("error");
      }
    }
    return JSON.toJSONString("sccuess");

  }
  /**
   * @Description:展示添加临时专家页面
   *
   * @author Wang Wenshuai
   * @version 2016年10月14日 下午7:29:36  
   * @param model  实体
   * @param  id 专家id
   */
  @RequestMapping("/showTemporaryExpert")
  public  String showTemporaryExpert(Model model,String packageId,String projectId,String flowDefineId){
    model.addAttribute("packageId", packageId);
    model.addAttribute("projectId", projectId);
    model.addAttribute("expert", new Expert());
    model.addAttribute("flowDefineId", flowDefineId);
    //证件类型
    model.addAttribute("idType", DictionaryDataUtil.find(9));
    //专家类型
    model.addAttribute("ddList", expExtractRecordService.ddList());
    //获取专家
    Packages packages = new Packages();
    packages.setProjectId(projectId);
    List<Packages> find = packagesService.find(packages);
    model.addAttribute("packList", find);
    return "bss/prms/temporary_expert_add";
  }

  /**
   * @Description:添加临时专家
   *
   * @author Wang Wenshuai
   * @version 2016年10月14日 下午7:29:36  
   * @param model  实体
   * @param  id 专家id
   * @throws UnsupportedEncodingException 
   */
  @RequestMapping(value="/AddtemporaryExpert",produces = "text/html;charset=UTF-8")
  public  Object addTemporaryExpert(@Valid Expert expert, BindingResult result, Model model, String projectId,String packageId,String packageName, String loginName, String loginPwd,String flowDefineId,HttpServletRequest sq) throws UnsupportedEncodingException{
    //转码
    if (expert != null) {
      if(expert.getRelName() != null && !"".equals(expert.getRelName())){
        expert.setRelName(URLDecoder.decode(expert.getRelName(),"UTF-8"));
      }
      if(expert.getIdCardNumber() != null && !"".equals(expert.getIdCardNumber())){
        expert.setIdCardNumber(URLDecoder.decode(expert.getIdCardNumber(),"UTF-8"));
      }
      if(expert.getAtDuty() != null && !"".equals(expert.getAtDuty() )){
        expert.setAtDuty(URLDecoder.decode(expert.getAtDuty(),"UTF-8"));
      }
      if(expert.getMobile() != null && !"".equals(expert.getMobile())){
        expert.setMobile(URLDecoder.decode(expert.getMobile(),"UTF-8"));
      }
    }
    if (loginName != null && !"".equals(loginName)) {
      loginName = URLDecoder.decode(loginName,"UTF-8");
    }
    if (loginPwd != null && !"".equals(loginPwd)) {
      loginPwd = URLDecoder.decode(loginPwd,"UTF-8");
    }
    if (packageName != null && !"".equals(packageName)) {
      packageName = URLDecoder.decode(packageName,"UTF-8");
    }
    Integer type = 0;
    //校验字段
    if (result.hasErrors()){
      type = 1;
    }
    if (loginName == null || "".equals(loginName)){
      model.addAttribute("loginNameError", "不能为空");
      if (loginName == null || !loginName.matches("^\\w{6,20}$")) {
        model.addAttribute("loginNameError", "登录名由6-20位字母数字和下划线组成 !");
      }
      type = 1;
    }else{
      //校验用户名是否存在
      List<User> users = userService.findByLoginName(loginName);
      if (users.size() > 0){
        type = 1;
        model.addAttribute("loginNameError", "用户名已存在");
      }
    }

    if (loginPwd == null || "".equals(loginPwd)) {
      model.addAttribute("loginPwdError", "不能为空");
      if (loginPwd == null || !loginPwd.matches("^\\w{6,20}$")) {
        model.addAttribute("loginPwdError", "密码由6-20位字母数字和下划线组成 !");
      }
      type = 1;
    }

    if(packageId == null || "".equals(packageId)){

      model.addAttribute("packageIdError", "不能为空");
      type = 1;
    }

    if(expert.getIdCardNumber() != null && !"".equals(expert.getIdCardNumber())){
      List<Expert> validateIdNumber = expertServices.validateIdCardNumber(expert.getIdCardNumber(), null);
      if(validateIdNumber != null && validateIdNumber.size() != 0){
        model.addAttribute("idCardNumberError", "已被占用");
        type = 1;
      }

    }


    if (type == 1){
      model.addAttribute("expert", expert);
      model.addAttribute("loginName", loginName);
      model.addAttribute("loginPwd", loginPwd);
      model.addAttribute("projectId", projectId);
      model.addAttribute("packageId", packageId);
      model.addAttribute("packageName", packageName);
      model.addAttribute("flowDefineId", flowDefineId);
      //专家类型
      model.addAttribute("ddList", expExtractRecordService.ddList());
      //证件类型
      model.addAttribute("idType", DictionaryDataUtil.find(9));
      return "bss/prms/temporary_expert_add";
    }


    expExtractRecordService.addTemporaryExpert(expert, projectId,packageId, loginName, loginPwd,sq);
    //修改状态
    flowMangeService.flowExe(sq, flowDefineId, projectId, 2);

    return  "redirect:/packageExpert/assignedExpert.html?projectId=" + projectId + "&&flowDefineId=" + flowDefineId;
  }



  /**
   * 
   *〈简述〉
   *〈详细描述〉
   * @author Wang Wenshuai
   * @return
   */
  @ResponseBody
  @RequestMapping("/findType")
  public  Object findType(HttpServletResponse response){

    // 查询供应商所有类型
    DictionaryData dictionaryData=new DictionaryData();
    dictionaryData.setKind(6);
    List<DictionaryData> ldlist1 = dictionaryDataServiceI.find(dictionaryData);
    dictionaryData.setKind(8);
    List<DictionaryData> ldlist2 = dictionaryDataServiceI.find(dictionaryData);

    List<SupplierTypeTree> listSupplierTypeTrees = new ArrayList<SupplierTypeTree>();

    for (DictionaryData dd : ldlist1) {
      SupplierTypeTree supplierTypeTree = new SupplierTypeTree();
      if("GOODS".equals(dd.getCode())){
        for (DictionaryData dd1 : ldlist2) {
          SupplierTypeTree supplierTypeTree1 = new SupplierTypeTree();
          supplierTypeTree1.setId(dd1.getCode());
          supplierTypeTree1.setName(dd1.getName());
          supplierTypeTree1.setParentId(dd.getCode());
          listSupplierTypeTrees.add(supplierTypeTree1);
        }
        supplierTypeTree.setParent(true);
      }
      supplierTypeTree.setId(dd.getCode());
      supplierTypeTree.setName(dd.getName());

      listSupplierTypeTrees.add(supplierTypeTree);
    }

    return JSON.toJSONString(listSupplierTypeTrees);
  }

  /**
   * 
   *〈简述〉获取专家类型
   *〈详细描述〉
   * @author Wang Wenshuai
   * @return
   */
  @ResponseBody
  @RequestMapping("projectType")
  public String projectType(){
    List<DictionaryData> ddList = expExtractRecordService.ddList();
    return  JSON.toJSONString(ddList);
  }

  /**
   * 
   *〈简述〉获取专家来源
   *〈详细描述〉
   * @author Wang Wenshuai
   * @return
   */
  @ResponseBody
  @RequestMapping("expertsFrom")
  public String expertsFrom(){
    List<DictionaryData> expertsFrom = DictionaryDataUtil.find(12);
    return JSON.toJSONString(expertsFrom);
  }

  /**
   * 
   *〈简述〉是否已经抽取完成
   *〈详细描述〉
   * @author Wang Wenshuai
   * @return
   */
  @ResponseBody
  @RequestMapping("isFinish")
  public String isFinish(String packageId){
    //获取查询条件类型
    ExpExtCondition condition = new ExpExtCondition();
    condition.setProjectId(packageId);
    condition.setStatus((short)1);
    String finish = conditionService.isFinish(condition);
    return JSON.toJSONString(finish);
  }

  /**
   * 
   *〈简述〉保存监督人员
   *〈详细描述〉
   * @author Wang Wenshuai
   * @return
   */
  @ResponseBody
  @RequestMapping("saveSupervise")
  public String saveSupervise(String[] relName,String[] company, String[] phone,String[] duties,String projectId,String type){
    //返回抽监督人id
    String superviseId = "";
    //姓名
    String strRelName="";
    Map<String, String> map = new HashMap<String, String>();
    ProExtSupervise ps=null;
    SupplierExtUser eu=null;
    if(relName.length == 0 || phone.length ==0 || company.length == 0){
      return ERROR;
    }
    if(projectId != null && !"".equals(projectId)){
      projectSupervisorServicel.deleteProjectId(projectId);
      extUserServicel.deleteProjectId(projectId);
    }
    for (int i = 0; i < relName.length; i++ ) {
      ps = new ProExtSupervise();
      eu = new SupplierExtUser();
      if(company[i] == null || "".equals(company[i])){
        return ERROR;
      }
      if(phone[i] == null || "".equals(phone[i])){
        return ERROR;
      }
      if(relName[i] == null || "".equals(relName[i])){
        return ERROR;
      }
      if(duties[i] == null || "".equals(duties[i])){
        return ERROR;
      }

      if(type != null && "supplier".equals(type)){
        //供应商
        eu.setCompany(company[i]);
        eu.setPhone(phone[i]);
        eu.setRelName(relName[i]);
        if(projectId != null && !"".equals(projectId)){

          eu.setProjectId(projectId);
        }
        eu.setDuties(duties[i]);
        strRelName+=relName[i]+",";
        extUserServicel.insert(eu);
        superviseId += eu.getId() + ",";
      }else{
        //专家
        ps.setCompany(company[i]);
        ps.setPhone(phone[i]);
        ps.setRelName(relName[i]);
        if(projectId != null && !"".equals(projectId)){

          eu.setProjectId(projectId);
        }
        ps.setProjectId(projectId);
        ps.setDuties(duties[i]);
        strRelName+=relName[i]+",";
        projectSupervisorServicel.insert(ps);
        superviseId += ps.getId() + ",";
      }


    }
    map.put("relName", strRelName.substring(0, strRelName.length()-1));
    map.put("superviseId", superviseId.substring(0, superviseId.length()-1));
    return JSON.toJSONString(map);
  }

  /**
   * @Description:显示监督人员
   *
   * @author Wang Wenshuai
   * @version 2016年9月25日 09:49:56 
   * @return String
   */
  @RequestMapping("/showSupervise")
  public String showSupervise(Model model,String projectId){
    if(projectId != null && !"".equals(projectId)){
      model.addAttribute("projectId", projectId);
      List<ProExtSupervise> list = projectSupervisorServicel.list(new ProExtSupervise(projectId));
      model.addAttribute("list", list);
    }
    return "ses/sms/supplier_extracts/supervise_list";
  }




  /**
   * 
   *〈简述〉获取品目树
   *〈详细描述〉
   * @author Wang Wenshuai
   * @return
   */
  @ResponseBody
  @RequestMapping("/getTree")
  public String getTree(Category category,String[] type){
    List<CategoryTree> jList=new ArrayList<CategoryTree>();
    //获取字典表中的根数据
    if (category.getId() == null || "0".equals(category.getId())){
      if (type != null && !"".equals(type)){
        //获取关联包id
        for (String  strType : type) {
          DictionaryData dictionaryData = dictionaryDataServiceI.getDictionaryData(strType);
          if(dictionaryData.getKind()!=19){
            CategoryTree ct = new CategoryTree();
            ct.setId(dictionaryData.getId());
            ct.setName(dictionaryData.getName());
            ct.setCode(dictionaryData.getCode());
            ct.setIsParent("true");
            jList.add(ct);
          }
        }
      } else {
        category.setId("0");
        DictionaryData data = new DictionaryData();
        data.setKind(6);
        List<DictionaryData> listByPage = dictionaryDataServiceI.listByPage(data, 1);
        for (DictionaryData dictionaryData : listByPage) {
          CategoryTree ct = new CategoryTree();
          ct.setId(dictionaryData.getId());
          ct.setName(dictionaryData.getName());
          ct.setCode(dictionaryData.getCode());
          ct.setIsParent("true"); 
          jList.add(ct);
        }

      }
      return JSON.toJSONString(jList);
    }   
    String list ="";
    List<Category> cateList = categoryService.findTreeByPid(category.getId());
    for (Category cate:cateList){
      List<Category> cList = categoryService.findTreeByPid(cate.getId());
      CategoryTree ct = new CategoryTree();
      if (!cList.isEmpty()){
        ct.setIsParent("true");
      }else{
        ct.setIsParent("false");
      }
      ct.setId(cate.getId());
      ct.setName(cate.getName());
      ct.setpId(cate.getParentId());
      ct.setKind(cate.getKind());
      ct.setStatus(cate.getStatus());
      jList.add(ct);
    }
    list = JSON.toJSONString(jList);
    return list;
  }

  /**
   * @Description:弹出限制条件和类别抽取数量
   *
   * @author Wang Wenshuai
   * @version 2016年9月25日 09:49:56 
   * @return String
   */
  @RequestMapping("/reasonnumber")
  public String reasonNumber(Model model,String[] expertsTypeCode,String addressReson,String eCount){
    model.addAttribute("expertsTypeCode", expertsTypeCode);
    model.addAttribute("addressReson", addressReson);
    model.addAttribute("eCount", eCount);
    return "ses/ems/exam/expert/extract/reason_and_number";
  }
}
