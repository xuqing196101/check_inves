package ses.controller.sys.sms;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.constants.SupplierConstants;
import ses.model.bms.Category;
import ses.model.bms.CategoryTree;
import ses.model.bms.DictionaryData;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierAudit;
import ses.model.sms.SupplierCateTree;
import ses.model.sms.SupplierItem;
import ses.service.bms.CategoryService;
import ses.service.bms.EngCategoryService;
import ses.service.sms.SupplierAuditService;
import ses.service.sms.SupplierItemService;
import ses.service.sms.SupplierService;
import ses.util.DictionaryDataUtil;
import ses.util.SupplierLevelUtil;
import bss.controller.base.BaseController;

import com.github.pagehelper.PageInfo;

@Controller
@Scope("prototype")
@RequestMapping(value = "/supplier_item")
public class SupplierItemController extends BaseController {

	@Autowired
	private SupplierItemService supplierItemService;

	@Autowired
	private SupplierService supplierService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private SupplierAuditService supplierAuditService;
	
	@Autowired
	private EngCategoryService engCategoryService;
	
	@ResponseBody
	@RequestMapping(value = "/saveCategory", method = RequestMethod.POST)
	public String saveCategory(SupplierItem supplierItem, boolean isParentChecked, String clickFlag) {
		// 判断是否是取消选中
		if("0".equals(clickFlag)) {
			supplierItemService.deleteItems(supplierItem, isParentChecked);
		} else {
			supplierItemService.saveOrUpdate(supplierItem, isParentChecked);
		}
		return "ok";
	}
	
