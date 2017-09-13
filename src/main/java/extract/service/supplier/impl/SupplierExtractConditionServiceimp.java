/**
 * 
 */
package extract.service.supplier.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.bms.DictionaryDataMapper;
import ses.model.bms.Category;
import ses.model.bms.CategoryTree;
import ses.model.bms.DictionaryData;
import ses.model.sms.Supplier;
import ses.service.bms.CategoryService;
import ses.util.DictionaryDataUtil;
import ses.util.PropUtil;
import bss.model.ppms.Packages;
import bss.model.ppms.Project;
import bss.service.ppms.PackageService;
import bss.service.ppms.ProjectService;

import com.github.pagehelper.PageHelper;

import extract.dao.supplier.ExtractConditionRelationMapper;
import extract.dao.supplier.SupplierExtractConditionMapper;
import extract.dao.supplier.SupplierExtractRelateResultMapper;
import extract.model.supplier.ExtractConditionRelation;
import extract.model.supplier.SupplierConType;
import extract.model.supplier.SupplierExtractCondition;
import extract.service.supplier.SupplierExtractConditionService;


/**
 * @Description:
 *	 
 * @author Wang Wenshuai
 * @version 2016年9月28日上午10:39:57
 * @since  JDK 1.7
 */
@Service
public class SupplierExtractConditionServiceimp  implements SupplierExtractConditionService {

  /** SCCUESS */
  private static final String SUCCESS = "SUCCESS";
  /** ERROR */
  private static final String ERROR = "ERROR";

  @Autowired
  SupplierExtractConditionMapper supplierConditionMapper;

  @Autowired
  SupplierExtractRelateResultMapper supplierExtRelateMapper;

  @Autowired
  PackageService packageService;
  
  @Autowired
  private ProjectService projectService;
  
  @Autowired
  private ExtractConditionRelationMapper extractConditionRelationMapper;//条件关联表
  
  @Autowired
  private DictionaryDataMapper dictionaryDataMapper;
  
  @Autowired
  private CategoryService categoryService;
  
  
  
  /**
   * @Description:添加
   *
   * @author Wang Wenshuai
   * @version 2016年9月28日 上午10:35:49  
   * @param @param condition      
   * @return void
   */
  @Override
  public void insert(SupplierExtractCondition condition){
	  condition.setCreatedAt(new Date());
    supplierConditionMapper.insertSelective(condition);
  }

  /**
   * @Description:修改
   *
   * @author Wang Wenshuai
   * @version 2016年9月28日 上午10:36:05  
   * @param @param condition      
   * @return void
   */
  public void update(SupplierExtractCondition condition){
	  
	//存储地区
	//extConditionAreaMapper.insertSupplierArea(condition);
	//存储品目 类型
	//类别是4种，每种类别对应多种品目 这里面会有关联关系前台传进来的数据是
	//
	//extTypeCategoryMapper.insertSupplierArea(condition);
    supplierConditionMapper.updateByPrimaryKeySelective(condition);
  }

  /**
   * @Description:集合查询
   *
   * @author Wang Wenshuai
   * @version 2016年9月28日 上午10:36:20  
   * @param @param condition
   * @param @return      
   * @return List<ExpExtCondition>
   */
  public List<SupplierExtractCondition> list(SupplierExtractCondition condition,Integer pageNum){
    if(pageNum != null && pageNum!=0){
      PageHelper.startPage(pageNum,PropUtil.getIntegerProperty("pageSize"));
    }
    return supplierConditionMapper.list(condition);
  }

  /**
   * @Description:获取单个
   *
   * @author Wang Wenshuai
   * @version 2016年9月28日 下午3:17:07  
   * @param @param condition
   * @param @return      
   * @return ExpExtCondition
   */
  @Override
  public SupplierExtractCondition show(String id) {
    return supplierConditionMapper.selectByPrimaryKey(id);
  }

  /**
   * 
   *〈简述〉更具关联包id查询是否有未抽取的条件
   *〈详细描述〉
   * @author Wang Wenshuai
   * @param id
   * @return
   */
  @Override
  public String getCount(String[] packId) {
    String packageId = "";
    Packages pack = new Packages();
    pack.setId(packId[0]);
    List<Packages> find = packageService.find(pack);
    HashMap<String, Object> map = new HashMap<String, Object>();
    map.put("projectId",find.get(0).getProjectId());
    List<Packages> findPackageById = packageService.findPackageById(map);
    for (Packages packages : findPackageById) {
      Integer count = supplierConditionMapper.getCount(packages.getId());
      if(count > 0 ){
        packageId += packages.getId()+ ",";
      }
    }
    return packageId;

  }

