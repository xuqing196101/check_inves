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
	
	/**
	 * 
	 * Description: 查询质量管理体系认证证书审核id
	 * 
	 * @author zhang shubin
	 * @data 2017年9月15日
	 * @param 
	 * @return
	 */
	public String findCertProByProIdAndName(String proId);
}
