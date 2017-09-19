
package extract.controller.supplier;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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
import ses.model.ems.ExtConType;
import ses.service.bms.AreaServiceI;
import ses.service.bms.CategoryService;
import ses.service.bms.DictionaryDataServiceI;
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
@RequestMapping("/SupplierExtracts")
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
    private SupplierExtractRecordService expExtractRecordService; //记录
    @Autowired
    private CategoryService categoryService; //品目
    @Autowired
    private AdvancedProjectService advancedProjectService;
    @Autowired
    private DictionaryDataServiceI dictionaryDataServiceI;
    
    @InitBinder
	public void initBinder(ServletRequestDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
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
    public String list(Integer page, Model model, Project project,@CurrentUser User user){
    	if(null!=user && "1".equals(user.getTypeName())){
    		List<SupplierExtractProjectInfo> extractRecords = expExtractRecordService.getList(page == null?1:page,user);
    		model.addAttribute("info", new PageInfo<SupplierExtractProjectInfo>(extractRecords));
    		return "ses/sms/supplier_extracts/project_list";
    	}
    	return "redirect:/qualifyError.jsp";
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
    		expExtractRecordService.insertProjectInfo(eRecord);
    		if(StringUtils.isNotBlank(eRecord.getProjectId())){
    			//说明是从项目实施进入 需要查询项目信息，生成一条记录，查询项目信息(包信息),条件id
    			 AdvancedProject selectById = advancedProjectService.selectById(eRecord.getProjectId());
    			 Project selectById2 = projectService.selectById(eRecord.getProjectId());
    			 if(null != selectById){
    				 eRecord.setProjectId(selectById.getId());
        			 eRecord.setProjectName(selectById.getName());
        			 eRecord.setProjectType(dictionaryDataServiceI.getDictionaryData(selectById.getPlanType()).getCode() );
        			 eRecord.setProjectCode(selectById.getProjectNumber());
    			 }else if(null!=selectById2){
    				 eRecord.setProjectId(selectById2.getId());
        			 eRecord.setProjectName(selectById2.getName());
        			 eRecord.setProjectType(dictionaryDataServiceI.getDictionaryData(selectById2.getPlanType()).getCode());
        			 eRecord.setProjectCode(selectById2.getProjectNumber());
    			 }
    			model.addAttribute("projectInfo", eRecord);
    			model.addAttribute("typeclassId", "hidden");
    		}else{
    			eRecord.setProjectId(WfUtil.createUUID());
    			model.addAttribute("projectInfo", eRecord);
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
    	model.addAttribute("address", areaService.findAreaByParentId(province.get(0).getId()));
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
    	
    	expExtractRecordService.saveOrUpdateProjectInfo(projectInfo,user);
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
    public ResponseEntity<byte[]> printRecord(String id,HttpServletRequest request, HttpServletResponse response){
    	ResponseEntity<byte[]> printRecord = null;
    	try {
			printRecord = expExtractRecordService.printRecord(id,request,response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return printRecord;
    }
    
    
    
    
}
