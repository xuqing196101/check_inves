package ses.controller.sys.ems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.model.bms.Area;
import ses.model.bms.Category;
import ses.model.bms.DictionaryData;
import ses.model.ems.Expert;
import ses.model.ems.ExpertCategory;
import ses.model.ems.ExpertTitle;
import ses.model.sms.SupplierCateTree;
import ses.service.bms.AreaServiceI;
import ses.service.bms.CategoryService;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.bms.EngCategoryService;
import ses.service.ems.ExpertCategoryService;
import ses.service.ems.ExpertService;
import ses.service.ems.ExpertTitleService;
import ses.util.DictionaryDataUtil;
import ses.util.PropUtil;

import com.github.pagehelper.PageInfo;
import common.constant.Constant;

/**
 * <p>Title:ExpertQuery </p>
 * <p>Description: 专家查询</p>
 * @date 2017-5-12下午2:18:34
 */
@Controller
@RequestMapping("/expertQuery")
public class ExpertQueryController {
	
	@Autowired
    private DictionaryDataServiceI dictionaryDataServiceI; // TypeId
	
	@Autowired
    private ExpertService service; // 专家管理
	
    @Autowired
    private AreaServiceI areaServiceI; // 地区查询
    
    @Autowired
	private ExpertService expertService;
    
    @Autowired
	private ExpertTitleService expertTitleService;

    @Autowired
	private ExpertCategoryService expertCategoryService; // 专家类别中间表
    
    @Autowired
	private CategoryService categoryService;
    
	@Autowired
	private EngCategoryService engCategoryService; //工程专业信息
	
	/**
     * 
     * @Title: view
     * @date 2016年9月29日 上午11:03:50
     * @Description: TODO 查看专家信息
     * @param id
     * @param model
     * @param @return
     * @return String
     */
    @RequestMapping("/view")
    public String view(String expertId, Model model, Integer sign) {
    	model.addAttribute("expertId", expertId);
    	//1是全部专家查询，2是入库专家查询
    	model.addAttribute("sign", sign);
        // 查询出专家
        Expert expert = service.selectByPrimaryKey(expertId);
        // 专家系统key
		Integer expertKey = Constant.EXPERT_SYS_KEY;
		Map < String, Object > typeMap = getTypeId();
		// typrId集合
		model.addAttribute("typeMap", typeMap);
		// 业务id就是专家id
		model.addAttribute("sysId", expertId);
		// Constant.EXPERT_SYS_VALUE;
		model.addAttribute("expertKey", expertKey);
        model.addAttribute("expert", expert);

        //专家来源
		if(expert.getExpertsFrom() != null) {
			DictionaryData expertsFrom = dictionaryDataServiceI.getDictionaryData(expert.getExpertsFrom());
			model.addAttribute("expertsFrom", expertsFrom.getName());
			// 专家来源
			model.addAttribute("froms", expertsFrom.getCode());
		}
		//军队人员身份证件类型
		if(expert.getIdType() != null) {
			DictionaryData idType = dictionaryDataServiceI.getDictionaryData(expert.getIdType());
			model.addAttribute("idType", idType.getName());
		}
		
		//地区查询
		List < Area > privnce = areaServiceI.findRootArea();
		model.addAttribute("privnce", privnce);

		if(expert.getAddress() !=null ){
			Area area = areaServiceI.listById(expert.getAddress());
			String sonName = area.getName();
			model.addAttribute("sonName", sonName);
			for(int i = 0; i < privnce.size(); i++) {
				if(area.getParentId().equals(privnce.get(i).getId())) {
					String parentName = privnce.get(i).getName();
					model.addAttribute("parentName", parentName);
				}
			}
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
        return "ses/ems/expertQuery/basic_info";
    }
	
	/**
	 * 入库查询
	 */
	@RequestMapping(value = "/list")
    public String findAllExpert(Expert expert, Integer page, Model model) {
        List < Expert > allExpert = service.selectRuKuExpert(expert, page);
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
            
            //专家来源
	  		if(exp.getExpertsFrom() != null) {
	  			DictionaryData expertsFrom = dictionaryDataServiceI.getDictionaryData(exp.getExpertsFrom());
	  			exp.setExpertsFrom(expertsFrom.getName());
	  		}
        }
        // 专家类型
        List < DictionaryData > expertFromList = DictionaryDataUtil.find(12);
        model.addAttribute("expertFromList", expertFromList);
        model.addAttribute("expert", expert);
        PageInfo<Expert> pageInfo = new PageInfo < Expert > (allExpert);
        model.addAttribute("result", pageInfo);
        return "ses/ems/expertQuery/list";
    }
	

