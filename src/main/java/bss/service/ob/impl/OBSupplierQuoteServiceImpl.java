package bss.service.ob.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.bms.DictionaryDataMapper;
import ses.dao.oms.OrgnizationMapper;
import ses.model.bms.DictionaryData;
import ses.model.bms.User;
import ses.model.oms.Orgnization;
import ses.util.DictionaryDataUtil;
import bss.dao.ob.OBProductInfoMapper;
import bss.dao.ob.OBProductMapper;
import bss.dao.ob.OBProjectMapper;
import bss.dao.ob.OBProjectResultMapper;
import bss.dao.ob.OBProjectSupplierMapper;
import bss.dao.ob.OBResultsInfoMapper;
import bss.dao.ob.OBSupplierMapper;
import bss.model.ob.OBProduct;
import bss.model.ob.OBProductInfo;
import bss.model.ob.OBProductInfoExample;
import bss.model.ob.OBProductInfoExample.Criteria;
import bss.model.ob.OBProject;
import bss.model.ob.OBProjectExample;
import bss.model.ob.OBProjectSupplier;
import bss.model.ob.OBResultInfoList;
import bss.model.ob.OBResultsInfo;
import bss.model.ob.OBResultsInfoExt;
import bss.model.ob.OBRule;
import bss.model.ob.OBSupplier;
import bss.service.ob.OBSupplierQuoteService;
import bss.util.BiddingStateUtil;
import common.constant.Constant;
import common.model.UploadFile;
import common.service.UploadService;
import common.utils.DateUtils;
import common.utils.JdcgResult;

/**
 * 
 * @ClassName: OBSupplierQuoteService
 * @Description: 供应商后台报价处理接口的实现类
 * @author Easong
 * @date 2017年3月9日 下午5:34:41
 * 
 */
@Service
public class OBSupplierQuoteServiceImpl implements OBSupplierQuoteService {

	@Autowired
	private OBProjectMapper obProjectMapper;

	@Autowired
	private OBProductInfoMapper obProductInfoMapper;

	@Autowired
	private OBProductMapper obProductMapper;
	
	@Autowired
	private OBSupplierMapper OBSupplierMapper;

	// 注入采购机构Mapper
	@Autowired
	private OrgnizationMapper orgnizationMapper;

	// 注入供应商
	@Autowired
	private OBSupplierMapper obSupplierMapper;

	// 注入供应商报价Mapper
	@Autowired
	private OBResultsInfoMapper obResultsInfoMapper;

	@Autowired
	private UploadService uploadService;

	@Autowired
	private OBProjectSupplierMapper obProjectSupplierMapper;

	@Autowired
	private OBProjectResultMapper obProjectResultMapper;

