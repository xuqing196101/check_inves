package bss.service.ob.impl;

import java.io.File;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.bms.DictionaryDataMapper;
import ses.dao.oms.OrgnizationMapper;
import ses.model.bms.DictionaryData;
import ses.model.bms.User;
import ses.model.oms.Orgnization;
import ses.util.DictionaryDataUtil;
import ses.util.PropertiesUtil;
import synchro.service.SynchRecordService;
import synchro.util.FileUtils;
import synchro.util.OperAttachment;
import bss.dao.ob.OBProductInfoMapper;
import bss.dao.ob.OBProductMapper;
import bss.dao.ob.OBProjectMapper;
import bss.dao.ob.OBProjectResultMapper;
import bss.dao.ob.OBProjectRuleMapper;
import bss.dao.ob.OBProjectSupplierMapper;
import bss.dao.ob.OBResultSubtabulationMapper;
import bss.dao.ob.OBResultsInfoMapper;
import bss.dao.ob.OBRuleMapper;
import bss.dao.ob.OBSpecialDateMapper;
import bss.dao.ob.OBSupplierMapper;
import bss.model.ob.OBProduct;
import bss.model.ob.OBProductInfo;
import bss.model.ob.OBProject;
import bss.model.ob.OBProjectResult;
import bss.model.ob.OBProjectRule;
import bss.model.ob.OBProjectSupplier;
import bss.model.ob.OBResultSubtabulation;
import bss.model.ob.OBResultsInfo;
import bss.model.ob.OBRule;
import bss.model.ob.OBRuleTimeInterval;
import bss.model.ob.OBSpecialDate;
import bss.model.ob.OBSupplier;
import bss.service.ob.OBProjectServer;
import bss.util.BiddingStateUtil;
import bss.util.CheckUtil;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;

import common.constant.Constant;
import common.dao.FileUploadMapper;
import common.model.UploadFile;
import common.service.UploadService;
import common.utils.DateUtils;

/**
 * 竞价信息管理接口实现
 * 
 * @author YangHongLiang
 * 
 */
@Service("OBProject")
public class OBProjectServerImpl implements OBProjectServer {
	@Autowired
	private OBProjectMapper OBprojectMapper;
	/** 竞价产品 **/
	@Autowired
	private OBProductMapper OBProductMapper;

	@Autowired
	private OBSupplierMapper OBSupplierMapper;

	@Autowired
	private OBProductInfoMapper OBProductInfoMapper;

	@Autowired
	private OBProjectResultMapper OBProjectResultMapper;
	/**文件**/
	@Autowired
    private UploadService uploadService;
	@Autowired
	private OBProjectRuleMapper OBProjectRuleMapper;
	/**
	 * 上传附件
	 */
    @Autowired
	private FileUploadMapper fileUploadMapper;
	/***
	 * 竞价信息和供应商关系表
	 */
	@Autowired
	private OBProjectSupplierMapper OBProjectSupplierMapper;
	/** 竞价规则 **/
	@Autowired
	private OBRuleMapper OBRuleMapper;
	/** 特殊日期 **/
	@Autowired
	private OBSpecialDateMapper OBSpecialDateMapper;
	@Autowired
	private OBResultsInfoMapper OBResultsInfoMapper;
	@Autowired
	private OBResultSubtabulationMapper OBResultSubtabulationMapper;
	 /**
     * 同步service
     */
    @Autowired
    private SynchRecordService recordService;
	// 注入竞价规则子表
	@Autowired
	private OBProjectRuleMapper obProjectRuleMapper;
	
	// 注入采购机构Mapper
	@Autowired
	private OrgnizationMapper orgnizationMapper;
	// 定义 竞价控制类型
	private int type = 2;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Autowired
	private OBProjectSupplierMapper obProjectSupplierMapper;
	
	@Autowired
	private OBProjectMapper obProjectMapper;
	
	@Autowired
	private DictionaryDataMapper dictionaryDataMapper;
	//定义 随机数 范围最大值
	private int max=5;
	//定义 随机数 范围最小值
	private  int min=3;
	@Override
	public List<OBProject> list(OBProject op) {
		// TODO Auto-generated method stub
		return OBprojectMapper.selectPageList(op);
	}

	/** ---------------竞价看板模块---------------- **/

