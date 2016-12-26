
package ses.controller.sys.sms;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import bss.controller.base.BaseController;
import bss.model.ppms.Packages;
import bss.model.ppms.Project;
import bss.model.ppms.SaleTender;
import bss.service.ppms.PackageService;
import bss.service.ppms.ProjectService;
import bss.service.ppms.SaleTenderService;
import bss.util.PropUtil;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import ses.dao.bms.DictionaryDataMapper;
import ses.model.bms.Area;
import ses.model.bms.Category;
import ses.model.bms.CategoryTree;
import ses.model.bms.DictionaryData;
import ses.model.bms.Todos;
import ses.model.bms.User;
import ses.model.ems.ExpExtCondition;
import ses.model.ems.Expert;
import ses.model.ems.ExtConType;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierAudit;
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
import ses.service.sms.SupplierAuditService;
import ses.service.sms.SupplierConTypeService;
import ses.service.sms.SupplierConditionService;
import ses.service.sms.SupplierExtPackageServicel;
import ses.service.sms.SupplierExtRelateService;
import ses.service.sms.SupplierExtUserServicel;
import ses.service.sms.SupplierExtractsService;
import ses.util.DictionaryDataUtil;

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
    /**包service**/
    @Autowired
    private PackageService packageService;
    /**待办消息**/
    @Autowired
    private TodosService todosService;
    
    private SupplierAuditService supplierAuditService;

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
    public String list(Integer page, Model model, Project project){
        List<Project> list = projectService.provisionalList(page == null?1:page, project);
        List<DictionaryData> find = DictionaryDataUtil.find(5);
        PageInfo<Project> info = new PageInfo<>(list);
        model.addAttribute("info", info);
        model.addAttribute("projects", project);
        model.addAttribute("kind", find);

        return "ses/sms/supplier_extracts/project_list";
    }
    /**
     * @Description:条件查询集合
     *
     * @author Wang Wenshuai
     * @version 2016年9月27日 下午6:03:40  
     * @param  id 包id
     * @return String
     */
    @RequestMapping("/Extraction")	
    public String listExtraction(Model model,String projectId,String page,String typeclassId,String packageId){
        if (packageId != null && !"".equals(packageId)){
            //已抽取
            SupplierCondition con = new SupplierCondition();
            con.setProjectId(packageId);
            con.setStatus((short)2);
            conditionService.update(con);
        }


        //根据包获取抽取出的供应商
        List<Packages> listResultSupplier = packageService.listResultSupplier(projectId);
        model.addAttribute("listResultSupplier", listResultSupplier);


        List<Area> listArea = areaService.findTreeByPid("0",null);
        model.addAttribute("listArea", listArea);
        model.addAttribute("typeclassId",typeclassId);
        if (projectId != null && !"".equals(projectId)){

            //供应商抽取地址
            SupplierExtracts record = new SupplierExtracts();
            record.setProjectId(projectId);
            List<SupplierExtracts> listSe = expExtractRecordService.listExtractRecord(record,0);
            if (listSe != null && listSe.size() != 0){
                model.addAttribute("extractionSites", listSe.get(0).getExtractionSites());
            }

            //获取监督人员
            List<User>  listUser = extUserServicl.list(new SupplierExtUser(projectId));
            model.addAttribute("listUser", listUser);
            String userName = "";
            String userId = "";
            if (listUser != null && listUser.size() != 0){
                for (User user : listUser) {
                    if (user != null) {
                        userName += user.getRelName() + ",";
                        userId += user.getId() + ",";
                    }
                }
                if (!"".equals(userId)){
                    model.addAttribute("userName", userName.substring(0, userName.length()-1));
                    model.addAttribute("userId", userId.substring(0, userId.length()-1));
                }

            }

            //获取项目信息
            Project project = projectService.selectById(projectId);
            if (project != null){
                model.addAttribute("projectId", project.getId());
                model.addAttribute("projectName", project.getName());
                model.addAttribute("projectNumber", project.getProjectNumber());
                //获取采购方式
                List<DictionaryData>  dictionaryData = new ArrayList<DictionaryData>();
                dictionaryData.add(dictionaryDataServiceI.getDictionaryData(project.getPurchaseType()));
                model.addAttribute("findByMap", dictionaryData);
            }

            //抽取条件集合
            List<SupplierCondition> listCon = conditionService.list(new SupplierCondition(projectId), page == null ? 1 : Integer.valueOf(page));
            model.addAttribute("list", new PageInfo<SupplierCondition>(listCon));

        } else {
            Map<String, Object> map = new HashMap<String, Object>();
            String[] str = {"YQZB", "DYLY", "JZXTP", "XJCG", "GKZB"};
            map.put("strs", str);
            List<DictionaryData> findByMap = dictionaryDataMapper.findByMap(map);
            model.addAttribute("findByMap", findByMap);
        }

        return "ses/sms/supplier_extracts/condition_list";
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
    public String addExtraction(Model model,String typeclassId,String projectId,String packageId){
        List<Area> privnce = areaService.findRootArea();
        model.addAttribute("privnce", privnce);
        model.addAttribute("typeclassId", typeclassId);
        model.addAttribute("projectId", projectId);
        model.addAttribute("packageId", packageId);

        //获取查询条件类型
        SupplierCondition condition=new SupplierCondition();
        condition.setProjectId(packageId);
        condition.setStatus((short)1);
        List<SupplierCondition> listCon = conditionService.list(condition,0);
        if (listCon != null && listCon.size() !=0 ){
            model.addAttribute("listCon", listCon.get(0));
            //所在地区回显
            if (listCon.get(0).getAddress() != null && listCon.get(0).getAddress() != null ){
                Area area = areaService.listById(listCon.get(0).getAddress());
                List<Area> city = areaService.findAreaByParentId(area.getParentId());
                model.addAttribute("city", city);
                model.addAttribute("area", area);
            }
            Map<String, Integer> mapcount = new HashMap<String, Integer>();
            List<SupplierExtRelate> list = extRelateService.list(new SupplierExtRelate(listCon.get(0).getId()), "");
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
                List<SupplierCondition> listCondition = conditionService.list(new SupplierCondition(condition.getId(), ""), 0);
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
                    conditionService.update(new SupplierCondition(condition.getId(), (short)2));
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
    public String validateAddExtraction(Project project, String packageName, String typeclassId, String[] sids, String extractionSites,HttpServletRequest sq){
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


        if (sids == null || sids.length==0 || "".equals(sids)){
            map.put("supervise", "不能为空");
            count=1;
        }

        if (extractionSites == null ||  "".equals(extractionSites)){
            map.put("extractionSitesError", "不能为空");
            count=1;
        }

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
                packageId=packages.getId();
                SupplierExtPackage sExtPackage = new SupplierExtPackage();
                sExtPackage.setPackageId(packages.getId());
                sExtPackage.setProjectId(project.getId());

                supplierExtPackageServicel.insert(sExtPackage);
                project.setId(sExtPackage.getId());
            }
            //抽取地址
            if (extractionSites != null && !"".equals(extractionSites)){
                SupplierExtracts supplierExtracts = new SupplierExtracts();
                supplierExtracts.setProjectId(projectId);
                PageHelper.startPage(1, 1);
                List<SupplierExtracts> listSe = expExtractRecordService.listExtractRecord(supplierExtracts,0);
                supplierExtracts.setExtractionSites(extractionSites);
                User user = (User)sq.getSession().getAttribute("loginUser");
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
            //监督人员
            if (sids != null && sids.length != 0){
                extUserServicel.deleteProjectId(project.getId());
                for (String id : sids) {
                    if (!"".equals(id)){
                        SupplierExtUser  record1 = new SupplierExtUser();
                        record1.setProjectId(project.getId());
                        record1.setUserId(id);
                        extUserServicel.insert(record1);
                    }
                }
            }
            //获取抽取条件状态，未抽取不能在抽取
            //            Integer count2 = conditionService.getCount(project.getId());
            //            if (count2 != null && count2 != 0){
            //                map.put("status", count2.toString());
            //            } else {
            map.put("projectId", project.getId());
            map.put("sccuess", "SCCUESS");
            //            }
            return JSON.toJSONString(map);
        }


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
    @RequestMapping("getpackage")
    public String getPackage(String projectId){
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("projectId",projectId);
        List<Packages> findPackageById = packagesService.findPackageById(map);
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
        User user = (User) sq.getSession().getAttribute("loginUser");
        List<SupplierExtRelate> list = extRelateService.list(new SupplierExtRelate(cId), "");
        if (list == null || list.size() == 0){
            extRelateService.insert(cId, user != null && !"".equals(user.getId()) ? user.getId() : "");
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
            conditionService.update(new SupplierCondition(cId, (short)2));
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
    public Object resultextract(Model model,String id,String reason,HttpServletRequest sq,HttpSession session){

        //		修改状态
        String ids[]=id.split(",");

        if (reason != null && !"".equals(reason)){
            extRelateService.update(new SupplierExtRelate(ids[0],new Short(ids[2]),reason));
        }else{
            extRelateService.update(new SupplierExtRelate(ids[0],new Short(ids[2])));
        }
        if ("1".equals(ids[2])){
            SupplierExtRelate supplierExtRelate = extRelateService.getSupplierExtRelate(ids[0]);
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("id", supplierExtRelate.getProjectId());
            List<Packages> findPackageById = packagesService.findPackageById(map);
            SaleTender saleTender = new SaleTender();
            saleTender.setProjectId(findPackageById.get(0).getProjectId());
            saleTender.setSupplierId(supplierExtRelate.getSupplier().getId());
            saleTender.setPackages(supplierExtRelate.getProjectId());   
            saleTenderService.insert(saleTender);
            
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
                SupplierAudit supplierAudit = new SupplierAudit();
                supplierAudit.setId(supplierExtRelate.getSupplier().getId());
                supplierAudit.setStatus(4);
                supplierAuditService.updateStatusById(supplierAudit);
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
        //拿出数量和session中存放的数字进行对比
        SupplierExtRelate pe = new SupplierExtRelate();
        pe.setId(ids[0]);
        List<SupplierExtRelate> list2 = extRelateService.list(pe,null);
        SupplierConType extConType = conTypeService.getExtConType(list2.get(0).getConTypeId());
        Integer count = mapcount.get(extConType.getId());
        if (count != null && count != 0){
            if (count >= extConType.getSupplierCount()){
                extRelateService.updateStatusCount("1",extConType.getId());
                forExtract(mapcount, ids[1], projectExtractListYes, projectExtractListNo,1);
            }else{
                forExtract(mapcount, ids[1], projectExtractListYes, projectExtractListNo,1);
            }
        }

        //获取查询条件类型
        List<SupplierCondition> listCondition = conditionService.list(new SupplierCondition(ids[1],""),0);
        List<SupplierConType> conTypes = listCondition.get(0).getConTypes();
        for (SupplierConType extConType1 : conTypes) {
            extConType1.setAlreadyCount(mapcount.get(extConType1.getId()) == null ? 0 : mapcount.get(extConType1.getId()));
        }
        projectExtractListYes.get(0).setConType(conTypes);
        if (projectExtractListNo.size() != 0){
            projectExtractListYes.add(projectExtractListNo.get(0));
        }else{
            //已抽取
            conditionService.update(new SupplierCondition(ids[1],(short)2));
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
    public String showRecord(Model model, String id,String projectId){
        SupplierExtracts showExpExtractRecord=null;
        if (projectId != null && projectId != null){
            //获取抽取记录
            SupplierExtracts extracts = new SupplierExtracts();
            extracts.setProjectId(projectId);
            List<SupplierExtracts> listExtractRecord = expExtractRecordService.listExtractRecord(extracts,0);
            if(listExtractRecord != null && listExtractRecord.size() !=0){
                showExpExtractRecord = listExtractRecord.get(0);
                model.addAttribute("ExpExtractRecord", showExpExtractRecord);
            }

        }else{
            //获取抽取记录
            List<SupplierExtracts> listExtractRecord = expExtractRecordService.listExtractRecord(new SupplierExtracts(id),0);
            if(listExtractRecord != null && listExtractRecord.size() !=0){
                showExpExtractRecord = listExtractRecord.get(0);
                model.addAttribute("ExpExtractRecord", showExpExtractRecord);
            }
        }

        //抽取条件
        if(showExpExtractRecord != null){
            SupplierExtPackage extPackage = new SupplierExtPackage();
            extPackage.setProjectId(showExpExtractRecord.getProjectId());
            List<SupplierExtPackage> conditionList = supplierExtPackageServicel.extractsList(extPackage);
            model.addAttribute("conditionList", conditionList);
            //        List<List<SupplierExtRelate>> listEp = new ArrayList<List<SupplierExtRelate>>();
            //        获取供应商人数  
            //        for (SupplierCondition expExtCondition : conditionList) {
            //            SupplierExtRelate pExtract = new SupplierExtRelate();
            //            pExtract.setProjectId(showExpExtractRecord.getProjectId());
            //            pExtract.setSupplierConditionId(expExtCondition.getId());
            //            //占用字段保存状态类型
            //            pExtract.setReason("1,2,3");
            //            List<SupplierExtRelate> projectExtract = extRelateService.list(pExtract, ""); 
            //            listEp.add(projectExtract);
            //        }
            //        model.addAttribute("ProjectExtract", listEp);
            //        //获取监督人员
            //            array
            if (conditionList != null && conditionList.size() != 0){
                List<User>  listUser = null ;
                for (SupplierExtPackage supplierExtPackage : conditionList) {
                    listUser = extUserServicl.list(new SupplierExtUser(supplierExtPackage.getId()));
                    if(listUser != null ){
                        break; 
                    }
                }
                model.addAttribute("listUser", listUser);
            }
        }
        return "ses/sms/supplier_extracts/show_info";
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
    public String showSupervise(Model model, Integer page){
        User user = new User();
        //监督人员
        //                user.setTypeName(DictionaryDataUtil.get("SUPERVISER_U").getId());
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("code", "SUPERVISER_R");
        List<User> users = userServicl.findByRole(map);
        model.addAttribute("list", new PageInfo<User>(users));
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
    public  String showTemporaryExpert(Model model,String packageId,String projectId,String flowDefineId){
        model.addAttribute("packageId", packageId);
        model.addAttribute("projectId", projectId);
        model.addAttribute("expert", new Expert());
        model.addAttribute("flowDefineId", flowDefineId);
        return "bss/ppms/sall_tender/temporary_supplier_add";
    }

    /**
     * @Description:添加临时供应商
     *
     * @author Wang Wenshuai
     * @version 2016年10月14日 下午7:29:36  
     * @param model  实体
     * @param  id 专家id
     */
    @RequestMapping("/AddtemporarySupplier")
    public  Object addTemporaryExpert(Supplier supplier,  Model model, String projectId,String packageId, String loginName, String loginPwd,String flowDefineId,HttpServletRequest sq){
        Integer type = 0;
     
        if (supplier.getSupplierName() == null || "".equals(supplier.getSupplierName())){
            model.addAttribute("supplierNameError", "不能为空");
            type = 1;
        }

        if(supplier.getArmyBusinessName() == null || "".equals(supplier.getArmyBusinessName())){
            model.addAttribute("armyBuinessTelephoneError", "不能为空");
            type = 1;
        }

        if(supplier.getArmyBuinessTelephone() == null || "".equals(supplier.getArmyBuinessTelephone())){
            model.addAttribute("armyBuinessTelephoneError", "不能为空");
            type = 1;
        }

        if(supplier.getCreditCode() == null || "".equals(supplier.getCreditCode())){
            model.addAttribute("creditCodeError", "不能为空");
            type = 1;
        }

        if (loginName == null || "".equals(loginName)){
            model.addAttribute("loginNameError", "不能为空");
            type = 1;
        }else{
            //校验用户名是否存在
            List<User> users = userServicl.findByLoginName(loginName);
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
            model.addAttribute("supplier", supplier);
            model.addAttribute("loginName", loginName);
            model.addAttribute("loginPwd", loginPwd);
            model.addAttribute("projectId", projectId);
            model.addAttribute("packageId", packageId);
            model.addAttribute("flowDefineId", flowDefineId);
            //专家类型
            //            model.addAttribute("ddList", expExtractRecordService.ddList());
            //证件类型
            //            model.addAttribute("idType", DictionaryDataUtil.find(9));
            return "bss/ppms/sall_tender/temporary_supplier_add";
        }


        expExtractRecordService.addTemporaryExpert(supplier, projectId,packageId, loginName, loginPwd,sq);

        return  "redirect:/saleTender/view.html?projectId=" + projectId + "&&flowDefineId=" + flowDefineId;
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
}
