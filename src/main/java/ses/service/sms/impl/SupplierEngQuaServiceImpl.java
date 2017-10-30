package ses.service.sms.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import ses.dao.sms.SupplierEngQuaMapper;
import ses.model.sms.SupplierEngQua;
import ses.service.sms.SupplierEngQuaService;

@Service(value = "supplierEngQuaService")
public class SupplierEngQuaServiceImpl implements SupplierEngQuaService {
	
	@Autowired
	private SupplierEngQuaMapper supplierEngQuaMapper;
	
	@Override
	public int saveOrUpdateEngQua(SupplierEngQua supplierEngQua) {
//		String id = supplierEngQua.getId();
//		if (id != null && !"".equals(id)) {
//			supplierEngQuaMapper.updateByPrimaryKeySelective(supplierEngQua);
//		} else {
			return supplierEngQuaMapper.insertSelective(supplierEngQua);
//		}

	}

	@Override
	public SupplierEngQua queryById(String id) {
		SupplierEngQua engQua = supplierEngQuaMapper.selectByPrimaryKey(id);
		return engQua;
	}

	@Override
	public List<SupplierEngQua> queryByEngId(String engId) {
		return supplierEngQuaMapper.findEngQuaByMatEngId(engId);
	}

	@Override
	public boolean deleteEngQuaByIds(String ids) {
		boolean isSuccess = false;
		try {
			if (StringUtils.isNotBlank(ids)) {
				String[] idArray = ids.split(",");
				int delCount = 0;
				int hasCount = 0;
				for (int i = 0; i < idArray.length; i++) {
					String id = idArray[i];
					if (StringUtils.isNotBlank(id)) {
						SupplierEngQua engQua = supplierEngQuaMapper.selectByPrimaryKey(id);
						if (engQua != null) {
							int key = supplierEngQuaMapper.deleteByPrimaryKey(id);
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