  /**
   * 直接删除查询不出结果的查询条件
   * @return 
   * @see ses.service.sms.SupplierConditionService#delById(java.lang.String)
   */
  @Override
  public Integer delById(String Id) {
    return supplierConditionMapper.deleteByPrimaryKey(Id);
  }

  /**
   * 返回满足条件的供应商 并存储抽取条件
   * @see ses.service.sms.SupplierConditionService#selectLikeSupplier(ses.model.sms.SupplierCondition, ses.model.sms.SupplierConType)
   */
  @Override
  public Map<String, Map<String, Object>> selectLikeSupplier(SupplierExtractCondition condition, SupplierConType conType,int type) {
	  
	 //此方法为公共方法 查询满足供应商数量 和供应商抽取结果  0 表示查询数量 1 表示 抽取 
	 //去除已经抽取到的供应商
	 if(StringUtils.isNotEmpty(condition.getRecordId())){
		 List<String> supplierIds = supplierExtRelateMapper.selectSupplierIdListByRecordId(condition.getRecordId());
		 if(null != supplierIds){
			 condition.setSupplierIds(supplierIds);
		 }
	 }
	 
	  
	 Map<String, Object> list = new HashMap<>();
	 Map<String, Object> count = new HashMap<>();
	 Map<String, Map<String, Object>> map = new HashMap<>();
	//   conType=condition.getSupplierConType();

	 
	 //若是类型为空说明只对地区进行查询
	if(StringUtils.isBlank(condition.getSupplierTypeCode())){
		count.put("area",supplierExtRelateMapper.listExtractionExpertCount(condition));
		if(type == 1){
			List<Supplier> selectAllExpert = supplierExtRelateMapper.listExtractionExpert(condition);//getAllSupplier(null);
			//存储
			saveOrUpdateCondition(condition, null);
		}
	}else if(condition.getSupplierTypeCodes().length>1){
		//按类别查询
		String[] supplierTypeCodes = condition.getSupplierTypeCodes();
		for (String code : supplierTypeCodes) {
			
			if(type == 1){
				List<Supplier> selectAllExpert = supplierExtRelateMapper.listExtractionExpert(condition);
				list.put(code, selectAllExpert);
				//存储
				saveOrUpdateCondition(condition, conType);
			}else{
				count.put(code+"Count", supplierExtRelateMapper.listExtractionExpertCount(condition));
				condition.setSupplierTypeCode(code);
			}
		}
	}else if(condition.getSupplierTypeCodes().length>0){
		count.put(condition.getSupplierTypeCode()+"Count", supplierExtRelateMapper.listExtractionExpertCount(condition));
		if(type==1){
			List<Supplier> selectAllExpert = supplierExtRelateMapper.listExtractionExpert(condition);
			list.put(condition.getSupplierTypeCode(), selectAllExpert);
			//存储
			saveOrUpdateCondition(condition, conType);
		}
	}
	    
	if(StringUtils.isNotEmpty(conType.getProductCategoryIds())){
		//SupplierCondition pdsc = new SupplierCondition("PRODUCT", conType.getProductIsMulticondition(), conType.getProductCategoryIds(), conType.getProductLevel());
		condition.setSupplierTypeCode("PRODUCT");
		condition.setIsMulticondition(conType.getProductIsMulticondition());
		condition.setCategoryId(conType.getProductCategoryIds());
		condition.setLevelTypeId(conType.getProductLevel());
		condition.setExtractNum(conType.getProductExtractNum());
		count.put("PRODUCTCount", supplierExtRelateMapper.listExtractionExpertCount(condition));
		if(type == 1){
			List<Supplier> products = supplierExtRelateMapper.listExtractionExpert(condition);
			list.put("PRODUCT", products);
			//存储
			saveOrUpdateCondition(condition, conType);
		}
	}
	if(StringUtils.isNotEmpty(conType.getProjectCategoryIds())){
		//SupplierCondition pjsc = new SupplierCondition("PROJECT", conType.getProjectIsMulticondition(), conType.getProjectCategoryIds(), conType.getProjectLevel());
	    condition.setSupplierTypeCode("PROJECT");
		condition.setIsMulticondition(conType.getProjectIsMulticondition());
		condition.setCategoryId(conType.getProjectCategoryIds());
		condition.setLevelTypeId(conType.getProjectLevel());
		condition.setExtractNum(conType.getProjectExtractNum());
		count.put("PROJECTCount", supplierExtRelateMapper.listExtractionExpertCount(condition));
		if(type == 1){
			List<Supplier> projects = supplierExtRelateMapper.listExtractionExpert(condition);
			list.put("PROJECT", projects);
			//存储
			saveOrUpdateCondition(condition, conType);
		}
	}
	if(StringUtils.isNotEmpty(conType.getSalesCategoryIds())){
		//SupplierCondition sasc = new SupplierCondition("SALES", conType.getSaleIsMulticondition(), conType.getSaleCategoryIds(), conType.getSaleLevel());
	    condition.setSupplierTypeCode("SALES");
		condition.setIsMulticondition(conType.getSaleIsMulticondition());
		condition.setCategoryId(conType.getSalesCategoryIds());
		condition.setLevelTypeId(conType.getSalesLevel());
		condition.setExtractNum(conType.getSalesExtractNum());
		count.put("SALESCount", supplierExtRelateMapper.listExtractionExpertCount(condition));
		if(type == 1){
			List<Supplier> sales = supplierExtRelateMapper.listExtractionExpert(condition);
			list.put("SALES", sales);
			//存储
			saveOrUpdateCondition(condition, conType);
		}
	}
	if(StringUtils.isNotEmpty(conType.getServiceCategoryIds())){
		//SupplierCondition svsc = new SupplierCondition("SERVICE", conType.getServiceIsMulticondition(), conType.getServiceCategoryIds(), conType.getServiceLevel());
		condition.setSupplierTypeCode("SERVICE");
		condition.setIsMulticondition(conType.getServiceIsMulticondition());
		condition.setCategoryId(conType.getServiceCategoryIds());
		condition.setLevelTypeId(conType.getServiceLevel());
		condition.setExtractNum(conType.getServiceExtractNum());
		count.put("SERVICECount",supplierExtRelateMapper.listExtractionExpertCount(condition));
		if(type == 1){
			List<Supplier> services = supplierExtRelateMapper.listExtractionExpert(condition);
			list.put("SERVICE", services.get(0));
			//存储
			saveOrUpdateCondition(condition, conType);
		}
	}
	
	map.put("count", count);
	map.put("list", list);
	return map;
  }


