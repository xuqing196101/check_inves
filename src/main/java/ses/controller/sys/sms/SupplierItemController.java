package ses.controller.sys.sms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.formbean.QualificationBean;
import ses.model.bms.Area;
import ses.model.bms.Category;
import ses.model.bms.CategoryTree;
import ses.model.bms.DictionaryData;
import ses.model.bms.Qualification;
import ses.model.oms.Orgnization;
import ses.model.oms.PurchaseDep;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierAptitute;
import ses.model.sms.SupplierAudit;
import ses.model.sms.SupplierCateTree;
import ses.model.sms.SupplierCertEng;
import ses.model.sms.SupplierItem;
import ses.model.sms.SupplierMatEng;
import ses.model.sms.SupplierPorjectQua;
import ses.service.bms.AreaServiceI;
import ses.service.bms.CategoryService;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.bms.QualificationService;
import ses.service.oms.PurchaseOrgnizationServiceI;
import ses.service.sms.SupplierAptituteService;
import ses.service.sms.SupplierAuditService;
import ses.service.sms.SupplierCertEngService;
import ses.service.sms.SupplierItemService;
import ses.service.sms.SupplierMatEngService;
import ses.service.sms.SupplierPorjectQuaService;
import ses.service.sms.SupplierService;
import ses.util.DictionaryDataUtil;
import bss.controller.base.BaseController;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;

import common.constant.Constant;
import common.model.UploadFile;
import common.service.UploadService;

@Controller
@Scope("prototype")
@RequestMapping(value = "/supplier_item")
public class SupplierItemController extends BaseController {

	@Autowired
	private SupplierItemService supplierItemService;

	@Autowired
	private SupplierService supplierService;

	@Autowired
	private SupplierCertEngService supplierCertEngService;

	@Autowired
	private AreaServiceI areaService;

	@Autowired
	private UploadService uploadService;

	@Autowired
	private DictionaryDataServiceI dictionaryDataServiceI;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private QualificationService qualificationService;

	@Autowired
	private PurchaseOrgnizationServiceI purchaseOrgnizationService;

	@Autowired
	private SupplierAuditService supplierAuditService;
	
	@Autowired
	private SupplierPorjectQuaService supplierPorjectQuaService;
	
	@Autowired
	private SupplierAptituteService supplierAptituteService;
	@Autowired
	private  SupplierMatEngService supplierMatEngService;
	
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
		model.addAttribute("result", new PageInfo < > (listSupplierItems));
		model.addAttribute("itemsList", allTreeList);

		// 不通过字段的名字
		SupplierAudit s = new SupplierAudit();
		s.setSupplierId(supplierItem.getSupplierId());;
		s.setAuditType("items_page");
		List < SupplierAudit > auditLists = supplierAuditService.selectByPrimaryKey(s);

