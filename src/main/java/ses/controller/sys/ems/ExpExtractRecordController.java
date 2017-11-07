/**
 * 
 */
package ses.controller.sys.ems;


import bss.controller.base.BaseController;
import bss.model.ppms.Packages;
import bss.model.ppms.Project;
import bss.service.ppms.FlowMangeService;
import bss.service.ppms.PackageService;
import bss.service.ppms.ProjectService;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import common.constant.StaticVariables;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
import ses.model.ems.ExtConType;
import ses.model.ems.ProExtSupervise;
import ses.model.ems.ProjectExtract;
import ses.model.oms.Orgnization;
import ses.model.sms.SupplierExtUser;
import ses.model.sms.SupplierTypeTree;
import ses.service.bms.AreaServiceI;
import ses.service.bms.CategoryService;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.bms.TodosService;
import ses.service.bms.UserServiceI;
import ses.service.ems.ExpExtConditionService;
import ses.service.ems.ExpExtPackageService;
import ses.service.ems.ExpExtractRecordService;
import ses.service.ems.ExpertAuditService;
import ses.service.ems.ExpertService;
import ses.service.ems.ExtConTypeService;
import ses.service.ems.ProjectExtractService;
import ses.service.ems.ProjectSupervisorServicel;
import ses.service.oms.OrgnizationServiceI;
import ses.service.sms.SupplierExtUserServicel;
import ses.util.DictionaryDataUtil;
import ses.util.PropertiesUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 *  @Description:专家抽取    @author Wang Wenshuai  @version 2016年9月27日下午4:34:37
 * 
 * @since JDK 1.7
 */
@Controller
@Scope("prototype")
@RequestMapping("/ExpExtract")
public class ExpExtractRecordController extends BaseController {

    /** ERROR */
    private static final String ERROR = "ERROR";

    /** 包 **/
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
    ExpertService ExpertService;// 专家管理

    @Autowired
    ProjectExtractService extractService; // 关联表

    @Autowired
    ExpExtractRecordService expExtractRecordService;

    @Autowired
    ProjectSupervisorServicel projectSupervisorServicel;

    @Autowired
    UserServiceI userServiceI;// 用户管理

    /** 关联包 **/
    @Autowired
    private ExpExtPackageService expExtPackageServicel;

    @Autowired
    private UserServiceI userService;

    @Autowired
    private FlowMangeService flowMangeService;// 环节

    @Autowired
    private CategoryService categoryService; // 品目

    /** 待办消息 **/
    @Autowired
    private TodosService todosService;

    @Autowired
    ExpertAuditService expertAuditService;

    @Autowired
    ExpertService expertServices;

    @Autowired
    private SupplierExtUserServicel extUserServicel; // 监督人员

    @Autowired
    private OrgnizationServiceI orgnizationService;

    @Autowired
    private ProjectExtractService projectExtractService;
    
    @Autowired
    private DictionaryDataServiceI dictionaryDataService;

    /**
     * 〈简述〉获取项目下的所有包。 〈详细描述〉
     * 
     * @author Wang Wenshuai
     * @param model
     * @param projectId 项目id
     * @param page
     * @return 路径
     */
    @RequestMapping("/packageList")
    public String packageList(Model model, String projectId, String packName, String page,
                              String typeclassId) {
        if (projectId != null && !"".equals(projectId)) {
            model.addAttribute("packName", packName);
            model.addAttribute("projectId", projectId);
            model.addAttribute("typeclassId", typeclassId);
            ExpExtPackage extPackage = new ExpExtPackage();
            extPackage.setProjectId(projectId);
            Packages packages = new Packages();
            packages.setName(packName);
            extPackage.setPackages(packages);
            List<ExpExtPackage> list = expExtPackageServicel.list(extPackage,
                page == null || "".equals(page) ? "1" : page);
            model.addAttribute("info", new PageInfo<ExpExtPackage>(list));
            return "ses/ems/exam/expert/extract/ext_package";
        } else {
            return "redirect:Extraction.html";
        }
    }

    /**
     * @Description: 获取项目集合
     * @author Wang Wenshuai
     * @version 2016年9月27日 下午4:38:31
     * @param @param page
     * @param @param model
     * @param @param project
     * @param @return
     * @return String
     */
    @RequestMapping("/projectList")
    public String list(Integer page, Model model, Project project, String typeclassId) {
        List<Project> list = projectService.provisionalList(page == null ? 1 : page, project);
        List<DictionaryData> find = DictionaryDataUtil.find(5);
        PageInfo<Project> info = new PageInfo<>(list);
        model.addAttribute("info", info);
        model.addAttribute("projects", project);
        model.addAttribute("kind", find);
        model.addAttribute("typeclassId", typeclassId);
        return "ses/ems/exam/expert/extract/project_list";
    }

    /**
     * 〈简述〉专家抽取 〈详细描述〉
     * 
     * @author FengTian
     * @param model
     * @param projectId
     * @return
     */
    @RequestMapping("/Extraction")
    public String listExtraction(Model model, String projectId) {
        if (StringUtils.isNotBlank(projectId)) {
            Project project = projectService.selectById(projectId);
            if (project != null && StringUtils.isNotBlank(project.getPurchaseType())) {
                DictionaryData findById = DictionaryDataUtil.findById(project.getPurchaseType());
                if (findById != null) {
                    project.setPurchaseType(findById.getName());
                }
                model.addAttribute("project", project);
            }
            
            // 根据包获取抽取出的专家
            List<Map<String, Object>> list = projectExtractService.selectProExpert(projectId);
            if (list != null && !list.isEmpty()) {
                for (Map<String, Object> map : list) {
                    for (Entry<String, Object> entry : map.entrySet()) {
                        if ("REVIEWTYPE".equals(entry.getKey())) {
                            String[] name = ((String)entry.getValue()).split(StaticVariables.COMMA_SPLLIT);
                            List<String> typeNames = new ArrayList<String>();
                            for (String string : name) {
                                String typeName = DictionaryDataUtil.findById(string).getName();
                                typeNames.add(typeName);
                            }
                            entry.setValue(StringUtils.join(typeNames, StaticVariables.COMMA_SPLLIT));
                        }
                    }
                }
                model.addAttribute("list", list);
            }
        }
        return "ses/ems/exam/expert/extract/condition_list";
    }

