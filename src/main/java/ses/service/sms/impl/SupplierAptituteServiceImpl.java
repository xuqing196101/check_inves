package ses.service.sms.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.SupplierAptituteMapper;
import ses.model.sms.SupplierAptitute;
import ses.service.sms.SupplierAptituteService;

@Service(value = "supplierAptituteService")
public class SupplierAptituteServiceImpl implements SupplierAptituteService {
	
	@Autowired
	private SupplierAptituteMapper supplierAptituteMapper;
	
	@Override
	public void saveOrUpdateAptitute(SupplierAptitute supplierAptitute) {
//		String id = supplierAptitute.getId();
//		if (id != null && !"".equals(id)) {
//			supplierAptituteMapper.updateByPrimaryKeySelective(supplierAptitute);
//		} else {
			supplierAptituteMapper.insertSelective(supplierAptitute);
//		}

	}

	@Override
	public void deleteAptitute(String aptituteIds) {
	    if(!StringUtils.isEmpty(aptituteIds)){
            for (String id : aptituteIds.split(",")) {
                supplierAptituteMapper.deleteById(id);
            }
        }
	}

	@Override
	public SupplierAptitute queryById(String id) {
		SupplierAptitute aptitute = supplierAptituteMapper.selectByPrimaryKey(id);
		return aptitute;
	}

	@Override
	public List<SupplierAptitute> queryByAptitute(String projectId) {
		// TODO Auto-generated method stub
		return supplierAptituteMapper.findAptituteByMatEngId(projectId);
	}

	@Override
	public List<SupplierAptitute> queryByCodeAndType(String certType,String matEngId,String code, String type) {
		// TODO Auto-generated method stub
		return supplierAptituteMapper.quertByCodeAndName(certType,matEngId, code, type);
	}

	@Override
	public List<String> getPorType(String typeId, String matEngId, String code) {
	 
		return supplierAptituteMapper.quertProType(typeId, matEngId, code);
	}

	@Override
	public int selectByCertCode(String certCode) {
		return supplierAptituteMapper.selectByCertCode(certCode);
	}

	
}