		StringBuffer errorField = new StringBuffer();
		for(SupplierAudit audit: auditLists) {
			errorField.append(audit.getAuditField() + ",");
		}
		model.addAttribute("audit", errorField);

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
	 * @Title: saveOrUpdate
	 * @author: Wang Zhaohua
	 * @date: 2016-11-2 下午3:27:32
	 * @Description: 保存或者更新品目
	 * @param: @param request
	 * @param: @param supplierItem
	 * @param: @param jsp
	 * @param: @param defaultPage
	 * @param: @return
	 * @return: String
	 */
	@RequestMapping(value = "save_or_update")
	public String saveOrUpdate(HttpServletRequest request, String supId, SupplierItem supplierItem, String flag, Model model, String supplierTypeIds, String clickFlag) {
		if(supId == null) {
			supId = supplierItem.getSupplierId();
		}
		Supplier supplier = supplierService.get(supId);
		HashMap < String, Object > map = new HashMap < String, Object > ();
		if(supplier.getProcurementDepId() != null) {
			map.put("id", supplier.getProcurementDepId());
			map.put("typeName", "1");
			// 采购机构
			List < PurchaseDep > depList = purchaseOrgnizationService.findPurchaseDepList(map);
			if(depList != null && depList.size() > 0) {
				Orgnization orgnization = depList.get(0);
				List < Area > city = areaService.findAreaByParentId(orgnization.getProvinceId());
				model.addAttribute("orgnization", orgnization);
				model.addAttribute("city", city);
				model.addAttribute("listOrgnizations1", depList);
			}

		}
		List < Area > privnce = areaService.findRootArea();

		model.addAttribute("privnce", privnce);
		Map < String, Object > maps = supplierService.getCategory(supplier.getId());
		model.addAttribute("server", maps.get("server"));
		model.addAttribute("product", maps.get("product"));
		model.addAttribute("sale", maps.get("sale"));
		model.addAttribute("project", maps.get("project"));

		List < DictionaryData > list = DictionaryDataUtil.find(6);
		for(int i = 0; i < list.size(); i++) {
			String code = list.get(i).getCode();
			if(code.equals("GOODS")) {
				list.remove(list.get(i));
			}
		}

		List < DictionaryData > wlist = DictionaryDataUtil.find(8);
		model.addAttribute("wlist", wlist);
		model.addAttribute("supplieType", list);

		// 页面跳转
		model.addAttribute("currSupplier", supplier);
		model.addAttribute("supplierTypeIds", supplierTypeIds);
		if(flag.equals("3")) {
			//初始化供应商注册附件类型
			model.addAttribute("supplierDictionaryData", dictionaryDataServiceI.getSupplierDictionary());
			model.addAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
			model.addAttribute("rootArea", areaService.findRootArea());
			List<Qualification> findList = qualificationService.findList(null, Integer.MAX_VALUE,null, 4);
			List<SupplierPorjectQua> supplierQua = supplierPorjectQuaService.queryByNameAndSupplierId(null, supplier.getId());
			if(supplierQua!=null&&supplierQua.size()>0){
				for(SupplierPorjectQua qua:supplierQua){
					Qualification q=new Qualification();
					q.setId(qua.getName());
					q.setName(qua.getName());
					findList.add(q);
				}
			}
			
			
			model.addAttribute("typeList", findList);
			return "ses/sms/supplier_register/supplier_type";
		}
		if(supplier.getSupplierTypeIds().trim().length()!=0){
			String[] types = supplier.getSupplierTypeIds().split(",");
			for(String s:types){
				List<SupplierItem> items = supplierItemService.queryBySupplierAndType( supId, s);
				if(items!=null&&items.size()<1&&s.equals("PRODUCT")){
					model.addAttribute("productError", "productError");
					return "ses/sms/supplier_register/items";
				}
				if(items!=null&&items.size()<1&&s.equals("PROJECT")){
					model.addAttribute("projectError", "projectError");
					return "ses/sms/supplier_register/items";
					}
				if(items!=null&&items.size()<1&&s.equals("SALES")){
					model.addAttribute("sellError", "sellError");
					 return "ses/sms/supplier_register/items";
				}
				if(items!=null&&items.size()<1&&s.equals("SERVICE")){
					model.addAttribute("serverError", "serverError");
					 return "ses/sms/supplier_register/items";
				}
			}
		}
		
			
		
		if(flag.equals("2")) {
			return "ses/sms/supplier_register/items";
		}

		//采购机构页面
		if(flag.equals("5")) {
			// 判断品目合同有没有全部上传
			String supplierId = supplierItem.getSupplierId();
			String[] typeIds = supplierTypeIds.split(",");
			// 总数量
			List < SupplierItem > itemsList = new ArrayList < SupplierItem > ();
			for(String type: typeIds) {
				if(!type.equals("PROJECT")) {
					itemsList.addAll(supplierItemService.findCategoryList(supplierId, type, null));
				}
			}
			// 实际上传数量
			List < UploadFile > filesList;
			boolean isOk = true;
			for(SupplierItem item: itemsList) {
				filesList = uploadService.getFilesOther(item.getId(), DictionaryDataUtil.getId("CATEGORY_ONE_YEAR"), Constant.SUPPLIER_SYS_KEY.toString());
				if(filesList.size() == 0) {
					isOk = false;
					break;
				}
				filesList = uploadService.getFilesOther(item.getId(), DictionaryDataUtil.getId("CATEGORY_TWO_YEAR"), Constant.SUPPLIER_SYS_KEY.toString());
				if(filesList.size() == 0) {
					isOk = false;
					break;
				}
				filesList = uploadService.getFilesOther(item.getId(), DictionaryDataUtil.getId("CATEGORY_THREE_YEAR"), Constant.SUPPLIER_SYS_KEY.toString());
				if(filesList.size() == 0) {
					isOk = false;
					break;
				}
				filesList = uploadService.getFilesOther(item.getId(), DictionaryDataUtil.getId("CTAEGORY_ONE_BIL"), Constant.SUPPLIER_SYS_KEY.toString());
				if(filesList.size() == 0) {
					isOk = false;
					break;
				}
				filesList = uploadService.getFilesOther(item.getId(), DictionaryDataUtil.getId("CTAEGORY_TWO_BIL"), Constant.SUPPLIER_SYS_KEY.toString());
				if(filesList.size() == 0) {
					isOk = false;
					break;
				}
				filesList = uploadService.getFilesOther(item.getId(), DictionaryDataUtil.getId("CATEGORY_THREE_BIL"), Constant.SUPPLIER_SYS_KEY.toString());
				if(filesList.size() == 0) {
					isOk = false;
					break;
				}
			}
			if(!isOk) {
				model.addAttribute("err_contract_files", "还有附件未上传!");
				model.addAttribute("supplierTypeIds", supplierTypeIds);
				model.addAttribute("supplierId", supplierId);
				return "ses/sms/supplier_register/contract";
			}
			HashMap < String, Object > map1 = new HashMap < String, Object > ();
			map1.put("typeName", "1");
			List < PurchaseDep > list1 = purchaseOrgnizationService.findPurchaseDepList(map1);
			for(PurchaseDep org: list1) {
				Area pro = areaService.listById(org.getProvinceId());
				Area city = areaService.listById(org.getCityId());
				if(pro != null && city != null) {
					org.setAddress(pro.getName() + city.getName());
				}
			}
			model.addAttribute("allPurList", list1);
			return "ses/sms/supplier_register/procurement_dep";
		}
		//查询所有的三级品目生产
		List < Category > listPro = getSupplier(supplier.getId(), supplierTypeIds);
		removeSame(listPro);
		//根据品目id查询所有的证书信息
		List < QualificationBean > list3 = supplierService.queryCategoyrId(listPro, 2);

		//查询所有的三级品目销售
		List < Category > listSlae = getSale(supplier.getId(), supplierTypeIds);
		removeSame(listSlae);
		//根据品目id查询所有的证书信息
		List < QualificationBean > saleQua = supplierService.queryCategoyrId(listSlae, 3);

		//查询所有的三级品目服务
		List < Category > listService = getServer(supplier.getId(), supplierTypeIds);
		removeSame(listService);
		//根据品目id查询所有的服务证书信息
		List < QualificationBean > serviceQua = supplierService.queryCategoyrId(listService, 1);

		//生产证书
		List < Qualification > qaList = new ArrayList < Qualification > ();
		List < Qualification > saleList = new ArrayList < Qualification > ();
		List < Qualification > serviceList = new ArrayList < Qualification > ();

		if(list3 != null && list3.size() > 0) {
			for(QualificationBean qb: list3) {
				qaList.addAll(qb.getList());
			}
		}
		//销售
		if(saleQua != null && saleQua.size() > 0) {
			for(QualificationBean qb: saleQua) {
				saleList.addAll(qb.getList());
			}
		}
		//服务
		if(serviceQua != null && serviceQua.size() > 0) {
			for(QualificationBean qb: serviceQua) {
				serviceList.addAll(qb.getList());
			}
		}

		//生产
		StringBuffer sbUp = new StringBuffer("");
		StringBuffer sbShow = new StringBuffer("");
		int len = qaList.size() + 1;
		for(int i = 1; i < len; i++) {
			sbUp.append("pUp" + i + ",");
			sbShow.append("pShow" + i + ",");

		}
		//销售
		int slaelen = saleList.size() + 1;
		for(int i = 1; i < slaelen; i++) {
			sbUp.append("saleUp" + i + ",");
			sbShow.append("saleShow" + i + ",");

		}

		if(serviceList != null && serviceList.size() > 0) {
			int serverlen = serviceList.size() + 1;
			for(int i = 1; i < serverlen; i++) {
				sbUp.append("serverUp" + i + ",");
				sbShow.append("serverShow" + i + ",");

			}
		}
		model.addAttribute("saleUp", sbUp.toString());
		model.addAttribute("saleShow", sbShow.toString());
		model.addAttribute("cateList", list3);
		model.addAttribute("saleQua", saleQua);
		model.addAttribute("serviceQua", serviceQua);
		model.addAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
		model.addAttribute("businessId", supplier.getId());
		 String id = DictionaryDataUtil.getId("SUPPLIER_APTITUD");
		model.addAttribute("typeId", id);

		// 不通过字段的名字
		SupplierAudit s = new SupplierAudit();
		s.setSupplierId(supplier.getId());;
		s.setAuditType("aptitude_page");
		List < SupplierAudit > auditLists = supplierAuditService.selectByPrimaryKey(s);

		StringBuffer errorField = new StringBuffer();
		for(SupplierAudit audit: auditLists) {
			errorField.append(audit.getAuditField() + ",");
		}
		model.addAttribute("audit", errorField);

		// 工程
		String[] typeIds = supplierTypeIds.split(",");
		boolean isEng = false;
		for(String type: typeIds) {
			if(type.equals("PROJECT")) {
				isEng = true;
				break;
			}
		}
		if(isEng) {
			SupplierMatEng matEng = supplierMatEngService.getMatEng(supId);
			List < SupplierItem > listSupplierItems = getProject(supId, "PROJECT");
			List < SupplierCateTree > allTreeList = new ArrayList < SupplierCateTree > ();
			for(SupplierItem item: listSupplierItems) {
				String categoryId = item.getCategoryId();
				SupplierCateTree cateTree = getTreeListByCategoryId(categoryId, item);
				if(cateTree != null && cateTree.getRootNode() != null) {
					cateTree.setItemsId(item.getId());
					cateTree.setDiyLevel(item.getLevel());
                    List < Qualification > typeList = new ArrayList < Qualification > ();
					if(cateTree!=null && cateTree.getCertCode() != null && cateTree.getQualificationType() != null && cateTree.getProName()!=null) {
						List<SupplierAptitute> certEng = supplierAptituteService.queryByCodeAndType(null,matEng.getId(), cateTree.getCertCode(), cateTree.getProName());
//						List < SupplierCertEng > certEng = supplierCertEngService.selectCertEngByCode(cateTree.getCertCode(), supId);
						if(certEng != null && certEng.size() > 0) {
//							String level = supplierCertEngService.getLevel(cateTree.getQualificationType(), cateTree.getCertCode(), supplierService.get(supId).getSupplierMatEng().getId());
//							if(level != null) {
								cateTree.setFileId(certEng.get(0).getId());
//							}
                            //根据选择的资质文件类型获取对应的类型
//                            if(null != certEng.get(0).getCertType()){
//                                Qualification qualification = qualificationService.getQualification(certEng.get(0).getCertType());
//                                if(null != qualification){
//                                    typeList.add(qualification);
//                                }else{
//                                    List<SupplierPorjectQua> supplierQua = supplierPorjectQuaService.queryByNameAndSupplierId(null, item.getSupplierId());
//                                    if(null != supplierQua && !supplierQua.isEmpty()){
//                                        for(SupplierPorjectQua qua:supplierQua){
//                                            if(certEng.get(0).getCertType().equals(qua.getName())){
//                                                Qualification q=new Qualification();
//                                                q.setId(qua.getName());
//                                                q.setName(qua.getName());
//                                                typeList.add(q);
//                                            }
//                                        }
//                                    }
//                                }
//                                cateTree.setTypeList(typeList);
//                            }
						}
					}
					allTreeList.add(cateTree);
				}
			}
			model.addAttribute("allTreeList", allTreeList);
			model.addAttribute("engTypeId", dictionaryDataServiceI.getSupplierDictionary().getSupplierEngCert());
		}
		return "ses/sms/supplier_register/aptitude";

	}

