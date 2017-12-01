package ses.service.sms.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import ses.dao.sms.SupplierAptituteMapper;
import ses.model.sms.SupplierAptitute;
import ses.service.sms.SupplierAptituteService;

@Service(value = "supplierAptituteService")
public class SupplierAptituteServiceImpl implements SupplierAptituteService {
	
	@Autowired
	private SupplierAptituteMapper supplierAptituteMapper;
	
	@Override
	public int saveOrUpdateAptitute(SupplierAptitute supplierAptitute) {
//		String id = supplierAptitute.getId();
//		if (id != null && !"".equals(id)) {
//			supplierAptituteMapper.updateByPrimaryKeySelective(supplierAptitute);
//		} else {
			return supplierAptituteMapper.insertSelective(supplierAptitute);
//		}

	}

	@Override
	public SupplierAptitute queryById(String id) {
		SupplierAptitute aptitute = supplierAptituteMapper.selectByPrimaryKey(id);
		return aptitute;
	}

	@Override
	public List<SupplierAptitute> queryByMatEngId(String matEngId) {
		return supplierAptituteMapper.findAptituteByMatEngId(matEngId);
	}

	@Override
	public List<SupplierAptitute> queryByCodeAndType(String certType,String matEngId,String code, String type) {
		return supplierAptituteMapper.quertByCodeAndName(certType, matEngId, code, type);
	}

	@Override
	public List<String> getProType(String typeId, String matEngId, String code) {
		return supplierAptituteMapper.quertProType(typeId, matEngId, code);
	}

	@Override
	public int selectByCertCode(String certCode) {
		return supplierAptituteMapper.selectByCertCode(certCode);
	}

	@Override
	public boolean deleteAptituteByIds(String ids) {
		boolean isSuccess = false;
		try {
			if (StringUtils.isNotBlank(ids)) {
				String[] idArray = ids.split(",");
				int delCount = 0;
				int hasCount = 0;
				for (int i = 0; i < idArray.length; i++) {
					String id = idArray[i];
					if (StringUtils.isNotBlank(id)) {
						SupplierAptitute aptitute = supplierAptituteMapper.selectByPrimaryKey(id);
						if (aptitute != null) {
							int key = supplierAptituteMapper.deleteByPrimaryKey(id);
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
