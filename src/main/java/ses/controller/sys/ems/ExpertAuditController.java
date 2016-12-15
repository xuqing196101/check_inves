package ses.controller.sys.ems;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.model.bms.Area;
import ses.model.bms.DictionaryData;
import ses.model.ems.Expert;
import ses.model.ems.ExpertAudit;
import ses.service.bms.AreaServiceI;
import ses.service.bms.CategoryService;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.bms.TodosService;
import ses.service.ems.ExpertAuditService;
import ses.service.ems.ExpertCategoryService;
import ses.service.ems.ExpertService;
import ses.service.oms.PurchaseOrgnizationServiceI;
import ses.util.DictionaryDataUtil;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import common.constant.Constant;


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
	
	/**
	 * 地区
	 */
	@Autowired
	private AreaServiceI areaService;
	
	@RequestMapping("/list")
	public String expertAuditList(Expert expert, Model model, Integer pageNum, HttpServletRequest request){
		if(expert.getSign() == null){
			Integer signs = (Integer) request.getSession().getAttribute("signs");
			expert.setSign(signs);
			request.getSession().removeAttribute("signs");
		}

		List<Expert> expertList = expertService.findExpertAuditList(expert, pageNum==null?1:pageNum);
		model.addAttribute("result", new PageInfo<Expert>(expertList));
		model.addAttribute("expertList", expertList);
		//初审复审标识（1初审，2复审）
		model.addAttribute("sign", expert.getSign());
		request.getSession().setAttribute("signs",  expert.getSign());
		
		return "ses/ems/expertAudit/list";
	}
	
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
        
        
        
		model.addAttribute("expertId", expertId);
		
		return "ses/ems/expertAudit/basic_info";
	}
	
	@RequestMapping("/auditReasons")
	public void auditReasons(ExpertAudit expertAudit, Model model, HttpServletResponse response){
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
	
	
	@RequestMapping("/experience")
	public String experience(Expert expert, Model model, String expertId){
		
		expert = expertService.selectByPrimaryKey(expertId);
		model.addAttribute("expert", expert);
		model.addAttribute("expertId", expertId);
		return "ses/ems/expertAudit/experience";
	}
	
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
     * @author ShaoYangYang
     * @date 2016年11月9日 下午2:32:38
     * @Description: TODO 封装附件类型
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
	
	@RequestMapping("/reasonsList")
	public String reasonsList(ExpertAudit expertAudit, Model model, String expertId ){

		List<ExpertAudit> reasonsList = expertAuditService.getListByExpertId(expertId);	
		model.addAttribute("reasonsList", reasonsList);
		//查看是否有记录
	    model.addAttribute("num", reasonsList.size());
		
		Expert expert = expertService.selectByPrimaryKey(expertId);
		model.addAttribute("status", expert.getStatus());

		model.addAttribute("expertId", expertId);
		return "ses/ems/expertAudit/reasonsList";
	}
	
	@RequestMapping("/updateStatus")
	public String updateStatus(Expert expert, Model model){
		//提交审核，更新状态
		expertService.updateByPrimaryKeySelective(expert);
		
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
	
	@RequestMapping("/deleteByIds")
	public void deleteByIds(String[] ids, HttpServletResponse response) {
		boolean Whether = expertAuditService.deleteByIds(ids);
		if(Whether){
			String msg = "{\"msg\":\"yes\"}";
			writeJson(response, msg);
		}
		
	}
}
