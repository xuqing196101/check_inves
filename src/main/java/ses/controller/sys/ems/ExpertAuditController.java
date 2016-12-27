package ses.controller.sys.ems;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.model.bms.Area;
import ses.model.bms.DictionaryData;
import ses.model.bms.Todos;
import ses.model.bms.User;
import ses.model.ems.Expert;
import ses.model.ems.ExpertAudit;
import ses.model.ems.ExpertHistory;
import ses.service.bms.AreaServiceI;
import ses.service.bms.CategoryService;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.bms.TodosService;
import ses.service.ems.ExpertAuditService;
import ses.service.ems.ExpertCategoryService;
import ses.service.ems.ExpertService;
import ses.service.ems.ProjectExtractService;
import ses.service.oms.PurchaseOrgnizationServiceI;
import ses.util.DictionaryDataUtil;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import common.constant.Constant;

/**
 * <p>Title:ExpertAuditController </p>
 * <p>Description: 专家审核</p>
 * @author XuQing
 * @date 2016-12-19下午7:33:27
 */
@Controller
@RequestMapping("/expertAudit")
public class ExpertAuditController {
	
	@Autowired
	private ExpertService expertService;
	
	@Autowired
	private DictionaryDataServiceI dictionaryDataServiceI;
	
	@Autowired
	private ExpertAuditService expertAuditService;
	
	@Autowired
    private ExpertCategoryService expertCategoryService;// 专家类别中间表
	
    @Autowired
    private CategoryService categoryService;//是否被抽取查询

    @Autowired
    private PurchaseOrgnizationServiceI purchaseOrgnizationService;// 采购机构管理
    
    @Autowired
    private ExpertService service;// 专家管理

	@Autowired
	private TodosService todosService; //待办
	
	@Autowired
	private ProjectExtractService projectExtractService;
	