    /**
     * @Description:添加查询条件
     * @author Wang Wenshuai
     * @version 2016年9月27日 下午6:04:26
     * @param @param id
     * @param @return
     * @return String
     */
    @RequestMapping("/addExtractions")
    public String addExtraction(Model model, String projectId, String typeclassId,
                                String[] packageId) {
        List<Area> privnce = areaService.findRootArea();
        model.addAttribute("privnce", privnce);
        model.addAttribute("typeclassId", typeclassId);
        model.addAttribute("projectId", projectId);
        String packIds = "";
        for (String packId : packageId) {
            packIds += packId + ",";
        }
        model.addAttribute("packageId", packIds);

        // 数据字典。专家来源
        model.addAttribute("find", DictionaryDataUtil.find(12));
        // 专家类型
        model.addAttribute("ddList", expExtractRecordService.ddList());
        // 专家类型
        model.addAttribute("ddListJson", JSON.toJSONString(expExtractRecordService.ddList()));

        // 获取查询条件类型
        ExpExtCondition condition = new ExpExtCondition();
        condition.setProjectId(packageId[0]);
        condition.setStatus((short)1);
        List<ExpExtCondition> listCon = conditionService.list(condition, 0);
        if (listCon != null && listCon.size() != 0) {
            // 条件
            model.addAttribute("listCon", listCon.get(0));

            // 所在地区回显
            // if (listCon.get(0).getAddress() != null && listCon.get(0).getAddress() != null ){
            // Area area = areaService.listById(listCon.get(0).getAddress());
            // List<Area> city = areaService.findAreaByParentId(area.getParentId());
            // model.addAttribute("city", city);
            // model.addAttribute("area", area);
            // }

            Map<String, Integer> mapcount = new HashMap<String, Integer>();
            Integer sum = conTypeService.getSum(listCon.get(0).getId());
            model.addAttribute("sumCount", sum);
            PageHelper.startPage(1, sum * 2);
            List<ProjectExtract> list = extractService.list(new ProjectExtract(
                listCon.get(0).getId()));
            // 已操作的
            List<ProjectExtract> projectExtractListYes = new ArrayList<ProjectExtract>();
            // 未操作的
            List<ProjectExtract> projectExtractListNo = new ArrayList<ProjectExtract>();
            for (ProjectExtract projectExtract : list) {
                if (projectExtract.getOperatingType() != null
                    && (projectExtract.getOperatingType() == 1 || projectExtract.getOperatingType() == 2)) {
                    projectExtractListYes.add(projectExtract);
                    Integer conTypeId = mapcount.get(projectExtract.getConTypeId());
                    if (conTypeId != null && conTypeId != 0) {
                        mapcount.put(projectExtract.getConTypeId(), conTypeId += 1);
                    } else {
                        mapcount.put(projectExtract.getConTypeId(), 1);
                    }
                } else if (projectExtract.getOperatingType() != null
                           && projectExtract.getOperatingType() == 3) {
                    projectExtractListYes.add(projectExtract);
                } else {
                    projectExtractListNo.add(projectExtract);
                }
            }
            // 获取查询条件类型
            List<ExtConType> conTypes = listCon.get(0).getConTypes();
            if (conTypes != null
                && conTypes.size() == 1
                && (conTypes.get(0).getExpertsTypeId() == null || !"".equals(conTypes.get(0).getExpertsTypeId()))) {
                ProjectExtract projectExtrac = new ProjectExtract();
                projectExtrac.setConTypeId("1");
                projectExtrac.setExpertConditionId(listCon.get(0).getId());
                List<ProjectExtract> peList = extractService.list(projectExtrac);
                conTypes.get(0).setAlreadyCount(peList == null ? 0 : peList.size());
            } else {
                for (ExtConType extConType1 : conTypes) {
                    // 获取抽取的专家类别
                    ProjectExtract projectExtrac = new ProjectExtract();
                    projectExtrac.setReviewType(extConType1.getExpertsTypeId());
                    projectExtrac.setExpertConditionId(listCon.get(0).getId());
                    projectExtrac.setReason("1,2");
                    List<ProjectExtract> list2 = extractService.list(projectExtrac);
                    extConType1.setAlreadyCount(list2 == null ? 0 : list2.size());
                }
            }
            model.addAttribute("extConType", conTypes);
            model.addAttribute("extConTypeJson", JSON.toJSONString(conTypes));

            if (projectExtractListNo.size() != 0) {
                projectExtractListYes.add(projectExtractListNo.get(0));
                projectExtractListNo.remove(0);
            } else {
                // 已抽取
                // conditionService.update(new ExpExtCondition(condition.getId(), (short)2));
            }
            model.addAttribute("extRelateListYes", projectExtractListYes);
            model.addAttribute("extRelateListNo", projectExtractListNo);
            // 删除查询不出的查询结果
            if (projectExtractListNo.size() == 0 && projectExtractListYes.size() == 0) {
                conditionService.delById(listCon.get(0).getId());
                conTypeService.delete(listCon.get(0).getConTypes().get(0).getId());
            }
        }
        return "ses/ems/exam/expert/extract/add_condition";
    }

    /**
     * 〈简述〉 〈详细描述〉ajax 验证必填项
     * 
     * @author Wang Wenshuai
     * @param model
     * @param project
     * @param packageName
     * @param typeclassId
     * @param sids
     * @param extAddress
     * @return
     */
    @ResponseBody
    @RequestMapping("/validateAddExtraction")
    public String validateAddExtraction(Project project, String packageName, String typeclassId, String[] sids, String extractionSites,
        HttpServletRequest rq, String[] packageId, String[] superviseId, Integer type) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("type", type.toString());
        // 后台数据校验
        int count = 0;
        if (project.getName() == null || "".equals(project.getName())) {
            map.put("projectNameError", "不能为空");
            count = 1;
        }

        if (project.getProjectNumber() == null || "".equals(project.getProjectNumber())) {
            map.put("projectNumberError", "不能为空");
            count = 1;
        }

        if (extractionSites == null || "".equals(extractionSites)) {
            map.put("extractionSitesError", "不能为空");
            count = 1;
        }

        // 独立
        if (typeclassId != null && !"".equals(typeclassId)) {
            if (superviseId == null || superviseId.length == 0) {
                map.put("supervise", "不能为空");
                count = 1;
            }
        } else {
            List<ProExtSupervise> list = projectSupervisorServicel.list(new ProExtSupervise(
                project.getId()));
            if (list == null || list.size() == 0 || "".equals(project.getId())) {
                map.put("supervise", "不能为空");
                count = 1;
            }
        }
        if (type == null || type != 1) {
            if (packageId == null || packageId.length == 0) {
                map.put("packageError", "不能为空");
                count = 1;
            }
        }

        // List<ProExtSupervise> list = projectSupervisorServicel.list(new
        // ProExtSupervise(project.getId()));
        // if (list == null || list.size() == 0){
        // map.put("supervise", "不能为空");
        // count = 1;
        // }
        // if(packageId == null || packageId.length == 0 ){
        // map.put("packageError", "不能为空");
        // count = 1;
        // }
        // //时
        // String hour = rq.getParameter("hour");
        // //分
        // String minute = rq.getParameter("minute");
        // if (hour == null || "".equals(hour) || minute == null || "".equals(minute)){
        // map.put("responseTimeError", "不能为空");
        // count = 1;
        // }
        // String tenderTime = rq.getParameter("tenderTime");
        // if (tenderTime == null || "".equals(tenderTime)){
        // map.put("tenderTimeError", "不能为空");
        // count = 1;
        // }