	/**
	 *〈简述〉加载品目树
	 *〈详细描述〉
	 * @author myc
	 * @param supplierId	供应商Id
	 * @param code 		  	编码
	 * @param id 		  	当前节点Id
	 * @param status 		状态
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/loadCategory", produces = "application/json;charset=UTF-8")
	public List < CategoryTree > loadCategory(String supplierId, String code, String id, Integer status) {
		List < CategoryTree > categoryList = new ArrayList < CategoryTree > ();
		//初始化跟节点
		if(StringUtils.isEmpty(id)) {
			if(StringUtils.isNotBlank(code)) {
				CategoryTree ct = new CategoryTree();
				ct.setCode(code);
				ct.setIsParent("true");
				DictionaryData dd = DictionaryDataUtil.get(code);
				if(dd != null){
					ct.setName(dd.getName());
					if("PRODUCT".equals(code) || "SALES".equals(code)) {
						dd = DictionaryDataUtil.get("GOODS");
					}
					if(dd != null){
						ct.setId(dd.getId());
						id = dd.getId();
					}
				}
				int count = supplierItemService.countBySupplierIdCategoryId(supplierId, id, code);
				if(count > 0){
					ct.setChecked(true);
				}
				categoryList.add(ct);
			}
		}
		//加载子集节点
		if(StringUtils.isNotBlank(id)) {
			List < Category > child = categoryService.findPublishTree(id, status);
			Integer level = SupplierLevelUtil.getLevel(supplierId, code);
			if (level != null) {
			    for (int i = 0; i < child.size(); i++) {
			        Category cate = child.get(i);
			        if (cate.getLevel() != null && cate.getLevel() < level) {
			            child.remove(i);
			        }
			    }
			}
			for(Category c: child) {
				CategoryTree ct1 = new CategoryTree();
				ct1.setName(c.getName());
				ct1.setParentId(c.getParentId());
				ct1.setId(c.getId());
                ct1.setCode(c.getCode());
                ct1.setIsParent(c.getIsParent());
                int count = supplierItemService.countBySupplierIdCategoryId(supplierId, c.getId(), code);
				if(count > 0){
					ct1.setChecked(true);
				}
				categoryList.add(ct1);
			}
		}
		return categoryList;
	}
	
	/**
	 * 品目搜索
	 * @param supplierId
	 * @param type
	 * @param cateName
	 * @param codeName
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/searchCate", produces = "application/json;charset=UTF-8")
	public List < CategoryTree > searchCate(String supplierId, String type, String cateName, String codeName) {
		List < CategoryTree > categoryList = new ArrayList < CategoryTree > ();
		//初始化跟节点
		String id = null;
		int classifyType = 0;
		if(StringUtils.isNotBlank(type)) {
			CategoryTree ct = new CategoryTree();
			ct.setCode(type);
			ct.setIsParent("true");
			DictionaryData dd = DictionaryDataUtil.get(type);
			if(dd != null){
				ct.setName(dd.getName());
				if("PRODUCT".equals(type) || "SALES".equals(type)) {
					dd = DictionaryDataUtil.get("GOODS");
					classifyType = 1;
				}else if("PROJECT".equals(type)){
					classifyType = 2;
				}else if("SERVICE".equals(type)){
					classifyType = 3;
				}
				if(dd != null){
					ct.setId(dd.getId());
					id = dd.getId();
				}
			}
			int count = supplierItemService.countBySupplierIdCategoryId(supplierId, id, type);
			if(count > 0){
				ct.setChecked(true);
			}
			categoryList.add(ct);
		}
		//加载子集节点
		List < Category > child = categoryService.searchList(classifyType, cateName, codeName);
		Integer level = SupplierLevelUtil.getLevel(supplierId, type);
		if (level != null) {
		    for (int i = 0; i < child.size(); i++) {
		        Category cate = child.get(i);
		        if (cate.getLevel() != null && cate.getLevel() < level) {
		            child.remove(i);
		        }
		    }
		}
		for(Category c: child) {
			CategoryTree ct1 = new CategoryTree();
			//ct1.setName(c.getName());
			ct1.setName((c.getName()+"").replaceAll(cateName, "<span style='background-color: yellow; color: red; margin-left: 0; margin-right: 0;'>"+cateName+"</span>"));
			ct1.setParentId(c.getParentId());
			ct1.setId(c.getId());
            ct1.setCode(c.getCode());
            ct1.setIsParent(c.getIsParent());
            int count = supplierItemService.countBySupplierIdCategoryId(supplierId, c.getId(), type);
			if(count > 0){
				ct1.setChecked(true);
			}
			categoryList.add(ct1);
		}
		return categoryList;
	}
	
	/**
	 * 异步获取所有已选中的节点
	 * @param model
	 * @param supplierId
	 * @param code
	 * @param pageNum
	 * @return
	 */
	@RequestMapping("/loadCheckedCategory")
	public String loadCheckedCategory(Model model, String supplierId, String code, Integer pageNum){
		String rootNode = null;
		DictionaryData dd = DictionaryDataUtil.get(code);
		if(dd != null){
			rootNode = dd.getName();
		}
		// 查询已选中的节点信息
		List < SupplierItem > listSupplierItems = supplierItemService.findCategoryList(supplierId, code, pageNum == null ? 1 : pageNum);
		List < SupplierCateTree > allTreeList = new ArrayList < SupplierCateTree > ();
		for(SupplierItem item: listSupplierItems) {
			String categoryId = item.getCategoryId();
//			SupplierCateTree cateTree = getTreeListByCategoryId(categoryId, null);
			SupplierCateTree cateTree = new SupplierCateTree();
			cateTree.setCategoryId(categoryId);
			cateTree.setRootNode(rootNode);
			cateTree = supplierItemService.getSupplierCateTree(cateTree);
			if(cateTree != null && cateTree.getRootNode() != null) {
				cateTree.setItemsId(item.getId());
				cateTree.setCategoryId(categoryId);
				cateTree.setIsReturned(item.getIsReturned());
				allTreeList.add(cateTree);
			}
		}
		for(SupplierCateTree cate: allTreeList) {
			cate.setRootNode(cate.getRootNode() == null ? "" : cate.getRootNode());
			cate.setFirstNode(cate.getFirstNode() == null ? "" : cate.getFirstNode());
			cate.setSecondNode(cate.getSecondNode() == null ? "" : cate.getSecondNode());
			cate.setThirdNode(cate.getThirdNode() == null ? "" : cate.getThirdNode());
			cate.setFourthNode(cate.getFourthNode() == null ? "" : cate.getFourthNode());
			/*String typeName = "";
			if(ses.util.Constant.SUPPLIER_PRODUCT.equals(supplierTypeRelateId)){
				typeName = "生产";
			}else if(ses.util.Constant.SUPPLIER_SALES.equals(supplierTypeRelateId)){
				typeName = "销售";
			}
			cate.setRootNode(cate.getRootNode() + typeName);*/
		}
		
		Supplier currSupplier = supplierService.selectById(supplierId);
		
		model.addAttribute("supplierId", supplierId);
		model.addAttribute("supplierTypeRelateId", code);
		model.addAttribute("currSupplier", currSupplier);
		model.addAttribute("result", new PageInfo < > (listSupplierItems));
		model.addAttribute("itemsList", allTreeList);

		if(currSupplier != null && currSupplier.getStatus() != null && currSupplier.getStatus() == 2){
			// 不通过字段的名字
			SupplierAudit s = new SupplierAudit();
			s.setSupplierId(supplierId);
			//s.setAuditType("items_page");
			String auditType = ses.util.Constant.ITEMS_PRODUCT_PAGE;
			if(ses.util.Constant.SUPPLIER_PRODUCT.equals(code)){
				auditType = ses.util.Constant.ITEMS_PRODUCT_PAGE;
			}
			if(ses.util.Constant.SUPPLIER_SALES.equals(code)){
				auditType = ses.util.Constant.ITEMS_SALES_PAGE;
			}
			s.setAuditType(auditType);
			List < SupplierAudit > auditLists = supplierAuditService.getAuditRecords(s, SupplierConstants.AUDIT_RETURN_STATUS);

			StringBuffer errorField = new StringBuffer();
			for(SupplierAudit audit: auditLists) {
				errorField.append(audit.getAuditField() + ",");
			}
			// 判断该类型是否审核通过
			s.setAuditType("supplierType_page");
			if(dd != null){
				s.setAuditField(dd.getId());
			}
			int supplierTypeAuditCount = supplierAuditService.countAuditRecords(s, new Integer[]{0,2});
			if(supplierTypeAuditCount > 0){
				model.addAttribute("isSupplierTypeAudited", true);
			}
			model.addAttribute("audit", errorField);
			model.addAttribute("auditType", auditType);
		}

		return "ses/sms/supplier_register/ajax_items";
	}

