package ses.service.sms.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import ses.dao.sms.SupplierCertProMapper;
import ses.model.sms.SupplierCertPro;
import ses.service.sms.SupplierCertProService;

@Service(value = "supplierCertProService")
public class SupplierCertProServiceImpl implements SupplierCertProService {
	
	@Autowired
	private SupplierCertProMapper supplierCertProMapper;
	
	@Override
	public int saveOrUpdateCertPro(SupplierCertPro supplierCertPro) {
		Integer sign = supplierCertPro.getSign();
		if (sign == 2) {
			return supplierCertProMapper.updateByPrimaryKeySelective(supplierCertPro);
		} else if (sign == 1) {
			return supplierCertProMapper.insertSelective(supplierCertPro);
		}
		return 0;
	}

	@Override
	public SupplierCertPro queryById(String id) {
		SupplierCertPro certPro = supplierCertProMapper.selectByPrimaryKey(id);
		return certPro;
	}

	@Override
	public List<SupplierCertPro> queryByProId(String proId) {
		return supplierCertProMapper.findCertProByProId(proId);
	}

	@Override
	public boolean deleteCertProByIds(String ids) {
		boolean isSuccess = false;
	    try{
            if(StringUtils.isNotBlank(ids)){
                String[] idArray = ids.split(",");
                int delCount = 0;
                for(int i=0;i<idArray.length;i++){
                    if(StringUtils.isNotBlank(idArray[i])){
                        int key = supplierCertProMapper.deleteByPrimaryKey(idArray[i]);
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
	
	@Override
	public String findCertProByProIdAndName(String proId){
		if(proId != null){
			List<SupplierCertPro> list = supplierCertProMapper.findCertProByProIdAndName(proId, "质量管理体系认证证书");
			if(list != null && list.size() > 0 && list.get(0) != null){
				return list.get(0).getId();
			}else{
				return null;
			}
		}else{
			return null;
		}
		
	}

}
