
package extract.controller.supplier;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.model.bms.Area;
import ses.model.bms.Category;
import ses.model.bms.CategoryTree;
import ses.model.bms.DictionaryData;
import ses.model.bms.User;
import ses.service.bms.AreaServiceI;
import ses.service.bms.CategoryService;
import ses.service.bms.DictionaryDataServiceI;
import ses.util.DictionaryDataUtil;
import ses.util.WfUtil;
import bss.controller.base.BaseController;
import bss.model.ppms.AdvancedProject;
import bss.model.ppms.Project;
import bss.service.ppms.AdvancedProjectService;
import bss.service.ppms.ProjectService;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import common.annotation.CurrentUser;

import extract.model.supplier.SupplierExtractCondition;
import extract.model.supplier.SupplierExtractProjectInfo;
import extract.model.supplier.SupplierExtractResult;
import extract.service.supplier.AutoExtractSupplierService;
import extract.service.supplier.SupplierExtractConditionService;
import extract.service.supplier.SupplierExtractRecordService;
import extract.service.supplier.SupplierExtractRelateResultService;

/**
 * @Description:供应商抽取记录
 *
 * @author Wang Wenshuai
 * @date 2016年9月18日下午3:29:12
 * @since  JDK 1.7
 */
@Controller
@Scope("prototype")
@RequestMapping("/SupplierExtracts_new")
public class ExtractSupplierController extends BaseController {
    /**
     * 项目
     */
    @Autowired
    private ProjectService projectService;

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
    private SupplierExtractRecordService recordService; //记录
    @Autowired
    private CategoryService categoryService; //品目
    @Autowired
    private AdvancedProjectService advancedProjectService;
    @Autowired
    private DictionaryDataServiceI dictionaryDataServiceI;
    
    @Autowired
    private AutoExtractSupplierService autoExtract;
    
    @InitBinder
	public void initBinder(ServletRequestDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
	}
    
    /**
     * @Description:获取抽取记录集合
     *
     * @author jia chengxiang
     * @version 2016年9月27日 下午4:38:31
     * @param  page
     * @param  model
     * @param  project
     * @return String
     */
    @RequestMapping("/projectList")
    public String list(Integer page, Model model, SupplierExtractProjectInfo project,@CurrentUser User user,String startTime,String endTime){
    	if(null!=user && ("1".equals(user.getTypeName()) || "4".equals(user.getTypeName())) ){
    		Map<String, Object> map = new HashMap<>();
    		map.put("page", page);
    		map.put("startTime", null == startTime ? "" : startTime.trim());
    		map.put("endTime", null == endTime ? "" : endTime.trim());
    		
    		//采购方式
            List<DictionaryData> purchaseWayList = new ArrayList<>();
            purchaseWayList.add(DictionaryDataUtil.get("GKZB"));
            purchaseWayList.add(DictionaryDataUtil.get("YQZB"));
            purchaseWayList.add(DictionaryDataUtil.get("JZXTP"));
        	DictionaryData xj = DictionaryDataUtil.get("XJCG");
        	xj.setName("询价");
        	purchaseWayList.add(xj);
        	purchaseWayList.add(DictionaryDataUtil.get("DYLY"));
            model.addAttribute("purchaseTypeList",purchaseWayList);
            model.addAttribute("startTime",startTime);
            model.addAttribute("endTime",endTime);
            model.addAttribute("project",project);
            List<SupplierExtractProjectInfo> extractRecords = recordService.getList(page == null?1:page,user,project);
            model.addAttribute("info", new PageInfo<SupplierExtractProjectInfo>(extractRecords));
    		return "ses/sms/supplier_extracts/project_list";
    	}
    	return "redirect:/qualifyError.jsp";
    }
    