	@Autowired
	private DictionaryDataMapper dictionaryDataMapper;
	/**
	 * 
	 * @Title: findQuoteInfo
	 * @Description: 查看报价信息列表
	 * @author Easong
	 * @param @param id
	 * @param @return 设定文件
	 */
	@Override
	public Map<String, Object> findQuoteInfo(String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> selectMap = new HashMap<String, Object>();

		// 根据id查询竞价信息
		OBProject obProject = obProjectMapper.selectByPrimaryKey(id);

		if (obProject != null) {
			// 获取竞价的上传文件项
			String attachmentId = obProject.getAttachmentId();
			if (attachmentId != null) {
				String typeId = DictionaryDataUtil
						.getId("BIDD_INFO_MANAGE_ANNEX");
				String sysKey = String.valueOf(Constant.TENDER_SYS_KEY);
				List<UploadFile> uploadFiles = uploadService.getFilesOther(
						attachmentId, typeId, sysKey);
				if (uploadFiles != null && uploadFiles.size() > 0) {
					map.put("uploadFiles", uploadFiles);
				}
			}
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
			// 获取成交供应商数量和参与供应商数量
			if (obProject.getStatus() != 0) {
				// 获取产品集合
				List<OBProductInfo> slist = obProductInfoMapper
						.selectByProjectId(obProject.getId());
				// 存储 产品id 集合
				List<String> pidList = new ArrayList<String>();
				if (slist != null) {
					for (OBProductInfo obinfo : slist) {
						pidList.add(obinfo.getProductId());
					}
					Map<String, Object> maps = new HashMap<String, Object>();
					maps.put("list", pidList);
					// 获取 成交供应商 数量
					Integer closingSupplier = obProjectResultMapper
							.countByStatus(obProject.getId());
					obProject.setProductName(pidList);
					if (closingSupplier == null) {
						closingSupplier = 0;
					}
					obProject.setClosingSupplier(closingSupplier);
					// 获取 供应商数量
					List<OBSupplier> sulist=OBSupplierMapper.selectSupplierByID(maps);
					Integer qualifiedSupplier =0;
					if(sulist!=null&&sulist.size()>0){
						qualifiedSupplier=sulist.size();
					}
					obProject.setQualifiedSupplier(qualifiedSupplier);
				}

			}
			// 存储报价信息
			map.put("obProject", obProject);
		}
		// 根据标题id查询该标题下发布的产品信息
		OBProductInfoExample example = new OBProductInfoExample();
		Criteria criteria = example.createCriteria();
		criteria.andProjectIdEqualTo(id);
		// 根据标题的id查询标题下所有的商品信息
		List<OBProductInfo> list = obProductInfoMapper.selectByExample(example);
		StringBuilder sb = new StringBuilder();
		if (list != null && list.size() > 0) {
			for (OBProductInfo obProductInfo : list) {
				OBProduct product = obProductInfo.getObProduct();
				String productId = product.getId();
				if (product != null) {
					sb.append(productId + ",");
				}
			}
		}
		int index = sb.toString().lastIndexOf(",");
		String productIds = "";
		if (index > 0) {
			productIds = sb.toString().substring(0, index);
			// 存储所有商品的id
			map.put("productIds", productIds);
		}
		// 存储参与竞价的产品信息的详细内容
		map.put("oBProductInfoList", list);
		return map;
	}

