package ses.service.sms.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import ses.dao.sms.SupplierCertSellMapper;
import ses.model.sms.SupplierCertSell;

@Service(value = "supplierCertSellService")
public class SupplierCertSellServiceImpl implements ses.service.sms.SupplierCertSellService {
	
	@Autowired
	private SupplierCertSellMapper supplierCertSellMapper;
	
	@Override
	public int saveOrUpdateCertSell(SupplierCertSell supplierCertSell) {
//		String id = supplierCertSell.getId();
//		if (id != null && !"".equals(id)) {
//			supplierCertSellMapper.updateByPrimaryKeySelective(supplierCertSell);
//		} else {
			return supplierCertSellMapper.insertSelective(supplierCertSell);
//		}

	}

	@Override
	public SupplierCertSell queryById(String id) {
		SupplierCertSell sell = supplierCertSellMapper.selectByPrimaryKey(id);
		return sell;
	}

	@Override
	public List<SupplierCertSell> queryBySaleId(String saleId) {
		return supplierCertSellMapper.findCertSellBySupplierMatSellId(saleId);
	}

	@Override
	public boolean deleteCertSellByIds(String ids) {
		boolean isSuccess = false;
	    try{
            if(StringUtils.isNotBlank(ids)){
                String[] idArray = ids.split(",");
                int delCount = 0;
                for(int i=0;i<idArray.length;i++){
                    if(StringUtils.isNotBlank(idArray[i])){
                        int key = supplierCertSellMapper.deleteByPrimaryKey(idArray[i]);
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