    /**
	 * @Title: expertType
	 * @date 2016-12-19 下午7:37:48  
	 * @Description:专家类型
	 * @param @param expertQuery
	 * @param @param model
	 * @param @param expertId
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/expertType")
	public String expertType(Model model, String expertId, Integer sign) {
		model.addAttribute("expertId", expertId);
		
		//1是全部专家查询，2是入库专家查询
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

		List<ExpertTitle> expertTitleList = new ArrayList<>();

		//工程技术
		String engCodeId = DictionaryDataUtil.getId("PROJECT");
		
		//工程经济
		String goodsProjectId = DictionaryDataUtil.getId("GOODS_PROJECT");
		
		if(expert.getExpertsTypeId() !=null){
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

		return "ses/ems/expertQuery/expertType";
	}
	
	
	/**
	 * @Title: product
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
		//1是全部专家查询，2是入库专家查询
		model.addAttribute("sign", sign);
		
		expert = expertService.selectByPrimaryKey(expertId);

		List < DictionaryData > allCategoryList = new ArrayList < DictionaryData > ();

        // 获取专家类别
        List < String > allTypeId = new ArrayList < String > ();
        if(expert.getExpertsTypeId() !=null){
        	 for(String id: expert.getExpertsTypeId().split(",")) {
                 allTypeId.add(id);
             }
             a: for(int i = 0; i < allTypeId.size(); i++) {
                 DictionaryData dictionaryData = dictionaryDataServiceI.getDictionaryData(allTypeId.get(i));
                 /*if(dictionaryData != null && dictionaryData.getKind() == 19) {
     				allTypeId.remove(i);
     				continue a;
     			};*/
                 
                 allCategoryList.add(dictionaryData);
             }
        	
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
		
		return "ses/ems/expertQuery/product";
	}
	
	
	/**
	 *〈简述〉
	 * 获取所有已选中的节点
	 *〈详细描述〉
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
        return "ses/ems/expertQuery/ajax_items";
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
     * 
     * @Title: getTypeId
     * @date 2016年11月9日 下午2:32:38
     * @Description: TODO 封装附件类型
     * @param @return
     * @return Map<String,Object>
     */
    private Map < String, Object > getTypeId() {
        DictionaryData dd = new DictionaryData();
        Map < String, Object > typeMap = new HashMap < > ();
        for(int i = 0; i < 8; i++) {
            if(i == 0) {
                // 专家身份证
                dd.setCode("EXPERT_IDNUMBER");
                List < DictionaryData > find = dictionaryDataServiceI.find(dd);
                typeMap.put("EXPERT_IDNUMBER_TYPEID", find.get(0).getId());
            }
            if(i == 1) {
                // 职称证书附件
                dd.setCode("EXPERT_TITLE");
                List < DictionaryData > find = dictionaryDataServiceI.find(dd);
                typeMap.put("EXPERT_TITLE_TYPEID", find.get(0).getId());
            }
            if(i == 2) {
                // 专家申请表附件
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
                // 专家学位证书附件
                dd.setCode("EXPERT_DEGREE");
                List < DictionaryData > find = dictionaryDataServiceI.find(dd);
                typeMap.put("EXPERT_DEGREE_TYPEID", find.get(0).getId());
            }
            if(i == 5) {
                // 专家个人照片附件
                dd.setCode("EXPERT_PHOTO");
                List < DictionaryData > find = dictionaryDataServiceI.find(dd);
                typeMap.put("EXPERT_PHOTO_TYPEID", find.get(0).getId());
            }
            if(i == 6) {
                // 专家合同书附件
                dd.setCode("EXPERT_CONTRACT");
                List < DictionaryData > find = dictionaryDataServiceI.find(dd);
                typeMap.put("EXPERT_CONTRACT_TYPEID", find.get(0).getId());
            }
            if(i == 7) {
                // 专家身份证附件
                dd.setCode("EXPERT_IDCARDNUMBER");
                List < DictionaryData > find = dictionaryDataServiceI.find(dd);
                typeMap.put("EXPERT_IDCARDNUMBER_TYPEID", find.get(0).getId());
            }
        }
        return typeMap;
    }
    
    /**
	 * @Title: expertFile
	 * @date 2016-12-19 下午7:37:01  
	 * @Description:附件信息
	 * @param @param expert
	 * @param @param model
	 * @param @param expertId
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/expertFile")
	public String expertFile( Model model, String expertId, Integer sign) {
		// 专家系统key
		Integer expertKey = Constant.EXPERT_SYS_KEY;
		model.addAttribute("expertKey", expertKey);
		// 获取各个附件类型id集合
		Map < String, Object > typeMap = getTypeId();
		// typrId集合
		model.addAttribute("typeMap", typeMap);
		model.addAttribute("expertId", expertId);
		
		//1是全部专家查询，2是入库专家查询
		model.addAttribute("sign", sign);
		return "ses/ems/expertQuery/expertFile";
	}
	
    /**
     * @Title: temporarySupplier
     * @date 2017-5-13 上午11:23:24  
     * @Description:临时供应商
     * @param       
     * @return void
     */
    @RequestMapping(value = "temporaryExpert")
    public String temporarySupplier(Model model, String expertId){
    	Expert expert = service.selectByPrimaryKey(expertId);
    	model.addAttribute("expert", expert);
    	return "ses/ems/expertQuery/temporary_expert_info";
    }
}
