package ses.service.sms.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.ProductParamMapper;
import ses.model.sms.ProductParam;
import ses.service.sms.ProductParamService;

@Service(value = "productParamService")
public class ProductParamServiceImpl implements ProductParamService {
	
	@Autowired
	private ProductParamMapper productParamMapper;
	
	@Override
	public void saveOrUpdateParam(ProductParam productParam) {
		String id = productParam.getId();
		if (id != null && !"".equals(id)) {
			productParamMapper.updateByPrimaryKeySelective(productParam);
		} else {
			productParamMapper.insertSelective(productParam);
		}
	}

}