	/**
	 * 地区
	 */
	@Autowired
	private AreaServiceI areaService;
	
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
	public String expertAuditList(Expert expert, Model model, Integer pageNum, HttpServletRequest request){
		if(expert.getSign() == null){
			Integer signs = (Integer) request.getSession().getAttribute("signs");
			expert.setSign(signs);
			request.getSession().removeAttribute("signs");
		}
		//是否被抽取
		List<Expert> expertList = expertService.findExpertAuditList(expert, pageNum==null?1:pageNum);
		/*if(expert.getSign() == 2){
			List<Expert> list = new ArrayList<Expert>();
			for(Expert e : expertList){
				List<ProjectExtract>  projectExtractList= projectExtractService.findExtractByExpertId(e.getId());
				if(!projectExtractList.isEmpty()){
					list.add(e);
				}
			}
			model.addAttribute("result", new PageInfo<Expert>(list));
			model.addAttribute("expertList", list);
		}else{
			model.addAttribute("result", new PageInfo<Expert>(expertList));
			model.addAttribute("expertList", expertList);
		}*/
		
		model.addAttribute("result", new PageInfo<Expert>(expertList));
		model.addAttribute("expertList", expertList);
		
		//初审复审标识（1初审，2复审）
		model.addAttribute("sign", expert.getSign());
		request.getSession().setAttribute("signs",  expert.getSign());
		
		//条件查询回显
		String relName = expert.getRelName();
		if(relName != null){
			relName  = relName.replaceAll("%", "");
		}
		String status = expert.getStatus();
		model.addAttribute("relName", relName);
		model.addAttribute("state", status);
		
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
	public String basicInfo(Expert expert, Model model, Integer pageNum, String expertId){
		expert = expertService.selectByPrimaryKey(expertId);
		model.addAttribute("expert", expert);
		
		//专家来源
		if(expert.getExpertsFrom() != null){
			DictionaryData expertsFrom = dictionaryDataServiceI.getDictionaryData(expert.getExpertsFrom());
			model.addAttribute("expertsFrom", expertsFrom.getName());
		}
		//性别
		if(expert.getGender() != null){
			DictionaryData gender = dictionaryDataServiceI.getDictionaryData(expert.getGender());
			model.addAttribute("gender", gender.getName());
		}
		//政治面貌
		if(expert.getPoliticsStatus() != null){
			DictionaryData politicsStatus = dictionaryDataServiceI.getDictionaryData(expert.getPoliticsStatus());
			model.addAttribute("politicsStatus", politicsStatus.getName());	
		}
		//军队人员身份证件类型
		if(expert.getIdType() != null){
			DictionaryData idType = dictionaryDataServiceI.getDictionaryData(expert.getIdType());
			model.addAttribute("idType", idType.getName());
		}
		//最高学历
		if(expert.getHightEducation() != null){
			DictionaryData hightEducation = dictionaryDataServiceI.getDictionaryData(expert.getHightEducation());
			model.addAttribute("hightEducation", hightEducation.getName());
		}
		//最高学位
		if(expert.getDegree() != null){
			DictionaryData degree = dictionaryDataServiceI.getDictionaryData(expert.getDegree());
			model.addAttribute("degree", degree.getName());
		}
		// 货物类型数据字典
        List<DictionaryData> hwList = DictionaryDataUtil.find(8);
        model.addAttribute("hwList", hwList);
        
        // 经济类型数据字典
        List<DictionaryData> jjTypeList = DictionaryDataUtil.find(19);
        model.addAttribute("jjList", jjTypeList);
        
       // 产品类型数据字典
        List<DictionaryData> spList = DictionaryDataUtil.find(6);
        model.addAttribute("spList", spList);
        
        
        //地区查询
		List<Area> privnce = areaService.findRootArea();
		model.addAttribute("privnce", privnce);
		
		Area area = areaService.listById(expert.getAddress());
		String sonName = area.getName();
		model.addAttribute("sonName", sonName);
		for(int i=0; i<privnce.size(); i++){
			if(area.getParentId().equals(privnce.get(i).getId())){
				String parentName = privnce.get(i).getName();
				model.addAttribute("parentName", parentName);
			}
		}
        
		
		// 专家系统key
        Integer expertKey = Constant.EXPERT_SYS_KEY;
        Map<String, Object> typeMap = getTypeId();
        // typrId集合
        model.addAttribute("typeMap", typeMap);
        // 业务id就是专家id
        model.addAttribute("sysId", expertId);
        // Constant.EXPERT_SYS_VALUE;
        model.addAttribute("expertKey", expertKey);
        
        
        
		model.addAttribute("expertId", expertId);
		if ("3".equals(expert.getStatus())) {
		    //  判断当前状态如果为退回修改则比较两次的信息
		    // 判断有没有进行修改
		    ExpertHistory oldExpert = service.selectOldExpertById(expertId);
		    Map<String, Object> compareMap = compareExpert(oldExpert, (ExpertHistory)expert);
		    // 如果isEdit==1代表没有进行任何修改就进行了二次提交
		    if (compareMap.isEmpty()) {
		        // 没有修改
		        model.addAttribute("isEdit", "0");
		    } else {
		        // 有修改
		        model.addAttribute("isEdit", "1");
		    }
		    Set<String> keySet = compareMap.keySet();
		    List<String> editFields = new ArrayList<String>();
		    for (String method : keySet) {
		        editFields.add(method);
		    }
		    model.addAttribute("editFields", editFields);
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
    @RequestMapping(value = "/getFieldContent",produces="text/html;charset=UTF-8")
    public String getFieldContent(String field, String type, String expertId) throws ParseException{
        StringBuffer content = new StringBuffer();
        Expert expert = service.selectByPrimaryKey(expertId);
        ExpertHistory oldExpert = service.selectOldExpertById(expertId);
        oldExpert.setTimeToWork(new SimpleDateFormat("yyyy-MM").parse(new SimpleDateFormat("yyyy-MM").format(oldExpert.getTimeToWork())));
        Map<String, Object> compareMap = compareExpert(oldExpert, (ExpertHistory)expert);
        String value = (String) compareMap.get(field);
        if ("0".equals(type)) {
            // 不需要数据字典查询的
            content.append(value);
        } else if ("1".equals(type)) {
            // 需要从数据字典查询的
            if (field.indexOf(",") == -1) {
                // 不需要拼接逗号的
                DictionaryData dictionaryData = dictionaryDataServiceI.getDictionaryData(value);
                if (dictionaryData != null) {
                    content.append(dictionaryData.getName());
                }
            } else {
                // 需要逗号拼接的
                StringBuffer temp = new StringBuffer();
                String[] ids = value.split(",");
                for (String id : ids) {
                    DictionaryData dictionaryData = dictionaryDataServiceI.getDictionaryData(id);
                    if (dictionaryData != null) {
                        temp.append(dictionaryData.getName() + "、");
                    }
                }
                content.append(temp.toString().substring(0, temp.length() - 1));
            }
        } else if ("2".equals(type)) {
            SimpleDateFormat sdf1 = new SimpleDateFormat ("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
            Date date=sdf1.parse(value);
            content.append(new SimpleDateFormat("yyyy-MM-dd").format(date));
        } else if ("3".equals(type)) {
            // Wed Feb 01 00:00:00 CST 2017         String
            SimpleDateFormat sdf1 = new SimpleDateFormat ("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
            Date date=sdf1.parse(value);
            content.append(new SimpleDateFormat("yyyy-MM").format(date));
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
    private Map<String, Object> compareExpert(ExpertHistory oldExpert, ExpertHistory newExpert){
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            Class<ExpertHistory> expertClass = ExpertHistory.class;
            Field[] fieldList = expertClass.getDeclaredFields();
            for (Field field : fieldList) {
                PropertyDescriptor pd = new PropertyDescriptor(field.getName(), expertClass);
                Method getMethod = pd.getReadMethod();
                Object o1 = getMethod.invoke(oldExpert);
                Object o2 = getMethod.invoke(newExpert);
                if (o1 != null && o2!= null && !o1.toString().equals(o2.toString())) {
                    map.put(getMethod.getName(), o1.toString());
                }
                if ((o1 == null && o2 != null) || o1 != null && o2 == null) {
                    map.put(getMethod.getName(), o1.toString());
                }
            }
        } catch (Exception e) {
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
	public void auditReasons(ExpertAudit expertAudit, Model model, HttpServletResponse response, HttpServletRequest request){
			User user=(User) request.getSession().getAttribute("loginUser");
			if(user != null){
				expertAudit.setAuditUserId(user.getId());
				expertAudit.setAuditUserName(user.getRelName());
			}
			expertAudit.setAuditAt(new Date());
			
		//唯一验证
		List<ExpertAudit> reasonsList = expertAuditService.getListByExpertId(expertAudit.getExpertId());	
		boolean same= true;
		for(int i=0; i<reasonsList.size(); i++){
			if(reasonsList.get(i).getAuditField().equals(expertAudit.getAuditField()) && reasonsList.get(i).getAuditContent().equals(expertAudit.getAuditContent()) && reasonsList.get(i).getSuggestType().equals(expertAudit.getSuggestType())){
				same = false;
				break;
			}
		}
		if(same){
			expertAuditService.add(expertAudit);
		}else{
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
	public String experience(Expert expert, Model model, String expertId){
		
		expert = expertService.selectByPrimaryKey(expertId);
		model.addAttribute("expert", expert);
		model.addAttribute("expertId", expertId);
		// 判断有没有进行修改
        ExpertHistory oldExpert = service.selectOldExpertById(expertId);
        Map<String, Object> compareMap = compareExpert(oldExpert, (ExpertHistory)expert);
        // 如果isEdit==1代表没有进行任何修改就进行了二次提交
        if (compareMap.isEmpty()) {
            // 没有修改
            model.addAttribute("isEdit", "0");
        } else {
            // 有修改
            model.addAttribute("isEdit", "1");
        }
        Set<String> keySet = compareMap.keySet();
        List<String> editFields = new ArrayList<String>();
        for (String method : keySet) {
            editFields.add(method);
        }
        model.addAttribute("editFields", editFields);
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
	public String product(Expert expert, Model model, String expertId){
		
		expert = expertService.selectByPrimaryKey(expertId);
		
		List<DictionaryData> allCategoryList = new ArrayList<DictionaryData>();
        // 获取专家类别
        List<String> allTypeId = new ArrayList<String>();
        for (String id : expert.getExpertsTypeId().split(",")) {
            allTypeId.add(id);
        }
        a:for (int i = 0; i < allTypeId.size(); i++ ) {
            DictionaryData dictionaryData = dictionaryDataServiceI.getDictionaryData(allTypeId.get(i));
            if (dictionaryData != null && dictionaryData.getName().contains("经济")) {
                allTypeId.remove(i);
                continue a;
            };
            allCategoryList.add(dictionaryData);
        }
        model.addAttribute("allCategoryList", allCategoryList);
        model.addAttribute("expertId", expertId);
		return "ses/ems/expertAudit/product";
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
	public String expertFile(Expert expert, Model model, String expertId){
		
		// 专家系统key
        Integer expertKey = Constant.EXPERT_SYS_KEY;
        model.addAttribute("expertKey", expertKey);
        // 获取各个附件类型id集合
        Map<String, Object> typeMap = getTypeId();
        // typrId集合
        model.addAttribute("typeMap", typeMap);
		
		expert = expertService.selectByPrimaryKey(expertId);
		model.addAttribute("expert", expert);
		model.addAttribute("expertId", expertId);
		
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
    private Map<String, Object> getTypeId() {
        DictionaryData dd = new DictionaryData();
        Map<String, Object> typeMap = new HashMap<>();
        for (int i = 0; i < 8; i++) {
            if (i == 0) {
                // 军官证件
                dd.setCode("EXPERT_IDNUMBER");
                List<DictionaryData> find = dictionaryDataServiceI.find(dd);
                typeMap.put("EXPERT_IDNUMBER_TYPEID", find.get(0).getId());
            }
            if (i == 1) {
                // 职称证书
                dd.setCode("EXPERT_TITLE");
                List<DictionaryData> find = dictionaryDataServiceI.find(dd);
                typeMap.put("EXPERT_TITLE_TYPEID", find.get(0).getId());
            }
            if (i == 2) {
                // 申请表
                dd.setCode("EXPERT_APPLICATION");
                List<DictionaryData> find = dictionaryDataServiceI.find(dd);
                typeMap.put("EXPERT_APPLICATION_TYPEID", find.get(0).getId());
            }
            if (i == 3) {
                // 学历证书
                dd.setCode("EXPERT_ACADEMIC");
                List<DictionaryData> find = dictionaryDataServiceI.find(dd);
                typeMap.put("EXPERT_ACADEMIC_TYPEID", find.get(0).getId());
            }
            if (i == 4) {
                // 学位证书
                dd.setCode("EXPERT_DEGREE");
                List<DictionaryData> find = dictionaryDataServiceI.find(dd);
                typeMap.put("EXPERT_DEGREE_TYPEID", find.get(0).getId());
            }
            if (i == 5) {
                // 个人照片
                dd.setCode("EXPERT_PHOTO");
                List<DictionaryData> find = dictionaryDataServiceI.find(dd);
                typeMap.put("EXPERT_PHOTO_TYPEID", find.get(0).getId());
            }
            if (i == 6) {
                // 合同书
                dd.setCode("EXPERT_CONTRACT");
                List<DictionaryData> find = dictionaryDataServiceI.find(dd);
                typeMap.put("EXPERT_CONTRACT_TYPEID", find.get(0).getId());
            }
            if (i == 7) {
                // 军官证件
                dd.setCode("EXPERT_IDCARDNUMBER");
                List<DictionaryData> find = dictionaryDataServiceI.find(dd);
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
	public String expertType(ExpertAudit expertAudit, Model model, String expertId){
    	// 产品类型数据字典
        List<DictionaryData> spList = DictionaryDataUtil.find(6);
        model.addAttribute("spList", spList);
        // 经济类型数据字典
        List<DictionaryData> jjTypeList = DictionaryDataUtil.find(19);
        model.addAttribute("jjList", jjTypeList);
        // 货物类型数据字典
        List<DictionaryData> hwList = DictionaryDataUtil.find(8);
        model.addAttribute("hwList", hwList);
        
        Expert expert = expertService.selectByPrimaryKey(expertId);
		model.addAttribute("expert", expert);
        
		return "ses/ems/expertAudit/expertType";
	}
    
    /**
     * @Title: reasonsList
     * @author XuQing 
     * @date 2016-12-19 下午7:38:05  
     * @Description:问题汇总
     * @param @param expertAudit
     * @param @param model
     * @param @param expertId
     * @param @return      
     * @return String
     */
	@RequestMapping("/reasonsList")
	public String reasonsList(ExpertAudit expertAudit, Model model, String expertId){

		List<ExpertAudit> reasonsList = expertAuditService.getListByExpertId(expertId);	
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
    public String updateStatus(Expert expert, Model model, HttpServletRequest request){
        // 如果是退回修改就保存历史信息
        if ("3".equals(expert.getStatus())) {
            service.deleteExpertHistory(expert.getId());
            Expert exp = service.selectByPrimaryKey(expert.getId());
            service.insertExpertHistory(exp);
            expert.setIsSubmit("0");
        }
        //提交审核，更新状态
        expertService.updateByPrimaryKeySelective(expert);
        
        String status = expert.getStatus();
		String expertId = expert.getId();
		expert= expertService.selectByPrimaryKey(expertId);
		Todos todos = new Todos();
		String expertName = expert.getRelName();
		User user=(User) request.getSession().getAttribute("loginUser");
		
		/**
		 * 更新待办（已完成）
		 */
		if(status.equals("1") || status.equals("2") || status.equals("3") || status.equals("4") || status.equals("5")){
			todosService.updateIsFinish("expertAudit/basicInfo.html?expertId=" + expertId);
			
		}
		

		/**
		 * 待办
		 */
		/*if(status.equals("1")){
			//待初审已完成
			todosService.updateIsFinish("expertAudit/basicInfo.html?expertId=" + expertId);
			*//**
			 * 推送
			 *//*
		    todos.setCreatedAt(new Date());
		    todos.setIsDeleted((short)0);
		    todos.setIsFinish((short)0);
		    //待办名称
		    todos.setName(expertName+"专家复审");
		    //todos.setReceiverId();
		    //接受人id
		    todos.setOrgId(record.getPurchaseDepId());
		    //权限id
		    PropertiesUtil config = new PropertiesUtil("config.properties");
		    todos.setPowerId(config.getString("zjdb"));
		    //发送人id
		    todos.setSenderId(user.getId());
		    //类型
		    todos.setUndoType((short)2);
		    //发送人姓名
		    todos.setSenderName(expert.getRelName());
		    //审核地址
		    todos.setUrl("expertAudit/basicInfo.html?expertId=" + expertId);
		    todosService.insert(todos );
		}*/
        
        return "redirect:list.html";
    }
	
	public void writeJson(HttpServletResponse response, Object object) {
		try {
			String json = JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss");
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write(json);
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
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
		if(Whether){
			String msg = "{\"msg\":\"yes\"}";
			writeJson(response, msg);
		}
		
	}
}
