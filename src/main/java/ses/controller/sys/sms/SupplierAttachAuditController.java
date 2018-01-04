package ses.controller.sys.sms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;

import common.constant.Constant;
import common.utils.DateUtils;
import common.utils.ListSortUtil;
import ses.formbean.QualificationBean;
import ses.model.bms.Area;
import ses.model.bms.Category;
import ses.model.bms.DictionaryData;
import ses.model.bms.Qualification;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierAddress;
import ses.model.sms.SupplierAptitute;
import ses.model.sms.SupplierCateTree;
import ses.model.sms.SupplierCertEng;
import ses.model.sms.SupplierCertPro;
import ses.model.sms.SupplierCertSell;
import ses.model.sms.SupplierCertServe;
import ses.model.sms.SupplierEngQua;
import ses.model.sms.SupplierFinance;
import ses.model.sms.SupplierItem;
import ses.model.sms.SupplierMatEng;
import ses.model.sms.SupplierMatPro;
import ses.model.sms.SupplierMatSell;
import ses.model.sms.SupplierMatServe;
import ses.model.sms.SupplierPorjectQua;
import ses.model.sms.review.SupplierAttachAudit;
import ses.service.bms.AreaServiceI;
import ses.service.bms.CategoryService;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.bms.QualificationService;
import ses.service.sms.SupplierAddressService;
import ses.service.sms.SupplierAttachAuditService;
import ses.service.sms.SupplierItemService;
import ses.service.sms.SupplierPorjectQuaService;
import ses.service.sms.SupplierService;
import ses.util.DictionaryDataUtil;

/**
 * 供应商附件审核
 */
@Controller
@Scope("prototype")
@RequestMapping("/supplierAttachAudit")
public class SupplierAttachAuditController {

	@Autowired
	private AreaServiceI areaService; //地区
	
	@Autowired
	private SupplierAddressService supplierAddressService; //生产经营地址
	
	@Autowired
	private DictionaryDataServiceI dictionaryDataServiceI;
	
	@Autowired
	private SupplierService supplierService;
	
	@Autowired
	private SupplierItemService supplierItemService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private SupplierPorjectQuaService supplierPorjectQuaService;
	
	@Autowired
	private QualificationService qualificationService;
	
	@Autowired
	private SupplierAttachAuditService supplierAttachAuditService;
	
	/**
	 * 生产或经营地址的房产证明或租赁协议
	 */
	@RequestMapping(value = "/address")
	public String address(String supplierId, Model model){
		List < SupplierAddress > supplierAddress = supplierAddressService.queryBySupplierId(supplierId);
		List < Area > province = areaService.findRootArea();
		if(!supplierAddress.isEmpty() && supplierAddress.size() > 0 ){
			for(Area a: province) {
				for(SupplierAddress s: supplierAddress) {
					if(a.getId().equals(s.getParentId())) {
						s.setParentName(a.getName());
					}
				}
			}
		}
		model.addAttribute("supplierAddress", supplierAddress);
		
		model.addAttribute("supplierDictionaryData", dictionaryDataServiceI.getSupplierDictionary());
		model.addAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);

