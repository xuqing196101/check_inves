package bss.service.ob.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.model.bms.User;
import ses.util.DictionaryDataUtil;
import ses.util.PropertiesUtil;
import bss.dao.ob.OBProductInfoMapper;
import bss.dao.ob.OBProductMapper;
import bss.dao.ob.OBProjectMapper;
import bss.dao.ob.OBProjectResultMapper;
import bss.dao.ob.OBProjectSupplierMapper;
import bss.dao.ob.OBResultsInfoMapper;
import bss.dao.ob.OBRuleMapper;
import bss.dao.ob.OBSupplierMapper;
import bss.model.ob.OBProduct;
import bss.model.ob.OBProductInfo;
import bss.model.ob.OBProject;
import bss.model.ob.OBProjectResult;
import bss.model.ob.OBProjectSupplier;
import bss.model.ob.OBResultsInfo;
import bss.model.ob.OBRule;
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
import common.utils.DateUtils;
import bss.dao.ob.OBSpecialDateMapper;

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
	public String getProduct() {
		// TODO Auto-generated method stub
		List<OBProduct> list = OBProductMapper.selectList();
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
		// 默认规则
		 OBRule obr = OBRuleMapper.selectByPrimaryKey(obProject.getRuleId());
		 if(obr ==null){
				show = "发布竞价请先设置默认规则";
		    return toJsonProject("attribute", show);
		 }
		
		// 获取间隔日
		int intervalWorkday = obr.getIntervalWorkday();
		// 获取竞价开始 时间忽略年月日
		Date definiteTime = obr.getDefiniteTime();
		// 获取报价时间
		int quoteTime = obr.getQuoteTime();
		// 确认时间
		int confirmTime = obr.getConfirmTime();
		// 第二轮 确认时间
		int confirmTimeSecond = obr.getConfirmTimeSecond();
		// 获取当前日期
		Date date = new Date();
		// 1.当前时间加上间隔日
		date = DateUtils.addDayDate(date, intervalWorkday);
		// date加上竞价开始时间 计算竞价开始时间 未加特殊节假日
		Date startdate = DateUtils.changeDate(date, definiteTime);

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

		// 生成ID
		String uuid = UUID.randomUUID().toString().toUpperCase()
				.replace("-", "");
		obProject.setCreatedAt(date);
		obProject.setCreaterId(userid);
		obProject.setStartTime(startdate);
		obProject.setEndTime(endDate);
		List<OBProductInfo> list = new ArrayList<OBProductInfo>();
		// 如果有 id 更新数据
		if (StringUtils.isNotBlank(obProject.getId())) {
			OBprojectMapper.updateByPrimaryKeySelective(obProject);
			// 组合 集合
			list = splitList(list, obProject, userid);
			for (OBProductInfo b : list) {
				b.setUpdatedAt(date);
				OBProductInfoMapper.updateByPrimaryKeySelective(b);
			}
			//删除关系
			OBProjectSupplierMapper.deleteByProjectId(obProject.getId());
			// 更新关系数据
			addSupplier(obProject,uuid, 1);
			if (obProject.getStatus() == 1) {
				//如果发布 更新规则数量 时间
				OBRuleMapper.updateCount(obProject.getRuleId(),date);
			}
		} else {
			obProject.setId(uuid);
			// 组合 集合
			list = splitList(list, obProject, userid);
			SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
			SimpleDateFormat ymd=new SimpleDateFormat("yyyy-MM-dd");
			Date start=new Date();
			//生成竞价编号
			int countByDate=OBprojectMapper.countByDate(ymd.format(start));
			BigDecimal big=new BigDecimal(sdf.format(start)+"00000");
			obProject.setProjectNumber(big.add(new BigDecimal(countByDate+1)));
			int i = OBprojectMapper.insertSelective(obProject);
			if (i > 0) {
				for (OBProductInfo b : list) {
					OBProductInfoMapper.insertSelective(b);
				}
				// 保存关系数据
				addSupplier(obProject,uuid, 0);
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
    	   tempCount=OBSupplierMapper.countProductId(productName.get(i));
    	   tempCount=tempCount/4;
    		 if(tempCount<count){
    			 return toJsonProject("pName", productName.get(i));
    		 }
		}
    	 List<OBProduct> productList= OBProductMapper.selectInId(productName);
    	 return verifyCatalog(productList);
     }
     /***
      *  验证 该产品的是否属于同一个目录 如果不属于那么不能发布竞价
      */
     private String verifyCatalog(List<OBProduct> productList){
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
			List<OBSupplier> supList= OBSupplierMapper.selectSupplierByID(map);
			   for(OBSupplier os:supList){
				   supplier = new OBProjectSupplier();
					supplier.setId(UUID.randomUUID().toString().toUpperCase()
							.replace("-", ""));
					supplier.setCreatedAt(new Date());
					supplier.setProjectId(uuid);
					supplier.setRemark("0");
					supplier.setSupplierPrimaryId(os.getId());
					if (i == 1) {
						supplier.setUpdatedAt(new Date());
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
		// 拆分数组
		List<String> productName = obProject.getProductName();
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
			product.setCreatedAt(new Date());
			product.setCreaterId(userid);
			list.add(product);
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
						pidList.add(obinfo.getProductId());
					}
					Map<String, Object> maps = new HashMap<String, Object>();
					maps.put("list", pidList);
					Integer closingSupplier=0;
					if(obp.getStatus()!=0){
					// 获取 中标供应商 数量
						closingSupplier = OBProjectResultMapper.countProportion(obp.getId());
					obp.setProductName(pidList);
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
				Integer nn = OBprojectMapper.selOfferSupplierNum(obp.getId());
				obp.setOfferSupplierNumber(nn);
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
	public void changeStatus() {
		// TODO Auto-generated method stub
		// 1.获取 不是暂存 和结束 的竞价数据
		List<OBProject> getOBProject = OBprojectMapper.selectByStatus();
		if (getOBProject != null) {
			// 获取当前 默认规则
			for (OBProject op : getOBProject) {
				OBRule obRule = OBRuleMapper.selectByPrimaryKey(op.getRuleId());
				if(obRule!=null){
					
				
				 //获取报价时间
				 int quoteTime=obRule.getQuoteTime();
				 //确认时间
				 int confirmTime=obRule.getConfirmTime();
				 //第二轮 确认时间
				 int confirmTimeSecond=obRule.getConfirmTimeSecond();
				 
				/** 竞价状态 0：暂存 1已发布 2竞价中 3：竞价结束 4.流拍 5待确认 **/
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
				case 2:// 竞价中
						// 根据竞价id 获取 是否 在规定的时间内 参与报价
					int compareDate = DateUtils.compareDate(new Date(),
							op.getEndTime());
					// 比较 竞价信息 如果等于1  那么是竞价 报价结束的时间
					if (compareDate == 1) {
						// 说明 已发布 的竞价信息 已经超过 报价 时间
						List<OBResultsInfo> obresultsList = OBResultsInfoMapper.selectByProjectId(op.getId());
						// 判断 是否有竞价供应商
						if (obresultsList != null && obresultsList.size() > 0) {
						//判读报价数量是否 达到竞价成交供应商数量
						if(op.getTradedSupplierCount()<=obresultsList.size()){
							for (int i = 0; i < obresultsList.size(); i++) {
								Integer rk[] = null;
								int proportion = 0;
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
								rsult.setProportion(String.valueOf(proportion));
								// 将 参与报价的供应商 插入到结果数据中
								OBProjectResultMapper.insertSelective(rsult);
							}
							// 根据状态竞价中 修改竞价结束时间
							OBProject upstatus = new OBProject();
							upstatus.setStatus(5);
							upstatus.setUpdatedAt(new Date());
							upstatus.setId(op.getId());
							upstatus.setEndTime(DateUtils.getAddDate(
									op.getEndTime(), confirmTime));
							OBprojectMapper
									.updateByPrimaryKeySelective(upstatus);
							}else{
								// 到规定时间  报价人数未达到 竞价成交供应商数量 流拍
								OBProject upstatus = new OBProject();
								upstatus.setStatus(4);
								upstatus.setId(op.getId());
								upstatus.setUpdatedAt(new Date());
								OBprojectMapper.updateByPrimaryKeySelective(upstatus);
							}
						} else {
							// 到规定时间 如果没有竞价供应商 修改竞状态 流拍
							OBProject upstatus = new OBProject();
							upstatus.setStatus(4);
							upstatus.setId(op.getId());
							upstatus.setUpdatedAt(new Date());
							OBprojectMapper
									.updateByPrimaryKeySelective(upstatus);
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
						// 说明 已发布 的竞价信息 已经超过 确认 时间 获取全部参与报价的供应商 数据
						List<OBProjectResult> prlist = OBProjectResultMapper.selectNotSuppler(op.getId(),null,null);
						// 临时存储交易比例
						int temp = 0;
						if (prlist != null && prlist.size()>0) {
							for (int i = 0; i < prlist.size(); i++) {
								String proportionString = prlist.get(i)
										.getProportion();
								if (proportionString == null || proportionString == "") {
									proportionString = "0";
								}
								// 累加交易比例
								if (prlist.get(i).getStatus() == 1) {
									temp = temp + Integer.valueOf(proportionString);
								}
							}
						}
						//获取供应商成交数量
						Integer closingSupplier = OBProjectResultMapper.countByStatus(op.getId());
						if(closingSupplier==null){
							closingSupplier=0;
						}
						// 根据状态竞价中 修改竞价结束时间
						OBProject upstatus = new OBProject();
						if(temp<100 && temp>0){
							if(closingSupplier<op.getTradedSupplierCount()){
								// 流拍
								upstatus.setStatus(4);
							  }else{
								// 竞价结束
								upstatus.setStatus(3);
							  }
							} else if (temp == 0) {
								// 流拍
								upstatus.setStatus(4);
							} else {
								// 竞价结束
								upstatus.setStatus(3);
							}
						upstatus.setId(op.getId());
						upstatus.setUpdatedAt(new Date());
						OBprojectMapper.updateByPrimaryKeySelective(upstatus);
					  }
					break;
				}
				}
			}
		}
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
		return OBprojectMapper.selectTemporary(map);
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
	public List<OBProjectSupplier> selectSupplierOBproject(Map<String, Object> map) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage((Integer) (map.get("page")),
				Integer.parseInt(config.getString("pageSize")));
		// 获取此用户所对应的供应商
		User user = (User) map.get("user");
		if (user != null) {
			map.put("supplier_id", user.getTypeId());
		}
		// 查询符合自己需求的竞价项目
		List<OBProjectSupplier> obProjectList = obProjectSupplierMapper
				.selectSupplierOBprojectList(map);
		// 遍历得到竞价项目信息
		if (obProjectList != null && obProjectList.size() > 0) {
			for (OBProjectSupplier obProjectSupplier : obProjectList) {
				List<OBProject> obProject = obProjectSupplier.getObProjectList();
				if (obProject != null && obProject.size() > 0) {
					// 调用封装报价截止时间方法
					obProject.get(0).setQuoteEndTime(BiddingStateUtil.getQuoteEndTime(obProject.get(0)));
				}
			}
		}
		return obProjectList;

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
			List<OBProjectResult> prlist = OBProjectResultMapper.selectNotSuppler(projectid,null,"1");
			Set<String> list=new HashSet<>();
			for (OBProjectResult obProjectResult : prlist) {
				list.add(obProjectResult.getSupplierId());
			}
			map.put("result", "1");
			map.put("list", list);
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
}
