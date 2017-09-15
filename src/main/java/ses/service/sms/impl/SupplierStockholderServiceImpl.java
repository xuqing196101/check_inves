package ses.service.sms.impl;

import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

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
	    try{
            if(StringUtils.isNotBlank(ids)){
                String[] idArray = ids.split(",");
                int delCount = 0;
                for(int i=0;i<idArray.length;i++){
                    if(StringUtils.isNotBlank(idArray[i])){
                        int key = supplierStockholderMapper.deleteByPrimaryKey(idArray[i]);
                        if(key == 1){
                            delCount++;
                        }
                    }
                }
                if(delCount==idArray.length){
                    isSuccess = true;
                }
            }
        }catch (Exception e){
	        e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return isSuccess;
	}
	
}