    /**
     *@Description:条件查询集合 / 跳转抽取条件页面，准备抽取
     *
     * @author jia chengxiang
     * @version 2016年9月27日 下午6:03:40
     * @param  id 包id
     * @return String
     */
    @RequestMapping("/Extraction")
   // public String listExtraction(@CurrentUser User user, Model model, String projectId, String page, String typeclassId, String packageId){
   public String listExtraction(@CurrentUser User user,Model model, SupplierExtractProjectInfo eRecord,String conditionId){
    	//两个入口 1.项目实施进入 2.直接进行抽取
    	
    	
    	if(!(null!=user && "1".equals(user.getTypeName()))){
    		return "redirect:/qualifyError.jsp";
    	}
    	if(StringUtils.isEmpty(conditionId)){
    		conditionId = WfUtil.createUUID();
    		//生成一条查询条件
    		SupplierExtractCondition supplierCondition = new SupplierExtractCondition();
    		supplierCondition.setId(conditionId);
    		conditionService.insert(supplierCondition);
    		//将conditionId 插入记录表
    		eRecord.setConditionId(conditionId);
    	}else{
    		//TUTO  抽取条件回显
			model.addAttribute("condition",conditionService.show(conditionId));
    	}
    	
    	//记录为空
    	if(StringUtils.isBlank(eRecord.getId())){
    		String recordId = WfUtil.createUUID();
    		eRecord.setId(recordId);
    		eRecord.setProcurementDepId(user.getOrg().getId());
    		eRecord.setExtractUser(user.getId());
    		recordService.insertProjectInfo(eRecord);
    		
    		if("advPro".equals(eRecord.getProjectInto())){
    			//预研进入
    			 AdvancedProject selectById = advancedProjectService.selectById(eRecord.getProjectId());
    			 DictionaryData purchaseType = dictionaryDataServiceI.getDictionaryData(selectById.getPurchaseType());
    			 eRecord.setProjectId(selectById.getId());
    			 eRecord.setProjectName(selectById.getName());
    			 eRecord.setProjectType(DictionaryDataUtil.findById(selectById.getPlanType()).getCode());
    			 eRecord.setProjectTypeName(dictionaryDataServiceI.getDictionaryData(selectById.getPlanType()).getName());
    			 eRecord.setPurchaseType(null !=purchaseType?purchaseType.getId():"");
    			 eRecord.setPurchaseTypeName(null !=purchaseType?purchaseType.getName():"");
    			 eRecord.setProjectCode(selectById.getProjectNumber());
        	}else if("relPro".equals(eRecord.getProjectInto())){
        		//真实项目
        		Project selectById2 = projectService.selectById(eRecord.getProjectId());
        		DictionaryData purchaseType = dictionaryDataServiceI.getDictionaryData(selectById2.getPurchaseType());
        		eRecord.setProjectId(selectById2.getId());
   			 	eRecord.setProjectName(selectById2.getName());
   			 	eRecord.setProjectType(DictionaryDataUtil.findById(selectById2.getPlanType()).getCode());
   			 	eRecord.setProjectTypeName(dictionaryDataServiceI.getDictionaryData(selectById2.getPlanType()).getName());
   			 	eRecord.setPurchaseType(null !=purchaseType?purchaseType.getId():"");
   			 	eRecord.setPurchaseTypeName(null !=purchaseType?purchaseType.getName():"");
   			 	eRecord.setProjectCode(selectById2.getProjectNumber());
        	}else{
        		//随机抽取
        		
        		
        	}
    		model.addAttribute("projectInfo", eRecord);
			model.addAttribute("typeclassId", "hidden");
    		
    		
    	}else if(StringUtils.isNotBlank(eRecord.getId())){
    		
    		eRecord=recordService.selectByPrimaryKey(eRecord.getId());
    		if(StringUtils.isEmpty(eRecord.getConditionId())){
    			eRecord.setConditionId(conditionId);
    			recordService.update(eRecord);
    		}
    		//从记录列表进入 继续抽取 项目信息
    		model.addAttribute("projectInfo",eRecord);
    		//model.addAttribute("supervises",extUserServicel.getName(eRecord.getId()));
    		//重新抽取  只携带项目信息
    	}
    	List<Area> province = areaService.findRootArea();
    	model.addAttribute("businessNature", conditionService.getBusinessNature());
    	model.addAttribute("province", province);
    	//加载采购方式
    	List<DictionaryData> purchaseTypeList = new ArrayList<>();
    	purchaseTypeList.add(DictionaryDataUtil.get("GKZB"));
    	purchaseTypeList.add(DictionaryDataUtil.get("YQZB"));
    	purchaseTypeList.add(DictionaryDataUtil.get("JZXTP"));
    	DictionaryData xj = DictionaryDataUtil.get("XJCG");
    	xj.setName("询价");
    	purchaseTypeList.add(xj);
    	purchaseTypeList.add(DictionaryDataUtil.get("DYLY"));
    	model.addAttribute("purchaseTypeList", purchaseTypeList);
    	//model.addAttribute("address", areaService.findAreaByParentId(province.get(0).getId()));
    	return "ses/sms/supplier_extracts/condition_list";
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
    public String addHeading(Model model, String categoryId,String projectId,String supplierTypeCode){
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("supplierTypeCode", supplierTypeCode);
        return "ses/sms/supplier_extracts/product";
    }

    /**
     * @Description:保存结果
     *
     * @author jia chegnxiang
     * @date 2016年9月19日 下午2:31:46
     * @param @param model
     * @param @return
     * @return String
     */
    @ResponseBody
    @RequestMapping("/saveResult")
    public String saveResult(Model model,SupplierExtractResult supplierExtRelate,String projectType){
    	
    	//保存抽取记录  供应商id  记录id 条件id  结果id 是否参加 不参加理由 供应商类型代码
    	//supplierExtRelate.setId(UUIDUtils.getUUID32());
    	int saveResult = extRelateService.saveResult(supplierExtRelate,projectType);
    	return JSON.toJSONString(saveResult);
    }


    /**
     * @Description: 获取市
     *
     * @author Jia chegnxiang
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
     *
     *〈简述〉获取品目树
     *〈详细描述〉
     * @author Jia chengxiang
     * @return
     */
    @ResponseBody
    @RequestMapping("/getTree")
    public String getTree(Category category,String projectId,String supplierTypeCode,String categoryId){
   	 //获取字典表中的根数据
    	List<CategoryTree> jList = 	categoryService.getTreeForExt(category,supplierTypeCode,categoryId);
        return JSON.toJSONString(jList);

    }

    

    /**
     *
     *〈简述〉供应商类型
     *〈详细描述〉
     * @author jia chengxiang
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
     * @author jia chengxiang
     * @return
     */
    @ResponseBody
    @RequestMapping("/supplieType2")
    public String supplierType2(String typeCode){
    	List<DictionaryData> supplierTypeList = conditionService.supplierType(typeCode);
    	return JSON.toJSONString(supplierTypeList);
    }
    
    
    
    /**
     *
     *〈简述〉存储供应商抽取 项目信息
     *〈详细描述〉
     * @author jcx
     * @return
     */
    @ResponseBody
    @RequestMapping("/saveProjectInfo" )
    public String saveProjectInfo( @Valid  SupplierExtractProjectInfo projectInfo,BindingResult result,@CurrentUser User user){
    	
    	if(result.hasErrors()){
    		List<FieldError> fieldErrors = result.getFieldErrors();
    		HashMap<String, String> errMsg = new HashMap<>();
    		for (FieldError fieldError : fieldErrors) {
    			errMsg.put(fieldError.getField(), fieldError.getDefaultMessage());
			}
    		return JSON.toJSONString(errMsg);
    	}
    	projectInfo.setProcurementDepId(user.getOrg().getId());//存储采购机构
    	recordService.saveOrUpdateProjectInfo(projectInfo);
    	return JSON.toJSONString(null);
    }
    
    
    /**
     *
     *〈简述〉下载记录表
     *〈详细描述〉
     * @author jcx
     * @return
     */
    @RequestMapping("/printRecord")																						
    public ResponseEntity<byte[]> printRecord(String id,HttpServletRequest request, HttpServletResponse response,String projectInto){
    	ResponseEntity<byte[]> printRecord = null;
    	try {
			printRecord = recordService.printRecord(id,request,response,projectInto);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return printRecord;
    }
    
    /**
     *
     *〈简述〉校验项目编号唯一
     *〈详细描述〉
     * @author jcx
     * @return
     */
    @RequestMapping("/checkSole")
    @ResponseBody
    public int checkSole(String projectCode){
    	List<SupplierExtractProjectInfo> extractRecords = recordService.checkSoleProjectCdoe(projectCode);
    	return extractRecords.size();
    }
    
    /**
     * 
     * <简述> 自动抽取返回通知结果
     *
     * @author Jia Chengxiang
     * @dateTime 2017-10-13下午3:44:17
     * @return
     */
    @RequestMapping(value="/supplierExtractResult",produces="application/json; charset=utf-8")
    @ResponseBody
    public String receiveVoiceResult(String json) {
    	String result = "";
    	if(StringUtils.isNotBlank(json)){
    		result = autoExtract.receiveVoiceResult(json);
    		result = StringUtils.isNotBlank(result)?result:"service error";
    	}else{
    		result = "receive success,but json is null";
    	}
    	return JSON.toJSONString(result);
 	}
    
    /**
     * 
     * <简述> 修改项目抽取状态
     *
     * @author Jia Chengxiang
     * @dateTime 2017-10-13下午3:44:17
     * @return
     */
    @RequestMapping(value="/updateExtractStatus",produces="application/json; charset=utf-8")
    @ResponseBody
    public String updateExtractStatus(SupplierExtractProjectInfo projectInfo) {
    	int saveOrUpdateProjectInfo = recordService.saveOrUpdateProjectInfo(projectInfo);
    	return JSON.toJSONString(saveOrUpdateProjectInfo);
    }
    
}
