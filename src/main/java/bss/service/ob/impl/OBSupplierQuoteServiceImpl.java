package bss.service.ob.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.oms.OrgnizationMapper;
import ses.model.oms.Orgnization;
import bss.dao.ob.OBProductInfoMapper;
import bss.dao.ob.OBProductMapper;
import bss.dao.ob.OBProjectMapper;
import bss.model.ob.OBProduct;
import bss.model.ob.OBProductInfo;
import bss.model.ob.OBProductInfoExample;
import bss.model.ob.OBProductInfoExample.Criteria;
import bss.model.ob.OBProject;
import bss.service.ob.OBSupplierQuoteService;

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

	@Override
	public Map<String, Object> findQuoteInfo(String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		HashMap<String, Object> selectMap = new HashMap<String, Object>();
		// 根据id查询报价信息
		OBProject obProject = obProjectMapper.selectByPrimaryKey(id);
		if (obProject != null) {
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
				if (product != null) {
					sb.append(product.getId() + ",");
				}
			}
		}
		int index = sb.toString().lastIndexOf(",");
		String productIds = sb.toString().substring(0, index);
		// 存储参与竞价的产品信息的详细内容
		map.put("oBProductInfoList", list);
		// 存储所有商品的id
		map.put("productIds", productIds);
		return map;
	}
}
