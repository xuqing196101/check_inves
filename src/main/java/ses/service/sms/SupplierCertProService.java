package ses.service.sms;

import java.util.List;

import ses.model.sms.SupplierCertPro;

public interface SupplierCertProService {
	
	public int saveOrUpdateCertPro(SupplierCertPro supplierCertPro);
	
	public SupplierCertPro queryById(String id);
	
	public List<SupplierCertPro> queryByProId(String proId);

	/**
	 * 批量删除生产证书
	 * @param ids
	 * @return
	 */
	public boolean deleteCertProByIds(String ids);
}
