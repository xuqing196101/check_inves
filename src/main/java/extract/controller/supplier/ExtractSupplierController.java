
package extract.controller.supplier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.model.bms.Area;
import ses.model.bms.Category;
import ses.model.bms.CategoryTree;
import ses.model.bms.DictionaryData;
import ses.model.bms.User;
import ses.model.ems.ExtConType;
import ses.model.sms.Supplier;
import ses.service.bms.AreaServiceI;
import ses.service.bms.CategoryService;
import ses.service.sms.SupplierService;
import ses.util.WfUtil;
import bss.controller.base.BaseController;
import bss.model.ppms.Packages;
import bss.model.ppms.Project;
import bss.service.ppms.PackageService;
import bss.service.ppms.ProjectService;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import common.annotation.CurrentUser;

import extract.model.supplier.SupplierExtractCondition;
import extract.model.supplier.SupplierExtractProjectInfo;
import extract.model.supplier.SupplierExtractResult;
import extract.service.supplier.SupplierExtractConditionService;
import extract.service.supplier.SupplierExtractRecordService;
import extract.service.supplier.SupplierExtractRelateResultService;
import extract.service.supplier.SupplierPersonServicel;

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
public class ExtractSupplierController extends BaseController {
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
    private SupplierExtractConditionService conditionService; //条件
    @Autowired
    private SupplierExtractRelateResultService extRelateService; //抽取结果关联表
    @Autowired
    private SupplierExtractRecordService expExtractRecordService; //记录
    @Autowired
    private CategoryService categoryService; //品目
    @Autowired
    private SupplierService supplierService;
    @Autowired
    private SupplierPersonServicel supplierPersonServicel;
    
    
    
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
    	List<SupplierExtractProjectInfo> extractRecords = expExtractRecordService.getList(page == null?1:page);
    	model.addAttribute("info", new PageInfo<SupplierExtractProjectInfo>(extractRecords));
        return "ses/sms/supplier_extracts/project_list";
    }
    /**
     *@Description:条件查询集合 / 跳转抽取条件页面，准备抽取
     *
     * @author Wang Wenshuai
     * @version 2016年9月27日 下午6:03:40
     * @param  id 包id
     * @return String
     */
    @RequestMapping("/Extraction")
   // public String listExtraction(@CurrentUser User user, Model model, String projectId, String page, String typeclassId, String packageId){
   public String listExtraction(@CurrentUser User user,Model model, SupplierExtractProjectInfo eRecord,String conditionId){

    	if(StringUtils.isEmpty(conditionId)){
    		conditionId = WfUtil.createUUID();
    		//生成一条查询条件
    		SupplierExtractCondition supplierCondition = new SupplierExtractCondition();
    		supplierCondition.setId(conditionId);
    		conditionService.insert(supplierCondition);
    		//将conditionId 插入记录表
    		eRecord.setConditionId(conditionId);
    		model.addAttribute("condition",supplierCondition);
    	}else{
    		//TUTO  抽取条件回显
			model.addAttribute("condition",conditionService.show(conditionId));
    	}
    	
    	//记录为空
    	if(StringUtils.isBlank(eRecord.getId())){
    		String recordId = WfUtil.createUUID();
    		eRecord.setId(recordId);
    		expExtractRecordService.insert(eRecord);
    		model.addAttribute("recordId", recordId);
    		if(StringUtils.isNotBlank(eRecord.getProjectId())){
    			//说明是从项目实施进入 需要查询项目信息，生成一条记录，查询项目信息(包信息),条件id
    			model.addAttribute("projectInfo", projectService.selectById(eRecord.getProjectId()));
    			model.addAttribute("typeclassId", "hidden");
    		}else{
    			//点击人工抽取
    			//model.addAttribute("purchaseTypes",DictionaryDataUtil.find(5));
    		}
    		
    	}else if(StringUtils.isNotBlank(eRecord.getId())){
    		
    		eRecord=expExtractRecordService.selectByPrimaryKey(eRecord.getId());
    		if(StringUtils.isEmpty(eRecord.getConditionId())){
    			eRecord.setConditionId(conditionId);
    			expExtractRecordService.update(eRecord);
    		}
    		//从记录列表进入 继续抽取 项目信息
    		model.addAttribute("projectInfo",eRecord);
    		//model.addAttribute("supervises",extUserServicel.getName(eRecord.getId()));
    		//重新抽取  只携带项目信息
    	}
    	List<Area> province = areaService.findRootArea();
    	model.addAttribute("businessNature", conditionService.getBusinessNature());
    	model.addAttribute("province", province);
    	return "ses/sms/supplier_extracts/condition_list";
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
    /*@ResponseBody
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
            List<ExtractUser> list = extUserServicel.list(new ExtractUser(project.getId()));
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
                            ExtractUser extUser = new ExtractUser();
                            extUser.setProjectId(projectId);
                            extUser.setId(id);
                            extUserServicel.update(extUser);
                        }
                    }
                }
                //抽取地址
                if (extractionSites != null && !"".equals(extractionSites)){
                    SupplierExtractProjectInfo supplierExtracts = new SupplierExtractProjectInfo();
                    supplierExtracts.setProjectId(projectId);
                    PageHelper.startPage(1, 1);
                    //查询抽取记录
                    List<SupplierExtractProjectInfo> listSe = expExtractRecordService.listExtractRecord(supplierExtracts,0);
                    //设置抽取地点
                    supplierExtracts.setExtractionSites(extractionSites);
                    User user = (User) sq.getSession().getAttribute("loginUser");
                    if(user != null ){
                    	//设置抽取人员
                        //supplierExtracts.setExtractsPeople(user.getId());
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
                	//通过项目分包Id 查询未完成抽取包id
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


    }*/

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
    public String addHeading(Model model, String[] id,String projectId,String supplierTypeCode){
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
        model.addAttribute("supplierTypeCode", supplierTypeCode);
        return "ses/sms/supplier_extracts/product";
    }

    /**
     * @Description:保存结果
     *
     * @author Wang Wenshuai
     * @date 2016年9月19日 下午2:31:46
     * @param @param model
     * @param @return
     * @return String
     */
    @ResponseBody
    @RequestMapping("/saveResult")
    public Object saveResult(Model model,SupplierExtractResult supplierExtRelate){
    	
    	//保存抽取记录  供应商id  记录id 条件id  结果id 是否参加 不参加理由 供应商类型代码
    	//supplierExtRelate.setId(UUIDUtils.getUUID32());
    	extRelateService.saveResult(supplierExtRelate);
		return null;
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
    public String resuleRecord(Model model,SupplierExtractProjectInfo se,String page){
        List<SupplierExtractProjectInfo> listExtractRecord = expExtractRecordService.listExtractRecord(se,page!=null&&!page.equals("")?Integer.parseInt(page):1);
        model.addAttribute("extractslist", new PageInfo<SupplierExtractProjectInfo>(listExtractRecord));
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
    /*@RequestMapping("/showRecord")
    public String showRecord(Model model, String id,String projectId,String packageId,String typeclassId){
        model.addAttribute("typeclassId", typeclassId);
        model.addAttribute("projectId", projectId);
        model.addAttribute("packageId", packageId);
        SupplierExtractProjectInfo showExpExtractRecord=null;
        if (projectId != null && projectId != null){
            //获取抽取记录
            SupplierExtractProjectInfo extracts = new SupplierExtractProjectInfo();
            extracts.setProjectId(projectId);
            List<SupplierExtractProjectInfo> listExtractRecord = expExtractRecordService.listExtractRecord(extracts,0);
            if(listExtractRecord != null && listExtractRecord.size() !=0){
                showExpExtractRecord = listExtractRecord.get(0);
                model.addAttribute("ExpExtractRecord", showExpExtractRecord);
                //获取监督人员
                List<ExtractUser>    listUser = extUserServicel.list(new ExtractUser(showExpExtractRecord.getProjectId()));
                model.addAttribute("listUser", listUser);
                //抽取条件
                List<Packages> conList = packagesService.listExpExtCondition(showExpExtractRecord.getProjectId());
                model.addAttribute("conditionList", conList);

                if (packageId != null && !"".equals(packageId)){
                    //已抽取
                    String[] packageIds =  packageId.split(",");
                    if(packageIds.length != 0 ){
                        SupplierExtractCondition con = null;
                        for (String pckId : packageIds) {
                            if(pckId != null && !"".equals(pckId)){
                                con = new SupplierExtractCondition();
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
            List<SupplierExtractProjectInfo> listExtractRecord = expExtractRecordService.listExtractRecord(new SupplierExtractProjectInfo(id),0);
            if(listExtractRecord != null && listExtractRecord.size() !=0){
                showExpExtractRecord = listExtractRecord.get(0);
                model.addAttribute("ExpExtractRecord", showExpExtractRecord);
            }
        }
        return "ses/sms/supplier_extracts/extract_supervise_word";
    }*/

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
    public List<Area> city(Model model, String area){

        return  areaService.getTree();
    }

    /**
     * @Description:显示监督人员
     *
     * @author Wang Wenshuai
     * @version 2016年9月25日 09:49:56
     * @return String
     */
    
    
   /* @RequestMapping("/showSupervise")
    public String showSupervise(Model model,String recordId){
        if (recordId != null && !"".equals(recordId)) {
            model.addAttribute("recordId", recordId);
            List<ExtractUser> list = extUserServicel.list(new ExtractUser(recordId));
            model.addAttribute("list", list);
        }
        model.addAttribute("type", "supplier");
        return "ses/sms/supplier_extracts/supervise_list";
    }*/

    @ResponseBody
    @RequestMapping("/isFinish")
    public String isFinish(String packageId){
        //获取查询条件类型
        SupplierExtractCondition condition = new SupplierExtractCondition();
        condition.setProjectId(packageId);
        condition.setStatus((short)1);
        String finish = conditionService.isFinish(condition);

        return JSON.toJSONString(finish);
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
     *
     *〈简述〉获取品目树
     *〈详细描述〉
     * @author Wang Wenshuai
     * @return
     */
    @ResponseBody
    @RequestMapping("/getTree")
    public String getTree(Category category,String projectId,String supplierTypeCode){
   	 //获取字典表中的根数据
    	List<CategoryTree> jList = 	categoryService.getTreeForExt(category,supplierTypeCode);
        return JSON.toJSONString(jList);

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
    public String supplierType(String projectId){
        List<DictionaryData> supplierTypeList = conditionService.supplierTypeList(projectId);
        return JSON.toJSONString(supplierTypeList);
    }
    
    
    /**
     *
     *〈简述〉供应商类型 根据项目类型获取需要抽取的供应商类型
     *〈详细描述〉
     * @author Wang Wenshuai
     * @return
     */
    @ResponseBody
    @RequestMapping("/supplieType2")
    public String supplierType2(String typeCode){
    	List<DictionaryData> supplierTypeList = conditionService.supplierType(typeCode);
    	return JSON.toJSONString(supplierTypeList);
    }
    
}
