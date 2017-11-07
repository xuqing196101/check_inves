/**
 * 
 */
package extract.service.supplier.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.bms.AreaMapper;
import ses.dao.bms.DictionaryDataMapper;
import ses.dao.bms.QualificationMapper;
import ses.dao.sms.SupplierBlacklistMapper;
import ses.model.bms.Area;
import ses.model.bms.Category;
import ses.model.bms.CategoryTree;
import ses.model.bms.DictionaryData;
import ses.model.bms.Qualification;
import ses.model.sms.Supplier;
import ses.service.bms.CategoryService;
import ses.util.DictionaryDataUtil;
import bss.model.ppms.Packages;
import bss.model.ppms.Project;
import bss.service.ppms.PackageService;
import bss.service.ppms.ProjectService;

import com.fasterxml.jackson.databind.ObjectMapper;

import extract.dao.supplier.ExtractConditionRelationMapper;
import extract.dao.supplier.SupplierExtractConditionMapper;
import extract.dao.supplier.SupplierExtractRelateResultMapper;
import extract.model.supplier.ExtractConditionRelation;
import extract.model.supplier.Qua;
import extract.model.supplier.SupplierConType;
import extract.model.supplier.SupplierExtractCondition;
import extract.service.supplier.SupplierExtractConditionService;

/**
 *  @Description:    @author Wang Wenshuai  @version 2016年9月28日上午10:39:57
 * 
 * @since JDK 1.7
 */
