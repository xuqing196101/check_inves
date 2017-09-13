/**
 * 
 *//*
package extract.service.supplier.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hamcrest.core.IsNot;
import org.jsoup.select.Evaluator.IsEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.SupplierMapper;
import ses.model.bms.DictionaryData;
import ses.model.sms.Supplier;
import ses.util.DictionaryDataUtil;
import ses.util.PropUtil;
import bss.model.ppms.Packages;
import bss.model.ppms.Project;
import bss.service.ppms.PackageService;
import bss.service.ppms.ProjectService;

import com.github.pagehelper.PageHelper;

import extract.dao.common.ExtractConditionRelationMapper;
import extract.dao.supplier.SupplierExtractConditionMapper;
import extract.dao.supplier.SupplierExtractRelateResultMapper;
import extract.model.common.ExtractConditionRelation;
import extract.model.supplier.SupplierConType;
import extract.model.supplier.SupplierCondition;
import extract.service.supplier.SupplierExtractConditionService;
import freemarker.core.ReturnInstruction.Return;


*//**
 * @Description:
 *	 
 * @author Wang Wenshuai
 * @version 2016年9月28日上午10:39:57
 * @since  JDK 1.7
 *//*
@Service
public class SupplierExtractConditionServiceimp  implements SupplierExtractConditionService {

  *//** SCCUESS *//*
  private static final String SUCCESS = "SUCCESS";
  *//** ERROR *//*
  private static final String ERROR = "ERROR";

  @Autowired
  SupplierExtractConditionMapper supplierConditionMapper;

  @Autowired
  SupplierMapper supplierMapper;

  @Autowired
  SupplierExtractRelateResultMapper supplierExtRelateMapper;

  @Autowired
  PackageService packageService;
  
  @Autowired
  private ProjectService projectService;
  
  @Autowired
  private ExtractConditionRelationMapper extractConditionRelationMapper;//条件关联表
  
  *//**
   * @Description:添加
   *
   * @author Wang Wenshuai
   * @version 2016年9月28日 上午10:35:49  
   * @param @param condition      
   * @return void
   *//*
  @Override
  public void insert(SupplierCondition condition){
	  condition.setCreatedAt(new Date());
    supplierConditionMapper.insertSelective(condition);
  }

  *//**
   * @Description:修改
   *
   * @author Wang Wenshuai
   * @version 2016年9月28日 上午10:36:05  
   * @param @param condition      
   * @return void
   *//*
  public void update(SupplierCondition condition){
	  
	//存储地区
	//extConditionAreaMapper.insertSupplierArea(condition);
	//存储品目 类型
	//类别是4种，每种类别对应多种品目 这里面会有关联关系前台传进来的数据是
	//
	//extTypeCategoryMapper.insertSupplierArea(condition);
    supplierConditionMapper.updateByPrimaryKeySelective(condition);
  }

  *//**
   * @Description:集合查询
   *
   * @author Wang Wenshuai
   * @version 2016年9月28日 上午10:36:20  
   * @param @param condition
   * @param @return      
   * @return List<ExpExtCondition>
   *//*
  public List<SupplierCondition> list(SupplierCondition condition,Integer pageNum){
    if(pageNum != null && pageNum!=0){
      PageHelper.startPage(pageNum,PropUtil.getIntegerProperty("pageSize"));
    }
    return supplierConditionMapper.list(condition);
  }

  *//**
   * @Description:获取单个
   *
   * @author Wang Wenshuai
   * @version 2016年9月28日 下午3:17:07  
   * @param @param condition
   * @param @return      
   * @return ExpExtCondition
   *//*
  @Override
  public SupplierCondition show(String id) {
    return supplierConditionMapper.selectByPrimaryKey(id);
  }

  *//**
   * 
   *〈简述〉更具关联包id查询是否有未抽取的条件
   *〈详细描述〉
   * @author Wang Wenshuai
   * @param id
   * @return
   *//*
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

  *//**
   * 直接删除查询不出结果的查询条件
   * @return 
   * @see ses.service.sms.SupplierConditionService#delById(java.lang.String)
   *//*
  @Override
  public Integer delById(String Id) {
    return supplierConditionMapper.deleteByPrimaryKey(Id);
  }

  *//**
   * 返回满足条件的供应商 并存储抽取条件
   * @see ses.service.sms.SupplierConditionService#selectLikeSupplier(ses.model.sms.SupplierCondition, ses.model.sms.SupplierConType)
   *//*
  @Override
  public Map<String,Object> selectLikeSupplier(SupplierCondition condition, SupplierConType conType) {
	 Map<String, Object> map = new HashMap<>();
	//   conType=condition.getSupplierConType();
	*//**
	 * 查询需要分类型
	 *//*
	 
	 
	 //若是类型为空说明只对地区进行查询
	if(StringUtils.isBlank(condition.getSupplierTypeCode())){
		List<Supplier> selectAllExpert = supplierMapper.listExtractionExpert(condition);//getAllSupplier(null);
		map.put("area", selectAllExpert.size());
		//存储
			saveOrUpdateCondition(condition, conType);
	}
	    
	if(StringUtils.isNotEmpty(conType.getProductCategoryIds())){
		//SupplierCondition pdsc = new SupplierCondition("PRODUCT", conType.getProductIsMulticondition(), conType.getProductCategoryIds(), conType.getProductLevel());
		condition.setSupplierTypeCode("PRODUCT");
		condition.setIsMulticondition(conType.getProductIsMulticondition());
		condition.setCategoryId(conType.getProductCategoryIds());
		condition.setLevelTypeId(conType.getProductLevel());
		condition.setExtractNum(conType.getProductExtractNum());
	    List<Supplier> products = supplierMapper.listExtractionExpert(condition);
	    map.put("products", products);
	  //存储
	  	saveOrUpdateCondition(condition, conType);
	}
	if(StringUtils.isNotEmpty(conType.getProjectCategoryIds())){
		//SupplierCondition pjsc = new SupplierCondition("PROJECT", conType.getProjectIsMulticondition(), conType.getProjectCategoryIds(), conType.getProjectLevel());
	    condition.setSupplierTypeCode("PROJECT");
		condition.setIsMulticondition(conType.getProjectIsMulticondition());
		condition.setCategoryId(conType.getProjectCategoryIds());
		condition.setLevelTypeId(conType.getProjectLevel());
		condition.setExtractNum(conType.getProjectExtractNum());
		List<Supplier> projects = supplierMapper.listExtractionExpert(condition);
	    map.put("projects", projects);
	  //存储
	  	saveOrUpdateCondition(condition, conType);
	}
	if(StringUtils.isNotEmpty(conType.getSalesCategoryIds())){
		//SupplierCondition sasc = new SupplierCondition("SALES", conType.getSaleIsMulticondition(), conType.getSaleCategoryIds(), conType.getSaleLevel());
	    condition.setSupplierTypeCode("SALES");
		condition.setIsMulticondition(conType.getSaleIsMulticondition());
		condition.setCategoryId(conType.getSalesCategoryIds());
		condition.setLevelTypeId(conType.getSalesLevel());
		condition.setExtractNum(conType.getSalesExtractNum());
		List<Supplier> sales = supplierMapper.listExtractionExpert(condition);
	    map.put("sales", sales);
	  //存储
	  	saveOrUpdateCondition(condition, conType);
	}
	if(StringUtils.isNotEmpty(conType.getServiceCategoryIds())){
		//SupplierCondition svsc = new SupplierCondition("SERVICE", conType.getServiceIsMulticondition(), conType.getServiceCategoryIds(), conType.getServiceLevel());
		condition.setSupplierTypeCode("SERVICE");
		condition.setIsMulticondition(conType.getServiceIsMulticondition());
		condition.setCategoryId(conType.getServiceCategoryIds());
		condition.setLevelTypeId(conType.getServiceLevel());
		condition.setExtractNum(conType.getServiceExtractNum());
		List<Supplier> services = supplierMapper.listExtractionExpert(condition);
	    map.put("services", services);
	  //存储
	  	saveOrUpdateCondition(condition, conType);
	}
	
	
	return map;
    //保存抽取结果
    
    
    //循环吧查询出的供应商集合insert到专家记录表和专家关联的表中
    for (Supplier supplier2 : selectAllExpert) {
      Map<String, String> map=new HashMap<String, String>();
      map.put("supplierId", supplier2.getId());
      map.put("projectId",packId[0]);
      if(supplierExtRelateMapper.getSupplierId(map)==0){
        count++;
      }
    }
  
  }


  *//**
   * 本次抽取是否完成
   * @see ses.service.ems.ExpExtConditionService#isFinish()
   *//*
  @Override
  public String isFinish(SupplierCondition condition) {
    List<SupplierCondition> list = supplierConditionMapper.list(condition);
    if (list != null && list.size() !=0 ){
      return SUCCESS;
    }else{
      return ERROR;
    }

  }

  *//**
   * 供应商类型
   * @see ses.service.sms.SupplierConditionService#supplierTypeList()
   *//*
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

  *//**
   * 添加包信息
   *〈简述〉
   *〈详细描述〉
   * @author Wang Wenshuai
   * @return
   *//*
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

  *//**
   * 存储查询条件
   *//*
	@Override
	public void saveOrUpdateCondition(SupplierCondition condition,SupplierConType conType) {
		if(StringUtils.isNotBlank(condition.getId())){
			supplierConditionMapper.updateConditionByPrimaryKeySelective(condition);
			//设置存储条件
			List<ExtractConditionRelation> list = new ArrayList<>();
			String cid = condition.getId();
			extractConditionRelationMapper.deleteConditionRelationByMap(condition.getId());
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
		//存储条件
		Map<String,String> map = new HashMap<>();
		map.put("conditionId", condition.getId());
		map.put("supplierTypeCode",condition.getSupplierTypeCode());
		List<SupplierConType> typeInfos = new ArrayList<>() ;
		if(StringUtils.isNotBlank(condition.getSupplierTypeCode())){
			typeInfos = supplierConditionMapper.getTypeInfoByMap(map);
		}
		if(typeInfos.size()>0){
			supplierConditionMapper.deleteTypeInfoByMap(map);
		}
		supplierConditionMapper.insertTypeInfo(condition);
		
		//
	}
}
*/