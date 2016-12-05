/**
 * 
 */
package ses.controller.sys.ems;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import ses.model.bms.DictionaryData;
import ses.model.bms.User;
import ses.model.ems.ExpExtCondition;
import ses.model.ems.ExpExtPackage;
import ses.model.ems.ExpExtractRecord;
import ses.model.ems.Expert;
import ses.model.ems.ExtConType;
import ses.model.ems.ProExtSupervise;
import ses.model.ems.ProjectExtract;
import ses.model.sms.SupplierExtPackage;
import ses.model.sms.SupplierTypeTree;
import ses.service.bms.AreaServiceI;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.bms.UserServiceI;
import ses.service.ems.ExpExtConditionService;
import ses.service.ems.ExpExtPackageService;
import ses.service.ems.ExpExtractRecordService;
import ses.service.ems.ExpertService;
import ses.service.ems.ExtConTypeService;
import ses.service.ems.ProjectExtractService;
import ses.service.ems.ProjectSupervisorServicel;
import ses.service.sms.SupplierTypeService;
import ses.util.DictionaryDataUtil;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import bss.controller.base.BaseController;
import bss.model.ppms.Packages;
import bss.model.ppms.Project;
import bss.service.ppms.FlowMangeService;
import bss.service.ppms.PackageService;
import bss.service.ppms.ProjectService;

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
    @RequestMapping("/projectlist")
    public String list(Integer page,Model model,Project project){
        List<Project> list = projectService.provisionalList(page == null?1:page, project);
        List<DictionaryData> find = DictionaryDataUtil.find(5);
        PageInfo<Project> info = new PageInfo<>(list);
        model.addAttribute("info", info);
        model.addAttribute("projects", project);
        model.addAttribute("kind", find);
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
    public String listExtraction(Model model,String id,String page,String typeclassId){
        List<Area> listArea = areaService.findTreeByPid("0",null);
        model.addAttribute("listArea", listArea);
        model.addAttribute("typeclassId",typeclassId);
        if (id != null && !"".equals(id)){
            ExpExtPackage expExtPackage = new ExpExtPackage();
            expExtPackage.setId(id);
            List<ExpExtPackage> list = expExtPackageServicel.list(expExtPackage,"0");
            if (list != null && list.size() != 0 && list.get(0) != null && list.get(0).getProject() != null ){
                //获取监督人员
                List<User>  listUser = projectSupervisorServicel.list(new ProExtSupervise(id));
                model.addAttribute("listUser", listUser);
                String userName = "";
                String userId = "";
                if (listUser != null && listUser.size() != 0){
                    for (User user : listUser) {
                        userName += user.getLoginName() + ",";
                        userId += user.getId() + ",";
                    }
                    model.addAttribute("userName", userName.substring(0, userName.length()-1));
                    model.addAttribute("userId", userId.substring(0, userId.length()-1));
                }
                //供应商抽取地址
                ExpExtractRecord record = new ExpExtractRecord();
                //存放包id
                record.setProjectId(list.get(0).getProjectId());
                //                PageHelper.startPage(1, 1);
                List<ExpExtractRecord> listSe = expExtractRecordService.listExtractRecord(record,0);
                if (listSe != null && listSe.size() != 0){
                    model.addAttribute("extractionSites", listSe.get(0).getExtractionSites());
                    String[] atime = listSe.get(0).getResponseTime() != null ? listSe.get(0).getResponseTime().split(",") : null;
                    if (atime != null && atime.length >= 2 ){
                        model.addAttribute("hour", atime[0]);
                        model.addAttribute("minute", atime[1]);
                    }
                }
                //                 存放的包id
                model.addAttribute("projectId", id);
                model.addAttribute("projectName", list.get(0).getProject().getName());
                model.addAttribute("projectNumber", list.get(0).getProject().getProjectNumber());
                model.addAttribute("packageName", list.get(0).getPackages().getName());
                model.addAttribute("bidDate", list.get(0).getProject().getBidDate());

                //获取采购方式
                List<DictionaryData>  dictionaryData = new ArrayList<DictionaryData>();
                dictionaryData.add(dictionaryDataServiceI.getDictionaryData(list.get(0).getProject().getPurchaseType()));
                model.addAttribute("findByMap", dictionaryData);
            }

            //条件集合
            List<ExpExtCondition> listCon = conditionService.list(new ExpExtCondition(id), page == null ? 1 : Integer.valueOf(page));
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
    public String addExtraction(Model model,String projectId,String typeclassId){
        List<Area> listArea = areaService.findTreeByPid("0",null);
        model.addAttribute("listArea", listArea);
        model.addAttribute("typeclassId", typeclassId);
        model.addAttribute("projectId", projectId);
        List<DictionaryData> find = DictionaryDataUtil.find(12);
        model.addAttribute("find", find);
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
    public String validateAddExtraction(Project project, String packageName, String typeclassId, String[] sids, String extAddress,HttpServletRequest rq){
        Map<String, String> map = new HashMap<String, String>();
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

        if (packageName == null || "".equals(packageName)){
            map.put("packageNameError", "不能为空");
            count = 1;
        }

        if (sids == null || sids.length == 0 || "".equals(sids)){
            map.put("supervise", "不能为空");
            count = 1;
        }
        //时
        String hour = rq.getParameter("hour");
        //分
        String minute = rq.getParameter("minute");
        if (hour == null || "".equals(hour) || minute == null || "".equals(minute)){
            map.put("responseTimeError", "不能为空");
            count = 1;
        }
//        String tenderTime = rq.getParameter("tenderTime");
//        if (tenderTime == null || "".equals(tenderTime)){
//            map.put("tenderTimeError", "不能为空");
//            count = 1;
//        }

        if (count == 1){

            return JSON.toJSONString(map);

        } else{
            //真实的项目id
            String projectId = project.getId();
            String packageId = "";
            if (project.getId() == null || "".equals(project.getId())){
                // 创建一个临时项目临时包
                project.setIsProvisional(1);
                projectService.add(project);
                projectId = project.getId();
                Packages packages = new Packages();
                packages.setName(packageName);
                packages.setProjectId(project.getId());
                packages.setIsDeleted(0);
                packagesService.insertSelective(packages);
                packageId = packages.getId();
                ExpExtPackage sExtPackage = new ExpExtPackage();
                sExtPackage.setPackageId(packages.getId());
                sExtPackage.setProjectId(project.getId());

                expExtPackageServicel.insert(sExtPackage);
                project.setId(sExtPackage.getId());
            }
            //抽取地址
            if (extAddress != null && !"".equals(extAddress)){

                ExpExtractRecord extractRecord = new ExpExtractRecord();
                //包id
                if (projectId != null && !"".equals(projectId)){
                    ExpExtPackage byId = expExtPackageServicel.getById(projectId);
                    if (byId != null && byId.getProjectId() != null){
                        projectId = byId.getProjectId();
                        packageId = byId.getPackageId();
                    }
                }
                extractRecord.setProjectId(projectId);
                PageHelper.startPage(1, 1);
                List<ExpExtractRecord> listSe = expExtractRecordService.listExtractRecord(extractRecord,0);
                extractRecord.setExtractionSites(extAddress);
                extractRecord.setResponseTime(hour + "," + minute);
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
                projectSupervisorServicel.deleteProjectId(packageId);
                for (String id : sids) {
                    if (!"".equals(id)){
                        ProExtSupervise  record1 = new ProExtSupervise();
                        record1.setProjectId(packageId);
                        record1.setSupviseId(id);
                        projectSupervisorServicel.insert(record1);
                    }
                }
            }
            //获取抽取条件状态，未抽取不能在抽取
            Integer count2 = conditionService.getCount(project.getId());
            if (count2 != null && count2 != 0){
                map.put("status", count2.toString());
            } else {
                map.put("projectId", project.getId());
                map.put("sccuess", "SCCUESS");
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
    public String addHeading(Model model, String[] id){
        ExtConType extConType=null;
        if(id!=null&&id.length!=0){
            extConType=new ExtConType();
            extConType.setCategoryId(id[0]);
            extConType.setExpertsTypeId(new Short(id[1]));
            extConType.setExpertsCount(Integer.parseInt(id[2]));
            extConType.setExpertsQualification(id[3]);
        }
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
        if (list == null || list.size() == 0){
            extractService.insert(cId, user != null && !"".equals(user.getId()) ? user.getId() : "");
            conditionService.update(new ExpExtCondition(cId, (short)2));
            list = extractService.list(new ProjectExtract(cId));
        }
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
        for (ExtConType extConType : conTypes) {
            extConType.setAlreadyCount(mapcount.get(extConType.getId()) == null ? 0 : mapcount.get(extConType.getId()));
        }
        model.addAttribute("extConType", conTypes);

        if (projectExtractListNo.size() != 0){
            projectExtractListYes.add(projectExtractListNo.get(0));
            projectExtractListNo.remove(0);
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
    public Object resultextract(Model model,String id,String reason,HttpServletRequest sq){
        //      修改状态
        String[] ids = id.split(",");
        if (reason != null && !"".equals(reason)){
            extractService.update(new ProjectExtract(ids[0], new Short(ids[2]), reason));
        } else {
            extractService.update(new ProjectExtract(ids[0], new Short(ids[2])));
        }



        List<ProjectExtract> projectExtractListYes = resultProjectExtract(sq, ids);

        return projectExtractListYes;
    }

    /**
     *〈简述〉返回专家抽取方法
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param sq HttpServletRequest
     * @param ids 集合  [1]关联表id [2]条件表id [3]参与不参与
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
        //拿出数量和session中存放的数字进行对比
        ProjectExtract pe = new ProjectExtract();
        pe.setId(ids[0]);
        List<ProjectExtract> list2 = extractService.list(pe);
        ExtConType extConType = conTypeService.getExtConType(list2.get(0).getConTypeId());
        Integer count = mapcount.get(extConType.getId());
        if (count != null && count != 0){
            if (count >= extConType.getExpertsCount()){
                extractService.updateStatusCount("1",extConType.getId());
                forExtract(mapcount, ids[1], projectExtractListYes, projectExtractListNo,1);
            } else {
                forExtract(mapcount, ids[1], projectExtractListYes, projectExtractListNo,1);
            }
        }
        //获取查询条件类型
        List<ExpExtCondition> listCondition = conditionService.list(new ExpExtCondition(ids[1], ""),null);
        List<ExtConType> conTypes = listCondition.get(0).getConTypes();
        for (ExtConType extConType1 : conTypes) {
            extConType1.setAlreadyCount(mapcount.get(extConType1.getId()) == null ? 0 : mapcount.get(extConType1.getId()));
        }
        projectExtractListYes.get(0).setConType(conTypes);
        if (projectExtractListNo.size() != 0){
            projectExtractListYes.add(projectExtractListNo.get(0));
        }else{
            //已抽取
            conditionService.update(new ExpExtCondition(ids[1],(short)3));
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
    public String showRecord(Model model,String id){
        //获取抽取记录
        ExpExtractRecord showExpExtractRecord = expExtractRecordService.listExtractRecord(new ExpExtractRecord(id),0).get(0);
        model.addAttribute("ExpExtractRecord", showExpExtractRecord);
        if(showExpExtractRecord !=null){
          //抽取条件
            ExpExtPackage extPackage = new ExpExtPackage();
            extPackage.setProjectId(showExpExtractRecord.getProjectId());
            List<ExpExtPackage> conditionList = expExtPackageServicel.extractsList(extPackage);
            model.addAttribute("conditionList", conditionList);
            //获取监督人员
            if (conditionList != null && conditionList.size() != 0){
                List<User>  listUser = null ;
                for (ExpExtPackage expExtPackage : conditionList) {
                    listUser =  projectSupervisorServicel.list(new ProExtSupervise(expExtPackage.getId()));
                    if (listUser != null ){
                        break;
                    }
                }
                model.addAttribute("listUser", listUser);  
            }
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
    public String resetPwd(Model model, String eid){
        User user = new User();
        user.setTypeId(eid);
        user.setTypeName(DictionaryDataUtil.get("EXPERT_U").getId());
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
            return JSON.toJSONString("sccuess");
        }else{
            return JSON.toJSONString("error");
        }
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
        return "bss/prms/temporary_expert_add";
    }

    /**
     * @Description:添加临时专家
     *
     * @author Wang Wenshuai
     * @version 2016年10月14日 下午7:29:36  
     * @param model  实体
     * @param  id 专家id
     */
    @RequestMapping("/AddtemporaryExpert")
    public  Object addTemporaryExpert(@Valid Expert expert, BindingResult result, Model model, String projectId,String packageId, String loginName, String loginPwd,String flowDefineId,HttpServletRequest sq){
        Integer type = 0;
        //校验字段
        if (result.hasErrors()){
            type = 1;
        }
        if (loginName == null || "".equals(loginName)){
            model.addAttribute("loginNameError", "不能为空");
            type = 1;
        }else{
            //校验用户名是否存在
            List<User> users = userService.findByLoginName(loginName);
            if (users.size() > 0){
                type = 1;
                model.addAttribute("loginNameError", "用户名已存在");
            }
        }

        if (loginPwd == null || "".equals(loginPwd)){
            model.addAttribute("loginPwdError", "不能为空");
            type = 1;
        }



        if (type == 1){
            model.addAttribute("expert", expert);
            model.addAttribute("loginName", loginName);
            model.addAttribute("loginPwd", loginPwd);
            model.addAttribute("projectId", projectId);
            model.addAttribute("packageId", packageId);
            //证件类型
            model.addAttribute("idType", DictionaryDataUtil.find(9));
            return "bss/prms/temporary_expert_add";
        }


        expExtractRecordService.addTemporaryExpert(expert, projectId,packageId, loginName, loginPwd);
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
}