	/**
	 * 
	 * @Title: saveQuoteInfo
	 * @Description: 开始报价
	 * @author Easong
	 * @param @param map
	 * @param @return 设定文件
	 */
	@Override
	public JdcgResult saveQuoteInfo(Map<String, Object> map) {
		String titleId = (String) map.get("titleId");
		String showQuotoTotalPriceStr = (String) map.get("showQuotoTotalPriceStr");
		// 获取用户
		User user = (User) map.get("user");
		OBResultInfoList obResultInfoList = (OBResultInfoList) map
				.get("obResultsInfoExtList");
		if (user == null) {
			return JdcgResult.ok("请先登录!");
		}
		
		// 排除同一个供应商不同的用户同时报价的情况
		Map<String, Object> selectMap = new HashMap<String, Object>();
		selectMap.put("project_id", titleId);
		selectMap.put("supplier_id", user.getTypeId());
		OBProjectSupplier selectRemarkBYPS = obProjectSupplierMapper.selectRemarkBYPS(selectMap);
		// 其他用户已完成本次报价
		if(selectRemarkBYPS != null && "1".equals(selectRemarkBYPS.getRemark())){
			return JdcgResult.ok("其他用户已完成本次报价！");
		}
		
		if (obResultInfoList != null) {
			List<OBResultsInfoExt> obResultsInfoExtList = obResultInfoList
					.getObResultsInfoExt();
			for (OBResultsInfoExt obResultsInfoExt : obResultsInfoExtList) {
				// 设置竞价标题
				obResultsInfoExt.setProjectId(titleId);
				// 设置供应商id
				obResultsInfoExt.setSupplierId(user.getTypeId());
				// 计算单个商品总价
				// 单个商品的采购数量
				Integer signalCountInt = obResultsInfoExt.getResultsNumber();
				BigDecimal myOfferMoney = obResultsInfoExt.getMyOfferMoney();
				BigDecimal signalCount = null;
				
				if (signalCountInt != null && myOfferMoney != null) {
					// 单个商品的报价
					signalCount = new BigDecimal(signalCountInt);
					obResultsInfoExt.setDealMoney(myOfferMoney
							.multiply(signalCount));
				}
				// 设置创建时间
				obResultsInfoExt.setCreatedAt(new Date());
				// 设置修改时间
				obResultsInfoExt.setUpdatedAt(new Date());
				// 保存
				OBResultsInfo obResultsInfo = new OBResultsInfo();
				BeanUtils.copyProperties(obResultsInfoExt, obResultsInfo);

				// 报价前，判断截止时间是否已到
				// 查询竞价标题信息
				OBProject obProject = obProjectMapper
						.selectByPrimaryKey(titleId);
				if (obProject != null) {
					// 获取报价截止时间
					Date quoteEndTime = BiddingStateUtil
							.getQuoteEndTime(obProject);
					int compareTo = BiddingStateUtil.compareTo(new Date(),
							quoteEndTime);
					// 报价时间截止
					// systemDate > biddingTime
					if (compareTo == 2) {
						// remark 2标识：时间截止，未能及时完成报价
						String remark = "2";
						BiddingStateUtil.updateRemark(obProjectSupplierMapper,
								obProject, user, remark);
						return JdcgResult.ok("抱歉，报价时间已结束，未完成本次报价！");
					}
					// systemDate < biddingTime
					if (compareTo == 1) {
						// remark 1标识：时间还未截止，完成报价
						String remark = "1";
						BiddingStateUtil.updateRemark(obProjectSupplierMapper,
								obProject, user, remark);
					}
				}
				// 竞价还没结束已报价，则显示已报价待确认状态
				obResultsInfoMapper.insert(obResultsInfo);
			}
		}
		return JdcgResult.ok(showQuotoTotalPriceStr);
	}

	
	/**
	 * 
	* @Title: selectQuotoInfo 
	* @Description: 查询报价结果
	* @author Easong
	* @param @param map
	* @param @return    设定文件 
	* @throws
	 */
	@Override
	public Map<String, Object> selectQuotoInfo(Map<String, Object> mapInfo) {
		// 获取projectId
		String projectId = (String) mapInfo.get("projectId");
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> selectMap = new HashMap<String, Object>();

		// 根据id查询竞价信息
		OBProject obProject = obProjectMapper.selectByPrimaryKey(projectId);

		if (obProject != null) {
			// 获取竞价的上传文件项
			String attachmentId = obProject.getAttachmentId();
			if (attachmentId != null) {
				String typeId = DictionaryDataUtil
						.getId("BIDD_INFO_MANAGE_ANNEX");
				String sysKey = String.valueOf(Constant.TENDER_SYS_KEY);
				List<UploadFile> uploadFiles = uploadService.getFilesOther(
						attachmentId, typeId, sysKey);
				if (uploadFiles != null && uploadFiles.size() > 0) {
					map.put("uploadFiles", uploadFiles);
				}
			}
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
			// 获取成交供应商数量和参与供应商数量
			if (obProject.getStatus() != 0) {
				// 获取产品集合
				List<OBProductInfo> slist = obProductInfoMapper
						.selectByProjectId(obProject.getId());
				// 存储 产品id 集合
				List<String> pidList = new ArrayList<String>();
				if (slist != null) {
					for (OBProductInfo obinfo : slist) {
						pidList.add(obinfo.getProductId());
					}
					Map<String, Object> maps = new HashMap<String, Object>();
					maps.put("list", pidList);
					// 获取 成交供应商 数量
					Integer closingSupplier = obProjectResultMapper
							.countByStatus(obProject.getId());
					obProject.setProductName(pidList);
					if (closingSupplier == null) {
						closingSupplier = 0;
					}
					obProject.setClosingSupplier(closingSupplier);
					// 获取 供应商数量
					List<OBSupplier> sulist=OBSupplierMapper.selectSupplierByID(maps);
					Integer qualifiedSupplier =0;
					if(sulist!=null&&sulist.size()>0){
						qualifiedSupplier=sulist.size();
					}
					obProject.setQualifiedSupplier(qualifiedSupplier);
				}

			}
			// 存储报价信息
			map.put("obProject", obProject);
		}
		
		// 查询所有的商品信息
		List<OBResultsInfo> oBResultsInfo = obResultsInfoMapper.selectQuotoInfo(mapInfo);
		map.put("oBResultsInfo", oBResultsInfo);
		return map;
	}
	
	
}
