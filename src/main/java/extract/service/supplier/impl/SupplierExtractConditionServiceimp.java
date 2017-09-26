/**
 * 
 */
package extract.service.supplier.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.bms.AreaMapper;
import ses.dao.bms.DictionaryDataMapper;
import ses.dao.bms.QualificationMapper;
import ses.model.bms.Area;
import ses.model.bms.Category;
import ses.model.bms.CategoryTree;
import ses.model.bms.DictionaryData;
import ses.model.bms.Qualification;
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
  
  @Autowired
  private AreaMapper areaMapper;
  
  /** 资质Mapper **/
  @Autowired
  private QualificationMapper mapper;
  
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
   * 返回满足条件的供应商 并存储抽取条件
   * @see ses.service.sms.SupplierConditionService#selectLikeSupplier(ses.model.sms.SupplierCondition, ses.model.sms.SupplierConType)
   */
  @Override
  public Map<String, Object> selectLikeSupplier(SupplierExtractCondition condition, SupplierConType conType,int type) {
	
	 Map<String, Object> list = new HashMap<>();
	 Map<String, Object> count = new HashMap<>();
	 Map<String, Object> map = new HashMap<>();
	 map.put("count", count);
	 map.put("list", list);
	 if(1==type){
		 if(StringUtils.isBlank(condition.getAreaName())){
			 map.put("error", "areaNameError");
			 return map;
		 }
		 if((!"0".equals(condition.getProvince()))&& StringUtils.isBlank(condition.getAddressReason())){
			 map.put("error", "areaError");
			 return map;
		 }
	 }
	  
	 //此方法为公共方法 查询满足供应商数量 和供应商抽取结果  0 表示查询数量 1 表示 抽取 
	 //去除已经抽取到的供应商
	
	 if(StringUtils.isNotBlank(condition.getProjectId())){
		 
		 List<String> supplierIds = supplierExtRelateMapper.selectSupplierIdListByProjectId(condition.getProjectId());
		 if(null != supplierIds){
			 condition.setSupplierIds(supplierIds);
		 }
		 
	 }else if(StringUtils.isNotEmpty(condition.getRecordId())){
		 List<String> supplierIds = supplierExtRelateMapper.selectSupplierIdListByRecordId(condition.getRecordId());
		 if(null != supplierIds){
			 condition.setSupplierIds(supplierIds);
		 }
	 }
	 
	  
	
	
	 
	Class<? extends SupplierConType> class1 = conType.getClass();
	String supplierTypeCode  = condition.getSupplierTypeCode();
	String typeCode = condition.getSupplierTypeCode();
	String addressId = condition.getAddressId();
	//for (String typeCode : supplierTypeCodes) {
		//首字母大写
		char[] cs=typeCode.toLowerCase().toCharArray();
        cs[0]-=32;
        String code = String.valueOf(cs);
		try {
			Short mu = (Short)class1.getMethod("get"+code+"IsMulticondition").invoke(conType);
			String cid = (String)class1.getMethod("get"+code+"CategoryIds").invoke(conType);
			String pid = (String)class1.getMethod("get"+code+"ParentId").invoke(conType);
			String le = (String)class1.getMethod("get"+code+"Level").invoke(conType);
			Short en = (Short)class1.getMethod("get"+code+"ExtractNum").invoke(conType);
			String ic = (String)class1.getMethod("get"+code+"IsHavingConCert").invoke(conType);
			String bu = (String)class1.getMethod("get"+code+"BusinessNature").invoke(conType);
			String ob = (String)class1.getMethod("get"+code+"OverseasBranch").invoke(conType);
			String qid = (String)class1.getMethod("get"+code+"QuaId").invoke(conType);
			String qname = (String)class1.getMethod("get"+code+"QuaName").invoke(conType);
			if(null != mu){
				condition.setIsMulticondition(mu);
			}
			if(StringUtils.isNotBlank(cid)){
				condition.setCategoryId(cid);
			}
			//若勾选了父节点
			if(StringUtils.isNotBlank(pid)){
				//遍历出全部的其下的末级节点，去重拼进categoryId
				cid = this.selectChild(pid,cid);
				
				condition.setCategoryId(cid);
			}
			
			if(StringUtils.isNotBlank(le)){
				condition.setLevelTypeId(le);
			}
			if(null != en){
				condition.setExtractNum(en);
			}
			
			if(StringUtils.isNotBlank(ic)){
				condition.setIsHavingConCert(ic);
			}
			if(StringUtils.isNotBlank(bu)){
				condition.setBusinessNature(bu);
			}
			if(StringUtils.isNotBlank(ob)){
				condition.setOverseasBranch(ob);
			}if(StringUtils.isNotBlank(qid)){
				condition.setQuaId(qid);
			}
			
			//condition查询和存储不是条件不一样，再创建一个用来存储
			/*SupplierExtractCondition ct = new SupplierExtractCondition();
			BeanUtils.copyProperties(ct, condition);*/
			
			//处理地区查询条件
			if(StringUtils.isNotBlank(condition.getProvince()) && !"0".equals(condition.getProvince())){
				String addr = "";
				for (String pro : condition.getProvinces()) {
					List<Area> areas = areaMapper.findAreaByParentId(pro);
					for (Area area : areas) {
						addr += ","+area.getId();
					}
				}
				addressId = StringUtils.isNotBlank(condition.getAddressId())?condition.getAddressId()+addr:StringUtils.isNotBlank(addr)?addr.substring(1):"";
				condition.setAddressId(addressId);
			}else if(StringUtils.isBlank(condition.getProvince()) && StringUtils.isBlank(condition.getAddressId())){
				condition.setAddressId("null");
			}
			
			//物资类 不限查询   需要将物资下的一起查询
			if("GOODS".equals(supplierTypeCode)){
				condition.setSupplierTypeCode("PRODUCT,SALES");
			}
			
			
			if(type == 1){
				if(null == en){
					map.put("error",code+"ExtractNumError");
					return map;
				}
				
				List<Supplier> selectAllExpert = supplierExtRelateMapper.listExtractionExpert(condition);
				
				for (Supplier supplier : selectAllExpert) {
					supplier.setSupplierType(dictionaryDataMapper.selectByCode(supplier.getSupplierTypeId()).get(0).getName());
				}
				
				
				/*String sid = selectAllExpert.get(0).getId();
				List<String> typeCodeList = supplierExtRelateMapper.selectTypeCodeBySid(sid);
				String temp = "";
				for (String s : typeCodeList) {
					temp += ","+ dictionaryDataMapper.selectByCode(s).get(0).getName();
				}
			    
				selectAllExpert.get(0).setSupplierType(StringUtils.isNotBlank(temp)?temp.substring(1):"");*/
				
				list.put(typeCode, selectAllExpert);
			}else{
				count.put(typeCode+"Count", supplierExtRelateMapper.listExtractionExpertCount(condition));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	return map;
  }


  private String selectChild(String pid, String cid) {
	Set<String> cateSet = supplierConditionMapper.seleselectChildCate(pid.split(","));
	if(StringUtils.isNotBlank(cid)){
		cateSet.addAll(Arrays.asList(cid.split(",")));
	}
	return StringUtils.join(cateSet.toArray(),",");
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
	public int saveOrUpdateCondition(SupplierExtractCondition condition,SupplierConType conType) {
		if(StringUtils.isNotBlank(condition.getId())){
			supplierConditionMapper.updateConditionByPrimaryKeySelective(condition);
			//设置存储条件
			List<ExtractConditionRelation> list = new ArrayList<>();
			String cid = condition.getId();
			//删除上次查询条件
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
			
			Class<? extends SupplierConType> class1 = conType.getClass();
			String[] supplierTypeCodes  = condition.getSupplierTypeCodes();
			for (String typeCode : supplierTypeCodes) {
				//首字母大写
				String c = typeCode.toLowerCase();
				char[] cs=c.toCharArray();
		        cs[0]-=32;
		        String code = String.valueOf(cs);
				try {
					Short mu = (Short)class1.getMethod("get"+code+"IsMulticondition").invoke(conType);
					String cids = (String)class1.getMethod("get"+code+"CategoryIds").invoke(conType);
					String le = (String)class1.getMethod("get"+code+"Level").invoke(conType);
					Short en = (Short)class1.getMethod("get"+code+"ExtractNum").invoke(conType);
					String ic = (String)class1.getMethod("get"+code+"IsHavingConCert").invoke(conType);
					String bu = (String)class1.getMethod("get"+code+"BusinessNature").invoke(conType);
					String ob = (String)class1.getMethod("get"+code+"OverseasBranch").invoke(conType);
					if(null != mu){
						list.add(new ExtractConditionRelation(cid,c+"IsMulticondition",mu.toString()));
					}
					if(StringUtils.isNotBlank(cids)){
						for (String cId : cids.split(",")) {
							list.add(new ExtractConditionRelation(cid, c+"CategoryId", cId));
						}
					}
					if(StringUtils.isNotBlank(le)){
						for (String lv : le.split(",")) {
							list.add(new ExtractConditionRelation(cid, c+"Level", lv));
						}
					}
					if(null !=en){
						list.add(new ExtractConditionRelation(cid,c+"ExtractNum",en.toString()));
					}
					if(StringUtils.isNotBlank(ic)){
						list.add(new ExtractConditionRelation(cid,c+"IsHavingConCert",ic));
					}
					if(StringUtils.isNotBlank(bu)){
						list.add(new ExtractConditionRelation(cid,c+"BusinessNature",StringUtils.isBlank(bu)?"0":bu));
					}
					if(StringUtils.isNotBlank(ob)){
						list.add(new ExtractConditionRelation(cid,c+"OverseasBranch",ob));
					}
					
				} catch (Exception e) {
					e.getMessage();
				}
			}
			
			if(list.size()>0){
				return extractConditionRelationMapper.insertConditionRelation(list);
			}
		}
		return 0;
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
		Map<String, Object> map = this.selectLikeSupplier(condition, conType,0);
		@SuppressWarnings("unchecked")
		Map<String, Object> map2 = (Map<String, Object>) map.get("count");
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
	public List<DictionaryData> getQuaByCid(String categoryId ,String code,String parentId) {
		
		if(StringUtils.isNotBlank(parentId)){
			
			categoryId = this.selectChild(parentId, categoryId);
		}
		
		HashMap<String,String[]> hashMap = new HashMap<>();
		if(StringUtils.isNotBlank(categoryId)){
			if("project".equals(code)){
				String[] checkParentCate = checkParentCate(categoryId);
				hashMap.put("categoryIds",checkParentCate);
			}else{
				hashMap.put("categoryIds",categoryId.split(","));
			}
			return supplierConditionMapper.getQuaByCid(hashMap);
		}
		return null;
	}

	@Override
	public List<DictionaryData> getLevelByQid(String qid) {
		return  supplierConditionMapper.getLevelByQid(qid.split(","));
	}

	/**
	 * 
	 * <简述> 根据选择的品目id查询父节点 判断是否是工程勘察 或者设计
	 *
	 * @author Jia Chengxiang
	 * @dateTime 2017-9-22下午3:53:02
	 * @return
	 */
	public String[] checkParentCate(String categoryIds) {
		// 递归获取所有父节点
		
		String[] cate = null;
		if(StringUtils.isNotBlank(categoryIds)){
			for (String cid : categoryIds.split(",")) {
				List<Category> checkParentCate = supplierConditionMapper.checkParentCate(cid);
				if("B02".equals(checkParentCate.get(0).getCode())||"B03".equals(checkParentCate.get(0).getCode())){
					cate = new String[checkParentCate.size()];
					for (int i = 0; i < checkParentCate.size(); i++) {
						cate[i]=checkParentCate.get(i).getId();
					}
				}
			}
		}
		return cate;
	}

	@Override
	public List<Qualification> qualificationList(String name) {
		
		return mapper.findList(name, null);
	}
}
