package ses.service.sms.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import ses.dao.sms.SupplierCertServeMapper;
import ses.model.sms.SupplierCertServe;

@Service(value = "supplierCertSeService")
public class SupplierCertSeServiceImpl implements ses.service.sms.SupplierCertSeService {
	
	@Autowired
	private SupplierCertServeMapper supplierCertSeMapper;
	
	@Override
	public int saveOrUpdateCertSe(SupplierCertServe supplierCertSe) {
//		String id = supplierCertSe.getId();
//		if (id != null && !"".equals(id)) {
//			supplierCertSeMapper.updateByPrimaryKeySelective(supplierCertSe);
//		} else {
			return supplierCertSeMapper.insertSelective(supplierCertSe);
//		}

	}

	@Override
	public SupplierCertServe queryById(String id) {
		SupplierCertServe server = supplierCertSeMapper.selectByPrimaryKey(id);
		return server;
	}

	@Override
	public List<SupplierCertServe> queryServerId(String serverId) {
		return supplierCertSeMapper.findCertSeBySupplierMatSeId(serverId);
	}

	@Override
	public boolean deleteCertSeByIds(String ids) {
		boolean isSuccess = false;
	    try{
            if(StringUtils.isNotBlank(ids)){
                String[] idArray = ids.split(",");
                int delCount = 0;
                for(int i=0;i<idArray.length;i++){
                    if(StringUtils.isNotBlank(idArray[i])){
                        int key = supplierCertSeMapper.deleteByPrimaryKey(idArray[i]);
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
