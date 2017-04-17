package ses.service.sms.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.SupplierCertEngMapper;
import ses.model.sms.SupplierCertEng;
import ses.service.sms.SupplierCertEngService;

@Service(value = "supplierCertEngService")
public class SupplierCertEngServiceImpl implements SupplierCertEngService {

	@Autowired
	private SupplierCertEngMapper supplierCertEngMapper;

	@Override
	public void saveOrUpdateCertEng(SupplierCertEng supplierCertEng) {
//		String id = supplierCertEng.getId();
//		if (id != null && !"".equals(id)) {
//			supplierCertEngMapper.updateByPrimaryKeySelective(supplierCertEng);
//		} else {
			supplierCertEngMapper.insertSelective(supplierCertEng);
//		}
	}

	@Override
	public void deleteCertEng(String certEngIds) {
		for (String id : certEngIds.split(",")) {
			supplierCertEngMapper.deleteById(id);
		}
	}

	public SupplierCertEng queryById(String id) {
		SupplierCertEng certEng = supplierCertEngMapper.selectByPrimaryKey(id);
		return certEng;
	}

	@Override
	public List<SupplierCertEng> queryByEngId(String endId) {
		// TODO Auto-generated method stub
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
        if (null != validateCertCode && validateCertCode.size() == 1) {
            if (validateCertCode.get(0).getId().equals(supplierCertEng.getId())) {
                return true;
            } else {
                return false;
            }
        } else {
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

}
