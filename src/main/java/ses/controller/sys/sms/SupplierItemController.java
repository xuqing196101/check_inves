package ses.controller.sys.sms;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.constants.SupplierConstants;
import ses.formbean.QualificationBean;
import ses.model.bms.Category;
import ses.model.bms.CategoryTree;
import ses.model.bms.DictionaryData;
import ses.model.bms.Qualification;
import ses.model.sms.SupplierAudit;
import ses.model.sms.SupplierCateTree;
import ses.model.sms.SupplierItem;
import ses.model.sms.SupplierPorjectQua;
import ses.service.bms.CategoryService;
import ses.service.sms.SupplierAuditService;
import ses.service.sms.SupplierItemService;
import ses.service.sms.SupplierPorjectQuaService;
import ses.service.sms.SupplierService;
import ses.util.DictionaryDataUtil;
import bss.controller.base.BaseController;

import com.alibaba.fastjson.JSON;
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
	private SupplierPorjectQuaService supplierPorjectQuaService;
	
	@ResponseBody
	@RequestMapping(value = "/saveCategory")
	public String saveCategory(SupplierItem supplierItem, String flag, String clickFlag) {
		// 判断是否是取消选中
		if("0".equals(clickFlag) && flag.equals("4")) {
			supplierItemService.deleteItems(supplierItem);
		} else if(flag.equals("4")) {
			supplierItemService.saveOrUpdate(supplierItem);
		}
		return "ok";
	}

	/**
	 *〈简述〉异步获取所有已选中的节点
	 *〈详细描述〉
	 * @author WangHuijie
	 * @param supplierItem
	 * @return
	 */
	@RequestMapping("/getCategories")
	public String getCategoryList(SupplierItem supplierItem, Model model, Integer pageNum) {
		// 查询已选中的节点信息
		List < SupplierItem > listSupplierItems = supplierItemService.findCategoryList(supplierItem.getSupplierId(), supplierItem.getSupplierTypeRelateId(), pageNum == null ? 1 : pageNum);
		List < SupplierCateTree > allTreeList = new ArrayList < SupplierCateTree > ();
		for(SupplierItem item: listSupplierItems) {
			String categoryId = item.getCategoryId();
			SupplierCateTree cateTree = getTreeListByCategoryId(categoryId, null);
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
			if(supplierItem.getSupplierTypeRelateId().equals("PRODUCT")) {
				typeName = "生产";
			} else if(supplierItem.getSupplierTypeRelateId().equals("SALES")) {
				typeName = "销售";
			}
			cate.setRootNode(cate.getRootNode() + typeName);
		}
		model.addAttribute("supplierId", supplierItem.getSupplierId());
		model.addAttribute("supplierTypeRelateId", supplierItem.getSupplierTypeRelateId());
		model.addAttribute("currSupplier", supplierService.selectById(supplierItem.getSupplierId()));
		model.addAttribute("result", new PageInfo < > (listSupplierItems));
		model.addAttribute("itemsList", allTreeList);

		// 不通过字段的名字
		SupplierAudit s = new SupplierAudit();
		s.setSupplierId(supplierItem.getSupplierId());
		//s.setAuditType("items_page");
		s.setAuditType(ses.util.Constant.ITEMS_PRODUCT_PAGE);
		if(ses.util.Constant.SUPPLIER_PRODUCT.equals(supplierItem.getSupplierTypeRelateId())){
			s.setAuditType(ses.util.Constant.ITEMS_PRODUCT_PAGE);
		}
		if(ses.util.Constant.SUPPLIER_SALES.equals(supplierItem.getSupplierTypeRelateId())){
			s.setAuditType(ses.util.Constant.ITEMS_SALES_PAGE);
		}
		List < SupplierAudit > auditLists = supplierAuditService.getAuditRecords(s, SupplierConstants.AUDIT_RETURN_STATUS);

		StringBuffer errorField = new StringBuffer();
		for(SupplierAudit audit: auditLists) {
			errorField.append(audit.getAuditField() + ",");
		}
		model.addAttribute("audit", errorField);
		model.addAttribute("auditType", s.getAuditType());

		return "ses/sms/supplier_register/ajax_items";
	}

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
	 *〈简述〉查询品目信息
	 *〈详细描述〉
	 * @author WangHuijie
	 * @param categoryId 产品Id
	 * @return List<CategoryTree> tree对象List
	 */
	public SupplierCateTree getTreeListByCategoryId(String categoryId, SupplierItem item) {
		SupplierCateTree cateTree = new SupplierCateTree();
		// 递归获取所有父节点
		List < Category > parentNodeList = getAllParentNode(categoryId);
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
				Category cate = categoryService.findById(parentNodeList.get(i).getId());
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
				Category cate = categoryService.findById(parentNodeList.get(i).getId());
				if(cate != null && cate.getParentId() != null) {
					Category parentNode = categoryService.findById(cate.getParentId());
					if(parentNode != null && cateTree.getFirstNode().equals(parentNode.getName())) {
						cateTree.setSecondNode(cate.getName());
					}
				}
			}
		}
		// 加入三级节点
		if(cateTree.getRootNode() != null && cateTree.getFirstNode() != null && cateTree.getSecondNode() != null) {
			for(int i = 0; i < parentNodeList.size(); i++) {
				Category cate = categoryService.findById(parentNodeList.get(i).getId());
				if(cate != null && cate.getParentId() != null) {
					Category parentNode = categoryService.findById(cate.getParentId());
					if(parentNode != null && cateTree.getSecondNode().equals(parentNode.getName())) {
						cateTree.setThirdNode(cate.getName());
					}
				}
			}
		}
		// 加入末级节点
		if(cateTree.getRootNode() != null && cateTree.getFirstNode() != null && cateTree.getSecondNode() != null && cateTree.getThirdNode() != null) {
		    if(parentNodeList.size()>4){
                for(int i = 0; i < parentNodeList.size(); i++) {
                    Category cate = categoryService.findById(parentNodeList.get(i).getId());
                    if(cate != null && cate.getParentId() != null) {
                        Category parentNode = categoryService.findById(cate.getParentId());
                        if(parentNode != null && cateTree.getThirdNode().equals(parentNode.getName())) {
                            cateTree.setFourthNode(cate.getName());
                        }
                    }
                }
            }
		}

		// 工程类等级
		if(item != null) {
			// 等级
			if(item != null && item.getLevel() != null) {
				DictionaryData data = DictionaryDataUtil.findById(item.getLevel());
				if(data!=null){
					cateTree.setLevel(data);
				}else{
					List<SupplierPorjectQua> projectData = supplierPorjectQuaService.queryByNameAndSupplierId(item.getQualificationType(), item.getSupplierId());
					if(projectData!=null&&projectData.size()>0){
				    	DictionaryData dd=new DictionaryData();
				    	dd.setName(projectData.get(0).getCertLevel());
				    	dd.setId(projectData.get(0).getId());
				    	cateTree.setLevel(dd); 
			    	}
				}
			}
			// 证书编号
			if(item != null && item.getCertCode() != null) {
				cateTree.setCertCode(item.getCertCode());
			}
			// 资质等级
			if(item != null && item.getQualificationType() != null) {
				cateTree.setQualificationType(item.getQualificationType());
			}
			if(item != null && item.getProfessType()!= null) {
				cateTree.setProName(item.getProfessType());
			}
			
			// 所有等级List
			List < Category > cateList = new ArrayList < Category > ();
			cateList.add(categoryService.selectByPrimaryKey(categoryId));
			List < QualificationBean > type = supplierService.queryCategoyrId(cateList, 4);
			List < Qualification > typeList = new ArrayList < Qualification > ();
			if(type != null && type.size() > 0 && type.get(0).getList() != null && type.get(0).getList().size() > 0) {
				typeList = type.get(0).getList();
			}
			//自定义等级
//			List<SupplierPorjectQua> supplierQua = supplierPorjectQuaService.queryByNameAndSupplierId(null, item.getSupplierId());
//			for(SupplierPorjectQua qua:supplierQua){
//				Qualification q=new Qualification();
//				q.setId(qua.getName());
//				q.setName(qua.getName());
//				typeList.add(q);
//			}
			
			cateTree.setTypeList(typeList);
		}
		return cateTree;
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
	@RequestMapping(value = "/category_type", produces = "text/html;charset=UTF-8")
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
	}

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

}