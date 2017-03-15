package bss.service.ob.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.oms.OrgnizationMapper;
import ses.model.bms.User;
import ses.model.oms.Orgnization;
import ses.util.DictionaryDataUtil;
import bss.dao.ob.OBProductInfoMapper;
import bss.dao.ob.OBProductMapper;
import bss.dao.ob.OBProjectMapper;
import bss.dao.ob.OBResultsInfoMapper;
import bss.dao.ob.OBSupplierMapper;
import bss.model.ob.OBProduct;
import bss.model.ob.OBProductInfo;
import bss.model.ob.OBProductInfoExample;
import bss.model.ob.OBProductInfoExample.Criteria;
import bss.model.ob.OBProject;
import bss.model.ob.OBProjectExample;
import bss.model.ob.OBResultInfoList;
import bss.model.ob.OBResultsInfo;
import bss.model.ob.OBResultsInfoExt;
import bss.service.ob.OBSupplierQuoteService;

import common.constant.Constant;
import common.model.UploadFile;
import common.service.UploadService;
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

	@Override
	public Map<String, Object> findQuoteInfo(String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		HashMap<String, Object> selectMap = new HashMap<String, Object>();

		// 根据id查询报价信息
		OBProject obProject = obProjectMapper.selectByPrimaryKey(id);

		if (obProject != null) {
			// 获取竞价的上传文件项
			String attachmentId = obProject.getAttachmentId();
			if(attachmentId != null){
				String typeId = DictionaryDataUtil.getId("BIDD_INFO_MANAGE_ANNEX");
				String sysKey = String.valueOf(Constant.TENDER_SYS_KEY);
				List<UploadFile> uploadFiles = uploadService.getFilesOther(
						attachmentId, typeId, sysKey);
				if (uploadFiles != null && uploadFiles.size() > 0) {
					map.put("uploadFiles", uploadFiles);
				}
			}
			// 存储报价信息
			map.put("obProject", obProject);
			// 根据采购机构id查询采购机构
			selectMap.put("id", obProject.getOrgId());
			List<Orgnization> list = orgnizationMapper
					.selectByPrimaryKey(selectMap);
			if (list != null && list.size() > 0) {
				Orgnization orgnization = list.get(0);
				map.put("orgName", orgnization.getName());
			}
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
		if(index > 0){
			productIds = sb.toString().substring(0, index);
			// 存储所有商品的id
			map.put("productIds", productIds);
		}
		// 存储参与竞价的产品信息的详细内容
		map.put("oBProductInfoList", list);
		return map;
	}

	@Override
	public JdcgResult saveQuoteInfo(Map<String, Object> map) {
		String titleId = (String) map.get("titleId");
		// 获取用户
		User user = (User) map.get("user");
		OBResultInfoList obResultInfoList = (OBResultInfoList) map
				.get("obResultsInfoExtList");
		if (user == null) {
			return JdcgResult.ok("请先登录!");
		}
		if (obResultInfoList != null) {
			List<OBResultsInfoExt> obResultsInfoExtList = obResultInfoList
					.getObResultsInfoExt();
			for (OBResultsInfoExt obResultsInfoExt : obResultsInfoExtList) {
				// 设置竞价标题
				obResultsInfoExt.setProjectId(titleId);
				// 设置供应商id
				obResultsInfoExt.setSupplierId(user.getId());
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
				obResultsInfoMapper.insert(obResultsInfo);
				
			}
		}
		return JdcgResult.ok("操作成功，请在报价截止时间后，查看本次中标结果！");
	}
}
