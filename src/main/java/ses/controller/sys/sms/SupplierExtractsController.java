
package ses.controller.sys.sms;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.dao.bms.DictionaryDataMapper;
import ses.model.bms.Area;
import ses.model.bms.Category;
import ses.model.bms.CategoryTree;
import ses.model.bms.DictionaryData;
import ses.model.bms.Todos;
import ses.model.bms.User;
import ses.model.ems.Expert;
import ses.model.ems.ExtConType;
import ses.model.oms.Orgnization;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierConType;
import ses.model.sms.SupplierCondition;
import ses.model.sms.SupplierExtPackage;
import ses.model.sms.SupplierExtRelate;
import ses.model.sms.SupplierExtUser;
import ses.model.sms.SupplierExtracts;
import ses.service.bms.AreaServiceI;
import ses.service.bms.CategoryService;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.bms.TodosService;
import ses.service.bms.UserServiceI;
import ses.service.oms.OrgnizationServiceI;
import ses.service.sms.SupplierAuditService;
import ses.service.sms.SupplierConTypeService;
import ses.service.sms.SupplierConditionService;
import ses.service.sms.SupplierExtPackageServicel;
import ses.service.sms.SupplierExtRelateService;
import ses.service.sms.SupplierExtUserServicel;
import ses.service.sms.SupplierExtractsService;
import ses.service.sms.SupplierService;
import ses.service.sms.SupplierTypeRelateService;
import ses.util.DictionaryDataUtil;
import bss.controller.base.BaseController;
import bss.model.ppms.Packages;
import bss.model.ppms.Project;
import bss.model.ppms.SaleTender;
import bss.service.ppms.FlowMangeService;
import bss.service.ppms.PackageService;
import bss.service.ppms.ProjectService;
import bss.service.ppms.SaleTenderService;
import bss.util.PropUtil;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import common.annotation.CurrentUser;

/**
 * @Description:供应商抽取记录
 *
 * @author Wang Wenshuai
 * @date 2016年9月18日下午3:29:12
 * @since  JDK 1.7
 */
@Controller
@Scope("prototype")
@RequestMapping("/SupplierExtracts")
public class SupplierExtractsController extends BaseController {
    /**
     * 项目
     */
    @Autowired
    private ProjectService projectService;
    /**
     * 包
     */
    @Autowired
    private PackageService packagesService;

    /**
     * 地区
     */
    @Autowired
    private AreaServiceI areaService;
    @Autowired
    private SupplierConditionService conditionService; //条件
    @Autowired
    private SupplierConTypeService conTypeService; //条件
    @Autowired
    private SupplierExtRelateService extRelateService; //关联表
    @Autowired
    private SupplierExtractsService expExtractRecordService; //记录
    @Autowired
    private SupplierExtUserServicel extUserServicl;
    @Autowired
    private UserServiceI userServicl;
    @Autowired
    private SaleTenderService saleTenderService; //发售标书
    @Autowired
    private SupplierExtPackageServicel  supplierExtPackageServicel;
    @Autowired
    private SupplierExtUserServicel extUserServicel; //监督人员
    @Autowired
    private DictionaryDataServiceI dictionaryDataServiceI; //字典
    @Autowired
    private CategoryService categoryService; //品目
    @Autowired
    private DictionaryDataMapper dictionaryDataMapper; //
    /**待办消息**/
    @Autowired
    private TodosService todosService;
    @Autowired
    private SupplierAuditService supplierAuditService;
    @Autowired
    private SupplierTypeRelateService supplierTypeRelateService;
    @Autowired
    private SupplierAuditService auditService;
    @Autowired
    private SupplierService supplierService;
    
    @Autowired
    private FlowMangeService flowMangeService;
    @Autowired
    private OrgnizationServiceI orgnizationService;

