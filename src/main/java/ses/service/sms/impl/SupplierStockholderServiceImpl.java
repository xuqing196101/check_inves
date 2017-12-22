package ses.service.sms.impl;

import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import ses.dao.sms.SupplierStockholderMapper;
import ses.dao.sms.SupplierStockholderRecyMapper;
import ses.model.sms.SupplierStockholder;
import ses.model.sms.SupplierStockholderRecy;
import ses.service.sms.SupplierService;
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
	@Autowired
	private SupplierStockholderRecyMapper supplierStockholderRecyMapper;
	@Autowired
	private SupplierService supplierService;

	@Override
	public int saveOrUpdateStockholder(SupplierStockholder supplierStockholder) {
		String id = UUID.randomUUID().toString().toUpperCase().replace("-", "");
		supplierStockholder.setId(id);
		return supplierStockholderMapper.insertSelective(supplierStockholder);
	}

	public SupplierStockholder queryById(String id) {
		SupplierStockholder stockholder = supplierStockholderMapper.selectByPrimaryKey(id);
		return stockholder;
	}

	@Override
	public List<SupplierStockholder> findStockholderBySupplierId(
			String supplierId) {
		return supplierStockholderMapper.findStockholderBySupplierId(supplierId);
	}

	@Override
	public boolean deleteStockholderByIds(String ids) {
		boolean isSuccess = false;
		try {
			if (StringUtils.isNotBlank(ids)) {
				String[] idArray = ids.split(",");
				int delCount = 0;
				int hasCount = 0;
				String supplierSt = null;
				for (int i = 0; i < idArray.length; i++) {
					String id = idArray[i];
					if (StringUtils.isNotBlank(id)) {
						SupplierStockholder stockholder = supplierStockholderMapper.selectByPrimaryKey(id);
						if (stockholder != null) {
							int key = supplierStockholderMapper.deleteByPrimaryKey(id);
							if (key == 1) {
								delCount++;
								// 将删除的记录保存至回收站
								if(supplierSt == null){
									String supplierId = stockholder.getSupplierId();
									supplierSt = supplierService.getStatusById(supplierId);
								}
								if("2".equals(supplierSt)){
									SupplierStockholderRecy supplierStockholderRecy = new SupplierStockholderRecy();
									BeanUtils.copyProperties(stockholder, supplierStockholderRecy);
									supplierStockholderRecyMapper.insertSelective(supplierStockholderRecy);
								}
							}
							hasCount++;
						}
					}
				}
				if (delCount == hasCount) {
					isSuccess = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return isSuccess;
	}
	
}
