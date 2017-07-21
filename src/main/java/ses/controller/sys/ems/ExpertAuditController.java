package ses.controller.sys.ems;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.dao.ems.ExpertField;
import ses.model.bms.Area;
import ses.model.bms.Category;
import ses.model.bms.CategoryTree;
import ses.model.bms.DictionaryData;
import ses.model.bms.Todos;
import ses.model.bms.User;
import ses.model.ems.Expert;
import ses.model.ems.ExpertAudit;
import ses.model.ems.ExpertAuditFileModify;
import ses.model.ems.ExpertAuditNot;
import ses.model.ems.ExpertAuditOpinion;
import ses.model.ems.ExpertCategory;
import ses.model.ems.ExpertEngHistory;
import ses.model.ems.ExpertHistory;
import ses.model.ems.ExpertSignature;
import ses.model.ems.ExpertTitle;
import ses.model.oms.Orgnization;
import ses.model.oms.PurchaseDep;
import ses.model.sms.SupplierCateTree;
import ses.service.bms.AreaServiceI;
import ses.service.bms.CategoryService;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.bms.EngCategoryService;
import ses.service.bms.TodosService;
import ses.service.ems.ExpertAuditNotService;
import ses.service.ems.ExpertAuditOpinionService;
import ses.service.ems.ExpertAuditService;
import ses.service.ems.ExpertCategoryService;
import ses.service.ems.ExpertEngHistorySerivce;
import ses.service.ems.ExpertEngModifySerivce;
import ses.service.ems.ExpertService;
import ses.service.ems.ExpertSignatureService;
import ses.service.ems.ExpertTitleService;
import ses.service.ems.ProjectExtractService;
import ses.service.oms.PurchaseOrgnizationServiceI;
import ses.util.DictionaryDataUtil;
import ses.util.PropUtil;
import ses.util.PropertiesUtil;
import ses.util.WordUtil;
import bss.formbean.PurchaseRequiredFormBean;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;

import common.annotation.CurrentUser;
import common.constant.Constant;
import common.constant.StaticVariables;

/**
 * <p>Title:ExpertAuditController </p>
 * <p>Description: 专家审核</p>
 * @author XuQing
 * @date 2016-12-19下午7:33:27
 */
@Controller
@RequestMapping("/expertAudit")
public class ExpertAuditController{

	@Autowired
	private ExpertService expertService;

	@Autowired
	private DictionaryDataServiceI dictionaryDataServiceI;

	@Autowired
	private ExpertAuditService expertAuditService;

	@Autowired
	private ExpertCategoryService expertCategoryService; // 专家类别中间表

	@Autowired
	private CategoryService categoryService; //是否被抽取查询

	@Autowired
	private PurchaseOrgnizationServiceI purchaseOrgnizationService; // 采购机构管理

	@Autowired
	private ExpertService service; // 专家管理

	@Autowired
	private TodosService todosService; //待办

	@Autowired
	private ProjectExtractService projectExtractService;
	
	@Autowired
	private EngCategoryService engCategoryService; //工程专业信息

	@Autowired 
	private ExpertEngHistorySerivce expertEngHistorySerivce;
	
	@Autowired 
	private ExpertEngModifySerivce expertEngModifySerivce;
	/**
	 * 地区
	 */
	@Autowired
	private AreaServiceI areaService;
	
	@Autowired
	private ExpertAuditOpinionService expertAuditOpinionService;

	@Autowired
	private ExpertSignatureService expertSignatureService;
	
	@Autowired
	private ExpertTitleService expertTitleService;
	
	@Autowired
	private ExpertAuditNotService expertAuditNotService;
	/**
	 * @Title: expertAuditList
	 * @author XuQing 
	 * @date 2016-12-19 下午7:31:56  
	 * @Description:审核列表 
	 * @param @param expert
	 * @param @param model
	 * @param @param pageNum
	 * @param @param request
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/list")
	public String expertAuditList(@CurrentUser User user, Expert expert, Model model, Integer pageNum, HttpServletRequest request) {
		if(pageNum == null) {
			pageNum = StaticVariables.DEFAULT_PAGE;
		}
		
		if(expert.getSign() == null) {
			Integer signs = (Integer) request.getSession().getAttribute("signs");
			expert.setSign(signs);
			request.getSession().removeAttribute("signs");
		}
		//获取登录人的机构id
		/*User user = (User) request.getSession().getAttribute("loginUser");*/
		Orgnization org = user.getOrg();
		
		//1代表机构
		if(user !=null && org !=null && (expert.getSign() == 1 || expert.getSign() == 3) && "1".equals(user.getTypeName())){
			/*String orgId = user.getOrg().getId();*/
			PurchaseDep dep = purchaseOrgnizationService.selectByOrgId(org.getId());
			if(dep !=null){
				expert.setPurchaseDepId(dep.getId());
				//抽取时的机构
				expert.setExtractOrgid(dep.getId());
				//1是采购机构，0不是
			}else{
				expert.setIsOrg(0);
				expert.setPurchaseDepId("");
				expert.setExtractOrgid("");
			}
		}else if(user !=null && expert.getSign() == 2 && "4".equals(user.getTypeName())){
			expert.setIsOrg(1);
		}else{
			expert.setIsOrg(0);
			expert.setPurchaseDepId("");
			expert.setExtractOrgid("");
		}
		

		//查询列表
		List < Expert > expertList = expertService.findExpertAuditList(expert, pageNum);
		PageInfo< Expert > result = new PageInfo < Expert > (expertList);
		
		model.addAttribute("result",result);
		model.addAttribute("expertList", expertList);
		if(expert.getSign() == 2 ){
		    if(!"4".equals(user.getTypeName())){
		        //判断是否 是资源服务中心 
		    	model.addAttribute("result", new PageInfo < Expert > ());
				model.addAttribute("expertList", new ArrayList<Expert>());
		     }

		}
		if(expert.getSign() == 1 || expert.getSign() == 3 ){
			if(!"1".equals(user.getTypeName())){
		        //判断是否 是采购机构 
		    	model.addAttribute("result", new PageInfo < Expert > ());
				model.addAttribute("expertList", new ArrayList<Expert>());
		     }
		}
		// 筛选,只有指定机构的人可以看到
		/*List<Expert> expertList = new ArrayList<Expert>();

		if (dep != null && dep.getId() != null) {
		    for (Expert exp : expList) {
		        if (exp.getPurchaseDepId() != null && exp.getPurchaseDepId().equals(dep.getId())) {
		            expertList.add(exp);
		        }
		    }
		}
		
		if (expert.getSign() == 2) {
			List<Expert> list = new ArrayList<Expert>();
			for (Expert e : expertList) {
				List<ProjectExtract>  projectExtractList= projectExtractService.findExtractByExpertId(e.getId());
				if (!projectExtractList.isEmpty()) {
					list.add(e);
				}
			}
			model.addAttribute("result", new PageInfo<Expert>(list));
			model.addAttribute("expertList", list);
		} else {
			model.addAttribute("result", new PageInfo<Expert>(expertList));
			model.addAttribute("expertList", expertList);
		}
		
		for (Expert exp : expertList) {
            StringBuffer expertType = new StringBuffer();
            if (exp.getExpertsTypeId() != null) {
                for (String typeId : exp.getExpertsTypeId().split(",")) {
                    DictionaryData data = dictionaryDataServiceI.getDictionaryData(typeId);
                    if (6 == data.getKind()) {
                        expertType.append(data.getName() + "技术、");
                    } else {
                        expertType.append(data.getName() + "、");
                    }
                }
                String expertsType = expertType.toString().substring(0, expertType.length() - 1);
                exp.setExpertsTypeId(expertsType);
            } else {
                exp.setExpertsTypeId("");
            }
        }*/

		//初审复审标识（1初审，2复审，3复查）
		model.addAttribute("sign", expert.getSign());
		request.getSession().setAttribute("signs", expert.getSign());

		//条件查询回显
		String relName = expert.getRelName();
		if(relName != null) {
			relName = relName.replaceAll("%", "");
		}
		String status = expert.getStatus();
		model.addAttribute("relName", relName);
		model.addAttribute("state", status);
		model.addAttribute("auditAt", expert.getAuditAt());