	/**
	 *〈简述〉异步获取所有已选中的节点
	 *〈详细描述〉
	 * @author WangHuijie
	 * @param supplierItem
	 * @return
	 */
/*	@RequestMapping("/getCategories")
	public String getCategoryList(SupplierItem supplierItem, Model model, Integer pageNum) {
		String supplierId = supplierItem.getSupplierId();
		String supplierTypeRelateId = supplierItem.getSupplierTypeRelateId();
		String rootNode = null;
		DictionaryData dd = DictionaryDataUtil.get(supplierTypeRelateId);
		if(dd != null){
			rootNode = dd.getName();
		}
		// 查询已选中的节点信息
		List < SupplierItem > listSupplierItems = supplierItemService.findCategoryList(supplierId, supplierTypeRelateId, pageNum == null ? 1 : pageNum);
		List < SupplierCateTree > allTreeList = new ArrayList < SupplierCateTree > ();
		for(SupplierItem item: listSupplierItems) {
			String categoryId = item.getCategoryId();
//			SupplierCateTree cateTree = getTreeListByCategoryId(categoryId, null);
			SupplierCateTree cateTree = new SupplierCateTree();
			cateTree.setCategoryId(categoryId);
			cateTree.setRootNode(rootNode);
			cateTree = supplierItemService.getSupplierCateTree(cateTree);
			if(cateTree != null && cateTree.getRootNode() != null) {
				cateTree.setItemsId(item.getId());
				cateTree.setCategoryId(categoryId);
				cateTree.setIsReturned(item.getIsReturned());
				allTreeList.add(cateTree);
			}
		}
		for(SupplierCateTree cate: allTreeList) {
			cate.setRootNode(cate.getRootNode() == null ? "" : cate.getRootNode());
			cate.setFirstNode(cate.getFirstNode() == null ? "" : cate.getFirstNode());
			cate.setSecondNode(cate.getSecondNode() == null ? "" : cate.getSecondNode());
			cate.setThirdNode(cate.getThirdNode() == null ? "" : cate.getThirdNode());
			cate.setFourthNode(cate.getFourthNode() == null ? "" : cate.getFourthNode());
			String typeName = "";
			if(ses.util.Constant.SUPPLIER_PRODUCT.equals(supplierTypeRelateId)){
				typeName = "生产";
			}else if(ses.util.Constant.SUPPLIER_SALES.equals(supplierTypeRelateId)){
				typeName = "销售";
			}
			cate.setRootNode(cate.getRootNode() + typeName);
		}
		
		Supplier currSupplier = supplierService.selectById(supplierId);
		
		model.addAttribute("supplierId", supplierId);
		model.addAttribute("supplierTypeRelateId", supplierTypeRelateId);
		model.addAttribute("currSupplier", currSupplier);
		model.addAttribute("result", new PageInfo < > (listSupplierItems));
		model.addAttribute("itemsList", allTreeList);

		if(currSupplier != null && currSupplier.getStatus() != null && currSupplier.getStatus() == 2){
			// 不通过字段的名字
			SupplierAudit s = new SupplierAudit();
			s.setSupplierId(supplierId);
			//s.setAuditType("items_page");
			String auditType = ses.util.Constant.ITEMS_PRODUCT_PAGE;
			if(ses.util.Constant.SUPPLIER_PRODUCT.equals(supplierTypeRelateId)){
				auditType = ses.util.Constant.ITEMS_PRODUCT_PAGE;
			}
			if(ses.util.Constant.SUPPLIER_SALES.equals(supplierTypeRelateId)){
				auditType = ses.util.Constant.ITEMS_SALES_PAGE;
			}
			s.setAuditType(auditType);
			List < SupplierAudit > auditLists = supplierAuditService.getAuditRecords(s, SupplierConstants.AUDIT_RETURN_STATUS);

			StringBuffer errorField = new StringBuffer();
			for(SupplierAudit audit: auditLists) {
				errorField.append(audit.getAuditField() + ",");
			}
			// 判断该类型是否审核通过
			s.setAuditType("supplierType_page");
			if(dd != null){
				s.setAuditField(dd.getId());
			}
			int supplierTypeAuditCount = supplierAuditService.countAuditRecords(s, new Integer[]{0,2});
			if(supplierTypeAuditCount > 0){
				model.addAttribute("isSupplierTypeAudited", true);
			}
			model.addAttribute("audit", errorField);
			model.addAttribute("auditType", auditType);
		}

		return "ses/sms/supplier_register/ajax_items";
	}*/