		return "ses/sms/supplier_attach/address";
	}
	
	/**
	 *近三年审计报告书
	 */
	@RequestMapping(value = "/finance")
	public String finance (String supplierId, Model model){
		List < SupplierFinance > supplierFinance = supplierService.get(supplierId, 1).getListSupplierFinances();
		if(supplierFinance != null && supplierFinance.size() > 0){
			// 排序
			ListSortUtil<SupplierFinance> sortList = new ListSortUtil<SupplierFinance>();
			sortList.sort(supplierFinance, "year", "asc");
			// 如果近三年财务信息超过三年，则取最近三年
			if(supplierFinance.size() > 3){
				Iterator<SupplierFinance> it = supplierFinance.iterator();
				int i = supplierFinance.size();
				while(it.hasNext()){
					it.next();
					if(i > 3){
						it.remove();
					}
					i--;
				}
			}
		}
		model.addAttribute("finance", supplierFinance);
		model.addAttribute("supplierDictionaryData", dictionaryDataServiceI.getSupplierDictionary());
		model.addAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
		return "ses/sms/supplier_attach/finance";
	}
	
	/**
	 *省级地域的合同主要页
	 */
	@RequestMapping(value ="/businessScope")
	public String businessScope(String supplierId, Model model){
		Supplier supplier = supplierService.get(supplierId, 2);
		SupplierMatEng supplierMatEng = supplier.getSupplierMatEng();
		//承揽业务范围
		String businessScope = supplierMatEng.getBusinessScope();
		List<Area> existenceArea = new ArrayList<>();
		if(StringUtils.isNotBlank(businessScope)){
			String[] areaIds = businessScope.split(",");
			for(String areaId : areaIds){
				Area area = areaService.listById(areaId);
				existenceArea.add(area);
			}
			model.addAttribute("areas", existenceArea);
		}
		model.addAttribute("supplierDictionaryData", dictionaryDataServiceI.getSupplierDictionary());
		model.addAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
		model.addAttribute("supplierId", supplierId);
		return "ses/sms/supplier_attach/businessScope";
	}
	
	/**
	 * 近三年销售合同主要页及相应合同的银行收款进帐单
	 */
	@RequestMapping(value = "/contract")
	public String contract(String supplierId, Model model){
		Supplier supplier = supplierService.get(supplierId, 2);
    	model.addAttribute("supplierTypeIds", supplier.getSupplierTypeIds());
		model.addAttribute("supplierId", supplierId);

		return "ses/sms/supplier_attach/contract";
	}
	
	@RequestMapping("/ajaxContract")
	public String ajaxContract(String supplierId, Model model, String supplierTypeId, Integer pageNum) {
		// 年份
		int referenceYear = 0;
		Supplier supplier = supplierService.selectById(supplierId);
		if(!"-1".equals(supplier.getStatus()+"")){
			referenceYear = DateUtils.getCurrentYear(supplier.getFirstSubmitAt());
		}
		List < Integer > years = supplierService.getLastThreeYear(referenceYear);
		model.addAttribute("years", years);
		
		//合同
		String id1 = DictionaryDataUtil.getId("CATEGORY_ONE_YEAR") + "_" + years.get(0);
		String id2 = DictionaryDataUtil.getId("CATEGORY_TWO_YEAR") + "_" + years.get(1);
		String id3 = DictionaryDataUtil.getId("CATEGORY_THREE_YEAR") + "_" + years.get(2);
		//账单
		String id4 = DictionaryDataUtil.getId("CTAEGORY_ONE_BIL") + "_" + years.get(0);
		String id5 = DictionaryDataUtil.getId("CTAEGORY_TWO_BIL") + "_" + years.get(1);
		String id6 = DictionaryDataUtil.getId("CATEGORY_THREE_BIL") + "_" + years.get(2);

		List < SupplierItem > itemsList = supplierItemService.findCategoryList(supplierId, supplierTypeId, pageNum == null ? 1 : pageNum);
		// 查询已选中的节点信息
		
		List < SupplierCateTree > allTreeList = new ArrayList < SupplierCateTree > ();
		for(SupplierItem item: itemsList) {
			String categoryId = item.getCategoryId();
			SupplierCateTree cateTree = getTreeListByCategoryId(categoryId, null);
			
			if(cateTree != null && cateTree.getRootNode() != null) {
				cateTree.setItemsId(item.getId());
				allTreeList.add(cateTree);
			}
		}
		for(SupplierCateTree item: allTreeList) {
			item.setOneContract(id1);
			item.setTwoContract(id2);
			item.setThreeContract(id3);
			item.setOneBil(id4);
			item.setTwoBil(id5);
			item.setThreeBil(id6);
		}
		for(SupplierCateTree cate: allTreeList) {
			cate.setRootNode(cate.getRootNode() == null ? "" : cate.getRootNode());
			cate.setFirstNode(cate.getFirstNode() == null ? "" : cate.getFirstNode());
			cate.setSecondNode(cate.getSecondNode() == null ? "" : cate.getSecondNode());
			cate.setThirdNode(cate.getThirdNode() == null ? "" : cate.getThirdNode());
			cate.setFourthNode(cate.getFourthNode() == null ? "" : cate.getFourthNode());
			String typeName = "";
			if(supplierTypeId.equals("PRODUCT")) {
				typeName = "生产";
			} else if(supplierTypeId.equals("SALES")) {
				typeName = "销售";
			}
			cate.setRootNode(cate.getRootNode() + typeName);
		}
		
		// 分页,pageSize == 10
		PageInfo < SupplierItem > pageInfo = new PageInfo < SupplierItem > (itemsList);
		model.addAttribute("result", pageInfo);
		model.addAttribute("contract", allTreeList);
		model.addAttribute("supplierTypeId", supplierTypeId);
		model.addAttribute("supplierId", supplierId);
		// 供应商附件sysKey参数
		model.addAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
		return "ses/sms/supplier_attach/ajax_contract";
	}
	
	/**
	 * 生产设施设备的购置凭证
	 * @param supplierId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/itemQua")
	public String itemQua(String supplierId, Model model){
		Supplier supplier = supplierService.get(supplierId, 4);
		String supplierTypeIds = supplier.getSupplierTypeIds();
		model.addAttribute("supplierTypeIds", supplierTypeIds);

		//查询所有的三级品目生产
		List < Category > list2 = getSupplier(supplierId, supplierTypeIds);
		if(!list2.isEmpty()){
			removeSame(list2);
		}

		//查询所有的三级品目生产
		List < Category > listPro = getSupplier(supplierId, supplierTypeIds);
		removeSame(listPro);
		//根据品目id查询所有的证书信息
		List < QualificationBean > list3 = supplierService.getQuaList(listPro, 2);

		//查询所有的三级品目销售
		List < Category > listSlae = getSale(supplierId, supplierTypeIds);
		removeSame(listSlae);
		//根据品目id查询所有的证书信息
		List < QualificationBean > saleQua = supplierService.getQuaList(listSlae, 3);

		//查询所有的三级品目服务
		List < Category > listService = getServer(supplierId, supplierTypeIds);
		removeSame(listService);
		//根据品目id查询所有的服务证书信息
		List < QualificationBean > serviceQua = supplierService.getQuaList(listService, 2);

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
		model.addAttribute("businessId", supplierId);
		String id = DictionaryDataUtil.getId("SUPPLIER_APTITUD");
		model.addAttribute("typeId", id);

		String provinceName = "";
		String cityName = "";
        try {
            Area area = areaService.listById(supplier.getAddress());
            if (area != null) {
                cityName = area.getName();
                Area area1 = areaService.listById(area.getParentId());
                if (area1 != null) {
                    provinceName = area1.getName();
                }
            }
            supplier.setAddress(provinceName + cityName);
        } catch (Exception e) {
            e.printStackTrace();
        }
		return "ses/sms/supplier_attach/itemQua";
	}
	
	/**
	 * 相关准入、认证资质证书
	 * @return
	 */
	@RequestMapping(value = "/certOther")
	public String certOther(String supplierId, Model model){
   		//文件
		model.addAttribute("supplierDictionaryData", dictionaryDataServiceI.getSupplierDictionary());
		model.addAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
   		
		Supplier supplier = supplierService.get(supplierId, 2);
        model.addAttribute("suppliers", supplier);
		if(supplier != null){
			// 勾选的供应商类型
			model.addAttribute("supplierTypeCode", supplier.getSupplierTypeIds());
			
			List < DictionaryData > gcfwList = DictionaryDataUtil.find(6);// 物资/工程/服务
			for(int i = 0; i < gcfwList.size(); i++) {
				DictionaryData dd = gcfwList.get(i);
				String code = dd.getCode();
				if(code.equals("GOODS")) {// 除去物资
					gcfwList.remove(dd);
				}
			}
			model.addAttribute("gcfwList", gcfwList);
			List < DictionaryData > scxsList = DictionaryDataUtil.find(8);// 物资生产/物资销售
			model.addAttribute("scxsList", scxsList);
			
			/**
			 * 生产
			 */
			SupplierMatPro supplierMatPro = supplier.getSupplierMatPro();
			if(supplierMatPro != null){
				//资质资格证书信息
				List < SupplierCertPro > materialProduction = supplierMatPro.getListSupplierCertPros();
				for(int i = 0; i < materialProduction.size() - 1; i++) {
					for(int j = materialProduction.size() - 1; j > i; j--) {
						if(materialProduction.get(j).getId().equals(materialProduction.get(i).getId())) {
							materialProduction.remove(j);
						}
					}
				}
				model.addAttribute("materialProduction", materialProduction);
				model.addAttribute("supplierMatPros", supplierMatPro);
			}

			/**
			 * 销售
			 */
			//组织机构和人员
			SupplierMatSell supplierMatSell = supplier.getSupplierMatSell();
			if(supplierMatSell != null){
				//资质资格证书
				List < SupplierCertSell > supplierCertSell = supplierMatSell.getListSupplierCertSells();
				for(int i = 0; i < supplierCertSell.size() - 1; i++) {
					for(int j = supplierCertSell.size() - 1; j > i; j--) {
						if(supplierCertSell.get(j).getId().equals(supplierCertSell.get(i).getId())) {
							supplierCertSell.remove(j);
						}
					}
				}
				model.addAttribute("supplierCertSell", supplierCertSell);
				model.addAttribute("supplierMatSells", supplierMatSell);
			}
			
			/**
			 * 工程
			 */
			//组织结构
			SupplierMatEng supplierMatEng = supplier.getSupplierMatEng();
			if(supplierMatEng != null){
				model.addAttribute("supplierMatEngs", supplierMatEng);
				//资质证书信息
				List < SupplierEngQua > supplierEngQuas = supplierMatEng.getListSupplierEngQuas();
				for(int i = 0; i < supplierEngQuas.size() - 1; i++) {
					for(int j = supplierEngQuas.size() - 1; j > i; j--) {
						if(supplierEngQuas.get(j).getId().equals(supplierEngQuas.get(i).getId())) {
							supplierEngQuas.remove(j);
						}
					}
				}
				model.addAttribute("supplierEngQuas", supplierEngQuas);
			}
			
			/**
			 * 服务
			 */
			//组织结构和人员
			SupplierMatServe supplierMatSe = supplier.getSupplierMatSe();
			if(supplierMatSe != null){
				//资质证书信息
				List < SupplierCertServe > supplierCertSe = supplierMatSe.getListSupplierCertSes();
				for(int i = 0; i < supplierCertSe.size() - 1; i++) {
					for(int j = supplierCertSe.size() - 1; j > i; j--) {
						if(supplierCertSe.get(j).getId().equals(supplierCertSe.get(i).getId())) {
							supplierCertSe.remove(j);
						}
					}
				}
				model.addAttribute("supplierCertSes", supplierCertSe);
				model.addAttribute("supplierMatSes", supplierMatSe);
			}
		}
   		
   		String provinceName = "";
   		String cityName = "";
        try {
            Area area = areaService.listById(supplier.getAddress());
            if (area != null) {
                cityName = area.getName();
                Area area1 = areaService.listById(area.getParentId());
                if (area1 != null) {
                    provinceName = area1.getName();
                }
            }
            supplier.setAddress(provinceName + cityName);
        } catch (Exception e) {
            e.printStackTrace();
        }
		return "ses/sms/supplier_attach/certOther";
	}
	
	/**
	 * 工程资质证书
	 * @return
	 */
	@RequestMapping(value = "/certEng")
	public String certEng(String supplierId, Model model){
		//文件
		model.addAttribute("supplierDictionaryData", dictionaryDataServiceI.getSupplierDictionary());
		model.addAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
		Supplier supplier = supplierService.get(supplierId, 2);
		SupplierMatEng supplierMatEng = supplier.getSupplierMatEng();
		if(supplierMatEng != null){
			model.addAttribute("supplierMatEngs", supplierMatEng);
			//资质资格证书信息
			List < SupplierCertEng > supplierCertEngs = supplierMatEng.getListSupplierCertEngs();
			for(int i = 0; i < supplierCertEngs.size() - 1; i++) {
				for(int j = supplierCertEngs.size() - 1; j > i; j--) {
					if(supplierCertEngs.get(j).getId().equals(supplierCertEngs.get(i).getId())) {
						supplierCertEngs.remove(j);
					}
				}
			}
			model.addAttribute("supplierCertEngs", supplierCertEngs);

			//资质资格信息
			List < SupplierAptitute > supplierAptitute = supplierMatEng.getListSupplierAptitutes();
			for(int i = 0; i < supplierAptitute.size() - 1; i++) {
				for(int j = supplierAptitute.size() - 1; j > i; j--) {
					if(supplierAptitute.get(j).getId().equals(supplierAptitute.get(i).getId())) {
						supplierAptitute.remove(j);
					}
				}
			}
			model.addAttribute("supplierAptitutes", supplierAptitute);
			//资质类型
			model.addAttribute("typeList", qualificationService.findList(null, Integer.MAX_VALUE, null, 4));
			//资质登记
			List < DictionaryData > businessList = DictionaryDataUtil.find(31);
			for(DictionaryData data : businessList){
				for(SupplierAptitute a : supplierAptitute){
					if(data.getId().equals(a.getAptituteLevel())){
						a.setAptituteLevel(data.getName());
					}
				}
			}
		}
		return "ses/sms/supplier_attach/certEng";
	}
	/**
	 * 销售
	 * @param supplierId
	 * @param code
	 * @return
	 */
 	public List < Category > getSale(String supplierId, String code) {
 		List < Category > categoryList = new ArrayList < Category > ();

 		String[] types = code.split(",");
 		for(String s: types) {
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
	
 	/**
 	 * 服务
 	 * @param supplierId
 	 * @param code
 	 * @return
 	 */
 	public List < Category > getServer(String supplierId, String code) {
 		List < Category > categoryList = new ArrayList < Category > ();

 		String[] types = code.split(",");
 		for(String s: types) {
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
     * @Title: removeSame
     *  
     * @date 2017-1-4 下午7:23:33  
     * @Description:去重
     * @param @param list      
     * @return void
     */
   public void removeSame(List<Category> list) {
       for (int i = 0; i < list.size() - 1; i++) {
           for (int j = list.size() - 1; j > i; j--) {
               if (list.get(j).getId().equals(list.get(i).getId())) {
                   list.remove(j);
               }
           }
       }
    }
   
	/**
	 * 生产所有的三级目录
	 * @param supplierId
	 * @param code
	 * @return
	 */
 	public List < Category > getSupplier(String supplierId, String code) {
 		List < Category > categoryList = new ArrayList < Category > ();
 		String[] types = code.split(",");
 		for(String s: types) {
 			if(s != null) {
 				if(s.equals("PRODUCT")) {
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
	
	/**
	 *〈简述〉查询品目信息
	 *〈详细描述〉
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
				        	dd.setId(projectData.get(0).getCertLevel());
				        	dd.setName(projectData.get(0).getCertLevel());
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
			List < QualificationBean > type = supplierService.getQuaList(cateList, 4);
			List < Qualification > typeList = new ArrayList < Qualification > ();
			if(type != null && type.size() > 0 && type.get(0).getList() != null && type.get(0).getList().size() > 0) {
				typeList = type.get(0).getList();
			}
			List<SupplierPorjectQua> supplierQua = supplierPorjectQuaService.queryByNameAndSupplierId(null, item.getSupplierId());
			for(SupplierPorjectQua qua:supplierQua){
				Qualification q=new Qualification();
				q.setId(qua.getName());
				q.setName(qua.getName());
				typeList.add(q);
			}
			
			cateTree.setTypeList(typeList);
		}
		return cateTree;
	}
	
	/**
	 *〈简述〉获取当前节点的所有父级节点(包括根节点)
	 *〈详细描述〉
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
	 * 保存审核信息
	 * @param supplierAttachAudit
	 */
	@RequestMapping(value = "/saveAuditInformation")
	@ResponseBody
	public void saveAuditInformation (SupplierAttachAudit supplierAttachAudit){
		supplierAttachAuditService.saveAuditInformation(supplierAttachAudit);
	}
}
