package ses.controller.sys.ems;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import common.annotation.CurrentUser;
import common.constant.Constant;
import common.constant.StaticVariables;
import ses.model.bms.Area;
import ses.model.bms.Category;
import ses.model.bms.DictionaryData;
import ses.model.bms.User;
import ses.model.ems.Expert;
import ses.model.ems.ExpertAttachment;
import ses.model.ems.ExpertAudit;
import ses.model.ems.ExpertAuditFileModify;
import ses.model.ems.ExpertAuditOpinion;
import ses.model.ems.ExpertCategory;
import ses.model.ems.ExpertFinalInspect;
import ses.model.ems.ExpertTitle;
import ses.model.oms.Orgnization;
import ses.model.sms.SupplierCateTree;
import ses.service.bms.AreaServiceI;
import ses.service.bms.CategoryService;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.bms.EngCategoryService;
import ses.service.ems.ExpertAttachmentService;
import ses.service.ems.ExpertAuditOpinionService;
import ses.service.ems.ExpertAuditService;
import ses.service.ems.ExpertCategoryService;
import ses.service.ems.ExpertFinalInspectService;
import ses.service.ems.ExpertService;
import ses.service.ems.ExpertTitleService;
import ses.util.DictionaryDataUtil;
import ses.util.WordUtil;
import synchro.outer.back.service.org.OrgService;
/*
 * 专家复查
 * */