	/**
	 *〈简述〉获取当前节点的所有父级节点(包括根节点)
	 *〈详细描述〉
	 * @author WangHuijie
	 * @param categoryId 
	 * @return
	 */
	public List < Category > getAllParentNode(String categoryId) {
		List < Category > categoryList = new ArrayList < Category > ();
		while(true) {
			Category cate = categoryService.findById(categoryId);
			if(cate == null) {
				DictionaryData root = DictionaryDataUtil.findById(categoryId);
				Category rootNode = new Category();
				rootNode.setId(root.getId());
				rootNode.setName(root.getName());
				categoryList.add(rootNode);
				break;
			} else {
				categoryList.add(cate);
				categoryId = cate.getParentId();
			}
		}
		return categoryList;
	}

	/**
	 *〈简述〉去除不是根节点的产品
	 *〈详细描述〉
	 * @author WangHuijie
	 * @param listSupplierItems
	 */
	public List < SupplierItem > removeNotChild(List < SupplierItem > listSupplierItems) {
		List < SupplierItem > newSupplierItems = new ArrayList < SupplierItem > ();
		for(SupplierItem cate: listSupplierItems) {
			String cateId = cate.getCategoryId();
			List < Category > childList = categoryService.findPublishTree(cateId, null);
			if(childList != null && childList.size() > 0) {
				newSupplierItems.add(cate);
			}
		}
		listSupplierItems.removeAll(newSupplierItems);
		return listSupplierItems;
	}

