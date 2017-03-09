package bss.service.ob.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bss.dao.ob.OBProductInfoMapper;
import bss.dao.ob.OBProductMapper;
import bss.dao.ob.OBProjectMapper;
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
	@Override
	public Map<String, Object> findQuoteInfo(String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 根据id查询报价信息
		OBProject obProject = obProjectMapper.selectByPrimaryKey(id);
		if(obProject != null){
			// 存储报价信息
			map.put("obProject", obProject);
		}
		// 根据标题id查询该标题下发布的产品信息
		OBProductInfoExample example = new OBProductInfoExample();
		Criteria criteria = example.createCriteria();
		criteria.andProjectIdEqualTo(id);
		// 根据标题的id查询标题下所有的商品信息
		List<OBProductInfo> list = obProductInfoMapper.selectByExample(example);
		map.put("oBProductInfoList", list);
		return null;
	}

}