	/**
	 * 竞价信息列表查询
	 * 
	 * @author Easong
	 */
	@Override
	public List<OBProject> selectAllOBproject(Map<String, Object> map) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage((Integer) (map.get("page")),
				Integer.parseInt(config.getString("pageSize")));
		List<OBProject> list = OBprojectMapper.selectAllOBproject(map);
		if (list != null) {
			for (OBProject obp : list) {
				if(obp.getStatus()!=0){
					// 获取产品集合
					List<OBProductInfo> slist = OBProductInfoMapper
							.selectByProjectId(obp.getId());
					// 存储 产品id 集合
					List<String> pidList = new ArrayList<String>();
					if (slist != null) {
						for (OBProductInfo obinfo : slist) {
							pidList.add(obinfo.getProductId());
						}
						Map<String, Object> maps = new HashMap<String, Object>();
						maps.put("list", pidList);
						maps.put("date", new Date());
						// 获取 成交供应商 数量
						Integer closingSupplier = OBProjectResultMapper.countByStatus(obp.getId());
						obp.setProductName(pidList);
						if(closingSupplier==null){
							closingSupplier=0;
						}
						obp.setClosingSupplier(closingSupplier);
						
						// 获取  供应商数量
						List<OBSupplier> sulist=OBSupplierMapper.selectSupplierByID(maps);
						Integer qualifiedSupplier =0;
						if(sulist!=null&&sulist.size()>0){
							qualifiedSupplier=sulist.size();
						}
						obp.setQualifiedSupplier(qualifiedSupplier);
					}
				}
			}
		}
		return list;
	}

	/**
	 * 实现获取竞价产品相关信息
	 */
	@Override
	public String getProduct(Map<String,Object> map) {
		// TODO Auto-generated method stub
		List<OBProduct> list = OBProductMapper.selpro(map);
		return JSON.toJSONString(list);

	}

	@Override
	public List<OBProduct> productList() {
		// TODO Auto-generated method stub
		return OBProductMapper.selectList();
	}

	/**
	 * 实现 保存 竞价信息
	 * 
	 * @author YangHongLiang
	 * @throws ParseException
	 */
	@Override
	public String saveProject(OBProject obProject, String userid, String fileid) {
		// TODO Auto-generated method stub
		String attribute = "";
		String show = "";
		//
		if(obProject.getStatus()==1){
		if (StringUtils.isBlank(obProject.getName())) {
			attribute = "nameErr";
			show = "竞价标题不能为空";
			return toJsonProject(attribute, show);
		}
		if (obProject.getName().length() > 101) {
			attribute = "nameErr";
			show = "竞价标题长度过长";
			return toJsonProject(attribute, show);
		}
		if (obProject.getDeliveryDeadline() == null) {
			attribute = "deliveryDeadlineErr";
			show = "交货日期不能为空";
			return toJsonProject(attribute, show);
		}
		if (StringUtils.isBlank(obProject.getDeliveryAddress())) {
			attribute = "deliveryAddressErr";
			show = "交货地点不能为空";
			return toJsonProject(attribute, show);
		}
		if (obProject.getDeliveryAddress().length() > 151) {
			attribute = "deliveryAddressErr";
			show = "交货地点长度过长";
			return toJsonProject(attribute, show);
		}
		if (obProject.getTradedSupplierCount() == null) {
			attribute = "tradedSupplierCountErr";
			show = "成交供应商数量不能为空";
			return toJsonProject(attribute, show);
		}
		if (obProject.getTransportFees() == null) {
			attribute = "transportFeesErr";
			show = "运杂费不能为空";
			return toJsonProject(attribute, show);
		}
		if (obProject.getTransportFees().toString().length() > 40) {
			attribute = "transportFeesErr";
			show = "运杂费长度过长";
			return toJsonProject(attribute, show);
		}
		if (StringUtils.isBlank(obProject.getDemandUnit())) {
			attribute = "demandUnitErr";
			show = "需求单位不能为空";
			return toJsonProject(attribute, show);
		}
		if (obProject.getDemandUnit().length() > 50) {
			attribute = "demandUnitErr";
			show = "需求单位长度过长";
			return toJsonProject(attribute, show);
		}
		if (StringUtils.isBlank(obProject.getContactName())) {
			attribute = "contactNameErr";
			show = "联系人不能为空";
			return toJsonProject(attribute, show);
		}
		if (obProject.getContactName().length() > 20) {
			attribute = "contactNameErr";
			show = "联系人长度过长";
			return toJsonProject(attribute, show);
		}
		if (StringUtils.isBlank(obProject.getContactTel())) {
			attribute = "contactTelErr";
			show = "联系人电话不能为空";
			return toJsonProject(attribute, show);
		}
		if (obProject.getContactTel().length() > 20) {
			attribute = "contactTelErr";
			show = "联系人电话长度过长";
			return toJsonProject(attribute, show);
		}

		if (StringUtils.isBlank(obProject.getOrgId())) {
			attribute = "orgIdErr";
			show = "采购机构不能为空";
			return toJsonProject(attribute, show);
		}
		if (StringUtils.isBlank(obProject.getOrgContactTel())) {
			attribute = "orgContactTelErr";
			show = "采购联系人电话不能为空";
			return toJsonProject(attribute, show);
		}
		if (StringUtils.isBlank(obProject.getOrgContactName())) {
			attribute = "orgContactNameErr";
			show = "采购联系人不能为空";
			return toJsonProject(attribute, show);
		}
		if (obProject.getIsEmergency()==null) {
			attribute = "isEmergencyErr";
			show = "是否为应急采购项目选项不能为空";
			return toJsonProject(attribute, show);
		}
		if (obProject.getIsEmergency()!=-1 && obProject.getIsEmergency()!=0) {
			attribute = "isEmergencyErr";
			show = "是否为应急采购项目选项非法";
			return toJsonProject(attribute, show);
		}
		
		if (StringUtils.isBlank(obProject.getContent())) {
			attribute = "contentErr";
			show = "竞价内容不能为空";
			return toJsonProject(attribute, show);
		}
		if (obProject.getContent().length() > 1000) {
			attribute = "contentErr";
			show = "竞价内容长度过长";
			return toJsonProject(attribute, show);
		}

		if (obProject.getProductName() == null) {
			attribute = "buttonErr";
			show = "竞价产品名称不能为空";
			return toJsonProject(attribute, show);
		}
		if (CheckUtil.isList(obProject.getProductName())) {
			attribute = "buttonErr";
			show = "竞价产品名称不能为空";
			return toJsonProject(attribute, show);
		}
		List<UploadFile> fileList= fileUploadMapper.getFiles(Constant.TENDER_SYS_VALUE, obProject.getAttachmentId(), DictionaryDataUtil.getId("BIDD_INFO_MANAGE_ANNEX"));
		 if(fileList==null||fileList.size()==0){
		   attribute = "fileUploadErr";
		   show = "附件不能为空";
		   return toJsonProject(attribute, show);
		 }
		
		
		if (obProject.getProductCount() == null) {
			attribute = "buttonErr";
			show = "竞价产品数量不能为空";
			return toJsonProject(attribute, show);
		}
		if (CheckUtil.isList(obProject.getProductCount())) {
			attribute = "buttonErr";
			show = "竞价产品数量不能为空";
			return toJsonProject(attribute, show);
		}
		if (obProject.getStatus() == null) {
			obProject.setStatus(0);
		}
		String verify=verifyProduct(obProject.getProductName(), obProject.getTradedSupplierCount());
		if(verify==null){
			attribute = "buttonErr";
			show = "竞价产品有误";
			return toJsonProject(attribute, show);
		}
		//验证 产品 信息 
		if(!verify.equals("success")){
			return verify;
		}
		}
		// 生成ID
		String uuid = UUID.randomUUID().toString().toUpperCase()
				.replace("-", "");
		// 默认规则
		// 暂存修改的时候使用的是子表中的规则
		// 获取间隔日
		int intervalWorkday = 0;
		// 获取竞价开始 时间忽略年月日
		Date definiteTime = null;
		// 获取报价时间
		int quoteTime = 0;
		// 确认时间
		int confirmTime;
		// 第二轮 确认时间
		int confirmTimeSecond;
		// 父表规则
		OBRule obr = null;
		// 子表负责
		OBProjectRule obProjectRule = null;
		// 编辑页面时  以及暂存后发布 调用的规则  
		if((obProject.getStatus() == 0 && StringUtils.isNotEmpty(obProject.getId()))
				|| (obProject.getStatus() == 1 && StringUtils.isNotEmpty(obProject.getId()))){
			obProjectRule = obProjectRuleMapper.selectByPrimaryKey(obProject.getId());
			if(obProjectRule == null){
				show = "发布竞价请先设置默认规则";
				return toJsonProject("attribute", show);
			}
			intervalWorkday = obProjectRule.getIntervalWorkday();
			definiteTime = obProjectRule.getDefiniteTime();
			quoteTime = obProjectRule.getQuoteTime();
			confirmTime = obProjectRule.getConfirmTime();
			confirmTimeSecond = obProjectRule.getConfirmTimeSecond();
		}
		// 发布时调用的规则
		if((obProject.getStatus() == 0 && StringUtils.isEmpty(obProject.getId()))
		|| (obProject.getStatus() == 1 && StringUtils.isEmpty(obProject.getId()))){
			obr = OBRuleMapper.selectByPrimaryKey(obProject.getRuleId());
			if(obr ==null){
				show = "发布竞价请先设置默认规则";
				return toJsonProject("attribute", show);
			}
			// 将竞价规则copy到子表中--获取通过竞价的ID
			OBProjectRule obProjectRules = new OBProjectRule();
			BeanUtils.copyProperties(obr, obProjectRules);
			// 设置竞价子表的ID为竞价的ID
			obProjectRules.setId(uuid);
			//生成随机 浮动比例 数
			Random random = new Random();
			int valid = random.nextInt(max)%(max-min+1) + min;
			obProjectRules.setFloatPercent(valid);
		     
			// 将竞价规则存入子表中
			obProjectRuleMapper.insert(obProjectRules);
			intervalWorkday = obr.getIntervalWorkday();
			definiteTime = obr.getDefiniteTime();
			quoteTime = obr.getQuoteTime();
			confirmTime = obr.getConfirmTime();
			confirmTimeSecond = obr.getConfirmTimeSecond();
		}
		// 获取当前日期
		Date date = new Date();
		// 1.当前时间加上间隔日
		Date dateG = DateUtils.addDayDate(date, intervalWorkday);
		// date加上竞价开始时间 计算竞价开始时间 未加特殊节假日
		Date startdate = DateUtils.changeDate(dateG, definiteTime);

		// 封装map
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startDate", DateUtils.combinationDate(startdate));
		// 根据规则 和特殊日期 节假日 组合竞价开始时间
		List<OBSpecialDate> specialDateList = OBSpecialDateMapper
				.selectBySpecialDate(map);
		// 2.date时间加上 节假日
		if (specialDateList != null && specialDateList.size() > 0) {
			// 调用排除 特殊假期
			startdate = recursion(specialDateList,
					DateUtils.combinationDate(startdate), type,
					specialDateList.size());
			// 判断是否是特殊日期上班 如果不是排除 周末假日
			if (getType() != 3) {
				startdate = DateUtils.ignoreWeekend(startdate);
			}
		}
		// 获取竞价结束时间 竞价开始时间+ 报价
		Date endDate = DateUtils.getAddDate(startdate, quoteTime);
		obProject.setCreaterId(userid);
		obProject.setStartTime(startdate);
		obProject.setEndTime(endDate);
		List<OBProductInfo> list = new ArrayList<OBProductInfo>();
		// 如果有 id 更新数据
		if (StringUtils.isNotBlank(obProject.getId())) {
			obProject.setUpdatedAt(date);
			OBprojectMapper.updateByPrimaryKeySelective(obProject);
			// 组合 集合
			list = splitList(list, obProject, userid);
			for (OBProductInfo b : list) {
				b.setUpdatedAt(date);
				OBProductInfoMapper.updateByPrimaryKeySelective(b);
			}
			if (obProject.getStatus() == 1) {
				// 更新关系数据
				addSupplier(obProject,obProject.getId(), 1);
				//如果发布 更新规则数量 时间
				OBRuleMapper.updateCount(obProject.getRuleId(),date);
			}
		} else {
			obProject.setId(uuid);
			obProject.setCreatedAt(date);
			
			list = splitList(list, obProject, userid);
			
			SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
			SimpleDateFormat ymd=new SimpleDateFormat("yyyy-MM-dd");
			Date start=new Date();
			//生成竞价编号
			int countByDate=OBprojectMapper.countByDate(ymd.format(start));
			BigDecimal big=new BigDecimal(sdf.format(start)+"00000");
			obProject.setProjectNumber(big.add(new BigDecimal(countByDate+1)));
			if (obProject.getStatus() == 1) {
				// 组合 集合
				//如果发布 更新规则数量 时间
				obProject.setUpdatedAt(date);
				// 保存关系数据
				addSupplier(obProject,uuid, 0);
			}
			int i = OBprojectMapper.insertSelective(obProject);
			if (i > 0) {
				for (OBProductInfo b : list) {
					OBProductInfoMapper.insertSelective(b);
				}
				
				if (obProject.getStatus() == 1) {
					//如果发布 更新规则数量 时间
					OBRuleMapper.updateCount(obProject.getRuleId(),date);
				}
			}
		}
		return toJsonProject("success", "执行成功");
	}
	/***
	 * 验证 产品的成交供应商数量不得超过 该产品注册的供应商数量的1/4 (忽略且最多不能超过6家)
	 * @param count 供应商成交数量
	 */
     private String verifyProduct(List<String> productName,Integer count){
    	  int tempCount=0;
    	 for (int i = 0; i < productName.size(); i++) {
    	   tempCount=OBSupplierMapper.countProductId(productName.get(i),new Date());
    	   tempCount=tempCount/4;
    		 if(tempCount<count){
    			 return toJsonProject("pName", productName.get(i));
    		 }
		}
    	 List<OBProduct> productList= OBProductMapper.selectInId(productName);
    	 return checkCatalog(productList);
     }
     /***
      *  验证 该产品的是否属于同一个目录 如果不属于那么不能发布竞价
      */
     public String checkCatalog(List<OBProduct> productList){
    	 //外循环
    	 OBProduct abroad=null;
    	 //内循环
    	 OBProduct within=null;
    	 if(productList.size()>1){
    	 //嵌套 便利判断产品集合
    	 for (int i = 0; i < productList.size(); i++) {
    		 abroad=productList.get(i);
    		 //目录id
    		 String catalogA=abroad.getSmallPointsId();
    		 if(catalogA ==null || catalogA==""){
    			 catalogA="1";
    		 }
    		  for (int j = 0; j < productList.size(); j++) {
    			  within=productList.get(j);
    			  //目录id
    	         String catalogW=within.getSmallPointsId();
    	         if(catalogW ==null || catalogW==""){
    	        	 catalogW="2";
        		 }
    			  //循环判断 排除本身
				if(j!=i){
					if(catalogA.equals(catalogW)){
						 return "success";
					}else{
						return toJsonProject("catalog", abroad.getId());
					}
				}
			}
    	 }
    	 }else{
    		 return "success";
    	 }
		return null;
    	 
     }
	/***
	 * 封装插入 竞价信息 供应商关系表
	 * 
	 * @param i类型区分
	 *            是否插入更新 时间
	 */
	private void addSupplier(OBProject obProject,String uuid, int i) {
			OBProjectSupplier supplier = null;
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("list", obProject.getProductName());
			map.put("date", new Date());
			Date date=new Date();
			List<OBSupplier> supList= OBSupplierMapper.selectSupplierByID(map);
			   for(OBSupplier os:supList){
				   supplier = new OBProjectSupplier();
					supplier.setId(UUID.randomUUID().toString().toUpperCase()
							.replace("-", ""));
					supplier.setCreatedAt(new Date());
					supplier.setProjectId(uuid);
					supplier.setRemark("0");
					// 存储 目录
					supplier.setSupplierPrimaryId(os.getSmallPointsId());
					if (i == 1) {
						supplier.setUpdatedAt(date);
					}
					supplier.setSupplierId(os.getSupplierId());
					OBProjectSupplierMapper.insertSelective(supplier);
			   }
			/*for (int j = 0; j < list.length; j++) {
				supplier = new OBProjectSupplier();
				supplier.setId(UUID.randomUUID().toString().toUpperCase()
						.replace("-", ""));
				supplier.setCreatedAt(new Date());
				supplier.setProjectId(uuid);
				if (i == 1) {
					supplier.setUpdatedAt(new Date());
				}
				supplier.setSupplierId(list[j]);
				OBProjectSupplierMapper.insertSelective(supplier);
			}*/
	}

	/**
	 * 拆分 集合
	 * */
	private List<OBProductInfo> splitList(List<OBProductInfo> list,
			OBProject obProject, String userid) {
		OBProductInfo product = null;
		Date date=new Date();
		// 拆分数组
		List<String> productName = obProject.getProductName();
		if(productName!=null){
		for (int i = 0; i < productName.size(); i++) {
			String uid = UUID.randomUUID().toString().toUpperCase()
					.replace("-", "");
			product = new OBProductInfo();
			product.setId(uid);
			product.setProductId(productName.get(i));
			if (obProject.getProductMoney().size()==0) {
				product.setLimitedPrice(null);
			} else {
				if(obProject.getProductMoney().get(i)=="" ){
					product.setLimitedPrice(null);
				}else{
					product.setLimitedPrice(new BigDecimal(Double.valueOf(obProject
							.getProductMoney().get(i))));
				}
			}
			if (obProject.getProductRemark().size()==0) {

			} else {
				if(obProject.getProductRemark().get(i)=="" ){
					product.setRemark("");
				}else{
					product.setRemark(obProject.getProductRemark().get(i));
				}
				
			}
			if (obProject.getProductCount().size()==0) {
				product.setPurchaseCount(new BigDecimal(0));
			} else {
				if(obProject.getProductCount().get(i)==""){
					product.setPurchaseCount(new BigDecimal(0));
				}else{
					product.setPurchaseCount(new BigDecimal(obProject.getProductCount().get(i)));
				}
				
			}
			product.setProjectId(obProject.getId());
			product.setCreatedAt(date);
			product.setCreaterId(String.valueOf(i));
			list.add(product);
		 }
		}
		return list;

	}

	/**
	 * 计算忽略 特殊
	 * 
	 * @param specialDateList
	 *            特殊日期集合
	 * @param date
	 *            竞价时间
	 * @param type
	 *            控制分类
	 * @param size
	 *            集合的数量
	 * @author YangHongLiang
	 * @return date
	 * @throws
	 */
	private Date recursion(List<OBSpecialDate> specialDateList, Date start,
			int type, int size) {
		for (OBSpecialDate sd : specialDateList) {
			if (type == 2) {
				if (sd.getSpecialDate().getTime() == start.getTime()) {
					if (sd.getDateType().equals("0")) {// 放假
						type = 2;
						start = DateUtils.addDayDate(start, 1);
						break;
					} else if (sd.getDateType().equals("1")) {// 上班
						type = 3;
						start = sd.getSpecialDate();
						break;
					}
				}
			}
		}
		if (size > 0 && type == 2) {
			size = size - 1;
			recursion(specialDateList, start, type, size);
		}
		setType(type);
		return start;
	}

	/***
	 * 封装 验证 竞价 json
	 */
	private String toJsonProject(String name, String context) {
		return "{\"attributeName\":\"" + name + "\",\"show\":\"" + context
				+ "\"}";
	}

	/***
	 * 实现 查询产品信息
	 * 
	 * @author YangHongLiang
	 * @param string
	 */
	@Override
	public List<OBProject> List(Map<String, Object> map) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage((Integer) (map.get("page")),
				Integer.parseInt(config.getString("pageSize")));

		List<OBProject> list = OBprojectMapper.selectData(map);
		if (list != null) {
			for (OBProject obp : list) {
				
				// 获取产品集合
				List<OBProductInfo> slist = OBProductInfoMapper.selectByProjectId(obp.getId());
				// 存储 产品id 集合
				List<String> pidList = new ArrayList<String>();
				if (slist != null) {
					for (OBProductInfo obinfo : slist) {
						if(obinfo!=null){
						pidList.add(obinfo.getProductId());
						}
					}
					Map<String, Object> maps = new HashMap<String, Object>();
					maps.put("list", pidList);
					maps.put("date", new Date());
					Integer closingSupplier=0;
					if(obp.getStatus()!=0 && pidList!=null && pidList.size()>0 ){
					// 获取 中标供应商 数量
						closingSupplier = OBProjectResultMapper.countProportion(obp.getId());
					obp.setProductName(pidList);
					}
					obp.setClosingSupplier(closingSupplier);
					// 获取  供应商数量
					Integer qualifiedSupplier =0;
					if(obp.getStatus()!=0 && pidList!=null && pidList.size()>0 ){
					List<OBSupplier> sulist=OBSupplierMapper.selectSupplierByID(maps);
					if(sulist!=null&&sulist.size()>0){
						qualifiedSupplier=sulist.size();
					   }
					}
					obp.setQualifiedSupplier(qualifiedSupplier);
				}
				String smallPointsId = null;
				if(obp.getStatus()!=0 && pidList!=null && pidList.size()>0 ){
				if(obp.getId() != null){
					List<OBProjectSupplier> listps = obProjectSupplierMapper.selByProjectId(obp.getId());
					if(listps != null && listps.size() > 0){
						smallPointsId = listps.get(0).getSupplierPrimaryId();
					}
				}
				Integer nn = OBprojectMapper.selOfferSupplierNum(obp.getId(),smallPointsId);
				obp.setOfferSupplierNumber(nn);
				}
			}
		}
		return list;
	}

	/**
	 * 根据竞价信息id查询
	 */
	@Override
	public OBProject selectByPrimaryKey(String id) {
		return OBprojectMapper.selectByPrimaryKey(id);
	}

	/***
	 * 实现根据规则 更新状态
	 * 
	 * @author YangHongLiang
	 * 
	 */

	@Override
	public void changeStatus(String projectId) {
		// TODO Auto-generated method stub
		// 1.获取 不是暂存 和结束 的竞价数据
		List<OBProject> getOBProject = OBprojectMapper.selectByStatus(projectId);
		if (getOBProject != null) {
			// 获取当前 默认规则
			for (OBProject op : getOBProject) {
				OBProjectRule obRule = OBProjectRuleMapper.selectByPrimaryKey(op.getId());
				if(obRule!=null){
				 //确认时间
				 int confirmTime=obRule.getConfirmTime();
				 //第二轮 确认时间
				 int confirmTimeSecond=obRule.getConfirmTimeSecond();
				 //第二次报价
				 int quoteTimeSecond=obRule.getQuoteTimeSecond();
				 //最少供应商报价数量
				 int leastSupplierNum=obRule.getLeastSupplierNum();
				 // 有效百分比
				 int percent=obRule.getPercent();
				 //浮动
				 int floatPercent=obRule.getFloatPercent();
				 //标识第二次竞价
				 String secoundBidding="1";
				 //标识 默认0
				 int type=0;
				/** 竞价状态 0：暂存 1已发布 2竞价中 3：竞价结束 4.流拍 5待确认 6第二次确认 7.第二次竞价**/
				switch (op.getStatus()) {
				// 已发布
				case 1:
					// 判断 竞价结束时间 是否已到
					int compare = DateUtils.compareDate(new Date(),
							op.getStartTime());
					// 比较 竞价信息 如果不等于 -1  那么说明是该竞价 处于竞价中
					if (compare != -1) {
						// 根据状态竞价中
						OBProject upstatus = new OBProject();
						upstatus.setStatus(2);
						upstatus.setId(op.getId());
						upstatus.setUpdatedAt(new Date());
						OBprojectMapper.updateByPrimaryKeySelective(upstatus);
					}
					break;
				case 7://第二次竞价
					secoundBidding="2";
				case 2://第一轮竞价中
						// 根据竞价id 获取 是否 在规定的时间内 参与报价
					int compareDate = DateUtils.compareDate(new Date(),op.getEndTime());
					// 比较 竞价信息 如果等于1  那么是竞价 报价结束的时间
					if (compareDate == 1) {
						//获取全部竞价数量 当前报价数量
						Integer second= OBResultsInfoMapper.countByBidding(op.getId(), secoundBidding, null);
						List<OBResultsInfo> obresultsList=null;
						//两家重新竞价  并且是第一次报价
						if( secoundBidding.equals("1")){
				    	if(second==2){
				    		//第一次 报价只有两家供应商
				    		type=1;
				    	 }else{
				    		 //第一次 报价
				    		 type=2;
				    	 }
				    	}
						//获取全部竞价数量
				    	Integer allSecond= OBResultsInfoMapper.countBiddingByID(op.getId());
						//判断是否第二次 报价
						if(type==1){
							//报价数量 大于最少规则报价数量
							if(allSecond<leastSupplierNum){
								OBProject upstatus1 = new OBProject();
								upstatus1.setStatus(4);
								upstatus1.setId(op.getId());
								upstatus1.setUpdatedAt(new Date());
								OBprojectMapper.updateByPrimaryKeySelective(upstatus1);
							}else{
							 //只有两家供应商报价 那么进行第二次竞价
				    		// 根据状态竞价中 修改竞价结束时间加上第二次 竞价时间
							OBProject upstatus = new OBProject();
							upstatus.setStatus(7);
							upstatus.setUpdatedAt(new Date());
							upstatus.setId(op.getId());
							upstatus.setEndTime(DateUtils.getAddDate(op.getEndTime(), quoteTimeSecond));
							OBprojectMapper.updateByPrimaryKeySelective(upstatus);
							//修改关系表 状态 20
							List<OBResultsInfo> resultsInfoList=OBResultsInfoMapper.selectByBidding(op.getId(), secoundBidding, null);
							for (OBResultsInfo obResultsInfo : resultsInfoList) {
								OBProject obProject = new OBProject();
								obProject.setId(op.getId());
								User users = new User();
								users.setTypeId(obResultsInfo.getSupplierId());
								String remark = "20";
								BiddingStateUtil.updateRemark(OBProjectSupplierMapper, obProject, users, remark);
							  }
							}
						}else{
					    	//总竞价数量 是否符合规则
					    	if(allSecond>=leastSupplierNum){
				    		//判断供应商报价数量 四家以上供应商基准价
							if(second>=4){
						     obresultsList=benchmark(op.getId(), null, floatPercent,secoundBidding,percent);
						     //修改 规则附表的 浮动比例字段
						     obRule.setFloatPercent(floatPercent);
						     obProjectRuleMapper.updateByPrimaryKeySelective(obRule);
							}
							//三家及以下供应商数量为最低价法
					    	if(second<=3){
					    		//供应商报价数据3个的时间 采用 最低价法 计算 排名
								obresultsList = OBResultsInfoMapper.selectByProjectId(op.getId(),secoundBidding);
					    	}
				    	//标识竞价 是否可以进行
				    	boolean boo=false;
							//判断竞价是否是 应急采购 
							if(op.getIsEmergency()==0){
								//报价人数是1人 符合应急竞价
								if(allSecond==1){
								 boo=true;
								}
							}else{
								//报价人数 也是1人
								if(allSecond==1){
									//获取总价
								BigDecimal deal=	OBResultsInfoMapper.sumAllDealMoney(op.getId());
								 if(deal!=null){
									 //判断总价 小于等于 50000 那么可以进行竞价
									 if(deal.compareTo(new BigDecimal(50000))!=1){
										 boo=true;
									 }
								 }
								}
							}
						// 判断 是否有报价价供应商
						if (obresultsList != null && obresultsList.size() > 0) {
						//判读报价数量是否 达到竞价成交供应商数量   或者 该竞价 是应急竞价
						if( leastSupplierNum<=obresultsList.size()||boo){
							for (int i = 0; i < obresultsList.size(); i++) {
								Integer rk[] = null;
								int proportion = 0;
								if(boo==true){
									op.setTradedSupplierCount(1); 
								}
								if (op.getTradedSupplierCount() == 1) {
									rk = Constant.OB_PROJECT_ONE;
								} else if (op.getTradedSupplierCount() == 2) {
									rk = Constant.OB_PROJECT_TWO;
								} else if (op.getTradedSupplierCount() == 3) {
									rk = Constant.OB_PROJECT_THREE;
								} else if (op.getTradedSupplierCount() == 4) {
									rk = Constant.OB_PROJECT_FOUR;
								} else if (op.getTradedSupplierCount() == 5) {
									rk = Constant.OB_PROJECT_FIVE;
								} else if (op.getTradedSupplierCount() == 6) {
									rk = Constant.OB_PROJECT_SIX;
								}
								// 生成ID
								String uuid = UUID.randomUUID().toString()
										.toUpperCase().replace("-", "");
								OBProjectResult rsult = new OBProjectResult();
								rsult.setRanking(i + 1);
								rsult.setSupplierId(obresultsList.get(i)
										.getSupplierId());
								rsult.setProjectId(op.getId());
								rsult.setId(uuid);
								rsult.setCreatedAt(new Date());
								 if(rk.length>i){
									 proportion=rk[i];
								 }else{
									 proportion=0 ;
								 }
								 if(proportion==0){
									 //第一轮 未中标
							    	OBProject obProject = new OBProject();
									obProject.setId(op.getId());
									User users = new User();
									users.setTypeId(obresultsList.get(i)
											.getSupplierId());
									String remark = "666";
									BiddingStateUtil.updateRemark(OBProjectSupplierMapper, obProject, users, remark);
								 }
								rsult.setProportion(String.valueOf(proportion));
								// 将 参与报价的供应商 插入到结果数据中
								OBProjectResultMapper.insertSelective(rsult);
							}
							// 根据状态竞价中 修改竞价结束时间
							OBProject upstatus1 = new OBProject();
							upstatus1.setStatus(5);
							upstatus1.setUpdatedAt(new Date());
							upstatus1.setId(op.getId());
							upstatus1.setEndTime(DateUtils.getAddDate(
									op.getEndTime(), confirmTime));
							OBprojectMapper
									.updateByPrimaryKeySelective(upstatus1);
							}else{
								// 到规定时间  报价人数未达到 竞价成交供应商数量 流拍
								OBProject upstatus1 = new OBProject();
								upstatus1.setStatus(4);
								upstatus1.setId(op.getId());
								upstatus1.setUpdatedAt(new Date());
								OBprojectMapper.updateByPrimaryKeySelective(upstatus1);
							 }
							}else{
								// 到规定时间 如果没有竞价供应商  修改竞状态 流拍
								
								OBProject upstatus1 = new OBProject();
								upstatus1.setStatus(4);
								upstatus1.setId(op.getId());
								upstatus1.setUpdatedAt(new Date());
								OBprojectMapper.updateByPrimaryKeySelective(upstatus1);
							}
						 } else {
							//有效的报价供应商 数量 必须大于等于 规矩最少供应商报价数量 否则流拍
							OBProject upstatus1 = new OBProject();
							upstatus1.setStatus(4);
							upstatus1.setId(op.getId());
							upstatus1.setUpdatedAt(new Date());
							OBprojectMapper
									.updateByPrimaryKeySelective(upstatus1);
						  }
						}
					}
					break;
				case 5:// 第一轮待确认
					//开始时间 加 报价时间 加确定时间 
					Date startDate=new Date();
					// 根据竞价id 获取 是否 在规定的时间内 参与报价
					int compareDate1 = DateUtils.compareDate(startDate, op.getEndTime());
					// 比较 竞价信息 如果等于1 那么是竞价确认结束的时间
					if (compareDate1  == 1) {
						// 说明 已发布 的竞价信息 已经超过 确认 时间 获取全部参与报价的供应商 数据
						List<OBProjectResult> prlist = OBProjectResultMapper.selectNotSuppler(op.getId(),null,null);
						// 临时存储交易比例
						int temp = 0;
						//确认 成交数量
						int closingSupplier=0;
						if (prlist != null && prlist.size()>0) {
							for (int i = 0; i < prlist.size(); i++) {
								String proportionString = prlist.get(i)
										.getProportion();
								if (proportionString == null || proportionString == "") {
									proportionString = "0";
								}
								// 累加交易比例
								if (prlist.get(i).getStatus() == 1) {
									closingSupplier++;
									temp = temp + Integer.valueOf(proportionString);
								}
							}
						}
						// 根据状态竞价中 修改竞价结束时间
						OBProject upstatus = new OBProject();
						if(closingSupplier==0){
							// 流拍
							upstatus.setStatus(4);
						}else{
						
					     if(temp<100 && temp>0){
					    	 //竞价二轮 待确认
								upstatus.setStatus(6);
								//竞价结束时间 加上 二轮结束时间
								upstatus.setEndTime(DateUtils.getAddDate(op.getEndTime(), confirmTimeSecond));
							} else if (temp == 0) {
								// 流拍
								upstatus.setStatus(4);
							} else {
								// 竞价结束
								upstatus.setStatus(3);
							}
						}
						upstatus.setId(op.getId());
						upstatus.setUpdatedAt(new Date());
						OBprojectMapper.updateByPrimaryKeySelective(upstatus);
					  }
					break;
				case 6://第二次待确认
					//开始时间 加 报价时间  二轮确定时间
					int compareDate2 = DateUtils.compareDate(new Date(), op.getEndTime());
					// 比较 竞价信息 如果等于1 那么是竞价确认结束的时间
					if (compareDate2 == 1) {
						OBProject project=new OBProject();
						//获取比例是否完成
						String proportion= OBProjectResultMapper.getProportionSum(op.getId());
						if(!proportion.equals("100")){
						//获取 结果全部信息  接受42 放弃32
						List< OBProjectResult> obresultsList = OBProjectResultMapper.getSecond(op.getId());
						if(obresultsList!=null && obresultsList.size()>0){
						  OBProjectResult result=obresultsList.get(0);
						  //修改 状态 第二轮 未选择 默认放弃
						  OBProject obProject = new OBProject();
							obProject.setId(op.getId());
							User users = new User();
							users.setTypeId(result.getSupplierId());
							//------------------------------------------
							OBProjectResult ex=new OBProjectResult();
							 ex.setProjectId(op.getId());
							 ex.setSupplierId(result.getSupplierId());
							 //判断 第一轮 是否有成交 比例
							 OBProjectResult ob= OBProjectResultMapper.selectProportionByProject(ex);
							 String remark="32";
							 if(ob!=null){
								 if(Integer.valueOf(ob.getProportion())>0){
									 remark="22";
								 }else{
									 remark = "32";
								 }
							 }
							BiddingStateUtil.updateRemark(OBProjectSupplierMapper, obProject, users, remark);
							if(obresultsList.size()>1){
							//如果 集合不是最后一条数据 那么 结束时间 在加上 第二轮确定时间
								project.setEndTime(DateUtils.getAddDate(op.getEndTime(), confirmTimeSecond));
							}else{
								//判断竞价 成交供应商是否满足 竞价供应商成交数量
								Integer closingSupplier = OBProjectResultMapper.countByStatus(op.getId());
								//成交供应商 必须大于等于 竞价成交供应商数量
								if(closingSupplier>=leastSupplierNum){
									project.setStatus(3);
								}else{
									//修改竞价状态
									project.setStatus(4);
								}
							}
						  }else{
							//判断竞价 成交供应商是否满足 竞价供应商成交数量
								Integer closingSupplier = OBProjectResultMapper.countByStatus(op.getId());
								//成交供应商 必须大于等于 竞价成交供应商数量
								if(closingSupplier>=op.getTradedSupplierCount()){
									project.setStatus(3);
								}else{
									//修改竞价状态
									project.setStatus(4);
							  }
						  }
					 }else{
						//修改竞价状态结束 
						 project.setStatus(3);
					}
						project.setId(op.getId());
						project.setUpdatedAt(new Date());
						OBprojectMapper.updateByPrimaryKeySelective(project);
					  }
					break;
				}
				}
			}
		}
	}
	/**
	 * 根据需求 计算基准价法的法则 添加排名并保存数据库
	 * @return 有效的排名 和有效数据
	 */
    private List<OBResultsInfo> benchmark(String projectId,String supplierId,Integer valid,String secoundBidding,int percent){
    	
    	List<OBResultsInfo> resultsInfoList=OBResultsInfoMapper.selectByBidding(projectId, secoundBidding, supplierId);
    	if(resultsInfoList!=null &&resultsInfoList.size()>0){
    		//定义临时变量 记录累加
    		BigDecimal acc=new BigDecimal(0);
    	  for (OBResultsInfo accinfo : resultsInfoList) {
    		  acc=acc.add(accinfo.getMyOfferMoney());
    		}
    	     //计算平均数据 如果有少数 四舍五入保留两位小数    
    	     BigDecimal pj=acc.divide(new BigDecimal(resultsInfoList.size()),2,BigDecimal.ROUND_HALF_UP);
    	     //计算有效供应商 平均数 如果供应商报价高于该数 即不入排名 视为无效报价 
    	     BigDecimal validAve=pj.multiply(new BigDecimal(percent/100D)).add(pj).setScale(2, BigDecimal.ROUND_HALF_UP);
    	   
    	     Iterator<OBResultsInfo> iterator = resultsInfoList.iterator();
    	     while(iterator.hasNext()){
    	    	 OBResultsInfo info=iterator.next();
    	    	 if(info!=null){
    	    	 //如果供应商 报价 金额 大于 有效金额 那么删除
    	    	 if(info.getMyOfferMoney().compareTo(validAve)!=-1){
    	    		 iterator.remove();
    	    		 OBProject obProject = new OBProject();
 					obProject.setId(info.getProjectId());
 					User users = new User();
 					users.setTypeId(info.getSupplierId());
    	    		 String remark = "-1";
    	    		 BiddingStateUtil.updateRemark(OBProjectSupplierMapper, obProject, users, remark);
 				   }
    	    	 }
    	     }
    	     acc=new BigDecimal(0);
    	     //计算筛选后的 平均值
    	     for (OBResultsInfo accinfo : resultsInfoList) {
       		  acc=acc.add(accinfo.getMyOfferMoney());
       		}
    	     pj=acc.divide(new BigDecimal(resultsInfoList.size()),2,BigDecimal.ROUND_HALF_UP);
    	     //中标参考价 排名
    	     BigDecimal validJ=pj.multiply(new BigDecimal((100-valid)/100D)).setScale(2, BigDecimal.ROUND_HALF_UP);
    	   
    	      //冒泡 排序  去掉部分 无效数据
    	      for (int k = 0; k < resultsInfoList.size()-1; k++) {
    			for (int k2 = 0; k2 < resultsInfoList.size()-1-k; k2++) {
    				double itemD=Math.abs(resultsInfoList.get(k2).getMyOfferMoney().subtract(validJ).doubleValue());
    				double itemD1=Math.abs(resultsInfoList.get(k2+1).getMyOfferMoney().subtract(validJ).doubleValue());
    				  if(itemD>itemD1){
    					  OBResultsInfo info=resultsInfoList.get(k2);
    					  resultsInfoList.set(k2, resultsInfoList.get(k2+1));
    					  resultsInfoList.set(k2+1,info);
    				  }
    			}
    		}
    	     if(resultsInfoList!=null&&resultsInfoList.size()>0){
    	    	  //选择第一名
    	      BigDecimal small=resultsInfoList.get(0).getMyOfferMoney();
    	      small=small.subtract(validJ).setScale(2, BigDecimal.ROUND_HALF_UP);
    	    Iterator<OBResultsInfo> iter = resultsInfoList.iterator();
     	     while(iter.hasNext()){
     	    	OBResultsInfo info=iter.next();
     	    	if(info!=null){
     	    	 //如果供应商 报价 金额 小于 有效金额 那么删除
     	    	 if(info.getMyOfferMoney().subtract(validJ).setScale(2, BigDecimal.ROUND_HALF_UP).compareTo(small)==-1){
     	    		 iter.remove();
     	    		OBProject obProject = new OBProject();
					obProject.setId(info.getProjectId());
					User users = new User();
					users.setTypeId(info.getSupplierId());
					String remark = "-1";
					BiddingStateUtil.updateRemark(OBProjectSupplierMapper, obProject, users, remark);
  				 }
     	    	 }
     	        }
    	     }
    	}
    	return resultsInfoList;
    }
	/**
	 * 更新竞价信息
	 * 
	 * @author YangHongLiang
	 */
	@Override
	public String updateProject(OBProject obProject, String userid,
			String fileid) {
		// TODO Auto-generated method stub
	   return toJsonProject("success", "执行成功");
	}

	/**
	 * 实现 获取可编辑的竞价信息
	 * 
	 * @author Yanghongliang
	 * @return OBProject
	 */
	@Override
	public OBProject editOBProject(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return OBprojectMapper.selectTemporaryBy(map);
	}

	/**
	 * 
	 * 实现获取 并集 供应商
	 * 
	 * @author YangHongLiang
	 * @exception
	 */
	@Override
	public List<OBSupplier> selecUniontSupplier(
			List<String> productID) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", productID);
		map.put("count", productID.size());
		map.put("date",new Date());
		return OBSupplierMapper.selecUniontSupplier(map);
	}

	/**
	 * 
	 * @Title: selectSupplierOBproject
	 * @Description: 供应商查看符合自己的竞价项目
	 * @author Easong
	 * @param @param map
	 * @param @return 设定文件
	 */
	@Override
	public Map<String, Object> selectSupplierOBproject(Map<String, Object> map) {
		
		// 定义Map集合用来存储竞价列表的信息
		Map<String, Object> supplierQuotoList = new HashMap<String, Object>();
		
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage((Integer) (map.get("page")),
				Integer.parseInt(config.getString("pageSize")));
		// 获取此用户所对应的供应商
		User user = (User) map.get("user");
		if (user != null) {
			map.put("supplier_id", user.getTypeId());
		}
		map.put("date", new Date());
		// 查询符合自己需求的竞价项目
		List<OBProjectSupplier> obProjectList = obProjectSupplierMapper
				.selectSupplierOBprojectList(map);
		// 遍历得到竞价项目信息
		// 定义集合用来封装竞价各段时间的信息
		List<OBRuleTimeInterval> timeList = new ArrayList<OBRuleTimeInterval>();
		if (obProjectList != null && obProjectList.size() > 0) {
			for (OBProjectSupplier obProjectSupplier : obProjectList) {
				List<OBProject> obProject = obProjectSupplier.getObProjectList();
				if (obProject != null && obProject.size() > 0) {
					 for (OBProject obProject2 : obProject) {
						if(obProject2.getStatus()!=0 || obProject2.getStatus()!=3 ||obProject2.getStatus()!=4){
							changeStatus(obProject2.getId());
						}
					}
					
					// 获取第一轮的报价截止时间
					Date quoteEndTime = BiddingStateUtil.getQuoteEndTime(obProject.get(0),obProjectRuleMapper);
					// 获取第二轮的报价截止时间
					Date quotoEndTimeSecond = BiddingStateUtil.getQuotoEndTimeSecond(obProject.get(0), quoteEndTime, OBProjectRuleMapper);
					// 调用封装报价截止时间方法
					obProject.get(0).setQuoteEndTime(quoteEndTime);
					obProject.get(0).setQuoteEndTimeSecond(quotoEndTimeSecond);
					
					// 查询每个竞价信息对应的竞价规则
					OBProjectRule obProjectRule = obProjectRuleMapper.selectByPrimaryKey(obProject.get(0).getId());
					// 创建竞价时间段对象
					OBRuleTimeInterval obRuleTimeInterval = new OBRuleTimeInterval();
					// 设置报价开始时间
					obRuleTimeInterval.setQuotoTimeDate(obProject.get(0).getStartTime());
					// 设置报价结束时间
					obRuleTimeInterval.setEndQuotoTimeDate(quoteEndTime);
					// 设置二次报价结束时间
					obRuleTimeInterval.setEndQuotoTimeDateSecond(quotoEndTimeSecond);
					// 设置第一轮确认时间
					// 第一轮确认时间=报价结束时间+第一轮确认时间
					if(obProjectRule != null){
						Integer confirmTimeInt = obProjectRule.getConfirmTime();
						Date confirmTime = DateUtils.getAddDate(quoteEndTime, confirmTimeInt);
						obRuleTimeInterval.setConfirmTime(confirmTime);
						
						// 设置第二轮确认时间
						// 第二轮确认时间=第一轮确认时间+第二轮确认时间
						Integer confirmTimeSecondInt = obProjectRule.getConfirmTimeSecond();
						Date secondConfirmTime = DateUtils.getAddDate(confirmTime, confirmTimeSecondInt);
						obRuleTimeInterval.setSecondConfirmTime(secondConfirmTime);
						// 设置竞价项目ID
						obRuleTimeInterval.setProjectId(obProject.get(0).getId());
						
						// 将时间段信息存储到集合中
						timeList.add(obRuleTimeInterval);
					}
				}
			}
		}
		// 存储报价列表信息
		supplierQuotoList.put("obProjectList", obProjectList);
		// 存储时间段信息
		supplierQuotoList.put("timeList", timeList);
		return supplierQuotoList;

	}
    /**
     * 实现获取供应商信息
     * @author YangHongLiang
	 * @exception
     */
	@Override
	public List<OBSupplier> supplierList(Integer page,String projectid,
			String name,String status,String result) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", page);
		map.put("projectId", projectid);
		map.put("date",new Date());
		if(StringUtils.isNotBlank(status)){
		//已过期
		if(status.equals("1")){
			map.put("inDate", "1");
		}
		//未过期
		if(status.equals("2")){
			map.put("notDate", "1");
		 }
		}
		//中标 供应商
		if(StringUtils.isNotBlank(result)){
			if(result.equals("1")){
				List<OBProjectResult> prlist = OBProjectResultMapper.selectNotSuppler(projectid,1,"1");
				Set<String> list=new HashSet<>();
				for (OBProjectResult obProjectResult : prlist) {
					list.add(obProjectResult.getSupplierId());
				}
				map.put("result", "1");
				map.put("list", list);
			}
			if(result.equals("2")){
				List<OBProjectResult> prlist = OBProjectResultMapper.selectNotSuppler(projectid,1,"1");
				Set<String> list=new HashSet<>();
				for (OBProjectResult obProjectResult : prlist) {
					list.add(obProjectResult.getSupplierId());
				}
				map.put("result", "2");
				map.put("list", list);
			}
		}
		if(StringUtils.isNotBlank(name)){
			map.put("name", name);
		}
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
		List<OBSupplier> lists = OBSupplierMapper.selectSupplierDate(map);
		return lists;
	}

	@Override
	public int updateProject(OBProject project) {
		// TODO Auto-generated method stub
		return OBprojectMapper.updateByPrimaryKeySelective(project);
	}
	 /***
     *  验证 该产品的是否属于同一个目录 如果不属于那么不能发布竞价
     */
	@Override
	public String verifyCatalog(java.util.List<String> productList) {
		// TODO Auto-generated method stub
		List<OBProduct> productLs= OBProductMapper.selectInId(productList);
		return checkCatalog(productLs);
	}

	/**
	 * 
	* @Title: findBiddingInfo 
	* @Description: 根据主键查询竞价信息
	* @author Easong
	* @param @param projectId
	* @param @return    设定文件 
	* @throws
	 */
	@Override
	public Map<String, Object> findBiddingInfo(String projectId) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> selectMap = new HashMap<String, Object>();
		// 根据id查询竞价信息
		OBProject obProject = obProjectMapper.selectByPrimaryKey(projectId);
		if(obProject != null){
			// 根据采购机构id查询采购机构
			selectMap.put("id", obProject.getOrgId());
			List<Orgnization> list = orgnizationMapper
					.selectByPrimaryKey(selectMap);
			if (list != null && list.size() > 0) {
				Orgnization orgnization = list.get(0);
				map.put("orgName", orgnization.getName());
			}
			// 根据需求单位id获取需求单位
			selectMap.put("id", obProject.getDemandUnit());
			List<Orgnization> demandUnitList = orgnizationMapper
					.selectByPrimaryKey(selectMap);
			if (demandUnitList != null && demandUnitList.size() > 0) {
				Orgnization orgnization = demandUnitList.get(0);
				map.put("demandUnit", orgnization.getName());
			}
			
			// 查询运费
			DictionaryData dictionaryData = dictionaryDataMapper.selectByPrimaryKey(obProject.getTransportFees());
			map.put("transportFees", dictionaryData.getName());
			// 存储竞价信息
			map.put("obProject", obProject);
		}
		return map;
	}
    /**
     * 实现导出竞价信息数据 非暂存信息
     * @author YangHongLiang
     */
	@Override
	public boolean exportProject(String start, String end, Date synchDate) {
		// TODO Auto-generated method stub
		boolean boo=false;
		//获取竞价非暂存信息
        List<OBProject> createList=obProjectMapper.selectByCreateDate(start, end);
        List<UploadFile> uploadList=new ArrayList<>();
        if(createList!=null&&createList.size()>0){
        for (OBProject obProject : createList) {
			List<OBProductInfo> product=OBProductInfoMapper.selectByProjectId(obProject.getId());
			obProject.setObProductInfo(product);
			List<OBProjectSupplier> projectSupplier=obProjectSupplierMapper.selByProjectId(obProject.getId());
			obProject.setObProjectSupplier(projectSupplier);
			if(StringUtils.isNotBlank(obProject.getAttachmentId())){
			//查询文件路径
			List<UploadFile> fileList = uploadService.findBybusinessId(obProject.getAttachmentId(),Constant.TENDER_SYS_KEY);
			uploadList.addAll(fileList);
			}
		 }
        FileUtils.writeFile(FileUtils.getExporttFile(FileUtils.C_OB_PROJECT_STATUS_FILENAME, 5),JSON.toJSONString(createList));
        }
        //同步附件
        if (uploadList != null && uploadList.size() > 0){
            FileUtils.writeFile(FileUtils.getExporttFile(FileUtils.C_OB_PROJECT_FILE_FILENAME, 5),JSON.toJSONString(uploadList));
            String basePath = FileUtils.attachExportPath(synchro.util.Constant.DATA_TYPE_BIDDING_FILE_CODE);
            if (StringUtils.isNotBlank(basePath)){
                OperAttachment.writeFile(basePath, uploadList);
                recordService.synchBidding(synchDate, new Integer(uploadList.size()).toString(), synchro.util.Constant.DATA_TYPE_ATTACH_CODE, synchro.util.Constant.OPER_TYPE_EXPORT, synchro.util.Constant.CREATED_COMMIT_ATTACH);
            }
        }
        recordService.synchBidding(synchDate, new Integer(createList.size()).toString(), synchro.util.Constant.DATA_TYPE_BIDDING_CODE, synchro.util.Constant.OPER_TYPE_EXPORT, synchro.util.Constant.OB_PROJECT_COMMIT);
		boo=true;
		return boo;
	}
    /**
     * 实现导出竞价结果信息 (竞价结束和流拍的竞价状态数据)
     */
	@Override
	public boolean exportProjectResult(String start, String end, Date synchDate) {
		// TODO Auto-generated method stub
		boolean boo=false;
		//获取竞价非暂存信息
        List<OBProject> createList=obProjectMapper.selectByUpdateDate(start, end);
        if(createList!=null&& createList.size()>0){
        	for (OBProject obProject : createList) {
            List<OBProjectResult> result=OBProjectResultMapper.selectRelationDate(obProject.getId()) ;
            obProject.setObProjectResult(result);
             List<OBProjectSupplier> supplier=OBProjectSupplierMapper.selByProjectId(obProject.getId());
             obProject.setObProjectSupplier(supplier);
        	}
            //将封装的 数据 保存
        	 FileUtils.writeFile(FileUtils.getExporttFile(FileUtils.C_OB_PROJECT_RESULT_FILENAME, 9),JSON.toJSONString(createList));
        }
        recordService.synchBidding(synchDate, new Integer(createList.size()).toString(), synchro.util.Constant.DATA_TYPE_BIDDING_RESULT_CODE, synchro.util.Constant.OPER_TYPE_EXPORT, synchro.util.Constant.OB_RESULT_COMMIT);
		boo=true;
		return boo;
	}
    /**
     * 实现竞价文件 数据文件导入
     */
	@Override
	public boolean importProject(File file) {
		// TODO Auto-generated method stub
		boolean boo=false;
		 List<OBProject> list = FileUtils.getBeans(file, OBProject.class); 
	        if (list != null && list.size() > 0){
	        	for (OBProject item : list) {
	        	Integer count=	obProjectMapper.countById(item.getId());
	        	  if(count==0){
	        		  obProjectMapper.insertSelective(item);
	        		  obProjectRuleMapper.insertSelective(item.getObProjectRule());
	        		  List<OBProductInfo> obProductInfo=item.getObProductInfo();
	        		  if(obProductInfo!=null&&obProductInfo.size()>0){
	        		  //循环导入竞价产品数据
	        		  for (OBProductInfo obProductInfo2 : obProductInfo) {
	        			  OBProductInfoMapper.insertSelective(obProductInfo2);
					    }
	        		  }
	        		  List<OBProjectSupplier> projectSupplier=item.getObProjectSupplier();
	        		  //循环 导入竞价与供应商关系表
	        		  if(projectSupplier!=null && projectSupplier.size()>0){
	        			  for (OBProjectSupplier obProjectSupplier : projectSupplier) {
	        				  OBProjectSupplierMapper.insertSelective(obProjectSupplier);
						}
	        		  }
	        	    }
				}
	        	recordService.synchBidding(new Date(), list.size()+"", synchro.util.Constant.DATA_TYPE_BIDDING_CODE, synchro.util.Constant.OPER_TYPE_IMPORT, synchro.util.Constant.OB_PROJECT_COMMIT_IMPORT);
	        }
	        boo=true;
		return boo;
	}
    /**
     * 实现导入文件
     */
	@Override
	public boolean importFile(File file) {
		// TODO Auto-generated method stub
		boolean boo=true;
		 List<UploadFile> list = FileUtils.getBeans(file, UploadFile.class); 
	        if (list != null && list.size() > 0){
	            for (UploadFile uploadFile : list){
	                Integer count = uploadService.findCountById(uploadFile.getId(),Constant.TENDER_SYS_KEY);
	                if (count > 0){
	                    uploadService.updateFile(uploadFile, Constant.TENDER_SYS_KEY);
	                } else {
	                    uploadService.insertFile(uploadFile,Constant.TENDER_SYS_KEY);
	                }
	            }
	            recordService.importAttach(new Integer(list.size()).toString());
	        }
		return boo;
	}
    /**
     * 实现导入竞价结果 数据
     */
	@Override
	public boolean importProjectResult(File file) {
		// TODO Auto-generated method stub
		boolean boo=false;
		 List<OBProject> list = FileUtils.getBeans(file, OBProject.class); 
		   if(list!=null&& list.size()>0){
			   for (OBProject obProject : list) {
				   Integer count=	obProjectMapper.countById(obProject.getId());
		        	  if(count>0){
		        		  //根系状态
		        		  obProjectMapper.updateByPrimaryKey(obProject);
		        		  //获取结果集合
		        		  List<OBProjectResult> result= obProject.getObProjectResult(); 
		        		  if(result!=null&& result.size()>0){
		        			  //循环更新结果表
		        		  for (OBProjectResult obProjectResult : result) {
							Integer rcount=OBProjectResultMapper.countById(obProjectResult.getId());
							if(rcount==0){
								OBProjectResultMapper.insertSelective(obProjectResult);
							}else{
								OBProjectResultMapper.updateByPrimaryKey(obProjectResult);
							}
							 List<OBResultSubtabulation> slist=obProjectResult.getObResultSubtabulation();
							 if(slist!=null&& slist.size()>0){
								 for (OBResultSubtabulation obResultSubtabulation : slist) {
									 Integer rs=OBResultSubtabulationMapper.countById(obResultSubtabulation.getId());
									 if(rs==0){
										 OBResultSubtabulationMapper.insertSelective(obResultSubtabulation);
									 }else{
										 OBResultSubtabulationMapper.updateByPrimaryKeySelective(obResultSubtabulation);
									 }
								}
								 
							 }
						   }
		        		  }
		                   List<OBProjectSupplier> supplier=obProject.getObProjectSupplier();
		                   if(supplier!=null&&supplier.size()>0){
		                	   //循环 更新关系表
		                	   for (OBProjectSupplier obProjectSupplier : supplier) {
		                		   Integer rcount=OBProjectSupplierMapper.countById(obProjectSupplier.getId());
									if(rcount>0){
										OBProjectSupplierMapper.updateByPrimaryKey(obProjectSupplier);
									}else{
										OBProjectSupplierMapper.insertSelective(obProjectSupplier);
									}
							    }
		                   }
		                   
		        	  }
			 }
			   recordService.synchBidding(new Date(), list.size()+"", synchro.util.Constant.DATA_TYPE_BIDDING_RESULT_CODE, synchro.util.Constant.OPER_TYPE_IMPORT, synchro.util.Constant.OB_RESULT_COMMIT_IMPORT);
		   }
		return boo;
	}
}