	/**
	 * @Title: getCategory
	 * @Description: 查询供应商勾选的三级品目
	 * author: Li Xiaoxiao
	 * @param @param code
	 * @param @param supplierId
	 * @return String
	 */
/*	@RequestMapping(value = "/category_type", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getCategory(String code, String supplierId, String stype) {
		List < CategoryTree > categoryList = new ArrayList < CategoryTree > ();
		String categoryId = "";
		if(code != null) {
			DictionaryData type = DictionaryDataUtil.get(code);
			if(code.equals("PRODUCT") || code.equals("SALES")) {

				categoryId = DictionaryDataUtil.getId("GOODS");
			} else {
				categoryId = DictionaryDataUtil.getId(code);
			}
			//查询子节点
			CategoryTree ct = new CategoryTree();
			ct.setName(type.getName());
			ct.setId(categoryId);
			ct.setChecked(true);
			ct.setIsParent("true");
			categoryList.add(ct);
			//查询三级节点
			List < SupplierItem > category = supplierItemService.getCategory(supplierId, categoryId, stype);
			for(SupplierItem c: category) {
				Category cate = categoryService.selectByPrimaryKey(c.getCategoryId());
				CategoryTree ct1 = new CategoryTree();
				ct1.setName(cate.getName());
				ct1.setParentId(cate.getParentId());
				ct1.setId(c.getCategoryId());
				ct1.setChecked(true);
				List < Category > cList = categoryService.findTreeByPid(c.getCategoryId());
				if(cList != null && cList.size() > 0) {
					ct1.setIsParent("true");
				} else {
					ct1.setIsParent("false");
				}

				categoryList.add(ct1);
			}
		}
		return JSON.toJSONString(categoryList);
	}*/

