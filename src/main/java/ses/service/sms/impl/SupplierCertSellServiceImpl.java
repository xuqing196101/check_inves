package ses.service.sms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.SupplierCertSellMapper;
import ses.model.sms.SupplierCertSell;

@Service(value = "supplierCertSellService")
public class SupplierCertSellServiceImpl implements ses.service.sms.SupplierCertSellService {
	
	@Autowired
	private SupplierCertSellMapper supplierCertSellMapper;
	
	@Override
	public void saveOrUpdateCertSell(SupplierCertSell supplierCertSell) {
//		String id = supplierCertSell.getId();
//		if (id != null && !"".equals(id)) {
//			supplierCertSellMapper.updateByPrimaryKeySelective(supplierCertSell);
//		} else {
			supplierCertSellMapper.insertSelective(supplierCertSell);
//		}

	}

	@Override
	public void deleteCertSell(String certSellIds) {
		for (String id : certSellIds.split(",")) {
			supplierCertSellMapper.deleteById(id);
		}
	}

	@Override
	public SupplierCertSell queryById(String id) {
		SupplierCertSell sell = supplierCertSellMapper.selectByPrimaryKey(id);
		return sell;
	}

	@Override
	public List<SupplierCertSell> queryBySaleId(String saleId) {
		// TODO Auto-generated method stub
		return supplierCertSellMapper.findCertSellBySupplierMatSellId(saleId);
	}
}