		return "ses/ems/expertAudit/list";
	}

	/**
	 * @Title: basicInfo
	 * @author XuQing 
	 * @date 2016-12-19 下午7:35:06  
	 * @Description:基本信息
	 * @param @param expert
	 * @param @param model
	 * @param @param pageNum
	 * @param @param expertId
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/basicInfo")
	public String basicInfo(Expert expert, Model model, Integer pageNum, String expertId, Integer sign) {
		/**
		 * 退回修改后对比历史记录
		 */
		ExpertEngHistory expertEngHistory = new ExpertEngHistory ();
		expertEngHistory.setExpertId(expertId);
		//先删除
		expertEngModifySerivce.deleteByExpertId(expertEngHistory);
		//插入对比后的
		expertEngModifySerivce.insertSelective(expertEngHistory);
		
		expert = expertService.selectByPrimaryKey(expertId);
		model.addAttribute("expert", expert);
		
		//初审复审标识（1初审，2复审，3复查）
		model.addAttribute("sign", sign);
		
		//专家来源
		if(expert.getExpertsFrom() != null) {
			DictionaryData expertsFrom = dictionaryDataServiceI.getDictionaryData(expert.getExpertsFrom());
			model.addAttribute("expertsFrom", expertsFrom.getName());
			// 专家来源
			model.addAttribute("froms", expertsFrom.getCode());
		}
		//性别
		if(expert.getGender() != null) {
			DictionaryData gender = dictionaryDataServiceI.getDictionaryData(expert.getGender());
			model.addAttribute("gender", gender.getName());
		}
		//政治面貌
		if(expert.getPoliticsStatus() != null) {
			DictionaryData politicsStatus = dictionaryDataServiceI.getDictionaryData(expert.getPoliticsStatus());
			model.addAttribute("politicsStatus", politicsStatus.getName());
		}
		//军队人员身份证件类型
		if(expert.getIdType() != null) {
			DictionaryData idType = dictionaryDataServiceI.getDictionaryData(expert.getIdType());
			model.addAttribute("idType", idType.getName());
		}
		//最高学历
		if(expert.getHightEducation() != null) {
			DictionaryData hightEducation = dictionaryDataServiceI.getDictionaryData(expert.getHightEducation());
			model.addAttribute("hightEducation", hightEducation.getName());
		}
		//最高学位
		if(expert.getDegree() != null) {
			DictionaryData degree = dictionaryDataServiceI.getDictionaryData(expert.getDegree());
			if(degree != null){
				model.addAttribute("degree", degree.getName());
			}
		}
		// 货物类型数据字典
		List < DictionaryData > hwList = DictionaryDataUtil.find(8);
		model.addAttribute("hwList", hwList);

		// 经济类型数据字典
		List < DictionaryData > jjTypeList = DictionaryDataUtil.find(19);
		model.addAttribute("jjList", jjTypeList);

		// 产品类型数据字典
		List < DictionaryData > spList = DictionaryDataUtil.find(6);
		model.addAttribute("spList", spList);

		//地区查询
		List < Area > privnce = areaService.findRootArea();
		model.addAttribute("privnce", privnce);
		
		if(expert.getAddress() !=null){
			Area area = areaService.listById(expert.getAddress());
			String sonName = area.getName();
			model.addAttribute("sonName", sonName);
			for(int i = 0; i < privnce.size(); i++) {
				if(area.getParentId().equals(privnce.get(i).getId())) {
					String parentName = privnce.get(i).getName();
					model.addAttribute("parentName", parentName);
				}
			}
		}

		// 专家系统key
		Integer expertKey = Constant.EXPERT_SYS_KEY;
		Map < String, Object > typeMap = getTypeId();
		// typrId集合
		model.addAttribute("typeMap", typeMap);
		// 业务id就是专家id
		model.addAttribute("sysId", expertId);
		// Constant.EXPERT_SYS_VALUE;
		model.addAttribute("expertKey", expertKey);

		model.addAttribute("expertId", expertId);
		//  判断当前状态如果为退回修改则比较两次的信息
		// 判断有没有进行修改
		if(expert.getStatus() != null || expert.getStatus().equals("0")) {
			ExpertHistory oldExpert = service.selectOldExpertById(expertId);
			if(oldExpert != null) {
				Map < String, Object > compareMap = compareExpert(oldExpert, (ExpertHistory) expert);
				// 如果isEdit==1代表没有进行任何修改就进行了二次提交
				
				expertEngHistory = new ExpertEngHistory();
				expertEngHistory.setExpertId(expertId);
				List<ExpertEngHistory> list = expertEngModifySerivce.selectByExpertId(expertEngHistory);
				if(compareMap.isEmpty() && list.isEmpty()) {
					// 没有修改
					model.addAttribute("isEdit", "0");
				} else {
					// 有修改
					model.addAttribute("isEdit", "1");
				}
				Set < String > keySet = compareMap.keySet();
				List < String > editFields = new ArrayList < String > ();
				for(String method: keySet) {
					editFields.add(method);
				}
				model.addAttribute("editFields", editFields);
			}
		}
		
		
		if( expert.getStatus().equals("0") ||  expert.getStatus().equals("1") ||  expert.getStatus().equals("6")){
			/**
			 * 回显未通过的字段
			 */
			ExpertAudit expertAudit = new ExpertAudit();
			expertAudit.setExpertId(expertId);
			expertAudit.setSuggestType("one");
			List < ExpertAudit > reasonsList = expertAuditService.getListByExpert(expertAudit);
			StringBuffer conditionStr = new StringBuffer();
			if(!reasonsList.isEmpty()){
				for (ExpertAudit expertAudit2 : reasonsList) {
					conditionStr.append(expertAudit2.getAuditField() + ",");
				}
			}
			model.addAttribute("conditionStr", conditionStr);
		}
		
		if( expert.getStatus().equals("0")){
			/**
			 * 附件退回修改
			 */
			ExpertAuditFileModify expertAuditFileModify = new ExpertAuditFileModify();
			expertAuditFileModify.setExpertId(expertId);
			List<ExpertAuditFileModify> selectFileModifyByExpertId = expertAuditService.selectFileModifyByExpertId(expertAuditFileModify);
			StringBuffer fileModify = new StringBuffer();
			if(!selectFileModifyByExpertId.isEmpty()){
				for(ExpertAuditFileModify m : selectFileModifyByExpertId){
					fileModify.append(m.getTypeId() + ",");
				}
				model.addAttribute("fileModify", fileModify);
			}
		}

		return "ses/ems/expertAudit/basic_info";
	}

	/**
	 *〈简述〉
	 * 根据字段名获取备份的信息
	 *〈详细描述〉
	 * @author WangHuijie
	 * @param field
	 * @param type
	 * @return
	 * @throws ParseException 
	 */
	@ResponseBody
	@RequestMapping(value = "/getFieldContent", produces = "text/html;charset=UTF-8")
	public String getFieldContent(String field, String type, String expertId) throws ParseException {
		StringBuffer content = new StringBuffer();
		Expert expert = service.selectByPrimaryKey(expertId);
		ExpertHistory oldExpert = service.selectOldExpertById(expertId);
		if(oldExpert.getTimeToWork() !=null){
			oldExpert.setTimeToWork(new SimpleDateFormat("yyyy-MM").parse(new SimpleDateFormat("yyyy-MM").format(oldExpert.getTimeToWork())));
		}
		Map < String, Object > compareMap = compareExpert(oldExpert, (ExpertHistory) expert);
		String value = (String) compareMap.get(field);
		if("0".equals(type)) {
			// 不需要数据字典查询的
			content.append(value);
		} else if("1".equals(type)) {
			// 需要从数据字典查询的
			if(field.indexOf(",") == -1) {
				// 不需要拼接逗号的
				DictionaryData dictionaryData = dictionaryDataServiceI.getDictionaryData(value);
				if(dictionaryData != null) {
					content.append(dictionaryData.getName());
				}
			} else {
				// 需要逗号拼接的
				StringBuffer temp = new StringBuffer();
				String[] ids = value.split(",");
				for(String id: ids) {
					DictionaryData dictionaryData = dictionaryDataServiceI.getDictionaryData(id);
					if(dictionaryData != null) {
						temp.append(dictionaryData.getName() + "、");
					}
				}
				content.append(temp.toString().substring(0, temp.length() - 1));
			}
			
			/**
			 * 地区查询
			 */
			if("getAddress".equals(field)){
				String sonName = null;
				String parentName = null;
				List < Area > privnce = areaService.findRootArea();
				if(expert.getAddress() !=null){
					Area area = areaService.listById(value);
					sonName = area.getName();
					for(int i = 0; i < privnce.size(); i++) {
						if(area.getParentId().equals(privnce.get(i).getId())) {
							parentName = privnce.get(i).getName();
							break;
						}
					}
				}
				content.append(parentName + sonName);
			}
			/**
			 * 是否缴纳社会保险
			 */
			if("getCoverNote".equals(field)){
				if(value.equals("1")){
					content.append("是");
				}else{
					content.append("否");
				}
			}
		} else if("2".equals(type)) {
			SimpleDateFormat sdf1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
			Date date = sdf1.parse(value);
			content.append(new SimpleDateFormat("yyyy-MM-dd").format(date));
		} else if("3".equals(type)) {
			// Wed Feb 01 00:00:00 CST 2017         String
			SimpleDateFormat sdf1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
			Date date = sdf1.parse(value);
			content.append(new SimpleDateFormat("yyyy-MM").format(date));
		}
		
		//相关机关事业部门推荐信
		if("getIsReferenceLftter".equals(field)){
			if(oldExpert.getIsReferenceLftter() == 1){
				content.replace(0, 1, "是");
			}else{
				content.replace(0, 1, "否");
			}
			
		}
		return content.toString();
	}

	/**
	 *〈简述〉
	 * 判断改变了哪些字段以及本来的内容
	 *〈详细描述〉
	 * @author WangHuijie
	 * @param oldExpert
	 * @param newExpert
	 * @return
	 */
	private Map < String, Object > compareExpert(ExpertHistory oldExpert, ExpertHistory newExpert) {
		Map < String, Object > map = new HashMap < String, Object > ();
		try {
			Class < ExpertHistory > expertClass = ExpertHistory.class;
			Field[] fieldList = expertClass.getDeclaredFields();
			for(Field field: fieldList) {
				PropertyDescriptor pd = new PropertyDescriptor(field.getName(), expertClass);
				Method getMethod = pd.getReadMethod();
				Object o1 = getMethod.invoke(oldExpert);
				Object o2 = getMethod.invoke(newExpert);
				if(o1 != null && o2 != null && !o1.toString().equals(o2.toString())) {
					map.put(getMethod.getName(), o1.toString());
				}
				if((o1 == null && o2 != null) || o1 != null && o2 == null) {
					map.put(getMethod.getName(), o1.toString());
				}
			}
		} catch(Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * @Title: auditReasons
	 * @author XuQing 
	 * @date 2016-12-19 下午7:35:55  
	 * @Description:记录审核
	 * @param @param expertAudit
	 * @param @param model
	 * @param @param response      
	 * @return void
	 */
	@RequestMapping("/auditReasons")
	public void auditReasons(ExpertAudit expertAudit, Model model, HttpServletResponse response, HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("loginUser");
		if(user != null) {
			expertAudit.setAuditUserId(user.getId());
			expertAudit.setAuditUserName(user.getRelName());
			
			//记录审核人
			Expert expert = new Expert();
			expert.setId(expertAudit.getExpertId());
			expert.setAuditor(user.getRelName());
			expertService.updateByPrimaryKeySelective(expert);
		}
		expertAudit.setAuditAt(new Date());

		//唯一验证
		boolean same = true;
		/*List < ExpertAudit > reasonsList = expertAuditService.getListByExpertId(expertAudit.getExpertId());
		for(int i = 0; i < reasonsList.size(); i++) {
			if(reasonsList.get(i).getAuditField().equals(expertAudit.getAuditField()) && reasonsList.get(i).getAuditContent().equals(expertAudit.getAuditContent()) && reasonsList.get(i).getSuggestType().equals(expertAudit.getSuggestType())) {
				same = false;
				break;
			}
			if(reasonsList.get(i) !=null && reasonsList.get(i).getAuditFieldId().equals(expertAudit.getAuditFieldId())){
				same = false;
				break;
			}
		}*/
		Integer num = expertAuditService.findByObj(expertAudit);
		if(num !=0 ){
			same = false;
		}
		
		if(same) {
			expertAuditService.add(expertAudit);
		} else {
			String msg = "{\"msg\":\"fail\"}";
			writeJson(response, msg);
		}

	}

	/**
	 * @Title: experience
	 * @author XuQing 
	 * @date 2016-12-19 下午7:36:21  
	 * @Description:经历经验
	 * @param @param expert
	 * @param @param model
	 * @param @param expertId
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/experience")
	public String experience(Expert expert, Model model, String expertId) {

		expert = expertService.selectByPrimaryKey(expertId);
		model.addAttribute("expert", expert);
		model.addAttribute("expertId", expertId);

		// 判断有没有进行修改
		if(expert.getStatus() != null && expert.getStatus().equals("0")) {
			ExpertHistory oldExpert = service.selectOldExpertById(expertId);
			Map < String, Object > compareMap = compareExpert(oldExpert, (ExpertHistory) expert);
			// 如果isEdit==1代表没有进行任何修改就进行了二次提交
			if(compareMap.isEmpty()) {
				// 没有修改
				model.addAttribute("isEdit", "0");
			} else {
				// 有修改
				model.addAttribute("isEdit", "1");
			}
			Set < String > keySet = compareMap.keySet();
			List < String > editFields = new ArrayList < String > ();
			for(String method: keySet) {
				editFields.add(method);
			}
			model.addAttribute("editFields", editFields);
		}

		return "ses/ems/expertAudit/experience";
	}

	/**
	 * @Title: product
	 * @author XuQing 
	 * @date 2016-12-19 下午7:36:47  
	 * @Description:产品目录
	 * @param @param expert
	 * @param @param model
	 * @param @param expertId
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/product")
	public String product(Expert expert, Model model, String expertId, Integer sign) {
		//初审复审标识（1初审，3复查，2复审）
		model.addAttribute("sign", sign);
		
		expert = expertService.selectByPrimaryKey(expertId);

		List < DictionaryData > allCategoryList = new ArrayList < DictionaryData > ();

        // 获取专家类别
        List < String > allTypeId = new ArrayList < String > ();
        if(expert.getExpertsTypeId() !=null && !"".equals(expert.getExpertsTypeId())){
        	for(String id: expert.getExpertsTypeId().split(",")) {
                allTypeId.add(id);
            }
        }
        
        a: for(int i = 0; i < allTypeId.size(); i++) {
            DictionaryData dictionaryData = dictionaryDataServiceI.getDictionaryData(allTypeId.get(i));
            /*if(dictionaryData != null && dictionaryData.getKind() == 19) {
				allTypeId.remove(i);
				continue a;
			};*/
            
            allCategoryList.add(dictionaryData);
        }
        //expertCategoryService.delNoTree(expert.getId(), allCategoryList);
        model.addAttribute("allCategoryList", allCategoryList);

		model.addAttribute("expertId", expertId);
		
		//查询品目类型id
		String matCodeId=DictionaryDataUtil.getId("GOODS");
		String engCodeId=DictionaryDataUtil.getId("PROJECT");
		String serCodeId=DictionaryDataUtil.getId("SERVICE");
		String engInfoId=DictionaryDataUtil.getId("ENG_INFO_ID");
		
		String goodsServerId=DictionaryDataUtil.getId("GOODS_SERVER");
		String goodsProjectId=DictionaryDataUtil.getId("GOODS_PROJECT");
		
		model.addAttribute("matCodeId", matCodeId);
		model.addAttribute("engCodeId", engCodeId);
		model.addAttribute("serCodeId", serCodeId);
		model.addAttribute("engInfoId", engInfoId);
		
		model.addAttribute("goodsServerId", goodsServerId);
		model.addAttribute("goodsProjectId", goodsProjectId);
		
		return "ses/ems/expertAudit/product";
	}

	

	/**
	 *〈简述〉
	 * 获取所有已选中的节点
	 *〈详细描述〉
	 * @author WangHuijie
	 * @param expertId
	 * @param typeId
	 * @param model
	 * @return
	 */
	@RequestMapping("/getCategories")
	public String getCategories(String expertId, String typeId, Model model, Integer pageNum) {
		String code = DictionaryDataUtil.findById(typeId).getCode();
        String flag = null;
        if (code != null && code.equals("GOODS_PROJECT")) {
            code = "PROJECT";
            typeId = DictionaryDataUtil.getId("PROJECT");
        }
        if (code.equals("ENG_INFO_ID")) {
            flag = "ENG_INFO";
        }
        // 查询已选中的节点信息(所有子节点)
        List<ExpertCategory> items = expertCategoryService.getListByExpertId(expertId, typeId, pageNum == null ? 1 : pageNum);
        List<ExpertCategory> expertItems = new ArrayList<ExpertCategory>();
        int count=0;
        for (ExpertCategory expertCategory : items) {
        	count++;
            if (!DictionaryDataUtil.findById(expertCategory.getTypeId()).getCode().equals("ENG_INFO_ID")) {
                Category data = categoryService.findById(expertCategory.getCategoryId());
                List<Category> findPublishTree = categoryService.findPublishTree(expertCategory.getCategoryId(), null);
                if (findPublishTree.size() == 0) {
                    expertItems.add(expertCategory);
                } else if (data != null && data.getCode().length() == 7) {
                    expertItems.add(expertCategory);
                }
            } else {
                Category data = engCategoryService.findById(expertCategory.getCategoryId());
                List<Category> findPublishTree = engCategoryService.findPublishTree(expertCategory.getCategoryId(), null);
                if (findPublishTree.size() == 0) {
                    expertItems.add(expertCategory);
                } else if (data != null && data.getCode().length() == 7) {
                    expertItems.add(expertCategory);
                }
            }
        }
        List < SupplierCateTree > allTreeList = new ArrayList < SupplierCateTree > ();
        for(ExpertCategory item: expertItems) {
            String categoryId = item.getCategoryId();
            SupplierCateTree cateTree = getTreeListByCategoryId(categoryId, flag);
            if(cateTree != null && cateTree.getRootNode() != null) {
                cateTree.setItemsId(categoryId);
                allTreeList.add(cateTree);
            }
        }
        for(SupplierCateTree cate: allTreeList) {
            cate.setRootNode(cate.getRootNode() == null ? "" : cate.getRootNode());
            cate.setFirstNode(cate.getFirstNode() == null ? "" : cate.getFirstNode());
            cate.setSecondNode(cate.getSecondNode() == null ? "" : cate.getSecondNode());
            cate.setThirdNode(cate.getThirdNode() == null ? "" : cate.getThirdNode());
            cate.setFourthNode(cate.getFourthNode() == null ? "" : cate.getFourthNode());
            cate.setRootNode(cate.getRootNode());
        }
        model.addAttribute("expertId", expertId);
        model.addAttribute("typeId", typeId);
        model.addAttribute("result", new PageInfo < > (items));
        model.addAttribute("itemsList", allTreeList);
        List<ExpertCategory> list = expertCategoryService.getListCount(expertId, typeId, "1");//设置level为1是为了过滤掉父节点,只统计子节点个数
        
        model.addAttribute("resultPages", (list == null ? 0 : this.totalPages(list)));
        model.addAttribute("resultTotal", (list == null ? 0 : list.size()));
        model.addAttribute("resultpageNum", pageNum);
        model.addAttribute("resultStartRow", (list == null ? 0 : 1));
        model.addAttribute("resultEndRow", new PageInfo < > (items).getEndRow()+1);

        
        //未通过 字段
        ExpertAudit expertAuditFor = new ExpertAudit();
		expertAuditFor.setExpertId(expertId);
		expertAuditFor.setSuggestType("six");
		
		List < ExpertAudit > reasonsList = expertAuditService.getListByExpert(expertAuditFor);
		StringBuffer conditionStr = new StringBuffer();
		for (ExpertAudit expertAudit2 : reasonsList) {
			conditionStr.append(expertAudit2.getAuditFieldId() + ",");
		}
		model.addAttribute("conditionStr", conditionStr);
        
        return "ses/ems/expertAudit/ajax_items";
	}
	
	public static int totalPages(List<ExpertCategory> list) {
		int pageSize = PropUtil.getIntegerProperty("pageSize");
		
		int totalPages = 0;  //总页数
		
		if ((list.size() % pageSize) == 0) {
            totalPages = list.size() / pageSize;
        } else {
            totalPages = list.size() / pageSize + 1;
        }
		
		return totalPages;
    }
	
	
	/**
     *〈简述〉查询品目信息
     *〈详细描述〉
     * @author WangHuijie
     * @param categoryId 产品Id
     * @return List<CategoryTree> tree对象List
     */
	public SupplierCateTree getTreeListByCategoryId(String categoryId, String flag) {
        SupplierCateTree cateTree = new SupplierCateTree();
        // 递归获取所有父节点
        List < Category > parentNodeList = getAllParentNode(categoryId, flag);
        // 加入根节点
        for(int i = 0; i < parentNodeList.size(); i++) {
            DictionaryData rootNode = DictionaryDataUtil.findById(parentNodeList.get(i).getId());
            if(rootNode != null) {
                cateTree.setRootNode(rootNode.getName());
            }
        }
        // 加入一级节点
        if(cateTree.getRootNode() != null) {
            for(int i = 0; i < parentNodeList.size(); i++) {
                Category cate = null;
                if (flag == null) {
                    cate = categoryService.findById(parentNodeList.get(i).getId());
                } else {
                    cate = engCategoryService.findById(parentNodeList.get(i).getId()); 
                }
                if(cate != null && cate.getParentId() != null) {
                    DictionaryData rootNode = DictionaryDataUtil.findById(cate.getParentId());
                    if(rootNode != null && cateTree.getRootNode().equals(rootNode.getName())) {
                        cateTree.setFirstNode(cate.getName());
                    }
                }
            }
        }
        // 加入二级节点
        if(cateTree.getRootNode() != null && cateTree.getFirstNode() != null) {
            for(int i = 0; i < parentNodeList.size(); i++) {
                Category cate = null;
                if (flag == null) {
                    cate = categoryService.findById(parentNodeList.get(i).getId());
                } else {
                    cate = engCategoryService.findById(parentNodeList.get(i).getId());
                }
                if(cate != null && cate.getParentId() != null) {
                    Category parentNode = null;
                    if (flag == null) {
                        parentNode = categoryService.findById(cate.getParentId());
                    } else {
                        parentNode = engCategoryService.findById(cate.getParentId());
                    }
                    if(parentNode != null && cateTree.getFirstNode().equals(parentNode.getName())) {
                        cateTree.setSecondNode(cate.getName());
                    }
                }
            }
        }
        // 加入三级节点
        if(cateTree.getRootNode() != null && cateTree.getFirstNode() != null && cateTree.getSecondNode() != null) {
            for(int i = 0; i < parentNodeList.size(); i++) {
                Category cate = null;
                if (flag == null) {
                    cate = categoryService.findById(parentNodeList.get(i).getId());
                } else {
                    cate = engCategoryService.findById(parentNodeList.get(i).getId());
                }
                if(cate != null && cate.getParentId() != null) {
                    Category parentNode = null;
                    if (flag == null) {
                        parentNode = categoryService.findById(cate.getParentId());
                    } else {
                        parentNode = engCategoryService.findById(cate.getParentId());
                    }
                    if(parentNode != null && cateTree.getSecondNode().equals(parentNode.getName())) {
                        cateTree.setThirdNode(cate.getName());
                    }
                }
            }
        }
        // 加入末级节点
        if(cateTree.getRootNode() != null && cateTree.getFirstNode() != null && cateTree.getSecondNode() != null && cateTree.getThirdNode() != null) {
            for(int i = 0; i < parentNodeList.size(); i++) {
                Category cate = null;
                if (flag == null) {
                    cate = categoryService.findById(parentNodeList.get(i).getId());
                } else {
                    cate = engCategoryService.findById(parentNodeList.get(i).getId());
                }
                if(cate != null && cate.getParentId() != null) {
                    Category parentNode = null;
                    if (flag == null) {
                        parentNode = categoryService.findById(cate.getParentId());
                    } else {
                        parentNode = engCategoryService.findById(cate.getParentId());
                    }
                    if(parentNode != null && cateTree.getThirdNode().equals(parentNode.getName())) {
                        cateTree.setFourthNode(cate.getName());
                    }
                }
            }
        }
        return cateTree;
    }
	
	/**
	 *〈简述〉获取当前节点的所有父级节点(包括根节点)
	 *〈详细描述〉
	 * @author WangHuijie
	 * @param categoryId 
	 * @return
	 */
	 public List < Category > getAllParentNode(String categoryId, String flag) {
	        List < Category > categoryList = new ArrayList < Category > ();
	        while(true) {
	            Category cate = null;
	            if (flag == null) {
	                cate = categoryService.findById(categoryId); 
	            } else {
	                cate = engCategoryService.findById(categoryId); 
	            }
	            if(cate == null) {
	                DictionaryData root = DictionaryDataUtil.findById(categoryId);
	                Category rootNode = new Category();
	                if (root != null) {
	                	rootNode.setId(root.getId());
	                	rootNode.setName(root.getName());
	                	categoryList.add(rootNode);
					}
	                break;
	            } else {
	                categoryList.add(cate);
	                categoryId = cate.getParentId();
	            }
	        }
	        return categoryList;
	    }
    
	/**
	 *〈简述〉
	 * 异步加载zTree
	 *〈详细描述〉
	 * @author WangHuijie
	 * @param expertId
	 * @param id
	 * @param categoryId
	 * @return
	 */
	@RequestMapping(value = "getCategory", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String getCategory(String expertId, String categoryId) {
		List < CategoryTree > allCategories = new ArrayList < CategoryTree > ();
		// 将根节点插入
		DictionaryData parent = dictionaryDataServiceI.getDictionaryData(categoryId);
		CategoryTree ct = new CategoryTree();
		ct.setName(parent.getName());
		ct.setId(parent.getId());
		ct.setIsParent("true");
		// 设置是否被选中
		ct.setChecked(true);
		allCategories.add(ct);
		// 查询所有被选中的
		List < ExpertCategory > MyCate = expertCategoryService.getListByExpertId(expertId, categoryId);
		for(ExpertCategory ec: MyCate) {
			Category cate = categoryService.findById(ec.getCategoryId());
			CategoryTree ct1 = new CategoryTree();
			ct1.setName(cate.getName());
			ct1.setId(cate.getId());
			ct1.setParentId(cate.getParentId());
			// 判断是否是子节点
			List < Category > nodesList = categoryService.findPublishTree(cate.getId(), null);
			if(nodesList != null && nodesList.size() > 0) {
				ct1.setIsParent("true");
			}
			// 设置是否被选中
			ct1.setChecked(true);
			allCategories.add(ct1);
			// 判断是否父级节点为根节点
			if(!categoryId.equals(ct1.getParentId())) {
				// 如果不是根节点则假如该节点的所有父节点
				List < CategoryTree > parentNodeList = getParentNodeList(ct1.getId(), categoryId);
				allCategories.addAll(parentNodeList);
			}
		}
		// 去重
		removeSame(allCategories);
		return JSON.toJSONString(allCategories);
	}

	/**
	 *〈简述〉品目去重
	 *〈详细描述〉
	 * @author WangHuijie
	 * @param allCategories
	 * @return
	 */
	public void removeSame(List < CategoryTree > list) {
		for(int i = 0; i < list.size() - 1; i++) {
			for(int j = list.size() - 1; j > i; j--) {
				if(list.get(j).getId().equals(list.get(i).getId())) {
					list.remove(j);
				}
			}
		}
	}

	/**
	 *〈简述〉获取当前节点的所有父级节点Tree
	 *〈详细描述〉
	 * @author WangHuijie
	 * @param nodeId 节点Id
	 * @return 返回CategoryTreeList
	 */
	public List < CategoryTree > getParentNodeList(String nodeId, String categoryId) {
		List < CategoryTree > parentNodeList = new ArrayList < CategoryTree > ();
		Category category = categoryService.findById(nodeId);
		if(category != null) {
			String parentId = category.getParentId();
			if(parentId != null && !"".equals(parentId) && !categoryId.equals(parentId)) {
				Category cate = categoryService.findById(parentId);
				if(cate != null) {
					CategoryTree ct1 = new CategoryTree();
					ct1.setName(cate.getName());
					ct1.setId(cate.getId());
					ct1.setParentId(cate.getParentId());
					ct1.setIsParent("true");
					// 设置是否被选中
					ct1.setChecked(true);
					parentNodeList.add(ct1);
					List < CategoryTree > parentList = getParentNodeList(ct1.getId(), categoryId);
					parentNodeList.addAll(parentList);
				}
			}
		}
		return parentNodeList;
	}

	/**
	 * @Title: expertFile
	 * @author XuQing 
	 * @date 2016-12-19 下午7:37:01  
	 * @Description:附件信息
	 * @param @param expert
	 * @param @param model
	 * @param @param expertId
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/expertFile")
	public String expertFile(Expert expert, Model model, String expertId, Integer sign) {
		//初审复审标识（1初审，3复查，2复审）
		model.addAttribute("sign", sign);
				
		// 专家系统key
		Integer expertKey = Constant.EXPERT_SYS_KEY;
		model.addAttribute("expertKey", expertKey);
		// 获取各个附件类型id集合
		Map < String, Object > typeMap = getTypeId();
		// typrId集合
		model.addAttribute("typeMap", typeMap);

		expert = expertService.selectByPrimaryKey(expertId);
		model.addAttribute("expert", expert);
		model.addAttribute("expertId", expertId);
		//回显不通过的字段
		if( expert.getStatus().equals("0") ||  expert.getStatus().equals("1") ||  expert.getStatus().equals("6")){
			ExpertAudit expertAuditFor = new ExpertAudit();
			expertAuditFor.setExpertId(expertId);
			expertAuditFor.setSuggestType("five");
			
			List < ExpertAudit > reasonsList = expertAuditService.getListByExpert(expertAuditFor);
			StringBuffer conditionStr = new StringBuffer();
			if(!reasonsList.isEmpty()){
				for (ExpertAudit expertAudit2 : reasonsList) {
					conditionStr.append(expertAudit2.getAuditField() + ",");
				}
			}
			model.addAttribute("conditionStr", conditionStr);
			
			/**
			 * 附件退回修改
			 */
			ExpertAuditFileModify expertAuditFileModify = new ExpertAuditFileModify();
			expertAuditFileModify.setExpertId(expertId);
			List<ExpertAuditFileModify> selectFileModifyByExpertId = expertAuditService.selectFileModifyByExpertId(expertAuditFileModify);
			StringBuffer fileModify = new StringBuffer();
			if(!selectFileModifyByExpertId.isEmpty()){
				for(ExpertAuditFileModify m : selectFileModifyByExpertId){
					fileModify.append(m.getTypeId() + ",");
				}
				model.addAttribute("fileModify", fileModify);
			}
		}
		return "ses/ems/expertAudit/expertFile";
	}

	/**
	 * 
	 * @Title: getTypeId
	 * @author XuQing
	 * @date 2016年11月9日 下午2:32:38
	 * @Description: 封装附件类型
	 * @param @return
	 * @return Map<String,Object>
	 */
	private Map < String, Object > getTypeId() {
		DictionaryData dd = new DictionaryData();
		Map < String, Object > typeMap = new HashMap < > ();
		for(int i = 0; i < 8; i++) {
			if(i == 0) {
				// 军官证件
				dd.setCode("EXPERT_IDNUMBER");
				List < DictionaryData > find = dictionaryDataServiceI.find(dd);
				typeMap.put("EXPERT_IDNUMBER_TYPEID", find.get(0).getId());
			}
			if(i == 1) {
				// 职称证书
				dd.setCode("EXPERT_TITLE");
				List < DictionaryData > find = dictionaryDataServiceI.find(dd);
				typeMap.put("EXPERT_TITLE_TYPEID", find.get(0).getId());
			}
			if(i == 2) {
				// 申请表
				dd.setCode("EXPERT_APPLICATION");
				List < DictionaryData > find = dictionaryDataServiceI.find(dd);
				typeMap.put("EXPERT_APPLICATION_TYPEID", find.get(0).getId());
			}
			if(i == 3) {
				// 学历证书
				dd.setCode("EXPERT_ACADEMIC");
				List < DictionaryData > find = dictionaryDataServiceI.find(dd);
				typeMap.put("EXPERT_ACADEMIC_TYPEID", find.get(0).getId());
			}
			if(i == 4) {
				// 学位证书
				dd.setCode("EXPERT_DEGREE");
				List < DictionaryData > find = dictionaryDataServiceI.find(dd);
				typeMap.put("EXPERT_DEGREE_TYPEID", find.get(0).getId());
			}
			if(i == 5) {
				// 个人照片
				dd.setCode("EXPERT_PHOTO");
				List < DictionaryData > find = dictionaryDataServiceI.find(dd);
				typeMap.put("EXPERT_PHOTO_TYPEID", find.get(0).getId());
			}
			if(i == 6) {
				// 合同书
				dd.setCode("EXPERT_CONTRACT");
				List < DictionaryData > find = dictionaryDataServiceI.find(dd);
				typeMap.put("EXPERT_CONTRACT_TYPEID", find.get(0).getId());
			}
			if(i == 7) {
				// 军官证件
				dd.setCode("EXPERT_IDCARDNUMBER");
				List < DictionaryData > find = dictionaryDataServiceI.find(dd);
				typeMap.put("EXPERT_IDCARDNUMBER_TYPEID", find.get(0).getId());
			}
		}
		return typeMap;
	}

	/**
	 * @Title: expertType
	 * @author XuQing 
	 * @date 2016-12-19 下午7:37:48  
	 * @Description:专家类型
	 * @param @param expertAudit
	 * @param @param model
	 * @param @param expertId
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/expertType")
	public String expertType(ExpertAudit expertAudit, Model model, String expertId, Integer sign) {
		//初审复审标识（1初审，3复查，2复审）
		model.addAttribute("sign", sign);
		
		// 产品类型数据字典
		List < DictionaryData > spList = DictionaryDataUtil.find(6);
		model.addAttribute("spList", spList);
		// 经济类型数据字典
		List < DictionaryData > jjTypeList = DictionaryDataUtil.find(19);
		model.addAttribute("jjList", jjTypeList);
		// 货物类型数据字典
		List < DictionaryData > hwList = DictionaryDataUtil.find(8);
		model.addAttribute("hwList", hwList);

		Expert expert = expertService.selectByPrimaryKey(expertId);
		model.addAttribute("expert", expert);
		
		String type = expert.getExpertsTypeId();
		model.addAttribute("expertType", type);
		/*//工程下的执业资格
		for(DictionaryData d : spList){
			if(d.getCode().equals("PROJECT")){
				if(type.contains(d.getId())){
					model.addAttribute("isProject", "project");
				}
			}
		}*/
		
		/**
		 * 修改前的专家类型
		 */
		if(expert.getStatus() != null && expert.getStatus().equals("0")) {
			StringBuffer editFields = new StringBuffer();
			
			//历史表里记录的类型（修改前的专家类型）
			ExpertHistory oldExpert = service.selectOldExpertById(expertId);
			if(oldExpert !=null && oldExpert.getExpertsTypeId()!=null){
				String oldType = oldExpert.getExpertsTypeId();
				String[] historyType = oldExpert.getExpertsTypeId().split(",");
				for(String h : historyType){
					if(!type.contains(h)){
						editFields.append(h);
					}
				}
			
			//全部类型
			StringBuffer typeAll = new StringBuffer();
			for(DictionaryData d : spList){
				typeAll.append(d.getId() + ",");
			}
			for(DictionaryData d : jjTypeList){
				typeAll.append(d.getId());
			}
			
			//现在勾选的类型
			String[] split = type.split(",");
			for(String s : split){
				if(typeAll.toString().contains(s) && !oldType.contains(s)){
					editFields.append(s + ",");
					}
				}
	
			model.addAttribute("editFields", editFields);
			}
		}
		
		//  判断当前状态如果为退回修改则比较两次的信息
		/*// 判断有没有进行修改
		if(expert.getStatus() != null || expert.getStatus().equals("0")) {
			ExpertHistory oldExpert = service.selectOldExpertById(expertId);
			if(oldExpert != null) {
				Map < String, Object > compareMap = compareExpert(oldExpert, (ExpertHistory) expert);
				// 如果isEdit==1代表没有进行任何修改就进行了二次提交
				if(compareMap.isEmpty()) {
					// 没有修改
					model.addAttribute("isEdit", "0");
				} else {
					// 有修改
					model.addAttribute("isEdit", "1");
				}
				Set < String > keySet = compareMap.keySet();
				List < String > editPractice = new ArrayList < String > ();
				for(String method: keySet) {
					editPractice.add(method);
				}
				model.addAttribute("editPractice", editPractice);
			}
		}*/
		
		//回显修改前的职业资格
		ExpertEngHistory expertEngHistory = new ExpertEngHistory();
		expertEngHistory.setExpertId(expertId);
		List<ExpertEngHistory> list = expertEngModifySerivce.selectByExpertId(expertEngHistory);
		StringBuffer modifyFiled = new StringBuffer();
		if(!list.isEmpty()){
			for(ExpertEngHistory history : list){
				String beforeField = history.getRelationId() +"_"+ history.getField();
				modifyFiled.append(beforeField + ",");
			}
			model.addAttribute("modifyFiled", modifyFiled);
		}
		
		
		
		/**
		 * 执业资格模块
		 */
		
		List<ExpertTitle> expertTitleList = new ArrayList<>();

		//工程技术
		String engCodeId = DictionaryDataUtil.getId("PROJECT");
		
		//工程经济
		String goodsProjectId = DictionaryDataUtil.getId("GOODS_PROJECT");
		
		if(expert.getExpertsTypeId() !=null && !"".equals(expert.getExpertsTypeId())){
			if(expert.getExpertsTypeId().contains(engCodeId)){
				expertTitleList = expertTitleService.queryByUserId(expertId,engCodeId);	
				model.addAttribute("isProject", "project");
			}
			if(expert.getExpertsTypeId().contains(goodsProjectId)){
				expertTitleList = expertTitleService.queryByUserId(expertId,goodsProjectId);	
				model.addAttribute("isProject", "project");
			}
		}
		
		model.addAttribute("expertTitleList", expertTitleList);
		
		// 专家系统key
		Integer expertKey = Constant.EXPERT_SYS_KEY;
		model.addAttribute("expertKey", expertKey);
		// 获取各个附件类型id集合
		Map < String, Object > typeMap = getTypeId();
		// typrId集合
		model.addAttribute("typeMap", typeMap);
		
		//回显不通过的字段
		if( expert.getStatus().equals("0") ||  expert.getStatus().equals("1") ||  expert.getStatus().equals("6")){
			/*ExpertAudit expertAuditFor = new ExpertAudit();
			expertAuditFor.setExpertId(expertId);
			expertAuditFor.setSuggestType("seven");
			
			List < ExpertAudit > reasonsList = expertAuditService.getListByExpert(expertAuditFor);
			StringBuffer conditionStr = new StringBuffer();
			if(!reasonsList.isEmpty()){
				for (ExpertAudit expertAudit2 : reasonsList) {
					String beforeField = expertAudit2.getAuditFieldId() +"_"+ expertAudit2.getAuditFieldName();
					conditionStr.append(beforeField + ",");
				}
				model.addAttribute("conditionStr", conditionStr);
			}*/
			
		
    		//不通过字段（专家类型）
        	ExpertAudit expertAuditFor = new ExpertAudit();
			expertAuditFor.setExpertId(expertId);
			expertAuditFor.setSuggestType("seven");
			expertAuditFor.settype("1");
			List < ExpertAudit > reasonsList = expertAuditService.getListByExpert(expertAuditFor);
			
			
			StringBuffer typeErrorField = new StringBuffer();
			if(!reasonsList.isEmpty()){
				for (ExpertAudit expertAudit2 : reasonsList) {
					String beforeField = expertAudit2.getAuditFieldId();
					typeErrorField.append(beforeField + ",");
				}
				model.addAttribute("typeErrorField", typeErrorField);
			}
			
			//不通过字段（执业资格）
			expertAuditFor.settype("2");
			List < ExpertAudit > engReasonsList = expertAuditService.getListByExpert(expertAuditFor);
			StringBuffer engErrorField = new StringBuffer();
			if(!engReasonsList.isEmpty()){
				for (ExpertAudit expertAudit2 : engReasonsList) {
					String beforeField = expertAudit2.getAuditFieldId() +"_"+ expertAudit2.getAuditFieldName();
					engErrorField.append(beforeField + ",");
				}
				model.addAttribute("engErrorField", engErrorField);
			}
		}
		
		if( expert.getStatus().equals("0")){
			/**
			 * 附件退回修改
			 */
			ExpertAuditFileModify expertAuditFileModify = new ExpertAuditFileModify();
			expertAuditFileModify.setTypeId("9");
			expertAuditFileModify.setExpertId(expertId);
			List<ExpertAuditFileModify> selectFileModifyByExpertId = expertAuditService.selectFileModifyByExpertId(expertAuditFileModify);
			StringBuffer fileModify = new StringBuffer();
			if(!selectFileModifyByExpertId.isEmpty()){
				for(ExpertAuditFileModify m : selectFileModifyByExpertId){
					fileModify.append(m.getListId() + ",");
				}
				model.addAttribute("fileModify", fileModify);
			}
		}
		return "ses/ems/expertAudit/expertType";
	}


	/**
	 * @Title: showModify
	 * @author XuQing 
	 * @date 2016-12-28 上午10:37:47  
	 * @Description:查询退回修改再次提交审核显示之前的信息
	 * @param @param supplierHistory
	 * @param @param request
	 * @param @return      
	 * @return String
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/showModify", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String showModify(ExpertEngHistory expertEngHistory, HttpServletRequest request) throws ParseException {
		List<ExpertEngHistory> modifyList = expertEngModifySerivce.selectByExpertId(expertEngHistory);
		if(!modifyList.isEmpty()  && modifyList.size() > 0){
			expertEngHistory = modifyList.get(0);
			if("titleTime".equals(expertEngHistory.getField())){
				String titleTime = expertEngHistory.getContent().substring(0, 7);
				expertEngHistory.setContent(titleTime);
			}
			return JSON.toJSONString(expertEngHistory.getContent());
		}
		return null;
	}
	
	
	/**
	 * @Title: reasonsList
	 * @author XuQing 
	 * @date 2016-12-19 下午7:38:05  
	 * @Description:问题汇总
	 * @param @param expertAudit
、
	 * @param @param model
	 * @param @param expertId
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/reasonsList")
	public String reasonsList(ExpertAudit expertAudit, Model model, String expertId, Integer sign) {
		//初审复审标识（1初审，3复查，2复审）
		model.addAttribute("sign", sign);
		
		List < ExpertAudit > reasonsList = expertAuditService.getListByExpertId(expertId);
		model.addAttribute("reasonsList", reasonsList);
		//查看是否有记录
		model.addAttribute("num", reasonsList.size());

		Expert expert = expertService.selectByPrimaryKey(expertId);
		model.addAttribute("status", expert.getStatus());
		model.addAttribute("isSubmit", expert.getIsSubmit());

		model.addAttribute("expertId", expertId);
		return "ses/ems/expertAudit/reasonsList";
	}

	/**
	 * @Title: updateStatus
	 * @author XuQing 
	 * @date 2016-12-19 下午7:38:19  
	 * @Description:提交审核
	 * @param @param expert
	 * @param @param model
	 * @param @param request
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/updateStatus")
	public String updateStatus(@CurrentUser User user, Expert expert, Model model, HttpServletRequest request) {
		/**
		 *  如果是退回修改就保存历史信息
		 */
		if("3".equals(expert.getStatus())) {
			//删除旧的专家input信息
			/*service.deleteExpertHistory(expert.getId());*/
			service.updateIsDeleteById(expert.getId());
			
			//重新插入新的信息
			Expert exp = service.selectByPrimaryKey(expert.getId());
			service.insertExpertHistory(exp);
			
			/**
			 * 删除工程下的职业资格之前的历史记录
			 */
			ExpertEngHistory expertEngHistory = new ExpertEngHistory();
			expertEngHistory.setExpertId(expert.getId());
			/*expertEngHistorySerivce.deleteByExpertId(expertEngHistory);*/
			//软删除历史信息
			expertEngHistorySerivce.updateIsDeletedByExpertId(expertEngHistory);
			// 新增历史记录
			expertEngHistorySerivce.insertSelective(expertEngHistory);
			//软删除之前的对比记录
			expertEngModifySerivce.updateIsDeletedByExpertId(expert.getId());
			
			//软删除附件修改的记录
			/*expertAuditService.delFileModifyByExpertId(expert.getId());*/
			expertAuditService.updateIsDeleted(expert.getId());
			
			expert.setIsSubmit("0");
		}
		
		//初审不通过记录
		if("2".equals(expert.getStatus())){
			ExpertAuditNot expertAuditNot = new ExpertAuditNot();
			Expert expertinfo = expertService.selectByPrimaryKey(expert.getId());
			expertAuditNot.setCreatedAt(new Date());
			expertAuditNot.setExpertId(expertinfo.getId());
			expertAuditNot.setExpertName(expertinfo.getRelName());
			expertAuditNot.setIdCard(expertinfo.getIdCardNumber());
			expertAuditNotService.insertSelective(expertAuditNot);
		}
		
		//提交审核，更新状态
		expert.setAuditAt(new Date());
		
		//审核人
		expert.setAuditor(user.getRelName());
		//还原暂存状态
		expert.setAuditTemporary(0);
		expertService.updateByPrimaryKeySelective(expert);

		
		String expertId = expert.getId();
		expert = expertService.selectByPrimaryKey(expertId);
		String status = expert.getStatus();
		/*Todos todos = new Todos();
		String expertName = expert.getRelName();
		User user=(User) request.getSession().getAttribute("loginUser");*/

		/**
		 * 更新待办（已完成）
		 */
		if(status.equals("1") || status.equals("2") || status.equals("3") || status.equals("4") || status.equals("5") || status.equals("7") || status.equals("8")) {
			todosService.updateIsFinish("expertAudit/basicInfo.html?expertId=" + expertId);

		}

		/**
		 * 待办
		 */
		if ("1".equals(status)){
	        Todos todos = new Todos();
	        todos.setCreatedAt(new Date());
	        todos.setIsDeleted((short)0);
	        todos.setIsFinish((short)0);
	        //待办名称
	        todos.setName(expert.getRelName()+"专家复审");
	        //todos.setReceiverId();
	        //接受人id
	        /*todos.setOrgId(expert.getPurchaseDepId());*/
	        //权限id
	        PropertiesUtil config = new PropertiesUtil("config.properties");
	        todos.setPowerId(config.getString("zjfs"));
	        //发送人id
	        /*User user = (User)request.getSession().getAttribute("loginUser");*/
	        todos.setSenderId(user.getId());
	        //类型
	        todos.setUndoType((short)2);
	        //发送人姓名
	        todos.setSenderName(expert.getRelName());
	        //审核地址
	        todos.setUrl("expertAudit/basicInfo.html?expertId=" + expert.getId());
	        todosService.insert(todos );
	      }
		
		return "redirect:list.html";
	}

	public void writeJson(HttpServletResponse response, Object object) {
		try {
			String json = JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss");
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write(json);
			response.getWriter().flush();
			response.getWriter().close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @Title: deleteByIds
	 * @author XuQing 
	 * @date 2016-12-19 下午7:38:43  
	 * @Description:批量移除审核问题
	 * @param @param ids
	 * @param @param response      
	 * @return void
	 */
	@RequestMapping("/deleteByIds")
	public void deleteByIds(String[] ids, HttpServletResponse response) {
		boolean Whether = expertAuditService.deleteByIds(ids);
		if(Whether) {
			String msg = "{\"msg\":\"yes\"}";
			writeJson(response, msg);
		}

	}

	/**
	 * @Title: download
	 * @author XuQing 
	 * @date 2016-12-27 下午2:34:20  
	 * @Description:word下载
	 * @param @param expertId
	 * @param @param request
	 * @param @param response
	 * @param @return
	 * @param @throws Exception      
	 * @return ResponseEntity<byte[]>
	 */
	@RequestMapping("download")
	public ResponseEntity < byte[] > download(String expertId, HttpServletRequest request, HttpServletResponse response, String tableType) throws Exception {
		// 根据编号查询专家信息
		Expert expert = service.selectByPrimaryKey(expertId);
		// 文件存储地址
		String filePath = request.getSession().getServletContext().getRealPath("/WEB-INF/upload_file/");
		// 文件名称
		String fileName = createWordMethod(expert, request, tableType);
		// 下载后的文件名
		String downFileName = "";
		if(tableType.equals("1")){
			downFileName = new String("军队采购评审专家入库初审表.doc".getBytes("UTF-8"), "iso-8859-1"); // 为了解决中文名称乱码问题
		}
		if(tableType.equals("2")){
			downFileName = new String("军队采购评审专家入库复审表.doc".getBytes("UTF-8"), "iso-8859-1"); // 为了解决中文名称乱码问题
		}
		if(tableType.equals("3")){
			downFileName = new String("军队采购评审专家入库复查表.doc".getBytes("UTF-8"), "iso-8859-1"); // 为了解决中文名称乱码问题
		}
		response.setContentType("application/x-download");
		return service.downloadFile(fileName, filePath, downFileName);
	}

	/**
	 * @Title: createWordMethod
	 * @author XuQing 
	 * @date 2016-12-27 下午2:34:36  
	 * @Description:生成word
	 * @param @param expert
	 * @param @param request
	 * @param @return
	 * @param @throws Exception      
	 * @return String
	 */
	private String createWordMethod(Expert expert, HttpServletRequest request, String tableType) throws Exception {
		/** 用于组装word页面需要的数据 */
		Map < String, Object > dataMap = new HashMap < String, Object > ();
		dataMap.put("relName", expert.getRelName() == null ? "" : expert.getRelName());
		String sex = expert.getGender();
		DictionaryData gender = dictionaryDataServiceI.getDictionaryData(sex);
		dataMap.put("gender", gender == null ? "" : gender.getName());
		dataMap.put("idCardNumber", expert.getIdCardNumber() == null ? "" : expert.getIdCardNumber());
		dataMap.put("workUnit", expert.getWorkUnit() == null ? "" : expert.getWorkUnit());
		dataMap.put("birthday", expert.getBirthday() == null ? "" : new SimpleDateFormat("yyyy-MM-dd").format(expert.getBirthday()));
		DictionaryData expertsForm = dictionaryDataServiceI.getDictionaryData(expert.getExpertsFrom());
		if(expertsForm != null) {
			dataMap.put("expertsFrom", expertsForm.getName() == null ? "" : expertsForm.getName());
		} else {
			dataMap.put("expertsFrom", "");
		}
		StringBuffer expertType = new StringBuffer();
		if(expert.getExpertsTypeId() !=null && expert.getExpertsTypeId() !=""){
			for(String typeId: expert.getExpertsTypeId().split(",")) {
				expertType.append(dictionaryDataServiceI.getDictionaryData(typeId).getName() + "、");
			}
		}
		dataMap.put("professTechTitles", expert.getProfessTechTitles() == null ? "" : expert.getProfessTechTitles());
		if(expertType.toString().trim().length() > 0 ){
			String expertsType = expertType.toString().substring(0, expertType.length() - 1);
			dataMap.put("expertsTypeId", expertsType);
		}else{
			dataMap.put("expertsTypeId", "");
		}
		
		
		//获取最终意见
		ExpertAuditOpinion expertAuditOpinion = new ExpertAuditOpinion();
		expertAuditOpinion.setExpertId(expert.getId());
		expertAuditOpinion = expertAuditOpinionService.selectByPrimaryKey(expertAuditOpinion);
		if(expertAuditOpinion != null){
			dataMap.put("reason", expertAuditOpinion.getOpinion() == null ? "无" : expertAuditOpinion.getOpinion());
		}else{
			dataMap.put("reason", "无");
		}
		
		
		//查询品目类型id
		
		//工程技术
		String engCodeId=DictionaryDataUtil.getId("PROJECT");
		
		//工程经济和工程技术就有
		String engInfoId=DictionaryDataUtil.getId("ENG_INFO_ID");
		
		//工程经济
		String goodsProjectId=DictionaryDataUtil.getId("GOODS_PROJECT");
		
		// 获取专家类别id
        List < String > expertTypeId = new ArrayList < String > ();
        for(String id: expert.getExpertsTypeId().split(",")) {
        	expertTypeId.add(id);
        	
        	//有工程技术就带出工程专业信息
        	if(id.equals(engCodeId)){
        		expertTypeId.add(engInfoId);
        	}
        	
        	//工程经济
        	if(id.equals(goodsProjectId)){
        		expertTypeId.add(engInfoId);
        	}
        }
        List<SupplierCateTree> itemsListAll = new ArrayList<SupplierCateTree>();
        for(String typeId : expertTypeId){
        	List<SupplierCateTree> itemsList = this.getItemsAll(expert.getId(), typeId);
        	for(SupplierCateTree c : itemsList){
        		itemsListAll.add(c);
        	}
        }		
		
        /**
         * 拼接产品
         */
        StringBuffer items = new StringBuffer();
        for(SupplierCateTree cateTree : itemsListAll ){
        	String rootNode = cateTree.getRootNode();
        	String firstNode = cateTree.getFirstNode();
        	String secondNode = cateTree.getSecondNode();
        	String thirdNode = cateTree.getThirdNode();
        	if(rootNode !=null && rootNode !=""){
        		items.append(rootNode);
        	}
        	if(firstNode !=	null && firstNode !=""){
        		items.append("—" + firstNode); 
        	}
        	if(secondNode != null && secondNode !=""){
        		items.append("—" + secondNode); 
        	}
        	if(thirdNode != null && thirdNode !=""){
        		items.append("—" + thirdNode); 
        	}
        	cateTree.setRootNode(items.toString());
        	items.delete(0, items.length());
        }
        
        /**
         * 比较勾选的产品是否通过审核
         */
        List < ExpertAudit > reasonsList = new ArrayList<ExpertAudit>();
    	for(SupplierCateTree cateTree : itemsListAll ){
    		//查询未通过审核的产品
    		ExpertAudit expertAudit = new ExpertAudit();
    		expertAudit.setExpertId(expert.getId());
    		expertAudit.setSuggestType("six");
    		expertAudit.setAuditFieldId(cateTree.getItemsId());
    		List < ExpertAudit > reasonsItemsList = expertAuditService.selectbyAuditType(expertAudit);
    		ExpertAudit expertAudit1 = new ExpertAudit();
    		//比较
    		if(reasonsItemsList !=null && reasonsItemsList.size() > 0){
    			for(ExpertAudit audit :reasonsItemsList){
    				expertAudit1.setAuditField(cateTree.getRootNode());
        			String reason = audit.getAuditReason();
        			expertAudit1.setAuditReason("不通过。原因：" + reason);
    			}
    		}else{
    			expertAudit1.setAuditField(cateTree.getRootNode());
    			expertAudit1.setAuditReason("通过");
    		}
    		reasonsList.add(expertAudit1);
    		
    	}
    	
    	/**
    	 * 比较附件那些通过审核(初审和复查才有附件信息)
    	 */
    	if(tableType.equals("1") || tableType.equals("3")){
    		ExpertAudit auditFileInfo = new ExpertAudit();
    		auditFileInfo.setExpertId(expert.getId());
    		auditFileInfo.setAuditField("近期免冠彩色证件照");
        	List < ExpertAudit > recentPhotos = expertAuditService.selectFailByExpertId(auditFileInfo);
        	if(recentPhotos != null && recentPhotos.size() > 0){
        		dataMap.put("recentPhotos", "否");
        	}else{ 
        		dataMap.put("recentPhotos", "是");
        	}
        	 
        	auditFileInfo.setAuditField("身份证复印件（正反面）:");
        	List < ExpertAudit > idCard = expertAuditService.selectFailByExpertId(auditFileInfo);
        	if(idCard != null && idCard.size() >0){
        		dataMap.put("idCard", "否");
        	}else{ 
        		dataMap.put("idCard", "是");
        	}

			if(expertsForm.getName().equals("军队") && expert.getExpertsFrom() != null) {
				auditFileInfo.setAuditField("军队人员身份证件");
	        	List < ExpertAudit > armyIdCard = expertAuditService.selectFailByExpertId(auditFileInfo);
	        	if(armyIdCard != null && armyIdCard.size() >0){
	        		dataMap.put("armyIdCard", "否");
	        	}else{ 
	        		dataMap.put("armyIdCard", "是");
	        	}
			} else {
				dataMap.put("armyIdCard", "无");
			}
    		
			auditFileInfo.setAuditField("专业技术资格证书");
        	List < ExpertAudit > qualification = expertAuditService.selectFailByExpertId(auditFileInfo);
        	if(qualification != null && qualification.size() >0){
        		dataMap.put("qualification", "否");
        	}else{ 
        		dataMap.put("qualification", "是");
        	}
        	
        	auditFileInfo.setAuditField("执业资格");
        	List < ExpertAudit > professionalFile = expertAuditService.selectFailByExpertId(auditFileInfo);
        	if(professionalFile != null && professionalFile.size() >0){
        		dataMap.put("professionalFile", "否");
        	}else{ 
        		dataMap.put("professionalFile", "是");
        	}
			
        	auditFileInfo.setAuditField("学位证书");
        	List < ExpertAudit > academicDegree = expertAuditService.selectFailByExpertId(auditFileInfo);
        	if(academicDegree != null && academicDegree.size() >0){
        		dataMap.put("academicDegree", "否");
        	}else{ 
        		dataMap.put("academicDegree", "是");
        	}

			if(expertsForm.getName().equals("地方") && expert.getExpertsFrom() != null) {
				auditFileInfo.setAuditField("学位证书");
	        	List < ExpertAudit > coverNote = expertAuditService.selectFailByExpertId(auditFileInfo);
	        	if(coverNote != null && coverNote.size() >0){
	        		dataMap.put("coverNote", "否");
	        	}else{ 
	        		dataMap.put("coverNote", "是");
	        	}
			} else {
				dataMap.put("coverNote", "无");
			}
    	}
    	

		/**
		 * 获取ExpertField类的属性值
		 */
		ExpertField expertField = new ExpertField();
		Field[] fields = expertField.getClass().getDeclaredFields(); 
		List < String > list = new ArrayList<>();
		for(int i=0; i<fields.length; i++){
			fields[i].setAccessible(true);
			Object str = fields[i].get(expertField);
			list.add(str.toString());
		}
		/**
		 * 拼接未通过审核的字段
		 */
		ExpertAudit expertAudit2 = new ExpertAudit();
		expertAudit2.setExpertId(expert.getId());
		expertAudit2.setSuggestType("one");
    	List < ExpertAudit > basicFileList = expertAuditService.selectbyAuditType(expertAudit2);
		StringBuffer buff = new StringBuffer();
		
		for(ExpertAudit a : basicFileList){
			buff.append(a.getAuditField() + ",");
		}
		
		/**
		 * 比较字段是否通过审核
		 */
		for(String str : list){
			expertAudit2.setExpertId(expert.getId());
			expertAudit2.setSuggestType("one");
			expertAudit2.setAuditField(str);
	    	List < ExpertAudit > basicFileList1 = expertAuditService.selectbyAuditType(expertAudit2);
	    	ExpertAudit expertAuditMap = new ExpertAudit();
			if(basicFileList1 !=null && basicFileList1.size() > 0){
				for(int i=0; i<basicFileList1.size(); i++){
					if(buff.toString().contains(str)){
						expertAuditMap.setAuditField(str);
		    			String reason = basicFileList1.get(0).getAuditReason();
		    			expertAuditMap.setAuditReason("不通过。原因：" + reason);
		    			break;
					}
				}
			}else{
				expertAuditMap.setAuditField(str);
				expertAuditMap.setAuditReason("通过");
			}
			reasonsList.add(expertAuditMap);
		}
		
		dataMap.put("reasonsList", reasonsList);
		
		
		/**
		 * 专家签字模块（获取勾选的专家）复审
		 */
		if(tableType.equals("2")){
			ExpertSignature expertsignature = new ExpertSignature();
			expertsignature.setExpertId(expert.getId());
			List<ExpertSignature> expertList = expertSignatureService.selectByExpertId(expertsignature);
			dataMap.put("expertList", expertList);
			
			//日期
			Date date = new Date();
		    SimpleDateFormat format = new SimpleDateFormat("yyyy 年 MM 月 dd 日");
		    String da = format.format(date);
			dataMap.put("date", da);
		}
		
		/**
		 * 执业资格
		 */		
		List<ExpertTitle> expertTitleList = new ArrayList<>();
		if(expert.getExpertsTypeId() !=null && !"".equals(expert.getExpertsTypeId())){
			if(expert.getExpertsTypeId().contains(engCodeId)){
				expertTitleList = expertTitleService.queryByUserId(expert.getId(),engCodeId);	
			}
			if(expert.getExpertsTypeId().contains(goodsProjectId)){
				expertTitleList = expertTitleService.queryByUserId(expert.getId(),goodsProjectId);	
			}
		}
		StringBuffer professional = new StringBuffer();
		if(!expertTitleList.isEmpty() && expertTitleList.size() >0){
			for(ExpertTitle expertTitle : expertTitleList){
				professional.append(expertTitle.getQualifcationTitle()+"、");
			}
			if(professional.length() > 0){
				professional.deleteCharAt(professional.length() -1);
			}
			dataMap.put("professional", professional);
		}else{
			dataMap.put("professional", "无");
		}
		
		
		/** 生成word 返回文件名 */
		String newFileName = "";
		String fileName = "";
		if(tableType.equals("1")){
			newFileName = WordUtil.createWord(dataMap, "expertOneAudit.ftl", fileName, request);
			fileName = new String(("军队采购评审专家入库初审表.doc").getBytes("UTF-8"), "UTF-8");
		}
		if(tableType.equals("2")){
			newFileName = WordUtil.createWord(dataMap, "expertTwoAudit.ftl", fileName, request);
			fileName = new String(("军队采购评审专家入库复审表.doc").getBytes("UTF-8"), "UTF-8");
		}
		if(tableType.equals("3")){
			newFileName = WordUtil.createWord(dataMap, "expertThreeAudit.ftl", fileName, request);
			fileName = new String(("军队采购评审专家入库复查表.doc").getBytes("UTF-8"), "UTF-8");
		}
		
		return newFileName;
	}
	
	/**
	 * @Title: publish
	 * @author XuQing 
	 * @date 2017-3-9 下午4:06:06  
	 * @Description:发布到门户名录上
	 * @param @param supplierId      
	 * @return String
	 */
	@RequestMapping(value = "/publish", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String publish(String expertId){
		Expert expert = new Expert();
		expert = expertService.selectByPrimaryKey(expertId);
		String msg = "";
		if(expert.getIsPublish() != 1){
			expert.setId(expertId);
			expert.setIsPublish(1);
			expertService.updateByPrimaryKeySelective(expert);
			msg = "yes";
		}else{
			msg = "no";
		}
		return JSON.toJSONString(msg);
	}
	
	
	public List<SupplierCateTree> getItems(String expertId, String typeId, Model model, Integer pageNum) {
		String code = DictionaryDataUtil.findById(typeId).getCode();
        String flag = null;
        if (code != null && code.equals("GOODS_PROJECT")) {
            code = "PROJECT";
            typeId = DictionaryDataUtil.getId("PROJECT");
        }
        if (code.equals("ENG_INFO_ID")) {
            flag = "ENG_INFO";
        }
        // 查询已选中的节点信息
        List<ExpertCategory> items = expertCategoryService.getListByExpertId(expertId, typeId, pageNum == null ? 1 : pageNum);
        List<ExpertCategory> expertItems = new ArrayList<ExpertCategory>();
        int count=0;
        for (ExpertCategory expertCategory : items) {
        	count++;
        	System.out.println(count);
            if (!DictionaryDataUtil.findById(expertCategory.getTypeId()).getCode().equals("ENG_INFO_ID")) {
                Category data = categoryService.findById(expertCategory.getCategoryId());
                List<Category> findPublishTree = categoryService.findPublishTree(expertCategory.getCategoryId(), null);
                if (findPublishTree.size() == 0) {
                    expertItems.add(expertCategory);
                } else if (data != null && data.getCode().length() == 7) {
                    expertItems.add(expertCategory);
                }
            } else {
                Category data = engCategoryService.findById(expertCategory.getCategoryId());
                List<Category> findPublishTree = engCategoryService.findPublishTree(expertCategory.getCategoryId(), null);
                if (findPublishTree.size() == 0) {
                    expertItems.add(expertCategory);
                } else if (data != null && data.getCode().length() == 7) {
                    expertItems.add(expertCategory);
                }
            }
        }
        List < SupplierCateTree > allTreeList = new ArrayList < SupplierCateTree > ();
        for(ExpertCategory item: expertItems) {
            String categoryId = item.getCategoryId();
            SupplierCateTree cateTree = getTreeListByCategoryId(categoryId, flag);
            if(cateTree != null && cateTree.getRootNode() != null) {
                cateTree.setItemsId(categoryId);
                allTreeList.add(cateTree);
            }
        }
        for(SupplierCateTree cate: allTreeList) {
            cate.setRootNode(cate.getRootNode() == null ? "" : cate.getRootNode());
            cate.setFirstNode(cate.getFirstNode() == null ? "" : cate.getFirstNode());
            cate.setSecondNode(cate.getSecondNode() == null ? "" : cate.getSecondNode());
            cate.setThirdNode(cate.getThirdNode() == null ? "" : cate.getThirdNode());
            cate.setFourthNode(cate.getFourthNode() == null ? "" : cate.getFourthNode());
            cate.setRootNode(cate.getRootNode());
        }
        return allTreeList;
	}
	
	/**
	 * @Title: getItemsAll
	 * @author XuQing 
	 * @date 2017-4-7 上午10:28:40  
	 * @Description:所有的不带分页
	 * @param @param expertId
	 * @param @param typeId
	 * @param @return      
	 * @return List<SupplierCateTree>
	 */
	public List<SupplierCateTree> getItemsAll(String expertId, String typeId) {
		String code = DictionaryDataUtil.findById(typeId).getCode();
        String flag = null;
        if (code != null && code.equals("GOODS_PROJECT")) {
            code = "PROJECT";
            typeId = DictionaryDataUtil.getId("PROJECT");
        }
        if (code.equals("ENG_INFO_ID")) {
            flag = "ENG_INFO";
        }
        // 查询已选中的节点信息
        List<ExpertCategory> items = expertCategoryService.getListByExpertId(expertId, typeId);
        List<ExpertCategory> expertItems = new ArrayList<ExpertCategory>();
        int count=0;
        for (ExpertCategory expertCategory : items) {
        	count++;
        	System.out.println(count);
            if (!DictionaryDataUtil.findById(expertCategory.getTypeId()).getCode().equals("ENG_INFO_ID")) {
                Category data = categoryService.findById(expertCategory.getCategoryId());
                List<Category> findPublishTree = categoryService.findPublishTree(expertCategory.getCategoryId(), null);
                if (findPublishTree.size() == 0) {
                    expertItems.add(expertCategory);
                } else if (data != null && data.getCode().length() == 7) {
                    expertItems.add(expertCategory);
                }
            } else {
                Category data = engCategoryService.findById(expertCategory.getCategoryId());
                List<Category> findPublishTree = engCategoryService.findPublishTree(expertCategory.getCategoryId(), null);
                if (findPublishTree.size() == 0) {
                    expertItems.add(expertCategory);
                } else if (data != null && data.getCode().length() == 7) {
                    expertItems.add(expertCategory);
                }
            }
        }
        List < SupplierCateTree > allTreeList = new ArrayList < SupplierCateTree > ();
        for(ExpertCategory item: expertItems) {
            String categoryId = item.getCategoryId();
            SupplierCateTree cateTree = getTreeListByCategoryId(categoryId, flag);
            if(cateTree != null && cateTree.getRootNode() != null) {
                cateTree.setItemsId(categoryId);
                allTreeList.add(cateTree);
            }
        }
        for(SupplierCateTree cate: allTreeList) {
            cate.setRootNode(cate.getRootNode() == null ? "" : cate.getRootNode());
            cate.setFirstNode(cate.getFirstNode() == null ? "" : cate.getFirstNode());
            cate.setSecondNode(cate.getSecondNode() == null ? "" : cate.getSecondNode());
            cate.setThirdNode(cate.getThirdNode() == null ? "" : cate.getThirdNode());
            cate.setFourthNode(cate.getFourthNode() == null ? "" : cate.getFourthNode());
            cate.setRootNode(cate.getRootNode());
        }
        return allTreeList;
	}
	
	
	
	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException {
		/*ExpertField expertField = new ExpertField();
		Field[] fields = expertField.getClass().getDeclaredFields(); 
		for(int i=0; i<fields.length; i++){
			fields[i].setAccessible(true);
			System.out.println(fields[i].get(expertField));
		}
		*/
		ExpertField expertField = new ExpertField();
		Field[] fields = expertField.getClass().getDeclaredFields(); 
		List < String > list = new ArrayList<>();
		for(int i=0; i<fields.length; i++){
			fields[i].setAccessible(true);
			Object str = fields[i].get(expertField);
			System.out.println(str.toString());
		}
		
	}
	
	/**
	 * @Title: auditOpinion
	 * @author XuQing 
	 * @date 2017-4-3 下午12:18:11  
	 * @Description:
	 * @param @param supplierAuditNot      
	 * @return void
	 */
	@RequestMapping(value = "/auditOpinion")
	@ResponseBody
	public void auditOpinion(ExpertAuditOpinion expertAuditOpinion) {
		expertAuditOpinion.setCreatedAt(new Date());
		expertAuditOpinionService.insertSelective(expertAuditOpinion);
	}

    /**
     * 
     * @Title: findAllExpert
     * @author lkzx
     * @date 2016年9月2日 下午5:44:37
     * @Description: TODO 查询所有专家 可以条件查询
     * @param @return
     * @return String
     */
    @RequestMapping("/findAllExpert")
    public String findAllExpert(Expert expert, Integer page, HttpServletRequest request, HttpServletResponse response, String[] ids, String expertId) {
        List < Expert > allExpert = service.selectAllExpert(page == null ? 0 : page, expert);
        for(Expert exp: allExpert) {
            DictionaryData dictionaryData = dictionaryDataServiceI
                .getDictionaryData(exp.getGender());
            exp.setGender(dictionaryData == null ? "" : dictionaryData.getName());
            StringBuffer expertType = new StringBuffer();
            if(exp.getExpertsTypeId() != null) {
                for(String typeId: exp.getExpertsTypeId().split(",")) {
                    DictionaryData data = dictionaryDataServiceI.getDictionaryData(typeId);
                    if(data != null){
                    	if(6 == data.getKind()) {
                            expertType.append(data.getName() + "技术、");
                        } else {
                            expertType.append(data.getName() + "、");
                        }
                    }
                    
                }
                if(expertType.length() > 0){
                	String expertsType = expertType.toString().substring(0, expertType.length() - 1);
                	 exp.setExpertsTypeId(expertsType);
                }
            } else {
                exp.setExpertsTypeId("");
            }
        }
        // 查询数据字典中的专家来源配置数据
        List < DictionaryData > lyTypeList = DictionaryDataUtil.find(12);
        request.setAttribute("lyTypeList", lyTypeList);
        // 查询数据字典中的专家类别数据
        List < DictionaryData > jsTypeList = DictionaryDataUtil.find(6);
        for(DictionaryData data: jsTypeList) {
            data.setName(data.getName() + "技术");
        }
        List < DictionaryData > jjTypeList = DictionaryDataUtil.find(19);
        jsTypeList.addAll(jjTypeList);
        request.setAttribute("expTypeList", jsTypeList);
        request.setAttribute("result", new PageInfo < Expert > (allExpert));
        if(expert.getRelName() != null && !"".equals(expert.getRelName())){
        	expert.setRelName(expert.getRelName().replaceAll("%", ""));
        }
        if(expert.getMobile() != null && !"".equals(expert.getMobile())){
        	expert.setMobile(expert.getMobile().replaceAll("%", ""));
        }
        request.setAttribute("expert", expert);
        
        String expertIds = "";
        Set<String> set=new HashSet<String>();
        if(ids !=null && ids.length > 0){
        	for(int i=0 ; i<ids.length; i++){
        		if(!"".equals(ids[i])){
        			set.add(ids[i]);
        		}
            }
        	if(set.size()>0){
        	  expertIds = set.toString().substring(1, set.toString().length()-1);
        	}
            request.setAttribute("ids", expertIds);
        }
        
        request.setAttribute("expertId", expertId);
        return "ses/ems/expertAudit/expert_list";
    }
	
	/**
	 * @Title: signature
	 * @author XuQing 
	 * @date 2017-4-3 下午12:18:11  
	 * @Description:添加签字人员校验唯一
	 * @param @param signature      
	 * @return void
	 */
	@RequestMapping(value = "/signature", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String signature(String ids, String expertId, HttpServletResponse response) {
		 	List<String> list = new ArrayList<String>();
		 	String[] split = ids.split(",");
		 	list = Arrays.asList(split);
		   //唯一判断
			ExpertSignature expertSignature = new ExpertSignature();
			for(String id : list){
				expertSignature.setExpertId(id);
				List<ExpertSignature> selectByExpertId = expertSignatureService.selectByExpertId(expertSignature);
				if(!selectByExpertId.isEmpty()){
				   Expert expert = expertService.selectByPrimaryKey(id);
				   return expert.getRelName();
				   
				}
			}
		 return "yes";
	}
	
	/**
	 * @Title: signature
	 * @author XuQing 
	 * @date 2017-4-6 下午2:48:52  
	 * @Description:跳转添加签字人员页面
	 * @param @param ids
	 * @param @param model
	 * @param @return      
	 * @return String
	 */
	@RequestMapping(value = "/addSignature")
	public String signature(String ids, Model model) {
		model.addAttribute("ids", ids);
		return "ses/ems/expertAudit/add_auditpersonnel";
	}
	
    
	/**
	 * @Title: saveSignature
	 * @author XuQing 
	 * @date 2017-4-6 下午1:17:20  
	 * @Description:复审表添加签字人员
	 * @param @param purchaseRequiredFormBean
	 * @param @param batchNo
	 * @param @param ids
	 * @param @return      
	 * @return String
	 */
	@RequestMapping(value = "/saveSignature", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String saveSignature(PurchaseRequiredFormBean purchaseRequiredFormBean,String batchNo,String ids) {
		String[] strs = ids.split(",");
		List<ExpertSignature> list = purchaseRequiredFormBean.getExpertSignatureList();
		if(list.size()>0){
			for(String id:strs){
				Expert ep=new Expert();
				ep.setId(id);
				ep.setIsAddExpert(batchNo);
				expertService.updateByPrimaryKeySelective(ep);
				for(ExpertSignature es:list){
					es.setBatch(batchNo);
					es.setExpertId(id);
					es.setCreatedAt(new Date());
					expertSignatureService.add(es);
				}
			}
		}
		return "";
	}
	
	/**
	 * @Title: saveAuditNot
	 * @author XuQing 
	 * @date 2017-5-3 下午7:12:17  
	 * @Description:记录审核未通过的
	 * @param       
	 * @return void
	 */
	@RequestMapping(value="/saveAuditNot")
	public String saveAuditNot(String expertId){
		ExpertAuditNot expertAuditNot = new ExpertAuditNot();
		Expert expert = expertService.selectByPrimaryKey(expertId);
		expertAuditNot.setCreatedAt(new Date());
		expertAuditNot.setExpertId(expertId);
		expertAuditNot.setExpertName(expert.getRelName());
		expertAuditNot.setIdCard(expert.getIdCardNumber());
		expertAuditNotService.insertSelective(expertAuditNot);
		
		return "redirect:list.html";
	}
	
	/**
	 * @Title: temporaryAudit
	 * @date 2017-6-15 下午3:58:28  
	 * @Description:暂存审核
	 * @param @param expertId
	 * @param @return      
	 * @return String
	 */
	@RequestMapping(value ="/temporaryAudit", produces="text/html;charset=UTF-8")
	@ResponseBody
	public String temporaryAudit(String expertId){
		boolean temporaryAudit = expertAuditService.temporaryAudit(expertId);
		if(temporaryAudit){
			return JSON.toJSONString("暂存成功");
		}else{
			return JSON.toJSONString("暂存失败");
		}
	}
}