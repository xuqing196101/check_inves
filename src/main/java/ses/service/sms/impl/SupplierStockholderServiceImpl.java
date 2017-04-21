package ses.service.sms.impl;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.SupplierStockholderMapper;
import ses.model.sms.SupplierStockholder;
import ses.service.sms.SupplierStockholderService;

/**
 * @Title: SupplierStockholderServiceImpl
 * @Description: SupplierStockholderServiceImpl 实现类
 * @author: Wang Zhaohua
 * @date: 2016-9-8上午11:45:45
 */
@Service(value = "supplierStockholderService")
public class SupplierStockholderServiceImpl implements SupplierStockholderService {

	@Autowired
	private SupplierStockholderMapper supplierStockholderMapper;

	@Override
	public void saveOrUpdateStockholder(SupplierStockholder supplierStockholder) {
		String id = UUID.randomUUID().toString().toUpperCase().replace("-", "");
				supplierStockholder.setId(id);
 
			supplierStockholderMapper.insertSelective(supplierStockholder);
//		}
	}

	@Override
	public void deleteStockholder(String stockholderIds) {
	    if(!StringUtils.isEmpty(stockholderIds)){
            for (String id : stockholderIds.split(",")) {
                supplierStockholderMapper.deleteByPrimaryKey(id);
            }
        }
	}

	public SupplierStockholder queryById(String id) {
		SupplierStockholder stockholder = supplierStockholderMapper.selectByPrimaryKey(id);
		return stockholder;
	}
	
	
	

}