  /**
   * 本次抽取是否完成
   * @see ses.service.ems.ExpExtConditionService#isFinish()
   */
  @Override
  public String isFinish(SupplierExtractCondition condition) {
    List<SupplierExtractCondition> list = supplierConditionMapper.list(condition);
    if (list != null && list.size() !=0 ){
      return SUCCESS;
    }else{
      return ERROR;
    }

  }

  /**
   * 供应商类型
   * @see ses.service.sms.SupplierConditionService#supplierTypeList()
   */
  @Override
  public List<DictionaryData> supplierTypeList(String projectId) {
    
	//查询项目类型
	//TODO 待优化
	Project curruntProject = null;
	List<DictionaryData> supplierTypes = new ArrayList<>();
	if(StringUtils.isNotBlank(projectId)){
		//根据项目类型 设置要选择的供应商类型
		curruntProject = projectService.selectById(projectId);
		if(null!=curruntProject && StringUtils.isNotBlank(curruntProject.getPlanType())){
			if("FC9528B2E74F4CB2A9E74735A8D6E90A".equals(curruntProject.getPlanType())){
				return DictionaryDataUtil.find(8);
			}else{
				supplierTypes.add(DictionaryDataUtil.findById(curruntProject.getPlanType()));
				return supplierTypes;
			}
		}
	}
	
	supplierTypes = DictionaryDataUtil.find(6);
    
   for(int i=0;i<supplierTypes.size();i++){
      String code = supplierTypes.get(i).getCode();
      if(code.equals("GOODS")){
        supplierTypes.remove(supplierTypes.get(i));
      }
    }
    List<DictionaryData> wlist =DictionaryDataUtil.find(8);
    supplierTypes.addAll(wlist);
    return supplierTypes;
  }

