package ses.service.sms.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import ses.dao.sms.SupplierCertServeMapper;
import ses.model.sms.SupplierCertServe;
import ses.service.sms.SupplierCertSeService;

@Service(value = "supplierCertSeService")
public class SupplierCertSeServiceImpl implements SupplierCertSeService {
	
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
	public List<SupplierCertServe> queryByServerId(String serverId) {
		return supplierCertSeMapper.findCertSeByMatSeId(serverId);
	}

	@Override
	public boolean deleteCertSeByIds(String ids) {
		boolean isSuccess = false;
		try {
			if (StringUtils.isNotBlank(ids)) {
				String[] idArray = ids.split(",");
				int delCount = 0;
				int hasCount = 0;
				for (int i = 0; i < idArray.length; i++) {
					String id = idArray[i];
					if (StringUtils.isNotBlank(id)) {
						SupplierCertServe certServe = supplierCertSeMapper.selectByPrimaryKey(id);
						if (certServe != null) {
							int key = supplierCertSeMapper.deleteByPrimaryKey(id);
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