@Controller
@RequestMapping("finalInspect")
public class ExpertFinalInspectController {
	@Autowired
	private ExpertFinalInspectService finalInspectService;
	@Autowired
	private DictionaryDataServiceI dictionaryDataServiceI;
	@Autowired
	private AreaServiceI areaService;
	@Autowired
	private ExpertService expertService;
	@Autowired
	private ExpertAuditService expertAuditService;
	@Autowired
	private ExpertTitleService expertTitleService;
	@Autowired
	private ExpertCategoryService expertCategoryService; // 专家类别中间表
	@Autowired
	private CategoryService categoryService; 
	@Autowired
	private EngCategoryService engCategoryService; //工程专业信息
	@Autowired
	private ExpertAttachmentService expertAttachmentService;
	@Autowired
	private ExpertAuditOpinionService expertAuditOpinionService;
	@Autowired
	private OrgService orgService;
	/**
	 * 复查列表
	 **/
	@RequestMapping("list")
	public String findExpertFinalInspectList(@CurrentUser User user,Expert expert , Model model, Integer pageNum, HttpServletRequest request) {
		if(pageNum == null) {
			pageNum = StaticVariables.DEFAULT_PAGE;
		}
		if("1".equals(user.getTypeName())){
			String orgId=user.getOrg()==null?user.getOrgId():user.getOrg().getId();
			expert.setFinalInspectPeople(orgId);
			List<Expert> expertList = finalInspectService.findExpertFinalInspectList(expert,pageNum);
			PageInfo< Expert > result = new PageInfo < Expert > (expertList);
			 for(Expert exp: expertList) {
		            DictionaryData dictionaryData = dictionaryDataServiceI.getDictionaryData(exp.getGender());
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
		            
		          //专家来源
		      		if(exp.getExpertsFrom() != null) {
		      			DictionaryData expertsFrom = dictionaryDataServiceI.getDictionaryData(exp.getExpertsFrom());
		      			exp.setExpertsFrom(expertsFrom.getName());
		      		}
		        }
			model.addAttribute("result",result);
			model.addAttribute("sign","3");
			model.addAttribute("expertList", expertList);
		}
		return "ses/ems/expertFinalInspect/list";
	}
	/**
	 * @Title: basicInfo
	 * @author  
	 * @date 2016-12-19 下午7:35:06  
	 * @Description:基本信息
	 * @param @param expert
	 * @param @param model
	 * @param @param pageNum
	 * @param @param expertId
	 * @param @return      
	 * @return String
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping("/basicInfo")
	public String basicInfo(@CurrentUser User user,HttpServletRequest request,String notCount,Expert expert, Model model, String expertId, Integer sign, String isCheck){
		expert = expertService.selectByPrimaryKey(expertId);
		String see = request.getParameter("see");
		if(see!=null){
			String orgId=user.getOrg()==null?user.getOrgId():user.getOrg().getId();
			Expert e = new Expert();
			e.setId(expertId);
			e.setFinalInspectPeople(orgId);
			//设置复审中状态
			e.setAuditTemporary(3);
			// 设置修改时间
			e.setUpdatedAt(new Date());
			expertService.updateByPrimaryKeySelective(e);
		}
		model.addAttribute("isCheck", isCheck == null? "no" : isCheck);
		if(notCount==null||"".equals(notCount)){
			notCount="0".equals(expert.getFinalInspectCount())?"1":expert.getFinalInspectCount();
		}
		model.addAttribute("notCount", notCount);
		if("7".equals(expert.getStatus())||"8".equals(expert.getStatus())){
			if("0".equals(expert.getFinalInspectCount())){
				expert.setFinalInspectCount("1");
			}
		}
		model.addAttribute("expert", expert);
		model.addAttribute("over", request.getParameter("over"));
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
		return "ses/ems/expertFinalInspect/basic_info";
	}
	/**
	 * 
	 * @Title: getTypeId
	 * @author 
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
	 * @author  
	 * @date 2016-12-19 下午7:37:48  
	 * @Description:专家类型
	 * @param @param expertAudit
	 * @param @param model
	 * @param @param expertId
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/expertType")
	public String expertType(ExpertAudit expertAudit,String over,String notCount, Model model, String expertId, Integer sign, String batchId, String isReviewRevision, String isCheck) {
		//初审复审标识（1初审，3复查，2复审）
		model.addAttribute("sign", sign);
		model.addAttribute("notCount", notCount);
		model.addAttribute("isCheck", isCheck == null? "no" : isCheck);
		Expert expert = expertService.selectByPrimaryKey(expertId);
		if("7".equals(expert.getStatus())||"8".equals(expert.getStatus())){
			if("0".equals(expert.getFinalInspectCount())){
				expert.setFinalInspectCount("1");
			}
		}
		model.addAttribute("expert", expert);
		model.addAttribute("over", over);
		
		String type = expert.getExpertsTypeId();
		model.addAttribute("expertType", type);
		//现在勾选的类型
		String[] types = type.split(",");
		for(String s : types){
			if(s!=null && !"".equals(s)){
				DictionaryData data = DictionaryDataUtil.findById(s);
				if("PROJECT".equals(data.getCode())||"GOODS_PROJECT".equals(data.getCode())){
					model.addAttribute("isShow", "1");
				}
			}
		}
		// 产品类型数据字典
		List < DictionaryData > spList = new ArrayList< DictionaryData >();
		// 经济类型数据字典
		List < DictionaryData > jjTypeList = new ArrayList< DictionaryData >();
		if(!"".equals(type)){
			for (DictionaryData dictionaryData : DictionaryDataUtil.find(6)) {
				if(type.indexOf(dictionaryData.getId())>=0){
					spList.add(dictionaryData);
				}
			}
			for (DictionaryData dictionaryData : DictionaryDataUtil.find(19)) {
				if(type.indexOf(dictionaryData.getId())>=0){
					jjTypeList.add(dictionaryData);
				}
			}
			
		}
		model.addAttribute("spList", spList);
		
		model.addAttribute("jjList", jjTypeList);
		// 货物类型数据字典
		List < DictionaryData > hwList = DictionaryDataUtil.find(8);
		model.addAttribute("hwList", hwList);
		
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
		return "ses/ems/expertFinalInspect/expertType";
	}
	/**
	 * @Title: product
	 * @author  
	 * @date 2016-12-19 下午7:36:47  
	 * @Description:产品目录
	 * @param @param expert
	 * @param @param model
	 * @param @param expertId
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/product")
	public String product(Expert expert,String over,String notCount, Model model, String expertId, Integer sign, String batchId, String isReviewRevision, String isCheck) {
		//初审复审标识（1初审，3复查，2复审）
		model.addAttribute("sign", sign);
		model.addAttribute("over", over);
		model.addAttribute("batchId", batchId);
		model.addAttribute("notCount", notCount);
		model.addAttribute("isCheck", isCheck == null? "no" : isCheck);
		expert = expertService.selectByPrimaryKey(expertId);
		model.addAttribute("status", expert.getStatus());

		List < DictionaryData > allCategoryList = new ArrayList < DictionaryData > ();

        // 获取专家类别
        List < String > allTypeId = new ArrayList < String > ();
        if(expert.getExpertsTypeId() !=null && !"".equals(expert.getExpertsTypeId())){
        	for(String id: expert.getExpertsTypeId().split(",")) {
                allTypeId.add(id);
            }
        }

        for(int i = 0; i < allTypeId.size(); i++) {
            DictionaryData dictionaryData = dictionaryDataServiceI.getDictionaryData(allTypeId.get(i));
            allCategoryList.add(dictionaryData);
        }
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
		if("7".equals(expert.getStatus())||"8".equals(expert.getStatus())){
			if("0".equals(expert.getFinalInspectCount())){
				expert.setFinalInspectCount("1");
			}
		}
		model.addAttribute("expert", expert);
		
		return "ses/ems/expertFinalInspect/product";
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
	public String getCategories(String expertId, String typeId, Model model, Integer pageNum, Integer sign, String batchId) {
		model.addAttribute("sign", sign);
		model.addAttribute("batchId", batchId);
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
        List<ExpertCategory> items = expertCategoryService.getListByExpertId(expertId, typeId);
        List<ExpertCategory> expertItems = new ArrayList<ExpertCategory>();
        ExpertAudit a = new ExpertAudit();
        a.setExpertId(expertId);
    	a.setAuditFalg(2);
        a.setSuggestType("six");
        a.setAuditStatus("6");
        int count=0;
        if(items != null && !items.isEmpty()){
            for (ExpertCategory expertCategory : items) {
            	 a.setAuditFieldId(expertCategory.getCategoryId());
                 //初审理由
             	ExpertAudit firstAuditInfo = expertAuditService.findAuditByExpertId(a);
             	if(firstAuditInfo==null){
             		count++;
                    if (!DictionaryDataUtil.findById(expertCategory.getTypeId()).getCode().equals("ENG_INFO_ID")) {
                        Category data = categoryService.findById(expertCategory.getCategoryId());
                        if(data.getCode().length()<9){
                        	 List<Category> findPublishTree = categoryService.findPublishTree(expertCategory.getCategoryId(), null);
                             if (findPublishTree.size() == 0) {
                                 expertItems.add(expertCategory);
                             } else if (data != null && data.getCode().length() == 7) {
                                 expertItems.add(expertCategory);
                             }
                        }
                    } else {
                        Category data = engCategoryService.findById(expertCategory.getCategoryId());
                        if(data.getCode().length()<9){
                        	  List<Category> findPublishTree = engCategoryService.findPublishTree(expertCategory.getCategoryId(), null);
                              if (findPublishTree.size() == 0) {
                                  expertItems.add(expertCategory);
                              } else if (data != null && data.getCode().length() == 7) {
                                  expertItems.add(expertCategory);
                              }
                        }
                    }
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
        
        ExpertAudit expertAudit = new ExpertAudit();
        for(SupplierCateTree cate: allTreeList) {
            cate.setRootNode(cate.getRootNode() == null ? "" : cate.getRootNode());
            cate.setFirstNode(cate.getFirstNode() == null ? "" : cate.getFirstNode());
            cate.setSecondNode(cate.getSecondNode() == null ? "" : cate.getSecondNode());
            cate.setThirdNode(cate.getThirdNode() == null ? "" : cate.getThirdNode());
            cate.setFourthNode(cate.getFourthNode() == null ? "" : cate.getFourthNode());
            cate.setRootNode(cate.getRootNode());
            
            if(sign != null && sign==2){
            	expertAudit.setAuditStatus("6");
            	expertAudit.setExpertId(expertId);
            	expertAudit.setAuditFalg(1);
            	
                expertAudit.setSuggestType("six");
                expertAudit.setAuditFieldId(cate.getItemsId());
                ExpertAudit findAuditByExpertId = expertAuditService.findAuditByExpertId(expertAudit);
                if(findAuditByExpertId !=null && findAuditByExpertId.getAuditReason() !=null){
                	cate.setAuditReason(findAuditByExpertId.getAuditReason());
                }
                
               //类型不通过的，下面品目全部不通过
	            expertAudit.setSuggestType("seven");
	            expertAudit.setType("1");
	            expertAudit.setAuditFieldId(typeId);
	            ExpertAudit itemsTypeNoPass = expertAuditService.findAuditByExpertId(expertAudit);
	            if(itemsTypeNoPass !=null && itemsTypeNoPass.getAuditReason() !=null){
                	cate.setAuditReason(itemsTypeNoPass.getAuditReason());
                }
            }
        }
        model.addAttribute("expertId", expertId);
        model.addAttribute("typeId", typeId);
        /*model.addAttribute("result", new PageInfo < > (items));*/
        model.addAttribute("itemsList", allTreeList);
        return "ses/ems/expertFinalInspect/ajax_items";
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
            	cateTree.setRootNodeCode(rootNode.getCode());
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
		 * @Title: expertFile
		 * @author  
		 * @date 2016-12-19 下午7:37:01  
		 * @Description:附件信息
		 * @param @param expert
		 * @param @param model
		 * @param @param expertId
		 * @param @return      
		 * @return String
		 */
		@RequestMapping("/expertFile")
		public String expertFile(Expert expert,String over,String notCount, Model model, String expertId, Integer sign, String isReviewRevision, String isCheck) {
			//初审复审标识（1初审，3复查，2复审）
			model.addAttribute("sign", sign);
			model.addAttribute("over", over);
			model.addAttribute("notCount", notCount);
			model.addAttribute("isCheck", isCheck == null? "no" : isCheck);
			// 专家系统key
			Integer expertKey = Constant.EXPERT_SYS_KEY;
			model.addAttribute("expertKey", expertKey);
			// 获取各个附件类型id集合
			Map < String, Object > typeMap = getTypeId();
			// typrId集合
			model.addAttribute("typeMap", typeMap);

			expert = expertService.selectByPrimaryKey(expertId);
			if("7".equals(expert.getStatus())||"8".equals(expert.getStatus())){
				if("0".equals(expert.getFinalInspectCount())){
					expert.setFinalInspectCount("1");
				}
			}
			model.addAttribute("expert", expert);
			model.addAttribute("expertId", expertId);
			model.addAttribute("status", expert.getStatus());
			return "ses/ems/expertFinalInspect/expertFile";
		}
		@RequestMapping("/expertAttachment")
		public String expertAttachmentList(String expertId, Integer sign, Model model,String finalInspectNumber,String over) {
			//初审复审标识（1初审，3复查，2复审）
			model.addAttribute("sign", sign);
			Expert expert = expertService.selectByPrimaryKey(expertId);
			if("7".equals(expert.getStatus())||"8".equals(expert.getStatus())){
				if("0".equals(expert.getFinalInspectCount())){
					expert.setFinalInspectCount("1");
				}
			}
			model.addAttribute("expert", expert);
			model.addAttribute("over", over);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("businessId", expertId);
			map.put("isDeleted", 0);
			String gpId = DictionaryDataUtil.getId("GOODS_PROJECT");
 			String pId = DictionaryDataUtil.getId("PROJECT");
 			List<ExpertTitle> proList=expertTitleService.queryByUserId(expert.getId(),pId);
 			List<ExpertTitle> ecoList=expertTitleService.queryByUserId(expert.getId(),gpId);
			List<ExpertAttachment> list = expertAttachmentService.selectListByMap(map);
			if(proList.size()>0){
				for (ExpertTitle expertTitle : proList) {
					ExpertAttachment e = new ExpertAttachment();
					e.setId(expertTitle.getId());
					e.setTypeId("9");
					e.setBusinessId(expertTitle.getId());
					e.setName(expertTitle.getQualifcationTitle());
					list.add(e);
				}
			}
			if(ecoList.size()>0){
				for (ExpertTitle expertTitle : ecoList) {
					ExpertAttachment e = new ExpertAttachment();
					e.setId(expertTitle.getId());
					e.setTypeId("9");
					e.setBusinessId(expertTitle.getId());
					e.setName(expertTitle.getQualifcationTitle());
					list.add(e);
				}
			}
			List<ExpertAttachment> expertAttachmentList=new ArrayList<ExpertAttachment>();
			for (ExpertAttachment e : list) {
				ExpertFinalInspect expertFinalInspect = new ExpertFinalInspect();
				expertFinalInspect.setExpertId(expertId);
				expertFinalInspect.setFileId(e.getId());
				expertFinalInspect.setFinalInspectNumber(finalInspectNumber);
				ExpertFinalInspect inspect = finalInspectService.getExpertFinalInspect(expertFinalInspect);
				if(inspect!=null){
					e.setStatus(inspect.getStatus());
					e.setReason(inspect.getReason());
				}
				String name="";
				if("50".equals(e.getTypeId())){
					name="近期免冠彩色证件照";
				}else if("1".equals(e.getTypeId())){
					name="缴纳社会保险证明";
				}else if("2".equals(e.getTypeId())){
					name="退休证书或退休证明";
				}else if("3".equals(e.getTypeId())){
					name="身份证扫描件";
				}else if("12".equals(e.getTypeId())){
					name="军队人员身份证件";
				}else if("4".equals(e.getTypeId())){
					name="专业技术职称证书";
				}else if("5".equals(e.getTypeId())){//毕业证书
					continue;
					//name="毕业证书";
				}else if("6".equals(e.getTypeId())){
					name="学位证书";
				}else if("8".equals(e.getTypeId())){
					name="推荐信";
				}else if("7".equals(e.getTypeId())){
					name="获奖证书";
				}else if("9".equals(e.getTypeId())){
					name=e.getName()+"(执业资格证书)";
				}else if("13".equals(e.getTypeId()) || "14".equals(e.getTypeId())){
					continue;
				}
				e.setName(name);
				expertAttachmentList.add(e);
			}
			// 专家系统key
		    Integer expertKey = Constant.EXPERT_SYS_KEY;
			model.addAttribute("expertKey", expertKey);
			model.addAttribute("list", expertAttachmentList);
			ExpertAuditOpinion find = new ExpertAuditOpinion();
			find.setExpertId(expertId);
			find.setFlagTime(2);
			List<ExpertAuditOpinion> auditOpinionList = expertAuditOpinionService.selectAllByExpertList(find);
			if(auditOpinionList.size()>0){
				if(auditOpinionList.size()>(Integer.valueOf(finalInspectNumber)-1)){
					ExpertAuditOpinion auditOpinion = auditOpinionList.get(Integer.valueOf(finalInspectNumber)-1);
					if(auditOpinion.getOpinion()!=null){
						String[] strings = auditOpinion.getOpinion().split("。");
						if(strings.length<2){
							auditOpinion.setOpinion(null);
						}else{
							auditOpinion.setOpinion(strings[1]);
						}
						
					}
					model.addAttribute("auditOpinion", auditOpinion);
				}else{
					model.addAttribute("auditOpinion", new ExpertAuditOpinion());
				}
			}else{
				model.addAttribute("auditOpinion", new ExpertAuditOpinion());
			}
			ExpertFinalInspect e = new ExpertFinalInspect();
			e.setExpertId(expertId);
			e.setStatus("2");
			e.setFinalInspectNumber(finalInspectNumber);
			List<ExpertFinalInspect> inspectList = finalInspectService.findExpertFinalInspectList(e);
			if(inspectList.size()>0){
				model.addAttribute("qualified",false);
			}else{
				model.addAttribute("qualified",true);
			}
			//获取专家复查批准表附件类型ID
			String typeId = DictionaryDataUtil.getId("EXPERT_PZFCB");
			model.addAttribute("typeId", typeId);
			model.addAttribute("notCount", finalInspectNumber);
			return "ses/ems/expertFinalInspect/expertAttachment";
		}
	@RequestMapping("updateExpertFinalInspect")
	@ResponseBody
	public boolean updateExpertFinalInspect(ExpertFinalInspect e) {
		ExpertFinalInspect find = new ExpertFinalInspect();
		find.setExpertId(e.getExpertId());
		find.setFileId(e.getFileId());
		find.setFinalInspectNumber(e.getFinalInspectNumber());
		ExpertFinalInspect expertFinalInspect = finalInspectService.getExpertFinalInspect(find);
		if(expertFinalInspect!=null){
			e.setId(expertFinalInspect.getId());
			e.setUpdateAt(new Date());
			finalInspectService.updateExpertFinalInspect(e);
		}else{
			e.setId(UUID.randomUUID().toString().replaceAll("-", ""));
			e.setCreateAt(new Date());
			e.setUpdateAt(new Date());
			finalInspectService.insertExpertFinalInspect(e);
		}
		return true;
	}
	@RequestMapping("updateExpertFinalInspectStatusIsNull")
	@ResponseBody
	public boolean updateExpertFinalInspectStatusIsNull(ExpertFinalInspect e) {
		ExpertFinalInspect find = new ExpertFinalInspect();
		find.setExpertId(e.getExpertId());
		find.setFileId(e.getFileId());
		find.setFinalInspectNumber(e.getFinalInspectNumber());
		ExpertFinalInspect expertFinalInspect = finalInspectService.getExpertFinalInspect(find);
		if(expertFinalInspect!=null){
			finalInspectService.updateExpertFinalInspectStatusIsNull(expertFinalInspect);
		}
		return true;
	}
	/**
	 *添加复查意见及结论
	 */
	@RequestMapping(value = "/auditOpinion")
	@ResponseBody
	public void auditOpinion(ExpertAuditOpinion expertAuditOpinion,String count) throws UnsupportedEncodingException {
		ExpertAuditOpinion find = new ExpertAuditOpinion();
		find.setExpertId(expertAuditOpinion.getExpertId());
		find.setFlagTime(2);
		List<ExpertAuditOpinion> list = expertAuditOpinionService.selectAllByExpertList(find);
		if(list.size()>0){
			if(list.size()>(Integer.valueOf(count)-1)){
				ExpertAuditOpinion auditOpinion = list.get(Integer.valueOf(count)-1);
				expertAuditOpinion.setId(auditOpinion.getId());
				expertAuditOpinion.setUpdatedAt(new Date());
				expertAuditOpinionService.updateByPrimaryKeySelective(expertAuditOpinion);
			}else{
				expertAuditOpinion.setId(UUID.randomUUID().toString().replaceAll("-", ""));
				expertAuditOpinion.setCreatedAt(new Date());
				expertAuditOpinion.setIsDeleted(0);
				expertAuditOpinionService.inserOpinion(expertAuditOpinion);
			}
		}else{
			expertAuditOpinion.setId(UUID.randomUUID().toString().replaceAll("-", ""));
			expertAuditOpinion.setCreatedAt(new Date());
			expertAuditOpinion.setIsDeleted(0);
			expertAuditOpinionService.inserOpinion(expertAuditOpinion);
		}
	}
	@RequestMapping("over")
	@ResponseBody
	public boolean over(@CurrentUser User user,String expertId,String count){
		ExpertAuditOpinion find = new ExpertAuditOpinion();
		find.setExpertId(expertId);
		find.setFlagTime(2);
		List<ExpertAuditOpinion> list = expertAuditOpinionService.selectAllByExpertList(find);
		ExpertAuditOpinion auditOpinion = list.get(Integer.valueOf(count)-1);
		String orgId=user.getOrg()==null?user.getOrgId():user.getOrg().getId();
		auditOpinion.setAuditPeoper(orgId);
		expertAuditOpinionService.updateByPrimaryKeySelective(auditOpinion);
		
		Expert e  = expertService.selectByPrimaryKey(expertId);
		e.setStatus(auditOpinion.getFlagAudit()+"");
		if(auditOpinion.getFlagAudit()==17){
			if(Integer.valueOf(count)>=3){
				e.setStatus("8");
			}
			e.setFinalInspectCount(count);
		}
		e.setFinalInspectPeople(orgId);
		//设置复审中状态
		e.setAuditTemporary(0);
		// 设置修改时间
		e.setUpdatedAt(new Date());
		expertService.updateByPrimaryKeySelective(e);
		return true;
	}
	@RequestMapping("downloadExpertFinalInspect")
	public ResponseEntity < byte[] > downloadExpertFinalInspect(String expertId,String count,
	        HttpServletRequest request, HttpServletResponse response) throws Exception {
	        // 根据编号查询专家信息
			Expert expert = expertService.selectByPrimaryKey(expertId);

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("businessId", expertId);
			map.put("isDeleted", 0);
			String gpId = DictionaryDataUtil.getId("GOODS_PROJECT");
 			String pId = DictionaryDataUtil.getId("PROJECT");
 			List<ExpertTitle> proList=expertTitleService.queryByUserId(expert.getId(),pId);
 			List<ExpertTitle> ecoList=expertTitleService.queryByUserId(expert.getId(),gpId);
			List<ExpertAttachment> list = expertAttachmentService.selectListByMap(map);
			if(proList.size()>0){
				for (ExpertTitle expertTitle : proList) {
					ExpertAttachment e = new ExpertAttachment();
					e.setId(expertTitle.getId());
					e.setTypeId("9");
					e.setBusinessId(expertTitle.getId());
					e.setName(expertTitle.getQualifcationTitle());
					list.add(e);
				}
			}
			if(ecoList.size()>0){
				for (ExpertTitle expertTitle : ecoList) {
					ExpertAttachment e = new ExpertAttachment();
					e.setId(expertTitle.getId());
					e.setTypeId("9");
					e.setBusinessId(expertTitle.getId());
					e.setName(expertTitle.getQualifcationTitle());
					list.add(e);
				}
			}
			List<ExpertAttachment> expertAttachmentList=new ArrayList<ExpertAttachment>();
			for (ExpertAttachment e : list) {
				ExpertFinalInspect expertFinalInspect = new ExpertFinalInspect();
				expertFinalInspect.setExpertId(expertId);
				expertFinalInspect.setFileId(e.getId());
				expertFinalInspect.setFinalInspectNumber(count);
				ExpertFinalInspect inspect = finalInspectService.getExpertFinalInspect(expertFinalInspect);
				if(inspect!=null){
					e.setStatus(inspect.getStatus());
					e.setReason(inspect.getReason());
				}
				String name="";
				if("50".equals(e.getTypeId())){
					name="近期免冠彩色证件照";
				}else if("1".equals(e.getTypeId())){
					name="缴纳社会保险证明";
				}else if("2".equals(e.getTypeId())){
					name="退休证书或退休证明";
				}else if("3".equals(e.getTypeId())){
					name="身份证扫描件";
				}else if("12".equals(e.getTypeId())){
					name="军队人员身份证件";
				}else if("4".equals(e.getTypeId())){
					name="专业技术职称证书";
				}else if("5".equals(e.getTypeId())){
					name="毕业证书";
				}else if("6".equals(e.getTypeId())){
					name="学位证书";
				}else if("8".equals(e.getTypeId())){
					name="推荐信";
				}else if("7".equals(e.getTypeId())){
					name="获奖证书";
				}else if("9".equals(e.getTypeId())){
					name=e.getName()+"(执业资格证书)";
				}else if("13".equals(e.getTypeId()) || "14".equals(e.getTypeId())){
					continue;
				}
				e.setName(name);
				expertAttachmentList.add(e);
			}
			ExpertAuditOpinion find = new ExpertAuditOpinion();
			find.setExpertId(expertId);
			find.setFlagTime(2);
			List<ExpertAuditOpinion> opinionList = expertAuditOpinionService.selectAllByExpertList(find);
			if(opinionList.size()>0){
				if(opinionList.size()>=Integer.valueOf(count)){
					find=opinionList.get(Integer.valueOf(count)-1);
				}
			}
			String orgName="";
			if(find.getAuditPeoper()!=null){
				Orgnization selectByorg = orgService.selectByorg(find.getAuditPeoper());
				if(selectByorg!=null){
					orgName=selectByorg.getName();
				}
			}
	        // 文件存储地址
	        String filePath = request.getSession().getServletContext()
	            .getRealPath("/WEB-INF/upload_file/");
	        // 文件名称
	        String fileName = createWordMethod(expertAttachmentList,orgName,expert,find, request);
	        // 下载后的文件名
	        String downFileName = "军队采购评审专家复查表.doc";
	        if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {
	            //解决IE下文件名乱码
	            downFileName = URLEncoder.encode(downFileName, "UTF-8");
	        } else {
	            //解决非IE下文件名乱码
	            downFileName = new String(downFileName.getBytes("UTF-8"), "ISO8859-1");
	        }

	        return expertService.downloadFile(fileName, filePath, downFileName);
	    }
	 private String createWordMethod(List<ExpertAttachment> list,String orgName,Expert e,ExpertAuditOpinion opinion, HttpServletRequest request) throws Exception {
	      /** 用于组装word页面需要的数据 */
	      Map<String, Object> dataMap = new HashMap<String, Object>();
	      StringBuffer expertType = new StringBuffer();
	        if(e.getExpertsTypeId() != null && !"".equals(e.getExpertsTypeId())) {
	        for (String typeId : e.getExpertsTypeId().split(",")) {
	            if(dictionaryDataServiceI.getDictionaryData(typeId).getKind() == 6){
	                expertType.append(dictionaryDataServiceI.getDictionaryData(typeId).getName() + "技术、");
	            }else{
	                expertType.append(dictionaryDataServiceI.getDictionaryData(typeId).getName() + "、");    
	            }
	            
	        }
	        }
	        String expertsType = expertType.toString().substring(0, expertType.length() - 1);
	        e.setExpertsTypeId(expertsType);
	        DictionaryData gender = dictionaryDataServiceI.getDictionaryData(e.getGender());
	        if (gender != null) {
	            e.setGender(gender.getName() == null ? "" : gender.getName());
	        } else {
	        	 e.setDegree("");
	        }
	        DictionaryData expertsForm = dictionaryDataServiceI.getDictionaryData(e.getExpertsFrom());
	        if (expertsForm != null) {
	        	e.setExpertsFrom(expertsForm.getName() == null ? "" : expertsForm.getName());
	        } else {
	           e.setExpertsFrom("");
	        }
	      dataMap.put("expert", e);
	      dataMap.put("list", list);
	      dataMap.put("orgName", orgName);
	      dataMap.put("o", opinion);
	      // 文件名称
	        String fileName = new String(("军队采购评审专家复查表.doc").getBytes("UTF-8"),
	            "UTF-8");
	       /** 生成word 返回文件名 */
	        String newFileName = WordUtil.createWord(dataMap, "expertFinalInspect.ftl",
	            fileName, request);
	        return newFileName;
	 }
}