  /**
   * 添加包信息
   *〈简述〉
   *〈详细描述〉
   * @author Wang Wenshuai
   * @return
   */
  public String addPackage(String packagesName,String projectId){
    String packagesId = "";
    if (packagesName != null && !"".equals(packagesName)){
      String[] array = packagesName.split(",");
      for (String name : array) {
        Packages pack = new Packages();
        pack.setProjectId(projectId);
        pack.setName(name);
        pack.setIsDeleted(0);
        packageService.insertSelective(pack);
        packagesId += pack.getId() + ",";
      }
      return packagesId.substring(0, packagesId.length()-1);
    }else{
      return ERROR;
    }


  }

  /**
   * 存储查询条件
   */
	@Override
	public void saveOrUpdateCondition(SupplierExtractCondition condition,SupplierConType conType) {
		if(StringUtils.isNotBlank(condition.getId())){
			supplierConditionMapper.updateConditionByPrimaryKeySelective(condition);
			//设置存储条件
			List<ExtractConditionRelation> list = new ArrayList<>();
			String cid = condition.getId();
			//删除上次查询条件
			//extractConditionRelationMapper.deleteConditionRelationByMap(condition.getId());
			//省份，直辖市
			if(StringUtils.isNotEmpty(condition.getProvince())){
				for (String province : condition.getProvinces()) {
					list.add(new ExtractConditionRelation(cid, "province", province));
				}
			}
			//市、区
			if(StringUtils.isNotEmpty(condition.getAddressId())){
				for (String addressId : condition.getAddressIds()) {
					list.add(new ExtractConditionRelation(cid, "addressId", addressId));
				}
			}
			//供应商类别
			if(StringUtils.isNotEmpty(condition.getSupplierTypeCode())){
				for (String supplierTypeCode : condition.getSupplierTypeCodes()) {
					list.add(new ExtractConditionRelation(cid, "supplierTypeCode", supplierTypeCode));
				}
			}
			//工程品目
			if(StringUtils.isNotEmpty(conType.getProjectCategoryIds())){
				for (String projectCategoryId : conType.getProjectCategoryIds().split(",")) {
					list.add(new ExtractConditionRelation(cid, "projectCategoryId", projectCategoryId));
				}
			}
			//销售品目
			if(StringUtils.isNotEmpty(conType.getSalesCategoryIds())){
				for (String salesCategoryId : conType.getSalesCategoryIds().split(",")) {
					list.add(new ExtractConditionRelation(cid, "salesCategoryId", salesCategoryId));
				}
			}
			//服务品目
			if(StringUtils.isNotEmpty(conType.getServiceCategoryIds())){
				for (String serviceCategoryId : conType.getServiceCategoryIds().split(",")) {
					list.add(new ExtractConditionRelation(cid, "serviceCategoryId", serviceCategoryId));
				}
			}
			//生产品目
			if(StringUtils.isNotEmpty(conType.getProductCategoryIds())){
				for (String productCategoryId : conType.getProductCategoryIds().split(",")) {
					list.add(new ExtractConditionRelation(cid, "productCategoryId", productCategoryId));
				}
			}
			//工程等级
			if(StringUtils.isNotEmpty(conType.getProjectLevel())){
				for (String projectLevel : conType.getProjectLevel().split(",")) {
					list.add(new ExtractConditionRelation(cid, "projectLevel", projectLevel));
				}
			}
			//销售等级
			if(StringUtils.isNotEmpty(conType.getSalesLevel())){
				for (String salesLevel : conType.getSalesLevel().split(",")) {
					list.add(new ExtractConditionRelation(cid, "salesLevel", salesLevel));
				}
			}
			//服务等级
			if(StringUtils.isNotEmpty(conType.getServiceLevel())){
				for (String serviceLevel : conType.getServiceLevel().split(",")) {
					list.add(new ExtractConditionRelation(cid, "serviceLevel", serviceLevel));
				}
			}
			//生产等级
			if(StringUtils.isNotEmpty(conType.getProductLevel())){
				for (String productLevel : conType.getProductLevel().split(",")) {
					list.add(new ExtractConditionRelation(cid, "productLevel", productLevel));
				}
			}
			//工程抽取数量
			if(null!=conType){
				if (null != conType.getProjectExtractNum()) {
					list.add(new ExtractConditionRelation(cid,"projectExtractNum",conType.getProjectExtractNum().toString()));
				}
				//工程品目标志
				if (null != conType.getProjectIsMulticondition()) {
					list.add(new ExtractConditionRelation(cid,"projectIsMulticondition",conType.getProjectIsMulticondition().toString()));
				}
				//生产抽取数量
				if (null != conType.getProductExtractNum()) {
					list.add(new ExtractConditionRelation(cid,"productExtractNum",conType.getProductExtractNum().toString()));
				}
				//生产目标志
				if (null != conType.getProductIsMulticondition()) {
					list.add(new ExtractConditionRelation(cid,"productIsMulticondition",conType.getProductIsMulticondition().toString()));
				}
				//服务抽取数量
				if (null != conType.getServiceExtractNum()) {
					list.add(new ExtractConditionRelation(cid,"serviceExtractNum",conType.getServiceExtractNum().toString()));
				}
				//服务品目标志
				if (null != conType.getServiceIsMulticondition()) {
					list.add(new ExtractConditionRelation(cid,"serviceIsMulticondition",conType.getServiceIsMulticondition().toString()));
				}
				//工程抽取数量
				if (null != conType.getSalesExtractNum()) {
					list.add(new ExtractConditionRelation(cid,"saleExtractNum",conType.getSalesExtractNum().toString()));
				}
				//工程品目标志
				if (null != conType.getSaleIsMulticondition()) {
					list.add(new ExtractConditionRelation(cid,"saleIsMulticondition",conType.getSaleIsMulticondition().toString()));
				}
			}
			if(list.size()>0){
				extractConditionRelationMapper.insertConditionRelation(list);
			}
		}
	}