	@RequestMapping(value = "/getSupplierCate", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getCategory(String supplierId) {
		//		List<Category> list = supplierItemService.getCategory(supplierId);
		//		if(list.size()>0){
		//			return "1";
		//		}else{
		return "0";
		//		}
	}
	
	/**
	 * 品目搜索
	 * @param typeId
	 * @param cateName
	 * @param supplierId
	 * @param codeName
	 * @return
	 * @throws Exception
	 */
/*    @ResponseBody
    @RequestMapping(value = "/searchCate", produces = "application/json;charset=utf-8")
    public String searchCate(String typeId, String cateName, String supplierId, String codeName) throws Exception {
    	
        DictionaryData typeData = DictionaryDataUtil.findById(typeId);
        if (typeData != null && typeData.getCode().equals("ENG_INFO_ID")) {
            // 查询出所有满足条件的品目
            List < Category > categoryList = categoryService.searchByName(cateName, "ENG_INFO", codeName);
            // 循环判断是不是当前树的节点
            List < Category > cateList = new ArrayList < Category > ();
            for(Category category: categoryList) {
                String parentId = getParentId(category.getId(), "ENG_INFO");
                if(parentId.equals(typeId)) {
                    cateList.add(category);
                }
            }
            // 去重
            removeSame(cateList);
            // 获取被选中的节点的父节点
            List < Category > allCateList = new ArrayList < Category > ();
            allCateList.addAll(cateList);
            for(Category category: cateList) {
                List < Category > list = getParentNodeList(category.getId(), "ENG_INFO");
                allCateList.addAll(list);
            }
            // 去重
            removeSame(allCateList);
            // 最后加入根节点
            DictionaryData data = DictionaryDataUtil.findById(typeId);
            Category root = new Category();
            root.setId(data.getId());
            root.setName(data.getName());
            root.setCode(data.getCode());
            allCateList.add(root);
            // 将筛选完的List转换为CategoryTreeList
            List < CategoryTree > treeList = new ArrayList < CategoryTree > ();
            for(Category category: allCateList) {
                CategoryTree treeNode = new CategoryTree();
                treeNode.setId(category.getId());
                treeNode.setName(category.getName());
                treeNode.setParentId(category.getParentId());
                // 判断是否为父级节点
                List < Category > nodesList = engCategoryService.findPublishTree(category.getId(), null);
                if(nodesList != null && nodesList.size() > 0) {
                    treeNode.setIsParent("true");
                }
                treeList.add(treeNode);
            }
            return JSON.toJSONString(treeList);
        } else {
            String type = typeId;
            if(supplierId != null) {
                if(typeId.equals("SALES") || typeId.equals("PRODUCT")) {
                    typeId = DictionaryDataUtil.getId("GOODS");
                } else {
                    typeId = DictionaryDataUtil.getId(typeId);
                }
            }
            // 查询出所有满足条件的品目
            List < Category > categoryList = categoryService.searchByName(cateName, null, codeName);
            Integer level = SupplierLevelUtil.getLevel(supplierId, type);
            if (level != null) {
                for (int i = 0; i < categoryList.size(); i++) {
                    Category cate = categoryList.get(i);
                    if (cate != null) {
                        if (cate.getLevel() != null && cate.getLevel() < level) {
                            categoryList.remove(i);
                        } else {
                            if (cate.getParentId() != null) {
                                Category parentCate = categoryService.findById(cate.getParentId());
                                if (parentCate != null) {
                                    if (parentCate.getLevel() != null && parentCate.getLevel() < level) {
                                        categoryList.remove(i);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            // 循环判断是不是当前树的节点
            List < Category > cateList = new ArrayList < Category > ();
            for(Category category: categoryList) {
                String parentId = getParentId(category.getId(), null);
                if(parentId.equals(typeId)) {
                    cateList.add(category);
                }
            }
            // 去重
            removeSame(cateList);
            // 获取被选中的节点的父节点
            List < Category > allCateList = new ArrayList < Category > ();
            allCateList.addAll(cateList);
            for(Category category: cateList) {
                List < Category > list = getParentNodeList(category.getId(), null);
                allCateList.addAll(list);
            }
            // 去重
            removeSame(allCateList);
            // 最后加入根节点
            DictionaryData data = DictionaryDataUtil.findById(typeId);
            Category root = new Category();
            root.setId(data.getId());
            if("PRODUCT".equals(type)) {
                data.setName(data.getName() + "生产");
            } else if("SALES".equals(type)) {
                data.setName(data.getName() + "销售");
            }
            root.setName(data.getName());
            root.setCode(data.getCode());
            allCateList.add(root);
            // 将筛选完的List转换为CategoryTreeList
            List < CategoryTree > treeList = new ArrayList < CategoryTree > ();
            for(Category category: allCateList) {
            	if(category.getCode().length()>=9){
            		continue;
            	}
                CategoryTree treeNode = new CategoryTree();
                treeNode.setId(category.getId());
                treeNode.setName(category.getName());
                treeNode.setParentId(category.getParentId());
                treeNode.setCode(category.getCode());
                // 判断是否为父级节点
                List < Category > nodesList = categoryService.findPublishTree(category.getId(), null);
                if(nodesList != null && nodesList.size() > 0) {
                    treeNode.setIsParent("true");
                }
                treeList.add(treeNode);
            }
            for(CategoryTree treeNode: treeList) {
                // 判断是否被选中
                if(supplierId != null) {
                    treeNode.setChecked(isSupplierChecked(treeNode.getId(), supplierId, type));
                }
            }
            return JSON.toJSONString(treeList);
        }
    }*/
    
    /**
     * 判断该节点是否需要被选中
     * @param categoryId
     * @param supplierId
     * @param type
     * @return
     */
    /*private boolean isSupplierChecked(String categoryId, String supplierId, String type) {
        List < SupplierItem > category = supplierItemService.getSupplierIdCategoryId(supplierId, categoryId, type);
        if(category != null && category.size() > 0) {
            return true;
        } else {
            return false;
        }
    }*/
    
    /**
     *〈简述〉品目去重
     *〈详细描述〉
     * @author WangHuijie
     * @param allCategories
     * @return
     */
    public void removeSame(List < Category > list) {
        for(int i = 0; i < list.size() - 1; i++) {
            for(int j = list.size() - 1; j > i; j--) {
                if(list.get(j).getId().equals(list.get(i).getId())) {
                    list.remove(j);
                }
            }
        }
    }
    
    public String getParentId(String cateId, String flag) {
        if (flag == null) {
            Category cate = categoryService.selectByPrimaryKey(cateId);
            if(cate != null) {
                cateId = getParentId(cate.getParentId(), null);
            }
            return cateId;
        } else {
            Category cate = engCategoryService.selectByPrimaryKey(cateId);
            if(cate != null) {
                cateId = getParentId(cate.getParentId(), "ENG_INFO");
            }
            return cateId;
        }
    }
    
    public List < Category > getParentNodeList(String nodeId, String flag) {
        if (flag == null) {
            List < Category > parentNodeList = new ArrayList < Category > ();
            Category category = categoryService.findById(nodeId);
            if(category != null) {
                String parentId = category.getParentId();
                if(parentId != null && !"".equals(parentId)) {
                    Category cate = categoryService.findById(parentId);
                    if(cate != null) {
                        parentNodeList.add(cate);
                        List < Category > parentList = getParentNodeList(cate.getId(), null);
                        parentNodeList.addAll(parentList);
                    }
                }
            }
            return parentNodeList;
        } else {
            List < Category > parentNodeList = new ArrayList < Category > ();
            Category category = engCategoryService.findById(nodeId);
            if(category != null) {
                String parentId = category.getParentId();
                if(parentId != null && !"".equals(parentId)) {
                    Category cate = engCategoryService.findById(parentId);
                    if(cate != null) {
                        parentNodeList.add(cate);
                        List < Category > parentList = getParentNodeList(cate.getId(), "ENG_INFO");
                        parentNodeList.addAll(parentList);
                    }
                }
            }
            return parentNodeList;
        }
    }
    
	/**
	 *〈简述〉加载品目树
	 *〈详细描述〉
	 * @author myc
	 * @param id 当前节点Id
	 * @param code 编码
	 * @param supplierId 供应商Id
	 * @param status 状态
	 * @return
	 */
/*	@ResponseBody
	@RequestMapping(value = "/category_type", produces = "application/json;charset=UTF-8")
	public List < CategoryTree > getCategory(String id, String code, String supplierId, Integer status, String stype, String shenhe) {
		List < CategoryTree > categoryList = new ArrayList < CategoryTree > ();
		List < CategoryTree > cateList = new ArrayList < CategoryTree > ();
		String typeId = "";
		//初始化跟节点
		if(StringUtils.isEmpty(id)) {
			if(StringUtils.isNotBlank(code)) {
				DictionaryData type = DictionaryDataUtil.get(code);
				CategoryTree ct = new CategoryTree();
				if(type != null) {
					if(type.getCode().equals("PRODUCT")) {
						DictionaryData dd = DictionaryDataUtil.get("GOODS");
						ct.setCode("PRODUCT");
						typeId = dd.getId();
					} else if(type.getCode().equals("SALES")) {
						DictionaryData dd = DictionaryDataUtil.get("GOODS");
						ct.setCode("SALES");
						typeId = dd.getId();
					} else {
						ct.setCode(code);
						typeId = type.getId();
					}
				}

				ct.setName(type.getName());
				ct.setId(typeId);
				//List < SupplierItem > items = supplierItemService.getBySupplierIdCategoryIdIsNotReturned(supplierId, typeId, code);
				List < SupplierItem > items = supplierItemService.getSupplierIdCategoryId(supplierId, typeId, code);
				// 去掉审核不通过的品目
				//items = supplierItemService.removeAuditNotItems(items, supplierId, code);
				if(items != null && items.size() > 0) {
					ct.setChecked(true);
				}
				ct.setIsParent("true");
				categoryList.add(ct);
			}
		}
		//加载子集节点
		if(StringUtils.isNotBlank(id)) {
			List < Category > child = categoryService.findPublishTree(id, status);
			Integer level = SupplierLevelUtil.getLevel(supplierId, code);
			if (level != null) {
			    for (int i = 0; i < child.size(); i++) {
			        Category cate = child.get(i);
			        if (cate.getLevel() != null && cate.getLevel() < level) {
			            child.remove(i);
			        }
			    }
			}
			for(Category c: child) {
				CategoryTree ct1 = new CategoryTree();
				ct1.setName(c.getName());
				ct1.setParentId(c.getParentId());
				ct1.setId(c.getId());
                ct1.setCode(c.getCode());
                //List < SupplierItem > items = supplierItemService.getBySupplierIdCategoryIdIsNotReturned(supplierId, c.getId(), code);
				List < SupplierItem > items = supplierItemService.getSupplierIdCategoryId(supplierId, c.getId(), code);
				// 去掉审核不通过的品目
				//items = supplierItemService.removeAuditNotItems(items, supplierId, code);
				if(items != null && items.size() > 0) {
					ct1.setChecked(true);
				}
				List < Category > cList = categoryService.findTreeByPid(c.getId());
				if(cList != null && cList.size() > 0) {
					ct1.setIsParent("true");
				} else {
					ct1.setIsParent("false");
				}
				categoryList.add(ct1);
			}
		}
		for(CategoryTree catet: categoryList) {
			if(catet.getChecked() == true) {
				cateList.add(catet);
			}
		}
		if("true".equals(shenhe)) {
			return cateList;
		} else {
			return categoryList;
		}
	}*/

/*    @ResponseBody
    @RequestMapping(value = "/loadCategory", produces = "application/json;charset=UTF-8")
    public String loadCategory(HttpServletRequest request){
	    JSONArray jsonArray = new JSONArray();
	    String id = request.getParameter("id");
        String typeId = "";
        String code = request.getParameter("code");
        String supplierId = request.getParameter("supplierId");
        String status = request.getParameter("status");
        Integer statusInt = null;
        if(!StringUtils.isEmpty(status)){
            statusInt = Integer.parseInt(status);
        }
        //初始化根节点
        if(StringUtils.isEmpty(id)) {
            if(StringUtils.isNotBlank(code)) {
                JSONObject jsonObject = new JSONObject();
                DictionaryData type = DictionaryDataUtil.get(code);
                if(type != null) {
                    if(type.getCode().equals("PRODUCT")) {
                        DictionaryData dd = DictionaryDataUtil.get("GOODS");
                        jsonObject.put("code","PRODUCT");
                        typeId = dd.getId();
                    } else if(type.getCode().equals("SALES")) {
                        DictionaryData dd = DictionaryDataUtil.get("GOODS");
                        jsonObject.put("code","SALES");
                        typeId = dd.getId();
                    } else {
                        jsonObject.put("code",code);
                        typeId = type.getId();
                    }
                }
                jsonObject.put("name",type.getName());
                jsonObject.put("id", typeId);
                jsonObject.put("open",true);//默认打开根节点
                //List < SupplierItem > items = supplierItemService.getBySupplierIdCategoryIdIsNotReturned(supplierId, typeId, code);
                List < SupplierItem > items = supplierItemService.getSupplierIdCategoryId(supplierId, typeId, code);
                // 去掉审核不通过的品目
                //items = supplierItemService.removeAuditNotItems(items, supplierId, code);
                if(items != null && items.size() > 0) {
                    jsonObject.put("checked", true);
                }
                jsonObject.put("isParent", true);
                jsonObject.put("children", loadChildCategory(typeId, statusInt, supplierId, code));
                jsonArray.add(jsonObject);
            }
        }
	    return jsonArray.toString();
    }
    public JSONArray loadChildCategory(String id, Integer status, String supplierId, String code){
        JSONArray jsonArray = new JSONArray();
        List < Category > child = categoryService.findPublishTree(id, status);
        if(null == child || child.isEmpty()){
            return jsonArray;
        }
        Integer level = SupplierLevelUtil.getLevel(supplierId, code);
        if (level != null) {
            for (int i = 0; i < child.size(); i++) {
                Category cate = child.get(i);
                if (cate.getLevel() != null && cate.getLevel() < level) {
                    child.remove(i);
                }
            }
        }
        for(Category c: child) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", c.getName());
            jsonObject.put("parentId", c.getParentId());
            jsonObject.put("id", c.getId());
            jsonObject.put("code", c.getCode());
            //List < SupplierItem > items = supplierItemService.getBySupplierIdCategoryIdIsNotReturned(supplierId, c.getId(), code);
            List < SupplierItem > items = supplierItemService.getSupplierIdCategoryId(supplierId, c.getId(), code);
            // 去掉审核不通过的品目
            //items = supplierItemService.removeAuditNotItems(items, supplierId, code);
            if(items != null && items.size() > 0) {
                jsonObject.put("checked", true);
            }
            List < Category > cList = categoryService.findTreeByPid(c.getId());
            if(cList != null && cList.size() > 0) {
                jsonObject.put("isParent", true);
                jsonObject.put("children", loadChildCategory(c.getId(), status, supplierId, code));
            } else {
                jsonObject.put("isParent", false);
            }
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }*/

}