@Service
public class SupplierExtractConditionServiceimp implements
		SupplierExtractConditionService {

	/** ERROR */
	private static final String ERROR = "ERROR";

	ObjectMapper mapper = new ObjectMapper();

	@Autowired
	SupplierExtractConditionMapper conditionMapper;

	@Autowired
	SupplierExtractRelateResultMapper supplierExtRelateMapper;

	@Autowired
	PackageService packageService;

	@Autowired
	private ProjectService projectService;

	@Autowired
	private ExtractConditionRelationMapper extractConditionRelationMapper;// 条件关联表

	@Autowired
	private DictionaryDataMapper dictionaryDataMapper;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private AreaMapper areaMapper;

	/** 资质Mapper **/
	@Autowired
	private QualificationMapper quaMapper;

	/** 供应商黑名单 **/
	@Autowired
	private SupplierBlacklistMapper supplierBlacklistMapper;

	/**
	 * @Description:添加
	 * 
	 * @author Wang Wenshuai
	 * @version 2016年9月28日 上午10:35:49
	 * @param @param condition
	 * @return void
	 */
	@Override
	public void insert(SupplierExtractCondition condition) {
		condition.setCreatedAt(new Date());
		conditionMapper.insertSelective(condition);
	}

	/**
	 * @Description:修改
	 * 
	 * @author Wang Wenshuai
	 * @version 2016年9月28日 上午10:36:05
	 * @param @param condition
	 * @return void
	 */
	public void update(SupplierExtractCondition condition) {

		// 存储地区
		// extConditionAreaMapper.insertSupplierArea(condition);
		// 存储品目 类型
		// 类别是4种，每种类别对应多种品目 这里面会有关联关系前台传进来的数据是
		//
		// extTypeCategoryMapper.insertSupplierArea(condition);
		conditionMapper.updateConditionByPrimaryKeySelective(condition);
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
		return conditionMapper.selectByPrimaryKey(id);
	}

	/***
	 * 返回满足条件的供应商
	 */
	@Override
	public Map<String, Object> selectLikeSupplier(
			SupplierExtractCondition condition, SupplierConType conType,
			int type) {

		Map<String, Object> list = new HashMap<>();
		Map<String, Object> count = new HashMap<>();
		Map<String, Object> map = new HashMap<>();
		map.put("count", count);
		map.put("list", list);

		// type 为1 表示是获取当前满足条件的供应商
		if (1 == type) {
			if (StringUtils.isBlank(condition.getAreaName())) {
				map.put("error", "areaNameError");
				return map;
			}
			if ((!"0".equals(condition.getProvince()))
					&& StringUtils.isBlank(condition.getAddressReason())) {
				map.put("error", "areaError");
				return map;
			}
		}

		this.excludeSupplier(condition);
		try {
			String typeCode = condition.getSupplierTypeCode();
			// 原处理抽取条件方法
			// String code = this.setExtractCondition(typeCode,condition,conType);
			String code = typeCode.toLowerCase();
			
			this.setExtractCondition2(condition,typeCode);
			
			if (type == 1) {
				if (null == condition.getExtractNum()) {
					map.put("error", code + "ExtractNumError");
					return map;
				}
				List<Supplier> selectAllExpert = supplierExtRelateMapper.listExtractionExpert(condition);

				for (Supplier supplier : selectAllExpert) {
					supplier.setSupplierType(dictionaryDataMapper.selectByCode(supplier.getSupplierTypeId()).get(0).getName());
				}

				list.put(typeCode, selectAllExpert);
			} else {
				count.put(typeCode + "Count", supplierExtRelateMapper.listExtractionExpertCount(condition));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/***
	 * 返回满足条件的供应商2
	 */
	@Override
	public Map<String, Object> selectLikeSupplier2(
			SupplierExtractCondition condition ,int type) {
		
		Map<String, Object> map = new HashMap<>();
		
		// type 为1 表示是获取当前满足条件的供应商
		if (1 == type) {
			if (StringUtils.isBlank(condition.getAreaName())) {
				map.put("error", "areaNameError");
				return map;
			}
			if ((!"0".equals(condition.getProvince()))
					&& StringUtils.isBlank(condition.getAddressReason())) {
				map.put("error", "areaError");
				return map;
			}
		}
		
		this.excludeSupplier(condition);
		try {
			String typeCode = condition.getSupplierTypeCode();
			// 原处理抽取条件方法
			// String code = this.setExtractCondition(typeCode,condition,conType);
			String code = typeCode.toLowerCase();
			
			this.setExtractCondition2(condition,typeCode);
			
			if (type == 1) {
				if (null == condition.getExtractNum()) {
					map.put("error", code + "ExtractNumError");
					return map;
				}
				List<Supplier> selectAllExpert = supplierExtRelateMapper.listExtractionExpert(condition);
				
				for (Supplier supplier : selectAllExpert) {
					supplier.setSupplierType(dictionaryDataMapper.selectByCode(supplier.getSupplierTypeId()).get(0).getName());
				}
				
				map.put("list", selectAllExpert);
			} else {
				map.put("count", supplierExtRelateMapper.listExtractionExpertCount(condition));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	private Set<String> selectChild(String pid, String cid) {
		Set<String> cateSet = conditionMapper.seleselectChildCate(pid
				.split(","));
		if (StringUtils.isNotBlank(cid)) {
			cateSet.addAll(Arrays.asList(cid.split(",")));
		}
		cateSet.addAll(Arrays.asList(pid.split(",")));
		return cateSet;// StringUtils.join(cateSet.toArray(),",");
	}

	/**
	 * 供应商类型
	 * 
	 * @see ses.service.sms.SupplierConditionService#supplierTypeList()
	 */
	@Override
	public List<DictionaryData> supplierTypeList(String projectId) {

		// 查询项目类型
		Project curruntProject = null;
		List<DictionaryData> supplierTypes = new ArrayList<>();
		if (StringUtils.isNotBlank(projectId)) {
			// 根据项目类型 设置要选择的供应商类型
			curruntProject = projectService.selectById(projectId);
			if (null != curruntProject
					&& StringUtils.isNotBlank(curruntProject.getPlanType())) {
				if ("FC9528B2E74F4CB2A9E74735A8D6E90A".equals(curruntProject
						.getPlanType())) {
					return DictionaryDataUtil.find(8);
				} else {
					supplierTypes.add(DictionaryDataUtil
							.findById(curruntProject.getPlanType()));
					return supplierTypes;
				}
			}
		}

		supplierTypes = DictionaryDataUtil.find(6);

		for (int i = 0; i < supplierTypes.size(); i++) {
			String code = supplierTypes.get(i).getCode();
			if (code.equals("GOODS")) {
				supplierTypes.remove(supplierTypes.get(i));
			}
		}
		List<DictionaryData> wlist = DictionaryDataUtil.find(8);
		supplierTypes.addAll(wlist);
		return supplierTypes;
	}

	/**
	 * 添加包信息 〈简述〉 〈详细描述〉
	 * 
	 * @author Wang Wenshuai
	 * @return
	 */
	public String addPackage(String packagesName, String projectId) {
		String packagesId = "";
		if (packagesName != null && !"".equals(packagesName)) {
			String[] array = packagesName.split(",");
			for (String name : array) {
				Packages pack = new Packages();
				pack.setProjectId(projectId);
				pack.setName(name);
				pack.setIsDeleted(0);
				packageService.insertSelective(pack);
				packagesId += pack.getId() + ",";
			}
			return packagesId.substring(0, packagesId.length() - 1);
		} else {
			return ERROR;
		}

	}

	/**
	 * 存储查询条件
	 */
	@Override
	public int saveOrUpdateCondition(SupplierExtractCondition condition,
			SupplierConType conType) {
		if (StringUtils.isNotBlank(condition.getId())) {
			conditionMapper.updateConditionByPrimaryKeySelective(condition);
			//return saveContype(condition, conType);
			return saveContype2(condition);
		}
		return 0;
	}

	@Override
	public List<DictionaryData> supplierType(String typeCode) {
		if ("GOODS".equals(typeCode)) {
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
		//Map<String, Object> map = this.selectLikeSupplier(condition, conType, 0);
		/*@SuppressWarnings("unchecked")
		Map<String, Object> map2 = (Map<String, Object>) map.get("count");*/
		Map<String, Object> map = this.selectLikeSupplier2(condition, 0);
		return map;
	}

	/**
	 * 查询企业性质
	 */
	@Override
	public List<DictionaryData> getBusinessNature() {
		return dictionaryDataMapper.findByKind("32");
	}

	@Override
	public List<CategoryTree> getTreeForExt(Category category,
			String supplierTypeCode) {

		List<CategoryTree> jList = new ArrayList<>();
		if (category.getId() == null) {
			category.setId(dictionaryDataMapper.selectByCode(supplierTypeCode)
					.get(0).getId());
		}
		List<Category> cateList = categoryService.disTreeGoodsData(category
				.getId());
		for (Category cate : cateList) {
			List<Category> cList = categoryService.disTreeGoodsData(cate
					.getId());
			CategoryTree ct = new CategoryTree();
			if (!cList.isEmpty()) {
				ct.setIsParent("true");
			} else {
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
		return conditionMapper.getEngAptitudeLevelByCategoryId(map);
	}

	@Override
	public List<Qua> getQuaByCid(String categoryId, String code, String parentId) {

		HashMap<String, Object> hashMap = new HashMap<>();
		if ("project".equals(code) && StringUtils.isNotBlank(categoryId)) {
			String[] checkParentCate = checkParentCate(categoryId);
			hashMap.put(
					"categoryIds",
					null != checkParentCate ? checkParentCate : categoryId
							.split(","));
			List<Qua> quaByCid = conditionMapper.getQuaByCid(hashMap);
			return sortQua(quaByCid);
		} else {
			// List<DictionaryData> cateList = new ArrayList<>();
			Set<Qua> cateList = new HashSet<>();
			hashMap.put("quaType",
					code.equals("product") ? "2" : code.equals("sales") ? "3"
							: null);
			if (StringUtils.isNotBlank(parentId)) {
				// categoryId = this.selectChild(parentId, categoryId);
				Set<String> selectChild = this
						.selectChild(parentId, categoryId);
				int size = selectChild.size();
				ArrayList<String> arrayList = new ArrayList<>();
				arrayList.addAll(selectChild);
				int count = size / 1000;
				if (count > 0) {
					for (int i = 0; i < count; i++) {
						ArrayList<String> arrayList2 = new ArrayList<>();
						for (int j = 0; j < 1000; j++) {
							arrayList2.add(arrayList.get(i * count + j));
						}
						hashMap.put("categoryIds", arrayList2);// categoryId.split(","));
						List<Qua> quaByCid = conditionMapper
								.getQuaByCid(hashMap);
						cateList.addAll(quaByCid);
					}
					if (size % 1000 > 0) {
						ArrayList<String> arrayList2 = new ArrayList<>();
						for (int i = 0; i < size % 1000; i++) {
							arrayList2.add(arrayList.get(1000 * count + i));
						}
						hashMap.put("categoryIds", arrayList2);// categoryId.split(","));
						cateList.addAll(conditionMapper.getQuaByCid(hashMap));
					}
				} else {
					hashMap.put("categoryIds", arrayList);// categoryId.split(","));
					cateList.addAll(conditionMapper.getQuaByCid(hashMap));
				}
			}
			if (StringUtils.isNotBlank(categoryId)) {
				hashMap.put("categoryIds", categoryId.split(","));
				cateList.addAll(conditionMapper.getQuaByCid(hashMap));

			}

			return sortQua(new ArrayList<>(cateList));
		}
	}

	/**
	 * 资质排序 <简述>
	 * 
	 * @author Jia Chengxiang
	 * @dateTime 2017-10-20下午6:21:35
	 * @param list
	 * @return
	 */
	private List<Qua> sortQua(List<Qua> list) {
		Collections.sort(list, new Comparator<Qua>() {
			@Override
			public int compare(Qua o1, Qua o2) {
				Integer i = Integer.parseInt(o1.getQuatype())
						- Integer.parseInt(o2.getQuatype());
				return i;
			}
		});

		return list;
	}

	/**
	 * 查询工程资质等级
	 */
	@Override
	public List<DictionaryData> getLevelByQid(String qid) {
		return conditionMapper.getLevelByQid(qid.split(","));
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

		HashSet<String> hashSet = new HashSet<>();
		if (StringUtils.isNotBlank(categoryIds)) {
			for (String cid : categoryIds.split(",")) {
				List<Category> checkParentCate = conditionMapper
						.checkParentCate(cid);
				if ("B02".equals(checkParentCate.get(0).getCode())
						|| "B03".equals(checkParentCate.get(0).getCode())) {
					for (Category category : checkParentCate) {
						hashSet.add(category.getId());
					}
				} else {
					hashSet.add(cid);
				}
			}
		}

		return hashSet.toArray(new String[hashSet.size()]);
	}

	/**
	 * 资质信息
	 */
	@Override
	public List<Qualification> qualificationList(String name) {

		return quaMapper.findList(name, null);
	}

	/**
	 * 查询已经抽取到的供应商和黑名单中供应商 <简述>
	 * 
	 * @author Jia Chengxiang
	 * @dateTime 2017-10-12上午11:26:21
	 * @return
	 */
	@Override
	public void excludeSupplier(SupplierExtractCondition condition) {
		// 此方法为公共方法 查询满足供应商数量 和供应商抽取结果 0 表示查询数量 1 表示 抽取
		// 去除已经抽取到的供应商

		if (StringUtils.isNotBlank(condition.getProjectId())) {

			List<String> supplierIds = supplierExtRelateMapper
					.selectSupplierIdListByProjectId(condition.getProjectId());
			if (null != supplierIds) {
				condition.setSupplierIds(supplierIds);
			}

		} else if (StringUtils.isNotEmpty(condition.getRecordId())) {
			List<String> supplierIds = supplierExtRelateMapper
					.selectSupplierIdListByRecordId(condition.getRecordId());
			if (null != supplierIds) {
				condition.setSupplierIds(supplierIds);
			}
		}

		// 筛选掉供应商黑名单中数据
		List<String> notList = new ArrayList<>();
		List<String> supplierblackList = supplierBlacklistMapper
				.findExtractList();
		if (condition.getSupplierIds() != null) {
			notList.addAll(condition.getSupplierIds());
		}
		if (supplierblackList != null && supplierblackList.size() > 0) {
			notList.addAll(supplierblackList);
		}
		condition.setSupplierIds(notList);
	}

	/**
	 * 处理抽取条件 <简述>
	 * 
	 * @author Jia Chengxiang
	 * @throws
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws Exception
	 * @dateTime 2017-10-12下午2:13:10
	 */
	@Override
	public String setExtractCondition(String typeCode,
			SupplierExtractCondition condition, SupplierConType conType)
			throws Exception {
		Class<? extends SupplierConType> class1 = conType.getClass();
		// 首字母大写
		char[] cs = typeCode.toLowerCase().toCharArray();
		cs[0] -= 32;
		String code = String.valueOf(cs);
		String addressId = condition.getAddressId();

		Short mu = (Short) class1.getMethod("get" + code + "IsMulticondition")
				.invoke(conType);
		String cid = (String) class1.getMethod("get" + code + "CategoryIds")
				.invoke(conType);
		String pid = (String) class1.getMethod("get" + code + "ParentId")
				.invoke(conType);
		String le = (String) class1.getMethod("get" + code + "Level").invoke(
				conType);
		Short en = (Short) class1.getMethod("get" + code + "ExtractNum")
				.invoke(conType);
		String ic = (String) class1.getMethod("get" + code + "IsHavingConCert")
				.invoke(conType);
		String bu = (String) class1.getMethod("get" + code + "BusinessNature")
				.invoke(conType);
		String ob = (String) class1.getMethod("get" + code + "OverseasBranch")
				.invoke(conType);
		String qid = (String) class1.getMethod("get" + code + "QuaId").invoke(
				conType);
		// String qname =
		// (String)class1.getMethod("get"+code+"QuaName").invoke(conType);
		if (StringUtils.isNotBlank(cid)) {
			condition.setCategoryId(cid);
		}
		if (null != mu) {
			if (2 == mu && StringUtils.isNotBlank(cid)) {
				int size = conditionMapper.seleselectChildCate(cid.split(","))
						.size();
				condition.setCsize(size);
			}
			condition.setIsMulticondition(mu);
		}
		// 若勾选了父节点
		if (StringUtils.isNotBlank(pid)) {
			// 遍历出全部的其下的末级节点，去重拼进categoryId
			// cid = this.selectChild(pid,cid);
			String tempCateIdString = "";
			tempCateIdString = pid
					+ (StringUtils.isNotBlank(cid) ? ("," + cid) : "");
			condition.setCategoryId(tempCateIdString);
		}

		if (StringUtils.isNotBlank(le)) {
			condition.setLevelTypeId(le);
		}
		condition.setExtractNum(en);

		if (StringUtils.isNotBlank(ic)) {
			condition.setIsHavingConCert(ic);
		}
		if (StringUtils.isNotBlank(bu)) {
			condition.setBusinessNature(bu);
		}
		if (StringUtils.isNotBlank(ob)) {
			condition.setOverseasBranch(ob);
		}
		if (StringUtils.isNotBlank(qid)) {
			condition.setQuaId(qid);
		}

		// 处理地区查询条件
		if (StringUtils.isNotBlank(condition.getProvince())
				&& !"0".equals(condition.getProvince())) {
			String addr = "";
			for (String pro : condition.getProvinces()) {
				List<Area> areas = areaMapper.findAreaByParentId(pro);
				for (Area area : areas) {
					addr += "," + area.getId();
				}
			}
			addressId = StringUtils.isNotBlank(condition.getAddressId()) ? condition
					.getAddressId() + addr
					: StringUtils.isNotBlank(addr) ? addr.substring(1) : "";
			condition.setAddressId(addressId);
		} else if (StringUtils.isBlank(condition.getProvince())
				&& StringUtils.isBlank(condition.getAddressId())) {
			condition.setAddressId("null");
		}

		// 物资类 不限查询 需要将物资下的一起查询
		if ("GOODS".equals(typeCode)) {
			condition.setSupplierTypeCode("PRODUCT,SALES");
		}
		return code;
	}

	@Override
	public void setExtractCondition2(SupplierExtractCondition condition,String typeCode) {
		
		//处理产品类目
		String cid = condition.getCategoryId();
		String pid = condition.getParentId();
		String addressId = condition.getAddressId();
		
		if (2 == condition.getIsMulticondition()) {
			Set<String> cate = new HashSet<>();
			//查询所有的末级节点
			if(StringUtils.isNotBlank(cid)){
				cate.addAll(conditionMapper.seleselectChildCate(cid.split(",")));
			}
			if(StringUtils.isNotBlank(pid)){
				cate.addAll(conditionMapper.seleselectChildCate(pid.split(",")));
			}
			condition.setCsize(cate.size());
		}
		
		// 若勾选了父节点
		if (StringUtils.isNotBlank(pid)) {
			// 遍历出全部的其下的末级节点，去重拼进categoryId
			// cid = this.selectChild(pid,cid);
			String tempCateIdString = "";
			tempCateIdString = pid	+ (StringUtils.isNotBlank(cid) ? ("," + cid) : "");
			condition.setCategoryId(tempCateIdString);
		}
		
		// 处理地区查询条件
		if (StringUtils.isNotBlank(condition.getProvince())
				&& !"0".equals(condition.getProvince())) {
			String addr = "";
			for (String pro : condition.getProvinces()) {
				List<Area> areas = areaMapper.findAreaByParentId(pro);
				for (Area area : areas) {
					addr += "," + area.getId();
				}
			}
			addressId = StringUtils.isNotBlank(condition.getAddressId()) ? condition
					.getAddressId() + addr
					: StringUtils.isNotBlank(addr) ? addr.substring(1) : "";
			condition.setAddressId(addressId);
		} else if (StringUtils.isBlank(condition.getProvince())
				&& StringUtils.isBlank(condition.getAddressId())) {
			condition.setAddressId("null");
		}

		// 物资类 不限查询 需要将物资下的一起查询
		if ("GOODS".equals(typeCode)) {
			condition.setSupplierTypeCode("PRODUCT,SALES");
		}
	}
	
	@Override
	public int saveContype(SupplierExtractCondition condition,
			SupplierConType conType) {
		// 设置存储条件
		List<ExtractConditionRelation> list = new ArrayList<>();
		String cid = condition.getId();
		// 删除上次查询条件
		extractConditionRelationMapper.deleteConditionRelationByMap(condition
				.getId());
		// 省份，直辖市
		if (StringUtils.isNotEmpty(condition.getProvince())) {
			for (String province : condition.getProvinces()) {
				list.add(new ExtractConditionRelation(cid, "province", province));
			}
		}
		// 市、区
		if (StringUtils.isNotEmpty(condition.getAddressId())) {
			for (String addressId : condition.getAddressIds()) {
				list.add(new ExtractConditionRelation(cid, "addressId",
						addressId));
			}
		}
		// 供应商类别
		if (StringUtils.isNotEmpty(condition.getSupplierTypeCode())) {
			for (String supplierTypeCode : condition.getSupplierTypeCodes()) {
				list.add(new ExtractConditionRelation(cid, "supplierTypeCode",
						supplierTypeCode));
			}
		}

		Class<? extends SupplierConType> class1 = conType.getClass();
		String[] supplierTypeCodes = condition.getSupplierTypeCodes();
		try {
			for (String typeCode : supplierTypeCodes) {
				// 首字母大写
				String c = typeCode.toLowerCase();
				char[] cs = c.toCharArray();
				cs[0] -= 32;
				String code = String.valueOf(cs);

				Short mu = (Short) class1.getMethod(
						"get" + code + "IsMulticondition").invoke(conType);
				String cids = (String) class1.getMethod(
						"get" + code + "CategoryIds").invoke(conType);
				String le = (String) class1.getMethod("get" + code + "Level")
						.invoke(conType);
				Short en = (Short) class1
						.getMethod("get" + code + "ExtractNum").invoke(conType);
				String ic = (String) class1.getMethod(
						"get" + code + "IsHavingConCert").invoke(conType);
				String bu = (String) class1.getMethod(
						"get" + code + "BusinessNature").invoke(conType);
				String ob = (String) class1.getMethod(
						"get" + code + "OverseasBranch").invoke(conType);
				if (null != mu) {
					list.add(new ExtractConditionRelation(cid, c
							+ "IsMulticondition", mu.toString()));
				}
				if (StringUtils.isNotBlank(cids)) {
					for (String cId : cids.split(",")) {
						list.add(new ExtractConditionRelation(cid, c
								+ "CategoryId", cId));
					}
				}
				if (StringUtils.isNotBlank(le)) {
					for (String lv : le.split(",")) {
						list.add(new ExtractConditionRelation(cid, c + "Level",
								lv));
					}
				}
				if (null != en) {
					list.add(new ExtractConditionRelation(cid,
							c + "ExtractNum", en.toString()));
				}
				if (StringUtils.isNotBlank(ic)) {
					list.add(new ExtractConditionRelation(cid, c
							+ "IsHavingConCert", ic));
				}
				if (StringUtils.isNotBlank(bu)) {
					list.add(new ExtractConditionRelation(cid, c
							+ "BusinessNature", StringUtils.isBlank(bu) ? "0"
							: bu));
				}
				if (StringUtils.isNotBlank(ob)) {
					list.add(new ExtractConditionRelation(cid, c
							+ "OverseasBranch", ob));
				}
			}
			if (list.size() > 0) {
				return extractConditionRelationMapper
						.insertConditionRelation(list);
			}
		} catch (Exception e) {
			e.getMessage();
		}
		return 0;
	}
	@Override
	public int saveContype2(SupplierExtractCondition condition) {
		// 设置存储条件
		List<ExtractConditionRelation> list = new ArrayList<>();
		String cid = condition.getId();
		// 删除上次查询条件
		extractConditionRelationMapper.deleteConditionRelationByMap(condition
				.getId());
		// 省份，直辖市
		if (StringUtils.isNotEmpty(condition.getProvince())) {
			for (String province : condition.getProvinces()) {
				list.add(new ExtractConditionRelation(cid, "province", province));
			}
		}
		// 市、区
		if (StringUtils.isNotEmpty(condition.getAddressId())) {
			for (String addressId : condition.getAddressIds()) {
				list.add(new ExtractConditionRelation(cid, "addressId",
						addressId));
			}
		}
		
		Set<String> hashSet = new HashSet<>();
		String cids = condition.getCategoryId();
		if (StringUtils.isNotBlank(cids)) {
			List<String> asList = Arrays.asList(cids.split(","));
			hashSet.addAll(asList);
		} 
		String pids = condition.getParentId();
		if (StringUtils.isNotBlank(pids)) {
			List<String> asList = Arrays.asList(pids.split(","));
			hashSet.addAll(asList);
		}
		if(hashSet.size()>0){
			for (String cId : hashSet) {
				list.add(new ExtractConditionRelation(cid, "categoryId", cId));
			}
		}
		String le = condition.getLevelTypeId();
		if (StringUtils.isNotBlank(le)) {
			for (String lv : le.split(",")) {
				list.add(new ExtractConditionRelation(cid, "levelTypeId",	lv));
			}
		}
		Short en = condition.getExtractNum();
		if (null != en) {
			list.add(new ExtractConditionRelation(cid,"currentExtractNum", en.toString()));
		}
		
		String[] quaIds = condition.getQuaIds();
		if(null!=quaIds){
			for (String quaId : quaIds) {
				list.add(new ExtractConditionRelation(cid, "quaId", quaId));
			}
		}
		String businessScope = condition.getBusinessScope();
		if (StringUtils.isNotBlank(businessScope)) {
			list.add(new ExtractConditionRelation(cid,"businessScope",businessScope));
		}
		
		if(condition.getSupplierTypeCode().equals("GOODS") && StringUtils.isNotBlank(condition.getSalesLevelTypeId())){
			for (String sl : condition.getSalesLevelTypeIds()) {
				list.add(new ExtractConditionRelation(cid, "salesLevelTypeId", sl));
			}
		}
		
		if (list.size() > 0) {
			return extractConditionRelationMapper.insertConditionRelation(list);
		}
		return 0;
	}

}