    @Autowired
    private SupplierExtRelateService supplierExtRelateService;
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
            SupplierExtPackage extPackage = new SupplierExtPackage();
            extPackage.setProjectId(projectId);
            Packages packages = new Packages();
            packages.setName(packName);
            extPackage.setPackages(packages);
            List<SupplierExtPackage> list = supplierExtPackageServicel.list(extPackage, page == null || "".equals(page) ? "1" : page);
            model.addAttribute("info", new PageInfo<SupplierExtPackage>(list));
            return "ses/sms/supplier_extracts/ext_package";
        } else {
            return "redirect:Extraction.html";
        }

    }


    /**
     * @Description:获取项目集合
     *
     * @author Wang Wenshuai
     * @version 2016年9月27日 下午4:38:31
     * @param  page
     * @param  model
     * @param  project
     * @return String
     */
    @RequestMapping("/projectList")
    public String list(Integer page, Model model, Project project,String typeclassId){
        List<Project> list = projectService.provisionalList(page == null?1:page, project);
        List<DictionaryData> find = DictionaryDataUtil.find(5);
        PageInfo<Project> info = new PageInfo<>(list);
        model.addAttribute("info", info);
        model.addAttribute("projects", project);
        model.addAttribute("kind", find);
        model.addAttribute("typeclassId", typeclassId);
        return "ses/sms/supplier_extracts/project_list";
    }
    
    /**
     * 
     *〈简述〉供应商抽取
     *〈详细描述〉
     * @author FengTian
     * @param user
     * @param model
     * @param projectId
     * @return
     */
    @RequestMapping("/Extraction")
    public String listExtraction(@CurrentUser User user, Model model, String projectId){
        Project project = projectService.selectById(projectId);
        
        if(project != null && StringUtils.isNotBlank(project.getPurchaseType())){
            DictionaryData findById = DictionaryDataUtil.findById(project.getPurchaseType());
            if(findById != null){
                project.setPurchaseType(findById.getName());
            }
        }
        model.addAttribute("project", project);
        
        //根据包获取抽取出的供应商
        List<Packages> listResultSupplier = packagesService.listExtRelate(projectId);
        if (listResultSupplier != null && !listResultSupplier.isEmpty()) {
            List<SupplierExtRelate> extRelates = new ArrayList<SupplierExtRelate>();
            for (Packages packages : listResultSupplier) {
                extRelates.addAll(packages.getListExtRelate());
            }
            if (extRelates != null && !extRelates.isEmpty()) {
                for (int i = 0; i < extRelates.size()-1; i++) {
                    SupplierExtRelate st = extRelates.get(i);
                    for (int j = extRelates.size()-1; j > i; j--) {
                        SupplierExtRelate st2 = extRelates.get(j);
                        if (st.getSupplier().getId().equals(st2.getSupplier().getId())) {
                            extRelates.remove(st2);
                        }
                    }
                }
                for (SupplierExtRelate extRelate : extRelates) {
                    String packageName = "";
                    SupplierExtRelate relate = new SupplierExtRelate();
                    relate.setSupplierId(extRelate.getSupplier().getId());
                    //该供应商参与的包
                    List<SupplierExtRelate> list2 = supplierExtRelateService.list(relate, null);
                    if (list2 != null && list2.size() > 0) {
                        for (int i = 0; i < list2.size(); i++) {
                            Packages packages = packagesService.selectByPrimaryKeyId(list2.get(i).getProjectId());
                            if (packages != null) {
                                String pName = packages.getName();
                                if (i == 0) {
                                    packageName += pName;
                                } else {
                                    packageName += ","+pName;
                                }
                            }
                        }
                    }
                    extRelate.setPackageName(packageName);
                }
                model.addAttribute("extRelates", extRelates);
            }
        }
        return "ses/sms/supplier_extracts/supplier_list";
    }

    /**
     * @Description:跳转到抽取条件
     *
     * @author Wang Wenshuai
     * @version 2016年9月27日 下午6:04:26
     * @param @param id
     * @param @return
     * @return String
     */
    @RequestMapping("/addExtractions")
    public String addExtraction(Model model,String typeclassId,String projectId,String[] packageId){
        List<Area> privnce = areaService.findRootArea();
        model.addAttribute("privnce", privnce);
        model.addAttribute("typeclassId", typeclassId);
        model.addAttribute("projectId", projectId);
        model.addAttribute("typeList", conditionService.supplierTypeList());
        model.addAttribute("typeListJson", JSON.toJSONString(conditionService.supplierTypeList()));
        String packIds="";
        for (String packId : packageId) {
            packIds += packId + ",";
        }
        model.addAttribute("packageId", packIds);
        //获取查询条件类型
        SupplierCondition condition=new SupplierCondition();
        condition.setProjectId(packageId[0]);
        condition.setStatus((short)1);
        List<SupplierCondition> listCon = conditionService.list(condition,0);
        if (listCon != null && listCon.size() !=0 ){
            model.addAttribute("listCon", listCon.get(0));
            String conId = listCon.get(0).getId();
            //所在地区回显
            //            if (listCon.get(0).getAddress() != null && listCon.get(0).getAddress() != null ){
            //                Area area = areaService.listById(listCon.get(0).getAddress());
            //                List<Area> city = areaService.findAreaByParentId(area.getParentId());
            //                model.addAttribute("city", city);
            //                model.addAttribute("area", area);
            //            }
            Map<String, Integer> mapcount = new HashMap<String, Integer>();
            Integer sum = conTypeService.getSum(conId);
            model.addAttribute("sumCount",sum);
            PageHelper.startPage(1, sum*2);
            List<SupplierExtRelate> list = extRelateService.list(new SupplierExtRelate(conId), "");
            if (list != null && list.size() != 0){
                //已操作的
                List<SupplierExtRelate> projectExtractListYes = new ArrayList<SupplierExtRelate>();
                //未操作的
                List<SupplierExtRelate> projectExtractListNo = new ArrayList<SupplierExtRelate>();
                for (SupplierExtRelate projectExtract : list) {
                    if (projectExtract.getOperatingType() != null && (projectExtract.getOperatingType() == 1 || projectExtract.getOperatingType() == 2)){
                        projectExtractListYes.add(projectExtract);
                        Integer conTypeId = mapcount.get(projectExtract.getConTypeId());
                        if (conTypeId != null && conTypeId != 0){
                            mapcount.put(projectExtract.getConTypeId(), conTypeId += 1);
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
                List<SupplierCondition> listCondition = conditionService.list(new SupplierCondition(conId, ""), 0);
                List<SupplierConType> conTypes = null;
                if (listCondition != null && listCondition.size() !=0 ){
                    conTypes = listCondition.get(0).getConTypes();
                }
                if (conTypes !=null && conTypes.size() !=0 ){
                    for (SupplierConType extConType1 : conTypes) {
                        //获取抽取的专家类别
                        SupplierExtRelate supplierExtRelateC = new SupplierExtRelate();
                        supplierExtRelateC.setReviewType(extConType1.getSupplierTypeId());
                        supplierExtRelateC.setReason("1,2");
                        supplierExtRelateC.setSupplierConditionId(conId);
                        List<SupplierExtRelate> list2 = extRelateService.list(supplierExtRelateC, null);
                        extConType1.setAlreadyCount(list2 == null ? 0 : list2.size());
                    }
                    model.addAttribute("extConType", conTypes);
                    model.addAttribute("extConTypeJson", JSON.toJSONString(conTypes));
                }

                if (projectExtractListNo.size() != 0){
                    projectExtractListYes.add(projectExtractListNo.get(0));
                    projectExtractListNo.remove(0);
                } else {
                    //已抽取
                    //          conditionService.update(new SupplierCondition(conId, (short)2));
                }
                model.addAttribute("extRelateListYes", projectExtractListYes);
                model.addAttribute("extRelateListNo", projectExtractListNo);
                //删除查询不出的查询结果
                if (projectExtractListNo.size() == 0 && projectExtractListYes.size() == 0){
                    conditionService.delById(listCondition.get(0).getId());
                    conTypeService.delete(listCondition.get(0).getConTypes().get(0).getId());
                }
            }
        }
        return "ses/sms/supplier_extracts/add_condition";
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
    @ResponseBody
    @RequestMapping("/validateAddExtraction")
    public String validateAddExtraction(Project project, String packageName, String typeclassId, String[] sids, String extractionSites,HttpServletRequest sq,String[] packageId, String[] superviseId,Integer type){

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
            List<SupplierExtUser> list = extUserServicl.list(new SupplierExtUser(project.getId()));
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

        if (count == 1){
            if(type == 2 && map.get("packageError") != null && !"".equals(map.get("packageError"))){
            }else{
                map.put("error", "error");
            }
            return JSON.toJSONString(map);

        }else if(type != null && 1 == type && project.getId() != null && !"".equals(project.getId()) ){//只是类型是1的
            map.put("typeclassId", typeclassId);
            if(project != null && project.getId() != null && !"".equals(project.getId())){
                map.put("projectId", project.getId());
            }
            return JSON.toJSONString(map);
        } else {
            try{
                //真实的项目id
                String projectId = project.getId();
                if (project.getId() == null || "".equals(project.getId())) {
                    // 创建一个临时项目临时包
                    project.setIsProvisional(1);
                    projectService.add(project);
                    projectId = project.getId();
                    project.setId(projectId);
                    //修改监督人员
                    if (superviseId != null && superviseId.length != 0) {
                        for (String id : superviseId) {
                            SupplierExtUser extUser = new SupplierExtUser();
                            extUser.setProjectId(projectId);
                            extUser.setId(id);
                            extUserServicel.update(extUser);
                        }
                    }
                }
                //抽取地址
                if (extractionSites != null && !"".equals(extractionSites)){
                    SupplierExtracts supplierExtracts = new SupplierExtracts();
                    supplierExtracts.setProjectId(projectId);
                    PageHelper.startPage(1, 1);
                    List<SupplierExtracts> listSe = expExtractRecordService.listExtractRecord(supplierExtracts,0);
                    supplierExtracts.setExtractionSites(extractionSites);
                    User user = (User) sq.getSession().getAttribute("loginUser");
                    if(user != null ){
                        supplierExtracts.setExtractsPeople(user.getId());
                    }
                    if (listSe != null && listSe.size() != 0){
                        supplierExtracts.setId(listSe.get(0).getId());
                        expExtractRecordService.update(supplierExtracts);
                    } else {
                        supplierExtracts.setProjectCode(project.getProjectNumber());
                        supplierExtracts.setProjectName(project.getName());
                        supplierExtracts.setExtractionTime(new Date());
                        expExtractRecordService.insert(supplierExtracts);
                    }
                }
                //获取抽取条件状态，未抽取不能在抽取
                map.put("projectId", project.getId());
                if(packageId != null && !"".equals(packageId) && packageId.length != 0){
                    String count2 = conditionService.getCount(packageId);
                    if (count2 != null && !"".equals(count2)){
                        map.put("status", "1");
                        map.put("packageId", count2);
                    } else {
                        map.put("sccuess", "SCCUESS");
                    }

                }
            }catch (Exception ex){
                map.put("isSuccess","false");
                map.put("msg","数据更新异常");
                ex.printStackTrace();
            }

        }
        return JSON.toJSONString(map);


    }

    /**
     *
     *〈简述〉根据项目id获取包信息
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param projectId
     * @return
     */
    @ResponseBody
    @RequestMapping("/getpackage")
    public String getPackage(String projectId){
        List<Packages> findPackageById = new ArrayList<Packages>();
        if(projectId != null && !"".equals(projectId)){
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("projectId",projectId);
            findPackageById = packagesService.findPackageById(map);
        }
        return  JSON.toJSONString(findPackageById);
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
    public String addHeading(Model model, String[] id,String projectId){
        ExtConType extConType = null;
        if (id != null && id.length != 0){
            extConType = new ExtConType();
            extConType.setCategoryId(id[0]);
            extConType.setExpertsCount(Integer.parseInt(id[2]));
            extConType.setExpertsQualification(id[3]);
        }
        //        List<DictionaryData> find = DictionaryDataUtil.find(8);
        model.addAttribute("extConType", extConType);
        //        supplierExtPackageServicel.list(sExtPackage, page);
        model.addAttribute("projectId", projectId);
        return "ses/sms/supplier_extracts/product";
    }
    /**
     * @Description: 条件抽取供应商
     *
     * @author Wang Wenshuai
     * @version 2016年9月28日 下午3:12:34
     * @param @return
     * @return String
     */
    @RequestMapping("/extractCondition")
    public String extractCondition(HttpServletRequest sq, Model model,String cId){
        Map<String, Integer> mapcount = new HashMap<String, Integer>();
        //    User user = (User) sq.getSession().getAttribute("loginUser");
        List<SupplierExtRelate> list = extRelateService.list(new SupplierExtRelate(cId), "");
        if (list == null || list.size() == 0){
            //            extRelateService.insert(cId, user != null && !"".equals(user.getId()) ? user.getId() : "", null, cId);
            list = extRelateService.list(new SupplierExtRelate(cId),"");
        }
        //已操作的
        List<SupplierExtRelate> projectExtractListYes = new ArrayList<SupplierExtRelate>();
        //未操作的
        List<SupplierExtRelate> projectExtractListNo = new ArrayList<SupplierExtRelate>();
        for (SupplierExtRelate projectExtract : list) {
            if (projectExtract.getOperatingType() != null && (projectExtract.getOperatingType() == 1 || projectExtract.getOperatingType() == 2)){
                projectExtractListYes.add(projectExtract);
                Integer conTypeId = mapcount.get(projectExtract.getConTypeId());
                if (conTypeId != null && conTypeId != 0){
                    mapcount.put(projectExtract.getConTypeId(), conTypeId += 1);
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
        List<SupplierCondition> listCondition = conditionService.list(new SupplierCondition(cId, ""), 0);
        List<SupplierConType> conTypes = null;
        if (listCondition != null && listCondition.size() !=0 ){
            conTypes = listCondition.get(0).getConTypes();
        }
        if (conTypes !=null && conTypes.size() !=0 ){
            for (SupplierConType extConType : conTypes) {
                extConType.setAlreadyCount(mapcount.get(extConType.getId()) == null ? 0 : mapcount.get(extConType.getId()));
            }
            model.addAttribute("extConType", conTypes);
        }

        if (projectExtractListNo.size() != 0){
            projectExtractListYes.add(projectExtractListNo.get(0));
            projectExtractListNo.remove(0);
        } else {
            //已抽取
            //      conditionService.update(new SupplierCondition(cId, (short)2));
        }
        model.addAttribute("extRelateListYes", projectExtractListYes);
        model.addAttribute("extRelateListNo", projectExtractListNo);
        return "ses/sms/supplier_extracts/resultlist";
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
    public Object resultextract(Model model,String id,String reason,HttpServletRequest sq,HttpSession session,String[] packageId){
        //    修改状态
        String ids[]=id.split(",");
        if ("1".equals(ids[2]) || "2".equals(ids[2])){
            SupplierExtRelate expExtRelate = extRelateService.getSupplierExtRelate(ids[0]);
            if(null != expExtRelate){
                List<SupplierCondition> conList =  conditionService.list(new SupplierCondition(expExtRelate.getSupplierConditionId(), "") , null);
                String expertTypeId = conList.get(0).getExpertsTypeId();
                //截取专家类型 如果满足insert
                SupplierExtRelate supplierExtRelateC = new SupplierExtRelate();
                if(expertTypeId != null && !"".equals(expertTypeId)){
                    String[] types = expertTypeId.split(",");
                    for (String typeId : types) {
                        //获取抽取的专家类别s
                        supplierExtRelateC.setReviewType(typeId);
                        supplierExtRelateC.setSupplierConditionId(ids[1]);
                        List<SupplierExtRelate> list = extRelateService.list(supplierExtRelateC, null);
                        //获取条件进行对比
                        Integer counts = conTypeService.getSupplierTypeById(ids[1], typeId) ;
                        if(counts == null ){
                            counts = 0;
                        }
                        if(counts !=0 && list.size() != 0 && list.size() >= counts){
                            continue;
                        }else{
                            int tp = 0;
                            if(expertTypeId != null && !"".equals(expertTypeId)){
                                String supplierTypeLists = supplierTypeRelateService.findBySupplier(expExtRelate.getSupplier().getId());
                                String[] supplierType = supplierTypeLists.split(",");
                                for (String data : supplierType) {
                                    if(data.equals(typeId)){
                                        //修改为抽取的类型
                                        SupplierExtRelate extract = new SupplierExtRelate();
                                        extract.setReviewType(typeId);
                                        extract.setId(ids[0]);
                                        extRelateService.update(extract);
                                        tp = 1;
                                    }
                                }
                            }
                            if(tp == 1){
                                break;
                            }

                        }
                    }

                }else{
                    //修改为抽取的类型
                    SupplierExtRelate extRelate = new SupplierExtRelate();
                    extRelate.setSupplierConditionId(ids[1]);
                    List<SupplierExtRelate> list = extRelateService.list(extRelate,null);
                    if (list != null && list.size() != 0){
                        SupplierExtRelate extract = new SupplierExtRelate();
                        int i = 0;
                        String  supplierType= supplierTypeRelateService.findBySupplier(list.get(0).getSupplierId());
                        String[] split = supplierType.split(",");
                        if(split.length > 1 ){
                            int max=split.length-1;
                            int min=0;
                            Random random = new Random();
                            i = random.nextInt(max)%(max-min+1) + min;
                        }
                        extract.setReviewType(split[i]);
                        extract.setId(ids[0]);
                        extRelateService.update(extract);
                    }

                }
            }
        }

        if (reason != null && !"".equals(reason)) {
            extRelateService.update(new SupplierExtRelate(ids[0],new Short(ids[2]),reason,packageId));
        } else {
            extRelateService.update(new SupplierExtRelate(ids[0],new Short(ids[2]),packageId));
        }
        if ("1".equals(ids[2]) || "2".equals(ids[2])){
            SupplierExtRelate supplierExtRelate = extRelateService.getSupplierExtRelate(ids[0]);
            if(null != supplierExtRelate){
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("id", supplierExtRelate.getProjectId());
                List<Packages> findPackageById = packagesService.findPackageById(map);
                for (String  packid : packageId) {
                    if (packid != null && !"".equals(packid)) {
                        SaleTender saleTender = new SaleTender();
                        saleTender.setProjectId(findPackageById.get(0).getProjectId());
                        saleTender.setSupplierId(supplierExtRelate.getSupplier().getId());
                        saleTender.setPackages(packid);
                        List<SaleTender> find = saleTenderService.find(saleTender);
                        if (find == null || find.size() == 0) {
                            saleTenderService.insert(saleTender);
                        }
                    }
                }

                if (supplierExtRelate.getSupplier().getStatus() == 1){
                    Todos todos = new Todos();
                    todos.setUrl("supplierAudit/essential.html?supplierId="+supplierExtRelate.getSupplier().getId());
                    todos.setName("供应商复核");
                    todosService.updateByUrl(todos);
                    //推送者id
                    //发送人id
                    User user = (User)sq.getSession().getAttribute("loginUser");
                    todos.setSenderId(user.getId());
                    //待办名称
                    todos.setName(supplierExtRelate.getSupplier().getName()+"供应商复核");
                    //机构id
                    todos.setOrgId(supplierExtRelate.getSupplier().getProcurementDepId());
                    //权限id
                    todos.setPowerId(PropUtil.getProperty("gsyfs"));
                    //url
                    todos.setUrl("supplierAudit/essential.html?supplierId=" + supplierExtRelate.getSupplier().getId());
                    //类型
                    todos.setUndoType((short) 1);
                    todosService.insert(todos);
                    //更新待复审
                    supplierAuditService.findBySupplierId(supplierExtRelate.getSupplier().getId());
                    Supplier supplier = new Supplier();
                    supplier.setId(supplierExtRelate.getSupplier().getId());
                    supplier.setStatus(4);
                    supplierAuditService.updateStatus(supplier);
                    //根据当前用户获取机构信息
                    if(null != user && null != user.getOrg()){
                        Orgnization orgnization = orgnizationService.getOrgByPrimaryKey(user.getOrg().getId());
                        if(null != orgnization && null!=orgnization.getTypeName() && orgnization.getTypeName().equals("1")){
                            supplier.setExtractOrgid(orgnization.getId());
                            supplierService.updateExtractOrgidById(supplier);//更新机构ID
                        }
                    }
                }
            }
        }
        List<SupplierExtRelate> projectExtractListYes = resultProjectExtract(sq, ids);

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
    private List<SupplierExtRelate> resultProjectExtract(HttpServletRequest sq, String[] ids) {
        //存放已抽取的数量
        Map<String, Integer> mapcount = new HashMap<String, Integer>();
        //存放已操作
        List<SupplierExtRelate> projectExtractListYes=new ArrayList<SupplierExtRelate>();
        //未操作
        List<SupplierExtRelate> projectExtractListNo=new ArrayList<SupplierExtRelate>();
        //循环出抽取未抽取的
        forExtract(mapcount, ids[1], projectExtractListYes, projectExtractListNo, 0);
        //获取查询条件类型
        List<SupplierCondition> listCondition = conditionService.list(new SupplierCondition(ids[1],""),0);
        //抽取满足类型
        List<String> expertTypeIds = new ArrayList<String>();
        //保存所有抽取类型
        List<String> saveExpertTypeIds = new ArrayList<String>();
        for (SupplierConType extConType1 : listCondition.get(0).getConTypes()) {
            //获取抽取的供应商类别
            SupplierExtRelate extRelate = new SupplierExtRelate();
            extRelate.setReviewType(extConType1.getSupplierTypeId());
            extRelate.setSupplierConditionId(ids[1]);
            extRelate.setReason("1,2");
            List<SupplierExtRelate> list = extRelateService.list(extRelate,null);
            extConType1.setAlreadyCount(list == null ? 0 : list.size());
            //删除满足数量的
            if(list.size() >= extConType1.getSupplierCount()){
                if(extConType1.getSupplierType() != null){
                    expertTypeIds.add(extConType1.getSupplierType().getCode());
                }
            }
            if (extConType1.getSupplierType() != null && extConType1.getSupplierType() != null && !"".equals(extConType1.getSupplierType().getCode())) {
                saveExpertTypeIds.add(extConType1.getSupplierType().getCode());
            }
        }
        if (expertTypeIds != null && expertTypeIds.size() !=0){
            Packages packages = new Packages();
            packages.setId(listCondition.get(0).getProjectId());
            List<Packages> find = packagesService.find(packages);
            extRelateService.del(listCondition.get(0).getId(),find.get(0).getProjectId(),expertTypeIds,saveExpertTypeIds);
        }

        //拿出数量和session中存放的数字进行对比
        SupplierExtRelate pe = new SupplierExtRelate();
        pe.setId(ids[0]);
        List<SupplierExtRelate> list2 = extRelateService.list(pe,null);
        if(null != list2 && !list2.isEmpty()){
            SupplierConType extConType = conTypeService.getExtConType(list2.get(0).getConTypeId());
            Integer count = mapcount.get(extConType.getId());
            Integer sum = conTypeService.getSum(list2.get(0).getSupplierConditionId());
            if(sum == null){
                sum = 0;
            }
            if (count != null && count != 0){
                if (count >= sum){
                    extRelateService.updateStatusCount("1",extConType.getId());
                    //                删除为抽取的数据
                    Packages packages = new Packages();
                    packages.setId(list2.get(0).getProjectId());
                    List<Packages> find = packagesService.find(packages);
                    extRelateService.delPe(find.get(0).getProjectId());
                    forExtract(mapcount, ids[1], projectExtractListYes, projectExtractListNo,1);
                }else{
                    forExtract(mapcount, ids[1], projectExtractListYes, projectExtractListNo,1);
                }
            }
        }

        //获取查询条件类型
        List<SupplierConType> conTypes = listCondition.get(0).getConTypes();
        if(conTypes != null && conTypes.size() != 0){
            for (SupplierConType extConType1 : conTypes) {
                //获取抽取的专家类别
                SupplierExtRelate projectExtrac = new SupplierExtRelate();
                projectExtrac.setReviewType(extConType1.getSupplierTypeId());
                projectExtrac.setSupplierConditionId(ids[1]);
                projectExtrac.setReason("1,2");
                List<SupplierExtRelate> list = extRelateService.list(projectExtrac,null);
                extConType1.setAlreadyCount(list == null ? 0 : list.size());
            }
            projectExtractListYes.get(0).setConType(conTypes);
        }
        if (projectExtractListNo.size() != 0){
            projectExtractListYes.add(projectExtractListNo.get(0));
        }else{
            //已抽取
            //      conditionService.update(new SupplierCondition(ids[1],(short)2));
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
                            List<SupplierExtRelate> projectExtractListYes,
                            List<SupplierExtRelate> projectExtractListNo,Integer type) {
        //每次进入方法清除数据
        projectExtractListYes.clear();
        projectExtractListNo.clear();
        //查询数据
        List<SupplierExtRelate> list = extRelateService.list(new SupplierExtRelate(expertConditionId),null);
        for (SupplierExtRelate projectExtract : list) {
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
            } else if (projectExtract.getOperatingType() != null && projectExtract.getOperatingType() == 3){
                //不参与
                projectExtractListYes.add(projectExtract);
                if (type == 0){
                    SupplierConType extConType = conTypeService.getExtConType(projectExtract.getConTypeId());
                    extRelateService.updateStatusCount("0",extConType.getId());
                }
            } else {
                projectExtractListNo.add(projectExtract);
            }
        }
    }



    /**
     * @Description:供应商抽取记录集合
     *
     * @author Wang Wenshuai
     * @version 2016年9月29日 下午2:11:25
     * @param @param model
     * @param @return
     * @return String
     */
    @RequestMapping("/resuleRecordlist")
    public String resuleRecord(Model model,SupplierExtracts se,String page){
        List<SupplierExtracts> listExtractRecord = expExtractRecordService.listExtractRecord(se,page!=null&&!page.equals("")?Integer.parseInt(page):1);
        model.addAttribute("extractslist", new PageInfo<SupplierExtracts>(listExtractRecord));
        model.addAttribute("se", se);
        return "ses/sms/supplier_extracts/recordlist";
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
    public String showRecord(Model model, String id,String projectId,String packageId,String typeclassId){
        model.addAttribute("typeclassId", typeclassId);
        model.addAttribute("projectId", projectId);
        model.addAttribute("packageId", packageId);
        SupplierExtracts showExpExtractRecord=null;
        if (projectId != null && projectId != null){
            //获取抽取记录
            SupplierExtracts extracts = new SupplierExtracts();
            extracts.setProjectId(projectId);
            List<SupplierExtracts> listExtractRecord = expExtractRecordService.listExtractRecord(extracts,0);
            if(listExtractRecord != null && listExtractRecord.size() !=0){
                showExpExtractRecord = listExtractRecord.get(0);
                model.addAttribute("ExpExtractRecord", showExpExtractRecord);
                //获取监督人员
                List<SupplierExtUser>    listUser = extUserServicl.list(new SupplierExtUser(showExpExtractRecord.getProjectId()));
                model.addAttribute("listUser", listUser);
                //抽取条件
                List<Packages> conList = packagesService.listExpExtCondition(showExpExtractRecord.getProjectId());
                model.addAttribute("conditionList", conList);

                if (packageId != null && !"".equals(packageId)){
                    //已抽取
                    String[] packageIds =  packageId.split(",");
                    if(packageIds.length != 0 ){
                        SupplierCondition con = null;
                        for (String pckId : packageIds) {
                            if(pckId != null && !"".equals(pckId)){
                                con = new SupplierCondition();
                                con.setProjectId(pckId);
                                con.setStatus((short)2);
                                conditionService.update(con);
                            }
                        }
                    }
                }

            }

        }else{
            //获取抽取记录
            List<SupplierExtracts> listExtractRecord = expExtractRecordService.listExtractRecord(new SupplierExtracts(id),0);
            if(listExtractRecord != null && listExtractRecord.size() !=0){
                showExpExtractRecord = listExtractRecord.get(0);
                model.addAttribute("ExpExtractRecord", showExpExtractRecord);
            }
        }
        return "ses/sms/supplier_extracts/extract_supervise_word";
    }

    /**
     * @Description: 获取市
     *
     * @author Wang Wenshuai
     * @date 2016年9月18日 下午4:16:35
     * @param @return
     * @return String
     */
    @ResponseBody
    @RequestMapping("/city")
    public Object city(Model model, String area){

        List<Area> listArea = areaService.findTreeByPid(area == null ? "1" : area, null);

        return listArea;
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
        if (projectId != null && !"".equals(projectId)) {
            model.addAttribute("projectId", projectId);
            List<SupplierExtUser> list = extUserServicel.list(new SupplierExtUser(projectId));
            model.addAttribute("list", list);
        }
        model.addAttribute("type", "supplier");
        return "ses/sms/supplier_extracts/supervise_list";
    }

    @ResponseBody
    @RequestMapping("/isFinish")
    public String isFinish(String packageId){
        //获取查询条件类型
        SupplierCondition condition = new SupplierCondition();
        condition.setProjectId(packageId);
        condition.setStatus((short)1);
        String finish = conditionService.isFinish(condition);

        return JSON.toJSONString(finish);
    }

    /**
     * @Description:展示添加临时专家页面
     *
     * @author Wang Wenshuai
     * @version 2016年10月14日 下午7:29:36
     * @param model  实体
     * @param  id 专家id
     */
    @RequestMapping("/showTemporarySupplier")
    public  String showTemporaryExpert(Model model, HttpServletRequest request, String packageId,String projectId,String flowDefineId,String ix){
        //该环节设置为执行中状态
        //flowMangeService.flowExe(request, flowDefineId, projectId, 2);
        model.addAttribute("packageId", packageId);
        model.addAttribute("ix", ix);
        model.addAttribute("projectId", projectId);
        model.addAttribute("expert", new Expert());
        model.addAttribute("flowDefineId", flowDefineId);
        return "bss/ppms/sall_tender/temporary_supplier_add";
    }
    
    /**
     * 
    	 * @Title: checkSupplierName
    	 * @author: Zhou Wei
    	 * @date: 2017年8月28日 下午5:11:32
    	 * @Description:  验证临时供应商名称
    	 * @return: String
     */
    @RequestMapping(value="/checkSupplierName",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String checkSupplierName(String packageId,String projectId){
    	Supplier supplier=new Supplier();
        String name="";
        SaleTender saleTender = new SaleTender();
       
        saleTender.setProject(new Project(projectId));
        saleTender.setPackages(packageId);
        saleTender.setSuppliers(supplier);
        List<SaleTender> saleTenderList = saleTenderService.getPackegeSupplier(saleTender);
        List<String> sippName=new ArrayList<>();
        for (SaleTender saleTender2 : saleTenderList) {
        	name = saleTender2.getSuppliers().getSupplierName();
			sippName.add(name);
		}
        return JSON.toJSONString(sippName);
    }
    
    /**
     * 
     * @Description:社会统一信用代码唯一校验
     * @author: Zhou Wei
     * @date: 2017年8月4日 下午2:16:01
     * @return: String
     */
    
   @RequestMapping("/selectUniqueReferenceNO")
   @ResponseBody
	public Integer selectUniqueReferenceNO(Model model,Supplier supplier,String creditCode){
	   Integer type = supplierService.CreditCode(creditCode);
	   return  type;
   }
    
    
    /**
     * @Description:添加临时供应商
     *
     * @author Wang Wenshuai
     * @version 2016年10月14日 下午7:29:36
     * @param model  实体
     * @param  id 专家id
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value="AddtemporarySupplier",produces = "text/html;charset=UTF-8")
    public  Object addTemporaryExpert(Supplier supplier,  Model model, String projectId,String packageId, String loginName, String loginPwd,String flowDefineId,HttpServletRequest sq,String ix) throws UnsupportedEncodingException{
        Integer type = 0;
        //转码
        if (supplier != null) {
            supplier = JSON.parseObject(URLDecoder.decode(JSON.toJSONString(supplier),"UTF-8"), Supplier.class);
        }
        if (loginName != null && !"".equals(loginName)) {
            loginName = URLDecoder.decode(loginName,"UTF-8");
        }
        if (loginPwd != null && !"".equals(loginPwd)) {
            loginPwd = URLDecoder.decode(loginPwd,"UTF-8");
        }




        if (supplier.getSupplierName() == null || "".equals(supplier.getSupplierName())) {
            model.addAttribute("supplierNameError", "不能为空");
            type = 1;
        }

        if (supplier.getArmyBusinessName() == null || "".equals(supplier.getArmyBusinessName())) {
            model.addAttribute("armyBusinessNameError", "不能为空");
            type = 1;
        }

        if (supplier.getArmyBuinessTelephone() == null || "".equals(supplier.getArmyBuinessTelephone())) {
            model.addAttribute("armyBuinessTelephoneError", "不能为空");
            type = 1;
        }
        if (StringUtils.isNotBlank(supplier.getCreditCode()) && StringUtils.isNotBlank(supplier.getArmyBuinessTelephone())) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("armyBuinessTelephone", supplier.getArmyBuinessTelephone());
            map.put("creditCode", supplier.getCreditCode());
            map.put("isProvisional",1);
            //查询临时供应商
            List<Supplier> tempList = supplierService.viewCreditCodeMobile(map);
            if (supplier.getCreditCode().length() > 36) {
                model.addAttribute("creditCodeError", "不能为空或是字符过长!");
                type = 1;
            }

            if (supplier.getCreditCode().length() != 18) {
                model.addAttribute("creditCodeError", "格式错误!");
                type = 1;
            }
            if (tempList != null && tempList.size() > 0) {
                for (Supplier supp : tempList) {
                    if (!supp.getId().equals(supplier.getId())) {
                        model.addAttribute("creditCodeError", "社会统一信用代码已被占用!");
                        type = 1;
                        break;
                    }
                }
            }
        } else {
            model.addAttribute("creditCodeError", "不能为空");
            type = 1;
        }

        if (loginName == null || "".equals(loginName)) {
            model.addAttribute("loginNameError", "不能为空");
            type = 1;
        } else {
            //校验用户名是否存在
            List<User> users = userServicl.findByLoginName(loginName);
            if (users.size() > 0) {
                type = 1;
                model.addAttribute("loginNameError", "用户名已存在");
            }
        }

        if (loginPwd == null || "".equals(loginPwd)) {
            model.addAttribute("loginPwdError", "不能为空");
            type = 1;
        }

        if (type == 1) {
            model.addAttribute("supplier", supplier);
            model.addAttribute("loginName", loginName);
            //model.addAttribute("loginPwd", loginPwd);
            model.addAttribute("projectId", projectId);
            model.addAttribute("packageId", packageId);
            model.addAttribute("flowDefineId", flowDefineId);
            model.addAttribute("ix", ix);
            //专家类型
            //            model.addAttribute("ddList", expExtractRecordService.ddList());
            //证件类型
            //            model.addAttribute("idType", DictionaryDataUtil.find(9));
            return "bss/ppms/sall_tender/temporary_supplier_add";
        }


        expExtractRecordService.addTemporaryExpert(supplier, projectId,packageId, loginName, loginPwd,sq);
        String ALLCHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < 15; i++) {
            sb.append(ALLCHAR.charAt(random.nextInt(ALLCHAR.length())));
        }
        String randomCode = sb.toString();
        flowMangeService.flowExe(request, flowDefineId, projectId, 2);
        return  "redirect:/saleTender/view.html?projectId=" + projectId + "&&flowDefineId=" + flowDefineId + "&ix=" + ix + "&randomCode=" + randomCode;
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
    public String getTree(Category category,String projectId){
        List<CategoryTree> jList=new ArrayList<CategoryTree>();
        //获取字典表中的根数据
        if (category.getId() == null || "0".equals(category.getId())){
            if (projectId != null && !"".equals(projectId)){
                //获取关联包id
                Project project = projectService.selectById(projectId);
                DictionaryData dictionaryData = dictionaryDataServiceI.getDictionaryData(project.getPlanType());
                if(dictionaryData != null  ){
                    CategoryTree ct = new CategoryTree();
                    ct.setId(dictionaryData.getId());
                    ct.setName(dictionaryData.getName());
                    ct.setCode(dictionaryData.getCode());
                    ct.setIsParent("true");
                    jList.add(ct);
                }else{
                    category.setId("0");
                    DictionaryData data = new DictionaryData();
                    data.setKind(6);
                    List<DictionaryData> listByPage = dictionaryDataServiceI.listByPage(data, 1);
                    for (DictionaryData dictionaryData1 : listByPage) {
                        CategoryTree ct = new CategoryTree();
                        ct.setId(dictionaryData1.getId());
                        ct.setName(dictionaryData1.getName());
                        ct.setCode(dictionaryData1.getCode());
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
    public String reasonNumber(Model model,String[] supplierTypeId,String addressReson,String eCount){
        model.addAttribute("supplierTypeCode", supplierTypeId);
        model.addAttribute("addressReson", addressReson);
        model.addAttribute("eCount", eCount);
        return "ses/sms/supplier_extracts/reason_and_number";
    }

    /**
     *
     *〈简述〉供应商类型
     *〈详细描述〉
     * @author Wang Wenshuai
     * @return
     */
    @ResponseBody
    @RequestMapping("/supplieType")
    public String supplierType(){
        List<DictionaryData> supplierTypeList = conditionService.supplierTypeList();
        return JSON.toJSONString(supplierTypeList);
    }

    /**
     *
     *〈简述〉打开添加包
     *〈详细描述〉
     * @author Wang Wenshuai
     * @return
     */
    @RequestMapping("/showPackage")
    public String showPackage(Model model, String projectId){
        model.addAttribute("projectId", projectId);
        return "ses/sms/supplier_extracts/add_packages";
    }

    /**
     *
     *〈简述〉添加包
     *〈详细描述〉
     * @author Wang Wenshuai
     * @return
     */
    @ResponseBody
    @RequestMapping("/addPackage")
    public String addPackage(String packagesName,String projectId){
        Map<String, String> map = new HashMap<String, String>();
        String   status = conditionService.addPackage(packagesName, projectId);
        if(!"ERROR".equals(status)){
            map.put("packagesName", packagesName);
            map.put("status", status);
        }else{
            map.put("status", status);
        }
        return JSON.toJSONString(map);
    }

    /**
     *〈简述〉修改临时供应商
     *〈详细描述〉
     * @author Ye MaoLin
     * @param model
     * @param request
     * @param flowDefineId
     * @param supplierId
     * @param ix
     * @return
     */
    @RequestMapping("/editTemporarySupplier")
    public String editTemporarySupplier(Model model, HttpServletRequest request,String projectId, String flowDefineId, String supplierId, String ix){
        if(supplierId != null && !"".equals(supplierId)){
            model.addAttribute("ix", ix);
            Supplier supplier = supplierService.selectById(supplierId);
            if (supplier != null) {
              model.addAttribute("supplier", supplier);
              model.addAttribute("flowDefineId", flowDefineId);
              model.addAttribute("projectId", projectId);
              User user = userServicl.findByTypeId(supplier.getId());
              if (user != null) {
                model.addAttribute("loginName", user.getLoginName());
              }
            }
        }
        return "bss/ppms/sall_tender/temporary_supplier_edit";
    }
    
    /**
     *〈简述〉修改临时供应商
     *〈详细描述〉
     * @author Ye MaoLin
     * @param projectId
     * @param supplier
     * @param model
     * @param loginName
     * @param loginPwd
     * @param flowDefineId
     * @param sq
     * @param ix
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value="updateTemporarySupplier",produces = "text/html;charset=UTF-8")
    public Object updateTemporarySupplier(String projectId, Supplier supplier, Model model, String loginName, String loginPwd,String flowDefineId, HttpServletRequest sq, String ix) throws UnsupportedEncodingException{
        Integer type = 0;
        //转码
        if (supplier != null) {
            supplier = JSON.parseObject(URLDecoder.decode(JSON.toJSONString(supplier),"UTF-8"), Supplier.class);
        }
        if (loginName != null && !"".equals(loginName)) {
            loginName = URLDecoder.decode(loginName,"UTF-8");
        }
        if (loginPwd != null && !"".equals(loginPwd)) {
            loginPwd = URLDecoder.decode(loginPwd,"UTF-8");
        }

        if (supplier.getSupplierName() == null || "".equals(supplier.getSupplierName())) {
            model.addAttribute("supplierNameError", "不能为空");
            type = 1;
        }

        if (supplier.getArmyBusinessName() == null || "".equals(supplier.getArmyBusinessName())) {
            model.addAttribute("armyBusinessNameError", "不能为空");
            type = 1;
        }

        if (supplier.getArmyBuinessTelephone() == null || "".equals(supplier.getArmyBuinessTelephone())) {
            model.addAttribute("armyBuinessTelephoneError", "不能为空");
            type = 1;
        }
        
        if (StringUtils.isNotBlank(supplier.getCreditCode()) && StringUtils.isNotBlank(supplier.getArmyBuinessTelephone())) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("armyBuinessTelephone", supplier.getArmyBuinessTelephone());
            map.put("creditCode", supplier.getCreditCode());
            map.put("isProvisional",1);
            //查询临时供应商
            List<Supplier> tempList = supplierService.viewCreditCodeMobile(map);
            if (supplier.getCreditCode().length() > 36) {
                model.addAttribute("creditCodeError", "不能为空或是字符过长!");
                type = 1;
            }

            if (supplier.getCreditCode().length() != 18) {
                model.addAttribute("creditCodeError", "格式错误!");
                type = 1;
            }
            if (tempList != null && tempList.size() > 0) {
                for (Supplier supp : tempList) {
                    if (!supp.getId().equals(supplier.getId())) {
                        model.addAttribute("creditCodeError", "社会统一信用代码已被占用!");
                        type = 1;
                        break;
                    }
                }
            }
        } else {
            model.addAttribute("creditCodeError", "不能为空");
            type = 1;
        }

        if (loginName == null || "".equals(loginName)) {
            model.addAttribute("loginNameError", "不能为空");
            type = 1;
        } else {
            //校验用户名是否存在
            List<User> users = userServicl.findByLoginName(loginName);
            if (users != null && users.size() > 0 && !supplier.getId().equals(users.get(0).getTypeId())) {
                type = 1;
                model.addAttribute("loginNameError", "用户名已存在");
            }
        }

        /*if (loginPwd == null || "".equals(loginPwd)) {
            model.addAttribute("loginPwdError", "不能为空");
            type = 1;
        }*/

        if (type == 1) {
            model.addAttribute("supplier", supplier);
            model.addAttribute("loginName", loginName);
            //model.addAttribute("loginPwd", loginPwd);
            model.addAttribute("projectId", projectId);
            model.addAttribute("flowDefineId", flowDefineId);
            model.addAttribute("ix", ix);
            return "bss/ppms/sall_tender/temporary_supplier_edit";
        }

        expExtractRecordService.updateTemporaryExpert(supplier, loginName, loginPwd,sq);
        String ALLCHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < 15; i++) {
            sb.append(ALLCHAR.charAt(random.nextInt(ALLCHAR.length())));
        }
        String randomCode = sb.toString();
        flowMangeService.flowExe(request, flowDefineId, projectId, 2);
        return  "redirect:/saleTender/view.html?projectId=" + projectId + "&&flowDefineId=" + flowDefineId + "&ix=" + ix + "&randomCode=" + randomCode;
    }

}
