package ses.service.sms.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import ses.dao.sms.SupplierCertEngMapper;
import ses.model.sms.SupplierCertEng;
import ses.service.sms.SupplierCertEngService;

@Service(value = "supplierCertEngService")
public class SupplierCertEngServiceImpl implements SupplierCertEngService {

	@Autowired
	private SupplierCertEngMapper supplierCertEngMapper;

	@Override
	public int saveOrUpdateCertEng(SupplierCertEng supplierCertEng) {
//		String id = supplierCertEng.getId();
//		if (id != null && !"".equals(id)) {
//			supplierCertEngMapper.updateByPrimaryKeySelective(supplierCertEng);
//		} else {
			return supplierCertEngMapper.insertSelective(supplierCertEng);
//		}
	}

	public SupplierCertEng queryById(String id) {
		SupplierCertEng certEng = supplierCertEngMapper.selectByPrimaryKey(id);
		return certEng;
	}

	@Override
	public List<SupplierCertEng> queryByEngId(String endId) {
		return supplierCertEngMapper.findCertEngByMatEngId(endId);
	}

    /**
     * @see ses.service.sms.SupplierCertEngService#selectCertEngByCode(java.lang.String, java.lang.String)
     */
    @Override
    public List<SupplierCertEng> selectCertEngByCode(String code, String supplierId) {
        return supplierCertEngMapper.selectCertEngByCode(code, supplierId);
    }

    /**
     * @see ses.service.sms.SupplierCertEngService#validateCertCode(ses.model.sms.SupplierCertEng)
     */
    @Override
    public boolean validateCertCode(SupplierCertEng supplierCertEng) {
        List<SupplierCertEng> validateCertCode = supplierCertEngMapper.validateCertCode(supplierCertEng.getCertCode());
        /*if (null != validateCertCode && validateCertCode.size() == 1) {
            if (validateCertCode.get(0).getId().equals(supplierCertEng.getId())) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }*/
		if(validateCertCode == null || validateCertCode.size() == 0){
			return true;
		}
		if(validateCertCode.size() == 1){
			if (validateCertCode.get(0).getId().equals(supplierCertEng.getId())) {
		        return true;
		    } else {
		        return false;
		    }
		}else{
			return false;
		}
    }

    /**
     * @see ses.service.sms.SupplierCertEngService#getLevel(java.lang.String, java.lang.String)
     */
    @Override
    public String getLevel(String typeId, String certCode, String supplierId) {
        Map<String, String> level = supplierCertEngMapper.getLevel(typeId, certCode, supplierId);
        if (level != null && level.size() > 0) {
            return level.get("APT_LEVEL");
        }
        return null;
    }

	@Override
	public List<SupplierCertEng> findCertEngBySupplierId(String supplierId) {
		return supplierCertEngMapper.findCertEngBySupplierId(supplierId);
	}

	@Override
	public boolean deleteCertEngByIds(String ids) {
		boolean isSuccess = false;
		try {
			if (StringUtils.isNotBlank(ids)) {
				String[] idArray = ids.split(",");
				int delCount = 0;
				int hasCount = 0;
				for (int i = 0; i < idArray.length; i++) {
					String id = idArray[i];
					if (StringUtils.isNotBlank(id)) {
						SupplierCertEng cerEng = supplierCertEngMapper.selectByPrimaryKey(id);
						if (cerEng != null) {
							int key = supplierCertEngMapper.deleteByPrimaryKey(id);
							if (key == 1) {
								delCount++;
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