	/**
	 *〈简述〉去重
	 *〈详细描述〉
	 * @author WangHuijie
	 * @param list
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

	/**
	 * 
	 * @Title: getCategory
	 * @Description: 查询供应商勾选的三级品目
	 * author: Li Xiaoxiao 
	 * @param @param code
	 * @param @param supplierId
	 * @param @return     
	 * @return String     
	 * @throws
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

	//生产所有的三级目录
	public List < Category > getSupplier(String supplierId, String code) {
		List < Category > categoryList = new ArrayList < Category > ();
		String[] types = code.split(",");
		for(String s: types) {
			String categoryId = "";
			if(s != null) {
				if(s.equals("PRODUCT")) {
					/*categoryId = DictionaryDataUtil.getId("GOODS");
					List < SupplierItem > category = supplierItemService.getCategory(supplierId, categoryId, s);*/
                    Map<String, Object> searchMap = new HashMap<String, Object>();
                    searchMap.put("supplierId", supplierId);
                    searchMap.put("type", s);
				    List < SupplierItem > category = supplierItemService.findByMap(searchMap);
					for(SupplierItem c: category) {
						Category cate = categoryService.selectByPrimaryKey(c.getCategoryId());
						if (cate == null) {
						    cate = new Category();
						    DictionaryData data = DictionaryDataUtil.findById(c.getCategoryId());
						    cate.setId(data.getId());
						    cate.setParentId(data.getId());
						    cate.setName(data.getName());
						} else {
							//供应商中间表的id和资质证书的id
						    cate.setParentId(c.getId());
						}
						categoryList.add(cate);
					}
				}
			}
		}
		return categoryList;
	}
	//销售
	public List < Category > getSale(String supplierId, String code) {
		List < Category > categoryList = new ArrayList < Category > ();

		String[] types = code.split(",");
		for(String s: types) {
			String categoryId = "";
			if(s != null) {
				if(s.equals("SALES")) {
					/*categoryId = DictionaryDataUtil.getId("GOODS");
					List < SupplierItem > category = supplierItemService.getCategory(supplierId, categoryId, s);*/
				    Map<String, Object> searchMap = new HashMap<String, Object>();
                    searchMap.put("supplierId", supplierId);
                    searchMap.put("type", s);
                    List < SupplierItem > category = supplierItemService.findByMap(searchMap);
					for(SupplierItem c: category) {
					    Category cate = categoryService.selectByPrimaryKey(c.getCategoryId());
                        if (cate == null) {
                            cate = new Category();
                            DictionaryData data = DictionaryDataUtil.findById(c.getCategoryId());
                            cate.setId(data.getId());
                            cate.setParentId(data.getId());
                            cate.setName(data.getName());
                        } else {
                            cate.setParentId(c.getId());
                        }
                        categoryList.add(cate);
					}
				}
			}
		}
		return categoryList;
	}

	//工程
	public List < SupplierItem > getProject(String supplierId, String code) {
		String[] types = code.split(",");
		for(String s: types) {
			String categoryId = "";
			if(s != null) {
				if(s.equals("PROJECT")) {
					categoryId = DictionaryDataUtil.getId("PROJECT");
                    return supplierItemService.getCategoryOther(supplierId, categoryId, s);
				}
			}

		}
		return null;
	}

	//服务
	public List < Category > getServer(String supplierId, String code) {
		List < Category > categoryList = new ArrayList < Category > ();

		String[] types = code.split(",");
		for(String s: types) {
			String categoryId = "";
			if(s != null) {
				if(s.equals("SERVICE")) {
					/*categoryId = DictionaryDataUtil.getId("SERVICE");
					List < SupplierItem > category = supplierItemService.getCategory(supplierId, categoryId, s);*/
				    Map<String, Object> searchMap = new HashMap<String, Object>();
                    searchMap.put("supplierId", supplierId);
                    searchMap.put("type", s);
                    List < SupplierItem > category = supplierItemService.findByMap(searchMap);
					for(SupplierItem c: category) {
					    Category cate = categoryService.selectByPrimaryKey(c.getCategoryId());
                        if (cate == null) {
                            cate = new Category();
                            DictionaryData data = DictionaryDataUtil.findById(c.getCategoryId());
                            cate.setId(data.getId());
                            cate.setParentId(data.getId());
                            cate.setName(data.getName());
                        } else {
                            cate.setParentId(c.getId());
                        }
                        categoryList.add(cate);
					}
				}
			}
		}
		return categoryList;
	}
	
	
	
    /**
     * 
    * @Title: validateFile
    * @Description: 校验资质文件是否上传
    * author: Li Xiaoxiao 
    * @param @param supplierId
    * @param @param supplierTypeIds
    * @param @return     
    * @return String     
    * @throws
     */
    @RequestMapping("/isaAtitude")
    @ResponseBody
    public String validateFile(String supplierId,String supplierTypeIds){
		Integer count=0;
		//查询所有的三级品目生产
		List < Category > listPro = getSupplier(supplierId, supplierTypeIds);
		removeSame(listPro);
		//根据品目id查询所有的证书信息
		List < QualificationBean > list3 = supplierService.queryCategoyrId(listPro, 2);

		//查询所有的三级品目销售
		List < Category > listSlae = getSale(supplierId, supplierTypeIds);
		removeSame(listSlae);
		//根据品目id查询所有的证书信息
		List < QualificationBean > saleQua = supplierService.queryCategoyrId(listSlae, 3);

		//查询所有的三级品目服务
		List < Category > listService = getServer(supplierId, supplierTypeIds);
		removeSame(listService);
		//根据品目id查询所有的服务证书信息
		List < QualificationBean > serviceQua = supplierService.queryCategoyrId(listService, 1);
		
		List<QualificationBean> list=new ArrayList<QualificationBean>();
		list.addAll(list3);
		list.addAll(saleQua);
		list.addAll(serviceQua);
		
		List<Qualification> qlist=new ArrayList<Qualification>();
		for(QualificationBean qb:list){
			List<Qualification> qbs = qb.getList();
			for(Qualification q:qbs){
				qlist.add(q);
				String id = DictionaryDataUtil.getId("SUPPLIER_APTITUD");
				List<UploadFile> files = uploadService.getFilesOther(q.getFlag(), id, "1");
				if(files!=null&&files.size()>0){
					count++;
				}
			}
		
		}
		Integer size = qlist.size();
		if(!count.equals(size)){
			return "0";
		}
    	return "1";
    }
    
    

}