        if (count == 1) {
            map.put("error", "error");
            return JSON.toJSONString(map);

        } else {

            // 真实的项目id
            String projectId = project.getId();
            // String packageId = "";
            if (project.getId() == null || "".equals(project.getId())) {
                // 创建一个临时项目临时包
                project.setIsProvisional(1);
                projectService.add(project);
                projectId = project.getId();
                project.setId(projectId);
                // 修改监督人员
                if (superviseId != null && superviseId.length != 0) {
                    for (String id : superviseId) {
                        ProExtSupervise extUser = new ProExtSupervise();
                        extUser.setProjectId(projectId);
                        extUser.setId(id);
                        projectSupervisorServicel.update(extUser);
                    }
                }
            }
            // 抽取地址
            if (extractionSites != null && !"".equals(extractionSites)) {

                ExpExtractRecord extractRecord = new ExpExtractRecord();
                extractRecord.setProjectId(projectId);
                PageHelper.startPage(1, 1);
                List<ExpExtractRecord> listSe = expExtractRecordService.listExtractRecord(
                    extractRecord, 0);
                extractRecord.setExtractionSites(extractionSites);
                // extractRecord.setResponseTime(hour + "," + minute);
                User user = (User)rq.getSession().getAttribute("loginUser");
                if (user != null) {
                    extractRecord.setExtractsPeople(user.getId());
                }
                if (listSe != null && listSe.size() != 0) {
                    extractRecord.setId(listSe.get(0).getId());
                    expExtractRecordService.update(extractRecord);
                } else {
                    extractRecord.setProjectCode(project.getProjectNumber());
                    extractRecord.setProjectName(project.getName());
                    extractRecord.setExtractionTime(new Date());
                    expExtractRecordService.insert(extractRecord);
                }
            }
            // 监督人员
            if (sids != null && sids.length != 0) {
                projectSupervisorServicel.deleteProjectId(project.getId());
                for (String id : sids) {
                    if (!"".equals(id)) {
                        ProExtSupervise record1 = new ProExtSupervise();
                        record1.setProjectId(project.getId());
                        record1.setSupviseId(id);
                        projectSupervisorServicel.insert(record1);
                    }
                }
            }
            map.put("projectId", project.getId());
            // 获取抽取条件状态，未抽取不能在抽取
            if (packageId != null && !"".equals(packageId) && packageId.length != 0) {
                String count2 = conditionService.getCount(packageId);

                if (count2 != null && !"".equals(count2)) {
                    map.put("status", "1");
                    map.put("packageId", count2);

                } else {

                    map.put("sccuess", "SCCUESS");
                }
            }
            return JSON.toJSONString(map);
        }

    }

    /**
     * @Description:选择品目
     * @author Wang Wenshuai
     * @version 2016年9月28日 上午9:48:28
     * @param @return
     * @return String
     */
    @RequestMapping("/addHeading")
    public String addHeading(Model model, String[] id, String type) {
        ExtConType extConType = null;
        if (id != null && id.length != 0) {
            extConType = new ExtConType();
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
     * @author Wang Wenshuai
     * @version 2016年9月28日 下午3:12:34
     * @param @return
     * @return String
     */
    @RequestMapping("/extractCondition")
    public String extractCondition(HttpServletRequest sq, Model model, String cId) {
        Map<String, Integer> mapcount = new HashMap<String, Integer>();
        List<ProjectExtract> list = extractService.list(new ProjectExtract(cId));
        // if (list == null || list.size() == 0){
        // extractService.insert(cId, user != null && !"".equals(user.getId()) ? user.getId() :
        // "");
        // list = extractService.list(new ProjectExtract(cId));
        // }
        // 已操作的
        List<ProjectExtract> projectExtractListYes = new ArrayList<ProjectExtract>();
        // 未操作的
        List<ProjectExtract> projectExtractListNo = new ArrayList<ProjectExtract>();
        for (ProjectExtract projectExtract : list) {
            if (projectExtract.getOperatingType() != null
                && (projectExtract.getOperatingType() == 1 || projectExtract.getOperatingType() == 2)) {
                projectExtractListYes.add(projectExtract);
                Integer conTypeId = mapcount.get(projectExtract.getConTypeId());
                if (conTypeId != null && conTypeId != 0) {
                    mapcount.put(projectExtract.getConTypeId(), conTypeId += 1);
                } else {
                    mapcount.put(projectExtract.getConTypeId(), 1);
                }
            } else if (projectExtract.getOperatingType() != null
                       && projectExtract.getOperatingType() == 3) {
                projectExtractListYes.add(projectExtract);
            } else {
                projectExtractListNo.add(projectExtract);
            }
        }
        // 获取查询条件类型
        List<ExpExtCondition> listCondition = conditionService.list(new ExpExtCondition(cId, ""),
            null);
        List<ExtConType> conTypes = listCondition.get(0).getConTypes();
        if (conTypes != null
            && conTypes.size() == 1
            && (conTypes.get(0).getExpertsTypeId() == null || !"".equals(conTypes.get(0).getExpertsTypeId()))) {
            ProjectExtract projectExtrac = new ProjectExtract();
            projectExtrac.setConTypeId("1");
            projectExtrac.setExpertConditionId(listCondition.get(0).getId());
            List<ProjectExtract> peList = extractService.list(projectExtrac);
            conTypes.get(0).setAlreadyCount(peList == null ? 0 : peList.size());
        } else {
            for (ExtConType extConType1 : conTypes) {
                // 获取抽取的专家类别
                ProjectExtract projectExtrac = new ProjectExtract();
                projectExtrac.setReviewType(extConType1.getExpertsTypeId());
                projectExtrac.setExpertConditionId(listCondition.get(0).getId());
                projectExtrac.setReason("1,2");
                List<ProjectExtract> list2 = extractService.list(projectExtrac);
                extConType1.setAlreadyCount(list2 == null ? 0 : list2.size());
            }
        }
        model.addAttribute("extConType", conTypes);

        if (projectExtractListNo.size() != 0) {
            projectExtractListYes.add(projectExtractListNo.get(0));
            projectExtractListNo.remove(0);
        } else {
            // 已抽取
            // conditionService.update(new ExpExtCondition(listCondition.get(0).getId(),
            // (short)2));
        }
        model.addAttribute("extRelateListYes", projectExtractListYes);
        model.addAttribute("extRelateListNo", projectExtractListNo);
        return "ses/ems/exam/expert/extract/resultlist";
    }

    /**
     * @Description:返回结果
     * @author Wang Wenshuai
     * @date 2016年9月19日 下午2:31:46
     * @param @param model
     * @param @return
     * @return String
     */
    @ResponseBody
    @RequestMapping("/resultextract")
    public Object resultextract(Model model, String id, String reason, HttpServletRequest sq,
                                String[] packageId) {
        // 修改状态
        String[] ids = id.split(",");

        if ("1".equals(ids[2]) || "2".equals(ids[2])) {
            ProjectExtract expExtRelate = extractService.getExpExtRelate(ids[0]);
            if (null != expExtRelate) {
                // 获取抽取类型
                List<ExpExtCondition> conList = conditionService.list(new ExpExtCondition(
                    expExtRelate.getExpertConditionId(), ""), null);
                String expertTypeId = conList.get(0).getExpertsTypeId();
                // 获取专家类型
                String expTypeId = expExtRelate.getExpert().getExpertsTypeId();
                // 截取专家类型 如果满足insert
                if (expertTypeId != null && !"".equals(expertTypeId)) {
                    // 抽取类型
                    String[] expertTypeIdArray = expertTypeId.split(",");
                    // 专家类型
                    String[] expTypeIdAry = expTypeId.split(",");
                    ProjectExtract projectExtrac = new ProjectExtract();
                    for (String typeId : expertTypeIdArray) {

                        // 获取抽取的专家类别
                        projectExtrac.setReviewType(typeId);
                        projectExtrac.setExpertConditionId(ids[1]);
                        List<ProjectExtract> list = extractService.list(projectExtrac);
                        // 获取条件进行对比
                        Integer counts = conTypeService.getExpertTypeById(ids[1], typeId);
                        if (counts == null) {
                            counts = 0;
                        }
                        if (counts != 0 && list.size() != 0 && list.size() >= counts) {
                            continue;
                        } else {
                            int tp = 0;
                            for (String exptypeay : expTypeIdAry) {
                                if (exptypeay.equals(typeId)) {
                                    // 修改为抽取的类型
                                    ProjectExtract extract = new ProjectExtract();
                                    extract.setReviewType(typeId);
                                    extract.setId(ids[0]);
                                    extractService.update(extract);
                                    tp = 1;
                                }
                            }

                            if (tp == 1) {
                                break;
                            }
                        }

                    }

                } else {
                    // 修改为抽取的类型
                    ProjectExtract projectExtrac = new ProjectExtract();
                    projectExtrac.setExpertConditionId(ids[1]);
                    List<ProjectExtract> list = extractService.list(projectExtrac);
                    if (list != null && list.size() != 0) {
                        ProjectExtract extract = new ProjectExtract();
                        int i = 0;
                        String[] split = list.get(0).getExpert().getExpertsTypeId().split(",");
                        if (split.length > 1) {
                            int max = split.length - 1;
                            int min = 0;
                            Random random = new Random();
                            i = random.nextInt(max) % (max - min + 1) + min;
                        }
                        extract.setReviewType(split[i]);
                        extract.setId(ids[0]);
                        extractService.update(extract);
                    }
                }
            }

        }

        if (reason != null && !"".equals(reason)) {
            extractService.update(new ProjectExtract(ids[0], new Short(ids[2]), reason, packageId));

        } else {
            extractService.update(new ProjectExtract(ids[0], new Short(ids[2]), packageId));
        }
        if ("1".equals(ids[2]) || "2".equals(ids[2])) {
            ProjectExtract expExtRelate = extractService.getExpExtRelate(ids[0]);
            if (null != expExtRelate) {
                if ("4".equals(expExtRelate.getExpert().getStatus())) {
                    /**
                     * 推送
                     */
                    Todos todos = new Todos();
                    todos.setCreatedAt(new Date());
                    todos.setIsDeleted((short)0);
                    todos.setIsFinish((short)0);
                    // 待办名称
                    todos.setName(expExtRelate.getExpert().getRelName() + "专家复查");
                    // todos.setReceiverId();
                    // 接受人id
                    todos.setOrgId(expExtRelate.getExpert().getPurchaseDepId());
                    // 权限id
                    PropertiesUtil config = new PropertiesUtil("config.properties");
                    todos.setPowerId(config.getString("zjfc"));
                    // 发送人id
                    User user = (User)sq.getSession().getAttribute("loginUser");
                    todos.setSenderId(user.getId());
                    // 类型
                    todos.setUndoType((short)2);
                    // 发送人姓名
                    todos.setSenderName(expExtRelate.getExpert().getRelName());
                    // 审核地址
                    todos.setUrl("expertAudit/basicInfo.html?expertId="
                                 + expExtRelate.getExpert().getId());
                    todosService.insert(todos);
                    Expert expert = new Expert();
                    expert.setId(expExtRelate.getExpert().getId());
                    expert.setStatus("6");
                    expertServices.updateByPrimaryKeySelective(expert);
                    // 根据当前用户获取机构信息
                    if (null != user && null != user.getOrg()) {
                        Orgnization orgnization = orgnizationService.getOrgByPrimaryKey(user.getOrg().getId());
                        if (null != orgnization && null != orgnization.getTypeName()
                            && orgnization.getTypeName().equals("1")) {
                            expert.setExtractOrgid(orgnization.getId());
                            expertServices.updateExtractOrgidById(expert);// 更新机构ID
                        }
                    }
                }
            }
        }

        List<ProjectExtract> projectExtractListYes = resultProjectExtract(sq, ids);

        return projectExtractListYes;
    }

    /**
     * 〈简述〉返回专家抽取方法 〈详细描述〉
     * 
     * @author Wang Wenshuai
     * @param sq HttpServletRequest
     * @param ids 集合 [0]关联表id [1]条件表id [2]参与不参与
     * @return List<ProjectExtract>
     */
    private List<ProjectExtract> resultProjectExtract(HttpServletRequest sq, String[] ids) {
        // 存放已抽取的数量
        Map<String, Integer> mapcount = new HashMap<String, Integer>();
        // 存放已操作
        List<ProjectExtract> projectExtractListYes = new ArrayList<ProjectExtract>();
        // 未操作
        List<ProjectExtract> projectExtractListNo = new ArrayList<ProjectExtract>();
        // 循环出抽取未抽取的
        forExtract(mapcount, ids[1], projectExtractListYes, projectExtractListNo, 0);
        // 获取查询条件类型
        List<ExpExtCondition> listCondition = conditionService.list(
            new ExpExtCondition(ids[1], ""), null);
        // 删除已经满足类型的
        List<String> expertTypeIds = new ArrayList<String>();
        // 保存所有类型
        List<String> saveExpertTypeIds = new ArrayList<String>();
        for (ExtConType extConType1 : listCondition.get(0).getConTypes()) {
            // 获取抽取的专家类别
            ProjectExtract projectExtrac = new ProjectExtract();
            projectExtrac.setReviewType(extConType1.getExpertsTypeId());
            projectExtrac.setExpertConditionId(ids[1]);
            projectExtrac.setReason("1,2");
            List<ProjectExtract> list = extractService.list(projectExtrac);
            extConType1.setAlreadyCount(list == null ? 0 : list.size());
            // 删除满足数量的
            if (list.size() >= extConType1.getExpertsCount()) {
                expertTypeIds.add(extConType1.getExpertsTypeId());
            }
            if (extConType1 != null && extConType1.getExpertsTypeId() != null
                && !"".equals(extConType1.getExpertsTypeId())) {
                saveExpertTypeIds.add(extConType1.getExpertsTypeId());
            }
        }

        if (expertTypeIds != null && expertTypeIds.size() != 0) {
            Packages packages = new Packages();
            packages.setId(listCondition.get(0).getProjectId());
            List<Packages> find = packagesService.find(packages);
            extractService.del(listCondition.get(0).getId(), find.get(0).getProjectId(),
                expertTypeIds, saveExpertTypeIds);
        }

        // 拿出数量和session中存放的数字进行对比
        ProjectExtract pe = new ProjectExtract();
        pe.setId(ids[0]);
        List<ProjectExtract> list2 = extractService.list(pe);
        if (null != list2 && !list2.isEmpty()) {
            ExtConType extConType = conTypeService.getExtConType(list2.get(0).getConTypeId());
            Integer sum = conTypeService.getSum(list2.get(0).getExpertConditionId());
            Integer count = mapcount.get(extConType.getId());
            if (count != null && count != 0) {
                if (count >= sum) {
                    extractService.updateStatusCount("1", extConType.getId());
                    // 删除为抽取的数据
                    Packages packages = new Packages();
                    packages.setId(list2.get(0).getProjectId());
                    List<Packages> find = packagesService.find(packages);
                    extractService.delPe(find.get(0).getProjectId());
                    forExtract(mapcount, ids[1], projectExtractListYes, projectExtractListNo, 1);
                } else {
                    forExtract(mapcount, ids[1], projectExtractListYes, projectExtractListNo, 1);
                }
            }
        }
        List<ExtConType> conTypes = listCondition.get(0).getConTypes();

        if (conTypes != null
            && conTypes.size() == 1
            && (conTypes.get(0).getExpertsTypeId() == null || !"".equals(conTypes.get(0).getExpertsTypeId()))) {
            ProjectExtract projectExtrac = new ProjectExtract();
            projectExtrac.setConTypeId("1");
            projectExtrac.setExpertConditionId(listCondition.get(0).getId());
            List<ProjectExtract> peList = extractService.list(projectExtrac);
            conTypes.get(0).setAlreadyCount(peList == null ? 0 : peList.size());
        }
        projectExtractListYes.get(0).setConType(conTypes);
        if (projectExtractListNo.size() != 0) {
            projectExtractListYes.add(projectExtractListNo.get(0));
        } else {
            // 已抽取
            // conditionService.update(new ExpExtCondition(ids[1],(short)2));
        }

        return projectExtractListYes;
    }

    /**
     * 〈简述〉循环出抽取未抽取的 〈详细描述〉
     * 
     * @author yggc
     * @param mapcount
     * @param expertConditionId
     * @param projectExtractListYes
     * @param projectExtractListNo
     */
    private void forExtract(Map<String, Integer> mapcount, String expertConditionId,
                            List<ProjectExtract> projectExtractListYes,
                            List<ProjectExtract> projectExtractListNo, Integer type) {
        // 每次进入方法清除数据
        projectExtractListYes.clear();
        projectExtractListNo.clear();
        // 查询数据
        List<ProjectExtract> list = extractService.list(new ProjectExtract(expertConditionId));
        for (ProjectExtract projectExtract : list) {
            if (projectExtract.getOperatingType() != null
                && (projectExtract.getOperatingType() == 1 || projectExtract.getOperatingType() == 2)) {
                projectExtractListYes.add(projectExtract);
                if (type == 0) {
                    Integer conTypeId = mapcount.get(projectExtract.getConTypeId());
                    if (conTypeId != null && conTypeId != 0) {
                        mapcount.put(projectExtract.getConTypeId(), conTypeId += 1);
                    } else {
                        mapcount.put(projectExtract.getConTypeId(), 1);
                    }
                }
            } else if (projectExtract != null && projectExtract.getOperatingType() != null
                       && projectExtract.getOperatingType() == 3) {
                // 不参与
                projectExtractListYes.add(projectExtract);
                if (type == 0) {
                    ExtConType extConType = conTypeService.getExtConType(projectExtract.getConTypeId());
                    extractService.updateStatusCount("0", extConType.getId());
                }
            } else {
                projectExtractListNo.add(projectExtract);
            }
        }
    }

    /**
     * @Description:专家抽取记录集合
     * @author Wang Wenshuai
     * @version 2016年9月29日 下午2:11:25
     * @param @param model
     * @param @return
     * @return String
     */
    @RequestMapping("/resuleRecordlist")
    public String resuleRecord(Model model, ExpExtractRecord expExtractRecord, String page) {
        List<ExpExtractRecord> listExtractRecord = expExtractRecordService.listExtractRecord(
            expExtractRecord, page != null && !page.equals("") ? Integer.parseInt(page) : 1);
        model.addAttribute("listExtractRecord", new PageInfo<ExpExtractRecord>(listExtractRecord));
        model.addAttribute("expExtractRecord", expExtractRecord);
        return "ses/ems/exam/expert/extract/recordlist";
    }

    /**
     * @Description:抽取记录
     * @author Wang Wenshuai
     * @version 2016年10月14日 下午7:29:36
     * @param @param model
     * @param @param id
     * @param @return
     * @return String
     */
    @RequestMapping("/showRecord")
    public String showRecord(Model model, String id, String projectId, String packageId,
                             String typeclassId) {
        // 专家类型
        model.addAttribute("ddList", expExtractRecordService.ddList());
        model.addAttribute("find", DictionaryDataUtil.find(12));
        model.addAttribute("typeclassId", typeclassId);
        model.addAttribute("projectId", projectId);
        model.addAttribute("packageId", packageId);
        // 获取抽取记录
        ExpExtractRecord showExpExtractRecord = null;
        if (id != null && !"".equals(id)) {
            showExpExtractRecord = expExtractRecordService.listExtractRecord(
                new ExpExtractRecord(id), 0).get(0);
        } else {
            ExpExtractRecord record = new ExpExtractRecord();
            record.setProjectId(projectId);
            showExpExtractRecord = expExtractRecordService.listExtractRecord(record, 0).get(0);
        }

        if (packageId != null && !"".equals(packageId)) {
            // 已抽取
            String[] packageIds = packageId.split(",");
            if (packageIds.length != 0) {
                ExpExtCondition con = null;
                for (String pckId : packageIds) {
                    if (pckId != null && !"".equals(pckId)) {
                        con = new ExpExtCondition();
                        con.setProjectId(pckId);
                        con.setStatus((short)2);
                        conditionService.update(con);
                    }
                }
            }
        }

        model.addAttribute("ExpExtractRecord", showExpExtractRecord);
        if (showExpExtractRecord != null) {
            // 抽取条件
            List<Packages> conList = packagesService.listExpExtCondition(showExpExtractRecord.getProjectId());
            model.addAttribute("conditionList", conList);
            // 获取监督人员
            List<ProExtSupervise> listUser = projectSupervisorServicel.list(new ProExtSupervise(
                showExpExtractRecord.getProjectId()));
            model.addAttribute("listUser", listUser);
        }
        return "ses/ems/exam/expert/extract/extract_expert_word";
    }

    /**
     * @Description:抽取专家记录(流程)
     * @author Wang Wenshuai
     * @version 2016年10月14日 下午7:29:36
     * @param @param model
     * @param @param id
     * @param @return
     * @return String
     */
    @RequestMapping("/record")
    public String Record(Model model, String projectId) {
        // 专家类型
        model.addAttribute("ddList", expExtractRecordService.ddList());
        ExpExtractRecord showExpExtractRecord = null;
        // 获取抽取记录
        ExpExtractRecord expExtractRecord = new ExpExtractRecord();
        expExtractRecord.setProjectId(projectId);
        List<ExpExtractRecord> listExtractRecord = expExtractRecordService.listExtractRecord(
            expExtractRecord, 0);
        if (listExtractRecord != null && listExtractRecord.size() != 0) {
            showExpExtractRecord = listExtractRecord.get(0);
        }

        if (showExpExtractRecord != null && showExpExtractRecord.getProjectId() != null) {

            model.addAttribute("ExpExtractRecord", showExpExtractRecord);
            // 抽取条件
            List<ExpExtCondition> conditionList = conditionService.list(new ExpExtCondition(
                showExpExtractRecord.getProjectId()), null);

            model.addAttribute("conditionList", conditionList);

            List<Packages> listResultExpert = packagesService.listResultExpert(projectId);
            model.addAttribute("listResultExpert", listResultExpert);

            // List<Packages> listResultExpert = packagesService.listResultExpert(projectId);
            // model.addAttribute("listResultExpert", listResultExpert);

        }
        return "ses/ems/exam/expert/extract/show_record";
    }

    /**
     * @Description:重置密码
     * @author Wang Wenshuai
     * @version 2016年10月14日 下午7:29:36
     * @param model 实体
     * @param id 专家id
     */
    @ResponseBody
    @RequestMapping("/resetPwd")
    public String resetPwd(Model model, String[] eid) {
        User user = null;
        for (String id : eid) {
            user = new User();
            user.setTypeId(id);
            List<User> queryByList = userServiceI.queryByList(user);
            if (queryByList != null && queryByList.size() != 0) {
                user = queryByList.get(0);
                // 根据随机码+密码加密
                Md5PasswordEncoder md5 = new Md5PasswordEncoder();
                // false 表示：生成32位的Hex版, 这也是encodeHashAsBase64的, Acegi 默认配置; true 表示：生成24位的Base64版
                md5.setEncodeHashAsBase64(false);
                String pwd = md5.encodePassword("123456", user.getRandomCode());
                String userId = user.getId();
                user = new User();
                user.setPassword(pwd);
                user.setId(userId);
                userServiceI.update(user);
            } else {
                return JSON.toJSONString("error");
            }
        }
        return JSON.toJSONString("sccuess");

    }

    /**
     * @Description:展示添加临时专家页面
     * @author Wang Wenshuai
     * @version 2016年10月14日 下午7:29:36
     * @param model 实体
     * @param id 专家id
     */
    @RequestMapping("/showTemporaryExpert")
    public String showTemporaryExpert(Model model, String packageId, String projectId,
                                      String flowDefineId) {
        model.addAttribute("packageId", packageId);
        model.addAttribute("projectId", projectId);
        model.addAttribute("expert", new Expert());
        model.addAttribute("flowDefineId", flowDefineId);
        // 证件类型
        model.addAttribute("idType", DictionaryDataUtil.find(9));
        // 专家类型
        model.addAttribute("ddList", expExtractRecordService.ddList());
        // 获取专家
        Packages packages = new Packages();
        packages.setProjectId(projectId);
        List<Packages> find = packagesService.find(packages);
        model.addAttribute("packList", find);
        return "bss/prms/temporary_expert_add";
    }

    /****
     * 验证 身份证 * 身份证15位编码规则：dddddd yymmdd xx p dddddd：6位地区编码 yymmdd: 出生年(两位年)月日，如：910215 xx:
     * 顺序编码，系统产生，无法确定 p: 性别，奇数为男，偶数为女 身份证18位编码规则：dddddd yyyymmdd xxx y dddddd：6位地区编码 yyyymmdd:
     * 出生年(四位年)月日，如：19910215 xxx：顺序编码，系统产生，无法确定，奇数为男，偶数为女 y: 校验码，该位数值可通过前17位计算获得 前17位号码加权因子为 Wi = [
     * 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 ] 验证位 Y = [ 1, 0, 10, 9, 8, 7, 6, 5, 4,
     * 3, 2 ] 如果验证码恰好是10，为了保证身份证是十八位，那么第十八位将用X来代替 校验位计算公式：Y_P = mod( ∑(Ai×Wi),11 ) i为身份证号码1...17 位;
     * Y_P为校验码Y所在校验码数组位置
     */
    public static String validateIdCard(String card) {
        String returnStr = "";
        // 15位和18位身份证号码的正则表达式
        String regIdCard = "^(^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$)|(^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])((\\d{4})|\\d{3}[Xx])$)$";
        // 编译正则表达式
        Pattern pattern = Pattern.compile(regIdCard);
        Matcher matcher = pattern.matcher(card);
        // 字符串是否与正则表达式相匹配
        boolean rs = matcher.matches();
        // 如果通过该验证，说明身份证格式正确，但准确性还需计算
        if (rs) {
            if (card.length() == 18) {
                Integer idCardWi[] = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2}; // 将前17位加权因子保存在数组里
                Integer idCardY[] = {1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2}; // 这是除以11后，可能产生的11位余数、验证码，也保存成数组
                int idCardWiSum = 0; // 用来保存前17位各自乖以加权因子后的总和
                for (int i = 0; i < 17; i++ ) {
                    idCardWiSum += Integer.valueOf(card.substring(i, i + 1)) * idCardWi[i];
                }

                int idCardMod = idCardWiSum % 11;// 计算出校验码所在数组的位置
                String idCardLast = card.substring(17);// 得到最后一位身份证号码

                // 如果等于2，则说明校验码是10，身份证号码最后一位应该是X
                if (idCardMod == 2) {
                    if ("X".equals(idCardLast) || "x".equals(idCardLast)) {
                        returnStr = "success";
                    } else {
                        returnStr = "身份证号码错误！";
                    }
                } else {
                    // 用计算出的验证码与最后一位身份证号码匹配，如果一致，说明通过，否则是无效的身份证号码
                    if (idCardLast.equals(idCardY[idCardMod] + "")) {
                        returnStr = "success";
                    } else {
                        returnStr = "身份证号码错误！";
                    }
                }
            } else {
                // 15位
                returnStr = "success";
            }
        } else {
            returnStr = "身份证格式不正确!";
        }
        return returnStr;
    }

    /**
     * @Description:添加临时专家
     * @author Wang Wenshuai
     * @version 2016年10月14日 下午7:29:36
     * @param model 实体
     * @param id 专家id
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value = "/AddtemporaryExpert", produces = "text/html;charset=UTF-8")
    public Object addTemporaryExpert(@Valid
    Expert expert, BindingResult result, Model model, String projectId, String packageId,
                                     String packageName, String loginName, String loginPwd,
                                     String flowDefineId, HttpServletRequest sq)
        throws UnsupportedEncodingException {
        String mobile = sq.getParameter("mobile");
        // 转码
        if (expert != null) {
            if (expert.getRelName() != null && !"".equals(expert.getRelName())) {
                expert.setRelName(URLDecoder.decode(expert.getRelName(), "UTF-8"));
            }
            if (expert.getIdCardNumber() != null && !"".equals(expert.getIdCardNumber())) {
                expert.setIdCardNumber(URLDecoder.decode(expert.getIdCardNumber(), "UTF-8"));
            }
            if (expert.getAtDuty() != null && !"".equals(expert.getAtDuty())) {
                expert.setAtDuty(URLDecoder.decode(expert.getAtDuty(), "UTF-8"));
            }
            if (expert.getMobile() != null && !"".equals(expert.getMobile())) {
                expert.setMobile(URLDecoder.decode(expert.getMobile(), "UTF-8"));
            }
            if (expert.getRemarks() != null && !"".equals(expert.getRemarks())) {
                expert.setRemarks(URLDecoder.decode(expert.getRemarks(), "UTF-8"));
            }
        }
        if (loginName != null && !"".equals(loginName)) {
            loginName = URLDecoder.decode(loginName, "UTF-8");
        }
        if (loginPwd != null && !"".equals(loginPwd)) {
            loginPwd = URLDecoder.decode(loginPwd, "UTF-8");
        }
        if (packageName != null && !"".equals(packageName)) {
            packageName = URLDecoder.decode(packageName, "UTF-8");
        }
        Integer type = 0;
        // 校验字段
        if (result.hasErrors()) {
            type = 1;
        }
        if (loginName == null || "".equals(loginName)) {
            model.addAttribute("loginNameError", "不能为空");
            /*
             * if (loginName == null || !loginName.matches("^\\w{6,20}$")) {
             * model.addAttribute("loginNameError", "登录名由6-20位字母数字和下划线组成 !"); }
             */
            type = 1;
        } else {
            // 校验用户名是否存在
            List<User> users = userService.findByLoginName(loginName);
            if (users.size() > 0) {
                type = 1;
                model.addAttribute("loginNameError", "用户名已存在");
            }
        }
        /*if (StringUtils.isEmpty(mobile)) {
            model.addAttribute("mobile", "不能为空");
        } else {
            Map<String, Object> map = new HashMap<>();
            map.put("mobile", mobile);
            List<Expert> list = expertServices.yzCardNumber(map);
            if (list != null && list.size() != 0) {
                model.addAttribute("mobile", "联系电话已存在");
            }
        }*/
        if (loginPwd == null || "".equals(loginPwd)) {
            model.addAttribute("loginPwdError", "不能为空");
            /*
             * if (loginPwd == null || !loginPwd.matches("^\\w{6,20}$")) {
             * model.addAttribute("loginPwdError", "密码由6-20位字母数字和下划线组成 !"); }
             */
            type = 1;
        }
        if (loginPwd != null && loginPwd.length() < 6) {
            model.addAttribute("loginPwdError", "密码至少为6位");
            type = 1;
        }
        if (packageId == null || "".equals(packageId)) {

            model.addAttribute("packageIdError", "不能为空");
            type = 1;
        }
        /*String validateIdCard = validateIdCard(expert.getIdCardNumber());*/
        /*if (expert.getIdCardNumber() == null || "".equals(expert.getIdCardNumber())) {
            model.addAttribute("idCardNumberError", "不能为空");
            type = 1;
        }*/

       /* if (expert.getIdCardNumber() != null && !"".equals(expert.getIdCardNumber())) {
            Map<String, Object> map = new HashMap<>();
            map.put("idCardNumber", expert.getIdCardNumber());
            List<Expert> list = expertServices.yzCardNumber(map);
            if (list != null && list.size() != 0) {
                model.addAttribute("idCardNumberError", "已被占用");
                type = 1;
            }

        }*/

        if (type == 1) {
            model.addAttribute("expert", expert);
            model.addAttribute("loginName", loginName);
            model.addAttribute("loginPwd", loginPwd);
            model.addAttribute("projectId", projectId);
            model.addAttribute("packageId", packageId);
            model.addAttribute("packageName", packageName);
            model.addAttribute("flowDefineId", flowDefineId);
            // 专家类型
            model.addAttribute("ddList", expExtractRecordService.ddList());
            // 证件类型
            model.addAttribute("idType", DictionaryDataUtil.find(9));
            return "bss/prms/temporary_expert_add";
        }

        expExtractRecordService.addTemporaryExpert(expert, projectId, packageId, loginName,
            loginPwd, sq);
        // 修改状态
        flowMangeService.flowExe(sq, flowDefineId, projectId, 2);
        String ALLCHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < 15; i++ ) {
            sb.append(ALLCHAR.charAt(random.nextInt(ALLCHAR.length())));
        }
        String randomCode = sb.toString();
        return "redirect:/packageExpert/assignedExpert.html?projectId=" + projectId
               + "&flowDefineId=" + flowDefineId + "&randomCode = " + randomCode;
    }

    /**
     * 〈简述〉 〈详细描述〉
     * 
     * @author Wang Wenshuai
     * @return
     */
    @ResponseBody
    @RequestMapping("/findType")
    public Object findType(HttpServletResponse response) {
        // 查询供应商所有类型
        List<DictionaryData> ldlist1 = DictionaryDataUtil.find(6);
        List<DictionaryData> ldlist2 = DictionaryDataUtil.find(8);

        List<SupplierTypeTree> listSupplierTypeTrees = new ArrayList<SupplierTypeTree>();

        for (DictionaryData dd : ldlist1) {
            SupplierTypeTree supplierTypeTree = new SupplierTypeTree();
            if ("GOODS".equals(dd.getCode())) {
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
     * 〈简述〉获取专家类型 〈详细描述〉
     * 
     * @author Wang Wenshuai
     * @return
     */
    @ResponseBody
    @RequestMapping("projectType")
    public String projectType() {
        List<DictionaryData> ddList = expExtractRecordService.ddList();
        return JSON.toJSONString(ddList);
    }

    /**
     * 〈简述〉获取专家来源 〈详细描述〉
     * 
     * @author Wang Wenshuai
     * @return
     */
    @ResponseBody
    @RequestMapping("expertsFrom")
    public String expertsFrom() {
        List<DictionaryData> expertsFrom = DictionaryDataUtil.find(12);
        return JSON.toJSONString(expertsFrom);
    }

    /**
     * 〈简述〉是否已经抽取完成 〈详细描述〉
     * 
     * @author Wang Wenshuai
     * @return
     */
    @ResponseBody
    @RequestMapping("isFinish")
    public String isFinish(String packageId) {
        // 获取查询条件类型
        ExpExtCondition condition = new ExpExtCondition();
        condition.setProjectId(packageId);
        condition.setStatus((short)1);
        String finish = conditionService.isFinish(condition);
        return JSON.toJSONString(finish);
    }

    /**
     * 〈简述〉保存监督人员 〈详细描述〉
     * 
     * @author Wang Wenshuai
     * @return
     */
    @ResponseBody
    @RequestMapping("saveSupervise")
    public String saveSupervise(String[] relName, String[] company, String[] phone,
                                String[] duties, String projectId, String type) {
        // 返回抽监督人id
        String superviseId = "";
        // 姓名
        String strRelName = "";
        Map<String, String> map = new HashMap<String, String>();
        ProExtSupervise ps = null;
        SupplierExtUser eu = null;
        if (relName.length == 0 || phone.length == 0 || company.length == 0) {
            return ERROR;
        }
        if (projectId != null && !"".equals(projectId)) {
            projectSupervisorServicel.deleteProjectId(projectId);
            extUserServicel.deleteProjectId(projectId);
        }
        for (int i = 0; i < relName.length; i++ ) {
            ps = new ProExtSupervise();
            eu = new SupplierExtUser();
            if (company[i] == null || "".equals(company[i])) {
                return ERROR;
            }
            if (phone[i] == null || "".equals(phone[i])) {
                return ERROR;
            }
            if (relName[i] == null || "".equals(relName[i])) {
                return ERROR;
            }
            if (duties[i] == null || "".equals(duties[i])) {
                return ERROR;
            }

            if (type != null && "supplier".equals(type)) {
                // 供应商
                eu.setCompany(company[i]);
                eu.setPhone(phone[i]);
                eu.setRelName(relName[i]);
                if (projectId != null && !"".equals(projectId)) {

                    eu.setProjectId(projectId);
                }
                eu.setDuties(duties[i]);
                strRelName += relName[i] + ",";
                extUserServicel.insert(eu);
                superviseId += eu.getId() + ",";
            } else {
                // 专家
                ps.setCompany(company[i]);
                ps.setPhone(phone[i]);
                ps.setRelName(relName[i]);
                if (projectId != null && !"".equals(projectId)) {

                    eu.setProjectId(projectId);
                }
                ps.setProjectId(projectId);
                ps.setDuties(duties[i]);
                strRelName += relName[i] + ",";
                projectSupervisorServicel.insert(ps);
                superviseId += ps.getId() + ",";
            }

        }
        map.put("relName", strRelName.substring(0, strRelName.length() - 1));
        map.put("superviseId", superviseId.substring(0, superviseId.length() - 1));
        return JSON.toJSONString(map);
    }

    /**
     * @Description:显示监督人员
     * @author Wang Wenshuai
     * @version 2016年9月25日 09:49:56
     * @return String
     */
    @RequestMapping("/showSupervise")
    public String showSupervise(Model model, String projectId) {
        if (projectId != null && !"".equals(projectId)) {
            model.addAttribute("projectId", projectId);
            List<ProExtSupervise> list = projectSupervisorServicel.list(new ProExtSupervise(
                projectId));
            model.addAttribute("list", list);
        }
        return "ses/sms/supplier_extracts/supervise_list";
    }

    /**
     * 〈简述〉获取品目树 〈详细描述〉
     * 
     * @author Wang Wenshuai
     * @return
     */
    @ResponseBody
    @RequestMapping("/getTree")
    public String getTree(Category category, String[] type) {
        List<CategoryTree> jList = new ArrayList<CategoryTree>();
        // 获取字典表中的根数据
        if (category.getId() == null || "0".equals(category.getId())) {
            if (type != null && !"".equals(type)) {
                // 获取关联包id
                for (String strType : type) {
                    DictionaryData dictionaryData = DictionaryDataUtil.findById(strType);
                    if (dictionaryData.getKind() != 19) {
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
                List<DictionaryData> listByPage = dictionaryDataService.listByPage(data, 1);
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
        String list = "";
        List<Category> cateList = categoryService.findTreeByPid(category.getId());
        for (Category cate : cateList) {
            List<Category> cList = categoryService.findTreeByPid(cate.getId());
            CategoryTree ct = new CategoryTree();
            if (!cList.isEmpty()) {
                ct.setIsParent("true");
            } else {
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
     * @author Wang Wenshuai
     * @version 2016年9月25日 09:49:56
     * @return String
     */
    @RequestMapping("/reasonnumber")
    public String reasonNumber(Model model, String[] expertsTypeCode, String addressReson,
                               String eCount) {
        model.addAttribute("expertsTypeCode", expertsTypeCode);
        model.addAttribute("addressReson", addressReson);
        model.addAttribute("eCount", eCount);
        return "ses/ems/exam/expert/extract/reason_and_number";
    }

    /**
     * Description: 页面异步验证身份证号和电话唯一
     * 
     * @author zhang shubin
     * @data 2017年6月19日
     * @param
     * @return
     */
    @RequestMapping("/yzCardNumber")
    @ResponseBody
    public String yzCardNumber(HttpServletRequest request) {
        String cardNumber = request.getParameter("cardNumber") == null ? "" : request.getParameter("cardNumber");
        String mobile = request.getParameter("mobile") == null ? "" : request.getParameter("mobile");
        String id = request.getParameter("id") == null ? "" : request.getParameter("id");
        Map<String, Object> map = new HashMap<>();
        if (!mobile.equals("")) {
            map.put("mobile", mobile);
        }
        if (!cardNumber.equals("")) {
            map.put("idCardNumber", cardNumber);
        }
        if (!id.equals("")) {
            map.put("id", id);
        }
        List<Expert> list = expertServices.yzCardNumber(map);
        if (list != null && list.size() > 0) {
            return "error";
        } else {
            return "success";
        }
    }

    /**
     * Description: 展示修改临时专家页面
     * 
     * @author zhang shubin
     * @data 2017年6月27日
     * @param
     * @return
     */
    @RequestMapping("/showEditTemporaryExpert")
    public String showEditTemporaryExpert(Model model, String packageId, String projectId,
                                          String flowDefineId, String id) {
        model.addAttribute("packageId", packageId);
        model.addAttribute("projectId", projectId);
        model.addAttribute("flowDefineId", flowDefineId);
        // 证件类型
        model.addAttribute("idType", DictionaryDataUtil.find(9));
        // 专家类型
        model.addAttribute("ddList", expExtractRecordService.ddList());
        // 获取专家
        Packages packages = new Packages();
        packages.setProjectId(projectId);
        List<Packages> find = packagesService.find(packages);
        model.addAttribute("packList", find);
        Expert expert = ExpertService.selectByPrimaryKey(id);
        model.addAttribute("expert", expert);
        Packages packages2 = packagesService.selectByPrimaryKeyId(packageId);
        if (packages2 != null) {
            model.addAttribute("packageName", packages2.getName());
        }
        List<User> userList = userServiceI.selectByTypeId(id);
        if (userList != null && userList.size() > 0) {
            model.addAttribute("loginName", userList.get(0).getLoginName());
            // model.addAttribute("loginPwd", userList.get(0).getPassword());
        }
        return "bss/prms/temporary_expert_edit";
    }

    /**
     * Description: 修改临时专家
     * 
     * @author zhang shubin
     * @data 2017年6月27日
     * @param
     * @return
     */
    @RequestMapping(value = "/editTemporaryExpert", produces = "text/html;charset=UTF-8")
    public Object editTemporaryExpert(@Valid
    Expert expert, BindingResult result, Model model, String projectId, String packageId,
                                      String packageName, String loginName, String loginPwd,
                                      String flowDefineId, HttpServletRequest sq,
                                      String oldPackageId)
        throws UnsupportedEncodingException {
        String mobile = sq.getParameter("mobile");
        // 转码
        if (expert != null) {
            if (expert.getRelName() != null && !"".equals(expert.getRelName())) {
                expert.setRelName(URLDecoder.decode(expert.getRelName(), "UTF-8"));
            }
            if (expert.getIdCardNumber() != null && !"".equals(expert.getIdCardNumber())) {
                expert.setIdCardNumber(URLDecoder.decode(expert.getIdCardNumber(), "UTF-8"));
            }
            if (expert.getAtDuty() != null && !"".equals(expert.getAtDuty())) {
                expert.setAtDuty(URLDecoder.decode(expert.getAtDuty(), "UTF-8"));
            }
            if (expert.getMobile() != null && !"".equals(expert.getMobile())) {
                expert.setMobile(URLDecoder.decode(expert.getMobile(), "UTF-8"));
            }
            if (expert.getRemarks() != null && !"".equals(expert.getRemarks())) {
                expert.setRemarks(URLDecoder.decode(expert.getRemarks(), "UTF-8"));
            }
        }
        if (loginName != null && !"".equals(loginName)) {
            loginName = URLDecoder.decode(loginName, "UTF-8");
        }
        if (packageName != null && !"".equals(packageName)) {
            packageName = URLDecoder.decode(packageName, "UTF-8");
        }
        Integer type = 0;
        // 校验字段
        if (result.hasErrors()) {
            type = 1;
        }
        if (loginName == null || "".equals(loginName)) {
            model.addAttribute("loginNameError", "不能为空");
            /*
             * if (loginName == null || !loginName.matches("^\\w{6,20}$")) {
             * model.addAttribute("loginNameError", "登录名由6-20位字母数字和下划线组成 !"); }
             */
            type = 1;
        } else {
            // 校验用户名是否存在
            Map<String, Object> umap = new HashMap<>();
            umap.put("loginName", loginName);
            umap.put("typeId", expert.getId());
            if (!userServiceI.yzLoginName(umap)) {
                type = 1;
                model.addAttribute("loginNameError", "用户名已存在");
            }
        }
        if (StringUtils.isEmpty(mobile)) {
            model.addAttribute("mobile", "不能为空");
            type = 1;
        } else {
            Map<String, Object> map = new HashMap<>();
            map.put("mobile", mobile);
            map.put("id", expert.getId());
            List<Expert> list = expertServices.yzCardNumber(map);
            if (list != null && list.size() != 0) {
                model.addAttribute("mobile", "联系电话已存在");
                type = 1;
            }
        }
        if (packageId == null || "".equals(packageId)) {
            model.addAttribute("packageIdError", "不能为空");
            type = 1;
        }
        if (expert.getIdCardNumber() != null && !"".equals(expert.getIdCardNumber())) {
            Map<String, Object> map = new HashMap<>();
            map.put("idCardNumber", expert.getIdCardNumber());
            map.put("id", expert.getId());
            List<Expert> list = expertServices.yzCardNumber(map);
            if (list != null && list.size() != 0) {
                model.addAttribute("idCardNumberError", "已被占用");
                type = 1;
            }
        }
        if (type == 1) {
            model.addAttribute("expert", expert);
            model.addAttribute("loginName", loginName);
            // model.addAttribute("loginPwd", loginPwd);
            model.addAttribute("projectId", projectId);
            model.addAttribute("packageId", packageId);
            model.addAttribute("packageName", packageName);
            model.addAttribute("flowDefineId", flowDefineId);
            // 专家类型
            model.addAttribute("ddList", expExtractRecordService.ddList());
            // 证件类型
            model.addAttribute("idType", DictionaryDataUtil.find(9));
            return "bss/prms/temporary_expert_edit";
        }
        expExtractRecordService.editTemporaryExpert(expert, projectId, packageId, loginName,
            loginPwd, sq, oldPackageId);
        String ALLCHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < 15; i++ ) {
            sb.append(ALLCHAR.charAt(random.nextInt(ALLCHAR.length())));
        }
        String randomCode = sb.toString();
        return "redirect:/packageExpert/assignedExpert.html?projectId=" + projectId
               + "&flowDefineId=" + flowDefineId + "&randomCode = " + randomCode;
    }

    /**
     * Description: 判断是否为临时专家
     * 
     * @author zhang shubin
     * @data 2017年6月28日
     * @param
     * @return
     */
    @RequestMapping("/isProvisional")
    @ResponseBody
    public String isProvisional() {
        String id = request.getParameter("id") == null ? "" : request.getParameter("id");
        Expert expert = ExpertService.selectByPrimaryKey(id);
        if (expert != null && expert.getIsProvisional() == 1) {
            return "true";
        } else if (expert != null && expert.getIsProvisional() == 0) {
            return "false";
        } else {
            return "";
        }
    }
}