	@Override
	public List<DictionaryData> supplierType(String typeCode) {
		if("GOODS".equals(typeCode)){
			return dictionaryDataMapper.findByKind("8");
		}
		return dictionaryDataMapper.selectByCode(typeCode);
	}

	/**
	 * 查询满足供应商数量
	 */
	@Override
	public Map<String, Object> selectLikeSupplierCount(
			SupplierExtractCondition condition, SupplierConType conType) {
		Map<String, Map<String, Object>> map = this.selectLikeSupplier(condition, conType,0);
		Map<String, Object> map2 = map.get("count");
		return map2;
	}
	
	
	/**
	 * 查询企业性质
	 */
	@Override
	public List<DictionaryData> getBusinessNature() {
		return dictionaryDataMapper.findByKind("32");
	}
	
	@Override
	public List<CategoryTree> getTreeForExt(Category category,String supplierTypeCode) {
		
		List<CategoryTree> jList = new ArrayList<>();
		if(category.getId()==null){
	    	   category.setId(dictionaryDataMapper.selectByCode(supplierTypeCode).get(0).getId());
	    }
         List<Category> cateList= categoryService.disTreeGoodsData(category.getId());
         for(Category cate:cateList){
             List<Category> cList= categoryService.disTreeGoodsData(cate.getId());
             CategoryTree ct=new CategoryTree();
             if(!cList.isEmpty()){
                 ct.setIsParent("true");
             }else{
                 ct.setIsParent("false");
             }
             ct.setId(cate.getId());
             ct.setName(cate.getName());
             ct.setpId(cate.getParentId());
             ct.setKind(cate.getKind());
             ct.setStatus(cate.getStatus());
             jList.add(ct);
         }
		return jList;
	}

	@Override
	public List<DictionaryData> getEngAptitudeLevelByCategoryId(
			String categoryId) {
		Map<String, String[]> map = new HashMap<>();
		map.put("categoryIds", categoryId.split(","));
		return supplierConditionMapper.getEngAptitudeLevelByCategoryId(map);
	}

	@Override
	public List<DictionaryData> getQuaByCid(String categoryId) {
		HashMap<String,String[]> hashMap = new HashMap<>();
		hashMap.put("categoryIds", categoryId.split(","));
		return supplierConditionMapper.getQuaByCid(hashMap);
	}

	@Override
	public List<DictionaryData> getLevelByQid(String qid) {
		return  supplierConditionMapper.getLevelByQid(qid.split(","));
	}

	
}

	
